/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.event;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.SimpleEvent;

/**
 * @test
 * @summary Test periodic setting that involves chunks.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.event.TestChunkPeriod
 */
public class TestChunkPeriod {

    // Margin of error is to avoid issues where JFR and
    // System.currentMillis take the clock differently
    private static final Duration MARGIN_OF_ERROR = Duration.ofNanos(1_000_000_000); // 1 s

    public static void main(String[] args) throws Throwable {
        FlightRecorder.addPeriodicEvent(SimpleEvent.class, () -> {
            SimpleEvent  pe = new SimpleEvent();
            pe.commit();
        });
        testBeginChunk();
        testEndChunk();
        testEveryChunk();
    }

    private static void testBeginChunk() throws IOException {
        Recording r = new Recording();
        r.enable(SimpleEvent.class).with("period", "beginChunk");
        Instant beforeStart = Instant.now().minus(MARGIN_OF_ERROR);
        r.start();
        Instant afterStart = Instant.now().plus(MARGIN_OF_ERROR);
        r.stop();
        List<RecordedEvent> events = Events.fromRecording(r);
        Asserts.assertEquals(events.size(), 1, "Expected one event with beginChunk");
        RecordedEvent event = events.getFirst();
        Asserts.assertGreaterThanOrEqual(event.getStartTime(), beforeStart);
        Asserts.assertGreaterThanOrEqual(afterStart, event.getStartTime());
        r.close();
    }

    private static void testEndChunk() throws IOException {
        Recording r = new Recording();
        r.enable(SimpleEvent.class).with("period", "endChunk");
        r.start();
        Instant beforeStop = Instant.now().minus(MARGIN_OF_ERROR);
        r.stop();
        Instant afterStop =  Instant.now().plus(MARGIN_OF_ERROR);
        List<RecordedEvent> events = Events.fromRecording(r);
        Asserts.assertEquals(events.size(), 1, "Expected one event with endChunk");
        RecordedEvent event = events.getFirst();
        Asserts.assertGreaterThanOrEqual(event.getStartTime(), beforeStop);
        Asserts.assertGreaterThanOrEqual(afterStop, event.getStartTime());
        r.close();
    }

    private static void testEveryChunk() throws IOException {
        Recording r = new Recording();
        r.enable(SimpleEvent.class).with("period", "everyChunk");
        r.start();
        r.stop();
        List<RecordedEvent> events = Events.fromRecording(r);
        Asserts.assertEquals(events.size(), 2, "Expected two events with everyChunk");
        r.close();
    }
}
