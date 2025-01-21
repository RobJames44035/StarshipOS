/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package org.openjdk.micro.bench.java.util;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Base64;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class Base64Encode {

    private Base64.Encoder encoder;
    private ArrayList<byte[]> unencoded;
    private byte[] encoded;

    private static final int TESTSIZE = 1000;

    @Param({"1", "2", "3", "6", "7", "9", "10", "48", "512", "1000", "20000"})
    private int maxNumBytes;

    @Setup
    public void setup() {
        Random r = new Random(1123);

        int dstLen = ((maxNumBytes + 16) / 3) * 4;

        encoder = Base64.getEncoder();
        unencoded = new ArrayList<byte[]> ();
        encoded = new byte[dstLen];

        for (int i = 0; i < TESTSIZE; i++) {
            int srcLen = 1 + r.nextInt(maxNumBytes);
            byte[] src = new byte[srcLen];
            r.nextBytes(src);
            unencoded.add(src);
        }
    }

    @Benchmark
    @OperationsPerInvocation(TESTSIZE)
    public void testBase64Encode(Blackhole bh) {
        for (byte[] s : unencoded) {
            encoder.encode(s, encoded);
            bh.consume(encoded);
        }
    }
}
