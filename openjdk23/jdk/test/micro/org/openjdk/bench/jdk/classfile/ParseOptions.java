/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.jdk.classfile;

import java.lang.classfile.ClassModel;
import java.lang.classfile.ClassFile;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;

import static org.openjdk.bench.jdk.classfile.Transforms.threeLevelNoop;

/**
 * ParseOptions
 */
public class ParseOptions extends AbstractCorpusBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transformNoDebug(Blackhole bh) {
        var cc = ClassFile.of(ClassFile.DebugElementsOption.DROP_DEBUG);
        for (byte[] aClass : classes) {
            ClassModel cm = cc.parse(aClass);
            bh.consume(cc.transformClass(cm, threeLevelNoop));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transformNoStackmap(Blackhole bh) {
        var cc = ClassFile.of(ClassFile.StackMapsOption.DROP_STACK_MAPS);
        for (byte[] aClass : classes) {
            ClassModel cm = cc.parse(aClass);
            bh.consume(cc.transformClass(cm, threeLevelNoop));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transformNoLineNumbers(Blackhole bh) {
        var cc = ClassFile.of(ClassFile.LineNumbersOption.DROP_LINE_NUMBERS);
        for (byte[] aClass : classes) {
            ClassModel cm = cc.parse(aClass);
            bh.consume(cc.transformClass(cm, threeLevelNoop));
        }
    }
}
