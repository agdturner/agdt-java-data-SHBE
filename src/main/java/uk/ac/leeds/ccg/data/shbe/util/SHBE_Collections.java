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
import java.util.Set;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_PersonID;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Collections extends SHBE_Object {

    public SHBE_Collections(SHBE_Environment e) {
        super(e);
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
