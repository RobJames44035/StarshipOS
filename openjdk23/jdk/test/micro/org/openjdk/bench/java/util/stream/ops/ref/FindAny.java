/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.ops.ref;

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
import java.util.stream.LongStream;

/**
 * Benchmark for findAny() operation.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class FindAny {

    @Param("100000")
    private int size;

    @Benchmark
    public Long seq_invoke() {
        return LongStream.range(0, size).boxed().findAny().get();
    }

    @Benchmark
    public Long par_invoke() {
        return LongStream.range(0, size).parallel().boxed().findAny().get();
    }

    public static void main(String... args) {
        FindAny findAny = new FindAny();
        findAny.size = 100000;
        findAny.seq_invoke();
        findAny.par_invoke();
    }
}
