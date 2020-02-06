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
package uk.ac.leeds.ccg.data.shbe.data.types;

import java.io.Serializable;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;

/**
 *
 * @author geoagdt
 */
public class SHBE_R_Record extends SHBE_DACTEGPRST_Record implements Serializable {

    /**
     * 163 171 DateOverPaymentDetectionActivityInitiatedOnCase
     */
    private String DateOverPaymentDetectionActivityInitiatedOnCase;
    /**
     * 164 172 ReasonForOverpaymentDetectionActivity
     */
    private int ReasonForOverpaymentDetectionActivity;
    /**
     * 165 173 MethodOfOverpayentDetectionActivity
     */
    private int MethodOfOverpayentDetectionActivity;
    /**
     * 166 174 DoesTheOverpaymentDetectionActivityConstituteAFullReview
     */
    private String DoesTheOverpaymentDetectionActivityConstituteAFullReview;
    /**
     * 167 175 DateOverPaymentDetectionActivityIsCompleted
     */
    private String DateOverPaymentDetectionActivityIsCompleted;
    /**
     * 168 176 OutcomeOfOverPaymentDetectionActivity
     */
    private int OutcomeOfOverPaymentDetectionActivity;
    /**
     * 289 316 IfThisActivityResolvesAnHBMSReferralProvideRMSNumber
     */
    private String IfThisActivityResolvesAnHBMSReferralProvideRMSNumber;
    /**
     * 290 317 HBMSRuleScanID
     */
    private String HBMSRuleScanID;
    /**
     * 291 318 DateOfHBMSMatch
     */
    private String DateOfHBMSMatch;
    /**
     * 292 319
     * IfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy
     */
    private String IfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy;

    public SHBE_R_Record(SHBE_Environment env) {
        super(env);
    }

    @Override
    public String toString() {
        return super.toString()
                + ",DateOverPaymentDetectionActivityInitiatedOnCase " + DateOverPaymentDetectionActivityInitiatedOnCase
                + ",ReasonForOverpaymentDetectionActivity " + ReasonForOverpaymentDetectionActivity
                + ",MethodOfOverpayentDetectionActivity " + MethodOfOverpayentDetectionActivity
                + ",DoesTheOverpaymentDetectionActivityConstituteAFullReview " + DoesTheOverpaymentDetectionActivityConstituteAFullReview
                + ",DateOverPaymentDetectionActivityIsCompleted " + DateOverPaymentDetectionActivityIsCompleted
                + ",OutcomeOfOverPaymentDetectionActivity " + OutcomeOfOverPaymentDetectionActivity;
    }

    /**
     * @return the DateOverPaymentDetectionActivityInitiatedOnCase
     */
    public String getDateOverPaymentDetectionActivityInitiatedOnCase() {
        return DateOverPaymentDetectionActivityInitiatedOnCase;
    }

    /**
     * @param DateOverPaymentDetectionActivityInitiatedOnCase the
     * DateOverPaymentDetectionActivityInitiatedOnCase to set
     */
    protected void setDateOverPaymentDetectionActivityInitiatedOnCase(String DateOverPaymentDetectionActivityInitiatedOnCase) {
        this.DateOverPaymentDetectionActivityInitiatedOnCase = DateOverPaymentDetectionActivityInitiatedOnCase;
    }

    /**
     * @return the ReasonForOverpaymentDetectionActivity
     */
    public int getReasonForOverpaymentDetectionActivity() {
        return ReasonForOverpaymentDetectionActivity;
    }

    /**
     * @param ReasonForOverpaymentDetectionActivity the
     * ReasonForOverpaymentDetectionActivity to set
     */
    protected void setReasonForOverpaymentDetectionActivity(int ReasonForOverpaymentDetectionActivity) {
        this.ReasonForOverpaymentDetectionActivity = ReasonForOverpaymentDetectionActivity;
    }

    protected void setReasonForOverpaymentDetectionActivity(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            ReasonForOverpaymentDetectionActivity = 0;
        } else {
            ReasonForOverpaymentDetectionActivity = Integer.valueOf(fields[n]);
            if (ReasonForOverpaymentDetectionActivity > 99 || ReasonForOverpaymentDetectionActivity < 0) {
                System.err.println("ReasonForOverpaymentDetectionActivity " + ReasonForOverpaymentDetectionActivity);
                System.err.println("ReasonForOverpaymentDetectionActivity > 99 || ReasonForOverpaymentDetectionActivity < 0");
                throw new Exception("ReasonForOverpaymentDetectionActivity > 99 || ReasonForOverpaymentDetectionActivity < 0");
            }
        }
    }

    /**
     * @return the MethodOfOverpayentDetectionActivity
     */
    public int getMethodOfOverpayentDetectionActivity() {
        return MethodOfOverpayentDetectionActivity;
    }

    /**
     * @param MethodOfOverpayentDetectionActivity the
     * MethodOfOverpayentDetectionActivity to set
     */
    protected void setMethodOfOverpayentDetectionActivity(int MethodOfOverpayentDetectionActivity) {
        this.MethodOfOverpayentDetectionActivity = MethodOfOverpayentDetectionActivity;
    }

    protected void setMethodOfOverpayentDetectionActivity(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            MethodOfOverpayentDetectionActivity = 0;
        } else {
            MethodOfOverpayentDetectionActivity = Integer.valueOf(fields[n]);
            if (MethodOfOverpayentDetectionActivity > 99 || MethodOfOverpayentDetectionActivity < 0) {
                System.err.println("MethodOfOverpayentDetectionActivity " + MethodOfOverpayentDetectionActivity);
                System.err.println("MethodOfOverpayentDetectionActivity > 99 || MethodOfOverpayentDetectionActivity < 0");
                throw new Exception("MethodOfOverpayentDetectionActivity > 99 || MethodOfOverpayentDetectionActivity < 0");
            }
        }
    }

    /**
     * @return the DoesTheOverpaymentDetectionActivityConstituteAFullReview
     */
    public String getDoesTheOverpaymentDetectionActivityConstituteAFullReview() {
        return DoesTheOverpaymentDetectionActivityConstituteAFullReview;
    }

    /**
     * @param DoesTheOverpaymentDetectionActivityConstituteAFullReview the
     * DoesTheOverpaymentDetectionActivityConstituteAFullReview to set
     */
    protected void setDoesTheOverpaymentDetectionActivityConstituteAFullReview(String DoesTheOverpaymentDetectionActivityConstituteAFullReview) {
        this.DoesTheOverpaymentDetectionActivityConstituteAFullReview = DoesTheOverpaymentDetectionActivityConstituteAFullReview;
    }

    /**
     * @return the DateOverPaymentDetectionActivityIsCompleted
     */
    public String getDateOverPaymentDetectionActivityIsCompleted() {
        return DateOverPaymentDetectionActivityIsCompleted;
    }

    /**
     * @param DateOverPaymentDetectionActivityIsCompleted the
     * DateOverPaymentDetectionActivityIsCompleted to set
     */
    protected void setDateOverPaymentDetectionActivityIsCompleted(String DateOverPaymentDetectionActivityIsCompleted) {
        this.DateOverPaymentDetectionActivityIsCompleted = DateOverPaymentDetectionActivityIsCompleted;
    }

    /**
     * @return the OutcomeOfOverPaymentDetectionActivity
     */
    public int getOutcomeOfOverPaymentDetectionActivity() {
        return OutcomeOfOverPaymentDetectionActivity;
    }

    /**
     * @param OutcomeOfOverPaymentDetectionActivity the
     * OutcomeOfOverPaymentDetectionActivity to set
     */
    protected void setOutcomeOfOverPaymentDetectionActivity(int OutcomeOfOverPaymentDetectionActivity) {
        this.OutcomeOfOverPaymentDetectionActivity = OutcomeOfOverPaymentDetectionActivity;
    }

    protected void setOutcomeOfOverPaymentDetectionActivity(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            OutcomeOfOverPaymentDetectionActivity = 0;
        } else {
            OutcomeOfOverPaymentDetectionActivity = Integer.valueOf(fields[n]);
            if (OutcomeOfOverPaymentDetectionActivity > 99 || OutcomeOfOverPaymentDetectionActivity < 0) {
                System.err.println("OutcomeOfOverPaymentDetectionActivity " + OutcomeOfOverPaymentDetectionActivity);
                System.err.println("OutcomeOfOverPaymentDetectionActivity > 99 || OutcomeOfOverPaymentDetectionActivity < 0");
                throw new Exception("OutcomeOfOverPaymentDetectionActivity > 99 || OutcomeOfOverPaymentDetectionActivity < 0");
            }
        }
    }

    public String getIfThisActivityResolvesAnHBMSReferralProvideRMSNumber() {
        return IfThisActivityResolvesAnHBMSReferralProvideRMSNumber;
    }

    protected void setIfThisActivityResolvesAnHBMSReferralProvideRMSNumber(String IfThisActivityResolvesAnHBMSReferralProvideRMSNumber) {
        this.IfThisActivityResolvesAnHBMSReferralProvideRMSNumber = IfThisActivityResolvesAnHBMSReferralProvideRMSNumber;
    }

    public String getHBMSRuleScanID() {
        return HBMSRuleScanID;
    }

    protected void setHBMSRuleScanID(String HBMSRuleScanID) {
        this.HBMSRuleScanID = HBMSRuleScanID;
    }

    public String getDateOfHBMSMatch() {
        return DateOfHBMSMatch;
    }

    protected void setDateOfHBMSMatch(String DateOfHBMSMatch) {
        this.DateOfHBMSMatch = DateOfHBMSMatch;
    }

    public String getIfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy() {
        return IfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy;
    }

    protected void setIfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy(String IfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy) {
        this.IfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy = IfResolutionOfHBMSReferralDoesNotResultInAFinancialAdjustmentPleaseIndicateTheReasonWhy;
    }

}
