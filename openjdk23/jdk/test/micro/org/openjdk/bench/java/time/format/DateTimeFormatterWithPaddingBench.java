/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.java.time.format;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@State(Scope.Thread)
public class DateTimeFormatterWithPaddingBench {

    private static final DateTimeFormatter FORMATTER_WITH_PADDING = new DateTimeFormatterBuilder()
            .appendLiteral("Date:")
            .padNext(20, ' ')
            .append(DateTimeFormatter.ISO_DATE)
            .toFormatter();

    private static final DateTimeFormatter FORMATTER_WITH_PADDING_ZERO = new DateTimeFormatterBuilder()
            .appendLiteral("Year:")
            .padNext(4)
            .appendValue(ChronoField.YEAR)
            .toFormatter();

    private static final DateTimeFormatter FORMATTER_WITH_PADDING_ONE = new DateTimeFormatterBuilder()
            .appendLiteral("Year:")
            .padNext(5)
            .appendValue(ChronoField.YEAR)
            .toFormatter();

    private final LocalDateTime now = LocalDateTime.now();

    @Benchmark
    public String formatWithPadding() {
        return FORMATTER_WITH_PADDING.format(now);
    }

    @Benchmark
    public String formatWithPaddingLengthZero() {
        return FORMATTER_WITH_PADDING_ZERO.format(now);
    }

    @Benchmark
    public String formatWithPaddingLengthOne() {
        return FORMATTER_WITH_PADDING_ONE.format(now);
    }

}
