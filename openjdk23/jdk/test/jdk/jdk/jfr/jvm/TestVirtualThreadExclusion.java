/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.jfr.jvm;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.internal.JVM;
import jdk.jfr.Recording;

import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

import static jdk.test.lib.Asserts.assertTrue;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR & vm.continuations
 * @library /test/lib
 * @modules jdk.jfr/jdk.jfr.internal
 * @compile TestVirtualThreadExclusion.java LatchedThread.java
 * @run main/othervm jdk.jfr.jvm.TestVirtualThreadExclusion
 */

/**
 * Starts and stops a number of threads in order.
 * Verifies that events are in the same order.
 */
public class TestVirtualThreadExclusion {
    private final static String EVENT_NAME_VIRTUAL_THREAD_START = EventNames.VirtualThreadStart;
    private final static String EVENT_NAME_VIRTUAL_THREAD_END = EventNames.VirtualThreadEnd;
    private static final String THREAD_NAME_PREFIX = "TestVirtualThread-";

    public static void main(String[] args) throws Throwable {
        // Test Java Thread Start event
        Recording recording = new Recording();
        recording.enable(EVENT_NAME_VIRTUAL_THREAD_START).withThreshold(Duration.ofMillis(0));
        recording.enable(EVENT_NAME_VIRTUAL_THREAD_END).withThreshold(Duration.ofMillis(0));
        recording.start();
        LatchedThread[] threads = startThreads();
        long[] javaThreadIds = getJavaThreadIds(threads);
        stopThreads(threads);
        recording.stop();
        List<RecordedEvent> events = Events.fromRecording(recording);
        verifyThreadExclusion(events, javaThreadIds);
    }

    private static void verifyThreadExclusion(List<RecordedEvent> events, long[] javaThreadIds) throws Exception {
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            final long eventJavaThreadId = event.getThread().getJavaThreadId();
            for (int i = 0; i < javaThreadIds.length; ++i) {
                if (eventJavaThreadId == javaThreadIds[i]) {
                    throw new Exception("Event " + event.getEventType().getName() + " has a thread id " + eventJavaThreadId + " that should have been excluded");
                }
            }
        }
    }

    private static LatchedThread[] startThreads() {
        LatchedThread threads[] = new LatchedThread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new LatchedThread(THREAD_NAME_PREFIX + i, true);
            JVM.exclude(threads[i].getThread());
            threads[i].start();
            System.out.println("Started virtual thread id=" + threads[i].getId());
        }
        return threads;
    }

    private static long[] getJavaThreadIds(LatchedThread[] threads) {
        long[] javaThreadIds = new long[threads.length];
        for (int i = 0; i < threads.length; ++i) {
            javaThreadIds[i] = threads[i].getId();
        }
        return javaThreadIds;
    }

    private static void stopThreads(LatchedThread[] threads) {
        for (LatchedThread t : threads) {
            assertTrue(JVM.isExcluded(t.getThread()), "Virtual Thread " + t.getThread() + "should be excluded");
            try {
                t.stopAndJoin();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
