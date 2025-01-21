/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.collection;

import static jdk.test.lib.Asserts.assertTrue;

import java.time.Duration;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm -Xlog:gc*=debug -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps jdk.jfr.event.gc.collection.TestGCGarbageCollectionEvent
 */
public class TestGCGarbageCollectionEvent {

    private final static String EVENT_NAME = GCHelper.event_garbage_collection;

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

            long longestPause = Events.assertField(event, "longestPause").atLeast(0L).getValue();
            Events.assertField(event, "sumOfPauses").atLeast(longestPause);
        }
        assertTrue(isAnyFound, "No matching event found");
    }
}
