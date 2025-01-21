/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.event.allocation;

import java.util.concurrent.CountDownLatch;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingStream;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Tests ObjectAllocationSampleEvent
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm -XX:+UseTLAB -XX:TLABSize=2k -XX:-ResizeTLAB jdk.jfr.event.allocation.TestObjectAllocationSampleEvent
 */
public class TestObjectAllocationSampleEvent {
    private static final String EVENT_NAME = EventNames.ObjectAllocationSample;
    private static final int OBJECT_SIZE = 4 * 1024;
    private static final int OBJECTS_TO_ALLOCATE = 16;
    private static final String BYTE_ARRAY_CLASS_NAME = new byte[0].getClass().getName();

    // Make sure allocation isn't dead code eliminated.
    public static byte[] tmp;

    public static void main(String... args) throws Exception {
        CountDownLatch delivered = new CountDownLatch(1);
        Thread current = Thread.currentThread();
        try (RecordingStream rs = new RecordingStream()) {
            rs.enable(EVENT_NAME);
            rs.onEvent(EVENT_NAME, e -> {
                if (verify(e, current)) {
                    delivered.countDown();
                }
            });
            rs.startAsync();
            for (int i = 0; i < OBJECTS_TO_ALLOCATE; ++i) {
                tmp = new byte[OBJECT_SIZE];
            }
            delivered.await();
        }
    }

    private static boolean verify(RecordedEvent event, Thread thread) {
        if (thread.getId() != event.getThread().getJavaThreadId()) {
            return false;
        }
        if (Events.assertField(event, "objectClass.name").notEmpty().getValue().equals(BYTE_ARRAY_CLASS_NAME)) {
            Events.assertField(event, "weight").atLeast(1L);
            return true;
        }
        return false;
    }
}
