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
    private transient final SHBE_Handler Handler;
    private transient final UKP_Data Postcode_Handler;

    /**
     * Keys are ClaimIDs, values are SHBE_Record.
     */
    private Map<SHBE_ClaimID, SHBE_Record> Records;

    /**
     * SHBE_PersonID of Claimants
     */
    Set<SHBE_PersonID> ClaimantPersonIDs;

    /**
     * SHBE_PersonID of Partners
     */
    Set<SHBE_PersonID> PartnerPersonIDs;

    /**
     * SHBE_PersonID of Non-Dependents
     */
    Set<SHBE_PersonID> NonDependentPersonIDs;

    /**
     * A store for ClaimIDs for Cottingley Springs Caravan Park where there are
     * two claims for a claimant, one for a pitch and the other for the rent of
     * a caravan.
     */
    private Set<SHBE_ClaimID> CottingleySpringsCaravanParkPairedClaimIDs;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private Set<SHBE_ClaimID> ClaimIDsWithStatusOfHBAtExtractDateInPayment;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 2 (Suspended).
     */
    private Set<SHBE_ClaimID> ClaimIDsWithStatusOfHBAtExtractDateSuspended;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 0 (Suspended).
     */
    private Set<SHBE_ClaimID> ClaimIDsWithStatusOfHBAtExtractDateOther;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private Set<SHBE_ClaimID> ClaimIDsWithStatusOfCTBAtExtractDateInPayment;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 2
     * (Suspended).
     */
    private Set<SHBE_ClaimID> ClaimIDsWithStatusOfCTBAtExtractDateSuspended;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 0
     * (Suspended).
     */
    private Set<SHBE_ClaimID> ClaimIDsWithStatusOfCTBAtExtractDateOther;

    /**
     * SRecordsWithoutDRecords indexed by ClaimRef SHBE_ID. Once the SHBE data
     * is loaded from source, this only contains those SRecordsWithoutDRecords
     * that are not linked to a DRecord.
     */
    private Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>> SRecordsWithoutDRecords;

    /**
     * For storing the ClaimIDs of Records that have SRecords along with the
     * count of those SRecordsWithoutDRecords.
     */
    private Map<SHBE_ClaimID, Integer> ClaimIDAndCountOfRecordsWithSRecords;

    /**
     * For storing the Year_Month of this. This is an identifier for these data.
     */
    private UKP_YM3 YM3;

    /**
     * For storing the NearestYM3ForONSPDLookup of this. This is derived from
     * YM3.
     */
    private UKP_YM3 NearestYM3ForONSPDLookup;

    /**
     * Holds a reference to the original input data file from which this was
     * created.
     */
    private Path InputFile;

    /**
     * Directory where this is stored.
     */
    private Path Dir;

    /**
     * Path for storing this.
     */
    private Path File;

    /**
     * Path for storing Data.
     */
    private Path RecordsFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims.
     */
    private Path ClaimIDsOfNewSHBEClaimsFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant was a
     * Claimant before.
     */
    private Path ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant was a Partner
     * before.
     */
    private Path ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant was a
     * NonDependent before.
     */
    private Path ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;

    /**
     * Path for storing ClaimIDs of new SHBE claims where Claimant is new.
     */
    private Path ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile;

    /**
     * ClaimantPersonIDs File.
     */
    private Path ClaimantPersonIDsFile;

    /**
     * PartnerPersonIDs File.
     */
    private Path PartnerPersonIDsFile;

    /**
     * NonDependentPersonIDs File.
     */
    private Path NonDependentPersonIDsFile;

    /**
     * Path for storing Cottingley Springs Caravan Park paired ClaimIDs.
     */
    private Path CottingleySpringsCaravanParkPairedClaimIDsFile;

    /**
     * Path for storing ClaimIDs with status of HB at extract date InPayment.
     */
    private Path ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile;

    /**
     * Path for storing ClaimIDs with status of HB at extract date Suspended.
     */
    private Path ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile;

    /**
     * Path for storing ClaimIDs with status of HB at extract date Other.
     */
    private Path ClaimIDsWithStatusOfHBAtExtractDateOtherFile;

    /**
     * Path for storing ClaimIDs with status of CTB at extract date InPayment.
     */
    private Path ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile;

    /**
     * Path for storing ClaimIDs with status of CTB at extract date Suspended.
     */
    private Path ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile;

    /**
     * Path for storing ClaimIDs with status of CTB at extract date Other.
     */
    private Path ClaimIDsWithStatusOfCTBAtExtractDateOtherFile;

    /**
     * Path for storing SRecordsWithoutDRecords.
     */
    private Path SRecordsWithoutDRecordsFile;

    /**
     * Path for storing ClaimIDs and count of records with SRecords.
     */
    private Path ClaimIDAndCountOfRecordsWithSRecordsFile;

    /**
     * For storing the ClaimID of Records without a mappable Claimant Postcode.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfClaimsWithoutAMappableClaimantPostcode;

    /**
     * Path for storing ClaimIDs of claims without a mappable claimant postcode.
     */
    private Path ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile;

    /**
     * ClaimIDs mapped to PersonIDs of Claimants.
     */
    private Map<SHBE_ClaimID, SHBE_PersonID> ClaimIDToClaimantPersonIDLookup;

    /**
     * ClaimIDs mapped to PersonIDs of Partners. If there is no main Partner for
     * the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, SHBE_PersonID> ClaimIDToPartnerPersonIDLookup;

    /**
     * ClaimIDs mapped to {@code Set<SHBE_PersonID>} of Dependents. If there are
     * no Dependents for the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, Set<SHBE_PersonID>> ClaimIDToDependentPersonIDsLookup;

    /**
     * ClaimIDs mapped to {@code Set<SHBE_PersonID>} of NonDependents. If there
     * are no NonDependents for the claim then there is no mapping.
     */
    private Map<SHBE_ClaimID, Set<SHBE_PersonID>> ClaimIDToNonDependentPersonIDsLookup;

    /**
     * ClaimIDs of Claims with Claimants that are Claimants in another claim.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Claimants that are Partners in another claim.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Claimants in another claim.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Partners in multiple claims.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with NonDependents that are Claimants or Partners in
     * another claim.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim;

    /**
     * SHBE_PersonIDs of Claimants that are in multiple claims in a month mapped
     * to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * SHBE_PersonIDs of Partners that are in multiple claims in a month mapped
     * to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * SHBE_PersonIDs of NonDependents that are in multiple claims in a month
     * mapped to a set of ClaimIDs of those claims.
     */
    private Map<SHBE_PersonID, Set<SHBE_ClaimID>> NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * ClaimIDs mapped to Postcode SHBE_IDs.
     */
    private Map<SHBE_ClaimID, UKP_RecordID> ClaimIDToPostcodeIDLookup;

    /**
     * ClaimIDs of the claims that have had PostcodeF updated from the future.
     * This is only to be stored if the postcode was previously of an invalid
     * format.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture;

    /**
     * ClaimIDs. This is only used when reading the data to check that ClaimIDs
     * are unique.
     */
    private Set<SHBE_ClaimID> ClaimIDs;

    /**
     * For storing ClaimIDs of new SHBE claims.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfNewSHBEClaims;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Claimant
     * before.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Partner
     * before.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a NonDependent
     * before.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant is new.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew;

    /**
     * ClaimIDs mapped to TenancyType.
     */
    private Map<SHBE_ClaimID, Integer> ClaimIDToTenancyTypeLookup;

    /**
     * LoadSummary
     */
    private Map<String, Number> LoadSummary;

    /**
     * The line numbers of records that for some reason could not be loaded.
     */
    private ArrayList<Long> RecordIDsNotLoaded;

    /**
     * For storing ClaimIDs of all Claims where Claimant National Insurance
     * Number is invalid.
     */
    private Set<SHBE_ClaimID> ClaimIDsOfInvalidClaimantNINOClaims;

    /**
     * // * For storing ClaimID mapped to Claim Postcodes that are not
     * (currently) mappable.
     */
    private Map<SHBE_ClaimID, String> ClaimantPostcodesUnmappable;

    /**
     * For storing ClaimID mapped to Claim Postcodes that have been
     * automatically modified to make them mappable.
     */
    private Map<SHBE_ClaimID, String[]> ClaimantPostcodesModified;

    /**
     * For storing ClaimID mapped to Claimant Postcodes Checked by local
     * authority to be mappable, but not found in the subsequent or the latest
     * ONSPD.
     */
    private Map<SHBE_ClaimID, String> ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes;

    /**
     * ClaimIDToClaimantPersonIDLookupFile File.
     */
    private Path ClaimIDToClaimantPersonIDLookupFile;

    /**
     * ClaimIDToPartnerPersonIDLookup File.
     */
    private Path ClaimIDToPartnerPersonIDLookupFile;

    /**
     * ClaimIDToDependentPersonIDsLookupFile File.
     */
    private Path ClaimIDToDependentPersonIDsLookupFile;

    /**
     * ClaimIDToNonDependentPersonIDsLookupFile File.
     */
    private Path ClaimIDToNonDependentPersonIDsLookupFile;

    /**
     * ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile File.
     */
    private Path ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;

    /**
     * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile File.
     */
    private Path ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;

    /**
     * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile File.
     */
    private Path ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;

    /**
     * ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile File.
     */
    private Path ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;

    /**
     * ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile File.
     */
    private Path ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;

    /**
     * ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile File.
     */
    private Path ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile File.
     */
    private Path PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile File.
     */
    private Path NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * ClaimIDToPostcodeIDLookupFile File.
     */
    private Path ClaimIDToPostcodeIDLookupFile;

    /**
     * ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile File.
     */
    private Path ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;

    /**
     * ClaimIDToTenancyTypeLookupFile File.
     */
    private Path ClaimIDToTenancyTypeLookupFile;

    /**
     * LoadSummary File.
     */
    private Path LoadSummaryFile;

    /**
     * RecordIDsNotLoaded File.
     */
    private Path RecordIDsNotLoadedFile;

    /**
     * ClaimIDsOfInvalidClaimantNINOClaimsFile File.
     */
    private Path ClaimIDsOfInvalidClaimantNINOClaimsFile;

    /**
     * ClaimantPostcodesUnmappableFile File.
     */
    private Path ClaimantPostcodesUnmappableFile;

    /**
     * ClaimantPostcodesModifiedFile File.
     */
    private Path ClaimantPostcodesModifiedFile;

    /**
     * ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile File.
     */
    private Path ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile;

    /**
     * If not initialised, initialises {@link #Records} then returns it.
     *
     * @return {@link #Records} initialised first if it is {@code null}.
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
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises {link #Records} then returns it.
     *
     * @return {@link #Records} initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected final Map<SHBE_ClaimID, SHBE_Record> getRecords()
            throws IOException, ClassNotFoundException {
        if (Records == null) {
            Path f = getRecordsFile();
            if (Files.exists(f)) {
                Records = (Map<SHBE_ClaimID, SHBE_Record>) Generic_IO.readObject(f);
            } else {
                Records = new HashMap<>();
            }
        }
        return Records;
    }

    /**
     * If not initialised, initialises {@link #ClaimIDsOfNewSHBEClaims} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaims} initialised first if it is
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
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises {@link #ClaimIDsOfNewSHBEClaims} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaims} initialised first if it is
     * {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaims()
            throws IOException, ClassNotFoundException {
        if (ClaimIDsOfNewSHBEClaims == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsFile();
            if (Files.exists(f)) {
                ClaimIDsOfNewSHBEClaims = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaims = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaims;
    }

    /**
     * If not initialised, initialises
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore}
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
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore()
            throws IOException, ClassNotFoundException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile();
            if (Files.exists(f)) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;
    }

    /**
     * If not initialised, initialises
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
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
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore()
            throws IOException, ClassNotFoundException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile();
            if (Files.exists(f)) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;
    }

    /**
     * If not initialised, initialises
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore} then
     * returns it.
     *
     * @return
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore}
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
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore} then
     * returns it.
     *
     * @return
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore}
     * initialised first if it is {@code null}.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore()
            throws IOException, ClassNotFoundException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile();
            if (Files.exists(f)) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;
    }

    /**
     * If not initialised, initialises ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew
     * If not initialised, initialises
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
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
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew
     * then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew() throws IOException, ClassNotFoundException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew == null) {
            Path f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile();
            if (Files.exists(f)) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew;
    }

    /**
     * If not initialised, initialises
     * CottingleySpringsCaravanParkPairedClaimIDs then returns it.
     *
     * If not initialised, initialises
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getCottingleySpringsCaravanParkPairedClaimIDs(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getCottingleySpringsCaravanParkPairedClaimIDs();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * CottingleySpringsCaravanParkPairedClaimIDs then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getCottingleySpringsCaravanParkPairedClaimIDs() throws IOException, ClassNotFoundException {
        if (CottingleySpringsCaravanParkPairedClaimIDs == null) {
            Path f;
            f = getCottingleySpringsCaravanParkPairedClaimIDsFile();
            if (Files.exists(f)) {
                CottingleySpringsCaravanParkPairedClaimIDs = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                CottingleySpringsCaravanParkPairedClaimIDs = new HashSet<>();
            }
        }
        return CottingleySpringsCaravanParkPairedClaimIDs;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsWithStatusOfHBAtExtractDateInPayment then returns it.
     *
     * If not initialised, initialises
     * {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore} then
     * returns it.
     *
     * @return {@link #ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore}
     * initialised first if it is {@code null}.
     * @param hoome If {@code true} then an attempt is made to handle
     * OutOfMemeoryErrors if they are encountered.
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateInPayment(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateInPayment();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimIDsWithStatusOfHBAtExtractDateInPayment then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateInPayment() throws IOException, ClassNotFoundException {
        if (ClaimIDsWithStatusOfHBAtExtractDateInPayment == null) {
            Path f;
            f = getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile();
            if (Files.exists(f)) {
                ClaimIDsWithStatusOfHBAtExtractDateInPayment = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsWithStatusOfHBAtExtractDateInPayment = new HashSet<>();
            }
        }
        return ClaimIDsWithStatusOfHBAtExtractDateInPayment;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsWithStatusOfHBAtExtractDateSuspended then returns it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateSuspended(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateSuspended();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimIDsWithStatusOfHBAtExtractDateSuspended then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateSuspended() throws IOException, ClassNotFoundException {
        if (ClaimIDsWithStatusOfHBAtExtractDateSuspended == null) {
            Path f;
            f = getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile();
            if (Files.exists(f)) {
                ClaimIDsWithStatusOfHBAtExtractDateSuspended = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsWithStatusOfHBAtExtractDateSuspended = new HashSet<>();
            }
        }
        return ClaimIDsWithStatusOfHBAtExtractDateSuspended;
    }

    /**
     * If not initialised, initialises ClaimIDsWithStatusOfHBAtExtractDateOther
     * then returns it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateOther(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateOther();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimIDsWithStatusOfHBAtExtractDateOther
     * then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfHBAtExtractDateOther() throws IOException, ClassNotFoundException {
        if (ClaimIDsWithStatusOfHBAtExtractDateOther == null) {
            Path f;
            f = getClaimIDsWithStatusOfHBAtExtractDateOtherFile();
            if (Files.exists(f)) {
                ClaimIDsWithStatusOfHBAtExtractDateOther = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsWithStatusOfHBAtExtractDateOther = new HashSet<>();
            }
        }
        return ClaimIDsWithStatusOfHBAtExtractDateOther;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsWithStatusOfCTBAtExtractDateInPayment then returns it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateInPayment(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateInPayment();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimIDsWithStatusOfCTBAtExtractDateInPayment then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateInPayment() throws IOException, ClassNotFoundException {
        if (ClaimIDsWithStatusOfCTBAtExtractDateInPayment == null) {
            Path f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile();
            if (Files.exists(f)) {
                ClaimIDsWithStatusOfCTBAtExtractDateInPayment = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsWithStatusOfCTBAtExtractDateInPayment = new HashSet<>();
            }
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateInPayment;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsWithStatusOfCTBAtExtractDateSuspended then returns it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateSuspended(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateSuspended();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimIDsWithStatusOfCTBAtExtractDateSuspended then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateSuspended() throws IOException, ClassNotFoundException {
        if (ClaimIDsWithStatusOfCTBAtExtractDateSuspended == null) {
            Path f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile();
            if (Files.exists(f)) {
                ClaimIDsWithStatusOfCTBAtExtractDateSuspended = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsWithStatusOfCTBAtExtractDateSuspended = new HashSet<>();
            }
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateSuspended;
    }

    /**
     * If not initialised, initialises ClaimIDsWithStatusOfCTBAtExtractDateOther
     * then returns it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateOther(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateOther();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimIDsWithStatusOfCTBAtExtractDateOther
     * then returns it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsWithStatusOfCTBAtExtractDateOther() throws IOException, ClassNotFoundException {
        if (ClaimIDsWithStatusOfCTBAtExtractDateOther == null) {
            Path f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateOtherFile();
            if (Files.exists(f)) {
                ClaimIDsWithStatusOfCTBAtExtractDateOther = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsWithStatusOfCTBAtExtractDateOther = new HashSet<>();
            }
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateOther;
    }

    /**
     * If not initialised, initialises SRecordsWithoutDRecords then returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>> getSRecordsWithoutDRecords(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getSRecordsWithoutDRecords();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * @return the SRecordsWithoutDRecords
     */
    protected Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>> getSRecordsWithoutDRecords() throws IOException, ClassNotFoundException {
        if (SRecordsWithoutDRecords == null) {
            Path f;
            f = getSRecordsWithoutDRecordsFile();
            if (Files.exists(f)) {
                SRecordsWithoutDRecords = (Map<SHBE_ClaimID, ArrayList<SHBE_S_Record>>) Generic_IO.readObject(f);
            } else {
                SRecordsWithoutDRecords = new HashMap<>();
            }
        }
        return SRecordsWithoutDRecords;
    }

    /**
     * If not initialised, initialises ClaimIDAndCountOfRecordsWithSRecords then
     * returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, Integer> getClaimIDAndCountOfRecordsWithSRecords(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDAndCountOfRecordsWithSRecords();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimIDsOfClaimsWithoutAMappableClaimantPostcode then returns it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithoutAValidClaimantPostcode(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithoutAMappableClaimantPostcode();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * @return the ClaimIDAndCountOfRecordsWithSRecords
     */
    protected Map<SHBE_ClaimID, Integer> getClaimIDAndCountOfRecordsWithSRecords() throws IOException, ClassNotFoundException {
        if (ClaimIDAndCountOfRecordsWithSRecords == null) {
            Path f;
            f = getClaimIDAndCountOfRecordsWithSRecordsFile();
            if (Files.exists(f)) {
                ClaimIDAndCountOfRecordsWithSRecords = (Map<SHBE_ClaimID, Integer>) Generic_IO.readObject(f);
            } else {
                ClaimIDAndCountOfRecordsWithSRecords = new HashMap<>();
            }
        }
        return ClaimIDAndCountOfRecordsWithSRecords;
    }

    /**
     * @return the ClaimIDsOfClaimsWithoutAMappableClaimantPostcode
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithoutAMappableClaimantPostcode() throws IOException, ClassNotFoundException {
        if (ClaimIDsOfClaimsWithoutAMappableClaimantPostcode == null) {
            Path f;
            f = getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile();
            if (Files.exists(f)) {
                ClaimIDsOfClaimsWithoutAMappableClaimantPostcode = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithoutAMappableClaimantPostcode = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithoutAMappableClaimantPostcode;
    }

    /**
     * @return YM3
     */
    public UKP_YM3 getYM3() {
        return YM3;
    }

    /**
     * @return NearestYM3ForONSPDLookup
     */
    public UKP_YM3 getNearestYM3ForONSPDLookup() {
        return NearestYM3ForONSPDLookup;
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
     * If Dir is null, it is initialised.
     *
     * @return Dir.
     */
    protected Path getDir() throws IOException {
        if (Dir == null) {
            Dir = Paths.get(env.files.getGeneratedSHBEDir().toString(),
                    getYM3().toString());
            Files.createDirectories(Dir);
        }
        return Dir;
    }

    /**
     * @param filename
     * @return The Path in Dir given by filename.
     */
    public Path getFile(String filename) throws IOException {
        return Paths.get(getDir().toString(), filename);
    }

    /**
     * For loading an existing collection.
     *
     * @param env
     * @param YM3
     */
    public SHBE_Records(SHBE_Environment env, UKP_YM3 YM3) throws IOException,
            ClassNotFoundException {
        this(env, 0, YM3);
    }

    /**
     * For loading an existing collection.
     *
     * @param env
     * @param logID The ID of the log to write to.
     * @param YM3
     */
    public SHBE_Records(SHBE_Environment env, int logID, UKP_YM3 YM3)
            throws IOException, ClassNotFoundException {
        super(env, logID);
        this.YM3 = YM3;
        Handler = this.env.handler;
        Postcode_Handler = this.env.oe.getHandler();
        NearestYM3ForONSPDLookup = Postcode_Handler.getNearestYM3ForONSPDLookup(YM3);
        env.env.log("YM3 " + YM3, logID);
        env.env.log("NearestYM3ForONSPDLookup " + NearestYM3ForONSPDLookup, logID);
        Records = getRecords();
        ClaimIDsOfNewSHBEClaims = getClaimIDsOfNewSHBEClaims(env.HOOME);
        ClaimantPersonIDs = getClaimantPersonIDs(env.HOOME);
        PartnerPersonIDs = getPartnerPersonIDs(env.HOOME);
        NonDependentPersonIDs = getNonDependentPersonIDs(env.HOOME);
        CottingleySpringsCaravanParkPairedClaimIDs = getCottingleySpringsCaravanParkPairedClaimIDs(env.HOOME);
        ClaimIDsWithStatusOfHBAtExtractDateInPayment = getClaimIDsWithStatusOfHBAtExtractDateInPayment(env.HOOME);
        ClaimIDsWithStatusOfHBAtExtractDateSuspended = getClaimIDsWithStatusOfHBAtExtractDateSuspended(env.HOOME);
        ClaimIDsWithStatusOfHBAtExtractDateOther = getClaimIDsWithStatusOfHBAtExtractDateOther(env.HOOME);
        ClaimIDsWithStatusOfCTBAtExtractDateInPayment = getClaimIDsWithStatusOfCTBAtExtractDateInPayment(env.HOOME);
        ClaimIDsWithStatusOfCTBAtExtractDateSuspended = getClaimIDsWithStatusOfCTBAtExtractDateSuspended(env.HOOME);
        ClaimIDsWithStatusOfCTBAtExtractDateOther = getClaimIDsWithStatusOfCTBAtExtractDateOther(env.HOOME);
        SRecordsWithoutDRecords = getSRecordsWithoutDRecords(env.HOOME);
        ClaimIDAndCountOfRecordsWithSRecords = getClaimIDAndCountOfRecordsWithSRecords(env.HOOME);
        ClaimIDsOfClaimsWithoutAMappableClaimantPostcode = getClaimIDsOfClaimsWithoutAValidClaimantPostcode(env.HOOME);
        ClaimIDToClaimantPersonIDLookup = getClaimIDToClaimantPersonIDLookup(env.HOOME);
        ClaimIDToPartnerPersonIDLookup = getClaimIDToPartnerPersonIDLookup(env.HOOME);
        ClaimIDToDependentPersonIDsLookup = getClaimIDToDependentPersonIDsLookup(env.HOOME);
        ClaimIDToNonDependentPersonIDsLookup = getClaimIDToNonDependentPersonIDsLookup(env.HOOME);
        ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(env.HOOME);
        ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(env.HOOME);
        ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(env.HOOME);
        ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim(env.HOOME);
        ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = getClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(env.HOOME);
        ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(env.HOOME);
        PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(env.HOOME);
        NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(env.HOOME);
        ClaimIDToPostcodeIDLookup = getClaimIDToPostcodeIDLookup(env.HOOME);
        ClaimIDToTenancyTypeLookup = getClaimIDToTenancyTypeLookup(env.HOOME);
        LoadSummary = getLoadSummary(env.HOOME);
        RecordIDsNotLoaded = getRecordIDsNotLoaded(env.HOOME);
        ClaimIDsOfInvalidClaimantNINOClaims = getClaimIDsOfInvalidClaimantNINOClaims(env.HOOME);
        ClaimantPostcodesUnmappable = getClaimantPostcodesUnmappable(env.HOOME);
        ClaimantPostcodesModified = getClaimantPostcodesModified(env.HOOME);
        ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(env.HOOME);
        ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(env.HOOME);
    }

    /**
     * Loads Data from source.
     *
     * @param env
     * @param logID
     * @param inputFilename
     * @param inputDirectory
     * @param LatestYM3ForONSPDFormat
     * @throws java.io.IOException
     */
    public SHBE_Records(SHBE_Environment env, int logID, Path inputDirectory,
            String inputFilename, UKP_YM3 LatestYM3ForONSPDFormat) throws IOException, ClassNotFoundException, Exception {
        super(env, logID);
        Handler = env.handler;
        InputFile = Paths.get(inputDirectory.toString(), inputFilename);
        YM3 = Handler.getYM3(inputFilename);
        Postcode_Handler = this.env.oe.getHandler();
        NearestYM3ForONSPDLookup = Postcode_Handler.getNearestYM3ForONSPDLookup(YM3);
        Records = new HashMap<>();
        ClaimIDs = new HashSet<>();
        ClaimIDsOfNewSHBEClaims = new HashSet<>();
        ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = new HashSet<>();
        ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = new HashSet<>();
        ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = new HashSet<>();
        ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew = new HashSet<>();
        ClaimantPersonIDs = new HashSet<>();
        PartnerPersonIDs = new HashSet<>();
        NonDependentPersonIDs = new HashSet<>();
        CottingleySpringsCaravanParkPairedClaimIDs = new HashSet<>();
        ClaimIDsWithStatusOfHBAtExtractDateInPayment = new HashSet<>();
        ClaimIDsWithStatusOfHBAtExtractDateSuspended = new HashSet<>();
        ClaimIDsWithStatusOfHBAtExtractDateOther = new HashSet<>();
        ClaimIDsWithStatusOfCTBAtExtractDateInPayment = new HashSet<>();
        ClaimIDsWithStatusOfCTBAtExtractDateSuspended = new HashSet<>();
        ClaimIDsWithStatusOfCTBAtExtractDateOther = new HashSet<>();
        SRecordsWithoutDRecords = new HashMap<>();
        ClaimIDAndCountOfRecordsWithSRecords = new HashMap<>();
        ClaimIDsOfClaimsWithoutAMappableClaimantPostcode = new HashSet<>();
        ClaimIDToClaimantPersonIDLookup = new HashMap<>();
        ClaimIDToPartnerPersonIDLookup = new HashMap<>();
        ClaimIDToDependentPersonIDsLookup = new HashMap<>();
        ClaimIDToNonDependentPersonIDsLookup = new HashMap<>();
        ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = new HashSet<>();
        ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = new HashSet<>();
        ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = new HashSet<>();
        ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = new HashSet<>();
        ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = new HashSet<>();
        ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
        PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
        NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
        ClaimIDToPostcodeIDLookup = new HashMap<>();
        ClaimIDToTenancyTypeLookup = new HashMap<>();
        LoadSummary = new HashMap<>();
        RecordIDsNotLoaded = new ArrayList<>();
        ClaimIDsOfInvalidClaimantNINOClaims = new HashSet<>();
        ClaimantPostcodesUnmappable = new HashMap<>();
        ClaimantPostcodesModified = new HashMap<>();
        ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = new HashMap<>();
        ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = new HashSet<>();
        env.env.log("----------------------", logID);
        env.env.log("Load " + YM3, logID);
        env.env.log("----------------------", logID);
        env.env.log("NearestYM3ForONSPDLookup " + NearestYM3ForONSPDLookup, logID);
        env.env.log("LatestYM3ForONSPDLookup " + LatestYM3ForONSPDFormat, logID);
        if (!LatestYM3ForONSPDFormat.equals(NearestYM3ForONSPDLookup)) {
            env.env.log("The " + LatestYM3ForONSPDFormat + " ONSPD may be used "
                    + "if the Claimant Postcode does not have a lookup in the "
                    + NearestYM3ForONSPDLookup + " ONSPD.", logID);
        }
        /**
         * Check the postcodes against these to see if we should report them
         * again as unmappable.
         */
        SHBE_CorrectedPostcodes SHBE_CorrectedPostcodes;
        Map<String, ArrayList<String>> ClaimRefToOriginalPostcodes;
        Map<String, ArrayList<String>> ClaimRefToCorrectedPostcodes;
        Set<String> PostcodesCheckedAsMappable;
        //Map<String, Set<String>> UnmappableToMappablePostcodes;
        /**
         * Mapping of National Insurance Numbers to SHBE_NINOID.
         */
        Map<String, SHBE_NINOID> NINOToNINOIDLookup;
        /**
         * SHBE_NINOID to National Insurance Numbers.
         */
        Map<SHBE_NINOID, String> NINOIDToNINOLookup;
        /**
         * Mapping of Dates of Birth to simple SHBE_IDs.
         */
        Map<String, SHBE_DOBID> DOBToDOBIDLookup;
        /**
         * Mapping of SHBE_IDs to Dates of Birth.
         */
        Map<SHBE_DOBID, String> DOBIDToDOBLookup;
        /**
         * Mapping of Unit Postcodes to simple SHBE_IDs.
         */
        Map<String, UKP_RecordID> PostcodeToPostcodeIDLookup;
        /**
         * Mapping of SHBE_ID to a Unit Postcode.
         */
        Map<UKP_RecordID, String> PostcodeIDToPostcodeLookup;
        /**
         * Mapping of SHBE_ID to a Unit Postcode.
         */
        Map<UKP_RecordID, ONSPD_Point> PostcodeIDToPointLookup;
        /**
         * Mapping of ClaimRef String to Claim SHBE_ID.
         */
        Map<String, SHBE_ClaimID> ClaimRefToClaimIDLookup;
        /**
         * Mapping of Claim SHBE_ID to ClaimRef String.
         */
        Map<SHBE_ClaimID, String> ClaimIDToClaimRefLookup;

        /**
         * SHBE_PersonID of All Claimants
         */
        Set<SHBE_PersonID> AllClaimantPersonIDs;

        /**
         * SHBE_PersonID of All Partners
         */
        Set<SHBE_PersonID> AllPartnerPersonIDs;

        /**
         * SHBE_PersonID of All Non-Dependents
         */
        Set<SHBE_PersonID> AllNonDependentIDs;

        /**
         * All SHBE_PersonID to ClaimIDs Lookup
         */
        Map<SHBE_PersonID, Set<SHBE_ClaimID>> PersonIDToClaimIDsLookup;

        /**
         * Initialise mappings from SHBE_Handler.
         */
        SHBE_CorrectedPostcodes = Handler.getCorrectedPostcodes();
        ClaimRefToOriginalPostcodes = SHBE_CorrectedPostcodes.getClaimRefToOriginalPostcodes();
        ClaimRefToCorrectedPostcodes = SHBE_CorrectedPostcodes.getClaimRefToCorrectedPostcodes();
        PostcodesCheckedAsMappable = SHBE_CorrectedPostcodes.getPostcodesCheckedAsMappable();
        //UnmappableToMappablePostcodes = SHBE_CorrectedPostcodes.getUnmappableToMappablePostcodes();

        NINOToNINOIDLookup = Handler.getNINOToNINOIDLookup();
        NINOIDToNINOLookup = Handler.getNINOIDToNINOLookup();
        DOBToDOBIDLookup = Handler.getDtodid();
        DOBIDToDOBLookup = Handler.getDid2d();
        AllClaimantPersonIDs = Handler.getClaimantPersonIDs();
        AllPartnerPersonIDs = Handler.getPartnerPersonIDs();
        AllNonDependentIDs = Handler.getNonDependentPersonIDs();
        PersonIDToClaimIDsLookup = Handler.getPid2cids();
        PostcodeToPostcodeIDLookup = Handler.getPostcodeToPostcodeIDLookup();
        PostcodeIDToPostcodeLookup = Handler.getPid2p();
        PostcodeIDToPointLookup = Handler.getPostcodeIDToPointLookup(YM3);
        ClaimRefToClaimIDLookup = Handler.getC2cid();
        ClaimIDToClaimRefLookup = Handler.getCid2c();
        // Initialise statistics
        int CountOfNewMappableClaimantPostcodes = 0;
        int CountOfMappableClaimantPostcodes = 0;
        int CountOfNewClaimantPostcodes = 0;
        int CountOfNonMappableClaimantPostcodes = 0;
        int CountOfValidFormatClaimantPostcodes = 0;
        int totalCouncilTaxBenefitClaims = 0;
        int totalCouncilTaxAndHousingBenefitClaims = 0;
        int totalHousingBenefitClaims = 0;
        int countSRecords = 0;
        int SRecordNotLoadedCount = 0;
        int NumberOfIncompleteDRecords = 0;
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
        try (BufferedReader br = Generic_IO.getBufferedReader(InputFile)) {
            StreamTokenizer st = new StreamTokenizer(br);
            Generic_IO.setStreamTokenizerSyntax5(st);
            st.wordChars('`', '`');
            st.wordChars('*', '*');
            String line;
            long RecordID = 0;
            lineCount = 0;
            // Declare Variables
            SHBE_S_Record SRecord;
            String ClaimRef;
            SHBE_D_Record DRecord;
            int TenancyType;
            boolean doLoop;
            SHBE_Record record;
            int StatusOfHBClaimAtExtractDate;
            int StatusOfCTBClaimAtExtractDate;
            String Postcode;
            String ClaimantNINO;
            String ClaimantDOB;
            SHBE_PersonID ClaimantPersonID;
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
            int tokenType;
            tokenType = st.nextToken();
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
                                SRecord = new SHBE_S_Record(
                                        env, RecordID, type, line);
                                ClaimRef = SRecord.getClaimRef();
                                if (ClaimRef == null) {
                                    env.env.log("SRecord without a ClaimRef "
                                            + this.getClass().getName()
                                            + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                    env.env.log("SRecord: " + SRecord.toString(), logID);
                                    env.env.log("Line: " + line, logID);
                                    env.env.log("RecordID " + RecordID, logID);
                                    RecordIDsNotLoaded.add(RecordID);
                                    SRecordNotLoadedCount++;
                                } else {
                                    claimID = Handler.getClaimIDAddIfNeeded(ClaimRef);
                                    ArrayList<SHBE_S_Record> recs;
                                    recs = SRecordsWithoutDRecords.get(claimID);
                                    if (recs == null) {
                                        recs = new ArrayList<>();
                                        SRecordsWithoutDRecords.put(claimID, recs);
                                    }
                                    recs.add(SRecord);
                                }
                            } catch (Exception e) {
                                env.env.log("Line not loaded in "
                                        + this.getClass().getName()
                                        + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                env.env.log("Line: " + line, logID);
                                env.env.log("RecordID " + RecordID, logID);
                                env.env.log(e.getLocalizedMessage(), logID);
                                RecordIDsNotLoaded.add(RecordID);
                            }
                            countSRecords++;
                        } else if (line.startsWith("D")) {
                            try {
                                DRecord = new SHBE_D_Record(
                                        env, RecordID, type, line);
                                /**
                                 * For the time being, if for some reason the
                                 * record does not load correctly, then do not
                                 * load this record. Ideally those that do not
                                 * load will be investigated and a solution for
                                 * loading them found.
                                 */
                                TenancyType = DRecord.getTenancyType();
                                if (TenancyType == 0) {
                                    env.env.log("Incomplete record "
                                            + this.getClass().getName()
                                            + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                    env.env.log("Line: " + line, logID);
                                    env.env.log("RecordID " + RecordID, logID);
                                    NumberOfIncompleteDRecords++;
                                    RecordIDsNotLoaded.add(RecordID);
                                    lineCount++;
                                    RecordID++;
                                    break;
                                } else {
                                    ClaimRef = DRecord.getClaimRef();
                                    if (ClaimRef == null) {
                                        RecordIDsNotLoaded.add(RecordID);
                                    } else {
                                        doLoop = false;
                                        claimID = Handler.getIDAddIfNeeded(ClaimRef,
                                                ClaimRefToClaimIDLookup,
                                                ClaimIDToClaimRefLookup,
                                                ClaimIDs,
                                                ClaimIDsOfNewSHBEClaims);
                                        if (Handler.isHBClaim(DRecord)) {
                                            if (DRecord.getCouncilTaxBenefitClaimReferenceNumber() != null) {
                                                totalCouncilTaxAndHousingBenefitClaims++;
                                            } else {
                                                totalHousingBenefitClaims++;
                                            }
                                        }
                                        if (Handler.isCTBOnlyClaim(DRecord)) {
                                            totalCouncilTaxBenefitClaims++;
                                        }
                                        /**
                                         * Get or initialise SHBE_Record record
                                         */
                                        record = Records.get(claimID);
                                        if (record == null) {
                                            record = new SHBE_Record(
                                                    env, claimID, DRecord);
                                            Records.put(claimID, record);
                                            doLoop = true;
                                        } else {
                                            env.env.log("Two records have the same ClaimRef "
                                                    + this.getClass().getName()
                                                    + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                            env.env.log("Line: " + line, logID);
                                            env.env.log("RecordID " + RecordID, logID);
                                            env.env.log("ClaimRef " + ClaimRef, logID);
                                        }
                                        StatusOfHBClaimAtExtractDate = DRecord.getStatusOfHBClaimAtExtractDate();
                                        /**
                                         * 0 = Other; 1 = InPayment; 2 =
                                         * Suspended.
                                         */
                                        switch (StatusOfHBClaimAtExtractDate) {
                                            case 0: {
                                                ClaimIDsWithStatusOfHBAtExtractDateOther.add(claimID);
                                                break;
                                            }
                                            case 1: {
                                                ClaimIDsWithStatusOfHBAtExtractDateInPayment.add(claimID);
                                                break;
                                            }
                                            case 2: {
                                                ClaimIDsWithStatusOfHBAtExtractDateSuspended.add(claimID);
                                                break;
                                            }
                                            default:
                                                env.env.log("Unexpected StatusOfHBClaimAtExtractDate "
                                                        + this.getClass().getName()
                                                        + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                                env.env.log("Line: " + line, logID);
                                                env.env.log("RecordID " + RecordID, logID);
                                                break;
                                        }
                                        StatusOfCTBClaimAtExtractDate = DRecord.getStatusOfCTBClaimAtExtractDate();
                                        /**
                                         * 0 = Other; 1 = InPayment; 2 =
                                         * Suspended.
                                         */
                                        switch (StatusOfCTBClaimAtExtractDate) {
                                            case 0: {
                                                ClaimIDsWithStatusOfCTBAtExtractDateOther.add(claimID);
                                                break;
                                            }
                                            case 1: {
                                                ClaimIDsWithStatusOfCTBAtExtractDateInPayment.add(claimID);
                                                break;
                                            }
                                            case 2: {
                                                ClaimIDsWithStatusOfCTBAtExtractDateSuspended.add(claimID);
                                                break;
                                            }
                                            default:
                                                env.env.log("Unexpected StatusOfCTBClaimAtExtractDate "
                                                        + this.getClass().getName()
                                                        + ".SHBE_Records(SHBE_Environment, File, String)", logID);
                                                env.env.log("Line: " + line, logID);
                                                env.env.log("RecordID " + RecordID, logID);
                                                break;
                                        }
                                        if (doLoop) {
                                            Postcode = DRecord.getClaimantsPostcode();
                                            record.ClaimPostcodeF = Postcode_Handler.formatPostcode(Postcode);
                                            record.ClaimPostcodeFManModified = false;
                                            record.ClaimPostcodeFAutoModified = false;
                                            // Do man modifications (modifications using lookups provided by LCC based on a manual checking of addresses)
                                            if (ClaimRefToOriginalPostcodes.keySet().contains(ClaimRef)) {
                                                ArrayList<String> OriginalPostcodes;
                                                OriginalPostcodes = ClaimRefToOriginalPostcodes.get(ClaimRef);
                                                if (OriginalPostcodes.contains(record.ClaimPostcodeF)) {
                                                    ArrayList<String> CorrectedPostcodes;
                                                    CorrectedPostcodes = ClaimRefToCorrectedPostcodes.get(ClaimRef);
                                                    record.ClaimPostcodeF = CorrectedPostcodes.get(OriginalPostcodes.indexOf(record.ClaimPostcodeF));
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
                                            isMappablePostcode = Postcode_Handler.isMappablePostcode(NearestYM3ForONSPDLookup, record.ClaimPostcodeF);
                                            boolean isMappablePostcodeLastestYM3 = false;
                                            if (!isMappablePostcode) {
                                                isMappablePostcodeLastestYM3 = Postcode_Handler.isMappablePostcode(LatestYM3ForONSPDFormat, record.ClaimPostcodeF);
                                                if (isMappablePostcodeLastestYM3) {
                                                    env.env.log(
                                                            "Postcode " + Postcode + " is not in the " + NearestYM3ForONSPDLookup + " ONSPD, "
                                                            + "but is in the " + LatestYM3ForONSPDFormat + " ONSPD!", logID);
                                                    isMappablePostcode = isMappablePostcodeLastestYM3;
                                                }
                                            }
                                            // For those that are mappable having been modified, store the modification
                                            if (isMappablePostcode) {
                                                if (record.ClaimPostcodeFAutoModified) {
                                                    String claimPostcodeFNoSpaces = record.ClaimPostcodeF.replaceAll(" ", "");
                                                    if (!Postcode.replaceAll(" ", "").equalsIgnoreCase(claimPostcodeFNoSpaces)) {
                                                        int l;
                                                        l = record.ClaimPostcodeF.length();
                                                        String[] p;
                                                        p = new String[2];
                                                        p[0] = Postcode;
                                                        p[1] = claimPostcodeFNoSpaces.substring(0, l - 3) + " " + claimPostcodeFNoSpaces.substring(l - 3);
                                                        ClaimantPostcodesModified.put(claimID, p);
                                                    }
                                                }
                                            }
                                            record.ClaimPostcodeFValidPostcodeFormat = Postcode_Handler.checker.isValidPostcodeUnit(record.ClaimPostcodeF);
                                            if (PostcodeToPostcodeIDLookup.containsKey(record.ClaimPostcodeF)) {
                                                CountOfMappableClaimantPostcodes++;
                                                record.ClaimPostcodeFMappable = true;
                                                record.PostcodeID = PostcodeToPostcodeIDLookup.get(record.ClaimPostcodeF);
                                                // Add the point to the lookup
                                                ONSPD_Point AGDT_Point;
                                                AGDT_Point = Postcode_Handler.getPointFromPostcodeNew(
                                                        NearestYM3ForONSPDLookup,
                                                        Postcode_Handler.TYPE_UNIT,
                                                        record.ClaimPostcodeF);
                                                PostcodeIDToPointLookup.put(record.PostcodeID, AGDT_Point);
                                            } else if (isMappablePostcode) {
                                                CountOfMappableClaimantPostcodes++;
                                                CountOfNewClaimantPostcodes++;
                                                CountOfNewMappableClaimantPostcodes++;
                                                record.ClaimPostcodeFMappable = true;
                                                record.PostcodeID = Handler.getPostcodeIDAddIfNeeded(
                                                        record.ClaimPostcodeF,
                                                        PostcodeToPostcodeIDLookup,
                                                        PostcodeIDToPostcodeLookup);
                                                // Add the point to the lookup
                                                ONSPD_Point p;
                                                if (isMappablePostcodeLastestYM3) {
                                                    p = Postcode_Handler.getPointFromPostcodeNew(
                                                            LatestYM3ForONSPDFormat,
                                                            Postcode_Handler.TYPE_UNIT,
                                                            record.ClaimPostcodeF);
                                                } else {
                                                    p = Postcode_Handler.getPointFromPostcodeNew(
                                                            NearestYM3ForONSPDLookup,
                                                            Postcode_Handler.TYPE_UNIT,
                                                            record.ClaimPostcodeF);
                                                }
                                                PostcodeIDToPointLookup.put(record.PostcodeID, p);
                                            } else {
                                                CountOfNonMappableClaimantPostcodes++;
                                                CountOfNewClaimantPostcodes++;
                                                if (record.ClaimPostcodeFValidPostcodeFormat) {
                                                    CountOfValidFormatClaimantPostcodes++;
                                                }
                                                record.ClaimPostcodeFMappable = false;
                                                ClaimIDsOfClaimsWithoutAMappableClaimantPostcode.add(claimID);
                                                boolean PostcodeCheckedAsMappable;
                                                PostcodeCheckedAsMappable = PostcodesCheckedAsMappable.contains(record.ClaimPostcodeF);
                                                if (PostcodeCheckedAsMappable) {
                                                    ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes.put(claimID, Postcode);
                                                } else {
                                                    // Store unmappable claimant postcode.
                                                    ClaimantPostcodesUnmappable.put(claimID, Postcode);
                                                }
                                            }
                                            ClaimIDToPostcodeIDLookup.put(claimID, record.PostcodeID);
                                            ClaimIDToTenancyTypeLookup.put(claimID, TenancyType);
                                            totalIncome = Handler.getClaimantsAndPartnersIncomeTotal(DRecord);
                                            grandTotalIncome += totalIncome;
                                            if (totalIncome > 0) {
                                                totalIncomeGreaterThanZeroCount++;
                                            }
                                            totalWeeklyEligibleRentAmount = DRecord.getWeeklyEligibleRentAmount();
                                            grandTotalWeeklyEligibleRentAmount += totalWeeklyEligibleRentAmount;
                                            if (totalWeeklyEligibleRentAmount > 0) {
                                                totalWeeklyEligibleRentAmountGreaterThanZeroCount++;
                                            }
                                        }
                                        /**
                                         * Get ClaimantSHBE_PersonID
                                         */
                                        ClaimantNINO = DRecord.getClaimantsNationalInsuranceNumber();
                                        if (ClaimantNINO.trim().equalsIgnoreCase("")
                                                || ClaimantNINO.trim().startsWith("XX999")) {
                                            ClaimIDsOfInvalidClaimantNINOClaims.add(claimID);
                                        }
                                        ClaimantDOB = DRecord.getClaimantsDateOfBirth();
                                        ClaimantPersonID = Handler.getPersonID(
                                                ClaimantNINO,
                                                ClaimantDOB,
                                                NINOToNINOIDLookup,
                                                NINOIDToNINOLookup,
                                                DOBToDOBIDLookup,
                                                DOBIDToDOBLookup);
                                        /**
                                         * If this is a new claim then add to
                                         * appropriate index if the person was
                                         * previously a Claimant, Partner,
                                         * NonDependent or if the Person is
                                         * "new".
                                         */
                                        if (ClaimIDsOfNewSHBEClaims.contains(claimID)) {
                                            addToNew = true;
                                            if (AllClaimantPersonIDs.contains(ClaimantPersonID)) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (AllPartnerPersonIDs.contains(ClaimantPersonID)) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (AllNonDependentIDs.contains(ClaimantPersonID)) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.add(claimID);
                                                addToNew = false;
                                            }
                                            if (addToNew) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew.add(claimID);
                                            }
                                        }
                                        /**
                                         * If ClaimantSHBE_PersonID is already
                                         * in ClaimIDToClaimantPersonIDLookup.
                                         * then ClaimantSHBE_PersonID has
                                         * multiple claims in a month.
                                         */
                                        if (ClaimIDToClaimantPersonIDLookup.containsValue(ClaimantPersonID)) {
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
                                            key = Generic_Collections.getKeys(ClaimIDToClaimantPersonIDLookup,
                                                    ClaimantPersonID).stream().findFirst();
                                            Postcode = DRecord.getClaimantsPostcode();
                                            if (key != null) {
                                                otherClaimID = (SHBE_ClaimID) key;
                                                //String otherClaimRef = ClaimIDToClaimRefLookup.get(otherClaimID);
                                                // Treat those paired records for Cottingley Springs Caravan Park differently
                                                if (Postcode.equalsIgnoreCase(SHBE_Strings.CottingleySpringsCaravanParkPostcode)) {
//                                                    env.log("Cottingley Springs Caravan Park "
//                                                            + strings.CottingleySpringsCaravanParkPostcode
//                                                            + " ClaimRef " + ClaimRef + " paired with " + otherClaimRef
//                                                            + " one claim is for the pitch, the other is for rent of "
//                                                            + "a mobile home. ");
                                                    CottingleySpringsCaravanParkPairedClaimIDs.add(claimID);
                                                    CottingleySpringsCaravanParkPairedClaimIDs.add(otherClaimID);
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
                                                    otherRecord = Records.get(otherClaimID);
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
                                                        ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(claimID);
                                                        ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(otherRecord.getClaimID());
                                                        Set<SHBE_ClaimID> set;
                                                        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(ClaimantPersonID)) {
                                                            set = ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(ClaimantPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(ClaimantPersonID, set);
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
                                        if (ClaimIDToPartnerPersonIDLookup.containsValue(ClaimantPersonID)) {
                                            /**
                                             * Ignore if this is a
                                             * CottingleySpringsCaravanParkPairedClaimIDs.
                                             * It may be that there are partners
                                             * shared in these claims, but such
                                             * a thing is ignored for now.
                                             */
                                            if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                                                /**
                                                 * If Claimant is a Partner in
                                                 * another claim add to
                                                 * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim
                                                 * and
                                                 * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.
                                                 */
                                                key = Generic_Collections.getKeys(ClaimIDToPartnerPersonIDLookup,
                                                        ClaimantPersonID).stream().findFirst();
                                                if (key != null) {
                                                    otherClaimID = (SHBE_ClaimID) key;
                                                    ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(otherClaimID);
                                                }
                                                ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(claimID);
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
                                        if (DRecord.getPartnerFlag() > 0) {
                                            /**
                                             * Add Partner.
                                             */
                                            PartnerPersonID = Handler.getPersonID(
                                                    DRecord.getPartnersNationalInsuranceNumber(),
                                                    DRecord.getPartnersDateOfBirth(),
                                                    NINOToNINOIDLookup,
                                                    NINOIDToNINOLookup,
                                                    DOBToDOBIDLookup,
                                                    DOBIDToDOBLookup);
                                            /**
                                             * If Partner is a Partner in
                                             * another claim add to
                                             * ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim
                                             * and
                                             * PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                                             */
                                            if (ClaimIDToPartnerPersonIDLookup.containsValue(PartnerPersonID)) {
                                                /*
                                                    * Ignore if this is a CottingleySpringsCaravanParkPairedClaimIDs.
                                                    * It may be that there are partners shared in these claims, but such
                                                    * a thing is ignored for now.
                                                 */
                                                if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                                                    key = Generic_Collections.getKeys(ClaimIDToPartnerPersonIDLookup,
                                                            PartnerPersonID).stream().findFirst();
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ClaimID) key;
                                                        Set<SHBE_ClaimID> set;
                                                        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(claimID);
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
                                            if (ClaimIDToClaimantPersonIDLookup.containsValue(PartnerPersonID)) {
                                                /**
                                                 * Ignore if this is a
                                                 * CottingleySpringsCaravanParkPairedClaimIDs.
                                                 * It may be that there are
                                                 * partners shared in these
                                                 * claims, but such a thing is
                                                 * ignored for now.
                                                 */
                                                if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                                                    key = Generic_Collections.getKeys(ClaimIDToClaimantPersonIDLookup,
                                                            PartnerPersonID).stream().findFirst();;
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ClaimID) key;

                                                        Set<SHBE_ClaimID> set;
                                                        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(claimID);
                                                        set.add(otherClaimID);
                                                        ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(claimID);
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    env.log("Partner with NINO " + NINOIDToNINOLookup.get(PartnerPersonID.getNINO_ID())
//                                                            + " DOB " + DOBIDToDOBLookup.get(PartnerPersonID.getDOB_ID())
//                                                            + " in ClaimRef " + ClaimRef
//                                                            + " is a Claimant in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                    env.log("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                }
                                                ClaimIDToPartnerPersonIDLookup.put(claimID, PartnerPersonID);
                                            }
                                        }
                                        /**
                                         * Add to
                                         * ClaimIDToClaimantPersonIDLookup.
                                         */
                                        ClaimIDToClaimantPersonIDLookup.put(claimID, ClaimantPersonID);

                                        /**
                                         * Add to AllClaimantPersonIDs and
                                         * AllPartnerPersonIDs.
                                         */
                                        AllClaimantPersonIDs.add(ClaimantPersonID);
                                        ClaimantPersonIDs.add(ClaimantPersonID);
                                        addToPersonIDToClaimRefsLookup(
                                                claimID,
                                                ClaimantPersonID,
                                                PersonIDToClaimIDsLookup);
                                        if (PartnerPersonID != null) {
                                            AllPartnerPersonIDs.add(PartnerPersonID);
                                            PartnerPersonIDs.add(PartnerPersonID);
                                            ClaimIDToPartnerPersonIDLookup.put(claimID, PartnerPersonID);
                                            addToPersonIDToClaimRefsLookup(
                                                    claimID,
                                                    PartnerPersonID,
                                                    PersonIDToClaimIDsLookup);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                env.env.log(line, logID);
                                env.env.log("RecordID " + RecordID, logID);
                                env.env.log(e.getLocalizedMessage(), logID);
                                RecordIDsNotLoaded.add(RecordID);
                            }
                        }
                        lineCount++;
                        RecordID++;
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
        ite = Records.keySet().iterator();
        while (ite.hasNext()) {
            claimID = ite.next();
            SHBE_Record = Records.get(claimID);
            initSRecords(Handler, SHBE_Record, NINOToNINOIDLookup,
                    NINOIDToNINOLookup, DOBToDOBIDLookup, DOBIDToDOBLookup,
                    AllNonDependentIDs, PersonIDToClaimIDsLookup,
                    ClaimIDToClaimRefLookup);
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
                ClaimIDsOfNewSHBEClaims.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore,
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore,
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore,
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewSHBEClaimsWhereClaimantIsNew,
                ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew.size());
        /**
         * Statistics on Postcodes
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewClaimantPostcodes,
                CountOfNewClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfNewValidMappableClaimantPostcodes,
                CountOfNewMappableClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfMappableClaimantPostcodes,
                CountOfMappableClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfNonMappableClaimantPostcodes,
                CountOfNonMappableClaimantPostcodes);
        addLoadSummaryCount(SHBE_Strings.s_CountOfInvalidFormatClaimantPostcodes,
                CountOfValidFormatClaimantPostcodes);
        /**
         * General count statistics
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaims, Records.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfCTBClaims,
                totalCouncilTaxBenefitClaims);
        addLoadSummaryCount(SHBE_Strings.s_CountOfCTBAndHBClaims,
                totalCouncilTaxAndHousingBenefitClaims);
        addLoadSummaryCount(SHBE_Strings.s_CountOfHBClaims,
                totalHousingBenefitClaims);
        addLoadSummaryCount(SHBE_Strings.s_CountOfRecords, Records.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfSRecords, countSRecords);
        addLoadSummaryCount(SHBE_Strings.s_CountOfSRecordsNotLoaded,
                SRecordNotLoadedCount);
        addLoadSummaryCount(SHBE_Strings.s_CountOfIncompleteDRecords,
                NumberOfIncompleteDRecords);
        addLoadSummaryCount(SHBE_Strings.s_CountOfRecordIDsNotLoaded,
                RecordIDsNotLoaded.size());
        Set<SHBE_PersonID> set;
        Set<SHBE_PersonID> allSet;
        allSet = new HashSet<>();
        /**
         * Claimants
         */
        set = new HashSet<>();
        set.addAll(ClaimIDToClaimantPersonIDLookup.values());
        allSet.addAll(set);
        addLoadSummaryCount(SHBE_Strings.s_CountOfUniqueClaimants, set.size());
        /**
         * Partners
         */
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartners,
                ClaimIDToPartnerPersonIDLookup.size());
        set = Handler.getUniquePersonIDs0(ClaimIDToPartnerPersonIDLookup);
        allSet.addAll(set);
        addLoadSummaryCount(SHBE_Strings.s_CountOfUniquePartners, set.size());
        /**
         * Dependents
         */
        int nDependents;
        nDependents = SHBE_Collections.getCount(ClaimIDToDependentPersonIDsLookup);
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfDependentsInAllClaims,
                nDependents);
        set = Handler.getUniquePersonIDs(ClaimIDToDependentPersonIDsLookup);
        allSet.addAll(set);
        int CountOfUniqueDependents = set.size();
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfUniqueDependents,
                CountOfUniqueDependents);
        /**
         * NonDependents
         */
        int nNonDependents;
        nNonDependents = SHBE_Collections.getCount(ClaimIDToNonDependentPersonIDsLookup);
        addLoadSummaryCount(
                SHBE_Strings.s_CountOfNonDependentsInAllClaims,
                nNonDependents);
        set = Handler.getUniquePersonIDs(ClaimIDToNonDependentPersonIDsLookup);
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
                ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithClaimantsThatArePartnersInAnotherClaim,
                ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim,
                ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimsWithPartnersThatArePartnersInAnotherClaim,
                ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfClaimantsInMultipleClaimsInAMonth,
                ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfPartnersInMultipleClaimsInAMonth,
                PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
        addLoadSummaryCount(SHBE_Strings.s_CountOfNonDependentsInMultipleClaimsInAMonth,
                NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
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
        Generic_IO.writeObject(Records, getRecordsFile());
        Generic_IO.writeObject(ClaimIDsOfNewSHBEClaims,
                getClaimIDsOfNewSHBEClaimsFile());
        Generic_IO.writeObject(ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore,
                getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile());
        Generic_IO.writeObject(ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore,
                getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile());
        Generic_IO.writeObject(ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore,
                getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile());
        Generic_IO.writeObject(ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew,
                getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile());
        Generic_IO.writeObject(ClaimantPersonIDs, getClaimantPersonIDsFile());
        Generic_IO.writeObject(PartnerPersonIDs, getPartnerPersonIDsFile());
        Generic_IO.writeObject(NonDependentPersonIDs, getNonDependentPersonIDsFile());
        Generic_IO.writeObject(CottingleySpringsCaravanParkPairedClaimIDs,
                getCottingleySpringsCaravanParkPairedClaimIDsFile());
        Generic_IO.writeObject(ClaimIDsWithStatusOfHBAtExtractDateInPayment,
                getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile());
        Generic_IO.writeObject(ClaimIDsWithStatusOfHBAtExtractDateSuspended,
                getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile());
        Generic_IO.writeObject(ClaimIDsWithStatusOfHBAtExtractDateOther,
                getClaimIDsWithStatusOfHBAtExtractDateOtherFile());
        Generic_IO.writeObject(ClaimIDsWithStatusOfCTBAtExtractDateInPayment,
                getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile());
        Generic_IO.writeObject(ClaimIDsWithStatusOfCTBAtExtractDateSuspended,
                getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile());
        Generic_IO.writeObject(ClaimIDsWithStatusOfCTBAtExtractDateOther,
                getClaimIDsWithStatusOfCTBAtExtractDateOtherFile());
        Generic_IO.writeObject(SRecordsWithoutDRecords, getSRecordsWithoutDRecordsFile());
        Generic_IO.writeObject(ClaimIDAndCountOfRecordsWithSRecords,
                getClaimIDAndCountOfRecordsWithSRecordsFile());
        Generic_IO.writeObject(ClaimIDsOfClaimsWithoutAMappableClaimantPostcode,
                getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile());
        Generic_IO.writeObject(ClaimIDToClaimantPersonIDLookup,
                getClaimIDToClaimantPersonIDLookupFile());
        Generic_IO.writeObject(ClaimIDToPartnerPersonIDLookup,
                getClaimIDToPartnerPersonIDLookupFile());
        Generic_IO.writeObject(ClaimIDToNonDependentPersonIDsLookup,
                getClaimIDToNonDependentPersonIDsLookupFile());
        Generic_IO.writeObject(ClaimIDToDependentPersonIDsLookup,
                getClaimIDToDependentPersonIDsLookupFile());
        Generic_IO.writeObject(ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim,
                getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile());
        Generic_IO.writeObject(ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim,
                getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile());
        Generic_IO.writeObject(ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim,
                getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile());
        Generic_IO.writeObject(ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim,
                getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile());
        Generic_IO.writeObject(ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim,
                getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile());
        Generic_IO.writeObject(ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile());
        Generic_IO.writeObject(PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile());
        Generic_IO.writeObject(NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile());
        Generic_IO.writeObject(ClaimIDToPostcodeIDLookup, getClaimIDToPostcodeIDLookupFile());
        Generic_IO.writeObject(ClaimIDToTenancyTypeLookup, getClaimIDToTenancyTypeLookupFile());
        Generic_IO.writeObject(LoadSummary, getLoadSummaryFile());
        Generic_IO.writeObject(RecordIDsNotLoaded, getRecordIDsNotLoadedFile());
        Generic_IO.writeObject(ClaimIDsOfInvalidClaimantNINOClaims, getClaimIDsOfInvalidClaimantNINOClaimsFile());
        Generic_IO.writeObject(ClaimantPostcodesUnmappable, getClaimantPostcodesUnmappableFile());
        Generic_IO.writeObject(ClaimantPostcodesModified, getClaimantPostcodesModifiedFile());
        Generic_IO.writeObject(ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes, getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile());
        Generic_IO.writeObject(ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture, getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile());

        // Write out other outputs
        // Write out ClaimRefs of ClaimantsInMultipleClaimsInAMonth
        String YMN;
        YMN = Handler.getYearMonthNumber(inputFilename);
        writeOut(ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                "ClaimantsInMultipleClaimsInAMonth", YMN,
                ClaimIDToClaimRefLookup, NINOIDToNINOLookup,
                DOBIDToDOBLookup);
        // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
        writeOut(PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                "PartnersInMultipleClaimsInAMonth", YMN,
                ClaimIDToClaimRefLookup, NINOIDToNINOLookup,
                DOBIDToDOBLookup);
        // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
        writeOut(NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                "NonDependentsInMultipleClaimsInAMonth", YMN,
                ClaimIDToClaimRefLookup, NINOIDToNINOLookup,
                DOBIDToDOBLookup);
        // Write out ClaimRefs of ClaimIDOfInvalidClaimantNINOClaims
        String name = "ClaimRefsOfInvalidClaimantNINOClaims";
        int logID2 = this.env.env.initLog(name, ".csv");
        Iterator<SHBE_ClaimID> ite2;
        ite2 = ClaimIDsOfInvalidClaimantNINOClaims.iterator();
        while (ite2.hasNext()) {
            claimID = ite2.next();
            this.env.env.log(ClaimIDToClaimRefLookup.get(claimID), logID2);
        }
        this.env.env.closeLog(logID2);
        env.env.log("----------------------", logID);
        env.env.log("Loaded " + YM3, logID);
        env.env.log("----------------------", logID);
    }

    private void writeOut(Map<SHBE_PersonID, Set<SHBE_ClaimID>> mainLookup,
            String name, String YMN,
            Map<SHBE_ClaimID, String> ClaimIDToClaimRefLookup,
            Map<SHBE_NINOID, String> NINOIDToNINOLookup,
            Map<SHBE_DOBID, String> DOBIDToDOBLookup) throws IOException, Exception {
        Iterator<SHBE_PersonID> ite2;
        Iterator<SHBE_ClaimID> ite3;
        String s;
        SHBE_ClaimID claimID;
        SHBE_PersonID PersonID;
        String NINO;
        String DOB;
        Set<SHBE_ClaimID> ClaimRefs;
        int logID2 = env.env.initLog(name + YMN, ".csv");
        env.env.log("NINO,DOB,ClaimRefs", logID2, false);
        ite2 = mainLookup.keySet().iterator();
        while (ite2.hasNext()) {
            PersonID = ite2.next();
            NINO = NINOIDToNINOLookup.get(PersonID.NINOID);
            DOB = DOBIDToDOBLookup.get(PersonID.DOBID);
            if (!NINO.trim().equalsIgnoreCase("")) {
                if (!NINO.trim().startsWith("XX999")) {
                    ClaimRefs = mainLookup.get(PersonID);
                    ite3 = ClaimRefs.iterator();
                    s = NINO + "," + DOB;
                    while (ite3.hasNext()) {
                        claimID = ite3.next();
                        s += "," + ClaimIDToClaimRefLookup.get(claimID);
                    }
                    env.env.log(s, logID2, false);
                }
            }
        }
        env.env.closeLog(logID2);
    }

    /**
     * logs and adds s and n to LoadSummary.
     *
     * @param s
     * @param n
     */
    public final void addLoadSummaryCount(String s, Number n) {
        env.env.log(s + " " + n, logID);
        LoadSummary.put(s, n);
    }

    /**
     *
     * @param SHBE_Handler
     * @param SHBE_Record
     * @param NINOToNINOIDLookup
     * @param NINOIDToNINOLookup
     * @param DOBToDOBIDLookup
     * @param DOBIDToDOBLookup
     * @param AllNonDependentPersonIDs
     * @param PersonIDToClaimRefsLookup
     * @param ClaimIDToClaimRefLookup
     */
    public final void initSRecords(SHBE_Handler SHBE_Handler,
            SHBE_Record SHBE_Record,
            Map<String, SHBE_NINOID> NINOToNINOIDLookup,
            Map<SHBE_NINOID, String> NINOIDToNINOLookup,
            Map<String, SHBE_DOBID> DOBToDOBIDLookup,
            Map<SHBE_DOBID, String> DOBIDToDOBLookup,
            Set<SHBE_PersonID> AllNonDependentPersonIDs,
            Map<SHBE_PersonID, Set<SHBE_ClaimID>> PersonIDToClaimRefsLookup,
            Map<SHBE_ClaimID, String> ClaimIDToClaimRefLookup
    ) throws IOException, ClassNotFoundException {
        ArrayList<SHBE_S_Record> SRecordsForClaim;
        SHBE_ClaimID claimID = SHBE_Record.getClaimID();
        SRecordsForClaim = getSRecordsWithoutDRecords().get(claimID);
        if (SRecordsForClaim != null) {
            // Declare variables
            SHBE_PersonID personID;
            String NINO;
            String DOB;
            int SubRecordType;
            Object key;
            SHBE_ClaimID otherClaimID;
            Iterator<SHBE_S_Record> ite = SRecordsForClaim.iterator();
            while (ite.hasNext()) {
                SHBE_S_Record srec = ite.next();
                NINO = srec.getSubRecordChildReferenceNumberOrNINO();
                DOB = srec.getSubRecordDateOfBirth();
                SubRecordType = srec.getSubRecordType();
                switch (SubRecordType) {
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
                        if (NINO.isEmpty()) {
                            boolean set;
                            set = false;
                            while (!set) {
                                NINO = "" + i;
                                NINO += "_" + cNINO;
                                if (NINOToNINOIDLookup.containsKey(NINO)) {
                                    env.env.log("NINO " + NINO + " is not unique"
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
                            NINO += "_" + cNINO;
                            if (NINOToNINOIDLookup.containsKey(NINO)) {
                                /**
                                 * If the claimant has more than one claim, this
                                 * is fine. Otherwise we have to do something.
                                 */
                                if (ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.contains(claimID)) {
                                    set = true;
                                } else {
                                    env.env.log("NINO " + NINO + " is not unique"
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
                                NINO = "" + i;
                                NINO += "_" + cNINO;
                                if (NINOToNINOIDLookup.containsKey(NINO)) {
                                    env.env.log("NINO " + NINO + " is not unique "
                                            + "for " + cNINO, logID,
                                            false);
                                } else {
                                    set = true;
                                }
                                i++;
                            }
                        }
                        personID = SHBE_Handler.getPersonID(NINO, DOB,
                                NINOToNINOIDLookup, NINOIDToNINOLookup,
                                DOBToDOBIDLookup, DOBIDToDOBLookup);
                        /**
                         * Add to ClaimIDToDependentPersonIDsLookup.
                         */
                        Set<SHBE_PersonID> s = ClaimIDToDependentPersonIDsLookup.get(claimID);
                        if (s == null) {
                            s = new HashSet<>();
                            ClaimIDToDependentPersonIDsLookup.put(claimID, s);
                        }
                        s.add(personID);
                        addToPersonIDToClaimRefsLookup(claimID, personID,
                                PersonIDToClaimRefsLookup);
                        break;
                    case 2:
                        personID = SHBE_Handler.getPersonID(NINO, DOB,
                                NINOToNINOIDLookup, NINOIDToNINOLookup,
                                DOBToDOBIDLookup, DOBIDToDOBLookup);
                        /**
                         * Ignore if this is a
                         * CottingleySpringsCaravanParkPairedClaimIDs. It may be
                         * that there are partners shared in these claims, but
                         * such a thing is ignored for now.
                         */
                        if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(claimID)) {
                            /**
                             * If NonDependent is a NonDependent in another
                             * claim add to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                             */
                            key = SHBE_Collections.getKey(ClaimIDToNonDependentPersonIDsLookup, personID);
                            if (key != null) {
                                otherClaimID = (SHBE_ClaimID) key;
                                Set<SHBE_ClaimID> set;
                                set = NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(personID);
                                if (set == null) {
                                    set = new HashSet<>();
                                    NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(personID, set);
                                }
                                set.add(claimID);
                                set.add(otherClaimID);
                                ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
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
                            if (ClaimIDToClaimantPersonIDLookup.containsValue(personID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ClaimID) key;
                                    Set<SHBE_ClaimID> set;
                                    set = NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(personID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(personID, set);
                                    }
                                    set.add(claimID);
                                    set.add(otherClaimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
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
                            if (ClaimIDToPartnerPersonIDLookup.containsValue(personID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ClaimID) key;
                                    Set<SHBE_ClaimID> set;
                                    set = NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(personID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(personID, set);
                                    }
                                    set.add(claimID);
                                    set.add(otherClaimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(claimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
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
                        s = ClaimIDToNonDependentPersonIDsLookup.get(claimID);
                        if (s == null) {
                            s = new HashSet<>();
                            ClaimIDToNonDependentPersonIDsLookup.put(claimID, s);
                        }
                        s.add(personID);
                        NonDependentPersonIDs.add(personID);
                        AllNonDependentPersonIDs.add(personID);
                        addToPersonIDToClaimRefsLookup(claimID, personID,
                                PersonIDToClaimRefsLookup);
                        break;
                    default:
                        env.env.log("Unrecognised SubRecordType " + SubRecordType, logID);
                        break;
                }
            }
            SHBE_Record.SRecords = SRecordsForClaim;
            ClaimIDAndCountOfRecordsWithSRecords.put(claimID, SRecordsForClaim.size());
        }
        /**
         * Remove all assigned SRecords from SRecordsWithoutDRecords.
         */
        Iterator<SHBE_ClaimID> iteID;
        iteID = ClaimIDAndCountOfRecordsWithSRecords.keySet().iterator();
        while (iteID.hasNext()) {
            SRecordsWithoutDRecords.remove(iteID.next());
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
     * @return
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
        Path inputFile = Paths.get(directory.toString(), filename);
        try {
            String line;
            //BufferedReader br = Generic_IO.getBufferedReader(inputFile);
            try (BufferedReader br = Generic_IO.getBufferedReader(inputFile)) {
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
     * @return the InputFile
     */
    public Path getInputFile() {
        return InputFile;
    }

    /**
     * If not initialised, initialises ClaimIDToClaimantPersonIDLookup then
     * returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToClaimantPersonIDLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToClaimantPersonIDLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimIDToClaimantPersonIDLookup then
     * returns it.
     *
     * @return
     */
    protected Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToClaimantPersonIDLookup()
            throws IOException, ClassNotFoundException {
        if (ClaimIDToClaimantPersonIDLookup == null) {
            Path f;
            f = getClaimIDToClaimantPersonIDLookupFile();
            if (Files.exists(f)) {
                ClaimIDToClaimantPersonIDLookup = (Map<SHBE_ClaimID, SHBE_PersonID>) Generic_IO.readObject(f);
            } else {
                ClaimIDToClaimantPersonIDLookup = new HashMap<>();
            }
        }
        return ClaimIDToClaimantPersonIDLookup;
    }

    /**
     * If not initialised, initialises ClaimIDToPartnerPersonIDLookup then
     * returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToPartnerPersonIDLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToPartnerPersonIDLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimIDToPartnerPersonIDLookup then
     * returns it.
     *
     * @return
     */
    protected Map<SHBE_ClaimID, SHBE_PersonID> getClaimIDToPartnerPersonIDLookup()
            throws IOException, ClassNotFoundException {
        if (ClaimIDToPartnerPersonIDLookup == null) {
            Path f;
            f = getClaimIDToPartnerPersonIDLookupFile();
            if (Files.exists(f)) {
                ClaimIDToPartnerPersonIDLookup = (Map<SHBE_ClaimID, SHBE_PersonID>) Generic_IO.readObject(f);
            } else {
                ClaimIDToPartnerPersonIDLookup = new HashMap<>();
            }
        }
        return ClaimIDToPartnerPersonIDLookup;
    }

    /**
     * If not initialised, initialises ClaimIDToDependentPersonIDsLookup then
     * returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToDependentPersonIDsLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToDependentPersonIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimIDToDependentPersonIDsLookup then
     * returns it.
     *
     * @return
     */
    protected Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToDependentPersonIDsLookup()
            throws IOException, ClassNotFoundException {
        if (ClaimIDToDependentPersonIDsLookup == null) {
            Path f;
            f = getClaimIDToDependentPersonIDsLookupFile();
            if (Files.exists(f)) {
                ClaimIDToDependentPersonIDsLookup = (Map<SHBE_ClaimID, Set<SHBE_PersonID>>) Generic_IO.readObject(f);
            } else {
                ClaimIDToDependentPersonIDsLookup = new HashMap<>();
            }
        }
        return ClaimIDToDependentPersonIDsLookup;
    }

    /**
     * If not initialised, initialises ClaimIDToNonDependentPersonIDsLookup then
     * returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToNonDependentPersonIDsLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToNonDependentPersonIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimIDToNonDependentPersonIDsLookup then
     * returns it.
     *
     * @return
     */
    protected Map<SHBE_ClaimID, Set<SHBE_PersonID>> getClaimIDToNonDependentPersonIDsLookup()
            throws IOException, ClassNotFoundException {
        if (ClaimIDToNonDependentPersonIDsLookup == null) {
            Path f;
            f = getClaimIDToNonDependentPersonIDsLookupFile();
            if (Files.exists(f)) {
                ClaimIDToNonDependentPersonIDsLookup = (Map<SHBE_ClaimID, Set<SHBE_PersonID>>) Generic_IO.readObject(f);
            } else {
                ClaimIDToNonDependentPersonIDsLookup = new HashMap<>();
            }
        }
        return ClaimIDToNonDependentPersonIDsLookup;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim then returns
     * it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim then returns
     * it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile();
            if (Files.exists(f)) {
                ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim then returns
     * it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim then returns
     * it.
     *
     * @return
     */
    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim()
            throws IOException, ClassNotFoundException {
        if (ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim then returns
     * it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile();
            if (Files.exists(f)) {
                ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim then returns
     * it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim == null) {
            Path f;
            f = getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim
     * then returns it.
     *
     * @param hoome
     * @return
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim == null) {
            Path f = getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile();
            if (Files.exists(f)) {
                ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim
     * then returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup then returns
     * it.
     *
     * @return
     */
    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup()
            throws IOException, ClassNotFoundException {
        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            Path f;
            f = getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (Files.exists(f)) {
                ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
            }
        }
        return ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
    }

    /**
     * If not initialised, initialises
     * PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup then returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            Path f = getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (Files.exists(f)) {
                PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
            }
        }
        return PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
    }

    /**
     * If not initialised, initialises
     * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup then
     * returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_PersonID, Set<SHBE_ClaimID>> getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup then
     * returns it.
     *
     * @return
     */
    protected Map<SHBE_PersonID, Set<SHBE_ClaimID>> getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup()
            throws IOException, ClassNotFoundException {
        if (NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            Path f;
            f = getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (Files.exists(f)) {
                NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (Map<SHBE_PersonID, Set<SHBE_ClaimID>>) Generic_IO.readObject(f);
            } else {
                NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
            }
        }
        return NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
    }

    /**
     * If not initialised, initialises ClaimIDToPostcodeLookup then returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, UKP_RecordID> getClaimIDToPostcodeIDLookup(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToPostcodeIDLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimIDToPostcodeIDLookup == null) {
            Path f;
            f = getClaimIDToPostcodeIDLookupFile();
            if (Files.exists(f)) {
                ClaimIDToPostcodeIDLookup = (Map<SHBE_ClaimID, UKP_RecordID>) Generic_IO.readObject(f);
            } else {
                ClaimIDToPostcodeIDLookup = new HashMap<>();
            }
        }
        return ClaimIDToPostcodeIDLookup;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture then returns it.
     *
     * @param hoome
     * @return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
                    throw e;
                }
                env.initMemoryReserve(env.env);
                return getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(hoome);
            } else {
                throw e;
            }
        }
    }

    protected Set<SHBE_ClaimID> getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture() throws IOException, ClassNotFoundException {
        if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture == null) {
            Path f;
            f = getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile();
            if (Files.exists(f)) {
                ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture;
    }

    /**
     * If not initialised, initialises ClaimIDToTenancyTypeLookup then returns
     * it.
     *
     * @param hoome
     * @return ClaimIDToTenancyTypeLookup
     */
    public final Map<SHBE_ClaimID, Integer> getClaimIDToTenancyTypeLookup(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDToTenancyTypeLookup();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimIDToTenancyTypeLookup == null) {
            Path f;
            f = getClaimIDToTenancyTypeLookupFile();
            if (Files.exists(f)) {
                ClaimIDToTenancyTypeLookup = (Map<SHBE_ClaimID, Integer>) Generic_IO.readObject(f);
            } else {
                ClaimIDToTenancyTypeLookup = new HashMap<>();
            }
        }
        return ClaimIDToTenancyTypeLookup;
    }

    /**
     * If not initialised, initialises LoadSummary then returns it.
     *
     * @param hoome
     * @return LoadSummary
     */
    public final Map<String, Number> getLoadSummary(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getLoadSummary();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (LoadSummary == null) {
            Path f;
            f = getLoadSummaryFile();
            if (Files.exists(f)) {
                LoadSummary = (Map<String, Number>) Generic_IO.readObject(f);
            } else {
                LoadSummary = new HashMap<>();
            }
        }
        return LoadSummary;
    }

    /**
     * If not initialised, initialises RecordIDsNotLoaded then returns it.
     *
     * @param hoome
     * @return RecordIDsNotLoaded
     */
    public final ArrayList<Long> getRecordIDsNotLoaded(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getRecordIDsNotLoaded();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (RecordIDsNotLoaded == null) {
            Path f;
            f = getRecordIDsNotLoadedFile();
            if (Files.exists(f)) {
                RecordIDsNotLoaded = (ArrayList<Long>) Generic_IO.readObject(f);
            } else {
                RecordIDsNotLoaded = new ArrayList<>();
            }
        }
        return RecordIDsNotLoaded;
    }

    /**
     * If not initialised, initialises ClaimRefsOfInvalidClaimantNINOClaims then
     * returns it.
     *
     * @param hoome
     * @return ClaimIDsOfInvalidClaimantNINOClaims
     */
    public final Set<SHBE_ClaimID> getClaimIDsOfInvalidClaimantNINOClaims(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimIDsOfInvalidClaimantNINOClaims();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimIDsOfInvalidClaimantNINOClaims == null) {
            Path f;
            f = getClaimIDsOfInvalidClaimantNINOClaimsFile();
            if (Files.exists(f)) {
                ClaimIDsOfInvalidClaimantNINOClaims = (Set<SHBE_ClaimID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfInvalidClaimantNINOClaims = new HashSet<>();
            }
        }
        return ClaimIDsOfInvalidClaimantNINOClaims;
    }

    /**
     * If not initialised, initialises ClaimantPostcodesUnmappable then returns
     * it.
     *
     * @param hoome
     * @return ClaimantPostcodesUnmappable
     */
    public final Map<SHBE_ClaimID, String> getClaimantPostcodesUnmappable(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPostcodesUnmappable();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimantPostcodesUnmappable == null) {
            Path f = getClaimantPostcodesUnmappableFile();
            if (Files.exists(f)) {
                ClaimantPostcodesUnmappable = (Map<SHBE_ClaimID, String>) Generic_IO.readObject(f);
            } else {
                ClaimantPostcodesUnmappable = new HashMap<>();
            }
        }
        return ClaimantPostcodesUnmappable;
    }

    /**
     * If not initialised, initialises ClaimantPostcodesModified then returns
     * it.
     *
     * @param hoome
     * @return ClaimantPostcodesModified
     */
    public final Map<SHBE_ClaimID, String[]> getClaimantPostcodesModified(
            boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPostcodesModified();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * If not initialised, initialises ClaimantPostcodesModified then returns
     * it.
     *
     * @return ClaimantPostcodesModified
     */
    protected Map<SHBE_ClaimID, String[]> getClaimantPostcodesModified()
            throws IOException, ClassNotFoundException {
        if (ClaimantPostcodesModified == null) {
            Path f;
            f = getClaimantPostcodesModifiedFile();
            if (Files.exists(f)) {
                ClaimantPostcodesModified = (Map<SHBE_ClaimID, String[]>) Generic_IO.readObject(f);
            } else {
                ClaimantPostcodesModified = new HashMap<>();
            }
        }
        return ClaimantPostcodesModified;
    }

    /**
     * If not initialised, initialises
     * ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes then returns it.
     *
     * @param hoome
     * @return
     */
    public final Map<SHBE_ClaimID, String> getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
        if (ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes == null) {
            Path f;
            f = getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile();
            if (Files.exists(f)) {
                ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = (Map<SHBE_ClaimID, String>) Generic_IO.readObject(f);
            } else {
                ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = new HashMap<>();
            }
        }
        return ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes;
    }

    /**
     * @return the DataFile
     * @throws java.io.IOException If encountered.
     */
    protected final Path getFile() throws IOException {
        if (File == null) {
            File = getFile("Records" + SHBE_Strings.s_BinaryFileExtension);
        }
        return File;
    }

    /**
     * @return RecordsFile initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getRecordsFile() throws IOException {
        if (RecordsFile == null) {
            RecordsFile = getFile(SHBE_Strings.s_Records + SHBE_Strings.symbol_underscore
                    + "Map_String__SHBE_Record" + SHBE_Strings.s_BinaryFileExtension);
        }
        return RecordsFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsFile() throws IOException {
        if (ClaimIDsOfNewSHBEClaimsFile == null) {
            ClaimIDsOfNewSHBEClaimsFile = getFile("ClaimIDsOfNewSHBEClaims"
                    + SHBE_Strings.symbol_underscore + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile() throws IOException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsFile initialising if it is not already
     * initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile()
            throws IOException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile
     * initialising if it is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile() throws IOException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile initialising if it
     * is not already initialised.
     * @throws java.io.IOException If encountered.
     */
    protected final Path getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile() throws IOException {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile;
    }

    public final Path getClaimantPersonIDsFile() throws IOException {
        if (ClaimantPersonIDsFile == null) {
            ClaimantPersonIDsFile = getFile(
                    "Claimant"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimantPersonIDsFile;
    }

    public final Path getPartnerPersonIDsFile() throws IOException {
        if (PartnerPersonIDsFile == null) {
            PartnerPersonIDsFile = getFile(
                    "Partner"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return PartnerPersonIDsFile;
    }

    public final Path getNonDependentPersonIDsFile() throws IOException {
        if (NonDependentPersonIDsFile == null) {
            NonDependentPersonIDsFile = getFile(
                    "NonDependent"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return NonDependentPersonIDsFile;
    }

    public final Set<SHBE_PersonID> getClaimantPersonIDs(boolean hoome) throws IOException, ClassNotFoundException {
        try {
            return getClaimantPersonIDs();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * @return
     */
    public Set<SHBE_PersonID> getClaimantPersonIDs()
            throws IOException, ClassNotFoundException {
        ClaimantPersonIDsFile = getClaimantPersonIDsFile();
        return getClaimantPersonIDs(ClaimantPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final Set<SHBE_PersonID> getClaimantPersonIDs(
            Path f) throws IOException, ClassNotFoundException {
        if (ClaimantPersonIDs == null) {
            ClaimantPersonIDs = env.collections.getPersonIDs(f);
        }
        return ClaimantPersonIDs;
    }

    public final Set<SHBE_PersonID> getPartnerPersonIDs(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getPartnerPersonIDs();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * @return
     */
    public Set<SHBE_PersonID> getPartnerPersonIDs() throws IOException,
            ClassNotFoundException {
        PartnerPersonIDsFile = getPartnerPersonIDsFile();
        return getPartnerPersonIDs(PartnerPersonIDsFile);
    }

    /**
     * @param f
     * @return
     */
    public final Set<SHBE_PersonID> getPartnerPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (PartnerPersonIDs == null) {
            PartnerPersonIDs = env.collections.getPersonIDs(f);
        }
        return PartnerPersonIDs;
    }

    public final Set<SHBE_PersonID> getNonDependentPersonIDs(boolean hoome)
            throws IOException, ClassNotFoundException {
        try {
            return getNonDependentPersonIDs();
        } catch (OutOfMemoryError e) {
            if (hoome) {
                env.clearMemoryReserve(env.env);
                if (!env.handler.clearSomeExcept(YM3)) {
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
     * @param f
     * @return
     */
    public final Set<SHBE_PersonID> getNonDependentPersonIDs(Path f)
            throws IOException, ClassNotFoundException {
        if (NonDependentPersonIDs == null) {
            NonDependentPersonIDs = env.collections.getPersonIDs(f);
        }
        return NonDependentPersonIDs;
    }

    /**
     * @return
     */
    public Set<SHBE_PersonID> getNonDependentPersonIDs()
            throws IOException, ClassNotFoundException {
        NonDependentPersonIDsFile = getNonDependentPersonIDsFile();
        return getNonDependentPersonIDs(NonDependentPersonIDsFile);
    }

    /**
     * @return CottingleySpringsCaravanParkPairedClaimIDsFile initialising if it
     * is not already initialised.
     */
    protected final Path getCottingleySpringsCaravanParkPairedClaimIDsFile()
            throws IOException, ClassNotFoundException {
        if (CottingleySpringsCaravanParkPairedClaimIDsFile == null) {
            CottingleySpringsCaravanParkPairedClaimIDsFile = getFile(
                    SHBE_Strings.s_CottingleySpringsCaravanPark + "PairedClaimIDs"
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return CottingleySpringsCaravanParkPairedClaimIDsFile;
    }

    /**
     * @return ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile initialising if
     * it is not already initialised.
     */
    protected final Path getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile() throws IOException {
        if (ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile == null) {
            ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeIn
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile;
    }

    /**
     * @return ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile initialising if
     * it is not already initialised.
     */
    protected final Path getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile() throws IOException {
        if (ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile == null) {
            ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeSuspended
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile;
    }

    /**
     * @return ClaimIDsWithStatusOfHBAtExtractDateOtherFile initialising if it
     * is not already initialised.
     */
    protected final Path getClaimIDsWithStatusOfHBAtExtractDateOtherFile() throws IOException {
        if (ClaimIDsWithStatusOfHBAtExtractDateOtherFile == null) {
            ClaimIDsWithStatusOfHBAtExtractDateOtherFile = getFile(
                    SHBE_Strings.s_HB + SHBE_Strings.s_PaymentTypeOther
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsWithStatusOfHBAtExtractDateOtherFile;
    }

    /**
     * @return ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile initialising if
     * it is not already initialised.
     */
    protected final Path getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile() throws IOException {
        if (ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile == null) {
            ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeIn
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile;
    }

    /**
     * @return ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile initialising if
     * it is not already initialised.
     */
    protected final Path getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile() throws IOException {
        if (ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile == null) {
            ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeSuspended
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile;
    }

    /**
     * @return ClaimIDsWithStatusOfCTBAtExtractDateOtherFile initialising if it
     * is not already initialised.
     */
    protected final Path getClaimIDsWithStatusOfCTBAtExtractDateOtherFile() throws IOException {
        if (ClaimIDsWithStatusOfCTBAtExtractDateOtherFile == null) {
            ClaimIDsWithStatusOfCTBAtExtractDateOtherFile = getFile(
                    SHBE_Strings.s_CTB + SHBE_Strings.s_PaymentTypeOther
                    + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateOtherFile;
    }

    /**
     * @return SRecordsWithoutDRecordsFile initialising if it is not already
     * initialised.
     */
    protected final Path getSRecordsWithoutDRecordsFile() throws IOException {
        if (SRecordsWithoutDRecordsFile == null) {
            SRecordsWithoutDRecordsFile = getFile(
                    "SRecordsWithoutDRecordsFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__ArrayList_SHBE_S_Record"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return SRecordsWithoutDRecordsFile;
    }

    /**
     * @return ClaimIDAndCountOfRecordsWithSRecordsFile initialising if it is
     * not already initialised.
     */
    protected final Path getClaimIDAndCountOfRecordsWithSRecordsFile()
            throws IOException {
        if (ClaimIDAndCountOfRecordsWithSRecordsFile == null) {
            ClaimIDAndCountOfRecordsWithSRecordsFile = getFile(
                    "ClaimIDAndCountOfRecordsWithSRecordsFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDAndCountOfRecordsWithSRecordsFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile initialising
     * if it is not already initialised.
     */
    protected final Path getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile()
            throws IOException {
        if (ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile == null) {
            ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile = getFile(
                    "ClaimIDsOfClaimsWithoutAMappableClaimantPostcode" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile;
    }

    /**
     * @return ClaimIDToClaimantPersonIDLookupFile initialising if it is not
     * already initialised.
     */
    public final Path getClaimIDToClaimantPersonIDLookupFile() throws IOException {
        if (ClaimIDToClaimantPersonIDLookupFile == null) {
            ClaimIDToClaimantPersonIDLookupFile = getFile(
                    "ClaimIDToClaimantPersonIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDToClaimantPersonIDLookupFile;
    }

    /**
     * @return ClaimIDToPartnerPersonIDLookupFile initialising if it is not
     * already initialised.
     */
    public final Path getClaimIDToPartnerPersonIDLookupFile()
            throws IOException {
        if (ClaimIDToPartnerPersonIDLookupFile == null) {
            ClaimIDToPartnerPersonIDLookupFile = getFile(
                    "ClaimIDToPartnerPersonIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDToPartnerPersonIDLookupFile;
    }

    /**
     * @return ClaimIDToDependentPersonIDsLookupFile initialising if it is not
     * already initialised.
     */
    public final Path getClaimIDToDependentPersonIDsLookupFile()
            throws IOException {
        if (ClaimIDToDependentPersonIDsLookupFile == null) {
            ClaimIDToDependentPersonIDsLookupFile = getFile(
                    "ClaimIDToDependentPersonIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Set<SHBE_PersonID>"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDToDependentPersonIDsLookupFile;
    }

    /**
     * @return ClaimIDToNonDependentPersonIDsLookupFile initialising if it is
     * not already initialised.
     */
    public final Path getClaimIDToNonDependentPersonIDsLookupFile() throws IOException {
        if (ClaimIDToNonDependentPersonIDsLookupFile == null) {
            ClaimIDToNonDependentPersonIDsLookupFile = getFile(
                    "ClaimIDToNonDependentPersonIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Set_SHBE_PersonID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDToNonDependentPersonIDsLookupFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile()
            throws IOException {
        if (ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile()
            throws IOException {
        if (ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile()
            throws IOException {
        if (ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile()
            throws IOException {
        if (ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return
     * ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile()
            throws IOException {
        if (ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile == null) {
            ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;
    }

    /**
     * @return ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile()
            throws IOException {
        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     */
    public final Path getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile()
            throws IOException {
        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     */
    public final Path getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile()
            throws IOException {
        if (NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_PersonID__Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return ClaimIDToPostcodeIDLookupFile initialising if it is not already
     * initialised.
     */
    public final Path getClaimIDToPostcodeIDLookupFile()
            throws IOException {
        if (ClaimIDToPostcodeIDLookupFile == null) {
            ClaimIDToPostcodeIDLookupFile = getFile(
                    "ClaimIDToPostcodeIDLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDToPostcodeIDLookupFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile()
            throws IOException {
        if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile == null) {
            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile = getFile(
                    "ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;
    }

    /**
     * @return ClaimIDToTenancyTypeLookupFile initialising if it is not already
     * initialised.
     */
    public final Path getClaimIDToTenancyTypeLookupFile() throws IOException {
        if (ClaimIDToTenancyTypeLookupFile == null) {
            ClaimIDToTenancyTypeLookupFile = getFile(
                    "ClaimIDToTenancyTypeLookup" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDToTenancyTypeLookupFile;
    }

    /**
     * @return LoadSummaryFile initialising if it is not already initialised.
     */
    public final Path getLoadSummaryFile() throws IOException {
        if (LoadSummaryFile == null) {
            LoadSummaryFile = getFile(
                    "LoadSummary" + SHBE_Strings.symbol_underscore
                    + "Map_String__Integer"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return LoadSummaryFile;
    }

    /**
     * @return RecordIDsNotLoadedFile initialising if it is not already
     * initialised.
     */
    public final Path getRecordIDsNotLoadedFile() throws IOException {
        if (RecordIDsNotLoadedFile == null) {
            RecordIDsNotLoadedFile = getFile(
                    "RecordIDsNotLoaded" + SHBE_Strings.symbol_underscore
                    + "ArrayList_Long"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return RecordIDsNotLoadedFile;
    }

    /**
     * @return ClaimIDsOfInvalidClaimantNINOClaimsFile initialising if it is not
     * already initialised.
     */
    public final Path getClaimIDsOfInvalidClaimantNINOClaimsFile()
            throws IOException {
        if (ClaimIDsOfInvalidClaimantNINOClaimsFile == null) {
            ClaimIDsOfInvalidClaimantNINOClaimsFile = getFile(
                    "ClaimIDsOfInvalidClaimantNINOClaimsFile" + SHBE_Strings.symbol_underscore
                    + "Set_SHBE_ID"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimIDsOfInvalidClaimantNINOClaimsFile;
    }

    /**
     * @return ClaimantPostcodesUnmappableFile initialising if it is not already
     * initialised.
     */
    public final Path getClaimantPostcodesUnmappableFile()
            throws IOException {
        if (ClaimantPostcodesUnmappableFile == null) {
            ClaimantPostcodesUnmappableFile = getFile(
                    "ClaimantPostcodesUnmappable" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimantPostcodesUnmappableFile;
    }

    /**
     * @return ClaimantPostcodesModifiedFile initialising if it is not already
     * initialised.
     */
    public final Path getClaimantPostcodesModifiedFile() throws IOException {
        if (ClaimantPostcodesModifiedFile == null) {
            ClaimantPostcodesModifiedFile = getFile(
                    "ClaimantPostcodesModified" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__String[]"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimantPostcodesModifiedFile;
    }

    /**
     * @return ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile
     * initialising if it is not already initialised.
     */
    public final Path getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile()
            throws IOException {
        if (ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile == null) {
            ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile = getFile(
                    "ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes" + SHBE_Strings.symbol_underscore
                    + "Map_SHBE_ID__String"
                    + SHBE_Strings.s_BinaryFileExtension);
        }
        return ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile;
    }

    /**
     * Clears the main Data. This is for memory handling reasons.
     */
    public void clearData() {
        this.Records = null;
        this.RecordIDsNotLoaded = null;
        this.SRecordsWithoutDRecords = null;
    }
}
