/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;

import static jdk.test.lib.Asserts.assertGreaterThan;
import static jdk.test.lib.Asserts.assertTrue;

import java.time.Duration;
import java.time.Instant;

import java.util.LinkedList;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.Events;

import jdk.test.whitebox.WhiteBox;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR & vm.gc.Z
 * @key jfr
 * @library /test/lib /test/jdk
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UseZGC -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -Xlog:gc* jdk.jfr.event.gc.collection.TestGarbageCollectionEventWithZMinor
 */
public class TestGarbageCollectionEventWithZMinor {

    private static final String EVENT_NAME = "jdk.GarbageCollection";
    private static final String GC_NAME = "ZGC Minor";

    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME).withThreshold(Duration.ofMillis(0));
        recording.start();
        WhiteBox.getWhiteBox().youngGC();
        recording.stop();

        boolean isAnyFound = false;
        for (RecordedEvent event : Events.fromRecording(recording)) {
            if (!EVENT_NAME.equals(event.getEventType().getName())) {
                continue;
            }
            if (!GC_NAME.equals(Events.assertField(event, "name").getValue())) {
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
