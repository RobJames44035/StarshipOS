/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.util.List;

import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Verifies that a single JFR event is recorded as expected
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestSingleRecordedEvent
 */
public class TestSingleRecordedEvent {

    @Description("MyDescription")
    private static class MyEvent extends Event {
    }

    public static void main(String[] args) throws Throwable {
        try (Recording r = new Recording()) {
            r.start();
            // Commit a single event to the recording
            MyEvent event = new MyEvent();
            event.commit();
            r.stop();
            List<RecordedEvent> events = Events.fromRecording(r);
            Events.hasEvents(events);

            // Should be 1 event only
            Asserts.assertEquals(events.size(), 1);
            RecordedEvent recordedEvent = events.getFirst();
            Asserts.assertEquals(recordedEvent.getEventType().getDescription(), "MyDescription");
        }
    }
}
