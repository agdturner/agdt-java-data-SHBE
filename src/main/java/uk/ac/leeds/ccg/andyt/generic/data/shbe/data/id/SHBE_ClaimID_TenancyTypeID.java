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

import java.io.Serializable;
import java.util.Objects;

/**
 * An ID for Claim and a Postcode.
 *
 * @author Andy Turner
 */
public class SHBE_ClaimID_TenancyTypeID implements Serializable, 
        Comparable<SHBE_ClaimID_TenancyTypeID> {

    public final SHBE_ClaimID ClaimID;
    public final SHBE_TenancyTypeID TenancyTypeID;

    public SHBE_ClaimID_TenancyTypeID(SHBE_ClaimID ClaimID,
            SHBE_TenancyTypeID TenancyTypeID) {
        this.ClaimID = ClaimID;
        this.TenancyTypeID = TenancyTypeID;
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
        if (obj instanceof SHBE_ClaimID_TenancyTypeID) {
            SHBE_ClaimID_TenancyTypeID o = (SHBE_ClaimID_TenancyTypeID) obj;
            if (this.hashCode() == o.hashCode()) {
                if (this.ClaimID.equals(o.ClaimID)) {
                    if (TenancyTypeID.equals(o.TenancyTypeID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.ClaimID);
        hash = 79 * hash + Objects.hashCode(this.TenancyTypeID);
        return hash;
    }
    
    @Override
    public int compareTo(SHBE_ClaimID_TenancyTypeID i) {
        int r = this.ClaimID.compareTo(i.ClaimID);
        if (r == 0) {
            return this.TenancyTypeID.compareTo(i.TenancyTypeID);
        } else {
            return r;
        }
    }
}
