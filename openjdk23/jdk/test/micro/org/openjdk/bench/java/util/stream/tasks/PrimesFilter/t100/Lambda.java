/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.tasks.PrimesFilter.t100;

import org.openjdk.bench.java.util.stream.tasks.PrimesFilter.PrimesProblem;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * This benchmark evaluates find all prime numbers in a range.
 *
 * filter()...into() actions are benchmarked.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class Lambda {

    private final long RANGE_START  = 1000_000_000_000_000L;
    private final long RANGE_END = RANGE_START + 100;

    @Benchmark
    public List<Long> bulk_seq_lambda() {
        return LongStream.range(RANGE_START, RANGE_END).boxed().filter(n -> PrimesProblem.isPrime(n)).collect(Collectors.<Long>toList());
    }

    @Benchmark
    public List<Long> bulk_seq_methodRef() {
        return LongStream.range(RANGE_START, RANGE_END).boxed().filter(PrimesProblem::isPrime).collect(Collectors.<Long>toList());
    }

    @Benchmark
    public List<Long> bulk_par_lambda() {
        return LongStream.range(RANGE_START, RANGE_END).parallel().boxed().filter(n -> PrimesProblem.isPrime(n)).collect(Collectors.<Long>toList());
    }

    @Benchmark
    public List<Long> bulk_par_methodRef() {
        return LongStream.range(RANGE_START, RANGE_END).parallel().boxed().filter(PrimesProblem::isPrime).collect(Collectors.<Long>toList());
    }

    @Benchmark
    public List<Long> bulk_parseq_lambda() {
        return LongStream.range(RANGE_START, RANGE_END).parallel().boxed().filter(n -> PrimesProblem.isPrime(n)).sequential().collect(Collectors.<Long>toList());
    }

    @Benchmark
    public List<Long> bulk_parseq_methodRef() {
        return LongStream.range(RANGE_START, RANGE_END).parallel().boxed().filter(PrimesProblem::isPrime).sequential().collect(Collectors.<Long>toList());
    }

}
