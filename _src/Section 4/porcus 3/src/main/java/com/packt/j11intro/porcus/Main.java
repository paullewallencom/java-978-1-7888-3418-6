package com.packt.j11intro.porcus;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initializes main engine.
        PigLatinTranslator t = new PigLatinTranslator();

        System.out.println("The pig latin for 'this' is '" + t.convertWord("this") + "'.");
    }
}
