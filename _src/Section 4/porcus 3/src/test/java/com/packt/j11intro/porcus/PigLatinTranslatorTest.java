package com.packt.j11intro.porcus;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PigLatinTranslatorTest {
    PigLatinTranslator t = new PigLatinTranslator();

    @Test
    public void test_01_isVowel() {
        // Chose to list out all characters one at a time to be explicit. Keeps it easier to trace.
        assertThat(t.isVowel('a')).isEqualTo(true);
        assertThat(t.isVowel('e')).isEqualTo(true);
        assertThat(t.isVowel('i')).isEqualTo(true);
        assertThat(t.isVowel('o')).isEqualTo(true);
        assertThat(t.isVowel('u')).isEqualTo(true);

        assertThat(t.isVowel('b')).isEqualTo(false);
        assertThat(t.isVowel('c')).isEqualTo(false);
        assertThat(t.isVowel('d')).isEqualTo(false);
        assertThat(t.isVowel('f')).isEqualTo(false);
        assertThat(t.isVowel('g')).isEqualTo(false);
        assertThat(t.isVowel('h')).isEqualTo(false);
        assertThat(t.isVowel('j')).isEqualTo(false);
        assertThat(t.isVowel('k')).isEqualTo(false);
        assertThat(t.isVowel('l')).isEqualTo(false);
        assertThat(t.isVowel('m')).isEqualTo(false);
        assertThat(t.isVowel('n')).isEqualTo(false);
        assertThat(t.isVowel('p')).isEqualTo(false);
        assertThat(t.isVowel('q')).isEqualTo(false);
        assertThat(t.isVowel('r')).isEqualTo(false);
        assertThat(t.isVowel('s')).isEqualTo(false);
        assertThat(t.isVowel('t')).isEqualTo(false);
        assertThat(t.isVowel('v')).isEqualTo(false);
        assertThat(t.isVowel('w')).isEqualTo(false);
        assertThat(t.isVowel('x')).isEqualTo(false);
        assertThat(t.isVowel('y')).isEqualTo(false);
        assertThat(t.isVowel('z')).isEqualTo(false);
    }

    @Test
    public void test_02_convertWord() {
        assertThat(t.convertWord("I")).isEqualTo("Iway");
        assertThat(t.convertWord("am")).isEqualTo("amway");
        assertThat(t.convertWord("christian")).isEqualTo("istianchray");
        assertThat(t.convertWord("pig")).isEqualTo("igpay");
        assertThat(t.convertWord("France")).isEqualTo("Ancefray");
        assertThat(t.convertWord("crying")).isEqualTo("yingcray");
        assertThat(t.convertWord("this")).isEqualTo("isthay");
        assertThat(t.convertWord("example")).isEqualTo("exampleway");
        assertThat(t.convertWord("t")).isEqualTo("tay"); // not real word, but...
        assertThat(t.convertWord("tx")).isEqualTo("txay"); // not real word, but...
        assertThat(t.convertWord("txy")).isEqualTo("ytxay"); // not real word, but...
    }
}