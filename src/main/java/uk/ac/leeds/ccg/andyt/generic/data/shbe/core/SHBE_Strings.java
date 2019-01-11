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

import java.io.Serializable;
import java.util.ArrayList;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author Andy Turner.
 */
public class SHBE_Strings extends Generic_Strings implements Serializable {

    public final String CottingleySpringsCaravanParkPostcode = "LS27 7NS";
    public final String sCottingleySpringsCaravanPark = "CottingleySpringsCaravanPark";

    public final String sCountOfCTBAndHBClaims = "CountOfCTBAndHBClaims";
    public final String sCountOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim = "CountOfClaimsWithClaimantsThatAreClaimantsInAnotherClaim";
    public final String sCountOfClaimsWithClaimantsThatArePartnersInAnotherClaim = "CountOfClaimsWithClaimantsThatArePartnersInAnotherClaim";
    public final String sCountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim = "CountOfClaimsWithPartnersThatAreClaimantsInAnotherClaim";
    public final String sCountOfClaimsWithPartnersThatArePartnersInAnotherClaim = "CountOfClaimsWithPartnersThatArePartnersInAnotherClaim";
    public final String sCountOfClaimantsInMultipleClaimsInAMonth = "CountOfClaimantsInMultipleClaimsInAMonth";
    public final String sCountOfDependentsInAllClaims = "CountOfDependentsInAllClaims";
    public final String sCountOfPartnersInMultipleClaimsInAMonth = "CountOfPartnersInMultipleClaimsInAMonth";
    public final String sCountOfNonDependentsInMultipleClaimsInAMonth = "CountOfNonDependentsInMultipleClaimsInAMonth";
    public final String sCountOfNewClaimantPostcodes = "CountOfNewClaimantPostcodes";
    public final String sCountOfNewValidMappableClaimantPostcodes = "CountOfNewValidMappableClaimantPostcodes";
    public final String sCountOfMappableClaimantPostcodes = "CountOfMappableClaimantPostcodes";
    public final String sCountOfNonMappableClaimantPostcodes = "CountOfNonMappableClaimantPostcodes";
    public final String sCountOfInvalidFormatClaimantPostcodes = "CountOfInvalidFormatClaimantPostcodes";
    public final String sCountOfClaims = "CountOfClaims";
    public final String sCountOfClaimsWithPartners = "CountOfClaimsWithPartners";
    public final String sCountOfCTBClaims = "CountOfCTBClaims";
    public final String sCountOfRecords = "CountOfRecords";
    public final String sCountOfHBClaims = "CountOfHBClaims";
    public final String sCountOfIncompleteDRecords = "CountOfIncompleteDRecords";
    public final String sCountOfNonDependentsInAllClaims = "CountOfNonDependentsInAllClaims";
    public final String sCountOfUniqueDependents = "CountOfUniqueDependents";
    public final String sCountOfUniqueNonDependents = "CountOfUniqueNonDependents";
    public final String sCountOfUniqueClaimants = "CountOfUniqueClaimants";
    public final String sCountOfUniquePartners = "CountOfUniquePartners";
    public final String sCountOfSRecords = "CountOfSRecords";
    public final String sCountOfSRecordsWithoutDRecord = "CountOfSRecordsWithoutDRecord";
    public final String sCountOfRecordIDsNotLoaded = "CountOfRecordIDsNotLoaded";
    public final String sCountOfSRecordsNotLoaded = "CountOfSRecordsNotLoaded";

    /**
     * Code for a Default National Insurance Number. There are other defaults
     * that appear to be in the source SHBE data that are of a similar form, but
     * this is the default one used by this program.
     */
    public final String sDefaultNINO = "ZX999999XZ";

    public final String sPaymentTypeOther = "PTO";
    public final String sPaymentTypeAll = "PTA";
    public final String sUnregulated = "Unregulated";
    public final String sRegulated = "Regulated";
    public final String sPaymentTypeIn = "PTI";
    public final String sPaymentTypeSuspended = "PTS";
    public final String sBinaryFileExtension = ".dat";

    public final String sIncludeSameTenancy = "IncludeSameTenancy";
    public final String sNotIncludeSameTenancy = "NotIncludeSameTenancy";
    public final String sIncludeMonthlySinceApril2013 = "IMU";
    public final String sInclude2MonthlySinceApril2013Offset0 = "I2MU0";
    public final String sInclude2MonthlySinceApril2013Offset1 = "I2MU1";
    public final String sIncludeStartEndSinceApril2013 = "ISEU";
    public final String sIncludingTenancyTransitionBreaks = "ITTBY";
    public final String sIncludingTenancyTransitionBreaksNo = "ITTBN";
    public final String sInclude6Monthly = "I6M";
    public final String sIncludeMonthly = "IM";
    public final String sIncludeApril2013May2013 = "IncludeApril2013May2013";
    public final String sIncludeYearly = "IY";
    public final String sIncludeAll = "IA";
    public final String sInclude3Monthly = "I3M";

    public final String sHB = "HB";
    public final String sCTB = "CTB";

    public final String sLCC = "LCC";
    public final String sSHBE = "SHBE";
    public final String sUnit = "Unit";

    public final String sU = "U";

    public final String sUngrouped = "Ungrouped";
    public final String sCountOfIndividuals = "CountOfIndividuals";
    public final String sCountOfNewSHBEClaims = "CountOfNewSHBEClaims";
    public final String sCountOfNewSHBEClaimsWhereClaimantWasClaimantBefore = "CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore";
    public final String sCountOfNewSHBEClaimsWhereClaimantWasPartnerBefore = "CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore";
    public final String sCountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = "CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore";
    public final String sCountOfNewSHBEClaimsWhereClaimantIsNew = "CountOfNewSHBEClaimsWhereClaimantIsNew";
    public final String sLineCount = "LineCount";
    public final String sRecords = "Records";

    public final String sTotalIncomeGreaterThanZeroCount = "TotalIncomeGreaterThanZeroCount";
    public final String sTotalWeeklyEligibleRentAmount = "TotalWeeklyEligibleRentAmount";
    public final String sTotalWeeklyEligibleRentAmountGreaterThanZeroCount = "TotalWeeklyEligibleRentAmountGreaterThanZeroCount";
    public final String sTotalIncome = "TotalIncome";
    public final String sCountOfNewSHBEClaimsPSI = "CountOfNewSHBEClaimsPSI";

    public final String sAverage_NonZero_Income = "Average_NonZero_Income";
    public final String sAverage_NonZero_WeeklyEligibleRentAmount = "Average_NonZero_WeeklyEligibleRentAmount";

    public SHBE_Strings() {
        super();
    }

    /**
     * For getting an {@code ArrayList<String>} of PaymentTypes.
     *
     * @return
     */
    public ArrayList<String> getPaymentTypes() {
        ArrayList<String> r;
        r = new ArrayList<>();
        r.add(sPaymentTypeAll);
        r.add(sPaymentTypeIn);
        r.add(sPaymentTypeSuspended);
        r.add(sPaymentTypeOther);
        return r;
    }
}
