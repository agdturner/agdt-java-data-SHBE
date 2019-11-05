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

/**
 * An ID for Claim and a TenancyType.
 *
 * @author Andy Turner
 */
public class SHBE_ClaimID_TenancyType extends SHBE_ClaimID {

    public final int TenancyType;

    public SHBE_ClaimID_TenancyType(SHBE_ClaimID ClaimID, int TenancyType) {
        super(ClaimID);
        this.TenancyType = TenancyType;
    }

    public SHBE_ClaimID_TenancyType(SHBE_ClaimID_TenancyType i) {
        super(i.ID);
        TenancyType = i.TenancyType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SHBE_ClaimID_TenancyType) {
            SHBE_ClaimID_TenancyType o;
            o = (SHBE_ClaimID_TenancyType) obj;
            if (ID == o.ID) {
                if (TenancyType == o.TenancyType) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.TenancyType;
        return hash;
    }
}
