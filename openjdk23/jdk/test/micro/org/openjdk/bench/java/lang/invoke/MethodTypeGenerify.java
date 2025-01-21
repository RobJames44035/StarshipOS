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

import java.lang.invoke.MethodType;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark assesses the MethodType.erase() performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodTypeGenerify {

    private MethodType mt;

    @Setup
    public void setup() {
        mt = MethodType.methodType(void.class, int.class, Integer.class);
    }

    @Benchmark
    public MethodType testGeneric() {
        return mt.generic();
    }

    @Benchmark
    public MethodType testWrapErase() {
        return mt.wrap().erase();
    }

    @Benchmark
    public MethodType testErase() {
        return mt.erase();
    }

    @Benchmark
    public MethodType testWrap() {
        return mt.wrap();
    }

    @Benchmark
    public MethodType testUnwrap() {
        return mt.unwrap();
    }

}
