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

import java.io.Serializable;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.io.SHBE_Files;

/**
 * @author Andy Turner
 */
public abstract class SHBE_Object implements Serializable {

    public transient SHBE_Environment Env;
    
    // For convenience.
    public transient SHBE_Strings Strings;
    public transient SHBE_Files Files;

    protected SHBE_Object() {
    }

    public SHBE_Object(SHBE_Environment e) {
        Env = e;
        Strings = e.Strings;
        Files = e.Files;
    }
}
