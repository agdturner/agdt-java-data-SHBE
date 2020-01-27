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
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Object;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public abstract class SHBE_DACTEGPRST_Record extends SHBE_Object {

    /**
     * 0 recordID
     */
    protected long recordID;

    /**
     * 0 1 recordType
     */
    private String recordType;

    /**
     * 1 2 housingBenefitClaimReferenceNumber
     */
    private String housingBenefitClaimReferenceNumber;
    //private Long housingBenefitClaimReferenceNumber;

    /**
     * 2 3 councilTaxBenefitClaimReferenceNumber
     */
    private String councilTaxBenefitClaimReferenceNumber;

    /**
     * 3 4 claimantsNationalInsuranceNumber
     */
    private String claimantsNationalInsuranceNumber;

    public SHBE_DACTEGPRST_Record(SHBE_Environment env) {
        super(env);
    }

    @Override
    public String toString() {
        return "recordID=" + recordID
                + ", recordType=" + recordType
                + " ,housingBenefitClaimReferenceNumber " + housingBenefitClaimReferenceNumber
                + " ,councilTaxBenefitClaimReferenceNumber " + councilTaxBenefitClaimReferenceNumber
                + " ,claimantsNationalInsuranceNumber " + claimantsNationalInsuranceNumber;
    }

    /**
     * @return {@link #recordID}.
     */
    public long getRecordID() {
        return recordID;
    }

    /**
     * Set {@link #recordID}.
     *
     * @param l The value to set.
     */
    protected final void setRecordID(long l) {
        this.recordID = l;
    }

    /**
     * @return {@link #recordType}.
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Set {@link #recordType}.
     *
     * @param s The value to set.
     */
    protected final void setRecordType(String s) {
        this.recordType = s;
    }

    /**
     * @return {@link #housingBenefitClaimReferenceNumber}.
     */
    public String getHousingBenefitClaimReferenceNumber() {
        return housingBenefitClaimReferenceNumber;
    }

    /**
     * Set {@link #housingBenefitClaimReferenceNumber}.
     *
     * @param s The value to set.
     */
    protected final void setHousingBenefitClaimReferenceNumber(String s) {
        this.housingBenefitClaimReferenceNumber = s;
    }

    /**
     * @return {@link #councilTaxBenefitClaimReferenceNumber}
     */
    public String getCouncilTaxBenefitClaimReferenceNumber() {
        return councilTaxBenefitClaimReferenceNumber;
    }

    /**
     * Set {@link #councilTaxBenefitClaimReferenceNumber}.
     *
     * @param s The value to set.
     */
    protected final void setCouncilTaxBenefitClaimReferenceNumber(String s) {
        this.councilTaxBenefitClaimReferenceNumber = s;
    }

    /**
     * @return {@link #claimantsNationalInsuranceNumber}.
     */
    public String getClaimantsNationalInsuranceNumber() {
        return claimantsNationalInsuranceNumber;
    }

    /**
     * Set {@link #claimantsNationalInsuranceNumber}.
     *
     * @param s The value to set.
     */
    protected final void setClaimantsNationalInsuranceNumber(String s) {
        this.claimantsNationalInsuranceNumber = s;
    }

    /**
     * @return {@link #councilTaxBenefitClaimReferenceNumber} or
     * {@link #housingBenefitClaimReferenceNumber}
     */
    public String getClaimRef() {
        String r = getCouncilTaxBenefitClaimReferenceNumber();
        if (r == null) {
            r = getHousingBenefitClaimReferenceNumber();
        }
        return r;
    }
}
