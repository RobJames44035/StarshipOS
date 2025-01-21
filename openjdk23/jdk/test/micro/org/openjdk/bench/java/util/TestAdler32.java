/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package org.openjdk.bench.java.util;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.zip.Adler32;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class TestAdler32 {

    private Adler32 adler32;
    private Random random;
    private byte[] bytes;

    @Param({"64", "128", "256", "512", "1024", "2048", "5012", "8192", "16384", "32768", "65536"})
    private int count;

    public TestAdler32() {
        adler32 = new Adler32();
        random = new Random(2147483648L);
        bytes = new byte[1000000];
        random.nextBytes(bytes);
    }

    @Setup(Level.Iteration)
    public void setupBytes() {
        adler32.reset();
    }

    @Benchmark
    public void testAdler32Update() {
        adler32.update(bytes, 0, count);
    }
}
