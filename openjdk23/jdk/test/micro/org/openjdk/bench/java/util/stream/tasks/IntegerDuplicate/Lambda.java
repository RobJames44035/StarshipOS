/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.tasks.IntegerDuplicate;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * This benchmark assesses flatMap() performance.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class Lambda {

    /**
     * Implementation notes:
     *   - parallel versions need to use special sink to get the values
     */

    private IntegerDuplicateProblem problem;

    @Setup
    public void setup() {
        problem = new IntegerDuplicateProblem();
    }

    @Benchmark
    public long lambda_seq_inner() {
        return Arrays.stream(problem.get()).flatMap(k -> Collections.nCopies(2, k).stream())
                .collect(LongAdder::new, LongAdder::add, (la1, la2) -> la1.add(la2.sum())).sum();
    }

    @Benchmark
    public long lambda_par_inner() {
        return Arrays.stream(problem.get()).parallel().flatMap(k -> Collections.nCopies(2, k).stream())
                .collect(LongAdder::new, LongAdder::add, (la1, la2) -> la1.add(la2.sum())).sum();
    }

}
