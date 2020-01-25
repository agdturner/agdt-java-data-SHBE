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
package uk.ac.leeds.ccg.data.shbe.process;

import java.io.IOException;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Main_Process extends SHBE_Object {

    public SHBE_Main_Process(SHBE_Environment env, int logID) {
        super(env, logID);
        files = env.files;
    }

    public static void main(String[] args) throws Exception {
        try {
            SHBE_Environment env = new SHBE_Environment(
                    new Data_Environment(new Generic_Environment(
                    new Generic_Defaults())));
            SHBE_Main_Process p = new SHBE_Main_Process(env, 0);
            // Main switches
            p.run();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public void run() {
    }

}
