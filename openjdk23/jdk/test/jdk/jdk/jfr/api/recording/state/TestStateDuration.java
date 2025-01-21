/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.state;

import java.time.Duration;
import java.time.Instant;

import jdk.jfr.Recording;
import jdk.jfr.RecordingState;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.CommonHelper;
import jdk.test.lib.jfr.VoidFunction;

/**
 * @test
 * @summary Test Recording state
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.state.TestStateDuration
 */
public class TestStateDuration {

    public static void main(String[] args) throws Throwable {
        Duration duration = Duration.ofSeconds(2);
        Recording r = new Recording();
        r.setDuration(duration);
        CommonHelper.verifyRecordingState(r, RecordingState.NEW);
        Instant start = Instant.now();
        System.out.println("Recording with duration " + duration + " started at " + start);
        r.start();

        // Wait for recording to stop automatically
        System.out.println("Waiting for recording to reach STOPPED state");
        CommonHelper.waitForRecordingState(r, RecordingState.STOPPED);
        Instant stop = Instant.now();
        Duration measuredDuration = start.until(stop);
        System.out.println("Recording stopped at " + stop + ". Measured duration " + measuredDuration);
        // Timer task uses System.currentMillis, and java.time uses other source.
        Duration deltaDueToClockNotInSync = Duration.ofMillis(100);
        Asserts.assertGreaterThan(measuredDuration.plus(deltaDueToClockNotInSync), duration);
        verifyIllegalState(() -> r.start(), "start() after stop()");
        r.close();
        CommonHelper.verifyRecordingState(r, RecordingState.CLOSED);
    }

    private static void verifyIllegalState(VoidFunction f, String msg) throws Throwable {
        CommonHelper.verifyException(f, msg, IllegalStateException.class);
    }
}
