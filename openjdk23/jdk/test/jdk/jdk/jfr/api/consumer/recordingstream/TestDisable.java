/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import jdk.jfr.Event;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests RecordingStream::disable(...)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestDisable
 */
public class TestDisable {

    private static class DisabledEvent extends Event {
    }

    private static class EnabledEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        testDisableWithClass();
        testDisableWithEventName();
    }

    private static void testDisableWithEventName() {
        test(r -> r.disable(DisabledEvent.class.getName()));
    }

    private static void testDisableWithClass() {
        test(r -> r.disable(DisabledEvent.class));
    }

    private static void test(Consumer<RecordingStream> disablement) {
        CountDownLatch twoEvent = new CountDownLatch(2);
        AtomicBoolean fail = new AtomicBoolean(false);
        try(RecordingStream r = new RecordingStream()) {
            r.onEvent(e -> {
                if (e.getEventType().getName().equals(DisabledEvent.class.getName())) {
                    fail.set(true);
                }
                twoEvent.countDown();
            });
            disablement.accept(r);
            r.startAsync();
            EnabledEvent e1 = new EnabledEvent();
            e1.commit();
            DisabledEvent d1 = new DisabledEvent();
            d1.commit();
            EnabledEvent e2 = new EnabledEvent();
            e2.commit();
            try {
                twoEvent.await();
            } catch (InterruptedException ie) {
                throw new RuntimeException("Unexpexpected interruption of thread", ie);
            }
            if (fail.get()) {
                throw new RuntimeException("Should not receive a disabled event");
            }
        }
    }
}
