/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.time;

import java.time.Instant;

import jdk.jfr.Recording;
import jdk.test.lib.Asserts;

/**
 * @test
 * @key jfr
 * @summary Test recording times with concurrent recordings
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.time.TestTimeMultiple
 */

public class TestTimeMultiple {

    public static void main(String[] args) throws Throwable {
        Recording rA = new Recording();
        Asserts.assertNull(rA.getStartTime(), "getStartTime() not null before start");
        Asserts.assertNull(rA.getStopTime(), "getStopTime() not null before start");

        final Instant beforeStartA = Instant.now();
        rA.start();
        final Instant afterStartA = Instant.now();

        Recording rB = new Recording();
        Asserts.assertNull(rB.getStartTime(), "getStartTime() not null before start");
        Asserts.assertNull(rB.getStopTime(), "getStopTime() not null before start");

        final Instant beforeStartB = Instant.now();
        rB.start();
        final Instant afterStartB = Instant.now();

        final Instant beforeStopB = Instant.now();
        rB.stop();
        final Instant afterStopB = Instant.now();

        final Instant beforeStopA = Instant.now();
        rA.stop();
        final Instant afterStopA = Instant.now();

        rA.close();
        rB.close();

        Asserts.assertNotNull(rA.getStartTime(), "getStartTime() null after start");
        Asserts.assertNotNull(rA.getStopTime(), "getStopTime() null after stop");
        Asserts.assertGreaterThanOrEqual(rA.getStartTime(), beforeStartA, "getStartTime() < beforeStart");
        Asserts.assertLessThanOrEqual(rA.getStartTime(), afterStartA, "getStartTime() > afterStart");
        Asserts.assertGreaterThanOrEqual(rA.getStopTime(), beforeStopA, "getStopTime() < beforeStop");
        Asserts.assertLessThanOrEqual(rA.getStopTime(), afterStopA, "getStopTime() > afterStop");

        Asserts.assertNotNull(rB.getStartTime(), "getStartTime() null after start");
        Asserts.assertNotNull(rB.getStopTime(), "getStopTime() null after stop");
        Asserts.assertGreaterThanOrEqual(rB.getStartTime(), beforeStartB, "getStartTime() < beforeStart");
        Asserts.assertLessThanOrEqual(rB.getStartTime(), afterStartB, "getStartTime() > afterStart");
        Asserts.assertGreaterThanOrEqual(rB.getStopTime(), beforeStopB, "getStopTime() < beforeStop");
        Asserts.assertLessThanOrEqual(rB.getStopTime(), afterStopB, "getStopTime() > afterStop");
    }

}
