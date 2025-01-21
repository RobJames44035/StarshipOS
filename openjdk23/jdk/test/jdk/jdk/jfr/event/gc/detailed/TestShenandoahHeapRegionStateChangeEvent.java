/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.event.gc.detailed;

import java.time.Duration;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.GCHelper;

/**
 * @test
 * @bug 8221507
 * @requires vm.hasJFR & vm.gc.Shenandoah
 * @key jfr
 * @library /test/lib /test/jdk
 * @run main/othervm  -Xmx32m -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:ShenandoahGarbageThreshold=1 jdk.jfr.event.gc.detailed.TestShenandoahHeapRegionStateChangeEvent
 */

public class TestShenandoahHeapRegionStateChangeEvent {
    private final static String EVENT_NAME = EventNames.ShenandoahHeapRegionStateChange;

    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            // activate the event we are interested in and start recording
            recording.enable(EVENT_NAME).withThreshold(Duration.ofMillis(0));
            recording.start();

            byte[][] array = new byte[1024][];
            for (int i = 0; i < array.length; i++) {
                array[i] = new byte[20 * 1024];
            }
            recording.stop();

            // Verify recording
            List<RecordedEvent> events = Events.fromRecording(recording);
            Asserts.assertFalse(events.isEmpty(), "No events found");

            for (RecordedEvent event : events) {
                Events.assertField(event, "index").notEqual(-1);
                GCHelper.assertIsValidShenandoahHeapRegionState(Events.assertField(event, "from").getValue());
                GCHelper.assertIsValidShenandoahHeapRegionState(Events.assertField(event, "to").getValue());
                Events.assertField(event, "used").atMost(1L*1024*1024);
            }
        }
    }
}
