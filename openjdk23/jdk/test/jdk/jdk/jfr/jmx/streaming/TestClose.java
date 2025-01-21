/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.jfr.jmx.streaming;

import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.management.MBeanServerConnection;

import jdk.jfr.Event;
import jdk.management.jfr.RemoteRecordingStream;

/**
 * @test
 * @key jfr
 * @summary Tests that a RemoteRecordingStream can be closed
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm -Xlog:jfr=debug jdk.jfr.jmx.streaming.TestClose
 */
public class TestClose {

    static class TestCloseEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        Path p = Files.createDirectory(Paths.get("test-close-" + System.currentTimeMillis()));

        RemoteRecordingStream e = new RemoteRecordingStream(conn, p);
        e.startAsync();
        // Produce enough to generate multiple chunks
        for (int i = 0; i < 200_000; i++) {
            TestCloseEvent event = new TestCloseEvent();
            event.commit();
        }
        e.onFlush(() -> {
            e.close(); // <- should clean up files.
        });
        e.awaitTermination();
        int count = 0;
        for (Object path : Files.list(p).toArray()) {
            System.out.println(path);
            count++;
        }
        if (count > 0) {
            throw new Exception("Expected repository to be empty");
        }
    }
}
