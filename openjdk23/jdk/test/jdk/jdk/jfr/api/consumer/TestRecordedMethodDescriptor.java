/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */
package jdk.jfr.api.consumer;

import static jdk.test.lib.Asserts.assertEquals;
import static jdk.test.lib.Asserts.assertFalse;
import static jdk.test.lib.Asserts.assertNotNull;
import static jdk.test.lib.Asserts.assertTrue;

import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedFrame;
import jdk.jfr.consumer.RecordedMethod;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.test.lib.jfr.Events;


/**
 * @test
 * @summary Verifies that the method descriptor is correct
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestRecordedMethodDescriptor
 */
public final class TestRecordedMethodDescriptor {

    public static class MyEvent extends Event {
    }

    private static final String MAIN_METHOD_DESCRIPTOR = "([Ljava/lang/String;)V";
    private static final String MAIN_METHOD_NAME = "main";

    public static void main(String[] args) throws Exception  {
        try (Recording recording = new Recording()) {
            recording.enable(MyEvent.class);
            recording.start();

            MyEvent event = new MyEvent();
            event.commit();
            recording.stop();

            List<RecordedEvent> recordedEvents = Events.fromRecording(recording);
            assertEquals(1, recordedEvents.size(), "Expected one event");
            RecordedEvent recordedEvent = recordedEvents.getFirst();

            RecordedStackTrace stacktrace = recordedEvent.getStackTrace();
            List<RecordedFrame> frames = stacktrace.getFrames();
            assertFalse(frames.isEmpty(), "Stacktrace frames was empty");

            boolean foundMainMethod = false;
            for (RecordedFrame frame : frames) {
                RecordedMethod method = frame.getMethod();
                String descr = method.getDescriptor();
                assertNotNull(descr, "Method descriptor is null");
                String name = method.getName();
                assertNotNull(name, "Method name is null");
                if (name.equals(MAIN_METHOD_NAME) && descr.equals(MAIN_METHOD_DESCRIPTOR)) {
                    assertFalse(foundMainMethod, "main() method descriptor already recorded");
                    foundMainMethod = true;
                }
            }
            assertTrue(foundMainMethod, "main() method descriptor has never been recorded");
        }
    }
}
