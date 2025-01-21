/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
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
 * @compile TestThreadStartEvent.java LatchedThread.java
 * @run main/othervm jdk.jfr.event.runtime.TestThreadStartEvent
 */
public class TestThreadStartEvent {
    private final static String EVENT_NAME_THREAD_START = EventNames.ThreadStart;

    public static void main(String[] args) throws Throwable {
        try (Recording recording = new Recording()) {
            recording.enable(EVENT_NAME_THREAD_START);

            // Start a thread before recording
            LatchedThread beforeThread = new LatchedThread("Before Thread");
            beforeThread.start();
            beforeThread.awaitStarted();
            recording.start();

            // Start and end a thread during recording
            LatchedThread duringThread = new LatchedThread("During Thread");
            duringThread.start();
            duringThread.stopAndJoin();

            // Start a thread and end it after the recording has stopped
            LatchedThread afterThread = new LatchedThread("After Thread");
            afterThread.start();
            afterThread.awaitStarted();

            recording.stop();
            afterThread.stopAndJoin();

            List<RecordedEvent> events = Events.fromRecording(recording);
            assertEvent(events, duringThread);
            assertEvent(events, afterThread);
            Asserts.assertNull(findEventByThreadName(events, beforeThread.getName()));
        }
    }

    private static void assertEvent(List<RecordedEvent> events, LatchedThread thread) throws Exception {
        RecordedEvent event = findEventByThreadName(events, thread.getName());
        System.out.println(event);
        RecordedThread t = event.getThread();
        Thread current = Thread.currentThread();
        Events.assertFrame(event, TestThreadStartEvent.class, "main");
        Asserts.assertEquals(event.getThread("thread").getJavaName(), thread.getName());
        Asserts.assertEquals(event.getThread("parentThread").getJavaName(), current.getName());
        Asserts.assertEquals(t.getThreadGroup().getName(), LatchedThread.THREAD_GROUP.getName());
        Asserts.assertEquals(t.isVirtual(), false);
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
