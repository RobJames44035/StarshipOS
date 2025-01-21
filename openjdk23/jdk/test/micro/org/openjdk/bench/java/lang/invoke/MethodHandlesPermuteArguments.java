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
 * Benchmark assesses MethodHandles.permuteArguments() performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandlesPermuteArguments {

    /**
     * Implementation notes:
     *   - calling static method to have consistent arg list without receiver type
     *   - using volatile ints as arguments to prevent opportunistic optimizations
     *   - using Integers to limit argument conversion costs
     */

    private MethodHandle mhOrig;
    private MethodHandle mhNoReorder;
    private MethodHandle mhReverse;
    private MethodHandle mhShift;

    private volatile Integer arg1 = 42;
    private volatile Integer arg2 = 43;
    private volatile Integer arg3 = 44;

    @Setup
    public void setup() throws Throwable {
        MethodType mt = MethodType.methodType(int.class, Integer.class, Integer.class, Integer.class);
        mhOrig = MethodHandles.lookup().findStatic(MethodHandlesPermuteArguments.class, "doWork", mt);
        mhNoReorder = MethodHandles.permuteArguments(mhOrig, mt, 0, 1, 2);
        mhReverse = MethodHandles.permuteArguments(mhOrig, mt, 2, 1, 0);
        mhShift = MethodHandles.permuteArguments(mhOrig, mt, 2, 0, 1);
    }

    @Benchmark
    public int baselineRaw() throws Throwable {
        return doWork(arg1, arg2, arg3);
    }

    @Benchmark
    public int invoke_Raw() throws Throwable {
        return (int) mhOrig.invokeExact(arg1, arg2, arg3);
    }

    @Benchmark
    public int invoke_NoReorder() throws Throwable {
        return (int) mhNoReorder.invokeExact(arg1, arg2, arg3);
    }

    @Benchmark
    public int invoke_Reverse() throws Throwable {
        return (int) mhReverse.invokeExact(arg1, arg2, arg3);
    }

    @Benchmark
    public int invoke_Shift() throws Throwable {
        return (int) mhShift.invokeExact(arg1, arg2, arg3);
    }

    public static int doWork(Integer a, Integer b, Integer c) {
        return 31*(31*(31*a + b) + c);
    }

}
