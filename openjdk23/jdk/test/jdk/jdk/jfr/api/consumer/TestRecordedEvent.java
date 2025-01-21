/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.util.List;

import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.ValueDescriptor;
import jdk.jfr.consumer.RecordedClass;
import jdk.jfr.consumer.RecordedClassLoader;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Verifies the methods of the RecordedEvent
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestRecordedEvent
 */
public class TestRecordedEvent {

    static class MyClass {
    }

    static class TestEvent extends Event {
        @Description("MyField")
        Class<?> clzField = String.class;
        int intField;
        String stringField = "myString";
        Class<?> myClass = MyClass.class;
    }

    public static void main(String[] args) throws Throwable {
        try (Recording r = new Recording()) {
            r.start();
            TestEvent t = new TestEvent();
            t.commit();
            r.stop();

            List<RecordedEvent> events = Events.fromRecording(r);
            Events.hasEvents(events);
            Asserts.assertEquals(events.size(), 1);
            RecordedEvent event = events.getFirst();

            List<ValueDescriptor> descriptors = event.getFields();

            System.out.println("Descriptors");
            for (ValueDescriptor descriptor : descriptors) {
                System.out.println(descriptor.getName());
                System.out.println(descriptor.getTypeName());
            }
            System.out.println("Descriptors end");

            Object recordedClass = event.getValue("clzField");
            Asserts.assertTrue(recordedClass instanceof RecordedClass, "Expected Recorded Class got " + recordedClass);

            Object recordedInt = event.getValue("intField");
            Asserts.assertTrue(recordedInt instanceof Integer);

            Object recordedString = event.getValue("stringField");
            System.out.println("recordedString class: " + recordedString.getClass());
            Asserts.assertTrue(recordedString instanceof String);

            Object myClass = event.getValue("myClass");
            Asserts.assertTrue(myClass instanceof RecordedClass, "Expected Recorded Class got " + recordedClass);

            RecordedClass myRecClass = (RecordedClass) myClass;
            Asserts.assertEquals(MyClass.class.getName(), myRecClass.getName(), "Got " + myRecClass.getName());

            Object recordedClassLoader = myRecClass.getValue("classLoader");
            Asserts.assertTrue(recordedClassLoader instanceof RecordedClassLoader, "Expected Recorded ClassLoader got " + recordedClassLoader);

            RecordedClassLoader myRecClassLoader = (RecordedClassLoader) recordedClassLoader;
            ClassLoader cl = MyClass.class.getClassLoader();
            Asserts.assertEquals(cl.getClass().getName(), myRecClassLoader.getType().getName(), "Expected Recorded ClassLoader type to equal loader type");

            Asserts.assertNotNull(myRecClass.getModifiers());
        }
    }
}
