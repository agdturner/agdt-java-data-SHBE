/*
 * Copyright 2018 Andy Turner, CCG, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.andyt.generic.data.shbe.core;

import java.util.ArrayList;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author Andy Turner.
 */
public class SHBE_Strings extends Generic_Strings {

    // special
    public static final String CottingleySpringsCaravanParkPostcode = "LS27 7NS";
    public static final String s_CottingleySpringsCaravanPark = "CottingleySpringsCaravanPark";

    public static final String s_CountOfCTBAndHBClaims = "CountOfCTBAndHBClaims";
    public static final String s_CountOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = "CountOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim";
    public static final String s_CountOfClaimsWithClaimantsThatArePartnersInAnotherClaim = "CountOfClaimsWithClaimantsThatArePartnersInAnotherClaim";
    public static final String s_CountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = "CountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim";
    public static final String s_CountOfClaimsWithPartnersThatArePartnersInAnotherClaim = "CountOfClaimsWithPartnersThatArePartnersInAnotherClaim";
    public static final String s_CountOfClaimantsInMultipleClaimsInAMonth = "CountOfClaimantsInMultipleClaimsInAMonth";
    public static final String s_CountOfDependentsInAllClaims = "CountOfDependentsInAllClaims";
    public static final String s_CountOfPartnersInMultipleClaimsInAMonth = "CountOfPartnersInMultipleClaimsInAMonth";
    public static final String s_CountOfNonDependentsInMultipleClaimsInAMonth = "CountOfNonDependentsInMultipleClaimsInAMonth";
    public static final String s_CountOfNewClaimantPostcodes = "CountOfNewClaimantPostcodes";
    public static final String s_CountOfNewValidMappableClaimantPostcodes = "CountOfNewValidMappableClaimantPostcodes";
    public static final String s_CountOfMappableClaimantPostcodes = "CountOfMappableClaimantPostcodes";
    public static final String s_CountOfNonMappableClaimantPostcodes = "CountOfNonMappableClaimantPostcodes";
    public static final String s_CountOfInvalidFormatClaimantPostcodes = "CountOfInvalidFormatClaimantPostcodes";
    public static final String s_CountOfClaims = "CountOfClaims";
    public static final String s_CountOfClaimsWithPartners = "CountOfClaimsWithPartners";
    public static final String s_CountOfCTBClaims = "CountOfCTBClaims";
    public static final String s_CountOfRecords = "CountOfRecords";
    public static final String s_CountOfHBClaims = "CountOfHBClaims";
    public static final String s_CountOfIncompleteDRecords = "CountOfIncompleteDRecords";
    public static final String s_CountOfNonDependentsInAllClaims = "CountOfNonDependentsInAllClaims";
    public static final String s_CountOfUniqueDependents = "CountOfUniqueDependents";
    public static final String s_CountOfUniqueNonDependents = "CountOfUniqueNonDependents";
    public static final String s_CountOfUniqueClaimants = "CountOfUniqueClaimants";
    public static final String s_CountOfUniquePartners = "CountOfUniquePartners";
    public static final String s_CountOfSRecords = "CountOfSRecords";
    public static final String s_CountOfSRecordsWithoutDRecord = "CountOfSRecordsWithoutDRecord";
    public static final String s_CountOfRecordIDsNotLoaded = "CountOfRecordIDsNotLoaded";
    public static final String s_CountOfSRecordsNotLoaded = "CountOfSRecordsNotLoaded";

    /**
     * Code for a Default National Insurance Number. There are other defaults
     * that appear to be in the source SHBE data that are of a similar form, but
     * this is the default one used by this program.
     */
    public static final String s_DefaultNINO = "ZX999999XZ";

    public static final String s_PaymentTypeOther = "PTO";
    public static final String s_PaymentTypeAll = "PTA";
    public static final String s_Unregulated = "Unregulated";
    public static final String s_Regulated = "Regulated";
    public static final String s_PaymentTypeIn = "PTI";
    public static final String s_PaymentTypeSuspended = "PTS";
    public static final String s_BinaryFileExtension = ".dat";

    public static final String s_IncludeSameTenancy = "IncludeSameTenancy";
    public static final String s_NotIncludeSameTenancy = "NotIncludeSameTenancy";
    public static final String s_IncludeMonthlySinceApril2013 = "IMU";
    public static final String s_Include2MonthlySinceApril2013Offset0 = "I2MU0";
    public static final String s_Include2MonthlySinceApril2013Offset1 = "I2MU1";
    public static final String s_IncludeStartEndSinceApril2013 = "ISEU";
    public static final String s_IncludingTenancyTransitionBreaks = "ITTBY";
    public static final String s_IncludingTenancyTransitionBreaksNo = "ITTBN";
    public static final String s_Include6Monthly = "I6M";
    public static final String s_IncludeMonthly = "IM";
    public static final String s_IncludeApril2013May2013 = "IncludeApril2013May2013";
    public static final String s_IncludeYearly = "IY";
    public static final String s_IncludeAll = "IA";
    public static final String s_Include3Monthly = "I3M";

    public static final String s_HB = "HB";
    public static final String s_CTB = "CTB";

    public static final String s_LCC = "LCC";
    public static final String s_SHBE = "SHBE";
    public static final String s_Unit = "Unit";

    public static final String s_U = "U";

    public static final String s_Ungrouped = "Ungrouped";
    public static final String s_CountOfIndividuals = "CountOfIndividuals";
    public static final String s_CountOfNewSHBEClaims = "CountOfNewSHBEClaims";
    public static final String s_CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore = "CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore";
    public static final String s_CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore = "CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore";
    public static final String s_CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = "CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore";
    public static final String s_CountOfNewSHBEClaimsWhereClaimantIsNew = "CountOfNewSHBEClaimsWhereClaimantIsNew";
    public static final String s_LineCount = "LineCount";
    public static final String s_Records = "Records";

    public static final String s_TotalIncomeGreaterThanZeroCount = "TotalIncomeGreaterThanZeroCount";
    public static final String s_TotalWeeklyEligibleRentAmount = "TotalWeeklyEligibleRentAmount";
    public static final String s_TotalWeeklyEligibleRentAmountGreaterThanZeroCount = "TotalWeeklyEligibleRentAmountGreaterThanZeroCount";
    public static final String s_TotalIncome = "TotalIncome";
    public static final String s_CountOfNewSHBEClaimsPSI = "CountOfNewSHBEClaimsPSI";

    public static final String s_Average_NonZero_Income = "Average_NonZero_Income";
    public static final String s_Average_NonZero_WeeklyEligibleRentAmount = "Average_NonZero_WeeklyEligibleRentAmount";

    public SHBE_Strings() {
        super();
    }

    /**
     * For getting an {@code ArrayList<String>} of PaymentTypes.
     *
     * @return
     */
    public static ArrayList<String> getPaymentTypes() {
        ArrayList<String> r;
        r = new ArrayList<>();
        r.add(s_PaymentTypeAll);
        r.add(s_PaymentTypeIn);
        r.add(s_PaymentTypeSuspended);
        r.add(s_PaymentTypeOther);
        return r;
    }
}
