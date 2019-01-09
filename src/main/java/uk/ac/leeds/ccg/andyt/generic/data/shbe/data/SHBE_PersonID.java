/*
 * Copyright (C) 2015 geoagdt.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package uk.ac.leeds.ccg.andyt.generic.data.shbe.data;

import java.io.Serializable;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_ID;

/**
 *
 * @author geoagdt
 */
public class SHBE_PersonID implements Serializable {

    private SHBE_ID NINO_ID;
    private SHBE_ID DOB_ID;

    public SHBE_PersonID() {
    }

    public SHBE_PersonID(
            SHBE_ID tNINO_ID,
            SHBE_ID tDOB_ID
    ) {
        this.NINO_ID = tNINO_ID;
        this.DOB_ID = tDOB_ID;
    }

    /**
     * @return the tNINO_ID
     */
    public SHBE_ID getNINO_ID() {
        return NINO_ID;
    }

    /**
     * @return the tDOB_ID
     */
    public SHBE_ID getDOB_ID() {
        return DOB_ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof SHBE_PersonID) {
            SHBE_PersonID o;
            o = (SHBE_PersonID) obj;
            if (this.hashCode() == o.hashCode()) {
                if (NINO_ID.equals(o.getNINO_ID())) {
                    if (DOB_ID.equals(o.getDOB_ID())) {
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
        hash = 67 * hash + (this.NINO_ID != null ? this.NINO_ID.hashCode() : 0);
        hash = 67 * hash + (this.DOB_ID != null ? this.DOB_ID.hashCode() : 0);
        return hash;
    }

}
