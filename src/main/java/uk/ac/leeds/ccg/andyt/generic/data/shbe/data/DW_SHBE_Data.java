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
//import uk.ac.leeds.ccg.andyt.projects.digitalwelfare.data.DW_CorrectedPostcodes;
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
public class DW_SHBE_Data extends SHBE_Object {

    /**
     * For convenience
     */
    transient ONSPD_Postcode_Handler DW_Postcode_Handler;
    transient SHBE_Files SHBE_Files;
    transient SHBE_Strings SHBE_Strings;

    /**
     * A reference to all the Data for this Payment Type. The keys are YM3 and
     * the values are the respective collection.
     */
    protected HashMap<ONSPD_YM3, DW_SHBE_Records> Data;

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

    private DW_CorrectedPostcodes CorrectedPostcodes;

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
     * DW_PersonID of Claimants
     */
    HashSet<DW_PersonID> ClaimantPersonIDs;

    /**
     * DW_PersonID of Partners
     */
    HashSet<DW_PersonID> PartnerPersonIDs;

    /**
     * DW_PersonID of Non-Dependents
     */
    HashSet<DW_PersonID> NonDependentPersonIDs;

    /**
     * DW_PersonID to ClaimIDs Lookup
     */
    HashMap<DW_PersonID, HashSet<SHBE_ID>> PersonIDToClaimIDsLookup;

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

    public DW_SHBE_Data() {
    }

    public DW_SHBE_Data(SHBE_Environment env) {
        super(env);
        DW_Postcode_Handler = env.getPostcode_Handler();
        SHBE_Files = env.Files;
        SHBE_Strings = env.Strings;
    }

    /**
     * {@code
     * if (Data == null) {
     * if (f.exists()) {
     * Data = (HashMap<String, DW_SHBE_Records>) Generic_IO.readObject(f);
     * } else {
     * Data = new HashMap<String, DW_SHBE_Records>(); } } return Data; }
     *
     * @param f
     * @return
     */
    public HashMap<ONSPD_YM3, DW_SHBE_Records> getData(File f) {
        if (Data == null) {
            if (f.exists()) {
                Data = (HashMap<ONSPD_YM3, DW_SHBE_Records>) Generic_IO.readObject(f);
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
    public HashMap<ONSPD_YM3, DW_SHBE_Records> getData() {
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
            String filename = "Data_HashMap_String__DW_SHBE_Records"
                    + SHBE_Strings.sBinaryFileExtension;
            DataFile = new File(SHBE_Files.getGeneratedDataDir(SHBE_Strings), filename);
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
    public HashMap<String, SHBE_ID> getStringToIDLookup(
            File f) {
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

    public final DW_CorrectedPostcodes getCorrectedPostcodes(
            File f) {
        if (CorrectedPostcodes == null) {
            if (f.exists()) {
                CorrectedPostcodes = (DW_CorrectedPostcodes) Generic_IO.readObject(f);
            } else {
                new DW_CorrectedPostcodes(Env).run();
                return getCorrectedPostcodes(f);
            }
        }
        return CorrectedPostcodes;
    }

    public DW_CorrectedPostcodes getCorrectedPostcodes() {
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
    public final HashSet<DW_PersonID> getClaimantPersonIDs(
            File f) {
        if (ClaimantPersonIDs == null) {
            ClaimantPersonIDs = SHBE_Collections.getHashSet_DW_PersonID(f);
        }
        return ClaimantPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<DW_PersonID> getClaimantPersonIDs() {
        ClaimantPersonIDsFile = getClaimantPersonIDsFile();
        return getClaimantPersonIDs(ClaimantPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<DW_PersonID> getPartnerPersonIDs(
            File f) {
        if (PartnerPersonIDs == null) {
            PartnerPersonIDs = SHBE_Collections.getHashSet_DW_PersonID(f);
        }
        return PartnerPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<DW_PersonID> getPartnerPersonIDs() {
        PartnerPersonIDsFile = getPartnerPersonIDsFile();
        return getPartnerPersonIDs(PartnerPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashSet<DW_PersonID> getNonDependentPersonIDs(
            File f) {
        if (NonDependentPersonIDs == null) {
            NonDependentPersonIDs = SHBE_Collections.getHashSet_DW_PersonID(f);
        }
        return NonDependentPersonIDs;
    }

    /**
     * @return
     */
    public HashSet<DW_PersonID> getNonDependentPersonIDs() {
        NonDependentPersonIDsFile = getNonDependentPersonIDsFile();
        return getNonDependentPersonIDs(NonDependentPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final HashMap<DW_PersonID, HashSet<SHBE_ID>> getPersonIDToClaimIDsLookup(
            File f) {
        if (PersonIDToClaimIDsLookup == null) {
            PersonIDToClaimIDsLookup = SHBE_Collections.getHashMap_DW_PersonID__HashSet_SHBE_ID(f);
        }
        return PersonIDToClaimIDsLookup;
    }

    /**
     * All DW_PersonID to ClaimIDs Lookup
     *
     * @return
     */
    public HashMap<DW_PersonID, HashSet<SHBE_ID>> getPersonIDToClaimIDLookup() {
        PersonIDToClaimIDsLookupFile = getPersonIDToClaimIDLookupFile();
        return getPersonIDToClaimIDsLookup(PersonIDToClaimIDsLookupFile);
    }

    /**
     * All DW_PersonID to ClaimIDs Lookup
     */
    HashMap<DW_PersonID, HashSet<SHBE_ID>> PersonIDToClaimIDLookup;

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
        NearestYM3ForONSPDLookup = DW_Postcode_Handler.getNearestYM3ForONSPDLookup(YM3);
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
     * + SHBE_Strings.sBinaryFileExtension;
     * PostcodeToPostcodeIDLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return ClaimRefToClaimIDLookupFile;}
     *
     * @return
     */
    public final File getClaimRefToClaimIDLookupFile() {
        if (ClaimRefToClaimIDLookupFile == null) {
            String filename = "ClaimRefToClaimID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.sBinaryFileExtension;
            ClaimRefToClaimIDLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return ClaimRefToClaimIDLookupFile;
    }

    /**
     * {@code if (ClaimIDToClaimRefLookupFile == null) {
     * String filename = "ClaimIDToClaimRef_HashMap_SHBE_ID__String"
     * + SHBE_Strings.sBinaryFileExtension;
     * ClaimIDToClaimRefLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return ClaimIDToClaimRefLookupFile;}
     *
     * @return
     */
    public final File getClaimIDToClaimRefLookupFile() {
        if (ClaimIDToClaimRefLookupFile == null) {
            String filename = "ClaimIDToClaimRef_HashMap_SHBE_ID__String"
                    + SHBE_Strings.sBinaryFileExtension;
            ClaimIDToClaimRefLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return ClaimIDToClaimRefLookupFile;
    }

    /**
     * {@code if (PostcodeToPostcodeDWLookupFile == null) {
     * String filename = "PostcodeToPostcodeID_HashMap_String__SHBE_ID"
     * + SHBE_Strings.class;
     * PostcodeToPostcodeDWLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return PostcodeToPostcodeDWLookupFile;}
     *
     * @return
     */
    public final File getPostcodeToPostcodeIDLookupFile() {
        if (PostcodeToPostcodeIDLookupFile == null) {
            String filename = "PostcodeToPostcodeID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.sBinaryFileExtension;
            PostcodeToPostcodeIDLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return PostcodeToPostcodeIDLookupFile;
    }

    /**
     * {@code if (PostcodeIDToPostcodeLookupFile == null) {
     * String filename = "PostcodeIDToPostcode_HashMap_SHBE_ID__String"
     * + SHBE_Strings.sBinaryFileExtension;
     * PostcodeIDToPostcodeLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return PostcodeIDToPostcodeLookupFile;}
     *
     * @return
     */
    public final File getPostcodeIDToPostcodeLookupFile() {
        if (PostcodeIDToPostcodeLookupFile == null) {
            String filename = "PostcodeIDToPostcode_HashMap_SHBE_ID__String"
                    + SHBE_Strings.sBinaryFileExtension;
            PostcodeIDToPostcodeLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return PostcodeIDToPostcodeLookupFile;
    }

    /**
     * {@code if (PostcodeIDToAGDT_PointLookupFile == null) {
     * String filename = "PostcodeIDToAGDT_Point_HashMap_SHBE_ID__AGDT_Point"
     * + SHBE_Strings.sBinaryFileExtension;
     * PostcodeIDToAGDT_PointLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return PostcodeIDToAGDT_PointLookupFile;}
     *
     * @return
     */
    public final File getPostcodeIDToPointLookupsFile() {
        if (PostcodeIDToPointLookupsFile == null) {
            String filename = "PostcodeIDToPoint_HashMap_String__HashMap_SHBE_ID__AGDT_Point"
                    + SHBE_Strings.sBinaryFileExtension;
            PostcodeIDToPointLookupsFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return PostcodeIDToPointLookupsFile;
    }

    public final File getCorrectedPostcodesFile() {
        if (CorrectedPostcodesFile == null) {
            String filename = "DW_CorrectedPostcodes"
                    + SHBE_Strings.sBinaryFileExtension;
            CorrectedPostcodesFile = new File(
                    SHBE_Files.getGeneratedLCCDir(),
                    filename);
        }
        return CorrectedPostcodesFile;
    }

    /**
     * {@code if (NINOToNINOIDLookupFile == null) {
     * String filename = "NINOToNINOID_HashMap_String__SHBE_ID"
     * + SHBE_Strings.sBinaryFileExtension;
     * NINOToNINOIDLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return NINOToNINOIDLookupFile;}
     *
     * @return
     */
    public final File getNINOToNINOIDLookupFile() {
        if (NINOToNINOIDLookupFile == null) {
            String filename = "NINOToNINOID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.sBinaryFileExtension;
            NINOToNINOIDLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return NINOToNINOIDLookupFile;
    }

    /**
     * {@code if (DOBToSHBE_IDLookupFile == null) {
     * String filename = "DOBToID_HashMap_String__SHBE_ID"
     * + SHBE_Strings.sBinaryFileExtension;
     * DOBToSHBE_IDLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return DOBToSHBE_IDLookupFile;}
     *
     * @return
     */
    public final File getDOBToDOBIDLookupFile() {
        if (DOBToDOBIDLookupFile == null) {
            String filename = "DOBToDOBID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.sBinaryFileExtension;
            DOBToDOBIDLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return DOBToDOBIDLookupFile;
    }

    /**
     * {@code if (NINOIDToNINOLookupFile == null) {
     * String filename = "NINOIDToNINO_HashMap_SHBE_ID__String"
     * + SHBE_Strings.sBinaryFileExtension;
     * NINOIDToNINOLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return NINOIDToNINOLookupFile;}
     *
     * @return
     */
    public final File getNINOIDToNINOLookupFile() {
        if (NINOIDToNINOLookupFile == null) {
            String filename = "NINOIDToNINO_HashMap_SHBE_ID__String"
                    + SHBE_Strings.sBinaryFileExtension;
            NINOIDToNINOLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return NINOIDToNINOLookupFile;
    }

    /**
     * {@code if (DOBIDToDOBLookupFile == null) {
     * String filename = "DOBIDToDOB_HashMap_SHBE_ID__String"
     * + SHBE_Strings.sBinaryFileExtension;
     * DOBIDToDOBLookupFile = new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * filename);
     * }
     * return DOBIDToDOBLookupFile;}
     *
     * @return
     */
    public final File getDOBIDToDOBLookupFile() {
        if (DOBIDToDOBLookupFile == null) {
            String filename = "DOBIDToDOB_HashMap_SHBE_ID__String"
                    + SHBE_Strings.sBinaryFileExtension;
            DOBIDToDOBLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return DOBIDToDOBLookupFile;
    }

    public final File getClaimantPersonIDsFile() {
        if (ClaimantPersonIDsFile == null) {
            String filename = "Claimant_HashSet_DW_PersonID"
                    + SHBE_Strings.sBinaryFileExtension;
            ClaimantPersonIDsFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return ClaimantPersonIDsFile;
    }

    public final File getPartnerPersonIDsFile() {
        if (PartnerPersonIDsFile == null) {
            String filename = "Partner_HashSet_DW_PersonID"
                    + SHBE_Strings.sBinaryFileExtension;
            PartnerPersonIDsFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return PartnerPersonIDsFile;
    }

    public final File getNonDependentPersonIDsFile() {
        if (NonDependentPersonIDsFile == null) {
            String filename = "NonDependent_HashSet_DW_PersonID"
                    + SHBE_Strings.sBinaryFileExtension;
            NonDependentPersonIDsFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return NonDependentPersonIDsFile;
    }

    public final File getPersonIDToClaimIDLookupFile() {
        if (PersonIDToClaimIDsLookupFile == null) {
            String filename = "PersonIDToClaimIDsLookup_HashMap_DW_PersonID__HashSet_SHBE_ID"
                    + SHBE_Strings.sBinaryFileExtension;
            PersonIDToClaimIDsLookupFile = new File(
                    SHBE_Files.getGeneratedDataDir(SHBE_Strings),
                    filename);
        }
        return PersonIDToClaimIDsLookupFile;
    }

    /**
     * If getData().get(YM3) != null then return it. Otherwise try to load it
     * from file and return it. Failing that return null.
     *
     * @param YM3
     * @return
     */
    public DW_SHBE_Records getDW_SHBE_Records(ONSPD_YM3 YM3) {
        DW_SHBE_Records DW_SHBE_Records;
        DW_SHBE_Records = getData().get(YM3);
        if (DW_SHBE_Records == null) {
            File f;
            f = getFile(YM3);
            if (f.exists()) {
                DW_SHBE_Records = (DW_SHBE_Records) Generic_IO.readObject(f);
                return DW_SHBE_Records;
            }
        }
        return DW_SHBE_Records;
    }

    /**
     * {@code return new File(
     * SHBE_Files.getGeneratedDataDir(SHBE_Strings),
     * YM3);}
     *
     * @param YM3
     * @return
     */
    protected File getDir(ONSPD_YM3 YM3) {
        return new File(
                SHBE_Files.getGeneratedDataDir(SHBE_Strings),
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
     * "DW_SHBE_Records"
     * + SHBE_Strings.sBinaryFileExtension);
     * return result;}
     *
     * @param YM3
     * @return
     */
    protected File getFile(ONSPD_YM3 YM3) {
        File result;
        File dir;
        dir = getDir(YM3);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        result = new File(
                dir,
                "DW_SHBE_Records"
                + SHBE_Strings.sBinaryFileExtension);
        return result;
    }

    /**
     * Clears all DW_SHBE_Records in Data from fast access memory.
     *
     * @return The number of DW_SHBE_Records cleared.
     */
    public int clearAllCache() {
        int result = 0;
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 YM3;
        DW_SHBE_Records recs;
        while (ite.hasNext()) {
            YM3 = ite.next();
            recs = Data.get(YM3);
            if (recs != null) {
                recs = null;
                result++;
            }
        }
        return result;
    }

    /**
     * Clears all DW_SHBE_Records in Data from fast access memory with the
     * exception of that for YM3.
     *
     * @param YM3 The Year_Month key of for the DW_SHBE_Records not to be
     * cleared from fast access memory.
     * @return The number of DW_SHBE_Records cleared.
     */
    public int clearAllCacheExcept(ONSPD_YM3 YM3) {
        int result = 0;
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 aYM3;
        DW_SHBE_Records recs;
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
     * Clears some DW_SHBE_Records in Data from fast access memory.
     *
     * @return true iff some DW_SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSomeCache() {
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 YM3;
        DW_SHBE_Records recs;
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
     * Clears some DW_SHBE_Records in Data from fast access memory except the
     * DW_SHBE_Records in Data indexed by YM3.
     *
     * @param YM3
     * @return true iff some DW_SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSomeCacheExcept(ONSPD_YM3 YM3) {
        Iterator<ONSPD_YM3> ite;
        ite = Data.keySet().iterator();
        ONSPD_YM3 aYM3;
        DW_SHBE_Records recs;
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
     * Clears DW_SHBE_Records for YM3 in Data from fast access memory.
     *
     * @param YM3 The Year_Month key for accessing the DW_SHBE_Records to be
     * cleared from fast access memory.
     * @return true iff the data were cleared and false otherwise (when the data
     * is already cleared).
     */
    public boolean clearCache(ONSPD_YM3 YM3) {
        DW_SHBE_Records recs;
        recs = Data.get(YM3);
        if (recs != null) {
            recs = null;
            return true;
        }
        return false;
    }
}
