/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream.tasks.IntegerMax;

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

import java.util.concurrent.TimeUnit;

/**
 * Bulk scenario: search for max value in array.
 *
 * This test covers other interesting solutions for the original problem.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class Xtras {

    private IntegerMaxProblem problem;

    @Setup(Level.Trial)
    public void populateData(){
        problem = new IntegerMaxProblem();
    }


//    @Benchmark
//    public int bulk_seq_inner_pull() {
//        Stream<Integer> stream = Arrays.stream(problem.get());
//
//        // force pull traversal
//        Integer car = stream.iterator().next();
//        Integer cdr = stream.reduce(Integer.MIN_VALUE, new BinaryOperator<Integer>() {
//            @Override
//            public Integer apply(Integer l, Integer r) {
//                return l > r ? l : r;
//            }
//        });
//        return (car > cdr) ? car : cdr;
//    }
//
//    @Benchmark
//    public int bulk_par_inner_pull() {
//        Stream<Integer> stream = Arrays.parallelStream(problem.get());
//
//        // force pull traversal
//        Integer car = stream.iterator().next();
//        Integer cdr = stream.reduce(Integer.MIN_VALUE, new BinaryOperator<Integer>() {
//            @Override
//            public Integer apply(Integer l, Integer r) {
//                return l > r ? l : r;
//            }
//        });
//        return (car > cdr) ? car : cdr;
//    }

}
