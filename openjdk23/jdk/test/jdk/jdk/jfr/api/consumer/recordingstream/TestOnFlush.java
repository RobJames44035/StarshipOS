/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import java.util.concurrent.CountDownLatch;

import jdk.jfr.Event;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests RecordingStream::onFlush(...)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestOnFlush
 */
public class TestOnFlush {

    static class OneEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        testOnFlushNull();
        testOneEvent();
        testNoEvent();
    }

    private static void testOnFlushNull() {
        log("Entering testOnFlushNull()");
        try (RecordingStream rs = new RecordingStream()) {
           try {
               rs.onFlush(null);
               throw new AssertionError("Expected NullPointerException from onFlush(null");
           } catch (NullPointerException npe) {
               // OK; as expected
           }
        }
        log("Leaving testOnFlushNull()");
     }

    private static void testNoEvent() throws Exception {
        log("Entering testNoEvent()");
        CountDownLatch flush = new CountDownLatch(1);
        try (RecordingStream r = new RecordingStream()) {
            r.onFlush(() -> {
                flush.countDown();
            });
            r.startAsync();
            flush.await();
        }
        log("Leaving testNoEvent()");
    }

    private static void testOneEvent() throws InterruptedException {
        log("Entering testOneEvent()");
        CountDownLatch flush = new CountDownLatch(1);
        try (RecordingStream r = new RecordingStream()) {
            r.onEvent(e -> {
                // ignore event
            });
            r.onFlush(() -> {
                flush.countDown();
            });
            r.startAsync();
            OneEvent e = new OneEvent();
            e.commit();
            flush.await();
        }
        log("Leaving testOneEvent()");
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}
