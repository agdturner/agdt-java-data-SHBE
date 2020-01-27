/*
 * Copyright 2015 Andy Turner, University of Leeds.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Object;
import uk.ac.leeds.ccg.data.shbe.core.SHBE_Strings;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_TenancyType_Handler extends SHBE_Object {

    public final int iMinus999 = -999;
    public final int zero = 0;
    public final int i1 = 1;
    public final int i2 = 2;
    public final int i3 = 3;
    public final int i4 = 4;
    public final int i5 = 5;
    public final int i6 = 6;
    public final int i7 = 7;
    public final int i8 = 8;
    public final int i9 = 9;
    public final String sMinus999 = "-999";
    public final String s0 = "0";
    public final String s1 = "1";
    public final String s2 = "2";
    public final String s3 = "3";
    public final String s4 = "4";
    public final String s5 = "5";
    public final String s6 = "6";
    public final String s7 = "7";
    public final String s8 = "8";
    public final String s9 = "9";
    public final String sU = "U";
    public final String sUnderOccupied = "Under Occupied";
    public final String sall = "all";
    public final String space = " ";
    public final String sEmpty = "";
    public final String sCouncil = "Council";
    public final String sPrivateRegulated = "Private Regulated";
    public final String sPrivateDeregulated = "Private Deregulated";
    public final String sHousingAssociation = "Housing Association";
    public final String sCTBOnlyCasesWhereClaimantSetAsOwnerWithinCouncilTax = "CTB only cases, where Claimant or Liable Person is set as the owner within Council Tax";
    public final String sCTBOnlyCasesWhereClaimantNotSetAsOwnerWithinCouncilTax = "CTB only cases, where Claimant or Liable Person is not set as the owner within Council Tax";
    public final String sPrivateOther = "Private Other";
    public final String sCouncilTenantCasesHRAFlagNo = "Council Tenant cases where the HRA flag is set to 'No'";
    public final String sPrivateTenantHonHANonHomelessWithMealsDeduction = "Private Tenant, non-HA, non-Homeless cases where there is a Meals deduction";
    public final String sNoTenancy = "No Tenancy";
    public final String sUnknown = "Unknown";

    public SHBE_TenancyType_Handler(SHBE_Environment env) {
        super(env);
    }

    public String getTenancyTypeName(String tenancyType) {
        String result;
        if (tenancyType.startsWith(s1)) {
            result = sCouncil;
        } else if (tenancyType.startsWith(s2)) {
            result = sPrivateRegulated;
        } else if (tenancyType.startsWith(s3)) {
            result = sPrivateDeregulated;
        } else if (tenancyType.startsWith(s4)) {
            result = sHousingAssociation;
        } else if (tenancyType.startsWith(s5)) {
            result = sCTBOnlyCasesWhereClaimantSetAsOwnerWithinCouncilTax;
        } else if (tenancyType.startsWith(s6)) {
            result = sPrivateOther;
        } else if (tenancyType.startsWith(s7)) {
            result = sCTBOnlyCasesWhereClaimantNotSetAsOwnerWithinCouncilTax;
        } else if (tenancyType.startsWith(s8)) {
            result = sCouncilTenantCasesHRAFlagNo;
        } else if (tenancyType.startsWith(s9)) {
            result = sPrivateTenantHonHANonHomelessWithMealsDeduction;
        } else if (tenancyType.startsWith(sMinus999)) {
            result = sNoTenancy;
        } else {
            result = tenancyType.replaceAll(sU, sEmpty);
        }
        if (tenancyType.endsWith(sU)) {
            result += space + sUnderOccupied;
        }
        return result;
    }

    public String getTenancyTypeName(int tenancyType) {
        switch (tenancyType) {
            case 1:
                //return "Council Tenant HRA cases";
                return sCouncil;
            case 2:
                //return "Private Tenant Regulated, non-Housing Association cases";
                return sPrivateRegulated;
            case 3:
                //return "Private Tenant Deregulated, non-Housing Association cases";
                return sPrivateDeregulated;
            case 4:
                //return "Private Tenant Housing Association cases";
                return sHousingAssociation;
            case 5:
                return sCTBOnlyCasesWhereClaimantSetAsOwnerWithinCouncilTax;
            case 6:
                //return "Other Private Tenant cases";
                return sPrivateOther;
            case 7:
                return sCTBOnlyCasesWhereClaimantNotSetAsOwnerWithinCouncilTax;
            case 8:
                return sCouncilTenantCasesHRAFlagNo;
            case 9:
                return sPrivateTenantHonHANonHomelessWithMealsDeduction;
            case -999:
                return sNoTenancy;
            default:
                return sUnknown;
        }
    }

    /**
     * Tenancy Type Unregulated.
     */
    protected ArrayList<String> ttu;

    /**
     * @return {@link #ttu} initialised first if it is {@code null}.
     */
    public ArrayList<String> getTtu() {
        if (ttu == null) {
            ttu = new ArrayList<>();
            ttu.add(s3);
            ttu.add(s6);
        }
        return ttu;
    }

    /**
     * Tenancy Type Regulated.
     */
    protected ArrayList<String> ttr;

    /**
     * Tenancy Type Regulated for Under Occupied.
     */
    protected ArrayList<String> ttru;

    /**
     * For returning an ArrayList of Tenancy Type Regulated.
     *
     * @param uo If {@code true} then it is the list for the 
     * Under Occupied that is returned.
     * @return List of Tenancy Type Regulated.
     */
    public ArrayList<String> getTtr(boolean uo) {
        if (uo) {
            if (ttru == null) {
                ttru = new ArrayList<>();
                ttru.add(s1);
                ttru.add(s2);
                ttru.add(s4);
                ttru.add(s1 + sU);
                //ttru.add(s2 + sU);
                ttru.add(s4 + sU);
            }
            return ttru;
        } else {
            if (ttr == null) {
                ttr = new ArrayList<>();
                ttr.add(s1);
                ttr.add(s2);
                ttr.add(s4);
            }
            return ttr;
        }
    }

    public ArrayList<String> getTenancyTypeAll() {
        ArrayList<String> r = new ArrayList<>();
        //r.add(s0);
        r.add(s1);
        r.add(s2);
        r.add(s3);
        r.add(s4);
        r.add(s5);
        r.add(s6);
        r.add(s7);
        r.add(s8);
        r.add(s9);
        return r;
    }

    public ArrayList<String> getTenancyTypeAll(boolean uo) {
        ArrayList<String> r = getTenancyTypeAll();
        if (uo) {
            //result.add(s0 + sU);
            r.add(s1 + sU);
            r.add(s2 + sU);
            r.add(s3 + sU);
            r.add(s4 + sU);
            r.add(s5 + sU);
            r.add(s6 + sU);
            r.add(s7 + sU);
            r.add(s8 + sU);
            r.add(s9 + sU);
        }
        return r;
    }

    /**
     * @return Object[4] r where:
     * <ul>
     * <li>r[0] {@code HashMap<Boolean, TreeMap<String, ArrayList<String>>>}
     * tenancyTypeGroups, the key is either UO or NotUO, the values have a key
     * which is the name of a type e.g. "regulated" and the values are the set
     * of all tenancy types in this group</li>
     * <li>r[1] {@code HashMap<Boolean, ArrayList<String>>} tenancyTypesGrouped,
     * the key is either UO or NotUO, the values are all the codes for the
     * possible respective grouped tenancy types</li>
     * <li>r[2] {@code HashMap<Boolean, ArrayList<String>>} regulatedGroups are
     * like r[2], but only contains the regulated tenancy types</li>
     * <li>r[3] {@code HashMap<Boolean, ArrayList<String>>} unregulatedGroups
     * are like r[2], but only contains the unregulated tenancy types.</li>
     * </ul>
     */
    public Object[] getTenancyTypeGroups() {
        Object[] r = new Object[4];
        // Tenancy Type Groups
        HashMap<Boolean, TreeMap<String, ArrayList<String>>> tenancyTypeGroups
                = new HashMap<>();
        HashMap<Boolean, ArrayList<String>> tenancyTypesGrouped
                = new HashMap<>();
        TreeMap<String, ArrayList<String>> ttgs = new TreeMap<>();
        ArrayList<String> ttg = new ArrayList<>();
        Boolean uo = false;
        ArrayList<String> all = getTenancyTypeAll(uo);
        ArrayList<String> regulated = getTtr(uo);
        ArrayList<String> unregulated = getTtu();
        HashMap<Boolean, ArrayList<String>> regulatedGroups = new HashMap<>();
        HashMap<Boolean, ArrayList<String>> unregulatedGroups = new HashMap<>();
        ttgs.put(sall, all);        
        ttgs.put(SHBE_Strings.s_Regulated, regulated);
        ttgs.put(SHBE_Strings.s_Unregulated, unregulated);
        tenancyTypeGroups.put(uo, ttgs);
        ttg.add(SHBE_Strings.s_Regulated);
        ttg.add(SHBE_Strings.s_Unregulated);
        ttg.add(SHBE_Strings.s_Ungrouped);
        ttg.add(sMinus999);
        tenancyTypesGrouped.put(uo, ttg);
        ArrayList<String> rg = getTtr(uo);
        ArrayList<String> ug = getTtu();       
        regulatedGroups.put(uo, rg);
        unregulatedGroups.put(uo, ug);
        uo = true;
        ttgs = new TreeMap<>();
        all = getTenancyTypeAll(uo);
        ttgs.put(sall, all);
        regulated = getTtr(uo);
        ttgs.put(SHBE_Strings.s_Regulated, regulated);
        unregulated = getTtu();
        ttgs.put(SHBE_Strings.s_Unregulated, unregulated);
        tenancyTypeGroups.put(uo, ttgs);
        ttg = new ArrayList<>();
        ttg.add(SHBE_Strings.s_Regulated);
        ttg.add(SHBE_Strings.s_Regulated + SHBE_Strings.s_U);
        ttg.add(SHBE_Strings.s_Unregulated);
        ttg.add(SHBE_Strings.s_Unregulated + SHBE_Strings.s_U);
        ttg.add(SHBE_Strings.s_Ungrouped);
        ttg.add(SHBE_Strings.s_Ungrouped + SHBE_Strings.s_U);
        ttg.add(sMinus999);
        ttg.add(sMinus999 + SHBE_Strings.s_U);
        tenancyTypesGrouped.put(uo, ttg);
        rg = getTtr(uo);
        regulatedGroups.put(uo, rg);
        ug = getTtu();
        unregulatedGroups.put(uo, ug);
        r[0] = tenancyTypeGroups;
        r[1] = tenancyTypesGrouped;
        r[2] = regulatedGroups;
        r[3] = unregulatedGroups;
        return r;
    }

    public HashMap<String, String> getTenancyTypeGroupLookup() {
        HashMap<String, String> r;
        r = new HashMap<>();
        r.put(s1, SHBE_Strings.s_Regulated);
        r.put(s2, SHBE_Strings.s_Regulated);
        r.put(s3, SHBE_Strings.s_Unregulated);
        r.put(s4, SHBE_Strings.s_Regulated);
        r.put(s5, SHBE_Strings.s_Ungrouped);
        r.put(s6, SHBE_Strings.s_Unregulated);
        r.put(s7, SHBE_Strings.s_Ungrouped);
        r.put(s8, SHBE_Strings.s_Ungrouped);
        r.put(s9, SHBE_Strings.s_Ungrouped);
        r.put(s1 + SHBE_Strings.s_U, SHBE_Strings.s_Regulated + SHBE_Strings.s_U);
        r.put(s2 + SHBE_Strings.s_U, SHBE_Strings.s_Regulated + SHBE_Strings.s_U);
        r.put(s3 + SHBE_Strings.s_U, SHBE_Strings.s_Unregulated + SHBE_Strings.s_U);
        r.put(s4 + SHBE_Strings.s_U, SHBE_Strings.s_Regulated + SHBE_Strings.s_U);
        r.put(s5 + SHBE_Strings.s_U, SHBE_Strings.s_Ungrouped + SHBE_Strings.s_U);
        r.put(s6 + SHBE_Strings.s_U, SHBE_Strings.s_Unregulated + SHBE_Strings.s_U);
        r.put(s7 + SHBE_Strings.s_U, SHBE_Strings.s_Ungrouped + SHBE_Strings.s_U);
        r.put(s8 + SHBE_Strings.s_U, SHBE_Strings.s_Ungrouped + SHBE_Strings.s_U);
        r.put(s9 + SHBE_Strings.s_U, SHBE_Strings.s_Ungrouped + SHBE_Strings.s_U);
        return r;
    }
}
