/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.jfr.jmx.streaming;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.MBeanServerConnection;

import jdk.jfr.Event;
import jdk.management.jfr.RemoteRecordingStream;

/**
 * @test
 * @key jfr
 * @summary Tests that max size can be set for a RemoteRecordingStream
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.streaming.TestMaxSize
 */
public class TestMaxSize {

    static class Monkey extends Event {
    }

    public static void main(String... args) throws Exception {
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        Path dir = Files.createDirectories(Paths.get("max-size-" + System.currentTimeMillis()));
        System.out.println(dir);
        AtomicBoolean finished = new AtomicBoolean();
        try (RemoteRecordingStream e = new RemoteRecordingStream(conn, dir)) {
            e.startAsync();
            e.onEvent(ev -> {
                if (finished.get()) {
                    return;
                }
                // Consume some events, but give back control
                // to stream so it can be closed.
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    // ignore
                }
            });
            while (directorySize(dir) < 50_000_000) {
                emitEvents(500_000);
            }
            System.out.println("Before setMaxSize(1_000_000)");
            fileCount(dir);
            e.setMaxSize(1_000_000);
            System.out.println("After setMaxSize(1_000_000)");
            long count = fileCount(dir);
            if (count > 3) {
                // Three files can happen when:
                // File 1: Header of new chunk is written to disk
                // File 2: Previous chunk is not yet finalized and added to list of DiskChunks
                // File 3: Previous previous file is in the list of DiskChunks.
                throw new Exception("Expected at most three chunks with setMaxSize(1_000_000). Found " + count);
            }
            finished.set(true);
        }
    }

    private static void emitEvents(int count) throws InterruptedException {
        for (int i = 0; i < count; i++) {
            Monkey m = new Monkey();
            m.commit();
        }
        System.out.println("Emitted " + count + " events");
        Thread.sleep(1000);
    }

    private static int fileCount(Path dir) throws IOException {
        System.out.println("Files:");
        AtomicInteger count = new AtomicInteger();
        Files.list(dir).forEach(p -> {
            System.out.println(p + " " + fileSize(p));
            count.incrementAndGet();
        });
        return count.get();
    }

    private static long directorySize(Path dir) throws IOException {
        long p = Files.list(dir).mapToLong(f -> fileSize(f)).sum();
        System.out.println("Directory size: " + p);
        return p;
    }

    private static long fileSize(Path p) {
        try {
            return Files.size(p);
        } catch (IOException e) {
            System.out.println("Could not determine file size for " + p);
            return 0;
        }
    }
}
