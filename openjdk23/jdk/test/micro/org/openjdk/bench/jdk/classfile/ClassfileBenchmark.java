/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package org.openjdk.bench.jdk.classfile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.lang.classfile.ClassModel;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.ClassFile;
import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeTransform;
import java.lang.classfile.CompoundElement;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;

import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * ClassFileBenchmark
 */
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(value = 1)
@State(Scope.Benchmark)
public class ClassfileBenchmark {
    private byte[] benchBytes;
    private ClassModel benchModel;
    private ClassFile sharedCP, newCP;
    private ClassTransform threeLevelNoop;
    private ClassTransform addNOP;

    @Setup
    public void setup() throws IOException {
        benchBytes = Files.readAllBytes(
                FileSystems.getFileSystem(URI.create("jrt:/"))
                .getPath("modules/java.base/java/util/AbstractMap.class"));
        sharedCP = ClassFile.of();
        newCP = ClassFile.of(ClassFile.ConstantPoolSharingOption.NEW_POOL);
        benchModel = ClassFile.of().parse(benchBytes);
        threeLevelNoop = ClassTransform.transformingMethodBodies(CodeTransform.ACCEPT_ALL);
        addNOP = ClassTransform.transformingMethodBodies(new CodeTransform() {
            @Override
            public void atStart(CodeBuilder cob) {
                cob.nop();
            }
            @Override
            public void accept(CodeBuilder cob, CodeElement coe) {
                cob.with(coe);
            }
        });
        //expand the model
        consume(benchModel);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void parse(Blackhole bh) {
        consume(sharedCP.parse(benchBytes));
    }

    private static void consume(CompoundElement<?> parent) {
        parent.forEach(e -> {
            if (e instanceof CompoundElement<?> ce) consume(ce);
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transformWithSharedCP(Blackhole bh) {
        bh.consume(sharedCP.transformClass(benchModel, threeLevelNoop));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transformWithNewCP(Blackhole bh) {
        bh.consume(newCP.transformClass(benchModel, threeLevelNoop));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transformWithAddedNOP(Blackhole bh) {
        bh.consume(sharedCP.transformClass(benchModel, addNOP));
    }
}
