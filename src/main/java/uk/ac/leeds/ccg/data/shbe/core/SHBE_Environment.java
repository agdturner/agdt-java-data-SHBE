/*
 * Copyright 2018 Andy Turner, CCG, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.data.shbe.core;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.data.ukp.core.UKP_Environment;
import uk.ac.leeds.ccg.data.shbe.data.SHBE_Handler;
import uk.ac.leeds.ccg.data.shbe.io.SHBE_Files;
import uk.ac.leeds.ccg.data.shbe.util.SHBE_Collections;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Environment extends SHBE_MemoryManager
        implements Serializable {

    public final transient Generic_Environment env;
    public final transient Data_Environment de;
    public final transient UKP_Environment oe;
    public final transient SHBE_Files files;
    public transient SHBE_Handler handler;
    public final transient SHBE_Collections collections;
    
    /**
     * Data.
     */
//    public ONSPD_Data data;

    public transient final String EOL = System.getProperty("line.separator");

    /**
     * @param de Data_Environment
     * @throws java.io.IOException If encountered.
     */
    public SHBE_Environment(Data_Environment de) throws IOException, Exception {
        //Memory_Threshold = 3000000000L;
        this.de = de;
        this.env = de.env;
        oe = new UKP_Environment(de);
        files = new SHBE_Files(Paths.get(de.files.getDataDir().toString(), 
                SHBE_Strings.s_SHBE));
        collections = new SHBE_Collections(this);
    }

    /**
     * A method to try to ensure there is enough memory to continue.
     *
     * @return true if all fine
     */
    @Override
    public boolean checkAndMaybeFreeMemory() {
        System.gc();
        while (getTotalFreeMemory() < Memory_Threshold) {
//            int clear = clearAllData();
//            if (clear == 0) {
//                return false;
//            }
            if (!swapSomeData()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean swapSomeData(boolean hoome) {
        try {
            boolean r = swapSomeData();
            checkAndMaybeFreeMemory();
            return r;
        } catch (OutOfMemoryError e) {
            if (hoome) {
                clearMemoryReserve(env);
                boolean r = swapSomeData(HOOMEF);
                initMemoryReserve(env);
                return r;
            } else {
                throw e;
            }
        }
    }

    /**
     * Currently this just tries to cache ONSPD data.
     *
     * @return true if some data was swapped.
     */
    @Override
    public boolean swapSomeData() {
        boolean r = clearSomeData();
        if (r) {
            return r;
        } else {
            env.log("No ONSPD data to clear. Do some coding to try to arrange "
                    + "to clear something else if needs be. If the program "
                    + "fails then try providing more memory...");
            return r;
        }
    }

    public boolean clearSomeData() {
        return handler.clearSome();
    }

    /**
     * Clears all 
     * @return  count of data cleared.
     */
    public int clearAllData() {
        return handler.clearAll();
    }
    
    /**
     * Attempts to write out {@link handler}.
     * @throws java.io.IOException If encountered.
     */
    public void cacheData() throws IOException {
        Path f = files.getEnvDataFile();
        env.log("<cache>", false);
        Generic_IO.writeObject(handler, f);
        env.log("</cache>", false);
    }

    /**
     * Attempts to load {@link handler} from a {@link SHBE_Files#getEnvDataFile()}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final void loadData() throws IOException, ClassNotFoundException {
        Path f = files.getEnvDataFile();
        env.log("<load>", false);
        handler = (SHBE_Handler) Generic_IO.readObject(f);
        env.log("</load>", false);
    }
}
