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
package uk.ac.leeds.ccg.data.shbe.data.types;

import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;

/**
 *
 * @author geoagdt
 */
public class SHBE_E_Record extends SHBE_DACTEGPRST_Record {
    
    /**
     * 293 320 UniqueTRecordIdentifier
     */
    private String UniqueTRecordIdentifier;
    /**
     * 309 336 BenefitThatThisPaymentErrorRelatesTo
     */
    private String BenefitThatThisPaymentErrorRelatesTo;
    /**
     * 310 337 TotalValueOfPaymentError
     */
    private long TotalValueOfPaymentError;
    /**
     * 311 338 WeeklyBenefitDiscrepancyAtStartOfPaymentError
     */
    private String WeeklyBenefitDiscrepancyAtStartOfPaymentError;
    /**
     * 312 339 StartDateOfPaymentErrorPeriod
     */
    private String StartDateOfPaymentErrorPeriod;
    /**
     * 313 340 EndDateOfPaymentErrorPeriod
     */
    private String EndDateOfPaymentErrorPeriod;
    /**
     * 314 341 WhatWasTheCauseOfTheOverPayment
     */
    private String WhatWasTheCauseOfTheOverPayment;

    public SHBE_E_Record(SHBE_Environment env) {
        super(env);
    }
            
    
    @Override
    public String toString() {
        return super.toString()
                + ",UniqueTRecordIdentifier " + UniqueTRecordIdentifier
                + ",BenefitThatThisPaymentErrorRelatesTo " + BenefitThatThisPaymentErrorRelatesTo
                + ",TotalValueOfPaymentError " + TotalValueOfPaymentError
                + ",WeeklyBenefitDiscrepancyAtStartOfPaymentError " + WeeklyBenefitDiscrepancyAtStartOfPaymentError
                + ",StartDateOfPaymentErrorPeriod " + StartDateOfPaymentErrorPeriod
                + ",EndDateOfPaymentErrorPeriod " + EndDateOfPaymentErrorPeriod
                + ",WhatWasTheCauseOfTheOverPayment " + WhatWasTheCauseOfTheOverPayment;
    }
    
    public String getUniqueTRecordIdentifier() {
        return UniqueTRecordIdentifier;
    }

    protected void setUniqueTRecordIdentifier(String UniqueTRecordIdentifier) {
        this.UniqueTRecordIdentifier = UniqueTRecordIdentifier;
    }

    public String getBenefitThatThisPaymentErrorRelatesTo() {
        return BenefitThatThisPaymentErrorRelatesTo;
    }
    
    protected void setBenefitThatThisPaymentErrorRelatesTo(String BenefitThatThisPaymentErrorRelatesTo) {
        this.BenefitThatThisPaymentErrorRelatesTo = BenefitThatThisPaymentErrorRelatesTo;
    }

     public String getWeeklyBenefitDiscrepancyAtStartOfPaymentError() {
        return WeeklyBenefitDiscrepancyAtStartOfPaymentError;
    }

    protected void setWeeklyBenefitDiscrepancyAtStartOfPaymentError(String WeeklyBenefitDiscrepancyAtStartOfPaymentError) {
        this.WeeklyBenefitDiscrepancyAtStartOfPaymentError = WeeklyBenefitDiscrepancyAtStartOfPaymentError;
    }

    public String getStartDateOfPaymentErrorPeriod() {
        return StartDateOfPaymentErrorPeriod;
    }

    protected void setStartDateOfPaymentErrorPeriod(String StartDateOfPaymentErrorPeriod) {
        this.StartDateOfPaymentErrorPeriod = StartDateOfPaymentErrorPeriod;
    }

    public String getEndDateOfPaymentErrorPeriod() {
        return EndDateOfPaymentErrorPeriod;
    }

    protected void setEndDateOfPaymentErrorPeriod(String EndDateOfPaymentErrorPeriod) {
        this.EndDateOfPaymentErrorPeriod = EndDateOfPaymentErrorPeriod;
    }

    public String getWhatWasTheCauseOfTheOverPayment() {
        return WhatWasTheCauseOfTheOverPayment;
    }

    protected void setWhatWasTheCauseOfTheOverPayment(String WhatWasTheCauseOfTheOverPayment) {
        this.WhatWasTheCauseOfTheOverPayment = WhatWasTheCauseOfTheOverPayment;
    }

    public long getTotalValueOfPaymentError() {
        return TotalValueOfPaymentError;
    }

    protected void setTotalValueOfPaymentError(long TotalValueOfPaymentError) {
        this.TotalValueOfPaymentError = TotalValueOfPaymentError;
    }
}
