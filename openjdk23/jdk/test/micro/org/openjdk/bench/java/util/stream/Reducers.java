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
import java.util.function.LongBinaryOperator;
import java.util.stream.LongStream;

/**
 * Benchmark for checking different reduce schemes.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class Reducers {

    /**
     * Implementation notes:
     *   - parallel version requires thread-safe sink, we use the same for sequential version for better comparison
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     *   - the result of applying consecutive operations is the same, in order to have the same number of elements in sink
     */

    @Param("100000")
    private int size;

    private LongBinaryOperator op;

    @Setup
    public void setup() {
        op = new LongBinaryOperator() {
            @Override
            public long applyAsLong(long l, long r) {
                return (l > r) ? l : r;
            }
        };
    }

    @Benchmark
    public long seq_reduce() {
        return LongStream.range(0, size).reduce(op).getAsLong();
    }

    @Benchmark
    public long par_reduce() {
        return LongStream.range(0, size).parallel().reduce(op).getAsLong();
    }

    @Benchmark
    public long seq_reduce_base() {
        return LongStream.range(0, size).reduce(0L, op);
    }

    @Benchmark
    public long par_reduce_base() {
        return LongStream.range(0, size).parallel().reduce(0L, op);
    }


}
