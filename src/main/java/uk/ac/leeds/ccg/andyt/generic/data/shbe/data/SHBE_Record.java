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
import java.util.ArrayList;
import java.util.Iterator;
import uk.ac.leeds.ccg.andyt.generic.data.onspd.core.ONSPD_ID;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_ID;
import uk.ac.leeds.ccg.andyt.generic.data.shbe.core.SHBE_Object;

/**
 *
 * @author geoagdt
 */
public class SHBE_Record extends SHBE_Object implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * StatusOfHBClaimAtExtractDate 0 is OtherPaymentType 1 is InPayment 2 is
     * Suspended
     */
    private int StatusOfHBClaimAtExtractDate;

    /**
     * The ClaimRef SHBE_ID.
     */
    protected SHBE_ID ClaimID;

    /**
     * A convenient lookup for knowing if ClaimPostcodeF is a valid format for a
     * UK postcode.
     */
    protected boolean ClaimPostcodeFValidPostcodeFormat;

    /**
     * A convenient lookup for knowing if ClaimPostcodeF is mappable.
     */
    protected boolean ClaimPostcodeFMappable;

    /**
     * For storing a ONSPD format version of ClaimPostcodeF1.
     */
    protected String ClaimPostcodeF;

    /**
     * For storing if the Claimant Postcode has been modified by removing non
     * A-Z, a-z, 0-9 characters and replacing "O" with "0" or removing "0"
     * altogether.
     */
    protected boolean ClaimPostcodeFAutoModified;

    /**
     * For storing if the ClaimPostcodeF was subsequently (since the extract)
     * checked and modified.
     */
    protected boolean ClaimPostcodeFManModified;

    /**
     * For storing if the ClaimPostcodeF has been updated from the future. For
     * the time being, this is only allowed for Claimant Postcodes that were
     * originally blank or that had invalid formats.
     */
    protected boolean ClaimPostcodeFUpdatedFromTheFuture;

    /**
     * The Postcode SHBE_ID.
     */
    protected ONSPD_ID PostcodeID;

    /**
     * DRecord
     */
    protected SHBE_D_Record DRecord;

    /**
     * SRecords associated with a DRecord
     */
    protected ArrayList<SHBE_S_Record> SRecords;

    /**
     *
     * @param e
     * @param ClaimID The ClaimRef SHBE_ID for this.
     */
    public SHBE_Record(SHBE_Environment e, SHBE_ID ClaimID) {
        super(e);
        this.ClaimID = ClaimID;
    }

    /**
     * Creates a DW_SHBE_Record.
     *
     * @param e
     * @param claimID The ClaimRef SHBE_ID for this.
     * @param DRecord
     */
    public SHBE_Record(SHBE_Environment e, SHBE_ID claimID, SHBE_D_Record DRecord) {
        super(e);
        this.ClaimID = claimID;
        this.DRecord = DRecord;
    }

    /**
     * Returns a Brief String description of this.
     *
     * @return
     */
    public String toStringBrief() {
        String result = "";
        if (DRecord != null) {
            result += "DRecord: " + DRecord.toStringBrief();
            result += Strings.special_newLine;
        }
        SRecords = getSRecords();
        if (SRecords != null) {
            long NumberOfS_Records;
            NumberOfS_Records = SRecords.size();
            result += " Number of SRecords = " + NumberOfS_Records;
            result += Strings.special_newLine;
            if (NumberOfS_Records > 0) {
                result += ": ";
            }
            Iterator<SHBE_S_Record> ite;
            ite = SRecords.iterator();
            while (ite.hasNext()) {
                SHBE_S_Record rec;
                rec = ite.next();
                result += " SRecord: " + rec.toString();
                result += Strings.special_newLine;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        String result;
        result = "ClaimRefSHBE_ID " + ClaimID
                + Strings.special_newLine
                + "StatusOfHBClaimAtExtractDate " + StatusOfHBClaimAtExtractDate
                + Strings.special_newLine;
        if (DRecord != null) {
            result += "DRecord: " + DRecord.toString()
                    + Strings.special_newLine;
        }
        SRecords = getSRecords();
        if (SRecords != null) {
            long NumberOfS_Records;
            NumberOfS_Records = SRecords.size();
            result += " Number of SRecords = " + NumberOfS_Records
                    + Strings.special_newLine;
            if (NumberOfS_Records > 0) {
                result += ": ";
            }
            Iterator<SHBE_S_Record> ite;
            ite = SRecords.iterator();
            while (ite.hasNext()) {
                SHBE_S_Record rec;
                rec = ite.next();
                result += " SRecord: " + rec.toString()
                        + Strings.special_newLine;
            }
        }
        return result;
    }

    /**
     * @return ClaimRefSHBE_ID
     */
    public SHBE_ID getClaimID() {
        return ClaimID;
    }

    /**
     * @return a copy of StatusOfHBClaimAtExtractDate.
     */
    public int getStatusOfHBClaimAtExtractDate() {
        return StatusOfHBClaimAtExtractDate;
    }

    /**
     * @param StatusOfHBClaimAtExtractDate the StatusOfHBClaimAtExtractDate to
     * set
     */
    protected final void getStatusOfHBClaimAtExtractDate(int StatusOfHBClaimAtExtractDate) {
        this.StatusOfHBClaimAtExtractDate = StatusOfHBClaimAtExtractDate;
    }

    /**
     * @return PaymentType
     */
    public String getPaymentType() {
        return Env.Strings.getPaymentTypes().get(StatusOfHBClaimAtExtractDate + 1);
    }

    /**
     *
     * @return
     */
    public SHBE_D_Record getDRecord() {
        return DRecord;
    }

    /**
     * @return the SRecords initialising if needs be.
     */
    public final ArrayList<SHBE_S_Record> getSRecords() {
        return SRecords;
    }

    /**
     * @param SRecords the SRecords to set
     */
    public void setSRecords(ArrayList<SHBE_S_Record> SRecords) {
        this.SRecords = SRecords;
    }

    /**
     * @return the ClaimPostcodeF
     */
    public String getClaimPostcodeF() {
        return ClaimPostcodeF;
    }

    /**
     * @return ClaimPostcodeFValidPostcodeFormat
     */
    public boolean isClaimPostcodeFValidFormat() {
        return ClaimPostcodeFValidPostcodeFormat;
    }

    /**
     * @return ClaimPostcodeFMappable
     */
    public boolean isClaimPostcodeFMappable() {
        return ClaimPostcodeFMappable;
    }

    /**
     * @return the PostcodeID
     */
    public ONSPD_ID getPostcodeID() {
        return PostcodeID;
    }
}
