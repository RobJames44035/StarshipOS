/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.jmx.streaming;

import java.lang.management.ManagementFactory;
import java.util.concurrent.CountDownLatch;

import javax.management.MBeanServerConnection;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.management.jfr.RemoteRecordingStream;

/**
 * @test
 * @key jfr
 * @summary Tests that a RemoteRecordingStream can stream over multiple chunks
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.jmx.streaming.TestMultipleChunks
 */
public class TestMultipleChunks {

    static class Snake extends Event {
    }

    public static void main(String... args) throws Exception {
        CountDownLatch latch = new CountDownLatch(5);
        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
        try (RemoteRecordingStream s = new RemoteRecordingStream(conn)) {
            s.onEvent(e -> latch.countDown());
            s.startAsync();
            for (int i = 0; i < 5; i++) {
                Snake snake = new Snake();
                snake.commit();
                rotate();
            }
        }
    }

    private static void rotate() {
        try (Recording r = new Recording()) {
            r.start();
        }
    }
}
