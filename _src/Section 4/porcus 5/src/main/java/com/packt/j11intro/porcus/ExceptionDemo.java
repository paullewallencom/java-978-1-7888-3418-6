package com.packt.j11intro.porcus;

public class ExceptionDemo {
    public static void main(String[] args) {
        String[] numsAsString = "7 10 3kdj 20 zz 98".split(" ");

        for (String numAsString : numsAsString) {
            try {
                int i = Integer.parseInt(numAsString);
                System.out.println("The string has int value of " + i + ".");
            } catch (NumberFormatException nfe) {
                System.out.println("The string is not an int: " + numAsString);
            }
        }
    }
}
