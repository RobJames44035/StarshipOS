/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.pipeline;

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

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

/**
 * Benchmark for forEach()/iterator()/into() operations;
 * Testing which one is faster for semantically-equvalent operations.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class Terminal {

    /**
     * Implementation notes:
     *   - parallel version requires thread-safe sink, we use the same for sequential version for better comparison
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     */

    @Param("100000")
    private int size;

    private LongAdder sink;
    private LongConsumer block;

    @Setup
    public void setup() {
        sink = new LongAdder();
        block = new LongConsumer() {
            @Override
            public void accept(long v) {
                sink.add(v);
            }
        };
    }

    @Benchmark
    public long baseline_prim_acc() {
        long s = 0;
        for (long l = 0L; l < size; l++) {
            s += l;
        }
        sink.add(s);
        return sink.sum();
    }

    @Benchmark
    public long baseline_prim_sink() {
        for (long l = 0L; l < size; l++) {
            sink.add(l);
        }
        return sink.sum();
    }

    @Benchmark
    public long baseline_iterator_acc() {
        long s = 0;
        for (Iterator<Long> iterator = LongStream.range(0, size).boxed().iterator(); iterator.hasNext(); ) {
            Long l = iterator.next();
            s += l;
        }
        sink.add(s);
        return sink.sum();
    }

    @Benchmark
    public long baseline_iterator_sink() {
        for (Iterator<Long> iterator = LongStream.range(0, size).boxed().iterator(); iterator.hasNext(); ) {
            sink.add(iterator.next());
        }
        return sink.sum();
    }

    @Benchmark
    public long seq_iterator() {
        Iterator<Long> i = LongStream.range(0, size).boxed().iterator();
        while (i.hasNext()) {
            sink.add(i.next());
        }
        return sink.sum();
    }

    @Benchmark
    public long par_iterator() {
        Iterator<Long> i = LongStream.range(0, size).parallel().boxed().iterator();
        while (i.hasNext()) {
            sink.add(i.next());
        }
        return sink.sum();
    }

    @Benchmark
    public long seq_forEach() {
        LongStream.range(1, size).forEach(block);
        return sink.sum();
    }

    @Benchmark
    public long par_forEach() {
        LongStream.range(1, size).parallel().forEach(block);
        return sink.sum();
    }

    @Benchmark
    public long seq_into() {
        return LongStream.range(1, size)
                .collect(LongAdder::new, LongAdder::add, (la1, la2) -> la1.add(la2.sum())).sum();
    }

    @Benchmark
    public long par_into() {
        return LongStream.range(1, size).parallel()
                .collect(LongAdder::new, LongAdder::add, (la1, la2) -> la1.add(la2.sum())).sum();
    }

}
