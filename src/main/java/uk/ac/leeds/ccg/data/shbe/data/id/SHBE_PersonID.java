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
package uk.ac.leeds.ccg.data.shbe.data.id;

import java.io.Serializable;
import java.util.Objects;

/**
 * An ID for Person NINOID and DOBID.
 *
 * @author Andy Turner
 */
public class SHBE_PersonID implements Serializable,
        Comparable<SHBE_PersonID> {

    public final SHBE_NINOID NINOID;
    public final SHBE_DOBID DOBID;

    public SHBE_PersonID(SHBE_NINOID NINOID, SHBE_DOBID DOBID) {
        this.NINOID = NINOID;
        this.DOBID = DOBID;
    }

    /**
     * @param obj Object to test for equality with this.
     * @return {@code true} if {@code obj} and {@code this} are equals and
     * {@code false} otherwise.
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
            if (this.hashCode() == o.hashCode()) {
                if (this.NINOID.equals(o.NINOID)) {
                    if (DOBID.equals(o.DOBID)) {
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
        hash = 79 * hash + Objects.hashCode(this.NINOID);
        hash = 79 * hash + Objects.hashCode(this.DOBID);
        return hash;
    }

    @Override
    public int compareTo(SHBE_PersonID i) {
        int r = this.NINOID.compareTo(i.NINOID);
        if (r == 0) {
            return this.DOBID.compareTo(i.DOBID);
        } else {
            return r;
        }
    }
}
