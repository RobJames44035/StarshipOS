/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/*
 * This benchmark naively explores String::format/formatted performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 3)
public class StringFormat {

    public String s = "str";
    public int i = 17;
    public static final BigDecimal pi = new BigDecimal(Math.PI);

    @Benchmark
    public String decimalFormat() {
        return "%010.3f".formatted(pi);
    }

    @Benchmark
    public String stringFormat() {
        return "%s".formatted(s);
    }

    @Benchmark
    public String stringIntFormat() {
        return "%s %d".formatted(s, i);
    }

    @Benchmark
    public String widthStringFormat() {
        return "%3s".formatted(s);
    }

    @Benchmark
    public String widthStringIntFormat() {
        return "%3s %d".formatted(s, i);
    }

    @Benchmark
    public String complexFormat() {
        return "%3s %10d %4S %04X %4S %04X %4S %04X".formatted(s, i, s, i, s, i, s, i);
    }
}

