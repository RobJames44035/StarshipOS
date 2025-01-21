/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(3)
public class StringBuilderToString {
    @Param({"128", "256", "1024"})
    public int MIXED_SIZE;

    /**
     * This microbench simulates how java.io.BufferedReader uses StringBuilder.
     */
    @Benchmark
    public String toStringWithMixedChars() {
        StringBuilder sb = new StringBuilder(MIXED_SIZE);
        for (int i = 0; i < MIXED_SIZE - 4; ++i) {
            sb.append('a');
        }
        sb.append('\u3042'); // can't be encoded in latin-1,
        return sb.toString();
    }
}
