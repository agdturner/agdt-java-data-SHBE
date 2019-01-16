/*
 * Copyright (C) 2014 geoagdt.
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.core.ONSPD_ID;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Point;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
//import uk.ac.leeds.ccg.andyt.projects.digitalwelfare.data.SHBE_CorrectedPostcodes;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Postcode_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.util.ONSPD_YM3;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_ID;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.io.SHBE_Files;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.util.SHBE_Collections;
//import uk.ac.leeds.ccg.andyt.projects.digitalwelfare.util.SHBE_Collections;

/**
 *
 * @author geoagdt
 */
public class SHBE_Data extends SHBE_Object {

    /**
     * For convenience
     */
    transient ONSPD_Postcode_Handler Postcode_Handler;
    transient SHBE_Files Files;
    transient SHBE_Strings Strings;

    /**
     * A reference to all the Data for this Payment Type. The keys are YM3 and
     * the values are the respective collection.
     */
    protected HashMap<ONSPD_YM3, SHBE_Records> Data;

    /**
     * File for storing Data
     */
    private File DataFile;

    /**
     * ClaimRef to ClaimID Lookup.
     */
    private HashMap<String, SHBE_ID> ClaimRefToClaimIDLookup;

    /**
     * ClaimID to ClaimRef Lookup.
     */
    private HashMap<SHBE_ID, String> ClaimIDToClaimRefLookup;

    private SHBE_CorrectedPostcodes CorrectedPostcodes;

    /**
     * National Insurance Number to NINO SHBE_ID Lookup.
     */
    private HashMap<String, SHBE_ID> NINOToNINOIDLookup;

    /**
     * NINO SHBE_ID to National Insurance Number Lookup.
     */
    private HashMap<SHBE_ID, String> NINOIDToNINOLookup;

    /**
     * Date Of Birth to DoB SHBE_ID Lookup.
     */
    private HashMap<String, SHBE_ID> DOBToDOBIDLookup;

    /**
     * DoB SHBE_ID to Date Of Birth Lookup.
     */
    private HashMap<SHBE_ID, String> DOBIDToDOBLookup;

    /**
     * SHBE_PersonID of Claimants
     */
    HashSet<SHBE_PersonID> ClaimantPersonIDs;

    /**
     * SHBE_PersonID of Partners
     */
    HashSet<SHBE_PersonID> PartnerPersonIDs;

    /**
     * SHBE_PersonID of Non-Dependents
     */
    HashSet<SHBE_PersonID> NonDependentPersonIDs;

    /**
     * SHBE_PersonID to ClaimIDs Lookup
     */
    HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PersonIDToClaimIDsLookup;

    /**
     * Postcode to Postcode SHBE_ID Lookup.
     */
    private HashMap<String, ONSPD_ID> PostcodeToPostcodeIDLookup;

    /**
     * Postcode SHBE_ID to Postcode Lookup.
     */
    private HashMap<ONSPD_ID, String> PostcodeIDToPostcodeLookup;

    /**
     * Postcode SHBE_ID to ONSPD_Point Lookups. There is a different one for
     * each ONSPD File. The keys are Nearest YM3s for the respective ONSPD File.
     */
    private HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> PostcodeIDToPointLookups;

    /**
     * ClaimRefToClaimIDLookup File.
     */
    private File ClaimRefToClaimIDLookupFile;

    /**
     * ClaimIDToClaimRefLookupFile File.
     */
    private File ClaimIDToClaimRefLookupFile;

    /**
     * NINOIDToNINOLookup File.
     */
    private File NINOIDToNINOLookupFile;

    /**
     * IDToDOBLookup File.
     */
    private File DOBIDToDOBLookupFile;

    /**
     * ClaimantPersonIDs File.
     */
    private File ClaimantPersonIDsFile;

    /**
     * PartnerPersonIDs File.
     */
    private File PartnerPersonIDsFile;

    /**
     * NonDependentPersonIDs File.
     */
    private File NonDependentPersonIDsFile;

    /**
     * PersonIDToClaimIDsLookup File.
     */
    private File PersonIDToClaimIDsLookupFile;

    /**
     * CorrectedPostcodes File.
     */
    private File CorrectedPostcodesFile;

    /**
     * NINOToIDLookup File.
     */
    private File NINOToNINOIDLookupFile;

    /**
     * DOBToSHBE_IDLookup File.
     */
    private File DOBToDOBIDLookupFile;

    /**
     * PostcodeToPostcodeIDLookup File.
     */
    private File PostcodeToPostcodeIDLookupFile;

    /**
     * PostcodeIDToPostcodeLookup File.
     */
    private File PostcodeIDToPostcodeLookupFile;

    /**
     * PostcodeIDToPointLookup File.
     */
    private File PostcodeIDToPointLookupsFile;

    public SHBE_Data() {
    }

    public SHBE_Data(SHBE_Environment env) {
        super(env);
        Postcode_Handler = env.getPostcode_Handler();
        Files = env.Files;
        Strings = env.Strings;
    }

    /**
     * {@code
     * if (Data == null) {
     * if (f.exists()) {
     * Data = (HashMap<String, SHBE_Records>) Generic_IO.readObject(f);
     * } else {
     * Data = new HashMap<String, SHBE_Records>(); } } return Data; }
     *
     * @param f
     * @return
     */
    public HashMap<ONSPD_YM3, SHBE_Records> getData(File f) {
        if (Data == null) {
            if (f.exists()) {
                Data = (HashMap<ONSPD_YM3, SHBE_Records>) Generic_IO.readObject(f);
            } else {
                Data = new HashMap<>();
            }
        }
        return Data;
    }

    /**
     * {@code
     * DataFile = getDataFile();
     * return getData(DataFile);
     * }
     *
     * @return
     */
    public HashMap<ONSPD_YM3, SHBE_Records> getData() {
        DataFile = getDataFile();
        return getData(DataFile);
    }

    /**
     * If DataFile is null, initialise it.
     *
     * @return DataFile
     */
    public final File getDataFile() {
        if (DataFile == null) {
            String filename = "Data_HashMap_String__SHBE_Records"
                    + Strings.sBinaryFileExtension;
            DataFile = new File(Files.getGeneratedSHBEDir(), filename);
        }
        return DataFile;
    }

    /**
     * If ClaimRefToClaimIDLookup is null initialise it.
     *
     * @param f
     * @return ClaimRefToClaimIDLookup
     */
    public HashMap<String, SHBE_ID> getClaimRefToClaimIDLookup(File f) {
        if (ClaimRefToClaimIDLookup == null) {
            ClaimRefToClaimIDLookup = getStringToIDLookup(f);
        }
        return ClaimRefToClaimIDLookup;
    }

    /**
     * {@code HashMap<String, SHBE_ID> result;
     * if (f.exists()) {
     * result = (HashMap<String, SHBE_ID>) Generic_IO.readObject(f);
     * } else {
     * result = new HashMap<String, SHBE_ID>(); } return result;}
     *
     * @param f
     * @return
     */
    public HashMap<String, SHBE_ID> getStringToIDLookup(            File f) {
        HashMap<String, SHBE_ID> r;
        if (f.exists()) {
            r = (HashMap<String, SHBE_ID>) Generic_IO.readObject(f);
        } else {
            r = new HashMap<>();
        }
        return r;
    }

    /**
     * If ClaimIDToClaimRefLookup is null initialise it.
     *
     * @param f
     * @return ClaimIDToClaimRefLookup
     */
    public HashMap<SHBE_ID, String> getClaimIDToClaimRefLookup(File f) {
        if (ClaimIDToClaimRefLookup == null) {
            ClaimIDToClaimRefLookup = SHBE_Collections.getHashMap_SHBE_ID__String(f);
        }
        return ClaimIDToClaimRefLookup;
    }

    /**
     * {@code ClaimRefToClaimIDLookupFile = getClaimRefToClaimIDLookupFile();
     * return getClaimRefToClaimIDLookup(ClaimRefToClaimIDLookupFile);}
     *
     * @return
     */
    public HashMap<String, SHBE_ID> getClaimRefToClaimIDLookup() {
        ClaimRefToClaimIDLookupFile = getClaimRefToClaimIDLookupFile();
        return getClaimRefToClaimIDLookup(ClaimRefToClaimIDLookupFile);
    }

    /**
     * {@code ClaimRefToClaimIDLookupFile = getClaimRefToClaimIDLookupFile();
     * return getClaimRefToClaimIDLookup(ClaimRefToClaimIDLookupFile);}
     *
     * @return
     */
    public HashMap<SHBE_ID, String> getClaimIDToClaimRefLookup() {
        ClaimIDToClaimRefLookupFile = getClaimIDToClaimRefLookupFile();
        return getClaimIDToClaimRefLookup(ClaimIDToClaimRefLookupFile);
    }

    public final SHBE_CorrectedPostcodes getCorrectedPostcodes(
            File f) {
        if (CorrectedPostcodes == null) {
            if (f.exists()) {
                CorrectedPostcodes = (SHBE_CorrectedPostcodes) Generic_IO.readObject(f);
            } else {
                new SHBE_CorrectedPostcodes(Env).run();
                return getCorrectedPostcodes(f);
            }
        }
        return CorrectedPostcodes;
    }

    public SHBE_CorrectedPostcodes getCorrectedPostcodes() {
        CorrectedPostcodesFile = getCorrectedPostcodesFile();
        return getCorrectedPostcodes(CorrectedPostcodesFile);
    }

    /**
     * {@code if (NINOToSHBE_IDLookup == null) {
     * NINOToSHBE_IDLookup = getStringToIDLookup(f);
     * }
     * return NINOToSHBE_IDLookup;}
     *
     * @param f
     * @return NINOToSHBE_IDLookup
     */
    public final HashMap<String, SHBE_ID> getNINOToNINOIDLookup(
            File f) {
        if (NINOToNINOIDLookup == null) {
            NINOToNINOIDLookup = getStringToIDLookup(f);
        }
        return NINOToNINOIDLookup;
    }

    /**
     * {@code NINOToSHBE_IDLookupFile = getNINOToIDLookupFile();
     * return getNINOToNINOIDLookup(NINOToSHBE_IDLookupFile);}
     *
     * @return
     */
    public HashMap<String, SHBE_ID> getNINOToNINOIDLookup() {
        NINOToNINOIDLookupFile = getNINOToNINOIDLookupFile();
        return getNINOToNINOIDLookup(NINOToNINOIDLookupFile);
    }

    /**
     * {@code if (DOBToDOBIDLookup == null) {
     * DOBToDOBIDLookup = getStringToIDLookup(f);
     * }
     * return DOBToDOBIDLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<String, SHBE_ID> getDOBToDOBIDLookup(
            File f) {
        if (DOBToDOBIDLookup == null) {
            DOBToDOBIDLookup = getStringToIDLookup(f);
        }
        return DOBToDOBIDLookup;
    }

    /**
     * {@code DOBToDOBIDLookupFile = getDOBToDOBIDLookupFile();
     * return getDOBToDOBIDLookup(DOBToDOBIDLookupFile);}
     *
     * @return
     */
    public HashMap<String, SHBE_ID> getDOBToDOBIDLookup() {
        DOBToDOBIDLookupFile = getDOBToDOBIDLookupFile();
        return getDOBToDOBIDLookup(DOBToDOBIDLookupFile);
    }

    /**
     * {@code if (NINOIDToNINOLookup == null) {
     * NINOIDToNINOLookup = getHashMap_SHBE_ID__String(f);
     * }
     * return NINOIDToNINOLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<SHBE_ID, String> getNINOIDToNINOLookup(
            File f) {
        if (NINOIDToNINOLookup == null) {
            NINOIDToNINOLookup = SHBE_Collections.getHashMap_SHBE_ID__String(f);
        }
        return NINOIDToNINOLookup;
    }

    /**
     * {@code NINOIDToNINOLookupFile = getNINOIDToNINOLookupFile();
     * return getNINOIDToNINOLookup(NINOIDToNINOLookupFile);}
     *
     * @return
     */
    public HashMap<SHBE_ID, String> getNINOIDToNINOLookup() {
        NINOIDToNINOLookupFile = getNINOIDToNINOLookupFile();
        return getNINOIDToNINOLookup(NINOIDToNINOLookupFile);
    }

    /**
     * {@code if (DOBIDToDOBLookup == null) {
     * DOBIDToDOBLookup = getHashMap_SHBE_ID__String(f);
     * }
     * return DOBIDToDOBLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<SHBE_ID, String> getDOBIDToDOBLookup(
            File f) {
        if (DOBIDToDOBLookup == null) {
            DOBIDToDOBLookup = SHBE_Collections.getHashMap_SHBE_ID__String(f);
        }
        return DOBIDToDOBLookup;
    }

    /**
     * {@code DOBIDToDOBLookupFile = getDOBIDToDOBLookupFile();
     * return getDOBIDToDOBLookup(DOBIDToDOBLookupFile);}
     *
     * @return
     */
    public HashMap<SHBE_ID, String> getDOBIDToDOBLookup() {
        DOBIDToDOBLookupFile = getDOBIDToDOBLookupFile();
        return getDOBIDToDOBLookup(DOBIDToDOBLookupFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<SHBE_PersonID> getClaimantPersonIDs(
            File f) {
        if (ClaimantPersonIDs == null) {
            ClaimantPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return ClaimantPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<SHBE_PersonID> getClaimantPersonIDs() {
        ClaimantPersonIDsFile = getClaimantPersonIDsFile();
        return getClaimantPersonIDs(ClaimantPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<SHBE_PersonID> getPartnerPersonIDs(
            File f) {
        if (PartnerPersonIDs == null) {
            PartnerPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return PartnerPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<SHBE_PersonID> getPartnerPersonIDs() {
        PartnerPersonIDsFile = getPartnerPersonIDsFile();
        return getPartnerPersonIDs(PartnerPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<SHBE_PersonID> getNonDependentPersonIDs(
            File f) {
        if (NonDependentPersonIDs == null) {
            NonDependentPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return NonDependentPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<SHBE_PersonID> getNonDependentPersonIDs() {
        NonDependentPersonIDsFile = getNonDependentPersonIDsFile();
        return getNonDependentPersonIDs(NonDependentPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getPersonIDToClaimIDsLookup(
            File f) {
        if (PersonIDToClaimIDsLookup == null) {
            PersonIDToClaimIDsLookup = SHBE_Collections.getHashMap_SHBE_PersonID__HashSet_SHBE_ID(f);
        }
        return PersonIDToClaimIDsLookup;
    }

    /**
     * All SHBE_PersonID to ClaimIDs Lookup
     *
     * @return
     */
    public HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getPersonIDToClaimIDLookup() {
        PersonIDToClaimIDsLookupFile = getPersonIDToClaimIDLookupFile();
        return getPersonIDToClaimIDsLookup(PersonIDToClaimIDsLookupFile);
    }

    /**
     * All SHBE_PersonID to ClaimIDs Lookup
     */
    HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PersonIDToClaimIDLookup;

    /**
     * {@code if (PostcodeToPostcodeIDLookup == null) {
     * PostcodeToPostcodeIDLookup = getStringToIDLookup(f);
     * }
     * return PostcodeToPostcodeIDLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<String, ONSPD_ID> getPostcodeToPostcodeIDLookup(
            File f) {
        if (PostcodeToPostcodeIDLookup == null) {
            if (f.exists()) {
                PostcodeToPostcodeIDLookup = (HashMap<String, ONSPD_ID>) Generic_IO.readObject(f);
            } else {
                PostcodeToPostcodeIDLookup = new HashMap<>();
            }
        }
        return PostcodeToPostcodeIDLookup;
    }

    /**
     * {@code PostcodeToPostcodeDWLookupFile = getPostcodeToPostcodeIDLookupFile();
     * return getPostcodeToPostcodeIDLookup(PostcodeToPostcodeDWLookupFile);}
     *
     * @return
     */
    public HashMap<String, ONSPD_ID> getPostcodeToPostcodeIDLookup() {
        PostcodeToPostcodeIDLookupFile = getPostcodeToPostcodeIDLookupFile();
        return getPostcodeToPostcodeIDLookup(PostcodeToPostcodeIDLookupFile);
    }

    /**
     * {@code if (PostcodeIDToPostcodeLookup == null) {
     * PostcodeIDToPostcodeLookup = getHashMap_SHBE_ID__String(f);
     * }
     * return PostcodeIDToPostcodeLookup;}
     *
     * @param f
     * @return
     */
    public final HashMap<ONSPD_ID, String> getPostcodeIDToPostcodeLookup(
            File f) {
        if (PostcodeIDToPostcodeLookup == null) {
            if (f.exists()) {
                PostcodeIDToPostcodeLookup = (HashMap<ONSPD_ID, String>) Generic_IO.readObject(f);
            } else {
                PostcodeIDToPostcodeLookup = new HashMap<>();
            }
        }
        return PostcodeIDToPostcodeLookup;
    }

    /**
     * {@code if (PostcodeIDToPointLookups == null) {
     * if (f.exists()) {
     * PostcodeIDToPointLookups = (HashMap<String, HashMap<SHBE_ID, ONSPD_Point>>) Generic_IO.readObject(f);
     * } else {
     * PostcodeIDToPointLookups = new HashMap<String, HashMap<SHBE_ID, ONSPD_Point>>();
     * } } return PostcodeIDToPointLookups;}
     *
     * @param f
     * @return
     */
    public final HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> getPostcodeIDToPointLookups(
            File f) {
        if (PostcodeIDToPointLookups == null) {
            if (f.exists()) {
                PostcodeIDToPointLookups = (HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>>) Generic_IO.readObject(f);
            } else {
                PostcodeIDToPointLookups = new HashMap<>();
            }
        }
        return PostcodeIDToPointLookups;
    }

    /**
     * {@code PostcodeIDToPostcodeLookupFile = getPostcodeIDToPostcodeLookupFile();
     * return getPostcodeIDToPostcodeLookup(PostcodeIDToPostcodeLookupFile);}
     *
     * @return
     */
    public HashMap<ONSPD_ID, String> getPostcodeIDToPostcodeLookup() {
        PostcodeIDToPostcodeLookupFile = getPostcodeIDToPostcodeLookupFile();
        return getPostcodeIDToPostcodeLookup(PostcodeIDToPostcodeLookupFile);
    }

    /**
     * {@code PostcodeIDToAGDT_PointLookupFile = getPostcodeIDToAGDT_PointLookupsFile();
     * return getPostcodeIDToPointLookups(PostcodeIDToAGDT_PointLookupFile);}
     *
     * @return
     */
    public HashMap<ONSPD_YM3, HashMap<ONSPD_ID, ONSPD_Point>> getPostcodeIDToPointLookups() {
        PostcodeIDToPointLookupsFile = getPostcodeIDToPointLookupsFile();
        return getPostcodeIDToPointLookups(PostcodeIDToPointLookupsFile);
    }

    /**
     * {@code PostcodeIDToAGDT_PointLookupFile = getPostcodeIDToAGDT_PointLookupsFile();
     * return getPostcodeIDToPointLookups(PostcodeIDToAGDT_PointLookupFile);}
     *
     * @param YM3
     * @return
     */
    public HashMap<ONSPD_ID, ONSPD_Point> getPostcodeIDToPointLookup(ONSPD_YM3 YM3) {
        ONSPD_YM3 NearestYM3ForONSPDLookup;
        NearestYM3ForONSPDLookup = Postcode_Handler.getNearestYM3ForONSPDLookup(YM3);
        HashMap<ONSPD_ID, ONSPD_Point> PostcodeIDToPointLookup;
        PostcodeIDToPointLookups = getPostcodeIDToPointLookups();
        if (PostcodeIDToPointLookups.containsKey(NearestYM3ForONSPDLookup)) {
            PostcodeIDToPointLookup = PostcodeIDToPointLookups.get(NearestYM3ForONSPDLookup);
        } else {
            PostcodeIDToPointLookup = new HashMap<>();
            PostcodeIDToPointLookups.put(NearestYM3ForONSPDLookup, PostcodeIDToPointLookup);
        }
        return PostcodeIDToPointLookup;
    }

    /**
     * {@code if (ClaimRefToClaimIDLookupFile == null) {
     * String filename = "ClaimRefToClaimID_HashMap_String__SHBE_ID"
     * + Strings.sBinaryFileExtension;
     * PostcodeToPostcodeIDLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return ClaimRefToClaimIDLookupFile;}
     *
     * @return
     */
    public final File getClaimRefToClaimIDLookupFile() {
        if (ClaimRefToClaimIDLookupFile == null) {
            String filename = "ClaimRefToClaimID_HashMap_String__SHBE_ID"
                    + Strings.sBinaryFileExtension;
            ClaimRefToClaimIDLookupFile = new File(
                    Files.getGeneratedSHBEDir(), filename);
        }
        return ClaimRefToClaimIDLookupFile;
    }

    /**
     * {@code if (ClaimIDToClaimRefLookupFile == null) {
     * String filename = "ClaimIDToClaimRef_HashMap_SHBE_ID__String"
     * + Strings.sBinaryFileExtension;
     * ClaimIDToClaimRefLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return ClaimIDToClaimRefLookupFile;}
     *
     * @return
     */
    public final File getClaimIDToClaimRefLookupFile() {
        if (ClaimIDToClaimRefLookupFile == null) {
            String filename = "ClaimIDToClaimRef_HashMap_SHBE_ID__String"
                    + Strings.sBinaryFileExtension;
            ClaimIDToClaimRefLookupFile = new File(
                    Files.getGeneratedSHBEDir(), filename);
        }
        return ClaimIDToClaimRefLookupFile;
    }

    /**
     * {@code if (PostcodeToPostcodeDWLookupFile == null) {
     * String filename = "PostcodeToPostcodeID_HashMap_String__SHBE_ID"
     * + Strings.class;
     * PostcodeToPostcodeDWLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return PostcodeToPostcodeDWLookupFile;}
     *
     * @return
     */
    public final File getPostcodeToPostcodeIDLookupFile() {
        if (PostcodeToPostcodeIDLookupFile == null) {
            String filename = "PostcodeToPostcodeID_HashMap_String__SHBE_ID"
                    + Strings.sBinaryFileExtension;
            PostcodeToPostcodeIDLookupFile = new File(
                    Files.getGeneratedSHBEDir(), filename);
        }
        return PostcodeToPostcodeIDLookupFile;
    }

    /**
     * {@code if (PostcodeIDToPostcodeLookupFile == null) {
     * String filename = "PostcodeIDToPostcode_HashMap_SHBE_ID__String"
     * + Strings.sBinaryFileExtension;
     * PostcodeIDToPostcodeLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return PostcodeIDToPostcodeLookupFile;}
     *
     * @return
     */
    public final File getPostcodeIDToPostcodeLookupFile() {
        if (PostcodeIDToPostcodeLookupFile == null) {
            String filename = "PostcodeIDToPostcode_HashMap_SHBE_ID__String"
                    + Strings.sBinaryFileExtension;
            PostcodeIDToPostcodeLookupFile = new File(
                    Files.getGeneratedSHBEDir(), filename);
        }
        return PostcodeIDToPostcodeLookupFile;
    }

    /**
     * {@code if (PostcodeIDToAGDT_PointLookupFile == null) {
     * String filename = "PostcodeIDToAGDT_Point_HashMap_SHBE_ID__AGDT_Point"
     * + Strings.sBinaryFileExtension;
     * PostcodeIDToAGDT_PointLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return PostcodeIDToAGDT_PointLookupFile;}
     *
     * @return
     */
    public final File getPostcodeIDToPointLookupsFile() {
        if (PostcodeIDToPointLookupsFile == null) {
            String filename = "PostcodeIDToPoint_HashMap_String__HashMap_SHBE_ID__AGDT_Point"
                    + Strings.sBinaryFileExtension;
            PostcodeIDToPointLookupsFile = new File(
                    Files.getGeneratedSHBEDir(), filename);
        }
        return PostcodeIDToPointLookupsFile;
    }

    public final File getCorrectedPostcodesFile() {
        if (CorrectedPostcodesFile == null) {
            String filename = "DW_CorrectedPostcodes"
                    + Strings.sBinaryFileExtension;
            CorrectedPostcodesFile = new File(
                    Files.getGeneratedLCCDir(),
                    filename);
        }
        return CorrectedPostcodesFile;
    }

    /**
     * {@code if (NINOToNINOIDLookupFile == null) {
     * String filename = "NINOToNINOID_HashMap_String__SHBE_ID"
     * + Strings.sBinaryFileExtension;
     * NINOToNINOIDLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return NINOToNINOIDLookupFile;}
     *
     * @return
     */
    public final File getNINOToNINOIDLookupFile() {
        if (NINOToNINOIDLookupFile == null) {
            String filename = "NINOToNINOID_HashMap_String__SHBE_ID"
                    + Strings.sBinaryFileExtension;
            NINOToNINOIDLookupFile = new File(
                    Files.getGeneratedSHBEDir(), filename);
        }
        return NINOToNINOIDLookupFile;
    }

    /**
     * {@code if (DOBToSHBE_IDLookupFile == null) {
     * String filename = "DOBToID_HashMap_String__SHBE_ID"
     * + Strings.sBinaryFileExtension;
     * DOBToSHBE_IDLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return DOBToSHBE_IDLookupFile;}
     *
     * @return
     */
    public final File getDOBToDOBIDLookupFile() {
        if (DOBToDOBIDLookupFile == null) {
            String filename = "DOBToDOBID_HashMap_String__SHBE_ID"
                    + Strings.sBinaryFileExtension;
            DOBToDOBIDLookupFile = new File(                    Files.getGeneratedSHBEDir(), filename);
        }
        return DOBToDOBIDLookupFile;
    }

    /**
     * {@code if (NINOIDToNINOLookupFile == null) {
     * String filename = "NINOIDToNINO_HashMap_SHBE_ID__String"
     * + Strings.sBinaryFileExtension;
     * NINOIDToNINOLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return NINOIDToNINOLookupFile;}
     *
     * @return
     */
    public final File getNINOIDToNINOLookupFile() {
        if (NINOIDToNINOLookupFile == null) {
            String filename = "NINOIDToNINO_HashMap_SHBE_ID__String"
                    + Strings.sBinaryFileExtension;
            NINOIDToNINOLookupFile = new File(Files.getGeneratedSHBEDir(), filename);
        }
        return NINOIDToNINOLookupFile;
    }

    /**
     * {@code if (DOBIDToDOBLookupFile == null) {
     * String filename = "DOBIDToDOB_HashMap_SHBE_ID__String"
     * + Strings.sBinaryFileExtension;
     * DOBIDToDOBLookupFile = new File(
     * Files.getGeneratedSHBEDir(),
     * filename);
     * }
     * return DOBIDToDOBLookupFile;}
     *
     * @return
     */
    public final File getDOBIDToDOBLookupFile() {
        if (DOBIDToDOBLookupFile == null) {
            String filename = "DOBIDToDOB_HashMap_SHBE_ID__String"
                    + Strings.sBinaryFileExtension;
            DOBIDToDOBLookupFile = new File(Files.getGeneratedSHBEDir(),
                    filename);
        }
        return DOBIDToDOBLookupFile;
    }

    public final File getClaimantPersonIDsFile() {
        if (ClaimantPersonIDsFile == null) {
            String filename = "Claimant_HashSet_SHBE_PersonID"
                    + Strings.sBinaryFileExtension;
            ClaimantPersonIDsFile = new File(Files.getGeneratedSHBEDir(),
                    filename);
        }
        return ClaimantPersonIDsFile;
    }

    public final File getPartnerPersonIDsFile() {
        if (PartnerPersonIDsFile == null) {
            String filename = "Partner_HashSet_SHBE_PersonID"
                    + Strings.sBinaryFileExtension;
            PartnerPersonIDsFile = new File(
                    Files.getGeneratedSHBEDir(),
                    filename);
        }
        return PartnerPersonIDsFile;
    }

    public final File getNonDependentPersonIDsFile() {
        if (NonDependentPersonIDsFile == null) {
            String filename = "NonDependent_HashSet_SHBE_PersonID"
                    + Strings.sBinaryFileExtension;
            NonDependentPersonIDsFile = new File(
                    Files.getGeneratedSHBEDir(),
                    filename);
        }
        return NonDependentPersonIDsFile;
    }

    public final File getPersonIDToClaimIDLookupFile() {
        if (PersonIDToClaimIDsLookupFile == null) {
            String filename = "PersonIDToClaimIDsLookup_HashMap_SHBE_PersonID__HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension;
            PersonIDToClaimIDsLookupFile = new File(
                    Files.getGeneratedSHBEDir(),
                    filename);
        }
        return PersonIDToClaimIDsLookupFile;
    }

    /**
     * If getData().get(YM3) != null then return it.Otherwise try to load it
     * from file and return it. Failing that return null.
     *
     * @param YM3
     * @param hoome
     * @return
     */
    public SHBE_Records getRecords(ONSPD_YM3 YM3, boolean hoome) {
        try {
            return getRecords(YM3);
        } catch (OutOfMemoryError e) {
            if (hoome) {
                Env.clearMemoryReserve();
                Env.clearAllData();
                SHBE_Records r = getRecords(YM3, hoome);
                Env.initMemoryReserve();
                return r;
            } else {
                throw e;
            }
        }
    }

    /**
     * If getData().get(YM3) != null then return it. Otherwise try to load it
     * from file and return it. Failing that return null.
     *
     * @param YM3
     * @return
     */
    protected SHBE_Records getRecords(ONSPD_YM3 YM3) {
        SHBE_Records r;
        r = getData().get(YM3);
        if (r == null) {
            File f;
            f = getFile(YM3);
            if (f.exists()) {
                r = (SHBE_Records) Generic_IO.readObject(f);
                return r;
            }
        }
        return r;
    }

    /**
     * {@code return new File(
     * Files.getGeneratedSHBEDir(),
     * YM3);}
     *
     * @param YM3
     * @return
     */
    protected File getDir(ONSPD_YM3 YM3) {
        return new File(
                Files.getGeneratedSHBEDir(),
                YM3.toString());
    }

    /**
     * {@code File result;
     * File dir;
     * dir = getDir(YM3);
     * if (!dir.exists()) {
     * dir.mkdirs();
     * }
     * result = new File(
     * dir,
     * "SHBE_Records"
     * + Strings.sBinaryFileExtension);
     * return result;}
     *
     * @param YM3
     * @return
     */
    protected File getFile(ONSPD_YM3 YM3) {
        File r;
        File dir;
        dir = getDir(YM3);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        r = new File(dir, "SHBE_Records" + Strings.sBinaryFileExtension);
        return r;
    }

    /**
     * Clears all SHBE_Records in Data from fast access memory.
     *
     * @return The number of SHBE_Records cleared.
     */
    public int clearAllCache() {
        String methodName = "clearAllCache";
        Env.logO(Env.DEBUG_Level, "<" + methodName + ">");
        int result = 0;
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 YM3;
        SHBE_Records recs;
        while (ite.hasNext()) {
            YM3 = ite.next();
            recs = Data.get(YM3);
            if (recs != null) {
                recs = null;
                result++;
            }
        }
        Env.logO(Env.DEBUG_Level, "</" + methodName + ">");
        return result;
    }

    /**
     * Clears all SHBE_Records in Data from fast access memory with the
     * exception of that for YM3.
     *
     * @param YM3 The Year_Month key of for the SHBE_Records not to be cleared
     * from fast access memory.
     * @return The number of SHBE_Records cleared.
     */
    public int clearAllCacheExcept(ONSPD_YM3 YM3) {
        int result = 0;
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 aYM3;
        SHBE_Records recs;
        while (ite.hasNext()) {
            aYM3 = ite.next();
            if (!aYM3.equals(YM3)) {
                recs = Data.get(YM3);
                if (recs != null) {
                    recs = null;
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * Clears some SHBE_Records in Data from fast access memory.
     *
     * @return true iff some SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSomeCache() {
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 YM3;
        SHBE_Records recs;
        while (ite.hasNext()) {
            YM3 = ite.next();
            recs = Data.get(YM3);
            if (recs != null) {
                recs = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Clears some SHBE_Records in Data from fast access memory except the
     * SHBE_Records in Data indexed by YM3.
     *
     * @param YM3
     * @return true iff some SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSomeCacheExcept(ONSPD_YM3 YM3) {
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 aYM3;
        SHBE_Records recs;
        while (ite.hasNext()) {
            aYM3 = ite.next();
            if (!YM3.equals(aYM3)) {
                recs = Data.get(YM3);
                if (recs != null) {
                    recs = null;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Clears SHBE_Records for YM3 in Data from fast access memory.
     *
     * @param YM3 The Year_Month key for accessing the SHBE_Records to be
     * cleared from fast access memory.
     * @return true iff the data were cleared and false otherwise (when the data
     * is already cleared).
     */
    public boolean clearCache(ONSPD_YM3 YM3) {
        SHBE_Records recs;
        recs = Data.get(YM3);
        if (recs != null) {
            recs = null;
            return true;
        }
        return false;
    }
}
