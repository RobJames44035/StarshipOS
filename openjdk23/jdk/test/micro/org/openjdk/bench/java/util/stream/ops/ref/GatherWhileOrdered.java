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
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Gatherer;
import static org.openjdk.bench.java.util.stream.ops.ref.BenchmarkGathererImpls.takeWhile;

/**
 * Benchmark for comparing the built-in takeWhile-operation with the Gatherer-based takeWhile-operation for ordered streams.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 7, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class GatherWhileOrdered {

    /**
     * Implementation notes:
     *   - parallel version requires thread-safe sink, we use the same for sequential version for better comparison
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     */

    @Param("100000")
    private int size;

    @Param({"0", "49999", "99999"})
    private int find;

    private Predicate<Long> predicate;

    private Gatherer<Long, ?, Long> gather_takeWhile;

    private Long[] cachedInputArray;

    @Setup
    public void setup() {
        final int limit = find;

        cachedInputArray = new Long[size];
        for(int i = 0;i < size;++i)
            cachedInputArray[i] = Long.valueOf(i);

        predicate = new Predicate<Long>() {
            @Override
            public boolean test(Long v) {
                return v < limit;
            }
        };

        gather_takeWhile = takeWhile(predicate);
    }

    @Benchmark
    public long seq_invoke_baseline() {
        return Arrays.stream(cachedInputArray).takeWhile(predicate)
                         .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_invoke_gather() {
        return Arrays.stream(cachedInputArray).gather(takeWhile(predicate))
                         .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long seq_invoke_gather_preallocated() {
        return Arrays.stream(cachedInputArray).gather(gather_takeWhile)
                         .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_invoke_baseline() {
        return Arrays.stream(cachedInputArray).parallel().takeWhile(predicate)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_invoke_gather() {
        return Arrays.stream(cachedInputArray).parallel().gather(takeWhile(predicate))
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }

    @Benchmark
    public long par_invoke_gather_preallocated() {
        return Arrays.stream(cachedInputArray).parallel().gather(gather_takeWhile)
                .collect(LongAccumulator::new, LongAccumulator::add, LongAccumulator::merge).get();
    }
}
