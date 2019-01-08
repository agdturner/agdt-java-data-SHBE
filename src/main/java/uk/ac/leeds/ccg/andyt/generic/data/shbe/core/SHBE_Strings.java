/*
 * Copyright 2018 Andy Turner, CCG, University of Leeds.
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
package uk.ac.leeds.ccg.andyt.generic.data.shbe.core;

import java.io.Serializable;
import java.util.ArrayList;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Strings;

/**
 *
 * @author geoagdt
 */
public class SHBE_Strings extends Generic_Strings implements Serializable {

    public final String CottingleySpringsCaravanParkPostcode = "LS27 7NS";
    public final String sCottingleySpringsCaravanPark = "CottingleySpringsCaravanPark";
    /**
     * Code for a Default National Insurance Number. There are other defaults
     * that appear to be in the source SHBE data that are of a similar form, but
     * this is the default one used by this program.
     */
    public final String sDefaultNINO = "ZX999999XZ";
    
    public final String sPaymentTypeOther = "PTO";
    public final String sPaymentTypeAll = "PTA";
    public final String sUnregulated = "Unregulated";
    public final String sRegulated = "Regulated";
    public final String sPaymentTypeIn = "PTI";
    public final String sPaymentTypeSuspended = "PTS";

    public final String sBinaryFileExtension = ".dat";

    
    public final String sHB = "HB";
    public final String sCTB = "CTB";

    public final String sLCC = "LCC";
    public final String sSHBE = "SHBE";
    public final String sUnit = "Unit";
    
    public final String sU = "U";

    public final String sUngrouped = "Ungrouped";

    /**
     * "CountOfNewSHBEClaims".
     */
    public final String sCountOfNewSHBEClaims = "CountOfNewSHBEClaims";

    /**
     * "CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore".
     */
    public final String sCountOfNewSHBEClaimsWhereClaimantWasClaimantBefore = "CountOfNewSHBEClaimsWhereClaimantWasClaimantBefore";

    /**
     * "CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore".
     */
    public final String sCountOfNewSHBEClaimsWhereClaimantWasPartnerBefore = "CountOfNewSHBEClaimsWhereClaimantWasPartnerBefore";

    /**
     * "CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore".
     */
    public final String sCountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore = "CountOfNewSHBEClaimsWhereClaimantWasNonDependentBefore";

    /**
     * "CountOfNewSHBEClaimsWhereClaimantIsNew".
     */
    public final String sCountOfNewSHBEClaimsWhereClaimantIsNew = "CountOfNewSHBEClaimsWhereClaimantIsNew";
    /*
     * General names.
     */
//    public final String S_dat = "dat";
//    public final String S_ONSPD = "ONSPD";
//    public final String S_PostcodeUnit = "PostcodeUnit";
//    public final String S_PostcodeSector = "PostcodeSector";
//    public final String S_PostcodeDistrict = "PostcodeDistrict";
    public SHBE_Strings() {
        super();
    }

    /**
     * For getting an {@code ArrayList<String>} of PaymentTypes.
     *
     * @return
     */
    public ArrayList<String> getPaymentTypes() {
        ArrayList<String> r;
        r = new ArrayList<>();
        r.add(sPaymentTypeAll);
        r.add(sPaymentTypeIn);
        r.add(sPaymentTypeSuspended);
        r.add(sPaymentTypeOther);
        return r;
    }
}
