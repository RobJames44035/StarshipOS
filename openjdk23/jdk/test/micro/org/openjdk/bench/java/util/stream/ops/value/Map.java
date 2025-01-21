/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.ops.value;

import org.openjdk.bench.java.util.stream.ops.LongAccumulator;
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
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

/**
 * Benchmark for map() operation.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class Map {

    /**
     * Implementation notes:
     *   - parallel version requires thread-safe sink, we use the same for sequential version for better comparison
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     *   - the result of applying consecutive operations is the same, in order to have the same number of elements in sink
     */

    @Param("100000")
    private int size;

    private LongUnaryOperator m1, m2, m3;

    @Setup
    public void setup() {
        m1 = new LongUnaryOperator() {
            @Override
            public long applyAsLong(long l) {
                return l * 2;
            }
        };
        m2 = new LongUnaryOperator() {
            @Override
            public long applyAsLong(long l) {
                return l * 2;
            }
        };
        m3 = new LongUnaryOperator() {
            @Override
            public long applyAsLong(long l) {
                return l * 2;
            }
        };
    }

    @Benchmark
    public long seq_invoke() {
        return LongStream.range(0, size)
                .map(m1)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_invoke() {
        return LongStream.range(0, size).parallel()
                .map(m1)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_chain_111() {
        return LongStream.range(0, size)
                .map(m1)
                .map(m1)
                .map(m1)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_chain_111() {
        return LongStream.range(0, size).parallel()
                .map(m1)
                .map(m1)
                .map(m1)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_chain_123() {
        return LongStream.range(0, size)
                .map(m1)
                .map(m2)
                .map(m3)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_chain_123() {
        return LongStream.range(0, size).parallel()
                .map(m1)
                .map(m2)
                .map(m3)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

}
