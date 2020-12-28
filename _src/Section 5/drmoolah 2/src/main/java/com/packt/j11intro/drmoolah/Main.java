package com.packt.j11intro.drmoolah;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        URL resource = ClassLoader.getSystemClassLoader().getResource("spending.csv");
        if (resource == null) {
            throw new RuntimeException("Failed to locate input file.");
        }

        List<String> lines = Files.readAllLines(Paths.get(resource.getPath()));
        Map<String, Double> spendingPerDescMap = new HashMap<>();
        int count = 0;
        for (String line : lines) {
            count++;
            if (count == 1) {
                continue;
            }


            SpendingDto spendingDto = SpendingDto.parseCsvLine(line);
            Double curSpending = spendingPerDescMap.get(spendingDto.getDescription());
            if (curSpending == null) {
                curSpending = 0.0;
            }

            curSpending += spendingDto.getAmount();
            spendingPerDescMap.put(spendingDto.getDescription(), curSpending);
        }

        List<Map.Entry<String, Double>> entries = new ArrayList<>(spendingPerDescMap.entrySet());
        entries.sort(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return Double.compare(o1.getValue(), o2.getValue()) * -1;
            }
        });

        for (Map.Entry<String, Double> e : entries) {
            System.out.printf("Spent %.2f on %s\n", e.getValue(), e.getKey());
        }
    }
}
