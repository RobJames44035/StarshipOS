/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.streaming;

import java.util.concurrent.CountDownLatch;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Period;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Verifies that it is possible to stream contents of ongoing
 *          recordings
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm -Xlog:jfr+system+streaming=trace
 *      jdk.jfr.api.consumer.streaming.TestStartSingleChunk
 */
public class TestStartSingleChunk {

    @Period("500 ms")
    static class ElkEvent extends Event {
    }

    static class FrogEvent extends Event {
    }

    static class LionEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        CountDownLatch frogLatch = new CountDownLatch(1);
        CountDownLatch lionLatch = new CountDownLatch(1);
        CountDownLatch elkLatch = new CountDownLatch(3);

        FlightRecorder.addPeriodicEvent(ElkEvent.class, () -> {
            ElkEvent ee = new ElkEvent();
            ee.commit();
        });
        try (RecordingStream s = new RecordingStream()) {
            s.onEvent(ElkEvent.class.getName(), e -> {
                System.out.println("Found elk!");
                elkLatch.countDown();
            });
            s.onEvent(LionEvent.class.getName(), e -> {
                System.out.println("Found lion!");
                lionLatch.countDown();
            });
            s.onEvent(FrogEvent.class.getName(), e -> {
                System.out.println("Found frog!");
                frogLatch.countDown();
            });
            s.startAsync();
            FrogEvent fe = new FrogEvent();
            fe.commit();

            LionEvent le = new LionEvent();
            le.commit();

            frogLatch.await();
            lionLatch.await();
            elkLatch.await();
        }
    }
}
