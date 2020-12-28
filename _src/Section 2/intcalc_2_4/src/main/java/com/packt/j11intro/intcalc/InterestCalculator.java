package com.packt.j11intro.intcalc;

public class InterestCalculator {
    public InterestCalculator() {
        // Constructor
    }

    public double calcMonthlyInterest(int owedInCents, int ratePerc) {
        double interest = owedInCents * ratePerc / 12.0 / 100;

        return interest;
    }
}
