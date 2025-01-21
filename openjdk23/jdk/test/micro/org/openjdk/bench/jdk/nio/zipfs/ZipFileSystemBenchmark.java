/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package org.openjdk.bench.jdk.nio.zipfs;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(Threads.MAX)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 3)
public class ZipFileSystemBenchmark {
    private static final String FILE_NAME = "filename";
    private FileSystemProvider jarFsProvider;
    private Path readPath;
    private FileSystem fileSystem;
    private Path zip;

    @Setup(Level.Trial) public void setup() throws IOException {
        jarFsProvider = FileSystemProvider.installedProviders().stream().filter(x -> x.getScheme().equals("jar")).findFirst().get();
        zip = Files.createTempFile("zipfs-benchmark", ".jar");
        createTestZip();
        fileSystem = jarFsProvider.newFileSystem(zip, Map.of());
        Path rootRead = fileSystem.getRootDirectories().iterator().next();
        readPath = rootRead.resolve(FILE_NAME);
    }

    private void createTestZip() throws IOException {
        Files.delete(zip);
        FileSystem writableFileSystem = jarFsProvider.newFileSystem(zip, Map.of("create", "true"));
        byte[] data = new byte[16 * 1024 * 1024];
        new Random(31).nextBytes(data);
        Path root = writableFileSystem.getRootDirectories().iterator().next();
        Files.write(root.resolve(FILE_NAME), data);
        writableFileSystem.close();
    }

    @TearDown public void tearDown() throws IOException {
        if (fileSystem != null) {
            fileSystem.close();
        }
        Files.deleteIfExists(zip);
    }

    // Performance should remain constant when varying the number of threads up to the
    // number of physical cores if the NIO implementation on the platform supports
    // concurrent reads to a single FileChannel instance. At the time of writing, NIO on Windows
    // serializes access.
    @Benchmark public void read(Blackhole bh) throws IOException {
        InputStream inputStream = Files.newInputStream(readPath);
        byte[] buffer = new byte[8192];
        while (inputStream.read(buffer) != -1) {
            bh.consume(buffer);
        }
    }
}
