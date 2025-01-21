/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import java.time.Duration;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedClass;
import jdk.jfr.consumer.RecordedClassLoader;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.TestClassLoader;

/**
 * @test
 * @summary The test verifies that a class unload event is created when class is unloaded
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules java.base/jdk.internal.misc
 * @build jdk.jfr.event.runtime.TestClasses
 * @run main/othervm -Xlog:class+unload -Xlog:gc -Xmx16m jdk.jfr.event.runtime.TestClassUnloadEvent
 */

public final class TestClassUnloadEvent {
    private final static String TEST_CLASS_NAME = "jdk.jfr.event.runtime.TestClasses";
    private final static String EVENT_PATH = EventNames.ClassUnload;

    // Declare unloadableClassLoader as "public static"
    // to prevent the compiler to optimize away all unread writes
    public static TestClassLoader unloadableClassLoader;

    public static void main(String[] args) throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_PATH).withThreshold(Duration.ofMillis(0));
        unloadableClassLoader = new TestClassLoader();
        recording.start();
        unloadableClassLoader.loadClass(TEST_CLASS_NAME);
        unloadableClassLoader = null;
        System.gc();
        recording.stop();

        List<RecordedEvent> events = Events.fromRecording(recording);
        Events.hasEvents(events);
        boolean isAnyFound = false;
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            RecordedClass unloadedClass = event.getValue("unloadedClass");
            if (TEST_CLASS_NAME.equals(unloadedClass.getName())) {
                RecordedClassLoader definingClassLoader = unloadedClass.getClassLoader();
                Asserts.assertEquals(TestClassLoader.class.getName(), definingClassLoader.getType().getName(),
                    "Expected " + TestClassLoader.class.getName() + ", got " + definingClassLoader.getType().getName());
                Asserts.assertEquals(TestClassLoader.CLASS_LOADER_NAME, definingClassLoader.getName(),
                    "Expected " + TestClassLoader.CLASS_LOADER_NAME + ", got " + definingClassLoader.getName());
                Asserts.assertFalse(isAnyFound, "Found more than 1 event");
                isAnyFound = true;
            }
        }
        Asserts.assertTrue(isAnyFound, "No events found");
    }
}
