package com.packt.j11intro.intcalc;

import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
        // Initialize calculator.
        InterestCalculator calculator = new InterestCalculator();

        // Get number format instance.
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        // Define amount owed in cents and rate.
        int originalOwedInCents = 10000 * 100; // $1000
        int owedInCents = originalOwedInCents;
        double ratePerc = 11.9; // 14%

        // Payment
        int monthlyPaymentInCents = 5000 * 100; // $x / month

        int month = 0;
        double totalInterest = 0;
        while (owedInCents > 0 && month < 1000) {
            month++;

            String owed = nf.format(owedInCents / 100.0);
            System.out.printf("Month[%2d] Owed[%12s] ", month, owed);
            double interest = calculator.calcMonthlyInterest(owedInCents, ratePerc);
            totalInterest += interest;
            owedInCents += interest;

            double payment = Math.min(owedInCents, monthlyPaymentInCents);
            owedInCents -= payment;

            System.out.printf("Payment[%10s] Interest[%12s] Remaining[%12s]\n",
                    nf.format(payment / 100.0), nf.format(interest / 100.0), nf.format(owedInCents / 100.0));

            monthlyPaymentInCents *= 1.2; // pay 20% more each month...
        }

        if (month == 1000) {
            System.out.println("We ran for too many months... something wrong.");
        }

        // Print out some summary.
        System.out.printf("Total interest paid: %11.2f\n", totalInterest / 100.0);
        System.out.printf("Percentage of interest paid: %9.2f%%\n", totalInterest / originalOwedInCents * 100);
    }
}
