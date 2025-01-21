/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdk.jfr.AnnotationElement;
import jdk.jfr.EventFactory;
import jdk.jfr.EventType;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Registered;
import jdk.test.lib.Asserts;


/**
 * @test
 * @summary EventFactory register/unregister API test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.event.TestEventFactoryRegistration
 */
public class TestEventFactoryRegistration {

    public static void main(String[] args) throws Exception {
        // Create an unregistered event
        List<AnnotationElement> annotations = new ArrayList<>();
        annotations.add(new AnnotationElement(Registered.class, false));
        EventFactory factory = EventFactory.create(annotations, Collections.emptyList());

        try {
            factory.getEventType();
            Asserts.fail("Should not be able to get event type from an unregistered event");
        } catch(IllegalStateException ise) {
            // OK as expected
        }

        // Now, register the event
        factory.register();
        EventType eventType = factory.getEventType();
        verifyRegistered(factory.getEventType());


        // Now, unregister the event
        factory.unregister();

        verifyUnregistered(eventType);

        // Create a registered event
        factory = EventFactory.create(Collections.emptyList(), Collections.emptyList());

        eventType = factory.getEventType();
        Asserts.assertNotNull(eventType);

        verifyRegistered(eventType);

    }

    private static void verifyUnregistered(EventType eventType) {
        // Verify the event is not registered
        for (EventType type : FlightRecorder.getFlightRecorder().getEventTypes()) {
            if (eventType.getId() == type.getId()) {
                Asserts.fail("Event is not unregistered");
            }
        }
    }

    private static void verifyRegistered(EventType eventType) {
        // Verify  the event is registered
        boolean found = false;
        for (EventType type : FlightRecorder.getFlightRecorder().getEventTypes()) {
            if (eventType.getId() == type.getId()) {
                found = true;
            }
        }
        if(!found) {
            Asserts.fail("Event not registered");
        }
    }

}
