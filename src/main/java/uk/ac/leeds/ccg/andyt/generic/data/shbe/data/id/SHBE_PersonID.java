/*
 * Copyright 2019 Centre for Computational Geography, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.data.id;

import java.util.Objects;

/**
 * An ID for Persons made up of a Date of Birth and a National Insurance Number.
 *
 * @author Andy Turner
 */
public class SHBE_PersonID extends SHBE_DOBID {

    public final SHBE_NINOID NINOID;

    public SHBE_PersonID(SHBE_NINOID NINOID, SHBE_DOBID DOBID) {
        super(DOBID);
        this.NINOID = NINOID;
    }

    public SHBE_DOBID getDOBID(){
        return new SHBE_DOBID(ID);
    }
    
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SHBE_PersonID) {
            SHBE_PersonID o = (SHBE_PersonID) obj;
                if (this.ID == o.ID) {
                    if (NINOID.equals(o.NINOID)) {
                        return true;
                    }
                }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.NINOID);
        return hash;
    }

}
