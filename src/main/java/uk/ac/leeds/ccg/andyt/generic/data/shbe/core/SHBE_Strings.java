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
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class SHBE_Strings extends Generic_Strings implements Serializable {

    /*
     * General names.
     */
    public final String S_dat = "dat";
    public final String S_ONSPD = "ONSPD";
    public final String S_PostcodeUnit = "PostcodeUnit";
    public final String S_PostcodeSector = "PostcodeSector";
    public final String S_PostcodeDistrict = "PostcodeDistrict";
    
    public SHBE_Strings() {
        super();
    }
}
