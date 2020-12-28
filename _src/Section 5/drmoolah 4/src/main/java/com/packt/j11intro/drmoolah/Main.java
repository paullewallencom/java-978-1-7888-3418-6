package com.packt.j11intro.drmoolah;

import org.springframework.lang.NonNull;

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

        System.out.println("Spending per description.");
        analyze(resource, stream -> {
            Map<String, Double> result = stream
                    // key: description, value: amount merged using a + b
                    .collect(Collectors.toMap(SpendingDto::getDescription, SpendingDto::getAmount, (a, b) -> a + b));

            return result.entrySet().stream()
                    // Sort based on value of entry, reversed.
                    .sorted(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed())
                    // Map to string.
                    .map((e) -> String.format("Spent %.2f on %s", e.getValue(), e.getKey()));
        });

        System.out.println();
        System.out.println();

        System.out.println("Spending on date.");
        analyze(resource, stream -> {
            Map<String, Double> result = stream
                    .collect(Collectors.toMap(SpendingDto::getDate, SpendingDto::getAmount, (a, b) -> a + b));

            return result.entrySet().stream()
                    // Sort based on value of entry, reversed.
                    .sorted(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed())
                    // Map to string.
                    .map((e) -> String.format("Spent %.2f on %s", e.getValue(), e.getKey()));
        });
    }

    protected static void analyze(@NonNull URL resource, @NonNull AnalysisFunc f) {
        // Put stream in try-with-resource.
        try (Stream<String> stream = Files.lines(Paths.get(resource.getPath()))) {
            f.analyze(
                    stream
                            .skip(1) // skip the label line
                            .map(SpendingDto::parseCsvLine) // map it into a SpendingDto
            ).forEach(System.out::println);// Pass to println for printing.
        } catch (IOException ioe) {
            throw new RuntimeException("Failed I/O while analyzing resource: " + resource, ioe);
        }
    }
}