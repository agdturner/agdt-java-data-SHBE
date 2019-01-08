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
public class DW_SHBE_DC_RecordAbstract extends DW_SHBE_DAC_RecordAbstract {

    /**
     * 32 37 DateOfFirstDecisionOnMostRecentHBClaim
     */
    private String DateOfFirstDecisionOnMostRecentHBClaim;
    /**
     * 33 38 DateOfFirstDecisionOnMostRecentCTBClaim
     */
    private String DateOfFirstDecisionOnMostRecentCTBClaim;
    /**
     * 34 39 OutcomeOfFirstDecisionOnMostRecentHBClaim
     */
    private int OutcomeOfFirstDecisionOnMostRecentHBClaim;
    /**
     * 35 40 OutcomeOfFirstDecisionOnMostRecentCTBClaim
     */
    private int OutcomeOfFirstDecisionOnMostRecentCTBClaim;
    /**
     * 36 41 HBClaimEntitlementStartDate
     */
    private String HBClaimEntitlementStartDate;
    /**
     * 58 63 IsThisCaseSubjectToLRROrSRRSchemes
     */
    private int IsThisCaseSubjectToLRROrSRRSchemes;
    /**
     * 67 72 DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision
     */
    private String DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision;
    /**
     * 193 201 CTBClaimEntitlementStartDate
     */
    private String CTBClaimEntitlementStartDate;
    /**
     * 216 225 WasThereABackdatedAwardMadeOnTheHBClaim
     */
    private int WasThereABackdatedAwardMadeOnTheHBClaim;
    /**
     * 217 226 DateHBBackdatingFrom
     */
    private String DateHBBackdatingFrom;
    /**
     * 218 227 DateHBBackdatingTo
     */
    private String DateHBBackdatingTo;
    /**
     * 219 228 TotalAmountOfBackdatedHBAwarded
     */
    private int TotalAmountOfBackdatedHBAwarded;
    /**
     * 223 232 WasThereABackdatedAwardMadeOnTheCTBClaim
     */
    private int WasThereABackdatedAwardMadeOnTheCTBClaim;
    /**
     * 224 233 DateCTBBackdatingFrom
     */
    private String DateCTBBackdatingFrom;
    /**
     * 225 234 DateCTBBackdatingTo
     */
    private String DateCTBBackdatingTo;
    /**
     * 226 235 TotalAmountOfBackdatedCTBAwarded
     */
    private int TotalAmountOfBackdatedCTBAwarded;

    public DW_SHBE_DC_RecordAbstract(SHBE_Environment env) {
        super(env);
    }

    @Override
    public String toStringBrief() {
        return super.toStringBrief();
    }
    
    @Override
    public String toString() {
        return super.toString()
                + ",DateOfFirstDecisionOnMostRecentHBClaim " + DateOfFirstDecisionOnMostRecentHBClaim
                + ",DateOfFirstDecisionOnMostRecentCTBClaim " + DateOfFirstDecisionOnMostRecentCTBClaim
                + ",OutcomeOfFirstDecisionOnMostRecentHBClaim " + OutcomeOfFirstDecisionOnMostRecentHBClaim
                + ",OutcomeOfFirstDecisionOnMostRecentCTBClaim " + OutcomeOfFirstDecisionOnMostRecentCTBClaim
                + ",HBClaimEntitlementStartDate " + HBClaimEntitlementStartDate
                + ",IsThisCaseSubjectToLRROrSRRSchemes " + IsThisCaseSubjectToLRROrSRRSchemes
                + ",DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision " + DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision
                + ",CTBClaimEntitlementStartDate " + CTBClaimEntitlementStartDate
                + ",WasThereABackdatedAwardMadeOnTheHBClaim " + WasThereABackdatedAwardMadeOnTheHBClaim
                + ",DateHBBackdatingFrom " + DateHBBackdatingFrom
                + ",DateHBBackdatingTo " + DateHBBackdatingTo
                + ",TotalAmountOfBackdatesHBAwarded " + TotalAmountOfBackdatedHBAwarded
                + ",WasThereABackdatedAwardMadeOnTheCTBClaim " + WasThereABackdatedAwardMadeOnTheCTBClaim
                + ",DateCTBBackdatingFrom " + DateCTBBackdatingFrom
                + ",DateCTBBackdatingTo " + DateCTBBackdatingTo
                + ",TotalAmountOfBackdatedCTBAwarded " + TotalAmountOfBackdatedCTBAwarded;
    }

    /**
     * @return the IsThisCaseSubjectToLRROrSRRSchemes
     */
    public int getIsThisCaseSubjectToLRROrSRRSchemes() {
        return IsThisCaseSubjectToLRROrSRRSchemes;
    }

    /**
     * @param IsThisCaseSubjectToLRROrSRRSchemes the
     * IsThisCaseSubjectToLRROrSRRSchemes to set
     */
    protected void setIsThisCaseSubjectToLRROrSRRSchemes(int IsThisCaseSubjectToLRROrSRRSchemes) {
        this.IsThisCaseSubjectToLRROrSRRSchemes = IsThisCaseSubjectToLRROrSRRSchemes;
    }

    protected void setIsThisCaseSubjectToLRROrSRRSchemes(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            IsThisCaseSubjectToLRROrSRRSchemes = -999;
        } else {
            try {
                IsThisCaseSubjectToLRROrSRRSchemes = Integer.valueOf(fields[n]);
                if (IsThisCaseSubjectToLRROrSRRSchemes > 4 || IsThisCaseSubjectToLRROrSRRSchemes < 1) {
                    System.err.println("IsThisCaseSubjectToLRROrSRRSchemes " + fields[n]);
                    System.err.println("n " + n);
                    System.err.println("IsThisCaseSubjectToLRROrSRRSchemes > 4 || IsThisCaseSubjectToLRROrSRRSchemes < 1");
//                    throw new Exception("IsThisCaseSubjectToLRROrSRRSchemes > 4 || IsThisCaseSubjectToLRROrSRRSchemes < 1");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setIsThisCaseSubjectToLRROrSRRSchemes(int,String[])");
                System.err.println("fields[n], " + fields[n]);
                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision
     */
    public String getDateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision() {
        return DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision;
    }

    /**
     * @param DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision the
     * DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision to set
     */
    protected void setDateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision(String DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision) {
        this.DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision = DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision;
    }

    /**
     * @return the CTBClaimEntitlementStartDate
     */
    public String getCTBClaimEntitlementStartDate() {
        return CTBClaimEntitlementStartDate;
    }

    /**
     * @param CTBClaimEntitlementStartDate the CTBClaimEntitlementStartDate to
     * set
     */
    protected void setCTBClaimEntitlementStartDate(String CTBClaimEntitlementStartDate) {
        this.CTBClaimEntitlementStartDate = CTBClaimEntitlementStartDate;
    }

    /**
     * @return the WasThereABackdatedAwardMadeOnTheHBClaim
     */
    public int getWasThereABackdatedAwardMadeOnTheHBClaim() {
        return WasThereABackdatedAwardMadeOnTheHBClaim;
    }

    /**
     * @param WasThereABackdatedAwardMadeOnTheHBClaim the
     * WasThereABackdatedAwardMadeOnTheHBClaim to set
     */
    protected void setWasThereABackdatedAwardMadeOnTheHBClaim(int WasThereABackdatedAwardMadeOnTheHBClaim) {
        this.WasThereABackdatedAwardMadeOnTheHBClaim = WasThereABackdatedAwardMadeOnTheHBClaim;
    }

    protected void setWasThereABackdatedAwardMadeOnTheHBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            WasThereABackdatedAwardMadeOnTheHBClaim = 0;
        } else {
            try {
                WasThereABackdatedAwardMadeOnTheHBClaim = Integer.valueOf(fields[n]);
                if (WasThereABackdatedAwardMadeOnTheHBClaim > 1 || WasThereABackdatedAwardMadeOnTheHBClaim < 0) {
                    System.err.println("WasThereABackdatedAwardMadeOnTheHBClaim " + fields[n]);
                    System.err.println("n " + n);
                    System.err.println("WasThereABackdatedAwardMadeOnTheHBClaim > 1 || WasThereABackdatedAwardMadeOnTheHBClaim < 0");
//                    throw new Exception("WasThereABackdatedAwardMadeOnTheHBClaim > 1 || WasThereABackdatedAwardMadeOnTheHBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("setWasThereABackdatedAwardMadeOnTheHBClaim(int,String[])");
                System.err.println("fields[n], " + fields[n]);
                //e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the DateHBBackdatingFrom
     */
    public String getDateHBBackdatingFrom() {
        return DateHBBackdatingFrom;
    }

    /**
     * @param DateHBBackdatingFrom the DateHBBackdatingFrom to set
     */
    protected void setDateHBBackdatingFrom(String DateHBBackdatingFrom) {
        this.DateHBBackdatingFrom = DateHBBackdatingFrom;
    }

    /**
     * @return the DateHBBackdatingTo
     */
    public String getDateHBBackdatingTo() {
        return DateHBBackdatingTo;
    }

    /**
     * @param DateHBBackdatingTo the DateHBBackdatingTo to set
     */
    protected void setDateHBBackdatingTo(String DateHBBackdatingTo) {
        this.DateHBBackdatingTo = DateHBBackdatingTo;
    }

    /**
     * @return the TotalAmountOfBackdatedHBAwarded
     */
    public int getTotalAmountOfBackdatedHBAwarded() {
        return TotalAmountOfBackdatedHBAwarded;
    }

    /**
     * @param TotalAmountOfBackdatedHBAwarded the
     * TotalAmountOfBackdatedHBAwarded to set
     */
    protected void setTotalAmountOfBackdatesHBAwarded(int TotalAmountOfBackdatedHBAwarded) {
        this.TotalAmountOfBackdatedHBAwarded = TotalAmountOfBackdatedHBAwarded;
    }

    /**
     * @return the WasThereABackdatedAwardMadeOnTheCTBClaim
     */
    public int getWasThereABackdatedAwardMadeOnTheCTBClaim() {
        return WasThereABackdatedAwardMadeOnTheCTBClaim;
    }

    /**
     * @param WasThereABackdatedAwardMadeOnTheCTBClaim the
     * WasThereABackdatedAwardMadeOnTheCTBClaim to set
     */
    protected void setWasThereABackdatedAwardMadeOnTheCTBClaim(int WasThereABackdatedAwardMadeOnTheCTBClaim) {
        this.WasThereABackdatedAwardMadeOnTheCTBClaim = WasThereABackdatedAwardMadeOnTheCTBClaim;
    }

    protected void setWasThereABackdatedAwardMadeOnTheCTBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            WasThereABackdatedAwardMadeOnTheCTBClaim = 0;
        } else {
            try {
                WasThereABackdatedAwardMadeOnTheCTBClaim = Integer.valueOf(fields[n]);
                if (WasThereABackdatedAwardMadeOnTheCTBClaim > 1 || WasThereABackdatedAwardMadeOnTheCTBClaim < 0) {
                    System.err.println("WasThereABackdatedAwardMadeOnTheCTBClaim " + fields[n]);
                    System.err.println("n " + n);
                    System.err.println("WasThereABackdatedAwardMadeOnTheCTBClaim > 1 || WasThereABackdatedAwardMadeOnTheCTBClaim < 0");
//                    throw new Exception("WasThereABackdatedAwardMadeOnTheCTBClaim > 1 || WasThereABackdatedAwardMadeOnTheCTBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID " + RecordID);
                System.err.println("n " + n);
                System.err.println("WasThereABackdatedAwardMadeOnTheCTBClaim(int,String[])");
                System.err.println("fields[n], " + fields[n]);
                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the DateCTBBackdatingFrom
     */
    public String getDateCTBBackdatingFrom() {
        return DateCTBBackdatingFrom;
    }

    /**
     * @param DateCTBBackdatingFrom the DateCTBBackdatingFrom to set
     */
    protected void setDateCTBBackdatingFrom(String DateCTBBackdatingFrom) {
        this.DateCTBBackdatingFrom = DateCTBBackdatingFrom;
    }

    /**
     * @return the DateCTBBackdatingTo
     */
    public String getDateCTBBackdatingTo() {
        return DateCTBBackdatingTo;
    }

    /**
     * @param DateCTBBackdatingTo the DateCTBBackdatingTo to set
     */
    protected void setDateCTBBackdatingTo(String DateCTBBackdatingTo) {
        this.DateCTBBackdatingTo = DateCTBBackdatingTo;
    }

    /**
     * @return the TotalAmountOfBackdatedCTBAwarded
     */
    public int getTotalAmountOfBackdatedCTBAwarded() {
        return TotalAmountOfBackdatedCTBAwarded;
    }

    /**
     * @param TotalAmountOfBackdatedCTBAwarded the
     * TotalAmountOfBackdatedCTBAwarded to set
     */
    protected void setTotalAmountOfBackdatedCTBAwarded(int TotalAmountOfBackdatedCTBAwarded) {
        this.TotalAmountOfBackdatedCTBAwarded = TotalAmountOfBackdatedCTBAwarded;
    }

}
