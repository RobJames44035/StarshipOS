/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/*
 * This benchmark tests Locale.getDefault variants
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class LocaleDefaults {

    @Benchmark
    public Locale getDefault() {
        return Locale.getDefault();
    }

    @Benchmark
    public Locale getDefaultDisplay() {
        return Locale.getDefault(Locale.Category.DISPLAY);
    }

    @Benchmark
    public Locale getDefaultFormat() {
        return Locale.getDefault(Locale.Category.FORMAT);
    }
}

