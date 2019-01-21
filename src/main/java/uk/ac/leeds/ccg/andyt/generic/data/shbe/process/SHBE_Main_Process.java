/*
 * Copyright 2018 geoagdt.
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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.process;

import java.io.File;
import java.util.logging.Level;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Object;
/**
 *
 * @author geoagdt
 */
public class SHBE_Main_Process extends SHBE_Object {

    public SHBE_Main_Process(SHBE_Environment env) {
        super(env);
//        data = env.data;
        Strings = env.Strings;
        Files = env.Files;
    }

    public static void main(String[] args) {
        File dataDir = new File(System.getProperty("user.dir"), "data");
        Generic_Environment ge = new Generic_Environment(dataDir, Level.FINE, 100);
        SHBE_Environment env;
        env = new SHBE_Environment(ge);
        SHBE_Main_Process p;
        p = new SHBE_Main_Process(env);
        // Main switches
        p.run();
    }

    public void run() {
    }

}
