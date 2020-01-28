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
import uk.ac.leeds.ccg.data.shbe.util.SHBE_Collections;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Records extends SHBE_Object implements Serializable {

    private static final long serialVersionUID = 1L;

    // For convenience.
    private transient final SHBE_Handler handler;
    private transient final UKP_Data postcodeHandler;

    /**
     * Keys are Claim IDs, values are SHBE_Records.
     */
    private Map<SHBE_ClaimID, SHBE_Record> records;

    /**
     * SHBE_PersonID of Claimants
     */
    Set<SHBE_PersonID> claimantPersonIDs;

    /**
     * SHBE_PersonID of Partners
     */
    Set<SHBE_PersonID> partnerPersonIDs;

    /**
     * SHBE_PersonID of Non-Dependents
     */
    Set<SHBE_PersonID> nonDependentPersonIDs;

    /**
     * A store for ClaimIDs for Cottingley Springs Caravan Park where there are
     * two claims for a claimant, one for a pitch and the other for the rent of
     * a caravan.
     */
    private Set<SHBE_ClaimID> cottingleySpringsCaravanParkPairedClaimIDs;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private Set<SHBE_ClaimID> claimIDsWithStatusOfHBAtExtractDateInPayment;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 2 (Suspended).
     */
    private Set<SHBE_ClaimID> claimIDsWithStatusOfHBAtExtractDateSuspended;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 0 (Suspended).
     */
    private Set<SHBE_ClaimID> claimIDsWithStatusOfHBAtExtractDateOther;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private Set<SHBE_ClaimID> claimIDsWithStatusOfCTBAtExtractDateInPayment;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 2
     * (Suspended).
     */
    private Set<SHBE_ClaimID> claimIDsWithStatusOfCTBAtExtractDateSuspended;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 0
     * (Suspended).
     */
    private Set<SHBE_ClaimID> claimIDsWithStatusOfCTBAtExtractDateOther;

    /**
     * SRecordsWithoutDRecords indexed by ClaimRef SHBE_ID. Once the SHBE data
     * is loaded from source, this only contains those SRecordsWithoutDRecords
     * that are not linked to a DRecord.
     */
    private Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>> sRecordsWithoutDRecords;

    /**
     * For storing the ClaimIDs of records that have SRecords along with the
     * count of those sRecordsWithoutDRecords.
     */
    private Map<SHBE_ClaimID, Integer> claimIDAndCountOfRecordsWithSRecords;

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
     * Path for storing Data.
     */
    private Path recordsFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims.
     */
    private Path claimIDsOfNewSHBEClaimsFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant was a
     * Claimant before.
     */
    private Path claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant was a Partner
     * before.
     */
    private Path claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant was a
     * NonDependent before.
     */
    private Path claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant is new.
     */
    private Path claimIDsOfNewSHBEClaimsWhereClaimantIsNewFile;

    /**
     * claimantPersonIDs file.
     */
    private Path claimantPersonIDsFile;

    /**
     * partnerPersonIDs file.
     */
    private Path partnerPersonIDsFile;

    /**
     * nonDependentPersonIDs file.
     */
    private Path nonDependentPersonIDsFile;

    /**
     * Path for storing Cottingley Springs Caravan Park paired ClaimIDs.
     */
    private Path cottingleySpringsCaravanParkPairedClaimIDsFile;

    /**
     * Path for storing ClaimIDs with status of HB at extract date InPayment.
     */
    private Path claimIDsWithStatusOfHBAtExtractDateInPaymentFile;

    /**
     * Path for storing ClaimIDs with status of HB at extract date Suspended.
     */
    private Path claimIDsWithStatusOfHBAtExtractDateSuspendedFile;

    /**
     * Path for storing ClaimIDs with status of HB at extract date Other.
     */
    private Path claimIDsWithStatusOfHBAtExtractDateOtherFile;

    /**
     * Path for storing ClaimIDs with status of CTB at extract date InPayment.
     */
    private Path claimIDsWithStatusOfCTBAtExtractDateInPaymentFile;

    /**
     * Path for storing ClaimIDs with status of CTB at extract date Suspended.
     */
    private Path claimIDsWithStatusOfCTBAtExtractDateSuspendedFile;

    /**
     * Path for storing ClaimIDs with status of CTB at extract date Other.
     */
    private Path claimIDsWithStatusOfCTBAtExtractDateOtherFile;

    /**
     * Path for storing sRecordsWithoutDRecords.
     */
    private Path sRecordsWithoutDRecordsFile;

    /**
     * Path for storing ClaimIDs and count of records with SRecords.
     */
    private Path claimIDAndCountOfRecordsWithSRecordsFile;

    /**
     * For storing the ClaimID of records without a mappable Claimant Postcode.
     */
    private Set<SHBE_ClaimID> claimIDsOfClaimsWithoutAMappableClaimantPostcode;

    /**
     * Path for storing ClaimIDs of claims without a mappable claimant postcode.
     */
    private Path claimIDsOfClaimsWithoutAMappableClaimantPostcodeFile;

    /**
     * ClaimIDs mapped to PersonIDs of Claimants.
     */
    private Map<SHBE_ClaimID, SHBE_PersonID> claimIDToClaimantPersonIDLookup;

    /**
     * ClaimIDs mapped to PersonIDs of Partners. If there is no main Partner for
     * the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, SHBE_PersonID> claimIDToPartnerPersonIDLookup;

    /**
     * ClaimIDs mapped to {@code Set<SHBE_PersonID>} of Dependents. If there are
     * no Dependents for the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, Set<SHBE_PersonID>> claimIDToDependentPersonIDsLookup;

    /**
     * ClaimIDs mapped to {@code Set<SHBE_PersonID>} of NonDependents. If there
     * are no NonDependents for the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, Set<SHBE_PersonID>> claimIDToNonDependentPersonIDsLookup;

    /**
     * ClaimIDs of Claims with Claimants that are Claimants in another claim.
     */
    private Set<SHBE_ClaimID> claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Claimants that are Partners in another claim.
     */
    private Set<SHBE_ClaimID> claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Claimants in another claim.
     */
    private Set<SHBE_ClaimID> claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Partners in multiple claims.
     */
    private Set<SHBE_ClaimID> claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with NonDependents that are Claimants or Partners in
     * another claim.
     */
    private Set<SHBE_ClaimID> claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim;

    /**
     * SHBE_PersonIDs of Claimants that are in multiple claims in a month mapped
     * to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * SHBE_PersonIDs of Partners that are in multiple claims in a month mapped
     * to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * SHBE_PersonIDs of NonDependents that are in multiple claims in a month
     * mapped to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * ClaimIDs mapped to Postcode SHBE_IDs.
     */
    private Map<SHBE_ClaimID, UKP_RecordID> claimIDToPostcodeIDLookup;

    /**
     * ClaimIDs of the claims that have had PostcodeF updated from the future.
     * This is only to be stored if the postcode was previously of an invalid
     * format.
     */
    private Set<SHBE_ClaimID> claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture;

    /**
     * ClaimIDs. This is only used when reading the data to check that ClaimIDs
     * are unique.
     */
    private Set<SHBE_ClaimID> claimIDs;

    /**
     * For storing ClaimIDs of new SHBE claims.
     */
    private Set<SHBE_ClaimID> claimIDsOfNewSHBEClaims;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Claimant
     * before.
     */
    private Set<SHBE_ClaimID> claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Partner
     * before.
     */
    private Set<SHBE_ClaimID> claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a NonDependent
     * before.
     */
    private Set<SHBE_ClaimID> claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant is new.
     */
    private Set<SHBE_ClaimID> claimIDsOfNewSHBEClaimsWhereClaimantIsNew;

    /**
     * ClaimIDs mapped to TenancyType.
     */
    private Map<SHBE_ClaimID, Integer> claimIDToTenancyTypeLookup;

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
    private Set<SHBE_ClaimID> claimIDsOfInvalidClaimantNINOClaims;

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
     * claimIDToClaimantPersonIDLookupFile file.
     */
    private Path claimIDToClaimantPersonIDLookupFile;

    /**
     * claimIDToPartnerPersonIDLookup file.
     */
    private Path claimIDToPartnerPersonIDLookupFile;

    /**
     * claimIDToDependentPersonIDsLookupFile file.
     */
    private Path claimIDToDependentPersonIDsLookupFile;

    /**
     * claimIDToNonDependentPersonIDsLookupFile file.
     */
    private Path claimIDToNonDependentPersonIDsLookupFile;

    /**
     * claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile file.
     */
    private Path claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;

    /**
     * claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile file.
     */
    private Path claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;

    /**
     * claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile file.
     */
    private Path claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;

    /**
     * claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile file.
     */
    private Path claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;

    /**
     * ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile file.
     */
    private Path claimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;

    /**
     * claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile file.
     */
    private Path claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile file.
     */
    private Path partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile file.
     */
    private Path nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * Claim ID to postcode ID file.
     */
    private Path cid2postcodeIDFile;

    /**
     * claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile file.
     */
    private Path claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;

    /**
     * claimIDToTenancyTypeLookupFile file.
     */
    private Path claimIDToTenancyTypeLookupFile;

    /**
     * loadSummary file.
     */
    private Path loadSummaryFile;

    /**
     * recordIDsNotLoaded file.
     */
    private Path recordIDsNotLoadedFile;

    /**
     * claimIDsOfInvalidClaimantNINOClaimsFile file.
     */
    private Path claimIDsOfInvalidClaimantNINOClaimsFile;

    /**
     * claimantPostcodesUnmappableFile file.
     */
    private Path claimantPostcodesUnmappableFile;

    /**
     * claimantPostcodesModifiedFile file.
     */
    private Path claimantPostcodesModifiedFile;

    /**
     * claimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile file.
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
     * If not initialised, initialises {@link #claimIDsOfNewSHBEClaims} then
     * returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaims} initialised first if it is
     * {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaims(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            env.checkAndMaybeFreeMemory();
            return getClaimIDsOfNewSHBEClaims();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfNewSHBEClaims(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDsOfNewSHBEClaims} then
     * returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaims} initialised first if it is
     * {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaims()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfNewSHBEClaims == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsFile();
            if (Files.exists(f)) {
                claimIDsOfNewSHBEClaims = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfNewSHBEClaims = new HashSet<>();
            }
        }
        return claimIDsOfNewSHBEClaims;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore} then
     * returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            env.checkAndMaybeFreeMemory();
            return getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * /**
     * If not initialised, initialises
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore} then
     * returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile();
            if (Files.exists(f)) {
                claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = new HashSet<>();
            }
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            env.checkAndMaybeFreeMemory();
            return getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile();
            if (Files.exists(f)) {
                claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = new HashSet<>();
            }
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore} then
     * returns it.
     *
     * @return
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore} then
     * returns it.
     *
     * @return
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile();
            if (Files.exists(f)) {
                claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = new HashSet<>();
            }
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;
    }

    /**
     * If not initialised, initialises claimIDsOfNewSHBEClaimsWhereClaimantIsNew
     * If not initialised, initialises
     * {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDsOfNewSHBEClaimsWhereClaimantIsNew}
     * then returns it.
     *
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantIsNew}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew() throws IOException, ClassNotFoundException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantIsNew == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile();
            if (Files.exists(f)) {
                claimIDsOfNewSHBEClaimsWhereClaimantIsNew = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfNewSHBEClaimsWhereClaimantIsNew = new HashSet<>();
            }
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantIsNew;
    }

    /**
     * If not initialised, initialises
     * {@link #cottingleySpringsCaravanParkPairedClaimIDs} then
     * returns it.
     *
     * @return {@link #cottingleySpringsCaravanParkPairedClaimIDs}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCottingleySpringsCaravanParkPairedClaimIDs(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCottingleySpringsCaravanParkPairedClaimIDs();
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
     * {@link #cottingleySpringsCaravanParkPairedClaimIDs} then returns it.
     *
     * @return {@link #cottingleySpringsCaravanParkPairedClaimIDs}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getCottingleySpringsCaravanParkPairedClaimIDs() throws IOException, ClassNotFoundException {
        if (cottingleySpringsCaravanParkPairedClaimIDs == null) {
            Path f = getCottingleySpringsCaravanParkPairedClaimIDsFile();
            if (Files.exists(f)) {
                cottingleySpringsCaravanParkPairedClaimIDs = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                cottingleySpringsCaravanParkPairedClaimIDs = new HashSet<>();
            }
        }
        return cottingleySpringsCaravanParkPairedClaimIDs;
    }

    /**
     * If not initialised, initialises
     * {@link #ClaimIDsWithStatusOfHBAtExtractDateInPayment} then
     * returns it.
     *
     * @return {@link #ClaimIDsWithStatusOfHBAtExtractDateInPayment}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateInPayment(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateInPayment();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsWithStatusOfHBAtExtractDateInPayment(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #ClaimIDsWithStatusOfHBAtExtractDateInPayment} then returns it.
     *
     * @return {@link #ClaimIDsWithStatusOfHBAtExtractDateInPayment}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateInPayment() 
            throws IOException, ClassNotFoundException {
        if (claimIDsWithStatusOfHBAtExtractDateInPayment == null) {
            Path f;
            f = getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile();
            if (Files.exists(f)) {
                claimIDsWithStatusOfHBAtExtractDateInPayment = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsWithStatusOfHBAtExtractDateInPayment = new HashSet<>();
            }
        }
        return claimIDsWithStatusOfHBAtExtractDateInPayment;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsWithStatusOfHBAtExtractDateSuspended} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsWithStatusOfHBAtExtractDateSuspended}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateSuspended(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateSuspended();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsWithStatusOfHBAtExtractDateSuspended(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsWithStatusOfHBAtExtractDateSuspended} then returns it.
     *
     * @return {@link #claimIDsWithStatusOfHBAtExtractDateSuspended}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateSuspended() 
            throws IOException, ClassNotFoundException {
        if (claimIDsWithStatusOfHBAtExtractDateSuspended == null) {
            Path f;
            f = getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile();
            if (Files.exists(f)) {
                claimIDsWithStatusOfHBAtExtractDateSuspended = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsWithStatusOfHBAtExtractDateSuspended = new HashSet<>();
            }
        }
        return claimIDsWithStatusOfHBAtExtractDateSuspended;
    }

    /**
     * If not initialised, initialises {@link #claimIDsWithStatusOfHBAtExtractDateOther}
     * then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsWithStatusOfHBAtExtractDateOther}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateOther(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateOther();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsWithStatusOfHBAtExtractDateOther(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDsWithStatusOfHBAtExtractDateOther}
     * then returns it.
     *
     * @return {@link #claimIDsWithStatusOfHBAtExtractDateOther}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateOther()
            throws IOException, ClassNotFoundException {
        if (claimIDsWithStatusOfHBAtExtractDateOther == null) {
            Path f;
            f = getClaimIDsWithStatusOfHBAtExtractDateOtherFile();
            if (Files.exists(f)) {
                claimIDsWithStatusOfHBAtExtractDateOther = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsWithStatusOfHBAtExtractDateOther = new HashSet<>();
            }
        }
        return claimIDsWithStatusOfHBAtExtractDateOther;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsWithStatusOfCTBAtExtractDateInPayment} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsWithStatusOfCTBAtExtractDateInPayment}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateInPayment(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateInPayment();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsWithStatusOfCTBAtExtractDateInPayment(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsWithStatusOfCTBAtExtractDateInPayment} then returns it.
     *
     * @return {@link #claimIDsWithStatusOfCTBAtExtractDateInPayment}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateInPayment() 
            throws IOException, ClassNotFoundException {
        if (claimIDsWithStatusOfCTBAtExtractDateInPayment == null) {
            Path f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile();
            if (Files.exists(f)) {
                claimIDsWithStatusOfCTBAtExtractDateInPayment = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsWithStatusOfCTBAtExtractDateInPayment = new HashSet<>();
            }
        }
        return claimIDsWithStatusOfCTBAtExtractDateInPayment;
    }

    /**
     * If not initialised, initialises {@link #claimIDsWithStatusOfCTBAtExtractDateSuspended}
     *  then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return 
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateSuspended(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateSuspended();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsWithStatusOfCTBAtExtractDateSuspended(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsWithStatusOfCTBAtExtractDateSuspended} then returns it.
     *
     * @return {@link #claimIDsWithStatusOfCTBAtExtractDateSuspended}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateSuspended()
            throws IOException, ClassNotFoundException {
        if (claimIDsWithStatusOfCTBAtExtractDateSuspended == null) {
            Path f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile();
            if (Files.exists(f)) {
                claimIDsWithStatusOfCTBAtExtractDateSuspended = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsWithStatusOfCTBAtExtractDateSuspended = new HashSet<>();
            }
        }
        return claimIDsWithStatusOfCTBAtExtractDateSuspended;
    }

    /**
     * If not initialised, initialises claimIDsWithStatusOfCTBAtExtractDateOther
     * then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsWithStatusOfCTBAtExtractDateOther}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateOther(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateOther();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsWithStatusOfCTBAtExtractDateOther(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDsWithStatusOfCTBAtExtractDateOther}
     * then returns it.
     *
     * @return {@link #claimIDsWithStatusOfCTBAtExtractDateOther}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateOther() throws IOException, ClassNotFoundException {
        if (claimIDsWithStatusOfCTBAtExtractDateOther == null) {
            Path f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateOtherFile();
            if (Files.exists(f)) {
                claimIDsWithStatusOfCTBAtExtractDateOther = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsWithStatusOfCTBAtExtractDateOther = new HashSet<>();
            }
        }
        return claimIDsWithStatusOfCTBAtExtractDateOther;
    }

    /**
     * If not initialised, initialises {@link #sRecordsWithoutDRecords} then returns it.
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
     * If not initialised, initialises {@link #claimIDAndCountOfRecordsWithSRecords} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDAndCountOfRecordsWithSRecords}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Integer> getClaimIDAndCountOfRecordsWithSRecords(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDAndCountOfRecordsWithSRecords();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDAndCountOfRecordsWithSRecords(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithoutAMappableClaimantPostcode} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithoutAMappableClaimantPostcode}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithoutAValidClaimantPostcode(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithoutAMappableClaimantPostcode();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfClaimsWithoutAValidClaimantPostcode(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return the {@link #claimIDAndCountOfRecordsWithSRecords}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, Integer> getClaimIDAndCountOfRecordsWithSRecords()
            throws IOException, ClassNotFoundException {
        if (claimIDAndCountOfRecordsWithSRecords == null) {
            Path f;
            f = getClaimIDAndCountOfRecordsWithSRecordsFile();
            if (Files.exists(f)) {
                claimIDAndCountOfRecordsWithSRecords = (Map<SHBE_ClaimID, Integer>) Generic_IO.readObject(f);
            } else {
                claimIDAndCountOfRecordsWithSRecords = new HashMap<>();
            }
        }
        return claimIDAndCountOfRecordsWithSRecords;
    }

    /**
     * @return {@link #claimIDsOfClaimsWithoutAMappableClaimantPostcode}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithoutAMappableClaimantPostcode() throws IOException, ClassNotFoundException {
        if (claimIDsOfClaimsWithoutAMappableClaimantPostcode == null) {
            Path f;
            f = getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile();
            if (Files.exists(f)) {
                claimIDsOfClaimsWithoutAMappableClaimantPostcode = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfClaimsWithoutAMappableClaimantPostcode = new HashSet<>();
            }
        }
        return claimIDsOfClaimsWithoutAMappableClaimantPostcode;
    }

    /**
     * @return {@link #ym3}
     */
    public UKP_YM3 getYm3() {
        return ym3;
    }

    /**
     * @return {@link #earestYM3ForONSPDLookup}
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
        postcodeHandler = this.env.oe.getHandler();
        nearestYM3ForONSPDLookup = postcodeHandler.getNearestYM3ForONSPDLookup(ym3);
        env.env.log("YM3 " + ym3, logID);
        env.env.log("NearestYM3ForONSPDLookup " + nearestYM3ForONSPDLookup, logID);
        records = getRecords();
        claimIDsOfNewSHBEClaims = getClaimIDsOfNewSHBEClaims(env.HOOME);
        claimantPersonIDs = getClaimantPersonIDs(env.HOOME);
        partnerPersonIDs = getPartnerPersonIDs(env.HOOME);
        nonDependentPersonIDs = getNonDependentPersonIDs(env.HOOME);
        cottingleySpringsCaravanParkPairedClaimIDs = getCottingleySpringsCaravanParkPairedClaimIDs(env.HOOME);
        claimIDsWithStatusOfHBAtExtractDateInPayment = getClaimIDsWithStatusOfHBAtExtractDateInPayment(env.HOOME);
        claimIDsWithStatusOfHBAtExtractDateSuspended = getClaimIDsWithStatusOfHBAtExtractDateSuspended(env.HOOME);
        claimIDsWithStatusOfHBAtExtractDateOther = getClaimIDsWithStatusOfHBAtExtractDateOther(env.HOOME);
        claimIDsWithStatusOfCTBAtExtractDateInPayment = getClaimIDsWithStatusOfCTBAtExtractDateInPayment(env.HOOME);
        claimIDsWithStatusOfCTBAtExtractDateSuspended = getClaimIDsWithStatusOfCTBAtExtractDateSuspended(env.HOOME);
        claimIDsWithStatusOfCTBAtExtractDateOther = getClaimIDsWithStatusOfCTBAtExtractDateOther(env.HOOME);
        sRecordsWithoutDRecords = getSRecordsWithoutDRecords(env.HOOME);
        claimIDAndCountOfRecordsWithSRecords = getClaimIDAndCountOfRecordsWithSRecords(env.HOOME);
        claimIDsOfClaimsWithoutAMappableClaimantPostcode = getClaimIDsOfClaimsWithoutAValidClaimantPostcode(env.HOOME);
        claimIDToClaimantPersonIDLookup = getClaimIDToClaimantPersonIDLookup(env.HOOME);
        claimIDToPartnerPersonIDLookup = getClaimIDToPartnerPersonIDLookup(env.HOOME);
        claimIDToDependentPersonIDsLookup = getClaimIDToDependentPersonIDsLookup(env.HOOME);
        claimIDToNonDependentPersonIDsLookup = getClaimIDToNonDependentPersonIDsLookup(env.HOOME);
        claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(env.HOOME);
        claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(env.HOOME);
        claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(env.HOOME);
        claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim(env.HOOME);
        claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = getClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(env.HOOME);
        claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(env.HOOME);
        partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(env.HOOME);
        nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(env.HOOME);
        claimIDToPostcodeIDLookup = getClaimIDToPostcodeIDLookup(env.HOOME);
        claimIDToTenancyTypeLookup = getClaimIDToTenancyTypeLookup(env.HOOME);
        loadSummary = getLoadSummary(env.HOOME);
        recordIDsNotLoaded = getRecordIDsNotLoaded(env.HOOME);
        claimIDsOfInvalidClaimantNINOClaims = getClaimIDsOfInvalidClaimantNINOClaims(env.HOOME);
        claimantPostcodesUnmappable = getClaimantPostcodesUnmappable(env.HOOME);
        claimantPostcodesModified = getClaimantPostcodesModified(env.HOOME);
        claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(env.HOOME);
        claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(env.HOOME);
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
        postcodeHandler = this.env.oe.getHandler();
        nearestYM3ForONSPDLookup = postcodeHandler.getNearestYM3ForONSPDLookup(ym3);
        records = new HashMap<>();
        claimIDs = new HashSet<>();
        claimIDsOfNewSHBEClaims = new HashSet<>();
        claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = new HashSet<>();
        claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = new HashSet<>();
        claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = new HashSet<>();
        claimIDsOfNewSHBEClaimsWhereClaimantIsNew = new HashSet<>();
        claimantPersonIDs = new HashSet<>();
        partnerPersonIDs = new HashSet<>();
        nonDependentPersonIDs = new HashSet<>();
        cottingleySpringsCaravanParkPairedClaimIDs = new HashSet<>();
        claimIDsWithStatusOfHBAtExtractDateInPayment = new HashSet<>();
        claimIDsWithStatusOfHBAtExtractDateSuspended = new HashSet<>();
        claimIDsWithStatusOfHBAtExtractDateOther = new HashSet<>();
        claimIDsWithStatusOfCTBAtExtractDateInPayment = new HashSet<>();
        claimIDsWithStatusOfCTBAtExtractDateSuspended = new HashSet<>();
        claimIDsWithStatusOfCTBAtExtractDateOther = new HashSet<>();
        sRecordsWithoutDRecords = new HashMap<>();
        claimIDAndCountOfRecordsWithSRecords = new HashMap<>();
        claimIDsOfClaimsWithoutAMappableClaimantPostcode = new HashSet<>();
        claimIDToClaimantPersonIDLookup = new HashMap<>();
        claimIDToPartnerPersonIDLookup = new HashMap<>();
        claimIDToDependentPersonIDsLookup = new HashMap<>();
        claimIDToNonDependentPersonIDsLookup = new HashMap<>();
        claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = new HashSet<>();
        claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = new HashSet<>();
        claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = new HashSet<>();
        claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = new HashSet<>();
        claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = new HashSet<>();
        claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
        partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
        nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
        claimIDToPostcodeIDLookup = new HashMap<>();
        claimIDToTenancyTypeLookup = new HashMap<>();
        loadSummary = new HashMap<>();
        recordIDsNotLoaded = new ArrayList<>();
        claimIDsOfInvalidClaimantNINOClaims = new HashSet<>();
        claimantPostcodesUnmappable = new HashMap<>();
        claimantPostcodesModified = new HashMap<>();
        claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = new HashMap<>();
        claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = new HashSet<>();
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
        Map<SHBE_PersonID, Set<SHBE_ClaimID>> personIDToClaimIDsLookup;

        /**
         * Initialise mappings from SHBE_Handler.
         */
        correctedPostcodes = handler.getCorrectedPostcodes();
        claimRefToOriginalPostcodes = correctedPostcodes.getClaimRefToOriginalPostcodes();
        claimRefToCorrectedPostcodes = correctedPostcodes.getClaimRefToCorrectedPostcodes();
        postcodesCheckedAsMappable = correctedPostcodes.getPostcodesCheckedAsMappable();
        //UnmappableToMappablePostcodes = SHBE_CorrectedPostcodes.getUnmappableToMappablePostcodes();

        n2nid = handler.getN2nid();
        nid2n = handler.getNINOIDToNINOLookup();
        d2did = handler.getD2did();
        did2d = handler.getDid2d();
        allClaimantPersonIDs = handler.getClaimantPersonIDs();
        allPartnerPersonIDs = handler.getPartnerPersonIDs();
        allNonDependentIDs = handler.getNonDependentPersonIDs();
        personIDToClaimIDsLookup = handler.getPid2cids();
        p2pid = handler.getPostcodeToPostcodeIDLookup();
        pid2p = handler.getPid2p();
        pid2point = handler.getPostcodeIDToPointLookup(ym3);
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
                                    claimID = handler.getClaimIDAddIfNeeded(claimRef);
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
                                                claimIDs,
                                                claimIDsOfNewSHBEClaims);
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
                                                claimIDsWithStatusOfHBAtExtractDateOther.add(claimID);
                                                break;
                                            }
                                            case 1: {
                                                claimIDsWithStatusOfHBAtExtractDateInPayment.add(claimID);
                                                break;
                                            }
                                            case 2: {
                                                claimIDsWithStatusOfHBAtExtractDateSuspended.add(claimID);
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
                                                claimIDsWithStatusOfCTBAtExtractDateOther.add(claimID);
                                                break;
                                            }
                                            case 1: {
                                                claimIDsWithStatusOfCTBAtExtractDateInPayment.add(claimID);
                                                break;
                                            }
                                            case 2: {
                                                claimIDsWithStatusOfCTBAtExtractDateSuspended.add(claimID);
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
                                            record.ClaimPostcodeF = postcodeHandler.formatPostcode(postcode);
                                            record.ClaimPostcodeFManModified = false;
                                            record.ClaimPostcodeFAutoModified = false;
                                            // Do man modifications (modifications using lookups provided by LCC based on a manual checking of addresses)
                                            if (claimRefToOriginalPostcodes.keySet().contains(claimRef)) {
                                                ArrayList<String> priginalPostcodes;
                                                priginalPostcodes = claimRefToOriginalPostcodes.get(claimRef);
                                                if (priginalPostcodes.contains(record.ClaimPostcodeF)) {
                                                    ArrayList<String> CorrectedPostcodes;
                                                    CorrectedPostcodes = claimRefToCorrectedPostcodes.get(claimRef);
                                                    record.ClaimPostcodeF = CorrectedPostcodes.get(priginalPostcodes.indexOf(record.ClaimPostcodeF));
                                                    record.ClaimPostcodeFManModified = true;
                                                }
                                            } else {
                                                // Do auto modifications ()
                                                if (record.ClaimPostcodeF.length() > 5) {
                                                    /**
                                                     * Remove any 0 which
                                                     * probably should not be
                                                     * there in the first part
                                                     * of the postcode. For
                                                     * example "LS02 9JT" should
                                                     * probably be "LS2 9JT".
                                                     */
                                                    if (record.ClaimPostcodeF.charAt(record.ClaimPostcodeF.length() - 5) == '0') {
                                                        //System.out.println("record.ClaimPostcodeF " + record.ClaimPostcodeF);
                                                        record.ClaimPostcodeF = record.ClaimPostcodeF.replaceFirst("0", "");
                                                        //System.out.println("Postcode " + Postcode);
                                                        //System.out.println("record.ClaimPostcodeF " + record.ClaimPostcodeF);
                                                        record.ClaimPostcodeFAutoModified = true;
                                                    }
                                                    /**
                                                     * Change any "O" which
                                                     * should be a "0" in the
                                                     * second part of the
                                                     * postcode. For example
                                                     * "LS2 OJT" should probably
                                                     * be "LS2 0JT".
                                                     */
                                                    if (record.ClaimPostcodeF.charAt(record.ClaimPostcodeF.length() - 3) == 'O') {
                                                        //System.out.println("record.ClaimPostcodeF " + record.ClaimPostcodeF);
                                                        record.ClaimPostcodeF = record.ClaimPostcodeF.substring(0, record.ClaimPostcodeF.length() - 3)
                                                                + "0" + record.ClaimPostcodeF.substring(record.ClaimPostcodeF.length() - 2);
                                                        //System.out.println("Postcode " + Postcode);
                                                        //System.out.println("record.ClaimPostcodeF " + record.ClaimPostcodeF);
                                                        record.ClaimPostcodeFAutoModified = true;
                                                    }
                                                }
                                            }
                                            // Check if record.ClaimPostcodeF is mappable
                                            boolean isMappablePostcode;
                                            isMappablePostcode = postcodeHandler.isMappablePostcode(nearestYM3ForONSPDLookup, record.ClaimPostcodeF);
                                            boolean isMappablePostcodeLastestYM3 = false;
                                            if (!isMappablePostcode) {
                                                isMappablePostcodeLastestYM3 = postcodeHandler.isMappablePostcode(lym3, record.ClaimPostcodeF);
                                                if (isMappablePostcodeLastestYM3) {
                                                    env.env.log("Postcode " + postcode + " is not in the " + nearestYM3ForONSPDLookup + " ONSPD, "
                                                            + "but is in the " + lym3 + " ONSPD!", logID);
                                                    isMappablePostcode = isMappablePostcodeLastestYM3;
                                                }
                                            }
                                            // For those that are mappable having been modified, store the modification
                                            if (isMappablePostcode) {
                                                if (record.ClaimPostcodeFAutoModified) {
                                                    String claimPostcodeFNoSpaces = record.ClaimPostcodeF.replaceAll(" ", "");
                                                    if (!postcode.replaceAll(" ", "").equalsIgnoreCase(claimPostcodeFNoSpaces)) {
                                                        int l;
                                                        l = record.ClaimPostcodeF.length();
                                                        String[] p;
                                                        p = new String[2];
                                                        p[0] = postcode;
                                                        p[1] = claimPostcodeFNoSpaces.substring(0, l - 3) + " " + claimPostcodeFNoSpaces.substring(l - 3);
                                                        claimantPostcodesModified.put(claimID, p);
                                                    }
                                                }
                                            }
                                            record.ClaimPostcodeFValidPostcodeFormat = postcodeHandler.checker.isValidPostcodeUnit(record.ClaimPostcodeF);
                                            if (p2pid.containsKey(record.ClaimPostcodeF)) {
                                                countOfMappableClaimantPostcodes++;
                                                record.ClaimPostcodeFMappable = true;
                                                record.PostcodeID = p2pid.get(record.ClaimPostcodeF);
                                                // Add the point to the lookup
                                                ONSPD_Point AGDT_Point;
                                                AGDT_Point = postcodeHandler.getPointFromPostcodeNew(nearestYM3ForONSPDLookup,
                                                        UKP_Data.TYPE_UNIT,
                                                        record.ClaimPostcodeF);
                                                pid2point.put(record.PostcodeID, AGDT_Point);
                                            } else if (isMappablePostcode) {
                                                countOfMappableClaimantPostcodes++;
                                                countOfNewClaimantPostcodes++;
                                                countOfNewMappableClaimantPostcodes++;
                                                record.ClaimPostcodeFMappable = true;
                                                record.PostcodeID = handler.getPostcodeIDAddIfNeeded(
                                                        record.ClaimPostcodeF,
                                                        p2pid,
                                                        pid2p);
                                                // Add the point to the lookup
                                                ONSPD_Point p;
                                                if (isMappablePostcodeLastestYM3) {
                                                    p = postcodeHandler.getPointFromPostcodeNew(
                                                            lym3,
                                                            UKP_Data.TYPE_UNIT,
                                                            record.ClaimPostcodeF);
                                                } else {
                                                    p = postcodeHandler.getPointFromPostcodeNew(nearestYM3ForONSPDLookup,
                                                            UKP_Data.TYPE_UNIT,
                                                            record.ClaimPostcodeF);
                                                }
                                                pid2point.put(record.PostcodeID, p);
                                            } else {
                                                countOfNonMappableClaimantPostcodes++;
                                                countOfNewClaimantPostcodes++;
                                                if (record.ClaimPostcodeFValidPostcodeFormat) {
                                                    countOfValidFormatClaimantPostcodes++;
                                                }
                                                record.ClaimPostcodeFMappable = false;
                                                claimIDsOfClaimsWithoutAMappableClaimantPostcode.add(claimID);
                                                boolean PostcodeCheckedAsMappable;
                                                PostcodeCheckedAsMappable = postcodesCheckedAsMappable.contains(record.ClaimPostcodeF);
                                                if (PostcodeCheckedAsMappable) {
                                                    claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes.put(claimID, postcode);
                                                } else {
                                                    // Store unmappable claimant postcode.
                                                    claimantPostcodesUnmappable.put(claimID, postcode);
                                                }
                                            }
                                            claimIDToPostcodeIDLookup.put(claimID, record.PostcodeID);
                                            claimIDToTenancyTypeLookup.put(claimID, tenancyType);
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
                                            claimIDsOfInvalidClaimantNINOClaims.add(claimID);
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
                                        if (claimIDsOfNewSHBEClaims.contains(claimID)) {
                                            addToNew = true;
                                            if (allClaimantPersonIDs.contains(claimantPersonID)) {
                                                claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (allPartnerPersonIDs.contains(claimantPersonID)) {
                                                claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (allNonDependentIDs.contains(claimantPersonID)) {
                                                claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (addToNew) {
                                                claimIDsOfNewSHBEClaimsWhereClaimantIsNew.add(claimID);
                                            }
                                        }
                                        /**
                                         * If ClaimantSHBE_PersonID is already
                                         * in ClaimIDToClaimantPersonIDLookup.
                                         * then ClaimantSHBE_PersonID has
                                         * multiple claims in a month.
                                         */
                                        if (claimIDToClaimantPersonIDLookup.containsValue(claimantPersonID)) {
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
                                            key = Generic_Collections.getKeys(claimIDToClaimantPersonIDLookup,
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
                                                    cottingleySpringsCaravanParkPairedClaimIDs.add(claimID);
                                                    cottingleySpringsCaravanParkPairedClaimIDs.add(otherClaimID);
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
//                                                        env.log(DRecord.toStringBrief());
//                                                        env.log("Other D Record");
//                                                        env.log(otherRecord.DRecord.toStringBrief());
                                                        /**
                                                         * Add to
                                                         * ClaimantsWithMultipleClaimsInAMonth.
                                                         */
                                                        claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(claimID);
                                                        claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(otherRecord.getClaimID());
                                                        Set<SHBE_ClaimID> set;
                                                        if (claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(claimantPersonID)) {
                                                            set = claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(claimantPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(claimantPersonID, set);
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
                                        if (claimIDToPartnerPersonIDLookup.containsValue(claimantPersonID)) {
                                            /**
                                             * Ignore if this is a
                                             * CottingleySpringsCaravanParkPairedClaimIDs.
                                             * It may be that there are partners
                                             * shared in these claims, but such
                                             * a thing is ignored for now.
                                             */
                                            if (!cottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                                                /**
                                                 * If Claimant is a Partner in
                                                 * another claim add to
                                                 * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim
                                                 * and
                                                 * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.
                                                 */
                                                key = Generic_Collections.getKeys(claimIDToPartnerPersonIDLookup,
                                                        claimantPersonID).stream().findFirst();
                                                if (key != null) {
                                                    otherClaimID = (SHBE_ClaimID) key;
                                                    claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(otherClaimID);
                                                }
                                                claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(claimID);
//                                                env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                env.log("Claimant with NINO " + ClaimantNINO
//                                                        + " DOB " + ClaimantDOB
//                                                        + " in ClaimRef " + ClaimRef
//                                                        + " is a Partner in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                            }
                                        }
                                        SHBE_PersonID PartnerPersonID;
                                        PartnerPersonID = null;
                                        if (dRecord.getPartnerFlag() > 0) {
                                            /**
                                             * Add Partner.
                                             */
                                            PartnerPersonID = handler.getPersonID(
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
                                            if (claimIDToPartnerPersonIDLookup.containsValue(PartnerPersonID)) {
                                                /*
                                                    * Ignore if this is a cottingleySpringsCaravanParkPairedClaimIDs.
                                                    * It may be that there are partners shared in these claims, but such
                                                    * a thing is ignored for now.
                                                 */
                                                if (!cottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                                                    key = Generic_Collections.getKeys(claimIDToPartnerPersonIDLookup,
                                                            PartnerPersonID).stream().findFirst();
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ClaimID) key;
                                                        Set<SHBE_ClaimID> set;
                                                        if (partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(claimID);
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
                                            if (claimIDToClaimantPersonIDLookup.containsValue(PartnerPersonID)) {
                                                /**
                                                 * Ignore if this is a
                                                 * CottingleySpringsCaravanParkPairedClaimIDs.
                                                 * It may be that there are
                                                 * partners shared in these
                                                 * claims, but such a thing is
                                                 * ignored for now.
                                                 */
                                                if (!cottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                                                    key = Generic_Collections.getKeys(claimIDToClaimantPersonIDLookup,
                                                            PartnerPersonID).stream().findFirst();
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ClaimID) key;
                                                        Set<SHBE_ClaimID> set;
                                                        if (partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        if (claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(claimID);
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    env.log("Partner with NINO " + NINOIDToNINOLookup.get(PartnerPersonID.getNINO_ID())
//                                                            + " DOB " + DOBIDToDOBLookup.get(PartnerPersonID.getDOB_ID())
//                                                            + " in ClaimRef " + ClaimRef
//                                                            + " is a Claimant in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                }
                                                claimIDToPartnerPersonIDLookup.put(claimID, PartnerPersonID);
                                            }
                                        }
                                        /**
                                         * Add to
                                         * ClaimIDToClaimantPersonIDLookup.
                                         */
                                        claimIDToClaimantPersonIDLookup.put(claimID, claimantPersonID);

                                        /**
                                         * Add to AllClaimantPersonIDs and
                                         * AllPartnerPersonIDs.
                                         */
                                        allClaimantPersonIDs.add(claimantPersonID);
                                        claimantPersonIDs.add(claimantPersonID);
                                        addToPersonIDToClaimRefsLookup(
                                                claimID,
                                                claimantPersonID,
                                                personIDToClaimIDsLookup);
                                        if (PartnerPersonID != null) {
                                            allPartnerPersonIDs.add(PartnerPersonID);
                                            partnerPersonIDs.add(PartnerPersonID);
                                            claimIDToPartnerPersonIDLookup.put(claimID, PartnerPersonID);
                                            addToPersonIDToClaimRefsLookup(
                                                    claimID,
                                                    PartnerPersonID,
                                                    personIDToClaimIDsLookup);
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
                    allNonDependentIDs, personIDToClaimIDsLookup,
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
                claimIDsOfNewSHBEClaims.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore,
                claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore,
                claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore,
                claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantIsNew,
                claimIDsOfNewSHBEClaimsWhereClaimantIsNew.size());
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
        set.addAll(claimIDToClaimantPersonIDLookup.values());
        allSet.addAll(set);
        addLoadSummaryCount(SHBE_Strings.s_CountOfUniqueClaimants, set.size());
        /**
         * Partners
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartners,
                claimIDToPartnerPersonIDLookup.size());
        set = handler.getUniquePersonIDs0(claimIDToPartnerPersonIDLookup);
        allSet.addAll(set);
        addLoadSummaryCount(SHBE_Strings.s_CountOfUniquePartners, set.size());
        /**
         * Dependents
         */
        int nDependents;
        nDependents = Generic_Collections.getCountInt(claimIDToDependentPersonIDsLookup);
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfDependentsInAllClaims,
                nDependents);
        set = handler.getUniquePersonIDs(claimIDToDependentPersonIDsLookup);
        allSet.addAll(set);
        int CountOfUniqueDependents = set.size();
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfUniqueDependents,
                CountOfUniqueDependents);
        /**
         * NonDependents
         */
        int nNonDependents;
        nNonDependents = Generic_Collections.getCountInt(claimIDToNonDependentPersonIDsLookup);
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfNonDependentsInAllClaims,
                nNonDependents);
        set = handler.getUniquePersonIDs(claimIDToNonDependentPersonIDsLookup);
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
                claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithClaimantsThatArePartnersInAnotherClaim,
                claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim,
                claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartnersThatArePartnersInAnotherClaim,
                claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimantsInMultipleClaimsInAMonth,
                claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfPartnersInMultipleClaimsInAMonth,
                partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNonDependentsInMultipleClaimsInAMonth,
                nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
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
        Generic_IO.writeObject(claimIDsOfNewSHBEClaims,
                getClaimIDsOfNewSHBEClaimsFile());
        Generic_IO.writeObject(claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore,
                getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile());
        Generic_IO.writeObject(claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore,
                getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile());
        Generic_IO.writeObject(claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore,
                getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile());
        Generic_IO.writeObject(claimIDsOfNewSHBEClaimsWhereClaimantIsNew,
                getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile());
        Generic_IO.writeObject(claimantPersonIDs, getClaimantPersonIDsFile());
        Generic_IO.writeObject(partnerPersonIDs, getPartnerPersonIDsFile());
        Generic_IO.writeObject(nonDependentPersonIDs, getNonDependentPersonIDsFile());
        Generic_IO.writeObject(cottingleySpringsCaravanParkPairedClaimIDs,
                getCottingleySpringsCaravanParkPairedClaimIDsFile());
        Generic_IO.writeObject(claimIDsWithStatusOfHBAtExtractDateInPayment,
                getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile());
        Generic_IO.writeObject(claimIDsWithStatusOfHBAtExtractDateSuspended,
                getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile());
        Generic_IO.writeObject(claimIDsWithStatusOfHBAtExtractDateOther,
                getClaimIDsWithStatusOfHBAtExtractDateOtherFile());
        Generic_IO.writeObject(claimIDsWithStatusOfCTBAtExtractDateInPayment,
                getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile());
        Generic_IO.writeObject(claimIDsWithStatusOfCTBAtExtractDateSuspended,
                getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile());
        Generic_IO.writeObject(claimIDsWithStatusOfCTBAtExtractDateOther,
                getClaimIDsWithStatusOfCTBAtExtractDateOtherFile());
        Generic_IO.writeObject(sRecordsWithoutDRecords, getSRecordsWithoutDRecordsFile());
        Generic_IO.writeObject(claimIDAndCountOfRecordsWithSRecords,
                getClaimIDAndCountOfRecordsWithSRecordsFile());
        Generic_IO.writeObject(claimIDsOfClaimsWithoutAMappableClaimantPostcode,
                getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile());
        Generic_IO.writeObject(claimIDToClaimantPersonIDLookup,
                getClaimIDToClaimantPersonIDLookupFile());
        Generic_IO.writeObject(claimIDToPartnerPersonIDLookup,
                getClaimIDToPartnerPersonIDLookupFile());
        Generic_IO.writeObject(claimIDToNonDependentPersonIDsLookup,
                getClaimIDToNonDependentPersonIDsLookupFile());
        Generic_IO.writeObject(claimIDToDependentPersonIDsLookup,
                getClaimIDToDependentPersonIDsLookupFile());
        Generic_IO.writeObject(claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim,
                getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile());
        Generic_IO.writeObject(claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim,
                getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile());
        Generic_IO.writeObject(claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim,
                getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile());
        Generic_IO.writeObject(claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim,
                getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile());
        Generic_IO.writeObject(claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim,
                getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile());
        Generic_IO.writeObject(claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile());
        Generic_IO.writeObject(partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile());
        Generic_IO.writeObject(nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile());
        Generic_IO.writeObject(claimIDToPostcodeIDLookup, getCid2postcodeIDFile());
        Generic_IO.writeObject(claimIDToTenancyTypeLookup, getClaimIDToTenancyTypeLookupFile());
        Generic_IO.writeObject(loadSummary, getLoadSummaryFile());
        Generic_IO.writeObject(recordIDsNotLoaded, getRecordIDsNotLoadedFile());
        Generic_IO.writeObject(claimIDsOfInvalidClaimantNINOClaims, getClaimIDsOfInvalidClaimantNINOClaimsFile());
        Generic_IO.writeObject(claimantPostcodesUnmappable, getClaimantPostcodesUnmappableFile());
        Generic_IO.writeObject(claimantPostcodesModified, getClaimantPostcodesModifiedFile());
        Generic_IO.writeObject(claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes, getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile());
        Generic_IO.writeObject(claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture, getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile());

        // Write out other outputs
        // Write out ClaimRefs of ClaimantsInMultipleClaimsInAMonth
        String YMN;
        YMN = handler.getYearMonthNumber(inputFilename);
        writeOut(claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                "ClaimantsInMultipleClaimsInAMonth", YMN,
                cid2c, nid2n,
                did2d);
        // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
        writeOut(partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                "PartnersInMultipleClaimsInAMonth", YMN,
                cid2c, nid2n,
                did2d);
        // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
        writeOut(nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                "NonDependentsInMultipleClaimsInAMonth", YMN,
                cid2c, nid2n,
                did2d);
        // Write out ClaimRefs of ClaimIDOfInvalidClaimantNINOClaims
        String name = "ClaimRefsOfInvalidClaimantNINOClaims";
        int logID2 = this.env.env.initLog(name, ".csv");
        Iterator<SHBE_ClaimID> ite2;
        ite2 = claimIDsOfInvalidClaimantNINOClaims.iterator();
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
     * @param SHBE_Handler SHBE_Handler
     * @param SHBE_Record SHBE_Record
     * @param n2nid NINOToNINOIDLookup
     * @param nid2n NINOIDToNINOLookup
     * @param d2did DOBToDOBIDLookup
     * @param did2d DOBIDToDOBLookup
     * @param allNonDependentPersonIDs allNonDependentPersonIDs
     * @param personIDToClaimRefsLookup personIDToClaimRefsLookup
     * @param claimIDToClaimRefLookup claimIDToClaimRefLookup
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final void initSRecords(SHBE_Handler SHBE_Handler,
            SHBE_Record SHBE_Record, Map<String, SHBE_NINOID> n2nid,
            Map<SHBE_NINOID, String> nid2n, Map<String, SHBE_DOBID> d2did,
            Map<SHBE_DOBID, String> did2d,
            Set<SHBE_PersonID> allNonDependentPersonIDs,
            Map<SHBE_PersonID, Set<SHBE_ClaimID>> personIDToClaimRefsLookup,
            Map<SHBE_ClaimID, String> claimIDToClaimRefLookup
    ) throws IOException, ClassNotFoundException {
        ArrayList<SHBE_S_Record> sRecordsForClaim;
        SHBE_ClaimID claimID = SHBE_Record.getClaimID();
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
                                if (claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.contains(claimID)) {
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
                        personID = SHBE_Handler.getPersonID(nino, dob,
                                n2nid, nid2n,
                                d2did, did2d);
                        /**
                         * Add to ClaimIDToDependentPersonIDsLookup.
                         */
                        Set<SHBE_PersonID> s = claimIDToDependentPersonIDsLookup.get(claimID);
                        if (s == null) {
                            s = new HashSet<>();
                            claimIDToDependentPersonIDsLookup.put(claimID, s);
                        }
                        s.add(personID);
                        addToPersonIDToClaimRefsLookup(claimID, personID,
                                personIDToClaimRefsLookup);
                        break;
                    case 2:
                        personID = SHBE_Handler.getPersonID(nino, dob,
                                n2nid, nid2n,
                                d2did, did2d);
                        /**
                         * Ignore if this is a
                         * CottingleySpringsCaravanParkPairedClaimIDs. It may be
                         * that there are partners shared in these claims, but
                         * such a thing is ignored for now.
                         */
                        if (!cottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                            /**
                             * If NonDependent is a NonDependent in another
                             * claim add to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                             */
                            key = Generic_Collections.getKey(claimIDToNonDependentPersonIDsLookup, personID);
                            if (key != null) {
                                otherClaimID = (SHBE_ClaimID) key;
                                Set<SHBE_ClaimID> set;
                                set = nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(personID);
                                if (set == null) {
                                    set = new HashSet<>();
                                    nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(personID, set);
                                }
                                set.add(claimID);
                                set.add(otherClaimID);
                                claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
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
                            if (claimIDToClaimantPersonIDLookup.containsValue(personID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ClaimID) key;
                                    Set<SHBE_ClaimID> set;
                                    set = nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(personID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(personID, set);
                                    }
                                    set.add(claimID);
                                    set.add(otherClaimID);
                                    claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                    claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
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
                            if (claimIDToPartnerPersonIDLookup.containsValue(personID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ClaimID) key;
                                    Set<SHBE_ClaimID> set;
                                    set = nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(personID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(personID, set);
                                    }
                                    set.add(claimID);
                                    set.add(otherClaimID);
                                    claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                    claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
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
                        s = claimIDToNonDependentPersonIDsLookup.get(claimID);
                        if (s == null) {
                            s = new HashSet<>();
                            claimIDToNonDependentPersonIDsLookup.put(claimID, s);
                        }
                        s.add(personID);
                        nonDependentPersonIDs.add(personID);
                        allNonDependentPersonIDs.add(personID);
                        addToPersonIDToClaimRefsLookup(claimID, personID,
                                personIDToClaimRefsLookup);
                        break;
                    default:
                        env.env.log("Unrecognised SubRecordType " + subRecordType, logID);
                        break;
                }
            }
            SHBE_Record.SRecords = sRecordsForClaim;
            claimIDAndCountOfRecordsWithSRecords.put(claimID, sRecordsForClaim.size());
        }
        /**
         * Remove all assigned SRecords from SRecordsWithoutDRecords.
         */
        Iterator<SHBE_ClaimID> iteID;
        iteID = claimIDAndCountOfRecordsWithSRecords.keySet().iterator();
        while (iteID.hasNext()) {
            sRecordsWithoutDRecords.remove(iteID.next());
        }
    }

    private void addToPersonIDToClaimRefsLookup(
            SHBE_ClaimID ClaimID,
            SHBE_PersonID SHBE_PersonID,
            Map<SHBE_PersonID, Set<SHBE_ClaimID>> PersonIDToClaimRefsLookup) {
        Set<SHBE_ClaimID> s;
        if (PersonIDToClaimRefsLookup.containsKey(SHBE_PersonID)) {
            s = PersonIDToClaimRefsLookup.get(SHBE_PersonID);
        } else {
            s = new HashSet<>();
            PersonIDToClaimRefsLookup.put(SHBE_PersonID, s);
        }
        s.add(ClaimID);
    }

    /**
     * Month_10_2010_11_381112_D_records.csv
     * 1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,293,294,295,296,297,298,299,308,309,310,311,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341
     * hb9803_SHBE_206728k\ April\ 2008.csv
     * 1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,293,294,295,296,297,298,299,307,308,309,310,311,315,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341
     * 1,2,3,4,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274,275,276,277,278,284,285,286,287,290,291,292,293,294,295,296,297,298,299,308,309,310,311,316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341
     * 307, 315
     *
     * @param directory
     * @param filename
     * @return int
     */
    public final int readAndCheckFirstLine(Path directory, String filename) {
        int type = 0;
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
     * If not initialised, initialises {@link #claimIDToClaimantPersonIDLookup} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDToClaimantPersonIDLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToClaimantPersonIDLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToClaimantPersonIDLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDToClaimantPersonIDLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDToClaimantPersonIDLookup} then
     * returns it.
     *
     * @return {@link #claimIDToClaimantPersonIDLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToClaimantPersonIDLookup()
            throws IOException, ClassNotFoundException {
        if (claimIDToClaimantPersonIDLookup == null) {
            Path f;
            f = getClaimIDToClaimantPersonIDLookupFile();
            if (Files.exists(f)) {
                claimIDToClaimantPersonIDLookup = (Map<SHBE_ClaimID, SHBE_PersonID>) Generic_IO.readObject(f);
            } else {
                claimIDToClaimantPersonIDLookup = new HashMap<>();
            }
        }
        return claimIDToClaimantPersonIDLookup;
    }

    /**
     * If not initialised, initialises {@link #claimIDToPartnerPersonIDLookup} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDToPartnerPersonIDLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToPartnerPersonIDLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToPartnerPersonIDLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDToPartnerPersonIDLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDToPartnerPersonIDLookup} then
     * returns it.
     *
     * @return {@link #claimIDToPartnerPersonIDLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToPartnerPersonIDLookup()
            throws IOException, ClassNotFoundException {
        if (claimIDToPartnerPersonIDLookup == null) {
            Path f;
            f = getClaimIDToPartnerPersonIDLookupFile();
            if (Files.exists(f)) {
                claimIDToPartnerPersonIDLookup = (Map<SHBE_ClaimID, SHBE_PersonID>) Generic_IO.readObject(f);
            } else {
                claimIDToPartnerPersonIDLookup = new HashMap<>();
            }
        }
        return claimIDToPartnerPersonIDLookup;
    }

    /**
     * If not initialised, initialises {@link #claimIDToDependentPersonIDsLookup} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDToDependentPersonIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToDependentPersonIDsLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToDependentPersonIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDToDependentPersonIDsLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDToDependentPersonIDsLookup} then
     * returns it.
     *
     * @return {@link #claimIDToDependentPersonIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToDependentPersonIDsLookup()
            throws IOException, ClassNotFoundException {
        if (claimIDToDependentPersonIDsLookup == null) {
            Path f;
            f = getClaimIDToDependentPersonIDsLookupFile();
            if (Files.exists(f)) {
                claimIDToDependentPersonIDsLookup = (Map<SHBE_ClaimID, Set<SHBE_PersonID>>) Generic_IO.readObject(f);
            } else {
                claimIDToDependentPersonIDsLookup = new HashMap<>();
            }
        }
        return claimIDToDependentPersonIDsLookup;
    }

    /**
     * If not initialised, initialises {@link #claimIDToNonDependentPersonIDsLookup} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDToNonDependentPersonIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToNonDependentPersonIDsLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToNonDependentPersonIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDToNonDependentPersonIDsLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises {@link #claimIDToNonDependentPersonIDsLookup} then
     * returns it.
     *
     * @return {@link #claimIDToNonDependentPersonIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToNonDependentPersonIDsLookup()
            throws IOException, ClassNotFoundException {
        if (claimIDToNonDependentPersonIDsLookup == null) {
            Path f;
            f = getClaimIDToNonDependentPersonIDsLookupFile();
            if (Files.exists(f)) {
                claimIDToNonDependentPersonIDsLookup = (Map<SHBE_ClaimID, Set<SHBE_PersonID>>) Generic_IO.readObject(f);
            } else {
                claimIDToNonDependentPersonIDsLookup = new HashMap<>();
            }
        }
        return claimIDToNonDependentPersonIDsLookup;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim} then returns
     * it.
     *
     * @return {@link #claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile();
            if (Files.exists(f)) {
                claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = new HashSet<>();
            }
        }
        return claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim} then returns
     * it.
     *
     * @return {@link #claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = new HashSet<>();
            }
        }
        return claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile();
            if (Files.exists(f)) {
                claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = new HashSet<>();
            }
        }
        return claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = new HashSet<>();
            }
        }
        return claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim == null) {
            Path f = getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = new HashSet<>();
            }
        }
        return claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup} then returns
     * it.
     *
     * @return {@link #claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup()
            throws IOException, ClassNotFoundException {
        if (claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            Path f;
            f = getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (Files.exists(f)) {
                claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
            }
        }
        return claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
    }

    /**
     * If not initialised, initialises
     * {@link #partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup()
            throws IOException, ClassNotFoundException {
        if (partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            Path f = getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (Files.exists(f)) {
                partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
            }
        }
        return partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
    }

    /**
     * If not initialised, initialises
     * {@link #nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * {@link #nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup} then
     * returns it.
     *
     * @return {@link #nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup()
            throws IOException, ClassNotFoundException {
        if (nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            Path f;
            f = getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (Files.exists(f)) {
                nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
            }
        }
        return nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
    }

    /**
     * If not initialised, initialises {@link #claimIDToPostcodeLookup} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDToPostcodeLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, UKP_RecordID> getClaimIDToPostcodeIDLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToPostcodeIDLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDToPostcodeIDLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_ClaimID, UKP_RecordID> getClaimIDToPostcodeIDLookup()
            throws IOException, ClassNotFoundException {
        if (claimIDToPostcodeIDLookup == null) {
            Path f;
            f = getCid2postcodeIDFile();
            if (Files.exists(f)) {
                claimIDToPostcodeIDLookup = (Map<SHBE_ClaimID, UKP_RecordID>) Generic_IO.readObject(f);
            } else {
                claimIDToPostcodeIDLookup = new HashMap<>();
            }
        }
        return claimIDToPostcodeIDLookup;
    }

    /**
     * If not initialised, initialises
     * {@link #claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture} then returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture == null) {
            Path f;
            f = getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile();
            if (Files.exists(f)) {
                claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = new HashSet<>();
            }
        }
        return claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture;
    }

    /**
     * If not initialised, initialises {@link #claimIDToTenancyTypeLookup} then returns
     * it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDToTenancyTypeLookup}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Map<SHBE_ClaimID, Integer> getClaimIDToTenancyTypeLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToTenancyTypeLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDToTenancyTypeLookup(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Map<SHBE_ClaimID, Integer> getClaimIDToTenancyTypeLookup()
            throws IOException, ClassNotFoundException {
        if (claimIDToTenancyTypeLookup == null) {
            Path f;
            f = getClaimIDToTenancyTypeLookupFile();
            if (Files.exists(f)) {
                claimIDToTenancyTypeLookup = (Map<SHBE_ClaimID, Integer>) Generic_IO.readObject(f);
            } else {
                claimIDToTenancyTypeLookup = new HashMap<>();
            }
        }
        return claimIDToTenancyTypeLookup;
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
     * If not initialised, initialises {@link #recordIDsNotLoaded} then returns it.
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
     * If not initialised, initialises {@link #claimIDsOfInvalidClaimantNINOClaims} then
     * returns it.
     *
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @return {@link #claimIDsOfInvalidClaimantNINOClaims}
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfInvalidClaimantNINOClaims(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfInvalidClaimantNINOClaims();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfInvalidClaimantNINOClaims(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getClaimIDsOfInvalidClaimantNINOClaims()
            throws IOException, ClassNotFoundException {
        if (claimIDsOfInvalidClaimantNINOClaims == null) {
            Path f;
            f = getClaimIDsOfInvalidClaimantNINOClaimsFile();
            if (Files.exists(f)) {
                claimIDsOfInvalidClaimantNINOClaims = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                claimIDsOfInvalidClaimantNINOClaims = new HashSet<>();
            }
        }
        return claimIDsOfInvalidClaimantNINOClaims;
    }

    /**
     * If not initialised, initialises {@link #claimantPostcodesUnmappable} then returns
     * it.
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
     * If not initialised, initialises {@link #claimantPostcodesModified} then returns
     * it.
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
     * If not initialised, initialises {@link #claimantPostcodesModified} then returns
     * it.
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
     * {@link #claimantPostcodesCheckedAsMappableButNotInONSPDPostcodes} then returns it.
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
     * @return {@link #recordsFile} initialising if it is not already initialised.
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
     * @return {@link #claimIDsOfNewSHBEClaimsFile} initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsFile() throws IOException {
        if (claimIDsOfNewSHBEClaimsFile == null) {
            claimIDsOfNewSHBEClaimsFile = getFile("ClaimIDsOfNewSHBEClaims"
                    + SHBE_Strings.symbol_underscore + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfNewSHBEClaimsFile;
    }

    /**
     * @return {@link #claimIDsOfNewSHBEClaimsFile} initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile() throws IOException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile == null) {
            claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;
    }

    /**
     * @return {@link #claimIDsOfNewSHBEClaimsFile} initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile()
            throws IOException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile == null) {
            claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;
    }

    /**
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile}
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile() throws IOException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile == null) {
            claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;
    }

    /**
     * @return {@link #claimIDsOfNewSHBEClaimsWhereClaimantIsNewFile} initialising if it
     * is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile() throws IOException {
        if (claimIDsOfNewSHBEClaimsWhereClaimantIsNewFile == null) {
            claimIDsOfNewSHBEClaimsWhereClaimantIsNewFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfNewSHBEClaimsWhereClaimantIsNewFile;
    }

    public final Path getClaimantPersonIDsFile() throws IOException {
        if (claimantPersonIDsFile == null) {
            claimantPersonIDsFile = getFile(
                    "Claimant"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimantPersonIDsFile;
    }

    public final Path getPartnerPersonIDsFile() throws IOException {
        if (partnerPersonIDsFile == null) {
            partnerPersonIDsFile = getFile(
                    "Partner"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return partnerPersonIDsFile;
    }

    public final Path getNonDependentPersonIDsFile() throws IOException {
        if (nonDependentPersonIDsFile == null) {
            nonDependentPersonIDsFile = getFile(
                    "NonDependent"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return nonDependentPersonIDsFile;
    }

    public final Set<SHBE_PersonID> getClaimantPersonIDs(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPersonIDs();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimantPersonIDs(hoome);
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
    public Set<SHBE_PersonID> getClaimantPersonIDs()
            throws IOException, ClassNotFoundException {
        claimantPersonIDsFile = getClaimantPersonIDsFile();
        return getClaimantPersonIDs(claimantPersonIDsFile);
    }

    /**
     * @param f Path
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_PersonID> getClaimantPersonIDs(
            Path f) throws IOException, ClassNotFoundException {
        if (claimantPersonIDs == null) {
            claimantPersonIDs = env.collections.getPersonIDs(f);
        }
        return claimantPersonIDs;
    }

    public final Set<SHBE_PersonID> getPartnerPersonIDs(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getPartnerPersonIDs();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getPartnerPersonIDs(hoome);
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
    public Set<SHBE_PersonID> getPartnerPersonIDs() throws IOException,
            ClassNotFoundException {
        partnerPersonIDsFile = getPartnerPersonIDsFile();
        return getPartnerPersonIDs(partnerPersonIDsFile);
    }

    /**
     * @param f Path
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_PersonID> getPartnerPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (partnerPersonIDs == null) {
            partnerPersonIDs = env.collections.getPersonIDs(f);
        }
        return partnerPersonIDs;
    }

    public final Set<SHBE_PersonID> getNonDependentPersonIDs(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getNonDependentPersonIDs();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(ym3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getNonDependentPersonIDs(hoome);
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
        if (nonDependentPersonIDs == null) {
            nonDependentPersonIDs = env.collections.getPersonIDs(f);
        }
        return nonDependentPersonIDs;
    }

    /**
     * @return Set
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public Set<SHBE_PersonID> getNonDependentPersonIDs()
            throws IOException, ClassNotFoundException {
        nonDependentPersonIDsFile = getNonDependentPersonIDsFile();
        return getNonDependentPersonIDs(nonDependentPersonIDsFile);
    }

    /**
     * @return ottingleySpringsCaravanParkPairedClaimIDsFile initialising if it
     * is not already initialised.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected final Path getCottingleySpringsCaravanParkPairedClaimIDsFile()
            throws IOException, ClassNotFoundException {
        if (cottingleySpringsCaravanParkPairedClaimIDsFile == null) {
            cottingleySpringsCaravanParkPairedClaimIDsFile = getFile(
                    SHBE_Strings.s_CottingleySpringsCaravanPark + "PairedClaimIDs"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return cottingleySpringsCaravanParkPairedClaimIDsFile;
    }

    /**
     * @return claimIDsWithStatusOfHBAtExtractDateInPaymentFile initialising if
     * it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile() throws IOException {
        if (claimIDsWithStatusOfHBAtExtractDateInPaymentFile == null) {
            claimIDsWithStatusOfHBAtExtractDateInPaymentFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeIn
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsWithStatusOfHBAtExtractDateInPaymentFile;
    }

    /**
     * @return claimIDsWithStatusOfHBAtExtractDateSuspendedFile initialising if
     * it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile() throws IOException {
        if (claimIDsWithStatusOfHBAtExtractDateSuspendedFile == null) {
            claimIDsWithStatusOfHBAtExtractDateSuspendedFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeSuspended
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsWithStatusOfHBAtExtractDateSuspendedFile;
    }

    /**
     * @return claimIDsWithStatusOfHBAtExtractDateOtherFile initialising if it
     * is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsWithStatusOfHBAtExtractDateOtherFile()
            throws IOException {
        if (claimIDsWithStatusOfHBAtExtractDateOtherFile == null) {
            claimIDsWithStatusOfHBAtExtractDateOtherFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeOther
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsWithStatusOfHBAtExtractDateOtherFile;
    }

    /**
     * @return claimIDsWithStatusOfCTBAtExtractDateInPaymentFile initialising if
     * it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile()
            throws IOException {
        if (claimIDsWithStatusOfCTBAtExtractDateInPaymentFile == null) {
            claimIDsWithStatusOfCTBAtExtractDateInPaymentFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeIn
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsWithStatusOfCTBAtExtractDateInPaymentFile;
    }

    /**
     * @return claimIDsWithStatusOfCTBAtExtractDateSuspendedFile initialising if
     * it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile()
            throws IOException {
        if (claimIDsWithStatusOfCTBAtExtractDateSuspendedFile == null) {
            claimIDsWithStatusOfCTBAtExtractDateSuspendedFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeSuspended
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsWithStatusOfCTBAtExtractDateSuspendedFile;
    }

    /**
     * @return claimIDsWithStatusOfCTBAtExtractDateOtherFile initialising if it
     * is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsWithStatusOfCTBAtExtractDateOtherFile()
            throws IOException {
        if (claimIDsWithStatusOfCTBAtExtractDateOtherFile == null) {
            claimIDsWithStatusOfCTBAtExtractDateOtherFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeOther
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsWithStatusOfCTBAtExtractDateOtherFile;
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
     * @return claimIDAndCountOfRecordsWithSRecordsFile initialising if it is
     * not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDAndCountOfRecordsWithSRecordsFile()
            throws IOException {
        if (claimIDAndCountOfRecordsWithSRecordsFile == null) {
            claimIDAndCountOfRecordsWithSRecordsFile = getFile(
                    "ClaimIDAndCountOfRecordsWithSRecordsFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDAndCountOfRecordsWithSRecordsFile;
    }

    /**
     * @return claimIDsOfClaimsWithoutAMappableClaimantPostcodeFile initialising
     * if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile()
            throws IOException {
        if (claimIDsOfClaimsWithoutAMappableClaimantPostcodeFile == null) {
            claimIDsOfClaimsWithoutAMappableClaimantPostcodeFile = getFile(
                    "ClaimIDsOfClaimsWithoutAMappableClaimantPostcode"
                    + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfClaimsWithoutAMappableClaimantPostcodeFile;
    }

    /**
     * @return claimIDToClaimantPersonIDLookupFile initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDToClaimantPersonIDLookupFile() throws IOException {
        if (claimIDToClaimantPersonIDLookupFile == null) {
            claimIDToClaimantPersonIDLookupFile = getFile(
                    "ClaimIDToClaimantPersonIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDToClaimantPersonIDLookupFile;
    }

    /**
     * @return claimIDToPartnerPersonIDLookupFile initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDToPartnerPersonIDLookupFile()
            throws IOException {
        if (claimIDToPartnerPersonIDLookupFile == null) {
            claimIDToPartnerPersonIDLookupFile = getFile(
                    "ClaimIDToPartnerPersonIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDToPartnerPersonIDLookupFile;
    }

    /**
     * @return claimIDToDependentPersonIDsLookupFile initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDToDependentPersonIDsLookupFile()
            throws IOException {
        if (claimIDToDependentPersonIDsLookupFile == null) {
            claimIDToDependentPersonIDsLookupFile = getFile(
                    "ClaimIDToDependentPersonIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Set<SHBE_PersonID>"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDToDependentPersonIDsLookupFile;
    }

    /**
     * @return claimIDToNonDependentPersonIDsLookupFile initialising if it is
     * not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDToNonDependentPersonIDsLookupFile() throws IOException {
        if (claimIDToNonDependentPersonIDsLookupFile == null) {
            claimIDToNonDependentPersonIDsLookupFile = getFile(
                    "ClaimIDToNonDependentPersonIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDToNonDependentPersonIDsLookupFile;
    }

    /**
     * @return claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile()
            throws IOException {
        if (claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile == null) {
            claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile()
            throws IOException {
        if (claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile == null) {
            claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile()
            throws IOException {
        if (claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile == null) {
            claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile()
            throws IOException {
        if (claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile == null) {
            claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return
     * ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile()
            throws IOException {
        if (claimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile == null) {
            claimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;
    }

    /**
     * @return claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile()
            throws IOException {
        if (claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile()
            throws IOException {
        if (partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return partnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile()
            throws IOException {
        if (nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return nonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
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
     * @return claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile()
            throws IOException {
        if (claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile == null) {
            claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile = getFile(
                    "ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;
    }

    /**
     * @return claimIDToTenancyTypeLookupFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDToTenancyTypeLookupFile() throws IOException {
        if (claimIDToTenancyTypeLookupFile == null) {
            claimIDToTenancyTypeLookupFile = getFile(
                    "ClaimIDToTenancyTypeLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDToTenancyTypeLookupFile;
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
     * @return claimIDsOfInvalidClaimantNINOClaimsFile initialising if it is not
     * already initialised.
     * @throws java.io.IOException If encountered.
     */
    public final Path getClaimIDsOfInvalidClaimantNINOClaimsFile()
            throws IOException {
        if (claimIDsOfInvalidClaimantNINOClaimsFile == null) {
            claimIDsOfInvalidClaimantNINOClaimsFile = getFile(
                    "ClaimIDsOfInvalidClaimantNINOClaimsFile" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return claimIDsOfInvalidClaimantNINOClaimsFile;
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
