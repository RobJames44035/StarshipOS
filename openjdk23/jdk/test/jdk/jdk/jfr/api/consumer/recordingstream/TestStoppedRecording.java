/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import java.util.concurrent.CountDownLatch;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests that a RecordingStream is closed if the underlying Recording
 *          is stopped.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestStoppedRecording
 */
public class TestStoppedRecording {

    private static final class StopEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try (RecordingStream rs = new RecordingStream()) {
            rs.onEvent(e -> {
                FlightRecorder.getFlightRecorder().getRecordings().getFirst().stop();
            });
            rs.onClose(() -> {
                latch.countDown();
            });
            rs.startAsync();
            StopEvent stop = new StopEvent();
            stop.commit();
            latch.await();
        }
    }
}
