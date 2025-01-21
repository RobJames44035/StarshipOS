/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class FileWrite {

    @Param("1000000")
    private int fileSize;

    private File f;
    private FileOutputStream fos;
    private long count;

    @Setup(Level.Trial)
    public void beforeRun() throws IOException {
        f = File.createTempFile("FileWriteBench", ".bin");
    }

    @TearDown(Level.Trial)
    public void afterRun() throws IOException {
        f.delete();
    }

    @Setup(Level.Iteration)
    public void beforeIteration() throws FileNotFoundException {
        fos = new FileOutputStream(f);
    }

    @TearDown(Level.Iteration)
    public void afterIteration() throws IOException {
        fos.close();
    }

    @Benchmark
    public void test() throws IOException {
        fos.write((byte) count);
        count++;
        if (count >= fileSize) {
            // restart
            fos.close();
            fos = new FileOutputStream(f);
            count = 0;
        }
    }

}
