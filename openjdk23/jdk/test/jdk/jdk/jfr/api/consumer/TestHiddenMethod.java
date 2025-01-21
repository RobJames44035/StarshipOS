/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package jdk.jfr.api.consumer;

import static jdk.test.lib.Asserts.assertEquals;
import static jdk.test.lib.Asserts.assertFalse;
import static jdk.test.lib.Asserts.assertTrue;

import java.time.Duration;
import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.test.lib.jfr.Events;


/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 *
 * @library /test/lib
 * @modules jdk.jfr
 *
 * @run main/othervm jdk.jfr.api.consumer.TestHiddenMethod
 */
public final class TestHiddenMethod {

    // Must emit events in separate threads, because JTREG uses reflection
    // to invoke main method, which uses hidden methods.
    public static void main(String[] args) throws Throwable {
        try (Recording recording = new Recording()) {
            recording.enable(MyEvent.class).withThreshold(Duration.ofMillis(0));
            recording.start();
            Thread t1 = new Thread(() -> {
                // Runs in hidden lambda frame
                MyEvent event = new MyEvent();
                event.hidden = true;
                event.commit();
            });
            t1.start();
            t1.join();
            Thread t2 = new Thread() {
                public void run() {
                    MyEvent event = new MyEvent();
                    event.hidden = false;
                    event.commit();
                }
            };
            t2.start();
            t2.join();
            recording.stop();

            List<RecordedEvent> events = Events.fromRecording(recording);
            assertEquals(2, events.size(), "Expected two events");
            // Swap events if they come out of order.
            boolean hidden = events.get(0).getBoolean("hidden");
            if (!hidden) {
                events = events.reversed();
            }
            RecordedEvent hiddenEvent = events.get(0);
            RecordedEvent visibleEvent = events.get(1);

            System.out.println("hiddenEvent:" + hiddenEvent);
            System.out.println("visibleEvent:" + visibleEvent);

            assertTrue(hasHiddenStackFrame(hiddenEvent), "No hidden frame in hidden event: " + hiddenEvent);
            assertFalse(hasHiddenStackFrame(visibleEvent), "Hidden frame in visible event: " + visibleEvent);
        }
    }

    private static boolean hasHiddenStackFrame(RecordedEvent event) throws Throwable {
        RecordedStackTrace stacktrace = event.getStackTrace();
        List<RecordedFrame> frames = stacktrace.getFrames();
        assertFalse(frames.isEmpty(), "Stacktrace frames was empty");
        for (RecordedFrame frame : frames) {
            if (frame.getMethod().isHidden()) {
                return true;
            }
        }
        return false;
    }

    public static class MyEvent extends Event {
        public boolean hidden;
    }
}
