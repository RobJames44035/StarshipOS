/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.jdk.classfile;

import java.lang.reflect.AccessFlag;
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassModel;
import java.lang.classfile.ClassFile;
import java.lang.classfile.FieldModel;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

public class ReadMetadata extends AbstractCorpusBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void jdkReadName(Blackhole bh) {
        var cc = ClassFile.of();
        for (byte[] bytes : classes) {
            bh.consume(cc.parse(bytes).thisClass().asInternalName());
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void jdkReadMemberNames(Blackhole bh) {
        var cc = ClassFile.of();
        for (byte[] bytes : classes) {
            var cm = cc.parse(bytes);
            bh.consume(cm.thisClass().asInternalName());
            for (var f : cm.fields()) {
                bh.consume(f.fieldName().stringValue());
                bh.consume(f.fieldType().stringValue());
            }
            for (var m : cm.methods()) {
                bh.consume(m.methodName().stringValue());
                bh.consume(m.methodType().stringValue());
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void jdkTreeCountFields(Blackhole bh) {
        var cc = ClassFile.of();
        for (byte[] bytes : classes) {
            int count = 0;
            ClassModel cm = cc.parse(bytes);
            for (FieldModel fm : cm.fields())
                if (!fm.flags().has(AccessFlag.PUBLIC)) {
                    ++count;
                }
            bh.consume(count);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void jdkCountFields(Blackhole bh) {
        var cc = ClassFile.of();
        for (byte[] bytes : classes) {
            int count = 0;
            ClassModel cm = cc.parse(bytes);
            for (ClassElement ce : cm) {
                if (ce instanceof FieldModel fm) {
                    if (!fm.flags().has(AccessFlag.PUBLIC)) {
                        ++count;
                    }
                }
            }
            bh.consume(count);
        }
    }
}
