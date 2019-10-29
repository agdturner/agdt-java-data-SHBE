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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.io;

import java.io.File;
import java.io.IOException;
import uk.ac.leeds.ccg.andyt.data.io.Data_Files;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Strings;

/**
 *
 * @author geoagdt
 */
public class SHBE_Files extends Data_Files {

    /**
     * @param dir
     * @throws java.io.IOException
     */
    public SHBE_Files(File dir) throws IOException {
        super(dir);
    }

    private File inputLCCDir;
    private File inputSHBEDir;
    private File generatedLCCDir;
    private File generatedSHBEDir;

    public File getInputLCCDir() {
        if (inputLCCDir == null) {
            inputLCCDir = new File(getInputDir(), SHBE_Strings.s_LCC);
        }
        return inputLCCDir;
    }

    public File getInputSHBEDir() {
        if (inputSHBEDir == null) {
            inputSHBEDir = new File(getInputLCCDir(), SHBE_Strings.s_SHBE);
        }
        return inputSHBEDir;
    }

    public File getGeneratedLCCDir() {
        if (generatedLCCDir == null) {
            generatedLCCDir = new File(getGeneratedDir(),  SHBE_Strings.s_LCC);
            generatedLCCDir.mkdirs();
        }
        return generatedLCCDir;
    }

    public File getGeneratedSHBEDir() {
        if (generatedSHBEDir == null) {
            generatedSHBEDir = new File(getGeneratedLCCDir(), SHBE_Strings.s_SHBE);
        }
        return generatedSHBEDir;
    }
}
