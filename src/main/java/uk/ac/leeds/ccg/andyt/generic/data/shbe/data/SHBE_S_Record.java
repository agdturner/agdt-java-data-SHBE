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

/**
 *
 * @author geoagdt
 */
public class SHBE_S_Record extends DW_SHBE_RecordAbstract implements Serializable {

    /**
     * 11 16 NonDependentStatus
     */
    private int NonDependentStatus;
    /**
     * 12 17 NonDependentDeductionAmountApplied
     */
    private int NonDependentDeductionAmountApplied;
    /**
     * 205 214 NonDependantGrossWeeklyIncomeFromRemunerativeWork
     */
    private int NonDependantGrossWeeklyIncomeFromRemunerativeWork;
    /**
     * 284 308 SubRecordType
     */
    private int SubRecordType;
    /**
     * 285 309 SubRecordChildReferenceNumberOrNINO
     */
    private String SubRecordChildReferenceNumberOrNINO;
    /**
     * 286 310 SubRecordStartDate
     */
    private String SubRecordStartDate;
    /**
     * 287 311 SubRecordEndDate
     */
    private String SubRecordEndDate;
    // SubRecordTitle
    // SubRecordSurname
    // SubRecordForename
    /**
     * In type1 but not type0 288 315 SubRecordDateOfBirth
     */
    private String SubRecordDateOfBirth;

    private SHBE_PersonID DW_PersonID;

    public SHBE_S_Record(SHBE_Environment env) {
        super(env);
    }

    /**
     * @param env
     * @param RecordID
     * @param type {@code The type is worked out by reading the first line of
     * the data. type1 has: LandlordPostcode 307 and SubRecordDateOfBirth 315.
     * type0---NoFields_307_315-------------------------------------------------
     * 1,2,3,4,8,9, 11,12,13,14,15,16,17,18,19, 20,21,22,23,24,25,26,27,28,29,
     * 30,31,32,33,34,35,36,37,38,39, 40,41,42,43,44,45,46,47,48,49,
     * 50,51,52,53,54,55,56,57,58,59, 60,61,62,63,64,65,66,67,68,69,
     * 70,71,72,73,74,75,76,77,78,79, 80,81,82,83,84,85,86,87,88,89,
     * 90,91,92,93,94,95,96,97,98,99, 100,101,102,103,104,105,106,107,108,109,
     * 110,111,112,113,114,115,116,117,118,119, 120,121,122,123,124,125,126,
     * 130,131,132,133,134,135,136,137,138,139,
     * 140,141,142,143,144,145,146,147,148,149,
     * 150,151,152,153,154,155,156,157,158,159,
     * 160,161,162,163,164,165,166,167,168,169,
     * 170,171,172,173,174,175,176,177,178,179,
     * 180,181,182,183,184,185,186,187,188,189,
     * 190,191,192,193,194,195,196,197,198,199,
     * 200,201,202,203,204,205,206,207,208,209,
     * 210,211,213,214,215,216,217,218,219,
     * 220,221,222,223,224,225,226,227,228,229,
     * 230,231,232,233,234,235,236,237,238,239,
     * 240,241,242,243,244,245,246,247,248,249,
     * 250,251,252,253,254,255,256,257,258,259,
     * 260,261,262,263,264,265,266,267,268,269,
     * 270,271,272,273,274,275,276,277,278, 284,285,286,287,
     * 290,291,292,293,294,295,296,297,298,299, 308,309,
     * 310,311,316,317,318,319, 320,321,322,323,324,325,326,327,328,329,
     * 330,331,332,333,334,335,336,337,338,339, 340,341
     * type1---ExtraFields_307(LandLordPostcode)_315(Sub-RecordDateOfBirth)-----
     * 1,2,3,4,8,9, 11,12,13,14,15,16,17,18,19, 20,21,22,23,24,25,26,27,28,29,
     * 30,31,32,33,34,35,36,37,38,39, 40,41,42,43,44,45,46,47,48,49,
     * 50,51,52,53,54,55,56,57,58,59, 60,61,62,63,64,65,66,67,68,69,
     * 70,71,72,73,74,75,76,77,78,79, 80,81,82,83,84,85,86,87,88,89,
     * 90,91,92,93,94,95,96,97,98,99, 100,101,102,103,104,105,106,107,108,109,
     * 110,111,112,113,114,115,116,117,118,119, 120,121,122,123,124,125,126,
     * 130,131,132,133,134,135,136,137,138,139,
     * 140,141,142,143,144,145,146,147,148,149,
     * 150,151,152,153,154,155,156,157,158,159,
     * 160,161,162,163,164,165,166,167,168,169,
     * 170,171,172,173,174,175,176,177,178,179,
     * 180,181,182,183,184,185,186,187,188,189,
     * 190,191,192,193,194,195,196,197,198,199,
     * 200,201,202,203,204,205,206,207,208,209,
     * 210,211,213,214,215,216,217,218,219,
     * 220,221,222,223,224,225,226,227,228,229,
     * 230,231,232,233,234,235,236,237,238,239,
     * 240,241,242,243,244,245,246,247,248,249,
     * 250,251,252,253,254,255,256,257,258,259,
     * 260,261,262,263,264,265,266,267,268,269,
     * 270,271,272,273,274,275,276,277,278, 284,285,286,287,
     * 290,291,292,293,294,295,296,297,298,299, 307,308,309,
     * 310,311,315,316,317,318,319, 320,321,322,323,324,325,326,327,328,329,
     * 330,331,332,333,334,335,336,337,338,339, 340,341}
     * @param line
     * @throws java.lang.Exception
     */
    public SHBE_S_Record(SHBE_Environment env, long RecordID, int type,
            String line    ) throws Exception {
        super(env);
        this.RecordID = RecordID;
        String[] fields = line.split(",");
        int n;
        n = 1;
        if (n < fields.length) {
            setHousingBenefitClaimReferenceNumber(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setCouncilTaxBenefitClaimReferenceNumber(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setClaimantsNationalInsuranceNumber(fields[n]);
        } else {
            return;
        }
        n = 11;
        if (n < fields.length) {
            setNonDependentStatus(n, fields);
        } else {
            return;
        }
        n++; //12
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                NonDependentDeductionAmountApplied = 0;
            } else {
                NonDependentDeductionAmountApplied = Integer.valueOf(fields[n]);
            }
        } else {
            return;
        }
        n = 205;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                NonDependantGrossWeeklyIncomeFromRemunerativeWork = 0;
            } else {
                NonDependantGrossWeeklyIncomeFromRemunerativeWork = Integer.valueOf(fields[n]);
            }
        } else {
            return;
        }
        if (type == 0) {
            n = 284;
        } else {
            //(type == 1)
            n = 285;
        }
        if (n < fields.length) {
            setSubRecordType(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            SubRecordChildReferenceNumberOrNINO = fields[n];
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            SubRecordStartDate = fields[n];
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            SubRecordEndDate = fields[n];
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            SubRecordDateOfBirth = fields[n];
        }
    }

    @Override
    public String toString() {
        return super.toString()
                + ",NonDependentStatus " + NonDependentStatus
                + ",NonDependentDeductionAmountApplied " + NonDependentDeductionAmountApplied
                + ",NonDependantGrossWeeklyIncomeFromRemunerativeWork " + NonDependantGrossWeeklyIncomeFromRemunerativeWork
                + ",SubRecordType " + SubRecordType;
    }

    /**
     * @return the NonDependentStatus
     */
    public int getNonDependentStatus() {
        return NonDependentStatus;
    }

    /**
     * @param NonDependentStatus the NonDependentStatus to set
     */
    protected void setNonDependentStatus(int NonDependentStatus) {
        this.NonDependentStatus = NonDependentStatus;
    }

    private void setNonDependentStatus(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            NonDependentStatus = 0;
        } else {
            NonDependentStatus = Integer.valueOf(fields[n]);
        }
        if (NonDependentStatus > 8 || NonDependentStatus < 0) {
            System.err.println("RecordID " + RecordID);
            System.err.println("NonDependentStatus " + NonDependentStatus);
            System.err.println("NonDependentStatus > 8 || NonDependentStatus < 0");
            throw new Exception("NonDependentStatus > 8 || NonDependentStatus < 0");
        }
    }

    /**
     * @return the NonDependentDeductionAmountApplied
     */
    public int getNonDependentDeductionAmountApplied() {
        return NonDependentDeductionAmountApplied;
    }

    /**
     * @param NonDependentDeductionAmountApplied the
     * NonDependentDeductionAmountApplied to set
     */
    protected void setNonDependentDeductionAmountApplied(int NonDependentDeductionAmountApplied) {
        this.NonDependentDeductionAmountApplied = NonDependentDeductionAmountApplied;
    }

    /**
     * @return the NonDependantGrossWeeklyIncomeFromRemunerativeWork
     */
    public int getNonDependantGrossWeeklyIncomeFromRemunerativeWork() {
        return NonDependantGrossWeeklyIncomeFromRemunerativeWork;
    }

    /**
     * @param NonDependantGrossWeeklyIncomeFromRemunerativeWork the
     * NonDependantGrossWeeklyIncomeFromRemunerativeWork to set
     */
    protected void setNonDependantGrossWeeklyIncomeFromRemunerativeWork(int NonDependantGrossWeeklyIncomeFromRemunerativeWork) {
        this.NonDependantGrossWeeklyIncomeFromRemunerativeWork = NonDependantGrossWeeklyIncomeFromRemunerativeWork;
    }

    /**
     * @return the SubRecordType 1 = Dependent 2 = NonDependent
     */
    public int getSubRecordType() {
        return SubRecordType;
    }

    /**
     * @param SubRecordType the SubRecordType to set
     */
    protected void setSubRecordType(int SubRecordType) {
        this.SubRecordType = SubRecordType;
    }

    protected final int setSubRecordType(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            SubRecordType = 0;
        } else {
            try {
                SubRecordType = Integer.valueOf(fields[n]);
            } catch (NumberFormatException e) {
//                System.err.println("Assuming LandlordPostcode was set to County Name");
//                System.err.println("LandlordPostcode " + LandlordPostcode);
//                if (LandlordPostcode.trim().equalsIgnoreCase("LEEDS")) {
//                    int debug = 1;
//                    n++;
//                }
//                LandlordPostcode = fields[n];
//                System.err.println("LandlordPostcode set to " + fields[n]);
//                n++;
//                setSubRecordType(n, fields);
//                System.err.println("SubRecordType set to " + SubRecordType);
//                System.err.println("RecordID " + RecordID);
                throw e;
            }
            if (SubRecordType > 2 || SubRecordType < 0) {
                System.err.println("SubRecordType " + SubRecordType);
                System.err.println("SubRecordType > 2 || SubRecordType < 0");
                throw new Exception("SubRecordType > 2 || SubRecordType < 0");
            }
        }
        return n;
    }

    /**
     * @return the SubRecordEndDate
     */
    public String getSubRecordEndDate() {
        return SubRecordEndDate;
    }

    /**
     * @param SubRecordEndDate the SubRecordEndDate to set
     */
    protected void setSubRecordEndDate(String SubRecordEndDate) {
        this.SubRecordEndDate = SubRecordEndDate;
    }

    /**
     * @return the SubRecordDateOfBirth
     */
    public String getSubRecordDateOfBirth() {
        return SubRecordDateOfBirth;
    }

    /**
     * @param SubRecordDateOfBirth the SubRecordDateOfBirth to set
     */
    protected void setSubRecordDateOfBirth(String SubRecordDateOfBirth) {
        this.SubRecordDateOfBirth = SubRecordDateOfBirth;
    }

    public String getSubRecordChildReferenceNumberOrNINO() {
        return SubRecordChildReferenceNumberOrNINO;
    }

    protected void setSubRecordChildReferenceNumberOrNINO(String SubRecordChildReferenceNumberOrNINO) {
        this.SubRecordChildReferenceNumberOrNINO = SubRecordChildReferenceNumberOrNINO;
    }

    public String getSubRecordStartDate() {
        return SubRecordStartDate;
    }

    protected void setSubRecordStartDate(String SubRecordStartDate) {
        this.SubRecordStartDate = SubRecordStartDate;
    }

}
