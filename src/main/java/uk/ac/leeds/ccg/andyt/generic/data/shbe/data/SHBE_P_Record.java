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
public class SHBE_P_Record extends SHBE_RecordAbstract {

    /**
     * 250 259 DateApplicationForRevisionReconsiderationReceived
     */
    private String DateApplicationForRevisionReconsiderationReceived;
    /**
     * 251 260 DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration
     */
    private String DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration;
    /**
     * 252 261 DateAppealApplicationWasLodged
     */
    private String DateAppealApplicationWasLodged;
    /**
     * 253 262 OutcomeOfAppealApplication
     */
    private int OutcomeOfAppealApplication;
    /**
     * 254 263 DateOfOutcomeOfAppealApplication
     */
    private String DateOfOutcomeOfAppealApplication;

    public SHBE_P_Record(SHBE_Environment env) {
        super(env);
    }

    @Override
    public String toString() {
        return super.toString()
                + ",DateApplicationForRevisionReconsiderationReceived " + DateApplicationForRevisionReconsiderationReceived
                + ",DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration " + DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration
                + ",DateAppealApplicationWasLodged " + DateAppealApplicationWasLodged
                + ",OutcomeOfAppealApplication " + OutcomeOfAppealApplication
                + ",DateOfOutcomeOfAppealApplication " + DateOfOutcomeOfAppealApplication;
    }

    /**
     * @return the DateApplicationForRevisionReconsiderationReceived
     */
    public String getDateApplicationForRevisionReconsiderationReceived() {
        return DateApplicationForRevisionReconsiderationReceived;
    }

    /**
     * @param DateApplicationForRevisionReconsiderationReceived the
     * DateApplicationForRevisionReconsiderationReceived to set
     */
    protected void setDateApplicationForRevisionReconsiderationReceived(String DateApplicationForRevisionReconsiderationReceived) {
        this.DateApplicationForRevisionReconsiderationReceived = DateApplicationForRevisionReconsiderationReceived;
    }

    /**
     * @return the
     * DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration
     */
    public String getDateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration() {
        return DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration;
    }

    /**
     * @param DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration
     * the DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration to
     * set
     */
    protected void setDateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration(String DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration) {
        this.DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration = DateClaimantNotifiedAboutTheDecisionOfTheRevisionReconsideration;
    }

    /**
     * @return the DateAppealApplicationWasLodged
     */
    public String getDateAppealApplicationWasLodged() {
        return DateAppealApplicationWasLodged;
    }

    /**
     * @param DateAppealApplicationWasLodged the DateAppealApplicationWasLodged
     * to set
     */
    protected void setDateAppealApplicationWasLodged(String DateAppealApplicationWasLodged) {
        this.DateAppealApplicationWasLodged = DateAppealApplicationWasLodged;
    }

    /**
     * @return the OutcomeOfAppealApplication
     */
    public int getOutcomeOfAppealApplication() {
        return OutcomeOfAppealApplication;
    }

    /**
     * @param OutcomeOfAppealApplication the OutcomeOfAppealApplication to set
     */
    protected void setOutcomeOfAppealApplication(int OutcomeOfAppealApplication) {
        this.OutcomeOfAppealApplication = OutcomeOfAppealApplication;
    }

    protected void setOutcomeOfAppealApplication(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            OutcomeOfAppealApplication = 0;
        } else {
            OutcomeOfAppealApplication = Integer.valueOf(fields[n]);
            if (OutcomeOfAppealApplication > 99 || OutcomeOfAppealApplication < 0) {
                System.err.println("OutcomeOfAppealApplication " + OutcomeOfAppealApplication);
                System.err.println("OutcomeOfAppealApplication > 99 || OutcomeOfAppealApplication < 0");
                throw new Exception("OutcomeOfAppealApplication > 99 || OutcomeOfAppealApplication < 0");
            }
        }
    }

    /**
     * @return the DateOfOutcomeOfAppealApplication
     */
    public String getDateOfOutcomeOfAppealApplication() {
        return DateOfOutcomeOfAppealApplication;
    }

    /**
     * @param DateOfOutcomeOfAppealApplication the
     * DateOfOutcomeOfAppealApplication to set
     */
    protected void setDateOfOutcomeOfAppealApplication(String DateOfOutcomeOfAppealApplication) {
        this.DateOfOutcomeOfAppealApplication = DateOfOutcomeOfAppealApplication;
    }

}
