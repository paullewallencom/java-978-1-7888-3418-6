package com.packt.j11intro.intcalc;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world.");

        // Initialize calculator.
        InterestCalculator calculator = new InterestCalculator();

        // Define amount owed in cents and rate.
        int originalOwedInCents = 1000 * 100; // $1000
        int owedInCents = originalOwedInCents;
        int ratePerc = 14; // 14%

        /*
        // Calculate interest.
        var interest = calculator.calcMonthlyInterest(owedInCents, ratePerc);
        System.out.println("Interest (byte): " + (byte) interest);
        System.out.println("Interest (short): " + (short) interest);
        System.out.println("Interest (int): " + (int) interest);
        System.out.println("Interest (long): " + (long) interest);
        System.out.println("Interest (float): " + (float) interest);
        System.out.println("Interest (double): " + interest);
        */

        int monthlyPaymentInCents = 50 * 100; // $x / month

        int month = 0;
        double totalInterest = 0;
        while (owedInCents > 0 && month < 1000) {
            month++;
            System.out.print("Owed[" + owedInCents + "] ");
            double interest = calculator.calcMonthlyInterest(owedInCents, ratePerc);
            totalInterest += interest;
            owedInCents += interest;

            double payment = Math.min(owedInCents, monthlyPaymentInCents);
            owedInCents -= payment;

            System.out.println("Month[" + month + "] Interest[" + interest + "] Payment[" + payment + "] " +
                    "Remaining[" + owedInCents + "]");

            monthlyPaymentInCents *= 1.2; // pay 20% more each month...
        }

        if (month == 1000) {
            System.out.println("We ran for too many months... something wrong.");
        }

        // Print out some summary.
        System.out.println("Total interest paid: " + totalInterest);
        System.out.println("Percentage of interest paid: " + (totalInterest / originalOwedInCents * 100) + "%");
    }
}
