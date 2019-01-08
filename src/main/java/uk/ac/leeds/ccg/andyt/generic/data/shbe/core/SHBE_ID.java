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

/**
 * A simple class for identifiers that uses a single long. There can only be as 
 * many unique identifiers as there are long numbers.
 *
 * @author geoagdt
 */
public class SHBE_ID implements Serializable, Comparable<SHBE_ID> {

    protected final long l;

    public SHBE_ID(
            SHBE_ID ID) {
        l = ID.l;
    }

    public SHBE_ID(
            long ID) {
        l = ID;
    }

    /**
     * @return the DW_ID
     */
    public long getID() {
        return l;
    }

    @Override
    public String toString() {
        return "" + l;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SHBE_ID) {
            SHBE_ID o;
            o = (SHBE_ID) obj;
            if (hashCode() == o.hashCode()) {
                return o.l == l;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (int) (l ^ (l >>> 32));
        return hash;
    }

    @Override
    public int compareTo(SHBE_ID t) {
        if (l > t.l) {
            return 1;
        } else if (l < t.l) {
            return -1;
        }
        return 0;
    }

}
