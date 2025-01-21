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
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.LongStream;

/**
 * Benchmark for noneMatch() operation.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class NoneMatch {

    /**
     * Implementation notes:
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     *   - the predicates are always false to avert shortcurcuiting
     */

    @Param("100000")
    private int size;

    private Predicate<Long> p1, p2, p3;

    @Setup
    public void setup() {
        p1 = new Predicate<Long>() {
            @Override
            public boolean test(Long v) {
                return false;
            }
        };
        p2 = new Predicate<Long>() {
            @Override
            public boolean test(Long v) {
                return false;
            }
        };
        p3 = new Predicate<Long>() {
            @Override
            public boolean test(Long v) {
                return false;
            }
        };
    }

    @Benchmark
    public boolean seq_invoke() {
        return LongStream.range(0, size).boxed().noneMatch(p1);
    }

    @Benchmark
    public int seq_chain111() {
        int s = 0;
        s += (LongStream.range(0, size).boxed().noneMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).boxed().noneMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).boxed().noneMatch(p1)) ? 1 : 0;
        return s;
    }

    @Benchmark
    public int seq_chain123() {
        int s = 0;
        s += (LongStream.range(0, size).boxed().noneMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).boxed().noneMatch(p2)) ? 1 : 0;
        s += (LongStream.range(0, size).boxed().noneMatch(p3)) ? 1 : 0;
        return s;
    }

    @Benchmark
    public boolean par_invoke() {
        return LongStream.range(0, size).parallel().boxed().noneMatch(p1);
    }

    @Benchmark
    public int par_chain111() {
        int s = 0;
        s += (LongStream.range(0, size).parallel().boxed().noneMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().boxed().noneMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().boxed().noneMatch(p1)) ? 1 : 0;
        return s;
    }

    @Benchmark
    public int par_chain123() {
        int s = 0;
        s += (LongStream.range(0, size).parallel().boxed().noneMatch(p1)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().boxed().noneMatch(p2)) ? 1 : 0;
        s += (LongStream.range(0, size).parallel().boxed().noneMatch(p3)) ? 1 : 0;
        return s;
    }

}
