/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.vm.compiler;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(value = 3)
public class IterativeEA {

    public static int ii = 1;

    static class A {
        int i;

        public A(int i) {
            this.i = i;
        }
    }

    static class B {
        A a;

        public B(A a) {
            this.a = a;
        }
    }

    static class C {
        B b;

        public C(B b) {
            this.b = b;
        }
    }

    @Benchmark
    public int test1() {
        C c = new C(new B(new A(ii)));
        return c.b.a.i;
    }

    static class Point {
        int x;
        int y;
        int ax[];
        int ay[];
    }

    @Benchmark
    public int test2() {
        Point p = new Point();
        p.ax = new int[2];
        p.ay = new int[2];
        int x = 3;
        p.ax[0] = x;
        p.ay[1] = 3 * x + ii;
        return p.ax[0] * p.ay[1];
    }

    public static final Double dbc = Double.valueOf(1.);

    @Benchmark
    public double test3() {
        Double j1 = Double.valueOf(1.);
        Double j2 = Double.valueOf(1.);
        for (int i = 0; i< 1000; i++) {
            j1 = j1 + 1.;
            j2 = j2 + 2.;
        }
        return j1 + j2;
    }
}
