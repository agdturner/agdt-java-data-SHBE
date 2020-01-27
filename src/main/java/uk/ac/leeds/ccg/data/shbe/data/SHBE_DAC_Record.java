/*
 * Copyright 2018 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.data.shbe.data;

import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class SHBE_DAC_Record extends SHBE_DACTEGPRST_Record {

    /**
     * 4 8 claimantsDateOfBirth
     */
    private String claimantsDateOfBirth;
    /**
     * 5 9 tenancyType
     */
    private int tenancyType;
    /**
     * 6 11 claimantsPostcode
     */
    private String claimantsPostcode;
    /**
     * 7 12 PpassportedStandardIndicator
     */
    private int passportedStandardIndicator;
    /**
     * 28 33 statusOfHBClaimAtExtractDate 0 = Other; 1 = InPayment; 2 =
     * Suspended.
     */
    private int statusOfHBClaimAtExtractDate;
    /**
     * 29 34 statusOfCTBClaimAtExtractDate
     */
    private int statusOfCTBClaimAtExtractDate;
    /**
     * 30 35 dateMostRecentHBClaimWasReceived
     */
    private String dateMostRecentHBClaimWasReceived;
    /**
     * 31 36 dateMostRecentCTBClaimWasReceived
     */
    private String dateMostRecentCTBClaimWasReceived;
    /**
     * 57 62 lHARegulationsApplied Blank - LHA flag set to 'No' 1 - LHA flag set
     * to 'Yes'
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1!!!!!!!!!!!!!!
     * In the SHBE Extracts for Leeds this is generally set as "No" or "Yes"
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1!!!!!!!!!!!!!!
     */
    private String lHARegulationsApplied;

    /**
     * 121 126 partnersNationalInsuranceNumber
     */
    private String partnersNationalInsuranceNumber;

    /**
     * 169 177 claimantsGender
     */
    private String claimantsGender;

    /**
     * 170 178 partnersDateOfBirth
     */
    private String partnersDateOfBirth;

    /**
     * 194 202 dateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private String dateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective;

    /**
     * 195 203 dateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private String dateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective;

    /**
     * 202 210 reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private int reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;

    /**
     * 203 211 reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private int reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;

    /**
     * 211 220 hBClaimTreatAsDateMade
     */
    private String hBClaimTreatAsDateMade;

    /**
     * 212 221 SourceOfMostRecentHBClaim
     */
    private int sourceOfTheMostRecentHBClaim;

    /**
     * 213 222 didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim
     */
    private int didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim;

    /**
     * 214 223 dateOfFirstHBPaymentRentAllowanceOnly
     */
    private String dateOfFirstHBPaymentRentAllowanceOnly;

    /**
     * 215 224 wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly
     */
    private int wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly;

    /**
     * 220 229 cTBClaimTreatAsMadeDate
     */
    private String cTBClaimTreatAsMadeDate;

    /**
     * 221 230 sourceOfTheMostRecentCTBClaim
     */
    private int sourceOfTheMostRecentCTBClaim;

    /**
     * 222 231 didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim
     */
    private int didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim;

    /**
     * 227 236 isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly
     */
    private int isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly;

    /**
     * 228 237 ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation
     */
    private int ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation;

    /*
     * 236 245 totalHBPaymentsCreditsSinceLastExtract
     */
    private int totalHBPaymentsCreditsSinceLastExtract;

    /**
     * 237 246 totalCTBPaymentsCreditsSinceLastExtract
     */
    private int totalCTBPaymentsCreditsSinceLastExtract;

    /**
     * 238 247 claimantsEthnicGroup 1 White: British 2 White: Irish 3 White: Any
     * Other 4 Mixed: White and Black Caribbean 5 Mixed: White and Black African
     * 6 Mixed: White and Asian 7 Mixed: Any Other 8 Asian or Asian British:
     * Indian 9 Asian or Asian British: Pakistani 10 Asian or Asian British:
     * Bangladeshi 11 Asian or Asian British: Any Other 12 Black or Black
     * British: Caribbean 13 Black or Black British: African 14 Black or Black
     * British: Any Other 15 Chinese 99 Any Other
     */
    private int claimantsEthnicGroup;

    /**
     * 260 269
     * dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     */
    private String dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim;

    /**
     * 261 270
     * dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     */
    private String dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim;

    /**
     * 262 271 dateCouncilTaxPayable
     */
    private String dateCouncilTaxPayable;

    /**
     * 263 272
     * dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     */
    private String dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim;

    /**
     * 264 273
     * dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     */
    private String dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;

    public SHBE_DAC_Record(SHBE_Environment env) {
        super(env);
    }

    public String toStringBrief() {
        return super.toString()
                + ", ClaimantsDateOfBirth=" + claimantsDateOfBirth
                + ", TenancyType=" + tenancyType
                + ", ClaimantsPostcode=" + claimantsPostcode
                + ", PassportedStandardIndicator=" + passportedStandardIndicator
                + ", StatusOfHBClaimAtExtractDate=" + statusOfHBClaimAtExtractDate
                + ", StatusOfCTBClaimAtExtractDate=" + statusOfCTBClaimAtExtractDate
                + ", ClaimantsGender=" + claimantsGender
                + ", ClaimantsEthnicGroup=" + claimantsEthnicGroup;
    }

    @Override
    public String toString() {
        return super.toString()
                + " ,ClaimantsDateOfBirth=" + claimantsDateOfBirth
                + " ,TenancyType=" + tenancyType
                + " ,ClaimantsPostcode=" + claimantsPostcode
                + " ,PassportedStandardIndicator=" + passportedStandardIndicator
                + " ,StatusOfHBClaimAtExtractDate=" + statusOfHBClaimAtExtractDate
                + " ,StatusOfCTBClaimAtExtractDate=" + statusOfCTBClaimAtExtractDate
                + " ,DateMostRecentHBClaimWasReceived=" + dateMostRecentHBClaimWasReceived
                + " ,DateMostRecentCTBClaimWasReceived=" + dateMostRecentCTBClaimWasReceived
                + " ,LHARegulationsApplied=" + lHARegulationsApplied
                + " ,PartnersNationalInsuranceNumber=" + partnersNationalInsuranceNumber
                + " ,ClaimantsGender=" + claimantsGender
                + " ,PartnersDateOfBirth=" + partnersDateOfBirth
                + " ,DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective=" + dateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective
                + " ,DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective=" + dateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective
                + " ,ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective=" + reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
                + " ,ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective=" + reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
                + " ,HBClaimTreatAsDateMade=" + hBClaimTreatAsDateMade
                + " ,DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim=" + didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim
                + " ,DateOfFirstHBPaymentRentAllowanceOnly=" + dateOfFirstHBPaymentRentAllowanceOnly
                + " ,WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly=" + wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly
                + " ,CTBClaimTreatAsMadeDate=" + cTBClaimTreatAsMadeDate
                + " ,SourceOfTheMostRecentCTBClaim=" + sourceOfTheMostRecentCTBClaim
                + " ,DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim=" + didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim
                + " ,InThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly=" + isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly
                + " ,IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation=" + ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation
                + " ,TotalHBPaymentsCreditsSinceLastExtract=" + totalHBPaymentsCreditsSinceLastExtract
                + " ,TotalCTBPaymentsCreditsSinceLastExtract=" + totalCTBPaymentsCreditsSinceLastExtract
                + " ,ClaimantsEthnicGroup=" + claimantsEthnicGroup
                + " ,DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim=" + dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
                + " ,DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim=" + dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
                + " ,DateCouncilTaxPayable=" + dateCouncilTaxPayable
                + " ,DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim=" + dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
                + " ,DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim=" + dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @return {@link #claimantsDateOfBirth}
     */
    public String getClaimantsDateOfBirth() {
        return claimantsDateOfBirth;
    }

    /**
     * @param s What {@link #claimantsDateOfBirth} is set to.
     */
    protected final void setClaimantsDateOfBirth(String s) {
        this.claimantsDateOfBirth = s;
    }

    /**
     * @return {@link #tenancyType}.
     */
    public int getTenancyType() {
        return tenancyType;
    }

    /**
     * @param TenancyType the tenancyType to set
     */
    protected final void setTenancyType(int TenancyType) {
        this.tenancyType = TenancyType;
    }

    /**
     * @param n
     * @param fields
     * @throws Exception
     */
    protected final void setTenancyType(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            tenancyType = -999;
        } else {
            try {
                tenancyType = Integer.valueOf(fields[n]);
                if (tenancyType > 9 || tenancyType < 1) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("TenancyType " + fields[n]);
                    System.err.println("TenancyType > 9 || TenancyType < 1");
//                throw new Exception("tenancyType > 9 || tenancyType < 1");
                }
            } catch (NumberFormatException e) {
                // For September 2014 there is some messed up data.
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in setSourceOfTheMostRecentHBClaim(n,String[])");
                if (claimantsDateOfBirth.trim().isEmpty()) {
                    claimantsDateOfBirth = fields[n];
                    System.err.println("ClaimantsDateOfBirth set to " + claimantsDateOfBirth);
                    tenancyType = -999;
                    System.err.println("TenancyType set to " + tenancyType);
                }
            }
        }
    }

    /**
     * @return the claimantsPostcode
     */
    public String getClaimantsPostcode() {
        return claimantsPostcode;
    }

    /**
     * @param ClaimantsPostcode the claimantsPostcode to set
     */
    protected final void setClaimantsPostcode(String ClaimantsPostcode) {
        this.claimantsPostcode = ClaimantsPostcode;
    }

    /**
     * @return the PpassportedStandardIndicator
     */
    public int getPassportedStandardIndicator() {
        return passportedStandardIndicator;
    }

    /**
     * @param PassportedStandardIndicator the PpassportedStandardIndicator to
     * set
     */
    protected final void setPassportedStandardIndicator(int PassportedStandardIndicator) {
        this.passportedStandardIndicator = PassportedStandardIndicator;
    }

    protected final void setPassportedStandardIndicator(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setPassportedStandardIndicator(0);
        } else {
            try {
                setPassportedStandardIndicator(Integer.valueOf(fields[n]));
            } catch (NumberFormatException e) {
                // For September 2014 there is some messed up data.
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in setPassportedStandardIndicator(n,String[])");
                System.err.println("ClaimantsPostcode originally set to " + claimantsPostcode);
                claimantsPostcode = fields[n];
                System.err.println("ClaimantsPostcode now set to " + claimantsPostcode);
                n++;
                setPassportedStandardIndicator(n, fields);
                System.err.println("PassportedStandardIndicator set to " + passportedStandardIndicator);
            }
            if (passportedStandardIndicator > 5 || passportedStandardIndicator < 0) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("PassportedStandardIndicator " + fields[n]);
                System.err.println("PassportedStandardIndicator > 5 || PassportedStandardIndicator < 0");
//                throw new Exception("PpassportedStandardIndicator > 5 || PpassportedStandardIndicator < 0");
            }
        }
    }

    /**
     * @return the statusOfHBClaimAtExtractDate: 0 = Other; 1 = InPayment; 2 =
     * Suspended.
     */
    public int getStatusOfHBClaimAtExtractDate() {
        return statusOfHBClaimAtExtractDate;
    }

    /**
     * @param StatusOfHBClaimAtExtractDate the statusOfHBClaimAtExtractDate to
     * set
     */
    protected final void setStatusOfHBClaimAtExtractDate(int StatusOfHBClaimAtExtractDate) {
        this.statusOfHBClaimAtExtractDate = StatusOfHBClaimAtExtractDate;
    }

    protected final void setStatusOfHBClaimAtExtractDate(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setStatusOfHBClaimAtExtractDate(-999);
        } else {
            try {
                setStatusOfHBClaimAtExtractDate(Integer.valueOf(fields[n]));
                if (statusOfHBClaimAtExtractDate > 2 || statusOfHBClaimAtExtractDate < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("StatusOfHBClaimAtExtractDate " + fields[n]);
                    System.err.println("StatusOfHBClaimAtExtractDate > 2 || StatusOfHBClaimAtExtractDate < 0");
//                throw new Exception("statusOfHBClaimAtExtractDate > 2 || statusOfHBClaimAtExtractDate < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setStatusOfHBClaimAtExtractDate(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the statusOfCTBClaimAtExtractDate
     */
    public int getStatusOfCTBClaimAtExtractDate() {
        return statusOfCTBClaimAtExtractDate;
    }

    /**
     * @param StatusOfCTBClaimAtExtractDate the statusOfCTBClaimAtExtractDate to
     * set
     */
    protected final void setStatusOfCTBClaimAtExtractDate(int StatusOfCTBClaimAtExtractDate) {
        this.statusOfCTBClaimAtExtractDate = StatusOfCTBClaimAtExtractDate;
    }

    protected final void setStatusOfCTBClaimAtExtractDate(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            statusOfCTBClaimAtExtractDate = -999;
        } else {
            try {
                statusOfCTBClaimAtExtractDate = Integer.valueOf(fields[n]);
                if (statusOfCTBClaimAtExtractDate > 2 || statusOfCTBClaimAtExtractDate < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("StatusOfCTBClaimAtExtractDate " + fields[n]);
                    System.err.println("StatusOfCTBClaimAtExtractDate > 2 || StatusOfCTBClaimAtExtractDate < 0");
//                throw new Exception("statusOfCTBClaimAtExtractDate > 2 || statusOfCTBClaimAtExtractDate < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setStatusOfCTBClaimAtExtractDate(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the dateMostRecentHBClaimWasReceived
     */
    public String getDateMostRecentHBClaimWasReceived() {
        return dateMostRecentHBClaimWasReceived;
    }

    /**
     * @param DateMostRecentHBClaimWasReceived the
     * dateMostRecentHBClaimWasReceived to set
     */
    protected final void setDateMostRecentHBClaimWasReceived(String DateMostRecentHBClaimWasReceived) {
        this.dateMostRecentHBClaimWasReceived = DateMostRecentHBClaimWasReceived;
    }

    /**
     * @return the dateMostRecentCTBClaimWasReceived
     */
    public String getDateMostRecentCTBClaimWasReceived() {
        return dateMostRecentCTBClaimWasReceived;
    }

    /**
     * @param DateMostRecentCTBClaimWasReceived the
     * dateMostRecentCTBClaimWasReceived to set
     */
    protected final void setDateMostRecentCTBClaimWasReceived(String DateMostRecentCTBClaimWasReceived) {
        this.dateMostRecentCTBClaimWasReceived = DateMostRecentCTBClaimWasReceived;
    }

    /**
     * @return the lHARegulationsApplied
     */
    public String getLHARegulationsApplied() {
        return lHARegulationsApplied;
    }

    /**
     * @param LHARegulationsApplied the lHARegulationsApplied to set
     */
    protected final void setLHARegulationsApplied(String LHARegulationsApplied) {
        this.lHARegulationsApplied = LHARegulationsApplied;
    }

    /**
     * @return the partnersNationalInsuranceNumber
     */
    public String getPartnersNationalInsuranceNumber() {
        return partnersNationalInsuranceNumber;
    }

    /**
     * @param PartnersNationalInsuranceNumber the
     * partnersNationalInsuranceNumber to set
     */
    protected final void setPartnersNationalInsuranceNumber(String PartnersNationalInsuranceNumber) {
        this.partnersNationalInsuranceNumber = PartnersNationalInsuranceNumber;
    }

    /**
     * @return the claimantsGender
     */
    public String getClaimantsGender() {
        return claimantsGender;
    }

    /**
     * @param ClaimantsGender the claimantsGender to set
     */
    protected final void setClaimantsGender(String ClaimantsGender) {
        this.claimantsGender = ClaimantsGender;
    }

    /**
     * @return the partnersDateOfBirth
     */
    public String getPartnersDateOfBirth() {
        return partnersDateOfBirth;
    }

    /**
     * @param PartnersDateOfBirth the partnersDateOfBirth to set
     */
    protected final void setPartnersDateOfBirth(String PartnersDateOfBirth) {
        this.partnersDateOfBirth = PartnersDateOfBirth;
    }

    /**
     * @return the dateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public String getDateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective() {
        return dateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective the
     * dateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective to set
     */
    protected final void setDateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective(String DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.dateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective = DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @return the dateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public String getDateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective() {
        return dateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective the
     * dateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective to set
     */
    protected final void setDateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective(String DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.dateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective = DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @return the
     * reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public int getReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective() {
        return reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     * the reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective to
     * set
     */
    protected final void setReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(int ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    protected final void setReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = 0;
        } else {
            try {
                reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = Integer.valueOf(fields[n]);
                if (reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective " + fields[n]);
                    System.err.println("ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
//                throw new Exception("reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || reasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the
     * reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public int getReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective() {
        return reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     * the reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective to
     * set
     */
    protected final void setReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(int ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    protected final void setReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = 0;
        } else {
            try {
                reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = Integer.valueOf(fields[n]);
                if (reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective " + fields[n]);
                    System.err.println("ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
//                throw new Exception("reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || reasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the hBClaimTreatAsDateMade
     */
    public String getHBClaimTreatAsDateMade() {
        return hBClaimTreatAsDateMade;
    }

    /**
     * @param HBClaimTreatAsDateMade the hBClaimTreatAsDateMade to set
     */
    protected final void setHBClaimTreatAsDateMade(String HBClaimTreatAsDateMade) {
        this.hBClaimTreatAsDateMade = HBClaimTreatAsDateMade;
    }

    /**
     * @return the sourceOfTheMostRecentHBClaim
     */
    public int getSourceOfTheMostRecentHBClaim() {
        return sourceOfTheMostRecentHBClaim;
    }

    /**
     * @param SourceOfTheMostRecentHBClaim the sourceOfTheMostRecentHBClaim to
     * set
     */
    protected final void setSourceOfTheMostRecentHBClaim(int SourceOfTheMostRecentHBClaim) {
        this.sourceOfTheMostRecentHBClaim = SourceOfTheMostRecentHBClaim;
    }

    protected final void setSourceOfTheMostRecentHBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            sourceOfTheMostRecentHBClaim = 0;
        } else {
            try {
                sourceOfTheMostRecentHBClaim = Integer.valueOf(fields[n]);
                if (sourceOfTheMostRecentHBClaim > 99 || sourceOfTheMostRecentHBClaim < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("SourceOfTheMostRecentHBClaim " + fields[n]);
                    System.err.println("SourceOfTheMostRecentHBClaim > 99 || SourceOfTheMostRecentHBClaim < 0");
//                throw new Exception("sourceOfTheMostRecentHBClaim > 99 || sourceOfTheMostRecentHBClaim < 0");
                }
            } catch (NumberFormatException e) {
                // For September 2014 there is some messed up data.
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in setSourceOfTheMostRecentHBClaim(n,String[])");
            }
        }
    }

    /**
     * @return the didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim
     */
    public int getDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim() {
        return didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim;
    }

    /**
     * @param DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim the
     * didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim to set
     */
    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim(int DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim) {
        this.didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim = DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim;
    }

    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim = 0;
        } else {
            try {
                didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim = Integer.valueOf(fields[n]);
                if (didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim > 1 || didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim " + fields[n]);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim > 1 || DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim < 0");
//                throw new Exception("didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim > 1 || didVerificationIdentifyAnyIncorrectInformationOnTheHBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the dateOfFirstHBPaymentRentAllowanceOnly
     */
    public String getDateOfFirstHBPaymentRentAllowanceOnly() {
        return dateOfFirstHBPaymentRentAllowanceOnly;
    }

    /**
     * @param DateOfFirstHBPaymentRentAllowanceOnly the
     * dateOfFirstHBPaymentRentAllowanceOnly to set
     */
    protected final void setDateOfFirstHBPaymentRentAllowanceOnly(String DateOfFirstHBPaymentRentAllowanceOnly) {
        this.dateOfFirstHBPaymentRentAllowanceOnly = DateOfFirstHBPaymentRentAllowanceOnly;
    }

    /**
     * @return the wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly
     */
    public int getWasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly() {
        return wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly;
    }

    /**
     * @param WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly the
     * wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly to set
     */
    protected final void setWasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly(int WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly) {
        this.wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly = WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly;
    }

    protected final void setWasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly = 0;
        } else {
            try {
                wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly = Integer.valueOf(fields[n]);
                if (wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly > 1 || wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly " + fields[n]);
                    System.err.println("WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly > 1 || WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly < 0");
//                    throw new Exception("wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly > 1 || wasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the cTBClaimTreatAsMadeDate
     */
    public String getCTBClaimTreatAsMadeDate() {
        return cTBClaimTreatAsMadeDate;
    }

    /**
     * @param CTBClaimTreatAsMadeDate the cTBClaimTreatAsMadeDate to set
     */
    protected final void setCTBClaimTreatAsMadeDate(String CTBClaimTreatAsMadeDate) {
        this.cTBClaimTreatAsMadeDate = CTBClaimTreatAsMadeDate;
    }

    /**
     * @return the sourceOfTheMostRecentCTBClaim
     */
    public int getSourceOfTheMostRecentCTBClaim() {
        return sourceOfTheMostRecentCTBClaim;
    }

    /**
     * @param SourceOfTheMostRecentCTBClaim the sourceOfTheMostRecentCTBClaim to
     * set
     */
    protected final void setSourceOfTheMostRecentCTBClaim(int SourceOfTheMostRecentCTBClaim) {
        this.sourceOfTheMostRecentCTBClaim = SourceOfTheMostRecentCTBClaim;
    }

    protected final void setSourceOfTheMostRecentCTBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            sourceOfTheMostRecentCTBClaim = 0;
        } else {
            try {
                sourceOfTheMostRecentCTBClaim = Integer.valueOf(fields[n]);
                if (sourceOfTheMostRecentCTBClaim > 99 || sourceOfTheMostRecentCTBClaim < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("SourceOfTheMostRecentCTBClaim " + fields[n]);
                    System.err.println("SourceOfTheMostRecentCTBClaim > 99 || SourceOfTheMostRecentCTBClaim < 0");
//                throw new Exception("sourceOfTheMostRecentCTBClaim > 99 || sourceOfTheMostRecentCTBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in SourceOfTheMostRecentCTBClaim(n,String[])");
            }
        }
    }

    /**
     * @return the didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim
     */
    public int getDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim() {
        return didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim;
    }

    /**
     * @param DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim the
     * didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim to set
     */
    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim(int DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim) {
        this.didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim = DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim;
    }

    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim = 0;
        } else {
            try {
                didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim = Integer.valueOf(fields[n]);
                if (didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim > 1 || didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim " + fields[n]);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim > 10 || DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim < 0");
//                throw new Exception("didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim > 10 || didVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly
     */
    public int getIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly() {
        return isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly;
    }

    /**
     * @param IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly the
     * isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly to set
     */
    protected final void setIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly(int IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly) {
        this.isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly = IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly;
    }

    protected final void setIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly = 0;
        } else {
            try {
                isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly = Integer.valueOf(fields[n]);
                if (isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly > 1 || isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly " + fields[n]);
                    System.err.println("IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly > 1 || IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly < 0");
//                throw new Exception("isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly > 1 || isThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation
     */
    public int getIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation() {
        return ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation;
    }

    /**
     * @param IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation the
     * ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation to set
     */
    protected final void setIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation(int IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation) {
        this.ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation = IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation;
    }

    protected final void setIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation = 0;
        } else {
            try {
                ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation = Integer.valueOf(fields[n]);
                if (ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation > 99 || ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation " + fields[n]);
                    System.err.println("IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation > 99 || IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation < 0");
//                throw new Exception("ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation > 99 || ifSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the totalHBPaymentsCreditsSinceLastExtract
     */
    public int getTotalHBPaymentsCreditsSinceLastExtract() {
        return totalHBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @param TotalHBPaymentsCreditsSinceLastExtract the
     * totalHBPaymentsCreditsSinceLastExtract to set
     */
    protected final void setTotalHBPaymentsCreditsSinceLastExtract(int TotalHBPaymentsCreditsSinceLastExtract) {
        this.totalHBPaymentsCreditsSinceLastExtract = TotalHBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @return the totalCTBPaymentsCreditsSinceLastExtract
     */
    public int getTotalCTBPaymentsCreditsSinceLastExtract() {
        return totalCTBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @param TotalCTBPaymentsCreditsSinceLastExtract the
     * totalCTBPaymentsCreditsSinceLastExtract to set
     */
    protected final void setTotalCTBPaymentsCreditsSinceLastExtract(int TotalCTBPaymentsCreditsSinceLastExtract) {
        this.totalCTBPaymentsCreditsSinceLastExtract = TotalCTBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @return the claimantsEthnicGroup
     */
    public int getClaimantsEthnicGroup() {
        if (claimantsEthnicGroup > 16 || claimantsEthnicGroup < 1) {
            return 16;
        }
        return claimantsEthnicGroup;
    }

    /**
     * @param ClaimantsEthnicGroup the claimantsEthnicGroup to set
     */
    protected final void setClaimantsEthnicGroup(int ClaimantsEthnicGroup) {
        this.claimantsEthnicGroup = ClaimantsEthnicGroup;
    }

    protected final void setClaimantsEthnicGroup(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            claimantsEthnicGroup = 0;
        } else {
            try {
                claimantsEthnicGroup = Integer.valueOf(fields[n]);
                if (claimantsEthnicGroup > 99 || claimantsEthnicGroup < 0) {
                    System.err.println("RecordID " + recordID);
                    System.err.println("n " + n);
                    System.err.println("ClaimantsEthnicGroup " + fields[n]);
                    System.err.println("ClaimantsEthnicGroup > 99 || ClaimantsEthnicGroup < 0");
//                throw new Exception("claimantsEthnicGroup > 99 || claimantsEthnicGroup < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + recordID);
                System.err.println("n " + n);
                System.err.println("setClaimantsEthnicGroup(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the
     * dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     */
    public String getDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim() {
        return dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     * the
     * dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim(String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim) {
        this.dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim = DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @return the
     * dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     */
    public final String getDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim() {
        return dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     * the
     * dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim(String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim) {
        this.dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim = DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @return the dateCouncilTaxPayable
     */
    public String getDateCouncilTaxPayable() {
        return dateCouncilTaxPayable;
    }

    /**
     * @param DateCouncilTaxPayable the dateCouncilTaxPayable to set
     */
    protected final void setDateCouncilTaxPayable(String DateCouncilTaxPayable) {
        this.dateCouncilTaxPayable = DateCouncilTaxPayable;
    }

    /**
     * @return the
     * dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     */
    public String getDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim() {
        return dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     * the
     * dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim(String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim) {
        this.dateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim = DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @return the
     * dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     */
    public String getDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim() {
        return dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     * the
     * dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim(String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim) {
        this.dateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim = DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;
    }

}
