/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.vm.lambda.capture;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * evaluates method reference capture
 *
 * @author Sergey Kuksenko (sergey.kuksenko@oracle.com)
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class CaptureMR {

    public static class Mock0 {
        public Mock0() {
        }
    }

    public static Object method_static() {
        return "42";
    }

    public Object method_instance() {
        return "42";
    }

    @Benchmark()
    public FunctionalInterface0 mref_static0(){
        return CaptureMR::method_static;
    }

    @Benchmark()
    public FunctionalInterface0 mref_bound0(){
        return this::method_instance;
    }

    @Benchmark()
    public FunctionalInterface0 mref_constructor0(){
        return Mock0::new;
    }


//---------------------------

    public static class Mock1 {
        private Object oo;
        public Mock1(Object o) {
            oo = o;
        }
    }

    public static Object method_static(Object bar) {
        return "42" + bar;
    }

    public Object method_instance(Object bar) {
        return "42" + bar;
    }

    @Benchmark()
    public FunctionalInterface1 mref_static1(){
        return CaptureMR::method_static;
    }

    @Benchmark()
    public FunctionalInterface1 mref_bound1(){
        return this::method_instance;
    }

    @Benchmark()
    public FunctionalInterface1 mref_constructor1(){
        return Mock1::new;
    }

}
