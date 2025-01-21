/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.tasks.DictionaryWordValue;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Bulk scenario: searching max "wordValue" through the dictionary (array of strings).
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class Lambda {

    @Setup(Level.Trial)
    public void loadData() {
        // cause classload and problem initialization
        DictionaryProblem.class.getName();
    }

    public static Integer maxInt(Integer l, Integer r) {
        return l > r ? l : r;
    }

    @Benchmark
    public int bulk_seq_lambda() {
        return Arrays.stream(DictionaryProblem.get())
                .map(s -> DictionaryProblem.wordValue(s))
                .reduce(0, (l, r) -> l > r ? l : r);
    }

    @Benchmark
    public int bulk_seq_mref() {
        return Arrays.stream(DictionaryProblem.get())
                .map(DictionaryProblem::wordValue)
                .reduce(0, Lambda::maxInt);
    }

    @Benchmark
    public int bulk_par_lambda() {
        return Arrays.stream(DictionaryProblem.get()).parallel()
                .map(s -> DictionaryProblem.wordValue(s))
                .reduce(0, (l, r) -> l > r ? l : r);
    }

    @Benchmark
    public int bulk_par_mref() {
        return Arrays.stream(DictionaryProblem.get()).parallel()
                .map(DictionaryProblem::wordValue)
                .reduce(0, Lambda::maxInt);
    }

}
