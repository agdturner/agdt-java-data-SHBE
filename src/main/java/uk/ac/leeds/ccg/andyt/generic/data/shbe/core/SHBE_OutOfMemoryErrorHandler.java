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
import uk.ac.leeds.ccg.andyt.generic.memory.Generic_OutOfMemoryErrorHandler;
import uk.ac.leeds.ccg.andyt.generic.memory.Generic_OutOfMemoryErrorHandlerInterface;

/**
 *
 * @author Andy Turner
 */
public abstract class SHBE_OutOfMemoryErrorHandler
        extends Generic_OutOfMemoryErrorHandler
        implements Serializable, Generic_OutOfMemoryErrorHandlerInterface {

    //static final long serialVersionUID = 1L;
    //public static long Memory_Threshold = 3000000000L;
    public static long Memory_Threshold = 2000000000L;

    @Override
    public boolean swapDataAny() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean swapDataAny(boolean handleOutOfMemoryError) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkAndMaybeFreeMemory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
