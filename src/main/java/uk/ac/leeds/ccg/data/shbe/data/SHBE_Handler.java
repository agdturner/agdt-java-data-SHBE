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
package uk.ac.leeds.ccg.data.shbe.data;

import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_PersonID;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import uk.ac.leeds.ccg.data.ukp.data.onspd.ONSPD_Point;
//import uk.ac.leeds.ccg.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.generic.util.Generic_Time;
import uk.ac.leeds.ccg.data.ukp.data.UKP_Data;
import uk.ac.leeds.ccg.data.ukp.data.id.UKP_RecordID;
import uk.ac.leeds.ccg.data.ukp.util.UKP_YM3;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_ClaimID;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_DOBID;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_NINOID;
import uk.ac.leeds.ccg.generic.io.Generic_FileStore;
import uk.ac.leeds.ccg.generic.io.Generic_IO;
//import uk.ac.leeds.ccg.math.Generic_BigDecimal;
//import uk.ac.leeds.ccg.projects.digitalwelfare.data.underoccupied.DW_UO_Record;
//import uk.ac.leeds.ccg.projects.digitalwelfare.data.underoccupied.DW_UO_Set;

/**
 * Class for handling SHBE data.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Handler extends SHBE_Object {

    /**
     * For convenience, these are initialised in construction from env.
     */
    private final transient UKP_Data pData;

    /**
     * For a set of expected record types. ("A", "D", "C", "R", "T", "P", "G",
     * "E", "S").
     */
    protected Set<String> types;

    /**
     * The main data.
     */
    protected Map<UKP_YM3, SHBE_Records> data;

    /**
     * Path for storing data
     */
    private Path path;

    /**
     * Claim Reference to Claim ID Lookup.
     */
    private Map<String, SHBE_ClaimID> c2cid;

    /**
     * Claim ID to Claim Reference Lookup.
     */
    private Map<SHBE_ClaimID, String> cid2c;

    private SHBE_CorrectedPostcodes correctedPostcodes;

    /**
     * National Insurance Number to ID lookup.
     */
    private Map<String, SHBE_NINOID> n2nid;

    /**
     * ID to National Insurance Number lookup.
     */
    private Map<SHBE_NINOID, String> nid2n;

    /**
     * Date Of Birth to ID lookup.
     */
    private Map<String, SHBE_DOBID> d2did;

    /**
     * ID to Date Of Birth lookup.
     */
    private Map<SHBE_DOBID, String> did2d;

    /**
     * All Person IDs of claimants.
     */
    Set<SHBE_PersonID> claimantPersonIDs;

    /**
     * All Person IDs of claimant partners.
     */
    Set<SHBE_PersonID> partnerPersonIDs;

    /**
     * All Person IDs of non-Dependents.
     */
    Set<SHBE_PersonID> nonDependentPersonIDs;

    /**
     * All Person IDs to Claim IDs. A person may in some circumstances be part
     * of more than one claim, but usually it is just one.
     */
    Map<SHBE_PersonID, Set<SHBE_ClaimID>> pid2cids;

    /**
     * Postcode to Postcode SHBE_ID Lookup.
     */
    private Map<String, UKP_RecordID> p2pid;

    /**
     * Postcode SHBE_ID to Postcode Lookup.
     */
    private Map<UKP_RecordID, String> pid2p;

    /**
     * Postcode ID to Point lookups. There is a different one for each ONSPD
     * File. The keys are Nearest YM3s for the respective ONSPD File.
     */
    private Map<UKP_YM3, Map<UKP_RecordID, ONSPD_Point>> pid2point;

    /**
     * {@link #c2cid} File.
     */
    private Path c2cidFile;

    /**
     * {@link #cid2c} File.
     */
    private Path cid2cFile;

    /**
     * {@link #nid2n} File.
     */
    private Path nid2nFile;

    /**
     * {@link #did2d} File.
     */
    private Path did2dFile;

    /**
     * claimantPersonIDs File.
     */
    private Path claimantPersonIDsFile;

    /**
     * {@link #partnerPersonIDs} File.
     */
    private Path partnerPersonIDsFile;

    /**
     * {@link #nonDependentPersonIDs} File.
     */
    private Path ndpidsFile;

    /**
     * {@link #pid2cids} File.
     */
    private Path pid2cidsFile;

    /**
     * {@link #correctedPostcodes} File.
     */
    private Path correctedPostcodesFile;

    /**
     * {@link #n2nid} File.
     */
    private Path n2nidFile;

    /**
     * {@link #d2did} File.
     */
    private Path d2didFile;

    /**
     * {@link #p2pid} File.
     */
    private Path p2pidFile;

    /**
     * {@link #pid2p} File.
     */
    private Path pid2pFile;

    /**
     * {@link #pid2point} File.
     */
    private Path pid2pointFile;

    public SHBE_Handler(SHBE_Environment e) throws IOException, Exception {
        this(e, e.env.initLog("SHBE_Handler"));
    }

    public SHBE_Handler(SHBE_Environment e, int logID) {
        super(e, logID);
//        n2nid = e.getN2nid();
//        d2did = data.getDOBToDOBIDLookup();
        pData = e.oe.getHandler();
    }

    /**
     * For loading in all SHBE data.
     *
     * @param logID The ID of the log for writing to.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public void run(int logID) throws IOException, ClassNotFoundException, Exception {
        String[] sfs = getFilenames();
        UKP_YM3 lastYM3 = getYM3(sfs[sfs.length - 1]);
        UKP_YM3 nYM3 = pData.getNearestYM3ForONSPDLookup(lastYM3);
        Path dir = files.getInputSHBEDir();
        for (String sf : sfs) {
            SHBE_Records recs = new SHBE_Records(env, logID, dir, sf, nYM3);
            Generic_IO.writeObject(recs, recs.getFile());
        }
        writeLookups();
        // Make a backup copy
        Path f = Paths.get(files.getGeneratedLCCDir().toString(),
                "SHBEBackup");
        Generic_FileStore fs;
        if (Files.isDirectory(f)) {
            fs = new Generic_FileStore(f);
        } else {
            fs = new Generic_FileStore(f.getParent(), "SHBEBackup");
            fs.addDir();
        }
        Generic_IO.copy(files.getGeneratedSHBEDir(), fs.getHighestLeaf());
    }

    public Map<UKP_YM3, SHBE_Records> getData(Path f) throws IOException,
            ClassNotFoundException {
        if (data == null) {
            if (Files.exists(f)) {
                data = (Map<UKP_YM3, SHBE_Records>) Generic_IO.readObject(f);
            } else {
                data = new HashMap<>();
            }
        }
        return data;
    }

    /**
     * @return The data.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Map<UKP_YM3, SHBE_Records> getData() throws IOException,
            ClassNotFoundException {
        path = getDataFile();
        return getData(path);
    }

    /**
     * If {@link #path} is {@code null}, initialise it, then return it.
     *
     * @return {@link #path} initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     */
    public final Path getDataFile() throws IOException {
        if (path == null) {
            String filename = "Data_Map_String__SHBE_Records"
                    + SHBE_Strings.s_BinaryFileExtension;
            path = Paths.get(files.getGeneratedSHBEDir().toString(),
                    filename);
        }
        return path;
    }

    /**
     * If {@link #c2cid} is {@code null} initialise it from {@code f}.
     *
     * @param f The file to load from.
     * @return {@link #c2cid} initialised if it is {@code null} from {@code f}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Map<String, SHBE_ClaimID> getClaimRefToClaimIDLookup(Path f)
            throws IOException, ClassNotFoundException {
        if (c2cid == null) {
            c2cid = getStringToTLookup(f);
        }
        return c2cid;
    }

    /**
     * @param <T> Type.
     * @param f The file to load the map from.
     * @return A map read from {@code f} if it exists and a new empty HashMap
     * otherwise.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public <T> Map<String, T> getStringToTLookup(Path f) throws IOException,
            ClassNotFoundException {
        Map<String, T> r;
        if (Files.exists(f)) {
            r = (Map<String, T>) Generic_IO.readObject(f);
        } else {
            r = new HashMap<>();
        }
        return r;
    }

    /**
     * If {@link #cid2c} is {@code null} initialise it from {@code f}.
     *
     * @param f The file to load from.
     * @return {@link #cid2c} initialised if it is {@code null} from {@code f}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Map<SHBE_ClaimID, String> getClaimIDToClaimRefLookup(Path f)
            throws IOException, ClassNotFoundException {
        if (cid2c == null) {
            cid2c = env.collections.getHashMapTString(f);
        }
        return cid2c;
    }

    /**
     * @return Map of Claim Refs to Claim IDs.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Map<String, SHBE_ClaimID> getC2cid()
            throws IOException, ClassNotFoundException {
        c2cidFile = getC2cidFile();
        return getClaimRefToClaimIDLookup(c2cidFile);
    }

    /**
     * @return Maps of Claim IDs to Claim Ref.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Map<SHBE_ClaimID, String> getCid2c()
            throws IOException, ClassNotFoundException {
        cid2cFile = getCid2cFile();
        return getClaimIDToClaimRefLookup(cid2cFile);
    }

    public final SHBE_CorrectedPostcodes getCorrectedPostcodes(Path f)
            throws IOException, ClassNotFoundException {
        if (correctedPostcodes == null) {
            if (Files.exists(f)) {
                correctedPostcodes = (SHBE_CorrectedPostcodes) Generic_IO.readObject(f);
            } else {
                new SHBE_CorrectedPostcodes(env).run();
                return getCorrectedPostcodes(f);
            }
        }
        return correctedPostcodes;
    }

    public SHBE_CorrectedPostcodes getCorrectedPostcodes() throws IOException,
            ClassNotFoundException {
        correctedPostcodesFile = getCorrectedPostcodesFile();
        return getCorrectedPostcodes(correctedPostcodesFile);
    }

    public final Map<String, SHBE_NINOID> getNINOToNINOIDLookup(Path f)
            throws IOException, ClassNotFoundException {
        if (n2nid == null) {
            n2nid = getStringToTLookup(f);
        }
        return n2nid;
    }

    public Map<String, SHBE_NINOID> getN2nid()
            throws IOException, ClassNotFoundException {
        n2nidFile = getN2nidFile();
        return getNINOToNINOIDLookup(n2nidFile);
    }

    public final Map<String, SHBE_DOBID> getDOBToDOBIDLookup(Path f)
            throws IOException, ClassNotFoundException {
        if (d2did == null) {
            d2did = getStringToTLookup(f);
        }
        return d2did;
    }

    public Map<String, SHBE_DOBID> getD2did()
            throws IOException, ClassNotFoundException {
        d2didFile = getD2didFile();
        return getDOBToDOBIDLookup(d2didFile);
    }

    public final Map<SHBE_NINOID, String> getNINOIDToNINOLookup(Path f)
            throws IOException, ClassNotFoundException {
        if (nid2n == null) {
            nid2n = env.collections.getHashMapTString(f);
        }
        return nid2n;
    }

    public Map<SHBE_NINOID, String> getNINOIDToNINOLookup()
            throws IOException, ClassNotFoundException {
        nid2nFile = getNid2nFile();
        return getNINOIDToNINOLookup(nid2nFile);
    }

    public final Map<SHBE_DOBID, String> getDOBIDToDOBLookup(Path f)
            throws IOException, ClassNotFoundException {
        if (did2d == null) {
            did2d = env.collections.getHashMapTString(f);
        }
        return did2d;
    }

    public Map<SHBE_DOBID, String> getDid2d()
            throws IOException, ClassNotFoundException {
        did2dFile = getDid2dFile();
        return getDOBIDToDOBLookup(did2dFile);
    }

    public final Set<SHBE_PersonID> getClaimantPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (claimantPersonIDs == null) {
            claimantPersonIDs = env.collections.getPersonIDs(f);
        }
        return claimantPersonIDs;
    }

    public Set<SHBE_PersonID> getClaimantPersonIDs()
            throws IOException, ClassNotFoundException {
        claimantPersonIDsFile = getClaimantPersonIDsFile();
        return getClaimantPersonIDs(claimantPersonIDsFile);
    }

    public final Set<SHBE_PersonID> getPartnerPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (partnerPersonIDs == null) {
            partnerPersonIDs = env.collections.getPersonIDs(f);
        }
        return partnerPersonIDs;
    }

    public Set<SHBE_PersonID> getPartnerPersonIDs()
            throws IOException, ClassNotFoundException {
        partnerPersonIDsFile = getPartnerPersonIDsFile();
        return getPartnerPersonIDs(partnerPersonIDsFile);
    }

    public final Set<SHBE_PersonID> getNonDependentPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (nonDependentPersonIDs == null) {
            nonDependentPersonIDs = env.collections.getPersonIDs(f);
        }
        return nonDependentPersonIDs;
    }

    public Set<SHBE_PersonID> getNonDependentPersonIDs()
            throws IOException, ClassNotFoundException {
        ndpidsFile = getNdpidsFile();
        return getNonDependentPersonIDs(ndpidsFile);
    }

    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cids(Path f)
            throws IOException, ClassNotFoundException {
        if (pid2cids == null) {
            if (Files.exists(f)) {
                pid2cids = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                pid2cids = new HashMap<>();
            }
        }
        return pid2cids;
    }

    public Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cids()
            throws IOException, ClassNotFoundException {
        pid2cidsFile = getPid2cidsFile();
        return getPid2cids(pid2cidsFile);
    }

    public final Map<String, UKP_RecordID> getP2pid(Path f)
            throws IOException, ClassNotFoundException {
        if (p2pid == null) {
            if (Files.exists(f)) {
                p2pid = (Map<String, UKP_RecordID>) Generic_IO.readObject(f);
            } else {
                p2pid = new HashMap<>();
            }
        }
        return p2pid;
    }

    public Map<String, UKP_RecordID> getPostcodeToPostcodeIDLookup()
            throws IOException, ClassNotFoundException {
        p2pidFile = getP2pidFile();
        return getP2pid(p2pidFile);
    }

    public final Map<UKP_RecordID, String> getPostcodeIDToPostcodeLookup(Path f)
            throws IOException, ClassNotFoundException {
        if (pid2p == null) {
            if (Files.exists(f)) {
                pid2p = (Map<UKP_RecordID, String>) Generic_IO.readObject(f);
            } else {
                pid2p = new HashMap<>();
            }
        }
        return pid2p;
    }

    public final Map<UKP_YM3, Map<UKP_RecordID, ONSPD_Point>> getpid2point(
            Path f) throws IOException, ClassNotFoundException {
        if (pid2point == null) {
            if (Files.exists(f)) {
                pid2point = (Map<UKP_YM3, Map<UKP_RecordID, ONSPD_Point>>) Generic_IO.readObject(f);
            } else {
                pid2point = new HashMap<>();
            }
        }
        return pid2point;
    }

    public Map<UKP_RecordID, String> getPid2p()
            throws IOException, ClassNotFoundException {
        pid2pFile = getPid2pFile();
        return getPostcodeIDToPostcodeLookup(pid2pFile);
    }

    public Map<UKP_YM3, Map<UKP_RecordID, ONSPD_Point>> getPid2point()
            throws IOException, ClassNotFoundException {
        pid2pointFile = getPid2pointFile();
        return getpid2point(pid2pointFile);
    }

    public Map<UKP_RecordID, ONSPD_Point> getPostcodeIDToPointLookup(UKP_YM3 YM3)
            throws IOException, ClassNotFoundException {
        UKP_YM3 nYM3 = pData.getNearestYM3ForONSPDLookup(YM3);
        Map<UKP_RecordID, ONSPD_Point> r;
        pid2point = getPid2point();
        if (pid2point.containsKey(nYM3)) {
            r = pid2point.get(nYM3);
        } else {
            r = new HashMap<>();
            pid2point.put(nYM3, r);
        }
        return r;
    }

    public final Path getC2cidFile() throws IOException,
            ClassNotFoundException {
        if (c2cidFile == null) {
            String filename = "ClaimRefToClaimID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            c2cidFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return c2cidFile;
    }

    public final Path getCid2cFile() throws IOException,
            ClassNotFoundException {
        if (cid2cFile == null) {
            String filename = "ClaimIDToClaimRef_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            cid2cFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return cid2cFile;
    }

    public final Path getP2pidFile() throws IOException,
            ClassNotFoundException {
        if (p2pidFile == null) {
            String filename = "PostcodeToPostcodeID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            p2pidFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return p2pidFile;
    }

    public final Path getPid2pFile() throws IOException,
            ClassNotFoundException {
        if (pid2pFile == null) {
            String filename = "PostcodeIDToPostcode_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            pid2pFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return pid2pFile;
    }

    public final Path getPid2pointFile() throws IOException,
            ClassNotFoundException {
        if (pid2pointFile == null) {
            String filename = "PostcodeIDToPoint_HashMap_String__HashMap_SHBE_ID__AGDT_Point"
                    + SHBE_Strings.s_BinaryFileExtension;
            pid2pointFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return pid2pointFile;
    }

    public final Path getCorrectedPostcodesFile() throws IOException,
            ClassNotFoundException {
        if (correctedPostcodesFile == null) {
            String filename = "DW_CorrectedPostcodes"
                    + SHBE_Strings.s_BinaryFileExtension;
            correctedPostcodesFile = Paths.get(
                    files.getGeneratedLCCDir().toString(), filename);
        }
        return correctedPostcodesFile;
    }

    public final Path getN2nidFile() throws IOException,
            ClassNotFoundException {
        if (n2nidFile == null) {
            String filename = "NINOToNINOID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            n2nidFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return n2nidFile;
    }

    public final Path getD2didFile() throws IOException, ClassNotFoundException {
        if (d2didFile == null) {
            String filename = "DOBToDOBID_HashMap_String__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            d2didFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return d2didFile;
    }

    public final Path getNid2nFile() throws IOException, ClassNotFoundException {
        if (nid2nFile == null) {
            String filename = "NINOIDToNINO_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            nid2nFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return nid2nFile;
    }

    public final Path getDid2dFile() throws IOException, ClassNotFoundException {
        if (did2dFile == null) {
            String filename = "DOBIDToDOB_HashMap_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension;
            did2dFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return did2dFile;
    }

    public final Path getClaimantPersonIDsFile() throws IOException,
            ClassNotFoundException {
        if (claimantPersonIDsFile == null) {
            String filename = "Claimant_HashSet_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension;
            claimantPersonIDsFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return claimantPersonIDsFile;
    }

    public final Path getPartnerPersonIDsFile() throws IOException,
            ClassNotFoundException {
        if (partnerPersonIDsFile == null) {
            String filename = "Partner_HashSet_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension;
            partnerPersonIDsFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return partnerPersonIDsFile;
    }

    public final Path getNdpidsFile() throws IOException,
            ClassNotFoundException {
        if (ndpidsFile == null) {
            String filename = "NonDependent_HashSet_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension;
            ndpidsFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return ndpidsFile;
    }

    public final Path getPid2cidsFile() throws IOException,
            ClassNotFoundException {
        if (pid2cidsFile == null) {
            String filename = "PersonIDToClaimIDsLookup_"
                    + "HashMap_SHBE_PersonID__HashSet_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension;
            pid2cidsFile = Paths.get(
                    files.getGeneratedSHBEDir().toString(), filename);
        }
        return pid2cidsFile;
    }

    /**
     * If {@code getData().get(YM3) != null} then return it. Otherwise try to
     * load it from file and return it. Failing that return {@code null}.
     *
     * @param ym3 The year and month of records to get.
     * @param hoome
     * @return
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_Records getRecords(UKP_YM3 ym3, boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getRecords(ym3);
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                env.clearAllData();
                SHBE_Records r = getRecords(ym3, hoome);
                env.initMemoryReserve(env.env);
                return r;
            } else {
                throw e;
            }
        }
    }

    /**
     * If {@code getData().get(YM3) != null} then return it.Otherwise try to
     * load it from file and return it.Failing that return {@code null}.
     *
     * @param ym3 The year and month of records to get.
     * @return ShBE Records
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected SHBE_Records getRecords(UKP_YM3 ym3) throws IOException,
            ClassNotFoundException {
        SHBE_Records r = getData().get(ym3);
        if (r == null) {
            Path f = getFile(ym3);
            if (Files.exists(f)) {
                r = (SHBE_Records) Generic_IO.readObject(f);
                r.env = env;
                return r;
            }
        }
        return r;
    }

    protected Path getDir(UKP_YM3 ym3) throws IOException {
        return Paths.get(files.getGeneratedSHBEDir().toString(), ym3.toString());
    }

    protected Path getFile(UKP_YM3 ym3) throws IOException {
        Path dir = getDir(ym3);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
        return Paths.get(dir.toString(), SHBE_Strings.s_Records
                + SHBE_Strings.s_BinaryFileExtension);
    }

    /**
     * Clears all SHBE_Records in data from fast access memory.
     *
     * @return The number of SHBE_Records cleared.
     */
    public int clearAll() {
        String methodName = "clearAll";
        env.env.logStartTag(methodName);
        int r = 0;
        Iterator<UKP_YM3> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            SHBE_Records recs = data.get(ite.next());
            if (recs != null) {
                recs = null; // Set to null to release resources.
                r++;
            }
        }
        env.env.logEndTag(methodName);
        return r;
    }

    /**
     * Clears all SHBE_Records in data from fast access memory with the
     * exception of that for YM3.
     *
     * @param ym3 The Year_Month key of the SHBE_Records not to be cleared from
     * fast access memory.
     * @return The number of SHBE_Records cleared.
     */
    public int clearAllExcept(UKP_YM3 ym3) {
        int r = 0;
        Iterator<UKP_YM3> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            UKP_YM3 y = ite.next();
            if (!y.equals(ym3)) {
                SHBE_Records recs = data.get(y);
                if (recs != null) {
                    recs = null;
                    r++;
                }
            }
        }
        return r;
    }

    /**
     * Clears some SHBE_Records in data from fast access memory.
     *
     * @return true iff some SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSome() {
        Iterator<UKP_YM3> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            SHBE_Records recs = data.get(ite.next());
            if (recs != null) {
                recs = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Clears some SHBE_Records in data from fast access memory except the
     * SHBE_Records in data indexed by YM3.
     *
     * @param ym3
     * @return true iff some SHBE_Records were cleared and false otherwise.
     */
    public boolean clearSomeExcept(UKP_YM3 ym3) {
        Iterator<UKP_YM3> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            UKP_YM3 y = ite.next();
            if (!ym3.equals(y)) {
                SHBE_Records recs = data.get(y);
                if (recs != null) {
                    recs = null;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Clears SHBE_Records for YM3 in data from fast access memory.
     *
     * @param ym3 The Year_Month key for accessing the SHBE_Records to be
     * cleared from fast access memory.
     * @return true iff the data were cleared and false otherwise (when the data
     * is already cleared).
     */
    public boolean clear(UKP_YM3 ym3) {
        SHBE_Records recs = data.get(ym3);
        if (recs != null) {
            recs = null;
            return true;
        }
        return false;
    }

    public void writeLookups() throws IOException, ClassNotFoundException {
        Generic_IO.writeObject(getCid2c(),
                getCid2cFile());
        Generic_IO.writeObject(getC2cid(),
                getC2cidFile());
        Generic_IO.writeObject(getN2nid(),
                getN2nidFile());
        Generic_IO.writeObject(getNINOIDToNINOLookup(),
                getNid2nFile());
        Generic_IO.writeObject(getD2did(),
                getD2didFile());
        Generic_IO.writeObject(getDid2d(),
                getDid2dFile());
        Generic_IO.writeObject(getPostcodeToPostcodeIDLookup(),
                getP2pidFile());
        Generic_IO.writeObject(getPid2p(),
                getPid2pFile());
        Generic_IO.writeObject(getPid2point(),
                getPid2pointFile());
        Generic_IO.writeObject(getClaimantPersonIDs(),
                getClaimantPersonIDsFile());
        Generic_IO.writeObject(getPartnerPersonIDs(),
                getPartnerPersonIDsFile());
        Generic_IO.writeObject(getNonDependentPersonIDs(),
                getNdpidsFile());
        Generic_IO.writeObject(SHBE_Handler.this.getPid2cids(),
                getPid2cidsFile());
    }

    /**
     * For loading in new SHBE data
     *
     */
    public void runNew() throws IOException, Exception {
        Path dir = env.files.getInputSHBEDir();
        // Ascertain which files are new and need loading
        String[] SHBEFilenames = getFilenames();
        ArrayList<String> newFilesToRead = new ArrayList<>();
        // Formatted/loaded SHBE files.
        Set<Path> ff = Files.list(files.getGeneratedSHBEDir()).collect(Collectors.toSet());
        // Formatted Year Month
        HashSet<UKP_YM3> fym3s = new HashSet<>();
        for (Path f : ff) {
            if (Files.isDirectory(f)) {
                fym3s.add(new UKP_YM3(f.getFileName().toString()));
            }
        }
        for (String SHBEFilename : SHBEFilenames) {
            if (!fym3s.contains(getYM3(SHBEFilename))) {
                newFilesToRead.add(SHBEFilename);
            }
        }
        UKP_YM3 ym30 = getYM3(SHBEFilenames[SHBEFilenames.length - 1]);
        UKP_YM3 nYM3 = pData.getNearestYM3ForONSPDLookup(ym30);
        if (newFilesToRead.size() > 0) {
            Iterator<String> ite = newFilesToRead.iterator();
            while (ite.hasNext()) {
                String SHBEFilename = ite.next();
                SHBE_Records recs = new SHBE_Records(env, logID, dir,
                        SHBEFilename, nYM3);
                Generic_IO.writeObject(recs, recs.getFile());
            }
            writeLookups();
        }
        // Make a backup copy - this needs testing
        Path f = Paths.get(files.getGeneratedLCCDir().toString(), "SHBEBackup");
        Generic_FileStore fs;
        if (Files.isDirectory(f)) {
            fs = new Generic_FileStore(f);
        } else {
            fs = new Generic_FileStore(f.getParent(), "SHBEBackup");
            fs.addDir();
        }
        Generic_IO.copy(files.getGeneratedSHBEDir(), fs.getHighestLeaf());
    }

    /**
     * For checking postcodes.
     *
     */
    public void runPostcodeCheckLatest() throws IOException, ClassNotFoundException, Exception {
        boolean hoome;
        hoome = true;

        // Declaration
        Map<UKP_YM3, Map<UKP_RecordID, ONSPD_Point>> PostcodeIDPointLookups;
        String YMN;
        String[] SHBEFilenames;
        String SHBEFilename1;

        boolean modifiedAnyRecs;
        String h;

        // Initialisation
        p2pid = getPostcodeToPostcodeIDLookup();
        PostcodeIDPointLookups = getPid2point();
        cid2c = getCid2c();

        modifiedAnyRecs = false;

        // Prepare for output
        SHBEFilenames = getFilenames();
        SHBEFilename1 = SHBEFilenames[SHBEFilenames.length - 1];
        YMN = getYearMonthNumber(SHBEFilename1);
        UKP_YM3 YM31;
        YM31 = getYM3(SHBEFilename1);
        System.out.println("YM31 " + YM31);
        // Nearest YM3 For ONSPD Lookup YM31
        UKP_YM3 nyfoly1 = pData.getNearestYM3ForONSPDLookup(YM31);
        System.out.println("NearestYM3ForONSPDLookupYM31 " + nyfoly1);
        SHBE_Records s1 = new SHBE_Records(env, logID, YM31);
        Map<SHBE_ClaimID, SHBE_Record> recs1 = s1.getRecords(hoome);
        SHBE_Record rec1;
        Map<UKP_RecordID, ONSPD_Point> PostcodeIDToPointLookup1
                = PostcodeIDPointLookups.get(nyfoly1);
        // Unique Unmappable Postcodes
        HashSet<String> uup = new HashSet<>();
        // Claimant Postcodes Unmappable
        Map<SHBE_ClaimID, String> cpu = s1.getClaimantPostcodesUnmappable();
        SHBE_ClaimID claimID;
        Iterator<SHBE_ClaimID> ite;
        String claimRef;
        ite = cpu.keySet().iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            claimRef = cid2c.get(claimID);
            uup.add(claimRef + "," + cpu.get(claimID));
        }
        // Unique Modified Postcodes
        HashSet<String> ump = new HashSet<>();
        writeOutModifiedPostcodes(ump, YMN, s1, hoome);

        /**
         * Set up log to write out some basic details of Claims with Claimant
         * Postcodes that are not yet mappable by any means.
         */
        int logIDUP = env.env.initLog("UnmappablePostcodes" + YMN, ".csv");
        h = "Ref,Year_Month,ClaimRef,Recorded Postcode,Correct Postcode,"
                + "Input To Academy (Y/N)";
        env.env.log(h, logIDUP);
        int ref2 = 1;

        UKP_YM3 YM30;
        // Nearest YM3 For ONSPD Lookup YM30
        UKP_YM3 nyfolYM30;
        // Claimant Postcodes Unmappable 0
        Map<SHBE_ClaimID, String> cpu0;
        SHBE_Records s0;
        Map<SHBE_ClaimID, SHBE_Record> recs0;
        SHBE_Record rec0;
        String postcode0;
        String postcode1;
        String postcodef0;
        String unmappablePostcodef0;
        String postcodef1;

        Map<UKP_RecordID, ONSPD_Point> PostcodeIDToPointLookup0;
        Map<SHBE_ClaimID, UKP_RecordID> ClaimIDToPostcodeIDLookup0 = null;
        Set<SHBE_ClaimID> ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = null;

        //for (int i = SHBEFilenames.length - 2; i >= 0; i--) {
        int i = SHBEFilenames.length - 2;
        // Get previous SHBE.
        YM30 = getYM3(SHBEFilenames[i]);
        System.out.println("YM30 " + YM30);
        YMN = getYearMonthNumber(SHBEFilenames[i]);
        int logID2 = env.env.initLog("FutureModifiedPostcodes" + YMN, ".csv");
        h = "ClaimRef,Original Claimant Postcode,Updated from the "
                + "Future Claimant Postcode";
        env.env.log(h, logID2);
        nyfolYM30 = pData.getNearestYM3ForONSPDLookup(YM30);
        env.env.log("NearestYM3ForONSPDLookupYM30 " + nyfolYM30);
        s0 = new SHBE_Records(env, logID, YM30);
        recs0 = s0.getRecords(hoome);
        writeOutModifiedPostcodes(ump, YMN, s0, hoome);
        PostcodeIDToPointLookup0 = PostcodeIDPointLookups.get(nyfolYM30);
        cpu0 = s0.getClaimantPostcodesUnmappable(hoome);
        boolean modifiedRecs = false;
        ite = cpu0.keySet().iterator();
        HashSet<SHBE_ClaimID> ClaimantPostcodesUnmappable0Remove = new HashSet<>();
        while (ite.hasNext()) {
            claimID = ite.next();
            unmappablePostcodef0 = cpu0.get(claimID);
            claimRef = cid2c.get(claimID);
            System.out.println(claimRef);
            rec1 = recs1.get(claimID);
            rec0 = recs0.get(claimID);
            postcodef0 = rec0.getClaimPostcodeF();
            postcode0 = rec0.getDRecord().getClaimantsPostcode();
            if (rec1 != null) {
                postcodef1 = rec1.getClaimPostcodeF();
                if (rec1.isClaimPostcodeFMappable()) {
                    env.env.log("Claimants Postcode 0 \"" + postcode0
                            + "\" unmappablePostcodef0 \"" + unmappablePostcodef0
                            + "\" postcodef0 \"" + postcodef0 + "\" changed to "
                            + postcodef1 + " which is mappable.");
                    if (!rec0.ClaimPostcodeFValidPostcodeFormat) {
                        rec0.ClaimPostcodeFUpdatedFromTheFuture = true;
                        rec0.ClaimPostcodeF = postcodef1;
                        rec0.ClaimPostcodeFMappable = true;
                        rec0.ClaimPostcodeFValidPostcodeFormat = true;
                        if (ClaimIDToPostcodeIDLookup0 == null) {
                            ClaimIDToPostcodeIDLookup0 = s0.getClaimIDToPostcodeIDLookup();
                        }
                        ClaimIDToPostcodeIDLookup0.put(claimID, p2pid.get(postcodef1));
                        if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 == null) {
                            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = s0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
                        }
                        ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0.add(claimID);
                        UKP_RecordID postcodeID;
                        postcodeID = ClaimIDToPostcodeIDLookup0.get(claimID);
                        ONSPD_Point p;
                        p = PostcodeIDToPointLookup1.get(postcodeID);
                        PostcodeIDToPointLookup0.put(postcodeID, p);
                        modifiedRecs = true;
                        modifiedAnyRecs = true;
                        postcode1 = postcodef1.replaceAll(" ", "");
                        postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
                        env.env.log(claimRef + "," + postcode0 + "," + postcode1, logID);
                        ClaimantPostcodesUnmappable0Remove.add(claimID);
                    }
                } else {
                    System.out.println("postcodef1 " + postcodef1 + " is not mappable.");
//                        postcode1 = postcodef1.replaceAll(" ", "");
//                        if (postcode1.length() > 3) {
//                        postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
//                        } else {
//                            postcodef1 = rec1.getClaimPostcodeF();
//                        }
                    postcode1 = rec1.getDRecord().getClaimantsPostcode();
                    uup.add(claimRef + "," + postcode1 + ",,");
                    env.env.log("" + ref2 + "," + YM31 + "," + claimRef + ","
                            + postcode1 + ",,", logIDUP);
                    ref2++;
//                        System.out.println("postcodef1 " + postcodef1 + " is not mappable.");
//                        UniqueUnmappablePostcodes.add(ClaimRef + "," + postcode0 + ",,");
//                        pw2.println("" + ref2 + "," + YM30 + "," + ClaimRef + "," + postcode0 + ",,");
//                        ref2++;
                }
            }
            // Prepare for next iteration
            recs0 = recs1;
        }
        ite = ClaimantPostcodesUnmappable0Remove.iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            cpu0.remove(claimID);
        }
        if (modifiedRecs == true) {
            // Write out recs0
            Generic_IO.writeObject(cpu0,
                    s0.getClaimantPostcodesUnmappableFile());
            Generic_IO.writeObject(ClaimIDToPostcodeIDLookup0,
                    s0.getCid2postcodeIDFile());
            Generic_IO.writeObject(recs0, s0.getRecordsFile());
            Generic_IO.writeObject(ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0,
                    s0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile());
        }
        env.env.closeLog(logIDUP);

        // Write out UniqueUnmappablePostcodes
        h = "ClaimRef,Original Claimant Postcode,Modified Claimant Postcode,"
                + "Input To Academy (Y/N)";
        writeLog("UniqueUnmappablePostcodes", ".csv", h, uup);

        // Write out UniqueModifiedPostcodes
        writeLog("UniqueModifiedPostcodes", ".csv", h, ump);

        // Write out PostcodeIDPointLookups
        if (modifiedAnyRecs == true) {
            Generic_IO.writeObject(PostcodeIDPointLookups,
                    getPid2pointFile());
        }
    }

    /**
     * Write out log.
     *
     * @param n The name of the log to be written.
     * @param e The extension for the filename to be written.
     * @param h The header.
     * @param l The lines.
     * @throws java.io.IOException If encountered.
     */
    protected void writeLog(String n, String e, String h, Collection<String> l)
            throws IOException, Exception {
        int logID2 = env.env.initLog(n, e);
        env.env.log(h, logID2, true);
        env.env.log(l, logID2, true);
        env.env.closeLog(logID2);
    }

    /**
     * For checking postcodes.
     *
     * @throws java.io.IOException If encountered.
     */
    public void runPostcodeCheck() throws IOException, Exception {
        boolean hoome;
        hoome = true;
        // Declaration
        Map<UKP_YM3, Map<UKP_RecordID, ONSPD_Point>> PostcodeIDPointLookups;
        String[] SHBEFilenames;
        String SHBEFilename1;
        String YMN;
        UKP_YM3 YM31;
        // Nearest YM3 For ONSPD Lookup YM31
        UKP_YM3 nyol;
        SHBE_Records SHBE_Records1;
        Map<SHBE_ClaimID, SHBE_Record> recs1;
        SHBE_Record rec1;
        Map<UKP_RecordID, ONSPD_Point> PostcodeIDToPointLookup1;
        Set<String> UniqueUnmappablePostcodes;
        Map<SHBE_ClaimID, String> ClaimantPostcodesUnmappable;
        SHBE_ClaimID claimID;
        Iterator<SHBE_ClaimID> ite;
        String claimRef;
        // UniqueModifiedPostcodes
        HashSet<String> ump;
        String h;
        boolean modifiedAnyRecs;

        // Initialisation
        p2pid = getPostcodeToPostcodeIDLookup();
        PostcodeIDPointLookups = getPid2point();
        cid2c = getCid2c();
        SHBEFilenames = getFilenames();
        SHBEFilename1 = SHBEFilenames[SHBEFilenames.length - 1];
        YMN = getYearMonthNumber(SHBEFilename1);
        YM31 = getYM3(SHBEFilename1);
//        System.out.println("YM31 " + YM31);
        nyol = pData.getNearestYM3ForONSPDLookup(YM31);
//        System.out.println("NearestYM3ForONSPDLookupYM31 "
//                + NearestYM3ForONSPDLookupYM31);
        SHBE_Records1 = new SHBE_Records(env, logID, YM31);
        recs1 = SHBE_Records1.getRecords(hoome);
        PostcodeIDToPointLookup1 = PostcodeIDPointLookups.get(nyol);
        UniqueUnmappablePostcodes = new HashSet<>();
        ClaimantPostcodesUnmappable = SHBE_Records1.getClaimantPostcodesUnmappable();
        ump = new HashSet<>();
        modifiedAnyRecs = false;

        // Add to UniqueUnmappablePostcodes
        ite = ClaimantPostcodesUnmappable.keySet().iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            claimRef = cid2c.get(claimID);
            UniqueUnmappablePostcodes.add(claimRef + ","
                    + ClaimantPostcodesUnmappable.get(claimID));
        }

        /**
         * Write out some basic details of Claims with Claimant Postcodes that
         * are not yet mappable by any means.
         */
        int ref2 = 1;
        // More declaration
        UKP_YM3 YM30;
        UKP_YM3 NearestYM3ForONSPDLookupYM30;
        Map<SHBE_ClaimID, String> ClaimantPostcodesUnmappable0;
        SHBE_Records SHBE_Records0;
        Map<SHBE_ClaimID, SHBE_Record> recs0;
        SHBE_Record rec0;
        String postcode0;
        String postcode1;
        String postcodef0;
        String unmappablePostcodef0;
        String postcodef1;

        Map<UKP_RecordID, ONSPD_Point> PostcodeIDToPointLookup0;
        Map<SHBE_ClaimID, UKP_RecordID> ClaimIDToPostcodeIDLookup0 = null;
        Set<SHBE_ClaimID> ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = null;

        for (int i = SHBEFilenames.length - 2; i >= 0; i--) {
            // Get previous SHBE.
            YM30 = getYM3(SHBEFilenames[i]);
            env.env.log("YM30 " + YM30);
            YMN = getYearMonthNumber(SHBEFilenames[i]);
            int logID2 = env.env.initLog("FutureModifiedPostcodes" + YMN, ".csv");
            h = "ClaimRef,Original Claimant Postcode,Updated from the Future "
                    + "Claimant Postcode";
            env.env.log(h, logID2);
            NearestYM3ForONSPDLookupYM30 = pData.getNearestYM3ForONSPDLookup(YM30);
            System.out.println("NearestYM3ForONSPDLookupYM30 " + NearestYM3ForONSPDLookupYM30);
            SHBE_Records0 = new SHBE_Records(env, YM30);
            recs0 = SHBE_Records0.getRecords(hoome);
            // <writeOutModifiedPostcodes>
            writeOutModifiedPostcodes(ump, YMN,
                    SHBE_Records0, hoome);
            // </writeOutModifiedPostcodes>
            PostcodeIDToPointLookup0 = PostcodeIDPointLookups.get(NearestYM3ForONSPDLookupYM30);
            // Get previously unmappable postcodes
            ClaimantPostcodesUnmappable0 = SHBE_Records0.getClaimantPostcodesUnmappable(hoome);
            boolean modifiedRecs = false;
            ite = ClaimantPostcodesUnmappable0.keySet().iterator();
            Set<SHBE_ClaimID> ClaimantPostcodesUnmappable0Remove = new HashSet<>();
            while (ite.hasNext()) {
                claimID = ite.next();
                unmappablePostcodef0 = ClaimantPostcodesUnmappable0.get(claimID);
                claimRef = cid2c.get(claimID);
                //System.out.println(ClaimRef);
                rec1 = recs1.get(claimID);
                rec0 = recs0.get(claimID);
                postcodef0 = rec0.getClaimPostcodeF();
                postcode0 = rec0.getDRecord().getClaimantsPostcode();
                if (rec1 != null) {
                    postcodef1 = rec1.getClaimPostcodeF();
                    if (rec1.isClaimPostcodeFMappable()) {
//                        System.out.println("Claimants Postcode 0 \"" 
//                                + postcode0 + "\" unmappablePostcodef0 \"" 
//                                + unmappablePostcodef0 + "\" postcodef0 \"" 
//                                + postcodef0 + "\" changed to " + postcodef1 
//                                + " which is mappable.");
                        if (!rec0.ClaimPostcodeFValidPostcodeFormat) {
                            rec0.ClaimPostcodeFUpdatedFromTheFuture = true;
                            rec0.ClaimPostcodeF = postcodef1;
                            rec0.ClaimPostcodeFMappable = true;
                            rec0.ClaimPostcodeFValidPostcodeFormat = true;
                            if (ClaimIDToPostcodeIDLookup0 == null) {
                                ClaimIDToPostcodeIDLookup0 = SHBE_Records0.getClaimIDToPostcodeIDLookup();
                            }
                            UKP_RecordID postcodeID = p2pid.get(postcodef1);
                            ClaimIDToPostcodeIDLookup0.put(claimID, postcodeID);
                            if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 == null) {
                                ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = SHBE_Records0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
                            }
                            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0.add(claimID);
                            ONSPD_Point p;
                            p = PostcodeIDToPointLookup1.get(postcodeID);
                            PostcodeIDToPointLookup0.put(postcodeID, p);
                            modifiedRecs = true;
                            modifiedAnyRecs = true;
                            postcode1 = postcodef1.replaceAll(" ", "");
                            postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
                            env.env.log(claimRef + "," + postcode0 + "," + postcode1, logID2);
                            ClaimantPostcodesUnmappable0Remove.add(claimID);
                        }
                    } else {
                        env.env.log("postcodef1 " + postcodef1 + " is not mappable.");
//                        postcode1 = postcodef1.replaceAll(" ", "");
//                        if (postcode1.length() > 3) {
//                        postcode1 = postcode1.substring(0, postcode1.length() - 3) + " " + postcode1.substring(postcode1.length() - 3);
//                        } else {
//                            postcodef1 = rec1.getClaimPostcodeF();
//                        }
                        postcode1 = rec1.getDRecord().getClaimantsPostcode();
                        UniqueUnmappablePostcodes.add(claimRef + "," + postcode1 + ",,");
                        env.env.log("" + ref2 + "," + YM31 + "," + claimRef
                                + "," + postcode1 + ",,", logID2);
                        ref2++;
//                        System.out.println("postcodef1 " + postcodef1 + " is not mappable.");
//                        UniqueUnmappablePostcodes.add(ClaimRef + "," + postcode0 + ",,");
//                        pw2.println("" + ref2 + "," + YM30 + "," + ClaimRef + "," + postcode0 + ",,");
//                        ref2++;
                    }
                }
            }
            Iterator<SHBE_ClaimID> ite2;
            ite2 = ClaimantPostcodesUnmappable0Remove.iterator();
            while (ite2.hasNext()) {
                claimID = ite2.next();
                ClaimantPostcodesUnmappable0.remove(claimID);
            }
            if (modifiedRecs == true) {
                // Write out recs0
                Generic_IO.writeObject(ClaimantPostcodesUnmappable0,
                        SHBE_Records0.getClaimantPostcodesUnmappableFile());
                Generic_IO.writeObject(ClaimIDToPostcodeIDLookup0,
                        SHBE_Records0.getCid2postcodeIDFile());
                Generic_IO.writeObject(recs0, SHBE_Records0.getRecordsFile());
                Generic_IO.writeObject(ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0,
                        SHBE_Records0.getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile());
            }
            // Prepare for next iteration
            recs1 = recs0;
            ClaimIDToPostcodeIDLookup0 = null;
            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture0 = null;
            env.env.closeLog(logID2);

            // Write out UniqueUnmappablePostcodes
            h = "ClaimRef,Original Claimant Postcode,Modified Claimant Postcode,"
                    + "Input To Academy (Y/N)";
            writeLog("UniqueUnmappablePostcodes", ".csv", h, UniqueUnmappablePostcodes);

            // Write out UniqueModifiedPostcodes
            writeLog("UniqueModifiedPostcodes", ".csv", h, ump);

            // Write out PostcodeIDPointLookups
            if (modifiedAnyRecs == true) {
                Generic_IO.writeObject(PostcodeIDPointLookups,
                        getPid2pointFile());
            }
        }
    }

    /**
     * Set up a PrintWriter to write out some details of claims with claimant
     * postcodes that are automatically modified in order that they are
     * mappable. The formatting may involve removing any Non A-Z, a-z or 0-9
     * characters. It may also involve changing a "O" to a "0" in the second
     * part of the postcode where a number is expected. And it may also involve
     * removing an additional "0" in the first part of the postcode for example
     * where "LS06" should be "LS6".
     *
     * @param ump Unique Modified Postcodes.
     * @param ymn YearMonthNumber.
     * @param records
     * @param hoome
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected void writeOutModifiedPostcodes(Set<String> ump, String ymn,
            SHBE_Records records, boolean hoome) throws IOException,
            ClassNotFoundException, Exception {
        int ref;
        Map<SHBE_ClaimID, String[]> ClaimantPostcodesModified;
        Iterator<SHBE_ClaimID> ite;
        SHBE_ClaimID claimID;
        String[] postcodes;
        String claimRef;
        int logID2 = env.env.initLog("ModifiedPostcodes" + ymn, ".csv");
        String s;
        s = "Ref,ClaimRef,Recorded Postcode,Modified Postcode,Input To Academy (Y/N)";
        env.env.log(s);
        ref = 1;
        ClaimantPostcodesModified = records.getClaimantPostcodesModified(hoome);
        ite = ClaimantPostcodesModified.keySet().iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            postcodes = ClaimantPostcodesModified.get(claimID);
            claimRef = cid2c.get(claimID);
            s = claimRef + "," + postcodes[0] + "," + postcodes[1] + ",";
            env.env.log("" + ref + "," + s);
            ump.add(s);
            ref++;
        }
        env.env.closeLog(logID2);
    }

    /**
     *
     * @param SHBE_Records
     * @param PT
     * @return
     *
     */
    public Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDate(
            SHBE_Records SHBE_Records, String PT) throws IOException,
            ClassNotFoundException {
        Set<SHBE_ClaimID> r = null;
        if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeAll)) {
            r = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateInPayment();
            r.addAll(SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateSuspended());
            r.addAll(SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateOther());
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeIn)) {
            r = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateInPayment();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeSuspended)) {
            r = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateSuspended();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeOther)) {
            r = SHBE_Records.getClaimIDsWithStatusOfHBAtExtractDateOther();
        }
        return r;
    }

    /**
     *
     * @param SHBE_Records
     * @param PT
     * @return
     */
    public Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDate(
            SHBE_Records SHBE_Records, String PT) throws IOException,
            ClassNotFoundException {
        Set<SHBE_ClaimID> r = null;
        if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeAll)) {
            r = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateInPayment();
            r.addAll(SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateSuspended());
            r.addAll(SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateOther());
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeIn)) {
            r = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateInPayment();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeSuspended)) {
            r = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateSuspended();
        } else if (PT.equalsIgnoreCase(SHBE_Strings.s_PaymentTypeOther)) {
            r = SHBE_Records.getClaimIDsWithStatusOfCTBAtExtractDateOther();
        }
        return r;
    }

    public String getClaimantType(SHBE_D_Record D_Record) {
        if (isHBClaim(D_Record)) {
            return SHBE_Strings.s_HB;
        }
        //if (isCTBOnlyClaim(D_Record)) {
        return SHBE_Strings.s_CTB;
        //}
    }

    public ArrayList<String> getClaimantTypes() {
        ArrayList<String> result;
        result = new ArrayList<>();
        result.add(SHBE_Strings.s_HB);
        result.add(SHBE_Strings.s_CTB);
        return result;
    }

    public boolean isCTBOnlyClaimOtherPT(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfCTBClaimAtExtractDate() == 0) {
            return isCTBOnlyClaim(D_Record);
        }
        return false;
    }

    public boolean isCTBOnlyClaimSuspended(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfCTBClaimAtExtractDate() == 2) {
            return isCTBOnlyClaim(D_Record);
        }
        return false;
    }

    public boolean isCTBOnlyClaimInPayment(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfCTBClaimAtExtractDate() == 1) {
            return isCTBOnlyClaim(D_Record);
        }
        return false;
    }

    public boolean isCTBOnlyClaim(SHBE_D_Record D_Record) {
        if (D_Record == null) {
            return false;
        }
        int TT;
        TT = D_Record.getTenancyType();
        return isCTBOnlyClaim(
                TT);
    }

    /**
     * @param TT
     * @return
     */
    public boolean isCTBOnlyClaim(
            int TT) {
        return TT == 5 || TT == 7;
    }

    public boolean isHBClaimOtherPT(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfHBClaimAtExtractDate() == 0) {
            return isHBClaim(D_Record);
        }
        return false;
    }

    public boolean isHBClaimSuspended(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfHBClaimAtExtractDate() == 2) {
            return isHBClaim(D_Record);
        }
        return false;
    }

    public boolean isHBClaimInPayment(SHBE_D_Record D_Record) {
        if (D_Record.getStatusOfHBClaimAtExtractDate() == 1) {
            return isHBClaim(D_Record);
        }
        return false;
    }

    public boolean isHBClaim(SHBE_D_Record D_Record) {
        if (D_Record == null) {
            return false;
        }
        int TT;
        TT = D_Record.getTenancyType();
        return isHBClaim(TT);
    }

    public boolean isHBClaim(int TT) {
        if (TT == 5) {
            return false;
        }
        if (TT == 7) {
            return false;
        }
        //return TT > -1 && TT < 10;
        return TT > 0 && TT < 10;
    }

    public Set<String> getRecordTypes() {
        return types;
    }

    /**
     * Initialises types
     */
    public final void initRecordTypes() {
        if (types == null) {
            types = new HashSet<>();
            types.add("A");
            types.add("D");
            types.add("C");
            types.add("R");
            types.add("T");
            types.add("P");
            types.add("G");
            types.add("E");
            types.add("S");
        }
    }

    Map<Integer, UKP_YM3> indexYM3s;

    /**
     *
     * @return Map with Integer indexes and ym3 values.
     * @throws java.io.IOException If encountered.
     */
    public Map<Integer, UKP_YM3> getIndexYM3s() throws IOException {
        if (indexYM3s == null) {
            indexYM3s = new HashMap<>();
            String[] fs = getFilenames();
            int i = 0;
            for (String f : fs) {
                indexYM3s.put(i, getYM3(f));
                i++;
            }
        }
        return indexYM3s;
    }

//    /**
//     *
//     * @param S
//     * @param StringToSHBE_IDLookup
//     * @param SHBE_IDToStringLookup
//     * @param list List to add result to if a new one is created.
//     * @return
//     */
//    public <T> T getIDAddIfNeeded(            String S,
//            Map<String, T> StringToSHBE_IDLookup,
//            Map<T, String> SHBE_IDToStringLookup,
//            ArrayList<T> list
//    ) {
//        T r;
//        if (StringToSHBE_IDLookup.containsKey(S)) {
//            r = StringToSHBE_IDLookup.get(S);
//        } else {
//            r = (T) new SHBE_ID(SHBE_IDToStringLookup.size());
//            SHBE_IDToStringLookup.put(r, S);
//            StringToSHBE_IDLookup.put(S, r);
//            list.add(r);
//        }
//        return r;
//    }
    /**
     * @param s National Insurance Number.
     * @return The ID for {@code s}
     */
    public SHBE_NINOID getNINOIDAddIfNeeded(String s) {
        SHBE_NINOID r;
        if (n2nid.containsKey(s)) {
            r = n2nid.get(s);
        } else {
            r = new SHBE_NINOID(nid2n.size());
            nid2n.put(r, s);
            n2nid.put(s, r);
        }
        return r;
    }

    /**
     *
     * @param S
     * @return
     */
    public SHBE_DOBID getDOBIDAddIfNeeded(String S) {
        SHBE_DOBID r;
        if (d2did.containsKey(S)) {
            r = d2did.get(S);
        } else {
            r = new SHBE_DOBID(did2d.size());
            did2d.put(r, S);
            d2did.put(S, r);
        }
        return r;
    }

    /**
     *
     * @param S
     * @return
     */
    public SHBE_ClaimID getClaimIDAddIfNeeded(String S) {
        SHBE_ClaimID r;
        if (c2cid.containsKey(S)) {
            r = c2cid.get(S);
        } else {
            r = new SHBE_ClaimID(cid2c.size());
            cid2c.put(r, S);
            c2cid.put(S, r);
        }
        return r;
    }

    /**
     * Only called when loading SHBE from source.
     *
     * @param ClaimRef
     * @param ClaimRefToClaimIDLookup
     * @param ClaimIDToClaimRefLookup
     * @param ClaimIDs
     * @param ClaimIDsOfNewSHBEClaims
     * @return
     * @throws java.lang.Exception
     */
    public SHBE_ClaimID getIDAddIfNeeded(
            String ClaimRef,
            Map<String, SHBE_ClaimID> ClaimRefToClaimIDLookup,
            Map<SHBE_ClaimID, String> ClaimIDToClaimRefLookup,
            Set<SHBE_ClaimID> ClaimIDs,
            Set<SHBE_ClaimID> ClaimIDsOfNewSHBEClaims
    ) throws Exception {
        SHBE_ClaimID result;
        if (ClaimRefToClaimIDLookup.containsKey(ClaimRef)) {
            result = ClaimRefToClaimIDLookup.get(ClaimRef);
        } else {
            result = new SHBE_ClaimID(ClaimIDToClaimRefLookup.size());
            ClaimIDToClaimRefLookup.put(result, ClaimRef);
            ClaimRefToClaimIDLookup.put(ClaimRef, result);
            if (ClaimIDs.contains(result)) {
                throw new Exception("DRecord already read for ClaimRef " + ClaimRef);
            }
            ClaimIDsOfNewSHBEClaims.add(result);
            ClaimIDs.add(result);
        }
        return result;
    }

    /**
     * Only called when loading SHBE from source.
     *
     * @param PostcodeF
     * @param PostcodeToPostcodeIDLookup
     * @param PostcodeIDToPostcodeLookup
     * @return
     */
    public UKP_RecordID getPostcodeIDAddIfNeeded(String PostcodeF,
            Map<String, UKP_RecordID> PostcodeToPostcodeIDLookup,
            Map<UKP_RecordID, String> PostcodeIDToPostcodeLookup) {
        UKP_RecordID r;
        if (PostcodeToPostcodeIDLookup.containsKey(PostcodeF)) {
            r = PostcodeToPostcodeIDLookup.get(PostcodeF);
        } else {
            r = new UKP_RecordID(PostcodeIDToPostcodeLookup.size());
//            if (IDToSLookup.size() > Integer.MAX_VALUE) {
//                throw new Error("LookupFiles are full!");
//            }
            PostcodeIDToPostcodeLookup.put(r, PostcodeF);
            PostcodeToPostcodeIDLookup.put(PostcodeF, r);
        }
        return r;
    }

    public SHBE_RecordAggregate aggregate(Set<SHBE_Record> records) {
        SHBE_RecordAggregate result = new SHBE_RecordAggregate();
        Iterator<SHBE_Record> ite = records.iterator();
        while (ite.hasNext()) {
            SHBE_Record rec;
            rec = ite.next();
            aggregate(rec, result);
        }
        return result;
    }

    public void aggregate(
            SHBE_Record record,
            SHBE_RecordAggregate a_Aggregate_SHBE_DataRecord) {
        SHBE_D_Record aDRecord;
        aDRecord = record.DRecord;
        a_Aggregate_SHBE_DataRecord.setTotalClaimCount(a_Aggregate_SHBE_DataRecord.getTotalClaimCount() + 1);
        //if (aDRecord.getHousingBenefitClaimReferenceNumber().length() > 2) {
        if (isHBClaim(aDRecord)) {
            a_Aggregate_SHBE_DataRecord.setTotalHBClaimCount(a_Aggregate_SHBE_DataRecord.getTotalHBClaimCount() + 1);
        } else {
            a_Aggregate_SHBE_DataRecord.setTotalCTBClaimCount(a_Aggregate_SHBE_DataRecord.getTotalCTBClaimCount() + 1);
        }
        if (aDRecord.getTenancyType() == 1) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType1Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType1Count() + 1);
        }
        if (aDRecord.getTenancyType() == 2) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType2Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType2Count() + 1);
        }
        if (aDRecord.getTenancyType() == 3) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType3Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType3Count() + 1);
        }
        if (aDRecord.getTenancyType() == 4) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType4Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType4Count() + 1);
        }
        if (aDRecord.getTenancyType() == 5) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType5Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType5Count() + 1);
        }
        if (aDRecord.getTenancyType() == 6) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType6Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType6Count() + 1);
        }
        if (aDRecord.getTenancyType() == 7) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType7Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType7Count() + 1);
        }
        if (aDRecord.getTenancyType() == 8) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType8Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType8Count() + 1);
        }
        if (aDRecord.getTenancyType() == 9) {
            a_Aggregate_SHBE_DataRecord.setTotalTenancyType9Count(a_Aggregate_SHBE_DataRecord.getTotalTenancyType9Count() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalNumberOfChildDependents(
                a_Aggregate_SHBE_DataRecord.getTotalNumberOfChildDependents()
                + aDRecord.getNumberOfChildDependents());
        a_Aggregate_SHBE_DataRecord.setTotalNumberOfNonDependents(
                a_Aggregate_SHBE_DataRecord.getTotalNumberOfNonDependents()
                + aDRecord.getNumberOfNonDependents());
//        ArrayList<SHBE_S_Record> tSRecords;
//        tSRecords = record.getSRecordsWithoutDRecords();
//        Iterator<SHBE_S_Record> ite;
//        ite = tSRecords.iterator();
//        while (ite.hasNext()) {
//            SHBE_S_Record aSRecord = ite.next();
//            if (aSRecord.getNonDependentStatus() == 0) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus0(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus0() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 1) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus1(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus1() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 2) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus2(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus2() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 3) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus3(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus3() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 4) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus4(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus4() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 5) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus5(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus5() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 6) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus6(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus6() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 7) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus7(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus7() + 1);
//            }
//            if (aSRecord.getNonDependentStatus() == 8) {
//                a_Aggregate_SHBE_DataRecord.setTotalNonDependentStatus8(
//                        a_Aggregate_SHBE_DataRecord.getTotalNonDependentStatus8() + 1);
//            }
//            a_Aggregate_SHBE_DataRecord.setTotalNonDependantGrossWeeklyIncomeFromRemunerativeWork(
//                    a_Aggregate_SHBE_DataRecord.getTotalNonDependantGrossWeeklyIncomeFromRemunerativeWork()
//                    + aSRecord.getNonDependantGrossWeeklyIncomeFromRemunerativeWork());
//        }
        if (aDRecord.getStatusOfHBClaimAtExtractDate() == 0) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfHBClaimAtExtractDate0(a_Aggregate_SHBE_DataRecord.getTotalStatusOfHBClaimAtExtractDate0() + 1);
        }
        if (aDRecord.getStatusOfHBClaimAtExtractDate() == 1) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfHBClaimAtExtractDate1(a_Aggregate_SHBE_DataRecord.getTotalStatusOfHBClaimAtExtractDate1() + 1);
        }
        if (aDRecord.getStatusOfHBClaimAtExtractDate() == 2) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfHBClaimAtExtractDate2(a_Aggregate_SHBE_DataRecord.getTotalStatusOfHBClaimAtExtractDate2() + 1);
        }
        if (aDRecord.getStatusOfCTBClaimAtExtractDate() == 0) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfCTBClaimAtExtractDate0(a_Aggregate_SHBE_DataRecord.getTotalStatusOfCTBClaimAtExtractDate0() + 1);
        }
        if (aDRecord.getStatusOfCTBClaimAtExtractDate() == 1) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfCTBClaimAtExtractDate1(a_Aggregate_SHBE_DataRecord.getTotalStatusOfCTBClaimAtExtractDate1() + 1);
        }
        if (aDRecord.getStatusOfCTBClaimAtExtractDate() == 2) {
            a_Aggregate_SHBE_DataRecord.setTotalStatusOfCTBClaimAtExtractDate2(a_Aggregate_SHBE_DataRecord.getTotalStatusOfCTBClaimAtExtractDate2() + 1);
        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 1) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim1(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim1() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 2) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim2(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim2() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 3) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim3(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim3() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 4) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim4(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim4() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 5) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim5(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim5() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentHBClaim() == 6) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim6(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim6() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 1) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim1(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim1() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 2) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim2(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim2() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 3) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim3(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim3() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 4) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim4(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim4() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 5) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim5(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim5() + 1);
//        }
//        if (aDRecord.getOutcomeOfFirstDecisionOnMostRecentCTBClaim() == 6) {
//            a_Aggregate_SHBE_DataRecord.setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim6(a_Aggregate_SHBE_DataRecord.getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim6() + 1);
//        }
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyHousingBenefitEntitlement(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyHousingBenefitEntitlement()
                + aDRecord.getWeeklyHousingBenefitEntitlement());
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyCouncilTaxBenefitEntitlement(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyCouncilTaxBenefitEntitlement()
                + aDRecord.getWeeklyCouncilTaxBenefitEntitlement());
        if (aDRecord.getLHARegulationsApplied().equalsIgnoreCase("NO")) { // A guess at the values: check!
            a_Aggregate_SHBE_DataRecord.setTotalLHARegulationsApplied0(
                    a_Aggregate_SHBE_DataRecord.getTotalLHARegulationsApplied0()
                    + 1);
        } else {
            //aSHBE_DataRecord.getLHARegulationsApplied() == 1
            a_Aggregate_SHBE_DataRecord.setTotalLHARegulationsApplied1(
                    a_Aggregate_SHBE_DataRecord.getTotalLHARegulationsApplied1()
                    + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyMaximumRent(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyMaximumRent()
                + aDRecord.getWeeklyMaximumRent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsAssessedIncomeFigure()
                + aDRecord.getClaimantsAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsAdjustedAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsAdjustedAssessedIncomeFigure()
                + aDRecord.getClaimantsAdjustedAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalCapital(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalCapital()
                + aDRecord.getClaimantsTotalCapital());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsGrossWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsGrossWeeklyIncomeFromEmployment()
                + aDRecord.getClaimantsGrossWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsNetWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsNetWeeklyIncomeFromEmployment()
                + aDRecord.getClaimantsNetWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsGrossWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsGrossWeeklyIncomeFromSelfEmployment()
                + aDRecord.getClaimantsGrossWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsNetWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsNetWeeklyIncomeFromSelfEmployment()
                + aDRecord.getClaimantsNetWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalAmountOfEarningsDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalAmountOfEarningsDisregarded()
                + aDRecord.getClaimantsTotalAmountOfEarningsDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded()
                + aDRecord.getClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromAttendanceAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromAttendanceAllowance()
                + aDRecord.getClaimantsIncomeFromAttendanceAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromAttendanceAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromAttendanceAllowance()
                + aDRecord.getClaimantsIncomeFromAttendanceAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromBusinessStartUpAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromBusinessStartUpAllowance()
                + aDRecord.getClaimantsIncomeFromBusinessStartUpAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromChildBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromChildBenefit()
                + aDRecord.getClaimantsIncomeFromChildBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent()
                + aDRecord.getClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromPersonalPension(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromPersonalPension()
                + aDRecord.getClaimantsIncomeFromPersonalPension());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromSevereDisabilityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromSevereDisabilityAllowance()
                + aDRecord.getClaimantsIncomeFromSevereDisabilityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromMaternityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromMaternityAllowance()
                + aDRecord.getClaimantsIncomeFromMaternityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromContributionBasedJobSeekersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromContributionBasedJobSeekersAllowance()
                + aDRecord.getClaimantsIncomeFromContributionBasedJobSeekersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStudentGrantLoan(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStudentGrantLoan()
                + aDRecord.getClaimantsIncomeFromStudentGrantLoan());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStudentGrantLoan(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStudentGrantLoan()
                + aDRecord.getClaimantsIncomeFromStudentGrantLoan());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromSubTenants(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromSubTenants()
                + aDRecord.getClaimantsIncomeFromSubTenants());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromBoarders(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromBoarders()
                + aDRecord.getClaimantsIncomeFromBoarders());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromTrainingForWorkCommunityAction(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromTrainingForWorkCommunityAction()
                + aDRecord.getClaimantsIncomeFromTrainingForWorkCommunityAction());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIncapacityBenefitShortTermLower(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIncapacityBenefitShortTermLower()
                + aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermLower());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIncapacityBenefitShortTermHigher(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIncapacityBenefitShortTermHigher()
                + aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermHigher());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIncapacityBenefitLongTerm(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIncapacityBenefitLongTerm()
                + aDRecord.getClaimantsIncomeFromIncapacityBenefitLongTerm());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromNewDeal50PlusEmploymentCredit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromNewDeal50PlusEmploymentCredit()
                + aDRecord.getClaimantsIncomeFromNewDeal50PlusEmploymentCredit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromNewTaxCredits(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromNewTaxCredits()
                + aDRecord.getClaimantsIncomeFromNewTaxCredits());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent()
                + aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceCareComponent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent()
                + aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromGovernemntTraining(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromGovernemntTraining()
                + aDRecord.getClaimantsIncomeFromGovernmentTraining());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit()
                + aDRecord.getClaimantsIncomeFromIndustrialInjuriesDisablementBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromCarersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromCarersAllowance()
                + aDRecord.getClaimantsIncomeFromCarersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStatutoryMaternityPaternityPay(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStatutoryMaternityPaternityPay()
                + aDRecord.getClaimantsIncomeFromStatutoryMaternityPaternityPay());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc()
                + aDRecord.getClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP()
                + aDRecord.getClaimantsIncomeFromWarDisablementPensionArmedForcesGIP());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWarMobilitySupplement(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWarMobilitySupplement()
                + aDRecord.getClaimantsIncomeFromWarMobilitySupplement());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWidowsWidowersPension(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWidowsWidowersPension()
                + aDRecord.getClaimantsIncomeFromWarWidowsWidowersPension());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromBereavementAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromBereavementAllowance()
                + aDRecord.getClaimantsIncomeFromBereavementAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWidowedParentsAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWidowedParentsAllowance()
                + aDRecord.getClaimantsIncomeFromWidowedParentsAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromYouthTrainingScheme(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromYouthTrainingScheme()
                + aDRecord.getClaimantsIncomeFromYouthTrainingScheme());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromStatuatorySickPay(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromStatuatorySickPay()
                + aDRecord.getClaimantsIncomeFromStatutorySickPay());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsOtherIncome(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsOtherIncome()
                + aDRecord.getClaimantsOtherIncome());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalAmountOfIncomeDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalAmountOfIncomeDisregarded()
                + aDRecord.getClaimantsTotalAmountOfIncomeDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalFamilyPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalFamilyPremiumAwarded()
                + aDRecord.getFamilyPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalFamilyLoneParentPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalFamilyLoneParentPremiumAwarded()
                + aDRecord.getFamilyLoneParentPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalDisabilityPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalDisabilityPremiumAwarded()
                + aDRecord.getDisabilityPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalSevereDisabilityPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalSevereDisabilityPremiumAwarded()
                + aDRecord.getSevereDisabilityPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalDisabledChildPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalDisabledChildPremiumAwarded()
                + aDRecord.getDisabledChildPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalCarePremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalCarePremiumAwarded()
                + aDRecord.getCarePremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalEnhancedDisabilityPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalEnhancedDisabilityPremiumAwarded()
                + aDRecord.getEnhancedDisabilityPremiumAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalBereavementPremiumAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalBereavementPremiumAwarded()
                + aDRecord.getBereavementPremiumAwarded());
        if (aDRecord.getPartnersStudentIndicator().equalsIgnoreCase("Y")) {
            a_Aggregate_SHBE_DataRecord.setTotalPartnersStudentIndicator(
                    a_Aggregate_SHBE_DataRecord.getTotalPartnersStudentIndicator() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalPartnersAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersAssessedIncomeFigure()
                + aDRecord.getPartnersAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersAdjustedAssessedIncomeFigure(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersAdjustedAssessedIncomeFigure()
                + aDRecord.getPartnersAdjustedAssessedIncomeFigure());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersGrossWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersGrossWeeklyIncomeFromEmployment()
                + aDRecord.getPartnersGrossWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersNetWeeklyIncomeFromEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersNetWeeklyIncomeFromEmployment()
                + aDRecord.getPartnersNetWeeklyIncomeFromEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersGrossWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersGrossWeeklyIncomeFromSelfEmployment()
                + aDRecord.getPartnersGrossWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersNetWeeklyIncomeFromSelfEmployment(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersNetWeeklyIncomeFromSelfEmployment()
                + aDRecord.getPartnersNetWeeklyIncomeFromSelfEmployment());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalAmountOfEarningsDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalAmountOfEarningsDisregarded()
                + aDRecord.getPartnersTotalAmountOfEarningsDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded()
                + aDRecord.getPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromAttendanceAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromAttendanceAllowance()
                + aDRecord.getPartnersIncomeFromAttendanceAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromBusinessStartUpAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromBusinessStartUpAllowance()
                + aDRecord.getPartnersIncomeFromBusinessStartUpAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromChildBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromChildBenefit()
                + aDRecord.getPartnersIncomeFromChildBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromPersonalPension(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromPersonalPension()
                + aDRecord.getPartnersIncomeFromPersonalPension());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromSevereDisabilityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromSevereDisabilityAllowance()
                + aDRecord.getPartnersIncomeFromSevereDisabilityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromMaternityAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromMaternityAllowance()
                + aDRecord.getPartnersIncomeFromMaternityAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromContributionBasedJobSeekersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromContributionBasedJobSeekersAllowance()
                + aDRecord.getPartnersIncomeFromContributionBasedJobSeekersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStudentGrantLoan(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStudentGrantLoan()
                + aDRecord.getPartnersIncomeFromStudentGrantLoan());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromSubTenants(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromSubTenants()
                + aDRecord.getPartnersIncomeFromSubTenants());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromBoarders(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromBoarders()
                + aDRecord.getPartnersIncomeFromBoarders());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromTrainingForWorkCommunityAction(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromTrainingForWorkCommunityAction()
                + aDRecord.getPartnersIncomeFromTrainingForWorkCommunityAction());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIncapacityBenefitShortTermLower(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIncapacityBenefitShortTermLower()
                + aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermLower());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIncapacityBenefitShortTermHigher(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIncapacityBenefitShortTermHigher()
                + aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermHigher());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIncapacityBenefitLongTerm(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIncapacityBenefitLongTerm()
                + aDRecord.getPartnersIncomeFromIncapacityBenefitLongTerm());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromNewDeal50PlusEmploymentCredit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromNewDeal50PlusEmploymentCredit()
                + aDRecord.getPartnersIncomeFromNewDeal50PlusEmploymentCredit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromNewTaxCredits(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromNewTaxCredits()
                + aDRecord.getPartnersIncomeFromNewTaxCredits());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromDisabilityLivingAllowanceCareComponent(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromDisabilityLivingAllowanceCareComponent()
                + aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceCareComponent());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent()
                + aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromGovernemntTraining(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromGovernemntTraining()
                + aDRecord.getPartnersIncomeFromGovernmentTraining());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromIndustrialInjuriesDisablementBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromIndustrialInjuriesDisablementBenefit()
                + aDRecord.getPartnersIncomeFromIndustrialInjuriesDisablementBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromCarersAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromCarersAllowance()
                + aDRecord.getPartnersIncomeFromCarersAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStatuatorySickPay(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStatuatorySickPay()
                + aDRecord.getPartnersIncomeFromStatutorySickPay());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStatutoryMaternityPaternityPay(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStatutoryMaternityPaternityPay()
                + aDRecord.getPartnersIncomeFromStatutoryMaternityPaternityPay());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc()
                + aDRecord.getPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWarDisablementPensionArmedForcesGIP(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWarDisablementPensionArmedForcesGIP()
                + aDRecord.getPartnersIncomeFromWarDisablementPensionArmedForcesGIP());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWarMobilitySupplement(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWarMobilitySupplement()
                + aDRecord.getPartnersIncomeFromWarMobilitySupplement());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWidowsWidowersPension(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWidowsWidowersPension()
                + aDRecord.getPartnersIncomeFromWarWidowsWidowersPension());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromBereavementAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromBereavementAllowance()
                + aDRecord.getPartnersIncomeFromBereavementAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWidowedParentsAllowance(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWidowedParentsAllowance()
                + aDRecord.getPartnersIncomeFromWidowedParentsAllowance());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromYouthTrainingScheme(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromYouthTrainingScheme()
                + aDRecord.getPartnersIncomeFromYouthTrainingScheme());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersOtherIncome(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersOtherIncome()
                + aDRecord.getPartnersOtherIncome());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalAmountOfIncomeDisregarded(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalAmountOfIncomeDisregarded()
                + aDRecord.getPartnersTotalAmountOfIncomeDisregarded());
        if (aDRecord.getClaimantsGender().equalsIgnoreCase("F")) {
            a_Aggregate_SHBE_DataRecord.setTotalClaimantsGenderFemale(
                    a_Aggregate_SHBE_DataRecord.getTotalClaimantsGenderFemale() + 1);
        }
        if (aDRecord.getClaimantsGender().equalsIgnoreCase("M")) {
            a_Aggregate_SHBE_DataRecord.setTotalClaimantsGenderMale(
                    a_Aggregate_SHBE_DataRecord.getTotalClaimantsGenderMale() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalContractualRentAmount(
                a_Aggregate_SHBE_DataRecord.getTotalContractualRentAmount()
                + aDRecord.getContractualRentAmount());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromPensionCreditSavingsCredit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromPensionCreditSavingsCredit()
                + aDRecord.getClaimantsIncomeFromPensionCreditSavingsCredit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromPensionCreditSavingsCredit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromPensionCreditSavingsCredit()
                + aDRecord.getPartnersIncomeFromPensionCreditSavingsCredit());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromMaintenancePayments(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromMaintenancePayments()
                + aDRecord.getClaimantsIncomeFromMaintenancePayments());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromMaintenancePayments(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromMaintenancePayments()
                + aDRecord.getPartnersIncomeFromMaintenancePayments());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromOccupationalPension(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromOccupationalPension()
                + aDRecord.getClaimantsIncomeFromOccupationalPension());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromOccupationalPension(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromOccupationalPension()
                + aDRecord.getPartnersIncomeFromOccupationalPension());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsIncomeFromWidowsBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsIncomeFromWidowsBenefit()
                + aDRecord.getClaimantsIncomeFromWidowsBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersIncomeFromWidowsBenefit(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersIncomeFromWidowsBenefit()
                + aDRecord.getPartnersIncomeFromWidowsBenefit());
        a_Aggregate_SHBE_DataRecord.setTotalTotalNumberOfRooms(
                a_Aggregate_SHBE_DataRecord.getTotalTotalNumberOfRooms()
                + aDRecord.getTotalNumberOfRooms());
        a_Aggregate_SHBE_DataRecord.setTotalValueOfLHA(
                a_Aggregate_SHBE_DataRecord.getTotalValueOfLHA()
                + aDRecord.getValueOfLHA());
        if (aDRecord.getPartnersGender().equalsIgnoreCase("F")) {
            a_Aggregate_SHBE_DataRecord.setTotalPartnersGenderFemale(
                    a_Aggregate_SHBE_DataRecord.getTotalPartnersGenderFemale() + 1);
        }
        if (aDRecord.getPartnersGender().equalsIgnoreCase("M")) {
            a_Aggregate_SHBE_DataRecord.setTotalPartnersGenderMale(
                    a_Aggregate_SHBE_DataRecord.getTotalPartnersGenderMale() + 1);
        }
        a_Aggregate_SHBE_DataRecord.setTotalTotalAmountOfBackdatedHBAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalTotalAmountOfBackdatedHBAwarded()
                + aDRecord.getTotalAmountOfBackdatedHBAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalTotalAmountOfBackdatedCTBAwarded(
                a_Aggregate_SHBE_DataRecord.getTotalTotalAmountOfBackdatedCTBAwarded()
                + aDRecord.getTotalAmountOfBackdatedCTBAwarded());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalCapital(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalCapital()
                + aDRecord.getPartnersTotalCapital());
        a_Aggregate_SHBE_DataRecord.setTotalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure(
                a_Aggregate_SHBE_DataRecord.getTotalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure()
                + aDRecord.getWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure());
        a_Aggregate_SHBE_DataRecord.setTotalClaimantsTotalHoursOfRemunerativeWorkPerWeek(
                a_Aggregate_SHBE_DataRecord.getTotalClaimantsTotalHoursOfRemunerativeWorkPerWeek()
                + aDRecord.getClaimantsTotalHoursOfRemunerativeWorkPerWeek());
        a_Aggregate_SHBE_DataRecord.setTotalPartnersTotalHoursOfRemunerativeWorkPerWeek(
                a_Aggregate_SHBE_DataRecord.getTotalPartnersTotalHoursOfRemunerativeWorkPerWeek()
                + aDRecord.getPartnersTotalHoursOfRemunerativeWorkPerWeek());
    }

    public long getHouseholdSize(SHBE_Record rec) {
        long result;
        result = 1;
        SHBE_D_Record D_Record;
        D_Record = rec.DRecord;
        result += D_Record.getPartnerFlag();
        int NumberOfChildDependents;
        NumberOfChildDependents = D_Record.getNumberOfChildDependents();
        int NumberOfNonDependents;
        NumberOfNonDependents = D_Record.getNumberOfNonDependents();
        int NumberOfDependentsAndNonDependents;
        NumberOfDependentsAndNonDependents = NumberOfChildDependents + NumberOfNonDependents;
        ArrayList<SHBE_S_Record> S_Records;
        S_Records = rec.SRecords;
        if (S_Records != null) {
            result += Math.max(NumberOfDependentsAndNonDependents, S_Records.size());
//            long NumberOfS_Records;
//            NumberOfS_Records = S_Records.size();
//            if (NumberOfS_Records != NumberOfNonDependents ) {
//                rec.init(env);
//                Iterator<SHBE_S_Record> ite;
//                ite = S_Records.iterator();
//                while (ite.hasNext()) {
//                    SHBE_S_Record S_Record;
//                    S_Record = ite.next();
//                }
//            }
        } else {
            result += NumberOfDependentsAndNonDependents;
        }
        return result;
    }

    public long getHouseholdSizeExcludingPartnerslong(SHBE_D_Record D_Record) {
        long result;
        result = 1;
        result += D_Record.getNumberOfChildDependents();
        long NumberOfNonDependents;
        NumberOfNonDependents = D_Record.getNumberOfNonDependents();
        result += NumberOfNonDependents;
        return result;
    }

    public int getHouseholdSizeExcludingPartnersint(SHBE_D_Record D_Record) {
        int result;
        result = 1;
        result += D_Record.getNumberOfChildDependents();
        long NumberOfNonDependents;
        NumberOfNonDependents = D_Record.getNumberOfNonDependents();
        result += NumberOfNonDependents;
        return result;
    }

    public long getHouseholdSize(SHBE_D_Record D_Record) {
        long result;
        result = getHouseholdSizeint(D_Record);
        return result;
    }

    public int getHouseholdSizeint(SHBE_D_Record D_Record) {
        int result;
        result = getHouseholdSizeExcludingPartnersint(D_Record);
        result += D_Record.getPartnerFlag();
        return result;
    }

    public long getClaimantsIncomeFromBenefitsAndAllowances(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromAttendanceAllowance();
        result += aDRecord.getClaimantsIncomeFromBereavementAllowance();
        result += aDRecord.getClaimantsIncomeFromBusinessStartUpAllowance();
        result += aDRecord.getClaimantsIncomeFromCarersAllowance();
        result += aDRecord.getClaimantsIncomeFromChildBenefit();
        result += aDRecord.getClaimantsIncomeFromContributionBasedJobSeekersAllowance();
        result += aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceCareComponent();
        result += aDRecord.getClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent();
        result += aDRecord.getClaimantsIncomeFromIncapacityBenefitLongTerm();
        result += aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermHigher();
        result += aDRecord.getClaimantsIncomeFromIncapacityBenefitShortTermLower();
        result += aDRecord.getClaimantsIncomeFromIndustrialInjuriesDisablementBenefit();
        result += aDRecord.getClaimantsIncomeFromMaternityAllowance();
        result += aDRecord.getClaimantsIncomeFromNewDeal50PlusEmploymentCredit();
        result += aDRecord.getClaimantsIncomeFromNewTaxCredits();
        result += aDRecord.getClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent();
        result += aDRecord.getClaimantsIncomeFromPensionCreditSavingsCredit();
        result += aDRecord.getClaimantsIncomeFromSevereDisabilityAllowance();
        result += aDRecord.getClaimantsIncomeFromStatutoryMaternityPaternityPay();
        result += aDRecord.getClaimantsIncomeFromStatutorySickPay();
        result += aDRecord.getClaimantsIncomeFromWarMobilitySupplement();
        result += aDRecord.getClaimantsIncomeFromWidowedParentsAllowance();
        result += aDRecord.getClaimantsIncomeFromWidowsBenefit();
        return result;
    }

    public long getClaimantsIncomeFromEmployment(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsGrossWeeklyIncomeFromEmployment();
        result += aDRecord.getClaimantsGrossWeeklyIncomeFromSelfEmployment();
        return result;
    }

    public long getClaimantsIncomeFromGovernmentTraining(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromGovernmentTraining();
        result += aDRecord.getClaimantsIncomeFromTrainingForWorkCommunityAction();
        result += aDRecord.getClaimantsIncomeFromYouthTrainingScheme();
        return result;
    }

    public long getClaimantsIncomeFromPensionPrivate(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromOccupationalPension();
        result += aDRecord.getClaimantsIncomeFromPersonalPension();
        return result;
    }

    public long getClaimantsIncomeFromPensionState(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc();
        result += aDRecord.getClaimantsIncomeFromWarDisablementPensionArmedForcesGIP();
        result += aDRecord.getClaimantsIncomeFromWarWidowsWidowersPension();
        return result;
    }

    public long getClaimantsIncomeFromBoardersAndSubTenants(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromSubTenants();
        result += aDRecord.getClaimantsIncomeFromBoarders();
        return result;
    }

    public long getClaimantsIncomeFromOther(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getClaimantsIncomeFromMaintenancePayments();
        result += aDRecord.getClaimantsIncomeFromStudentGrantLoan();
        result += aDRecord.getClaimantsOtherIncome();
        return result;
    }

    public long getClaimantsIncomeTotal(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += getClaimantsIncomeFromBenefitsAndAllowances(aDRecord);
        result += getClaimantsIncomeFromEmployment(aDRecord);
        result += getClaimantsIncomeFromGovernmentTraining(aDRecord);
        result += getClaimantsIncomeFromPensionPrivate(aDRecord);
        result += getClaimantsIncomeFromPensionState(aDRecord);
        result += getClaimantsIncomeFromBoardersAndSubTenants(aDRecord);
        result += getClaimantsIncomeFromOther(aDRecord);
        return result;
    }

    public long getHouseholdIncomeTotal(
            SHBE_Record aRecord,
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += getClaimantsIncomeTotal(aDRecord);
        result += getPartnersIncomeTotal(aDRecord);
        ArrayList<SHBE_S_Record> SRecords;
        SRecords = aRecord.getSRecords();
        if (SRecords != null) {
            Iterator<SHBE_S_Record> ite;
            ite = SRecords.iterator();
            SHBE_S_Record SHBE_S_Record;
            while (ite.hasNext()) {
                SHBE_S_Record = ite.next();
                result += SHBE_S_Record.getNonDependantGrossWeeklyIncomeFromRemunerativeWork();
            }
        }
        return result;
    }

    public long getPartnersIncomeFromBenefitsAndAllowances(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromAttendanceAllowance();
        result += aDRecord.getPartnersIncomeFromBereavementAllowance();
        result += aDRecord.getPartnersIncomeFromBusinessStartUpAllowance();
        result += aDRecord.getPartnersIncomeFromCarersAllowance();
        result += aDRecord.getPartnersIncomeFromChildBenefit();
        result += aDRecord.getPartnersIncomeFromContributionBasedJobSeekersAllowance();
        result += aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceCareComponent();
        result += aDRecord.getPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent();
        result += aDRecord.getPartnersIncomeFromIncapacityBenefitLongTerm();
        result += aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermHigher();
        result += aDRecord.getPartnersIncomeFromIncapacityBenefitShortTermLower();
        result += aDRecord.getPartnersIncomeFromIndustrialInjuriesDisablementBenefit();
        result += aDRecord.getPartnersIncomeFromMaternityAllowance();
        result += aDRecord.getPartnersIncomeFromNewDeal50PlusEmploymentCredit();
        result += aDRecord.getPartnersIncomeFromNewTaxCredits();
        result += aDRecord.getPartnersIncomeFromPensionCreditSavingsCredit();
        result += aDRecord.getPartnersIncomeFromSevereDisabilityAllowance();
        result += aDRecord.getPartnersIncomeFromStatutoryMaternityPaternityPay();
        result += aDRecord.getPartnersIncomeFromStatutorySickPay();
        result += aDRecord.getPartnersIncomeFromWarMobilitySupplement();
        result += aDRecord.getPartnersIncomeFromWidowedParentsAllowance();
        result += aDRecord.getPartnersIncomeFromWidowsBenefit();
        return result;
    }

    public long getPartnersIncomeFromEmployment(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersGrossWeeklyIncomeFromEmployment();
        result += aDRecord.getPartnersGrossWeeklyIncomeFromSelfEmployment();
        return result;
    }

    public long getPartnersIncomeFromGovernmentTraining(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromGovernmentTraining();
        result += aDRecord.getPartnersIncomeFromTrainingForWorkCommunityAction();
        result += aDRecord.getPartnersIncomeFromYouthTrainingScheme();
        return result;
    }

    public long getPartnersIncomeFromPensionPrivate(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromOccupationalPension();
        result += aDRecord.getPartnersIncomeFromPersonalPension();
        return result;
    }

    public long getPartnersIncomeFromPensionState(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc();
        result += aDRecord.getPartnersIncomeFromWarDisablementPensionArmedForcesGIP();
        result += aDRecord.getPartnersIncomeFromWarWidowsWidowersPension();
        return result;
    }

    public long getPartnersIncomeFromBoardersAndSubTenants(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromSubTenants();
        result += aDRecord.getPartnersIncomeFromBoarders();
        return result;
    }

    public long getPartnersIncomeFromOther(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += aDRecord.getPartnersIncomeFromMaintenancePayments();
        result += aDRecord.getPartnersIncomeFromStudentGrantLoan();
        result += aDRecord.getPartnersOtherIncome();
        return result;
    }

    public long getPartnersIncomeTotal(
            SHBE_D_Record aDRecord) {
        long result = 0L;
        result += getPartnersIncomeFromBenefitsAndAllowances(aDRecord);
        result += getPartnersIncomeFromEmployment(aDRecord);
        result += getPartnersIncomeFromGovernmentTraining(aDRecord);
        result += getPartnersIncomeFromPensionPrivate(aDRecord);
        result += getPartnersIncomeFromPensionState(aDRecord);
        result += getPartnersIncomeFromBoardersAndSubTenants(aDRecord);
        result += getPartnersIncomeFromOther(aDRecord);
        return result;
    }

    public long getClaimantsAndPartnersIncomeTotal(
            SHBE_D_Record aDRecord) {
        long result = getClaimantsIncomeTotal(aDRecord) + getPartnersIncomeTotal(aDRecord);
        return result;
    }

    public boolean getUnderOccupancy(
            SHBE_D_Record aDRecord) {
        int numberOfBedroomsForLHARolloutCasesOnly = aDRecord.getNumberOfBedroomsForLHARolloutCasesOnly();
        if (numberOfBedroomsForLHARolloutCasesOnly > 0) {
            if (numberOfBedroomsForLHARolloutCasesOnly
                    > aDRecord.getNumberOfChildDependents()
                    + aDRecord.getNumberOfNonDependents()) {
                return true;
            }
        }
        return false;
    }

    public int getUnderOccupancyAmount(
            SHBE_D_Record aDRecord) {
        int result = 0;
        int numberOfBedroomsForLHARolloutCasesOnly = aDRecord.getNumberOfBedroomsForLHARolloutCasesOnly();
        if (numberOfBedroomsForLHARolloutCasesOnly > 0) {
            result = numberOfBedroomsForLHARolloutCasesOnly
                    - aDRecord.getNumberOfChildDependents()
                    - aDRecord.getNumberOfNonDependents();
        }
        return result;
    }

    private String[] filenames;

    public int getFilenamesLength() throws IOException {
        return getFilenames().length;
    }

    public String[] getFilenames() throws IOException {
        if (filenames == null) {
            //String[] list = env.files.getInputSHBEDir().list();
            //SHBEFilenamesAll = new String[list.length];
            List<Path> list = Files.list(env.files.getInputSHBEDir()).collect(
                    Collectors.toList());
            filenames = new String[list.size()];
            String s;
            String ym;
            TreeMap<String, String> yms = new TreeMap<>();
//            for (String list1 : list) {
//                s = list1;
//                ym = getYearMonthNumber(s);
//                yms.put(ym, s);
//            }
            for (Path list1 : list) {
                s = list1.getFileName().toString();
                ym = getYearMonthNumber(s);
                yms.put(ym, s);
            }
            Iterator<String> ite = yms.keySet().iterator();
            int i = 0;
            while (ite.hasNext()) {
                ym = ite.next();
                filenames[i] = yms.get(ym);
                i++;
            }
        }
        return filenames;
    }

    private ArrayList<UKP_YM3> YM3All;

    public ArrayList<UKP_YM3> getYM3All() throws IOException {
        if (YM3All == null) {
            filenames = getFilenames();
            YM3All = new ArrayList<>();
            filenames = getFilenames();
            for (String SHBEFilename : filenames) {
                YM3All.add(getYM3(SHBEFilename));
            }
        }
        return YM3All;
    }

    public ArrayList<Integer> getSHBEFilenameIndexes() throws IOException {
        ArrayList<Integer> result;
        result = new ArrayList<>();
        filenames = getFilenames();
        for (int i = 0; i < filenames.length; i++) {
            result.add(i);
        }
        return result;
    }

    /**
     *
     * @param tSHBEFilenames
     * @param include
     * @return * {@code
     * Object[] result;
     * result = new Object[2];
     * TreeMap<BigDecimal, String> valueLabel;
     * valueLabel = new TreeMap<BigDecimal, String>();
     * TreeMap<String, BigDecimal> fileLabelValue;
     * fileLabelValue = new TreeMap<String, BigDecimal>();
     * result[0] = valueLabel;
     * result[1] = fileLabelValue;
     * }
     */
    public Object[] getTreeMapDateLabelSHBEFilenames(String[] tSHBEFilenames,
            ArrayList<Integer> include
    ) {
        // Initialise result r
        Object[] r;
        r = new Object[2];
        TreeMap<BigDecimal, String> valueLabel;
        valueLabel = new TreeMap<>();
        TreeMap<String, BigDecimal> fileLabelValue;
        fileLabelValue = new TreeMap<>();
        r[0] = valueLabel;
        r[1] = fileLabelValue;

        // Get month3Letters lookup
        ArrayList<String> month3Letters;
        month3Letters = Generic_Time.getMonths3Letters();

        // Declare variables
        int startMonth;
        int startYear;
        int yearInt0;
        int month0Int;
        String month0;
        String m30;
        UKP_YM3 yM30;
        int i;
        Iterator<Integer> ite;

        // Iterate
        ite = include.iterator();

        // Initialise first
        i = ite.next();
        yM30 = getYM3(tSHBEFilenames[i]);
        yearInt0 = Integer.valueOf(getYear(tSHBEFilenames[i]));
        month0 = getMonth(tSHBEFilenames[i]);
        m30 = month0.substring(0, 3);
        month0Int = month3Letters.indexOf(m30) + 1;
        startMonth = month0Int;
        startYear = yearInt0;

        // Iterate through rest
        while (ite.hasNext()) {
            i = ite.next();
            UKP_YM3 yM31;
            yM31 = getYM3(tSHBEFilenames[i]);
            int yearInt;
            String month;
            int monthInt;
            String m3;
            month = getMonth(tSHBEFilenames[i]);
            yearInt = Integer.valueOf(getYear(tSHBEFilenames[i]));
            m3 = month.substring(0, 3);
            monthInt = month3Letters.indexOf(m3) + 1;
            BigDecimal timeSinceStart;
            timeSinceStart = BigDecimal.valueOf(Generic_Time.getMonthDiff(
                    startYear, yearInt, startMonth, monthInt));
            //System.out.println(timeSinceStart);
            String label;
            label = yM30.toString() + "-" + yM31.toString();
            //System.out.println(label);
            valueLabel.put(timeSinceStart, label);
            fileLabelValue.put(label, timeSinceStart);

            // Prepare variables for next iteration
            yM30 = yM31;
        }
        return r;
    }

    /**
     *
     * @param SHBEFilenames
     * @param include
     * @return * {@code
     * Object[] result;
     * result = new Object[2];
     * TreeMap<BigDecimal, String> valueLabel;
     * valueLabel = new TreeMap<BigDecimal, String>();
     * TreeMap<String, BigDecimal> fileLabelValue;
     * fileLabelValue = new TreeMap<String, BigDecimal>();
     * result[0] = valueLabel;
     * result[1] = fileLabelValue;
     * }
     */
    public Object[] getTreeMapDateLabelSHBEFilenamesSingle(
            String[] SHBEFilenames, ArrayList<Integer> include
    ) {
        // Initiailise result r
        Object[] r;
        r = new Object[2];
        TreeMap<BigDecimal, UKP_YM3> valueLabel;
        valueLabel = new TreeMap<>();
        TreeMap<UKP_YM3, BigDecimal> fileLabelValue;
        fileLabelValue = new TreeMap<>();
        r[0] = valueLabel;
        r[1] = fileLabelValue;

        // Get month3Letters lookup
        ArrayList<String> month3Letters;
        month3Letters = Generic_Time.getMonths3Letters();

        // Declare variables
        int startMonth;
        int startYear;
        UKP_YM3 YM3;
        int yearInt;
        String month;
        int monthInt;
        String m3;
        Iterator<Integer> ite;
        int i;

        // Iterate
        ite = include.iterator();

        // Initialise first
        i = ite.next();
        int yearInt0 = Integer.valueOf(getYear(SHBEFilenames[i]));
        String m30 = getMonth3(SHBEFilenames[i]);
        int month0Int = month3Letters.indexOf(m30) + 1;
        startMonth = month0Int;
        startYear = yearInt0;

        // Iterate through rest
        while (ite.hasNext()) {
            i = ite.next();
            YM3 = getYM3(SHBEFilenames[i]);
            month = getMonth(SHBEFilenames[i]);
            yearInt = Integer.valueOf(getYear(SHBEFilenames[i]));
            m3 = month.substring(0, 3);
            monthInt = month3Letters.indexOf(m3) + 1;
            BigDecimal timeSinceStart;
            timeSinceStart = BigDecimal.valueOf(Generic_Time.getMonthDiff(
                    startYear, yearInt, startMonth, monthInt));
            valueLabel.put(timeSinceStart, YM3);
            fileLabelValue.put(YM3, timeSinceStart);
        }
        return r;
    }

//    /**
//     *
//     * @param tSHBEFilenames
//     * @param include
//     * @param startIndex
//     * @return * {@code
//     * Object[] result;
//     * result = new Object[2];
//     * TreeMap<BigDecimal, String> valueLabel;
//     * valueLabel = new TreeMap<BigDecimal, String>();
//     * TreeMap<String, BigDecimal> fileLabelValue;
//     * fileLabelValue = new TreeMap<String, BigDecimal>();
//     * result[0] = valueLabel;
//     * result[1] = fileLabelValue;
//     * }
//     */
//    public TreeMap<BigDecimal, String> getDateValueLabelSHBEFilenames(
//            String[] tSHBEFilenames,
//            ArrayList<Integer> include) {
//        TreeMap<BigDecimal, String> result;
//        result = new TreeMap<BigDecimal, String>();
//        
//        ArrayList<String> month3Letters;
//        month3Letters = Generic_Time.getMonths3Letters();
//
//        int startMonth = 0;
//        int startYear = 0;
//        int yearInt0 = 0;
//        int month0Int = 0;
//        String month0 = "";
//        String m30 = "";
//        String yM30 = "";
//
//        boolean first = true;
//        Iterator<Integer> ite;
//        ite = include.iterator();
//        while (ite.hasNext()) {
//            int i = ite.next();
//            if (first) {
//                yM30 = getYM3(tSHBEFilenames[i]);
//                yearInt0 = Integer.valueOf(getYear(tSHBEFilenames[i]));
//                month0 = getMonth(tSHBEFilenames[i]);
//                m30 = month0.substring(0, 3);
//                month0Int = month3Letters.indexOf(m30) + 1;
//                startMonth = month0Int;
//                startYear = yearInt0;
//                first = false;
//            } else {
//                String yM31 = getYM3(tSHBEFilenames[i]);
//                int yearInt;
//                String month;
//                int monthInt;
//                String m3;
//                month = getMonth(tSHBEFilenames[i]);
//                yearInt = Integer.valueOf(getYear(tSHBEFilenames[i]));
//                m3 = month.substring(0, 3);
//                monthInt = month3Letters.indexOf(m3) + 1;
//                BigDecimal timeSinceStart;
//                timeSinceStart = BigDecimal.valueOf(
//                        Generic_Time.getMonthDiff(
//                                startYear, yearInt, startMonth, monthInt));
//                //System.out.println(timeSinceStart);
//                result.put(
//                        timeSinceStart,
//                        yM30 + " - " + yM31);
//                
//                //System.out.println(fileLabel);
//                yearInt0 = yearInt;
//                month0 = month;
//                m30 = m3;
//                month0Int = monthInt;
//            }
//        }
//        return result;
//    }
    public String getMonth3(String SHBEFilename) {
        String result;
        result = getMonth(SHBEFilename).substring(0, 3);
        return result;
    }

    public UKP_YM3 getYM3(String SHBEFilename) {
        return getYM3(SHBEFilename, "_");
    }

    public UKP_YM3 getYM3(String SHBEFilename, String separator) {
        UKP_YM3 result;
        String year;
        year = getYear(SHBEFilename);
        String m3;
        m3 = getMonth3(SHBEFilename);
        result = new UKP_YM3(year + separator + m3);
        return result;
    }

    public String getYM3FromYearMonthNumber(String YearMonth) {
        String result;
        String[] yM;
        yM = YearMonth.split("-");
        String m3;
        m3 = Generic_Time.getMonth3Letters(yM[1]);
        result = yM[0] + SHBE_Strings.symbol_underscore + m3;
        return result;
    }

    public String getYearMonthNumber(String SHBEFilename) {
        String result;
        String year;
        year = getYear(SHBEFilename);
        String monthNumber;
        monthNumber = getMonthNumber(SHBEFilename);
        result = year + "-" + monthNumber;
        return result;
    }

    /**
     * For example for SHBEFilename "hb9991_SHBE_555086k May 2013.csv", this
     * returns "May"
     *
     * @param SHBEFilename
     * @return
     */
    public String getMonth(String SHBEFilename) {
        return SHBEFilename.split(" ")[1];
    }

    /**
     * For example for SHBEFilename "hb9991_SHBE_555086k May 2013.csv", this
     * returns "May"
     *
     * @param SHBEFilename
     * @return
     */
    public String getMonthNumber(String SHBEFilename) {
        String m3;
        m3 = getMonth3(SHBEFilename);
        return Generic_Time.getMonthNumber(m3);
    }

    /**
     * For example for SHBEFilename "hb9991_SHBE_555086k May 2013.csv", this
     * returns "2013"
     *
     * @param SHBEFilename
     * @return
     */
    public String getYear(String SHBEFilename) {
        return SHBEFilename.split(" ")[2].substring(0, 4);
    }

    /**
     * Method for getting SHBE collections filenames in an array
     *
     * @return String[] SHBE collections filenames
     */
    public String[] getSHBEFilenamesSome() {
        String[] result = new String[6];
        result[0] = "hb9991_SHBE_549416k April 2013.csv";
        result[1] = "hb9991_SHBE_555086k May 2013.csv";
        result[2] = "hb9991_SHBE_562036k June 2013.csv";
        result[3] = "hb9991_SHBE_568694k July 2013.csv";
        result[4] = "hb9991_SHBE_576432k August 2013.csv";
        result[5] = "hb9991_SHBE_582832k September 2013.csv";
        return result;
    }

//    public Map<SHBE_ID, String> getIDToStringLookup(
//            Path f) {
//        Map<SHBE_ID, String> result;
//        if (Files.exists(f)) {
//            result = (Map<SHBE_ID, String>) Generic_IO.readObject(f);
//        } else {
//            result = new HashMap<>();
//        }
//        return result;
//    }
    public int getNumberOfTenancyTypes() {
        return 10;
    }

    public int getNumberOfPassportedStandardIndicators() {
        return 6;
    }

    public int getNumberOfClaimantsEthnicGroups() {
        return 17;
    }

    public int getNumberOfClaimantsEthnicGroupsGrouped() {
        return 10;
    }

    public int getOneOverMaxValueOfPassportStandardIndicator() {
        return 6;
    }

    /**
     * Negation of getOmits()
     *
     * sIncludeAll sIncludeYearly sInclude6Monthly sInclude3Monthly
     * sIncludeMonthly sIncludeMonthlySinceApril2013
     * sInclude2MonthlySinceApril2013Offset0
     * sInclude2MonthlySinceApril2013Offset1 sIncludeStartEndSinceApril2013
     * sIncludeApril2013May2013
     *
     * @return
     * @throws java.io.IOException If encountered.
     */
    public TreeMap<String, ArrayList<Integer>> getIncludes() throws IOException {
        TreeMap<String, ArrayList<Integer>> result;
        result = new TreeMap<>();
        TreeMap<String, ArrayList<Integer>> omits;
        omits = getOmits();
        Iterator<String> ite;
        ite = omits.keySet().iterator();
        while (ite.hasNext()) {
            String omitKey;
            omitKey = ite.next();
            ArrayList<Integer> omit;
            omit = omits.get(omitKey);
            ArrayList<Integer> include;
            //include = getSHBEFilenameIndexesExcept34();
            include = getSHBEFilenameIndexes();
            include.removeAll(omit);
            result.put(omitKey, include);
        }
        return result;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getOmitAll() {
        return new ArrayList<>();
    }

    public ArrayList<Integer> getIncludeAll() throws IOException {
        ArrayList<Integer> r;
        ArrayList<Integer> omit;
        omit = getOmitAll();
        r = getSHBEFilenameIndexes();
        r.removeAll(omit);
        return r;
    }

    /**
     *
     * @param n The number of SHBE files.
     * @return
     */
    public ArrayList<Integer> getOmitYearly(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        r.add(1);
        r.add(3);
        r.add(5);
        r.add(6);
        r.add(8);
        r.add(9);
        r.add(10);
        r.add(12);
        r.add(13);
        r.add(14); //Jan 13 NB. Prior to this data not monthly
        r.add(15); //Feb 13
        r.add(16); //Mar 13
        int i0 = 17;
        for (int i = i0; i < n; i++) {
            // Do not add 17,29,41,53...
            if (!((i - i0) % 12 == 0)) {
                r.add(i);
            }
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return Include Yearly.
     * @throws java.io.IOException If encountered.
     */
    public ArrayList<Integer> getIncludeYearly(int n) throws IOException {
        ArrayList<Integer> r = getSHBEFilenameIndexes();
        r.removeAll(getOmitYearly(n));
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return Omit6Monthly
     */
    public ArrayList<Integer> getOmit6Monthly(int n) {
        ArrayList<Integer> r = new ArrayList<>();
        r.add(6);
        r.add(8);
        r.add(10);
        r.add(12);
        r.add(14); //Jan 13 NB. Prior to this data not monthly
        r.add(15); //Feb 13
        r.add(16); //Mar 13
        int i0 = 17;
        for (int i = i0; i < n; i++) {
            // Do not add 17,23,29,35,41,47,53...
            if (!((i - i0) % 6 == 0)) {
                r.add(i);
            }
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return Include6Monthly
     * @throws java.io.IOException If encountered.
     */
    public ArrayList<Integer> getInclude6Monthly(int n) throws IOException {
        ArrayList<Integer> r = getSHBEFilenameIndexes();
        r.removeAll(getOmit6Monthly(n));
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return Omit7Monthly
     */
    public ArrayList<Integer> getOmit3Monthly(int n) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            r.add(i);
        }
        r.add(15); //Feb 13 NB. Prior to this data not monthly
        r.add(16); //Mar 13
        int i0 = 17;
        for (int i = i0; i < n; i++) {
            // Do not add 17,20,23,26,29,32,35,38,41,44,47,50,53...
            if (!((i - i0) % 3 == 0)) {
                r.add(i);
            }
        }
        return r;
    }

    /**
     * @return Include3Monthly
     * @throws java.io.IOException If encountered.
     */
    public ArrayList<Integer> getInclude3Monthly() throws IOException {
        return getInclude3Monthly(getFilenamesLength());
    }

    /**
     * @param n The number of SHBE files.
     * @return Include3Monthly
     * @throws java.io.IOException If encountered.
     */
    public ArrayList<Integer> getInclude3Monthly(int n) throws IOException {
        ArrayList<Integer> r = getSHBEFilenameIndexes();
        r.removeAll(getOmit3Monthly(n));
        return r;
    }

    /**
     * @return Omit Monthly.
     */
    public ArrayList<Integer> getOmitMonthly() {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            r.add(i);
        }
        return r;
    }

    /**
     * @return IncludeMonthly
     * @throws java.io.IOException If encountered.
     */
    public ArrayList<Integer> getIncludeMonthly() throws IOException {
        ArrayList<Integer> r = getSHBEFilenameIndexes();
        r.removeAll(getOmitMonthly());
        return r;
    }

    /**
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013.
     */
    public ArrayList<Integer> getOmitMonthlyUO() {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 1 month.
     */
    public ArrayList<Integer> getOmit2MonthlyUO1(int n) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 17; i < n; i += 2) {
            r.add(i);
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 1 month.
     */
    public ArrayList<Integer> getOmit2StartEndSinceApril2013(int n) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 18; i < n - 2; i++) {
            r.add(i);
        }
        r.add(n - 1);
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 1 month.
     */
    public ArrayList<Integer> getOmit2April2013May2013(int n) {
        ArrayList<Integer> r;
        r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 19; i < n; i++) {
            r.add(i);
        }
        return r;
    }

    /**
     * @param n The number of SHBE files.
     * @return a list with the indexes of all SHBE files to omit when
     * considering only those in the period from April 2013 every other month
     * offset by 0 months.
     */
    public ArrayList<Integer> getOmit2MonthlyUO0(int n) {
        ArrayList<Integer> r = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            r.add(i);
        }
        for (int i = 18; i < n; i += 2) {
            r.add(i);
        }
        return r;
    }

    /**
     *
     * @return List of includes.
     * @throws java.io.IOException If encountered.
     */
    public ArrayList<Integer> getIncludeMonthlyUO() throws IOException {
        return getIncludeMonthlyUO(getFilenamesLength());
    }

    /**
     * @param n The number of SHBE files.
     * @return List of includes.
     * @throws java.io.IOException If encountered.
     */
    public ArrayList<Integer> getIncludeMonthlyUO(int n) throws IOException {
        ArrayList<Integer> r = getSHBEFilenameIndexes();
        r.removeAll(getOmitMonthlyUO());
        return r;
    }

    /**
     * Negation of getIncludes().This method will want modifying if data prior
     * to January 2013 is added. sIncludeAll sIncludeYearly sInclude6Monthly
     * sInclude3Monthly sIncludeMonthly sIncludeMonthlySinceApril2013
     * sInclude2MonthlySinceApril2013Offset0
     * sInclude2MonthlySinceApril2013Offset1 sIncludeStartEndSinceApril2013
     * sIncludeApril2013May2013
     *
     * @return Omits
     * @throws java.io.IOException If encountered.
     */
    public TreeMap<String, ArrayList<Integer>> getOmits() throws IOException {
        TreeMap<String, ArrayList<Integer>> r = new TreeMap<>();
        String[] tSHBEFilenames = getFilenames();
        int n = tSHBEFilenames.length;
        r.put(SHBE_Strings.s_IncludeAll, getOmitAll());
        r.put(SHBE_Strings.s_IncludeYearly, getOmitYearly(n));
        r.put(SHBE_Strings.s_Include6Monthly, getOmit6Monthly(n));
        r.put(SHBE_Strings.s_Include3Monthly, getOmit3Monthly(n));
        r.put(SHBE_Strings.s_IncludeMonthly, getOmitMonthly());
        r.put(SHBE_Strings.s_IncludeMonthlySinceApril2013, getOmitMonthlyUO());
        r.put(SHBE_Strings.s_Include2MonthlySinceApril2013Offset0, getOmit2MonthlyUO0(n));
        r.put(SHBE_Strings.s_Include2MonthlySinceApril2013Offset1, getOmit2MonthlyUO1(n));
        r.put(SHBE_Strings.s_IncludeStartEndSinceApril2013, getOmit2StartEndSinceApril2013(n));
        r.put(SHBE_Strings.s_IncludeApril2013May2013, getOmit2April2013May2013(n));
        return r;
    }

    /**
     * @param ym3 The ym3.
     * @param dRecord The D record.
     * @return The claimants age.
     */
    public String getClaimantsAge(String ym3, SHBE_D_Record dRecord) {
        String[] syM3 = ym3.split(SHBE_Strings.symbol_underscore);
        return getClaimantsAge(syM3[0], syM3[1], dRecord);
    }

    /**
     * @param y The year.
     * @param m The month
     * @param dRecord The D record.
     * @return Claimants age.
     */
    public String getClaimantsAge(String y, String m, SHBE_D_Record dRecord) {
        return getAge(y, m, dRecord.getClaimantsDateOfBirth());
    }

    /**
     * @param y The year.
     * @param m The month.
     * @param dRecord The D record.
     * @return Partners age.
     */
    public String getPartnersAge(String y, String m, SHBE_D_Record dRecord) {
        return getAge(y, m, dRecord.getPartnersDateOfBirth());
    }

    /**
     * @param y The year.
     * @param m The month.
     * @param DoB The date of birth
     * @return The age.
     */
    public String getAge(String y, String m, String DoB) {
        if (DoB == null) {
            return "";
        }
        if (DoB.isEmpty()) {
            return DoB;
        }
        String[] sDoB = DoB.split("/");
        Generic_Time tDoB = new Generic_Time(Integer.valueOf(sDoB[0]),
                Integer.valueOf(sDoB[1]), Integer.valueOf(sDoB[2]));
        Generic_Time tNow = new Generic_Time(0, Integer.valueOf(m),
                Integer.valueOf(y));
        return Integer.toString(Generic_Time.getAgeInYears(tNow, tDoB));
    }

    /**
     * @param dRecord D_Record
     * @return {@code true} if there are any disability awards in the D_Record
     * household.
     */
    public boolean getDisability(SHBE_D_Record dRecord) {
        return dRecord.getDisabilityPremiumAwarded() == 1
                || dRecord.getSevereDisabilityPremiumAwarded() == 1
                || dRecord.getDisabledChildPremiumAwarded() == 1
                || dRecord.getEnhancedDisabilityPremiumAwarded() == 1;
    }

    public int getEthnicityGroup(SHBE_D_Record D_Record) {
        int claimantsEthnicGroup = D_Record.getClaimantsEthnicGroup();
        switch (claimantsEthnicGroup) {
            case 1:
                return 1;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 3;
            case 6:
                return 4;
            case 7:
                return 5;
            case 8:
                return 6;
            case 9:
                return 6;
            case 10:
                return 6;
            case 11:
                return 6;
            case 12:
                return 7;
            case 13:
                return 7;
            case 14:
                return 7;
            case 15:
                return 8;
            case 16:
                return 9;
        }
        return 0;
    }

    public String getEthnicityName(SHBE_D_Record D_Record) {
        int claimantsEthnicGroup = D_Record.getClaimantsEthnicGroup();
        switch (claimantsEthnicGroup) {
            case 1:
                return "White: British";
            case 2:
                return "White: Irish";
            case 3:
                return "White: Any Other";
            case 4:
                return "Mixed: White and Black Caribbean";
            case 5:
                return "Mixed: White and Black African";
            case 6:
                return "Mixed: White and Asian";
            case 7:
                return "Mixed: Any Other";
            case 8:
                return "Asian or Asian British: Indian";
            case 9:
                return "Asian or Asian British: Pakistani";
            case 10:
                return "Asian or Asian British: Bangladeshi";
            case 11:
                return "Asian or Asian British: Any Other";
            case 12:
                return "Black or Black British: Caribbean";
            case 13:
                return "Black or Black British: African";
            case 14:
                return "Black or Black British: Any Other";
            case 15:
                return "Chinese";
            case 16:
                return "Any Other";
        }
        return "";
    }

    public String getEthnicityGroupName(int ethnicityGroup) {
        switch (ethnicityGroup) {
            case 1:
                return "WhiteBritish_Or_WhiteIrish";
            case 2:
                return "WhiteOther";
            case 3:
                return "MixedWhiteAndBlackAfrican_Or_MixedWhiteAndBlackCaribbean";
            case 4:
                return "MixedWhiteAndAsian";
            case 5:
                return "MixedOther";
            case 6:
                return "Asian_Or_AsianBritish";
            case 7:
                return "BlackOrBlackBritishCaribbean_Or_BlackOrBlackBritishAfrican_Or_BlackOrBlackBritishOther";
            case 8:
                return "Chinese";
            case 9:
                return "Other";
        }
        return "";
    }

    /**
     * @param d D Record.
     * @return Claimant Person ID.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_PersonID getClaimantPersonID(SHBE_D_Record d)
            throws IOException, ClassNotFoundException {
        return new SHBE_PersonID(
                getN2nid().get(d.getClaimantsNationalInsuranceNumber()),
                getD2did().get(d.getClaimantsDateOfBirth()));
    }

    /**
     * @param d D Record.
     * @return Partner Person ID.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_PersonID getPartnerPersonID(SHBE_D_Record d)
            throws IOException, ClassNotFoundException {
        return new SHBE_PersonID(
                getN2nid().get(d.getPartnersNationalInsuranceNumber()),
                getD2did().get(d.getPartnersDateOfBirth()));
    }

    /**
     * @param s S_Record
     * @return NonDependent Person ID.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_PersonID getNonDependentPersonID(SHBE_S_Record s)
            throws IOException, ClassNotFoundException {
        return new SHBE_PersonID(
                getN2nid().get(s.getSubRecordChildReferenceNumberOrNINO()),
                getD2did().get(s.getSubRecordDateOfBirth()));
    }

    /**
     *
     * @param s S Record
     * @param index
     * @return Person ID for dependent.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_PersonID getDependentPersonID(SHBE_S_Record s,
            int index) throws IOException, ClassNotFoundException {
        String nino = s.getSubRecordChildReferenceNumberOrNINO();
        String cNINO = s.getClaimantsNationalInsuranceNumber();
        if (cNINO.trim().isEmpty()) {
            cNINO = SHBE_Strings.s_DefaultNINO;
            env.env.log("ClaimantsNINO is empty for "
                    + "ClaimRef " + s.getCouncilTaxBenefitClaimReferenceNumber()
                    + " Setting as default NINO " + cNINO, true);
        }
        if (nino.isEmpty()) {
            nino = "" + index;
            nino += "_" + cNINO;
        } else {
            nino += "_" + cNINO;
        }
        return new SHBE_PersonID(getN2nid().get(nino),
                getD2did().get(s.getSubRecordDateOfBirth()));
    }

    /**
     * @param S_Records S Records for which a set of Person IDs is returned.
     * @return A set of Person IDs.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Set<SHBE_PersonID> getPersonIDs(ArrayList<SHBE_S_Record> S_Records)
            throws IOException, ClassNotFoundException {
        Set<SHBE_PersonID> r = new HashSet<>();
        Iterator<SHBE_S_Record> ite = S_Records.iterator();
        while (ite.hasNext()) {
            SHBE_S_Record s = ite.next();
            r.add(new SHBE_PersonID(getN2nid().get(
                    s.getSubRecordChildReferenceNumberOrNINO()),
                    getD2did().get(s.getSubRecordDateOfBirth())));
        }
        return r;
    }

    /**
     * For getting a Person ID for the National Insurance Number {@code nino}
     * and Date of Birth {@code dob} given. If the {@code nino} and {@code dob}
     * are not already in the lookups they are added.
     *
     * @param nino National Insurance Number.
     * @param dob Date of Birth.
     * @param n2nid NINO to NINO ID lookup.
     * @param nid2n NINO ID to NINO lookup.
     * @param d2did DOB to DOB ID lookup.
     * @param did2d DOB ID to DOB lookup.
     * @return Person ID.
     */
    SHBE_PersonID getPersonID(String nino, String dob,
            Map<String, SHBE_NINOID> n2nid, Map<SHBE_NINOID, String> nid2n,
            Map<String, SHBE_DOBID> d2did, Map<SHBE_DOBID, String> did2d) {
        return new SHBE_PersonID(getNINOIDAddIfNeeded(nino),
                getDOBIDAddIfNeeded(dob));
    }

    /**
     * For getting a Person ID for the D Record {@code d}. If the NINO ID and/or
     * the DOB ID for the D Record do not already exist, these are added to the
     * lookups.
     *
     * @param d D Record.
     * @param n2nid NINO to NINO ID lookup.
     * @param nid2n NINO ID to NINO lookup.
     * @param d2did DOB to DOB ID lookup.
     * @param did2d DOB ID to DOB lookup
     * @return Person ID.
     */
    SHBE_PersonID getPersonID(SHBE_D_Record d, Map<String, SHBE_NINOID> n2nid,
            Map<SHBE_NINOID, String> nid2n, Map<String, SHBE_DOBID> d2did,
            Map<SHBE_DOBID, String> did2d) {
        String nino = d.getPartnersNationalInsuranceNumber();
        String dob = d.getPartnersDateOfBirth();
        return getPersonID(nino, dob, n2nid, nid2n, d2did, did2d);
    }

    /**
     * @param cid2pid Claim ID to Person ID lookup.
     * @return A set of all Person IDs in {@code cid2pid}.
     */
    public Set<SHBE_PersonID> getUniquePersonIDs(
            Map<SHBE_ClaimID, Set<SHBE_PersonID>> cid2pid) {
        Set<SHBE_PersonID> r = new HashSet<>();
        Collection<Set<SHBE_PersonID>> c = cid2pid.values();
        Iterator<Set<SHBE_PersonID>> ite = c.iterator();
        while (ite.hasNext()) {
            r.addAll(ite.next());
        }
        return r;
    }

    public Set<SHBE_PersonID> getUniquePersonIDs0(
            Map<SHBE_ClaimID, SHBE_PersonID> cid2pid) {
        Set<SHBE_PersonID> r = new HashSet<>();
        r.addAll(cid2pid.values());
        return r;
    }
}
