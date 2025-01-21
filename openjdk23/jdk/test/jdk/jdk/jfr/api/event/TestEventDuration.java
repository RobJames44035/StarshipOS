/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package jdk.jfr.api.event;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.SimpleEvent;

/**
 * @test
 * @summary Tests that a duration is recorded.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.event.TestEventDuration
 */
public class TestEventDuration {

    public static int counter;

    public static void main(String[] args) throws Exception {

        try(Recording r = new Recording()) {
            r.start();
            SimpleEvent e = new SimpleEvent();
            e.begin();
            for (int i = 0; i < 10_000;i++) {
                counter+=i;
            }
            e.end();
            e.commit();

            r.stop();
            List<RecordedEvent> events = Events.fromRecording(r);
            if (events.getFirst().getDuration().toNanos() < 1) {
                throw new AssertionError("Expected a duration");
            }
        }

    }

}
