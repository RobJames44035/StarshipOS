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
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

/**
 * Benchmark tests the pipeline fusion abilities.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class PipelineParMultiple {

    @Param("100000")
    private int size;

    @Benchmark
    public long bulk_into_anon() {
        return LongStream.range(0, size).parallel()
                .filter((x) -> true)
                .filter((x) -> true)
                .filter((x) -> true)
                .filter((x) -> true)
                .filter((x) -> true)
                .filter((x) -> true)
                .filter((x) -> true)
                .filter((x) -> false)
                .collect(LongAdder::new, LongAdder::add, (la1, la2) -> la1.add(la2.sum())).sum();
    }

    @Benchmark
    public long bulk_into_named() {
        LongPredicate t = (x) -> true;
        LongPredicate f = (x) -> false;
        return LongStream.range(0, size).parallel()
                .filter(t)
                .filter(t)
                .filter(t)
                .filter(t)
                .filter(t)
                .filter(t)
                .filter(t)
                .filter(f)
                .collect(LongAdder::new, LongAdder::add, (la1, la2) -> la1.add(la2.sum())).sum();
    }


    @Benchmark
    public long bulk_foreach_anon() {
        LongAdder adder = new LongAdder();
        LongStream.range(0, size).parallel().forEach((l) -> {
            if (((LongPredicate) (x) -> true).test(l))
            if (((LongPredicate) (x) -> true).test(l))
            if (((LongPredicate) (x) -> true).test(l))
            if (((LongPredicate) (x) -> true).test(l))
            if (((LongPredicate) (x) -> true).test(l))
            if (((LongPredicate) (x) -> true).test(l))
            if (((LongPredicate) (x) -> true).test(l))
            if (((LongPredicate) (x) -> false).test(l))
                adder.add(l);
        });
        return adder.sum();
    }


    @Benchmark
    public long bulk_foreach_named() {
        LongAdder adder = new LongAdder();
        LongPredicate t = (x) -> true;
        LongPredicate f = (x) -> false;
        LongStream.range(0, size).parallel().forEach((l) -> {
            if (t.test(l))
            if (t.test(l))
            if (t.test(l))
            if (t.test(l))
            if (t.test(l))
            if (t.test(l))
            if (t.test(l))
            if (f.test(l))
                adder.add(l);
        });
        return adder.sum();
    }

    @Benchmark
    public long bulk_ifs() {
        LongAdder adder = new LongAdder();
        LongStream.range(0, size).parallel().forEach((l) -> {
            if (true)
            if (true)
            if (true)
            if (true)
            if (true)
            if (true)
            if (true)
            if (false)
                adder.add(l);
        });
        return adder.sum();
    }

}
