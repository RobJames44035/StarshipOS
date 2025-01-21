/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.io;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark measuring UTF8 char operations.
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(2)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
public class UTF8 {

    private String s;
    private BlackholedOutputStream bos;
    private DataOutputStream dos;

    @Setup
    public void setup(Blackhole bh) {
        bos = new BlackholedOutputStream(bh);
        dos = new DataOutputStream(bos);
        s = "abcdefghijklmnopqrstuvxyz0123456789";
    }

    @Benchmark
    public void testCharConversion() throws IOException {
        dos.writeUTF(s);
    }

}
