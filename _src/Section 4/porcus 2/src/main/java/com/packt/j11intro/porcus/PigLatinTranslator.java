package com.packt.j11intro.porcus;

public class PigLatinTranslator {
    /**
     * Convert a single word into pig latin.
     *
     * @param word a single word.
     * @return word converted into pig latin
     */
    public String convertWord(final String word) {
        char[] chars = word.toCharArray();

        // If it is a vowel, just add 'way'.
        if (isVowel(chars[0])) {
            // This is a simple case, we return right away to get it done with.
            return word + "way";
        }

        // Loop through to find first vowel or "y" (it is treated like a vowel in this case).
        int remainingStartIdx = -1; // -1 is used as an indicator for nothing found, its initial state
        for (int i = 0; i < chars.length; i++) {
            char c = Character.toLowerCase(chars[i]);
            if (c == 'y' || isVowel(c)) {
                remainingStartIdx = i;
                break;
            }
        }

        // If no vowel (or "y") found, just add 'ay'.
        if (remainingStartIdx == -1) {
            // Second simple case, add "ay" and return and be done with.
            return word + "ay";
        }

        // Move consonants to behind and add 'ay'. We lowercase the word.
        final String lword = word.toLowerCase();
        // New first character is the first character of the portion moved to the front.
        char firstChar = chars[remainingStartIdx];
        // Construct the first character carefully to retain the correct casing. If the first character was uppercase, we keep it that way.
        firstChar = (Character.isUpperCase(chars[0])) ? Character.toUpperCase(firstChar) : Character.toLowerCase(firstChar);
        // Returned word = first character + portion moved to front + portion moved to behind + "ay"
        return firstChar + lword.substring(remainingStartIdx + 1) + lword.substring(0, remainingStartIdx) + "ay";
    }

    /**
     * Check if character is a vowel.
     *
     * @param c character to check
     * @return true if it is a vowel, false otherwise
     */
    protected boolean isVowel(char c) {
        char lowerCase = Character.toLowerCase(c);

        switch (lowerCase) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }
}
