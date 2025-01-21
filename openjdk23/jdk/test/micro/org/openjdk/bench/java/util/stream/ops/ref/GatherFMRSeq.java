/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
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

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Arrays;
import java.util.stream.Gatherer;
import static org.openjdk.bench.java.util.stream.ops.ref.BenchmarkGathererImpls.filter;
import static org.openjdk.bench.java.util.stream.ops.ref.BenchmarkGathererImpls.map;

/**
 * Benchmark for filter+map+reduce operations implemented as Gatherer, with the default map implementation of Stream as baseline.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 7, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class GatherFMRSeq {

    @Param({"10","100","1000000"})
    private int size;

    private Function<Long, Long> squared;
    private Predicate<Long> evens;

    private Gatherer<Long, ?, Long> gathered;
    private Gatherer<Long, ?, Long> ga_map_squared;
    private Gatherer<Long, ?, Long> ga_filter_evens;

    private Long[] cachedInputArray;

    @Setup
    public void setup() {
        cachedInputArray = new Long[size];
        for(int i = 0;i < size;++i)
            cachedInputArray[i] = Long.valueOf(i);

        squared = new Function<Long, Long>() { @Override public Long apply(Long l) { return l*l; } };
        evens = new Predicate<Long>() { @Override public boolean test(Long l) {
            return l % 2 == 0;
        } };

        ga_map_squared = map(squared);
        ga_filter_evens = filter(evens);

        gathered = ga_filter_evens.andThen(ga_map_squared);
    }

    @Benchmark
    public long seq_fmr_baseline() {
        return Arrays.stream(cachedInputArray)
                .filter(evens)
                .map(squared)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_fmr_gather() {
        return Arrays.stream(cachedInputArray)
                .gather(filter(evens))
                .gather(map(squared))
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_fmr_gather_preallocated() {
        return Arrays.stream(cachedInputArray)
                .gather(ga_filter_evens)
                .gather(ga_map_squared)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_fmr_gather_composed() {
        return Arrays.stream(cachedInputArray)
                .gather(filter(evens).andThen(map(squared)))
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_fmr_gather_composed_preallocated() {
        return Arrays.stream(cachedInputArray)
                .gather(filter(evens).andThen(map(squared)))
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_fmr_gather_precomposed() {
        return Arrays.stream(cachedInputArray)
                .gather(gathered)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }
}
