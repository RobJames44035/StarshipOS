/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
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
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 * Benchmark for noneMatch() operation.
 * Focuses on short-circuiting behavior.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class NoneMatchShort {

    /**
     * Implementation notes:
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     *   - test the predicate which will become true closer to start, in the middle, and closer to the end
     */

    @Param("100000")
    private int size;

    @Param("100")
    private int offset;

    private LongPredicate pMid, pStart, pEnd;

    @Setup
    public void setup() {
        pStart = new LongPredicate() {
            @Override
            public boolean test(long v) {
                return v > offset;
            }
        };
        pMid = new LongPredicate() {
            @Override
            public boolean test(long v) {
                return v > size / 2;
            }
        };
        pEnd = new LongPredicate() {
            @Override
            public boolean test(long v) {
                return v > size - offset;
            }
        };
    }

    @Benchmark
    public boolean seq_start() {
        return LongStream.range(0, size).noneMatch(pStart);
    }

    @Benchmark
    public boolean seq_mid() {
        return LongStream.range(0, size).noneMatch(pMid);
    }

    @Benchmark
    public boolean seq_end() {
        return LongStream.range(0, size).noneMatch(pEnd);
    }

    @Benchmark
    public boolean par_start() {
        return LongStream.range(0, size).parallel().noneMatch(pStart);
    }

    @Benchmark
    public boolean par_mid() {
        return LongStream.range(0, size).parallel().noneMatch(pMid);
    }

    @Benchmark
    public boolean par_end() {
        return LongStream.range(0, size).parallel().noneMatch(pEnd);
    }

}
