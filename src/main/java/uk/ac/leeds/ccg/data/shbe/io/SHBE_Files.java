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
package uk.ac.leeds.ccg.data.shbe.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.data.io.Data_Files;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Strings;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Files extends Data_Files {

    /**
     * @param dir
     * @throws java.io.IOException
     */
    public SHBE_Files(Path dir) throws IOException {
        super(dir);
    }

    private Path inputLCCDir;
    private Path inputSHBEDir;
    private Path generatedLCCDir;
    private Path generatedSHBEDir;

    public Path getInputLCCDir() throws IOException {
        if (inputLCCDir == null) {
            inputLCCDir = Paths.get(getInputDir().toString(),
                    SHBE_Strings.s_LCC);
        }
        return inputLCCDir;
    }

    public Path getInputSHBEDir() throws IOException {
        if (inputSHBEDir == null) {
            inputSHBEDir = Paths.get(getInputLCCDir().toString(),
                    SHBE_Strings.s_SHBE);
        }
        return inputSHBEDir;
    }

    public Path getGeneratedLCCDir() throws IOException {
        if (generatedLCCDir == null) {
            generatedLCCDir = Paths.get(getGeneratedDir().toString(),
                    SHBE_Strings.s_LCC);
            Files.createDirectories(generatedLCCDir);
        }
        return generatedLCCDir;
    }

    public Path getGeneratedSHBEDir() throws IOException {
        if (generatedSHBEDir == null) {
            generatedSHBEDir = Paths.get(getGeneratedLCCDir().toString(),
                    SHBE_Strings.s_SHBE);
        }
        return generatedSHBEDir;
    }
}
