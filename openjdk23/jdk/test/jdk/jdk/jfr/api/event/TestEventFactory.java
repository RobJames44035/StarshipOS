/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.event;

import java.util.ArrayList;
import java.util.List;

import jdk.jfr.AnnotationElement;
import jdk.jfr.Event;
import jdk.jfr.EventFactory;
import jdk.jfr.EventType;
import jdk.jfr.Label;
import jdk.jfr.ValueDescriptor;
import jdk.test.lib.Asserts;


/**
 * @test
 * @summary EventFactory simple test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.event.TestEventFactory
 */
public class TestEventFactory {

    public static void main(String[] args) throws Exception {
        List<ValueDescriptor> vds = new ArrayList<>();
        vds.add(new ValueDescriptor(String.class, "Message"));
        vds.add(new ValueDescriptor(String.class, "message"));

        List<AnnotationElement> annos = new ArrayList<>();
        annos.add(new AnnotationElement(Label.class, "Hello World"));

        EventFactory f = EventFactory.create(annos, vds);
        EventType type = f.getEventType();
        Asserts.assertNotNull(type);

        Event e = f.newEvent();
        e.set(0, "test Message");
        e.set(1, "test message");

        try {
            e.set(100, "should fail");
            Asserts.fail("The expected exception IndexOutOfBoundsException have not been thrown");
        } catch(IndexOutOfBoundsException expected) {
            // OK, as expected
        }

        try {
            e.set(-200, "should fail again");
            Asserts.fail("The expected exception IndexOutOfBoundsException have not been thrown");
        } catch(IndexOutOfBoundsException expected) {
            // OK, as expected
        }
    }
}
