package com.packt.j11intro.porcus;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Initializes main engine.
        PigLatinTranslator t = new PigLatinTranslator();

        String[] words = "These are the words we want to experiment with for now".split(" ");

        for (String word : words) {
            System.out.println("The pig latin for '" + word + "' is '" + t.convertWord(word) + "'.");
        }
    }
}
