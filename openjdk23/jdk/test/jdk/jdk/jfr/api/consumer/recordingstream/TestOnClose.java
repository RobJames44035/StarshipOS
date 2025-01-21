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
 * @summary Tests RecordingStream::onClose(...)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestOnClose
 */
public class TestOnClose {

    private static class CloseEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        testOnCloseNull();
        testOnClosedUnstarted();
        testOnClosedStarted();
    }

    private static void testOnCloseNull() {
       try (RecordingStream rs = new RecordingStream()) {
          try {
              rs.onClose(null);
              throw new AssertionError("Expected NullPointerException from onClose(null");
          } catch (NullPointerException npe) {
              // OK; as expected
          }
       }
    }

    private static void testOnClosedStarted() throws InterruptedException {
        AtomicBoolean onClose = new AtomicBoolean(false);
        CountDownLatch event = new CountDownLatch(1);
        try (RecordingStream r = new RecordingStream()) {
            r.onEvent(e -> {
                event.countDown();
            });
            r.onClose(() -> {
                onClose.set(true);
            });
            r.startAsync();
            CloseEvent c = new CloseEvent();
            c.commit();
            event.await();
        }
        if (!onClose.get()) {
            throw new AssertionError("OnClose was not called");
        }
    }

    private static void testOnClosedUnstarted() {
        AtomicBoolean onClose = new AtomicBoolean(false);
        try (RecordingStream r = new RecordingStream()) {
            r.onClose(() -> {
                onClose.set(true);
            });
        }
        if (!onClose.get()) {
            throw new AssertionError("OnClose was not called");
        }
    }
}
