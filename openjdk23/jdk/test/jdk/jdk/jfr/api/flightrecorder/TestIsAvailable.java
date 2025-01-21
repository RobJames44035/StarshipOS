/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.flightrecorder;

import jdk.jfr.FlightRecorder;
import jdk.test.lib.Asserts;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm -XX:+FlightRecorder jdk.jfr.api.flightrecorder.TestIsAvailable true
 * @run main/othervm -XX:-FlightRecorder jdk.jfr.api.flightrecorder.TestIsAvailable  false
 * @run main/othervm jdk.jfr.api.flightrecorder.TestIsAvailable true
 */
public class TestIsAvailable {

    public static void main(String[] args) throws Throwable {
        boolean expected = Boolean.parseBoolean(args[0]);
        System.out.println("Expected: " + expected);
        Asserts.assertTrue(expected == FlightRecorder.isAvailable());
    }
}
