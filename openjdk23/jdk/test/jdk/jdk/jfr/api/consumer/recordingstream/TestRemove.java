/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import jdk.jfr.Event;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests RecordingStrream::remove(...)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestRemove
 */
public class TestRemove {

    static class RemoveEvent extends Event {

    }

    public static void main(String... args) throws Exception {
        testRemoveNull();
        testRemoveOnFlush();
        testRemoveOnClose();
        testRemoveOnEvent();
    }

    private static void testRemoveNull() {
        log("Entering testRemoveNull()");
        try (RecordingStream rs = new RecordingStream()) {
           try {
               rs.remove(null);
               throw new AssertionError("Expected NullPointerException from remove(null");
           } catch (NullPointerException npe) {
               // OK; as expected
           }
        }
        log("Leaving testRemoveNull()");
     }

    private static void testRemoveOnEvent() throws Exception {
        log("Entering testRemoveOnEvent()");
        try (RecordingStream rs = new RecordingStream()) {
            AtomicInteger counter = new AtomicInteger(0);
            CountDownLatch events = new CountDownLatch(2);
            Consumer<RecordedEvent> c1 = e -> {
                counter.incrementAndGet();
            };

            Consumer<RecordedEvent> c2 = e -> {
                events.countDown();
            };
            rs.onEvent(c1);
            rs.onEvent(c2);

            rs.remove(c1);
            rs.startAsync();
            RemoveEvent r1 = new RemoveEvent();
            r1.commit();
            RemoveEvent r2 = new RemoveEvent();
            r2.commit();
            events.await();
            if (counter.get() > 0) {
                throw new AssertionError("OnEvent handler not removed!");
            }
        }
        log("Leaving testRemoveOnEvent()");
    }

    private static void testRemoveOnClose() {
        log("Entering testRemoveOnClose()");
        try (RecordingStream rs = new RecordingStream()) {
            AtomicBoolean onClose = new AtomicBoolean(false);
            Runnable r = () -> {
                onClose.set(true);
            };
            rs.onClose(r);
            rs.remove(r);
            rs.close();
            if (onClose.get()) {
                throw new AssertionError("onClose handler not removed!");
            }
        }
        log("Leaving testRemoveOnClose()");
    }

    private static void testRemoveOnFlush() throws Exception {
        log("Entering testRemoveOnFlush()");
        try (RecordingStream rs = new RecordingStream()) {
            AtomicInteger flushCount = new AtomicInteger(2);
            AtomicBoolean removeExecuted = new AtomicBoolean(false);
            Runnable onFlush1 = () -> {
                removeExecuted.set(true);
            };
            Runnable onFlush2 = () -> {
                flushCount.incrementAndGet();
            };

            rs.onFlush(onFlush1);
            rs.onFlush(onFlush2);
            rs.remove(onFlush1);
            rs.startAsync();
            while (flushCount.get() < 2) {
                RemoveEvent r = new RemoveEvent();
                r.commit();
                Thread.sleep(100);
            }

            if (removeExecuted.get()) {
                throw new AssertionError("onFlush handler not removed!");
            }
        }
        log("Leaving testRemoveOnFlush()");
    }

    private static void log(String msg) {
        System.out.println(msg);
    }
}
