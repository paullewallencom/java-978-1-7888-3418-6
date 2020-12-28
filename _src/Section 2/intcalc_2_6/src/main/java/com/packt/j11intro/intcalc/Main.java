package com.packt.j11intro.intcalc;

import jdk.jfr.Description;

import javax.annotation.processing.Generated;
import java.beans.Transient;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
        // Initialize calculator.
        InterestCalculator calculator = new InterestCalculator();

        // Get number format instance.
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        CentsToCurrencyFormatter cf = (var n) -> {
            return nf.format(n.doubleValue() / 100.0);
        };

        // Define amount owed in cents and rate.
        int originalOwedInCents = 10000 * 100; // $1000
        int owedInCents = originalOwedInCents;
        double ratePerc = 11.9; // rate percentage

        // Payment
        int monthlyPaymentInCents = 5000 * 100; // $x / month

        int month = 0;
        double totalInterest = 0;
        while (owedInCents > 0 && month < 1000) {
            month++;

            String owed = cf.fmt(owedInCents);
            System.out.printf("Month[%2d] Owed[%12s] ", month, owed);
            double interest = calculator.calcMonthlyInterest(owedInCents, ratePerc);
            totalInterest += interest;
            owedInCents += interest;

            double payment = Math.min(owedInCents, monthlyPaymentInCents);
            owedInCents -= payment;

            System.out.printf("Payment[%10s] Interest[%12s] Remaining[%12s]\n",
                    cf.fmt(payment), cf.fmt(interest), cf.fmt(owedInCents));
        }

        if (month == 1000) {
            System.out.println("We ran for too many months... something wrong.");
        }

        // Print out some summary.
        System.out.printf("Total interest paid: %11.2f\n", totalInterest / 100.0);
        System.out.printf("Percentage of interest paid: %9.2f%%\n", totalInterest / originalOwedInCents * 100);
    }
}

@FunctionalInterface
interface CentsToCurrencyFormatter {
    String fmt(Number n);
}