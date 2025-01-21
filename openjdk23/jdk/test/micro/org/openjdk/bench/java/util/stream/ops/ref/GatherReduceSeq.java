/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
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
import java.util.function.BinaryOperator;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Gatherer;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.openjdk.bench.java.util.stream.ops.ref.BenchmarkGathererImpls.findFirst;
import static org.openjdk.bench.java.util.stream.ops.ref.BenchmarkGathererImpls.reduce;

/**
 * Benchmark for comparing the built-in reduce() operation with the Gatherer-based reduce-operation.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 7, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class GatherReduceSeq {

    /**
     * Implementation notes:
     *   - parallel version requires thread-safe sink, we use the same for sequential version for better comparison
     *   - operations are explicit inner classes to untangle unwanted lambda effects
     */

    @Param({"100", "100000"})
    private int size;

    private BinaryOperator<Long> op1;

    private Gatherer<Long, ?, Long> gather_op1;

    private Long[] cachedInputArray;

    @Setup
    public void setup() {
        cachedInputArray = new Long[size];
        for(int i = 0;i < size;++i)
            cachedInputArray[i] = Long.valueOf(i);

        op1 = new BinaryOperator<Long>() {
            @Override
            public Long apply(Long l, Long r) {
                return (l < r) ? r : l;
            }
        };

        gather_op1 = reduce(op1);
    }

    @Benchmark
    public long seq_invoke_baseline() {
        return Arrays.stream(cachedInputArray).reduce(op1).get();
    }

    @Benchmark
    public long seq_invoke_gather() {
        return Arrays.stream(cachedInputArray).gather(reduce(op1)).collect(findFirst()).get();
    }

    @Benchmark
    public long seq_invoke_gather_preallocated() {
        return Arrays.stream(cachedInputArray).gather(gather_op1).collect(findFirst()).get();
    }
}
