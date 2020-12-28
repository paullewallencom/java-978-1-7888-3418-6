package com.packt.j11intro.j11hello;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

public class Main {
    public static void main(String[] args) throws CheckDigitException {
        System.out.println("Hello world.");

        LuhnCheckDigit lcd = new LuhnCheckDigit();
        String result = lcd.calculate("234");
        System.out.println("LCD of 234: " + result);
    }
}
