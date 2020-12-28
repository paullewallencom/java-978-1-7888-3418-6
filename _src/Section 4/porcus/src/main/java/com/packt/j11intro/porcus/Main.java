package com.packt.j11intro.porcus;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initializes main engine.
        PigLatinTranslator t = new PigLatinTranslator();

        System.out.println("Pig Latin Converter, type in anything, we will convert it into pig latin. Press <ENTER> to submit for conversion. Ctrl-D to end.");
        Scanner scanner = new Scanner(System.in);
        String line = null;
        do {
            if (line != null) {
                System.out.println("The pig latin for [" + line + "] is [" + t.convert(line) + "].");
                System.out.println("Please type in something else to convert.");
            }

            if (scanner.hasNext()) {
                line = scanner.nextLine();
            } else {
                line = null;
            }
        } while (line != null);

        System.out.println("Thanks for using PigLatinTranslator.");
    }
}
