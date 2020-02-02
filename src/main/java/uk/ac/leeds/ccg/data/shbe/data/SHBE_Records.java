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
package uk.ac.leeds.ccg.data.shbe.data;

import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_PersonID;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.data.ukp.data.onspd.ONSPD_Point;
import uk.ac.leeds.ccg.generic.util.Generic_Collections;
//import uk.ac.leeds.ccg.projects.digitalwelfare.data.SHBE_CorrectedPostcodes;
import uk.ac.leeds.ccg.data.ukp.data.UKP_Data;
import uk.ac.leeds.ccg.data.ukp.data.id.UKP_RecordID;
import uk.ac.leeds.ccg.data.ukp.util.UKP_YM3;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_ClaimID;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_DOBID;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_NINOID;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 * SHBE Records.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Records extends SHBE_Object implements Serializable {

    private static final long serialVersionUID = 1L;

    // For convenience.
    private transient final SHBE_Handler handler;
    private transient final UKP_Data ukpData;

    /**
     * Keys are Claim IDs, values are SHBE Records.
     */
    private Map<SHBE_ClaimID, SHBE_Record> records;

    /**
     * SHBE Person IDs of Claimants.
     */
    Set<SHBE_PersonID> cpids;

    /**
     * SHBE Person IDs of Partners.
     */
    Set<SHBE_PersonID> ppids;

    /**
     * SHBE PersonID of Non-Dependents.
     */
    Set<SHBE_PersonID> ndpids;

    /**
     * A store for Claim IDs for Cottingley Springs Caravan Park where there are
     * often two claims for a claimant, one for a pitch and the other for the
     * rent of a caravan.
     */
    private Set<SHBE_ClaimID> cidsOfCottingleySpringsCaravanParkPairedClaims;

    /**
     * A store for Claim IDs where: StatusOfHBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private Set<SHBE_ClaimID> cidsHII;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 2 (Suspended).
     */
    private Set<SHBE_ClaimID> cidsHIS;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 0 (Other).
     */
    private Set<SHBE_ClaimID> cidsHIO;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private Set<SHBE_ClaimID> cidsCII;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 2
     * (Suspended).
     */
    private Set<SHBE_ClaimID> cidsCIS;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 0
     * (Suspended).
     */
    private Set<SHBE_ClaimID> cidsCIO;

    /**
     * SRecordsWithoutDRecords indexed by ClaimRef SHBE_ID. Once the SHBE data
     * is loaded from source, this only contains those SRecordsWithoutDRecords
     * that are not linked to a DRecord.
     */
    private Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>> sRecordsWithoutDRecords;

    /**
     * For storing the Claim IDs of records that have sRecords along with the
     * count of those sRecordsWithoutDRecords.
     */
    private Map<SHBE_ClaimID, Integer> cid2CountOfSRecords;

    /**
     * For storing the Year_Month of this. This is an identifier for these data.
     */
    private UKP_YM3 ym3;

    /**
     * For storing the NearestYM3ForONSPDLookup of this. This is derived from
     * YM3.
     */
    private UKP_YM3 nearestYM3ForONSPDLookup;

    /**
     * Holds a reference to the original input data file from which this was
     * created.
     */
    private Path inputFile;

    /**
     * Directory where this is stored.
     */
    private Path dir;

    /**
     * Path for storing this.
     */
    private Path file;

    /**
     * {@link #records} file.
     */
    private Path recordsFile;

    /**
     * {@link #cidsOfNewSHBEClaims} file.
     */
    private Path cidsOfNewSHBEClaimsFile;

    /**
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore} file.
     */
    private Path cidsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;

    /**
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} file.
     */
    private Path cidsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;

    /**
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore} file.
     */
    private Path cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;

    /**
     * {@link #cidsOfNewSHBEClaimsWhereClaimantIsNew} file.
     */
    private Path cidsOfNewSHBEClaimsWhereClaimantIsNewFile;

    /**
     * {@link #cpids} file.
     */
    private Path cpidsFile;

    /**
     * {@link #ppids} file.
     */
    private Path ppidsFile;

    /**
     * {@link #ndpids} file.
     */
    private Path ndpidsFile;

    /**
     * {@link #cidsOfCottingleySpringsCaravanParkPairedClaims} file.
     */
    private Path cidsOfCottingleySpringsCaravanParkPairedClaimsFile;

    /**
     * {@link #cidsHII} file.
     */
    private Path cidsHIIFile;

    /**
     * {@link #cidsHIS} file.
     */
    private Path cidsHISFile;

    /**
     * {@link #cidsHIO} file.
     */
    private Path cidsHIOFile;

    /**
     * {@link #cidsCII} file.
     */
    private Path cidsCIIFile;

    /**
     * {@link #cidsCIS} file.
     */
    private Path cidsCISFile;

    /**
     * {@link #cidsCIO} file.
     */
    private Path cidsCIOFile;

    /**
     * {@link #sRecordsWithoutDRecords} file.
     */
    private Path sRecordsWithoutDRecordsFile;

    /**
     * {@link #cid2CountOfSRecords} file.
     */
    private Path cidToCountOfSRecordsFile;

    /**
     * For storing the Claim IDs of records without a mappable Claimant
     * Postcode.
     */
    private Set<SHBE_ClaimID> cidsOfClaimsWithoutAMappableClaimantPostcode;

    /**
     * {@link #cidsOfClaimsWithoutAMappableClaimantPostcode} file.
     */
    private Path cidsOfClaimsWithoutAMappableClaimantPostcodeFile;

    /**
     * ClaimIDs mapped to PersonIDs of Claimants.
     */
    private Map<SHBE_ClaimID, SHBE_PersonID> cid2cpid;

    /**
     * ClaimIDs mapped to PersonIDs of Partners. If there is no main Partner for
     * the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, SHBE_PersonID> cid2ppid;

    /**
     * ClaimIDs mapped to {@code Set<SHBE_PersonID>} of Dependents. If there are
     * no Dependents for the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, Set<SHBE_PersonID>> cid2dpids;

    /**
     * ClaimIDs mapped to {@code Set<SHBE_PersonID>} of NonDependents. If there
     * are no NonDependents for the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, Set<SHBE_PersonID>> cid2ndpids;

    /**
     * ClaimIDs of Claims with Claimants that are Claimants in another claim.
     */
    private Set<SHBE_ClaimID> cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Claimants that are Partners in another claim.
     */
    private Set<SHBE_ClaimID> cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Claimants in another claim.
     */
    private Set<SHBE_ClaimID> cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Partners in multiple claims.
     */
    private Set<SHBE_ClaimID> cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with NonDependents that are Claimants or Partners in
     * another claim.
     */
    private Set<SHBE_ClaimID> cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim;

    /**
     * SHBE_PersonIDs of Claimants that are in multiple claims in a month mapped
     * to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> pid2cidsOfClaimantsInMultipleClaimsInAMonth;

    /**
     * SHBE_PersonIDs of Partners that are in multiple claims in a month mapped
     * to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> pid2cidsOfPartnersInMultipleClaimsInAMonth;

    /**
     * SHBE_PersonIDs of NonDependents that are in multiple claims in a month
     * mapped to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> pid2cidsOfNonDependentsInMultipleClaimsInAMonth;

    /**
     * Claim ID mapped to Postcode ID.
     */
    private Map<SHBE_ClaimID, UKP_RecordID> cid2postcodeID;

    /**
     * ClaimIDs of the claims that have had PostcodeF updated from the future.
     * This is only to be stored if the postcode was previously of an invalid
     * format.
     */
    private Set<SHBE_ClaimID> cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture;

    /**
     * ClaimIDs. This is only used when reading the data to check that ClaimIDs
     * are unique.
     */
    private Set<SHBE_ClaimID> cids;

    /**
     * For storing ClaimIDs of new SHBE claims.
     */
    private Set<SHBE_ClaimID> cidsOfNewSHBEClaims;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Claimant
     * before.
     */
    private Set<SHBE_ClaimID> cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Partner
     * before.
     */
    private Set<SHBE_ClaimID> cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a NonDependent
     * before.
     */
    private Set<SHBE_ClaimID> cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant is new.
     */
    private Set<SHBE_ClaimID> cidsOfNewSHBEClaimsWhereClaimantIsNew;

    /**
     * Claim IDs mapped to Tenancy Type.
     */
    private Map<SHBE_ClaimID, Integer> cid2tt;

    /**
     * loadSummary
     */
    private Map<String, Number> loadSummary;

    /**
     * The line numbers of records that for some reason could not be loaded.
     */
    private ArrayList<Long> recordIDsNotLoaded;

    /**
     * For storing ClaimIDs of all Claims where Claimant National Insurance
     * Number is invalid.
     */
    private Set<SHBE_ClaimID> cidsOfInvalidClaimantNINOClaims;

    /**
     * For storing ClaimID mapped to Claim Postcodes that are not (currently)
     * mappable.
     */
    private Map<SHBE_ClaimID, String> claimantPostcodesUnmappable;

    /**
     * For storing ClaimID mapped to Claim Postcodes that have been
     * automatically modified to make them mappable.
     */
    private Map<SHBE_ClaimID, String[]> claimantPostcodesModified;

    /**
     * For storing ClaimID mapped to Claimant Postcodes Checked by local
     * authority to be mappable, but not found in the subsequent or the latest
     * ONSPD.
     */
    private Map<SHBE_ClaimID, String> claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes;

    /**
     * {@link #cid2cpid} file.
     */
    private Path cid2cpidFile;

    /**
     * {@link cid2ppid} file.
     */
    private Path cid2ppidFile;

    /**
     * {@link #cid2dpids} file.
     */
    private Path cid2dpidsFile;

    /**
     * {@link #cid2ndpids} file.
     */
    private Path cid2ndpidsFile;

    /**
     * {@link #cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim} file.
     */
    private Path cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;

    /**
     * {@link #cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim} file.
     */
    private Path cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;

    /**
     * {@link #cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim} file.
     */
    private Path cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;

    /**
     * {@link #cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim} file.
     */
    private Path cidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;

    /**
     * {@link #cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * file.
     */
    private Path cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;

    /**
     * {@link #pid2cidsOfClaimantsInMultipleClaimsInAMonth} file.
     */
    private Path pid2cidsOfClaimantsInMultipleClaimsInAMonthFile;

    /**
     * {@link #pid2cidsOfPartnersInMultipleClaimsInAMonth} file.
     */
    private Path pid2cidsOfPartnersInMultipleClaimsInAMonthFile;

    /**
     * {@link #pid2cidsOfNonDependentsInMultipleClaimsInAMonth} file.
     */
    private Path pid2cidsOfNonDependentsInMultipleClaimsInAMonthFile;

    /**
     * {@link #cid2postcodeID} file.
     */
    private Path cid2postcodeIDFile;

    /**
     * {@link #cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture} file.
     */
    private Path cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;

    /**
     * {@link #cid2tt} file.
     */
    private Path cid2ttFile;

    /**
     * {@link #loadSummary} file.
     */
    private Path loadSummaryFile;

    /**
     * {@link #recordIDsNotLoaded} file.
     */
    private Path recordIDsNotLoadedFile;

    /**
     * {@link #cidsOfInvalidClaimantNINOClaims} file.
     */
    private Path cidsOfInvalidClaimantNINOClaimsFile;

    /**
     * {@link #claimantPostcodesUnmappable} file.
     */
    private Path claimantPostcodesUnmappableFile;

    /**
     * {@link #claimantPostcodesModified} file.
     */
    private Path claimantPostcodesModifiedFile;

    /**
     * {@link #claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes} file.
     */
    private Path claimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile;

    /**
     * If not initialised, initialises {@link #records} then returns it.
     *
     * @return {@link #records} initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, SHBE_Record> getRecords(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            env.checkAndMaybeFreeMemory();
            return getRecords();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getRecords(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {link #records} then returns it.
     *
     * @return {@link #records} initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected final Map<SHBE_ClaimID, SHBE_Record> getRecords()
            throws IOException, ClassNotFoundException {
        if (records == null) {
            Path f = getRecordsFile();
            if (Files.exists(f)) {
                records = (Map<SHBE_ClaimID, SHBE_Record>) Generic_IO.readObject(f);
            } else {
                records = new HashMap<>();
            }
        }
        return records;
    }

    /**
     * If not initialised, initialises {@link #cidsOfNewSHBEClaims} then returns
     * it.
     *
     * @return {@link #cidsOfNewSHBEClaims} initialised first if it is
     * {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfNewSHBEClaims(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            env.checkAndMaybeFreeMemory();
            return getCidsOfNewSHBEClaims();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfNewSHBEClaims(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cidsOfNewSHBEClaims} then returns
     * it.
     *
     * @return {@link #cidsOfNewSHBEClaims} initialised first if it is
     * {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfNewSHBEClaims()
            throws IOException, ClassNotFoundException {
        if (cidsOfNewSHBEClaims == null) {
            Path f;
            f = getCidsOfNewSHBEClaimsFile();
            if (Files.exists(f)) {
                cidsOfNewSHBEClaims = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfNewSHBEClaims = new HashSet<>();
            }
        }
        return cidsOfNewSHBEClaims;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore} then returns
     * it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            env.checkAndMaybeFreeMemory();
            return getCidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * /**
     * If not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore} then returns
     * it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore()
            throws IOException, ClassNotFoundException {
        if (cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore == null) {
            Path f;
            f = getCidsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile();
            if (Files.exists(f)) {
                cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = new HashSet<>();
            }
        }
        return cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then returns
     * it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            env.checkAndMaybeFreeMemory();
            return getCidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then returns
     * it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore()
            throws IOException, ClassNotFoundException {
        if (cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore == null) {
            Path f;
            f = getCidsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile();
            if (Files.exists(f)) {
                cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = new HashSet<>();
            }
        }
        return cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore} then
     * returns it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore} then
     * returns it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore()
            throws IOException, ClassNotFoundException {
        if (cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore == null) {
            Path f;
            f = getCidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile();
            if (Files.exists(f)) {
                cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = new HashSet<>();
            }
        }
        return cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;
    }

    /**
     * If not initialised, initialises cidsOfNewSHBEClaimsWhereClaimantIsNew If
     * not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then returns
     * it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantIsNew
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfNewSHBEClaimsWhereClaimantIsNew();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfNewSHBEClaimsWhereClaimantIsNew(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfNewSHBEClaimsWhereClaimantIsNew} then returns it.
     *
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantIsNew}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfNewSHBEClaimsWhereClaimantIsNew() throws IOException, ClassNotFoundException {
        if (cidsOfNewSHBEClaimsWhereClaimantIsNew == null) {
            Path f;
            f = getCidsOfNewSHBEClaimsWhereClaimantIsNewFile();
            if (Files.exists(f)) {
                cidsOfNewSHBEClaimsWhereClaimantIsNew = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfNewSHBEClaimsWhereClaimantIsNew = new HashSet<>();
            }
        }
        return cidsOfNewSHBEClaimsWhereClaimantIsNew;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfCottingleySpringsCaravanParkPairedClaims} then returns it.
     *
     * @return {@link #cidsOfCottingleySpringsCaravanParkPairedClaims}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCottingleySpringsCaravanParkPairedClaimIDs(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfCottingleySpringsCaravanParkPairedClaims();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCottingleySpringsCaravanParkPairedClaimIDs(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfCottingleySpringsCaravanParkPairedClaims} then returns it.
     *
     * @return {@link #cidsOfCottingleySpringsCaravanParkPairedClaims}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfCottingleySpringsCaravanParkPairedClaims() throws IOException, ClassNotFoundException {
        if (cidsOfCottingleySpringsCaravanParkPairedClaims == null) {
            Path f = getCidsOfCottingleySpringsCaravanParkPairedClaimsFile();
            if (Files.exists(f)) {
                cidsOfCottingleySpringsCaravanParkPairedClaims = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfCottingleySpringsCaravanParkPairedClaims = new HashSet<>();
            }
        }
        return cidsOfCottingleySpringsCaravanParkPairedClaims;
    }

    /**
     * If not initialised, initialises {@link #cidsHII} then returns it.
     *
     * @return {@link #cidsHII} initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsHII(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsHII();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsHII(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cidsHII} then returns it.
     *
     * @return {@link #cidsHII}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsHII()
            throws IOException, ClassNotFoundException {
        if (cidsHII == null) {
            Path f;
            f = getCidsHIIFile();
            if (Files.exists(f)) {
                cidsHII = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsHII = new HashSet<>();
            }
        }
        return cidsHII;
    }

    /**
     * If not initialised, initialises {@link #cidsHIS} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsHIS}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsHIS(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsHIS();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsHIS(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cidsHIS} then returns it.
     *
     * @return {@link #cidsHIS}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsHIS()
            throws IOException, ClassNotFoundException {
        if (cidsHIS == null) {
            Path f;
            f = getCidsHISFile();
            if (Files.exists(f)) {
                cidsHIS = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsHIS = new HashSet<>();
            }
        }
        return cidsHIS;
    }

    /**
     * If not initialised, initialises {@link #cidsHIO} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsHIO}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsHIO(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsHIO();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsHIO(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cidsHIO} then returns it.
     *
     * @return {@link #cidsHIO}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsHIO()
            throws IOException, ClassNotFoundException {
        if (cidsHIO == null) {
            Path f;
            f = getCidsHIOFile();
            if (Files.exists(f)) {
                cidsHIO = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsHIO = new HashSet<>();
            }
        }
        return cidsHIO;
    }

    /**
     * If not initialised, initialises {@link #cidsCII} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsCII}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsCII(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsCII();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsCII(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cidsCII} then returns it.
     *
     * @return {@link #cidsCII}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsCII()
            throws IOException, ClassNotFoundException {
        if (cidsCII == null) {
            Path f;
            f = getCidsCIIFile();
            if (Files.exists(f)) {
                cidsCII = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsCII = new HashSet<>();
            }
        }
        return cidsCII;
    }

    /**
     * If not initialised, initialises {@link #cidsCIS} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsCIS}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsCIS(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsCIS();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsCIS(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cidsCIS} then returns it.
     *
     * @return {@link #cidsCIS}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsCIS()
            throws IOException, ClassNotFoundException {
        if (cidsCIS == null) {
            Path f;
            f = getCidsCISFile();
            if (Files.exists(f)) {
                cidsCIS = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsCIS = new HashSet<>();
            }
        }
        return cidsCIS;
    }

    /**
     * If not initialised, initialises cidsCIO then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsCIO}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsCIO(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsCIO();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsCIO(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cidsCIO} then returns it.
     *
     * @return {@link #cidsCIO}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsCIO() throws IOException, ClassNotFoundException {
        if (cidsCIO == null) {
            Path f;
            f = getCidsCIOFile();
            if (Files.exists(f)) {
                cidsCIO = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsCIO = new HashSet<>();
            }
        }
        return cidsCIO;
    }

    /**
     * If not initialised, initialises {@link #sRecordsWithoutDRecords} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #sRecordsWithoutDRecords}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>> getSRecordsWithoutDRecords(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getSRecordsWithoutDRecords();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getSRecordsWithoutDRecords(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return {@link #sRecordsWithoutDRecords}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>> getSRecordsWithoutDRecords() throws IOException, ClassNotFoundException {
        if (sRecordsWithoutDRecords == null) {
            Path f;
            f = getSRecordsWithoutDRecordsFile();
            if (Files.exists(f)) {
                sRecordsWithoutDRecords = (Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>>) Generic_IO.readObject(f);
            } else {
                sRecordsWithoutDRecords = new HashMap<>();
            }
        }
        return sRecordsWithoutDRecords;
    }

    /**
     * If not initialised, initialises {@link #cid2CountOfSRecords} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cid2CountOfSRecords}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Integer> getCid2CountOfSRecords(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCid2CountOfSRecords();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCid2CountOfSRecords(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithoutAMappableClaimantPostcode} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsOfClaimsWithoutAMappableClaimantPostcode}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfClaimsWithoutAValidClaimantPostcode(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfClaimsWithoutAMappableClaimantPostcode();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfClaimsWithoutAValidClaimantPostcode(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return the {@link #cid2CountOfSRecords}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, Integer> getCid2CountOfSRecords()
            throws IOException, ClassNotFoundException {
        if (cid2CountOfSRecords == null) {
            Path f;
            f = getCidToCountOfSRecordsFile();
            if (Files.exists(f)) {
                cid2CountOfSRecords = (Map<SHBE_ClaimID, Integer>) Generic_IO.readObject(f);
            } else {
                cid2CountOfSRecords = new HashMap<>();
            }
        }
        return cid2CountOfSRecords;
    }

    /**
     * @return {@link #cidsOfClaimsWithoutAMappableClaimantPostcode}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfClaimsWithoutAMappableClaimantPostcode() throws IOException, ClassNotFoundException {
        if (cidsOfClaimsWithoutAMappableClaimantPostcode == null) {
            Path f;
            f = getCidsOfClaimsWithoutAMappableClaimantPostcodeFile();
            if (Files.exists(f)) {
                cidsOfClaimsWithoutAMappableClaimantPostcode = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfClaimsWithoutAMappableClaimantPostcode = new HashSet<>();
            }
        }
        return cidsOfClaimsWithoutAMappableClaimantPostcode;
    }

    /**
     * @return {@link #ym3}
     */
    public UKP_YM3 getYm3() {
        return ym3;
    }

    /**
     * @return {@link #nearestYM3ForONSPDLookup}
     */
    public UKP_YM3 getNearestYM3ForONSPDLookup() {
        return nearestYM3ForONSPDLookup;
    }

    /**
     * Write this to file.
     *
     * @throws java.io.IOException If encountered.
     */
    public void write() throws IOException {
        Generic_IO.writeObject(this, getFile());
    }

    /**
     * If dir is null, it is initialised.
     *
     * @return dir.
     * @throws java.io.IOException If encountered.
     */
    protected Path getDir() throws IOException {
        if (dir == null) {
            dir = Paths.get(env.files.getGeneratedSHBEDir().toString(),
                    getYm3().toString());
            Files.createDirectories(dir);
        }
        return dir;
    }

    /**
     * @param filename Filename.
     * @return The Path in dir given by filename.
     * @throws java.io.IOException If encountered.
     */
    public Path getFile(String filename) throws IOException {
        return Paths.get(getDir().toString(), filename);
    }

    /**
     * For loading an existing collection.
     *
     * @param env SHBE_Environment
     * @param ym3 The year and month.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_Records(SHBE_Environment env, UKP_YM3 ym3) throws IOException,
            ClassNotFoundException {
        this(env, 0, ym3);
    }

    /**
     * For loading an existing collection.
     *
     * @param env SHBE_Environment
     * @param logID The ID of the log to write to.
     * @param ym3 The year and month.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_Records(SHBE_Environment env, int logID, UKP_YM3 ym3)
            throws IOException, ClassNotFoundException {
        super(env, logID);
        this.ym3 = ym3;
        handler = this.env.handler;
        ukpData = this.env.oe.getHandler();
        nearestYM3ForONSPDLookup = ukpData.getNearestYM3ForONSPDLookup(ym3);
        env.env.log("YM3 " + ym3, logID);
        env.env.log("NearestYM3ForONSPDLookup " + nearestYM3ForONSPDLookup, logID);
        records = getRecords();
        cidsOfNewSHBEClaims = getCidsOfNewSHBEClaims(env.HOOME);
        cpids = getCpids(env.HOOME);
        ppids = getPpids(env.HOOME);
        ndpids = getNdpids(env.HOOME);
        cidsOfCottingleySpringsCaravanParkPairedClaims = getCottingleySpringsCaravanParkPairedClaimIDs(env.HOOME);
        cidsHII = getCidsHII(env.HOOME);
        cidsHIS = getCidsHIS(env.HOOME);
        cidsHIO = getCidsHIO(env.HOOME);
        cidsCII = getCidsCII(env.HOOME);
        cidsCIS = getCidsCIS(env.HOOME);
        cidsCIO = getCidsCIO(env.HOOME);
        sRecordsWithoutDRecords = getSRecordsWithoutDRecords(env.HOOME);
        cid2CountOfSRecords = getCid2CountOfSRecords(env.HOOME);
        cidsOfClaimsWithoutAMappableClaimantPostcode = getCidsOfClaimsWithoutAValidClaimantPostcode(env.HOOME);
        cid2cpid = getCid2cpid(env.HOOME);
        cid2ppid = getCid2ppid(env.HOOME);
        cid2dpids = getCid2dpids(env.HOOME);
        cid2ndpids = getCid2ndpids(env.HOOME);
        cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(env.HOOME);
        cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(env.HOOME);
        cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(env.HOOME);
        cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim = getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaim(env.HOOME);
        cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = getCidsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(env.HOOME);
        pid2cidsOfClaimantsInMultipleClaimsInAMonth = getPid2cidOfClaimantsInMultipleClaimsInAMonth(env.HOOME);
        pid2cidsOfPartnersInMultipleClaimsInAMonth = getPid2cidsOfPartnersInMultipleClaimsInAMonth(env.HOOME);
        pid2cidsOfNonDependentsInMultipleClaimsInAMonth = getPid2cidOfNonDependentsInMultipleClaimsInAMonth(env.HOOME);
        cid2postcodeID = getCid2postcodeIDLookup(env.HOOME);
        cid2tt = getCid2tt(env.HOOME);
        loadSummary = getLoadSummary(env.HOOME);
        recordIDsNotLoaded = getRecordIDsNotLoaded(env.HOOME);
        cidsOfInvalidClaimantNINOClaims = getCidsOfInvalidClaimantNINOClaims(env.HOOME);
        claimantPostcodesUnmappable = getClaimantPostcodesUnmappable(env.HOOME);
        claimantPostcodesModified = getClaimantPostcodesModified(env.HOOME);
        claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(env.HOOME);
        cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(env.HOOME);
    }

    /**
     * Loads Data from source.
     *
     * @param env SHBE_Environment
     * @param logID The ID of the log to write to.
     * @param inputFilename inputFilename
     * @param inputDirectory inputDirectory
     * @param lym3 LatestYM3ForONSPDFormat
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public SHBE_Records(SHBE_Environment env, int logID, Path inputDirectory,
            String inputFilename, UKP_YM3 lym3)
            throws IOException, ClassNotFoundException, Exception {
        super(env, logID);
        handler = env.handler;
        inputFile = Paths.get(inputDirectory.toString(), inputFilename);
        ym3 = handler.getYM3(inputFilename);
        ukpData = this.env.oe.getHandler();
        nearestYM3ForONSPDLookup = ukpData.getNearestYM3ForONSPDLookup(ym3);
        records = new HashMap<>();
        cids = new HashSet<>();
        cidsOfNewSHBEClaims = new HashSet<>();
        cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = new HashSet<>();
        cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = new HashSet<>();
        cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = new HashSet<>();
        cidsOfNewSHBEClaimsWhereClaimantIsNew = new HashSet<>();
        cpids = new HashSet<>();
        ppids = new HashSet<>();
        ndpids = new HashSet<>();
        cidsOfCottingleySpringsCaravanParkPairedClaims = new HashSet<>();
        cidsHII = new HashSet<>();
        cidsHIS = new HashSet<>();
        cidsHIO = new HashSet<>();
        cidsCII = new HashSet<>();
        cidsCIS = new HashSet<>();
        cidsCIO = new HashSet<>();
        sRecordsWithoutDRecords = new HashMap<>();
        cid2CountOfSRecords = new HashMap<>();
        cidsOfClaimsWithoutAMappableClaimantPostcode = new HashSet<>();
        cid2cpid = new HashMap<>();
        cid2ppid = new HashMap<>();
        cid2dpids = new HashMap<>();
        cid2ndpids = new HashMap<>();
        cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = new HashSet<>();
        cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = new HashSet<>();
        cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = new HashSet<>();
        cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim = new HashSet<>();
        cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = new HashSet<>();
        pid2cidsOfClaimantsInMultipleClaimsInAMonth = new HashMap<>();
        pid2cidsOfPartnersInMultipleClaimsInAMonth = new HashMap<>();
        pid2cidsOfNonDependentsInMultipleClaimsInAMonth = new HashMap<>();
        cid2postcodeID = new HashMap<>();
        cid2tt = new HashMap<>();
        loadSummary = new HashMap<>();
        recordIDsNotLoaded = new ArrayList<>();
        cidsOfInvalidClaimantNINOClaims = new HashSet<>();
        claimantPostcodesUnmappable = new HashMap<>();
        claimantPostcodesModified = new HashMap<>();
        claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = new HashMap<>();
        cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = new HashSet<>();
        env.env.log("----------------------", logID);
        env.env.log("Load " + ym3, logID);
        env.env.log("----------------------", logID);
        env.env.log("NearestYM3ForONSPDLookup " + nearestYM3ForONSPDLookup, logID);
        env.env.log("LatestYM3ForONSPDLookup " + lym3, logID);
        if (!lym3.equals(nearestYM3ForONSPDLookup)) {
            env.env.log("The " + lym3 + " ONSPD may be used "
                    + "if the Claimant Postcode does not have a lookup in the "
                    + nearestYM3ForONSPDLookup + " ONSPD.", logID);
        }
        /**
         * Check the postcodes against these to see if we should report them
         * again as unmappable.
         */
        SHBE_CorrectedPostcodes correctedPostcodes;
        Map<String, ArrayList<String>> claimRefToOriginalPostcodes;
        Map<String, ArrayList<String>> claimRefToCorrectedPostcodes;
        Set<String> postcodesCheckedAsMappable;
        //Map<String, Set<String>> UnmappableToMappablePostcodes;
        /**
         * Mapping of National Insurance Numbers to SHBE_NINOID.
         */
        Map<String, SHBE_NINOID> n2nid;
        /**
         * SHBE_NINOID to National Insurance Numbers.
         */
        Map<SHBE_NINOID, String> nid2n;
        /**
         * Mapping of Dates of Birth to simple SHBE_IDs.
         */
        Map<String, SHBE_DOBID> d2did;
        /**
         * Mapping of SHBE_IDs to Dates of Birth.
         */
        Map<SHBE_DOBID, String> did2d;
        /**
         * Mapping of Unit Postcodes to simple SHBE_IDs.
         */
        Map<String, UKP_RecordID> p2pid;
        /**
         * Mapping of SHBE_ID to a Unit Postcode.
         */
        Map<UKP_RecordID, String> pid2p;
        /**
         * Mapping of SHBE_ID to a Unit Postcode.
         */
        Map<UKP_RecordID, ONSPD_Point> pid2point;
        /**
         * Mapping of ClaimRef String to Claim SHBE_ID.
         */
        Map<String, SHBE_ClaimID> c2cid;
        /**
         * Mapping of Claim SHBE_ID to ClaimRef String.
         */
        Map<SHBE_ClaimID, String> cid2c;

        /**
         * SHBE_PersonID of All Claimants
         */
        Set<SHBE_PersonID> allClaimantPersonIDs;

        /**
         * SHBE_PersonID of All Partners
         */
        Set<SHBE_PersonID> allPartnerPersonIDs;

        /**
         * SHBE_PersonID of All Non-Dependents
         */
        Set<SHBE_PersonID> allNonDependentIDs;

        /**
         * All SHBE_PersonID to ClaimIDs Lookup
         */
        Map<SHBE_PersonID, Set<SHBE_ClaimID>> personID2ClaimIDs;

        /**
         * Initialise mappings from SHBE_Handler.
         */
        correctedPostcodes = handler.getCorrectedPostcodes();
        claimRefToOriginalPostcodes = correctedPostcodes.getClaimRefToOriginalPostcodes();
        claimRefToCorrectedPostcodes = correctedPostcodes.getClaimRefToCorrectedPostcodes();
        postcodesCheckedAsMappable = correctedPostcodes.getPostcodesCheckedAsMappable();
        //UnmappableToMappablePostcodes = SHBE_CorrectedPostcodes.getUnmappableToMappablePostcodes();

        n2nid = handler.getN2nid();
        nid2n = handler.getNid2n();
        d2did = handler.getD2did();
        did2d = handler.getDid2d();
        allClaimantPersonIDs = handler.getCpids();
        allPartnerPersonIDs = handler.getPpids();
        allNonDependentIDs = handler.getNdpids();
        personID2ClaimIDs = handler.getPid2cids();
        p2pid = handler.getP2pid();
        pid2p = handler.getPid2p();
        pid2point = handler.getPid2point(ym3);
        c2cid = handler.getC2cid();
        cid2c = handler.getCid2c();
        // Initialise statistics
        int countOfNewMappableClaimantPostcodes = 0;
        int countOfMappableClaimantPostcodes = 0;
        int countOfNewClaimantPostcodes = 0;
        int countOfNonMappableClaimantPostcodes = 0;
        int countOfValidFormatClaimantPostcodes = 0;
        int totalCouncilTaxBenefitClaims = 0;
        int totalCouncilTaxAndHousingBenefitClaims = 0;
        int totalHousingBenefitClaims = 0;
        int countSRecords = 0;
        int sRecordNotLoadedCount = 0;
        int numberOfIncompleteDRecords = 0;
        long totalIncome;
        long grandTotalIncome = 0;
        int totalIncomeGreaterThanZeroCount = 0;
        long totalWeeklyEligibleRentAmount;
        long grandTotalWeeklyEligibleRentAmount = 0;
        int totalWeeklyEligibleRentAmountGreaterThanZeroCount = 0;
        // Read data
        env.env.log("<Read data>", logID);
        int lineCount;
        SHBE_ClaimID claimID;
        try (BufferedReader br = Generic_IO.getBufferedReader(inputFile)) {
            StreamTokenizer st = new StreamTokenizer(br);
            Generic_IO.setStreamTokenizerSyntax5(st);
            st.wordChars('`', '`');
            st.wordChars('*', '*');
            String line;
            long recordID = 0;
            lineCount = 0;
            // Declare Variables
            SHBE_S_Record sRecord;
            String claimRef;
            SHBE_D_Record dRecord;
            int tenancyType;
            boolean doLoop;
            SHBE_Record record;
            int statusOfHBClaimAtExtractDate;
            int statusOfCTBClaimAtExtractDate;
            String postcode;
            String claimantNINO;
            String claimantDOB;
            SHBE_PersonID claimantPersonID;
            boolean addToNew;
            Object key;
            SHBE_ClaimID otherClaimID;
            SHBE_Record otherRecord;
            /**
             * There are two types of SHBE data encountered so far. Each has
             * slightly different field definitions.
             */
            int type = readAndCheckFirstLine(inputDirectory, inputFilename);
            br.readLine();
            // Read collections
            int tokenType = st.nextToken();
            int counter = 0;
            while (tokenType != StreamTokenizer.TT_EOF) {
                switch (tokenType) {
                    case StreamTokenizer.TT_EOL:
                        if (counter % 10000 == 0) {
                            //env.env.log(line);
                            env.env.log("Read line " + counter, logID);
                        }
                        counter++;
                        break;
                    case StreamTokenizer.TT_WORD:
                        line = st.sval;
                        if (line.startsWith("S")) {
                            try {
                                sRecord = new SHBE_S_Record(
                                        env, recordID, type, line);
                                claimRef = sRecord.getClaimRef();
                                if (claimRef == null) {
                                    env.env.log("SRecord without a ClaimRef "
                                            + this.getClass().getName()
                                            + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                    env.env.log("SRecord: " + sRecord.toString(), logID);
                                    env.env.log("Line: " + line, logID);
                                    env.env.log("RecordID " + recordID, logID);
                                    recordIDsNotLoaded.add(recordID);
                                    sRecordNotLoadedCount++;
                                } else {
                                    claimID = handler.getCidAddIfNeeded(claimRef);
                                    ArrayList<SHBE_S_Record> recs;
                                    recs = sRecordsWithoutDRecords.get(claimID);
                                    if (recs == null) {
                                        recs = new ArrayList<>();
                                        sRecordsWithoutDRecords.put(claimID, recs);
                                    }
                                    recs.add(sRecord);
                                }
                            } catch (Exception e) {
                                env.env.log("Line not loaded in "
                                        + this.getClass().getName()
                                        + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                env.env.log("Line: " + line, logID);
                                env.env.log("RecordID " + recordID, logID);
                                env.env.log(e.getLocalizedMessage(), logID);
                                recordIDsNotLoaded.add(recordID);
                            }
                            countSRecords++;
                        } else if (line.startsWith("D")) {
                            try {
                                dRecord = new SHBE_D_Record(
                                        env, recordID, type, line);
                                /**
                                 * For the time being, if for some reason the
                                 * record does not load correctly, then do not
                                 * load this record. Ideally those that do not
                                 * load will be investigated and a solution for
                                 * loading them found.
                                 */
                                tenancyType = dRecord.getTenancyType();
                                if (tenancyType == 0) {
                                    env.env.log("Incomplete record "
                                            + this.getClass().getName()
                                            + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                    env.env.log("Line: " + line, logID);
                                    env.env.log("RecordID " + recordID, logID);
                                    numberOfIncompleteDRecords++;
                                    recordIDsNotLoaded.add(recordID);
                                    lineCount++;
                                    recordID++;
                                    break;
                                } else {
                                    claimRef = dRecord.getClaimRef();
                                    if (claimRef == null) {
                                        recordIDsNotLoaded.add(recordID);
                                    } else {
                                        doLoop = false;
                                        claimID = handler.getIDAddIfNeeded(claimRef,
                                                c2cid,
                                                cid2c,
                                                cids,
                                                cidsOfNewSHBEClaims);
                                        if (handler.isHBClaim(dRecord)) {
                                            if (dRecord.getCouncilTaxBenefitClaimReferenceNumber() != null) {
                                                totalCouncilTaxAndHousingBenefitClaims++;
                                            } else {
                                                totalHousingBenefitClaims++;
                                            }
                                        }
                                        if (handler.isCTBOnlyClaim(dRecord)) {
                                            totalCouncilTaxBenefitClaims++;
                                        }
                                        /**
                                         * Get or initialise SHBE_Record record
                                         */
                                        record = records.get(claimID);
                                        if (record == null) {
                                            record = new SHBE_Record(
                                                    env, claimID, dRecord);
                                            records.put(claimID, record);
                                            doLoop = true;
                                        } else {
                                            env.env.log("Two records have the same ClaimRef "
                                                    + this.getClass().getName()
                                                    + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                            env.env.log("Line: " + line, logID);
                                            env.env.log("RecordID " + recordID, logID);
                                            env.env.log("ClaimRef " + claimRef, logID);
                                        }
                                        statusOfHBClaimAtExtractDate = dRecord.getStatusOfHBClaimAtExtractDate();
                                        /**
                                         * 0 = Other; 1 = InPayment; 2 =
                                         * Suspended.
                                         */
                                        switch (statusOfHBClaimAtExtractDate) {
                                            case 0: {
                                                cidsHIO.add(claimID);
                                                break;
                                            }
                                            case 1: {
                                                cidsHII.add(claimID);
                                                break;
                                            }
                                            case 2: {
                                                cidsHIS.add(claimID);
                                                break;
                                            }
                                            default:
                                                env.env.log("Unexpected StatusOfHBClaimAtExtractDate "
                                                        + this.getClass().getName()
                                                        + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                                env.env.log("Line: " + line, logID);
                                                env.env.log("RecordID " + recordID, logID);
                                                break;
                                        }
                                        statusOfCTBClaimAtExtractDate = dRecord.getStatusOfCTBClaimAtExtractDate();
                                        /**
                                         * 0 = Other; 1 = InPayment; 2 =
                                         * Suspended.
                                         */
                                        switch (statusOfCTBClaimAtExtractDate) {
                                            case 0: {
                                                cidsCIO.add(claimID);
                                                break;
                                            }
                                            case 1: {
                                                cidsCII.add(claimID);
                                                break;
                                            }
                                            case 2: {
                                                cidsCIS.add(claimID);
                                                break;
                                            }
                                            default:
                                                env.env.log("Unexpected StatusOfCTBClaimAtExtractDate "
                                                        + this.getClass().getName()
                                                        + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                                env.env.log("Line: " + line, logID);
                                                env.env.log("RecordID " + recordID, logID);
                                                break;
                                        }
                                        if (doLoop) {
                                            postcode = dRecord.getClaimantsPostcode();
                                            record.claimPostcodeF = ukpData.formatPostcode(postcode);
                                            record.claimPostcodeFManModified = false;
                                            record.claimPostcodeFAutoModified = false;
                                            // Do man modifications (modifications using lookups provided by LCC based on a manual checking of addresses)
                                            if (claimRefToOriginalPostcodes.keySet().contains(claimRef)) {
                                                ArrayList<String> priginalPostcodes;
                                                priginalPostcodes = claimRefToOriginalPostcodes.get(claimRef);
                                                if (priginalPostcodes.contains(record.claimPostcodeF)) {
                                                    ArrayList<String> CorrectedPostcodes;
                                                    CorrectedPostcodes = claimRefToCorrectedPostcodes.get(claimRef);
                                                    record.claimPostcodeF = CorrectedPostcodes.get(priginalPostcodes.indexOf(record.claimPostcodeF));
                                                    record.claimPostcodeFManModified = true;
                                                }
                                            } else {
                                                // Do auto modifications ()
                                                if (record.claimPostcodeF.length() > 5) {
                                                    /**
                                                     * Remove any 0 which
                                                     * probably should not be
                                                     * there in the first part
                                                     * of the postcode. For
                                                     * example "LS02 9JT" should
                                                     * probably be "LS2 9JT".
                                                     */
                                                    if (record.claimPostcodeF.charAt(record.claimPostcodeF.length() - 5) == '0') {
                                                        //System.out.println("record.claimPostcodeF " + record.claimPostcodeF);
                                                        record.claimPostcodeF = record.claimPostcodeF.replaceFirst("0", "");
                                                        //System.out.println("Postcode " + Postcode);
                                                        //System.out.println("record.claimPostcodeF " + record.claimPostcodeF);
                                                        record.claimPostcodeFAutoModified = true;
                                                    }
                                                    /**
                                                     * Change any "O" which
                                                     * should be a "0" in the
                                                     * second part of the
                                                     * postcode. For example
                                                     * "LS2 OJT" should probably
                                                     * be "LS2 0JT".
                                                     */
                                                    if (record.claimPostcodeF.charAt(record.claimPostcodeF.length() - 3) == 'O') {
                                                        //System.out.println("record.claimPostcodeF " + record.claimPostcodeF);
                                                        record.claimPostcodeF = record.claimPostcodeF.substring(0, record.claimPostcodeF.length() - 3)
                                                                + "0" + record.claimPostcodeF.substring(record.claimPostcodeF.length() - 2);
                                                        //System.out.println("Postcode " + Postcode);
                                                        //System.out.println("record.claimPostcodeF " + record.claimPostcodeF);
                                                        record.claimPostcodeFAutoModified = true;
                                                    }
                                                }
                                            }
                                            // Check if record.claimPostcodeF is mappable
                                            boolean isMappablePostcode;
                                            isMappablePostcode = ukpData.isMappablePostcode(nearestYM3ForONSPDLookup, record.claimPostcodeF);
                                            boolean isMappablePostcodeLastestYM3 = false;
                                            if (!isMappablePostcode) {
                                                isMappablePostcodeLastestYM3 = ukpData.isMappablePostcode(lym3, record.claimPostcodeF);
                                                if (isMappablePostcodeLastestYM3) {
                                                    env.env.log("Postcode " + postcode + " is not in the " + nearestYM3ForONSPDLookup + " ONSPD, "
                                                            + "but is in the " + lym3 + " ONSPD!", logID);
                                                    isMappablePostcode = isMappablePostcodeLastestYM3;
                                                }
                                            }
                                            // For those that are mappable having been modified, store the modification
                                            if (isMappablePostcode) {
                                                if (record.claimPostcodeFAutoModified) {
                                                    String claimPostcodeFNoSpaces = record.claimPostcodeF.replaceAll(" ", "");
                                                    if (!postcode.replaceAll(" ", "").equalsIgnoreCase(claimPostcodeFNoSpaces)) {
                                                        int l;
                                                        l = record.claimPostcodeF.length();
                                                        String[] p;
                                                        p = new String[2];
                                                        p[0] = postcode;
                                                        p[1] = claimPostcodeFNoSpaces.substring(0, l - 3) + " " + claimPostcodeFNoSpaces.substring(l - 3);
                                                        claimantPostcodesModified.put(claimID, p);
                                                    }
                                                }
                                            }
                                            record.claimPostcodeFValidPostcodeFormat = ukpData.checker.isValidPostcodeUnit(record.claimPostcodeF);
                                            if (p2pid.containsKey(record.claimPostcodeF)) {
                                                countOfMappableClaimantPostcodes++;
                                                record.claimPostcodeFMappable = true;
                                                record.postcodeID = p2pid.get(record.claimPostcodeF);
                                                // Add the point to the lookup
                                                ONSPD_Point AGDT_Point;
                                                AGDT_Point = ukpData.getPointFromPostcodeNew(nearestYM3ForONSPDLookup,
                                                        UKP_Data.TYPE_UNIT,
                                                        record.claimPostcodeF);
                                                pid2point.put(record.postcodeID, AGDT_Point);
                                            } else if (isMappablePostcode) {
                                                countOfMappableClaimantPostcodes++;
                                                countOfNewClaimantPostcodes++;
                                                countOfNewMappableClaimantPostcodes++;
                                                record.claimPostcodeFMappable = true;
                                                record.postcodeID = handler.getPidAddIfNeeded(record.claimPostcodeF,
                                                        p2pid,
                                                        pid2p);
                                                // Add the point to the lookup
                                                ONSPD_Point p;
                                                if (isMappablePostcodeLastestYM3) {
                                                    p = ukpData.getPointFromPostcodeNew(lym3,
                                                            UKP_Data.TYPE_UNIT,
                                                            record.claimPostcodeF);
                                                } else {
                                                    p = ukpData.getPointFromPostcodeNew(nearestYM3ForONSPDLookup,
                                                            UKP_Data.TYPE_UNIT,
                                                            record.claimPostcodeF);
                                                }
                                                pid2point.put(record.postcodeID, p);
                                            } else {
                                                countOfNonMappableClaimantPostcodes++;
                                                countOfNewClaimantPostcodes++;
                                                if (record.claimPostcodeFValidPostcodeFormat) {
                                                    countOfValidFormatClaimantPostcodes++;
                                                }
                                                record.claimPostcodeFMappable = false;
                                                cidsOfClaimsWithoutAMappableClaimantPostcode.add(claimID);
                                                boolean PostcodeCheckedAsMappable;
                                                PostcodeCheckedAsMappable = postcodesCheckedAsMappable.contains(record.claimPostcodeF);
                                                if (PostcodeCheckedAsMappable) {
                                                    claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes.put(claimID, postcode);
                                                } else {
                                                    // Store unmappable claimant postcode.
                                                    claimantPostcodesUnmappable.put(claimID, postcode);
                                                }
                                            }
                                            cid2postcodeID.put(claimID, record.postcodeID);
                                            cid2tt.put(claimID, tenancyType);
                                            totalIncome = handler.getClaimantsAndPartnersIncomeTotal(dRecord);
                                            grandTotalIncome += totalIncome;
                                            if (totalIncome > 0) {
                                                totalIncomeGreaterThanZeroCount++;
                                            }
                                            totalWeeklyEligibleRentAmount = dRecord.getWeeklyEligibleRentAmount();
                                            grandTotalWeeklyEligibleRentAmount += totalWeeklyEligibleRentAmount;
                                            if (totalWeeklyEligibleRentAmount > 0) {
                                                totalWeeklyEligibleRentAmountGreaterThanZeroCount++;
                                            }
                                        }
                                        /**
                                         * Get ClaimantSHBE_PersonID
                                         */
                                        claimantNINO = dRecord.getClaimantsNationalInsuranceNumber();
                                        if (claimantNINO.trim().equalsIgnoreCase("")
                                                || claimantNINO.trim().startsWith("XX999")) {
                                            cidsOfInvalidClaimantNINOClaims.add(claimID);
                                        }
                                        claimantDOB = dRecord.getClaimantsDateOfBirth();
                                        claimantPersonID = handler.getPersonID(
                                                claimantNINO,
                                                claimantDOB,
                                                n2nid,
                                                nid2n,
                                                d2did,
                                                did2d);
                                        /**
                                         * If this is a new claim then add to
                                         * appropriate index if the person was
                                         * previously a Claimant, Partner,
                                         * NonDependent or if the Person is
                                         * "new".
                                         */
                                        if (cidsOfNewSHBEClaims.contains(claimID)) {
                                            addToNew = true;
                                            if (allClaimantPersonIDs.contains(claimantPersonID)) {
                                                cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (allPartnerPersonIDs.contains(claimantPersonID)) {
                                                cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (allNonDependentIDs.contains(claimantPersonID)) {
                                                cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (addToNew) {
                                                cidsOfNewSHBEClaimsWhereClaimantIsNew.add(claimID);
                                            }
                                        }
                                        /**
                                         * If ClaimantSHBE_PersonID is already
                                         * in ClaimIDToClaimantPersonIDLookup.
                                         * then ClaimantSHBE_PersonID has
                                         * multiple claims in a month.
                                         */
                                        if (cid2cpid.containsValue(claimantPersonID)) {
                                            /**
                                             * This claimant is in multiple
                                             * claims in this SHBE data. This
                                             * can happen and is expected to
                                             * happen for some travellers. Some
                                             * claimants have their NINO set to
                                             * a default like XX999999XX and it
                                             * is possible that multiple have
                                             * this default and the same date of
                                             * birth. In such cases this program
                                             * does not distinguish them, but
                                             * there may be other
                                             * characteristics such as gender,
                                             * age and ethnicity which could be
                                             * used to help differentiate.
                                             */
                                            key = Generic_Collections.getKeys(cid2cpid,
                                                    claimantPersonID).stream().findFirst();
                                            postcode = dRecord.getClaimantsPostcode();
                                            if (key != null) {
                                                otherClaimID = (SHBE_ClaimID) key;
                                                //String otherClaimRef = ClaimIDToClaimRefLookup.get(otherClaimID);
                                                // Treat those paired records for Cottingley Springs Caravan Park differently
                                                if (postcode.equalsIgnoreCase(SHBE_Strings.CottingleySpringsCaravanParkPostcode)) {
//                                                    env.log("Cottingley Springs Caravan Park "
//                                                            + strings.CottingleySpringsCaravanParkPostcode
//                                                            + " ClaimRef " + ClaimRef + " paired with " + otherClaimRef
//                                                            + " one claim is for the pitch, the other is for rent of "
//                                                            + "a mobile home. ");
                                                    cidsOfCottingleySpringsCaravanParkPairedClaims.add(claimID);
                                                    cidsOfCottingleySpringsCaravanParkPairedClaims.add(otherClaimID);
                                                } else {
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    env.log(
//                                                            "Claimant with NINO " + ClaimantNINO
//                                                            + " DoB " + ClaimantDOB
//                                                            + " has mulitple claims. "
//                                                            + "The Claimant has had a second claim set up and the "
//                                                            + "previous claim is still on the system for some reason.");
//                                                    env.log("Current ClaimRef " + ClaimRef);
//                                                    env.log("Other ClaimRef " + otherClaimRef);
                                                    otherRecord = records.get(otherClaimID);
                                                    if (otherRecord == null) {
                                                        env.env.log("Unexpected error xx: This should not happen. "
                                                                + this.getClass().getName()
                                                                + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                                    } else {
//                                                        env.log("This D Record");
//                                                        env.log(dRecord.toStringBrief());
//                                                        env.log("Other D Record");
//                                                        env.log(otherRecord.dRecord.toStringBrief());
                                                        /**
                                                         * Add to
                                                         * ClaimantsWithMultipleClaimsInAMonth.
                                                         */
                                                        cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(claimID);
                                                        cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(otherRecord.getClaimID());
                                                        Set<SHBE_ClaimID> set;
                                                        if (pid2cidsOfClaimantsInMultipleClaimsInAMonth.containsKey(claimantPersonID)) {
                                                            set = pid2cidsOfClaimantsInMultipleClaimsInAMonth.get(claimantPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            pid2cidsOfClaimantsInMultipleClaimsInAMonth.put(claimantPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                    }
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                }
                                            }
                                        }
                                        /**
                                         * If ClaimantPersonID is in
                                         * ClaimIDToPartnerPersonIDLookup, then
                                         * claimant is a partner in another
                                         * claim. Add to
                                         * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim
                                         * and
                                         * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.
                                         */
                                        if (cid2ppid.containsValue(claimantPersonID)) {
                                            /**
                                             * Ignore if this is a
                                             * CottingleySpringsCaravanParkPairedClaimIDs.
                                             * It may be that there are partners
                                             * shared in these claims, but such
                                             * a thing is ignored for now.
                                             */
                                            if (!cidsOfCottingleySpringsCaravanParkPairedClaims.contains(claimID)) {
                                                /**
                                                 * If Claimant is a Partner in
                                                 * another claim add to
                                                 * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim
                                                 * and
                                                 * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.
                                                 */
                                                key = Generic_Collections.getKeys(cid2ppid,
                                                        claimantPersonID).stream().findFirst();
                                                if (key != null) {
                                                    otherClaimID = (SHBE_ClaimID) key;
                                                    cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(otherClaimID);
                                                }
                                                cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(claimID);
//                                                env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                env.log("Claimant with NINO " + ClaimantNINO
//                                                        + " DOB " + ClaimantDOB
//                                                        + " in ClaimRef " + ClaimRef
//                                                        + " is a Partner in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                            }
                                        }
                                        SHBE_PersonID partnerPersonID;
                                        partnerPersonID = null;
                                        if (dRecord.getPartnerFlag() > 0) {
                                            /**
                                             * Add Partner.
                                             */
                                            partnerPersonID = handler.getPersonID(
                                                    dRecord.getPartnersNationalInsuranceNumber(),
                                                    dRecord.getPartnersDateOfBirth(),
                                                    n2nid,
                                                    nid2n,
                                                    d2did,
                                                    did2d);
                                            /**
                                             * If Partner is a Partner in
                                             * another claim add to
                                             * ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim
                                             * and
                                             * PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                                             */
                                            if (cid2ppid.containsValue(partnerPersonID)) {
                                                /*
                                                    * Ignore if this is a cidsOfCottingleySpringsCaravanParkPairedClaims.
                                                    * It may be that there are partners shared in these claims, but such
                                                    * a thing is ignored for now.
                                                 */
                                                if (!cidsOfCottingleySpringsCaravanParkPairedClaims.contains(claimID)) {
                                                    key = Generic_Collections.getKeys(cid2ppid,
                                                            partnerPersonID).stream().findFirst();
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ClaimID) key;
                                                        Set<SHBE_ClaimID> set;
                                                        if (pid2cidsOfPartnersInMultipleClaimsInAMonth.containsKey(partnerPersonID)) {
                                                            set = pid2cidsOfPartnersInMultipleClaimsInAMonth.get(partnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            pid2cidsOfPartnersInMultipleClaimsInAMonth.put(partnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(claimID);
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    env.log("Partner with NINO " + NINOIDToNINOLookup.get(PartnerPersonID.getNINO_ID())
//                                                            + " DOB " + DOBIDToDOBLookup.get(PartnerPersonID.getDOB_ID())
//                                                            + " in ClaimRef " + ClaimRef
//                                                            + " is a Partner in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                }
                                            }
                                            /**
                                             * If Partner is a Claimant in
                                             * another claim add to
                                             * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim
                                             * and
                                             * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.
                                             */
                                            if (cid2cpid.containsValue(partnerPersonID)) {
                                                /**
                                                 * Ignore if this is a
                                                 * CottingleySpringsCaravanParkPairedClaimIDs.
                                                 * It may be that there are
                                                 * partners shared in these
                                                 * claims, but such a thing is
                                                 * ignored for now.
                                                 */
                                                if (!cidsOfCottingleySpringsCaravanParkPairedClaims.contains(claimID)) {
                                                    key = Generic_Collections.getKeys(cid2cpid,
                                                            partnerPersonID).stream().findFirst();
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ClaimID) key;
                                                        Set<SHBE_ClaimID> set;
                                                        if (pid2cidsOfPartnersInMultipleClaimsInAMonth.containsKey(partnerPersonID)) {
                                                            set = pid2cidsOfPartnersInMultipleClaimsInAMonth.get(partnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            pid2cidsOfPartnersInMultipleClaimsInAMonth.put(partnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        if (pid2cidsOfClaimantsInMultipleClaimsInAMonth.containsKey(partnerPersonID)) {
                                                            set = pid2cidsOfClaimantsInMultipleClaimsInAMonth.get(partnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            pid2cidsOfClaimantsInMultipleClaimsInAMonth.put(partnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(claimID);
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    env.log("Partner with NINO " + NINOIDToNINOLookup.get(PartnerPersonID.getNINO_ID())
//                                                            + " DOB " + DOBIDToDOBLookup.get(PartnerPersonID.getDOB_ID())
//                                                            + " in ClaimRef " + ClaimRef
//                                                            + " is a Claimant in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                }
                                                cid2ppid.put(claimID, partnerPersonID);
                                            }
                                        }
                                        /**
                                         * Add to
                                         * ClaimIDToClaimantPersonIDLookup.
                                         */
                                        cid2cpid.put(claimID, claimantPersonID);

                                        /**
                                         * Add to AllClaimantPersonIDs and
                                         * AllPartnerPersonIDs.
                                         */
                                        allClaimantPersonIDs.add(claimantPersonID);
                                        cpids.add(claimantPersonID);
                                        Generic_Collections.addToMap(personID2ClaimIDs, claimantPersonID, claimID);
                                        if (partnerPersonID != null) {
                                            allPartnerPersonIDs.add(partnerPersonID);
                                            ppids.add(partnerPersonID);
                                            cid2ppid.put(claimID, partnerPersonID);
                                            Generic_Collections.addToMap(personID2ClaimIDs, partnerPersonID, claimID);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                env.env.log(line, logID);
                                env.env.log("RecordID " + recordID, logID);
                                env.env.log(e.getLocalizedMessage(), logID);
                                recordIDsNotLoaded.add(recordID);
                            }
                        }
                        lineCount++;
                        recordID++;
                        break;
                }
                tokenType = st.nextToken();
            }
            env.env.log("</Read data>", logID);
        }

        /**
         * Add SRecords to Records. Add ClaimantSHBE_IDs from SRecords.
         */
        //SHBE_ClaimID claimID;
        SHBE_Record SHBE_Record;
        Iterator<SHBE_ClaimID> ite;
        env.env.log("<Add SRecords>", logID);
        ite = records.keySet().iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            SHBE_Record = records.get(claimID);
            initSRecords(handler, SHBE_Record, n2nid,
                    nid2n, d2did, did2d,
                    allNonDependentIDs, personID2ClaimIDs,
                    cid2c);
        }
        env.env.log("</Add SRecords>", logID);

        env.env.log("<Summary Statistics>", logID);
        /**
         * Add statistics to LoadSummary.
         */
        /**
         * Statistics on New SHBE Claims
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaims,
                cidsOfNewSHBEClaims.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore,
                cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore,
                cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore,
                cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantIsNew,
                cidsOfNewSHBEClaimsWhereClaimantIsNew.size());
        /**
         * Statistics on Postcodes
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewClaimantPostcodes,
                countOfNewClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewValidMappableClaimantPostcodes,
                countOfNewMappableClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfMappableClaimantPostcodes,
                countOfMappableClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfNonMappableClaimantPostcodes,
                countOfNonMappableClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfInvalidFormatClaimantPostcodes,
                countOfValidFormatClaimantPostcodes);
        /**
         * General count statistics
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaims, records.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfCTBClaims,
                totalCouncilTaxBenefitClaims);
        addLoadSummaryCount(SHBE_Strings.s_CountOfCTBAndHBClaims,
                totalCouncilTaxAndHousingBenefitClaims);
        addLoadSummaryCount(SHBE_Strings.s_CountOfHBClaims,
                totalHousingBenefitClaims);
        addLoadSummaryCount(SHBE_Strings.s_CountOfRecords, records.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfSRecords, countSRecords);
        addLoadSummaryCount(SHBE_Strings.s_CountOfSRecordsNotLoaded,
                sRecordNotLoadedCount);
        addLoadSummaryCount(SHBE_Strings.s_CountOfIncompleteDRecords,
                numberOfIncompleteDRecords);
        addLoadSummaryCount(SHBE_Strings.s_CountOfRecordIDsNotLoaded,
                recordIDsNotLoaded.size());
        Set<SHBE_PersonID> set;
        Set<SHBE_PersonID> allSet;
        allSet = new HashSet<>();
        /**
         * Claimants
         */
        set = new HashSet<>();
        set.addAll(cid2cpid.values());
        allSet.addAll(set);
        addLoadSummaryCount(SHBE_Strings.s_CountOfUniqueClaimants, set.size());
        /**
         * Partners
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartners,
                cid2ppid.size());
        set = handler.getUniquePersonIDs0(cid2ppid);
        allSet.addAll(set);
        addLoadSummaryCount(SHBE_Strings.s_CountOfUniquePartners, set.size());
        /**
         * Dependents
         */
        int nDependents;
        nDependents = Generic_Collections.getCountInt(cid2dpids);
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfDependentsInAllClaims,
                nDependents);
        set = handler.getUniquePersonIDs(cid2dpids);
        allSet.addAll(set);
        int CountOfUniqueDependents = set.size();
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfUniqueDependents,
                CountOfUniqueDependents);
        /**
         * NonDependents
         */
        int nNonDependents;
        nNonDependents = Generic_Collections.getCountInt(cid2ndpids);
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfNonDependentsInAllClaims,
                nNonDependents);
        set = handler.getUniquePersonIDs(cid2ndpids);
        allSet.addAll(set);
        int CountOfUniqueNonDependents = set.size();
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfUniqueNonDependents,
                CountOfUniqueNonDependents);
        /**
         * All individuals
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfIndividuals, allSet.size());
        /**
         * Counts of: ClaimsWithClaimantsThatAreClaimantsInAnotherClaim
         * ClaimsWithClaimantsThatArePartnersInAnotherClaim
         * ClaimsWithPartnersThatAreClaimantsInAnotherClaim
         * ClaimsWithPartnersThatArePartnersInAnotherClaim
         * ClaimantsInMultipleClaimsInAMonth PartnersInMultipleClaimsInAMonth
         * NonDependentsInMultipleClaimsInAMonth
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim,
                cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithClaimantsThatArePartnersInAnotherClaim,
                cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim,
                cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartnersThatArePartnersInAnotherClaim,
                cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimantsInMultipleClaimsInAMonth,
                pid2cidsOfClaimantsInMultipleClaimsInAMonth.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfPartnersInMultipleClaimsInAMonth,
                pid2cidsOfPartnersInMultipleClaimsInAMonth.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNonDependentsInMultipleClaimsInAMonth,
                pid2cidsOfNonDependentsInMultipleClaimsInAMonth.size());
        addLoadSummaryCount(SHBE_Strings.s_LineCount, lineCount);
        addLoadSummaryCount(SHBE_Strings.s_TotalIncome, grandTotalIncome);
        addLoadSummaryCount(SHBE_Strings.s_TotalIncomeGreaterThanZeroCount,
                totalIncomeGreaterThanZeroCount);
        addLoadSummaryCount(SHBE_Strings.s_Average_NonZero_Income,
                grandTotalIncome / (double) totalIncomeGreaterThanZeroCount);
        addLoadSummaryCount(SHBE_Strings.s_TotalWeeklyEligibleRentAmount,
                grandTotalWeeklyEligibleRentAmount);
        addLoadSummaryCount(SHBE_Strings.s_TotalWeeklyEligibleRentAmountGreaterThanZeroCount,
                totalWeeklyEligibleRentAmountGreaterThanZeroCount);
        addLoadSummaryCount(SHBE_Strings.s_Average_NonZero_WeeklyEligibleRentAmount,
                grandTotalWeeklyEligibleRentAmount / (double) totalWeeklyEligibleRentAmountGreaterThanZeroCount);
        env.env.log("<Summary Statistics>", logID);

        /**
         * Write out data.
         */
        Generic_IO.writeObject(records, getRecordsFile());
        Generic_IO.writeObject(cidsOfNewSHBEClaims,
                getCidsOfNewSHBEClaimsFile());
        Generic_IO.writeObject(cidsOfNewSHBEClaimsWhereClaimantWasClaimantBefore,
                getCidsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile());
        Generic_IO.writeObject(cidsOfNewSHBEClaimsWhereClaimantWasPartnerBefore,
                getCidsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile());
        Generic_IO.writeObject(cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore,
                getCidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile());
        Generic_IO.writeObject(cidsOfNewSHBEClaimsWhereClaimantIsNew,
                getCidsOfNewSHBEClaimsWhereClaimantIsNewFile());
        Generic_IO.writeObject(cpids, getCpidsFile());
        Generic_IO.writeObject(ppids, getPpidsFile());
        Generic_IO.writeObject(ndpids, getNdpidsFile());
        Generic_IO.writeObject(cidsOfCottingleySpringsCaravanParkPairedClaims,
                getCidsOfCottingleySpringsCaravanParkPairedClaimsFile());
        Generic_IO.writeObject(cidsHII,
                getCidsHIIFile());
        Generic_IO.writeObject(cidsHIS,
                getCidsHISFile());
        Generic_IO.writeObject(cidsHIO,
                getCidsHIOFile());
        Generic_IO.writeObject(cidsCII,
                getCidsCIIFile());
        Generic_IO.writeObject(cidsCIS,
                getCidsCISFile());
        Generic_IO.writeObject(cidsCIO,
                getCidsCIOFile());
        Generic_IO.writeObject(sRecordsWithoutDRecords, getSRecordsWithoutDRecordsFile());
        Generic_IO.writeObject(cid2CountOfSRecords,
                getCidToCountOfSRecordsFile());
        Generic_IO.writeObject(cidsOfClaimsWithoutAMappableClaimantPostcode,
                getCidsOfClaimsWithoutAMappableClaimantPostcodeFile());
        Generic_IO.writeObject(cid2cpid,
                getCid2cpidFile());
        Generic_IO.writeObject(cid2ppid,
                getCid2ppidFile());
        Generic_IO.writeObject(cid2ndpids,
                getCid2ndpidsFile());
        Generic_IO.writeObject(cid2dpids,
                getCid2dpidsFile());
        Generic_IO.writeObject(cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim,
                getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile());
        Generic_IO.writeObject(cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim,
                getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile());
        Generic_IO.writeObject(cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim,
                getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile());
        Generic_IO.writeObject(cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim,
                getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile());
        Generic_IO.writeObject(cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim,
                getCidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile());
        Generic_IO.writeObject(pid2cidsOfClaimantsInMultipleClaimsInAMonth,
                getPid2cidsOfClaimantsInMultipleClaimsInAMonthFile());
        Generic_IO.writeObject(pid2cidsOfPartnersInMultipleClaimsInAMonth,
                getPid2cidsOfPartnersInMultipleClaimsInAMonthFile());
        Generic_IO.writeObject(pid2cidsOfNonDependentsInMultipleClaimsInAMonth,
                getPid2cidsOfNonDependentsInMultipleClaimsInAMonthFile());
        Generic_IO.writeObject(cid2postcodeID, getCid2postcodeIDFile());
        Generic_IO.writeObject(cid2tt, getCid2ttFile());
        Generic_IO.writeObject(loadSummary, getLoadSummaryFile());
        Generic_IO.writeObject(recordIDsNotLoaded, getRecordIDsNotLoadedFile());
        Generic_IO.writeObject(cidsOfInvalidClaimantNINOClaims, getCidsOfInvalidClaimantNINOClaimsFile());
        Generic_IO.writeObject(claimantPostcodesUnmappable, getClaimantPostcodesUnmappableFile());
        Generic_IO.writeObject(claimantPostcodesModified, getClaimantPostcodesModifiedFile());
        Generic_IO.writeObject(claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes, getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile());
        Generic_IO.writeObject(cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture, getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile());

        // Write out other outputs
        // Write out ClaimRefs of ClaimantsInMultipleClaimsInAMonth
        String YMN;
        YMN = handler.getYearMonthNumber(inputFilename);
        writeOut(pid2cidsOfClaimantsInMultipleClaimsInAMonth,
                "ClaimantsInMultipleClaimsInAMonth", YMN,
                cid2c, nid2n,
                did2d);
        // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
        writeOut(pid2cidsOfPartnersInMultipleClaimsInAMonth,
                "PartnersInMultipleClaimsInAMonth", YMN,
                cid2c, nid2n,
                did2d);
        // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
        writeOut(pid2cidsOfNonDependentsInMultipleClaimsInAMonth,
                "NonDependentsInMultipleClaimsInAMonth", YMN,
                cid2c, nid2n,
                did2d);
        // Write out ClaimRefs of ClaimIDOfInvalidClaimantNINOClaims
        String name = "ClaimRefsOfInvalidClaimantNINOClaims";
        int logID2 = this.env.env.initLog(name, ".csv");
        Iterator<SHBE_ClaimID> ite2;
        ite2 = cidsOfInvalidClaimantNINOClaims.iterator();
        while (ite2.hasNext()) {
            claimID = ite2.next();
            this.env.env.log(cid2c.get(claimID), logID2);
        }
        this.env.env.closeLog(logID2);
        env.env.log("----------------------", logID);
        env.env.log("Loaded " + ym3, logID);
        env.env.log("----------------------", logID);
    }

    private void writeOut(Map<SHBE_PersonID, Set<SHBE_ClaimID>> mainLookup,
            String name, String ymn, Map<SHBE_ClaimID, String> cid2c,
            Map<SHBE_NINOID, String> nid2n, Map<SHBE_DOBID, String> di2d)
            throws IOException, Exception {
        Iterator<SHBE_PersonID> ite2;
        Iterator<SHBE_ClaimID> ite3;
        String s;
        SHBE_ClaimID claimID;
        SHBE_PersonID PersonID;
        Set<SHBE_ClaimID> ClaimRefs;
        int logID2 = env.env.initLog(name + ymn, ".csv");
        env.env.log("NINO,DOB,ClaimRefs", logID2, false);
        ite2 = mainLookup.keySet().iterator();
        while (ite2.hasNext()) {
            PersonID = ite2.next();
            String nino = nid2n.get(PersonID.NINOID);
            String dob = di2d.get(PersonID.DOBID);
            if (!nino.trim().equalsIgnoreCase("")) {
                if (!nino.trim().startsWith("XX999")) {
                    ClaimRefs = mainLookup.get(PersonID);
                    ite3 = ClaimRefs.iterator();
                    s = nino + "," + dob;
                    while (ite3.hasNext()) {
                        claimID = ite3.next();
                        s += "," + cid2c.get(claimID);
                    }
                    env.env.log(s, logID2, false);
                }
            }
        }
        env.env.closeLog(logID2);
    }

    /**
     * logs and adds s and n to loadSummary.
     *
     * @param s The string to add.
     * @param n The number to add.
     */
    public final void addLoadSummaryCount(String s, Number n) {
        env.env.log(s + " " + n, logID);
        loadSummary.put(s, n);
    }

    /**
     *
     * @param handler SHBE_Handler
     * @param record SHBE_Record
     * @param n2nid NINOToNINOIDLookup
     * @param nid2n NINOIDToNINOLookup
     * @param d2did DOBToDOBIDLookup
     * @param did2d DOBIDToDOBLookup
     * @param allNonDependentPersonIDs allNonDependentPersonIDs
     * @param personID2ClaimIDs personIDToClaimRefsLookup
     * @param claimIDToClaimRefLookup claimIDToClaimRefLookup
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final void initSRecords(SHBE_Handler handler,
            SHBE_Record record, Map<String, SHBE_NINOID> n2nid,
            Map<SHBE_NINOID, String> nid2n, Map<String, SHBE_DOBID> d2did,
            Map<SHBE_DOBID, String> did2d,
            Set<SHBE_PersonID> allNonDependentPersonIDs,
            Map<SHBE_PersonID, Set<SHBE_ClaimID>> personID2ClaimIDs,
            Map<SHBE_ClaimID, String> claimIDToClaimRefLookup
    ) throws IOException, ClassNotFoundException {
        ArrayList<SHBE_S_Record> sRecordsForClaim;
        SHBE_ClaimID claimID = record.getClaimID();
        sRecordsForClaim = getSRecordsWithoutDRecords().get(claimID);
        if (sRecordsForClaim != null) {
            // Declare variables
            SHBE_PersonID personID;
            Object key;
            SHBE_ClaimID otherClaimID;
            Iterator<SHBE_S_Record> ite = sRecordsForClaim.iterator();
            while (ite.hasNext()) {
                SHBE_S_Record srec = ite.next();
                String nino = srec.getSubRecordChildReferenceNumberOrNINO();
                String dob = srec.getSubRecordDateOfBirth();
                int subRecordType = srec.getSubRecordType();
                switch (subRecordType) {
                    case 1:
                        /**
                         * claimantsNINO
                         */
                        String cNINO = srec.getClaimantsNationalInsuranceNumber();
                        if (cNINO.trim().isEmpty()) {
                            cNINO = SHBE_Strings.s_DefaultNINO;
                            env.env.log("ClaimantsNINO is empty for "
                                    + "ClaimID " + claimID + " ClaimRef "
                                    + env.handler.getCid2c().get(claimID)
                                    + " Setting as default NINO " + cNINO, logID);
                        }
                        int i;
                        i = 0;
                        if (nino.isEmpty()) {
                            boolean set;
                            set = false;
                            while (!set) {
                                nino = "" + i;
                                nino += "_" + cNINO;
                                if (n2nid.containsKey(nino)) {
                                    env.env.log("NINO " + nino + " is not unique"
                                            + " for " + cNINO, logID,
                                            false);
                                } else {
                                    set = true;
                                }
                                i++;
                            }
                        } else {
                            boolean set;
                            set = false;
                            nino += "_" + cNINO;
                            if (n2nid.containsKey(nino)) {
                                /**
                                 * If the claimant has more than one claim, this
                                 * is fine. Otherwise we have to do something.
                                 */
                                if (cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.contains(claimID)) {
                                    set = true;
                                } else {
                                    env.env.log("NINO " + nino + " is not unique"
                                            + " for " + cNINO + " and "
                                            + "ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim does not contain "
                                            + "ClaimID " + claimID + " for ClaimRef "
                                            + env.handler.getCid2c().get(claimID), logID,
                                            false);
                                }
                            } else {
                                set = true;
                            }
                            while (!set) {
                                nino = "" + i;
                                nino += "_" + cNINO;
                                if (n2nid.containsKey(nino)) {
                                    env.env.log("NINO " + nino + " is not unique "
                                            + "for " + cNINO, logID,
                                            false);
                                } else {
                                    set = true;
                                }
                                i++;
                            }
                        }
                        personID = handler.getPersonID(nino, dob,
                                n2nid, nid2n,
                                d2did, did2d);
                        /**
                         * Add to ClaimIDToDependentPersonIDsLookup.
                         */
                        Set<SHBE_PersonID> s = cid2dpids.get(claimID);
                        if (s == null) {
                            s = new HashSet<>();
                            cid2dpids.put(claimID, s);
                        }
                        s.add(personID);
                        Generic_Collections.addToMap(personID2ClaimIDs, personID, claimID);
                        break;
                    case 2:
                        personID = handler.getPersonID(nino, dob,
                                n2nid, nid2n, d2did, did2d);
                        /**
                         * Ignore if this is a
                         * CottingleySpringsCaravanParkPairedClaimIDs. It may be
                         * that there are partners shared in these claims, but
                         * such a thing is ignored for now.
                         */
                        if (!cidsOfCottingleySpringsCaravanParkPairedClaims.contains(claimID)) {
                            /**
                             * If NonDependent is a NonDependent in another
                             * claim add to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                             */
                            key = Generic_Collections.getKey(cid2ndpids, personID);
                            if (key != null) {
                                otherClaimID = (SHBE_ClaimID) key;
                                Set<SHBE_ClaimID> set;
                                set = pid2cidsOfNonDependentsInMultipleClaimsInAMonth.get(personID);
                                if (set == null) {
                                    set = new HashSet<>();
                                    pid2cidsOfNonDependentsInMultipleClaimsInAMonth.put(personID, set);
                                }
                                set.add(claimID);
                                set.add(otherClaimID);
                                cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
//                                if (!(NINO.trim().equalsIgnoreCase("") || NINO.startsWith("XX999"))) {
//                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                    env.log("NonDependent with NINO " + NINO + " DOB " + DOB
//                                            + " is in ClaimRef " + ClaimIDToClaimRefLookup.get(ClaimID)
//                                            + " and " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                }
                            }
                            /**
                             * If NonDependent is a Claimant in another claim
                             * add to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                             */
                            if (cid2cpid.containsValue(personID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ClaimID) key;
                                    Set<SHBE_ClaimID> set;
                                    set = pid2cidsOfNonDependentsInMultipleClaimsInAMonth.get(personID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        pid2cidsOfNonDependentsInMultipleClaimsInAMonth.put(personID, set);
                                    }
                                    set.add(claimID);
                                    set.add(otherClaimID);
                                    cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                    cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
//                                    if (!(NINO.trim().equalsIgnoreCase("") || NINO.startsWith("XX999"))) {
//                                        env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                        env.log("NonDependent with NINO " + NINO + " DOB " + DOB
//                                                + " in ClaimRef " + ClaimIDToClaimRefLookup.get(ClaimID)
//                                                + " is a Claimant in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                        env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                    }
                                }
                            }
                            /**
                             * If NonDependent is a Partner in another claim add
                             * to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
                             */
                            if (cid2ppid.containsValue(personID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ClaimID) key;
                                    Set<SHBE_ClaimID> set;
                                    set = pid2cidsOfNonDependentsInMultipleClaimsInAMonth.get(personID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        pid2cidsOfNonDependentsInMultipleClaimsInAMonth.put(personID, set);
                                    }
                                    set.add(claimID);
                                    set.add(otherClaimID);
                                    cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                    cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
//                                    if (!(NINO.trim().equalsIgnoreCase("") || NINO.startsWith("XX999"))) {
//                                        env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                        env.log("NonDependent with NINO " + NINO + " DOB " + DOB
//                                                + " in ClaimRef " + ClaimIDToClaimRefLookup.get(ClaimID)
//                                                + " is a Partner in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                        env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                    }
                                }
                            }
                        }
                        //Set<SHBE_PersonID> s;
                        s = cid2ndpids.get(claimID);
                        if (s == null) {
                            s = new HashSet<>();
                            cid2ndpids.put(claimID, s);
                        }
                        s.add(personID);
                        ndpids.add(personID);
                        allNonDependentPersonIDs.add(personID);
                        Generic_Collections.addToMap(personID2ClaimIDs, personID, claimID);
                        break;
                    default:
                        env.env.log("Unrecognised SubRecordType " + subRecordType, logID);
                        break;
                }
            }
            record.sRecords = sRecordsForClaim;
            cid2CountOfSRecords.put(claimID, sRecordsForClaim.size());
        }
        /**
         * Remove all assigned SRecords from SRecordsWithoutDRecords.
         */
        Iterator<SHBE_ClaimID> iteID;
        iteID = cid2CountOfSRecords.keySet().iterator();
        while (iteID.hasNext()) {
            sRecordsWithoutDRecords.remove(iteID.next());
        }
    }

    /**
     * Month_10_2010_11_381112_D_records.csv
     * 1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,293,294,295,296,297,298,299,308,309,310,311,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341
     * hb9803_SHBE_206728k\ April\ 2008.csv
     * 1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,293,294,295,296,297,298,299,307,308,309,310,311,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341
     * 1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,293,294,295,296,297,298,299,308,309,310,311,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341
     * 307, 315
     *
     * @param directory The directory
     * @param filename The filename.
     * @return int type
     */
    public final int readAndCheckFirstLine(Path directory, String filename) {
        //int type = 0;
        String type0Header = "1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,"
                + "23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,"
                + "43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,"
                + "63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,"
                + "83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,"
                + "102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,"
                + "117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,"
                + "135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,"
                + "150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,"
                + "165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,"
                + "180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,"
                + "195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,"
                + "210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,"
                + "226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,"
                + "241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,"
                + "256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,"
                + "271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,"
                + "293,294,295,296,297,298,299,308,309,310,311,316,317,318,319,"
                + "320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,"
                + "335,336,337,338,339,340,341";
        String type1Header = "1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,"
                + "23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,"
                + "43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,"
                + "63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,"
                + "83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,"
                + "102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,"
                + "117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,"
                + "135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,"
                + "150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,"
                + "165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,"
                + "180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,"
                + "195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,"
                + "210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,"
                + "226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,"
                + "241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,"
                + "256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,"
                + "271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,"
                + "293,294,295,296,297,298,299,307,308,309,310,311,315,316,317,"
                + "318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,"
                + "333,334,335,336,337,338,339,340,341";
        Path p = Paths.get(directory.toString(), filename);
        try {
            String line;
            //BufferedReader br = Generic_IO.getBufferedReader(inputFile);
            try (BufferedReader br = Generic_IO.getBufferedReader(p)) {
                //BufferedReader br = Generic_IO.getBufferedReader(inputFile);
                StreamTokenizer st = new StreamTokenizer(br);
                Generic_IO.setStreamTokenizerSyntax5(st);
                st.wordChars('`', '`');
                int tokenType;
                tokenType = st.nextToken();
                line = "";
                while (tokenType != StreamTokenizer.TT_EOL) {
                    switch (tokenType) {
                        case StreamTokenizer.TT_WORD:
                            line += st.sval;
                            break;
                    }
                    tokenType = st.nextToken();
                }
            }
            if (line.startsWith(type0Header)) {
                return 0;
            }
            if (line.startsWith(type1Header)) {
                return 1;
            } else {
                String[] lineSplit = line.split(",");
                env.env.log("Unrecognised header in SHBE_Records.readAndCheckFirstLine(File,String)", logID);
                env.env.log("Number of fields in header " + lineSplit.length, logID);
                env.env.log("header:", logID);
                env.env.log(line, logID);

            }
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return 2;
    }

    /**
     * @return the inputFile
     */
    public Path getInputFile() {
        return inputFile;
    }

    /**
     * If not initialised, initialises {@link #cid2cpid} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cid2cpid}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, SHBE_PersonID> getCid2cpid(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCid2cpid();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCid2cpid(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cid2cpid} then returns it.
     *
     * @return {@link #cid2cpid}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, SHBE_PersonID> getCid2cpid()
            throws IOException, ClassNotFoundException {
        if (cid2cpid == null) {
            Path f;
            f = getCid2cpidFile();
            if (Files.exists(f)) {
                cid2cpid = (Map<SHBE_ClaimID, SHBE_PersonID>) Generic_IO.readObject(f);
            } else {
                cid2cpid = new HashMap<>();
            }
        }
        return cid2cpid;
    }

    /**
     * If not initialised, initialises {@link #cid2ppid} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cid2ppid}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, SHBE_PersonID> getCid2ppid(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCid2ppid();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCid2ppid(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cid2ppid} then returns it.
     *
     * @return {@link #cid2ppid}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, SHBE_PersonID> getCid2ppid()
            throws IOException, ClassNotFoundException {
        if (cid2ppid == null) {
            Path f;
            f = getCid2ppidFile();
            if (Files.exists(f)) {
                cid2ppid = (Map<SHBE_ClaimID, SHBE_PersonID>) Generic_IO.readObject(f);
            } else {
                cid2ppid = new HashMap<>();
            }
        }
        return cid2ppid;
    }

    /**
     * If not initialised, initialises {@link #cid2dpids} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cid2dpids}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Set<SHBE_PersonID>> getCid2dpids(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCid2dpids();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCid2dpids(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cid2dpids} then returns it.
     *
     * @return {@link #cid2dpids}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, Set<SHBE_PersonID>> getCid2dpids()
            throws IOException, ClassNotFoundException {
        if (cid2dpids == null) {
            Path f;
            f = getCid2dpidsFile();
            if (Files.exists(f)) {
                cid2dpids = (Map<SHBE_ClaimID, Set<SHBE_PersonID>>) Generic_IO.readObject(f);
            } else {
                cid2dpids = new HashMap<>();
            }
        }
        return cid2dpids;
    }

    /**
     * If not initialised, initialises {@link #cid2ndpids} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cid2ndpids}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Set<SHBE_PersonID>> getCid2ndpids(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCid2ndpids();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCid2ndpids(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #cid2ndpids} then returns it.
     *
     * @return {@link #cid2ndpids}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, Set<SHBE_PersonID>> getCid2ndpids()
            throws IOException, ClassNotFoundException {
        if (cid2ndpids == null) {
            Path f;
            f = getCid2ndpidsFile();
            if (Files.exists(f)) {
                cid2ndpids = (Map<SHBE_ClaimID, Set<SHBE_PersonID>>) Generic_IO.readObject(f);
            } else {
                cid2ndpids = new HashMap<>();
            }
        }
        return cid2ndpids;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim} then
     * returns it.
     *
     * @return {@link #cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim == null) {
            Path f;
            f = getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile();
            if (Files.exists(f)) {
                cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = new HashSet<>();
            }
        }
        return cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim} then
     * returns it.
     *
     * @return {@link #cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim == null) {
            Path f;
            f = getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = new HashSet<>();
            }
        }
        return cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim == null) {
            Path f;
            f = getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile();
            if (Files.exists(f)) {
                cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = new HashSet<>();
            }
        }
        return cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim == null) {
            Path f;
            f = getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim = new HashSet<>();
            }
        }
        return cidsOfClaimsWithPartnersThatArePartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return
     * {@link #cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getCidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim == null) {
            Path f = getCidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = new HashSet<>();
            }
        }
        return cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return
     * {@link #cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cidOfClaimantsInMultipleClaimsInAMonth(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getPid2cidsOfClaimantsInMultipleClaimsInAMonth();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getPid2cidOfClaimantsInMultipleClaimsInAMonth(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #pid2cidsOfClaimantsInMultipleClaimsInAMonth} then returns it.
     *
     * @return {@link #pid2cidsOfClaimantsInMultipleClaimsInAMonth}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cidsOfClaimantsInMultipleClaimsInAMonth()
            throws IOException, ClassNotFoundException {
        if (pid2cidsOfClaimantsInMultipleClaimsInAMonth == null) {
            Path f;
            f = getPid2cidsOfClaimantsInMultipleClaimsInAMonthFile();
            if (Files.exists(f)) {
                pid2cidsOfClaimantsInMultipleClaimsInAMonth = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                pid2cidsOfClaimantsInMultipleClaimsInAMonth = new HashMap<>();
            }
        }
        return pid2cidsOfClaimantsInMultipleClaimsInAMonth;
    }

    /**
     * If not initialised, initialises
     * {@link #pid2cidsOfPartnersInMultipleClaimsInAMonth} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #pid2cidsOfPartnersInMultipleClaimsInAMonth}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cidsOfPartnersInMultipleClaimsInAMonth(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getPid2cidsOfPartnersInMultipleClaimsInAMonth();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getPid2cidsOfPartnersInMultipleClaimsInAMonth(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cidsOfPartnersInMultipleClaimsInAMonth()
            throws IOException, ClassNotFoundException {
        if (pid2cidsOfPartnersInMultipleClaimsInAMonth == null) {
            Path f = getPid2cidsOfPartnersInMultipleClaimsInAMonthFile();
            if (Files.exists(f)) {
                pid2cidsOfPartnersInMultipleClaimsInAMonth = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                pid2cidsOfPartnersInMultipleClaimsInAMonth = new HashMap<>();
            }
        }
        return pid2cidsOfPartnersInMultipleClaimsInAMonth;
    }

    /**
     * If not initialised, initialises
     * {@link #pid2cidsOfNonDependentsInMultipleClaimsInAMonth} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #pid2cidsOfNonDependentsInMultipleClaimsInAMonth}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cidOfNonDependentsInMultipleClaimsInAMonth(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getPid2cidsOfNonDependentsInMultipleClaimsInAMonth();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getPid2cidOfNonDependentsInMultipleClaimsInAMonth(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #pid2cidsOfNonDependentsInMultipleClaimsInAMonth} then returns it.
     *
     * @return {@link #pid2cidsOfNonDependentsInMultipleClaimsInAMonth}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPid2cidsOfNonDependentsInMultipleClaimsInAMonth()
            throws IOException, ClassNotFoundException {
        if (pid2cidsOfNonDependentsInMultipleClaimsInAMonth == null) {
            Path f;
            f = getPid2cidsOfNonDependentsInMultipleClaimsInAMonthFile();
            if (Files.exists(f)) {
                pid2cidsOfNonDependentsInMultipleClaimsInAMonth = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                pid2cidsOfNonDependentsInMultipleClaimsInAMonth = new HashMap<>();
            }
        }
        return pid2cidsOfNonDependentsInMultipleClaimsInAMonth;
    }

    /**
     * If not initialised, initialises {@link #cid2postcodeID} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cid2postcodeID}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, UKP_RecordID> getCid2postcodeIDLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCid2postcodeID();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCid2postcodeIDLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_ClaimID, UKP_RecordID> getCid2postcodeID()
            throws IOException, ClassNotFoundException {
        if (cid2postcodeID == null) {
            Path f;
            f = getCid2postcodeIDFile();
            if (Files.exists(f)) {
                cid2postcodeID = (Map<SHBE_ClaimID, UKP_RecordID>) Generic_IO.readObject(f);
            } else {
                cid2postcodeID = new HashMap<>();
            }
        }
        return cid2postcodeID;
    }

    /**
     * If not initialised, initialises
     * {@link #cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture()
            throws IOException, ClassNotFoundException {
        if (cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture == null) {
            Path f;
            f = getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile();
            if (Files.exists(f)) {
                cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = new HashSet<>();
            }
        }
        return cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture;
    }

    /**
     * If not initialised, initialises {@link #cid2tt} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cid2tt}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Integer> getCid2tt(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCid2tt();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCid2tt(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_ClaimID, Integer> getCid2tt()
            throws IOException, ClassNotFoundException {
        if (cid2tt == null) {
            Path f;
            f = getCid2ttFile();
            if (Files.exists(f)) {
                cid2tt = (Map<SHBE_ClaimID, Integer>) Generic_IO.readObject(f);
            } else {
                cid2tt = new HashMap<>();
            }
        }
        return cid2tt;
    }

    /**
     * If not initialised, initialises loadSummary then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #loadSummary}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<String, Number> getLoadSummary(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getLoadSummary();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getLoadSummary(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<String, Number> getLoadSummary() throws IOException,
            ClassNotFoundException {
        if (loadSummary == null) {
            Path f;
            f = getLoadSummaryFile();
            if (Files.exists(f)) {
                loadSummary = (Map<String, Number>) Generic_IO.readObject(f);
            } else {
                loadSummary = new HashMap<>();
            }
        }
        return loadSummary;
    }

    /**
     * If not initialised, initialises {@link #recordIDsNotLoaded} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #recordIDsNotLoaded}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final ArrayList<Long> getRecordIDsNotLoaded(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getRecordIDsNotLoaded();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getRecordIDsNotLoaded(hoome);
            } else {
                throw e;
            }
        }
    }

    protected ArrayList<Long> getRecordIDsNotLoaded() throws IOException,
            ClassNotFoundException {
        if (recordIDsNotLoaded == null) {
            Path f;
            f = getRecordIDsNotLoadedFile();
            if (Files.exists(f)) {
                recordIDsNotLoaded = (ArrayList<Long>) Generic_IO.readObject(f);
            } else {
                recordIDsNotLoaded = new ArrayList<>();
            }
        }
        return recordIDsNotLoaded;
    }

    /**
     * If not initialised, initialises {@link #cidsOfInvalidClaimantNINOClaims}
     * then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #cidsOfInvalidClaimantNINOClaims}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCidsOfInvalidClaimantNINOClaims(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCidsOfInvalidClaimantNINOClaims();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCidsOfInvalidClaimantNINOClaims(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getCidsOfInvalidClaimantNINOClaims()
            throws IOException, ClassNotFoundException {
        if (cidsOfInvalidClaimantNINOClaims == null) {
            Path f;
            f = getCidsOfInvalidClaimantNINOClaimsFile();
            if (Files.exists(f)) {
                cidsOfInvalidClaimantNINOClaims = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cidsOfInvalidClaimantNINOClaims = new HashSet<>();
            }
        }
        return cidsOfInvalidClaimantNINOClaims;
    }

    /**
     * If not initialised, initialises {@link #claimantPostcodesUnmappable} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimantPostcodesUnmappable}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, String> getClaimantPostcodesUnmappable(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPostcodesUnmappable();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimantPostcodesUnmappable(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_ClaimID, String> getClaimantPostcodesUnmappable()
            throws IOException, ClassNotFoundException {
        if (claimantPostcodesUnmappable == null) {
            Path f = getClaimantPostcodesUnmappableFile();
            if (Files.exists(f)) {
                claimantPostcodesUnmappable = (Map<SHBE_ClaimID, String>) Generic_IO.readObject(f);
            } else {
                claimantPostcodesUnmappable = new HashMap<>();
            }
        }
        return claimantPostcodesUnmappable;
    }

    /**
     * If not initialised, initialises {@link #claimantPostcodesModified} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimantPostcodesModified}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, String[]> getClaimantPostcodesModified(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPostcodesModified();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimantPostcodesModified(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimantPostcodesModified} then
     * returns it.
     *
     * @return {@link #claimantPostcodesModified}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, String[]> getClaimantPostcodesModified()
            throws IOException, ClassNotFoundException {
        if (claimantPostcodesModified == null) {
            Path f;
            f = getClaimantPostcodesModifiedFile();
            if (Files.exists(f)) {
                claimantPostcodesModified = (Map<SHBE_ClaimID, String[]>) Generic_IO.readObject(f);
            } else {
                claimantPostcodesModified = new HashMap<>();
            }
        }
        return claimantPostcodesModified;
    }

    /**
     * If not initialised, initialises
     * {@link #claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, String> getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_ClaimID, String> getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes()
            throws IOException, ClassNotFoundException {
        if (claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes == null) {
            Path f;
            f = getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile();
            if (Files.exists(f)) {
                claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = (Map<SHBE_ClaimID, String>) Generic_IO.readObject(f);
            } else {
                claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = new HashMap<>();
            }
        }
        return claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes;
    }

    /**
     * @return {@link #file}
     * @throws java.io.IOException If encountered.
     */
    protected final Path getFile() throws IOException {
        if (file == null) {
            file = getFile("Records" + SHBE_Strings.s_BinaryFileExtension);
        }
        return file;
    }

    /**
     * @return {@link #recordsFile} initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getRecordsFile() throws IOException {
        if (recordsFile == null) {
            recordsFile = getFile(SHBE_Strings.s_Records + SHBE_Strings.symbol_underscore
                    + "Map_String__SHBE_Record" + SHBE_Strings.s_BinaryFileExtension);
        }
        return recordsFile;
    }

    /**
     * @return {@link #cidsOfNewSHBEClaimsFile} initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsOfNewSHBEClaimsFile() throws IOException {
        if (cidsOfNewSHBEClaimsFile == null) {
            cidsOfNewSHBEClaimsFile = getFile("ClaimIDsOfNewSHBEClaims"
                    + SHBE_Strings.symbol_underscore + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfNewSHBEClaimsFile;
    }

    /**
     * @return {@link #cidsOfNewSHBEClaimsFile} initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile() throws IOException {
        if (cidsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile == null) {
            cidsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;
    }

    /**
     * @return {@link #cidsOfNewSHBEClaimsFile} initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile()
            throws IOException {
        if (cidsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile == null) {
            cidsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;
    }

    /**
     * @return
     * {@link #cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile}
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile() throws IOException {
        if (cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile == null) {
            cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;
    }

    /**
     * @return {@link #cidsOfNewSHBEClaimsWhereClaimantIsNewFile} initialising
     * if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsOfNewSHBEClaimsWhereClaimantIsNewFile() throws IOException {
        if (cidsOfNewSHBEClaimsWhereClaimantIsNewFile == null) {
            cidsOfNewSHBEClaimsWhereClaimantIsNewFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfNewSHBEClaimsWhereClaimantIsNewFile;
    }

    public final Path getCpidsFile() throws IOException {
        if (cpidsFile == null) {
            cpidsFile = getFile(
                    "Claimant"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cpidsFile;
    }

    public final Path getPpidsFile() throws IOException {
        if (ppidsFile == null) {
            ppidsFile = getFile(
                    "Partner"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ppidsFile;
    }

    public final Path getNdpidsFile() throws IOException {
        if (ndpidsFile == null) {
            ndpidsFile = getFile(
                    "NonDependent"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ndpidsFile;
    }

    public final Set<SHBE_PersonID> getCpids(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCpids();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getCpids(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Set<SHBE_PersonID> getCpids()
            throws IOException, ClassNotFoundException {
        cpidsFile = getCpidsFile();
        return getClaimantPersonIDs(cpidsFile);
    }

    /**
     * @param f Path
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_PersonID> getClaimantPersonIDs(
            Path f) throws IOException, ClassNotFoundException {
        if (cpids == null) {
            cpids = env.collections.getPersonIDs(f);
        }
        return cpids;
    }

    public final Set<SHBE_PersonID> getPpids(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getPpids();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getPpids(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Set<SHBE_PersonID> getPpids() throws IOException,
            ClassNotFoundException {
        ppidsFile = getPpidsFile();
        return getPartnerPersonIDs(ppidsFile);
    }

    /**
     * @param f Path
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_PersonID> getPartnerPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (ppids == null) {
            ppids = env.collections.getPersonIDs(f);
        }
        return ppids;
    }

    public final Set<SHBE_PersonID> getNdpids(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getNdpids();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getNdpids(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * @param f Path
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_PersonID> getNonDependentPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (ndpids == null) {
            ndpids = env.collections.getPersonIDs(f);
        }
        return ndpids;
    }

    /**
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Set<SHBE_PersonID> getNdpids()
            throws IOException, ClassNotFoundException {
        ndpidsFile = getNdpidsFile();
        return getNonDependentPersonIDs(ndpidsFile);
    }

    /**
     * @return ottingleySpringsCaravanParkPairedClaimIDsFile initialising if it
     * is not already initialised.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected final Path getCidsOfCottingleySpringsCaravanParkPairedClaimsFile()
            throws IOException, ClassNotFoundException {
        if (cidsOfCottingleySpringsCaravanParkPairedClaimsFile == null) {
            cidsOfCottingleySpringsCaravanParkPairedClaimsFile = getFile(
                    SHBE_Strings.s_CottingleySpringsCaravanPark + "PairedClaimIDs"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfCottingleySpringsCaravanParkPairedClaimsFile;
    }

    /**
     * @return cidsHIIFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsHIIFile() throws IOException {
        if (cidsHIIFile == null) {
            cidsHIIFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeIn
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsHIIFile;
    }

    /**
     * @return cidsHISFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsHISFile() throws IOException {
        if (cidsHISFile == null) {
            cidsHISFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeSuspended
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsHISFile;
    }

    /**
     * @return cidsHIOFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsHIOFile()
            throws IOException {
        if (cidsHIOFile == null) {
            cidsHIOFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeOther
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsHIOFile;
    }

    /**
     * @return cidsCIIFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsCIIFile()
            throws IOException {
        if (cidsCIIFile == null) {
            cidsCIIFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeIn
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsCIIFile;
    }

    /**
     * @return cidsCISFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsCISFile()
            throws IOException {
        if (cidsCISFile == null) {
            cidsCISFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeSuspended
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsCISFile;
    }

    /**
     * @return cidsCIOFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsCIOFile()
            throws IOException {
        if (cidsCIOFile == null) {
            cidsCIOFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeOther
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsCIOFile;
    }

    /**
     * @return sRecordsWithoutDRecordsFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getSRecordsWithoutDRecordsFile() throws IOException {
        if (sRecordsWithoutDRecordsFile == null) {
            sRecordsWithoutDRecordsFile = getFile(
                    "SRecordsWithoutDRecordsFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__ArrayList_SHBE_S_Record"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return sRecordsWithoutDRecordsFile;
    }

    /**
     * @return cidToCountOfSRecordsFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidToCountOfSRecordsFile()
            throws IOException {
        if (cidToCountOfSRecordsFile == null) {
            cidToCountOfSRecordsFile = getFile(
                    "ClaimIDAndCountOfRecordsWithSRecordsFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidToCountOfSRecordsFile;
    }

    /**
     * @return cidsOfClaimsWithoutAMappableClaimantPostcodeFile initialising if
     * it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getCidsOfClaimsWithoutAMappableClaimantPostcodeFile()
            throws IOException {
        if (cidsOfClaimsWithoutAMappableClaimantPostcodeFile == null) {
            cidsOfClaimsWithoutAMappableClaimantPostcodeFile = getFile(
                    "ClaimIDsOfClaimsWithoutAMappableClaimantPostcode"
                    + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfClaimsWithoutAMappableClaimantPostcodeFile;
    }

    /**
     * @return cid2cpidFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCid2cpidFile() throws IOException {
        if (cid2cpidFile == null) {
            cid2cpidFile = getFile(
                    "ClaimIDToClaimantPersonIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cid2cpidFile;
    }

    /**
     * @return cid2ppidFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCid2ppidFile()
            throws IOException {
        if (cid2ppidFile == null) {
            cid2ppidFile = getFile(
                    "ClaimIDToPartnerPersonIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cid2ppidFile;
    }

    /**
     * @return cid2dpidsFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCid2dpidsFile()
            throws IOException {
        if (cid2dpidsFile == null) {
            cid2dpidsFile = getFile(
                    "ClaimIDToDependentPersonIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Set<SHBE_PersonID>"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cid2dpidsFile;
    }

    /**
     * @return cid2ndpidsFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCid2ndpidsFile() throws IOException {
        if (cid2ndpidsFile == null) {
            cid2ndpidsFile = getFile(
                    "ClaimIDToNonDependentPersonIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cid2ndpidsFile;
    }

    /**
     * @return cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile()
            throws IOException {
        if (cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile == null) {
            cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile()
            throws IOException {
        if (cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile == null) {
            cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile()
            throws IOException {
        if (cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile == null) {
            cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return cidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile()
            throws IOException {
        if (cidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile == null) {
            cidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return
     * ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile()
            throws IOException {
        if (cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile == null) {
            cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;
    }

    /**
     * @return pid2cidsOfClaimantsInMultipleClaimsInAMonthFile initialising if
     * it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getPid2cidsOfClaimantsInMultipleClaimsInAMonthFile()
            throws IOException {
        if (pid2cidsOfClaimantsInMultipleClaimsInAMonthFile == null) {
            pid2cidsOfClaimantsInMultipleClaimsInAMonthFile = getFile(
                    "ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return pid2cidsOfClaimantsInMultipleClaimsInAMonthFile;
    }

    /**
     * @return pid2cidOfPartnersInMultipleClaimsInAMonthFile initialising if it
     * is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getPid2cidsOfPartnersInMultipleClaimsInAMonthFile()
            throws IOException {
        if (pid2cidsOfPartnersInMultipleClaimsInAMonthFile == null) {
            pid2cidsOfPartnersInMultipleClaimsInAMonthFile = getFile(
                    "PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return pid2cidsOfPartnersInMultipleClaimsInAMonthFile;
    }

    /**
     * @return pid2cidsOfNonDependentsInMultipleClaimsInAMonthFile initialising
     * if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getPid2cidsOfNonDependentsInMultipleClaimsInAMonthFile()
            throws IOException {
        if (pid2cidsOfNonDependentsInMultipleClaimsInAMonthFile == null) {
            pid2cidsOfNonDependentsInMultipleClaimsInAMonthFile = getFile(
                    "NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return pid2cidsOfNonDependentsInMultipleClaimsInAMonthFile;
    }

    /**
     * @return cid2postcodeIDFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCid2postcodeIDFile() throws IOException {
        if (cid2postcodeIDFile == null) {
            cid2postcodeIDFile = getFile(
                    "ClaimIDToPostcodeIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cid2postcodeIDFile;
    }

    /**
     * @return cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile()
            throws IOException {
        if (cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile == null) {
            cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile = getFile(
                    "ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;
    }

    /**
     * @return cid2ttFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCid2ttFile() throws IOException {
        if (cid2ttFile == null) {
            cid2ttFile = getFile(
                    "ClaimIDToTenancyTypeLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cid2ttFile;
    }

    /**
     * @return loadSummaryFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getLoadSummaryFile() throws IOException {
        if (loadSummaryFile == null) {
            loadSummaryFile = getFile(
                    "LoadSummary" + SHBE_Strings.symbol_underscore
                    + "Map_String__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return loadSummaryFile;
    }

    /**
     * @return recordIDsNotLoadedFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getRecordIDsNotLoadedFile() throws IOException {
        if (recordIDsNotLoadedFile == null) {
            recordIDsNotLoadedFile = getFile(
                    "RecordIDsNotLoaded" + SHBE_Strings.symbol_underscore
                    + "ArrayList_Long"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return recordIDsNotLoadedFile;
    }

    /**
     * @return cidsOfInvalidClaimantNINOClaimsFile initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getCidsOfInvalidClaimantNINOClaimsFile()
            throws IOException {
        if (cidsOfInvalidClaimantNINOClaimsFile == null) {
            cidsOfInvalidClaimantNINOClaimsFile = getFile(
                    "ClaimIDsOfInvalidClaimantNINOClaimsFile" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cidsOfInvalidClaimantNINOClaimsFile;
    }

    /**
     * @return claimantPostcodesUnmappableFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimantPostcodesUnmappableFile()
            throws IOException {
        if (claimantPostcodesUnmappableFile == null) {
            claimantPostcodesUnmappableFile = getFile(
                    "ClaimantPostcodesUnmappable" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimantPostcodesUnmappableFile;
    }

    /**
     * @return claimantPostcodesModifiedFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimantPostcodesModifiedFile() throws IOException {
        if (claimantPostcodesModifiedFile == null) {
            claimantPostcodesModifiedFile = getFile(
                    "ClaimantPostcodesModified" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__String[]"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimantPostcodesModifiedFile;
    }

    /**
     * @return claimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile()
            throws IOException {
        if (claimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile == null) {
            claimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile = getFile(
                    "ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile;
    }

    /**
     * Clears the main Data. This is for memory handling reasons.
     */
    public void clearData() {
        this.records = null;
        this.recordIDsNotLoaded = null;
        this.sRecordsWithoutDRecords = null;
    }
}
