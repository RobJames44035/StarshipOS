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
 * @run main/othervm -XX:-ExplicitGCInvokesConcurrent jdk.jfr.event.gc.detailed.TestGCHeapMemoryUsageEvent
 */
public class TestGCHeapMemoryUsageEvent {
    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            recording.enable(EventNames.GCHeapMemoryUsage);
            recording.start();
            System.gc();
            recording.stop();

            List<RecordedEvent> events = Events.fromRecording(recording);
            System.out.println(events);
            assertFalse(events.isEmpty());
            RecordedEvent event = events.getFirst();
            Events.assertField(event, "used").above(0L);
            Events.assertField(event, "committed").above(0L);
            Events.assertField(event, "max").above(0L);
        }
    }
}

