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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.core;

import java.io.File;
import java.io.Serializable;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.core.ONSPD_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Postcode_Handler;
//import uk.ac.leeds.ccg.andyt.data.postcode.Generic_UKPostcode_Handler;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
//import uk.ac.leeds.ccg.andyt.generic.data.shbe.data.ONSPD_Data;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.io.SHBE_Files;

/**
 *
 * @author geoagdt
 */
public class SHBE_Environment extends SHBE_OutOfMemoryErrorHandler
        implements Serializable {

    public transient Generic_Environment ge;
    public transient SHBE_Strings Strings;
    public transient SHBE_Files Files;
    
    /**
     * For storing an instance of ONSPD_Postcode_Handler for convenience.
     */
    private ONSPD_Postcode_Handler Postcode_Handler;

    /**
     * Data.
     */
//    public ONSPD_Data data;

    public transient static final String EOL = System.getProperty("line.separator");

    public SHBE_Environment() {
        //Memory_Threshold = 3000000000L;
        Strings = new SHBE_Strings();
        Files = new SHBE_Files(Strings, Strings.s_data);
        ge = new Generic_Environment(Files, Strings);
//        File f;
//        f = Files.getEnvDataFile();
//        if (f.exists()) {
//            loadData();
//            data.Files = Files;
//            data.Files.Strings = Strings;
//            data.Strings = Strings;
//        } else {
//            data = new ONSPD_Data(Files, Strings);
//        }
    }

    /**
     * A method to try to ensure there is enough memory to continue.
     *
     * @return
     */
    @Override
    public boolean checkAndMaybeFreeMemory() {
        System.gc();
        while (getTotalFreeMemory() < Memory_Threshold) {
//            int clear = clearAllData();
//            if (clear == 0) {
//                return false;
//            }
            if (!swapDataAny()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean swapDataAny(boolean handleOutOfMemoryError) {
        try {
            boolean result = swapDataAny();
            checkAndMaybeFreeMemory();
            return result;
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                clearMemoryReserve();
                boolean result = swapDataAny(HOOMEF);
                initMemoryReserve();
                return result;
            } else {
                throw e;
            }
        }
    }

    /**
     * Currently this just tries to swap ONSPD data.
     *
     * @return
     */
    @Override
    public boolean swapDataAny() {
        boolean r;
        r = clearSomeData();
        if (r) {
            return r;
        } else {
            System.out.println("No ONSPD data to clear. Do some coding to try "
                    + "to arrange to clear something else if needs be. If the "
                    + "program fails then try providing more memory...");
            return r;
        }
    }

    public boolean clearSomeData() {
//        return data.clearSomeData();
        return false;
    }

    public int clearAllData() {
        int r;
//        r = data.clearAllData();
//        return r;
    return 0;
    }
    
    public void cacheData() {
//        File f;
//        f = Files.getEnvDataFile();
        System.out.println("<cache data>");
//        Generic_IO.writeObject(data, f);
        System.out.println("</cache data>");
    }

    public final void loadData() {
//        File f;
//        f = Files.getEnvDataFile();
        System.out.println("<load data>");
//        data = (ONSPD_Data) Generic_IO.readObject(f);
        System.out.println("<load data>");
    }
    
    /**
     * For returning an instance of ONSPD_Postcode_Handler for convenience.
     *
     * @return
     */
    public ONSPD_Postcode_Handler getPostcode_Handler() {
        if (Postcode_Handler == null) {
            ONSPD_Environment ONSPD_Env;
            ONSPD_Env = new ONSPD_Environment();
            Postcode_Handler = new ONSPD_Postcode_Handler(ONSPD_Env);
        }
        return Postcode_Handler;
    }
}
