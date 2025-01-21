/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.jdk.classfile;

import java.lang.classfile.ClassTransform;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeTransform;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.infra.Blackhole;

/**
 * AdHocAdapt
 */
public class AdHocAdapt extends AbstractCorpusBenchmark {
    public enum X {
        LIFT(ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL)),
        LIFT1(ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL
                                                          .andThen(CodeTransform.ACCEPT_ALL))),
        LIFT2(ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL)
                            .andThen(ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL)));

        ClassTransform transform;

        X(ClassTransform transform) {
            this.transform = transform;
        }
    }

    @Param
    X transform;

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transform(Blackhole bh) {
        var cc = ClassFile.of();
        for (byte[] bytes : classes)
            bh.consume(cc.transformClass(cc.parse(bytes), transform.transform));
    }
}
