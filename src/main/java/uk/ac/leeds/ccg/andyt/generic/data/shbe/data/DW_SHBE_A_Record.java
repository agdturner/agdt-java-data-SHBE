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
public class DW_SHBE_A_Record extends DW_SHBE_DAC_RecordAbstract {

    public DW_SHBE_A_Record(SHBE_Environment env) {
        super(env);
    }

    /**
     * 49 54 TimingOfPaymentOfRentAllowance
     */
    private int TimingOfPaymentOfRentAllowance;

    @Override
    public String toString() {
        return super.toString()
                + "TimingOfPaymentOfRentAllowance, " + TimingOfPaymentOfRentAllowance;
    }

    /**
     * @return the TimingOfPaymentOfRentAllowance
     */
    public int getTimingOfPaymentOfRentAllowance() {
        return TimingOfPaymentOfRentAllowance;
    }

    /**
     * @param TimingOfPaymentOfRentAllowance the TimingOfPaymentOfRentAllowance
     * to set
     */
    public void setTimingOfPaymentOfRentAllowance(int TimingOfPaymentOfRentAllowance) {
        this.TimingOfPaymentOfRentAllowance = TimingOfPaymentOfRentAllowance;
    }

    public void setTimingOfPaymentOfRentAllowance(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            TimingOfPaymentOfRentAllowance = 0;
        } else {
            TimingOfPaymentOfRentAllowance = Integer.valueOf(fields[n]);
//            if (TimingOfPaymentOfRentAllowance > 4 || TimingOfPaymentOfRentAllowance < 0) {
//                System.err.println("TimingOfPaymentOfRentAllowance " + TimingOfPaymentOfRentAllowance);
//                System.err.println("TimingOfPaymentOfRentAllowance > 4 || TimingOfPaymentOfRentAllowance < 0");
//                throw new Exception("TimingOfPaymentOfRentAllowance > 4 || TimingOfPaymentOfRentAllowance < 0");
//            }
        }
    }
}
