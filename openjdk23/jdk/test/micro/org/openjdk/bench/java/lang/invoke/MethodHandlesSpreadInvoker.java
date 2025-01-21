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
 * Benchmark assesses MethodHandles.spreadInvoker() performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandlesSpreadInvoker {

    /**
     * Implementation notes:
     *   - calling static method to have consistent arg list without receiver type
     *   - using volatile ints as arguments to prevent opportunistic optimizations
     *   - using Integers to limit argument conversion costs
     *   - tested method should perform no worse than the baseline
     */

    private MethodHandle mhOrig;
    private MethodHandle mnInvoke;

    private volatile Integer arg1 = 42;
    private volatile Integer arg2 = 43;
    private volatile Integer arg3 = 44;
    private Object[] cachedArgs;

    @Setup
    public void setup() throws Throwable {
        cachedArgs = new Integer[]{arg1, arg2, arg3};

        MethodType mt = MethodType.methodType(int.class, Integer.class, Integer.class, Integer.class);
        mhOrig = MethodHandles.lookup().findStatic(MethodHandlesInsertArguments.class, "doWork", mt);
        mnInvoke = MethodHandles.spreadInvoker(mt, 0);
    }

    @Benchmark
    public int baselineOrig() throws Throwable {
        return (int) mhOrig.invokeExact(arg1, arg2, arg3);
    }

    @Benchmark
    public int testInvoker() throws Throwable {
        return (int) mnInvoke.invokeExact(mhOrig, new Object[] { arg1, arg2, arg3 });
    }

    @Benchmark
    public int testInvokerCached() throws Throwable {
        return (int) mnInvoke.invokeExact(mhOrig, cachedArgs);
    }

    public static int doWork(Integer a, Integer b, Integer c) {
        return 31*(31*(31*a + b) + c);
    }
}
