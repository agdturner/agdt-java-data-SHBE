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

import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;

/**
 *
 * @author geoagdt
 */
public class SHBE_T_Record extends DW_SHBE_RecordAbstract {
    
    /**
     * 45 50 WeeklyHBEntitlementBeforeChange
     */
    private int WeeklyHBEntitlementBeforeChange;
    // Empty Field
    /**
     * 47 52 WeeklyCTBEntitlementBeforeChange
     */
    private int WeeklyCTBEntitlementBeforeChange;
    
    /**
     * 239 248 NewWeeklyHBEntitlementAfterTheChange
     */
    private int NewWeeklyHBEntitlementAfterTheChange;
    /**
     * 240 249 NewWeeklyCTBEntitlementAfterTheChange
     */
    private int NewWeeklyCTBEntitlementAfterTheChange;
    /**
     * 241 250 TypeOfChange
     */
    private int TypeOfChange;
    /**
     * 242 251 DateLAFirstNotifiedOfChangeInClaimDetails
     */
    private String DateLAFirstNotifiedOfChangeInClaimDetails;
    //Empty Field
    /**
     * 244 253 DateChangeOfDetailsAreEffectiveFrom
     */
    private String DateChangeOfDetailsAreEffectiveFrom;
    /**
     * 245 254 IfNotAnnualUpratingHowWasTheChangeIdentified
     */
    private int IfNotAnnualUpratingHowWasTheChangeIdentified;
    /**
     * 246 255 DateSupercessionDecisionWasMadeOnTheHBClaim
     */
    private String DateSupercessionDecisionWasMadeOnTheHBClaim;
    /**
     * 247 256 DateSupercessionDecisionWasMadeOnTheCTBClaim
     */
    private String DateSupercessionDecisionWasMadeOnTheCTBClaim;
    /**
     * 293 320 UniqueTRecordIdentifier
     */
    private String UniqueTRecordIdentifier;
    /**
     * 294 321 OverpaymentReasonCapital
     */
    private String OverpaymentReasonCapital;
    /**
     * 295 322 OverpaymentReasonClaimantPartnersEarnedIncome
     */
    private String OverpaymentReasonClaimantPartnersEarnedIncome;
    /**
     * 296 323 OverpaymentReasonNonDependentsEarnedIncome
     */
    private String OverpaymentReasonNonDependentsEarnedIncome;
    /**
     * 297 324 OverpaymentReasonPassportingStatus
     */
    private String OverpaymentReasonPassportingStatus;
    /**
     * 298 325 OverpaymentReasonIncomeFromDWPBenefits
     */
    private String OverpaymentReasonIncomeFromDWPBenefits;
    /**
     * 299 326 OverpaymentReasonTaxCredits
     */
    private String OverpaymentReasonTaxCredits;
    /**
     * 230 327 OverpaymentReasonOtherIncome
     */
    private String OverpaymentReasonOtherIncome;
    /**
     * 231 328 OverpaymentReasonLivingTogetherAsHusbandAndWife
     */
    private String OverpaymentReasonLivingTogetherAsHusbandAndWife;
    /**
     * 232 329 OverpaymentReasonNumberOfNonDependents
     */
    private String OverpaymentReasonNumberOfNonDependents;
    /**
     * 233 330 OverpaymentReasonNumberOfDependents
     */
    private String OverpaymentReasonNumberOfDependents;
    /**
     * 234 331 OverpaymentReasonNonResidence
     */
    private String OverpaymentReasonNonResidence;
    /**
     * 235 332 OverpaymentReasonEligibleRentCouncilTax
     */
    private String OverpaymentReasonEligibleRentCouncilTax;
    /**
     * 236 333 OverpaymentReasonIneligible
     */
    private String OverpaymentReasonIneligible;
    /**
     * 237 334 OverpaymentReasonIdentityDeath
     */
    private String OverpaymentReasonIdentityDeath;
    /**
     * 238 335 OverpaymentReasonOther
     */
    private String OverpaymentReasonOther;

    public SHBE_T_Record(SHBE_Environment env) {
        super(env);
    }
    
    @Override
    public String toString() {
        return super.toString()
                + ",WeeklyHBEntitlementBeforeChange " + WeeklyHBEntitlementBeforeChange
                + ",WeeklyCTBEntitlementBeforeChange " + WeeklyCTBEntitlementBeforeChange
                + ",NewWeeklyHBEntitlementAfterTheChange " + NewWeeklyHBEntitlementAfterTheChange
                + ",NewWeeklyCTBEntitlementAfterTheChange " + NewWeeklyCTBEntitlementAfterTheChange
                + ",TypeOfChange " + TypeOfChange
                + ",DateLAFirstNotifiedOfChangeInClaimDetails " + DateLAFirstNotifiedOfChangeInClaimDetails
                + ",DateChangeOfDetailsAreEffectiveFrom " + DateChangeOfDetailsAreEffectiveFrom
                + ",IfNotAnnualUpratingHowWasTheChangeIdentified " + IfNotAnnualUpratingHowWasTheChangeIdentified
                + ",DateSupercessionDecisionWasMadeOnTheHBClaim " + DateSupercessionDecisionWasMadeOnTheHBClaim
                + ",DateSupercessionDecisionWasMadeOnTheCTBClaim " + DateSupercessionDecisionWasMadeOnTheCTBClaim;
                }

    public String getUniqueTRecordIdentifier() {
        return UniqueTRecordIdentifier;
    }

    protected void setUniqueTRecordIdentifier(String UniqueTRecordIdentifier) {
        this.UniqueTRecordIdentifier = UniqueTRecordIdentifier;
    }

     /**
     * @return the WeeklyHBEntitlementBeforeChange
     */
    public int getWeeklyHBEntitlementBeforeChange() {
        return WeeklyHBEntitlementBeforeChange;
    }

    /**
     * @param WeeklyHBEntitlementBeforeChange the
     * WeeklyHBEntitlementBeforeChange to set
     */
    protected void setWeeklyHBEntitlementBeforeChange(int WeeklyHBEntitlementBeforeChange) {
        this.WeeklyHBEntitlementBeforeChange = WeeklyHBEntitlementBeforeChange;
    }
    
    /**
     * @return the WeeklyCTBEntitlementBeforeChange
     */
    public int getWeeklyCTBEntitlementBeforeChange() {
        return WeeklyCTBEntitlementBeforeChange;
    }

    /**
     * @param WeeklyCTBEntitlementBeforeChange the
     * WeeklyCTBEntitlementBeforeChange to set
     */
    protected void setWeeklyCTBEntitlementBeforeChange(int WeeklyCTBEntitlementBeforeChange) {
        this.WeeklyCTBEntitlementBeforeChange = WeeklyCTBEntitlementBeforeChange;
    }
    
    /**
     * @return the NewWeeklyHBEntitlementAfterTheChange
     */
    public int getNewWeeklyHBEntitlementAfterTheChange() {
        return NewWeeklyHBEntitlementAfterTheChange;
    }

    /**
     * @param NewWeeklyHBEntitlementAfterTheChange the
     * NewWeeklyHBEntitlementAfterTheChange to set
     */
    protected void setNewWeeklyHBEntitlementAfterTheChange(int NewWeeklyHBEntitlementAfterTheChange) {
        this.NewWeeklyHBEntitlementAfterTheChange = NewWeeklyHBEntitlementAfterTheChange;
    }

    /**
     * @return the NewWeeklyCTBEntitlementAfterTheChange
     */
    public int getNewWeeklyCTBEntitlementAfterTheChange() {
        return NewWeeklyCTBEntitlementAfterTheChange;
    }

    /**
     * @param NewWeeklyCTBEntitlementAfterTheChange the
     * NewWeeklyCTBEntitlementAfterTheChange to set
     */
    protected void setNewWeeklyCTBEntitlementAfterTheChange(int NewWeeklyCTBEntitlementAfterTheChange) {
        this.NewWeeklyCTBEntitlementAfterTheChange = NewWeeklyCTBEntitlementAfterTheChange;
    }

    /**
     * @return the TypeOfChange
     */
    public int getTypeOfChange() {
        return TypeOfChange;
    }

    /**
     * @param TypeOfChange the TypeOfChange to set
     */
    protected void setTypeOfChange(int TypeOfChange) {
        this.TypeOfChange = TypeOfChange;
    }

    protected void setTypeOfChange(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            TypeOfChange = 0;
        } else {
            TypeOfChange = Integer.valueOf(fields[n]);
            if (TypeOfChange > 2 || TypeOfChange < 0) {
                System.err.println("TypeOfChange " + TypeOfChange);
                System.err.println("TypeOfChange > 2 || TypeOfChange < 0");
                throw new Exception("TypeOfChange > 2 || TypeOfChange < 0");
            }
        }
    }

    /**
     * @return the DateLAFirstNotifiedOfChangeInClaimDetails
     */
    public String getDateLAFirstNotifiedOfChangeInClaimDetails() {
        return DateLAFirstNotifiedOfChangeInClaimDetails;
    }

    /**
     * @param DateLAFirstNotifiedOfChangeInClaimDetails the
     * DateLAFirstNotifiedOfChangeInClaimDetails to set
     */
    protected void setDateLAFirstNotifiedOfChangeInClaimDetails(String DateLAFirstNotifiedOfChangeInClaimDetails) {
        this.DateLAFirstNotifiedOfChangeInClaimDetails = DateLAFirstNotifiedOfChangeInClaimDetails;
    }

    /**
     * @return the DateChangeOfDetailsAreEffectiveFrom
     */
    public String getDateChangeOfDetailsAreEffectiveFrom() {
        return DateChangeOfDetailsAreEffectiveFrom;
    }

    /**
     * @param DateChangeOfDetailsAreEffectiveFrom the
     * DateChangeOfDetailsAreEffectiveFrom to set
     */
    protected void setDateChangeOfDetailsAreEffectiveFrom(String DateChangeOfDetailsAreEffectiveFrom) {
        this.DateChangeOfDetailsAreEffectiveFrom = DateChangeOfDetailsAreEffectiveFrom;
    }

    /**
     * @return the IfNotAnnualUpratingHowWasTheChangeIdentified
     */
    public int getIfNotAnnualUpratingHowWasTheChangeIdentified() {
        return IfNotAnnualUpratingHowWasTheChangeIdentified;
    }

    /**
     * @param IfNotAnnualUpratingHowWasTheChangeIdentified the
     * IfNotAnnualUpratingHowWasTheChangeIdentified to set
     */
    protected void setIfNotAnnualUpratingHowWasTheChangeIdentified(int IfNotAnnualUpratingHowWasTheChangeIdentified) {
        this.IfNotAnnualUpratingHowWasTheChangeIdentified = IfNotAnnualUpratingHowWasTheChangeIdentified;
    }

    protected void setIfNotAnnualUpratingHowWasTheChangeIdentified(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            IfNotAnnualUpratingHowWasTheChangeIdentified = 0;
        } else {
            IfNotAnnualUpratingHowWasTheChangeIdentified = Integer.valueOf(fields[n]);
            if (IfNotAnnualUpratingHowWasTheChangeIdentified > 99 || IfNotAnnualUpratingHowWasTheChangeIdentified < 0) {
                System.err.println("IfNotAnnualUpratingHowWasTheChangeIdentified " + IfNotAnnualUpratingHowWasTheChangeIdentified);
                System.err.println("IfNotAnnualUpratingHowWasTheChangeIdentified > 99 || IfNotAnnualUpratingHowWasTheChangeIdentified < 0");
                throw new Exception("IfNotAnnualUpratingHowWasTheChangeIdentified > 99 || IfNotAnnualUpratingHowWasTheChangeIdentified < 0");
            }
        }
    }

    /**
     * @return the DateSupercessionDecisionWasMadeOnTheHBClaim
     */
    public String getDateSupercessionDecisionWasMadeOnTheHBClaim() {
        return DateSupercessionDecisionWasMadeOnTheHBClaim;
    }

    /**
     * @param DateSupercessionDecisionWasMadeOnTheHBClaim the
     * DateSupercessionDecisionWasMadeOnTheHBClaim to set
     */
    protected void setDateSupercessionDecisionWasMadeOnTheHBClaim(String DateSupercessionDecisionWasMadeOnTheHBClaim) {
        this.DateSupercessionDecisionWasMadeOnTheHBClaim = DateSupercessionDecisionWasMadeOnTheHBClaim;
    }

    /**
     * @return the DateSupercessionDecisionWasMadeOnTheCTBClaim
     */
    public String getDateSupercessionDecisionWasMadeOnTheCTBClaim() {
        return DateSupercessionDecisionWasMadeOnTheCTBClaim;
    }

    /**
     * @param DateSupercessionDecisionWasMadeOnTheCTBClaim the
     * DateSupercessionDecisionWasMadeOnTheCTBClaim to set
     */
    protected void setDateSupercessionDecisionWasMadeOnTheCTBClaim(String DateSupercessionDecisionWasMadeOnTheCTBClaim) {
        this.DateSupercessionDecisionWasMadeOnTheCTBClaim = DateSupercessionDecisionWasMadeOnTheCTBClaim;
    }

    public String getOverpaymentReasonCapital() {
        return OverpaymentReasonCapital;
    }

    protected void setOverpaymentReasonCapital(String OverpaymentReasonCapital) {
        this.OverpaymentReasonCapital = OverpaymentReasonCapital;
    }

    public String getOverpaymentReasonClaimantPartnersEarnedIncome() {
        return OverpaymentReasonClaimantPartnersEarnedIncome;
    }

    protected void setOverpaymentReasonClaimantPartnersEarnedIncome(String OverpaymentReasonClaimantPartnersEarnedIncome) {
        this.OverpaymentReasonClaimantPartnersEarnedIncome = OverpaymentReasonClaimantPartnersEarnedIncome;
    }

    public String getOverpaymentReasonNonDependentsEarnedIncome() {
        return OverpaymentReasonNonDependentsEarnedIncome;
    }

    protected void setOverpaymentReasonNonDependentsEarnedIncome(String OverpaymentReasonNonDependentsEarnedIncome) {
        this.OverpaymentReasonNonDependentsEarnedIncome = OverpaymentReasonNonDependentsEarnedIncome;
    }

    public String getOverpaymentReasonPassportingStatus() {
        return OverpaymentReasonPassportingStatus;
    }

    protected void setOverpaymentReasonPassportingStatus(String OverpaymentReasonPassportingStatus) {
        this.OverpaymentReasonPassportingStatus = OverpaymentReasonPassportingStatus;
    }

    public String getOverpaymentReasonIncomeFromDWPBenefits() {
        return OverpaymentReasonIncomeFromDWPBenefits;
    }

    protected void setOverpaymentReasonIncomeFromDWPBenefits(String OverpaymentReasonIncomeFromDWPBenefits) {
        this.OverpaymentReasonIncomeFromDWPBenefits = OverpaymentReasonIncomeFromDWPBenefits;
    }

    public String getOverpaymentReasonTaxCredits() {
        return OverpaymentReasonTaxCredits;
    }

    protected void setOverpaymentReasonTaxCredits(String OverpaymentReasonTaxCredits) {
        this.OverpaymentReasonTaxCredits = OverpaymentReasonTaxCredits;
    }

    public String getOverpaymentReasonOtherIncome() {
        return OverpaymentReasonOtherIncome;
    }

    protected void setOverpaymentReasonOtherIncome(String OverpaymentReasonOtherIncome) {
        this.OverpaymentReasonOtherIncome = OverpaymentReasonOtherIncome;
    }

    public String getOverpaymentReasonLivingTogetherAsHusbandAndWife() {
        return OverpaymentReasonLivingTogetherAsHusbandAndWife;
    }

    protected void setOverpaymentReasonLivingTogetherAsHusbandAndWife(String OverpaymentReasonLivingTogetherAsHusbandAndWife) {
        this.OverpaymentReasonLivingTogetherAsHusbandAndWife = OverpaymentReasonLivingTogetherAsHusbandAndWife;
    }

    public String getOverpaymentReasonNumberOfNonDependents() {
        return OverpaymentReasonNumberOfNonDependents;
    }

    protected void setOverpaymentReasonNumberOfNonDependents(String OverpaymentReasonNumberOfNonDependents) {
        this.OverpaymentReasonNumberOfNonDependents = OverpaymentReasonNumberOfNonDependents;
    }

    public String getOverpaymentReasonNumberOfDependents() {
        return OverpaymentReasonNumberOfDependents;
    }

    protected void setOverpaymentReasonNumberOfDependents(String OverpaymentReasonNumberOfDependents) {
        this.OverpaymentReasonNumberOfDependents = OverpaymentReasonNumberOfDependents;
    }

    public String getOverpaymentReasonNonResidence() {
        return OverpaymentReasonNonResidence;
    }

    protected void setOverpaymentReasonNonResidence(String OverpaymentReasonNonResidence) {
        this.OverpaymentReasonNonResidence = OverpaymentReasonNonResidence;
    }

    public String getOverpaymentReasonEligibleRentCouncilTax() {
        return OverpaymentReasonEligibleRentCouncilTax;
    }

    protected void setOverpaymentReasonEligibleRentCouncilTax(String OverpaymentReasonEligibleRentCouncilTax) {
        this.OverpaymentReasonEligibleRentCouncilTax = OverpaymentReasonEligibleRentCouncilTax;
    }

    public String getOverpaymentReasonIneligible() {
        return OverpaymentReasonIneligible;
    }

    protected void setOverpaymentReasonIneligible(String OverpaymentReasonIneligible) {
        this.OverpaymentReasonIneligible = OverpaymentReasonIneligible;
    }

    public String getOverpaymentReasonIdentityDeath() {
        return OverpaymentReasonIdentityDeath;
    }

    protected void setOverpaymentReasonIdentityDeath(String OverpaymentReasonIdentityDeath) {
        this.OverpaymentReasonIdentityDeath = OverpaymentReasonIdentityDeath;
    }

    public String getOverpaymentReasonOther() {
        return OverpaymentReasonOther;
    }

    protected void setOverpaymentReasonOther(String OverpaymentReasonOther) {
        this.OverpaymentReasonOther = OverpaymentReasonOther;
    }
}
