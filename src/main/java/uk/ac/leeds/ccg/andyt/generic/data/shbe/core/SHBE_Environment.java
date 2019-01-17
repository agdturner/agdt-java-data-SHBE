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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    protected transient Generic_Environment ge;

    // For convenience
    protected transient ONSPD_Handler ONSPD_Handler;

    public transient SHBE_Handler Handler;
    public transient SHBE_Strings Strings;
    public transient SHBE_Files Files;

    /**
     * Logging levels.
     */
    public int DEBUG_Level;
    public static final int DEBUG_Level_FINEST = 0;
    public static final int DEBUG_Level_FINE = 1;
    public static final int DEBUG_Level_NORMAL = 2;
    
/**
     * For writing output messages to.
     */
    private PrintWriter PrintWriterOut;

    /**
     * For writing error messages to.
     */
    private PrintWriter PrintWriterErr;

    /**
     * Data.
     */
//    public ONSPD_Data data;

    public transient static final String EOL = System.getProperty("line.separator");

    public SHBE_Environment(File dataDir, int DEBUG_Level) {
        //Memory_Threshold = 3000000000L;
        Strings = new SHBE_Strings();
        Files = new SHBE_Files(Strings, dataDir);
        ge = new Generic_Environment(Files, Strings);
        File outDir = Files.getOutputDataDir();
        this.DEBUG_Level = DEBUG_Level;
        File f;
        f = new File(outDir, "Out.txt");
        try {
            PrintWriterOut = new PrintWriter(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SHBE_Environment.class.getName()).log(Level.SEVERE, null, ex);
        }
        f = new File(outDir, "Err.txt");
        try {
            PrintWriterErr = new PrintWriter(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SHBE_Environment.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * For returning an instance of ONSPD_Handler for convenience.
     *
     * @return
     */
    public ONSPD_Handler getONSPD_Handler() {
        if (ONSPD_Handler == null) {
            ONSPD_Environment ONSPD_Env;
            ONSPD_Env = new ONSPD_Environment(Files.getDataDir());
            ONSPD_Handler = new ONSPD_Handler(ONSPD_Env);
        }
        return ONSPD_Handler;
    }
    
    /**
     * Writes s to a new line of the output log and error log and also prints it
     * to std.out.
     *
     * @param s
     */
    public void logEO(String s) {
        logO(s, false);
        logE(s);
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
    /**
     * Writes s to a new line of the output log and also prints it to std.out.
     *
     * @param s
     * @param println
     */
    public void logO(String s, boolean println) {
        if (PrintWriterOut != null) {
            PrintWriterOut.println(s);
        }
        if (println) {
            System.out.println(s);
        }
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
            PrintWriterOut.println(s);
            System.out.println(s);
        }
    }

    /**
     * Writes s to a new line of the error log and also prints it to std.err.
     *
     * @param s
     */
    public void logE(String s) {
        if (PrintWriterErr != null) {
            PrintWriterErr.println(s);
        }
        System.err.println(s);
    }

    /**
     * Writes {@code e.getStackTrace()} to the error log and also prints it to
     * std.err.
     *
     * @param e
     */
    public void logE(Exception e) {
        StackTraceElement[] st;
        st = e.getStackTrace();
        for (StackTraceElement st1 : st) {
            logE(st1.toString());
        }
    }

    /**
     * Writes e StackTrace to the error log and also prints it to std.err.
     *
     * @param e
     */
    public void logE(Error e) {
        StackTraceElement[] st;
        st = e.getStackTrace();
        for (StackTraceElement st1 : st) {
            logE(st1.toString());
        }
    }
}
