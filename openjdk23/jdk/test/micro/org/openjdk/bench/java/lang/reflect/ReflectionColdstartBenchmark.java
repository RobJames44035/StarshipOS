/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.lang.reflect;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark measuring cold-start of reflective method invocation.
 */
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(value = 10, warmups = 0)
public class ReflectionColdstartBenchmark {

    static class Nested {
        static Object m00(Object p) {return p;}

        static Object m01(Object p) {return p;}

        static Object m02(Object p) {return p;}

        static Object m03(Object p) {return p;}

        static Object m04(Object p) {return p;}

        static Object m05(Object p) {return p;}

        static Object m06(Object p) {return p;}

        static Object m07(Object p) {return p;}

        static Object m08(Object p) {return p;}

        static Object m09(Object p) {return p;}

        static Object m0A(Object p) {return p;}

        static Object m0B(Object p) {return p;}

        static Object m0C(Object p) {return p;}

        static Object m0D(Object p) {return p;}

        static Object m0E(Object p) {return p;}

        static Object m0F(Object p) {return p;}

        static Object m10(Object p) {return p;}

        static Object m11(Object p) {return p;}

        static Object m12(Object p) {return p;}

        static Object m13(Object p) {return p;}

        static Object m14(Object p) {return p;}

        static Object m15(Object p) {return p;}

        static Object m16(Object p) {return p;}

        static Object m17(Object p) {return p;}

        static Object m18(Object p) {return p;}

        static Object m19(Object p) {return p;}

        static Object m1A(Object p) {return p;}

        static Object m1B(Object p) {return p;}

        static Object m1C(Object p) {return p;}

        static Object m1D(Object p) {return p;}

        static Object m1E(Object p) {return p;}

        static Object m1F(Object p) {return p;}
    }

    private Method[] methods;
    private Object arg;

    @Setup(Level.Trial)
    public void setup() {
        methods = Nested.class.getDeclaredMethods();
        arg = new Object();
    }

    @Benchmark
    public void invokeMethods() throws ReflectiveOperationException {
        // As this is testing warmup, JITs are unlikely to progress to the
        // point where the lack of a blackhole might lead to DCE. Omitting it
        // makes it easier to test this code minimal use of JMH code
        for (Method m : methods) {
            m.invoke(null, arg);
        }
    }

    /**
     * Runs invokeMethods once without any JMH interaction, acting as an
     * independent startup benchmark.
     */
    public static void main(String ... args) throws Exception {
        var coldstart = new ReflectionColdstartBenchmark();
        coldstart.setup();
        coldstart.invokeMethods();
    }
}
