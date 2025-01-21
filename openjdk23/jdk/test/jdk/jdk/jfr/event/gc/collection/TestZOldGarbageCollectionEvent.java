/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;

import static jdk.test.lib.Asserts.assertGreaterThan;
import static jdk.test.lib.Asserts.assertTrue;

import java.time.Duration;
import java.time.Instant;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @requires vm.hasJFR & vm.gc.Z
 * @key jfr
 * @library /test/lib /test/jdk
 * @run main/othervm -Xmx50m -XX:+UseZGC -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xlog:gc* jdk.jfr.event.gc.collection.TestZOldGarbageCollectionEvent
 */
public class TestZOldGarbageCollectionEvent {

    private static final String EVENT_NAME = "jdk.ZOldGarbageCollection";

    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME).withThreshold(Duration.ofMillis(0));
        recording.start();
        System.gc();
        recording.stop();

        boolean isAnyFound = false;
        for (RecordedEvent event : Events.fromRecording(recording)) {
            if (!EVENT_NAME.equals(event.getEventType().getName())) {
                continue;
            }
            System.out.println("Event: " + event);
            isAnyFound = true;
            Events.assertField(event, "gcId").atLeast(0);

            Instant startTime = event.getStartTime();
            Instant endTime = event.getEndTime();
            Duration duration = event.getDuration();
            assertGreaterThan(startTime, Instant.EPOCH, "startTime should be at least 0");
            assertGreaterThan(endTime, Instant.EPOCH, "endTime should be at least 0");
            assertGreaterThan(duration, Duration.ZERO, "Duration should be above 0");
            assertGreaterThan(endTime, startTime, "End time should be after start time");
        }
        assertTrue(isAnyFound, "No matching event found");
    }

}
