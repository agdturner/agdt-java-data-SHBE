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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.core.ONSPD_ID;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Point;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.util.Generic_Collections;
//import uk.ac.leeds.ccg.andyt.projects.digitalwelfare.data.SHBE_CorrectedPostcodes;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Postcode_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.util.ONSPD_YM3;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_ID;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.util.SHBE_Collections;
//import uk.ac.leeds.ccg.andyt.projects.digitalwelfare.util.SHBE_Collections;

/**
 *
 * @author geoagdt
 */
public class SHBE_Records extends SHBE_Object implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * For convenience.
     */
    private transient SHBE_Strings Strings;
    private transient ONSPD_Postcode_Handler Postcode_Handler;

    /**
     * Call this method to initialise fields declared transient after having
     * read this back as a Serialized Object.
     *
     * @param e
     */
    public void init(SHBE_Environment e) {
        Env = e;
        Strings = e.Strings;
        Postcode_Handler = e.getPostcode_Handler();
    }

    /**
     * Keys are ClaimIDs, values are SHBE_Record.
     */
    private HashMap<SHBE_ID, SHBE_Record> Records;

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
     * A store for ClaimIDs for Cottingley Springs Caravan Park where there are
     * two claims for a claimant, one for a pitch and the other for the rent of
     * a caravan.
     */
    private HashSet<SHBE_ID> CottingleySpringsCaravanParkPairedClaimIDs;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private HashSet<SHBE_ID> ClaimIDsWithStatusOfHBAtExtractDateInPayment;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 2 (Suspended).
     */
    private HashSet<SHBE_ID> ClaimIDsWithStatusOfHBAtExtractDateSuspended;

    /**
     * A store for ClaimIDs where: StatusOfHBClaimAtExtractDate = 0 (Suspended).
     */
    private HashSet<SHBE_ID> ClaimIDsWithStatusOfHBAtExtractDateOther;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 1 (In
     * Payment).
     */
    private HashSet<SHBE_ID> ClaimIDsWithStatusOfCTBAtExtractDateInPayment;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 2
     * (Suspended).
     */
    private HashSet<SHBE_ID> ClaimIDsWithStatusOfCTBAtExtractDateSuspended;

    /**
     * A store for ClaimIDs where: StatusOfCTBClaimAtExtractDate = 0
     * (Suspended).
     */
    private HashSet<SHBE_ID> ClaimIDsWithStatusOfCTBAtExtractDateOther;

    /**
     * SRecordsWithoutDRecords indexed by ClaimRef SHBE_ID. Once the SHBE data
     * is loaded from source, this only contains those SRecordsWithoutDRecords
     * that are not linked to a DRecord.
     */
    private HashMap<SHBE_ID, ArrayList<SHBE_S_Record>> SRecordsWithoutDRecords;

    /**
     * For storing the ClaimIDs of Records that have SRecords along with the
     * count of those SRecordsWithoutDRecords.
     */
    private HashMap<SHBE_ID, Integer> ClaimIDAndCountOfRecordsWithSRecords;

    /**
     * For storing the Year_Month of this. This is an identifier for these data.
     */
    private ONSPD_YM3 YM3;

    /**
     * For storing the NearestYM3ForONSPDLookup of this. This is derived from
     * YM3.
     */
    private ONSPD_YM3 NearestYM3ForONSPDLookup;

    /**
     * Holds a reference to the original input data file from which this was
     * created.
     */
    private File InputFile;

    /**
     * Directory where this is stored.
     */
    private File Dir;

    /**
     * File for storing this.
     */
    private File File;

    /**
     * File for storing Data.
     */
    private File RecordsFile;

    /**
     * File for storing ClaimIDs of new SHBE claims.
     */
    private File ClaimIDsOfNewSHBEClaimsFile;

    /**
     * File for storing ClaimIDs of new SHBE claims where Claimant was a
     * Claimant before.
     */
    private File ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;

    /**
     * File for storing ClaimIDs of new SHBE claims where Claimant was a Partner
     * before.
     */
    private File ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;

    /**
     * File for storing ClaimIDs of new SHBE claims where Claimant was a
     * NonDependent before.
     */
    private File ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;

    /**
     * File for storing ClaimIDs of new SHBE claims where Claimant is new.
     */
    private File ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile;

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
     * File for storing Cottingley Springs Caravan Park paired ClaimIDs.
     */
    private File CottingleySpringsCaravanParkPairedClaimIDsFile;

    /**
     * File for storing ClaimIDs with status of HB at extract date InPayment.
     */
    private File ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile;

    /**
     * File for storing ClaimIDs with status of HB at extract date Suspended.
     */
    private File ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile;

    /**
     * File for storing ClaimIDs with status of HB at extract date Other.
     */
    private File ClaimIDsWithStatusOfHBAtExtractDateOtherFile;

    /**
     * File for storing ClaimIDs with status of CTB at extract date InPayment.
     */
    private File ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile;

    /**
     * File for storing ClaimIDs with status of CTB at extract date Suspended.
     */
    private File ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile;

    /**
     * File for storing ClaimIDs with status of CTB at extract date Other.
     */
    private File ClaimIDsWithStatusOfCTBAtExtractDateOtherFile;

    /**
     * File for storing SRecordsWithoutDRecords.
     */
    private File SRecordsWithoutDRecordsFile;

    /**
     * File for storing ClaimIDs and count of records with SRecords.
     */
    private File ClaimIDAndCountOfRecordsWithSRecordsFile;

    /**
     * For storing the ClaimID of Records without a mappable Claimant Postcode.
     */
    private HashSet<SHBE_ID> ClaimIDsOfClaimsWithoutAMappableClaimantPostcode;

    /**
     * File for storing ClaimIDs of claims without a mappable claimant postcode.
     */
    private File ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile;

    /**
     * ClaimIDs mapped to PersonIDs of Claimants.
     */
    private HashMap<SHBE_ID, SHBE_PersonID> ClaimIDToClaimantPersonIDLookup;

    /**
     * ClaimIDs mapped to PersonIDs of Partners. If there is no main Partner for
     * the claim then there is no mapping.
     */
    private HashMap<SHBE_ID, SHBE_PersonID> ClaimIDToPartnerPersonIDLookup;

    /**
     * ClaimIDs mapped to {@code HashSet<SHBE_PersonID>} of Dependents. If there
     * are no Dependents for the claim then there is no mapping.
     */
    private HashMap<SHBE_ID, HashSet<SHBE_PersonID>> ClaimIDToDependentPersonIDsLookup;

    /**
     * ClaimIDs mapped to {@code HashSet<SHBE_PersonID>} of NonDependents. If
     * there are no NonDependents for the claim then there is no mapping.
     */
    private HashMap<SHBE_ID, HashSet<SHBE_PersonID>> ClaimIDToNonDependentPersonIDsLookup;

    /**
     * ClaimIDs of Claims with Claimants that are Claimants in another claim.
     */
    private HashSet<SHBE_ID> ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Claimants that are Partners in another claim.
     */
    private HashSet<SHBE_ID> ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Claimants in another claim.
     */
    private HashSet<SHBE_ID> ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim;

    /**
     * ClaimIDs of Claims with Partners that are Partners in multiple claims.
     */
    private HashSet<SHBE_ID> ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim;

    /**
     * ClaimIDs of Claims with NonDependents that are Claimants or Partners in
     * another claim.
     */
    private HashSet<SHBE_ID> ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim;

    /**
     * SHBE_PersonIDs of Claimants that are in multiple claims in a month mapped
     * to a set of ClaimIDs of those claims.
     */
    private HashMap<SHBE_PersonID, HashSet<SHBE_ID>> ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * SHBE_PersonIDs of Partners that are in multiple claims in a month mapped to
     * a set of ClaimIDs of those claims.
     */
    private HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * SHBE_PersonIDs of NonDependents that are in multiple claims in a month
     * mapped to a set of ClaimIDs of those claims.
     */
    private HashMap<SHBE_PersonID, HashSet<SHBE_ID>> NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;

    /**
     * ClaimIDs mapped to Postcode SHBE_IDs.
     */
    private HashMap<SHBE_ID, ONSPD_ID> ClaimIDToPostcodeIDLookup;

    /**
     * ClaimIDs of the claims that have had PostcodeF updated from the future.
     * This is only to be stored if the postcode was previously of an invalid
     * format.
     */
    private HashSet<SHBE_ID> ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture;

    /**
     * ClaimIDs. This is only used when reading the data to check that ClaimIDs
     * are unique.
     */
    private HashSet<SHBE_ID> ClaimIDs;

    /**
     * For storing ClaimIDs of new SHBE claims.
     */
    private HashSet<SHBE_ID> ClaimIDsOfNewSHBEClaims;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Claimant
     * before.
     */
    private HashSet<SHBE_ID> ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a Partner
     * before.
     */
    private HashSet<SHBE_ID> ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant was a NonDependent
     * before.
     */
    private HashSet<SHBE_ID> ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;

    /**
     * For storing ClaimIDs of new SHBE claims where Claimant is new.
     */
    private HashSet<SHBE_ID> ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew;

    /**
     * ClaimIDs mapped to TenancyType.
     */
    private HashMap<SHBE_ID, Integer> ClaimIDToTenancyTypeLookup;

    /**
     * LoadSummary
     */
    private HashMap<String, Number> LoadSummary;

    /**
     * The line numbers of records that for some reason could not be loaded.
     */
    private ArrayList<Long> RecordIDsNotLoaded;

    /**
     * For storing ClaimIDs of all Claims where Claimant National Insurance
     * Number is invalid.
     */
    private HashSet<SHBE_ID> ClaimIDsOfInvalidClaimantNINOClaims;

    /**
     * // * For storing ClaimID mapped to Claim Postcodes that are not
     * (currently) mappable.
     */
    private HashMap<SHBE_ID, String> ClaimantPostcodesUnmappable;

    /**
     * For storing ClaimID mapped to Claim Postcodes that have been
     * automatically modified to make them mappable.
     */
    private HashMap<SHBE_ID, String[]> ClaimantPostcodesModified;

    /**
     * For storing ClaimID mapped to Claimant Postcodes Checked by local
     * authority to be mappable, but not found in the subsequent or the latest
     * ONSPD.
     */
    private HashMap<SHBE_ID, String> ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes;

    /**
     * ClaimIDToClaimantPersonIDLookupFile File.
     */
    private File ClaimIDToClaimantPersonIDLookupFile;

    /**
     * ClaimIDToPartnerPersonIDLookup File.
     */
    private File ClaimIDToPartnerPersonIDLookupFile;

    /**
     * ClaimIDToDependentPersonIDsLookupFile File.
     */
    private File ClaimIDToDependentPersonIDsLookupFile;

    /**
     * ClaimIDToNonDependentPersonIDsLookupFile File.
     */
    private File ClaimIDToNonDependentPersonIDsLookupFile;

    /**
     * ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile File.
     */
    private File ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;

    /**
     * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile File.
     */
    private File ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;

    /**
     * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile File.
     */
    private File ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;

    /**
     * ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile File.
     */
    private File ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;

    /**
     * ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile File.
     */
    private File ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;

    /**
     * ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile File.
     */
    private File ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile File.
     */
    private File PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile File.
     */
    private File NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;

    /**
     * ClaimIDToPostcodeIDLookupFile File.
     */
    private File ClaimIDToPostcodeIDLookupFile;

    /**
     * ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile File.
     */
    private File ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;

    /**
     * ClaimIDToTenancyTypeLookupFile File.
     */
    private File ClaimIDToTenancyTypeLookupFile;

    /**
     * LoadSummary File.
     */
    private File LoadSummaryFile;

    /**
     * RecordIDsNotLoaded File.
     */
    private File RecordIDsNotLoadedFile;

    /**
     * ClaimIDsOfInvalidClaimantNINOClaimsFile File.
     */
    private File ClaimIDsOfInvalidClaimantNINOClaimsFile;

    /**
     * ClaimantPostcodesUnmappableFile File.
     */
    private File ClaimantPostcodesUnmappableFile;

    /**
     * ClaimantPostcodesModifiedFile File.
     */
    private File ClaimantPostcodesModifiedFile;

    /**
     * ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile File.
     */
    private File ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile;

    /**
     * If not initialised, initialises Records then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, SHBE_Record> getClaimIDToSHBE_RecordMap(boolean handleOutOfMemoryError) {
        try {
            return getRecords();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDToSHBE_RecordMap(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises Records then returns it.
     *
     * @return
     */
    public HashMap<SHBE_ID, SHBE_Record> getRecords() {
        if (Records == null) {
            File f;
            f = getRecordsFile();
            if (f.exists()) {
                Records = (HashMap<SHBE_ID, SHBE_Record>) Generic_IO.readObject(f);
            } else {
                Records = new HashMap<>();
            }
        }
        return Records;
    }

    /**
     * If not initialised, initialises ClaimIDsOfNewSHBEClaims then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaims(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfNewSHBEClaims();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfNewSHBEClaims(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises ClaimIDsOfNewSHBEClaims then returns it.
     *
     * @return
     */
    protected HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaims() {
        if (ClaimIDsOfNewSHBEClaims == null) {
            File f;
            f = getClaimIDsOfNewSHBEClaimsFile();
            if (f.exists()) {
                ClaimIDsOfNewSHBEClaims = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaims = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaims;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore then returns it.
     *
     * @return
     */
    protected HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore == null) {
            File f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile();
            if (f.exists()) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore then returns it.
     *
     * @return
     */
    protected HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore == null) {
            File f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile();
            if (f.exists()) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore;
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore then returns
     * it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore then returns
     * it.
     *
     * @return
     */
    protected HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore == null) {
            File f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile();
            if (f.exists()) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = new HashSet<>();
            }
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore;
    }

    /**
     * If not initialised, initialises ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew
     * then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsOfNewSHBEClaimsWhereClaimantIsNew() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew == null) {
            File f;
            f = getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile();
            if (f.exists()) {
                ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getCottingleySpringsCaravanParkPairedClaimIDs(boolean handleOutOfMemoryError) {
        try {
            return getCottingleySpringsCaravanParkPairedClaimIDs();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getCottingleySpringsCaravanParkPairedClaimIDs(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getCottingleySpringsCaravanParkPairedClaimIDs() {
        if (CottingleySpringsCaravanParkPairedClaimIDs == null) {
            File f;
            f = getCottingleySpringsCaravanParkPairedClaimIDsFile();
            if (f.exists()) {
                CottingleySpringsCaravanParkPairedClaimIDs = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsWithStatusOfHBAtExtractDateInPayment(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateInPayment();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsWithStatusOfHBAtExtractDateInPayment(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsWithStatusOfHBAtExtractDateInPayment() {
        if (ClaimIDsWithStatusOfHBAtExtractDateInPayment == null) {
            File f;
            f = getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile();
            if (f.exists()) {
                ClaimIDsWithStatusOfHBAtExtractDateInPayment = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsWithStatusOfHBAtExtractDateSuspended(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateSuspended();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsWithStatusOfHBAtExtractDateSuspended(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsWithStatusOfHBAtExtractDateSuspended() {
        if (ClaimIDsWithStatusOfHBAtExtractDateSuspended == null) {
            File f;
            f = getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile();
            if (f.exists()) {
                ClaimIDsWithStatusOfHBAtExtractDateSuspended = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsWithStatusOfHBAtExtractDateOther(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsWithStatusOfHBAtExtractDateOther();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsWithStatusOfHBAtExtractDateOther(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsWithStatusOfHBAtExtractDateOther() {
        if (ClaimIDsWithStatusOfHBAtExtractDateOther == null) {
            File f;
            f = getClaimIDsWithStatusOfHBAtExtractDateOtherFile();
            if (f.exists()) {
                ClaimIDsWithStatusOfHBAtExtractDateOther = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsWithStatusOfCTBAtExtractDateInPayment(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateInPayment();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsWithStatusOfCTBAtExtractDateInPayment(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsWithStatusOfCTBAtExtractDateInPayment() {
        if (ClaimIDsWithStatusOfCTBAtExtractDateInPayment == null) {
            File f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile();
            if (f.exists()) {
                ClaimIDsWithStatusOfCTBAtExtractDateInPayment = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsWithStatusOfCTBAtExtractDateSuspended(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateSuspended();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsWithStatusOfCTBAtExtractDateSuspended(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsWithStatusOfCTBAtExtractDateSuspended() {
        if (ClaimIDsWithStatusOfCTBAtExtractDateSuspended == null) {
            File f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile();
            if (f.exists()) {
                ClaimIDsWithStatusOfCTBAtExtractDateSuspended = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsWithStatusOfCTBAtExtractDateOther(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsWithStatusOfCTBAtExtractDateOther();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsWithStatusOfCTBAtExtractDateOther(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsWithStatusOfCTBAtExtractDateOther() {
        if (ClaimIDsWithStatusOfCTBAtExtractDateOther == null) {
            File f;
            f = getClaimIDsWithStatusOfCTBAtExtractDateOtherFile();
            if (f.exists()) {
                ClaimIDsWithStatusOfCTBAtExtractDateOther = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsWithStatusOfCTBAtExtractDateOther = new HashSet<>();
            }
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateOther;
    }

    /**
     * If not initialised, initialises SRecordsWithoutDRecords then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, ArrayList<SHBE_S_Record>> getSRecordsWithoutDRecords(boolean handleOutOfMemoryError) {
        try {
            return getSRecordsWithoutDRecords();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getSRecordsWithoutDRecords(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return the SRecordsWithoutDRecords
     */
    protected HashMap<SHBE_ID, ArrayList<SHBE_S_Record>> getSRecordsWithoutDRecords() {
        if (SRecordsWithoutDRecords == null) {
            File f;
            f = getSRecordsWithoutDRecordsFile();
            if (f.exists()) {
                SRecordsWithoutDRecords = (HashMap<SHBE_ID, ArrayList<SHBE_S_Record>>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, Integer> getClaimIDAndCountOfRecordsWithSRecords(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDAndCountOfRecordsWithSRecords();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDAndCountOfRecordsWithSRecords(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithoutAMappableClaimantPostcode then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfClaimsWithoutAValidClaimantPostcode(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfClaimsWithoutAMappableClaimantPostcode();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfClaimsWithoutAValidClaimantPostcode(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return the ClaimIDAndCountOfRecordsWithSRecords
     */
    protected HashMap<SHBE_ID, Integer> getClaimIDAndCountOfRecordsWithSRecords() {
        if (ClaimIDAndCountOfRecordsWithSRecords == null) {
            File f;
            f = getClaimIDAndCountOfRecordsWithSRecordsFile();
            if (f.exists()) {
                ClaimIDAndCountOfRecordsWithSRecords = (HashMap<SHBE_ID, Integer>) Generic_IO.readObject(f);
            } else {
                ClaimIDAndCountOfRecordsWithSRecords = new HashMap<>();
            }
        }
        return ClaimIDAndCountOfRecordsWithSRecords;
    }

    /**
     * @return the ClaimIDsOfClaimsWithoutAMappableClaimantPostcode
     */
    protected HashSet<SHBE_ID> getClaimIDsOfClaimsWithoutAMappableClaimantPostcode() {
        if (ClaimIDsOfClaimsWithoutAMappableClaimantPostcode == null) {
            File f;
            f = getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile();
            if (f.exists()) {
                ClaimIDsOfClaimsWithoutAMappableClaimantPostcode = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
            } else {
                ClaimIDsOfClaimsWithoutAMappableClaimantPostcode = new HashSet<>();
            }
        }
        return ClaimIDsOfClaimsWithoutAMappableClaimantPostcode;
    }

    /**
     * @return YM3
     */
    public ONSPD_YM3 getYM3() {
        return YM3;
    }

    /**
     * @return NearestYM3ForONSPDLookup
     */
    public ONSPD_YM3 getNearestYM3ForONSPDLookup() {
        return NearestYM3ForONSPDLookup;
    }

    /**
     * Write this to file.
     */
    public void write() {
        Generic_IO.writeObject(
                this,
                getFile());
    }

    /**
     * If Dir is null, it is initialised.
     *
     * @return Dir.
     */
    protected File getDir() {
        if (Dir == null) {
            Dir = new File(Env.Files.getGeneratedDataDir(), getYM3().toString());
            Dir.mkdirs();
        }
        return Dir;
    }

    /**
     * @param filename
     * @return The File in Dir given by filename.
     */
    public File getFile(String filename) {
        return new File(getDir(), filename);
    }

    public SHBE_Records() {
    }

    /**
     * For loading an existing collection.
     *
     * @param env
     * @param YM3
     */
    public SHBE_Records(SHBE_Environment env, ONSPD_YM3 YM3) {
        super(env);
        this.YM3 = YM3;
        NearestYM3ForONSPDLookup = Postcode_Handler.getNearestYM3ForONSPDLookup(YM3);
        env.logO("YM3 " + YM3, true);
        env.logO("NearestYM3ForONSPDLookup " + NearestYM3ForONSPDLookup, true);
        Strings = env.Strings;
        Records = getClaimIDToSHBE_RecordMap(env.HOOME);
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
     * @param inputFilename
     * @param inputDirectory
     * @param LatestYM3ForONSPDFormat
     * @param logDir
     */
    public SHBE_Records(
            SHBE_Environment env,
            File inputDirectory,
            String inputFilename,
            ONSPD_YM3 LatestYM3ForONSPDFormat,
            File logDir
    ) {
        super(env);
        SHBE_Handler handler = env.Handler;
        Postcode_Handler = env.getPostcode_Handler();
        SHBE_Data Data = env.Data;
        InputFile = new File(inputDirectory, inputFilename);
        YM3 = handler.getYM3(inputFilename);
        NearestYM3ForONSPDLookup = Postcode_Handler.getNearestYM3ForONSPDLookup(YM3);
        Strings = env.Strings;
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
        env.logO("----------------------", true);
        env.logO("Load " + YM3, true);
        env.logO("----------------------", true);
        env.logO("NearestYM3ForONSPDLookup " + NearestYM3ForONSPDLookup, true);
        env.logO("LatestYM3ForONSPDLookup " + LatestYM3ForONSPDFormat, true);
        if (!LatestYM3ForONSPDFormat.equals(NearestYM3ForONSPDLookup)) {
            env.logO("The " + LatestYM3ForONSPDFormat + " ONSPD may be used "
                    + "if the Claimant Postcode does not have a lookup in the "
                    + NearestYM3ForONSPDLookup + " ONSPD.", true);
        }
        /**
         * Check the postcodes against these to see if we should report them
         * again as unmappable.
         */
        SHBE_CorrectedPostcodes SHBE_CorrectedPostcodes;
        HashMap<String, ArrayList<String>> ClaimRefToOriginalPostcodes;
        HashMap<String, ArrayList<String>> ClaimRefToCorrectedPostcodes;
        HashSet<String> PostcodesCheckedAsMappable;
        //HashMap<String, HashSet<String>> UnmappableToMappablePostcodes;
        /**
         * Mapping of National Insurance Numbers to simple SHBE_IDs.
         */
        HashMap<String, SHBE_ID> NINOToNINOIDLookup;
        /**
         * Mapping of SHBE_IDs to National Insurance Numbers.
         */
        HashMap<SHBE_ID, String> NINOIDToNINOLookup;
        /**
         * Mapping of Dates of Birth to simple SHBE_IDs.
         */
        HashMap<String, SHBE_ID> DOBToDOBIDLookup;
        /**
         * Mapping of SHBE_IDs to Dates of Birth.
         */
        HashMap<SHBE_ID, String> DOBIDToDOBLookup;
        /**
         * Mapping of Unit Postcodes to simple SHBE_IDs.
         */
        HashMap<String, ONSPD_ID> PostcodeToPostcodeIDLookup;
        /**
         * Mapping of SHBE_ID to a Unit Postcode.
         */
        HashMap<ONSPD_ID, String> PostcodeIDToPostcodeLookup;
        /**
         * Mapping of SHBE_ID to a Unit Postcode.
         */
        HashMap<ONSPD_ID, ONSPD_Point> PostcodeIDToPointLookup;
        /**
         * Mapping of ClaimRef String to Claim SHBE_ID.
         */
        HashMap<String, SHBE_ID> ClaimRefToClaimIDLookup;
        /**
         * Mapping of Claim SHBE_ID to ClaimRef String.
         */
        HashMap<SHBE_ID, String> ClaimIDToClaimRefLookup;

        /**
         * SHBE_PersonID of All Claimants
         */
        HashSet<SHBE_PersonID> AllClaimantPersonIDs;

        /**
         * SHBE_PersonID of All Partners
         */
        HashSet<SHBE_PersonID> AllPartnerPersonIDs;

        /**
         * SHBE_PersonID of All Non-Dependents
         */
        HashSet<SHBE_PersonID> AllNonDependentIDs;

        /**
         * All SHBE_PersonID to ClaimIDs Lookup
         */
        HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PersonIDToClaimIDsLookup;

        /**
         * Initialise mappings from SHBE_Data.
         */
        SHBE_CorrectedPostcodes = Data.getCorrectedPostcodes();
        ClaimRefToOriginalPostcodes = SHBE_CorrectedPostcodes.getClaimRefToOriginalPostcodes();
        ClaimRefToCorrectedPostcodes = SHBE_CorrectedPostcodes.getClaimRefToCorrectedPostcodes();
        PostcodesCheckedAsMappable = SHBE_CorrectedPostcodes.getPostcodesCheckedAsMappable();
        //UnmappableToMappablePostcodes = SHBE_CorrectedPostcodes.getUnmappableToMappablePostcodes();

        NINOToNINOIDLookup = Data.getNINOToNINOIDLookup();
        NINOIDToNINOLookup = Data.getNINOIDToNINOLookup();
        DOBToDOBIDLookup = Data.getDOBToDOBIDLookup();
        DOBIDToDOBLookup = Data.getDOBIDToDOBLookup();
        AllClaimantPersonIDs = Data.getClaimantPersonIDs();
        AllPartnerPersonIDs = Data.getPartnerPersonIDs();
        AllNonDependentIDs = Data.getNonDependentPersonIDs();
        PersonIDToClaimIDsLookup = Data.getPersonIDToClaimIDLookup();
        PostcodeToPostcodeIDLookup = Data.getPostcodeToPostcodeIDLookup();
        PostcodeIDToPostcodeLookup = Data.getPostcodeIDToPostcodeLookup();
        PostcodeIDToPointLookup = Data.getPostcodeIDToPointLookup(YM3);
        ClaimRefToClaimIDLookup = Data.getClaimRefToClaimIDLookup();
        ClaimIDToClaimRefLookup = Data.getClaimIDToClaimRefLookup();
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
        long totalIncome = 0;
        long grandTotalIncome = 0;
        int totalIncomeGreaterThanZeroCount = 0;
        long totalWeeklyEligibleRentAmount = 0;
        long grandTotalWeeklyEligibleRentAmount = 0;
        int totalWeeklyEligibleRentAmountGreaterThanZeroCount = 0;
        // Read data
        env.logO("<Read data>", true);
        try {
            BufferedReader br;
            br = Generic_IO.getBufferedReader(InputFile);
            StreamTokenizer st = new StreamTokenizer(br);
            Generic_IO.setStreamTokenizerSyntax5(st);
            st.wordChars('`', '`');
            st.wordChars('*', '*');
            String line = "";
            long RecordID = 0;
            int lineCount = 0;
            // Declare Variables
            SHBE_S_Record SRecord;
            String ClaimRef;

            SHBE_D_Record DRecord;
            int TenancyType;
            boolean doLoop;
            SHBE_ID ClaimID;
            SHBE_Record record;
            int StatusOfHBClaimAtExtractDate;
            int StatusOfCTBClaimAtExtractDate;
            String Postcode;
            String ClaimantNINO;
            String ClaimantDOB;
            SHBE_PersonID ClaimantPersonID;
            boolean addToNew;
            Object key;
            SHBE_ID otherClaimID = null;
            String otherClaimRef;
            SHBE_Record otherRecord;
            /**
             * There are two types of SHBE data encountered so far. Each has
             * slightly different field definitions.
             */
            int type;
            type = readAndCheckFirstLine(inputDirectory, inputFilename);
            Generic_IO.skipline(st);
            // Read collections
            int tokenType;
            tokenType = st.nextToken();
            int counter = 0;
            while (tokenType != StreamTokenizer.TT_EOF) {
                switch (tokenType) {
                    case StreamTokenizer.TT_EOL:
                        if (counter % 10000 == 0) {
                            //env.logO(line);
                            env.logO("Read line " + counter, true);
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
                                    env.logE("SRecord without a ClaimRef "
                                            + this.getClass().getName()
                                            + ".SHBE_Records(SHBE_Environment, File, String)");
                                    env.logE("SRecord: " + SRecord.toString());
                                    env.logE("Line: " + line);
                                    env.logE("RecordID " + RecordID);
                                    RecordIDsNotLoaded.add(RecordID);
                                    SRecordNotLoadedCount++;
                                } else {
                                    ClaimID = handler.getIDAddIfNeeded(
                                            ClaimRef,
                                            ClaimRefToClaimIDLookup,
                                            ClaimIDToClaimRefLookup);
                                    ArrayList<SHBE_S_Record> recs;
                                    recs = SRecordsWithoutDRecords.get(ClaimID);
                                    if (recs == null) {
                                        recs = new ArrayList<>();
                                        SRecordsWithoutDRecords.put(ClaimID, recs);
                                    }
                                    recs.add(SRecord);
                                }
                            } catch (Exception e) {
                                env.logE("Line not loaded in "
                                        + this.getClass().getName()
                                        + ".SHBE_Records(SHBE_Environment, File, String)");
                                env.logE("Line: " + line);
                                env.logE("RecordID " + RecordID);
                                env.logE(e.getLocalizedMessage());
                                env.logE(e);
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
                                    env.logE("Incomplete record "
                                            + this.getClass().getName()
                                            + ".SHBE_Records(SHBE_Environment, File, String)");
                                    env.logE("Line: " + line);
                                    env.logE("RecordID " + RecordID);
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
                                        ClaimID = handler.getIDAddIfNeeded(ClaimRef,
                                                ClaimRefToClaimIDLookup,
                                                ClaimIDToClaimRefLookup,
                                                ClaimIDs,
                                                ClaimIDsOfNewSHBEClaims);
                                        if (handler.isHBClaim(DRecord)) {
                                            if (DRecord.getCouncilTaxBenefitClaimReferenceNumber() != null) {
                                                totalCouncilTaxAndHousingBenefitClaims++;
                                            } else {
                                                totalHousingBenefitClaims++;
                                            }
                                        }
                                        if (handler.isCTBOnlyClaim(DRecord)) {
                                            totalCouncilTaxBenefitClaims++;
                                        }
                                        /**
                                         * Get or initialise SHBE_Record
                                         * record
                                         */
                                        record = Records.get(ClaimID);
                                        if (record == null) {
                                            record = new SHBE_Record(
                                                    env, YM3, ClaimID, DRecord);
                                            Records.put(ClaimID, record);
                                            doLoop = true;
                                        } else {
                                            env.logE("Two records have the same ClaimRef "
                                                    + this.getClass().getName()
                                                    + ".SHBE_Records(SHBE_Environment, File, String)");
                                            env.logE("Line: " + line);
                                            env.logE("RecordID " + RecordID);
                                            env.logE("ClaimRef " + ClaimRef);
                                        }
                                        StatusOfHBClaimAtExtractDate = DRecord.getStatusOfHBClaimAtExtractDate();
                                        /**
                                         * 0 = Other; 1 = InPayment; 2 =
                                         * Suspended.
                                         */
                                        switch (StatusOfHBClaimAtExtractDate) {
                                            case 0: {
                                                ClaimIDsWithStatusOfHBAtExtractDateOther.add(ClaimID);
                                                break;
                                            }
                                            case 1: {
                                                ClaimIDsWithStatusOfHBAtExtractDateInPayment.add(ClaimID);
                                                break;
                                            }
                                            case 2: {
                                                ClaimIDsWithStatusOfHBAtExtractDateSuspended.add(ClaimID);
                                                break;
                                            }
                                            default:
                                                env.logE("Unexpected StatusOfHBClaimAtExtractDate "
                                                        + this.getClass().getName()
                                                        + ".SHBE_Records(SHBE_Environment, File, String)");
                                                env.logE("Line: " + line);
                                                env.logE("RecordID " + RecordID);
                                                break;
                                        }
                                        StatusOfCTBClaimAtExtractDate = DRecord.getStatusOfCTBClaimAtExtractDate();
                                        /**
                                         * 0 = Other; 1 = InPayment; 2 =
                                         * Suspended.
                                         */
                                        switch (StatusOfCTBClaimAtExtractDate) {
                                            case 0: {
                                                ClaimIDsWithStatusOfCTBAtExtractDateOther.add(ClaimID);
                                                break;
                                            }
                                            case 1: {
                                                ClaimIDsWithStatusOfCTBAtExtractDateInPayment.add(ClaimID);
                                                break;
                                            }
                                            case 2: {
                                                ClaimIDsWithStatusOfCTBAtExtractDateSuspended.add(ClaimID);
                                                break;
                                            }
                                            default:
                                                env.logE("Unexpected StatusOfCTBClaimAtExtractDate "
                                                        + this.getClass().getName()
                                                        + ".SHBE_Records(SHBE_Environment, File, String)");
                                                env.logE("Line: " + line);
                                                env.logE("RecordID " + RecordID);
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
                                                    env.logO(env.DEBUG_Level_FINEST,
                                                            "Postcode " + Postcode + " is not in the " + NearestYM3ForONSPDLookup + " ONSPD, "
                                                            + "but is in the " + LatestYM3ForONSPDFormat + " ONSPD!");
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
                                                        ClaimantPostcodesModified.put(ClaimID, p);
                                                    }
                                                }
                                            }
                                            record.ClaimPostcodeFValidPostcodeFormat = Postcode_Handler.isValidPostcodeForm(record.ClaimPostcodeF);
                                            if (PostcodeToPostcodeIDLookup.containsKey(record.ClaimPostcodeF)) {
                                                CountOfMappableClaimantPostcodes++;
                                                record.ClaimPostcodeFMappable = true;
                                                record.PostcodeID = PostcodeToPostcodeIDLookup.get(record.ClaimPostcodeF);
                                                // Add the point to the lookup
                                                ONSPD_Point AGDT_Point;
                                                AGDT_Point = Postcode_Handler.getPointFromPostcodeNew(
                                                        NearestYM3ForONSPDLookup,
                                                        Strings.sUnit,
                                                        record.ClaimPostcodeF);
                                                PostcodeIDToPointLookup.put(record.PostcodeID, AGDT_Point);
                                            } else if (isMappablePostcode) {
                                                CountOfMappableClaimantPostcodes++;
                                                CountOfNewClaimantPostcodes++;
                                                CountOfNewMappableClaimantPostcodes++;
                                                record.ClaimPostcodeFMappable = true;
                                                record.PostcodeID = handler.getPostcodeIDAddIfNeeded(
                                                        record.ClaimPostcodeF,
                                                        PostcodeToPostcodeIDLookup,
                                                        PostcodeIDToPostcodeLookup);
                                                // Add the point to the lookup
                                                ONSPD_Point p;
                                                if (isMappablePostcodeLastestYM3) {
                                                    p = Postcode_Handler.getPointFromPostcodeNew(
                                                            LatestYM3ForONSPDFormat,
                                                            Strings.sUnit,
                                                            record.ClaimPostcodeF);
                                                } else {
                                                    p = Postcode_Handler.getPointFromPostcodeNew(
                                                            NearestYM3ForONSPDLookup,
                                                            Strings.sUnit,
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
                                                ClaimIDsOfClaimsWithoutAMappableClaimantPostcode.add(ClaimID);
                                                boolean PostcodeCheckedAsMappable = false;
                                                PostcodeCheckedAsMappable = PostcodesCheckedAsMappable.contains(record.ClaimPostcodeF);
                                                if (PostcodeCheckedAsMappable) {
                                                    ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes.put(ClaimID, Postcode);
                                                } else {
                                                    // Store unmappable claimant postcode.
                                                    ClaimantPostcodesUnmappable.put(ClaimID, Postcode);
                                                }
                                            }
                                            ClaimIDToPostcodeIDLookup.put(ClaimID, record.PostcodeID);
                                            ClaimIDToTenancyTypeLookup.put(ClaimID, TenancyType);
                                            totalIncome = handler.getClaimantsAndPartnersIncomeTotal(DRecord);
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
                                            ClaimIDsOfInvalidClaimantNINOClaims.add(ClaimID);
                                        }
                                        ClaimantDOB = DRecord.getClaimantsDateOfBirth();
                                        ClaimantPersonID = handler.getPersonID(
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
                                        if (ClaimIDsOfNewSHBEClaims.contains(ClaimID)) {
                                            addToNew = true;
                                            if (AllClaimantPersonIDs.contains(ClaimantPersonID)) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.add(ClaimID);
                                                addToNew = false;
                                            }
                                            if (AllPartnerPersonIDs.contains(ClaimantPersonID)) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.add(ClaimID);
                                                addToNew = false;
                                            }
                                            if (AllNonDependentIDs.contains(ClaimantPersonID)) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.add(ClaimID);
                                                addToNew = false;
                                            }
                                            if (addToNew) {
                                                ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew.add(ClaimID);
                                            }
                                        }
                                        /**
                                         * If ClaimantSHBE_PersonID is already in
                                         * ClaimIDToClaimantPersonIDLookup. then
                                         * ClaimantSHBE_PersonID has multiple
                                         * claims in a month.
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
                                            key = Generic_Collections.getKeyFromValue(ClaimIDToClaimantPersonIDLookup,
                                                    ClaimantPersonID);
                                            Postcode = DRecord.getClaimantsPostcode();
                                            if (key != null) {
                                                otherClaimID = (SHBE_ID) key;
                                                otherClaimRef = ClaimIDToClaimRefLookup.get(otherClaimID);
                                                // Treat those paired records for Cottingley Springs Caravan Park differently
                                                if (Postcode.equalsIgnoreCase(Strings.CottingleySpringsCaravanParkPostcode)) {
//                                                    Env.logO("Cottingley Springs Caravan Park "
//                                                            + SHBE_Strings.CottingleySpringsCaravanParkPostcode
//                                                            + " ClaimRef " + ClaimRef + " paired with " + otherClaimRef
//                                                            + " one claim is for the pitch, the other is for rent of "
//                                                            + "a mobile home. ");
                                                    CottingleySpringsCaravanParkPairedClaimIDs.add(ClaimID);
                                                    CottingleySpringsCaravanParkPairedClaimIDs.add(otherClaimID);
                                                } else {
//                                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    Env.logO(
//                                                            "Claimant with NINO " + ClaimantNINO
//                                                            + " DoB " + ClaimantDOB
//                                                            + " has mulitple claims. "
//                                                            + "The Claimant has had a second claim set up and the "
//                                                            + "previous claim is still on the system for some reason.");
//                                                    Env.logO("Current ClaimRef " + ClaimRef);
//                                                    Env.logO("Other ClaimRef " + otherClaimRef);
                                                    otherRecord = Records.get(otherClaimID);
                                                    if (otherRecord == null) {
                                                        env.logE("Unexpected error xx: This should not happen. "
                                                                + this.getClass().getName()
                                                                + ".SHBE_Records(SHBE_Environment, File, String)");
                                                    } else {
//                                                        Env.logO("This D Record");
//                                                        Env.logO(DRecord.toStringBrief());
//                                                        Env.logO("Other D Record");
//                                                        Env.logO(otherRecord.DRecord.toStringBrief());
                                                        /**
                                                         * Add to
                                                         * ClaimantsWithMultipleClaimsInAMonth.
                                                         */
                                                        ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(ClaimID);
                                                        ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.add(otherRecord.getClaimID());
                                                        HashSet<SHBE_ID> set;
                                                        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(ClaimantPersonID)) {
                                                            set = ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(ClaimantPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(ClaimantPersonID, set);
                                                        }
                                                        set.add(ClaimID);
                                                        set.add(otherClaimID);
                                                    }
//                                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
                                            if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(ClaimID)) {
                                                /**
                                                 * If Claimant is a Partner in
                                                 * another claim add to
                                                 * ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim
                                                 * and
                                                 * ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.
                                                 */
                                                key = Generic_Collections.getKeyFromValue(ClaimIDToPartnerPersonIDLookup,
                                                        ClaimantPersonID);
                                                if (key != null) {
                                                    otherClaimID = (SHBE_ID) key;
                                                    HashSet<SHBE_ID> set;
                                                    ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(otherClaimID);
                                                }
                                                ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(ClaimID);
//                                                Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                Env.logO("Claimant with NINO " + ClaimantNINO
//                                                        + " DOB " + ClaimantDOB
//                                                        + " in ClaimRef " + ClaimRef
//                                                        + " is a Partner in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                            }
                                        }
                                        SHBE_PersonID PartnerPersonID;
                                        PartnerPersonID = null;
                                        if (DRecord.getPartnerFlag() > 0) {
                                            /**
                                             * Add Partner.
                                             */
                                            PartnerPersonID = handler.getPersonID(
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
                                                if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(ClaimID)) {
                                                    key = Generic_Collections.getKeyFromValue(ClaimIDToPartnerPersonIDLookup,
                                                            PartnerPersonID);
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ID) key;
                                                        HashSet<SHBE_ID> set;
                                                        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(ClaimID);
                                                        set.add(otherClaimID);
                                                        ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.add(ClaimID);
//                                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    Env.logO("Partner with NINO " + NINOIDToNINOLookup.get(PartnerPersonID.getNINO_ID())
//                                                            + " DOB " + DOBIDToDOBLookup.get(PartnerPersonID.getDOB_ID())
//                                                            + " in ClaimRef " + ClaimRef
//                                                            + " is a Partner in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
                                                if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(ClaimID)) {
                                                    key = Generic_Collections.getKeyFromValue(ClaimIDToClaimantPersonIDLookup,
                                                            PartnerPersonID);
                                                    if (key != null) {
                                                        otherClaimID = (SHBE_ID) key;

                                                        HashSet<SHBE_ID> set;
                                                        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(ClaimID);
                                                        set.add(otherClaimID);
                                                        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.containsKey(PartnerPersonID)) {
                                                            set = ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PartnerPersonID);
                                                        } else {
                                                            set = new HashSet<>();
                                                            ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(PartnerPersonID, set);
                                                        }
                                                        set.add(ClaimID);
                                                        set.add(otherClaimID);
                                                        ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.add(otherClaimID);
                                                    }
                                                    ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.add(ClaimID);
//                                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                                    Env.logO("Partner with NINO " + NINOIDToNINOLookup.get(PartnerPersonID.getNINO_ID())
//                                                            + " DOB " + DOBIDToDOBLookup.get(PartnerPersonID.getDOB_ID())
//                                                            + " in ClaimRef " + ClaimRef
//                                                            + " is a Claimant in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                }
                                                ClaimIDToPartnerPersonIDLookup.put(ClaimID, PartnerPersonID);
                                            }
                                        }
                                        /**
                                         * Add to
                                         * ClaimIDToClaimantPersonIDLookup.
                                         */
                                        ClaimIDToClaimantPersonIDLookup.put(ClaimID, ClaimantPersonID);

                                        /**
                                         * Add to AllClaimantPersonIDs and
                                         * AllPartnerPersonIDs.
                                         */
                                        AllClaimantPersonIDs.add(ClaimantPersonID);
                                        ClaimantPersonIDs.add(ClaimantPersonID);
                                        addToPersonIDToClaimRefsLookup(
                                                ClaimID,
                                                ClaimantPersonID,
                                                PersonIDToClaimIDsLookup);
                                        if (PartnerPersonID != null) {
                                            AllPartnerPersonIDs.add(PartnerPersonID);
                                            PartnerPersonIDs.add(PartnerPersonID);
                                            ClaimIDToPartnerPersonIDLookup.put(ClaimID, PartnerPersonID);
                                            addToPersonIDToClaimRefsLookup(
                                                    ClaimID,
                                                    PartnerPersonID,
                                                    PersonIDToClaimIDsLookup);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                env.logE(line);
                                env.logE("RecordID " + RecordID);
                                env.logE(e.getLocalizedMessage());
                                env.logE(e);
                                RecordIDsNotLoaded.add(RecordID);
                            }
                        }
                        lineCount++;
                        RecordID++;
                        break;
                }
                tokenType = st.nextToken();
            }
            env.logO("</Read data>", true);

            br.close();

            /**
             * Add SRecords to Records. Add ClaimantSHBE_IDs from SRecords.
             */
            SHBE_ID SHBE_ID;
            SHBE_Record SHBE_Record;
            Iterator<SHBE_ID> ite;
            env.logO("<Add SRecords>", true);
            ite = Records.keySet().iterator();
            while (ite.hasNext()) {
                SHBE_ID = ite.next();
                SHBE_Record = Records.get(SHBE_ID);
                initSRecords(
                        handler,
                        SHBE_Record,
                        NINOToNINOIDLookup,
                        NINOIDToNINOLookup,
                        DOBToDOBIDLookup,
                        DOBIDToDOBLookup,
                        AllNonDependentIDs,
                        PersonIDToClaimIDsLookup,
                        ClaimIDToClaimRefLookup);
            }
            env.logO("</Add SRecords>", true);

            env.logO("<Summary Statistics>", true);
            /**
             * Add statistics to LoadSummary.
             */
            /**
             * Statistics on New SHBE Claims
             */
            addLoadSummaryCount(Strings.sCountOfNewSHBEClaims,
                    ClaimIDsOfNewSHBEClaims.size());
            addLoadSummaryCount(Strings.sCountOfNewSHBEClaimsWhereClaimantWasClaimantBefore,
                    ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore.size());
            addLoadSummaryCount(Strings.sCountOfNewSHBEClaimsWhereClaimantWasPartnerBefore,
                    ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore.size());
            addLoadSummaryCount(Strings.sCountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore,
                    ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore.size());
            addLoadSummaryCount(Strings.sCountOfNewSHBEClaimsWhereClaimantIsNew,
                    ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew.size());
            /**
             * Statistics on Postcodes
             */
            addLoadSummaryCount(Strings.sCountOfNewClaimantPostcodes,
                    CountOfNewClaimantPostcodes);
            addLoadSummaryCount(Strings.sCountOfNewValidMappableClaimantPostcodes,
                    CountOfNewMappableClaimantPostcodes);
            addLoadSummaryCount(Strings.sCountOfMappableClaimantPostcodes,
                    CountOfMappableClaimantPostcodes);
            addLoadSummaryCount(
                    Strings.sCountOfNonMappableClaimantPostcodes,
                    CountOfNonMappableClaimantPostcodes);
            addLoadSummaryCount(
                    Strings.sCountOfInvalidFormatClaimantPostcodes,
                    CountOfValidFormatClaimantPostcodes);
            /**
             * General count statistics
             */
            addLoadSummaryCount(Strings.sCountOfClaims, Records.size());
            addLoadSummaryCount(                    Strings.sCountOfCTBClaims,
                    totalCouncilTaxBenefitClaims);
            addLoadSummaryCount(
                    Strings.sCountOfCTBAndHBClaims,
                    totalCouncilTaxAndHousingBenefitClaims);
            addLoadSummaryCount(
                    Strings.sCountOfHBClaims,
                    totalHousingBenefitClaims);
            addLoadSummaryCount(
                    Strings.sCountOfRecords,
                    Records.size());
            addLoadSummaryCount(
                    Strings.sCountOfSRecords,
                    countSRecords);
            addLoadSummaryCount(
                    Strings.sCountOfSRecordsNotLoaded,
                    SRecordNotLoadedCount);
            addLoadSummaryCount(
                    Strings.sCountOfIncompleteDRecords,
                    NumberOfIncompleteDRecords);
            addLoadSummaryCount(
                    Strings.sCountOfRecordIDsNotLoaded,
                    RecordIDsNotLoaded.size());
            HashSet<SHBE_PersonID> set;
            HashSet<SHBE_PersonID> allSet;
            allSet = new HashSet<>();
            /**
             * Claimants
             */
            set = new HashSet<>();
            set.addAll(ClaimIDToClaimantPersonIDLookup.values());
            allSet.addAll(set);
            addLoadSummaryCount(Strings.sCountOfUniqueClaimants, set.size());
            /**
             * Partners
             */
            addLoadSummaryCount(Strings.sCountOfClaimsWithPartners,
                    ClaimIDToPartnerPersonIDLookup.size());
            set = handler.getUniquePersonIDs0(ClaimIDToPartnerPersonIDLookup);
            allSet.addAll(set);
            addLoadSummaryCount(Strings.sCountOfUniquePartners, set.size());
            /**
             * Dependents
             */
            int nDependents;
            nDependents = SHBE_Collections.getCountHashMap_SHBE_ID__HashSet_SHBE_PersonID(ClaimIDToDependentPersonIDsLookup);
            addLoadSummaryCount(
                    Strings.sCountOfDependentsInAllClaims,
                    nDependents);
            set = handler.getUniquePersonIDs(ClaimIDToDependentPersonIDsLookup);
            allSet.addAll(set);
            int CountOfUniqueDependents = set.size();
            addLoadSummaryCount(
                    Strings.sCountOfUniqueDependents,
                    CountOfUniqueDependents);
            /**
             * NonDependents
             */
            int nNonDependents;
            nNonDependents = SHBE_Collections.getCountHashMap_SHBE_ID__HashSet_SHBE_PersonID(ClaimIDToNonDependentPersonIDsLookup);
            addLoadSummaryCount(
                    Strings.sCountOfNonDependentsInAllClaims,
                    nNonDependents);
            set = handler.getUniquePersonIDs(ClaimIDToNonDependentPersonIDsLookup);
            allSet.addAll(set);
            int CountOfUniqueNonDependents = set.size();
            addLoadSummaryCount(
                    Strings.sCountOfUniqueNonDependents,
                    CountOfUniqueNonDependents);
            /**
             * All individuals
             */
            addLoadSummaryCount(Strings.sCountOfIndividuals, allSet.size());
            /**
             * Counts of: ClaimsWithClaimantsThatAreClaimantsInAnotherClaim
             * ClaimsWithClaimantsThatArePartnersInAnotherClaim
             * ClaimsWithPartnersThatAreClaimantsInAnotherClaim
             * ClaimsWithPartnersThatArePartnersInAnotherClaim
             * ClaimantsInMultipleClaimsInAMonth
             * PartnersInMultipleClaimsInAMonth
             * NonDependentsInMultipleClaimsInAMonth
             */
            addLoadSummaryCount(Strings.sCountOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim,
                    ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.size());
            addLoadSummaryCount(Strings.sCountOfClaimsWithClaimantsThatArePartnersInAnotherClaim,
                    ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim.size());
            addLoadSummaryCount(Strings.sCountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim,
                    ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim.size());
            addLoadSummaryCount(Strings.sCountOfClaimsWithPartnersThatArePartnersInAnotherClaim,
                    ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim.size());
            addLoadSummaryCount(Strings.sCountOfClaimantsInMultipleClaimsInAMonth,
                    ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
            addLoadSummaryCount(Strings.sCountOfPartnersInMultipleClaimsInAMonth,
                    PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
            addLoadSummaryCount(Strings.sCountOfNonDependentsInMultipleClaimsInAMonth,
                    NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.size());
            addLoadSummaryCount(Strings.sLineCount, lineCount);
            addLoadSummaryCount(Strings.sTotalIncome, grandTotalIncome);
            addLoadSummaryCount(Strings.sTotalIncomeGreaterThanZeroCount,
                    totalIncomeGreaterThanZeroCount);
            addLoadSummaryCount(Strings.sAverage_NonZero_Income,
                    grandTotalIncome / (double) totalIncomeGreaterThanZeroCount);
            addLoadSummaryCount(Strings.sTotalWeeklyEligibleRentAmount,
                    grandTotalWeeklyEligibleRentAmount);
            addLoadSummaryCount(Strings.sTotalWeeklyEligibleRentAmountGreaterThanZeroCount,
                    totalWeeklyEligibleRentAmountGreaterThanZeroCount);
            addLoadSummaryCount(Strings.sAverage_NonZero_WeeklyEligibleRentAmount,
                    grandTotalWeeklyEligibleRentAmount / (double) totalWeeklyEligibleRentAmountGreaterThanZeroCount);
            env.logO("<Summary Statistics>", true);

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
            YMN = handler.getYearMonthNumber(inputFilename);
            writeOut(ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                    logDir,
                    "ClaimantsInMultipleClaimsInAMonth",
                    YMN,
                    ClaimIDToClaimRefLookup,
                    NINOIDToNINOLookup,
                    DOBIDToDOBLookup);
            // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
            writeOut(PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                    logDir,
                    "PartnersInMultipleClaimsInAMonth",
                    YMN,
                    ClaimIDToClaimRefLookup,
                    NINOIDToNINOLookup,
                    DOBIDToDOBLookup);
            // Write out ClaimRefs of PartnersInMultipleClaimsInAMonth
            writeOut(NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
                    logDir,
                    "NonDependentsInMultipleClaimsInAMonth",
                    YMN,
                    ClaimIDToClaimRefLookup,
                    NINOIDToNINOLookup,
                    DOBIDToDOBLookup);
            // Write out ClaimRefs of ClaimIDOfInvalidClaimantNINOClaims
            String name = "ClaimRefsOfInvalidClaimantNINOClaims";
            File dir;
            dir = new File(
                    logDir,
                    name);
            dir.mkdirs();
            File f = new File(
                    dir,
                    name + YMN + ".csv");
            PrintWriter pw;
            pw = null;
            try {
                pw = new PrintWriter(f);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SHBE_Records.class.getName()).log(Level.SEVERE, null, ex);
            }
            pw.println("ClaimRefs");
            Iterator<SHBE_ID> ite2;
            ite2 = ClaimIDsOfInvalidClaimantNINOClaims.iterator();
            while (ite2.hasNext()) {
                SHBE_ID = ite2.next();
                pw.println(ClaimIDToClaimRefLookup.get(SHBE_ID));
            }
            pw.close();
            env.logO("----------------------", true);
            env.logO("Loaded " + YM3, true);
            env.logO("----------------------", true);
        } catch (IOException ex) {
            Logger.getLogger(SHBE_Handler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeOut(
            HashMap<SHBE_PersonID, HashSet<SHBE_ID>> InMultipleClaimsInAMonthPersonIDToClaimIDsLookup,
            File logDir,
            String name,
            String YMN,
            HashMap<SHBE_ID, String> ClaimIDToClaimRefLookup,
            HashMap<SHBE_ID, String> NINOIDToNINOLookup,
            HashMap<SHBE_ID, String> DOBIDToDOBLookup) {
        File dir;
        PrintWriter pw = null;
        HashSet<SHBE_ID> ClaimRefs;
        Iterator<SHBE_PersonID> ite2;
        Iterator<SHBE_ID> ite3;
        File f;
        String s;
        SHBE_ID SHBE_ID;
        SHBE_PersonID PersonID;
        String NINO;
        String DOB;
        dir = new File(
                logDir,
                name);
        dir.mkdirs();
        f = new File(
                dir,
                name + YMN + ".csv");
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SHBE_Records.class.getName()).log(Level.SEVERE, null, ex);
        }
        pw.println("NINO,DOB,ClaimRefs");
        ite2 = InMultipleClaimsInAMonthPersonIDToClaimIDsLookup.keySet().iterator();
        while (ite2.hasNext()) {
            PersonID = ite2.next();
            NINO = NINOIDToNINOLookup.get(PersonID.getNINO_ID());
            DOB = DOBIDToDOBLookup.get(PersonID.getDOB_ID());
            if (!NINO.trim().equalsIgnoreCase("")) {
                if (!NINO.trim().startsWith("XX999")) {
                    ClaimRefs = InMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(PersonID);
                    ite3 = ClaimRefs.iterator();
                    s = NINO + "," + DOB;
                    while (ite3.hasNext()) {
                        SHBE_ID = ite3.next();
                        s += "," + ClaimIDToClaimRefLookup.get(SHBE_ID);
                    }
                    pw.println(s);
                }
            }
        }
        pw.close();
    }

    /**
     * logs and adds s and n to LoadSummary.
     *
     * @param s
     * @param n
     */
    public final void addLoadSummaryCount(String s, Number n) {
        Env.logO(s + " " + n, true);
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
    public final void initSRecords(
            SHBE_Handler SHBE_Handler,
            SHBE_Record SHBE_Record,
            HashMap<String, SHBE_ID> NINOToNINOIDLookup,
            HashMap<SHBE_ID, String> NINOIDToNINOLookup,
            HashMap<String, SHBE_ID> DOBToDOBIDLookup,
            HashMap<SHBE_ID, String> DOBIDToDOBLookup,
            HashSet<SHBE_PersonID> AllNonDependentPersonIDs,
            HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PersonIDToClaimRefsLookup,
            HashMap<SHBE_ID, String> ClaimIDToClaimRefLookup
    ) {
        ArrayList<SHBE_S_Record> SRecordsForClaim;
        SHBE_ID ClaimID;
        ClaimID = SHBE_Record.ClaimID;
        Iterator<SHBE_S_Record> ite;
        SHBE_S_Record SRecord;
        String ClaimantsNINO;
        SRecordsForClaim = getSRecordsWithoutDRecords().get(ClaimID);
        if (SRecordsForClaim != null) {
            // Declare variables
            SHBE_PersonID SHBE_PersonID;
            String NINO;
            String DOB;
            int SubRecordType;
            Object key;
            SHBE_ID otherClaimID;
            ite = SRecordsForClaim.iterator();
            while (ite.hasNext()) {
                SRecord = ite.next();
                NINO = SRecord.getSubRecordChildReferenceNumberOrNINO();
                DOB = SRecord.getSubRecordDateOfBirth();
                SubRecordType = SRecord.getSubRecordType();
                switch (SubRecordType) {
                    case 1:
                        ClaimantsNINO = SRecord.getClaimantsNationalInsuranceNumber();
                        if (ClaimantsNINO.trim().isEmpty()) {
                            ClaimantsNINO = Strings.sDefaultNINO;
                            Env.logE("ClaimantsNINO is empty for "
                                    + "ClaimID " + ClaimID + " ClaimRef "
                                    + Env.Data.getClaimIDToClaimRefLookup().get(ClaimID)
                                    + " Setting as default NINO " + ClaimantsNINO);
                        }
                        int i;
                        i = 0;
                        if (NINO.isEmpty()) {
                            boolean set;
                            set = false;
                            while (!set) {
                                NINO = "" + i;
                                NINO += "_" + ClaimantsNINO;
                                if (NINOToNINOIDLookup.containsKey(NINO)) {
                                    Env.logO(Env.DEBUG_Level_FINEST,
                                            "NINO " + NINO + " is not unique for " + ClaimantsNINO);
                                } else {
                                    set = true;
                                }
                                i++;
                            }
                        } else {
                            boolean set;
                            set = false;
                            NINO += "_" + ClaimantsNINO;
                            if (NINOToNINOIDLookup.containsKey(NINO)) {
                                /**
                                 * If the claimant has more than one claim, this
                                 * is fine. Otherwise we have to do something.
                                 */
                                if (ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim.contains(ClaimID)) {
                                    set = true;
                                } else {
                                    Env.logO(Env.DEBUG_Level_FINEST,
                                            "NINO " + NINO + " is not unique for " + ClaimantsNINO
                                            + " and ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim does not contain "
                                            + "ClaimID " + ClaimID + " for ClaimRef "
                                            + Env.Data.getClaimIDToClaimRefLookup().get(ClaimID));
                                }
                            } else {
                                set = true;
                            }
                            while (!set) {
                                NINO = "" + i;
                                NINO += "_" + ClaimantsNINO;
                                if (NINOToNINOIDLookup.containsKey(NINO)) {
                                    Env.logO(Env.DEBUG_Level_FINEST,
                                            "NINO " + NINO + " is not unique for " + ClaimantsNINO);
                                } else {
                                    set = true;
                                }
                                i++;
                            }
                        }
                        SHBE_PersonID = SHBE_Handler.getPersonID(
                                NINO,
                                DOB,
                                NINOToNINOIDLookup,
                                NINOIDToNINOLookup,
                                DOBToDOBIDLookup,
                                DOBIDToDOBLookup);
                        /**
                         * Add to ClaimIDToDependentPersonIDsLookup.
                         */
                        HashSet<SHBE_PersonID> s;
                        s = ClaimIDToDependentPersonIDsLookup.get(ClaimID);
                        if (s == null) {
                            s = new HashSet<>();
                            ClaimIDToDependentPersonIDsLookup.put(ClaimID, s);
                        }
                        s.add(SHBE_PersonID);
                        addToPersonIDToClaimRefsLookup(
                                ClaimID,
                                SHBE_PersonID,
                                PersonIDToClaimRefsLookup);
                        break;
                    case 2:
                        SHBE_PersonID = SHBE_Handler.getPersonID(
                                NINO,
                                DOB,
                                NINOToNINOIDLookup,
                                NINOIDToNINOLookup,
                                DOBToDOBIDLookup,
                                DOBIDToDOBLookup);
                        /**
                         * Ignore if this is a
                         * CottingleySpringsCaravanParkPairedClaimIDs. It may be
                         * that there are partners shared in these claims, but
                         * such a thing is ignored for now.
                         */
                        if (!CottingleySpringsCaravanParkPairedClaimIDs.contains(ClaimID)) {
                            /**
                             * If NonDependent is a NonDependent in another
                             * claim add to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                             */
                            key = SHBE_Collections.getKeyOfSetValue(ClaimIDToNonDependentPersonIDsLookup, SHBE_PersonID);
                            if (key != null) {
                                otherClaimID = (SHBE_ID) key;
                                HashSet<SHBE_ID> set;
                                set = NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(SHBE_PersonID);
                                if (set == null) {
                                    set = new HashSet<>();
                                    NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(SHBE_PersonID, set);
                                }
                                set.add(ClaimID);
                                set.add(otherClaimID);
                                ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(ClaimID);
                                ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
//                                if (!(NINO.trim().equalsIgnoreCase("") || NINO.startsWith("XX999"))) {
//                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                    Env.logO("NonDependent with NINO " + NINO + " DOB " + DOB
//                                            + " is in ClaimRef " + ClaimIDToClaimRefLookup.get(ClaimID)
//                                            + " and " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                    Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                }
                            }
                            /**
                             * If NonDependent is a Claimant in another claim
                             * add to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.
                             */
                            if (ClaimIDToClaimantPersonIDLookup.containsValue(SHBE_PersonID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ID) key;
                                    HashSet<SHBE_ID> set;
                                    set = NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(SHBE_PersonID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(SHBE_PersonID, set);
                                    }
                                    set.add(ClaimID);
                                    set.add(otherClaimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(ClaimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
//                                    if (!(NINO.trim().equalsIgnoreCase("") || NINO.startsWith("XX999"))) {
//                                        Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                        Env.logO("NonDependent with NINO " + NINO + " DOB " + DOB
//                                                + " in ClaimRef " + ClaimIDToClaimRefLookup.get(ClaimID)
//                                                + " is a Claimant in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                        Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                    }
                                }
                            }
                            /**
                             * If NonDependent is a Partner in another claim add
                             * to
                             * NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
                             */
                            if (ClaimIDToPartnerPersonIDLookup.containsValue(SHBE_PersonID)) {
                                if (key != null) {
                                    otherClaimID = (SHBE_ID) key;
                                    HashSet<SHBE_ID> set;
                                    set = NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.get(SHBE_PersonID);
                                    if (set == null) {
                                        set = new HashSet<>();
                                        NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup.put(SHBE_PersonID, set);
                                    }
                                    set.add(ClaimID);
                                    set.add(otherClaimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(ClaimID);
                                    ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim.add(otherClaimID);
//                                    if (!(NINO.trim().equalsIgnoreCase("") || NINO.startsWith("XX999"))) {
//                                        Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                        Env.logO("NonDependent with NINO " + NINO + " DOB " + DOB
//                                                + " in ClaimRef " + ClaimIDToClaimRefLookup.get(ClaimID)
//                                                + " is a Partner in " + ClaimIDToClaimRefLookup.get(otherClaimID));
//                                        Env.logO("!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                                    }
                                }
                            }
                        }
                        //HashSet<SHBE_PersonID> s;
                        s = ClaimIDToNonDependentPersonIDsLookup.get(ClaimID);
                        if (s == null) {
                            s = new HashSet<>();
                            ClaimIDToNonDependentPersonIDsLookup.put(ClaimID, s);
                        }
                        s.add(SHBE_PersonID);
                        NonDependentPersonIDs.add(SHBE_PersonID);
                        AllNonDependentPersonIDs.add(SHBE_PersonID);
                        addToPersonIDToClaimRefsLookup(
                                ClaimID,
                                SHBE_PersonID,
                                PersonIDToClaimRefsLookup);
                        break;
                    default:
                        Env.logE("Unrecognised SubRecordType " + SubRecordType);
                        break;
                }
            }
            SHBE_Record.SRecords = SRecordsForClaim;
            ClaimIDAndCountOfRecordsWithSRecords.put(ClaimID, SRecordsForClaim.size());
        }
        /**
         * Remove all assigned SRecords from SRecordsWithoutDRecords.
         */
        Iterator<SHBE_ID> iteID;
        iteID = ClaimIDAndCountOfRecordsWithSRecords.keySet().iterator();
        while (iteID.hasNext()) {
            SRecordsWithoutDRecords.remove(iteID.next());
        }
    }

    private void addToPersonIDToClaimRefsLookup(
            SHBE_ID ClaimID,
            SHBE_PersonID SHBE_PersonID,
            HashMap<SHBE_PersonID, HashSet<SHBE_ID>> PersonIDToClaimRefsLookup) {
        HashSet<SHBE_ID> s;
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
    public final int readAndCheckFirstLine(File directory, String filename) {
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
        File inputFile = new File(directory, filename);
        try {
            BufferedReader br = Generic_IO.getBufferedReader(inputFile);
            StreamTokenizer st = new StreamTokenizer(br);
            Generic_IO.setStreamTokenizerSyntax5(st);
            st.wordChars('`', '`');
            int tokenType;
            tokenType = st.nextToken();
            String line = "";
            while (tokenType != StreamTokenizer.TT_EOL) {
                switch (tokenType) {
                    case StreamTokenizer.TT_WORD:
                        line += st.sval;
                        break;
                }
                tokenType = st.nextToken();
            }
            br.close();
            if (line.startsWith(type0Header)) {
                return 0;
            }
            if (line.startsWith(type1Header)) {
                return 1;
            } else {
                String[] lineSplit = line.split(",");
                Env.logE("Unrecognised header in SHBE_Records.readAndCheckFirstLine(File,String)");
                Env.logE("Number of fields in header " + lineSplit.length);
                Env.logE("header:");
                Env.logE(line);

            }
        } catch (IOException ex) {
            Logger.getLogger(SHBE_Handler.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 2;
    }

    /**
     * @return the InputFile
     */
    public File getInputFile() {
        return InputFile;
    }

    /**
     * If not initialised, initialises ClaimIDToClaimantPersonIDLookup then
     * returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, SHBE_PersonID> getClaimIDToClaimantPersonIDLookup(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDToClaimantPersonIDLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDToClaimantPersonIDLookup(handleOutOfMemoryError);
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
    protected HashMap<SHBE_ID, SHBE_PersonID> getClaimIDToClaimantPersonIDLookup() {
        if (ClaimIDToClaimantPersonIDLookup == null) {
            File f;
            f = getClaimIDToClaimantPersonIDLookupFile();
            if (f.exists()) {
                ClaimIDToClaimantPersonIDLookup = (HashMap<SHBE_ID, SHBE_PersonID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, SHBE_PersonID> getClaimIDToPartnerPersonIDLookup(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDToPartnerPersonIDLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDToPartnerPersonIDLookup(handleOutOfMemoryError);
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
    protected HashMap<SHBE_ID, SHBE_PersonID> getClaimIDToPartnerPersonIDLookup() {
        if (ClaimIDToPartnerPersonIDLookup == null) {
            File f;
            f = getClaimIDToPartnerPersonIDLookupFile();
            if (f.exists()) {
                ClaimIDToPartnerPersonIDLookup = (HashMap<SHBE_ID, SHBE_PersonID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, HashSet<SHBE_PersonID>> getClaimIDToDependentPersonIDsLookup(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDToDependentPersonIDsLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDToDependentPersonIDsLookup(handleOutOfMemoryError);
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
    protected HashMap<SHBE_ID, HashSet<SHBE_PersonID>> getClaimIDToDependentPersonIDsLookup() {
        if (ClaimIDToDependentPersonIDsLookup == null) {
            File f;
            f = getClaimIDToDependentPersonIDsLookupFile();
            if (f.exists()) {
                ClaimIDToDependentPersonIDsLookup = (HashMap<SHBE_ID, HashSet<SHBE_PersonID>>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, HashSet<SHBE_PersonID>> getClaimIDToNonDependentPersonIDsLookup(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDToNonDependentPersonIDsLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDToNonDependentPersonIDsLookup(handleOutOfMemoryError);
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
    protected HashMap<SHBE_ID, HashSet<SHBE_PersonID>> getClaimIDToNonDependentPersonIDsLookup() {
        if (ClaimIDToNonDependentPersonIDsLookup == null) {
            File f;
            f = getClaimIDToNonDependentPersonIDsLookupFile();
            if (f.exists()) {
                ClaimIDToNonDependentPersonIDsLookup = (HashMap<SHBE_ID, HashSet<SHBE_PersonID>>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim() {
        if (ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim == null) {
            File f;
            f = getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile();
            if (f.exists()) {
                ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim() {
        if (ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim == null) {
            File f;
            f = getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile();
            if (f.exists()) {
                ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim(handleOutOfMemoryError);
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
    protected HashSet<SHBE_ID> getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim() {
        if (ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim == null) {
            File f;
            f = getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile();
            if (f.exists()) {
                ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim then returns
     * it.
     *
     * @return
     */
    protected HashSet<SHBE_ID> getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim() {
        if (ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim == null) {
            File f;
            f = getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile();
            if (f.exists()) {
                ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashSet<SHBE_ID> getClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim
     * then returns it.
     *
     * @return
     */
    protected HashSet<SHBE_ID> getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim() {
        if (ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim == null) {
            File f;
            f = getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile();
            if (f.exists()) {
                ClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaim = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean handleOutOfMemoryError) {
        try {
            return getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(handleOutOfMemoryError);
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
    protected HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup() {
        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            File f;
            f = getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (f.exists()) {
                ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (HashMap<SHBE_PersonID, HashSet<SHBE_ID>>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean handleOutOfMemoryError) {
        try {
            return getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup then returns it.
     *
     * @return
     */
    protected HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup() {
        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            File f;
            f = getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (f.exists()) {
                PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (HashMap<SHBE_PersonID, HashSet<SHBE_ID>>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(boolean handleOutOfMemoryError) {
        try {
            return getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup(handleOutOfMemoryError);
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
    protected HashMap<SHBE_PersonID, HashSet<SHBE_ID>> getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup() {
        if (NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup == null) {
            File f;
            f = getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile();
            if (f.exists()) {
                NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = (HashMap<SHBE_PersonID, HashSet<SHBE_ID>>) Generic_IO.readObject(f);
            } else {
                NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup = new HashMap<>();
            }
        }
        return NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup;
    }

    /**
     * If not initialised, initialises ClaimIDToPostcodeLookup then returns it.
     *
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, ONSPD_ID> getClaimIDToPostcodeIDLookup(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDToPostcodeIDLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDToPostcodeIDLookup(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * @return the ClaimIDToPostcodeLookup
     */
    protected HashMap<SHBE_ID, ONSPD_ID> getClaimIDToPostcodeIDLookup() {
        if (ClaimIDToPostcodeIDLookup == null) {
            File f;
            f = getClaimIDToPostcodeIDLookupFile();
            if (f.exists()) {
                ClaimIDToPostcodeIDLookup = (HashMap<SHBE_ID, ONSPD_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture
     */
    public final HashSet<SHBE_ID> getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture then returns it.
     *
     * @return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture
     */
    protected HashSet<SHBE_ID> getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture() {
        if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture == null) {
            File f;
            f = getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile();
            if (f.exists()) {
                ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return ClaimIDToTenancyTypeLookup
     */
    public final HashMap<SHBE_ID, Integer> getClaimIDToTenancyTypeLookup(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDToTenancyTypeLookup();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDToTenancyTypeLookup(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises ClaimIDToTenancyTypeLookup then returns
     * it.
     *
     * @return ClaimIDToTenancyTypeLookup
     */
    protected HashMap<SHBE_ID, Integer> getClaimIDToTenancyTypeLookup() {
        if (ClaimIDToTenancyTypeLookup == null) {
            File f;
            f = getClaimIDToTenancyTypeLookupFile();
            if (f.exists()) {
                ClaimIDToTenancyTypeLookup = (HashMap<SHBE_ID, Integer>) Generic_IO.readObject(f);
            } else {
                ClaimIDToTenancyTypeLookup = new HashMap<>();
            }
        }
        return ClaimIDToTenancyTypeLookup;
    }

    /**
     * If not initialised, initialises LoadSummary then returns it.
     *
     * @param handleOutOfMemoryError
     * @return LoadSummary
     */
    public final HashMap<String, Number> getLoadSummary(boolean handleOutOfMemoryError) {
        try {
            return getLoadSummary();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getLoadSummary(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises LoadSummary then returns it.
     *
     * @return LoadSummary
     */
    protected HashMap<String, Number> getLoadSummary() {
        if (LoadSummary == null) {
            File f;
            f = getLoadSummaryFile();
            if (f.exists()) {
                LoadSummary = (HashMap<String, Number>) Generic_IO.readObject(f);
            } else {
                LoadSummary = new HashMap<>();
            }
        }
        return LoadSummary;
    }

    /**
     * If not initialised, initialises RecordIDsNotLoaded then returns it.
     *
     * @param handleOutOfMemoryError
     * @return RecordIDsNotLoaded
     */
    public final ArrayList<Long> getRecordIDsNotLoaded(boolean handleOutOfMemoryError) {
        try {
            return getRecordIDsNotLoaded();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getRecordIDsNotLoaded(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises RecordIDsNotLoaded then returns it.
     *
     * @return RecordIDsNotLoaded
     */
    protected ArrayList<Long> getRecordIDsNotLoaded() {
        if (RecordIDsNotLoaded == null) {
            File f;
            f = getRecordIDsNotLoadedFile();
            if (f.exists()) {
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
     * @param handleOutOfMemoryError
     * @return ClaimIDsOfInvalidClaimantNINOClaims
     */
    public final HashSet<SHBE_ID> getClaimIDsOfInvalidClaimantNINOClaims(boolean handleOutOfMemoryError) {
        try {
            return getClaimIDsOfInvalidClaimantNINOClaims();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimIDsOfInvalidClaimantNINOClaims(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises ClaimRefsOfInvalidClaimantNINOClaims then
     * returns it.
     *
     * @return ClaimIDsOfInvalidClaimantNINOClaims
     */
    protected HashSet<SHBE_ID> getClaimIDsOfInvalidClaimantNINOClaims() {
        if (ClaimIDsOfInvalidClaimantNINOClaims == null) {
            File f;
            f = getClaimIDsOfInvalidClaimantNINOClaimsFile();
            if (f.exists()) {
                ClaimIDsOfInvalidClaimantNINOClaims = (HashSet<SHBE_ID>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return ClaimantPostcodesUnmappable
     */
    public final HashMap<SHBE_ID, String> getClaimantPostcodesUnmappable(boolean handleOutOfMemoryError) {
        try {
            return getClaimantPostcodesUnmappable();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimantPostcodesUnmappable(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises ClaimantPostcodesUnmappable then returns
     * it.
     *
     * @return ClaimantPostcodesUnmappable
     */
    protected HashMap<SHBE_ID, String> getClaimantPostcodesUnmappable() {
        if (ClaimantPostcodesUnmappable == null) {
            File f;
            f = getClaimantPostcodesUnmappableFile();
            if (f.exists()) {
                ClaimantPostcodesUnmappable = (HashMap<SHBE_ID, String>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return ClaimantPostcodesModified
     */
    public final HashMap<SHBE_ID, String[]> getClaimantPostcodesModified(boolean handleOutOfMemoryError) {
        try {
            return getClaimantPostcodesModified();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimantPostcodesModified(handleOutOfMemoryError);
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
    protected HashMap<SHBE_ID, String[]> getClaimantPostcodesModified() {
        if (ClaimantPostcodesModified == null) {
            File f;
            f = getClaimantPostcodesModifiedFile();
            if (f.exists()) {
                ClaimantPostcodesModified = (HashMap<SHBE_ID, String[]>) Generic_IO.readObject(f);
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
     * @param handleOutOfMemoryError
     * @return
     */
    public final HashMap<SHBE_ID, String> getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(boolean handleOutOfMemoryError) {
        try {
            return getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
    }

    /**
     * If not initialised, initialises
     * ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes then returns it.
     *
     * @return ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes
     */
    protected HashMap<SHBE_ID, String> getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes() {
        if (ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes == null) {
            File f;
            f = getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile();
            if (f.exists()) {
                ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = (HashMap<SHBE_ID, String>) Generic_IO.readObject(f);
            } else {
                ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes = new HashMap<>();
            }
        }
        return ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes;
    }

    /**
     * @return the DataFile
     */
    protected final File getFile() {
        if (File == null) {
            File = getFile(
                    "SHBE_Records"
                    + Strings.sBinaryFileExtension);
        }
        return File;
    }

    /**
     * @return RecordsFile initialising if it is not already initialised.
     */
    protected final File getRecordsFile() {
        if (RecordsFile == null) {
            RecordsFile = getFile(Strings.sRecords + Strings.symbol_underscore
                    + "HashMap_String__SHBE_Record"
                    + Strings.sBinaryFileExtension);
        }
        return RecordsFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsFile initialising if it is not already
     * initialised.
     */
    protected final File getClaimIDsOfNewSHBEClaimsFile() {
        if (ClaimIDsOfNewSHBEClaimsFile == null) {
            ClaimIDsOfNewSHBEClaimsFile = getFile(
                    "ClaimIDsOfNewSHBEClaims"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsFile initialising if it is not already
     * initialised.
     */
    protected final File getClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBefore"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasClaimantBeforeFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsFile initialising if it is not already
     * initialised.
     */
    protected final File getClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBefore"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasPartnerBeforeFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile
     * initialising if it is not already initialised.
     */
    protected final File getClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBefore"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantWasNonDependentBeforeFile;
    }

    /**
     * @return ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile initialising if it
     * is not already initialised.
     */
    protected final File getClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile() {
        if (ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile == null) {
            ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile = getFile(
                    "ClaimIDsOfNewSHBEClaimsWhereClaimantIsNew"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfNewSHBEClaimsWhereClaimantIsNewFile;
    }

    public final File getClaimantPersonIDsFile() {
        if (ClaimantPersonIDsFile == null) {
            ClaimantPersonIDsFile = getFile(
                    "Claimant"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_PersonID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimantPersonIDsFile;
    }

    public final File getPartnerPersonIDsFile() {
        if (PartnerPersonIDsFile == null) {
            PartnerPersonIDsFile = getFile(
                    "Partner"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_PersonID"
                    + Strings.sBinaryFileExtension);
        }
        return PartnerPersonIDsFile;
    }

    public final File getNonDependentPersonIDsFile() {
        if (NonDependentPersonIDsFile == null) {
            NonDependentPersonIDsFile = getFile(
                    "NonDependent"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_PersonID"
                    + Strings.sBinaryFileExtension);
        }
        return NonDependentPersonIDsFile;
    }

    public final HashSet<SHBE_PersonID> getClaimantPersonIDs(boolean handleOutOfMemoryError) {
        try {
            return getClaimantPersonIDs();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getClaimantPersonIDs(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
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
    public final HashSet<SHBE_PersonID> getClaimantPersonIDs(
            File f) {
        if (ClaimantPersonIDs == null) {
            ClaimantPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return ClaimantPersonIDs;
    }

    public final HashSet<SHBE_PersonID> getPartnerPersonIDs(boolean handleOutOfMemoryError) {
        try {
            return getPartnerPersonIDs();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getPartnerPersonIDs(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
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
    public final HashSet<SHBE_PersonID> getPartnerPersonIDs(
            File f) {
        if (PartnerPersonIDs == null) {
            PartnerPersonIDs = SHBE_Collections.getHashSet_SHBE_PersonID(f);
        }
        return PartnerPersonIDs;
    }

    public final HashSet<SHBE_PersonID> getNonDependentPersonIDs(boolean handleOutOfMemoryError) {
        try {
            return getNonDependentPersonIDs();
        } catch (OutOfMemoryError e) {
            if (handleOutOfMemoryError) {
                Env.clearMemoryReserve();
                if (!Env.Data.clearSomeCacheExcept(YM3)) {
                    throw e;
                }
                Env.initMemoryReserve();
                return getNonDependentPersonIDs(handleOutOfMemoryError);
            } else {
                throw e;
            }
        }
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
     * @return CottingleySpringsCaravanParkPairedClaimIDsFile initialising if it
     * is not already initialised.
     */
    protected final File getCottingleySpringsCaravanParkPairedClaimIDsFile() {
        if (CottingleySpringsCaravanParkPairedClaimIDsFile == null) {
            CottingleySpringsCaravanParkPairedClaimIDsFile = getFile(
                    Strings.sCottingleySpringsCaravanPark + "PairedClaimIDs"
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return CottingleySpringsCaravanParkPairedClaimIDsFile;
    }

    /**
     * @return ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile initialising if
     * it is not already initialised.
     */
    protected final File getClaimIDsWithStatusOfHBAtExtractDateInPaymentFile() {
        if (ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile == null) {
            ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile = getFile(
                    Strings.sHB + Strings.sPaymentTypeIn
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsWithStatusOfHBAtExtractDateInPaymentFile;
    }

    /**
     * @return ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile initialising if
     * it is not already initialised.
     */
    protected final File getClaimIDsWithStatusOfHBAtExtractDateSuspendedFile() {
        if (ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile == null) {
            ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile = getFile(
                    Strings.sHB + Strings.sPaymentTypeSuspended
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsWithStatusOfHBAtExtractDateSuspendedFile;
    }

    /**
     * @return ClaimIDsWithStatusOfHBAtExtractDateOtherFile initialising if it
     * is not already initialised.
     */
    protected final File getClaimIDsWithStatusOfHBAtExtractDateOtherFile() {
        if (ClaimIDsWithStatusOfHBAtExtractDateOtherFile == null) {
            ClaimIDsWithStatusOfHBAtExtractDateOtherFile = getFile(
                    Strings.sHB + Strings.sPaymentTypeOther
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsWithStatusOfHBAtExtractDateOtherFile;
    }

    /**
     * @return ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile initialising if
     * it is not already initialised.
     */
    protected final File getClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile() {
        if (ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile == null) {
            ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile = getFile(
                    Strings.sCTB + Strings.sPaymentTypeIn
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateInPaymentFile;
    }

    /**
     * @return ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile initialising if
     * it is not already initialised.
     */
    protected final File getClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile() {
        if (ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile == null) {
            ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile = getFile(
                    Strings.sCTB + Strings.sPaymentTypeSuspended
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateSuspendedFile;
    }

    /**
     * @return ClaimIDsWithStatusOfCTBAtExtractDateOtherFile initialising if it
     * is not already initialised.
     */
    protected final File getClaimIDsWithStatusOfCTBAtExtractDateOtherFile() {
        if (ClaimIDsWithStatusOfCTBAtExtractDateOtherFile == null) {
            ClaimIDsWithStatusOfCTBAtExtractDateOtherFile = getFile(
                    Strings.sCTB + Strings.sPaymentTypeOther
                    + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsWithStatusOfCTBAtExtractDateOtherFile;
    }

    /**
     * @return SRecordsWithoutDRecordsFile initialising if it is not already
     * initialised.
     */
    protected final File getSRecordsWithoutDRecordsFile() {
        if (SRecordsWithoutDRecordsFile == null) {
            SRecordsWithoutDRecordsFile = getFile(
                    "SRecordsWithoutDRecordsFile" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__ArrayList_SHBE_S_Record"
                    + Strings.sBinaryFileExtension);
        }
        return SRecordsWithoutDRecordsFile;
    }

    /**
     * @return ClaimIDAndCountOfRecordsWithSRecordsFile initialising if it is
     * not already initialised.
     */
    protected final File getClaimIDAndCountOfRecordsWithSRecordsFile() {
        if (ClaimIDAndCountOfRecordsWithSRecordsFile == null) {
            ClaimIDAndCountOfRecordsWithSRecordsFile = getFile(
                    "ClaimIDAndCountOfRecordsWithSRecordsFile" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__Integer"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDAndCountOfRecordsWithSRecordsFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile initialising
     * if it is not already initialised.
     */
    protected final File getClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile() {
        if (ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile == null) {
            ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile = getFile(
                    "ClaimIDsOfClaimsWithoutAMappableClaimantPostcode" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__Integer"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithoutAMappableClaimantPostcodeFile;
    }

    /**
     * @return ClaimIDToClaimantPersonIDLookupFile initialising if it is not
     * already initialised.
     */
    public final File getClaimIDToClaimantPersonIDLookupFile() {
        if (ClaimIDToClaimantPersonIDLookupFile == null) {
            ClaimIDToClaimantPersonIDLookupFile = getFile(
                    "ClaimIDToClaimantPersonIDLookup" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID_SHBE_PersonID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDToClaimantPersonIDLookupFile;
    }

    /**
     * @return ClaimIDToPartnerPersonIDLookupFile initialising if it is not
     * already initialised.
     */
    public final File getClaimIDToPartnerPersonIDLookupFile() {
        if (ClaimIDToPartnerPersonIDLookupFile == null) {
            ClaimIDToPartnerPersonIDLookupFile = getFile(
                    "ClaimIDToPartnerPersonIDLookup" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__SHBE_PersonID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDToPartnerPersonIDLookupFile;
    }

    /**
     * @return ClaimIDToDependentPersonIDsLookupFile initialising if it is not
     * already initialised.
     */
    public final File getClaimIDToDependentPersonIDsLookupFile() {
        if (ClaimIDToDependentPersonIDsLookupFile == null) {
            ClaimIDToDependentPersonIDsLookupFile = getFile(
                    "ClaimIDToDependentPersonIDsLookupFile" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__HashSet<SHBE_PersonID>"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDToDependentPersonIDsLookupFile;
    }

    /**
     * @return ClaimIDToNonDependentPersonIDsLookupFile initialising if it is
     * not already initialised.
     */
    public final File getClaimIDToNonDependentPersonIDsLookupFile() {
        if (ClaimIDToNonDependentPersonIDsLookupFile == null) {
            ClaimIDToNonDependentPersonIDsLookupFile = getFile(
                    "ClaimIDToNonDependentPersonIDsLookupFile" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__HashSet_SHBE_PersonID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDToNonDependentPersonIDsLookupFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final File getClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile() {
        if (ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim" + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithClaimantsThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final File getClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile() {
        if (ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaim" + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithClaimantsThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final File getClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile() {
        if (ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile" + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithPartnersThatAreClaimantsInAnotherClaimFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final File getClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile() {
        if (ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile == null) {
            ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaim" + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithPartnersThatArePartnersInAnotherClaimFile;
    }

    /**
     * @return
     * ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile
     * initialising if it is not already initialised.
     */
    public final File getClaimIDsOfClaimsWithNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile() {
        if (ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile == null) {
            ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile = getFile(
                    "ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaim" + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfNonDependentsThatAreClaimantsOrPartnersInAnotherClaimFile;
    }

    /**
     * @return ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     */
    public final File getClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile() {
        if (ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookup" + Strings.symbol_underscore
                    + "HashMap_SHBE_PersonID__HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimantsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     */
    public final File getPartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile() {
        if (PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + Strings.symbol_underscore
                    + "HashMap_SHBE_PersonID__HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return PartnersInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile
     * initialising if it is not already initialised.
     */
    public final File getNonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile() {
        if (NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile == null) {
            NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile = getFile(
                    "NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile" + Strings.symbol_underscore
                    + "HashMap_SHBE_PersonID__HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return NonDependentsInMultipleClaimsInAMonthPersonIDToClaimIDsLookupFile;
    }

    /**
     * @return ClaimIDToPostcodeIDLookupFile initialising if it is not already
     * initialised.
     */
    public final File getClaimIDToPostcodeIDLookupFile() {
        if (ClaimIDToPostcodeIDLookupFile == null) {
            ClaimIDToPostcodeIDLookupFile = getFile(
                    "ClaimIDToPostcodeIDLookup" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDToPostcodeIDLookupFile;
    }

    /**
     * @return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile
     * initialising if it is not already initialised.
     */
    public final File getClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile() {
        if (ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile == null) {
            ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile = getFile(
                    "ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFuture" + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfClaimsWithClaimPostcodeFUpdatedFromTheFutureFile;
    }

    /**
     * @return ClaimIDToTenancyTypeLookupFile initialising if it is not already
     * initialised.
     */
    public final File getClaimIDToTenancyTypeLookupFile() {
        if (ClaimIDToTenancyTypeLookupFile == null) {
            ClaimIDToTenancyTypeLookupFile = getFile(
                    "ClaimIDToTenancyTypeLookup" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__Integer"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDToTenancyTypeLookupFile;
    }

    /**
     * @return LoadSummaryFile initialising if it is not already initialised.
     */
    public final File getLoadSummaryFile() {
        if (LoadSummaryFile == null) {
            LoadSummaryFile = getFile(
                    "LoadSummary" + Strings.symbol_underscore
                    + "HashMap_String__Integer"
                    + Strings.sBinaryFileExtension);
        }
        return LoadSummaryFile;
    }

    /**
     * @return RecordIDsNotLoadedFile initialising if it is not already
     * initialised.
     */
    public final File getRecordIDsNotLoadedFile() {
        if (RecordIDsNotLoadedFile == null) {
            RecordIDsNotLoadedFile = getFile(
                    "RecordIDsNotLoaded" + Strings.symbol_underscore
                    + "ArrayList_Long"
                    + Strings.sBinaryFileExtension);
        }
        return RecordIDsNotLoadedFile;
    }

    /**
     * @return ClaimIDsOfInvalidClaimantNINOClaimsFile initialising if it is not
     * already initialised.
     */
    public final File getClaimIDsOfInvalidClaimantNINOClaimsFile() {
        if (ClaimIDsOfInvalidClaimantNINOClaimsFile == null) {
            ClaimIDsOfInvalidClaimantNINOClaimsFile = getFile(
                    "ClaimIDsOfInvalidClaimantNINOClaimsFile" + Strings.symbol_underscore
                    + "HashSet_SHBE_ID"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimIDsOfInvalidClaimantNINOClaimsFile;
    }

    /**
     * @return ClaimantPostcodesUnmappableFile initialising if it is not already
     * initialised.
     */
    public final File getClaimantPostcodesUnmappableFile() {
        if (ClaimantPostcodesUnmappableFile == null) {
            ClaimantPostcodesUnmappableFile = getFile(
                    "ClaimantPostcodesUnmappable" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__String"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimantPostcodesUnmappableFile;
    }

    /**
     * @return ClaimantPostcodesModifiedFile initialising if it is not already
     * initialised.
     */
    public final File getClaimantPostcodesModifiedFile() {
        if (ClaimantPostcodesModifiedFile == null) {
            ClaimantPostcodesModifiedFile = getFile(
                    "ClaimantPostcodesModified" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__String[]"
                    + Strings.sBinaryFileExtension);
        }
        return ClaimantPostcodesModifiedFile;
    }

    /**
     * @return ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile
     * initialising if it is not already initialised.
     */
    public final File getClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile() {
        if (ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile == null) {
            ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodesFile = getFile(
                    "ClaimantPostcodesCheckedAsMappableButNotInONSPDPostcodes" + Strings.symbol_underscore
                    + "HashMap_SHBE_ID__String"
                    + Strings.sBinaryFileExtension);
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
