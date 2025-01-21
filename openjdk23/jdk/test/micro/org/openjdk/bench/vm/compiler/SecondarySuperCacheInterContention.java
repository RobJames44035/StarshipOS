/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 300, timeUnit = TimeUnit.MILLISECONDS)
@Fork(value = 3, jvmArgs = {"-XX:+TieredCompilation", "-XX:TieredStopAtLevel=1"})
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Threads(Threads.MAX)
@State(Scope.Benchmark)
public class SecondarySuperCacheInterContention {

    // This test targets C1 specifically, to enter the interesting code path
    // without heavily optimizing compiler like C2 optimizing based on profiles,
    // or folding the instanceof checks.

    // The test verifies what happens on unhappy path, when we contend a lot over
    // the secondary super cache, where different threads want to update the cache
    // with different value. In tihs test, every thread comes with its own stable
    // cached value. Meaning, this tests the INTER-thread contention.

    interface IA {}
    interface IB {}
    class B {}
    class C1 extends B implements IA, IB {}
    class C2 extends B implements IA, IB {}

    volatile B o1, o2;

    @Setup
    public void setup() {
        o1 = new C1();
        o2 = new C2();
    }

    @Benchmark
    @OperationsPerInvocation(2)
    @Group("test")
    @GroupThreads(1)
    public void t1(Blackhole bh) {
        bh.consume(o1 instanceof IA);
        bh.consume(o2 instanceof IA);
    }

    @Benchmark
    @OperationsPerInvocation(2)
    @Group("test")
    @GroupThreads(1)
    public void t2(Blackhole bh) {
        bh.consume(o1 instanceof IB);
        bh.consume(o2 instanceof IB);
    }

}
