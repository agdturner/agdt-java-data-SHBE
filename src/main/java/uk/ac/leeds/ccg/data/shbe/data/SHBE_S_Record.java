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

import uk.ac.leeds.ccg.data.shbe.data.id.SHBE_PersonID;

import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_S_Record extends SHBE_DACTEGPRST_Record {

    /**
     * 11 16 Non Dependent Status
     */
    private int nds;
    /**
     * 12 17 Non Dependent Deduction Amount Applied
     */
    private int nddaa;

    /**
     * 205 214 nonDependantGrossWeeklyIncomeFromRemunerativeWork
     */
    private int nonDependantGrossWeeklyIncomeFromRemunerativeWork;

    /**
     * 284 308 subRecordType
     */
    private int subRecordType;

    /**
     * 285 309 subRecordChildReferenceNumberOrNINO
     */
    private String subRecordChildReferenceNumberOrNINO;

    /**
     * 286 310 subRecordStartDate
     */
    private String subRecordStartDate;

    /**
     * 287 311 subRecordEndDate
     */
    private String subRecordEndDate;

    // SubRecordTitle
    // SubRecordSurname
    // SubRecordForename
    /**
     * In type1 but not type0 288 315 subRecordDateOfBirth
     */
    private String subRecordDateOfBirth;

    private SHBE_PersonID personID;

    public SHBE_S_Record(SHBE_Environment env) {
        super(env);
    }

    /**
     * @param env SHBE_Environment
     * @param recordID RecordID
     * @param type {@code The type is worked out by reading the first line of
     * the data. type1 has: LandlordPostcode 307 and subRecordDateOfBirth 315.
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
     * @throws java.lang.Exception If encountered.
     */
    public SHBE_S_Record(SHBE_Environment env, long recordID, int type,
            String line) throws Exception {
        super(env);
        this.recordID = recordID;
        String[] fields = line.split(",");
        int n = 1;
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
                nddaa = 0;
            } else {
                nddaa = Integer.valueOf(fields[n]);
            }
        } else {
            return;
        }
        n = 205;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                nonDependantGrossWeeklyIncomeFromRemunerativeWork = 0;
            } else {
                nonDependantGrossWeeklyIncomeFromRemunerativeWork = Integer.valueOf(fields[n]);
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
            subRecordChildReferenceNumberOrNINO = fields[n];
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            subRecordStartDate = fields[n];
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            subRecordEndDate = fields[n];
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            subRecordDateOfBirth = fields[n];
        }
    }

    @Override
    public String toString() {
        return super.toString()
                + ",NonDependentStatus " + nds
                + ",NonDependentDeductionAmountApplied " + nddaa
                + ",NonDependantGrossWeeklyIncomeFromRemunerativeWork " + nonDependantGrossWeeklyIncomeFromRemunerativeWork
                + ",SubRecordType " + subRecordType;
    }

    /**
     * @return {@link #nds}
     */
    public int getNds() {
        return nds;
    }

    /**
     * @param nds What {@link #nds} is set to.
     */
    protected void setNds(int nds) {
        this.nds = nds;
    }

    private void setNonDependentStatus(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            nds = 0;
        } else {
            nds = Integer.valueOf(fields[n]);
        }
        if (nds > 8 || nds < 0) {
            System.err.println("recordID=" + recordID);
            System.err.println("nonDependentStatus=" + nds);
            System.err.println("nonDependentStatus > 8 || nonDependentStatus < 0");
            throw new Exception("nonDependentStatus > 8 || nonDependentStatus < 0");
        }
    }

    /**
     * @return {@link #nddaa}
     */
    public int getNddaa() {
        return nddaa;
    }

    /**
     * @param nddaa What {@link #nddaa} is set to.
     */
    protected void setNddaa(int nddaa) {
        this.nddaa = nddaa;
    }

    /**
     * @return {@link #nonDependantGrossWeeklyIncomeFromRemunerativeWork}
     */
    public int getNonDependantGrossWeeklyIncomeFromRemunerativeWork() {
        return nonDependantGrossWeeklyIncomeFromRemunerativeWork;
    }

    /**
     * @param i What {@link #nonDependantGrossWeeklyIncomeFromRemunerativeWork} is set to.
     */
    protected void setNonDependantGrossWeeklyIncomeFromRemunerativeWork(int i) {
        this.nonDependantGrossWeeklyIncomeFromRemunerativeWork = i;
    }

    /**
     * @return the subRecordType 1 = Dependent 2 = NonDependent
     */
    public int getSubRecordType() {
        return subRecordType;
    }

    /**
     * @param i What {@link #subRecordType} is set to.
     */
    protected void setSubRecordType(int i) {
        this.subRecordType = i;
    }

    protected final int setSubRecordType(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            subRecordType = 0;
        } else {
            try {
                subRecordType = Integer.valueOf(fields[n]);
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
//                System.err.println("subRecordType set to " + subRecordType);
//                System.err.println("recordID " + recordID);
                throw e;
            }
            if (subRecordType > 2 || subRecordType < 0) {
                System.err.println("SubRecordType " + subRecordType);
                System.err.println("SubRecordType > 2 || SubRecordType < 0");
                throw new Exception("SubRecordType > 2 || SubRecordType < 0");
            }
        }
        return n;
    }

    /**
     * @return {@link #subRecordEndDate}
     */
    public String getSubRecordEndDate() {
        return subRecordEndDate;
    }

    /**
     * @param s What {@link #subRecordEndDate} is set to.
     */
    protected void setSubRecordEndDate(String s) {
        this.subRecordEndDate = s;
    }

    /**
     * @return {@link #subRecordDateOfBirth}
     */
    public String getSubRecordDateOfBirth() {
        return subRecordDateOfBirth;
    }

    /**
     * @param s What {@link #subRecordDateOfBirth} is set to.
     */
    protected void setSubRecordDateOfBirth(String s) {
        this.subRecordDateOfBirth = s;
    }

    public String getSubRecordChildReferenceNumberOrNINO() {
        return subRecordChildReferenceNumberOrNINO;
    }

    protected void setSubRecordChildReferenceNumberOrNINO(String s) {
        this.subRecordChildReferenceNumberOrNINO = s;
    }

    public String getSubRecordStartDate() {
        return subRecordStartDate;
    }

    protected void setSubRecordStartDate(String s) {
        this.subRecordStartDate = s;
    }

}
