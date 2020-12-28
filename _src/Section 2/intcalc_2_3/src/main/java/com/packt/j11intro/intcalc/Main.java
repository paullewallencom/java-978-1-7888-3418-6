package com.packt.j11intro.intcalc;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world.");

        // Initialize calculator.
        InterestCalculator calculator = new InterestCalculator();

        // Define amount owed in cents and rate.
        int owedInCents = 100000 * 100; // $1000
        int ratePerc = 14; // 14%

        // Calculate interest.
        int interest = calculator.calcInterest(owedInCents, ratePerc);
        System.out.println("Interest: " + interest);
    }
}
