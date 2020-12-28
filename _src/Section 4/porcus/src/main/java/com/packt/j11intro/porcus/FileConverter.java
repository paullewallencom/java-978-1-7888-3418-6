package com.packt.j11intro.porcus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class FileConverter {
    public static void main(String[] args) {
        PigLatinTranslator translator = new PigLatinTranslator();
        Path path = Paths.get("news.txt");
        try (Stream<String> stream = Files.lines(path)) {
            stream.map(line -> {
                if (line.trim().length() < 1) return null;
                return line + "\n>> " + translator.convert(line);
            })
                    .filter(Objects::nonNull)
                    .forEach(System.out::println);
        } catch (IOException ioe) {
            System.err.printf("Failed reading from %s due to %s.\n", path, ioe);
        }
    }
}
