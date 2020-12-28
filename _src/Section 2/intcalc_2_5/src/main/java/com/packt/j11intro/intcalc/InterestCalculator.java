package com.packt.j11intro.intcalc;

public class InterestCalculator {
    public InterestCalculator() {
        // Constructor
    }

    public double calcMonthlyInterest(int owedInCents, double ratePerc) {
        return owedInCents * ratePerc / 12.0 / 100;
    }
}
