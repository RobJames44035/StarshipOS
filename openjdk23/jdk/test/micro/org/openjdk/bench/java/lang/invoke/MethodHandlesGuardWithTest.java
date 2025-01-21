/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.lang.invoke;

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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark assesses MethodHandles.guardWithTest() performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandlesGuardWithTest {

    /**
     * Implementation notes:
     *   - using volatile ints as arguments to prevent opportunistic optimizations
     *   - using Integers to limit argument conversion costs
     *   - tested method should perform no worse than the baseline
     */

    private MethodHandle mhWork1;
    private MethodHandle mhWork2;
    private MethodHandle guard;
    private boolean choice;

    private volatile Integer arg1 = 42;
    private volatile Integer arg2 = 43;
    private volatile Integer arg3 = 44;

    @Setup
    public void setup() throws Throwable {
        MethodType mt = MethodType.methodType(int.class, Integer.class, Integer.class, Integer.class);
        mhWork1 = MethodHandles.lookup().findVirtual(MethodHandlesGuardWithTest.class, "doWork1", mt);
        mhWork2 = MethodHandles.lookup().findVirtual(MethodHandlesGuardWithTest.class, "doWork2", mt);

        MethodHandle chooser = MethodHandles.lookup().findVirtual(MethodHandlesGuardWithTest.class, "chooser", MethodType.methodType(boolean.class));
        guard = MethodHandles.guardWithTest(chooser, mhWork1, mhWork2);
    }

    @Benchmark
    public int baselineManual() throws Throwable {
        if (choice) {
            return (int) mhWork1.invokeExact(this, arg1, arg2, arg3);
        } else {
            return (int) mhWork2.invokeExact(this, arg1, arg2, arg3);
        }
    }

    @Benchmark
    public int testInvoke() throws Throwable {
        return (int) guard.invoke(this, arg1, arg2, arg3);
    }

    public boolean chooser() {
        choice = !choice;
        return choice;
    }

    public int doWork1(Integer a, Integer b, Integer c) {
        return 31*(31*(31*a + b) + c);
    }

    public int doWork2(Integer a, Integer b, Integer c) {
        return 31*(31*(31*c + b) + a);
    }
}
