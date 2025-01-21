/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.tasks.IntegerSum;

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
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;

/**
 * Bulk scenario: compute the sum of Integer array.
 *
 * This test covers bulk infrastructure only. Refer to other tests for lambda-specific cases.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class Bulk {

    private IntegerSumProblem problem;

    @Setup(Level.Trial)
    public void populateData(){
        problem = new IntegerSumProblem();
    }

    @Benchmark
    public int hm_seq() {
        int sum = 0;
        for (int v : problem.get()) {
            sum += v;
        }
        return sum;
    }

    @Benchmark
    public int hm_par() {
        return new SumIntTask(problem.get()).invoke();
    }

    @Benchmark
    public int bulk_seq_inner() {
        return Arrays.stream(problem.get()).reduce(0, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer left, Integer right) {
                return left + right;
            }
        });
    }

    @Benchmark
    public int bulk_par_inner() {
        return Arrays.stream(problem.get()).parallel().reduce(0, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer left, Integer right) {
                return left + right;
            }
        });
    }

    static class SumIntTask extends RecursiveTask<Integer> {
        private static final int FORK_LIMIT = 1000;
        final Integer[] data;
        final int start, end;

        SumIntTask(Integer[] data) {
            this(data, 0, data.length);
        }

        SumIntTask(Integer[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int size = end - start;
            if (size > FORK_LIMIT) {
                int mid = start + size / 2;
                SumIntTask t1 = new SumIntTask(data, start, mid);
                SumIntTask t2 = new SumIntTask(data, mid, end);
                t1.fork();
                return t2.invoke() + t1.join();
            } else {
                int sum = 0;
                for (int i = start; i < end; i++) {
                    sum += data[i];
                }
                return sum;
            }
        }
    }

}
