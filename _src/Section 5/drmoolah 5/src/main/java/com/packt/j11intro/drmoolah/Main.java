package com.packt.j11intro.drmoolah;

import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private final static MonetaryAmountFormat FORMAT_US = MonetaryFormats.getAmountFormat(Locale.US);

    public static void main(String[] args) throws IOException {
        URL resource = ClassLoader.getSystemClassLoader().getResource("spending.csv");
        if (resource == null) {
            throw new RuntimeException("Failed to locate input file.");
        }

        System.out.println("Spending per description.");
        analyze(resource, stream -> {
            Map<String, Money> result = stream
                    // key: description, value: amount merged using Money::add
                    .collect(Collectors.toMap(SpendingDto::getDescription, SpendingDto::getAmount, Money::add));

            return result.entrySet().stream()
                    // Sort based on value of entry, and Money::compareTo, reversed.
                    .sorted(Comparator.comparing(Map.Entry<String, Money>::getValue, Money::compareTo).reversed())
                    // Map to string.
                    .map((e) -> String.format("Spent %s on %s", FORMAT_US.format(e.getValue()), e.getKey()));
        });

        System.out.println();
        System.out.println();

        System.out.println("Spending on date.");
        analyze(resource, stream -> {
            Map<LocalDate, Money> result = stream
                    .collect(Collectors.toMap(SpendingDto::getDate, SpendingDto::getAmount, Money::add));

            return result.entrySet().stream()
                    // Sort based on value of entry, reversed.
                    .sorted(Comparator.comparing(Map.Entry<LocalDate, Money>::getValue, Money::compareTo).reversed())
                    // Map to string.
                    .map((e) -> String.format("Spent %s on %s", FORMAT_US.format(e.getValue()), e.getKey()));
        });

        System.out.println();
        System.out.println();

        System.out.println("Spending on per day of week.");
        analyze(resource, stream -> {
            Map<DayOfWeek, Money> result = stream
                    .collect(Collectors.toMap(s -> s.getDate().getDayOfWeek(), SpendingDto::getAmount, Money::add));

            return result.entrySet().stream()
                    // Sort based on value of entry, reversed.
                    .sorted(Comparator.comparing(Map.Entry<DayOfWeek, Money>::getValue, Money::compareTo).reversed())
                    // Map to string.
                    .map((e) -> String.format("Spent %s on %s", FORMAT_US.format(e.getValue()), e.getKey()));
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