/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.io;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;

/**
 * Tests the overheads of I/O API.
 * This test is known to depend heavily on disk subsystem performance.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(2)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
public class FileChannelWrite {

    @Param("1000000")
    private int fileSize;

    private File f;
    private FileChannel fc;
    private ByteBuffer bb;
    private int count;

    @Setup(Level.Trial)
    public void beforeRun() throws IOException {
        f = File.createTempFile("FileChannelWriteBench", ".bin");
        bb = ByteBuffer.allocate(1);
        bb.put((byte) 47);
        bb.flip();
    }

    @TearDown(Level.Trial)
    public void afterRun() throws IOException {
        f.delete();
    }

    @Setup(Level.Iteration)
    public void beforeIteration() throws IOException {
        fc = FileChannel.open(f.toPath(), StandardOpenOption.WRITE);
        count = 0;
    }

    @TearDown(Level.Iteration)
    public void afterIteration() throws IOException {
        fc.close();
    }

    @Benchmark
    public void test() throws IOException {
        fc.write(bb);
        bb.flip();
        count++;
        if (count >= fileSize) {
            // start over
            fc.position(0);
            count = 0;
        }
    }

}
