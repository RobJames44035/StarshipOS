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
 * Microbenchmark assesses MethodHandle.asSpreader() performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandleAsSpreader {

    /*
    * Implementation notes:
    *   - simple five parameter method is being called using MH.asSpreader
    *   - baselineMH invokes method directly without going through spreader
    */

    public int i;
    private static MethodHandle mh;
    private static MethodHandle spreaderMH;
    private static int[] cachedArgs;

    @Setup
    public void setup() throws IllegalAccessException, NoSuchMethodException {
        mh = MethodHandles.lookup().findVirtual(MethodHandleAsSpreader.class, "doWork", MethodType.methodType(void.class, int.class, int.class, int.class, int.class, int.class));
        spreaderMH = mh.asSpreader(int[].class, 5);
        cachedArgs = new int[]{1, 2, 3, 4, 5};
    }

    @Benchmark
    public void baselineMH() throws Throwable {
        mh.invokeExact(this, 1, 2, 3, 4, 5);
    }

    @Benchmark
    public MethodHandle testCreate() {
        return mh.asSpreader(int[].class, 5);
    }

    @Benchmark
    public void testSpreader() throws Throwable {
        spreaderMH.invokeExact(this, new int[] { 1, 2, 3, 4, 5 });
    }

    @Benchmark
    public void testSpreaderCached() throws Throwable {
        spreaderMH.invokeExact(this, cachedArgs);
    }

    public void doWork(int arg1, int arg2, int arg3, int arg4, int arg5) {
        i += (arg1 + arg2 + arg3 + arg4 + arg5);
    }

}
