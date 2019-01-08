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
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Object;

/**
 *
 * @author geoagdt
 */
public abstract class DW_SHBE_RecordAbstract extends SHBE_Object implements Serializable {

    /**
     * 0 RecordID
     */
    protected long RecordID;
    /**
     * 0 1 RecordType
     */
    private String RecordType;
    /**
     * 1 2 HousingBenefitClaimReferenceNumber
     */
    private String HousingBenefitClaimReferenceNumber;
    //private Long HousingBenefitClaimReferenceNumber;
    /**
     * 2 3 CouncilTaxBenefitClaimReferenceNumber
     */
    private String CouncilTaxBenefitClaimReferenceNumber;
    /**
     * 3 4 ClaimantsNationalInsuranceNumber
     */
    private String ClaimantsNationalInsuranceNumber;

    public DW_SHBE_RecordAbstract() {
    }

    public DW_SHBE_RecordAbstract(SHBE_Environment env) {
        super(env);
    }

    @Override
    public String toString() {
        return "RecordID " + RecordID
                + ",RecordType " + RecordType
                + ",HousingBenefitClaimReferenceNumber " + HousingBenefitClaimReferenceNumber
                + ",CouncilTaxBenefitClaimReferenceNumber " + CouncilTaxBenefitClaimReferenceNumber
                + ",ClaimantsNationalInsuranceNumber " + ClaimantsNationalInsuranceNumber;
    }

    /**
     * @return the RecordID
     */
    public long getRecordID() {
        return RecordID;
    }

    /**
     * @param RecordID the RecordID to set
     */
    protected final void setRecordID(long RecordID) {
        this.RecordID = RecordID;
    }

    /**
     * @return the RecordType
     */
    public String getRecordType() {
        return RecordType;
    }

    /**
     * @param RecordType the RecordType to set
     */
    protected final void setRecordType(String RecordType) {
        this.RecordType = RecordType;
    }

    /**
     * @return the HousingBenefitClaimReferenceNumber
     */
    public String getHousingBenefitClaimReferenceNumber() {
        return HousingBenefitClaimReferenceNumber;
    }

    /**
     * @param HousingBenefitClaimReferenceNumber the
     * HousingBenefitClaimReferenceNumber to set
     */
    protected final void setHousingBenefitClaimReferenceNumber(String HousingBenefitClaimReferenceNumber) {
        this.HousingBenefitClaimReferenceNumber = HousingBenefitClaimReferenceNumber;
    }

    /**
     * @return the CouncilTaxBenefitClaimReferenceNumber
     */
    public String getCouncilTaxBenefitClaimReferenceNumber() {
        return CouncilTaxBenefitClaimReferenceNumber;
    }

    /**
     * @param CouncilTaxBenefitClaimReferenceNumber the
     * CouncilTaxBenefitClaimReferenceNumber to set
     */
    protected final void setCouncilTaxBenefitClaimReferenceNumber(String CouncilTaxBenefitClaimReferenceNumber) {
        this.CouncilTaxBenefitClaimReferenceNumber = CouncilTaxBenefitClaimReferenceNumber;
    }

    /**
     * @return the ClaimantsNationalInsuranceNumber
     */
    public String getClaimantsNationalInsuranceNumber() {
        return ClaimantsNationalInsuranceNumber;
    }

    /**
     * @param ClaimantsNationalInsuranceNumber the
     * ClaimantsNationalInsuranceNumber to set
     */
    protected final void setClaimantsNationalInsuranceNumber(String ClaimantsNationalInsuranceNumber) {
        this.ClaimantsNationalInsuranceNumber = ClaimantsNationalInsuranceNumber;
    }

    /**
     * {@code
     * String ClaimRef;
     * ClaimRef = getClaimantsNationalInsuranceNumber();
     * if (ClaimRef == null) {
     * ClaimRef = getHousingBenefitClaimReferenceNumber();
     * }
     * return ClaimRef;
     * }
     *
     * @return
     */
    public String getClaimRef() {
        String ClaimRef;
        ClaimRef = getCouncilTaxBenefitClaimReferenceNumber();
        if (ClaimRef == null) {
            ClaimRef = getHousingBenefitClaimReferenceNumber();
        }
        return ClaimRef;
    }
}
