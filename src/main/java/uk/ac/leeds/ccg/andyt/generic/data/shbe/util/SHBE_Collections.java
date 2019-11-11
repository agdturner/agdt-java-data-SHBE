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
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.data.id.SHBE_PersonID;
import uk.ac.leeds.ccg.andyt.generic.util.Generic_Collections;

/**
 *
 * @author geoagdt
 */
public class SHBE_Collections extends Generic_Collections {

    public final SHBE_Environment env;

    public SHBE_Collections(SHBE_Environment e) {
        env = e;
    }

    /**
     * Returns the count of all values in the map (the sum of all the number of
     * things in the Sets), null values are not allowed.
     *
     * @param m
     * @return
     */
    public static <K, T> int getCountHashMapKHashSetT(HashMap<K, HashSet<T>> m) {
        int r = 0;
        Collection<HashSet<T>> c;
        c = m.values();
        Iterator<HashSet<T>> ite;
        ite = c.iterator();
        while (ite.hasNext()) {
            r += ite.next().size();
        }
        return r;
    }

    public static <K, T> K getKeyOfSetValue(HashMap<K, HashSet<T>> map, T t) {
        K r;
        Set<Entry<K, HashSet<T>>> mapEntrySet = map.entrySet();
        Iterator<Entry<K, HashSet<T>>> ite = mapEntrySet.iterator();
        while (ite.hasNext()) {
            Entry<K, HashSet<T>> entry = ite.next();
            r = entry.getKey();
            if (entry.getValue().contains(t)) {
                return r;
            }
        }
        return null;
    }

    public <T> HashMap<T, String> getHashMapTString(File f) {
        HashMap<T, String> r;
        if (f.exists()) {
            r = (HashMap<T, String>) env.env.io.readObject(f);
        } else {
            r = new HashMap<>();
        }
        return r;
    }

    public HashSet<SHBE_PersonID> getPersonIDs(File f) {
        HashSet<SHBE_PersonID> r;
        if (f.exists()) {
            r = (HashSet<SHBE_PersonID>) env.env.io.readObject(f);
        } else {
            r = new HashSet<>();
        }
        return r;
    }

    public <T> HashMap<SHBE_PersonID, HashSet<T>> getHashMap_PersonID_HashSetT(
            File f) {
        HashMap<SHBE_PersonID, HashSet<T>> r;
        if (f.exists()) {
            r = (HashMap<SHBE_PersonID, HashSet<T>>) env.env.io.readObject(f);
        } else {
            r = new HashMap<>();
        }
        return r;
    }
}
