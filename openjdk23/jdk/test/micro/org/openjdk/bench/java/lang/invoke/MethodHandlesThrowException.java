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
import java.util.concurrent.TimeUnit;

/**
 * Benchmark assesses MethodHandles.throwException() performance
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class MethodHandlesThrowException {

    /**
     * Implementation notes:
     *   - exceptions have a thorough call back to benchmark instance to prevent elimination (against dumb JITs)
     *   - testing in plain and cached modes
     *   - baselines do the same thing, but in pure Java
     */

    public int flag;
    private MethodHandle mh;
    private MyException cachedException;

    @Setup
    public void setup() {
        flag = 42;
        cachedException = new MyException();
        mh = MethodHandles.throwException(void.class, MyException.class);
    }

    @Benchmark
    public int baselineThrow() {
        try {
            throw cachedException;
        } catch (MyException my) {
            return my.getFlag();
        }
    }

    @Benchmark
    public int testInvoke() throws Throwable {
        try {
            mh.invoke(cachedException);
            throw new IllegalStateException("Should throw exception");
        } catch (MyException my) {
            return my.getFlag();
        }
    }

    @Benchmark
    public MethodHandle interCreate() {
        return MethodHandles.throwException(void.class, MyException.class);
    }

    public class MyException extends Exception {

        public int getFlag() {
            return flag;
        }
    }

}
