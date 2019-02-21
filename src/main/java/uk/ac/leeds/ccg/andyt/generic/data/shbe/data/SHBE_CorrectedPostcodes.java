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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import uk.ac.leeds.ccg.andyt.data.format.Data_ReadCSV;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Object;

/**
 *
 * @author geoagdt
 */
public class SHBE_CorrectedPostcodes extends SHBE_Object {

    protected final transient ONSPD_Handler Postcode_Handler;

    private HashMap<String, HashSet<String>> UnmappableToMappablePostcodes;
    private HashMap<String, ArrayList<String>> ClaimRefToOriginalPostcodes;
    private HashMap<String, ArrayList<String>> ClaimRefToCorrectedPostcodes;
    private HashSet<String> PostcodesCheckedAsMappable;

    public SHBE_CorrectedPostcodes(SHBE_Environment env) {
        super(env);
        Postcode_Handler = env.oe.getHandler();
        files = env.files;
        strings = env.strings;
    }

    public static void main(String[] args) {
//        new SHBE_CorrectedPostcodes(new SHBE_Environment(0)).run();
    }

    /**
     * Currently this reads in Postcodes2016-10.csv. These are postcodes that
     * have been checked in Academy and either corrected, or checked and found
     * to be mappable.
     */
    public void run() {
        File dir;
        dir = new File(files.getInputLCCDir(), "AcademyPostcodeCorrections");
        File f;
        f = new File(dir, "Postcodes2016-10.csv");
        //Generic_ReadCSV.testRead(f, dir, 6);
        ArrayList<String> lines;
        lines = Data_ReadCSV.read(f, dir, 6);
        Iterator<String> ite;
        ite = lines.iterator();
        String s;
        s = ite.next(); // Skip header
        String[] split;
        String ClaimRef;
        String OriginalPostcode;
        String CorrectedPostcode;
        String OriginalPostcodeF;
        String CorrectedPostcodeF;

        UnmappableToMappablePostcodes = new HashMap<>();
        ClaimRefToOriginalPostcodes = new HashMap<>();
        ClaimRefToCorrectedPostcodes = new HashMap<>();
        PostcodesCheckedAsMappable = new HashSet<>();

        while (ite.hasNext()) {
            s = ite.next();
            split = s.split(",");
            ClaimRef = split[1].trim();
            OriginalPostcode = split[2];
            OriginalPostcodeF = Postcode_Handler.formatPostcode(OriginalPostcode);
            CorrectedPostcode = split[3];
            CorrectedPostcodeF = Postcode_Handler.formatPostcode(CorrectedPostcode);
//            for (int i = 0; i < split.length; i ++ ){
//                System.out.print(split[i].trim() + " ");
//            }
            if (OriginalPostcodeF.equalsIgnoreCase(CorrectedPostcodeF)) {
                //System.out.println(ClaimRef + ", " + OriginalPostcode + ", " + CorrectedPostcode + ", N");
                PostcodesCheckedAsMappable.add(OriginalPostcodeF);
            } else {
                //System.out.println(ClaimRef + ", " + OriginalPostcode + ", " + CorrectedPostcode + ", Y");
                HashSet<String> MappablePostcodes;
                if (UnmappableToMappablePostcodes.containsKey(OriginalPostcodeF)) {
                    MappablePostcodes = UnmappableToMappablePostcodes.get(OriginalPostcodeF);
                } else {
                    MappablePostcodes = new HashSet<>();
                    UnmappableToMappablePostcodes.put(OriginalPostcodeF, MappablePostcodes);
                }
                MappablePostcodes.add(CorrectedPostcodeF);
                ArrayList<String> OriginalPostcodes;
                OriginalPostcodes = new ArrayList<>();
                OriginalPostcodes.add(OriginalPostcodeF);
                getClaimRefToOriginalPostcodes().put(ClaimRef, OriginalPostcodes);
                ArrayList<String> CorrectedPostcodes;
                CorrectedPostcodes = new ArrayList<>();
                CorrectedPostcodes.add(CorrectedPostcodeF);
                getClaimRefToCorrectedPostcodes().put(ClaimRef, CorrectedPostcodes);
            }
        }
        Env.ge.log("UnmappableToMappablePostcode.size() " 
                + UnmappableToMappablePostcodes.size(), logID, true);
        String m;
        m = "UnmappableToMappablePostcode";
        Env.ge.log("<" + m + ">", logID, false);
        Env.ge.log("Unmappable,Mappable", logID, false);
        ite = UnmappableToMappablePostcodes.keySet().iterator();
        while (ite.hasNext()) {
            OriginalPostcodeF = ite.next();
            s = OriginalPostcodeF;
            HashSet<String> CorrectedPostcodes;
            CorrectedPostcodes = getUnmappableToMappablePostcodes().get(
                    OriginalPostcodeF);
            Iterator<String> ite2;
            ite2 = CorrectedPostcodes.iterator();
            while (ite2.hasNext()) {
                CorrectedPostcodeF = ite2.next();
                s += "," + CorrectedPostcodeF;
            }
            Env.ge.log(s, logID, false);
        }
        Env.ge.log("</" + m + ">", logID, false);

        m = "PostcodesCheckedAsMappable";
        Env.ge.log("<" + m + ">", logID, false);
        Env.ge.log("Mappable", logID, false);
        ite = getPostcodesCheckedAsMappable().iterator();
        while (ite.hasNext()) {
            OriginalPostcodeF = ite.next();
            Env.ge.log(OriginalPostcodeF, logID, false);
        }
        Env.ge.log("</" + m + ">", logID, false);

        File dirout;
        dirout = files.getGeneratedLCCDir();
        File fout;
        fout = new File(dirout,
                "DW_CorrectedPostcodes" + strings.sBinaryFileExtension);
        Generic_IO.writeObject(this, fout);
    }

    /**
     * @return the UnmappableToMappablePostcode
     */
    public HashMap<String, HashSet<String>> getUnmappableToMappablePostcodes() {
        return UnmappableToMappablePostcodes;
    }

    /**
     * @return the ClaimRefToOriginalPostcode
     */
    public HashMap<String, ArrayList<String>> getClaimRefToOriginalPostcodes() {
        return ClaimRefToOriginalPostcodes;
    }

    /**
     * @return the ClaimRefToCorrectedPostcode
     */
    public HashMap<String, ArrayList<String>> getClaimRefToCorrectedPostcodes() {
        return ClaimRefToCorrectedPostcodes;
    }

    /**
     * @return the PostcodesCheckedAsMappable
     */
    public HashSet<String> getPostcodesCheckedAsMappable() {
        return PostcodesCheckedAsMappable;
    }

}
