/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.ops.ref;

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

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

/**
 * Benchmark for sorted() operation.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class Sorted {

    /**
     * Implementation notes:
     *   - parallel version requires thread-safe sink, we use the same for sequential version for better comparison
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     *   - consecutive sorts should reuse the knowledge that stream was sorted already
     */

    @Param("100000")
    private int size;

    private Comparator<Long> cmp;

    @Setup
    public void setup() {
        cmp = new Comparator<Long>() {
            @Override
            public int compare(Long x, Long y) {
                return (x > y) ? 1 : (x < y ? -1 : 0);
            }
        };
    }

    @Benchmark
    public long seq_invoke() {
        return LongStream.range(0, size)
                .boxed()
                .sorted(cmp)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_invoke() {
        return LongStream.range(0, size).parallel()
                .boxed()
                .sorted(cmp)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_chain() {
        return LongStream.range(0, size)
                .boxed()
                .sorted(cmp)
                .sorted(cmp)
                .sorted(cmp)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_chain() {
        return LongStream.range(0, size).parallel()
                .boxed()
                .sorted(cmp)
                .sorted(cmp)
                .sorted(cmp)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

}
