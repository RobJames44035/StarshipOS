/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.event;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Test periodic events.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.event.TestPeriod
 */
public class TestPeriod {

    static class Pulse extends Event {
    }

    public static void main(String[] args) throws Throwable {

        CountDownLatch latch = new CountDownLatch(3);
        FlightRecorder.addPeriodicEvent(Pulse.class, () -> {
           Pulse event = new Pulse();
           event.commit();
           latch.countDown();
        });

        try (Recording r = new Recording()) {
            r.enable(Pulse.class).withPeriod(Duration.ofMillis(500));
            r.start();
            latch.await();
            r.stop();
            List<RecordedEvent> events = Events.fromRecording(r);
            if (events.size() < 3) {
                System.out.println(events);
                throw new Exception("Expected at least 3 events");
            }
        }

    }

}
