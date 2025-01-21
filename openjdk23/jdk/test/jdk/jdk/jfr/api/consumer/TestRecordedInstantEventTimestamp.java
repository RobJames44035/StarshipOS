/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.SimpleEvent;

/**
 * @test
 * @summary Tests that an instant event gets recorded with its start time equal to its end time
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestRecordedInstantEventTimestamp
 */
public class TestRecordedInstantEventTimestamp {

    public static void main(String[] args) throws Throwable {
        try (Recording r = new Recording()) {
            r.start();
            SimpleEvent s = new SimpleEvent();
            s.commit();
            r.stop();

            List<RecordedEvent> events = Events.fromRecording(r);
            Events.hasEvents(events);
            RecordedEvent event = events.getFirst();
            Asserts.assertEquals(event.getStartTime(), event.getEndTime());
        }
    }
}
