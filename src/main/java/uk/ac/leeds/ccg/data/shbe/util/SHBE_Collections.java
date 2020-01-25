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
package uk.ac.leeds.ccg.data.shbe.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_PersonID;
import uk.ac.leeds.ccg.generic.io.Generic_IO;
import uk.ac.leeds.ccg.generic.util.Generic_Collections;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Collections extends Generic_Collections {

    public final SHBE_Environment env;

    public SHBE_Collections(SHBE_Environment e) {
        env = e;
    }

    /**
     * Calculates and returns the sum of the sizes of all the sets in {@code m}.
     * 
     * @Todo Return a BigInteger and move to Generic_Collections.
     * 
     * @param <K> Keys
     * @param <T> Types
     * @param m Map
     * @return The sum of the sizes of all the sets in {@code m}.
     */
    public static <K, T> int getCount(Map<K, Set<T>> m) {
        int r = 0;
        Iterator<Set<T>> ite = m.values().iterator();
        while (ite.hasNext()) {
            r += ite.next().size();
        }
        return r;
    }

    /**
     * @param <K> The key type.
     * @param <T> The value type.
     * @param map The map.
     * @param t The value to find the key for.
     * @return The first key found.
     */
    public static <K, T> K getKey(Map<K, Set<T>> map, T t) {
        Set<Entry<K, Set<T>>> mapEntrySet = map.entrySet();
        Iterator<Entry<K, Set<T>>> ite = mapEntrySet.iterator();
        while (ite.hasNext()) {
            Entry<K, Set<T>> entry = ite.next();
            K k = entry.getKey();
            if (entry.getValue().contains(t)) {
                return k;
            }
        }
        return null;
    }

    public <T> HashMap<T, String> getHashMapTString(Path f) throws IOException, 
            ClassNotFoundException {
        HashMap<T, String> r;
        if (Files.exists(f)) {
            r = (HashMap<T, String>) Generic_IO.readObject(f);
        } else {
            r = new HashMap<>();
        }
        return r;
    }

    public Set<SHBE_PersonID> getPersonIDs(Path f) throws IOException, 
            ClassNotFoundException {
        Set<SHBE_PersonID> r;
        if (Files.exists(f)) {
            r = (Set<SHBE_PersonID>) Generic_IO.readObject(f);
        } else {
            r = new HashSet<>();
        }
        return r;
    }
}
