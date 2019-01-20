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
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.data.SHBE_Handler;
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

    public final int DEBUG_Level;
    public final transient Generic_Environment ge;
    public final transient ONSPD_Environment ONSPD_Env;
    public final transient SHBE_Strings Strings;
    public final transient SHBE_Files Files;
//    public transient ONSPD_Handler ONSPD_Handler;
    public transient SHBE_Handler Handler;
    
    /**
     * Data.
     */
//    public ONSPD_Data data;

    public transient static final String EOL = System.getProperty("line.separator");

    /**
     * 
     * @param ge
     * @param DEBUG_Level 
     */
    public SHBE_Environment(Generic_Environment ge, int DEBUG_Level) {
        //Memory_Threshold = 3000000000L;
        this.ge = ge;
            ONSPD_Env = new ONSPD_Environment(ge, 
                    Generic_Environment.DEBUG_Level_FINE);
        this.DEBUG_Level = DEBUG_Level;
        Strings = new SHBE_Strings();
        Files = new SHBE_Files(Strings, ge.getFiles().getDataDir());
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
        return Handler.clearSomeCache();
    }

    public int clearAllData() {
        int r;
        r = Handler.clearAllCache();
        return r;
    }
    
    public void cacheData() {
        File f;
        f = Files.getDataFile();
        System.out.println("<cache>");
        Generic_IO.writeObject(Handler, f);
        System.out.println("</cache>");
    }

    public final void loadData() {
        File f;
        f = Files.getDataFile();
        System.out.println("<load>");
        Handler = (SHBE_Handler) Generic_IO.readObject(f);
        System.out.println("</load>");
    }
    
    /**
     * Writes s to a new line of the output log and also prints it to std.out if
     * {@code this.DEBUG_Level <= DEBUG_Level}.
     *
     * @param DEBUG_Level
     * @param s
     */
    public void logO(int DEBUG_Level, String s) {
        if (this.DEBUG_Level <= DEBUG_Level) {
            ge.logO(s, true);
        }
    }

    

//    private static void log(
//            String message) {
//        log(DW_Log.DW_DefaultLogLevel, message);
//    }
//
//    private static void log(
//            Level level,
//            String message) {
//        Logger.getLogger(DW_Log.DW_DefaultLoggerName).log(level, message);
//    }
    
}
