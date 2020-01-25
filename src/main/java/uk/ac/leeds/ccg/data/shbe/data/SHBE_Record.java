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
package uk.ac.leeds.ccg.data.shbe.data;

import java.util.ArrayList;
import java.util.Iterator;
import uk.ac.leeds.ccg.data.Data_Record;
import uk.ac.leeds.ccg.data.id.Data_ID;
import uk.ac.leeds.ccg.data.ukp.data.id.UKP_RecordID;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_ClaimID;

/**
 *
 * @author geoagdt
 */
public class SHBE_Record extends Data_Record {

    public transient final SHBE_Environment se;

    /**
     * StatusOfHBClaimAtExtractDate 0 is OtherPaymentType 1 is InPayment 2 is
     * Suspended
     */
    private int StatusOfHBClaimAtExtractDate;

//    /**
//     * The ClaimRef SHBE_ClaimID.
//     */
//    protected SHBE_ClaimID ClaimID;
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
    protected UKP_RecordID PostcodeID;

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
     * @param claimID The ClaimRef SHBE_ID for this.
     */
    public SHBE_Record(SHBE_Environment e, SHBE_ClaimID claimID) {
        super(claimID);
        this.se = e;
        //this.ClaimID = claimID;
    }

    /**
     * Creates a DW_SHBE_Record.
     *
     * @param e
     * @param claimID The ClaimRef SHBE_ID for this.
     * @param DRecord
     */
    public SHBE_Record(SHBE_Environment e, SHBE_ClaimID claimID, SHBE_D_Record DRecord) {
        super(claimID);
        se = e;
        //this.ClaimID = claimID;
        this.DRecord = DRecord;
    }

    /**
     * Returns a Brief String description of this.
     *
     * @return
     */
    public String toStringBrief() {
        String r = "SHBE_Record comprising of:";
        r += SHBE_Strings.special_newLine;
        if (DRecord != null) {
            r += " DRecord:";
            r += SHBE_Strings.special_newLine;
            r += "  " + DRecord.toStringBrief();
        }
        SRecords = getSRecords();
        if (SRecords != null) {
            long n;
            n = SRecords.size();
            r += SHBE_Strings.special_newLine;
            r += " " + n + " SRecords:";
            Iterator<SHBE_S_Record> ite = SRecords.iterator();
            while (ite.hasNext()) {
                r += "  ";
                SHBE_S_Record rec = ite.next();
                r += rec.toString();
                r += SHBE_Strings.special_newLine;
            }
        } else {
            r += " 0 SRecords";
        }
        return r;
    }

    @Override
    public String toString() {
        String r = super.toString();
        r += ", StatusOfHBClaimAtExtractDate=" + StatusOfHBClaimAtExtractDate
                + ", ClaimPostcodeFValidPostcodeFormat=" + ClaimPostcodeFValidPostcodeFormat
                + ", ClaimPostcodeFMappable=" + ClaimPostcodeFMappable
                + ", ClaimPostcodeF=" + ClaimPostcodeF
                + ", ClaimPostcodeFAutoModified=" + ClaimPostcodeFAutoModified
                + ", ClaimPostcodeFManModified=" + ClaimPostcodeFManModified
                + ", ClaimPostcodeFUpdatedFromTheFuture=" + ClaimPostcodeFUpdatedFromTheFuture
                + ", PostcodeID=" + PostcodeID;

        if (DRecord != null) {
            r += "DRecord: " + DRecord.toString()
                    + SHBE_Strings.special_newLine;
        }
        SRecords = getSRecords();
        if (SRecords != null) {
            long n;
            n = SRecords.size();
            r += " Number of SRecords = " + n
                    + SHBE_Strings.special_newLine;
            if (n > 0) {
                r += ": ";
            }
            Iterator<SHBE_S_Record> ite;
            ite = SRecords.iterator();
            while (ite.hasNext()) {
                SHBE_S_Record rec;
                rec = ite.next();
                r += " SRecord: " + rec.toString()
                        + SHBE_Strings.special_newLine;
            }
        }
        return r;
    }

    /**
     * @return (SHBE_ClaimID) ID
     */
    public SHBE_ClaimID getClaimID() {
        return (SHBE_ClaimID) ID;
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
        return SHBE_Strings.getPaymentTypes().get(StatusOfHBClaimAtExtractDate + 1);
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
    public UKP_RecordID getPostcodeID() {
        return PostcodeID;
    }

    @Override
    public Data_ID getID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
