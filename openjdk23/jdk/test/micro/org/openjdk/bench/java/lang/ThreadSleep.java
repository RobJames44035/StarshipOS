/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package org.openjdk.bench.java.lang;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@State(Scope.Benchmark)
public class ThreadSleep {

    @Param({"0",
            "1",
            "10",
            "100",
            "1000",
            "10000",
            "100000",
            "1000000",
            "10000000",
            "100000000",
            "1000000000"})
    private int sleep;

    private long millis;
    private int nanos;

    @Setup
    public void setup() {
        millis = TimeUnit.NANOSECONDS.toMillis(sleep);
        nanos = (int)(sleep - TimeUnit.MILLISECONDS.toNanos(millis));
    }

    @Benchmark
    public void millis() throws InterruptedException {
        Thread.sleep(millis);
    }

    @Benchmark
    public void millisNanos() throws InterruptedException {
        Thread.sleep(millis, nanos);
    }

}
