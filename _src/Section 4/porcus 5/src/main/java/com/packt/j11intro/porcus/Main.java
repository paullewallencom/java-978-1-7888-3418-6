package com.packt.j11intro.porcus;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initializes main engine.
        PigLatinTranslator t = new PigLatinTranslator();

        String sentence = "These are the words we want to experiment with for now.";
        System.out.println("The pig latin for [" + sentence + "] is [" + t.convert(sentence) + "].");
    }
}
