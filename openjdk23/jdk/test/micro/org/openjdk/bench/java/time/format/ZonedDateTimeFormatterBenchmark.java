/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.java.time.format;

import org.openjdk.jmh.annotations.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.concurrent.TimeUnit;

@Fork(4)
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class ZonedDateTimeFormatterBenchmark {

    private static final DateTimeFormatter df = new DateTimeFormatterBuilder()
            .appendPattern("yyyy:MM:dd:HH:mm:v")
            .toFormatter();
    private static final String TEXT = "2015:03:10:12:13:ECT";

    @Setup
    public void setUp() {
        ZonedDateTime.parse(TEXT, df);
    }

    @Benchmark
    public ZonedDateTime parse() {
        return ZonedDateTime.parse(TEXT, df);
    }

}
