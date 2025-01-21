/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.lang;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * Tests throwing exceptions.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class Throw {

    public static boolean alwaysTrue = true;
    private static Object nullObject = null;
    public Object useObject = new Object();

    @Benchmark
    public void throwSyncException(Blackhole bh) {
        try {
            throwingMethod();
        } catch (Exception ex) {
            bh.consume(useObject);
        }
    }

    @Benchmark
    public void throwASyncException(Blackhole bh) {
        try {
            throwNullpointer();
        } catch (Exception ex) {
            bh.consume(useObject);
        }
    }

    @Benchmark
    public void throwSyncExceptionUseException(Blackhole bh) {
        try {
            throwingMethod();
        } catch (Exception ex) {
            bh.consume(ex);
        }
    }

    @Benchmark
    public void throwSyncExceptionUseMessage(Blackhole bh) {
        try {
            throwingMethod();
        } catch (Exception ex) {
            bh.consume(ex.getMessage());
        }
    }

    @Benchmark
    public void throwSyncExceptionUseStacktrace(Blackhole bh) {
        try {
            throwingMethod();
        } catch (Exception ex) {
            bh.consume(ex.getStackTrace());
        }
    }

    @Benchmark
    public void throwWith16Frames(Blackhole bh) {
        try {
            throwingMethod(16);
        } catch (Exception ex) {
            bh.consume(useObject);
        }
    }

    @Benchmark
    public void throwWith32Frames(Blackhole bh) {
        try {
            throwingMethod(32);
        } catch (Exception ex) {
            bh.consume(useObject);
        }
    }

    @Benchmark
    public void throwWith64Frames(Blackhole bh) {
        try {
            throwingMethod(64);
        } catch (Exception ex) {
            bh.consume(useObject);
        }
    }

    public void throwingMethod() throws Exception {
        if (alwaysTrue) {
            throw new Exception();
        }
    }

    public void throwingMethod(int i) throws Exception {
        if (i == 0) {
            throw new Exception();
        }
        throwingMethod(i - 1);
    }

    public void throwNullpointer() {
        nullObject.hashCode();
    }
}
