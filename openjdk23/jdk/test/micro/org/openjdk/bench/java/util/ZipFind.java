/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.java.util;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Tests ZipFile.getEntry() on the microbenchmarks.jar zip file
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 4, time = 2)
@Measurement(iterations = 4, time = 2)
@Fork(value = 3)
public class ZipFind {

    // Files that exist in the microbenchmarks.jar zip file
    public static final String[] existingFiles = {"org/openjdk/bench/java/util/ZipFind.class",
            "org/openjdk/bench/vm/lang/Throw.class",
            "org/openjdk/bench/java/nio/ByteBuffers.class"};
    public static String[] nonExistingFiles = {"/try/to/findme.not", "needle/in/a/HayStack.class"};

    private ZipFile zip;

    @Setup
    public void prepare() throws IOException, URISyntaxException {
        String zipFile = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        zip = new ZipFile(zipFile);

        // Verify no typos in the filename lists above
        assert zip.getEntry(ZipFind.nonExistingFiles[0]) == null;
        assert zip.getEntry(ZipFind.existingFiles[0]) != null;
        assert zip.getEntry(ZipFind.existingFiles[1]) != null;
        assert zip.getEntry(ZipFind.existingFiles[2]) != null;
    }

    @TearDown
    public void cleanup() throws IOException {
        zip.close();
    }

    @Benchmark
    public ZipEntry testOneNonExisting() throws IOException {
         return zip.getEntry(ZipFind.nonExistingFiles[0]);
    }

    @Benchmark
    public void testTwoNonExisting(Blackhole bh) throws IOException {
        bh.consume(zip.getEntry(nonExistingFiles[0]));
        bh.consume(zip.getEntry(nonExistingFiles[1]));
    }

    @Benchmark
    public void testNonExistingAndExisting(Blackhole bh) throws IOException {
        bh.consume(zip.getEntry(nonExistingFiles[0]));
        bh.consume(zip.getEntry(existingFiles[0]));
    }

    @Benchmark
    public ZipEntry testOneExisting() throws IOException {
        return zip.getEntry(ZipFind.existingFiles[0]);
    }

    @Benchmark
    public void testTwoExisting(Blackhole bh) throws IOException {
        bh.consume(zip.getEntry(existingFiles[0]));
        bh.consume(zip.getEntry(existingFiles[1]));
    }

    @Benchmark
    public void testThreeExisting(Blackhole bh) throws IOException {
        bh.consume(zip.getEntry(existingFiles[0]));
        bh.consume(zip.getEntry(existingFiles[1]));
        bh.consume(zip.getEntry(existingFiles[2]));
    }
}
