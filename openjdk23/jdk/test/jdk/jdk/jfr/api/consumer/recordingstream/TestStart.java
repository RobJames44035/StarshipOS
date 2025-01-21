/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import jdk.jfr.Event;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests RecordingStream::start()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @build jdk.jfr.api.consumer.recordingstream.EventProducer
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestStart
 */
public class TestStart {
    static class StartEvent extends Event {
    }
    public static void main(String... args) throws Exception {
        testStart();
        testStartOnEvent();
        testStartTwice();
        testStartClosed();
    }

    private static void testStartTwice() throws Exception {
        log("Entering testStartTwice()");
        CountDownLatch started = new CountDownLatch(1);
        try (RecordingStream rs = new RecordingStream()) {
            EventProducer t = new EventProducer();
            t.start();
            Thread thread = new Thread() {
                public void run() {
                    rs.start();
                }
            };
            thread.start();
            rs.onEvent(e -> {
                if (started.getCount() > 0) {
                    started.countDown();
                }
            });
            started.await();
            t.kill();
            try {
                rs.start();
                throw new AssertionError("Expected IllegalStateException if started twice");
            } catch (IllegalStateException ise) {
                // OK, as expected
            }
        }
        log("Leaving testStartTwice()");
    }

    static void testStart() throws Exception {
        log("Entering testStart()");
        CountDownLatch started = new CountDownLatch(1);
        try (RecordingStream rs = new RecordingStream()) {
            rs.onEvent(e -> {
                started.countDown();
            });
            EventProducer t = new EventProducer();
            t.start();
            Thread thread = new Thread() {
                public void run() {
                    rs.start();
                }
            };
            thread.start();
            started.await();
            t.kill();
        }
        log("Leaving testStart()");
    }

    static void testStartOnEvent() throws Exception {
        log("Entering testStartOnEvent()");
        AtomicBoolean ISE = new AtomicBoolean(false);
        CountDownLatch startedTwice = new CountDownLatch(1);
        try (RecordingStream rs = new RecordingStream()) {
            rs.onEvent(e -> {
                try {
                    rs.start(); // must not deadlock
                } catch (IllegalStateException ise) {
                    if (!ISE.get())  {
                        ISE.set(true);
                        startedTwice.countDown();
                    }
                }
            });
            EventProducer t = new EventProducer();
            t.start();
            Thread thread = new Thread() {
                public void run() {
                    rs.start();
                }
            };
            thread.start();
            startedTwice.await();
            t.kill();
            if (!ISE.get()) {
                throw new AssertionError("Expected IllegalStateException");
            }
        }
        log("Leaving testStartOnEvent()");
    }

    static void testStartClosed() {
        log("Entering testStartClosed()");
        RecordingStream rs = new RecordingStream();
        rs.close();
        try {
            rs.start();
            throw new AssertionError("Expected IllegalStateException");
        } catch (IllegalStateException ise) {
            // OK, as expected.
        }
        log("Leaving testStartClosed()");
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}
