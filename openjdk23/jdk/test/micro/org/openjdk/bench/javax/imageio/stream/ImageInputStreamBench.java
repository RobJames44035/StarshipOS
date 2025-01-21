/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.javax.imageio.stream;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

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

import javax.imageio.stream.ImageInputStreamImpl;
import javax.imageio.stream.ImageOutputStreamImpl;

/**
 * Examine ImageInpuStream operations
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@State(Scope.Benchmark)
public class ImageInputStreamBench {

    // private static final byte[] ARRAY = new byte[8];
    private ImageInputStreamImpl imageInputStream;

    @Setup
    public void createInstants() {
        // Various instants during the same day
        imageInputStream = new ImageInputStreamImpl() {
            @Override
            public int read() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                //System.arraycopy(ARRAY, 0, b, off, len);
                return len;
            }
        };

    }

    @Benchmark
    public void readInt(Blackhole bh) throws IOException {
        bh.consume(imageInputStream.readInt());
    }

    @Benchmark
    public void readLong(Blackhole bh) throws IOException {
        bh.consume(imageInputStream.readLong());
    }

}
