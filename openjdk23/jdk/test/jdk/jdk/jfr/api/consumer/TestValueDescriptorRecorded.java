/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.util.List;

import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Recording;
import jdk.jfr.ValueDescriptor;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;


/**
 * @test
 * @summary Verifies that the recorded value descriptors are correct
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm  jdk.jfr.api.consumer.TestValueDescriptorRecorded
 */
public class TestValueDescriptorRecorded {

    private static class MyEvent extends Event {
        @Label("myLabel")
        @Description("myDescription")
        int myValue;
    }

    public static void main(String[] args) throws Throwable {
        try (Recording r = new Recording()) {
            r.enable(MyEvent.class).withoutStackTrace();
            r.start();
            MyEvent event = new MyEvent();
            event.commit();
            r.stop();

            List<RecordedEvent> events = Events.fromRecording(r);
            Events.hasEvents(events);
            RecordedEvent recordedEvent = events.getFirst();
            for (ValueDescriptor desc : recordedEvent.getFields()) {
                if ("myValue".equals(desc.getName())) {
                    Asserts.assertEquals(desc.getLabel(), "myLabel");
                    Asserts.assertEquals(desc.getDescription(), "myDescription");
                    Asserts.assertEquals(desc.getTypeName(), int.class.getName());
                    Asserts.assertFalse(desc.isArray());
                    Asserts.assertNull(desc.getContentType());
                }
            }
        }
    }
}
