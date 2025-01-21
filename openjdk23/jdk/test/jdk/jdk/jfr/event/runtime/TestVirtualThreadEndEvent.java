/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedThread;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR & vm.continuations
 * @library /test/lib
 * @compile TestVirtualThreadEndEvent.java LatchedThread.java
 * @run main/othervm jdk.jfr.event.runtime.TestVirtualThreadEndEvent
 */
public class TestVirtualThreadEndEvent {
    private final static String EVENT_NAME_THREAD_END = EventNames.VirtualThreadEnd;

    public static void main(String[] args) throws Throwable {
        try (Recording recording = new Recording()) {
            recording.enable(EVENT_NAME_THREAD_END);

            LatchedThread beforeThread = new LatchedThread("Before Thread", true);
            beforeThread.start();
            beforeThread.awaitStarted();
            recording.start();

            // End an already running thread
            beforeThread.stopAndJoin();

            // End a thread that is started during recording
            LatchedThread duringThread = new LatchedThread("During Thread", true);
            duringThread.start();
            duringThread.stopAndJoin();

            // Start a thread and end it after the recording has stopped
            LatchedThread afterThread = new LatchedThread("After Thread", true);
            afterThread.start();
            afterThread.awaitStarted();

            recording.stop();
            afterThread.stopAndJoin();

            List<RecordedEvent> events = Events.fromRecording(recording);
            assertEvent(events, beforeThread);
            assertEvent(events, duringThread);
            Asserts.assertNull(findEventByThreadName(events, afterThread.getName()));
        }
    }

    private static void assertEvent(List<RecordedEvent> events, LatchedThread thread) throws Exception {
        RecordedEvent event = findEventByThreadName(events, thread.getName());
        System.out.println(event);
        RecordedThread t = event.getThread();
        Asserts.assertEquals(event.getLong("javaThreadId"), thread.getId());
        Asserts.assertTrue(t.isVirtual());
        Asserts.assertEquals(t.getThreadGroup().getName(), "VirtualThreads");
    }

    private static RecordedEvent findEventByThreadName(List<RecordedEvent> events, String name) {
        for (RecordedEvent e : events) {
            if (e.getThread().getJavaName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
