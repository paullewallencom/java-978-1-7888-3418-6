package com.packt.j11intro.drmoolah;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        URL resource = ClassLoader.getSystemClassLoader().getResource("spending.csv");
        if (resource == null) {
            throw new RuntimeException("Failed to locate input file.");
        }

        // Put stream in try-with-resource.
        try (Stream<String> stream = Files.lines(Paths.get(resource.getPath()))) {
            Map<String, Double> result = stream.skip(1) // skip first label line
                    .map(SpendingDto::parseCsvLine) // map it into a SpendingDto
                    // key: description, value: amount merged using a + b
                    .collect(Collectors.toMap(SpendingDto::getDescription, SpendingDto::getAmount, (a, b) -> a + b));

            result.entrySet().stream()
                    // Sort based on value of entry, reversed.
                    .sorted(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed())
                    // Map to string.
                    .map((e) -> String.format("Spent %.2f on %s", e.getValue(), e.getKey()))
                    // Pass to println for printing.
                    .forEach(System.out::println);
        }
    }
}
