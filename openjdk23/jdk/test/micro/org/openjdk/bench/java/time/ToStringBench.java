/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package org.openjdk.bench.java.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@State(Scope.Thread)
public class ToStringBench {
    private static final Instant[] INSTANTS;
    private static final ZonedDateTime[] ZONED_DATE_TIMES;
    private static final LocalDateTime[] LOCAL_DATE_TIMES;
    private static final LocalDate[] LOCAL_DATES;
    private static final LocalTime[] LOCAL_TIMES;

    static {
        Instant loInstant = Instant.EPOCH.plus(Duration.ofDays(365*50)); // 2020-01-01
        Instant hiInstant = loInstant.plus(Duration.ofDays(1));
        long maxOffsetNanos = Duration.between(loInstant, hiInstant).toNanos();
        Random random = new Random(0);
        INSTANTS = IntStream
                .range(0, 1_000)
                .mapToObj(ignored -> {
                    final long offsetNanos = (long) Math.floor(random.nextDouble() * maxOffsetNanos);
                    return loInstant.plus(offsetNanos, ChronoUnit.NANOS);
                })
                .toArray(Instant[]::new);

        ZONED_DATE_TIMES = Stream.of(INSTANTS)
                .map(instant -> ZonedDateTime.ofInstant(instant, ZoneOffset.UTC))
                .toArray(ZonedDateTime[]::new);

        LOCAL_DATE_TIMES = Stream.of(ZONED_DATE_TIMES)
                .map(zdt -> zdt.toLocalDateTime())
                .toArray(LocalDateTime[]::new);

        LOCAL_DATES = Stream.of(LOCAL_DATE_TIMES)
                .map(ldt -> ldt.toLocalDate())
                .toArray(LocalDate[]::new);

        LOCAL_TIMES = Stream.of(LOCAL_DATE_TIMES)
                .map(ldt -> ldt.toLocalTime())
                .toArray(LocalTime[]::new);
    }

    @Benchmark
    public void zonedDateTimeToString(Blackhole bh) {
        for (final ZonedDateTime zonedDateTime : ZONED_DATE_TIMES) {
            bh.consume(zonedDateTime.toString());
        }
    }

    @Benchmark
    public void localDateTimeToString(Blackhole bh) {
        for (LocalDateTime localDateTime : LOCAL_DATE_TIMES) {
            bh.consume(localDateTime.toString());
        }
    }

    @Benchmark
    public void localDateToString(Blackhole bh) {
        for (LocalDate localDate : LOCAL_DATES) {
            bh.consume(localDate.toString());
        }
    }

    @Benchmark
    public void localTimeToString(Blackhole bh) {
        for (LocalTime localTime : LOCAL_TIMES) {
            bh.consume(localTime.toString());
        }
    }
}