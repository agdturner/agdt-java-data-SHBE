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
public class SHBE_DC_Record extends SHBE_DAC_Record {

    /**
     * 32 37 DateOfFirstDecisionOnMostRecentHBClaim
     */
    private String dateOfFirstDecisionOnMostRecentHBClaim;

    /**
     * 33 38 DateOfFirstDecisionOnMostRecentCTBClaim
     */
    private String dateOfFirstDecisionOnMostRecentCTBClaim;

    /**
     * 34 39 OutcomeOfFirstDecisionOnMostRecentHBClaim
     */
    private int outcomeOfFirstDecisionOnMostRecentHBClaim;

    /**
     * 35 40 OutcomeOfFirstDecisionOnMostRecentCTBClaim
     */
    private int outcomeOfFirstDecisionOnMostRecentCTBClaim;

    /**
     * 36 41 HBClaimEntitlementStartDate
     */
    private String hBClaimEntitlementStartDate;

    /**
     * 58 63 isThisCaseSubjectToLRROrSRRSchemes
     */
    private int isThisCaseSubjectToLRROrSRRSchemes;

    /**
     * 67 72 dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision
     */
    private String dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision;

    /**
     * 193 201 cTBClaimEntitlementStartDate
     */
    private String cTBClaimEntitlementStartDate;

    /**
     * 216 225 wasThereABackdatedAwardMadeOnTheHBClaim
     */
    private int wasThereABackdatedAwardMadeOnTheHBClaim;

    /**
     * 217 226 dateHBBackdatingFrom
     */
    private String dateHBBackdatingFrom;

    /**
     * 218 227 dateHBBackdatingTo
     */
    private String dateHBBackdatingTo;

    /**
     * 219 228 totalAmountOfBackdatedHBAwarded
     */
    private int totalAmountOfBackdatedHBAwarded;

    /**
     * 223 232 wasThereABackdatedAwardMadeOnTheCTBClaim
     */
    private int wasThereABackdatedAwardMadeOnTheCTBClaim;

    /**
     * 224 233 dateCTBBackdatingFrom
     */
    private String dateCTBBackdatingFrom;

    /**
     * 225 234 dateCTBBackdatingTo
     */
    private String dateCTBBackdatingTo;

    /**
     * 226 235 totalAmountOfBackdatedCTBAwarded
     */
    private int totalAmountOfBackdatedCTBAwarded;

    public SHBE_DC_Record(SHBE_Environment env) {
        super(env);
    }

    @Override
    public String toStringBrief() {
        return super.toStringBrief();
    }

    @Override
    public String toString() {
        return super.toString()
                + " ,dateOfFirstDecisionOnMostRecentHBClaim=" + dateOfFirstDecisionOnMostRecentHBClaim
                + " ,dateOfFirstDecisionOnMostRecentCTBClaim=" + dateOfFirstDecisionOnMostRecentCTBClaim
                + " ,outcomeOfFirstDecisionOnMostRecentHBClaim=" + outcomeOfFirstDecisionOnMostRecentHBClaim
                + " ,outcomeOfFirstDecisionOnMostRecentCTBClaim=" + outcomeOfFirstDecisionOnMostRecentCTBClaim
                + " ,hBClaimEntitlementStartDate=" + hBClaimEntitlementStartDate
                + " ,isThisCaseSubjectToLRROrSRRSchemes=" + isThisCaseSubjectToLRROrSRRSchemes
                + " ,dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision=" + dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision
                + " ,cTBClaimEntitlementStartDate=" + cTBClaimEntitlementStartDate
                + " ,wasThereABackdatedAwardMadeOnTheHBClaim=" + wasThereABackdatedAwardMadeOnTheHBClaim
                + " ,dateHBBackdatingFrom=" + dateHBBackdatingFrom
                + " ,dateHBBackdatingTo=" + dateHBBackdatingTo
                + " ,totalAmountOfBackdatesHBAwarded=" + totalAmountOfBackdatedHBAwarded
                + " ,wasThereABackdatedAwardMadeOnTheCTBClaim=" + wasThereABackdatedAwardMadeOnTheCTBClaim
                + " ,dateCTBBackdatingFrom=" + dateCTBBackdatingFrom
                + " ,dateCTBBackdatingTo=" + dateCTBBackdatingTo
                + " ,totalAmountOfBackdatedCTBAwarded=" + totalAmountOfBackdatedCTBAwarded;
    }

    /**
     * @return {@link #isThisCaseSubjectToLRROrSRRSchemes}
     */
    public int getIsThisCaseSubjectToLRROrSRRSchemes() {
        return isThisCaseSubjectToLRROrSRRSchemes;
    }

    /**
     * @param i What {@link #isThisCaseSubjectToLRROrSRRSchemes} is set to.
     */
    protected void setIsThisCaseSubjectToLRROrSRRSchemes(int i) {
        this.isThisCaseSubjectToLRROrSRRSchemes = i;
    }

    protected final void setIsThisCaseSubjectToLRROrSRRSchemes(int n, String[] fields)
            throws Exception {
        if (fields[n].trim().isEmpty()) {
            isThisCaseSubjectToLRROrSRRSchemes = -999;
        } else {
            try {
                isThisCaseSubjectToLRROrSRRSchemes = Integer.valueOf(fields[n]);
                if (isThisCaseSubjectToLRROrSRRSchemes > 4 || isThisCaseSubjectToLRROrSRRSchemes < 1) {
                    System.err.println("IsThisCaseSubjectToLRROrSRRSchemes=" + fields[n]);
                    System.err.println("n=" + n);
                    System.err.println("IsThisCaseSubjectToLRROrSRRSchemes > 4 || IsThisCaseSubjectToLRROrSRRSchemes < 1");
//                    throw new Exception("isThisCaseSubjectToLRROrSRRSchemes > 4 || isThisCaseSubjectToLRROrSRRSchemes < 1");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + recordID);
                System.err.println("n=" + n);
                System.err.println("setIsThisCaseSubjectToLRROrSRRSchemes(int,String[])");
                System.err.println("fields[n],=" + fields[n]);
                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return {@link #dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision}
     */
    public String getDateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision() {
        return dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision;
    }

    /**
     * @param s What {@link #dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision} is set to.
     */
    protected final void setDateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision(String s) {
        this.dateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision = s;
    }

    /**
     * @return {@link #cTBClaimEntitlementStartDate}
     */
    public String getCTBClaimEntitlementStartDate() {
        return cTBClaimEntitlementStartDate;
    }

    /**
     * @param s What {@link #cTBClaimEntitlementStartDate} is set to.
     */
    protected final void setCTBClaimEntitlementStartDate(String s) {
        this.cTBClaimEntitlementStartDate = s;
    }

    /**
     * @return {@link #wasThereABackdatedAwardMadeOnTheHBClaim
     */
    public int getWasThereABackdatedAwardMadeOnTheHBClaim() {
        return wasThereABackdatedAwardMadeOnTheHBClaim;
    }

    /**
     * @param i What {@link #wasThereABackdatedAwardMadeOnTheHBClaim} is set to.
     */
    protected void setWasThereABackdatedAwardMadeOnTheHBClaim(int i) {
        this.wasThereABackdatedAwardMadeOnTheHBClaim = i;
    }

    protected final void setWasThereABackdatedAwardMadeOnTheHBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            wasThereABackdatedAwardMadeOnTheHBClaim = 0;
        } else {
            try {
                wasThereABackdatedAwardMadeOnTheHBClaim = Integer.valueOf(fields[n]);
                if (wasThereABackdatedAwardMadeOnTheHBClaim > 1 || wasThereABackdatedAwardMadeOnTheHBClaim < 0) {
                    System.err.println("WasThereABackdatedAwardMadeOnTheHBClaim=" + fields[n]);
                    System.err.println("n=" + n);
                    System.err.println("WasThereABackdatedAwardMadeOnTheHBClaim > 1 || WasThereABackdatedAwardMadeOnTheHBClaim < 0");
//                    throw new Exception("wasThereABackdatedAwardMadeOnTheHBClaim > 1 || wasThereABackdatedAwardMadeOnTheHBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + recordID);
                System.err.println("n=" + n);
                System.err.println("setWasThereABackdatedAwardMadeOnTheHBClaim(int,String[])");
                System.err.println("fields[n],=" + fields[n]);
                //e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return {@link #dateHBBackdatingFrom}
     */
    public String getDateHBBackdatingFrom() {
        return dateHBBackdatingFrom;
    }

    /**
     * @param s What {@link #dateHBBackdatingFrom} is set to.
     */
    protected final void setDateHBBackdatingFrom(String s) {
        this.dateHBBackdatingFrom = s;
    }

    /**
     * @return {@link #dateHBBackdatingTo}
     */
    public String getDateHBBackdatingTo() {
        return dateHBBackdatingTo;
    }

    /**
     * @param s What {@link #dateHBBackdatingTo} is set to.
     */
    protected final void setDateHBBackdatingTo(String s) {
        this.dateHBBackdatingTo = s;
    }

    /**
     * @return {@link #totalAmountOfBackdatedHBAwarded}
     */
    public int getTotalAmountOfBackdatedHBAwarded() {
        return totalAmountOfBackdatedHBAwarded;
    }

    /**
     * @param i What {@link #totalAmountOfBackdatedHBAwarded} is set to.
     */
    protected void setTotalAmountOfBackdatesHBAwarded(int i) {
        this.totalAmountOfBackdatedHBAwarded = i;
    }

    /**
     * @return {@link #wasThereABackdatedAwardMadeOnTheCTBClaim}
     */
    public int getWasThereABackdatedAwardMadeOnTheCTBClaim() {
        return wasThereABackdatedAwardMadeOnTheCTBClaim;
    }

    /**
     * @param i What {@link #wasThereABackdatedAwardMadeOnTheCTBClaim} is set to.
     */
    protected void setWasThereABackdatedAwardMadeOnTheCTBClaim(int i) {
        this.wasThereABackdatedAwardMadeOnTheCTBClaim = i;
    }

    protected final void setWasThereABackdatedAwardMadeOnTheCTBClaim(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            wasThereABackdatedAwardMadeOnTheCTBClaim = 0;
        } else {
            try {
                wasThereABackdatedAwardMadeOnTheCTBClaim = Integer.valueOf(fields[n]);
                if (wasThereABackdatedAwardMadeOnTheCTBClaim > 1 || wasThereABackdatedAwardMadeOnTheCTBClaim < 0) {
                    System.err.println("WasThereABackdatedAwardMadeOnTheCTBClaim=" + fields[n]);
                    System.err.println("n=" + n);
                    System.err.println("WasThereABackdatedAwardMadeOnTheCTBClaim > 1 || WasThereABackdatedAwardMadeOnTheCTBClaim < 0");
//                    throw new Exception("wasThereABackdatedAwardMadeOnTheCTBClaim > 1 || wasThereABackdatedAwardMadeOnTheCTBClaim < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + recordID);
                System.err.println("n=" + n);
                System.err.println("WasThereABackdatedAwardMadeOnTheCTBClaim(int,String[])");
                System.err.println("fields[n],=" + fields[n]);
                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return {@link #dateCTBBackdatingFrom}
     */
    public String getDateCTBBackdatingFrom() {
        return dateCTBBackdatingFrom;
    }

    /**
     * @param s What {@link #dateCTBBackdatingFrom} is set to.
     */
    protected final void setDateCTBBackdatingFrom(String s) {
        this.dateCTBBackdatingFrom = s;
    }

    /**
     * @return {@link #dDateCTBBackdatingTo}
     */
    public String getDateCTBBackdatingTo() {
        return dateCTBBackdatingTo;
    }

    /**
     * @param s What {@link #dDateCTBBackdatingTo} is set to.
     */
    protected final void setDateCTBBackdatingTo(String s) {
        this.dateCTBBackdatingTo = s;
    }

    /**
     * @return {@link #totalAmountOfBackdatedCTBAwarded}
     */
    public int getTotalAmountOfBackdatedCTBAwarded() {
        return totalAmountOfBackdatedCTBAwarded;
    }

    /**
     * @param i What {@link #totalAmountOfBackdatedCTBAwarded} is set to.
     */
    protected final void setTotalAmountOfBackdatedCTBAwarded(int i) {
        this.totalAmountOfBackdatedCTBAwarded = i;
    }

}
