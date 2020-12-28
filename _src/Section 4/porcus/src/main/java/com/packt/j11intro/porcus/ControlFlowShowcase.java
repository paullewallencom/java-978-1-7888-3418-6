package com.packt.j11intro.porcus;

public class ControlFlowShowcase {
    public static void main(String[] args) {
//                showIf();
//                showSwitch();
//                showWhile();
//                showFor();
                showContinueBreak();
//                showReturn();
    }

    static void showIf() {
        // if-then, if-then-else
        int x = 3;
        int y = 10;
        if (x < y) {
            System.out.println("x is less than y.");
        }

        if (x > y) {
            System.out.println("x is larger than y");
        } else {
            System.out.println("x is not larger than y");
        }
    }

    static void showSwitch() {
        char c = 'f';

        switch (c) {
            case 'a':
                System.out.println("c is 'a'");
                break;
            case 'f':
                System.out.println("c is 'f'");
            case 'o':
                System.out.println("i will also print because no break");
                break;
            default:
                System.out.println("for other cases");
        }
    }

    static void showWhile() {
        int i = 0;

        while (i < 3) {
            System.out.println("i is now " + i);
            i++;
        }

        i = 0;
        int j = 0;
        do {
            System.out.println("in do while, i is now " + i);
            i++;
            j = i * 5;
        } while (j < 30);
    }

    static void showFor() {
        for (int i = 0; i < 26; i++) {
            System.out.print((char) ('a' + i));
        }
        System.out.println();
    }

    static void showContinueBreak() {
        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                continue;
            }

            System.out.print(c);
        }
        System.out.println();

        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            if (c == 'l') {
                break;
            }

            System.out.print(c);
        }
        System.out.println();
    }

    static void showReturn() {
        System.out.println(addXY(2, 3));
    }

    static int addXY(int x, int y) {
        return x + y;
    }
}
