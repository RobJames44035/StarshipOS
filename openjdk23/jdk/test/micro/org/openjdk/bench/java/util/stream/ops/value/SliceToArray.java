/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.ops.value;

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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Benchmark for limit()/skip() operation in sized streams.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class SliceToArray {

    @Param("10000")
    private int size;

    @Benchmark
    public int[] seq_baseline() {
        return IntStream.range(0, size)
                .toArray();
    }

    @Benchmark
    public int[] seq_limit() {
        return IntStream.range(0, size * 2)
                .limit(size)
                .toArray();
    }

    @Benchmark
    public int[] seq_skipLimit() {
        return IntStream.range(0, size * 2)
                .skip(1)
                .limit(size)
                .toArray();
    }

    @Benchmark
    public int[] par_baseline() {
        return IntStream.range(0, size)
                .parallel()
                .toArray();
    }

    @Benchmark
    public int[] par_limit() {
        return IntStream.range(0, size * 2)
                .parallel()
                .limit(size)
                .toArray();
    }

    @Benchmark
    public int[] par_skipLimit() {
        return IntStream.range(0, size * 2)
                .parallel()
                .skip(1)
                .limit(size)
                .toArray();
    }
}
