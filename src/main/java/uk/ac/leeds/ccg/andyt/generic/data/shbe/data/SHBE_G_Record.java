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
public class SHBE_G_Record extends SHBE_DACTEGPRST_Record {
    
    public SHBE_G_Record(SHBE_Environment env) {
        super(env);
    }
    /**
     * 265 274 SoftwareProvider
     */
    private int SoftwareProvider;
    /**
     * 266 275 StaffingFTE
     */
    private String StaffingFTE;
    /**
     * 267 276 ContractingOutHandlingAndMaintenanceOfHBCTB
     */
    private int ContractingOutHandlingAndMaintenanceOfHBCTB;
    /**
     * 268 277 ContractingOutCounterFraudWorkRelatingToHBCTB
     */
    private int ContractingOutCounterFraudWorkRelatingToHBCTB;
    
    @Override
    public String toString() {
        return super.toString()
                + ",SoftwareProvider " + SoftwareProvider
                + ",StaffingFTE " + StaffingFTE
                + ",ContractingOutHandlingAndMaintenanceOfHBCTB " + ContractingOutHandlingAndMaintenanceOfHBCTB
                + ",ContractingOutCounterFraudWorkRelatingToHBCTB " + ContractingOutCounterFraudWorkRelatingToHBCTB;
    }

    /**
     * @return the SoftwareProvider
     */
    public int getSoftwareProvider() {
        return SoftwareProvider;
    }

    /**
     * @param SoftwareProvider the SoftwareProvider to set
     */
    protected void setSoftwareProvider(int SoftwareProvider) {
        this.SoftwareProvider = SoftwareProvider;
    }

    protected void setSoftwareProvider(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            SoftwareProvider = 0;
        } else {
            SoftwareProvider = Integer.valueOf(fields[n]);
            if (SoftwareProvider > 3 || SoftwareProvider < 0) {
                System.err.println("SoftwareProvider " + SoftwareProvider);
                System.err.println("SoftwareProvider > 3 || SoftwareProvider < 0");
                throw new Exception("SoftwareProvider > 3 || SoftwareProvider < 0");
            }
        }
    }

    /**
     * @return the StaffingFTE
     */
    public String getStaffingFTE() {
        return StaffingFTE;
    }

    /**
     * @param StaffingFTE the StaffingFTE to set
     */
    protected void setStaffingFTE(String StaffingFTE) {
        this.StaffingFTE = StaffingFTE;
    }

    /**
     * @return the ContractingOutHandlingAndMaintenanceOfHBCTB
     */
    public int getContractingOutHandlingAndMaintenanceOfHBCTB() {
        return ContractingOutHandlingAndMaintenanceOfHBCTB;
    }

    /**
     * @param ContractingOutHandlingAndMaintenanceOfHBCTB the
     * ContractingOutHandlingAndMaintenanceOfHBCTB to set
     */
    protected void setContractingOutHandlingAndMaintenanceOfHBCTB(int ContractingOutHandlingAndMaintenanceOfHBCTB) {
        this.ContractingOutHandlingAndMaintenanceOfHBCTB = ContractingOutHandlingAndMaintenanceOfHBCTB;
    }

    protected void setContractingOutHandlingAndMaintenanceOfHBCTB(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            ContractingOutHandlingAndMaintenanceOfHBCTB = 0;
        } else {
            ContractingOutHandlingAndMaintenanceOfHBCTB = Integer.valueOf(fields[n]);
            if (ContractingOutHandlingAndMaintenanceOfHBCTB > 9 || ContractingOutHandlingAndMaintenanceOfHBCTB < 0) {
                System.err.println("ContractingOutHandlingAndMaintenanceOfHBCTB " + ContractingOutHandlingAndMaintenanceOfHBCTB);
                System.err.println("ContractingOutHandlingAndMaintenanceOfHBCTB > 9 || ContractingOutHandlingAndMaintenanceOfHBCTB < 0");
                throw new Exception("ContractingOutHandlingAndMaintenanceOfHBCTB > 9 || ContractingOutHandlingAndMaintenanceOfHBCTB < 0");
            }
        }
    }

    /**
     * @return the ContractingOutCounterFraudWorkRelatingToHBCTB
     */
    public int getContractingOutCounterFraudWorkRelatingToHBCTB() {
        return ContractingOutCounterFraudWorkRelatingToHBCTB;
    }

    /**
     * @param ContractingOutCounterFraudWorkRelatingToHBCTB the
     * ContractingOutCounterFraudWorkRelatingToHBCTB to set
     */
    protected void setContractingOutCounterFraudWorkRelatingToHBCTB(int ContractingOutCounterFraudWorkRelatingToHBCTB) {
        this.ContractingOutCounterFraudWorkRelatingToHBCTB = ContractingOutCounterFraudWorkRelatingToHBCTB;
    }

    protected void setContractingOutCounterFraudWorkRelatingToHBCTB(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            ContractingOutCounterFraudWorkRelatingToHBCTB = 0;
        } else {
            ContractingOutCounterFraudWorkRelatingToHBCTB = Integer.valueOf(fields[n]);
            if (ContractingOutCounterFraudWorkRelatingToHBCTB > 9 || ContractingOutCounterFraudWorkRelatingToHBCTB < 0) {
                System.err.println("ContractingOutCounterFraudWorkRelatingToHBCTB " + ContractingOutCounterFraudWorkRelatingToHBCTB);
                System.err.println("ContractingOutCounterFraudWorkRelatingToHBCTB > 9 || ContractingOutCounterFraudWorkRelatingToHBCTB < 0");
                throw new Exception("ContractingOutCounterFraudWorkRelatingToHBCTB > 9 || ContractingOutCounterFraudWorkRelatingToHBCTB < 0");
            }
        }
    }

    
}
