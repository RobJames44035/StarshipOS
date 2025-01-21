/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package org.openjdk.bench.jdk.classfile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

/**
 * AbstractCorpusBenchmark
 */
@Warmup(iterations = 2)
@Measurement(iterations = 4)
@Fork(value = 1, jvmArgs = {
        "--add-exports", "java.base/jdk.internal.classfile.components=ALL-UNNAMED",
        "--add-exports", "java.base/jdk.internal.classfile.impl=ALL-UNNAMED"})
@State(Scope.Benchmark)
public class AbstractCorpusBenchmark {
    protected byte[][] classes;

    @Setup
    public void setup() {
        classes = rtJarToBytes(FileSystems.getFileSystem(URI.create("jrt:/")));
    }

    @TearDown
    public void tearDown() {
        //nop
    }

    private static byte[][] rtJarToBytes(FileSystem fs) {
        try {
            var modules = Stream.of(
                    Files.walk(fs.getPath("modules/java.base/java")),
                    Files.walk(fs.getPath("modules"), 2).filter(p -> p.endsWith("module-info.class")))
                                .flatMap(p -> p)
                                .filter(p -> Files.isRegularFile(p) && p.toString().endsWith(".class"))
                                .map(AbstractCorpusBenchmark::readAllBytes)
                                .toArray(byte[][]::new);
            return modules;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static byte[] readAllBytes(Path p) {
        try {
            return Files.readAllBytes(p);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
