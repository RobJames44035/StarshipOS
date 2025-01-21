/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.jfr.event.gc.detailed;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import static jdk.test.lib.Asserts.assertFalse;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:-ExplicitGCInvokesConcurrent jdk.jfr.event.gc.detailed.TestGCHeapMemoryPoolUsageEvent
 */
public class TestGCHeapMemoryPoolUsageEvent {
    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            recording.enable(EventNames.GCHeapMemoryPoolUsage);
            recording.start();
            System.gc();
            recording.stop();

            List<RecordedEvent> events = Events.fromRecording(recording);
            System.out.println(events);
            assertFalse(events.isEmpty());

            RecordedEvent event = events.getFirst();
            Events.assertField(event, "name").notNull();
            Events.assertField(event, "used").atLeast(0L);
            Events.assertField(event, "committed").atLeast(0L);
            Events.assertField(event, "max").atLeast(-1L);
        }
    }
}


