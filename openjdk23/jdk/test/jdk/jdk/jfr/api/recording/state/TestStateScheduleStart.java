/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.state;

import java.time.Duration;
import java.time.Instant;

import jdk.jfr.Recording;
import jdk.jfr.RecordingState;
import jdk.test.lib.jfr.CommonHelper;
import jdk.test.lib.jfr.VoidFunction;

/**
 * @test
 * @summary Test Recording state
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.state.TestStateScheduleStart
 */
public class TestStateScheduleStart {

    public static void main(String[] args) throws Throwable {
        Recording r = new Recording();
        CommonHelper.verifyRecordingState(r, RecordingState.NEW);

        Instant start = Instant.now();
        r.scheduleStart(Duration.ofMillis(2000));

        System.out.println("Wait for recording to start: " + start);
        CommonHelper.waitForRecordingState(r, RecordingState.RUNNING);

        // Duration should be about 2000 ms.
        // Test servers vary too much in performance to make an accurate check.
        // We only check that we don't time out after 20 seconds.
        Instant started = Instant.now();
        long millis = start.until(started).toMillis();
        System.out.println("Recording started at " + started + ". Delta millis=" + millis + ", expected about 2000");

        verifyIllegalState(() -> r.start(), "double start()");
        r.stop();
        CommonHelper.verifyRecordingState(r, RecordingState.STOPPED);
        r.close();
        CommonHelper.verifyRecordingState(r, RecordingState.CLOSED);
    }

    private static void verifyIllegalState(VoidFunction f, String msg) throws Throwable {
        CommonHelper.verifyException(f, msg, IllegalStateException.class);
    }
}
