/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.streaming;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Verifies that it is possible to stream contents from specified event
 *          settings
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 *
 * @run main/othervm jdk.jfr.api.consumer.streaming.TestEnableEvents
 */
public class TestEnableEvents {

    @Enabled(false)
    static class HorseEvent extends Event {
    }

    @Enabled(false)
    static class ElephantEvent extends Event {
    }

    @Enabled(false)
    static class TigerEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        CountDownLatch elephantLatch = new CountDownLatch(1);
        CountDownLatch tigerLatch = new CountDownLatch(1);
        CountDownLatch horseLatch = new CountDownLatch(1);

        FlightRecorder.addPeriodicEvent(ElephantEvent.class, () -> {
            HorseEvent ze = new HorseEvent();
            ze.commit();
        });

        try (RecordingStream s = new RecordingStream()) {
            s.enable(HorseEvent.class.getName()).withPeriod(Duration.ofMillis(50));
            s.enable(TigerEvent.class.getName());
            s.enable(ElephantEvent.class.getName());
            s.onEvent(TigerEvent.class.getName(), e -> {
                System.out.println("Event: " + e.getEventType().getName());
                System.out.println("Found tiger!");
                tigerLatch.countDown();
            });
            s.onEvent(HorseEvent.class.getName(), e -> {
                System.out.println("Found horse!");
                horseLatch.countDown();
            });
            s.onEvent(ElephantEvent.class.getName(), e -> {
                System.out.println("Found elelphant!");
                elephantLatch.countDown();
            });
            s.startAsync();
            TigerEvent te = new TigerEvent();
            te.commit();
            ElephantEvent ee = new ElephantEvent();
            ee.commit();
            elephantLatch.await();
            horseLatch.await();
            tigerLatch.await();
        }

    }

}
