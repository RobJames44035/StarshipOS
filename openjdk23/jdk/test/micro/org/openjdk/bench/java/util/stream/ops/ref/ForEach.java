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
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.stream.LongStream;

/**
 * Benchmark for forEach() operations.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class ForEach {

    /**
     * Implementation notes:
     *   - parallel version requires thread-safe sink, we use the same for sequential version for better comparison
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     */

    @Param("100000")
    private int size;

    private LongAdder sink;
    private Consumer<Long> b1, b2, b3;

    @Setup
    public void setup() {
        sink = new LongAdder();
        b1 = new Consumer<Long>() {
            @Override
            public void accept(Long v) {
                sink.add(v);
            }
        };
        b2 = new Consumer<Long>() {
            @Override
            public void accept(Long v) {
                sink.add(v);
            }
        };
        b3 = new Consumer<Long>() {
            @Override
            public void accept(Long v) {
                sink.add(v);
            }
        };
    }

    @Benchmark
    public long seq_invoke() {
        LongStream.range(0, size).boxed().forEach(b1);
        return sink.sum();
    }

    @Benchmark
    public long seq_chain111() {
        LongStream.range(0, size).boxed().forEach(b1);
        LongStream.range(0, size).boxed().forEach(b1);
        LongStream.range(0, size).boxed().forEach(b1);
        return sink.sum();
    }

    @Benchmark
    public long seq_chain123() {
        LongStream.range(0, size).boxed().forEach(b1);
        LongStream.range(0, size).boxed().forEach(b2);
        LongStream.range(0, size).boxed().forEach(b3);
        return sink.sum();
    }

    @Benchmark
    public long par_invoke() {
        LongStream.range(0, size).parallel().boxed().forEach(b1);
        return sink.sum();
    }

    @Benchmark
    public long par_chain111() {
        LongStream.range(0, size).parallel().boxed().forEach(b1);
        LongStream.range(0, size).parallel().boxed().forEach(b1);
        LongStream.range(0, size).parallel().boxed().forEach(b1);
        return sink.sum();
    }

    @Benchmark
    public long par_chain123() {
        LongStream.range(0, size).parallel().boxed().forEach(b1);
        LongStream.range(0, size).parallel().boxed().forEach(b2);
        LongStream.range(0, size).parallel().boxed().forEach(b3);
        return sink.sum();
    }

}
