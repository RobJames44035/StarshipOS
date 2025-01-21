/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util.stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

/**
 * This benchmark is the golden benchmark for decompositions.
 * There are at least four parameters to juggle:
 *   - pool parallelism (P), controlled via -Djava.util.concurrent.ForkJoinUtils.pool.parallelism
 *   - problem size (N), controlled as benchmark param
 *   - operation cost (Q), controlled as benchmark param
 *   - number of clients (C), controlled via -t option in harness
 *
 * @author Aleksey Shipilev (aleksey.shipilev@oracle.com)
 */
@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class Decomposition {

    @Param("1000")
    private int N;

    @Param("1000")
    private int Q;

    @State(Scope.Thread)
    public static class Thinktime {
        @Param("10")
        private int S;

        @Setup(Level.Invocation)
        public void sleep() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(S);
        }
    }

    @Benchmark
    public long saturated_sequential() throws InterruptedException {
        return LongStream.range(1, N).filter(k -> doWork(k, Q)).sum();
    }

    @Benchmark
    public long thinktime_sequential(Thinktime t) throws InterruptedException {
        return LongStream.range(1, N).filter(k -> doWork(k, Q)).sum();
    }

    @Benchmark
    public long saturated_parallel() throws InterruptedException {
        return LongStream.range(1, N).parallel().filter(k -> doWork(k, Q)).sum();
    }

    @Benchmark
    public long thinktime_parallel(Thinktime t) throws InterruptedException {
        return LongStream.range(1, N).parallel().filter(k -> doWork(k, Q)).sum();
    }

    /**
     * Make some work.
     * This method have a couple of distinguishable properties:
     *   - the run time is linear with Q
     *   - the computation is dependent on input, preventing common reductions
     *   - the returned result is dependent on loop result, preventing dead code elimination
     *   - the returned result is almost always false
     *
     * This code uses inlined version of ThreadLocalRandom.next() to mitigate the edge effects
     * of acquiring TLR every single call.
     *
     * @param input input
     * @return result
     */
    public static boolean doWork(long input, long count) {
        long t = input;
        for (int i = 0; i < count; i++) {
            t += (t * 0x5DEECE66DL + 0xBL) & (0xFFFFFFFFFFFFL);
        }
        return (t == 0);
    }

}
