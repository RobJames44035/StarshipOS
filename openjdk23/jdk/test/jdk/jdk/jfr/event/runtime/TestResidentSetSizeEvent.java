/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import static jdk.test.lib.Asserts.assertGreaterThan;
import static jdk.test.lib.Asserts.assertLessThanOrEqual;

import java.util.ArrayList;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules jdk.jfr
 *          jdk.management
 * @run main/othervm -Xms16m -Xmx128m -Xlog:gc jdk.jfr.event.runtime.TestResidentSetSizeEvent true
 */
public class TestResidentSetSizeEvent {
    private final static String ResidentSetSizeEvent = EventNames.ResidentSetSize;

    private final static int Period = 1000;
    private final static int K = 1024;

  private static ArrayList<byte[]> data = new ArrayList<byte[]>();

    private static void generateHeapContents() {
        for (int i = 0 ; i < 64; i++) {
            for (int j = 0; j < K; j++) {
                data.add(new byte[K]);
            }
        }
    }

    private static void generateEvents(Recording recording) throws Exception {
        recording.enable(ResidentSetSizeEvent).with("period", "everyChunk");

        recording.start();

        // Generate data to force heap to grow.
        generateHeapContents();

        recording.stop();
    }

    private static void verifyExpectedEventTypes(List<RecordedEvent> events) throws Exception {
        List<RecordedEvent> filteredEvents = events.stream().filter(e -> e.getEventType().getName().equals(ResidentSetSizeEvent)).toList();

        assertGreaterThan(filteredEvents.size(), 0, "Should exist events of type: " + ResidentSetSizeEvent);

        for (RecordedEvent event : filteredEvents) {
            long size = event.getLong("size");
            long peak = event.getLong("peak");
            assertGreaterThan(size, 0L, "Should be non-zero");
            assertGreaterThan(peak, 0L, "Should be non-zero");
            assertLessThanOrEqual(size, peak, "The size should be less than or equal to peak");
        }
    }

    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            generateEvents(recording);

            var events = Events.fromRecording(recording);
            verifyExpectedEventTypes(events);
        }
    }
}
