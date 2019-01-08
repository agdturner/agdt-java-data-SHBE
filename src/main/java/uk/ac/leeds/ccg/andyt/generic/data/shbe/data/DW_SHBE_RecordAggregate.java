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

/**
 *
 * @author geoagdt
 */
public class DW_SHBE_RecordAggregate {

    private int totalClaimCount;
    private int totalHBClaimCount;
    private int totalCTBClaimCount;
    private int totalTenancyType1Count;
    private int totalTenancyType2Count;
    private int totalTenancyType3Count;
    private int totalTenancyType4Count;
    private int totalTenancyType5Count;
    private int totalTenancyType6Count;
    private int totalTenancyType7Count;
    private int totalTenancyType8Count;
    private int totalTenancyType9Count;
    private int totalNumberOfChildDependents;
    private int totalNumberOfNonDependents;
    private int totalNonDependentStatus0;
    private int totalNonDependentStatus1;
    private int totalNonDependentStatus2;
    private int totalNonDependentStatus3;
    private int totalNonDependentStatus4;
    private int totalNonDependentStatus5;
    private int totalNonDependentStatus6;
    private int totalNonDependentStatus7;
    private int totalNonDependentStatus8;
    private int totalStatusOfHBClaimAtExtractDate0;
    private int totalStatusOfHBClaimAtExtractDate1;
    private int totalStatusOfHBClaimAtExtractDate2;
    private int totalStatusOfCTBClaimAtExtractDate0;
    private int totalStatusOfCTBClaimAtExtractDate1;
    private int totalStatusOfCTBClaimAtExtractDate2;
    private int totalOutcomeOfFirstDecisionOnMostRecentHBClaim1;
    private int totalOutcomeOfFirstDecisionOnMostRecentHBClaim2;
    private int totalOutcomeOfFirstDecisionOnMostRecentHBClaim3;
    private int totalOutcomeOfFirstDecisionOnMostRecentHBClaim4;
    private int totalOutcomeOfFirstDecisionOnMostRecentHBClaim5;
    private int totalOutcomeOfFirstDecisionOnMostRecentHBClaim6;
    private int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1;
    private int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2;
    private int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3;
    private int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4;
    private int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5;
    private int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6;
    private long totalWeeklyHousingBenefitEntitlement;
    private long totalWeeklyCouncilTaxBenefitEntitlement;
    //private int FrequencyOfPaymentOfHB;
    //private int FrequencyOfPaymentOfCTB;
    //private int PreDeterminationAmountOfHB;
    //private int PreDeterminationAmountOfCTB;
    //private int WeeklyHBEntitlementBeforeChange;
    //private int WeeklyCTBEntitlementBeforeChange;
    //private int ReasonForDirectPayment;
    //private int TimingOfPaymentOfRentAllowance;
    //private int ExtendedPaymentCase;
    //private String CouncilTaxBand;
    //private int WeeklyEligibleRentAmount;
    //private int WeeklyEligibleCouncilTaxAmount;
    //private String ClaimantsStudentIndicator;
    //private int RebatePercentageWhereASecondAdultRebateHasBeenAwarded;
    //private int SecondAdultRebate;
    private int totalLHARegulationsApplied0;
    private int totalLHARegulationsApplied1;
    //private int IsThisCaseSubjectToLRROrSRRSchemes;
    //private int WeeklyLocalReferenceRent;
    //private int WeeklySingleRoomRent;
    //private int WeelklyClaimRelatedRent;
    //private int RentOfficerDeterminationOfIneligibleCharges;
    private int totalWeeklyMaximumRent;
    //private int TotalDeductionForMeals;
    //private int WeeklyAdditionalDiscretionaryPayment;
    //private int ThirteenOrFiftyTwoWeekProtectionApplies;
    //private String DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision;
    private int totalClaimantsAssessedIncomeFigure;
    private int totalClaimantsAdjustedAssessedIncomeFigure;
    private int totalClaimantsTotalCapital;
    private int totalClaimantsGrossWeeklyIncomeFromEmployment;
    private int totalClaimantsNetWeeklyIncomeFromEmployment;
    private int totalClaimantsGrossWeeklyIncomeFromSelfEmployment;
    private int totalClaimantsNetWeeklyIncomeFromSelfEmployment;
    private int totalClaimantsTotalAmountOfEarningsDisregarded;
    private int totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    private int totalClaimantsIncomeFromAttendanceAllowance;
    private int totalClaimantsIncomeFromBusinessStartUpAllowance;
    private int totalClaimantsIncomeFromChildBenefit;
    private int totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent;
    private int totalClaimantsIncomeFromPersonalPension;
    private int totalClaimantsIncomeFromSevereDisabilityAllowance;
    private int totalClaimantsIncomeFromMaternityAllowance;
    private int totalClaimantsIncomeFromContributionBasedJobSeekersAllowance;
    private int totalClaimantsIncomeFromStudentGrantLoan;
    private int totalClaimantsIncomeFromSubTenants;
    private int totalClaimantsIncomeFromBoarders;
    private int totalClaimantsIncomeFromTrainingForWorkCommunityAction;
    private int totalClaimantsIncomeFromIncapacityBenefitShortTermLower;
    private int totalClaimantsIncomeFromIncapacityBenefitShortTermHigher;
    private int totalClaimantsIncomeFromIncapacityBenefitLongTerm;
    private int totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit;
    private int totalClaimantsIncomeFromNewTaxCredits;
    private int totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent;
    private int totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent;
    private int totalClaimantsIncomeFromGovernemntTraining;
    private int totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit;
    private int totalClaimantsIncomeFromCarersAllowance;
    private int totalClaimantsIncomeFromStatutoryMaternityPaternityPay;
    private int totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    private int totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP;
    private int totalClaimantsIncomeFromWarMobilitySupplement;
    private int totalClaimantsIncomeFromWidowsWidowersPension;
    private int totalClaimantsIncomeFromBereavementAllowance;
    private int totalClaimantsIncomeFromWidowedParentsAllowance;
    private int totalClaimantsIncomeFromYouthTrainingScheme;
    private int totalClaimantsIncomeFromStatuatorySickPay;
    private int totalClaimantsOtherIncome;
    private int totalClaimantsTotalAmountOfIncomeDisregarded;
    private int totalFamilyPremiumAwarded;
    private int totalFamilyLoneParentPremiumAwarded;
    private int totalDisabilityPremiumAwarded;
    private int totalSevereDisabilityPremiumAwarded;
    private int totalDisabledChildPremiumAwarded;
    private int totalCarePremiumAwarded;
    private int totalEnhancedDisabilityPremiumAwarded;
    private int totalBereavementPremiumAwarded;
    //private int PartnerFlag;
    //private String PartnersStartDate;
    //private String PartnersEndDate;
    //private String PartnersNationalInsuranceNumber;
    private int totalPartnersStudentIndicator;
    private int totalPartnersAssessedIncomeFigure;
    private int totalPartnersAdjustedAssessedIncomeFigure;
    private int totalPartnersGrossWeeklyIncomeFromEmployment;
    private int totalPartnersNetWeeklyIncomeFromEmployment;
    private int totalPartnersGrossWeeklyIncomeFromSelfEmployment;
    private int totalPartnersNetWeeklyIncomeFromSelfEmployment;
    private int totalPartnersTotalAmountOfEarningsDisregarded;
    private int totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    private int totalPartnersIncomeFromAttendanceAllowance;
    private int totalPartnersIncomeFromBusinessStartUpAllowance;
    private int totalPartnersIncomeFromChildBenefit;
    private int totalPartnersIncomeFromPersonalPension;
    private int totalPartnersIncomeFromSevereDisabilityAllowance;
    private int totalPartnersIncomeFromMaternityAllowance;
    private int totalPartnersIncomeFromContributionBasedJobSeekersAllowance;
    private int totalPartnersIncomeFromStudentGrantLoan;
    private int totalPartnersIncomeFromSubTenants;
    private int totalPartnersIncomeFromBoarders;
    private int totalPartnersIncomeFromTrainingForWorkCommunityAction;
    private int totalPartnersIncomeFromIncapacityBenefitShortTermLower;
    private int totalPartnersIncomeFromIncapacityBenefitShortTermHigher;
    private int totalPartnersIncomeFromIncapacityBenefitLongTerm;
    private int totalPartnersIncomeFromNewDeal50PlusEmploymentCredit;
    private int totalPartnersIncomeFromNewTaxCredits;
    private int totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent;
    private int totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent;
    private int totalPartnersIncomeFromGovernemntTraining;
    private int totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit;
    private int totalPartnersIncomeFromCarersAllowance;
    private int totalPartnersIncomeFromStatuatorySickPay;
    private int totalPartnersIncomeFromStatutoryMaternityPaternityPay;
    private int totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    private int totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP;
    private int totalPartnersIncomeFromWarMobilitySupplement;
    private int totalPartnersIncomeFromWidowsWidowersPension;
    private int totalPartnersIncomeFromBereavementAllowance;
    private int totalPartnersIncomeFromWidowedParentsAllowance;
    private int totalPartnersIncomeFromYouthTrainingScheme;
    private int totalPartnersOtherIncome;
    private int totalPartnersTotalAmountOfIncomeDisregarded;
    //private String DateOverPaymentDetectionActivityInitiatedOnCase;
    //private int ReasonForOverpaymentDetectionActivity;
    //private int MethodOfOverpayentDetectionActivity;
    //private String DoesTheOverpaymentDetectionActivityConstituteAFullReview;
    //private String DateOverPaymentDetectionActivityIsCompleted;
    //private int OutcomeOfOverPaymentDetectionActivity;
    private int totalClaimantsGenderFemale;
    private int totalClaimantsGenderMale;
    //private String PartnersDateOfBirth;
    //private int RentAllowanceMethodOfPayment;
    //private int RentAllowancePaymentDestination;
    private int totalContractualRentAmount;
    //private int TimePeriodContractualRentFigureCovers;
    private int totalClaimantsIncomeFromPensionCreditSavingsCredit;
    private int totalPartnersIncomeFromPensionCreditSavingsCredit;
    private int totalClaimantsIncomeFromMaintenancePayments;
    private int totalPartnersIncomeFromMaintenancePayments;
    private int totalClaimantsIncomeFromOccupationalPension;
    private int totalPartnersIncomeFromOccupationalPension;
    private int totalClaimantsIncomeFromWidowsBenefit;
    private int totalPartnersIncomeFromWidowsBenefit;
    //private String CTBClaimEntitlementStartDate;
    //private String DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    //private String DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    private int totalTotalNumberOfRooms;
    //private int NonSelfContainedAccomodation;
    //private int TypeOfLHANumberOfRoomsEntitedTo;
    //private int TransitionalProtectionFormNationalRolloutOfLHA;
    //private String Locality;
    private int totalValueOfLHA;
    //private int ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    //private int ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    private int totalPartnersGenderFemale;
    private int totalPartnersGenderMale;
    private int totalNonDependantGrossWeeklyIncomeFromRemunerativeWork;
    //private String HBClaimTreatAsDateMade;
    //private int SourceOfMostRecentHBClaim;
    //private int DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim;
    //private String DateOfFirstHBPaymentRentAllowanceOnly;
    //private int WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly;
    //private int WasThereABackdatedAwardMadeOnTheHBClaim;
    //private String DateHBBackdatingFrom;
    //private String DateHBBackdatingTo;
    private int totalTotalAmountOfBackdatedHBAwarded;
    //private String CTBClaimTreatAsMadeDate;
    //private int SourceOfTheMostRecentCTBClaim;
    //private int DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim;
    //private int WasThereABackdatedAwardMadeOnTheCTBClaim;
    //private String DateCTBBackdatingFrom;
    //private String DateCTBBackdatingTo;
    private int totalTotalAmountOfBackdatedCTBAwarded;
    //private int InThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly;
    //private int IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation;
    //private String DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT;
    private int totalPartnersTotalCapital;
    private int totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure;
    private double totalClaimantsTotalHoursOfRemunerativeWorkPerWeek;
    private double totalPartnersTotalHoursOfRemunerativeWorkPerWeek;
    //private int TotalHBPaymentsCreditsSinceLastExtract;
    //private int TotalCTBPaymentsCreditsSinceLastExtract;
    private int ClaimantsEthnicGroup;

    //private int NewWeeklyHBEntitlementAfterTheChange;
    //private int NewWeeklyCTBEntitlementAfterTheChange;
    //private int TypeOfChange;
    //private String DateLAFirstNotifiedOfChangeInClaimDetails;
    //private String DateChangeOfDetailsAreEffectiveFrom;
    //private int IfNotAnnualUpratingHowWasTheChangeIdentified;
    //private String DateSupercessionDecisionWasMadeOnTheHBClaim;
    //private String DateSupercessionDecisionWasMadeOnTheCTBClaim;
    //private String DateApplicationForRevisionReconsiderationReceived;
    //private String DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration;
    //private String DateAppealApplicationWasLodged;
    //private int OutcomeOfAppealApplication;
    //private String DateOfOutcomeOfAppealApplication;
    //private String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim;
    //private String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim;
    //private String DateCouncilTaxPayable;
    //private String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim;
    //private String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;
    //private int SoftwareProvider;
    //private String StaffingFTE;
    //private int ContractingOutHandlingAndMaintenanceOfHBCTB;
    //private int ContractingOutCounterFraudWorkRelatingToHBCTB;
    //private int NumberOfBedroomsForLHARolloutCasesOnly;
    //private String PartnersDateOfDeath;
    //private int JointTenancyFlag;
    //private int AppointeeFlag;
    //private int RentFreeWeeksIndicator;
    //private String LastPaidToDate;
    //private int WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability;
    //private int ClaimantsWeeklyIncomeFromESABasicElement;
    //private int PartnersWeeklyIncomeFromESABasicElement;
    //private int ClaimantsWeeklyIncomeFromESAWRAGElement;
    //private int PartnersWeeklyIncomeFromESAWRAGElement;
    //private int ClaimantsWeeklyIncomeFromESASCGElement;
    //private int PartnersWeeklyIncomeFromESASCGElement;
    //private String WRAGPremiumFlag;
    //private String SCGPremiumFlag;
    //private int SubRecordType;
    public DW_SHBE_RecordAggregate() {
    }

    @Override
    public String toString() {
        return "totalClaimCount " + getTotalClaimCount()
                + ",totalHBClaimCount " + getTotalHBClaimCount()
                + ",totalCTBClaimCount " + getTotalCTBClaimCount()
                + ",totalTenancyType1Count " + getTotalTenancyType1Count()
                + ",totalTenancyType2Count " + getTotalTenancyType2Count()
                + ",totalTenancyType3Count " + getTotalTenancyType3Count()
                + ",totalTenancyType4Count " + getTotalTenancyType4Count()
                + ",totalTenancyType5Count " + getTotalTenancyType5Count()
                + ",totalTenancyType6Count " + getTotalTenancyType6Count()
                + ",totalTenancyType7Count " + getTotalTenancyType7Count()
                + ",totalTenancyType8Count " + getTotalTenancyType8Count()
                + ",totalTenancyType9Count " + getTotalTenancyType9Count()
                + ",totalNumberOfChildDependents " + getTotalNumberOfChildDependents()
                + ",totalNumberOfNonDependents " + getTotalNumberOfNonDependents()
                + ",totalNonDependentStatus0 " + getTotalNonDependentStatus0()
                + ",totalNonDependentStatus1 " + getTotalNonDependentStatus1()
                + ",totalNonDependentStatus2 " + getTotalNonDependentStatus2()
                + ",totalNonDependentStatus3 " + getTotalNonDependentStatus3()
                + ",totalNonDependentStatus4 " + getTotalNonDependentStatus4()
                + ",totalNonDependentStatus5 " + getTotalNonDependentStatus5()
                + ",totalNonDependentStatus6 " + getTotalNonDependentStatus6()
                + ",totalNonDependentStatus7 " + getTotalNonDependentStatus7()
                + ",totalNonDependentStatus8 " + getTotalNonDependentStatus8()
                + ",totalStatusOfHBClaimAtExtractDate0 " + getTotalStatusOfHBClaimAtExtractDate0()
                + ",totalStatusOfHBClaimAtExtractDate1 " + getTotalStatusOfHBClaimAtExtractDate1()
                + ",totalStatusOfHBClaimAtExtractDate2 " + getTotalStatusOfHBClaimAtExtractDate2()
                + ",totalStatusOfCTBClaimAtExtractDate0 " + getTotalStatusOfCTBClaimAtExtractDate0()
                + ",totalStatusOfCTBClaimAtExtractDate1 " + getTotalStatusOfCTBClaimAtExtractDate1()
                + ",totalStatusOfCTBClaimAtExtractDate2 " + getTotalStatusOfCTBClaimAtExtractDate2()
                + ",totalOutcomeOfFirstDecisionOnMostRecentHBClaim1 " + getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim1()
                + ",totalOutcomeOfFirstDecisionOnMostRecentHBClaim2 " + getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim2()
                + ",totalOutcomeOfFirstDecisionOnMostRecentHBClaim3 " + getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim3()
                + ",totalOutcomeOfFirstDecisionOnMostRecentHBClaim4 " + getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim4()
                + ",totalOutcomeOfFirstDecisionOnMostRecentHBClaim5 " + getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim5()
                + ",totalOutcomeOfFirstDecisionOnMostRecentHBClaim6 " + getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim6()
                + ",totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1 " + getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim1()
                + ",totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2 " + getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim2()
                + ",totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3 " + getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim3()
                + ",totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4 " + getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim4()
                + ",totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5 " + getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim5()
                + ",totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6 " + getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim6()
                + ",totalWeeklyHousingBenefitEntitlement " + getTotalWeeklyHousingBenefitEntitlement()
                + ",totalWeeklyCouncilTaxBenefitEntitlement " + getTotalWeeklyCouncilTaxBenefitEntitlement()
                + ",totalLHARegulationsApplied0 " + getTotalLHARegulationsApplied0()
                + ",totalLHARegulationsApplied1 " + getTotalLHARegulationsApplied1()
                + ",totalWeeklyMaximumRent " + getTotalWeeklyMaximumRent()
                + ",totalClaimantsAssessedIncomeFigure " + getTotalClaimantsAssessedIncomeFigure()
                + ",totalClaimantsAdjustedAssessedIncomeFigure " + getTotalClaimantsAdjustedAssessedIncomeFigure()
                + ",totalClaimantsTotalCapital " + getTotalClaimantsTotalCapital()
                + ",totalClaimantsGrossWeeklyIncomeFromEmployment " + getTotalClaimantsGrossWeeklyIncomeFromEmployment()
                + ",totalClaimantsNetWeeklyIncomeFromEmployment " + getTotalClaimantsNetWeeklyIncomeFromEmployment()
                + ",totalClaimantsGrossWeeklyIncomeFromSelfEmployment " + getTotalClaimantsGrossWeeklyIncomeFromSelfEmployment()
                + ",totalClaimantsNetWeeklyIncomeFromSelfEmployment " + getTotalClaimantsNetWeeklyIncomeFromSelfEmployment()
                + ",totalClaimantsTotalAmountOfEarningsDisregarded " + getTotalClaimantsTotalAmountOfEarningsDisregarded()
                + ",totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded " + getTotalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded()
                + ",totalClaimantsIncomeFromAttendanceAllowance " + getTotalClaimantsIncomeFromAttendanceAllowance()
                + ",totalClaimantsIncomeFromBusinessStartUpAllowance " + getTotalClaimantsIncomeFromBusinessStartUpAllowance()
                + ",totalClaimantsIncomeFromChildBenefit " + getTotalClaimantsIncomeFromChildBenefit()
                + ",totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent " + getTotalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent()
                + ",totalClaimantsIncomeFromPersonalPension " + getTotalClaimantsIncomeFromPersonalPension()
                + ",totalClaimantsIncomeFromSevereDisabilityAllowance " + getTotalClaimantsIncomeFromSevereDisabilityAllowance()
                + ",totalClaimantsIncomeFromMaternityAllowance " + getTotalClaimantsIncomeFromMaternityAllowance()
                + ",totalClaimantsIncomeFromContributionBasedJobSeekersAllowance " + getTotalClaimantsIncomeFromContributionBasedJobSeekersAllowance()
                + ",totalClaimantsIncomeFromStudentGrantLoan " + getTotalClaimantsIncomeFromStudentGrantLoan()
                + ",totalClaimantsIncomeFromSubTenants " + getTotalClaimantsIncomeFromSubTenants()
                + ",totalClaimantsIncomeFromBoarders " + getTotalClaimantsIncomeFromBoarders()
                + ",totalClaimantsIncomeFromTrainingForWorkCommunityAction " + getTotalClaimantsIncomeFromTrainingForWorkCommunityAction()
                + ",totalClaimantsIncomeFromIncapacityBenefitShortTermLower " + getTotalClaimantsIncomeFromIncapacityBenefitShortTermLower()
                + ",totalClaimantsIncomeFromIncapacityBenefitShortTermHigher " + getTotalClaimantsIncomeFromIncapacityBenefitShortTermHigher()
                + ",totalClaimantsIncomeFromIncapacityBenefitLongTerm " + getTotalClaimantsIncomeFromIncapacityBenefitLongTerm()
                + ",totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit " + getTotalClaimantsIncomeFromNewDeal50PlusEmploymentCredit()
                + ",totalClaimantsIncomeFromNewTaxCredits " + getTotalClaimantsIncomeFromNewTaxCredits()
                + ",totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent " + getTotalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent()
                + ",totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent " + getTotalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent()
                + ",totalClaimantsIncomeFromGovernemntTraining " + getTotalClaimantsIncomeFromGovernemntTraining()
                + ",totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit " + getTotalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit()
                + ",totalClaimantsIncomeFromCarersAllowance " + getTotalClaimantsIncomeFromCarersAllowance()
                + ",totalClaimantsIncomeFromStatutoryMaternityPaternityPay " + getTotalClaimantsIncomeFromStatutoryMaternityPaternityPay()
                + ",totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc " + getTotalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc()
                + ",totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP " + getTotalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP()
                + ",totalClaimantsIncomeFromWarMobilitySupplement " + getTotalClaimantsIncomeFromWarMobilitySupplement()
                + ",totalClaimantsIncomeFromWidowsWidowersPension " + getTotalClaimantsIncomeFromWidowsWidowersPension()
                + ",totalClaimantsIncomeFromBereavementAllowance " + getTotalClaimantsIncomeFromBereavementAllowance()
                + ",totalClaimantsIncomeFromWidowedParentsAllowance " + getTotalClaimantsIncomeFromWidowedParentsAllowance()
                + ",totalClaimantsIncomeFromYouthTrainingScheme " + getTotalClaimantsIncomeFromYouthTrainingScheme()
                + ",totalClaimantsIncomeFromStatuatorySickPay " + getTotalClaimantsIncomeFromStatuatorySickPay()
                + ",totalClaimantsOtherIncome " + getTotalClaimantsOtherIncome()
                + ",totalClaimantsTotalAmountOfIncomeDisregarded " + getTotalClaimantsTotalAmountOfIncomeDisregarded()
                + ",totalFamilyPremiumAwarded " + getTotalFamilyPremiumAwarded()
                + ",totalFamilyLoneParentPremiumAwarded " + getTotalFamilyLoneParentPremiumAwarded()
                + ",totalDisabilityPremiumAwarded " + getTotalDisabilityPremiumAwarded()
                + ",totalSevereDisabilityPremiumAwarded " + getTotalSevereDisabilityPremiumAwarded()
                + ",totalDisabledChildPremiumAwarded " + getTotalDisabledChildPremiumAwarded()
                + ",totalCarePremiumAwarded " + getTotalCarePremiumAwarded()
                + ",totalEnhancedDisabilityPremiumAwarded " + getTotalEnhancedDisabilityPremiumAwarded()
                + ",totalBereavementPremiumAwarded " + getTotalBereavementPremiumAwarded()
                + ",totalPartnersStudentIndicator " + getTotalPartnersStudentIndicator()
                + ",totalPartnersAssessedIncomeFigure " + getTotalPartnersAssessedIncomeFigure()
                + ",totalPartnersAdjustedAssessedIncomeFigure " + getTotalPartnersAdjustedAssessedIncomeFigure()
                + ",totalPartnersGrossWeeklyIncomeFromEmployment " + getTotalPartnersGrossWeeklyIncomeFromEmployment()
                + ",totalPartnersNetWeeklyIncomeFromEmployment " + getTotalPartnersNetWeeklyIncomeFromEmployment()
                + ",totalPartnersGrossWeeklyIncomeFromSelfEmployment " + getTotalPartnersGrossWeeklyIncomeFromSelfEmployment()
                + ",totalPartnersNetWeeklyIncomeFromSelfEmployment " + getTotalPartnersNetWeeklyIncomeFromSelfEmployment()
                + ",totalPartnersTotalAmountOfEarningsDisregarded " + getTotalPartnersTotalAmountOfEarningsDisregarded()
                + ",totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded " + getTotalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded()
                + ",totalPartnersIncomeFromAttendanceAllowance " + getTotalPartnersIncomeFromAttendanceAllowance()
                + ",totalPartnersIncomeFromBusinessStartUpAllowance " + getTotalPartnersIncomeFromBusinessStartUpAllowance()
                + ",totalPartnersIncomeFromChildBenefit " + getTotalPartnersIncomeFromChildBenefit()
                + ",totalPartnersIncomeFromPersonalPension " + getTotalPartnersIncomeFromPersonalPension()
                + ",totalPartnersIncomeFromSevereDisabilityAllowance " + getTotalPartnersIncomeFromSevereDisabilityAllowance()
                + ",totalPartnersIncomeFromMaternityAllowance " + getTotalPartnersIncomeFromMaternityAllowance()
                + ",totalPartnersIncomeFromContributionBasedJobSeekersAllowance " + getTotalPartnersIncomeFromContributionBasedJobSeekersAllowance()
                + ",totalPartnersIncomeFromStudentGrantLoan " + getTotalPartnersIncomeFromStudentGrantLoan()
                + ",totalPartnersIncomeFromSubTenants " + getTotalPartnersIncomeFromSubTenants()
                + ",totalPartnersIncomeFromBoarders " + getTotalPartnersIncomeFromBoarders()
                + ",totalPartnersIncomeFromTrainingForWorkCommunityAction " + getTotalPartnersIncomeFromTrainingForWorkCommunityAction()
                + ",totalPartnersIncomeFromIncapacityBenefitShortTermLower " + getTotalPartnersIncomeFromIncapacityBenefitShortTermLower()
                + ",totalPartnersIncomeFromIncapacityBenefitShortTermHigher " + getTotalPartnersIncomeFromIncapacityBenefitShortTermHigher()
                + ",totalPartnersIncomeFromIncapacityBenefitLongTerm " + getTotalPartnersIncomeFromIncapacityBenefitLongTerm()
                + ",totalPartnersIncomeFromNewDeal50PlusEmploymentCredit " + getTotalPartnersIncomeFromNewDeal50PlusEmploymentCredit()
                + ",totalPartnersIncomeFromNewTaxCredits " + getTotalPartnersIncomeFromNewTaxCredits()
                + ",totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent " + getTotalPartnersIncomeFromDisabilityLivingAllowanceCareComponent()
                + ",totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent " + getTotalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent()
                + ",totalPartnersIncomeFromGovernemntTraining " + getTotalPartnersIncomeFromGovernemntTraining()
                + ",totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit " + getTotalPartnersIncomeFromIndustrialInjuriesDisablementBenefit()
                + ",totalPartnersIncomeFromCarersAllowance " + getTotalPartnersIncomeFromCarersAllowance()
                + ",totalPartnersIncomeFromStatuatorySickPay " + getTotalPartnersIncomeFromStatuatorySickPay()
                + ",totalPartnersIncomeFromStatutoryMaternityPaternityPay " + getTotalPartnersIncomeFromStatutoryMaternityPaternityPay()
                + ",totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc " + getTotalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc()
                + ",totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP " + getTotalPartnersIncomeFromWarDisablementPensionArmedForcesGIP()
                + ",totalPartnersIncomeFromWarMobilitySupplement " + getTotalPartnersIncomeFromWarMobilitySupplement()
                + ",totalPartnersIncomeFromWidowsWidowersPension " + getTotalPartnersIncomeFromWidowsWidowersPension()
                + ",totalPartnersIncomeFromBereavementAllowance " + getTotalPartnersIncomeFromBereavementAllowance()
                + ",totalPartnersIncomeFromWidowedParentsAllowance " + getTotalPartnersIncomeFromWidowedParentsAllowance()
                + ",totalPartnersIncomeFromYouthTrainingScheme " + getTotalPartnersIncomeFromYouthTrainingScheme()
                + ",totalPartnersOtherIncome " + getTotalPartnersOtherIncome()
                + ",totalPartnersTotalAmountOfIncomeDisregarded " + getTotalPartnersTotalAmountOfIncomeDisregarded()
                + ",totalClaimantsGenderFemale " + getTotalClaimantsGenderFemale()
                + ",totalClaimantsGenderMale " + getTotalClaimantsGenderMale()
                + ",totalContractualRentAmount " + getTotalContractualRentAmount()
                + ",totalClaimantsIncomeFromPensionCreditSavingsCredit " + getTotalClaimantsIncomeFromPensionCreditSavingsCredit()
                + ",totalPartnersIncomeFromPensionCreditSavingsCredit " + getTotalPartnersIncomeFromPensionCreditSavingsCredit()
                + ",totalClaimantsIncomeFromMaintenancePayments " + getTotalClaimantsIncomeFromMaintenancePayments()
                + ",totalPartnersIncomeFromMaintenancePayments " + getTotalPartnersIncomeFromMaintenancePayments()
                + ",totalClaimantsIncomeFromOccupationalPension " + getTotalClaimantsIncomeFromOccupationalPension()
                + ",totalPartnersIncomeFromOccupationalPension " + getTotalPartnersIncomeFromOccupationalPension()
                + ",totalClaimantsIncomeFromWidowsBenefit " + getTotalClaimantsIncomeFromWidowsBenefit()
                + ",totalPartnersIncomeFromWidowsBenefit " + getTotalPartnersIncomeFromWidowsBenefit()
                + ",totalTotalNumberOfRooms " + getTotalTotalNumberOfRooms()
                + ",totalValueOfLHA " + getTotalValueOfLHA()
                + ",totalPartnersGenderFemale " + getTotalPartnersGenderFemale()
                + ",totalPartnersGenderMale " + getTotalPartnersGenderMale()
                + ",totalNonDependantGrossWeeklyIncomeFromRemunerativeWork " + getTotalNonDependantGrossWeeklyIncomeFromRemunerativeWork()
                + ",totalTotalAmountOfBackdatedHBAwarded " + getTotalTotalAmountOfBackdatedHBAwarded()
                + ",totalTotalAmountOfBackdatedCTBAwarded " + getTotalTotalAmountOfBackdatedCTBAwarded()
                + ",totalPartnersTotalCapital " + getTotalPartnersTotalCapital()
                + ",totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure " + getTotalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure()
                + ",totalClaimantsTotalHoursOfRemunerativeWorkPerWeek " + getTotalClaimantsTotalHoursOfRemunerativeWorkPerWeek()
                + ",totalPartnersTotalHoursOfRemunerativeWorkPerWeek " + getTotalPartnersTotalHoursOfRemunerativeWorkPerWeek();
    }

    /**
     * @return the totalClaimCount
     */
    public int getTotalClaimCount() {
        return totalClaimCount;
    }

    /**
     * @param totalClaimCount the totalClaimCount to set
     */
    public void setTotalClaimCount(int totalClaimCount) {
        this.totalClaimCount = totalClaimCount;
    }

    /**
     * @return the totalHBClaimCount
     */
    public int getTotalHBClaimCount() {
        return totalHBClaimCount;
    }

    /**
     * @param totalHBClaimCount the totalHBClaimCount to set
     */
    public void setTotalHBClaimCount(int totalHBClaimCount) {
        this.totalHBClaimCount = totalHBClaimCount;
    }

    /**
     * @return the totalCTBClaimCount
     */
    public int getTotalCTBClaimCount() {
        return totalCTBClaimCount;
    }

    /**
     * @param totalCTBClaimCount the totalCTBClaimCount to set
     */
    public void setTotalCTBClaimCount(int totalCTBClaimCount) {
        this.totalCTBClaimCount = totalCTBClaimCount;
    }

    /**
     * @return the totalTenancyType1Count
     */
    public int getTotalTenancyType1Count() {
        return totalTenancyType1Count;
    }

    /**
     * @param totalTenancyType1Count the totalTenancyType1Count to set
     */
    public void setTotalTenancyType1Count(int totalTenancyType1Count) {
        this.totalTenancyType1Count = totalTenancyType1Count;
    }

    /**
     * @return the totalTenancyType2Count
     */
    public int getTotalTenancyType2Count() {
        return totalTenancyType2Count;
    }

    /**
     * @param totalTenancyType2Count the totalTenancyType2Count to set
     */
    public void setTotalTenancyType2Count(int totalTenancyType2Count) {
        this.totalTenancyType2Count = totalTenancyType2Count;
    }

    /**
     * @return the totalTenancyType3Count
     */
    public int getTotalTenancyType3Count() {
        return totalTenancyType3Count;
    }

    /**
     * @param totalTenancyType3Count the totalTenancyType3Count to set
     */
    public void setTotalTenancyType3Count(int totalTenancyType3Count) {
        this.totalTenancyType3Count = totalTenancyType3Count;
    }

    /**
     * @return the totalTenancyType4Count
     */
    public int getTotalTenancyType4Count() {
        return totalTenancyType4Count;
    }

    /**
     * @param totalTenancyType4Count the totalTenancyType4Count to set
     */
    public void setTotalTenancyType4Count(int totalTenancyType4Count) {
        this.totalTenancyType4Count = totalTenancyType4Count;
    }

    /**
     * @return the totalTenancyType5Count
     */
    public int getTotalTenancyType5Count() {
        return totalTenancyType5Count;
    }

    /**
     * @param totalTenancyType5Count the totalTenancyType5Count to set
     */
    public void setTotalTenancyType5Count(int totalTenancyType5Count) {
        this.totalTenancyType5Count = totalTenancyType5Count;
    }

    /**
     * @return the totalTenancyType6Count
     */
    public int getTotalTenancyType6Count() {
        return totalTenancyType6Count;
    }

    /**
     * @param totalTenancyType6Count the totalTenancyType6Count to set
     */
    public void setTotalTenancyType6Count(int totalTenancyType6Count) {
        this.totalTenancyType6Count = totalTenancyType6Count;
    }

    /**
     * @return the totalTenancyType7Count
     */
    public int getTotalTenancyType7Count() {
        return totalTenancyType7Count;
    }

    /**
     * @param totalTenancyType7Count the totalTenancyType7Count to set
     */
    public void setTotalTenancyType7Count(int totalTenancyType7Count) {
        this.totalTenancyType7Count = totalTenancyType7Count;
    }

    /**
     * @return the totalTenancyType8Count
     */
    public int getTotalTenancyType8Count() {
        return totalTenancyType8Count;
    }

    /**
     * @param totalTenancyType8Count the totalTenancyType8Count to set
     */
    public void setTotalTenancyType8Count(int totalTenancyType8Count) {
        this.totalTenancyType8Count = totalTenancyType8Count;
    }

    /**
     * @return the totalTenancyType9Count
     */
    public int getTotalTenancyType9Count() {
        return totalTenancyType9Count;
    }

    /**
     * @param totalTenancyType9Count the totalTenancyType9Count to set
     */
    public void setTotalTenancyType9Count(int totalTenancyType9Count) {
        this.totalTenancyType9Count = totalTenancyType9Count;
    }

    /**
     * @return the totalNumberOfChildDependents
     */
    public int getTotalNumberOfChildDependents() {
        return totalNumberOfChildDependents;
    }

    /**
     * @param totalNumberOfChildDependents the totalNumberOfChildDependents to
     * set
     */
    public void setTotalNumberOfChildDependents(int totalNumberOfChildDependents) {
        this.totalNumberOfChildDependents = totalNumberOfChildDependents;
    }

    /**
     * @return the totalNumberOfNonDependents
     */
    public int getTotalNumberOfNonDependents() {
        return totalNumberOfNonDependents;
    }

    /**
     * @param totalNumberOfNonDependents the totalNumberOfNonDependents to set
     */
    public void setTotalNumberOfNonDependents(int totalNumberOfNonDependents) {
        this.totalNumberOfNonDependents = totalNumberOfNonDependents;
    }

    /**
     * @return the totalNonDependentStatus0
     */
    public int getTotalNonDependentStatus0() {
        return totalNonDependentStatus0;
    }

    /**
     * @param totalNonDependentStatus0 the totalNonDependentStatus0 to set
     */
    public void setTotalNonDependentStatus0(int totalNonDependentStatus0) {
        this.totalNonDependentStatus0 = totalNonDependentStatus0;
    }

    /**
     * @return the totalNonDependentStatus1
     */
    public int getTotalNonDependentStatus1() {
        return totalNonDependentStatus1;
    }

    /**
     * @param totalNonDependentStatus1 the totalNonDependentStatus1 to set
     */
    public void setTotalNonDependentStatus1(int totalNonDependentStatus1) {
        this.totalNonDependentStatus1 = totalNonDependentStatus1;
    }

    /**
     * @return the totalNonDependentStatus2
     */
    public int getTotalNonDependentStatus2() {
        return totalNonDependentStatus2;
    }

    /**
     * @param totalNonDependentStatus2 the totalNonDependentStatus2 to set
     */
    public void setTotalNonDependentStatus2(int totalNonDependentStatus2) {
        this.totalNonDependentStatus2 = totalNonDependentStatus2;
    }

    /**
     * @return the totalNonDependentStatus3
     */
    public int getTotalNonDependentStatus3() {
        return totalNonDependentStatus3;
    }

    /**
     * @param totalNonDependentStatus3 the totalNonDependentStatus3 to set
     */
    public void setTotalNonDependentStatus3(int totalNonDependentStatus3) {
        this.totalNonDependentStatus3 = totalNonDependentStatus3;
    }

    /**
     * @return the totalNonDependentStatus4
     */
    public int getTotalNonDependentStatus4() {
        return totalNonDependentStatus4;
    }

    /**
     * @param totalNonDependentStatus4 the totalNonDependentStatus4 to set
     */
    public void setTotalNonDependentStatus4(int totalNonDependentStatus4) {
        this.totalNonDependentStatus4 = totalNonDependentStatus4;
    }

    /**
     * @return the totalNonDependentStatus5
     */
    public int getTotalNonDependentStatus5() {
        return totalNonDependentStatus5;
    }

    /**
     * @param totalNonDependentStatus5 the totalNonDependentStatus5 to set
     */
    public void setTotalNonDependentStatus5(int totalNonDependentStatus5) {
        this.totalNonDependentStatus5 = totalNonDependentStatus5;
    }

    /**
     * @return the totalNonDependentStatus6
     */
    public int getTotalNonDependentStatus6() {
        return totalNonDependentStatus6;
    }

    /**
     * @param totalNonDependentStatus6 the totalNonDependentStatus6 to set
     */
    public void setTotalNonDependentStatus6(int totalNonDependentStatus6) {
        this.totalNonDependentStatus6 = totalNonDependentStatus6;
    }

    /**
     * @return the totalNonDependentStatus7
     */
    public int getTotalNonDependentStatus7() {
        return totalNonDependentStatus7;
    }

    /**
     * @param totalNonDependentStatus7 the totalNonDependentStatus7 to set
     */
    public void setTotalNonDependentStatus7(int totalNonDependentStatus7) {
        this.totalNonDependentStatus7 = totalNonDependentStatus7;
    }

    /**
     * @return the totalNonDependentStatus8
     */
    public int getTotalNonDependentStatus8() {
        return totalNonDependentStatus8;
    }

    /**
     * @param totalNonDependentStatus8 the totalNonDependentStatus8 to set
     */
    public void setTotalNonDependentStatus8(int totalNonDependentStatus8) {
        this.totalNonDependentStatus8 = totalNonDependentStatus8;
    }

    /**
     * @return the totalStatusOfHBClaimAtExtractDate0
     */
    public int getTotalStatusOfHBClaimAtExtractDate0() {
        return totalStatusOfHBClaimAtExtractDate0;
    }

    /**
     * @param totalStatusOfHBClaimAtExtractDate0 the
     * totalStatusOfHBClaimAtExtractDate0 to set
     */
    public void setTotalStatusOfHBClaimAtExtractDate0(int totalStatusOfHBClaimAtExtractDate0) {
        this.totalStatusOfHBClaimAtExtractDate0 = totalStatusOfHBClaimAtExtractDate0;
    }

    /**
     * @return the totalStatusOfHBClaimAtExtractDate1
     */
    public int getTotalStatusOfHBClaimAtExtractDate1() {
        return totalStatusOfHBClaimAtExtractDate1;
    }

    /**
     * @param totalStatusOfHBClaimAtExtractDate1 the
     * totalStatusOfHBClaimAtExtractDate1 to set
     */
    public void setTotalStatusOfHBClaimAtExtractDate1(int totalStatusOfHBClaimAtExtractDate1) {
        this.totalStatusOfHBClaimAtExtractDate1 = totalStatusOfHBClaimAtExtractDate1;
    }

    /**
     * @return the totalStatusOfHBClaimAtExtractDate2
     */
    public int getTotalStatusOfHBClaimAtExtractDate2() {
        return totalStatusOfHBClaimAtExtractDate2;
    }

    /**
     * @param totalStatusOfHBClaimAtExtractDate2 the
     * totalStatusOfHBClaimAtExtractDate2 to set
     */
    public void setTotalStatusOfHBClaimAtExtractDate2(int totalStatusOfHBClaimAtExtractDate2) {
        this.totalStatusOfHBClaimAtExtractDate2 = totalStatusOfHBClaimAtExtractDate2;
    }

    /**
     * @return the totalStatusOfCTBClaimAtExtractDate0
     */
    public int getTotalStatusOfCTBClaimAtExtractDate0() {
        return totalStatusOfCTBClaimAtExtractDate0;
    }

    /**
     * @param totalStatusOfCTBClaimAtExtractDate0 the
     * totalStatusOfCTBClaimAtExtractDate0 to set
     */
    public void setTotalStatusOfCTBClaimAtExtractDate0(int totalStatusOfCTBClaimAtExtractDate0) {
        this.totalStatusOfCTBClaimAtExtractDate0 = totalStatusOfCTBClaimAtExtractDate0;
    }

    /**
     * @return the totalStatusOfCTBClaimAtExtractDate1
     */
    public int getTotalStatusOfCTBClaimAtExtractDate1() {
        return totalStatusOfCTBClaimAtExtractDate1;
    }

    /**
     * @param totalStatusOfCTBClaimAtExtractDate1 the
     * totalStatusOfCTBClaimAtExtractDate1 to set
     */
    public void setTotalStatusOfCTBClaimAtExtractDate1(int totalStatusOfCTBClaimAtExtractDate1) {
        this.totalStatusOfCTBClaimAtExtractDate1 = totalStatusOfCTBClaimAtExtractDate1;
    }

    /**
     * @return the totalStatusOfCTBClaimAtExtractDate2
     */
    public int getTotalStatusOfCTBClaimAtExtractDate2() {
        return totalStatusOfCTBClaimAtExtractDate2;
    }

    /**
     * @param totalStatusOfCTBClaimAtExtractDate2 the
     * totalStatusOfCTBClaimAtExtractDate2 to set
     */
    public void setTotalStatusOfCTBClaimAtExtractDate2(int totalStatusOfCTBClaimAtExtractDate2) {
        this.totalStatusOfCTBClaimAtExtractDate2 = totalStatusOfCTBClaimAtExtractDate2;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentHBClaim1
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim1() {
        return totalOutcomeOfFirstDecisionOnMostRecentHBClaim1;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentHBClaim1 the
     * totalOutcomeOfFirstDecisionOnMostRecentHBClaim1 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim1(int totalOutcomeOfFirstDecisionOnMostRecentHBClaim1) {
        this.totalOutcomeOfFirstDecisionOnMostRecentHBClaim1 = totalOutcomeOfFirstDecisionOnMostRecentHBClaim1;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentHBClaim2
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim2() {
        return totalOutcomeOfFirstDecisionOnMostRecentHBClaim2;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentHBClaim2 the
     * totalOutcomeOfFirstDecisionOnMostRecentHBClaim2 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim2(int totalOutcomeOfFirstDecisionOnMostRecentHBClaim2) {
        this.totalOutcomeOfFirstDecisionOnMostRecentHBClaim2 = totalOutcomeOfFirstDecisionOnMostRecentHBClaim2;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentHBClaim3
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim3() {
        return totalOutcomeOfFirstDecisionOnMostRecentHBClaim3;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentHBClaim3 the
     * totalOutcomeOfFirstDecisionOnMostRecentHBClaim3 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim3(int totalOutcomeOfFirstDecisionOnMostRecentHBClaim3) {
        this.totalOutcomeOfFirstDecisionOnMostRecentHBClaim3 = totalOutcomeOfFirstDecisionOnMostRecentHBClaim3;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentHBClaim4
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim4() {
        return totalOutcomeOfFirstDecisionOnMostRecentHBClaim4;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentHBClaim4 the
     * totalOutcomeOfFirstDecisionOnMostRecentHBClaim4 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim4(int totalOutcomeOfFirstDecisionOnMostRecentHBClaim4) {
        this.totalOutcomeOfFirstDecisionOnMostRecentHBClaim4 = totalOutcomeOfFirstDecisionOnMostRecentHBClaim4;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentHBClaim5
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim5() {
        return totalOutcomeOfFirstDecisionOnMostRecentHBClaim5;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentHBClaim5 the
     * totalOutcomeOfFirstDecisionOnMostRecentHBClaim5 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim5(int totalOutcomeOfFirstDecisionOnMostRecentHBClaim5) {
        this.totalOutcomeOfFirstDecisionOnMostRecentHBClaim5 = totalOutcomeOfFirstDecisionOnMostRecentHBClaim5;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentHBClaim6
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentHBClaim6() {
        return totalOutcomeOfFirstDecisionOnMostRecentHBClaim6;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentHBClaim6 the
     * totalOutcomeOfFirstDecisionOnMostRecentHBClaim6 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentHBClaim6(int totalOutcomeOfFirstDecisionOnMostRecentHBClaim6) {
        this.totalOutcomeOfFirstDecisionOnMostRecentHBClaim6 = totalOutcomeOfFirstDecisionOnMostRecentHBClaim6;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim1() {
        return totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1 the
     * totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim1(int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1) {
        this.totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1 = totalOutcomeOfFirstDecisionOnMostRecentCTBClaim1;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim2() {
        return totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2 the
     * totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim2(int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2) {
        this.totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2 = totalOutcomeOfFirstDecisionOnMostRecentCTBClaim2;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim3() {
        return totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3 the
     * totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim3(int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3) {
        this.totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3 = totalOutcomeOfFirstDecisionOnMostRecentCTBClaim3;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim4() {
        return totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4 the
     * totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim4(int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4) {
        this.totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4 = totalOutcomeOfFirstDecisionOnMostRecentCTBClaim4;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim5() {
        return totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5 the
     * totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim5(int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5) {
        this.totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5 = totalOutcomeOfFirstDecisionOnMostRecentCTBClaim5;
    }

    /**
     * @return the totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6
     */
    public int getTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim6() {
        return totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6;
    }

    /**
     * @param totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6 the
     * totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6 to set
     */
    public void setTotalOutcomeOfFirstDecisionOnMostRecentCTBClaim6(int totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6) {
        this.totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6 = totalOutcomeOfFirstDecisionOnMostRecentCTBClaim6;
    }

    /**
     * @return the totalWeeklyHousingBenefitEntitlement
     */
    public long getTotalWeeklyHousingBenefitEntitlement() {
        return totalWeeklyHousingBenefitEntitlement;
    }

    /**
     * @param totalWeeklyHousingBenefitEntitlement the
     * totalWeeklyHousingBenefitEntitlement to set
     */
    public void setTotalWeeklyHousingBenefitEntitlement(long totalWeeklyHousingBenefitEntitlement) {
        this.totalWeeklyHousingBenefitEntitlement = totalWeeklyHousingBenefitEntitlement;
    }

    /**
     * @return the totalWeeklyCouncilTaxBenefitEntitlement
     */
    public long getTotalWeeklyCouncilTaxBenefitEntitlement() {
        return totalWeeklyCouncilTaxBenefitEntitlement;
    }

    /**
     * @param totalWeeklyCouncilTaxBenefitEntitlement the
     * totalWeeklyCouncilTaxBenefitEntitlement to set
     */
    public void setTotalWeeklyCouncilTaxBenefitEntitlement(long totalWeeklyCouncilTaxBenefitEntitlement) {
        this.totalWeeklyCouncilTaxBenefitEntitlement = totalWeeklyCouncilTaxBenefitEntitlement;
    }

    /**
     * @return the totalLHARegulationsApplied0
     */
    public int getTotalLHARegulationsApplied0() {
        return totalLHARegulationsApplied0;
    }

    /**
     * @param totalLHARegulationsApplied0 the totalLHARegulationsApplied0 to set
     */
    public void setTotalLHARegulationsApplied0(int totalLHARegulationsApplied0) {
        this.totalLHARegulationsApplied0 = totalLHARegulationsApplied0;
    }

    /**
     * @return the totalLHARegulationsApplied1
     */
    public int getTotalLHARegulationsApplied1() {
        return totalLHARegulationsApplied1;
    }

    /**
     * @param totalLHARegulationsApplied1 the totalLHARegulationsApplied1 to set
     */
    public void setTotalLHARegulationsApplied1(int totalLHARegulationsApplied1) {
        this.totalLHARegulationsApplied1 = totalLHARegulationsApplied1;
    }

    /**
     * @return the totalWeeklyMaximumRent
     */
    public int getTotalWeeklyMaximumRent() {
        return totalWeeklyMaximumRent;
    }

    /**
     * @param totalWeeklyMaximumRent the totalWeeklyMaximumRent to set
     */
    public void setTotalWeeklyMaximumRent(int totalWeeklyMaximumRent) {
        this.totalWeeklyMaximumRent = totalWeeklyMaximumRent;
    }

    /**
     * @return the totalClaimantsAssessedIncomeFigure
     */
    public int getTotalClaimantsAssessedIncomeFigure() {
        return totalClaimantsAssessedIncomeFigure;
    }

    /**
     * @param totalClaimantsAssessedIncomeFigure the
     * totalClaimantsAssessedIncomeFigure to set
     */
    public void setTotalClaimantsAssessedIncomeFigure(int totalClaimantsAssessedIncomeFigure) {
        this.totalClaimantsAssessedIncomeFigure = totalClaimantsAssessedIncomeFigure;
    }

    /**
     * @return the totalClaimantsAdjustedAssessedIncomeFigure
     */
    public int getTotalClaimantsAdjustedAssessedIncomeFigure() {
        return totalClaimantsAdjustedAssessedIncomeFigure;
    }

    /**
     * @param totalClaimantsAdjustedAssessedIncomeFigure the
     * totalClaimantsAdjustedAssessedIncomeFigure to set
     */
    public void setTotalClaimantsAdjustedAssessedIncomeFigure(int totalClaimantsAdjustedAssessedIncomeFigure) {
        this.totalClaimantsAdjustedAssessedIncomeFigure = totalClaimantsAdjustedAssessedIncomeFigure;
    }

    /**
     * @return the totalClaimantsTotalCapital
     */
    public int getTotalClaimantsTotalCapital() {
        return totalClaimantsTotalCapital;
    }

    /**
     * @param totalClaimantsTotalCapital the totalClaimantsTotalCapital to set
     */
    public void setTotalClaimantsTotalCapital(int totalClaimantsTotalCapital) {
        this.totalClaimantsTotalCapital = totalClaimantsTotalCapital;
    }

    /**
     * @return the totalClaimantsGrossWeeklyIncomeFromEmployment
     */
    public int getTotalClaimantsGrossWeeklyIncomeFromEmployment() {
        return totalClaimantsGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @param totalClaimantsGrossWeeklyIncomeFromEmployment the
     * totalClaimantsGrossWeeklyIncomeFromEmployment to set
     */
    public void setTotalClaimantsGrossWeeklyIncomeFromEmployment(int totalClaimantsGrossWeeklyIncomeFromEmployment) {
        this.totalClaimantsGrossWeeklyIncomeFromEmployment = totalClaimantsGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @return the totalClaimantsNetWeeklyIncomeFromEmployment
     */
    public int getTotalClaimantsNetWeeklyIncomeFromEmployment() {
        return totalClaimantsNetWeeklyIncomeFromEmployment;
    }

    /**
     * @param totalClaimantsNetWeeklyIncomeFromEmployment the
     * totalClaimantsNetWeeklyIncomeFromEmployment to set
     */
    public void setTotalClaimantsNetWeeklyIncomeFromEmployment(int totalClaimantsNetWeeklyIncomeFromEmployment) {
        this.totalClaimantsNetWeeklyIncomeFromEmployment = totalClaimantsNetWeeklyIncomeFromEmployment;
    }

    /**
     * @return the totalClaimantsGrossWeeklyIncomeFromSelfEmployment
     */
    public int getTotalClaimantsGrossWeeklyIncomeFromSelfEmployment() {
        return totalClaimantsGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param totalClaimantsGrossWeeklyIncomeFromSelfEmployment the
     * totalClaimantsGrossWeeklyIncomeFromSelfEmployment to set
     */
    public void setTotalClaimantsGrossWeeklyIncomeFromSelfEmployment(int totalClaimantsGrossWeeklyIncomeFromSelfEmployment) {
        this.totalClaimantsGrossWeeklyIncomeFromSelfEmployment = totalClaimantsGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the totalClaimantsNetWeeklyIncomeFromSelfEmployment
     */
    public int getTotalClaimantsNetWeeklyIncomeFromSelfEmployment() {
        return totalClaimantsNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param totalClaimantsNetWeeklyIncomeFromSelfEmployment the
     * totalClaimantsNetWeeklyIncomeFromSelfEmployment to set
     */
    public void setTotalClaimantsNetWeeklyIncomeFromSelfEmployment(int totalClaimantsNetWeeklyIncomeFromSelfEmployment) {
        this.totalClaimantsNetWeeklyIncomeFromSelfEmployment = totalClaimantsNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the totalClaimantsTotalAmountOfEarningsDisregarded
     */
    public int getTotalClaimantsTotalAmountOfEarningsDisregarded() {
        return totalClaimantsTotalAmountOfEarningsDisregarded;
    }

    /**
     * @param totalClaimantsTotalAmountOfEarningsDisregarded the
     * totalClaimantsTotalAmountOfEarningsDisregarded to set
     */
    public void setTotalClaimantsTotalAmountOfEarningsDisregarded(int totalClaimantsTotalAmountOfEarningsDisregarded) {
        this.totalClaimantsTotalAmountOfEarningsDisregarded = totalClaimantsTotalAmountOfEarningsDisregarded;
    }

    /**
     * @return the
     * totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     */
    public int getTotalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded() {
        return totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @param
     * totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded the
     * totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded to
     * set
     */
    public void setTotalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(int totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded) {
        this.totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded = totalClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @return the totalClaimantsIncomeFromAttendanceAllowance
     */
    public int getTotalClaimantsIncomeFromAttendanceAllowance() {
        return totalClaimantsIncomeFromAttendanceAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromAttendanceAllowance the
     * totalClaimantsIncomeFromAttendanceAllowance to set
     */
    public void setTotalClaimantsIncomeFromAttendanceAllowance(int totalClaimantsIncomeFromAttendanceAllowance) {
        this.totalClaimantsIncomeFromAttendanceAllowance = totalClaimantsIncomeFromAttendanceAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromBusinessStartUpAllowance
     */
    public int getTotalClaimantsIncomeFromBusinessStartUpAllowance() {
        return totalClaimantsIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromBusinessStartUpAllowance the
     * totalClaimantsIncomeFromBusinessStartUpAllowance to set
     */
    public void setTotalClaimantsIncomeFromBusinessStartUpAllowance(int totalClaimantsIncomeFromBusinessStartUpAllowance) {
        this.totalClaimantsIncomeFromBusinessStartUpAllowance = totalClaimantsIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromChildBenefit
     */
    public int getTotalClaimantsIncomeFromChildBenefit() {
        return totalClaimantsIncomeFromChildBenefit;
    }

    /**
     * @param totalClaimantsIncomeFromChildBenefit the
     * totalClaimantsIncomeFromChildBenefit to set
     */
    public void setTotalClaimantsIncomeFromChildBenefit(int totalClaimantsIncomeFromChildBenefit) {
        this.totalClaimantsIncomeFromChildBenefit = totalClaimantsIncomeFromChildBenefit;
    }

    /**
     * @return the
     * totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent
     */
    public int getTotalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent() {
        return totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent;
    }

    /**
     * @param totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent the
     * totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent to set
     */
    public void setTotalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent(int totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent) {
        this.totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent = totalClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent;
    }

    /**
     * @return the totalClaimantsIncomeFromPersonalPension
     */
    public int getTotalClaimantsIncomeFromPersonalPension() {
        return totalClaimantsIncomeFromPersonalPension;
    }

    /**
     * @param totalClaimantsIncomeFromPersonalPension the
     * totalClaimantsIncomeFromPersonalPension to set
     */
    public void setTotalClaimantsIncomeFromPersonalPension(int totalClaimantsIncomeFromPersonalPension) {
        this.totalClaimantsIncomeFromPersonalPension = totalClaimantsIncomeFromPersonalPension;
    }

    /**
     * @return the totalClaimantsIncomeFromSevereDisabilityAllowance
     */
    public int getTotalClaimantsIncomeFromSevereDisabilityAllowance() {
        return totalClaimantsIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromSevereDisabilityAllowance the
     * totalClaimantsIncomeFromSevereDisabilityAllowance to set
     */
    public void setTotalClaimantsIncomeFromSevereDisabilityAllowance(int totalClaimantsIncomeFromSevereDisabilityAllowance) {
        this.totalClaimantsIncomeFromSevereDisabilityAllowance = totalClaimantsIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromMaternityAllowance
     */
    public int getTotalClaimantsIncomeFromMaternityAllowance() {
        return totalClaimantsIncomeFromMaternityAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromMaternityAllowance the
     * totalClaimantsIncomeFromMaternityAllowance to set
     */
    public void setTotalClaimantsIncomeFromMaternityAllowance(int totalClaimantsIncomeFromMaternityAllowance) {
        this.totalClaimantsIncomeFromMaternityAllowance = totalClaimantsIncomeFromMaternityAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromContributionBasedJobSeekersAllowance
     */
    public int getTotalClaimantsIncomeFromContributionBasedJobSeekersAllowance() {
        return totalClaimantsIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromContributionBasedJobSeekersAllowance the
     * totalClaimantsIncomeFromContributionBasedJobSeekersAllowance to set
     */
    public void setTotalClaimantsIncomeFromContributionBasedJobSeekersAllowance(int totalClaimantsIncomeFromContributionBasedJobSeekersAllowance) {
        this.totalClaimantsIncomeFromContributionBasedJobSeekersAllowance = totalClaimantsIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromStudentGrantLoan
     */
    public int getTotalClaimantsIncomeFromStudentGrantLoan() {
        return totalClaimantsIncomeFromStudentGrantLoan;
    }

    /**
     * @param totalClaimantsIncomeFromStudentGrantLoan the
     * totalClaimantsIncomeFromStudentGrantLoan to set
     */
    public void setTotalClaimantsIncomeFromStudentGrantLoan(int totalClaimantsIncomeFromStudentGrantLoan) {
        this.totalClaimantsIncomeFromStudentGrantLoan = totalClaimantsIncomeFromStudentGrantLoan;
    }

    /**
     * @return the totalClaimantsIncomeFromSubTenants
     */
    public int getTotalClaimantsIncomeFromSubTenants() {
        return totalClaimantsIncomeFromSubTenants;
    }

    /**
     * @param totalClaimantsIncomeFromSubTenants the
     * totalClaimantsIncomeFromSubTenants to set
     */
    public void setTotalClaimantsIncomeFromSubTenants(int totalClaimantsIncomeFromSubTenants) {
        this.totalClaimantsIncomeFromSubTenants = totalClaimantsIncomeFromSubTenants;
    }

    /**
     * @return the totalClaimantsIncomeFromBoarders
     */
    public int getTotalClaimantsIncomeFromBoarders() {
        return totalClaimantsIncomeFromBoarders;
    }

    /**
     * @param totalClaimantsIncomeFromBoarders the
     * totalClaimantsIncomeFromBoarders to set
     */
    public void setTotalClaimantsIncomeFromBoarders(int totalClaimantsIncomeFromBoarders) {
        this.totalClaimantsIncomeFromBoarders = totalClaimantsIncomeFromBoarders;
    }

    /**
     * @return the totalClaimantsIncomeFromTrainingForWorkCommunityAction
     */
    public int getTotalClaimantsIncomeFromTrainingForWorkCommunityAction() {
        return totalClaimantsIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @param totalClaimantsIncomeFromTrainingForWorkCommunityAction the
     * totalClaimantsIncomeFromTrainingForWorkCommunityAction to set
     */
    public void setTotalClaimantsIncomeFromTrainingForWorkCommunityAction(int totalClaimantsIncomeFromTrainingForWorkCommunityAction) {
        this.totalClaimantsIncomeFromTrainingForWorkCommunityAction = totalClaimantsIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @return the totalClaimantsIncomeFromIncapacityBenefitShortTermLower
     */
    public int getTotalClaimantsIncomeFromIncapacityBenefitShortTermLower() {
        return totalClaimantsIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @param totalClaimantsIncomeFromIncapacityBenefitShortTermLower the
     * totalClaimantsIncomeFromIncapacityBenefitShortTermLower to set
     */
    public void setTotalClaimantsIncomeFromIncapacityBenefitShortTermLower(int totalClaimantsIncomeFromIncapacityBenefitShortTermLower) {
        this.totalClaimantsIncomeFromIncapacityBenefitShortTermLower = totalClaimantsIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @return the totalClaimantsIncomeFromIncapacityBenefitShortTermHigher
     */
    public int getTotalClaimantsIncomeFromIncapacityBenefitShortTermHigher() {
        return totalClaimantsIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @param totalClaimantsIncomeFromIncapacityBenefitShortTermHigher the
     * totalClaimantsIncomeFromIncapacityBenefitShortTermHigher to set
     */
    public void setTotalClaimantsIncomeFromIncapacityBenefitShortTermHigher(int totalClaimantsIncomeFromIncapacityBenefitShortTermHigher) {
        this.totalClaimantsIncomeFromIncapacityBenefitShortTermHigher = totalClaimantsIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @return the totalClaimantsIncomeFromIncapacityBenefitLongTerm
     */
    public int getTotalClaimantsIncomeFromIncapacityBenefitLongTerm() {
        return totalClaimantsIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @param totalClaimantsIncomeFromIncapacityBenefitLongTerm the
     * totalClaimantsIncomeFromIncapacityBenefitLongTerm to set
     */
    public void setTotalClaimantsIncomeFromIncapacityBenefitLongTerm(int totalClaimantsIncomeFromIncapacityBenefitLongTerm) {
        this.totalClaimantsIncomeFromIncapacityBenefitLongTerm = totalClaimantsIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @return the totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit
     */
    public int getTotalClaimantsIncomeFromNewDeal50PlusEmploymentCredit() {
        return totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @param totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit the
     * totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit to set
     */
    public void setTotalClaimantsIncomeFromNewDeal50PlusEmploymentCredit(int totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit) {
        this.totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit = totalClaimantsIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @return the totalClaimantsIncomeFromNewTaxCredits
     */
    public int getTotalClaimantsIncomeFromNewTaxCredits() {
        return totalClaimantsIncomeFromNewTaxCredits;
    }

    /**
     * @param totalClaimantsIncomeFromNewTaxCredits the
     * totalClaimantsIncomeFromNewTaxCredits to set
     */
    public void setTotalClaimantsIncomeFromNewTaxCredits(int totalClaimantsIncomeFromNewTaxCredits) {
        this.totalClaimantsIncomeFromNewTaxCredits = totalClaimantsIncomeFromNewTaxCredits;
    }

    /**
     * @return the
     * totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent
     */
    public int getTotalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent() {
        return totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @param totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent the
     * totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent to set
     */
    public void setTotalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent(int totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent) {
        this.totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent = totalClaimantsIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @return the
     * totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent
     */
    public int getTotalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent() {
        return totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @param totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent
     * the totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent to
     * set
     */
    public void setTotalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent(int totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent) {
        this.totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent = totalClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @return the totalClaimantsIncomeFromGovernemntTraining
     */
    public int getTotalClaimantsIncomeFromGovernemntTraining() {
        return totalClaimantsIncomeFromGovernemntTraining;
    }

    /**
     * @param totalClaimantsIncomeFromGovernemntTraining the
     * totalClaimantsIncomeFromGovernemntTraining to set
     */
    public void setTotalClaimantsIncomeFromGovernemntTraining(int totalClaimantsIncomeFromGovernemntTraining) {
        this.totalClaimantsIncomeFromGovernemntTraining = totalClaimantsIncomeFromGovernemntTraining;
    }

    /**
     * @return the totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit
     */
    public int getTotalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit() {
        return totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @param totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit the
     * totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit to set
     */
    public void setTotalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit(int totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit) {
        this.totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit = totalClaimantsIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @return the totalClaimantsIncomeFromCarersAllowance
     */
    public int getTotalClaimantsIncomeFromCarersAllowance() {
        return totalClaimantsIncomeFromCarersAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromCarersAllowance the
     * totalClaimantsIncomeFromCarersAllowance to set
     */
    public void setTotalClaimantsIncomeFromCarersAllowance(int totalClaimantsIncomeFromCarersAllowance) {
        this.totalClaimantsIncomeFromCarersAllowance = totalClaimantsIncomeFromCarersAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromStatutoryMaternityPaternityPay
     */
    public int getTotalClaimantsIncomeFromStatutoryMaternityPaternityPay() {
        return totalClaimantsIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @param totalClaimantsIncomeFromStatutoryMaternityPaternityPay the
     * totalClaimantsIncomeFromStatutoryMaternityPaternityPay to set
     */
    public void setTotalClaimantsIncomeFromStatutoryMaternityPaternityPay(int totalClaimantsIncomeFromStatutoryMaternityPaternityPay) {
        this.totalClaimantsIncomeFromStatutoryMaternityPaternityPay = totalClaimantsIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @return the
     * totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     */
    public int getTotalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc() {
        return totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @param
     * totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * the
     * totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * to set
     */
    public void setTotalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(int totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc) {
        this.totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc = totalClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @return the totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP
     */
    public int getTotalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP() {
        return totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @param totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP the
     * totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP to set
     */
    public void setTotalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP(int totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP) {
        this.totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP = totalClaimantsIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @return the totalClaimantsIncomeFromWarMobilitySupplement
     */
    public int getTotalClaimantsIncomeFromWarMobilitySupplement() {
        return totalClaimantsIncomeFromWarMobilitySupplement;
    }

    /**
     * @param totalClaimantsIncomeFromWarMobilitySupplement the
     * totalClaimantsIncomeFromWarMobilitySupplement to set
     */
    public void setTotalClaimantsIncomeFromWarMobilitySupplement(int totalClaimantsIncomeFromWarMobilitySupplement) {
        this.totalClaimantsIncomeFromWarMobilitySupplement = totalClaimantsIncomeFromWarMobilitySupplement;
    }

    /**
     * @return the totalClaimantsIncomeFromWidowsWidowersPension
     */
    public int getTotalClaimantsIncomeFromWidowsWidowersPension() {
        return totalClaimantsIncomeFromWidowsWidowersPension;
    }

    /**
     * @param totalClaimantsIncomeFromWidowsWidowersPension the
     * totalClaimantsIncomeFromWidowsWidowersPension to set
     */
    public void setTotalClaimantsIncomeFromWidowsWidowersPension(int totalClaimantsIncomeFromWidowsWidowersPension) {
        this.totalClaimantsIncomeFromWidowsWidowersPension = totalClaimantsIncomeFromWidowsWidowersPension;
    }

    /**
     * @return the totalClaimantsIncomeFromBereavementAllowance
     */
    public int getTotalClaimantsIncomeFromBereavementAllowance() {
        return totalClaimantsIncomeFromBereavementAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromBereavementAllowance the
     * totalClaimantsIncomeFromBereavementAllowance to set
     */
    public void setTotalClaimantsIncomeFromBereavementAllowance(int totalClaimantsIncomeFromBereavementAllowance) {
        this.totalClaimantsIncomeFromBereavementAllowance = totalClaimantsIncomeFromBereavementAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromWidowedParentsAllowance
     */
    public int getTotalClaimantsIncomeFromWidowedParentsAllowance() {
        return totalClaimantsIncomeFromWidowedParentsAllowance;
    }

    /**
     * @param totalClaimantsIncomeFromWidowedParentsAllowance the
     * totalClaimantsIncomeFromWidowedParentsAllowance to set
     */
    public void setTotalClaimantsIncomeFromWidowedParentsAllowance(int totalClaimantsIncomeFromWidowedParentsAllowance) {
        this.totalClaimantsIncomeFromWidowedParentsAllowance = totalClaimantsIncomeFromWidowedParentsAllowance;
    }

    /**
     * @return the totalClaimantsIncomeFromYouthTrainingScheme
     */
    public int getTotalClaimantsIncomeFromYouthTrainingScheme() {
        return totalClaimantsIncomeFromYouthTrainingScheme;
    }

    /**
     * @param totalClaimantsIncomeFromYouthTrainingScheme the
     * totalClaimantsIncomeFromYouthTrainingScheme to set
     */
    public void setTotalClaimantsIncomeFromYouthTrainingScheme(int totalClaimantsIncomeFromYouthTrainingScheme) {
        this.totalClaimantsIncomeFromYouthTrainingScheme = totalClaimantsIncomeFromYouthTrainingScheme;
    }

    /**
     * @return the totalClaimantsIncomeFromStatuatorySickPay
     */
    public int getTotalClaimantsIncomeFromStatuatorySickPay() {
        return totalClaimantsIncomeFromStatuatorySickPay;
    }

    /**
     * @param totalClaimantsIncomeFromStatuatorySickPay the
     * totalClaimantsIncomeFromStatuatorySickPay to set
     */
    public void setTotalClaimantsIncomeFromStatuatorySickPay(int totalClaimantsIncomeFromStatuatorySickPay) {
        this.totalClaimantsIncomeFromStatuatorySickPay = totalClaimantsIncomeFromStatuatorySickPay;
    }

    /**
     * @return the totalClaimantsOtherIncome
     */
    public int getTotalClaimantsOtherIncome() {
        return totalClaimantsOtherIncome;
    }

    /**
     * @param totalClaimantsOtherIncome the totalClaimantsOtherIncome to set
     */
    public void setTotalClaimantsOtherIncome(int totalClaimantsOtherIncome) {
        this.totalClaimantsOtherIncome = totalClaimantsOtherIncome;
    }

    /**
     * @return the totalClaimantsTotalAmountOfIncomeDisregarded
     */
    public int getTotalClaimantsTotalAmountOfIncomeDisregarded() {
        return totalClaimantsTotalAmountOfIncomeDisregarded;
    }

    /**
     * @param totalClaimantsTotalAmountOfIncomeDisregarded the
     * totalClaimantsTotalAmountOfIncomeDisregarded to set
     */
    public void setTotalClaimantsTotalAmountOfIncomeDisregarded(int totalClaimantsTotalAmountOfIncomeDisregarded) {
        this.totalClaimantsTotalAmountOfIncomeDisregarded = totalClaimantsTotalAmountOfIncomeDisregarded;
    }

    /**
     * @return the totalFamilyPremiumAwarded
     */
    public int getTotalFamilyPremiumAwarded() {
        return totalFamilyPremiumAwarded;
    }

    /**
     * @param totalFamilyPremiumAwarded the totalFamilyPremiumAwarded to set
     */
    public void setTotalFamilyPremiumAwarded(int totalFamilyPremiumAwarded) {
        this.totalFamilyPremiumAwarded = totalFamilyPremiumAwarded;
    }

    /**
     * @return the totalFamilyLoneParentPremiumAwarded
     */
    public int getTotalFamilyLoneParentPremiumAwarded() {
        return totalFamilyLoneParentPremiumAwarded;
    }

    /**
     * @param totalFamilyLoneParentPremiumAwarded the
     * totalFamilyLoneParentPremiumAwarded to set
     */
    public void setTotalFamilyLoneParentPremiumAwarded(int totalFamilyLoneParentPremiumAwarded) {
        this.totalFamilyLoneParentPremiumAwarded = totalFamilyLoneParentPremiumAwarded;
    }

    /**
     * @return the totalDisabilityPremiumAwarded
     */
    public int getTotalDisabilityPremiumAwarded() {
        return totalDisabilityPremiumAwarded;
    }

    /**
     * @param totalDisabilityPremiumAwarded the totalDisabilityPremiumAwarded to
     * set
     */
    public void setTotalDisabilityPremiumAwarded(int totalDisabilityPremiumAwarded) {
        this.totalDisabilityPremiumAwarded = totalDisabilityPremiumAwarded;
    }

    /**
     * @return the totalSevereDisabilityPremiumAwarded
     */
    public int getTotalSevereDisabilityPremiumAwarded() {
        return totalSevereDisabilityPremiumAwarded;
    }

    /**
     * @param totalSevereDisabilityPremiumAwarded the
     * totalSevereDisabilityPremiumAwarded to set
     */
    public void setTotalSevereDisabilityPremiumAwarded(int totalSevereDisabilityPremiumAwarded) {
        this.totalSevereDisabilityPremiumAwarded = totalSevereDisabilityPremiumAwarded;
    }

    /**
     * @return the totalDisabledChildPremiumAwarded
     */
    public int getTotalDisabledChildPremiumAwarded() {
        return totalDisabledChildPremiumAwarded;
    }

    /**
     * @param totalDisabledChildPremiumAwarded the
     * totalDisabledChildPremiumAwarded to set
     */
    public void setTotalDisabledChildPremiumAwarded(int totalDisabledChildPremiumAwarded) {
        this.totalDisabledChildPremiumAwarded = totalDisabledChildPremiumAwarded;
    }

    /**
     * @return the totalCarePremiumAwarded
     */
    public int getTotalCarePremiumAwarded() {
        return totalCarePremiumAwarded;
    }

    /**
     * @param totalCarePremiumAwarded the totalCarePremiumAwarded to set
     */
    public void setTotalCarePremiumAwarded(int totalCarePremiumAwarded) {
        this.totalCarePremiumAwarded = totalCarePremiumAwarded;
    }

    /**
     * @return the totalEnhancedDisabilityPremiumAwarded
     */
    public int getTotalEnhancedDisabilityPremiumAwarded() {
        return totalEnhancedDisabilityPremiumAwarded;
    }

    /**
     * @param totalEnhancedDisabilityPremiumAwarded the
     * totalEnhancedDisabilityPremiumAwarded to set
     */
    public void setTotalEnhancedDisabilityPremiumAwarded(int totalEnhancedDisabilityPremiumAwarded) {
        this.totalEnhancedDisabilityPremiumAwarded = totalEnhancedDisabilityPremiumAwarded;
    }

    /**
     * @return the totalBereavementPremiumAwarded
     */
    public int getTotalBereavementPremiumAwarded() {
        return totalBereavementPremiumAwarded;
    }

    /**
     * @param totalBereavementPremiumAwarded the totalBereavementPremiumAwarded
     * to set
     */
    public void setTotalBereavementPremiumAwarded(int totalBereavementPremiumAwarded) {
        this.totalBereavementPremiumAwarded = totalBereavementPremiumAwarded;
    }

    /**
     * @return the totalPartnersStudentIndicator
     */
    public int getTotalPartnersStudentIndicator() {
        return totalPartnersStudentIndicator;
    }

    /**
     * @param totalPartnersStudentIndicator the totalPartnersStudentIndicator to
     * set
     */
    public void setTotalPartnersStudentIndicator(int totalPartnersStudentIndicator) {
        this.totalPartnersStudentIndicator = totalPartnersStudentIndicator;
    }

    /**
     * @return the totalPartnersAssessedIncomeFigure
     */
    public int getTotalPartnersAssessedIncomeFigure() {
        return totalPartnersAssessedIncomeFigure;
    }

    /**
     * @param totalPartnersAssessedIncomeFigure the
     * totalPartnersAssessedIncomeFigure to set
     */
    public void setTotalPartnersAssessedIncomeFigure(int totalPartnersAssessedIncomeFigure) {
        this.totalPartnersAssessedIncomeFigure = totalPartnersAssessedIncomeFigure;
    }

    /**
     * @return the totalPartnersAdjustedAssessedIncomeFigure
     */
    public int getTotalPartnersAdjustedAssessedIncomeFigure() {
        return totalPartnersAdjustedAssessedIncomeFigure;
    }

    /**
     * @param totalPartnersAdjustedAssessedIncomeFigure the
     * totalPartnersAdjustedAssessedIncomeFigure to set
     */
    public void setTotalPartnersAdjustedAssessedIncomeFigure(int totalPartnersAdjustedAssessedIncomeFigure) {
        this.totalPartnersAdjustedAssessedIncomeFigure = totalPartnersAdjustedAssessedIncomeFigure;
    }

    /**
     * @return the totalPartnersGrossWeeklyIncomeFromEmployment
     */
    public int getTotalPartnersGrossWeeklyIncomeFromEmployment() {
        return totalPartnersGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @param totalPartnersGrossWeeklyIncomeFromEmployment the
     * totalPartnersGrossWeeklyIncomeFromEmployment to set
     */
    public void setTotalPartnersGrossWeeklyIncomeFromEmployment(int totalPartnersGrossWeeklyIncomeFromEmployment) {
        this.totalPartnersGrossWeeklyIncomeFromEmployment = totalPartnersGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @return the totalPartnersNetWeeklyIncomeFromEmployment
     */
    public int getTotalPartnersNetWeeklyIncomeFromEmployment() {
        return totalPartnersNetWeeklyIncomeFromEmployment;
    }

    /**
     * @param totalPartnersNetWeeklyIncomeFromEmployment the
     * totalPartnersNetWeeklyIncomeFromEmployment to set
     */
    public void setTotalPartnersNetWeeklyIncomeFromEmployment(int totalPartnersNetWeeklyIncomeFromEmployment) {
        this.totalPartnersNetWeeklyIncomeFromEmployment = totalPartnersNetWeeklyIncomeFromEmployment;
    }

    /**
     * @return the totalPartnersGrossWeeklyIncomeFromSelfEmployment
     */
    public int getTotalPartnersGrossWeeklyIncomeFromSelfEmployment() {
        return totalPartnersGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param totalPartnersGrossWeeklyIncomeFromSelfEmployment the
     * totalPartnersGrossWeeklyIncomeFromSelfEmployment to set
     */
    public void setTotalPartnersGrossWeeklyIncomeFromSelfEmployment(int totalPartnersGrossWeeklyIncomeFromSelfEmployment) {
        this.totalPartnersGrossWeeklyIncomeFromSelfEmployment = totalPartnersGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the totalPartnersNetWeeklyIncomeFromSelfEmployment
     */
    public int getTotalPartnersNetWeeklyIncomeFromSelfEmployment() {
        return totalPartnersNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param totalPartnersNetWeeklyIncomeFromSelfEmployment the
     * totalPartnersNetWeeklyIncomeFromSelfEmployment to set
     */
    public void setTotalPartnersNetWeeklyIncomeFromSelfEmployment(int totalPartnersNetWeeklyIncomeFromSelfEmployment) {
        this.totalPartnersNetWeeklyIncomeFromSelfEmployment = totalPartnersNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the totalPartnersTotalAmountOfEarningsDisregarded
     */
    public int getTotalPartnersTotalAmountOfEarningsDisregarded() {
        return totalPartnersTotalAmountOfEarningsDisregarded;
    }

    /**
     * @param totalPartnersTotalAmountOfEarningsDisregarded the
     * totalPartnersTotalAmountOfEarningsDisregarded to set
     */
    public void setTotalPartnersTotalAmountOfEarningsDisregarded(int totalPartnersTotalAmountOfEarningsDisregarded) {
        this.totalPartnersTotalAmountOfEarningsDisregarded = totalPartnersTotalAmountOfEarningsDisregarded;
    }

    /**
     * @return the
     * totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     */
    public int getTotalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded() {
        return totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @param
     * totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded the
     * totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded to
     * set
     */
    public void setTotalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(int totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded) {
        this.totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded = totalPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @return the totalPartnersIncomeFromAttendanceAllowance
     */
    public int getTotalPartnersIncomeFromAttendanceAllowance() {
        return totalPartnersIncomeFromAttendanceAllowance;
    }

    /**
     * @param totalPartnersIncomeFromAttendanceAllowance the
     * totalPartnersIncomeFromAttendanceAllowance to set
     */
    public void setTotalPartnersIncomeFromAttendanceAllowance(int totalPartnersIncomeFromAttendanceAllowance) {
        this.totalPartnersIncomeFromAttendanceAllowance = totalPartnersIncomeFromAttendanceAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromBusinessStartUpAllowance
     */
    public int getTotalPartnersIncomeFromBusinessStartUpAllowance() {
        return totalPartnersIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @param totalPartnersIncomeFromBusinessStartUpAllowance the
     * totalPartnersIncomeFromBusinessStartUpAllowance to set
     */
    public void setTotalPartnersIncomeFromBusinessStartUpAllowance(int totalPartnersIncomeFromBusinessStartUpAllowance) {
        this.totalPartnersIncomeFromBusinessStartUpAllowance = totalPartnersIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromChildBenefit
     */
    public int getTotalPartnersIncomeFromChildBenefit() {
        return totalPartnersIncomeFromChildBenefit;
    }

    /**
     * @param totalPartnersIncomeFromChildBenefit the
     * totalPartnersIncomeFromChildBenefit to set
     */
    public void setTotalPartnersIncomeFromChildBenefit(int totalPartnersIncomeFromChildBenefit) {
        this.totalPartnersIncomeFromChildBenefit = totalPartnersIncomeFromChildBenefit;
    }

    /**
     * @return the totalPartnersIncomeFromPersonalPension
     */
    public int getTotalPartnersIncomeFromPersonalPension() {
        return totalPartnersIncomeFromPersonalPension;
    }

    /**
     * @param totalPartnersIncomeFromPersonalPension the
     * totalPartnersIncomeFromPersonalPension to set
     */
    public void setTotalPartnersIncomeFromPersonalPension(int totalPartnersIncomeFromPersonalPension) {
        this.totalPartnersIncomeFromPersonalPension = totalPartnersIncomeFromPersonalPension;
    }

    /**
     * @return the totalPartnersIncomeFromSevereDisabilityAllowance
     */
    public int getTotalPartnersIncomeFromSevereDisabilityAllowance() {
        return totalPartnersIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @param totalPartnersIncomeFromSevereDisabilityAllowance the
     * totalPartnersIncomeFromSevereDisabilityAllowance to set
     */
    public void setTotalPartnersIncomeFromSevereDisabilityAllowance(int totalPartnersIncomeFromSevereDisabilityAllowance) {
        this.totalPartnersIncomeFromSevereDisabilityAllowance = totalPartnersIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromMaternityAllowance
     */
    public int getTotalPartnersIncomeFromMaternityAllowance() {
        return totalPartnersIncomeFromMaternityAllowance;
    }

    /**
     * @param totalPartnersIncomeFromMaternityAllowance the
     * totalPartnersIncomeFromMaternityAllowance to set
     */
    public void setTotalPartnersIncomeFromMaternityAllowance(int totalPartnersIncomeFromMaternityAllowance) {
        this.totalPartnersIncomeFromMaternityAllowance = totalPartnersIncomeFromMaternityAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromContributionBasedJobSeekersAllowance
     */
    public int getTotalPartnersIncomeFromContributionBasedJobSeekersAllowance() {
        return totalPartnersIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @param totalPartnersIncomeFromContributionBasedJobSeekersAllowance the
     * totalPartnersIncomeFromContributionBasedJobSeekersAllowance to set
     */
    public void setTotalPartnersIncomeFromContributionBasedJobSeekersAllowance(int totalPartnersIncomeFromContributionBasedJobSeekersAllowance) {
        this.totalPartnersIncomeFromContributionBasedJobSeekersAllowance = totalPartnersIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromStudentGrantLoan
     */
    public int getTotalPartnersIncomeFromStudentGrantLoan() {
        return totalPartnersIncomeFromStudentGrantLoan;
    }

    /**
     * @param totalPartnersIncomeFromStudentGrantLoan the
     * totalPartnersIncomeFromStudentGrantLoan to set
     */
    public void setTotalPartnersIncomeFromStudentGrantLoan(int totalPartnersIncomeFromStudentGrantLoan) {
        this.totalPartnersIncomeFromStudentGrantLoan = totalPartnersIncomeFromStudentGrantLoan;
    }

    /**
     * @return the totalPartnersIncomeFromSubTenants
     */
    public int getTotalPartnersIncomeFromSubTenants() {
        return totalPartnersIncomeFromSubTenants;
    }

    /**
     * @param totalPartnersIncomeFromSubTenants the
     * totalPartnersIncomeFromSubTenants to set
     */
    public void setTotalPartnersIncomeFromSubTenants(int totalPartnersIncomeFromSubTenants) {
        this.totalPartnersIncomeFromSubTenants = totalPartnersIncomeFromSubTenants;
    }

    /**
     * @return the totalPartnersIncomeFromBoarders
     */
    public int getTotalPartnersIncomeFromBoarders() {
        return totalPartnersIncomeFromBoarders;
    }

    /**
     * @param totalPartnersIncomeFromBoarders the
     * totalPartnersIncomeFromBoarders to set
     */
    public void setTotalPartnersIncomeFromBoarders(int totalPartnersIncomeFromBoarders) {
        this.totalPartnersIncomeFromBoarders = totalPartnersIncomeFromBoarders;
    }

    /**
     * @return the totalPartnersIncomeFromTrainingForWorkCommunityAction
     */
    public int getTotalPartnersIncomeFromTrainingForWorkCommunityAction() {
        return totalPartnersIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @param totalPartnersIncomeFromTrainingForWorkCommunityAction the
     * totalPartnersIncomeFromTrainingForWorkCommunityAction to set
     */
    public void setTotalPartnersIncomeFromTrainingForWorkCommunityAction(int totalPartnersIncomeFromTrainingForWorkCommunityAction) {
        this.totalPartnersIncomeFromTrainingForWorkCommunityAction = totalPartnersIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @return the totalPartnersIncomeFromIncapacityBenefitShortTermLower
     */
    public int getTotalPartnersIncomeFromIncapacityBenefitShortTermLower() {
        return totalPartnersIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @param totalPartnersIncomeFromIncapacityBenefitShortTermLower the
     * totalPartnersIncomeFromIncapacityBenefitShortTermLower to set
     */
    public void setTotalPartnersIncomeFromIncapacityBenefitShortTermLower(int totalPartnersIncomeFromIncapacityBenefitShortTermLower) {
        this.totalPartnersIncomeFromIncapacityBenefitShortTermLower = totalPartnersIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @return the totalPartnersIncomeFromIncapacityBenefitShortTermHigher
     */
    public int getTotalPartnersIncomeFromIncapacityBenefitShortTermHigher() {
        return totalPartnersIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @param totalPartnersIncomeFromIncapacityBenefitShortTermHigher the
     * totalPartnersIncomeFromIncapacityBenefitShortTermHigher to set
     */
    public void setTotalPartnersIncomeFromIncapacityBenefitShortTermHigher(int totalPartnersIncomeFromIncapacityBenefitShortTermHigher) {
        this.totalPartnersIncomeFromIncapacityBenefitShortTermHigher = totalPartnersIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @return the totalPartnersIncomeFromIncapacityBenefitLongTerm
     */
    public int getTotalPartnersIncomeFromIncapacityBenefitLongTerm() {
        return totalPartnersIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @param totalPartnersIncomeFromIncapacityBenefitLongTerm the
     * totalPartnersIncomeFromIncapacityBenefitLongTerm to set
     */
    public void setTotalPartnersIncomeFromIncapacityBenefitLongTerm(int totalPartnersIncomeFromIncapacityBenefitLongTerm) {
        this.totalPartnersIncomeFromIncapacityBenefitLongTerm = totalPartnersIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @return the totalPartnersIncomeFromNewDeal50PlusEmploymentCredit
     */
    public int getTotalPartnersIncomeFromNewDeal50PlusEmploymentCredit() {
        return totalPartnersIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @param totalPartnersIncomeFromNewDeal50PlusEmploymentCredit the
     * totalPartnersIncomeFromNewDeal50PlusEmploymentCredit to set
     */
    public void setTotalPartnersIncomeFromNewDeal50PlusEmploymentCredit(int totalPartnersIncomeFromNewDeal50PlusEmploymentCredit) {
        this.totalPartnersIncomeFromNewDeal50PlusEmploymentCredit = totalPartnersIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @return the totalPartnersIncomeFromNewTaxCredits
     */
    public int getTotalPartnersIncomeFromNewTaxCredits() {
        return totalPartnersIncomeFromNewTaxCredits;
    }

    /**
     * @param totalPartnersIncomeFromNewTaxCredits the
     * totalPartnersIncomeFromNewTaxCredits to set
     */
    public void setTotalPartnersIncomeFromNewTaxCredits(int totalPartnersIncomeFromNewTaxCredits) {
        this.totalPartnersIncomeFromNewTaxCredits = totalPartnersIncomeFromNewTaxCredits;
    }

    /**
     * @return the totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent
     */
    public int getTotalPartnersIncomeFromDisabilityLivingAllowanceCareComponent() {
        return totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @param totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent the
     * totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent to set
     */
    public void setTotalPartnersIncomeFromDisabilityLivingAllowanceCareComponent(int totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent) {
        this.totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent = totalPartnersIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @return the
     * totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent
     */
    public int getTotalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent() {
        return totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @param totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent
     * the totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent to
     * set
     */
    public void setTotalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent(int totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent) {
        this.totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent = totalPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @return the totalPartnersIncomeFromGovernemntTraining
     */
    public int getTotalPartnersIncomeFromGovernemntTraining() {
        return totalPartnersIncomeFromGovernemntTraining;
    }

    /**
     * @param totalPartnersIncomeFromGovernemntTraining the
     * totalPartnersIncomeFromGovernemntTraining to set
     */
    public void setTotalPartnersIncomeFromGovernemntTraining(int totalPartnersIncomeFromGovernemntTraining) {
        this.totalPartnersIncomeFromGovernemntTraining = totalPartnersIncomeFromGovernemntTraining;
    }

    /**
     * @return the totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit
     */
    public int getTotalPartnersIncomeFromIndustrialInjuriesDisablementBenefit() {
        return totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @param totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit the
     * totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit to set
     */
    public void setTotalPartnersIncomeFromIndustrialInjuriesDisablementBenefit(int totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit) {
        this.totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit = totalPartnersIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @return the totalPartnersIncomeFromCarersAllowance
     */
    public int getTotalPartnersIncomeFromCarersAllowance() {
        return totalPartnersIncomeFromCarersAllowance;
    }

    /**
     * @param totalPartnersIncomeFromCarersAllowance the
     * totalPartnersIncomeFromCarersAllowance to set
     */
    public void setTotalPartnersIncomeFromCarersAllowance(int totalPartnersIncomeFromCarersAllowance) {
        this.totalPartnersIncomeFromCarersAllowance = totalPartnersIncomeFromCarersAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromStatuatorySickPay
     */
    public int getTotalPartnersIncomeFromStatuatorySickPay() {
        return totalPartnersIncomeFromStatuatorySickPay;
    }

    /**
     * @param totalPartnersIncomeFromStatuatorySickPay the
     * totalPartnersIncomeFromStatuatorySickPay to set
     */
    public void setTotalPartnersIncomeFromStatuatorySickPay(int totalPartnersIncomeFromStatuatorySickPay) {
        this.totalPartnersIncomeFromStatuatorySickPay = totalPartnersIncomeFromStatuatorySickPay;
    }

    /**
     * @return the totalPartnersIncomeFromStatutoryMaternityPaternityPay
     */
    public int getTotalPartnersIncomeFromStatutoryMaternityPaternityPay() {
        return totalPartnersIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @param totalPartnersIncomeFromStatutoryMaternityPaternityPay the
     * totalPartnersIncomeFromStatutoryMaternityPaternityPay to set
     */
    public void setTotalPartnersIncomeFromStatutoryMaternityPaternityPay(int totalPartnersIncomeFromStatutoryMaternityPaternityPay) {
        this.totalPartnersIncomeFromStatutoryMaternityPaternityPay = totalPartnersIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @return the
     * totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     */
    public int getTotalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc() {
        return totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @param
     * totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * the
     * totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * to set
     */
    public void setTotalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(int totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc) {
        this.totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc = totalPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @return the totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP
     */
    public int getTotalPartnersIncomeFromWarDisablementPensionArmedForcesGIP() {
        return totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @param totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP the
     * totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP to set
     */
    public void setTotalPartnersIncomeFromWarDisablementPensionArmedForcesGIP(int totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP) {
        this.totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP = totalPartnersIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @return the totalPartnersIncomeFromWarMobilitySupplement
     */
    public int getTotalPartnersIncomeFromWarMobilitySupplement() {
        return totalPartnersIncomeFromWarMobilitySupplement;
    }

    /**
     * @param totalPartnersIncomeFromWarMobilitySupplement the
     * totalPartnersIncomeFromWarMobilitySupplement to set
     */
    public void setTotalPartnersIncomeFromWarMobilitySupplement(int totalPartnersIncomeFromWarMobilitySupplement) {
        this.totalPartnersIncomeFromWarMobilitySupplement = totalPartnersIncomeFromWarMobilitySupplement;
    }

    /**
     * @return the totalPartnersIncomeFromWidowsWidowersPension
     */
    public int getTotalPartnersIncomeFromWidowsWidowersPension() {
        return totalPartnersIncomeFromWidowsWidowersPension;
    }

    /**
     * @param totalPartnersIncomeFromWidowsWidowersPension the
     * totalPartnersIncomeFromWidowsWidowersPension to set
     */
    public void setTotalPartnersIncomeFromWidowsWidowersPension(int totalPartnersIncomeFromWidowsWidowersPension) {
        this.totalPartnersIncomeFromWidowsWidowersPension = totalPartnersIncomeFromWidowsWidowersPension;
    }

    /**
     * @return the totalPartnersIncomeFromBereavementAllowance
     */
    public int getTotalPartnersIncomeFromBereavementAllowance() {
        return totalPartnersIncomeFromBereavementAllowance;
    }

    /**
     * @param totalPartnersIncomeFromBereavementAllowance the
     * totalPartnersIncomeFromBereavementAllowance to set
     */
    public void setTotalPartnersIncomeFromBereavementAllowance(int totalPartnersIncomeFromBereavementAllowance) {
        this.totalPartnersIncomeFromBereavementAllowance = totalPartnersIncomeFromBereavementAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromWidowedParentsAllowance
     */
    public int getTotalPartnersIncomeFromWidowedParentsAllowance() {
        return totalPartnersIncomeFromWidowedParentsAllowance;
    }

    /**
     * @param totalPartnersIncomeFromWidowedParentsAllowance the
     * totalPartnersIncomeFromWidowedParentsAllowance to set
     */
    public void setTotalPartnersIncomeFromWidowedParentsAllowance(int totalPartnersIncomeFromWidowedParentsAllowance) {
        this.totalPartnersIncomeFromWidowedParentsAllowance = totalPartnersIncomeFromWidowedParentsAllowance;
    }

    /**
     * @return the totalPartnersIncomeFromYouthTrainingScheme
     */
    public int getTotalPartnersIncomeFromYouthTrainingScheme() {
        return totalPartnersIncomeFromYouthTrainingScheme;
    }

    /**
     * @param totalPartnersIncomeFromYouthTrainingScheme the
     * totalPartnersIncomeFromYouthTrainingScheme to set
     */
    public void setTotalPartnersIncomeFromYouthTrainingScheme(int totalPartnersIncomeFromYouthTrainingScheme) {
        this.totalPartnersIncomeFromYouthTrainingScheme = totalPartnersIncomeFromYouthTrainingScheme;
    }

    /**
     * @return the totalPartnersOtherIncome
     */
    public int getTotalPartnersOtherIncome() {
        return totalPartnersOtherIncome;
    }

    /**
     * @param totalPartnersOtherIncome the totalPartnersOtherIncome to set
     */
    public void setTotalPartnersOtherIncome(int totalPartnersOtherIncome) {
        this.totalPartnersOtherIncome = totalPartnersOtherIncome;
    }

    /**
     * @return the totalPartnersTotalAmountOfIncomeDisregarded
     */
    public int getTotalPartnersTotalAmountOfIncomeDisregarded() {
        return totalPartnersTotalAmountOfIncomeDisregarded;
    }

    /**
     * @param totalPartnersTotalAmountOfIncomeDisregarded the
     * totalPartnersTotalAmountOfIncomeDisregarded to set
     */
    public void setTotalPartnersTotalAmountOfIncomeDisregarded(int totalPartnersTotalAmountOfIncomeDisregarded) {
        this.totalPartnersTotalAmountOfIncomeDisregarded = totalPartnersTotalAmountOfIncomeDisregarded;
    }

    /**
     * @return the totalClaimantsGenderFemale
     */
    public int getTotalClaimantsGenderFemale() {
        return totalClaimantsGenderFemale;
    }

    /**
     * @param totalClaimantsGenderFemale the totalClaimantsGenderFemale to set
     */
    public void setTotalClaimantsGenderFemale(int totalClaimantsGenderFemale) {
        this.totalClaimantsGenderFemale = totalClaimantsGenderFemale;
    }

    /**
     * @return the totalClaimantsGenderMale
     */
    public int getTotalClaimantsGenderMale() {
        return totalClaimantsGenderMale;
    }

    /**
     * @param totalClaimantsGenderMale the totalClaimantsGenderMale to set
     */
    public void setTotalClaimantsGenderMale(int totalClaimantsGenderMale) {
        this.totalClaimantsGenderMale = totalClaimantsGenderMale;
    }

    /**
     * @return the totalContractualRentAmount
     */
    public int getTotalContractualRentAmount() {
        return totalContractualRentAmount;
    }

    /**
     * @param totalContractualRentAmount the totalContractualRentAmount to set
     */
    public void setTotalContractualRentAmount(int totalContractualRentAmount) {
        this.totalContractualRentAmount = totalContractualRentAmount;
    }

    /**
     * @return the totalClaimantsIncomeFromPensionCreditSavingsCredit
     */
    public int getTotalClaimantsIncomeFromPensionCreditSavingsCredit() {
        return totalClaimantsIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @param totalClaimantsIncomeFromPensionCreditSavingsCredit the
     * totalClaimantsIncomeFromPensionCreditSavingsCredit to set
     */
    public void setTotalClaimantsIncomeFromPensionCreditSavingsCredit(int totalClaimantsIncomeFromPensionCreditSavingsCredit) {
        this.totalClaimantsIncomeFromPensionCreditSavingsCredit = totalClaimantsIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @return the totalPartnersIncomeFromPensionCreditSavingsCredit
     */
    public int getTotalPartnersIncomeFromPensionCreditSavingsCredit() {
        return totalPartnersIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @param totalPartnersIncomeFromPensionCreditSavingsCredit the
     * totalPartnersIncomeFromPensionCreditSavingsCredit to set
     */
    public void setTotalPartnersIncomeFromPensionCreditSavingsCredit(int totalPartnersIncomeFromPensionCreditSavingsCredit) {
        this.totalPartnersIncomeFromPensionCreditSavingsCredit = totalPartnersIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @return the totalClaimantsIncomeFromMaintenancePayments
     */
    public int getTotalClaimantsIncomeFromMaintenancePayments() {
        return totalClaimantsIncomeFromMaintenancePayments;
    }

    /**
     * @param totalClaimantsIncomeFromMaintenancePayments the
     * totalClaimantsIncomeFromMaintenancePayments to set
     */
    public void setTotalClaimantsIncomeFromMaintenancePayments(int totalClaimantsIncomeFromMaintenancePayments) {
        this.totalClaimantsIncomeFromMaintenancePayments = totalClaimantsIncomeFromMaintenancePayments;
    }

    /**
     * @return the totalPartnersIncomeFromMaintenancePayments
     */
    public int getTotalPartnersIncomeFromMaintenancePayments() {
        return totalPartnersIncomeFromMaintenancePayments;
    }

    /**
     * @param totalPartnersIncomeFromMaintenancePayments the
     * totalPartnersIncomeFromMaintenancePayments to set
     */
    public void setTotalPartnersIncomeFromMaintenancePayments(int totalPartnersIncomeFromMaintenancePayments) {
        this.totalPartnersIncomeFromMaintenancePayments = totalPartnersIncomeFromMaintenancePayments;
    }

    /**
     * @return the totalClaimantsIncomeFromOccupationalPension
     */
    public int getTotalClaimantsIncomeFromOccupationalPension() {
        return totalClaimantsIncomeFromOccupationalPension;
    }

    /**
     * @param totalClaimantsIncomeFromOccupationalPension the
     * totalClaimantsIncomeFromOccupationalPension to set
     */
    public void setTotalClaimantsIncomeFromOccupationalPension(int totalClaimantsIncomeFromOccupationalPension) {
        this.totalClaimantsIncomeFromOccupationalPension = totalClaimantsIncomeFromOccupationalPension;
    }

    /**
     * @return the totalPartnersIncomeFromOccupationalPension
     */
    public int getTotalPartnersIncomeFromOccupationalPension() {
        return totalPartnersIncomeFromOccupationalPension;
    }

    /**
     * @param totalPartnersIncomeFromOccupationalPension the
     * totalPartnersIncomeFromOccupationalPension to set
     */
    public void setTotalPartnersIncomeFromOccupationalPension(int totalPartnersIncomeFromOccupationalPension) {
        this.totalPartnersIncomeFromOccupationalPension = totalPartnersIncomeFromOccupationalPension;
    }

    /**
     * @return the totalClaimantsIncomeFromWidowsBenefit
     */
    public int getTotalClaimantsIncomeFromWidowsBenefit() {
        return totalClaimantsIncomeFromWidowsBenefit;
    }

    /**
     * @param totalClaimantsIncomeFromWidowsBenefit the
     * totalClaimantsIncomeFromWidowsBenefit to set
     */
    public void setTotalClaimantsIncomeFromWidowsBenefit(int totalClaimantsIncomeFromWidowsBenefit) {
        this.totalClaimantsIncomeFromWidowsBenefit = totalClaimantsIncomeFromWidowsBenefit;
    }

    /**
     * @return the totalPartnersIncomeFromWidowsBenefit
     */
    public int getTotalPartnersIncomeFromWidowsBenefit() {
        return totalPartnersIncomeFromWidowsBenefit;
    }

    /**
     * @param totalPartnersIncomeFromWidowsBenefit the
     * totalPartnersIncomeFromWidowsBenefit to set
     */
    public void setTotalPartnersIncomeFromWidowsBenefit(int totalPartnersIncomeFromWidowsBenefit) {
        this.totalPartnersIncomeFromWidowsBenefit = totalPartnersIncomeFromWidowsBenefit;
    }

    /**
     * @return the totalTotalNumberOfRooms
     */
    public int getTotalTotalNumberOfRooms() {
        return totalTotalNumberOfRooms;
    }

    /**
     * @param totalTotalNumberOfRooms the totalTotalNumberOfRooms to set
     */
    public void setTotalTotalNumberOfRooms(int totalTotalNumberOfRooms) {
        this.totalTotalNumberOfRooms = totalTotalNumberOfRooms;
    }

    /**
     * @return the totalValueOfLHA
     */
    public int getTotalValueOfLHA() {
        return totalValueOfLHA;
    }

    /**
     * @param totalValueOfLHA the totalValueOfLHA to set
     */
    public void setTotalValueOfLHA(int totalValueOfLHA) {
        this.totalValueOfLHA = totalValueOfLHA;
    }

    /**
     * @return the totalPartnersGenderFemale
     */
    public int getTotalPartnersGenderFemale() {
        return totalPartnersGenderFemale;
    }

    /**
     * @param totalPartnersGenderFemale the totalPartnersGenderFemale to set
     */
    public void setTotalPartnersGenderFemale(int totalPartnersGenderFemale) {
        this.totalPartnersGenderFemale = totalPartnersGenderFemale;
    }

    /**
     * @return the totalPartnersGenderMale
     */
    public int getTotalPartnersGenderMale() {
        return totalPartnersGenderMale;
    }

    /**
     * @param totalPartnersGenderMale the totalPartnersGenderMale to set
     */
    public void setTotalPartnersGenderMale(int totalPartnersGenderMale) {
        this.totalPartnersGenderMale = totalPartnersGenderMale;
    }

    /**
     * @return the totalNonDependantGrossWeeklyIncomeFromRemunerativeWork
     */
    public int getTotalNonDependantGrossWeeklyIncomeFromRemunerativeWork() {
        return totalNonDependantGrossWeeklyIncomeFromRemunerativeWork;
    }

    /**
     * @param totalNonDependantGrossWeeklyIncomeFromRemunerativeWork the
     * totalNonDependantGrossWeeklyIncomeFromRemunerativeWork to set
     */
    public void setTotalNonDependantGrossWeeklyIncomeFromRemunerativeWork(int totalNonDependantGrossWeeklyIncomeFromRemunerativeWork) {
        this.totalNonDependantGrossWeeklyIncomeFromRemunerativeWork = totalNonDependantGrossWeeklyIncomeFromRemunerativeWork;
    }

    /**
     * @return the totalTotalAmountOfBackdatedHBAwarded
     */
    public int getTotalTotalAmountOfBackdatedHBAwarded() {
        return totalTotalAmountOfBackdatedHBAwarded;
    }

    /**
     * @param totalTotalAmountOfBackdatedHBAwarded the
     * totalTotalAmountOfBackdatedHBAwarded to set
     */
    public void setTotalTotalAmountOfBackdatedHBAwarded(int totalTotalAmountOfBackdatedHBAwarded) {
        this.totalTotalAmountOfBackdatedHBAwarded = totalTotalAmountOfBackdatedHBAwarded;
    }

    /**
     * @return the totalTotalAmountOfBackdatedCTBAwarded
     */
    public int getTotalTotalAmountOfBackdatedCTBAwarded() {
        return totalTotalAmountOfBackdatedCTBAwarded;
    }

    /**
     * @param totalTotalAmountOfBackdatedCTBAwarded the
     * totalTotalAmountOfBackdatedCTBAwarded to set
     */
    public void setTotalTotalAmountOfBackdatedCTBAwarded(int totalTotalAmountOfBackdatedCTBAwarded) {
        this.totalTotalAmountOfBackdatedCTBAwarded = totalTotalAmountOfBackdatedCTBAwarded;
    }

    /**
     * @return the totalPartnersTotalCapital
     */
    public int getTotalPartnersTotalCapital() {
        return totalPartnersTotalCapital;
    }

    /**
     * @param totalPartnersTotalCapital the totalPartnersTotalCapital to set
     */
    public void setTotalPartnersTotalCapital(int totalPartnersTotalCapital) {
        this.totalPartnersTotalCapital = totalPartnersTotalCapital;
    }

    /**
     * @return the
     * totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure
     */
    public int getTotalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure() {
        return totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure;
    }

    /**
     * @param
     * totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure the
     * totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure to
     * set
     */
    public void setTotalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure(int totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure) {
        this.totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure = totalWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure;
    }

    /**
     * @return the totalClaimantsTotalHoursOfRemunerativeWorkPerWeek
     */
    public double getTotalClaimantsTotalHoursOfRemunerativeWorkPerWeek() {
        return totalClaimantsTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @param totalClaimantsTotalHoursOfRemunerativeWorkPerWeek the
     * totalClaimantsTotalHoursOfRemunerativeWorkPerWeek to set
     */
    public void setTotalClaimantsTotalHoursOfRemunerativeWorkPerWeek(double totalClaimantsTotalHoursOfRemunerativeWorkPerWeek) {
        this.totalClaimantsTotalHoursOfRemunerativeWorkPerWeek = totalClaimantsTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @return the totalPartnersTotalHoursOfRemunerativeWorkPerWeek
     */
    public double getTotalPartnersTotalHoursOfRemunerativeWorkPerWeek() {
        return totalPartnersTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @param totalPartnersTotalHoursOfRemunerativeWorkPerWeek the
     * totalPartnersTotalHoursOfRemunerativeWorkPerWeek to set
     */
    public void setTotalPartnersTotalHoursOfRemunerativeWorkPerWeek(double totalPartnersTotalHoursOfRemunerativeWorkPerWeek) {
        this.totalPartnersTotalHoursOfRemunerativeWorkPerWeek = totalPartnersTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @return the ClaimantsEthnicGroup
     */
    public int getClaimantsEthnicGroup() {
        return ClaimantsEthnicGroup;
    }

    /**
     * @param ClaimantsEthnicGroup the ClaimantsEthnicGroup to set
     */
    public void setClaimantsEthnicGroup(int ClaimantsEthnicGroup) {
        this.ClaimantsEthnicGroup = ClaimantsEthnicGroup;
    }
}
