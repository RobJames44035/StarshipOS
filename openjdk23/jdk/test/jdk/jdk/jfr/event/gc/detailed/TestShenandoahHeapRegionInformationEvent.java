/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.event.gc.detailed;

import java.util.List;

import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @bug 8221507
 * @requires vm.hasJFR & vm.gc.Shenandoah
 * @key jfr
 * @library /test/lib /test/jdk
 * @run main/othervm  -Xmx32m -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGarbageThreshold=1 jdk.jfr.event.gc.detailed.TestShenandoahHeapRegionInformationEvent
 */


public class TestShenandoahHeapRegionInformationEvent {
    private final static String EVENT_NAME = EventNames.ShenandoahHeapRegionInformation;
    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            // activate the event we are interested in and start recording
            for (EventType t : FlightRecorder.getFlightRecorder().getEventTypes()) {
                System.out.println(t.getName());
            }
            recording.enable(EVENT_NAME);
            recording.start();
            recording.stop();

            // Verify recording
            List<RecordedEvent> events = Events.fromRecording(recording);
            Events.hasEvents(events);
            for (RecordedEvent event : events) {
                Events.assertField(event, "index").notEqual(-1);
                GCHelper.assertIsValidShenandoahHeapRegionState(Events.assertField(event, "state").getValue());
                Events.assertField(event, "used").atMost(1L*1024*1024);
            }
        }
    }
}
