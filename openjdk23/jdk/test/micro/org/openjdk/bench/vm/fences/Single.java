/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.vm.fences;

import org.openjdk.jmh.annotations.*;

import java.lang.invoke.VarHandle;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Single {

    @Benchmark
    public void plain() {
        // Do nothing
    }

    @Benchmark
    public void loadLoad() {
        VarHandle.loadLoadFence();
    }

    @Benchmark
    public void storeStore() {
        VarHandle.storeStoreFence();
    }

    @Benchmark
    public void acquire() {
        VarHandle.acquireFence();
    }

    @Benchmark
    public void release() {
        VarHandle.releaseFence();
    }

    @Benchmark
    public void full() {
        VarHandle.fullFence();
    }

}
