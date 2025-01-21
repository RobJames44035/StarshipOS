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
 * Benchmark assesses runtime argument conversion performance for MethodHandles.
 * This particular test checks the widening conversion.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandleConvertWidening {

    /*
     * Implementation notes:
     *
     * Baseline is invokeExact call, which presumably measures the performance without argument conversion.
     *
     * The test is subdivided into three subtests, gradually doing more work:
     *   - 1_Convert: calls MH.asType to do actual conversion
     *   - 2_MTConvert: instantiates MT, and then calls MH.asType to do actual conversion
     *   - 3_Call: calls MH.invoke, requesting argument conversion
     *
     * Calling static method as to minimize virtual dispatch overheads.
     */

    private long valueLong;
    private int valueInt;

    private MethodHandle mh;
    private MethodType newType;

    @Setup
    public void setup() throws Throwable {
        mh = MethodHandles.lookup().findStatic(MethodHandleConvertWidening.class, "target", MethodType.methodType(long.class, long.class));
        newType = MethodType.methodType(long.class, int.class);
        valueInt = 42;
        valueLong = 42L;
    }

    @Benchmark
    public long baselineExact() throws Throwable {
        return (long) mh.invokeExact(valueLong);
    }

    @Benchmark
    public MethodHandle test_1_Convert() throws Throwable {
        return mh.asType(newType);
    }

    @Benchmark
    public MethodHandle test_2_MTConvert() throws Throwable {
        return mh.asType(MethodType.methodType(long.class, int.class));
    }

    @Benchmark
    public long test_3_Call() throws Throwable {
        return (long) mh.invoke(valueInt);
    }

    public static long target(long value) {
        return value + 1;
    }

}
