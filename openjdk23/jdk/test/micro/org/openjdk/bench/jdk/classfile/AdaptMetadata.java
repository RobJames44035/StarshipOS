/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.jdk.classfile;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * CorpusAdapt
 */
public class AdaptMetadata extends AbstractCorpusBenchmark {

    @Param
    Transforms.SimpleTransform transform;

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void transform(Blackhole bh) {
        for (byte[] aClass : classes)
            bh.consume(transform.transform.apply(aClass));
    }
}
