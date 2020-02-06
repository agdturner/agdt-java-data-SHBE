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

import uk.ac.leeds.ccg.data.shbe.data.types.SHBE_S_Record;
import uk.ac.leeds.ccg.data.shbe.data.types.SHBE_D_Record;
import java.util.ArrayList;
import java.util.Iterator;
import uk.ac.leeds.ccg.data.Data_Record;
import uk.ac.leeds.ccg.data.id.Data_ID;
import uk.ac.leeds.ccg.data.ukp.data.id.UKP_RecordID;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Strings;
import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_ClaimID;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_Record extends Data_Record {

    public transient final SHBE_Environment se;

    /**
     * statusOfHBClaimAtExtractDate 0 is OtherPaymentType 1 is InPayment 2 is
     * Suspended
     */
    private int statusOfHBClaimAtExtractDate;

    /**
     * A convenient lookup for knowing if claimPostcodeF is a valid format for a
     * UK postcode.
     */
    protected boolean claimPostcodeFValidPostcodeFormat;

    /**
     * A convenient lookup for knowing if claimPostcodeF is mappable.
     */
    protected boolean claimPostcodeFMappable;

    /**
     * For storing a ONSPD format version of ClaimPostcodeF1.
     */
    protected String claimPostcodeF;

    /**
     * For storing if the Claimant Postcode has been modified by removing non
     * A-Z, a-z, 0-9 characters and replacing "O" with "0" or removing "0"
     * altogether.
     */
    protected boolean claimPostcodeFAutoModified;

    /**
     * For storing if the claimPostcodeF was subsequently (since the extract)
     * checked and modified.
     */
    protected boolean claimPostcodeFManModified;

    /**
     * For storing if the ClaimPostcodeF has been updated from the future. For
     * the time being, this is only allowed for Claimant Postcodes that were
     * originally blank or that had invalid formats.
     */
    protected boolean claimPostcodeFUpdatedFromTheFuture;

    /**
     * The Postcode ID.
     */
    protected UKP_RecordID postcodeID;

    /**
     * The dRecord
     */
    protected SHBE_D_Record dRecord;

    /**
     * The sRecords associated with the dRecord
     */
    protected ArrayList<SHBE_S_Record> sRecords;

    /**
     * @param e SHBE_Environment
     * @param claimID The Claim ID.
     */
    public SHBE_Record(SHBE_Environment e, SHBE_ClaimID claimID) {
        super(claimID);
        this.se = e;
    }

    /**
     * Creates a SHBE_Record.
     *
     * @param e SHBE_Environment
     * @param claimID The Claim ID.
     * @param dRecord DRecord
     */
    public SHBE_Record(SHBE_Environment e, SHBE_ClaimID claimID, 
            SHBE_D_Record dRecord) {
        super(claimID);
        se = e;
        this.dRecord = dRecord;
    }

    /**
     * @return a Brief String description of this.
     */
    public String toStringBrief() {
        String r = "SHBE_Record comprising of:";
        r += SHBE_Strings.special_newLine;
        if (dRecord != null) {
            r += " DRecord:";
            r += SHBE_Strings.special_newLine;
            r += "  " + dRecord.toStringBrief();
        }
        sRecords = getSRecords();
        if (sRecords != null) {
            long n;
            n = sRecords.size();
            r += SHBE_Strings.special_newLine;
            r += " " + n + " SRecords:";
            Iterator<SHBE_S_Record> ite = sRecords.iterator();
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
        r += ", statusOfHBClaimAtExtractDate=" + statusOfHBClaimAtExtractDate
                + ", claimPostcodeFValidPostcodeFormat=" + claimPostcodeFValidPostcodeFormat
                + ", claimPostcodeFMappable=" + claimPostcodeFMappable
                + ", claimPostcodeF=" + claimPostcodeF
                + ", claimPostcodeFAutoModified=" + claimPostcodeFAutoModified
                + ", claimPostcodeFManModified=" + claimPostcodeFManModified
                + ", claimPostcodeFUpdatedFromTheFuture=" + claimPostcodeFUpdatedFromTheFuture
                + ", postcodeID=" + postcodeID;

        if (dRecord != null) {
            r += "dRecord: " + dRecord.toString()
                    + SHBE_Strings.special_newLine;
        }
        sRecords = getSRecords();
        if (sRecords != null) {
            long n;
            n = sRecords.size();
            r += " Number of SRecords = " + n
                    + SHBE_Strings.special_newLine;
            if (n > 0) {
                r += ": ";
            }
            Iterator<SHBE_S_Record> ite;
            ite = sRecords.iterator();
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
        return (SHBE_ClaimID) id;
    }

    /**
     * @return {@link #statusOfHBClaimAtExtractDate}
     */
    public int getStatusOfHBClaimAtExtractDate() {
        return statusOfHBClaimAtExtractDate;
    }

    /**
     * @param s {@link #statusOfHBClaimAtExtractDate}
     * set
     */
    protected final void getStatusOfHBClaimAtExtractDate(int s) {
        this.statusOfHBClaimAtExtractDate = s;
    }

    /**
     * @return PaymentType
     */
    public String getPaymentType() {
        return SHBE_Strings.getPaymentTypes().get(statusOfHBClaimAtExtractDate + 1);
    }

    /**
     * @return {@link #dRecord}
     */
    public SHBE_D_Record getDRecord() {
        return dRecord;
    }

    /**
     * @return the sRecords initialising if needs be.
     */
    public final ArrayList<SHBE_S_Record> getSRecords() {
        return sRecords;
    }

    /**
     * @param r What {@link #sRecords} is set to.
     */
    public void setSRecords(ArrayList<SHBE_S_Record> r) {
        this.sRecords = r;
    }

    /**
     * @return {@link #claimPostcodeF}
     */
    public String getClaimPostcodeF() {
        return claimPostcodeF;
    }

    /**
     * @return {@link #claimPostcodeFValidPostcodeFormat}
     */
    public boolean isClaimPostcodeFValidFormat() {
        return claimPostcodeFValidPostcodeFormat;
    }

    /**
     * @return {@link #claimPostcodeFMappable}
     */
    public boolean isClaimPostcodeFMappable() {
        return claimPostcodeFMappable;
    }

    /**
     * @return {@link #postcodeID}
     */
    public UKP_RecordID getPostcodeID() {
        return postcodeID;
    }

    @Override
    public Data_ID getID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
