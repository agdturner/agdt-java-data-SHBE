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

import java.io.Serializable;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.data.ONSPD_Postcode_Handler;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;

/**
 *
 * @author geoagdt
 */
public abstract class SHBE_DAC_RecordAbstract extends DW_SHBE_RecordAbstract implements Serializable {

    /**
     * 4 8 ClaimantsDateOfBirth
     */
    private String ClaimantsDateOfBirth;
    /**
     * 5 9 TenancyType
     */
    private int TenancyType;
    /**
     * 6 11 ClaimantsPostcode
     */
    private String ClaimantsPostcode;
    /**
     * 7 12 PassportedStandardIndicator
     */
    private int PassportedStandardIndicator;
    /**
     * 28 33 StatusOfHBClaimAtExtractDate 0 = Other; 1 = InPayment; 2 =
     * Suspended.
     */
    private int StatusOfHBClaimAtExtractDate;
    /**
     * 29 34 StatusOfCTBClaimAtExtractDate
     */
    private int StatusOfCTBClaimAtExtractDate;
    /**
     * 30 35 DateMostRecentHBClaimWasReceived
     */
    private String DateMostRecentHBClaimWasReceived;
    /**
     * 31 36 DateMostRecentCTBClaimWasReceived
     */
    private String DateMostRecentCTBClaimWasReceived;
    /**
     * 57 62 LHARegulationsApplied Blank - LHA flag set to 'No' 1 - LHA flag set
     * to 'Yes'
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1!!!!!!!!!!!!!!
     * In the SHBE Extracts for Leeds this is generally set as "No" or "Yes"
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1!!!!!!!!!!!!!!
     */
    private String LHARegulationsApplied;
    /**
     * 121 126 PartnersNationalInsuranceNumber
     */
    private String PartnersNationalInsuranceNumber;
    /**
     * 169 177 ClaimantsGender
     */
    private String ClaimantsGender;
    /**
     * 170 178 PartnersDateOfBirth
     */
    private String PartnersDateOfBirth;
    /**
     * 194 202 DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private String DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    /**
     * 195 203 DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private String DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    /**
     * 202 210 ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private int ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    /**
     * 203 211 ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    private int ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    /**
     * 211 220 HBClaimTreatAsDateMade
     */
    private String HBClaimTreatAsDateMade;
    /**
     * 212 221 SourceOfMostRecentHBClaim
     */
    private int SourceOfTheMostRecentHBClaim;
    /**
     * 213 222 DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim
     */
    private int DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim;
    /**
     * 214 223 DateOfFirstHBPaymentRentAllowanceOnly
     */
    private String DateOfFirstHBPaymentRentAllowanceOnly;
    /**
     * 215 224 WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly
     */
    private int WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly;
    /**
     * 220 229 CTBClaimTreatAsMadeDate
     */
    private String CTBClaimTreatAsMadeDate;
    /**
     * 221 230 SourceOfTheMostRecentCTBClaim
     */
    private int SourceOfTheMostRecentCTBClaim;
    /**
     * 222 231 DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim
     */
    private int DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim;
    /**
     * 227 236 IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly
     */
    private int IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly;
    /**
     * 228 237 IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation
     */
    private int IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation;
    /*
     * 236 245 TotalHBPaymentsCreditsSinceLastExtract
     */
    private int TotalHBPaymentsCreditsSinceLastExtract;
    /**
     * 237 246 TotalCTBPaymentsCreditsSinceLastExtract
     */
    private int TotalCTBPaymentsCreditsSinceLastExtract;
    /**
     * 238 247 ClaimantsEthnicGroup 1 White: British 2 White: Irish 3 White: Any
     * Other 4 Mixed: White and Black Caribbean 5 Mixed: White and Black African
     * 6 Mixed: White and Asian 7 Mixed: Any Other 8 Asian or Asian British:
     * Indian 9 Asian or Asian British: Pakistani 10 Asian or Asian British:
     * Bangladeshi 11 Asian or Asian British: Any Other 12 Black or Black
     * British: Caribbean 13 Black or Black British: African 14 Black or Black
     * British: Any Other 15 Chinese 99 Any Other
     */
    private int ClaimantsEthnicGroup;
    /**
     * 260 269
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     */
    private String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim;
    /**
     * 261 270
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     */
    private String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim;
    /**
     * 262 271 DateCouncilTaxPayable
     */
    private String DateCouncilTaxPayable;
    /**
     * 263 272
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     */
    private String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim;
    /**
     * 264 273
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     */
    private String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;

    public SHBE_DAC_RecordAbstract(SHBE_Environment env) {
        super(env);
    }

    public String toStringBrief() {
        return super.toString()
                + ",ClaimantsDateOfBirth " + ClaimantsDateOfBirth
                + ",TenancyType " + TenancyType
                + ",ClaimantsPostcode " + ClaimantsPostcode
                + ",PassportedStandardIndicator " + PassportedStandardIndicator
                + ",StatusOfHBClaimAtExtractDate " + StatusOfHBClaimAtExtractDate
                + ",StatusOfCTBClaimAtExtractDate " + StatusOfCTBClaimAtExtractDate
                + ",ClaimantsGender " + ClaimantsGender
                + ",ClaimantsEthnicGroup " + ClaimantsEthnicGroup;
    }

    @Override
    public String toString() {
        return super.toString()
                + ",ClaimantsDateOfBirth " + ClaimantsDateOfBirth
                + ",TenancyType " + TenancyType
                + ",ClaimantsPostcode " + ClaimantsPostcode
                + ",PassportedStandardIndicator " + PassportedStandardIndicator
                + ",StatusOfHBClaimAtExtractDate " + StatusOfHBClaimAtExtractDate
                + ",StatusOfCTBClaimAtExtractDate " + StatusOfCTBClaimAtExtractDate
                + ",DateMostRecentHBClaimWasReceived " + DateMostRecentHBClaimWasReceived
                + ",DateMostRecentCTBClaimWasReceived " + DateMostRecentCTBClaimWasReceived
                + ",LHARegulationsApplied " + LHARegulationsApplied
                + ",PartnersNationalInsuranceNumber " + PartnersNationalInsuranceNumber
                + ",ClaimantsGender " + ClaimantsGender
                + ",PartnersDateOfBirth " + PartnersDateOfBirth
                + ",DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective " + DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective
                + ",DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective " + DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective
                + ",ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective " + ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
                + ",ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective " + ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
                + ",HBClaimTreatAsDateMade " + HBClaimTreatAsDateMade
                + ",DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim " + DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim
                + ",DateOfFirstHBPaymentRentAllowanceOnly " + DateOfFirstHBPaymentRentAllowanceOnly
                + ",WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly " + WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly
                + ",CTBClaimTreatAsMadeDate " + CTBClaimTreatAsMadeDate
                + ",SourceOfTheMostRecentCTBClaim " + SourceOfTheMostRecentCTBClaim
                + ",DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim " + DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim
                + ",InThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly " + IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly
                + ",IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation " + IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation
                + ",TotalHBPaymentsCreditsSinceLastExtract " + TotalHBPaymentsCreditsSinceLastExtract
                + ",TotalCTBPaymentsCreditsSinceLastExtract " + TotalCTBPaymentsCreditsSinceLastExtract
                + ",ClaimantsEthnicGroup " + ClaimantsEthnicGroup
                + ",DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim " + DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
                + ",DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim " + DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
                + ",DateCouncilTaxPayable " + DateCouncilTaxPayable
                + ",DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim " + DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
                + ",DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim " + DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @return the ClaimantsDateOfBirth
     */
    public String getClaimantsDateOfBirth() {
        return ClaimantsDateOfBirth;
    }

    /**
     * @param ClaimantsDateOfBirth the ClaimantsDateOfBirth to set
     */
    protected final void setClaimantsDateOfBirth(String ClaimantsDateOfBirth) {
        this.ClaimantsDateOfBirth = ClaimantsDateOfBirth;
    }

    /**
     * @return the TenancyType
     */
    public int getTenancyType() {
        return TenancyType;
    }

    /**
     * @param TenancyType the TenancyType to set
     */
    protected final void setTenancyType(int TenancyType) {
        this.TenancyType = TenancyType;
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
            TenancyType = -999;
        } else {
            try {
                TenancyType = Integer.valueOf(fields[n]);
                if (TenancyType > 9 || TenancyType < 1) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("TenancyType " + fields[n]);
                    System.err.println("TenancyType > 9 || TenancyType < 1");
//                throw new Exception("TenancyType > 9 || TenancyType < 1");
                }
            } catch (NumberFormatException e) {
                // For September 2014 there is some messed up data.
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in setSourceOfTheMostRecentHBClaim(n,String[])");
                if (ClaimantsDateOfBirth.trim().isEmpty()) {
                    ClaimantsDateOfBirth = fields[n];
                    System.err.println("ClaimantsDateOfBirth set to " + ClaimantsDateOfBirth);
                    TenancyType = -999;
                    System.err.println("TenancyType set to " + TenancyType);
                }
            }
        }
    }

    /**
     * @return the ClaimantsPostcode
     */
    public String getClaimantsPostcode() {
        return ClaimantsPostcode;
    }

    /**
     * @param ClaimantsPostcode the ClaimantsPostcode to set
     */
    protected final void setClaimantsPostcode(String ClaimantsPostcode) {
        this.ClaimantsPostcode = ClaimantsPostcode;
    }
    
    /**
     * @return the PassportedStandardIndicator
     */
    public int getPassportedStandardIndicator() {
        return PassportedStandardIndicator;
    }

    /**
     * @param PassportedStandardIndicator the PassportedStandardIndicator to set
     */
    protected final void setPassportedStandardIndicator(int PassportedStandardIndicator) {
        this.PassportedStandardIndicator = PassportedStandardIndicator;
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
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in setPassportedStandardIndicator(n,String[])");
                System.err.println("ClaimantsPostcode originally set to " + ClaimantsPostcode);
                ClaimantsPostcode = fields[n];
                System.err.println("ClaimantsPostcode now set to " + ClaimantsPostcode);
                n++;
                setPassportedStandardIndicator(n, fields);
                System.err.println("PassportedStandardIndicator set to " + PassportedStandardIndicator);
            }
            if (PassportedStandardIndicator > 5 || PassportedStandardIndicator < 0) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("PassportedStandardIndicator " + fields[n]);
                System.err.println("PassportedStandardIndicator > 5 || PassportedStandardIndicator < 0");
//                throw new Exception("PassportedStandardIndicator > 5 || PassportedStandardIndicator < 0");
            }
        }
    }

    /**
     * @return the StatusOfHBClaimAtExtractDate: 0 = Other; 1 = InPayment; 2 =
     * Suspended.
     */
    public int getStatusOfHBClaimAtExtractDate() {
        return StatusOfHBClaimAtExtractDate;
    }

    /**
     * @param StatusOfHBClaimAtExtractDate the StatusOfHBClaimAtExtractDate to
     * set
     */
    protected final void setStatusOfHBClaimAtExtractDate(int StatusOfHBClaimAtExtractDate) {
        this.StatusOfHBClaimAtExtractDate = StatusOfHBClaimAtExtractDate;
    }

    protected final void setStatusOfHBClaimAtExtractDate(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setStatusOfHBClaimAtExtractDate(-999);
        } else {
            try {
                setStatusOfHBClaimAtExtractDate(Integer.valueOf(fields[n]));
                if (StatusOfHBClaimAtExtractDate > 2 || StatusOfHBClaimAtExtractDate < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("StatusOfHBClaimAtExtractDate " + fields[n]);
                    System.err.println("StatusOfHBClaimAtExtractDate > 2 || StatusOfHBClaimAtExtractDate < 0");
//                throw new Exception("StatusOfHBClaimAtExtractDate > 2 || StatusOfHBClaimAtExtractDate < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setStatusOfHBClaimAtExtractDate(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the StatusOfCTBClaimAtExtractDate
     */
    public int getStatusOfCTBClaimAtExtractDate() {
        return StatusOfCTBClaimAtExtractDate;
    }

    /**
     * @param StatusOfCTBClaimAtExtractDate the StatusOfCTBClaimAtExtractDate to
     * set
     */
    protected final void setStatusOfCTBClaimAtExtractDate(int StatusOfCTBClaimAtExtractDate) {
        this.StatusOfCTBClaimAtExtractDate = StatusOfCTBClaimAtExtractDate;
    }

    protected final void setStatusOfCTBClaimAtExtractDate(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            StatusOfCTBClaimAtExtractDate = -999;
        } else {
            try {
                StatusOfCTBClaimAtExtractDate = Integer.valueOf(fields[n]);
                if (StatusOfCTBClaimAtExtractDate > 2 || StatusOfCTBClaimAtExtractDate < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("StatusOfCTBClaimAtExtractDate " + fields[n]);
                    System.err.println("StatusOfCTBClaimAtExtractDate > 2 || StatusOfCTBClaimAtExtractDate < 0");
//                throw new Exception("StatusOfCTBClaimAtExtractDate > 2 || StatusOfCTBClaimAtExtractDate < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setStatusOfCTBClaimAtExtractDate(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the DateMostRecentHBClaimWasReceived
     */
    public String getDateMostRecentHBClaimWasReceived() {
        return DateMostRecentHBClaimWasReceived;
    }

    /**
     * @param DateMostRecentHBClaimWasReceived the
     * DateMostRecentHBClaimWasReceived to set
     */
    protected final void setDateMostRecentHBClaimWasReceived(String DateMostRecentHBClaimWasReceived) {
        this.DateMostRecentHBClaimWasReceived = DateMostRecentHBClaimWasReceived;
    }

    /**
     * @return the DateMostRecentCTBClaimWasReceived
     */
    public String getDateMostRecentCTBClaimWasReceived() {
        return DateMostRecentCTBClaimWasReceived;
    }

    /**
     * @param DateMostRecentCTBClaimWasReceived the
     * DateMostRecentCTBClaimWasReceived to set
     */
    protected final void setDateMostRecentCTBClaimWasReceived(String DateMostRecentCTBClaimWasReceived) {
        this.DateMostRecentCTBClaimWasReceived = DateMostRecentCTBClaimWasReceived;
    }

    /**
     * @return the LHARegulationsApplied
     */
    public String getLHARegulationsApplied() {
        return LHARegulationsApplied;
    }

    /**
     * @param LHARegulationsApplied the LHARegulationsApplied to set
     */
    protected final void setLHARegulationsApplied(String LHARegulationsApplied) {
        this.LHARegulationsApplied = LHARegulationsApplied;
    }

    /**
     * @return the PartnersNationalInsuranceNumber
     */
    public String getPartnersNationalInsuranceNumber() {
        return PartnersNationalInsuranceNumber;
    }

    /**
     * @param PartnersNationalInsuranceNumber the
     * PartnersNationalInsuranceNumber to set
     */
    protected final void setPartnersNationalInsuranceNumber(String PartnersNationalInsuranceNumber) {
        this.PartnersNationalInsuranceNumber = PartnersNationalInsuranceNumber;
    }

    /**
     * @return the ClaimantsGender
     */
    public String getClaimantsGender() {
        return ClaimantsGender;
    }

    /**
     * @param ClaimantsGender the ClaimantsGender to set
     */
    protected final void setClaimantsGender(String ClaimantsGender) {
        this.ClaimantsGender = ClaimantsGender;
    }

    /**
     * @return the PartnersDateOfBirth
     */
    public String getPartnersDateOfBirth() {
        return PartnersDateOfBirth;
    }

    /**
     * @param PartnersDateOfBirth the PartnersDateOfBirth to set
     */
    protected final void setPartnersDateOfBirth(String PartnersDateOfBirth) {
        this.PartnersDateOfBirth = PartnersDateOfBirth;
    }

    /**
     * @return the DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public String getDateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective() {
        return DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective the
     * DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective to set
     */
    protected final void setDateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective(String DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective = DateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @return the DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public String getDateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective() {
        return DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective the
     * DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective to set
     */
    protected final void setDateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective(String DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective = DateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @return the
     * ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public int getReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective() {
        return ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     * the ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective to
     * set
     */
    protected final void setReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(int ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    protected final void setReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = 0;
        } else {
            try {
                ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = Integer.valueOf(fields[n]);
                if (ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective " + fields[n]);
                    System.err.println("ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
//                throw new Exception("ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
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
     * ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     */
    public int getReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective() {
        return ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    /**
     * @param ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective
     * the ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective to
     * set
     */
    protected final void setReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(int ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective) {
        this.ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective;
    }

    protected final void setReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = 0;
        } else {
            try {
                ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective = Integer.valueOf(fields[n]);
                if (ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective " + fields[n]);
                    System.err.println("ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
//                throw new Exception("ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective > 9 || ReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the HBClaimTreatAsDateMade
     */
    public String getHBClaimTreatAsDateMade() {
        return HBClaimTreatAsDateMade;
    }

    /**
     * @param HBClaimTreatAsDateMade the HBClaimTreatAsDateMade to set
     */
    protected final void setHBClaimTreatAsDateMade(String HBClaimTreatAsDateMade) {
        this.HBClaimTreatAsDateMade = HBClaimTreatAsDateMade;
    }

    /**
     * @return the SourceOfTheMostRecentHBClaim
     */
    public int getSourceOfTheMostRecentHBClaim() {
        return SourceOfTheMostRecentHBClaim;
    }

    /**
     * @param SourceOfTheMostRecentHBClaim the SourceOfTheMostRecentHBClaim to
     * set
     */
    protected final void setSourceOfTheMostRecentHBClaim(int SourceOfTheMostRecentHBClaim) {
        this.SourceOfTheMostRecentHBClaim = SourceOfTheMostRecentHBClaim;
    }

    protected final void setSourceOfTheMostRecentHBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            SourceOfTheMostRecentHBClaim = 0;
        } else {
            try {
                SourceOfTheMostRecentHBClaim = Integer.valueOf(fields[n]);
                if (SourceOfTheMostRecentHBClaim > 99 || SourceOfTheMostRecentHBClaim < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("SourceOfTheMostRecentHBClaim " + fields[n]);
                    System.err.println("SourceOfTheMostRecentHBClaim > 99 || SourceOfTheMostRecentHBClaim < 0");
//                throw new Exception("SourceOfTheMostRecentHBClaim > 99 || SourceOfTheMostRecentHBClaim < 0");
                }
            } catch (NumberFormatException e) {
                // For September 2014 there is some messed up data.
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in setSourceOfTheMostRecentHBClaim(n,String[])");
            }
        }
    }

    /**
     * @return the DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim
     */
    public int getDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim() {
        return DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim;
    }

    /**
     * @param DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim the
     * DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim to set
     */
    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim(int DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim) {
        this.DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim = DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim;
    }

    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim = 0;
        } else {
            try {
                DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim = Integer.valueOf(fields[n]);
                if (DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim > 1 || DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim " + fields[n]);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim > 1 || DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim < 0");
//                throw new Exception("DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim > 1 || DidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the DateOfFirstHBPaymentRentAllowanceOnly
     */
    public String getDateOfFirstHBPaymentRentAllowanceOnly() {
        return DateOfFirstHBPaymentRentAllowanceOnly;
    }

    /**
     * @param DateOfFirstHBPaymentRentAllowanceOnly the
     * DateOfFirstHBPaymentRentAllowanceOnly to set
     */
    protected final void setDateOfFirstHBPaymentRentAllowanceOnly(String DateOfFirstHBPaymentRentAllowanceOnly) {
        this.DateOfFirstHBPaymentRentAllowanceOnly = DateOfFirstHBPaymentRentAllowanceOnly;
    }

    /**
     * @return the WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly
     */
    public int getWasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly() {
        return WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly;
    }

    /**
     * @param WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly the
     * WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly to set
     */
    protected final void setWasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly(int WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly) {
        this.WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly = WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly;
    }

    protected final void setWasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly = 0;
        } else {
            try {
                WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly = Integer.valueOf(fields[n]);
                if (WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly > 1 || WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly " + fields[n]);
                    System.err.println("WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly > 1 || WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly < 0");
//                    throw new Exception("WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly > 1 || WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("WasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the CTBClaimTreatAsMadeDate
     */
    public String getCTBClaimTreatAsMadeDate() {
        return CTBClaimTreatAsMadeDate;
    }

    /**
     * @param CTBClaimTreatAsMadeDate the CTBClaimTreatAsMadeDate to set
     */
    protected final void setCTBClaimTreatAsMadeDate(String CTBClaimTreatAsMadeDate) {
        this.CTBClaimTreatAsMadeDate = CTBClaimTreatAsMadeDate;
    }

    /**
     * @return the SourceOfTheMostRecentCTBClaim
     */
    public int getSourceOfTheMostRecentCTBClaim() {
        return SourceOfTheMostRecentCTBClaim;
    }

    /**
     * @param SourceOfTheMostRecentCTBClaim the SourceOfTheMostRecentCTBClaim to
     * set
     */
    protected final void setSourceOfTheMostRecentCTBClaim(int SourceOfTheMostRecentCTBClaim) {
        this.SourceOfTheMostRecentCTBClaim = SourceOfTheMostRecentCTBClaim;
    }

    protected final void setSourceOfTheMostRecentCTBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            SourceOfTheMostRecentCTBClaim = 0;
        } else {
            try {
                SourceOfTheMostRecentCTBClaim = Integer.valueOf(fields[n]);
                if (SourceOfTheMostRecentCTBClaim > 99 || SourceOfTheMostRecentCTBClaim < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("SourceOfTheMostRecentCTBClaim " + fields[n]);
                    System.err.println("SourceOfTheMostRecentCTBClaim > 99 || SourceOfTheMostRecentCTBClaim < 0");
//                throw new Exception("SourceOfTheMostRecentCTBClaim > 99 || SourceOfTheMostRecentCTBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("NumberFormatException in SourceOfTheMostRecentCTBClaim(n,String[])");
            }
        }
    }

    /**
     * @return the DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim
     */
    public int getDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim() {
        return DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim;
    }

    /**
     * @param DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim the
     * DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim to set
     */
    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim(int DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim) {
        this.DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim = DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim;
    }

    protected final void setDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim = 0;
        } else {
            try {
                DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim = Integer.valueOf(fields[n]);
                if (DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim > 1 || DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim " + fields[n]);
                    System.err.println("DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim > 10 || DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim < 0");
//                throw new Exception("DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim > 10 || DidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly
     */
    public int getIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly() {
        return IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly;
    }

    /**
     * @param IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly the
     * IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly to set
     */
    protected final void setIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly(int IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly) {
        this.IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly = IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly;
    }

    protected final void setIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly = 0;
        } else {
            try {
                IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly = Integer.valueOf(fields[n]);
                if (IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly > 1 || IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly " + fields[n]);
                    System.err.println("IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly > 1 || IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly < 0");
//                throw new Exception("IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly > 1 || IsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation
     */
    public int getIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation() {
        return IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation;
    }

    /**
     * @param IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation the
     * IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation to set
     */
    protected final void setIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation(int IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation) {
        this.IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation = IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation;
    }

    protected final void setIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation = 0;
        } else {
            try {
                IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation = Integer.valueOf(fields[n]);
                if (IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation > 99 || IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation " + fields[n]);
                    System.err.println("IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation > 99 || IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation < 0");
//                throw new Exception("IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation > 99 || IfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation(int,String[])");
                System.err.println("fields[n], " + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the TotalHBPaymentsCreditsSinceLastExtract
     */
    public int getTotalHBPaymentsCreditsSinceLastExtract() {
        return TotalHBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @param TotalHBPaymentsCreditsSinceLastExtract the
     * TotalHBPaymentsCreditsSinceLastExtract to set
     */
    protected final void setTotalHBPaymentsCreditsSinceLastExtract(int TotalHBPaymentsCreditsSinceLastExtract) {
        this.TotalHBPaymentsCreditsSinceLastExtract = TotalHBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @return the TotalCTBPaymentsCreditsSinceLastExtract
     */
    public int getTotalCTBPaymentsCreditsSinceLastExtract() {
        return TotalCTBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @param TotalCTBPaymentsCreditsSinceLastExtract the
     * TotalCTBPaymentsCreditsSinceLastExtract to set
     */
    protected final void setTotalCTBPaymentsCreditsSinceLastExtract(int TotalCTBPaymentsCreditsSinceLastExtract) {
        this.TotalCTBPaymentsCreditsSinceLastExtract = TotalCTBPaymentsCreditsSinceLastExtract;
    }

    /**
     * @return the ClaimantsEthnicGroup
     */
    public int getClaimantsEthnicGroup() {
        if (ClaimantsEthnicGroup > 16 || ClaimantsEthnicGroup < 1) {
            return 16;
        }
        return ClaimantsEthnicGroup;
    }

    /**
     * @param ClaimantsEthnicGroup the ClaimantsEthnicGroup to set
     */
    protected final void setClaimantsEthnicGroup(int ClaimantsEthnicGroup) {
        this.ClaimantsEthnicGroup = ClaimantsEthnicGroup;
    }

    protected final void setClaimantsEthnicGroup(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            ClaimantsEthnicGroup = 0;
        } else {
            try {
                ClaimantsEthnicGroup = Integer.valueOf(fields[n]);
                if (ClaimantsEthnicGroup > 99 || ClaimantsEthnicGroup < 0) {
                    System.err.println("RecordID " + RecordID);
                    System.err.println("n " + n);
                    System.err.println("ClaimantsEthnicGroup " + fields[n]);
                    System.err.println("ClaimantsEthnicGroup > 99 || ClaimantsEthnicGroup < 0");
//                throw new Exception("ClaimantsEthnicGroup > 99 || ClaimantsEthnicGroup < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
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
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     */
    public String getDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim() {
        return DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     * the
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim(String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim) {
        this.DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim = DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @return the
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     */
    public final String getDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim() {
        return DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     * the
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim(String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim) {
        this.DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim = DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim;
    }

    /**
     * @return the DateCouncilTaxPayable
     */
    public String getDateCouncilTaxPayable() {
        return DateCouncilTaxPayable;
    }

    /**
     * @param DateCouncilTaxPayable the DateCouncilTaxPayable to set
     */
    protected final void setDateCouncilTaxPayable(String DateCouncilTaxPayable) {
        this.DateCouncilTaxPayable = DateCouncilTaxPayable;
    }

    /**
     * @return the
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     */
    public String getDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim() {
        return DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     * the
     * DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim(String DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim) {
        this.DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim = DateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @return the
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     */
    public String getDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim() {
        return DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;
    }

    /**
     * @param
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     * the
     * DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim
     * to set
     */
    protected final void setDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim(String DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim) {
        this.DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim = DateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim;
    }

}
