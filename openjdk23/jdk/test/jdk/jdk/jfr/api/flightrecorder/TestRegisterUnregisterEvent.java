/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.flightrecorder;

import static jdk.test.lib.Asserts.assertEquals;

import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.flightrecorder.TestRegisterUnregisterEvent
 */
public class TestRegisterUnregisterEvent {

    public static void main(String[] args) throws Throwable {
        // Register before Flight Recorder is started
        FlightRecorder.register(MyEvent.class);
        // Repeat
        FlightRecorder.register(MyEvent.class);

        FlightRecorder recorder = FlightRecorder.getFlightRecorder();
        int count = 0;
        for (EventType et : recorder.getEventTypes()) {
            if (et.getName().equals(MyEvent.class.getName())) {
                count++;
            }
        }
        assertEquals(1, count);

        FlightRecorder.unregister(MyEvent.class);

        count = 0;
        for (EventType et : recorder.getEventTypes()) {
            if (et.getName().equals(MyEvent.class.getName())) {
                count++;
            }
        }
        assertEquals(0, count);

        FlightRecorder.register(MyEvent.class);

        count = 0;
        for (EventType et : recorder.getEventTypes()) {
            if (et.getName().equals(MyEvent.class.getName())) {
                count++;
            }
        }
        assertEquals(1, count);

    }
}

class MyEvent extends Event {
}
