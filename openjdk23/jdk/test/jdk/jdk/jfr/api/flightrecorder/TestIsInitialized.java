/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.flightrecorder;

import jdk.jfr.FlightRecorder;
import jdk.jfr.FlightRecorderListener;
import jdk.test.lib.Asserts;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.flightrecorder.TestIsInitialized
 */
public class TestIsInitialized {

    public static void main(String[] args) throws Throwable {
        Asserts.assertFalse(FlightRecorder.isInitialized());
        FlightRecorder.addListener(new FlightRecorderListener() {
            public void recorderInitialized(FlightRecorder recorder) {
                Asserts.assertTrue(FlightRecorder.isInitialized());
            }
        });
        FlightRecorder.getFlightRecorder();
        Asserts.assertTrue(FlightRecorder.isInitialized());
    }
}
