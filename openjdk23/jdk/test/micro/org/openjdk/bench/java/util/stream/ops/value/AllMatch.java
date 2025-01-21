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
 * Benchmark for allMatch() operation.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class AllMatch {

    /**
     * Implementation notes:
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     *   - the predicates are always true to avert shortcurcuiting
     */

    @Param("100000")
    private int size;

    private LongPredicate p1, p2, p3;

    @Setup
    public void setup() {
        p1 = new LongPredicate() {
            @Override
            public boolean test(long v) {
                return true;
            }
        };
        p2 = new LongPredicate() {
            @Override
            public boolean test(long v) {
                return true;
            }
        };
        p3 = new LongPredicate() {
            @Override
            public boolean test(long v) {
                return true;
            }
        };
    }

    @Benchmark
    public boolean seq_invoke() {
        return LongStream.range(0, size).allMatch(p1);
    }

    @Benchmark
    public int seq_chain111() {
        int s = 0;
        s += (LongStream.range(0, size).allMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).allMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).allMatch(p1)) ? 1 : 0;
        return s;
    }

    @Benchmark
    public int seq_chain123() {
        int s = 0;
        s += (LongStream.range(0, size).allMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).allMatch(p2)) ? 1 : 0;
        s += (LongStream.range(0, size).allMatch(p3)) ? 1 : 0;
        return s;
    }

    @Benchmark
    public boolean par_invoke() {
        return LongStream.range(0, size).parallel().allMatch(p1);
    }

    @Benchmark
    public int par_chain111() {
        int s = 0;
        s += (LongStream.range(0, size).parallel().allMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().allMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().allMatch(p1)) ? 1 : 0;
        return s;
    }

    @Benchmark
    public int par_chain123() {
        int s = 0;
        s += (LongStream.range(0, size).parallel().allMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().allMatch(p2)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().allMatch(p3)) ? 1 : 0;
        return s;
    }

}
