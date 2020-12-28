package com.packt.j11intro.drmoolah;

import java.util.stream.Stream;

@FunctionalInterface
public interface AnalysisFunc {
    Stream<String> analyze(Stream<SpendingDto> stream);
}
