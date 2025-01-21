/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream;

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

import java.util.concurrent.TimeUnit;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 * Benchmark for checking different "anyMatch" schemes.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class AnyMatcher {

    /**
     * Implementation notes:
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     *   - all operations have similar semantics
     */

    @Param("100000")
    private int size;

    private LongPredicate op;

    @Setup
    public void setup() {
        op = new LongPredicate() {
            @Override
            public boolean test(long x) {
                return false;
            }
        };
    }

    @Benchmark
    public boolean seq_anyMatch() {
        return LongStream.range(0, size).anyMatch(op);
    }

    @Benchmark
    public boolean seq_filter_findFirst() {
        return LongStream.range(0, size).filter(op).findFirst().isPresent();
    }

    @Benchmark
    public boolean seq_filter_findAny() {
        return LongStream.range(0, size).filter(op).findAny().isPresent();
    }

    @Benchmark
    public boolean par_anyMatch() {
        return LongStream.range(0, size).parallel().anyMatch(op);
    }

    @Benchmark
    public boolean par_filter_findFirst() {
        return LongStream.range(0, size).parallel().filter(op).findFirst().isPresent();
    }

    @Benchmark
    public boolean par_filter_findAny() {
        return LongStream.range(0, size).parallel().filter(op).findAny().isPresent();
    }

}
