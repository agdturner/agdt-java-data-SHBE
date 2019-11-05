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
 * An ID for Claim and a Postcode.
 *
 * @author Andy Turner
 */
public class SHBE_ClaimID_PostcodeID extends SHBE_ClaimID {

    public final SHBE_PostcodeID PostcodeID;

    public SHBE_ClaimID_PostcodeID(SHBE_ClaimID ClaimID,
            SHBE_PostcodeID PostcodeID) {
        super(ClaimID);
        this.PostcodeID = PostcodeID;
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
        if (obj instanceof SHBE_ClaimID_PostcodeID) {
            SHBE_ClaimID_PostcodeID o = (SHBE_ClaimID_PostcodeID) obj;
            if (this.hashCode() == o.hashCode()) {
                if (this.ID == o.ID) {
                    if (PostcodeID.equals(o.PostcodeID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.PostcodeID);
        return hash;
    }
}
