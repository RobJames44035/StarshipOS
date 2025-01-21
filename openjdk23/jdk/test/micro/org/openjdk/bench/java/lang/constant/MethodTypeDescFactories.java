/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
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
import org.openjdk.jmh.infra.Blackhole;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.util.List;
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
public class MethodTypeDescFactories {

    private static final ClassDesc DUMMY_CD = ClassDesc.of("Dummy_invalid");

    /** Dots will be replaced by the descriptor of this benchmark class. */
    @Param({
            "(Ljava/lang/Object;Ljava/lang/String;)I",
            "()V",
            "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
            "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",
            "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;",
            "()Ljava/lang/Object;",
            "([IJLjava/lang/String;Z)Ljava/util/List;",
            "()[Ljava/lang/String;",
            "(..IIJ)V",
            "([III.Z[B..[.[B).",
            "(.....................)."
    })
    public String descString;
    public MethodTypeDesc desc;
    public ClassDesc ret;
    public ClassDesc[] args;
    public List<ClassDesc> argsList;

    @Setup
    public void setup() {
        descString = descString.replaceAll("\\.", MethodTypeDescFactories.class.descriptorString());
        desc = MethodTypeDesc.ofDescriptor(descString);
        ret = desc.returnType();
        args = desc.parameterArray();
        argsList = desc.parameterList();
    }

    @Benchmark
    public String descriptorString(Blackhole blackhole) {
        // swaps return types with dummy classdesc;
        // this shares parameter arrays and avoids revalidation
        // while it drops the descriptor string cache
        var mtd = desc.changeReturnType(DUMMY_CD);
        blackhole.consume(mtd);
        mtd = mtd.changeReturnType(desc.returnType());
        blackhole.consume(mtd);

        return mtd.descriptorString();
    }

    @Benchmark
    public MethodTypeDesc ofDescriptor() {
        return MethodTypeDesc.ofDescriptor(descString);
    }

    @Benchmark
    public MethodTypeDesc ofArray() {
        return MethodTypeDesc.of(ret, args);
    }

    @Benchmark
    public MethodTypeDesc ofList() {
        return MethodTypeDesc.of(ret, argsList);
    }
}
