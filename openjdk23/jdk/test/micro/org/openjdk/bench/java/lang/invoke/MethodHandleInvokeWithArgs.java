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
 * Benchmark to assess basic MethodHandle performance.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandleInvokeWithArgs {

    /*
     * Implementation notes:
     *   - this is a very basic test, does not do any parameter conversion (in fact, no parameters at all)
     *   - baselines include calling method directly, and doing the same via reflection
     *   - baselineRaw is known to be super-fast with good inlining
     */

    private int i;
    private static MethodHandle mh;

    private Integer a = 42;

    @Setup
    public void setup() throws Throwable {
        mh = MethodHandles.lookup().findVirtual(MethodHandleInvokeWithArgs.class, "doWork", MethodType.methodType(int.class, Integer.class));
    }

    @Benchmark
    public int baselineRaw() throws Throwable {
        return doWork(a);
    }

    @Benchmark
    public int baselineInvoke() throws Throwable {
        return (int) mh.invoke(this, a);
    }

    @Benchmark
    public int baselineInvokeExact() throws Throwable {
        return (int) mh.invokeExact(this, a);
    }

    @Benchmark
    public int testInvoke_WithArguments() throws Throwable {
        return (int) mh.invokeWithArguments(this, a);
    }

    public int doWork(Integer a) {
        return i += a;
    }

}
