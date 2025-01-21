/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.objectcount;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;


public class ObjectCountAfterGCEvent {

    private static final String objectCountEventPath = EventNames.ObjectCountAfterGC;
    private static final String gcEventPath = EventNames.GarbageCollection;
    private static final String heapSummaryEventPath = EventNames.GCHeapSummary;

    public static void test(String gcName) throws Exception {
        Recording recording = new Recording();
        recording.enable(objectCountEventPath);
        recording.enable(gcEventPath);
        recording.enable(heapSummaryEventPath);

        ObjectCountEventVerifier.createTestData();
        recording.start();
        System.gc();
        System.gc();
        recording.stop();

        System.out.println("gcName=" + gcName);
        List<RecordedEvent> events = Events.fromRecording(recording);
        for (RecordedEvent event : events) {
            System.out.println("Event: " + event);
        }

        Optional<RecordedEvent> gcEvent = events.stream()
                                .filter(e -> isMySystemGc(e, gcName))
                                .findFirst();
        Asserts.assertTrue(gcEvent.isPresent(), "No event System.gc event of type " + gcEventPath);
        System.out.println("Found System.gc event: " + gcEvent.get());
        int gcId = Events.assertField(gcEvent.get(), "gcId").getValue();

        List<RecordedEvent> objCountEvents = events.stream()
                                .filter(e -> Events.isEventType(e, objectCountEventPath))
                                .filter(e -> isGcId(e, gcId))
                                .collect(Collectors.toList());
        Asserts.assertFalse(objCountEvents.isEmpty(), "No objCountEvents for gcId=" + gcId);

        Optional<RecordedEvent> heapSummaryEvent = events.stream()
                                .filter(e -> Events.isEventType(e, heapSummaryEventPath))
                                .filter(e -> isGcId(e, gcId))
                                .filter(e -> "After GC".equals(Events.assertField(e, "when").getValue()))
                                .findFirst();
        Asserts.assertTrue(heapSummaryEvent.isPresent(), "No heapSummary for gcId=" + gcId);
        System.out.println("Found heapSummaryEvent: " + heapSummaryEvent.get());

        Events.assertField(heapSummaryEvent.get(), "heapUsed").atLeast(0L).getValue();
        ObjectCountEventVerifier.verify(objCountEvents);
    }

    private static boolean isGcId(RecordedEvent event, int gcId) {
        return gcId == (int)Events.assertField(event, "gcId").getValue();
    }

    private static boolean isMySystemGc(RecordedEvent event, String gcName) {
        return Events.isEventType(event, gcEventPath) &&
            gcName.equals(Events.assertField(event, "name").getValue()) &&
            "System.gc()".equals(Events.assertField(event, "cause").getValue());
    }

}
