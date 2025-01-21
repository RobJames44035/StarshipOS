/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.flightrecorder;

import static jdk.test.lib.Asserts.fail;

import jdk.jfr.FlightRecorder;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.flightrecorder.TestListenerNull
 */
public class TestListenerNull {

    public static void main(String[] args) throws Throwable {
        try {
            FlightRecorder.addListener(null);
            fail("No exception when addListener(null)");
        } catch (NullPointerException | IllegalArgumentException e) {
        }

        try {
            FlightRecorder.removeListener(null);
            fail("No exception when removeListener(null)");
        } catch (NullPointerException | IllegalArgumentException e) {
        }
    }
}
