/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.jfr.api.metadata.annotations;

import jdk.jfr.Registered;
import jdk.test.lib.Asserts;
import jdk.jfr.Event;
import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.annotations.TestRegistered
 */
public class TestRegistered {

    static class RegisteredByDefaultEvent extends Event {
    }

    @Registered(false)
    static class NotRegisteredByDefaultEvent extends Event {
    }

    public static void main(String[] args) throws Exception {
        try {
            EventType.getEventType(NotRegisteredByDefaultEvent.class);
            throw new Exception("Should not be able to get event type from unregistered event type");
        } catch (IllegalStateException ise) {
            // as expected
        }
        EventType registered = EventType.getEventType(RegisteredByDefaultEvent.class);
        boolean registeredWorking = false;
        for (EventType type : FlightRecorder.getFlightRecorder().getEventTypes()) {
            if (registered.getId() == type.getId()) {
                registeredWorking = true;
            }
        }
        if (!registeredWorking) {
            Asserts.fail("Default regsitration is not working, can't validate @NoAutoRegistration");
        }
    }
}
