package com.packt.j11intro.drmoolah;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        LocalDate d = LocalDate.of(2018, 1, 1);
        String[] types = "Mortgage, Concert Tix, Bar, Petrol, Food Court, IT Purchase, Train Pass, Donation, Grocery, Sushi, Fast Food, Movie, Guardians, Magazine, Drinks".split(", ");
        Random r = new Random();

        for (int i = 0; i < 365; i++) {
            String date = d.toString();
            for (int j = 0; j < r.nextInt(10); j++) {
                String type = types[r.nextInt(types.length)];
                int cents = r.nextInt(200 * 100); // 200 dollars max
                String amount = new BigDecimal(cents).divide(new BigDecimal(100)).toPlainString();

                System.out.println(date + "," + type + "," + amount);
            }

            d = d.plusDays(1);
        }
    }
}
