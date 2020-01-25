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
package uk.ac.leeds.ccg.data.shbe.data;

import uk.ac.leeds.ccg.data.shbe.core.SHBE_Environment;

/**
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class SHBE_D_Record extends SHBE_DC_Record {

    /**
     * Creates a null record in case this is needed
     *
     * @param env
     * @param RecordID
     */
    public SHBE_D_Record(SHBE_Environment env, long RecordID) {
        super(env);
        this.RecordID = RecordID;
    }

    /**
     *
     * @param env
     * @param RecordID
     * @param type It is not necessary to pass this in, as we only add Landlords
     * postcode if the record is long enough. But it might be useful to have
     * other types in future if the number of fields or the field usage changes.
     * @param line
     * @throws Exception
     */
    public SHBE_D_Record(SHBE_Environment env, long RecordID, int type, 
            String line) throws Exception {
        this(env, RecordID);
        String[] fields = line.split(",");
        //int exceptionalType = 0;
        int n = 0;
        if (n < fields.length) {
            if (!fields[n].equalsIgnoreCase("D")) {
                System.err.println("RecordType=" + fields[n] + " is not D");
                throw new Exception("RecordType=" + fields[n] + " is not D");
            }
            setRecordType(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setHousingBenefitClaimReferenceNumber(fields[n]);
            //setHousingBenefitClaimReferenceNumber = Long.valueOf(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setCouncilTaxBenefitClaimReferenceNumber(fields[n]);
            //setCouncilTaxBenefitClaimReferenceNumber = Long.valueOf(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setClaimantsNationalInsuranceNumber(fields[n]);
        } else {
            return;
        }
        //ClaimantsTitle(fields[n]);
        //ClaimantsSurname(fields[n]);
        //ClaimantsFirstForename = fields[n;
        n++;
//        if (n < fields.length) {
//            if (fields[n].isEmpty()) {
//                n++;
//                exceptionalType = 1;
//            }
//        } else {
//            return;
//        }
        if (n < fields.length) {
            setClaimantsDateOfBirth(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setTenancyType(n, fields);
//            if (exceptionalType == 0) {
//                setTenancyType(n, fields);
//            } else {
//                if (exceptionalType == 1) {
//                    System.out.println("Ignoring fields=" + n + ": " 
//                            + fields[n] + " instead of a numerical Tenancy "
//                            + "Type getting something else RecordID=" + RecordID);
//                }
//            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setClaimantsPostcode(fields[n]);
        } else {
            return;
        }
        n++; //7
        if (n < fields.length) {
            setPassportedStandardIndicator(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setNumberOfChildDependents(0);
            } else {
                try {
                    setNumberOfChildDependents(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setNumberOfChildDependents(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setNumberOfNonDependents(0);
            } else {
                try {
                    setNumberOfNonDependents(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setNumberOfNonDependents(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n = 28;
        if (n < fields.length) {
            setStatusOfHBClaimAtExtractDate(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setStatusOfCTBClaimAtExtractDate(n, fields);
        } else {
            return;
        }
        n++; //30
        if (n < fields.length) {
            setDateMostRecentHBClaimWasReceived(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateMostRecentCTBClaimWasReceived(fields[n]);
        } else {
            return;
        }
        n = 38;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyHousingBenefitEntitlement(0);
            } else {
                try {
                    setWeeklyHousingBenefitEntitlement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyHousingBenefitEntitlement(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyCouncilTaxBenefitEntitlement(0);
            } else {
                try {
                    setWeeklyCouncilTaxBenefitEntitlement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyCouncilTaxBenefitEntitlement(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //40
        if (n < fields.length) {
            setFrequencyOfPaymentOfHB(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setFrequencyOfPaymentOfCTB(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPreDeterminationAmountOfHB(0);
            } else {
                try {
                    setPreDeterminationAmountOfHB(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPreDeterminationAmountOfHB(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //43
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPreDeterminationAmountOfCTB(0);
            } else {
                try {
                    setPreDeterminationAmountOfCTB(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPreDeterminationAmountOfCTB(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n = 48;
        if (n < fields.length) {
            setReasonForDirectPayment(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setTimingOfPaymentOfRentAllowance(n, fields);
        } else {
            return;
        }
        n++; //50
        if (n < fields.length) {
            setExtendedPaymentCase(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setCouncilTaxBand(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyEligibleRentAmount(0);
            } else {
                n = setWeeklyEligibleRentAmount(n, fields);
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyEligibleCouncilTaxAmount(0);
            } else {
                try {
                    setWeeklyEligibleCouncilTaxAmount(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyEligibleCouncilTaxAmount(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].equalsIgnoreCase("Y")) {
                setClaimantsStudentIndicator("Y");
            } else {
                setClaimantsStudentIndicator("N");
            }
        } else {
            return;
        }
        n++; //55
        if (n < fields.length) {
            setSecondAdultRebate(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setRebatePercentageWhereASecondAdultRebateHasBeenAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setLHARegulationsApplied("No");
            } else {
                setLHARegulationsApplied("Yes");
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setIsThisCaseSubjectToLRROrSRRSchemes(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyLocalReferenceRent(0);
            } else {
                try {
                    setWeeklyLocalReferenceRent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyLocalReferenceRent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //60
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklySingleRoomRent(0);
            } else {
                try {
                    setWeeklySingleRoomRent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklySingleRoomRent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyClaimRelatedRent(0);
            } else {
                try {
                    setWeeklyClaimRelatedRent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyClaimRelatedRent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setRentOfficerDeterminationOfIneligibleCharges(0);
            } else {
                try {
                    setRentOfficerDeterminationOfIneligibleCharges(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setRentOfficerDeterminationOfIneligibleCharges(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyMaximumRent(0);
            } else {
                try {
                    setWeeklyMaximumRent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyMaximumRent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setTotalDeductionForMeals(0);
            } else {
                try {
                    setTotalDeductionForMeals(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setTotalDeductionForMeals(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //65
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyAdditionalDiscretionaryPayment(0);
            } else {
                try {
                    setWeeklyAdditionalDiscretionaryPayment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyAdditionalDiscretionaryPayment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setThirteenOrFiftyTwoWeekProtectionApplies(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsAssessedIncomeFigure(0);
            } else {
                try {
                    setClaimantsAssessedIncomeFigure(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsAssessedIncomeFigure(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsAdjustedAssessedIncomeFigure(0);
            } else {
                try {
                    setClaimantsAdjustedAssessedIncomeFigure(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsAdjustedAssessedIncomeFigure(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //70
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsTotalCapital(0);
            } else {
                try {
                    setClaimantsTotalCapital(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsTotalCapital(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsGrossWeeklyIncomeFromEmployment(0);
            } else {
                try {
                    setClaimantsGrossWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsGrossWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsNetWeeklyIncomeFromEmployment(0);
            } else {
                try {
                    setClaimantsNetWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsNetWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsGrossWeeklyIncomeFromSelfEmployment(0);
            } else {
                try {
                    setClaimantsGrossWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsGrossWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsNetWeeklyIncomeFromSelfEmployment(0);
            } else {
                try {
                    setClaimantsNetWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsNetWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //75
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsTotalAmountOfEarningsDisregarded(0);
            } else {
                try {
                    setClaimantsTotalAmountOfEarningsDisregarded(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsTotalAmountOfEarningsDisregarded(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(0);
            } else {
                try {
                    setClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromAttendanceAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromAttendanceAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromAttendanceAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromBusinessStartUpAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromBusinessStartUpAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromBusinessStartUpAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromChildBenefit(0);
            } else {
                try {
                    setClaimantsIncomeFromChildBenefit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromChildBenefit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //80
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent(0);
            } else {
                try {
                    setClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromPersonalPension(0);
            } else {
                try {
                    setClaimantsIncomeFromPersonalPension(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromPersonalPension(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromSevereDisabilityAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromSevereDisabilityAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromSevereDisabilityAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromMaternityAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromMaternityAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromMaternityAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromContributionBasedJobSeekersAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromContributionBasedJobSeekersAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromContributionBasedJobSeekersAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //85
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromStudentGrantLoan(0);
            } else {
                try {
                    setClaimantsIncomeFromStudentGrantLoan(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromStudentGrantLoan(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromSubTenants(0);
            } else {
                try {
                    setClaimantsIncomeFromSubTenants(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromSubTenants(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromBoarders(0);
            } else {
                try {
                    setClaimantsIncomeFromBoarders(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromBoarders(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromTrainingForWorkCommunityAction(0);
            } else {
                try {
                    setClaimantsIncomeFromTrainingForWorkCommunityAction(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromTrainingForWorkCommunityAction(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromIncapacityBenefitShortTermLower(0);
            } else {
                try {
                    setClaimantsIncomeFromIncapacityBenefitShortTermLower(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromIncapacityBenefitShortTermLower(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //90
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromIncapacityBenefitShortTermHigher(0);
            } else {
                try {
                    setClaimantsIncomeFromIncapacityBenefitShortTermHigher(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromIncapacityBenefitShortTermHigher(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromIncapacityBenefitLongTerm(0);
            } else {
                try {
                    setClaimantsIncomeFromIncapacityBenefitLongTerm(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromIncapacityBenefitLongTerm(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromNewDeal50PlusEmploymentCredit(0);
            } else {
                try {
                    setClaimantsIncomeFromNewDeal50PlusEmploymentCredit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromNewDeal50PlusEmploymentCredit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromNewTaxCredits(0);
            } else {
                try {
                    setClaimantsIncomeFromNewTaxCredits(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromNewTaxCredits(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromDisabilityLivingAllowanceCareComponent(0);
            } else {
                try {
                    setClaimantsIncomeFromDisabilityLivingAllowanceCareComponent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromDisabilityLivingAllowanceCareComponent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //95
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent(0);
            } else {
                try {
                    setClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromGovernemntTraining(0);
            } else {
                try {
                    setClaimantsIncomeFromGovernemntTraining(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromGovernemntTraining(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromIndustrialInjuriesDisablementBenefit(0);
            } else {
                try {
                    setClaimantsIncomeFromIndustrialInjuriesDisablementBenefit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromIndustrialInjuriesDisablementBenefit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromCarersAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromCarersAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromCarersAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromStatutoryMaternityPaternityPay(0);
            } else {
                try {
                    setClaimantsIncomeFromStatutoryMaternityPaternityPay(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromStatutoryMaternityPaternityPay(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //100
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(0);
            } else {
                try {
                    setClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromWarDisablementPensionArmedForcesGIP(0);
            } else {
                try {
                    setClaimantsIncomeFromWarDisablementPensionArmedForcesGIP(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromWarDisablementPensionArmedForcesGIP(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromWarMobilitySupplement(0);
            } else {
                try {
                    setClaimantsIncomeFromWarMobilitySupplement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromWarMobilitySupplement(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromWidowsWidowersPension(0);
            } else {
                try {
                    setClaimantsIncomeFromWidowsWidowersPension(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromWidowsWidowersPension(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromBereavementAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromBereavementAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromBereavementAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //105
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromWidowedParentsAllowance(0);
            } else {
                try {
                    setClaimantsIncomeFromWidowedParentsAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromWidowedParentsAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromYouthTrainingScheme(0);
            } else {
                try {
                    setClaimantsIncomeFromYouthTrainingScheme(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromYouthTrainingScheme(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromStatuatorySickPay(0);
            } else {
                try {
                    setClaimantsIncomeFromStatuatorySickPay(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromStatuatorySickPay(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsOtherIncome(0);
            } else {
                try {
                    setClaimantsOtherIncome(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsOtherIncome(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsTotalAmountOfIncomeDisregarded(0);
            } else {
                try {
                    setClaimantsTotalAmountOfIncomeDisregarded(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsTotalAmountOfIncomeDisregarded(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //110
        if (n < fields.length) {
            setFamilyPremiumAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setFamilyLoneParentPremiumAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDisabilityPremiumAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setSevereDisabilityPremiumAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDisabledChildPremiumAwarded(n, fields);
        } else {
            return;
        }
        n++; //115
        if (n < fields.length) {
            setCarePremiumAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setEnhancedDisabilityPremiumAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setBereavementPremiumAwarded(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setPartnerFlag(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setPartnersStartDate(fields[n]);
        } else {
            return;
        }
        n++; //120
        if (n < fields.length) {
            setPartnersEndDate(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setPartnersNationalInsuranceNumber(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersStudentIndicator("N");
            } else {
                setPartnersStudentIndicator("Y");
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersAssessedIncomeFigure(0);
            } else {
                try {
                    setPartnersAssessedIncomeFigure(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersAssessedIncomeFigure(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersAdjustedAssessedIncomeFigure(0);
            } else {
                try {
                    setPartnersAdjustedAssessedIncomeFigure(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersAdjustedAssessedIncomeFigure(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //125
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersGrossWeeklyIncomeFromEmployment(0);
            } else {
                try {
                    setPartnersGrossWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersGrossWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersNetWeeklyIncomeFromEmployment(0);
            } else {
                try {
                    setPartnersNetWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersNetWeeklyIncomeFromEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersGrossWeeklyIncomeFromSelfEmployment(0);
            } else {
                try {
                    setPartnersGrossWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersGrossWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersNetWeeklyIncomeFromSelfEmployment(0);
            } else {
                try {
                    setPartnersNetWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersNetWeeklyIncomeFromSelfEmployment(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersTotalAmountOfEarningsDisregarded(0);
            } else {
                try {
                    setPartnersTotalAmountOfEarningsDisregarded(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersTotalAmountOfEarningsDisregarded(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //130
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(0);
            } else {
                try {
                    setPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromAttendanceAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromAttendanceAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromAttendanceAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromBusinessStartUpAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromBusinessStartUpAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromBusinessStartUpAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromChildBenefit(0);
            } else {
                try {
                    setPartnersIncomeFromChildBenefit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromChildBenefit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromPersonalPension(0);
            } else {
                try {
                    setPartnersIncomeFromPersonalPension(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromPersonalPension(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //135
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromSevereDisabilityAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromSevereDisabilityAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromSevereDisabilityAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromMaternityAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromMaternityAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromMaternityAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromContributionBasedJobSeekersAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromContributionBasedJobSeekersAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromContributionBasedJobSeekersAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromStudentGrantLoan(0);
            } else {
                try {
                    setPartnersIncomeFromStudentGrantLoan(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromStudentGrantLoan(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromSubTenants(0);
            } else {
                try {
                    setPartnersIncomeFromSubTenants(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromSubTenants(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //140
        if (n < fields.length) {
            if (fields[140].equalsIgnoreCase("")) {
                setPartnersIncomeFromBoarders(0);
            } else {
                try {
                    setPartnersIncomeFromBoarders(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromBoarders(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromTrainingForWorkCommunityAction(0);
            } else {
                try {
                    setPartnersIncomeFromTrainingForWorkCommunityAction(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromTrainingForWorkCommunityAction(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromIncapacityBenefitShortTermLower(0);
            } else {
                try {
                    setPartnersIncomeFromIncapacityBenefitShortTermLower(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromIncapacityBenefitShortTermLower(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromIncapacityBenefitShortTermHigher(0);
            } else {
                try {
                    setPartnersIncomeFromIncapacityBenefitShortTermHigher(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromIncapacityBenefitShortTermHigher(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromIncapacityBenefitLongTerm(0);
            } else {
                try {
                    setPartnersIncomeFromIncapacityBenefitLongTerm(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromIncapacityBenefitLongTerm(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //145
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromNewDeal50PlusEmploymentCredit(0);
            } else {
                try {
                    setPartnersIncomeFromNewDeal50PlusEmploymentCredit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromNewDeal50PlusEmploymentCredit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromNewTaxCredits(0);
            } else {
                try {
                    setPartnersIncomeFromNewTaxCredits(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromNewTaxCredits(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromDisabilityLivingAllowanceCareComponent(0);
            } else {
                try {
                    setPartnersIncomeFromDisabilityLivingAllowanceCareComponent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromDisabilityLivingAllowanceCareComponent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent(0);
            } else {
                try {
                    setPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromGovernemntTraining(0);
            } else {
                try {
                    setPartnersIncomeFromGovernemntTraining(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromGovernemntTraining(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //150
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromIndustrialInjuriesDisablementBenefit(0);
            } else {
                try {
                    setPartnersIncomeFromIndustrialInjuriesDisablementBenefit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromIndustrialInjuriesDisablementBenefit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromCarersAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromCarersAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromCarersAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromStatuatorySickPay(0);
            } else {
                try {
                    setPartnersIncomeFromStatuatorySickPay(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromStatuatorySickPay(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromStatutoryMaternityPaternityPay(0);
            } else {
                try {
                    setPartnersIncomeFromStatutoryMaternityPaternityPay(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromStatutoryMaternityPaternityPay(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(0);
            } else {
                try {
                    setPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //155
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromWarDisablementPensionArmedForcesGIP(0);
            } else {
                try {
                    setPartnersIncomeFromWarDisablementPensionArmedForcesGIP(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromWarDisablementPensionArmedForcesGIP(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromWarMobilitySupplement(0);
            } else {
                try {
                    setPartnersIncomeFromWarMobilitySupplement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromWarMobilitySupplement(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromWidowsWidowersPension(0);
            } else {
                try {
                    setPartnersIncomeFromWidowsWidowersPension(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromWidowsWidowersPension(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromBereavementAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromBereavementAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromBereavementAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromWidowedParentsAllowance(0);
            } else {
                try {
                    setPartnersIncomeFromWidowedParentsAllowance(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromWidowedParentsAllowance(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //160
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromYouthTrainingScheme(0);
            } else {
                try {
                    setPartnersIncomeFromYouthTrainingScheme(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromYouthTrainingScheme(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersOtherIncome(0);
            } else {
                try {
                    setPartnersOtherIncome(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersOtherIncome(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersTotalAmountOfIncomeDisregarded(0);
            } else {
                try {
                    setPartnersTotalAmountOfIncomeDisregarded(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersTotalAmountOfIncomeDisregarded(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n = 169;
        if (n < fields.length) {
            setClaimantsGender(fields[n]);
        } else {
            return;
        }
        n++; //170
        if (n < fields.length) {
            setPartnersDateOfBirth(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setRentAllowanceMethodOfPayment(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setRentAllowancePaymentDestination(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setContractualRentAmount(0);
            } else {
                try {
                    setContractualRentAmount(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setContractualRentAmount(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            n = setTimePeriodContractualRentFigureCovers(n, fields);
        } else {
            return;
        }
        n++; //175
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromPensionCreditSavingsCredit(0);
            } else {
                try {
                    setClaimantsIncomeFromPensionCreditSavingsCredit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromPensionCreditSavingsCredit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromPensionCreditSavingsCredit(0);
            } else {
                try {
                    setPartnersIncomeFromPensionCreditSavingsCredit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromPensionCreditSavingsCredit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromMaintenancePayments(0);
            } else {
                try {
                    setClaimantsIncomeFromMaintenancePayments(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromMaintenancePayments(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromMaintenancePayments(0);
            } else {
                try {
                    setPartnersIncomeFromMaintenancePayments(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromMaintenancePayments(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromOccupationalPension(0);
            } else {
                try {
                    setClaimantsIncomeFromOccupationalPension(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromOccupationalPension(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //180
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromOccupationalPension(0);
            } else {
                try {
                    setPartnersIncomeFromOccupationalPension(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromOccupationalPension(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsIncomeFromWidowsBenefit(0);
            } else {
                try {
                    setClaimantsIncomeFromWidowsBenefit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsIncomeFromWidowsBenefit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++; //182
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersIncomeFromWidowsBenefit(0);
            } else {
                try {
                    setPartnersIncomeFromWidowsBenefit(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersIncomeFromWidowsBenefit(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n = 193; //193
        if (n < fields.length) {
            setCTBClaimEntitlementStartDate(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateHBClaimClosedWithdrawnDecidedUnsuccessfulDefective(fields[n]);
        } else {
            return;
        }
        n++; //195
        if (n < fields.length) {
            setDateCTBClaimClosedWithdrawnDecidedUnsuccessfulDefective(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setTotalNumberOfRooms(0);
            } else {
                try {
                    setTotalNumberOfRooms(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setTotalNumberOfRooms(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setNonSelfContainedAccomodation(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setTypeOfLHANumberOfRoomsEntitedTo(0);
            } else {
                try {
                    setTypeOfLHANumberOfRoomsEntitedTo(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setTypeOfLHANumberOfRoomsEntitedTo(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setTransitionalProtectionFromNationalRolloutOfLHA(n, fields);
        } else {
            return;
        }
        n++; //200
        if (n < fields.length) {
            setLocality(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setValueOfLHA(0);
            } else {
                try {
                    setValueOfLHA(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setValueOfLHA(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setReasonsThatHBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setReasonsThatCTBClaimWasClosedWithdrawnDecidedUnsuccessfulDefective(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setPartnersGender(fields[n]);
        } else {
            return;
        }
        n = 211;
        if (n < fields.length) {
            setHBClaimTreatAsDateMade(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setSourceOfTheMostRecentHBClaim(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDidVerificationIdentifyAnyIncorrectInformationOnTheHBClaim(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateOfFirstHBPaymentRentAllowanceOnly(fields[n]);
        } else {
            return;
        }
        n++; //215
        if (n < fields.length) {
            setWasTheFirstPaymentAPaymentOnAccountRentAllowanceOnly(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setWasThereABackdatedAwardMadeOnTheHBClaim(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateHBBackdatingFrom(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateHBBackdatingTo(fields[n]);
        } else {
            return;
        }
        n = 220;
        if (n < fields.length) {
            setCTBClaimTreatAsMadeDate(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setSourceOfTheMostRecentCTBClaim(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDidVerificationIdentifyAnyIncorrectInformationOnTheCTBClaim(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setWasThereABackdatedAwardMadeOnTheCTBClaim(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateCTBBackdatingFrom(fields[n]);
        } else {
            return;
        }
        n++; //225
        if (n < fields.length) {
            setDateCTBBackdatingTo(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setTotalAmountOfBackdatedCTBAwarded(0);
            } else {
                try {
                    setTotalAmountOfBackdatedCTBAwarded(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setTotalAmountOfBackdatedCTBAwarded(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setIsThisCaseSubjectToNonHRAThresholdAndCapsNonHRACasesOnly(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setIfSubjectToTheNonHRAThresholdAndCapsStateTypeOfAccommodation(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT(fields[n]);
        } else {
            return;
        }
        n++; //230
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersTotalCapital(0);
            } else {
                try {
                    setPartnersTotalCapital(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersTotalCapital(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure(0);
            } else {
                try {
                    setWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure(Integer.valueOf(fields[n]))");
//                throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsTotalHoursOfRemunerativeWorkPerWeek(0);
            } else {
                try {
                    setClaimantsTotalHoursOfRemunerativeWorkPerWeek(Double.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("setClaimantsTotalHoursOfRemunerativeWorkPerWeek(Double.valueOf(fields[n]))");
                    System.err.println("fields[n],=" + fields[n]);
//                    e.printStackTrace(System.err);
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].equalsIgnoreCase("") || fields[n].isEmpty() || fields[n].equalsIgnoreCase(" ") || fields[n].equalsIgnoreCase("  ")) {
                setPartnersTotalHoursOfRemunerativeWorkPerWeek(0);
            } else {
                try {
                    setPartnersTotalHoursOfRemunerativeWorkPerWeek(Double.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("setPartnersTotalHoursOfRemunerativeWorkPerWeek(Double.valueOf(fields[n]))");
                    System.err.println("fields[n],=" + fields[n]);
//                    e.printStackTrace(System.err);
//                    throw e;
                }
            }
        } else {
            return;
        }
        n = 236;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setTotalHBPaymentsCreditsSinceLastExtract(0);
            } else {
                try {
                    setTotalHBPaymentsCreditsSinceLastExtract(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("setTotalHBPaymentsCreditsSinceLastExtract(Double.valueOf(fields[n]))");
                    System.err.println("fields[n],=" + fields[n]);
//                    e.printStackTrace(System.err);
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setTotalCTBPaymentsCreditsSinceLastExtract(0);
            } else {
                try {
                    setTotalCTBPaymentsCreditsSinceLastExtract(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("setTotalCTBPaymentsCreditsSinceLastExtract(Double.valueOf(fields[n]))");
                    System.err.println("fields[n],=" + fields[n]);
//                    e.printStackTrace(System.err);
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setClaimantsEthnicGroup(n, fields);
        } else {
            return;
        }
        n = 260;
        if (n < fields.length) {
            setDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentHBClaim(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentHBClaim(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateCouncilTaxPayable(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateThatAllInformationWasRecievedFromTheClaimantToEnableADecisionOnTheMostRecentCTBClaim(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setDateThatAllInformationWasRecievedFromThirdPartiesToEnableADecisionOnTheMostRecentCTBClaim(fields[n]);
        } else {
            return;
        }
        n = 270;
        if (n < fields.length) {
            setPartnersDateOfDeath(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setJointTenancyFlag(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setAppointeeFlag(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setRentFreeWeeksIndicator(0);
            } else {
                try {
                    setRentFreeWeeksIndicator(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("setRentFreeWeeksIndicator(Double.valueOf(fields[n]))");
                    System.err.println("fields[n],=" + fields[n]);
//                    e.printStackTrace(System.err);
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++; //274
        if (n < fields.length) {
            setLastPaidToDate(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            n = setWeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability(n, fields);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsWeeklyIncomeFromESABasicElement(0);
            } else {
                try {
                    setClaimantsWeeklyIncomeFromESABasicElement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsWeeklyIncomeFromESABasicElement(Integer.valueOf(fields[n]))");
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersWeeklyIncomeFromESABasicElement(0);
            } else {
                try {
                    setPartnersWeeklyIncomeFromESABasicElement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersWeeklyIncomeFromESABasicElement(Integer.valueOf(fields[n]))");
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsWeeklyIncomeFromESAWRAGElement(0);
            } else {
                try {
                    setClaimantsWeeklyIncomeFromESAWRAGElement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsWeeklyIncomeFromESAWRAGElement(Integer.valueOf(fields[n]))");
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++; //279
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersWeeklyIncomeFromESAWRAGElement(0);
            } else {
                try {
                    setPartnersWeeklyIncomeFromESAWRAGElement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersWeeklyIncomeFromESAWRAGElement(Integer.valueOf(fields[n]))");
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setClaimantsWeeklyIncomeFromESASCGElement(0);
            } else {
                try {
                    setClaimantsWeeklyIncomeFromESASCGElement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setClaimantsWeeklyIncomeFromESASCGElement(Integer.valueOf(fields[n]))");
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            if (fields[n].trim().isEmpty()) {
                setPartnersWeeklyIncomeFromESASCGElement(0);
            } else {
                try {
                    setPartnersWeeklyIncomeFromESASCGElement(Integer.valueOf(fields[n]));
                } catch (NumberFormatException e) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NumberFormatException in setPartnersWeeklyIncomeFromESASCGElement(Integer.valueOf(fields[n]))");
//                    throw e;
                }
            }
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            setWRAGPremiumFlag(fields[n]);
        } else {
            return;
        }
        n++; //283
        if (n < fields.length) {
            setSCGPremiumFlag(fields[n]);
        } else {
            return;
        }
        n++;
        if (n < fields.length) {
            // This is only in type 1 records.
            setLandlordPostcode(this.env.oe.getHandler().formatPostcode(fields[n]));
        }
    }

    /**
     * 8 13 NumberOfChildDependents: The total number of household members with
     * the statuses 'Child Under 16' and 'Youth 16 - 19' in the split in which
     * the Extract Date falls.
     */
    private int NumberOfChildDependents;
    /**
     * 9 14 NumberOfNonDependents: The number of Non-Dependant Groups present on
     * the claim in the split in which the Extract Date falls.
     */
    private int NumberOfNonDependents;

    /**
     * 38 43 WeeklyHousingBenefitEntitlement
     */
    private int WeeklyHousingBenefitEntitlement;
    /**
     * 39 44 WeeklyCouncilTaxBenefitEntitlement
     */
    private int WeeklyCouncilTaxBenefitEntitlement;
    /**
     * 40 45 FrequencyOfPaymentOfHB
     */
    private int FrequencyOfPaymentOfHB;
    /**
     * 41 46 FrequencyOfPaymentOfCTB
     */
    private int FrequencyOfPaymentOfCTB;
    /**
     * 42 47 PreDeterminationAmountOfHB
     */
    private int PreDeterminationAmountOfHB;
    /**
     * 43 48 PreDeterminationAmountOfCTB
     */
    private int PreDeterminationAmountOfCTB;

    /**
     * 48 53 ReasonForDirectPayment
     */
    private int ReasonForDirectPayment;

    /**
     * 49 54 TimingOfPaymentOfRentAllowance
     */
    private int TimingOfPaymentOfRentAllowance;
    /**
     * 50 55 ExtendedPaymentCase
     */
    private int ExtendedPaymentCase;
    /**
     * 51 56 CouncilTaxBand
     */
    private String CouncilTaxBand;
    /**
     * 52 57 WeeklyEligibleRentAmount
     */
    private int WeeklyEligibleRentAmount;
    /**
     * 53 58 WeeklyEligibleCouncilTaxAmount
     */
    private int WeeklyEligibleCouncilTaxAmount;
    /**
     * 54 59 ClaimantsStudentIndicator
     */
    private String ClaimantsStudentIndicator;
    /**
     * 55 60 SecondAdultRebate
     */
    private int SecondAdultRebate;
    /**
     * 56 61 RebatePercentageWhereASecondAdultRebateHasBeenAwarded
     */
    private int RebatePercentageWhereASecondAdultRebateHasBeenAwarded;
    /**
     * 59 64 WeeklyLocalReferenceRent
     */
    private int WeeklyLocalReferenceRent;
    /**
     * 60 65 WeeklySingleRoomRent
     */
    private int WeeklySingleRoomRent;
    /**
     * 61 66 WeeklyClaimRelatedRent
     */
    private int WeeklyClaimRelatedRent;
    /**
     * 62 67 RentOfficerDeterminationOfIneligibleCharges
     */
    private int RentOfficerDeterminationOfIneligibleCharges;
    /**
     * 63 68 WeeklyMaximumRent
     */
    private int WeeklyMaximumRent;
    /**
     * 64 69 TotalDeductionForMeals
     */
    private int TotalDeductionForMeals;
    /**
     * 65 70 WeeklyAdditionalDiscretionaryPayment
     */
    private int WeeklyAdditionalDiscretionaryPayment;
    /**
     * 66 71 ThirteenOrFiftyTwoWeekProtectionApplies
     */
    private int ThirteenOrFiftyTwoWeekProtectionApplies;
    /**
     * 67 72 DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision
     */
    private String DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision;
    /**
     * 68 73 ClaimantsAssessedIncomeFigure
     */
    private int ClaimantsAssessedIncomeFigure;
    /**
     * 69 74 ClaimantsAdjustedAssessedIncomeFigure
     */
    private int ClaimantsAdjustedAssessedIncomeFigure;
    /**
     * 70 75 ClaimantsTotalCapital
     */
    private int ClaimantsTotalCapital;
    /**
     * 71 76 ClaimantsGrossWeeklyIncomeFromEmployment
     */
    private int ClaimantsGrossWeeklyIncomeFromEmployment;
    /**
     * 72 77 ClaimantsNetWeeklyIncomeFromEmployment
     */
    private int ClaimantsNetWeeklyIncomeFromEmployment;
    /**
     * 73 78 ClaimantsGrossWeeklyIncomeFromSelfEmployment
     */
    private int ClaimantsGrossWeeklyIncomeFromSelfEmployment;
    /**
     * 74 79 ClaimantsNetWeeklyIncomeFromSelfEmployment
     */
    private int ClaimantsNetWeeklyIncomeFromSelfEmployment;
    /**
     * 75 80 ClaimantsTotalAmountOfEarningsDisregarded
     */
    private int ClaimantsTotalAmountOfEarningsDisregarded;
    /**
     * 76 81 ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     */
    private int ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    /**
     * 77 82 ClaimantsIncomeFromAttendanceAllowance
     */
    private int ClaimantsIncomeFromAttendanceAllowance;
    /**
     * 78 83 ClaimantsIncomeFromBusinessStartUpAllowance
     */
    private int ClaimantsIncomeFromBusinessStartUpAllowance;
    /**
     * 79 84 ClaimantsIncomeFromChildBenefit
     */
    private int ClaimantsIncomeFromChildBenefit;
    /**
     * 80 85 ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent
     */
    private int ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent;
    /**
     * 81 86 ClaimantsIncomeFromPersonalPension
     */
    private int ClaimantsIncomeFromPersonalPension;
    /**
     * 82 87 ClaimantsIncomeFromSevereDisabilityAllowance
     */
    private int ClaimantsIncomeFromSevereDisabilityAllowance;
    /**
     * 83 88 ClaimantsIncomeFromMaternityAllowance
     */
    private int ClaimantsIncomeFromMaternityAllowance;
    /**
     * 84 89 ClaimantsIncomeFromContributionBasedJobSeekersAllowance
     */
    private int ClaimantsIncomeFromContributionBasedJobSeekersAllowance;
    /**
     * 85 90 ClaimantsIncomeFromStudentGrantLoan
     */
    private int ClaimantsIncomeFromStudentGrantLoan;
    /**
     * 86 91 ClaimantsIncomeFromSubTenants
     */
    private int ClaimantsIncomeFromSubTenants;
    /**
     * 87 92 ClaimantsIncomeFromBoarders
     */
    private int ClaimantsIncomeFromBoarders;
    /**
     * 88 93 ClaimantsIncomeFromTrainingForWorkCommunityAction
     */
    private int ClaimantsIncomeFromTrainingForWorkCommunityAction;
    /**
     * 89 94 ClaimantsIncomeFromIncapacityBenefitShortTermLower
     */
    private int ClaimantsIncomeFromIncapacityBenefitShortTermLower;
    /**
     * 90 95 ClaimantsIncomeFromIncapacityBenefitShortTermHigher
     */
    private int ClaimantsIncomeFromIncapacityBenefitShortTermHigher;
    /**
     * 91 96 ClaimantsIncomeFromIncapacityBenefitLongTerm
     */
    private int ClaimantsIncomeFromIncapacityBenefitLongTerm;
    /**
     * 92 97 ClaimantsIncomeFromNewDeal50PlusEmploymentCredit
     */
    private int ClaimantsIncomeFromNewDeal50PlusEmploymentCredit;
    /**
     * 93 98 ClaimantsIncomeFromNewTaxCredits
     */
    private int ClaimantsIncomeFromNewTaxCredits;
    /**
     * 94 99 ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent
     */
    private int ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent;
    /**
     * 95 100 ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent
     */
    private int ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent;
    /**
     * 96 101 ClaimantsIncomeFromGovernemntTraining
     */
    private int ClaimantsIncomeFromGovernemntTraining;
    /**
     * 97 102 ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit
     */
    private int ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit;
    /**
     * 98 103 ClaimantsIncomeFromCarersAllowance
     */
    private int ClaimantsIncomeFromCarersAllowance;
    /**
     * 99 104 ClaimantsIncomeFromStatutoryMaternityPaternityPay
     */
    private int ClaimantsIncomeFromStatutoryMaternityPaternityPay;
    /**
     * 100 105
     * ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     */
    private int ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    /**
     * 101 106 ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP
     */
    private int ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP;
    /**
     * 102 107 ClaimantsIncomeFromWarMobilitySupplement
     */
    private int ClaimantsIncomeFromWarMobilitySupplement;
    /**
     * 103 108 ClaimantsIncomeFromWidowsWidowersPension
     */
    private int ClaimantsIncomeFromWidowsWidowersPension;
    /**
     * 104 109 ClaimantsIncomeFromBereavementAllowance
     */
    private int ClaimantsIncomeFromBereavementAllowance;
    /**
     * 105 110 ClaimantsIncomeFromWidowedParentsAllowance
     */
    private int ClaimantsIncomeFromWidowedParentsAllowance;
    /**
     * 106 111 ClaimantsIncomeFromYouthTrainingScheme
     */
    private int ClaimantsIncomeFromYouthTrainingScheme;
    /**
     * 107 112 ClaimantsIncomeFromStatuatorySickPay
     */
    private int ClaimantsIncomeFromStatuatorySickPay;
    /**
     * 108 113 ClaimantsOtherIncome
     */
    private int ClaimantsOtherIncome;
    /**
     * 109 114 ClaimantsTotalAmountOfIncomeDisregarded
     */
    private int ClaimantsTotalAmountOfIncomeDisregarded;
    /**
     * 110 115 FamilyPremiumAwarded 0 Not Awarded 1 Awarded
     */
    private int FamilyPremiumAwarded;
    /**
     * 111 116 FamilyLoneParentPremiumAwarded 0 Not Awarded 1 Awarded
     */
    private int FamilyLoneParentPremiumAwarded;
    /**
     * 112 117 DisabilityPremiumAwarded 0 Not Awarded 1 Awarded
     */
    private int DisabilityPremiumAwarded;
    /**
     * 113 118 SevereDisabilityPremiumAwarded 0 Not Awarded 1 Awarded
     */
    private int SevereDisabilityPremiumAwarded;
    /**
     * 114 119 DisabledChildPremiumAwarded 0 Not Awarded 1 Awarded
     */
    private int DisabledChildPremiumAwarded;
    /**
     * 115 120 CarePremiumAwarded 0 Not Awarded 1 Awarded
     */
    private int CarePremiumAwarded;
    /**
     * 116 121 EnhancedDisabilityPremiumAwarded
     */
    private int EnhancedDisabilityPremiumAwarded;
    /**
     * 117 122 BereavementPremiumAwarded
     */
    private int BereavementPremiumAwarded;
    /**
     * 118 123 PartnerFlag 0 No Partner or Disregarded Partner present 1 The
     * total of Partners and Disregarded Partners is 1 2 Multiple
     * Partners/Disregarded Partners are present
     */
    private int PartnerFlag;
    /**
     * 119 124 PartnersStartDate
     */
    private String PartnersStartDate;
    /**
     * 120 125 PartnersEndDate
     */
    private String PartnersEndDate;
    // PartnersTitle
    // PartnersSurname
    // PartnersForename
    /**
     * 122 130 PartnersStudentIndicator
     */
    private String PartnersStudentIndicator;
    /**
     * 123 131 PartnersAssessedIncomeFigure
     */
    private int PartnersAssessedIncomeFigure;
    /**
     * 124 132 PartnersAdjustedAssessedIncomeFigure
     */
    private int PartnersAdjustedAssessedIncomeFigure;
    /**
     * 125 133 PartnersGrossWeeklyIncomeFromEmployment
     */
    private int PartnersGrossWeeklyIncomeFromEmployment;
    /**
     * 126 134 PartnersNetWeeklyIncomeFromEmployment
     */
    private int PartnersNetWeeklyIncomeFromEmployment;
    /**
     * 127 135 PartnersGrossWeeklyIncomeFromSelfEmployment
     */
    private int PartnersGrossWeeklyIncomeFromSelfEmployment;
    /**
     * 128 136 PartnersNetWeeklyIncomeFromSelfEmployment
     */
    private int PartnersNetWeeklyIncomeFromSelfEmployment;
    /**
     * 129 137 PartnersTotalAmountOfEarningsDisregarded
     */
    private int PartnersTotalAmountOfEarningsDisregarded;
    /**
     * 130 138 PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     */
    private int PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    /**
     * 131 139 PartnersIncomeFromAttendanceAllowance
     */
    private int PartnersIncomeFromAttendanceAllowance;
    /**
     * 132 140 PartnersIncomeFromBusinessStartUpAllowance
     */
    private int PartnersIncomeFromBusinessStartUpAllowance;
    /**
     * 133 140 PartnersIncomeFromChildBenefit
     */
    private int PartnersIncomeFromChildBenefit;
    /**
     * 134 141 PartnersIncomeFromPersonalPension
     */
    private int PartnersIncomeFromPersonalPension;
    /**
     * 135 142 PartnersIncomeFromSevereDisabilityAllowance
     */
    private int PartnersIncomeFromSevereDisabilityAllowance;
    /**
     * 136 143 PartnersIncomeFromMaternityAllowance
     */
    private int PartnersIncomeFromMaternityAllowance;
    /**
     * 137 144 PartnersIncomeFromContributionBasedJobSeekersAllowance
     */
    private int PartnersIncomeFromContributionBasedJobSeekersAllowance;
    /**
     * 138 145 PartnersIncomeFromStudentGrantLoan
     */
    private int PartnersIncomeFromStudentGrantLoan;
    /**
     * 139 146 PartnersIncomeFromSubTenants
     */
    private int PartnersIncomeFromSubTenants;
    /**
     * 140 147 PartnersIncomeFromBoarders
     */
    private int PartnersIncomeFromBoarders;
    /**
     * 141 148 PartnersIncomeFromTrainingForWorkCommunityAction
     */
    private int PartnersIncomeFromTrainingForWorkCommunityAction;
    /**
     * 142 150 PartnersIncomeFromIncapacityBenefitShortTermLower
     */
    private int PartnersIncomeFromIncapacityBenefitShortTermLower;
    /**
     * 143 151 PartnersIncomeFromIncapacityBenefitShortTermHigher
     */
    private int PartnersIncomeFromIncapacityBenefitShortTermHigher;
    /**
     * 144 152 PartnersIncomeFromIncapacityBenefitLongTerm
     */
    private int PartnersIncomeFromIncapacityBenefitLongTerm;
    /**
     * 145 153 PartnersIncomeFromNewDeal50PlusEmploymentCredit
     */
    private int PartnersIncomeFromNewDeal50PlusEmploymentCredit;
    /**
     * 146 154 PartnersIncomeFromNewTaxCredits
     */
    private int PartnersIncomeFromNewTaxCredits;
    /**
     * 147 155 PartnersIncomeFromDisabilityLivingAllowanceCareComponent
     */
    private int PartnersIncomeFromDisabilityLivingAllowanceCareComponent;
    /**
     * 148 156 PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent
     */
    private int PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent;
    /**
     * 149 157 PartnersIncomeFromGovernemntTraining
     */
    private int PartnersIncomeFromGovernemntTraining;
    /**
     * 150 158 PartnersIncomeFromIndustrialInjuriesDisablementBenefit
     */
    private int PartnersIncomeFromIndustrialInjuriesDisablementBenefit;
    /**
     * 151 159 PartnersIncomeFromCarersAllowance
     */
    private int PartnersIncomeFromCarersAllowance;
    /**
     * 152 160 PartnersIncomeFromStatuatorySickPay
     */
    private int PartnersIncomeFromStatuatorySickPay;
    /**
     * 153 160 PartnersIncomeFromStatutoryMaternityPaternityPay
     */
    private int PartnersIncomeFromStatutoryMaternityPaternityPay;
    /**
     * 154 161
     * PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     */
    private int PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    /**
     * 155 163 PartnersIncomeFromWarDisablementPensionArmedForcesGIP
     */
    private int PartnersIncomeFromWarDisablementPensionArmedForcesGIP;
    /**
     * 156 164 PartnersIncomeFromWarMobilitySupplement
     */
    private int PartnersIncomeFromWarMobilitySupplement;
    /**
     * 157 165 PartnersIncomeFromWidowsWidowersPension
     */
    private int PartnersIncomeFromWidowsWidowersPension;
    /**
     * 158 166 PartnersIncomeFromBereavementAllowance
     */
    private int PartnersIncomeFromBereavementAllowance;
    /**
     * 159 167 PartnersIncomeFromWidowedParentsAllowance
     */
    private int PartnersIncomeFromWidowedParentsAllowance;
    /**
     * 160 168 PartnersIncomeFromYouthTrainingScheme
     */
    private int PartnersIncomeFromYouthTrainingScheme;
    /**
     * 161 169 PartnersOtherIncome
     */
    private int PartnersOtherIncome;
    /**
     * 162 170 PartnersTotalAmountOfIncomeDisregarded
     */
    private int PartnersTotalAmountOfIncomeDisregarded;
    /**
     * 171 179 RentAllowanceMethodOfPayment
     */
    private int RentAllowanceMethodOfPayment;
    /**
     * 172 180 RentAllowancePaymentDestination
     */
    private int RentAllowancePaymentDestination;
    /**
     * 173 181 ContractualRentAmount
     */
    private int ContractualRentAmount;
    /**
     * 174 182 TimePeriodContractualRentFigureCovers
     */
    private int TimePeriodContractualRentFigureCovers;
    /**
     * 175 183 ClaimantsIncomeFromPensionCreditSavingsCredit
     */
    private int ClaimantsIncomeFromPensionCreditSavingsCredit;
    /**
     * 176 184 PartnersIncomeFromPensionCreditSavingsCredit
     */
    private int PartnersIncomeFromPensionCreditSavingsCredit;
    /**
     * 177 185 ClaimantsIncomeFromMaintenancePayments
     */
    private int ClaimantsIncomeFromMaintenancePayments;
    /**
     * 178 186 PartnersIncomeFromMaintenancePayments
     */
    private int PartnersIncomeFromMaintenancePayments;
    /**
     * 179 187 ClaimantsIncomeFromOccupationalPension
     */
    private int ClaimantsIncomeFromOccupationalPension;
    /**
     * 180 188 PartnersIncomeFromOccupationalPension
     */
    private int PartnersIncomeFromOccupationalPension;
    /**
     * 181 189 ClaimantsIncomeFromWidowsBenefit
     */
    private int ClaimantsIncomeFromWidowsBenefit;
    /**
     * 182 190 PartnersIncomeFromWidowsBenefit
     */
    private int PartnersIncomeFromWidowsBenefit;
    /**
     * 196 204 TotalNumberOfRooms (LHA cases only)
     */
    private int TotalNumberOfRooms;
    /**
     * 197 205 NonSelfContainedAccomodation
     */
    private int NonSelfContainedAccomodation;
    /**
     * 198 206 TypeOfLHANumberOfRoomsEntitedTo
     */
    private int TypeOfLHANumberOfRoomsEntitedTo;
    /**
     * 199 207 TransitionalProtectionFromNationalRolloutOfLHA
     */
    private int TransitionalProtectionFromNationalRolloutOfLHA;
    /**
     * 200 208 Locality
     */
    private String Locality;
    /**
     * 201 209 ValueOfLHA
     */
    private int ValueOfLHA;
    // ClaimantsTelephoneNumber
    /**
     * 204 213 PartnersGender
     */
    private String PartnersGender;
    /**
     * 229 238
     * DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT
     */
    private String DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT;
    /**
     * 230 239 PartnersTotalCapital
     */
    private int PartnersTotalCapital;
    /**
     * 231 240 WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure
     */
    private int WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure;
    /**
     * 232 241 ClaimantsTotalHoursOfRemunerativeWorkPerWeek
     */
    private double ClaimantsTotalHoursOfRemunerativeWorkPerWeek;
    /**
     * 233 242 PartnersTotalHoursOfRemunerativeWorkPerWeek
     */
    private double PartnersTotalHoursOfRemunerativeWorkPerWeek;
    /**
     * 269 278 NumberOfBedroomsForLHARolloutCasesOnly
     */
    private int NumberOfBedroomsForLHARolloutCasesOnly;
    // ClaimantsForename2
    // ClaimantsAddressLine2
    // ClaimantsAddressLine3
    // ClaimantsAddressLine4
    // PartnersForename2
    /**
     * 270 284 PartnersDateOfDeath
     */
    private String PartnersDateOfDeath;
    /**
     * 271 285 JointTenancyFlag
     */
    private int JointTenancyFlag;
    /**
     * 272 286 AppointeeFlag
     */
    private int AppointeeFlag;
    /**
     * 273 287 RentFreeWeeksIndicator
     */
    private int RentFreeWeeksIndicator;
    //BankOrBuildingSocietySortCode
    //BankOrBuildingSocietyAccountNumber
    /**
     * 274 290 LastPaidToDate
     */
    private String LastPaidToDate;
    /**
     * 275 291 WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability
     */
    private int WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability;
    /**
     * 276 292 ClaimantsWeeklyIncomeFromESABasicElement
     */
    private int ClaimantsWeeklyIncomeFromESABasicElement;
    /**
     * 277 293 PartnersWeeklyIncomeFromESABasicElement
     */
    private int PartnersWeeklyIncomeFromESABasicElement;
    /**
     * 278 294 ClaimantsWeeklyIncomeFromESAWRAGElement
     */
    private int ClaimantsWeeklyIncomeFromESAWRAGElement;
    /**
     * 279 295 PartnersWeeklyIncomeFromESAWRAGElement
     */
    private int PartnersWeeklyIncomeFromESAWRAGElement;
    /**
     * 280 296 ClaimantsWeeklyIncomeFromESASCGElement
     */
    private int ClaimantsWeeklyIncomeFromESASCGElement;
    /**
     * 281 297 PartnersWeeklyIncomeFromESASCGElement
     */
    private int PartnersWeeklyIncomeFromESASCGElement;
    /**
     * 282 298 WRAGPremiumFlag
     */
    private String WRAGPremiumFlag;
    /**
     * 283 299 SCGPremiumFlag
     */
    private String SCGPremiumFlag;
    /**
     * In type1 but not type0 283 307
     */
    private String LandlordPostcode;

    @Override
    public String toStringBrief() {
        return super.toStringBrief()
                + ", NumberOfChildDependents=" + NumberOfChildDependents
                + ", NumberOfNonDependents=" + NumberOfNonDependents
                + ", PartnerFlag=" + PartnerFlag
                + ", TotalNumberOfRooms=" + TotalNumberOfRooms;
    }

    @Override
    public String toString() {
        return super.toString()
                + ",NumberOfChildDependents=" + NumberOfChildDependents
                + ",NumberOfNonDependents=" + NumberOfNonDependents
                + ",WeeklyHousingBenefitEntitlement=" + WeeklyHousingBenefitEntitlement
                + ",WeeklyCouncilTaxBenefitEntitlement=" + WeeklyCouncilTaxBenefitEntitlement
                + ",FrequencyOfPaymentOfHB=" + FrequencyOfPaymentOfHB
                + ",FrequencyOfPaymentOfCTB=" + FrequencyOfPaymentOfCTB
                + ",PreDeterminationAmountOfHB=" + PreDeterminationAmountOfHB
                + ",PreDeterminationAmountOfCTB=" + PreDeterminationAmountOfCTB
                + ",ReasonForDirectPayment=" + ReasonForDirectPayment
                + ",TimingOfPaymentOfRentAllowance=" + TimingOfPaymentOfRentAllowance
                + ",ExtendedPaymentCase=" + ExtendedPaymentCase
                + ",CouncilTaxBand=" + CouncilTaxBand
                + ",WeeklyEligibleRentAmount=" + WeeklyEligibleRentAmount
                + ",WeeklyEligibleCouncilTaxAmount=" + WeeklyEligibleCouncilTaxAmount
                + ",ClaimantsStudentIndicator=" + ClaimantsStudentIndicator
                + ",RebatePercentageWhereASecondAdultRebateHasBeenAwarded=" + RebatePercentageWhereASecondAdultRebateHasBeenAwarded
                + ",SecondAdultRebate=" + SecondAdultRebate
                + ",WeeklyLocalReferenceRent=" + WeeklyLocalReferenceRent
                + ",WeeklySingleRoomRent=" + WeeklySingleRoomRent
                + ",WeelklyClaimRelatedRent=" + WeeklyClaimRelatedRent
                + ",RentOfficerDeterminationOfIneligibleCharges=" + RentOfficerDeterminationOfIneligibleCharges
                + ",WeeklyMaximumRent=" + WeeklyMaximumRent
                + ",TotalDeductionForMeals=" + TotalDeductionForMeals
                + ",WeeklyAdditionalDiscretionaryPayment=" + WeeklyAdditionalDiscretionaryPayment
                + ",ThirteenOrFiftyTwoWeekProtectionApplies=" + ThirteenOrFiftyTwoWeekProtectionApplies
                + ",DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision=" + DateOfFirstPaymentOnMostRecentHBClaimFollowingAFullDecision
                + ",ClaimantsAssessedIncomeFigure=" + ClaimantsAssessedIncomeFigure
                + ",ClaimantsAdjustedAssessedIncomeFigure=" + ClaimantsAdjustedAssessedIncomeFigure
                + ",ClaimantsTotalCapital=" + ClaimantsTotalCapital
                + ",ClaimantsGrossWeeklyIncomeFromEmployment=" + ClaimantsGrossWeeklyIncomeFromEmployment
                + ",ClaimantsNetWeeklyIncomeFromEmployment=" + ClaimantsNetWeeklyIncomeFromEmployment
                + ",ClaimantsGrossWeeklyIncomeFromSelfEmployment=" + ClaimantsGrossWeeklyIncomeFromSelfEmployment
                + ",ClaimantsNetWeeklyIncomeFromSelfEmployment=" + ClaimantsNetWeeklyIncomeFromSelfEmployment
                + ",ClaimantsTotalAmountOfEarningsDisregarded=" + ClaimantsTotalAmountOfEarningsDisregarded
                + ",ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded=" + ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
                + ",ClaimantsIncomeFromAttendanceAllowance=" + ClaimantsIncomeFromAttendanceAllowance
                + ",ClaimantsIncomeFromBusinessStartUpAllowance=" + ClaimantsIncomeFromBusinessStartUpAllowance
                + ",ClaimantsIncomeFromChildBenefit=" + ClaimantsIncomeFromChildBenefit
                + ",ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent=" + ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent
                + ",ClaimantsIncomeFromPersonalPension=" + ClaimantsIncomeFromPersonalPension
                + ",ClaimantsIncomeFromSevereDisabilityAllowance=" + ClaimantsIncomeFromSevereDisabilityAllowance
                + ",ClaimantsIncomeFromMaternityAllowance=" + ClaimantsIncomeFromMaternityAllowance
                + ",ClaimantsIncomeFromContributionBasedJobSeekersAllowance=" + ClaimantsIncomeFromContributionBasedJobSeekersAllowance
                + ",ClaimantsIncomeFromStudentGrantLoan=" + ClaimantsIncomeFromStudentGrantLoan
                + ",ClaimantsIncomeFromSubTenants=" + ClaimantsIncomeFromSubTenants
                + ",ClaimantsIncomeFromBoarders=" + ClaimantsIncomeFromBoarders
                + ",ClaimantsIncomeFromTrainingForWorkCommunityAction=" + ClaimantsIncomeFromTrainingForWorkCommunityAction
                + ",ClaimantsIncomeFromIncapacityBenefitShortTermLower=" + ClaimantsIncomeFromIncapacityBenefitShortTermLower
                + ",ClaimantsIncomeFromIncapacityBenefitShortTermHigher=" + ClaimantsIncomeFromIncapacityBenefitShortTermHigher
                + ",ClaimantsIncomeFromIncapacityBenefitLongTerm=" + ClaimantsIncomeFromIncapacityBenefitLongTerm
                + ",ClaimantsIncomeFromNewDeal50PlusEmploymentCredit=" + ClaimantsIncomeFromNewDeal50PlusEmploymentCredit
                + ",ClaimantsIncomeFromNewTaxCredits=" + ClaimantsIncomeFromNewTaxCredits
                + ",ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent=" + ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent
                + ",ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent=" + ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent
                + ",ClaimantsIncomeFromGovernemntTraining=" + ClaimantsIncomeFromGovernemntTraining
                + ",ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit=" + ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit
                + ",ClaimantsIncomeFromCarersAllowance=" + ClaimantsIncomeFromCarersAllowance
                + ",ClaimantsIncomeFromStatutoryMaternityPaternityPay=" + ClaimantsIncomeFromStatutoryMaternityPaternityPay
                + ",ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc=" + ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
                + ",ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP=" + ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP
                + ",ClaimantsIncomeFromWarMobilitySupplement=" + ClaimantsIncomeFromWarMobilitySupplement
                + ",ClaimantsIncomeFromBereavementAllowance=" + ClaimantsIncomeFromBereavementAllowance
                + ",ClaimantsIncomeFromWidowedParentsAllowance=" + ClaimantsIncomeFromWidowedParentsAllowance
                + ",ClaimantsIncomeFromYouthTrainingScheme=" + ClaimantsIncomeFromYouthTrainingScheme
                + ",ClaimantsIncomeFromStatuatorySickPay=" + ClaimantsIncomeFromStatuatorySickPay
                + ",ClaimantsOtherIncome=" + ClaimantsOtherIncome
                + ",ClaimantsTotalAmountOfIncomeDisregarded=" + ClaimantsTotalAmountOfIncomeDisregarded
                + ",FamilyPremiumAwarded=" + FamilyPremiumAwarded
                + ",FamilyLoneParentPremiumAwarded=" + FamilyLoneParentPremiumAwarded
                + ",DisabilityPremiumAwarded=" + DisabilityPremiumAwarded
                + ",SevereDisabilityPremiumAwarded=" + SevereDisabilityPremiumAwarded
                + ",DisabledChildPremiumAwarded=" + DisabledChildPremiumAwarded
                + ",CarePremiumAwarded=" + CarePremiumAwarded
                + ",EnhancedDisabilityPremiumAwarded=" + EnhancedDisabilityPremiumAwarded
                + ",BereavementPremiumAwarded=" + BereavementPremiumAwarded
                + ",PartnerFlag=" + PartnerFlag
                + ",PartnersStartDate=" + PartnersStartDate
                + ",PartnersEndDate=" + PartnersEndDate
                + ",PartnersStudentIndicator=" + PartnersStudentIndicator
                + ",PartnersAssessedIncomeFigure=" + PartnersAssessedIncomeFigure
                + ",PartnersAdjustedAssessedIncomeFigure=" + PartnersAdjustedAssessedIncomeFigure
                + ",PartnersGrossWeeklyIncomeFromEmployment=" + PartnersGrossWeeklyIncomeFromEmployment
                + ",PartnersNetWeeklyIncomeFromEmployment=" + PartnersNetWeeklyIncomeFromEmployment
                + ",PartnersGrossWeeklyIncomeFromSelfEmployment=" + PartnersGrossWeeklyIncomeFromSelfEmployment
                + ",PartnersNetWeeklyIncomeFromSelfEmployment=" + PartnersNetWeeklyIncomeFromSelfEmployment
                + ",PartnersTotalAmountOfEarningsDisregarded=" + PartnersTotalAmountOfEarningsDisregarded
                + ",PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded=" + PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
                + ",PartnersIncomeFromAttendanceAllowance=" + PartnersIncomeFromAttendanceAllowance
                + ",PartnersIncomeFromBusinessStartUpAllowance=" + PartnersIncomeFromBusinessStartUpAllowance
                + ",PartnersIncomeFromChildBenefit=" + PartnersIncomeFromChildBenefit
                + ",PartnersIncomeFromPersonalPension=" + PartnersIncomeFromPersonalPension
                + ",PartnersIncomeFromSevereDisabilityAllowance=" + PartnersIncomeFromSevereDisabilityAllowance
                + ",PartnersIncomeFromMaternityAllowance=" + PartnersIncomeFromMaternityAllowance
                + ",PartnersIncomeFromContributionBasedJobSeekersAllowance=" + PartnersIncomeFromContributionBasedJobSeekersAllowance
                + ",PartnersIncomeFromStudentGrantLoan=" + PartnersIncomeFromStudentGrantLoan
                + ",PartnersIncomeFromSubTenants=" + PartnersIncomeFromSubTenants
                + ",PartnersIncomeFromBoarders=" + PartnersIncomeFromBoarders
                + ",PartnersIncomeFromTrainingForWorkCommunityAction=" + PartnersIncomeFromTrainingForWorkCommunityAction
                + ",PartnersIncomeFromIncapacityBenefitShortTermLower=" + PartnersIncomeFromIncapacityBenefitShortTermLower
                + ",PartnersIncomeFromIncapacityBenefitShortTermHigher=" + PartnersIncomeFromIncapacityBenefitShortTermHigher
                + ",PartnersIncomeFromIncapacityBenefitLongTerm=" + PartnersIncomeFromIncapacityBenefitLongTerm
                + ",PartnersIncomeFromNewDeal50PlusEmploymentCredit=" + PartnersIncomeFromNewDeal50PlusEmploymentCredit
                + ",PartnersIncomeFromNewTaxCredits=" + PartnersIncomeFromNewTaxCredits
                + ",PartnersIncomeFromDisabilityLivingAllowanceCareComponent=" + PartnersIncomeFromDisabilityLivingAllowanceCareComponent
                + ",PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent=" + PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent
                + ",PartnersIncomeFromGovernemntTraining=" + PartnersIncomeFromGovernemntTraining
                + ",PartnersIncomeFromIndustrialInjuriesDisablementBenefit=" + PartnersIncomeFromIndustrialInjuriesDisablementBenefit
                + ",PartnersIncomeFromCarersAllowance=" + PartnersIncomeFromCarersAllowance
                + ",PartnersIncomeFromStatuatorySickPay=" + PartnersIncomeFromStatuatorySickPay
                + ",PartnersIncomeFromStatutoryMaternityPaternityPay=" + PartnersIncomeFromStatutoryMaternityPaternityPay
                + ",PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc=" + PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
                + ",PartnersIncomeFromWarDisablementPensionArmedForcesGIP=" + PartnersIncomeFromWarDisablementPensionArmedForcesGIP
                + ",PartnersIncomeFromWarMobilitySupplement=" + PartnersIncomeFromWarMobilitySupplement
                + ",PartnersIncomeFromWidowsWidowersPension=" + PartnersIncomeFromWidowsWidowersPension
                + ",PartnersIncomeFromBereavementAllowance=" + PartnersIncomeFromBereavementAllowance
                + ",PartnersIncomeFromWidowedParentsAllowance=" + PartnersIncomeFromWidowedParentsAllowance
                + ",PartnersIncomeFromYouthTrainingScheme=" + PartnersIncomeFromYouthTrainingScheme
                + ",PartnersOtherIncome=" + PartnersOtherIncome
                + ",PartnersTotalAmountOfIncomeDisregarded=" + PartnersTotalAmountOfIncomeDisregarded
                + ",RentAllowanceMethodOfPayment=" + RentAllowanceMethodOfPayment
                + ",RentAllowancePaymentDestination=" + RentAllowancePaymentDestination
                + ",ContractualRentAmount=" + ContractualRentAmount
                + ",TimePeriodContractualRentFigureCovers=" + TimePeriodContractualRentFigureCovers
                + ",ClaimantsIncomeFromPensionCreditSavingsCredit=" + ClaimantsIncomeFromPensionCreditSavingsCredit
                + ",PartnersIncomeFromPensionCreditSavingsCredit=" + PartnersIncomeFromPensionCreditSavingsCredit
                + ",ClaimantsIncomeFromMaintenancePayments=" + ClaimantsIncomeFromMaintenancePayments
                + ",PartnersIncomeFromMaintenancePayments=" + PartnersIncomeFromMaintenancePayments
                + ",ClaimantsIncomeFromOccupationalPension=" + ClaimantsIncomeFromOccupationalPension
                + ",PartnersIncomeFromOccupationalPension=" + PartnersIncomeFromOccupationalPension
                + ",ClaimantsIncomeFromWidowsBenefit=" + ClaimantsIncomeFromWidowsBenefit
                + ",PartnersIncomeFromWidowsBenefit=" + PartnersIncomeFromWidowsBenefit
                + ",TotalNumberOfRooms=" + TotalNumberOfRooms
                + ",NonSelfContainedAccomodation=" + NonSelfContainedAccomodation
                + ",TypeOfLHANumberOfRoomsEntitedTo=" + TypeOfLHANumberOfRoomsEntitedTo
                + ",TransitionalProtectionFromNationalRolloutOfLHA=" + TransitionalProtectionFromNationalRolloutOfLHA
                + ",Locality=" + Locality
                + ",ValueOfLHA=" + ValueOfLHA
                + ",PartnersGender=" + PartnersGender
                + ",DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT=" + DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT
                + ",PartnersTotalCapital=" + PartnersTotalCapital
                + ",WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure=" + WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure
                + ",ClaimantsTotalHoursOfRemunerativeWorkPerWeek=" + ClaimantsTotalHoursOfRemunerativeWorkPerWeek
                + ",PartnersTotalHoursOfRemunerativeWorkPerWeek=" + PartnersTotalHoursOfRemunerativeWorkPerWeek
                + ",NumberOfBedroomsForLHARolloutCasesOnly=" + NumberOfBedroomsForLHARolloutCasesOnly
                + ",PartnersDateOfDeath=" + PartnersDateOfDeath
                + ",JointTenancyFlag=" + JointTenancyFlag
                + ",AppointeeFlag=" + AppointeeFlag
                + ",RentFreeWeeksIndicator=" + RentFreeWeeksIndicator
                + ",LastPaidToDate=" + LastPaidToDate
                + ",WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability=" + WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability
                + ",ClaimantsWeeklyIncomeFromESABasicElement=" + ClaimantsWeeklyIncomeFromESABasicElement
                + ",PartnersWeeklyIncomeFromESABasicElement=" + PartnersWeeklyIncomeFromESABasicElement
                + ",ClaimantsWeeklyIncomeFromESAWRAGElement=" + ClaimantsWeeklyIncomeFromESAWRAGElement
                + ",PartnersWeeklyIncomeFromESAWRAGElement=" + PartnersWeeklyIncomeFromESAWRAGElement
                + ",ClaimantsWeeklyIncomeFromESASCGElement=" + ClaimantsWeeklyIncomeFromESASCGElement
                + ",PartnersWeeklyIncomeFromESASCGElement=" + PartnersWeeklyIncomeFromESASCGElement
                + ",WRAGPremiumFlag=" + WRAGPremiumFlag
                + ",SCGPremiumFlag=" + SCGPremiumFlag
                + ",LandlordPostcode=" + LandlordPostcode;
    }

    /**
     * @return the NumberOfChildDependents
     */
    public int getNumberOfChildDependents() {
        return NumberOfChildDependents;
    }

    /**
     * @param NumberOfChildDependents the NumberOfChildDependents to set
     */
    protected final void setNumberOfChildDependents(int NumberOfChildDependents) {
        this.NumberOfChildDependents = NumberOfChildDependents;
    }

    /**
     * @return the NumberOfNonDependents
     */
    public int getNumberOfNonDependents() {
        return NumberOfNonDependents;
    }

    /**
     * @param NumberOfNonDependents the NumberOfNonDependents to set
     */
    protected final void setNumberOfNonDependents(int NumberOfNonDependents) {
        this.NumberOfNonDependents = NumberOfNonDependents;
    }

    /**
     * @return the WeeklyHousingBenefitEntitlement
     */
    public int getWeeklyHousingBenefitEntitlement() {
        return WeeklyHousingBenefitEntitlement;
    }

    /**
     * @param WeeklyHousingBenefitEntitlement the
     * WeeklyHousingBenefitEntitlement to set
     */
    protected final void setWeeklyHousingBenefitEntitlement(int WeeklyHousingBenefitEntitlement) {
        this.WeeklyHousingBenefitEntitlement = WeeklyHousingBenefitEntitlement;
    }

    /**
     * @return the WeeklyCouncilTaxBenefitEntitlement
     */
    public int getWeeklyCouncilTaxBenefitEntitlement() {
        return WeeklyCouncilTaxBenefitEntitlement;
    }

    /**
     * @param WeeklyCouncilTaxBenefitEntitlement the
     * WeeklyCouncilTaxBenefitEntitlement to set
     */
    protected final void setWeeklyCouncilTaxBenefitEntitlement(int WeeklyCouncilTaxBenefitEntitlement) {
        this.WeeklyCouncilTaxBenefitEntitlement = WeeklyCouncilTaxBenefitEntitlement;
    }

    /**
     * @return the FrequencyOfPaymentOfHB
     */
    public int getFrequencyOfPaymentOfHB() {
        return FrequencyOfPaymentOfHB;
    }

    /**
     * @param FrequencyOfPaymentOfHB the FrequencyOfPaymentOfHB to set
     */
    protected final void setFrequencyOfPaymentOfHB(int FrequencyOfPaymentOfHB) {
        this.FrequencyOfPaymentOfHB = FrequencyOfPaymentOfHB;
    }

    protected final void setFrequencyOfPaymentOfHB(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setFrequencyOfPaymentOfHB(0);
        } else {
            setFrequencyOfPaymentOfHB(Integer.valueOf(fields[n]));
            if (FrequencyOfPaymentOfHB != 99
                    && (FrequencyOfPaymentOfHB > 7 || FrequencyOfPaymentOfHB < 0)) {
//                if (! (RecordID == 1338 || RecordID == 1488 || RecordID == 1573)) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("FrequencyOfPaymentOfHB=" + fields[n]);
                System.err.println("FrequencyOfPaymentOfHB != 99 &&"
                        + "|| (FrequencyOfPaymentOfHB > 7 || FrequencyOfPaymentOfHB < 0)");
//                    throw new Exception("FrequencyOfPaymentOfHB != 99 &&"
//                            + "|| (FrequencyOfPaymentOfHB > 7 || FrequencyOfPaymentOfHB < 0)");
//                } else {
//                    int debug = 1;
//                }
            }
        }
    }

    /**
     * @return the FrequencyOfPaymentOfCTB
     */
    public int getFrequencyOfPaymentOfCTB() {
        return FrequencyOfPaymentOfCTB;
    }

    /**
     * @param FrequencyOfPaymentOfCTB the FrequencyOfPaymentOfCTB to set
     */
    protected final void setFrequencyOfPaymentOfCTB(int FrequencyOfPaymentOfCTB) {
        this.FrequencyOfPaymentOfCTB = FrequencyOfPaymentOfCTB;
    }

    protected final void setFrequencyOfPaymentOfCTB(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setFrequencyOfPaymentOfCTB(0);
        } else {
            setFrequencyOfPaymentOfCTB(Integer.valueOf(fields[n]));
        }
        if (FrequencyOfPaymentOfCTB != 3 && FrequencyOfPaymentOfCTB != 0) {
            System.err.println("RecordID=" + RecordID);
            System.err.println("n=" + n);
            System.err.println("FrequencyOfPaymentOfCTB=" + fields[n]);
            System.err.println("FrequencyOfPaymentOfCTB != 3 && FrequencyOfPaymentOfCTB != 0");
//            throw new Exception("FrequencyOfPaymentOfCTB != 3 && FrequencyOfPaymentOfCTB != 0");
        }
    }

    /**
     * @return the PreDeterminationAmountOfHB
     */
    public int getPreDeterminationAmountOfHB() {
        return PreDeterminationAmountOfHB;
    }

    /**
     * @param PreDeterminationAmountOfHB the PreDeterminationAmountOfHB to set
     */
    protected final void setPreDeterminationAmountOfHB(int PreDeterminationAmountOfHB) {
        this.PreDeterminationAmountOfHB = PreDeterminationAmountOfHB;
    }

    /**
     * @return the PreDeterminationAmountOfCTB
     */
    public int getPreDeterminationAmountOfCTB() {
        return PreDeterminationAmountOfCTB;
    }

    /**
     * @param PreDeterminationAmountOfCTB the PreDeterminationAmountOfCTB to set
     */
    protected final void setPreDeterminationAmountOfCTB(int PreDeterminationAmountOfCTB) {
        this.PreDeterminationAmountOfCTB = PreDeterminationAmountOfCTB;
    }

    /**
     * @return the ReasonForDirectPayment
     */
    public int getReasonForDirectPayment() {
        return ReasonForDirectPayment;
    }

    /**
     * @param ReasonForDirectPayment the ReasonForDirectPayment to set
     */
    protected final void setReasonForDirectPayment(int ReasonForDirectPayment) {
        this.ReasonForDirectPayment = ReasonForDirectPayment;
    }

    protected final void setReasonForDirectPayment(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setReasonForDirectPayment(0);
        } else {
            setReasonForDirectPayment(Integer.valueOf(fields[n]));
            if (ReasonForDirectPayment > 8 || ReasonForDirectPayment < 0) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("ReasonForDirectPayment=" + fields[n]);
                System.err.println("ReasonForDirectPayment > 8 || ReasonForDirectPayment < 0");
//                throw new Exception("ReasonForDirectPayment=" + ReasonForDirectPayment + " > 8 || < 0");
            }
        }
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
    protected final void setTimingOfPaymentOfRentAllowance(int TimingOfPaymentOfRentAllowance) {
        this.TimingOfPaymentOfRentAllowance = TimingOfPaymentOfRentAllowance;
    }

    protected final void setTimingOfPaymentOfRentAllowance(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setTimingOfPaymentOfRentAllowance(0);
        } else {
            setTimingOfPaymentOfRentAllowance(Integer.valueOf(fields[n]));
            if (TimingOfPaymentOfRentAllowance > 4 || TimingOfPaymentOfRentAllowance < 0) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("TimingOfPaymentOfRentAllowance=" + fields[n]);
                System.err.println("TimingOfPaymentOfRentAllowance > 4 || TimingOfPaymentOfRentAllowance < 0");
//                throw new Exception("TimingOfPaymentOfRentAllowance > 4 || TimingOfPaymentOfRentAllowance < 0");
            }
        }
    }

    /**
     * @return the ExtendedPaymentCase
     */
    public int getExtendedPaymentCase() {
        return ExtendedPaymentCase;
    }

    /**
     * @param ExtendedPaymentCase the ExtendedPaymentCase to set
     */
    protected final void setExtendedPaymentCase(int ExtendedPaymentCase) {
        this.ExtendedPaymentCase = ExtendedPaymentCase;
    }

    protected final void setExtendedPaymentCase(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setExtendedPaymentCase(0);
        } else {
            setExtendedPaymentCase(Integer.valueOf(fields[n]));
            if (ExtendedPaymentCase > 4 || ExtendedPaymentCase < 0) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("ExtendedPaymentCase=" + fields[n]);
                System.err.println("ExtendedPaymentCase > 4 || ExtendedPaymentCase < 0");
//                throw new Exception("ExtendedPaymentCase > 4 || ExtendedPaymentCase < 0");
            }
        }
    }

    /**
     * @return the CouncilTaxBand
     */
    public String getCouncilTaxBand() {
        return CouncilTaxBand;
    }

    /**
     * @param CouncilTaxBand the CouncilTaxBand to set
     */
    protected final void setCouncilTaxBand(String CouncilTaxBand) {
        this.CouncilTaxBand = CouncilTaxBand;
    }

    /**
     * @return the WeeklyEligibleRentAmount
     */
    public int getWeeklyEligibleRentAmount() {
        return WeeklyEligibleRentAmount;
    }

    /**
     * @param WeeklyEligibleRentAmount the WeeklyEligibleRentAmount to set
     */
    protected final void setWeeklyEligibleRentAmount(int WeeklyEligibleRentAmount) {
        this.WeeklyEligibleRentAmount = WeeklyEligibleRentAmount;
    }

    protected final int setWeeklyEligibleRentAmount(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setWeeklyEligibleRentAmount(0);
        } else {
            try {
                setWeeklyEligibleRentAmount(Integer.valueOf(fields[n]));
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("setWeeklyEligibleRentAmount(int,String[])");
                System.err.println("fields[n],=" + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
                String currentCouncilTaxBand = CouncilTaxBand;
                CouncilTaxBand = fields[n];
                System.err.println("current CouncilTaxBand=" + currentCouncilTaxBand + " set to=" + CouncilTaxBand);
                n++;
                setWeeklyEligibleRentAmount(
                        n,
                        fields);
            }
        }
        return n;
    }

    /**
     * @return the WeeklyEligibleCouncilTaxAmount
     */
    public int getWeeklyEligibleCouncilTaxAmount() {
        return WeeklyEligibleCouncilTaxAmount;
    }

    /**
     * @param WeeklyEligibleCouncilTaxAmount the WeeklyEligibleCouncilTaxAmount
     * to set
     */
    protected final void setWeeklyEligibleCouncilTaxAmount(int WeeklyEligibleCouncilTaxAmount) {
        this.WeeklyEligibleCouncilTaxAmount = WeeklyEligibleCouncilTaxAmount;
    }

    /**
     * @return the ClaimantsStudentIndicator
     */
    public String getClaimantsStudentIndicator() {
        return ClaimantsStudentIndicator;
    }

    /**
     * @param ClaimantsStudentIndicator the ClaimantsStudentIndicator to set
     */
    protected final void setClaimantsStudentIndicator(String ClaimantsStudentIndicator) {
        this.ClaimantsStudentIndicator = ClaimantsStudentIndicator;
    }

    /**
     * @return the RebatePercentageWhereASecondAdultRebateHasBeenAwarded
     */
    public int getRebatePercentageWhereASecondAdultRebateHasBeenAwarded() {
        return RebatePercentageWhereASecondAdultRebateHasBeenAwarded;
    }

    /**
     * @param RebatePercentageWhereASecondAdultRebateHasBeenAwarded the
     * RebatePercentageWhereASecondAdultRebateHasBeenAwarded to set
     */
    protected final void setRebatePercentageWhereASecondAdultRebateHasBeenAwarded(int RebatePercentageWhereASecondAdultRebateHasBeenAwarded) {
        this.RebatePercentageWhereASecondAdultRebateHasBeenAwarded = RebatePercentageWhereASecondAdultRebateHasBeenAwarded;
    }

    protected final void setRebatePercentageWhereASecondAdultRebateHasBeenAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setRebatePercentageWhereASecondAdultRebateHasBeenAwarded(0);
        } else {
            try {
                setRebatePercentageWhereASecondAdultRebateHasBeenAwarded(Integer.valueOf(fields[n]));
                if (RebatePercentageWhereASecondAdultRebateHasBeenAwarded > 4 || RebatePercentageWhereASecondAdultRebateHasBeenAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("RebatePercentageWhereASecondAdultRebateHasBeenAwarded=" + fields[n]);
                    System.err.println("RebatePercentageWhereASecondAdultRebateHasBeenAwarded > 4 || RebatePercentageWhereASecondAdultRebateHasBeenAwarded < 0");
//                throw new Exception("RebatePercentageWhereASecondAdultRebateHasBeenAwarded > 4 || RebatePercentageWhereASecondAdultRebateHasBeenAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("RebatePercentageWhereASecondAdultRebateHasBeenAwarded(int,String[])");
                System.err.println("fields[n],=" + fields[n]);
//                e.printStackTrace(System.err);
//                throw e
            }
        }
    }

    /**
     * @return the SecondAdultRebate
     */
    public int getSecondAdultRebate() {
        return SecondAdultRebate;
    }

    /**
     * @param SecondAdultRebate the SecondAdultRebate to set
     */
    protected final void setSecondAdultRebate(int SecondAdultRebate) {
        this.SecondAdultRebate = SecondAdultRebate;
    }

    protected final void setSecondAdultRebate(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setSecondAdultRebate(-999);
        } else {
            try {
                setSecondAdultRebate(Integer.valueOf(fields[n]));
                if (SecondAdultRebate > 2 || SecondAdultRebate < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("SecondAdultRebate=" + fields[n]);
                    System.err.println("SecondAdultRebate > 2 || SecondAdultRebate < 1");
//                throw new Exception("SecondAdultRebate > 2 || SecondAdultRebate < 1");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setSecondAdultRebate(n,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the WeeklyLocalReferenceRent
     */
    public int getWeeklyLocalReferenceRent() {
        return WeeklyLocalReferenceRent;
    }

    /**
     * @param WeeklyLocalReferenceRent the WeeklyLocalReferenceRent to set
     */
    protected final void setWeeklyLocalReferenceRent(int WeeklyLocalReferenceRent) {
        this.WeeklyLocalReferenceRent = WeeklyLocalReferenceRent;
    }

    /**
     * @return the WeeklySingleRoomRent
     */
    public int getWeeklySingleRoomRent() {
        return WeeklySingleRoomRent;
    }

    /**
     * @param WeeklySingleRoomRent the WeeklySingleRoomRent to set
     */
    protected final void setWeeklySingleRoomRent(int WeeklySingleRoomRent) {
        this.WeeklySingleRoomRent = WeeklySingleRoomRent;
    }

    /**
     * @return the WeeklyClaimRelatedRent
     */
    public int getWeelklyClaimRelatedRent() {
        return WeeklyClaimRelatedRent;
    }

    /**
     * @param WeeklyClaimRelatedRent the WeeklyClaimRelatedRent to set
     */
    protected final void setWeeklyClaimRelatedRent(int WeeklyClaimRelatedRent) {
        this.WeeklyClaimRelatedRent = WeeklyClaimRelatedRent;
    }

    /**
     * @return the RentOfficerDeterminationOfIneligibleCharges
     */
    public int getRentOfficerDeterminationOfIneligibleCharges() {
        return RentOfficerDeterminationOfIneligibleCharges;
    }

    /**
     * @param RentOfficerDeterminationOfIneligibleCharges the
     * RentOfficerDeterminationOfIneligibleCharges to set
     */
    protected final void setRentOfficerDeterminationOfIneligibleCharges(int RentOfficerDeterminationOfIneligibleCharges) {
        this.RentOfficerDeterminationOfIneligibleCharges = RentOfficerDeterminationOfIneligibleCharges;
    }

    /**
     * @return the WeeklyMaximumRent
     */
    public int getWeeklyMaximumRent() {
        return WeeklyMaximumRent;
    }

    /**
     * @param WeeklyMaximumRent the WeeklyMaximumRent to set
     */
    protected final void setWeeklyMaximumRent(int WeeklyMaximumRent) {
        this.WeeklyMaximumRent = WeeklyMaximumRent;
    }

    /**
     * @return the TotalDeductionForMeals
     */
    public int getTotalDeductionForMeals() {
        return TotalDeductionForMeals;
    }

    /**
     * @param TotalDeductionForMeals the TotalDeductionForMeals to set
     */
    protected final void setTotalDeductionForMeals(int TotalDeductionForMeals) {
        this.TotalDeductionForMeals = TotalDeductionForMeals;
    }

    /**
     * @return the WeeklyAdditionalDiscretionaryPayment
     */
    public int getWeeklyAdditionalDiscretionaryPayment() {
        return WeeklyAdditionalDiscretionaryPayment;
    }

    /**
     * @param WeeklyAdditionalDiscretionaryPayment the
     * WeeklyAdditionalDiscretionaryPayment to set
     */
    protected final void setWeeklyAdditionalDiscretionaryPayment(int WeeklyAdditionalDiscretionaryPayment) {
        this.WeeklyAdditionalDiscretionaryPayment = WeeklyAdditionalDiscretionaryPayment;
    }

    /**
     * @return the ThirteenOrFiftyTwoWeekProtectionApplies
     */
    public int getThirteenOrFiftyTwoWeekProtectionApplies() {
        return ThirteenOrFiftyTwoWeekProtectionApplies;
    }

    /**
     * @param ThirteenOrFiftyTwoWeekProtectionApplies the
     * ThirteenOrFiftyTwoWeekProtectionApplies to set
     */
    protected final void setThirteenOrFiftyTwoWeekProtectionApplies(int ThirteenOrFiftyTwoWeekProtectionApplies) {
        this.ThirteenOrFiftyTwoWeekProtectionApplies = ThirteenOrFiftyTwoWeekProtectionApplies;
    }

    protected final void setThirteenOrFiftyTwoWeekProtectionApplies(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setThirteenOrFiftyTwoWeekProtectionApplies(0);
        } else {
            try {
                setThirteenOrFiftyTwoWeekProtectionApplies(Integer.valueOf(fields[n]));
                if (ThirteenOrFiftyTwoWeekProtectionApplies > 1 || ThirteenOrFiftyTwoWeekProtectionApplies < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("ThirteenOrFiftyTwoWeekProtectionApplies=" + fields[n]);
                    System.err.println("ThirteenOrFiftyTwoWeekProtectionApplies > 1 || ThirteenOrFiftyTwoWeekProtectionApplies < 0");
//                throw new Exception("ThirteenOrFiftyTwoWeekProtectionApplies > 1 || ThirteenOrFiftyTwoWeekProtectionApplies < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setThirteenOrFiftyTwoWeekProtectionApplies(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the ClaimantsAssessedIncomeFigure
     */
    public int getClaimantsAssessedIncomeFigure() {
        return ClaimantsAssessedIncomeFigure;
    }

    /**
     * @param ClaimantsAssessedIncomeFigure the ClaimantsAssessedIncomeFigure to
     * set
     */
    protected final void setClaimantsAssessedIncomeFigure(int ClaimantsAssessedIncomeFigure) {
        this.ClaimantsAssessedIncomeFigure = ClaimantsAssessedIncomeFigure;
    }

    /**
     * @return the ClaimantsAdjustedAssessedIncomeFigure
     */
    public int getClaimantsAdjustedAssessedIncomeFigure() {
        return ClaimantsAdjustedAssessedIncomeFigure;
    }

    /**
     * @param ClaimantsAdjustedAssessedIncomeFigure the
     * ClaimantsAdjustedAssessedIncomeFigure to set
     */
    protected final void setClaimantsAdjustedAssessedIncomeFigure(int ClaimantsAdjustedAssessedIncomeFigure) {
        this.ClaimantsAdjustedAssessedIncomeFigure = ClaimantsAdjustedAssessedIncomeFigure;
    }

    /**
     * @return the ClaimantsTotalCapital
     */
    public int getClaimantsTotalCapital() {
        return ClaimantsTotalCapital;
    }

    /**
     * @param ClaimantsTotalCapital the ClaimantsTotalCapital to set
     */
    protected final void setClaimantsTotalCapital(int ClaimantsTotalCapital) {
        this.ClaimantsTotalCapital = ClaimantsTotalCapital;
    }

    /**
     * @return the ClaimantsGrossWeeklyIncomeFromEmployment
     */
    public int getClaimantsGrossWeeklyIncomeFromEmployment() {
        return ClaimantsGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @param ClaimantsGrossWeeklyIncomeFromEmployment the
     * ClaimantsGrossWeeklyIncomeFromEmployment to set
     */
    protected final void setClaimantsGrossWeeklyIncomeFromEmployment(int ClaimantsGrossWeeklyIncomeFromEmployment) {
        this.ClaimantsGrossWeeklyIncomeFromEmployment = ClaimantsGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @return the ClaimantsNetWeeklyIncomeFromEmployment
     */
    public int getClaimantsNetWeeklyIncomeFromEmployment() {
        return ClaimantsNetWeeklyIncomeFromEmployment;
    }

    /**
     * @param ClaimantsNetWeeklyIncomeFromEmployment the
     * ClaimantsNetWeeklyIncomeFromEmployment to set
     */
    protected final void setClaimantsNetWeeklyIncomeFromEmployment(int ClaimantsNetWeeklyIncomeFromEmployment) {
        this.ClaimantsNetWeeklyIncomeFromEmployment = ClaimantsNetWeeklyIncomeFromEmployment;
    }

    /**
     * @return the ClaimantsGrossWeeklyIncomeFromSelfEmployment
     */
    public int getClaimantsGrossWeeklyIncomeFromSelfEmployment() {
        return ClaimantsGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param ClaimantsGrossWeeklyIncomeFromSelfEmployment the
     * ClaimantsGrossWeeklyIncomeFromSelfEmployment to set
     */
    protected final void setClaimantsGrossWeeklyIncomeFromSelfEmployment(int ClaimantsGrossWeeklyIncomeFromSelfEmployment) {
        this.ClaimantsGrossWeeklyIncomeFromSelfEmployment = ClaimantsGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the ClaimantsNetWeeklyIncomeFromSelfEmployment
     */
    public int getClaimantsNetWeeklyIncomeFromSelfEmployment() {
        return ClaimantsNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param ClaimantsNetWeeklyIncomeFromSelfEmployment the
     * ClaimantsNetWeeklyIncomeFromSelfEmployment to set
     */
    protected final void setClaimantsNetWeeklyIncomeFromSelfEmployment(int ClaimantsNetWeeklyIncomeFromSelfEmployment) {
        this.ClaimantsNetWeeklyIncomeFromSelfEmployment = ClaimantsNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the ClaimantsTotalAmountOfEarningsDisregarded
     */
    public int getClaimantsTotalAmountOfEarningsDisregarded() {
        return ClaimantsTotalAmountOfEarningsDisregarded;
    }

    /**
     * @param ClaimantsTotalAmountOfEarningsDisregarded the
     * ClaimantsTotalAmountOfEarningsDisregarded to set
     */
    protected final void setClaimantsTotalAmountOfEarningsDisregarded(int ClaimantsTotalAmountOfEarningsDisregarded) {
        this.ClaimantsTotalAmountOfEarningsDisregarded = ClaimantsTotalAmountOfEarningsDisregarded;
    }

    /**
     * @return the
     * ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     */
    public int getClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded() {
        return ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @param ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     * the ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded to
     * set
     */
    protected final void setClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(int ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded) {
        this.ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded = ClaimantsIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @return the ClaimantsIncomeFromAttendanceAllowance
     */
    public int getClaimantsIncomeFromAttendanceAllowance() {
        return ClaimantsIncomeFromAttendanceAllowance;
    }

    /**
     * @param ClaimantsIncomeFromAttendanceAllowance the
     * ClaimantsIncomeFromAttendanceAllowance to set
     */
    protected final void setClaimantsIncomeFromAttendanceAllowance(int ClaimantsIncomeFromAttendanceAllowance) {
        this.ClaimantsIncomeFromAttendanceAllowance = ClaimantsIncomeFromAttendanceAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromBusinessStartUpAllowance
     */
    public int getClaimantsIncomeFromBusinessStartUpAllowance() {
        return ClaimantsIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @param ClaimantsIncomeFromBusinessStartUpAllowance the
     * ClaimantsIncomeFromBusinessStartUpAllowance to set
     */
    protected final void setClaimantsIncomeFromBusinessStartUpAllowance(int ClaimantsIncomeFromBusinessStartUpAllowance) {
        this.ClaimantsIncomeFromBusinessStartUpAllowance = ClaimantsIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromChildBenefit
     */
    public int getClaimantsIncomeFromChildBenefit() {
        return ClaimantsIncomeFromChildBenefit;
    }

    /**
     * @param ClaimantsIncomeFromChildBenefit the
     * ClaimantsIncomeFromChildBenefit to set
     */
    protected final void setClaimantsIncomeFromChildBenefit(int ClaimantsIncomeFromChildBenefit) {
        this.ClaimantsIncomeFromChildBenefit = ClaimantsIncomeFromChildBenefit;
    }

    /**
     * @return the ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent
     */
    public int getClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent() {
        return ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent;
    }

    /**
     * @param ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent the
     * ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent to set
     */
    protected final void setClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent(int ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent) {
        this.ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent = ClaimantsIncomeFromOneParentBenefitChildBenefitLoneParent;
    }

    /**
     * @return the ClaimantsIncomeFromPersonalPension
     */
    public int getClaimantsIncomeFromPersonalPension() {
        return ClaimantsIncomeFromPersonalPension;
    }

    /**
     * @param ClaimantsIncomeFromPersonalPension the
     * ClaimantsIncomeFromPersonalPension to set
     */
    protected final void setClaimantsIncomeFromPersonalPension(int ClaimantsIncomeFromPersonalPension) {
        this.ClaimantsIncomeFromPersonalPension = ClaimantsIncomeFromPersonalPension;
    }

    /**
     * @return the ClaimantsIncomeFromSevereDisabilityAllowance
     */
    public int getClaimantsIncomeFromSevereDisabilityAllowance() {
        return ClaimantsIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @param ClaimantsIncomeFromSevereDisabilityAllowance the
     * ClaimantsIncomeFromSevereDisabilityAllowance to set
     */
    protected final void setClaimantsIncomeFromSevereDisabilityAllowance(int ClaimantsIncomeFromSevereDisabilityAllowance) {
        this.ClaimantsIncomeFromSevereDisabilityAllowance = ClaimantsIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromMaternityAllowance
     */
    public int getClaimantsIncomeFromMaternityAllowance() {
        return ClaimantsIncomeFromMaternityAllowance;
    }

    /**
     * @param ClaimantsIncomeFromMaternityAllowance the
     * ClaimantsIncomeFromMaternityAllowance to set
     */
    protected final void setClaimantsIncomeFromMaternityAllowance(int ClaimantsIncomeFromMaternityAllowance) {
        this.ClaimantsIncomeFromMaternityAllowance = ClaimantsIncomeFromMaternityAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromContributionBasedJobSeekersAllowance
     */
    public int getClaimantsIncomeFromContributionBasedJobSeekersAllowance() {
        return ClaimantsIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @param ClaimantsIncomeFromContributionBasedJobSeekersAllowance the
     * ClaimantsIncomeFromContributionBasedJobSeekersAllowance to set
     */
    protected final void setClaimantsIncomeFromContributionBasedJobSeekersAllowance(int ClaimantsIncomeFromContributionBasedJobSeekersAllowance) {
        this.ClaimantsIncomeFromContributionBasedJobSeekersAllowance = ClaimantsIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromStudentGrantLoan
     */
    public int getClaimantsIncomeFromStudentGrantLoan() {
        return ClaimantsIncomeFromStudentGrantLoan;
    }

    /**
     * @param ClaimantsIncomeFromStudentGrantLoan the
     * ClaimantsIncomeFromStudentGrantLoan to set
     */
    protected final void setClaimantsIncomeFromStudentGrantLoan(int ClaimantsIncomeFromStudentGrantLoan) {
        this.ClaimantsIncomeFromStudentGrantLoan = ClaimantsIncomeFromStudentGrantLoan;
    }

    /**
     * @return the ClaimantsIncomeFromSubTenants
     */
    public int getClaimantsIncomeFromSubTenants() {
        return ClaimantsIncomeFromSubTenants;
    }

    /**
     * @param ClaimantsIncomeFromSubTenants the ClaimantsIncomeFromSubTenants to
     * set
     */
    protected final void setClaimantsIncomeFromSubTenants(int ClaimantsIncomeFromSubTenants) {
        this.ClaimantsIncomeFromSubTenants = ClaimantsIncomeFromSubTenants;
    }

    /**
     * @return the ClaimantsIncomeFromBoarders
     */
    public int getClaimantsIncomeFromBoarders() {
        return ClaimantsIncomeFromBoarders;
    }

    /**
     * @param ClaimantsIncomeFromBoarders the ClaimantsIncomeFromBoarders to set
     */
    protected final void setClaimantsIncomeFromBoarders(int ClaimantsIncomeFromBoarders) {
        this.ClaimantsIncomeFromBoarders = ClaimantsIncomeFromBoarders;
    }

    /**
     * @return the ClaimantsIncomeFromTrainingForWorkCommunityAction
     */
    public int getClaimantsIncomeFromTrainingForWorkCommunityAction() {
        return ClaimantsIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @param ClaimantsIncomeFromTrainingForWorkCommunityAction the
     * ClaimantsIncomeFromTrainingForWorkCommunityAction to set
     */
    protected final void setClaimantsIncomeFromTrainingForWorkCommunityAction(int ClaimantsIncomeFromTrainingForWorkCommunityAction) {
        this.ClaimantsIncomeFromTrainingForWorkCommunityAction = ClaimantsIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @return the ClaimantsIncomeFromIncapacityBenefitShortTermLower
     */
    public int getClaimantsIncomeFromIncapacityBenefitShortTermLower() {
        return ClaimantsIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @param ClaimantsIncomeFromIncapacityBenefitShortTermLower the
     * ClaimantsIncomeFromIncapacityBenefitShortTermLower to set
     */
    protected final void setClaimantsIncomeFromIncapacityBenefitShortTermLower(int ClaimantsIncomeFromIncapacityBenefitShortTermLower) {
        this.ClaimantsIncomeFromIncapacityBenefitShortTermLower = ClaimantsIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @return the ClaimantsIncomeFromIncapacityBenefitShortTermHigher
     */
    public int getClaimantsIncomeFromIncapacityBenefitShortTermHigher() {
        return ClaimantsIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @param ClaimantsIncomeFromIncapacityBenefitShortTermHigher the
     * ClaimantsIncomeFromIncapacityBenefitShortTermHigher to set
     */
    protected final void setClaimantsIncomeFromIncapacityBenefitShortTermHigher(int ClaimantsIncomeFromIncapacityBenefitShortTermHigher) {
        this.ClaimantsIncomeFromIncapacityBenefitShortTermHigher = ClaimantsIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @return the ClaimantsIncomeFromIncapacityBenefitLongTerm
     */
    public int getClaimantsIncomeFromIncapacityBenefitLongTerm() {
        return ClaimantsIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @param ClaimantsIncomeFromIncapacityBenefitLongTerm the
     * ClaimantsIncomeFromIncapacityBenefitLongTerm to set
     */
    protected final void setClaimantsIncomeFromIncapacityBenefitLongTerm(int ClaimantsIncomeFromIncapacityBenefitLongTerm) {
        this.ClaimantsIncomeFromIncapacityBenefitLongTerm = ClaimantsIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @return the ClaimantsIncomeFromNewDeal50PlusEmploymentCredit
     */
    public int getClaimantsIncomeFromNewDeal50PlusEmploymentCredit() {
        return ClaimantsIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @param ClaimantsIncomeFromNewDeal50PlusEmploymentCredit the
     * ClaimantsIncomeFromNewDeal50PlusEmploymentCredit to set
     */
    protected final void setClaimantsIncomeFromNewDeal50PlusEmploymentCredit(int ClaimantsIncomeFromNewDeal50PlusEmploymentCredit) {
        this.ClaimantsIncomeFromNewDeal50PlusEmploymentCredit = ClaimantsIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @return the ClaimantsIncomeFromNewTaxCredits
     */
    public int getClaimantsIncomeFromNewTaxCredits() {
        return ClaimantsIncomeFromNewTaxCredits;
    }

    /**
     * @param ClaimantsIncomeFromNewTaxCredits the
     * ClaimantsIncomeFromNewTaxCredits to set
     */
    protected final void setClaimantsIncomeFromNewTaxCredits(int ClaimantsIncomeFromNewTaxCredits) {
        this.ClaimantsIncomeFromNewTaxCredits = ClaimantsIncomeFromNewTaxCredits;
    }

    /**
     * @return the ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent
     */
    public int getClaimantsIncomeFromDisabilityLivingAllowanceCareComponent() {
        return ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @param ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent the
     * ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent to set
     */
    protected final void setClaimantsIncomeFromDisabilityLivingAllowanceCareComponent(int ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent) {
        this.ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent = ClaimantsIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @return the ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent
     */
    public int getClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent() {
        return ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @param ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent the
     * ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent to set
     */
    protected final void setClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent(int ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent) {
        this.ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent = ClaimantsIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @return the ClaimantsIncomeFromGovernemntTraining
     */
    public int getClaimantsIncomeFromGovernmentTraining() {
        return ClaimantsIncomeFromGovernemntTraining;
    }

    /**
     * @param ClaimantsIncomeFromGovernemntTraining the
     * ClaimantsIncomeFromGovernemntTraining to set
     */
    protected final void setClaimantsIncomeFromGovernemntTraining(int ClaimantsIncomeFromGovernemntTraining) {
        this.ClaimantsIncomeFromGovernemntTraining = ClaimantsIncomeFromGovernemntTraining;
    }

    /**
     * @return the ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit
     */
    public int getClaimantsIncomeFromIndustrialInjuriesDisablementBenefit() {
        return ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @param ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit the
     * ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit to set
     */
    protected final void setClaimantsIncomeFromIndustrialInjuriesDisablementBenefit(int ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit) {
        this.ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit = ClaimantsIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @return the ClaimantsIncomeFromCarersAllowance
     */
    public int getClaimantsIncomeFromCarersAllowance() {
        return ClaimantsIncomeFromCarersAllowance;
    }

    /**
     * @param ClaimantsIncomeFromCarersAllowance the
     * ClaimantsIncomeFromCarersAllowance to set
     */
    protected final void setClaimantsIncomeFromCarersAllowance(int ClaimantsIncomeFromCarersAllowance) {
        this.ClaimantsIncomeFromCarersAllowance = ClaimantsIncomeFromCarersAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromStatutoryMaternityPaternityPay
     */
    public int getClaimantsIncomeFromStatutoryMaternityPaternityPay() {
        return ClaimantsIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @param ClaimantsIncomeFromStatutoryMaternityPaternityPay the
     * ClaimantsIncomeFromStatutoryMaternityPaternityPay to set
     */
    protected final void setClaimantsIncomeFromStatutoryMaternityPaternityPay(int ClaimantsIncomeFromStatutoryMaternityPaternityPay) {
        this.ClaimantsIncomeFromStatutoryMaternityPaternityPay = ClaimantsIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @return the
     * ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     */
    public int getClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc() {
        return ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @param
     * ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * the
     * ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * to set
     */
    protected final void setClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(int ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc) {
        this.ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc = ClaimantsIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @return the ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP
     */
    public int getClaimantsIncomeFromWarDisablementPensionArmedForcesGIP() {
        return ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @param ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP the
     * ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP to set
     */
    protected final void setClaimantsIncomeFromWarDisablementPensionArmedForcesGIP(int ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP) {
        this.ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP = ClaimantsIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @return the ClaimantsIncomeFromWarMobilitySupplement
     */
    public int getClaimantsIncomeFromWarMobilitySupplement() {
        return ClaimantsIncomeFromWarMobilitySupplement;
    }

    /**
     * @param ClaimantsIncomeFromWarMobilitySupplement the
     * ClaimantsIncomeFromWarMobilitySupplement to set
     */
    protected final void setClaimantsIncomeFromWarMobilitySupplement(int ClaimantsIncomeFromWarMobilitySupplement) {
        this.ClaimantsIncomeFromWarMobilitySupplement = ClaimantsIncomeFromWarMobilitySupplement;
    }

    /**
     * @return the ClaimantsIncomeFromWidowsWidowersPension
     */
    public int getClaimantsIncomeFromWarWidowsWidowersPension() {
        return ClaimantsIncomeFromWidowsWidowersPension;
    }

    /**
     * @param ClaimantsIncomeFromWidowsWidowersPension the
     * ClaimantsIncomeFromWidowsWidowersPension to set
     */
    protected final void setClaimantsIncomeFromWidowsWidowersPension(int ClaimantsIncomeFromWidowsWidowersPension) {
        this.ClaimantsIncomeFromWidowsWidowersPension = ClaimantsIncomeFromWidowsWidowersPension;
    }

    /**
     * @return the ClaimantsIncomeFromBereavementAllowance
     */
    public int getClaimantsIncomeFromBereavementAllowance() {
        return ClaimantsIncomeFromBereavementAllowance;
    }

    /**
     * @param ClaimantsIncomeFromBereavementAllowance the
     * ClaimantsIncomeFromBereavementAllowance to set
     */
    protected final void setClaimantsIncomeFromBereavementAllowance(int ClaimantsIncomeFromBereavementAllowance) {
        this.ClaimantsIncomeFromBereavementAllowance = ClaimantsIncomeFromBereavementAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromWidowedParentsAllowance
     */
    public int getClaimantsIncomeFromWidowedParentsAllowance() {
        return ClaimantsIncomeFromWidowedParentsAllowance;
    }

    /**
     * @param ClaimantsIncomeFromWidowedParentsAllowance the
     * ClaimantsIncomeFromWidowedParentsAllowance to set
     */
    protected final void setClaimantsIncomeFromWidowedParentsAllowance(int ClaimantsIncomeFromWidowedParentsAllowance) {
        this.ClaimantsIncomeFromWidowedParentsAllowance = ClaimantsIncomeFromWidowedParentsAllowance;
    }

    /**
     * @return the ClaimantsIncomeFromYouthTrainingScheme
     */
    public int getClaimantsIncomeFromYouthTrainingScheme() {
        return ClaimantsIncomeFromYouthTrainingScheme;
    }

    /**
     * @param ClaimantsIncomeFromYouthTrainingScheme the
     * ClaimantsIncomeFromYouthTrainingScheme to set
     */
    protected final void setClaimantsIncomeFromYouthTrainingScheme(int ClaimantsIncomeFromYouthTrainingScheme) {
        this.ClaimantsIncomeFromYouthTrainingScheme = ClaimantsIncomeFromYouthTrainingScheme;
    }

    /**
     * @return the ClaimantsIncomeFromStatuatorySickPay
     */
    public int getClaimantsIncomeFromStatutorySickPay() {
        return ClaimantsIncomeFromStatuatorySickPay;
    }

    /**
     * @param ClaimantsIncomeFromStatuatorySickPay the
     * ClaimantsIncomeFromStatuatorySickPay to set
     */
    protected final void setClaimantsIncomeFromStatuatorySickPay(int ClaimantsIncomeFromStatuatorySickPay) {
        this.ClaimantsIncomeFromStatuatorySickPay = ClaimantsIncomeFromStatuatorySickPay;
    }

    /**
     * @return the ClaimantsOtherIncome
     */
    public int getClaimantsOtherIncome() {
        return ClaimantsOtherIncome;
    }

    /**
     * @param ClaimantsOtherIncome the ClaimantsOtherIncome to set
     */
    protected final void setClaimantsOtherIncome(int ClaimantsOtherIncome) {
        this.ClaimantsOtherIncome = ClaimantsOtherIncome;
    }

    /**
     * @return the ClaimantsTotalAmountOfIncomeDisregarded
     */
    public int getClaimantsTotalAmountOfIncomeDisregarded() {
        return ClaimantsTotalAmountOfIncomeDisregarded;
    }

    /**
     * @param ClaimantsTotalAmountOfIncomeDisregarded the
     * ClaimantsTotalAmountOfIncomeDisregarded to set
     */
    protected final void setClaimantsTotalAmountOfIncomeDisregarded(int ClaimantsTotalAmountOfIncomeDisregarded) {
        this.ClaimantsTotalAmountOfIncomeDisregarded = ClaimantsTotalAmountOfIncomeDisregarded;
    }

    /**
     * @return the FamilyPremiumAwarded
     */
    public int getFamilyPremiumAwarded() {
        return FamilyPremiumAwarded;
    }

    /**
     * @param FamilyPremiumAwarded the FamilyPremiumAwarded to set
     */
    protected final void setFamilyPremiumAwarded(int FamilyPremiumAwarded) {
        this.FamilyPremiumAwarded = FamilyPremiumAwarded;
    }

    protected final void setFamilyPremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setFamilyPremiumAwarded(0);
        } else {
            try {
                setFamilyPremiumAwarded(Integer.valueOf(fields[n]));
                if (FamilyPremiumAwarded > 1 || FamilyPremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("FamilyPremiumAwarded=" + fields[n]);
                    System.err.println("FamilyPremiumAwarded > 1 || FamilyPremiumAwarded < 0");
//                throw new Exception("FamilyPremiumAwarded > 1 || FamilyPremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setFamilyPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the FamilyLoneParentPremiumAwarded
     */
    public int getFamilyLoneParentPremiumAwarded() {
        return FamilyLoneParentPremiumAwarded;
    }

    /**
     * @param FamilyLoneParentPremiumAwarded the FamilyLoneParentPremiumAwarded
     * to set
     */
    protected final void setFamilyLoneParentPremiumAwarded(int FamilyLoneParentPremiumAwarded) {
        this.FamilyLoneParentPremiumAwarded = FamilyLoneParentPremiumAwarded;
    }

    protected final void setFamilyLoneParentPremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setFamilyLoneParentPremiumAwarded(0);
        } else {
            try {
                setFamilyLoneParentPremiumAwarded(Integer.valueOf(fields[n]));
                if (FamilyLoneParentPremiumAwarded > 1 || FamilyLoneParentPremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("FamilyLoneParentPremiumAwarded=" + fields[n]);
                    System.err.println("FamilyLoneParentPremiumAwarded > 1 || FamilyLoneParentPremiumAwarded < 0");
//                throw new Exception("FamilyLoneParentPremiumAwarded > 1 || FamilyLoneParentPremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setFamilyLoneParentPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the DisabilityPremiumAwarded
     */
    public int getDisabilityPremiumAwarded() {
        return DisabilityPremiumAwarded;
    }

    /**
     * @param DisabilityPremiumAwarded the DisabilityPremiumAwarded to set
     */
    protected final void setDisabilityPremiumAwarded(int DisabilityPremiumAwarded) {
        this.DisabilityPremiumAwarded = DisabilityPremiumAwarded;
    }

    protected final void setDisabilityPremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setDisabilityPremiumAwarded(0);
        } else {
            try {
                setDisabilityPremiumAwarded(Integer.valueOf(fields[n]));
                if (DisabilityPremiumAwarded > 1 || DisabilityPremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("DisabilityPremiumAwarded=" + fields[n]);
                    System.err.println("DisabilityPremiumAwarded > 1 || DisabilityPremiumAwarded < 0");
//                throw new Exception("DisabilityPremiumAwarded > 1 || DisabilityPremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setDisabilityPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the SevereDisabilityPremiumAwarded
     */
    public int getSevereDisabilityPremiumAwarded() {
        return SevereDisabilityPremiumAwarded;
    }

    /**
     * @param SevereDisabilityPremiumAwarded the SevereDisabilityPremiumAwarded
     * to set
     */
    protected final void setSevereDisabilityPremiumAwarded(int SevereDisabilityPremiumAwarded) {
        this.SevereDisabilityPremiumAwarded = SevereDisabilityPremiumAwarded;
    }

    protected final void setSevereDisabilityPremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setSevereDisabilityPremiumAwarded(0);
        } else {
            try {
                setSevereDisabilityPremiumAwarded(Integer.valueOf(fields[n]));
                if (SevereDisabilityPremiumAwarded > 1 || SevereDisabilityPremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("SevereDisabilityPremiumAwarded=" + fields[n]);
                    System.err.println("SevereDisabilityPremiumAwarded > 1 || SevereDisabilityPremiumAwarded < 0");
//                throw new Exception("SevereDisabilityPremiumAwarded > 1 || SevereDisabilityPremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setSevereDisabilityPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the DisabledChildPremiumAwarded
     */
    public int getDisabledChildPremiumAwarded() {
        return DisabledChildPremiumAwarded;
    }

    /**
     * @param DisabledChildPremiumAwarded the DisabledChildPremiumAwarded to set
     */
    protected final void setDisabledChildPremiumAwarded(int DisabledChildPremiumAwarded) {
        this.DisabledChildPremiumAwarded = DisabledChildPremiumAwarded;
    }

    protected final void setDisabledChildPremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setDisabledChildPremiumAwarded(0);
        } else {
            try {
                setDisabledChildPremiumAwarded(Integer.valueOf(fields[n]));
                if (DisabledChildPremiumAwarded > 1 || DisabledChildPremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("DisabledChildPremiumAwarded=" + fields[n]);
                    System.err.println("DisabledChildPremiumAwarded > 1 || DisabiledChildPremiumAwarded < 0");
//                throw new Exception("DisabledChildPremiumAwarded > 1 || DisabiledChildPremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setDisabledChildPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the CarePremiumAwarded
     */
    public int getCarePremiumAwarded() {
        return CarePremiumAwarded;
    }

    /**
     * @param CarePremiumAwarded the CarePremiumAwarded to set
     */
    protected final void setCarePremiumAwarded(int CarePremiumAwarded) {
        this.CarePremiumAwarded = CarePremiumAwarded;
    }

    protected final void setCarePremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setCarePremiumAwarded(0);
        } else {
            try {
                setCarePremiumAwarded(Integer.valueOf(fields[n]));
                if (CarePremiumAwarded > 1 || CarePremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("CarePremiumAwarded=" + fields[n]);
                    System.err.println("CarePremiumAwarded > 1 || CarePremiumAwarded < 0");
//                throw new Exception("CarePremiumAwarded > 1 || CarePremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setCarePremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the EnhancedDisabilityPremiumAwarded
     */
    public int getEnhancedDisabilityPremiumAwarded() {
        return EnhancedDisabilityPremiumAwarded;
    }

    /**
     * @param EnhancedDisabilityPremiumAwarded the
     * EnhancedDisabilityPremiumAwarded to set
     */
    protected final void setEnhancedDisabilityPremiumAwarded(int EnhancedDisabilityPremiumAwarded) {
        this.EnhancedDisabilityPremiumAwarded = EnhancedDisabilityPremiumAwarded;
    }

    protected final void setEnhancedDisabilityPremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setEnhancedDisabilityPremiumAwarded(0);
        } else {
            try {
                setEnhancedDisabilityPremiumAwarded(Integer.valueOf(fields[n]));
                if (EnhancedDisabilityPremiumAwarded > 1 || EnhancedDisabilityPremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("EnhancedDisabilityPremiumAwarded=" + fields[n]);
                    System.err.println("EnhancedDisabilityPremiumAwarded > 1 || EnhancedDisabilityPremiumAwarded < 0");
//                throw new Exception("EnhancedDisabilityPremiumAwarded > 1 || EnhancedDisabilityPremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setEnhancedDisabilityPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the BereavementPremiumAwarded
     */
    public int getBereavementPremiumAwarded() {
        return BereavementPremiumAwarded;
    }

    /**
     * @param BereavementPremiumAwarded the BereavementPremiumAwarded to set
     */
    protected final void setBereavementPremiumAwarded(int BereavementPremiumAwarded) {
        this.BereavementPremiumAwarded = BereavementPremiumAwarded;
    }

    protected final void setBereavementPremiumAwarded(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setBereavementPremiumAwarded(0);
        } else {
            try {
                setBereavementPremiumAwarded(Integer.valueOf(fields[n]));
                if (BereavementPremiumAwarded > 1 || BereavementPremiumAwarded < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("BereavementPremiumAwarded=" + fields[n]);
                    System.err.println("BereavementPremiumAwarded > 1 || BereavementPremiumAwarded < 0");
//                throw new Exception("BereavementPremiumAwarded > 1 || BereavementPremiumAwarded < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setBereavementPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the PartnerFlag
     */
    public int getPartnerFlag() {
        return PartnerFlag;
    }

    /**
     * @param PartnerFlag the PartnerFlag to set
     */
    protected final void setPartnerFlag(int PartnerFlag) {
        this.PartnerFlag = PartnerFlag;
    }

    protected final void setPartnerFlag(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setPartnerFlag(0);
        } else {
            try {
                setPartnerFlag(Integer.valueOf(fields[n]));
                if (PartnerFlag > 2 || PartnerFlag < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("PartnerFlag=" + fields[n]);
                    System.err.println("PartnerFlag > 2 || PartnerFlag < 0");
//                throw new Exception("PartnerFlag > 2 || PartnerFlag < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setBereavementPremiumAwarded(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the PartnersStartDate
     */
    public String getPartnersStartDate() {
        return PartnersStartDate;
    }

    /**
     * @param PartnersStartDate the PartnersStartDate to set
     */
    protected final void setPartnersStartDate(String PartnersStartDate) {
        this.PartnersStartDate = PartnersStartDate;
    }

    /**
     * @return the PartnersEndDate
     */
    public String getPartnersEndDate() {
        return PartnersEndDate;
    }

    /**
     * @param PartnersEndDate the PartnersEndDate to set
     */
    protected final void setPartnersEndDate(String PartnersEndDate) {
        this.PartnersEndDate = PartnersEndDate;
    }

    /**
     * @return the PartnersStudentIndicator
     */
    public String getPartnersStudentIndicator() {
        return PartnersStudentIndicator;
    }

    /**
     * @param PartnersStudentIndicator the PartnersStudentIndicator to set
     */
    protected final void setPartnersStudentIndicator(String PartnersStudentIndicator) {
        this.PartnersStudentIndicator = PartnersStudentIndicator;
    }

    /**
     * @return the PartnersAssessedIncomeFigure
     */
    public int getPartnersAssessedIncomeFigure() {
        return PartnersAssessedIncomeFigure;
    }

    /**
     * @param PartnersAssessedIncomeFigure the PartnersAssessedIncomeFigure to
     * set
     */
    protected final void setPartnersAssessedIncomeFigure(int PartnersAssessedIncomeFigure) {
        this.PartnersAssessedIncomeFigure = PartnersAssessedIncomeFigure;
    }

    /**
     * @return the PartnersAdjustedAssessedIncomeFigure
     */
    public int getPartnersAdjustedAssessedIncomeFigure() {
        return PartnersAdjustedAssessedIncomeFigure;
    }

    /**
     * @param PartnersAdjustedAssessedIncomeFigure the
     * PartnersAdjustedAssessedIncomeFigure to set
     */
    protected final void setPartnersAdjustedAssessedIncomeFigure(int PartnersAdjustedAssessedIncomeFigure) {
        this.PartnersAdjustedAssessedIncomeFigure = PartnersAdjustedAssessedIncomeFigure;
    }

    /**
     * @return the PartnersGrossWeeklyIncomeFromEmployment
     */
    public int getPartnersGrossWeeklyIncomeFromEmployment() {
        return PartnersGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @param PartnersGrossWeeklyIncomeFromEmployment the
     * PartnersGrossWeeklyIncomeFromEmployment to set
     */
    protected final void setPartnersGrossWeeklyIncomeFromEmployment(int PartnersGrossWeeklyIncomeFromEmployment) {
        this.PartnersGrossWeeklyIncomeFromEmployment = PartnersGrossWeeklyIncomeFromEmployment;
    }

    /**
     * @return the PartnersNetWeeklyIncomeFromEmployment
     */
    public int getPartnersNetWeeklyIncomeFromEmployment() {
        return PartnersNetWeeklyIncomeFromEmployment;
    }

    /**
     * @param PartnersNetWeeklyIncomeFromEmployment the
     * PartnersNetWeeklyIncomeFromEmployment to set
     */
    protected final void setPartnersNetWeeklyIncomeFromEmployment(int PartnersNetWeeklyIncomeFromEmployment) {
        this.PartnersNetWeeklyIncomeFromEmployment = PartnersNetWeeklyIncomeFromEmployment;
    }

    /**
     * @return the PartnersGrossWeeklyIncomeFromSelfEmployment
     */
    public int getPartnersGrossWeeklyIncomeFromSelfEmployment() {
        return PartnersGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param PartnersGrossWeeklyIncomeFromSelfEmployment the
     * PartnersGrossWeeklyIncomeFromSelfEmployment to set
     */
    protected final void setPartnersGrossWeeklyIncomeFromSelfEmployment(int PartnersGrossWeeklyIncomeFromSelfEmployment) {
        this.PartnersGrossWeeklyIncomeFromSelfEmployment = PartnersGrossWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the PartnersNetWeeklyIncomeFromSelfEmployment
     */
    public int getPartnersNetWeeklyIncomeFromSelfEmployment() {
        return PartnersNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @param PartnersNetWeeklyIncomeFromSelfEmployment the
     * PartnersNetWeeklyIncomeFromSelfEmployment to set
     */
    protected final void setPartnersNetWeeklyIncomeFromSelfEmployment(int PartnersNetWeeklyIncomeFromSelfEmployment) {
        this.PartnersNetWeeklyIncomeFromSelfEmployment = PartnersNetWeeklyIncomeFromSelfEmployment;
    }

    /**
     * @return the PartnersTotalAmountOfEarningsDisregarded
     */
    public int getPartnersTotalAmountOfEarningsDisregarded() {
        return PartnersTotalAmountOfEarningsDisregarded;
    }

    /**
     * @param PartnersTotalAmountOfEarningsDisregarded the
     * PartnersTotalAmountOfEarningsDisregarded to set
     */
    protected final void setPartnersTotalAmountOfEarningsDisregarded(int PartnersTotalAmountOfEarningsDisregarded) {
        this.PartnersTotalAmountOfEarningsDisregarded = PartnersTotalAmountOfEarningsDisregarded;
    }

    /**
     * @return the
     * PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     */
    public int getPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded() {
        return PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @param PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded
     * the PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded to
     * set
     */
    protected final void setPartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded(int PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded) {
        this.PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded = PartnersIfChildcareDisregardAllowedWeeklyAmountBeingDisregarded;
    }

    /**
     * @return the PartnersIncomeFromAttendanceAllowance
     */
    public int getPartnersIncomeFromAttendanceAllowance() {
        return PartnersIncomeFromAttendanceAllowance;
    }

    /**
     * @param PartnersIncomeFromAttendanceAllowance the
     * PartnersIncomeFromAttendanceAllowance to set
     */
    protected final void setPartnersIncomeFromAttendanceAllowance(int PartnersIncomeFromAttendanceAllowance) {
        this.PartnersIncomeFromAttendanceAllowance = PartnersIncomeFromAttendanceAllowance;
    }

    /**
     * @return the PartnersIncomeFromBusinessStartUpAllowance
     */
    public int getPartnersIncomeFromBusinessStartUpAllowance() {
        return PartnersIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @param PartnersIncomeFromBusinessStartUpAllowance the
     * PartnersIncomeFromBusinessStartUpAllowance to set
     */
    protected final void setPartnersIncomeFromBusinessStartUpAllowance(int PartnersIncomeFromBusinessStartUpAllowance) {
        this.PartnersIncomeFromBusinessStartUpAllowance = PartnersIncomeFromBusinessStartUpAllowance;
    }

    /**
     * @return the PartnersIncomeFromChildBenefit
     */
    public int getPartnersIncomeFromChildBenefit() {
        return PartnersIncomeFromChildBenefit;
    }

    /**
     * @param PartnersIncomeFromChildBenefit the PartnersIncomeFromChildBenefit
     * to set
     */
    protected final void setPartnersIncomeFromChildBenefit(int PartnersIncomeFromChildBenefit) {
        this.PartnersIncomeFromChildBenefit = PartnersIncomeFromChildBenefit;
    }

    /**
     * @return the PartnersIncomeFromPersonalPension
     */
    public int getPartnersIncomeFromPersonalPension() {
        return PartnersIncomeFromPersonalPension;
    }

    /**
     * @param PartnersIncomeFromPersonalPension the
     * PartnersIncomeFromPersonalPension to set
     */
    protected final void setPartnersIncomeFromPersonalPension(int PartnersIncomeFromPersonalPension) {
        this.PartnersIncomeFromPersonalPension = PartnersIncomeFromPersonalPension;
    }

    /**
     * @return the PartnersIncomeFromSevereDisabilityAllowance
     */
    public int getPartnersIncomeFromSevereDisabilityAllowance() {
        return PartnersIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @param PartnersIncomeFromSevereDisabilityAllowance the
     * PartnersIncomeFromSevereDisabilityAllowance to set
     */
    protected final void setPartnersIncomeFromSevereDisabilityAllowance(int PartnersIncomeFromSevereDisabilityAllowance) {
        this.PartnersIncomeFromSevereDisabilityAllowance = PartnersIncomeFromSevereDisabilityAllowance;
    }

    /**
     * @return the PartnersIncomeFromMaternityAllowance
     */
    public int getPartnersIncomeFromMaternityAllowance() {
        return PartnersIncomeFromMaternityAllowance;
    }

    /**
     * @param PartnersIncomeFromMaternityAllowance the
     * PartnersIncomeFromMaternityAllowance to set
     */
    protected final void setPartnersIncomeFromMaternityAllowance(int PartnersIncomeFromMaternityAllowance) {
        this.PartnersIncomeFromMaternityAllowance = PartnersIncomeFromMaternityAllowance;
    }

    /**
     * @return the PartnersIncomeFromContributionBasedJobSeekersAllowance
     */
    public int getPartnersIncomeFromContributionBasedJobSeekersAllowance() {
        return PartnersIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @param PartnersIncomeFromContributionBasedJobSeekersAllowance the
     * PartnersIncomeFromContributionBasedJobSeekersAllowance to set
     */
    protected final void setPartnersIncomeFromContributionBasedJobSeekersAllowance(int PartnersIncomeFromContributionBasedJobSeekersAllowance) {
        this.PartnersIncomeFromContributionBasedJobSeekersAllowance = PartnersIncomeFromContributionBasedJobSeekersAllowance;
    }

    /**
     * @return the PartnersIncomeFromStudentGrantLoan
     */
    public int getPartnersIncomeFromStudentGrantLoan() {
        return PartnersIncomeFromStudentGrantLoan;
    }

    /**
     * @param PartnersIncomeFromStudentGrantLoan the
     * PartnersIncomeFromStudentGrantLoan to set
     */
    protected final void setPartnersIncomeFromStudentGrantLoan(int PartnersIncomeFromStudentGrantLoan) {
        this.PartnersIncomeFromStudentGrantLoan = PartnersIncomeFromStudentGrantLoan;
    }

    /**
     * @return the PartnersIncomeFromSubTenants
     */
    public int getPartnersIncomeFromSubTenants() {
        return PartnersIncomeFromSubTenants;
    }

    /**
     * @param PartnersIncomeFromSubTenants the PartnersIncomeFromSubTenants to
     * set
     */
    protected final void setPartnersIncomeFromSubTenants(int PartnersIncomeFromSubTenants) {
        this.PartnersIncomeFromSubTenants = PartnersIncomeFromSubTenants;
    }

    /**
     * @return the PartnersIncomeFromBoarders
     */
    public int getPartnersIncomeFromBoarders() {
        return PartnersIncomeFromBoarders;
    }

    /**
     * @param PartnersIncomeFromBoarders the PartnersIncomeFromBoarders to set
     */
    protected final void setPartnersIncomeFromBoarders(int PartnersIncomeFromBoarders) {
        this.PartnersIncomeFromBoarders = PartnersIncomeFromBoarders;
    }

    /**
     * @return the PartnersIncomeFromTrainingForWorkCommunityAction
     */
    public int getPartnersIncomeFromTrainingForWorkCommunityAction() {
        return PartnersIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @param PartnersIncomeFromTrainingForWorkCommunityAction the
     * PartnersIncomeFromTrainingForWorkCommunityAction to set
     */
    protected final void setPartnersIncomeFromTrainingForWorkCommunityAction(int PartnersIncomeFromTrainingForWorkCommunityAction) {
        this.PartnersIncomeFromTrainingForWorkCommunityAction = PartnersIncomeFromTrainingForWorkCommunityAction;
    }

    /**
     * @return the PartnersIncomeFromIncapacityBenefitShortTermLower
     */
    public int getPartnersIncomeFromIncapacityBenefitShortTermLower() {
        return PartnersIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @param PartnersIncomeFromIncapacityBenefitShortTermLower the
     * PartnersIncomeFromIncapacityBenefitShortTermLower to set
     */
    protected final void setPartnersIncomeFromIncapacityBenefitShortTermLower(int PartnersIncomeFromIncapacityBenefitShortTermLower) {
        this.PartnersIncomeFromIncapacityBenefitShortTermLower = PartnersIncomeFromIncapacityBenefitShortTermLower;
    }

    /**
     * @return the PartnersIncomeFromIncapacityBenefitShortTermHigher
     */
    public int getPartnersIncomeFromIncapacityBenefitShortTermHigher() {
        return PartnersIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @param PartnersIncomeFromIncapacityBenefitShortTermHigher the
     * PartnersIncomeFromIncapacityBenefitShortTermHigher to set
     */
    protected final void setPartnersIncomeFromIncapacityBenefitShortTermHigher(int PartnersIncomeFromIncapacityBenefitShortTermHigher) {
        this.PartnersIncomeFromIncapacityBenefitShortTermHigher = PartnersIncomeFromIncapacityBenefitShortTermHigher;
    }

    /**
     * @return the PartnersIncomeFromIncapacityBenefitLongTerm
     */
    public int getPartnersIncomeFromIncapacityBenefitLongTerm() {
        return PartnersIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @param PartnersIncomeFromIncapacityBenefitLongTerm the
     * PartnersIncomeFromIncapacityBenefitLongTerm to set
     */
    protected final void setPartnersIncomeFromIncapacityBenefitLongTerm(int PartnersIncomeFromIncapacityBenefitLongTerm) {
        this.PartnersIncomeFromIncapacityBenefitLongTerm = PartnersIncomeFromIncapacityBenefitLongTerm;
    }

    /**
     * @return the PartnersIncomeFromNewDeal50PlusEmploymentCredit
     */
    public int getPartnersIncomeFromNewDeal50PlusEmploymentCredit() {
        return PartnersIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @param PartnersIncomeFromNewDeal50PlusEmploymentCredit the
     * PartnersIncomeFromNewDeal50PlusEmploymentCredit to set
     */
    protected final void setPartnersIncomeFromNewDeal50PlusEmploymentCredit(int PartnersIncomeFromNewDeal50PlusEmploymentCredit) {
        this.PartnersIncomeFromNewDeal50PlusEmploymentCredit = PartnersIncomeFromNewDeal50PlusEmploymentCredit;
    }

    /**
     * @return the PartnersIncomeFromNewTaxCredits
     */
    public int getPartnersIncomeFromNewTaxCredits() {
        return PartnersIncomeFromNewTaxCredits;
    }

    /**
     * @param PartnersIncomeFromNewTaxCredits the
     * PartnersIncomeFromNewTaxCredits to set
     */
    protected final void setPartnersIncomeFromNewTaxCredits(int PartnersIncomeFromNewTaxCredits) {
        this.PartnersIncomeFromNewTaxCredits = PartnersIncomeFromNewTaxCredits;
    }

    /**
     * @return the PartnersIncomeFromDisabilityLivingAllowanceCareComponent
     */
    public int getPartnersIncomeFromDisabilityLivingAllowanceCareComponent() {
        return PartnersIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @param PartnersIncomeFromDisabilityLivingAllowanceCareComponent the
     * PartnersIncomeFromDisabilityLivingAllowanceCareComponent to set
     */
    protected final void setPartnersIncomeFromDisabilityLivingAllowanceCareComponent(int PartnersIncomeFromDisabilityLivingAllowanceCareComponent) {
        this.PartnersIncomeFromDisabilityLivingAllowanceCareComponent = PartnersIncomeFromDisabilityLivingAllowanceCareComponent;
    }

    /**
     * @return the PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent
     */
    public int getPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent() {
        return PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @param PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent the
     * PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent to set
     */
    protected final void setPartnersIncomeFromDisabilityLivingAllowanceMobilityComponent(int PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent) {
        this.PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent = PartnersIncomeFromDisabilityLivingAllowanceMobilityComponent;
    }

    /**
     * @return the PartnersIncomeFromGovernemntTraining
     */
    public int getPartnersIncomeFromGovernmentTraining() {
        return PartnersIncomeFromGovernemntTraining;
    }

    /**
     * @param PartnersIncomeFromGovernemntTraining the
     * PartnersIncomeFromGovernemntTraining to set
     */
    protected final void setPartnersIncomeFromGovernemntTraining(int PartnersIncomeFromGovernemntTraining) {
        this.PartnersIncomeFromGovernemntTraining = PartnersIncomeFromGovernemntTraining;
    }

    /**
     * @return the PartnersIncomeFromIndustrialInjuriesDisablementBenefit
     */
    public int getPartnersIncomeFromIndustrialInjuriesDisablementBenefit() {
        return PartnersIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @param PartnersIncomeFromIndustrialInjuriesDisablementBenefit the
     * PartnersIncomeFromIndustrialInjuriesDisablementBenefit to set
     */
    protected final void setPartnersIncomeFromIndustrialInjuriesDisablementBenefit(int PartnersIncomeFromIndustrialInjuriesDisablementBenefit) {
        this.PartnersIncomeFromIndustrialInjuriesDisablementBenefit = PartnersIncomeFromIndustrialInjuriesDisablementBenefit;
    }

    /**
     * @return the PartnersIncomeFromCarersAllowance
     */
    public int getPartnersIncomeFromCarersAllowance() {
        return PartnersIncomeFromCarersAllowance;
    }

    /**
     * @param PartnersIncomeFromCarersAllowance the
     * PartnersIncomeFromCarersAllowance to set
     */
    protected final void setPartnersIncomeFromCarersAllowance(int PartnersIncomeFromCarersAllowance) {
        this.PartnersIncomeFromCarersAllowance = PartnersIncomeFromCarersAllowance;
    }

    /**
     * @return the PartnersIncomeFromStatuatorySickPay
     */
    public int getPartnersIncomeFromStatutorySickPay() {
        return PartnersIncomeFromStatuatorySickPay;
    }

    /**
     * @param PartnersIncomeFromStatuatorySickPay the
     * PartnersIncomeFromStatuatorySickPay to set
     */
    protected final void setPartnersIncomeFromStatuatorySickPay(int PartnersIncomeFromStatuatorySickPay) {
        this.PartnersIncomeFromStatuatorySickPay = PartnersIncomeFromStatuatorySickPay;
    }

    /**
     * @return the PartnersIncomeFromStatutoryMaternityPaternityPay
     */
    public int getPartnersIncomeFromStatutoryMaternityPaternityPay() {
        return PartnersIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @param PartnersIncomeFromStatutoryMaternityPaternityPay the
     * PartnersIncomeFromStatutoryMaternityPaternityPay to set
     */
    protected final void setPartnersIncomeFromStatutoryMaternityPaternityPay(int PartnersIncomeFromStatutoryMaternityPaternityPay) {
        this.PartnersIncomeFromStatutoryMaternityPaternityPay = PartnersIncomeFromStatutoryMaternityPaternityPay;
    }

    /**
     * @return the
     * PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     */
    public int getPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc() {
        return PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @param
     * PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * the
     * PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc
     * to set
     */
    protected final void setPartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc(int PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc) {
        this.PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc = PartnersIncomeFromStateRetirementPensionIncludingSERPsGraduatedPensionetc;
    }

    /**
     * @return the PartnersIncomeFromWarDisablementPensionArmedForcesGIP
     */
    public int getPartnersIncomeFromWarDisablementPensionArmedForcesGIP() {
        return PartnersIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @param PartnersIncomeFromWarDisablementPensionArmedForcesGIP the
     * PartnersIncomeFromWarDisablementPensionArmedForcesGIP to set
     */
    protected final void setPartnersIncomeFromWarDisablementPensionArmedForcesGIP(int PartnersIncomeFromWarDisablementPensionArmedForcesGIP) {
        this.PartnersIncomeFromWarDisablementPensionArmedForcesGIP = PartnersIncomeFromWarDisablementPensionArmedForcesGIP;
    }

    /**
     * @return the PartnersIncomeFromWarMobilitySupplement
     */
    public int getPartnersIncomeFromWarMobilitySupplement() {
        return PartnersIncomeFromWarMobilitySupplement;
    }

    /**
     * @param PartnersIncomeFromWarMobilitySupplement the
     * PartnersIncomeFromWarMobilitySupplement to set
     */
    protected final void setPartnersIncomeFromWarMobilitySupplement(int PartnersIncomeFromWarMobilitySupplement) {
        this.PartnersIncomeFromWarMobilitySupplement = PartnersIncomeFromWarMobilitySupplement;
    }

    /**
     * @return the PartnersIncomeFromWidowsWidowersPension
     */
    public int getPartnersIncomeFromWarWidowsWidowersPension() {
        return PartnersIncomeFromWidowsWidowersPension;
    }

    /**
     * @param PartnersIncomeFromWidowsWidowersPension the
     * PartnersIncomeFromWidowsWidowersPension to set
     */
    protected final void setPartnersIncomeFromWidowsWidowersPension(int PartnersIncomeFromWidowsWidowersPension) {
        this.PartnersIncomeFromWidowsWidowersPension = PartnersIncomeFromWidowsWidowersPension;
    }

    /**
     * @return the PartnersIncomeFromBereavementAllowance
     */
    public int getPartnersIncomeFromBereavementAllowance() {
        return PartnersIncomeFromBereavementAllowance;
    }

    /**
     * @param PartnersIncomeFromBereavementAllowance the
     * PartnersIncomeFromBereavementAllowance to set
     */
    protected final void setPartnersIncomeFromBereavementAllowance(int PartnersIncomeFromBereavementAllowance) {
        this.PartnersIncomeFromBereavementAllowance = PartnersIncomeFromBereavementAllowance;
    }

    /**
     * @return the PartnersIncomeFromWidowedParentsAllowance
     */
    public int getPartnersIncomeFromWidowedParentsAllowance() {
        return PartnersIncomeFromWidowedParentsAllowance;
    }

    /**
     * @param PartnersIncomeFromWidowedParentsAllowance the
     * PartnersIncomeFromWidowedParentsAllowance to set
     */
    protected final void setPartnersIncomeFromWidowedParentsAllowance(int PartnersIncomeFromWidowedParentsAllowance) {
        this.PartnersIncomeFromWidowedParentsAllowance = PartnersIncomeFromWidowedParentsAllowance;
    }

    /**
     * @return the PartnersIncomeFromYouthTrainingScheme
     */
    public int getPartnersIncomeFromYouthTrainingScheme() {
        return PartnersIncomeFromYouthTrainingScheme;
    }

    /**
     * @param PartnersIncomeFromYouthTrainingScheme the
     * PartnersIncomeFromYouthTrainingScheme to set
     */
    protected final void setPartnersIncomeFromYouthTrainingScheme(int PartnersIncomeFromYouthTrainingScheme) {
        this.PartnersIncomeFromYouthTrainingScheme = PartnersIncomeFromYouthTrainingScheme;
    }

    /**
     * @return the PartnersOtherIncome
     */
    public int getPartnersOtherIncome() {
        return PartnersOtherIncome;
    }

    /**
     * @param PartnersOtherIncome the PartnersOtherIncome to set
     */
    protected final void setPartnersOtherIncome(int PartnersOtherIncome) {
        this.PartnersOtherIncome = PartnersOtherIncome;
    }

    /**
     * @return the PartnersTotalAmountOfIncomeDisregarded
     */
    public int getPartnersTotalAmountOfIncomeDisregarded() {
        return PartnersTotalAmountOfIncomeDisregarded;
    }

    /**
     * @param PartnersTotalAmountOfIncomeDisregarded the
     * PartnersTotalAmountOfIncomeDisregarded to set
     */
    protected final void setPartnersTotalAmountOfIncomeDisregarded(int PartnersTotalAmountOfIncomeDisregarded) {
        this.PartnersTotalAmountOfIncomeDisregarded = PartnersTotalAmountOfIncomeDisregarded;
    }

    /**
     * @return the RentAllowanceMethodOfPayment
     */
    public int getRentAllowanceMethodOfPayment() {
        return RentAllowanceMethodOfPayment;
    }

    /**
     * @param RentAllowanceMethodOfPayment the RentAllowanceMethodOfPayment to
     * set
     */
    protected final void setRentAllowanceMethodOfPayment(int RentAllowanceMethodOfPayment) {
        this.RentAllowanceMethodOfPayment = RentAllowanceMethodOfPayment;
    }

    protected final void setRentAllowanceMethodOfPayment(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setRentAllowanceMethodOfPayment(0);
        } else {
            try {
                setRentAllowanceMethodOfPayment(Integer.valueOf(fields[n]));
                if (RentAllowanceMethodOfPayment > 99 || RentAllowanceMethodOfPayment < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("RentAllowanceMethodOfPayment=" + fields[n]);
                    System.err.println("RentAllowanceMethodOfPayment > 99 || RentAllowanceMethodOfPayment < 0");
//                    throw new Exception("RentAllowanceMethodOfPayment > 99 || RentAllowanceMethodOfPayment < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setRentAllowanceMethodOfPayment(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the RentAllowancePaymentDestination
     */
    public int getRentAllowancePaymentDestination() {
        return RentAllowancePaymentDestination;
    }

    /**
     * @param RentAllowancePaymentDestination the
     * RentAllowancePaymentDestination to set
     */
    protected final void setRentAllowancePaymentDestination(int RentAllowancePaymentDestination) {
        this.RentAllowancePaymentDestination = RentAllowancePaymentDestination;
    }

    protected final void setRentAllowancePaymentDestination(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setRentAllowancePaymentDestination(0);
        } else {
            try {
                setRentAllowancePaymentDestination(Integer.valueOf(fields[n]));
                if (RentAllowancePaymentDestination > 99 || RentAllowancePaymentDestination < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("RentAllowancePaymentDestination=" + fields[n]);
                    System.err.println("RentAllowancePaymentDestination > 99 || RentAllowancePaymentDestination < 0");
//                throw new Exception("RentAllowancePaymentDestination > 99 || RentAllowancePaymentDestination < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setRentAllowancePaymentDestination(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the ContractualRentAmount
     */
    public int getContractualRentAmount() {
        return ContractualRentAmount;
    }

    /**
     * @param ContractualRentAmount the ContractualRentAmount to set
     */
    protected final void setContractualRentAmount(int ContractualRentAmount) {
        this.ContractualRentAmount = ContractualRentAmount;
    }

    /**
     * @return the TimePeriodContractualRentFigureCovers
     */
    public int getTimePeriodContractualRentFigureCovers() {
        return TimePeriodContractualRentFigureCovers;
    }

    /**
     * @param TimePeriodContractualRentFigureCovers the
     * TimePeriodContractualRentFigureCovers to set
     */
    protected final void setTimePeriodContractualRentFigureCovers(int TimePeriodContractualRentFigureCovers) {
        this.TimePeriodContractualRentFigureCovers = TimePeriodContractualRentFigureCovers;
    }

    protected final int setTimePeriodContractualRentFigureCovers(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setTimePeriodContractualRentFigureCovers(0);
        } else {
            try {
                setTimePeriodContractualRentFigureCovers(Integer.valueOf(fields[n]));
                if (TimePeriodContractualRentFigureCovers != 99
                        && (TimePeriodContractualRentFigureCovers > 9
                        || TimePeriodContractualRentFigureCovers < 0)) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("TimePeriodContractualRentFigureCovers=" + fields[n]);
                    System.err.println("TimePeriodContractualRentFigureCovers != 99 "
                            + "&& (TimePeriodContractualRentFigureCovers > 9 || "
                            + "TimePeriodContractualRentFigureCovers < 0)");
//                throw new Exception("TimePeriodContractualRentFigureCovers != 99 "
//                        + "&& (TimePeriodContractualRentFigureCovers > 9 || "
//                        + "TimePeriodContractualRentFigureCovers < 0)");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setTimePeriodContractualRentFigureCovers(int,String[])");
//                throw e;
            }
        }
        return n;
    }

    /**
     * @return the ClaimantsIncomeFromPensionCreditSavingsCredit
     */
    public int getClaimantsIncomeFromPensionCreditSavingsCredit() {
        return ClaimantsIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @param ClaimantsIncomeFromPensionCreditSavingsCredit the
     * ClaimantsIncomeFromPensionCreditSavingsCredit to set
     */
    protected final void setClaimantsIncomeFromPensionCreditSavingsCredit(
            int ClaimantsIncomeFromPensionCreditSavingsCredit) {
        this.ClaimantsIncomeFromPensionCreditSavingsCredit = ClaimantsIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @return the PartnersIncomeFromPensionCreditSavingsCredit
     */
    public int getPartnersIncomeFromPensionCreditSavingsCredit() {
        return PartnersIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @param PartnersIncomeFromPensionCreditSavingsCredit the
     * PartnersIncomeFromPensionCreditSavingsCredit to set
     */
    protected final void setPartnersIncomeFromPensionCreditSavingsCredit(int PartnersIncomeFromPensionCreditSavingsCredit) {
        this.PartnersIncomeFromPensionCreditSavingsCredit = PartnersIncomeFromPensionCreditSavingsCredit;
    }

    /**
     * @return the ClaimantsIncomeFromMaintenancePayments
     */
    public int getClaimantsIncomeFromMaintenancePayments() {
        return ClaimantsIncomeFromMaintenancePayments;
    }

    /**
     * @param ClaimantsIncomeFromMaintenancePayments the
     * ClaimantsIncomeFromMaintenancePayments to set
     */
    protected final void setClaimantsIncomeFromMaintenancePayments(int ClaimantsIncomeFromMaintenancePayments) {
        this.ClaimantsIncomeFromMaintenancePayments = ClaimantsIncomeFromMaintenancePayments;
    }

    /**
     * @return the PartnersIncomeFromMaintenancePayments
     */
    public int getPartnersIncomeFromMaintenancePayments() {
        return PartnersIncomeFromMaintenancePayments;
    }

    /**
     * @param PartnersIncomeFromMaintenancePayments the
     * PartnersIncomeFromMaintenancePayments to set
     */
    protected final void setPartnersIncomeFromMaintenancePayments(int PartnersIncomeFromMaintenancePayments) {
        this.PartnersIncomeFromMaintenancePayments = PartnersIncomeFromMaintenancePayments;
    }

    /**
     * @return the ClaimantsIncomeFromOccupationalPension
     */
    public int getClaimantsIncomeFromOccupationalPension() {
        return ClaimantsIncomeFromOccupationalPension;
    }

    /**
     * @param ClaimantsIncomeFromOccupationalPension the
     * ClaimantsIncomeFromOccupationalPension to set
     */
    protected final void setClaimantsIncomeFromOccupationalPension(int ClaimantsIncomeFromOccupationalPension) {
        this.ClaimantsIncomeFromOccupationalPension = ClaimantsIncomeFromOccupationalPension;
    }

    /**
     * @return the PartnersIncomeFromOccupationalPension
     */
    public int getPartnersIncomeFromOccupationalPension() {
        return PartnersIncomeFromOccupationalPension;
    }

    /**
     * @param PartnersIncomeFromOccupationalPension the
     * PartnersIncomeFromOccupationalPension to set
     */
    protected final void setPartnersIncomeFromOccupationalPension(int PartnersIncomeFromOccupationalPension) {
        this.PartnersIncomeFromOccupationalPension = PartnersIncomeFromOccupationalPension;
    }

    /**
     * @return the ClaimantsIncomeFromWidowsBenefit
     */
    public int getClaimantsIncomeFromWidowsBenefit() {
        return ClaimantsIncomeFromWidowsBenefit;
    }

    /**
     * @param ClaimantsIncomeFromWidowsBenefit the
     * ClaimantsIncomeFromWidowsBenefit to set
     */
    protected final void setClaimantsIncomeFromWidowsBenefit(int ClaimantsIncomeFromWidowsBenefit) {
        this.ClaimantsIncomeFromWidowsBenefit = ClaimantsIncomeFromWidowsBenefit;
    }

    /**
     * @return the PartnersIncomeFromWidowsBenefit
     */
    public int getPartnersIncomeFromWidowsBenefit() {
        return PartnersIncomeFromWidowsBenefit;
    }

    /**
     * @param PartnersIncomeFromWidowsBenefit the
     * PartnersIncomeFromWidowsBenefit to set
     */
    protected final void setPartnersIncomeFromWidowsBenefit(int PartnersIncomeFromWidowsBenefit) {
        this.PartnersIncomeFromWidowsBenefit = PartnersIncomeFromWidowsBenefit;
    }

    /**
     * @return the TotalNumberOfRooms
     */
    public int getTotalNumberOfRooms() {
        return TotalNumberOfRooms;
    }

    /**
     * @param TotalNumberOfRooms the TotalNumberOfRooms to set
     */
    protected final void setTotalNumberOfRooms(int TotalNumberOfRooms) {
        this.TotalNumberOfRooms = TotalNumberOfRooms;
    }

    /**
     * @return the NonSelfContainedAccomodation
     */
    public int getNonSelfContainedAccomodation() {
        return NonSelfContainedAccomodation;
    }

    /**
     * @param NonSelfContainedAccomodation the NonSelfContainedAccomodation to
     * set
     */
    protected final void setNonSelfContainedAccomodation(int NonSelfContainedAccomodation) {
        this.NonSelfContainedAccomodation = NonSelfContainedAccomodation;
    }

    protected final void setNonSelfContainedAccomodation(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setNonSelfContainedAccomodation(0);
        } else {
            try {
                setNonSelfContainedAccomodation(Integer.valueOf(fields[n]));
                if (NonSelfContainedAccomodation > 1 || NonSelfContainedAccomodation < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("NonSelfContainedAccomodation=" + fields[n]);
                    System.err.println("NonSelfContainedAccomodation > 1 || NonSelfContainedAccomodation < 0");
//                throw new Exception("NonSelfContainedAccomodation > 1 || NonSelfContainedAccomodation < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setNonSelfContainedAccomodation(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the TypeOfLHANumberOfRoomsEntitedTo
     */
    public int getTypeOfLHANumberOfRoomsEntitedTo() {
        return TypeOfLHANumberOfRoomsEntitedTo;
    }

    /**
     * @param TypeOfLHANumberOfRoomsEntitedTo the
     * TypeOfLHANumberOfRoomsEntitedTo to set
     */
    protected final void setTypeOfLHANumberOfRoomsEntitedTo(int TypeOfLHANumberOfRoomsEntitedTo) {
        this.TypeOfLHANumberOfRoomsEntitedTo = TypeOfLHANumberOfRoomsEntitedTo;
    }

    /**
     * @return the TransitionalProtectionFromNationalRolloutOfLHA
     */
    public int getTransitionalProtectionFromNationalRolloutOfLHA() {
        return TransitionalProtectionFromNationalRolloutOfLHA;
    }

    /**
     * @param TransitionalProtectionFromNationalRolloutOfLHA the
     * TransitionalProtectionFromNationalRolloutOfLHA to set
     */
    protected final void setTransitionalProtectionFromNationalRolloutOfLHA(int TransitionalProtectionFromNationalRolloutOfLHA) {
        this.TransitionalProtectionFromNationalRolloutOfLHA = TransitionalProtectionFromNationalRolloutOfLHA;
    }

    protected final void setTransitionalProtectionFromNationalRolloutOfLHA(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setTransitionalProtectionFromNationalRolloutOfLHA(0);
        } else {
            try {
                setTransitionalProtectionFromNationalRolloutOfLHA(Integer.valueOf(fields[n]));
                if (TransitionalProtectionFromNationalRolloutOfLHA > 1 || TransitionalProtectionFromNationalRolloutOfLHA < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("TransitionalProtectionFromNationalRolloutOfLHA=" + fields[n]);
                    System.err.println("TransitionalProtectionFromNationalRolloutOfLHA > 1 || TransitionalProtectionFromNationalRolloutOfLHA < 0");
//                throw new Exception("TransitionalProtectionFromNationalRolloutOfLHA > 1 || TransitionalProtectionFromNationalRolloutOfLHA < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setTransitionalProtectionFromNationalRolloutOfLHA(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the Locality
     */
    public String getLocality() {
        return Locality;
    }

    /**
     * @param Locality the Locality to set
     */
    protected final void setLocality(String Locality) {
        this.Locality = Locality;
    }

    /**
     * @return the ValueOfLHA
     */
    public int getValueOfLHA() {
        return ValueOfLHA;
    }

    /**
     * @param ValueOfLHA the ValueOfLHA to set
     */
    protected final void setValueOfLHA(int ValueOfLHA) {
        this.ValueOfLHA = ValueOfLHA;
    }

    /**
     * @return the PartnersGender
     */
    public String getPartnersGender() {
        return PartnersGender;
    }

    /**
     * @param PartnersGender the PartnersGender to set
     */
    protected final void setPartnersGender(String PartnersGender) {
        this.PartnersGender = PartnersGender;
    }

    /**
     * @return the
     * DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT
     */
    public String getDateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT() {
        return DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT;
    }

    /**
     * @param
     * DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT
     * the
     * DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT
     * to set
     */
    protected final void setDateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT(String DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT) {
        this.DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT = DateOfTransferFromLocalAuthorityLandlordToHousingAssociationRSLIfSubjectToStockTRansferOrLSVT;
    }

    /**
     * @return the PartnersTotalCapital
     */
    public int getPartnersTotalCapital() {
        return PartnersTotalCapital;
    }

    /**
     * @param PartnersTotalCapital the PartnersTotalCapital to set
     */
    protected final void setPartnersTotalCapital(int PartnersTotalCapital) {
        this.PartnersTotalCapital = PartnersTotalCapital;
    }

    /**
     * @return the
     * WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure
     */
    public int getWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure() {
        return WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure;
    }

    /**
     * @param WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure
     * the WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure to
     * set
     */
    protected final void setWeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure(int WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure) {
        this.WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure = WeeklyNotionalIncomeFromCapitalClaimantAndPartnerCombinedFigure;
    }

    /**
     * @return the ClaimantsTotalHoursOfRemunerativeWorkPerWeek
     */
    public double getClaimantsTotalHoursOfRemunerativeWorkPerWeek() {
        return ClaimantsTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @param ClaimantsTotalHoursOfRemunerativeWorkPerWeek the
     * ClaimantsTotalHoursOfRemunerativeWorkPerWeek to set
     */
    protected final void setClaimantsTotalHoursOfRemunerativeWorkPerWeek(double ClaimantsTotalHoursOfRemunerativeWorkPerWeek) {
        this.ClaimantsTotalHoursOfRemunerativeWorkPerWeek = ClaimantsTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @return the PartnersTotalHoursOfRemunerativeWorkPerWeek
     */
    public double getPartnersTotalHoursOfRemunerativeWorkPerWeek() {
        return PartnersTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @param PartnersTotalHoursOfRemunerativeWorkPerWeek the
     * PartnersTotalHoursOfRemunerativeWorkPerWeek to set
     */
    protected final void setPartnersTotalHoursOfRemunerativeWorkPerWeek(double PartnersTotalHoursOfRemunerativeWorkPerWeek) {
        this.PartnersTotalHoursOfRemunerativeWorkPerWeek = PartnersTotalHoursOfRemunerativeWorkPerWeek;
    }

    /**
     * @return the NumberOfBedroomsForLHARolloutCasesOnly
     */
    public int getNumberOfBedroomsForLHARolloutCasesOnly() {
        return NumberOfBedroomsForLHARolloutCasesOnly;
    }

    /**
     * @param NumberOfBedroomsForLHARolloutCasesOnly the
     * NumberOfBedroomsForLHARolloutCasesOnly to set
     */
    protected final void setNumberOfBedroomsForLHARolloutCasesOnly(int NumberOfBedroomsForLHARolloutCasesOnly) {
        this.NumberOfBedroomsForLHARolloutCasesOnly = NumberOfBedroomsForLHARolloutCasesOnly;
    }

    /**
     * @return the PartnersDateOfDeath
     */
    public String getPartnersDateOfDeath() {
        return PartnersDateOfDeath;
    }

    /**
     * @param PartnersDateOfDeath the PartnersDateOfDeath to set
     */
    protected final void setPartnersDateOfDeath(String PartnersDateOfDeath) {
        this.PartnersDateOfDeath = PartnersDateOfDeath;
    }

    /**
     * @return the JointTenancyFlag
     */
    public int getJointTenancyFlag() {
        return JointTenancyFlag;
    }

    /**
     * @param JointTenancyFlag the JointTenancyFlag to set
     */
    protected final void setJointTenancyFlag(int JointTenancyFlag) {
        this.JointTenancyFlag = JointTenancyFlag;
    }

    protected final void setJointTenancyFlag(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setJointTenancyFlag(0);
        } else {
            try {
                setJointTenancyFlag(Integer.valueOf(fields[n]));
                if (JointTenancyFlag > 2 || JointTenancyFlag < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("JointTenancyFlag=" + fields[n]);
                    System.err.println("JointTenancyFlag > 2 || JointTenancyFlag < 0");
//                throw new Exception("JointTenancyFlag > 2 || JointTenancyFlag < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setJointTenancyFlag(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the AppointeeFlag
     */
    public int getAppointeeFlag() {
        return AppointeeFlag;
    }

    /**
     * @param AppointeeFlag the AppointeeFlag to set
     */
    protected final void setAppointeeFlag(int AppointeeFlag) {
        this.AppointeeFlag = AppointeeFlag;
    }

    protected final void setAppointeeFlag(
            int n,
            String[] fields) throws Exception {
        if (fields[n].trim().isEmpty()) {
            setAppointeeFlag(0);
        } else {
            try {
                setAppointeeFlag(Integer.valueOf(fields[n]));
                if (AppointeeFlag > 2 || AppointeeFlag < 0) {
                    System.err.println("RecordID=" + RecordID);
                    System.err.println("n=" + n);
                    System.err.println("AppointeeFlag=" + fields[n]);
                    System.err.println("AppointeeFlag > 2 || AppointeeFlag < 0");
//                throw new Exception("AppointeeFlag > 2 || AppointeeFlag < 0");
                }
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setAppointeeFlag(int,String[])");
//                throw e;
            }
        }
    }

    /**
     * @return the RentFreeWeeksIndicator
     */
    public int getRentFreeWeeksIndicator() {
        return RentFreeWeeksIndicator;
    }

    /**
     * @param RentFreeWeeksIndicator the RentFreeWeeksIndicator to set
     */
    protected final void setRentFreeWeeksIndicator(int RentFreeWeeksIndicator) {
        this.RentFreeWeeksIndicator = RentFreeWeeksIndicator;
    }

    /**
     * @return the LastPaidToDate
     */
    public String getLastPaidToDate() {
        return LastPaidToDate;
    }

    /**
     * @param LastPaidToDate the LastPaidToDate to set
     */
    protected final void setLastPaidToDate(String LastPaidToDate) {
        this.LastPaidToDate = LastPaidToDate;
    }

    /**
     * @return the WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability
     */
    public int getWeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability() {
        return WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability;
    }

    protected final int setWeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability(
            int n,
            String[] fields) {
        if (fields[n].trim().isEmpty()) {
            WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability = 0;
        } else {
            try {
                WeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability = (Integer.valueOf(fields[n]));
            } catch (NumberFormatException e) {
                System.err.println("RecordID=" + RecordID);
                System.err.println("n=" + n);
                System.err.println("NumberFormatException in setWeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability(int,String[])");
//                throw e;
                String currentLastPaidToDate = LastPaidToDate;
                LastPaidToDate = fields[n];
                System.err.println("current LastPaidToDate=" + currentLastPaidToDate + " set to=" + LastPaidToDate);
                n++;
                setWeeklyAdditionalDiscretionaryPaymentForCouncilTaxLiability(
                        n,
                        fields);
            }
        }
        return n;
    }

    /**
     * @return the ClaimantsWeeklyIncomeFromESABasicElement
     */
    public int getClaimantsWeeklyIncomeFromESABasicElement() {
        return ClaimantsWeeklyIncomeFromESABasicElement;
    }

    /**
     * @param ClaimantsWeeklyIncomeFromESABasicElement the
     * ClaimantsWeeklyIncomeFromESABasicElement to set
     */
    protected final void setClaimantsWeeklyIncomeFromESABasicElement(int ClaimantsWeeklyIncomeFromESABasicElement) {
        this.ClaimantsWeeklyIncomeFromESABasicElement = ClaimantsWeeklyIncomeFromESABasicElement;
    }

    /**
     * @return the PartnersWeeklyIncomeFromESABasicElement
     */
    public int getPartnersWeeklyIncomeFromESABasicElement() {
        return PartnersWeeklyIncomeFromESABasicElement;
    }

    /**
     * @param PartnersWeeklyIncomeFromESABasicElement the
     * PartnersWeeklyIncomeFromESABasicElement to set
     */
    protected final void setPartnersWeeklyIncomeFromESABasicElement(int PartnersWeeklyIncomeFromESABasicElement) {
        this.PartnersWeeklyIncomeFromESABasicElement = PartnersWeeklyIncomeFromESABasicElement;
    }

    /**
     * @return the ClaimantsWeeklyIncomeFromESAWRAGElement
     */
    public int getClaimantsWeeklyIncomeFromESAWRAGElement() {
        return ClaimantsWeeklyIncomeFromESAWRAGElement;
    }

    /**
     * @param ClaimantsWeeklyIncomeFromESAWRAGElement the
     * ClaimantsWeeklyIncomeFromESAWRAGElement to set
     */
    protected final void setClaimantsWeeklyIncomeFromESAWRAGElement(int ClaimantsWeeklyIncomeFromESAWRAGElement) {
        this.ClaimantsWeeklyIncomeFromESAWRAGElement = ClaimantsWeeklyIncomeFromESAWRAGElement;
    }

    /**
     * @return the PartnersWeeklyIncomeFromESAWRAGElement
     */
    public int getPartnersWeeklyIncomeFromESAWRAGElement() {
        return PartnersWeeklyIncomeFromESAWRAGElement;
    }

    /**
     * @param PartnersWeeklyIncomeFromESAWRAGElement the
     * PartnersWeeklyIncomeFromESAWRAGElement to set
     */
    protected final void setPartnersWeeklyIncomeFromESAWRAGElement(int PartnersWeeklyIncomeFromESAWRAGElement) {
        this.PartnersWeeklyIncomeFromESAWRAGElement = PartnersWeeklyIncomeFromESAWRAGElement;
    }

    /**
     * @return the ClaimantsWeeklyIncomeFromESASCGElement
     */
    public int getClaimantsWeeklyIncomeFromESASCGElement() {
        return ClaimantsWeeklyIncomeFromESASCGElement;
    }

    /**
     * @param ClaimantsWeeklyIncomeFromESASCGElement the
     * ClaimantsWeeklyIncomeFromESASCGElement to set
     */
    protected final void setClaimantsWeeklyIncomeFromESASCGElement(int ClaimantsWeeklyIncomeFromESASCGElement) {
        this.ClaimantsWeeklyIncomeFromESASCGElement = ClaimantsWeeklyIncomeFromESASCGElement;
    }

    /**
     * @return the PartnersWeeklyIncomeFromESASCGElement
     */
    public int getPartnersWeeklyIncomeFromESASCGElement() {
        return PartnersWeeklyIncomeFromESASCGElement;
    }

    /**
     * @param PartnersWeeklyIncomeFromESASCGElement the
     * PartnersWeeklyIncomeFromESASCGElement to set
     */
    protected final void setPartnersWeeklyIncomeFromESASCGElement(int PartnersWeeklyIncomeFromESASCGElement) {
        this.PartnersWeeklyIncomeFromESASCGElement = PartnersWeeklyIncomeFromESASCGElement;
    }

    /**
     * @return the WRAGPremiumFlag
     */
    public String getWRAGPremiumFlag() {
        return WRAGPremiumFlag;
    }

    /**
     * @param WRAGPremiumFlag the WRAGPremiumFlag to set
     */
    protected final void setWRAGPremiumFlag(String WRAGPremiumFlag) {
        this.WRAGPremiumFlag = WRAGPremiumFlag;
    }

    /**
     * @return the SCGPremiumFlag
     */
    public String getSCGPremiumFlag() {
        return SCGPremiumFlag;
    }

    /**
     * @param SCGPremiumFlag the SCGPremiumFlag to set
     */
    protected final void setSCGPremiumFlag(String SCGPremiumFlag) {
        this.SCGPremiumFlag = SCGPremiumFlag;
    }

    /**
     * @return the LandlordPostcode
     */
    public String getLandlordPostcode() {
        return LandlordPostcode;
    }

    /**
     * @param LandlordPostcode the LandlordPostcode to set
     */
    protected final void setLandlordPostcode(String LandlordPostcode) {
        this.LandlordPostcode = env.oe.getHandler().formatPostcode(LandlordPostcode);
    }

}
