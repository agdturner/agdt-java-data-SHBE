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
import uk.ac.leeds.ccg.andyt.data.Data_RecordID;

/**
 * An ID for Claim, TenancyType and a Postcode.
 *
 * @author Andy Turner
 */
public class SHBE_ClaimID_TenancyType_PostcodeID extends SHBE_ClaimID_TenancyType {

    private final Data_RecordID PostcodeID;

    public SHBE_ClaimID_TenancyType_PostcodeID(
            SHBE_ClaimID_TenancyType ID_TenancyType, Data_RecordID PostcodeID) {
        super(ID_TenancyType);
        this.PostcodeID = PostcodeID;
    }

    /**
     * @return the PostcodeID
     */
    public Data_RecordID getPostcodeID() {
        return PostcodeID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SHBE_ClaimID_TenancyType_PostcodeID) {
            SHBE_ClaimID_TenancyType_PostcodeID o;
            o = (SHBE_ClaimID_TenancyType_PostcodeID) obj;
            if (this.hashCode() == o.hashCode()) {
                if (PostcodeID.equals(o.PostcodeID)) {
                    if (super.ID == o.ID) {
                        if (super.TenancyType == o.TenancyType) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.PostcodeID);
        return hash;
    }

}
