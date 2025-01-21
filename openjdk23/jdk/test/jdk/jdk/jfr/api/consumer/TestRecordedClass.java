/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.lang.reflect.Modifier;
import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedClass;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @summary Verifies methods of RecordedClass
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestRecordedClass
 */
public class TestRecordedClass {

    static class TestEvent extends Event {
        Class<?> typeA;
        Class<?> typeB;
    }

    private static class TypeA {
    }

    public final static class TypeB {
    }

    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            recording.start();
            TestEvent event = new TestEvent();
            event.typeA = TypeA.class;
            event.typeB = TypeB.class;
            event.commit();
            recording.stop();

            List<RecordedEvent> events = Events.fromRecording(recording);
            Events.hasEvents(events);
            for (RecordedEvent recordedEvent : events) {
                RecordedClass typeA = recordedEvent.getClass("typeA");
                RecordedClass typeB = recordedEvent.getClass("typeB");
                assertModifiers(typeA, TypeA.class);
                assertModifiers(typeB, TypeB.class);
                assertName(typeA, TypeA.class);
                assertName(typeB, TypeB.class);
                assertClassLoader(typeA, TypeA.class.getClassLoader());
                assertClassLoader(typeB, TypeB.class.getClassLoader());
                assertId(typeA);
                assertId(typeB);
                if (typeA.getId() == typeB.getId()) {
                    throw new Exception("Same ID for different classes");
                }
            }
        }
    }

    private static void assertId(RecordedClass recordedClass) throws Exception {
        long id = recordedClass.getId();
        if (id < 1 || id >= 1024 * 1024) {
            throw new Exception("Expected class ID to be above 1 and below 1 M");
        }
    }

    private static void assertClassLoader(RecordedClass recordedClass, ClassLoader classLoader) throws Exception {
        String expected = classLoader.getClass().getName();
        String actual = recordedClass.getClassLoader().getType().getName();
        if (!expected.equals(actual)) {
            throw new Exception("Expected class loader to be " + expected + ", was " + actual);
        }
    }

    private static void assertName(RecordedClass recordedClass, Class<?> clazz) throws Exception {
        String className = clazz.getClass().getName();
        if (className.equals(recordedClass.getName())) {
            throw new Exception("Expected class to be named " + className);
        }
    }

    private static void assertModifiers(RecordedClass recordedClass, Class<?> clazz) throws Exception {
        int modifiers = clazz.getModifiers();
        if (modifiers != recordedClass.getModifiers()) {
            String expected = Modifier.toString(modifiers);
            String actual = Modifier.toString(recordedClass.getModifiers());
            throw new Exception("Expected modifier to be '" + expected + "', was '" + actual + "'");
        }
    }
}
