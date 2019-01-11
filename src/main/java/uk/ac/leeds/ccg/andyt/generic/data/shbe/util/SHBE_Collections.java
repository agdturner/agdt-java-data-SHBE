/*
 * Copyright (C) 2016 geoagdt.
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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_ID;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.data.SHBE_PersonID;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.util.Generic_Collections;

/**
 *
 * @author geoagdt
 */
public class SHBE_Collections extends Generic_Collections {
    
    /**
     * Returns the count of all values in the map (the sum of all the number of
     * things in the Sets), null values are not allowed.
     *
     * @param m
     * @return
     */
    public static int getCountHashMap_SHBE_PersonID__HashSet_SHBE_ID(HashMap<SHBE_PersonID, HashSet<SHBE_ID>> m) {
        int result = 0;
        Collection<HashSet<SHBE_ID>> c;
        c = m.values();
        Iterator<HashSet<SHBE_ID>> ite;
        ite = c.iterator();
        while (ite.hasNext()) {
            result += ite.next().size();
        }
        return result;
    }

    /**
     * Returns the count of all values in the map (the sum of all the number of
     * things in the Sets), null values are not allowed.
     *
     * @param m
     * @return
     */
    public static int getCountHashMap_SHBE_ID__HashSet_SHBE_PersonID(HashMap<SHBE_ID, HashSet<SHBE_PersonID>> m) {
        int result = 0;
        Collection<HashSet<SHBE_PersonID>> c;
        c = m.values();
        Iterator<HashSet<SHBE_PersonID>> ite;
        ite = c.iterator();
        while (ite.hasNext()) {
            result += ite.next().size();
        }
        return result;
    }
    
    public static SHBE_ID getKeyOfSetValue(
            HashMap<SHBE_ID, HashSet<SHBE_PersonID>> map,
            SHBE_PersonID SHBE_PersonID) {
        SHBE_ID result;
        Set<Entry<SHBE_ID, HashSet<SHBE_PersonID>>> mapEntrySet;
        mapEntrySet = map.entrySet();
        Iterator<Entry<SHBE_ID, HashSet<SHBE_PersonID>>> ite;
        ite = mapEntrySet.iterator();
        Entry<SHBE_ID, HashSet<SHBE_PersonID>> entry;
        HashSet<SHBE_PersonID> set;
        while (ite.hasNext()) {
            entry = ite.next();
            result = entry.getKey();
            if (entry.getValue().contains(SHBE_PersonID)) {
                return result;
            }
        }
        return null;
    }

    public static HashMap<SHBE_ID, String> getHashMap_SHBE_ID__String(File f) {
        HashMap<SHBE_ID, String> r;
        if (f.exists()) {
            r = (HashMap<SHBE_ID, String>) Generic_IO.readObject(f);
        } else {
            r = new HashMap<>();
        }
        return r;
    }

    public static HashSet<SHBE_ID> getHashSet_SHBE_ID(File f) {
        HashSet<SHBE_ID> result;
        if (f.exists()) {
            result = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
        } else {
            result = new HashSet<>();
        }
        return result;
    }
    
    public static HashSet<SHBE_PersonID> getHashSet_SHBE_PersonID(File f) {
        HashSet<SHBE_PersonID> result;
        if (f.exists()) {
            result = (HashSet<SHBE_PersonID>) Generic_IO.readObject(f);
        } else {
            result = new HashSet<>();
        }
        return result;
    }
    
    public static HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getHashMap_SHBE_PersonID__HashSet_SHBE_ID(
            File f) {
        HashMap<SHBE_PersonID, HashSet<SHBE_ID>> result;
        if (f.exists()) {
            result = (HashMap<SHBE_PersonID, HashSet<SHBE_ID>>) Generic_IO.readObject(f);
        } else {
            result = new HashMap<>();
        }
        return result;
    }
}
