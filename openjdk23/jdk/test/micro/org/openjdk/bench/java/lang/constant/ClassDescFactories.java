/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package org.openjdk.bench.java.lang.constant;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.util.concurrent.TimeUnit;

/**
 * Performance of conversion from and to method type descriptor symbols with
 * descriptor strings and class descriptor symbols
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 2)
@Measurement(iterations = 6, time = 1)
@Fork(1)
@State(Scope.Thread)
public class ClassDescFactories {

    @Param({
            "Ljava/lang/Object;",
            "V",
            "I"
    })
    public String descString;

    @Benchmark
    public ClassDesc ofDescriptor() {
        return ClassDesc.ofDescriptor(descString);
    }

    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Warmup(iterations = 3, time = 2)
    @Measurement(iterations = 6, time = 1)
    @Fork(1)
    @State(Scope.Thread)
    public static class ReferenceOnly {
        public ClassDesc desc = ConstantDescs.CD_Object;
        @Benchmark
        public ClassDesc ofNested() {
            return desc.nested("Foo");
        }

    }
}
