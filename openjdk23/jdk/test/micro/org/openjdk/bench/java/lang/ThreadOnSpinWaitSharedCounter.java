/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.lang;

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
import java.util.concurrent.atomic.AtomicInteger;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 3)
public class ThreadOnSpinWaitSharedCounter {
    @Param({"1000000"})
    public int maxNum;

    @Param({"4"})
    public int threadCount;

    AtomicInteger theCounter;

    Thread threads[];

    void work() {
        for (;;) {
            int prev = theCounter.get();
            if (prev >= maxNum) {
                break;
            }
            if (theCounter.compareAndExchange(prev, prev + 1) != prev) {
                Thread.onSpinWait();
            }
        }
    }

    @Setup(Level.Trial)
    public void foo() {
        theCounter = new AtomicInteger();
    }

    @Setup(Level.Invocation)
    public void setup() {
        theCounter.set(0);
        threads = new Thread[threadCount];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(this::work);
        }
    }

    @Benchmark
    public void trial() throws Exception {
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
}
