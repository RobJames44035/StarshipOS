/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package org.openjdk.bench.java.lang.reflect;

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
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark for regression in native method invocation.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class NativeMethodInvoke {

    private Method objectHashCode;
    private Method threadCurrentThread;

    private Object[] objects;

    @Setup
    public void setup() throws ReflectiveOperationException {
        objects = new Object[]{
                1, 5L,
                5.6d, 23.11f,
                Boolean.TRUE, 'd'
        };

        objectHashCode = Object.class.getDeclaredMethod("hashCode");
        threadCurrentThread = Thread.class.getDeclaredMethod("currentThread");
    }

    @Benchmark
    public void objectHashCode(Blackhole bh) throws ReflectiveOperationException {
        for (var obj : objects) {
            bh.consume(objectHashCode.invoke(obj));
        }
    }

    @Benchmark
    public Object threadCurrentThread() throws ReflectiveOperationException {
        return threadCurrentThread.invoke(null);
    }
}
