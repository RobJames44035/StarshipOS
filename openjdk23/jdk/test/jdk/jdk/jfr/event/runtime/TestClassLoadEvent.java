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
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules java.base/jdk.internal.misc
 * @build jdk.jfr.event.runtime.TestClasses
 * @run main/othervm jdk.jfr.event.runtime.TestClassLoadEvent
 */
public final class TestClassLoadEvent {

    private final static String TEST_CLASS_NAME = "jdk.jfr.event.runtime.TestClasses";
    private final static String BOOT_CLASS_LOADER_NAME = "bootstrap";
    private final static String SEARCH_CLASS_NAME = "java.lang.Object";
    private final static String SEARCH_PACKAGE_NAME = "java/lang";
    private final static String SEARCH_MODULE_NAME = "java.base";
    private final static String EVENT_NAME = EventNames.ClassLoad;

    public static void main(String[] args) throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME).withThreshold(Duration.ofMillis(0));
        TestClassLoader cl = new TestClassLoader();
        recording.start();
        cl.loadClass(TEST_CLASS_NAME);
        recording.stop();

        List<RecordedEvent> events = Events.fromRecording(recording);
        boolean isLoaded = false;
        for (RecordedEvent event : events) {
            RecordedClass loadedClass = event.getValue("loadedClass");
            if (SEARCH_CLASS_NAME.equals(loadedClass.getName())) {
                System.out.println(event);
                Events.assertClassPackage(loadedClass, SEARCH_PACKAGE_NAME);
                Events.assertClassModule(loadedClass, SEARCH_MODULE_NAME);
                RecordedClassLoader initiatingClassLoader = event.getValue("initiatingClassLoader");
                Asserts.assertEquals(cl.getClass().getName(), initiatingClassLoader.getType().getName(),
                    "Expected type " + cl.getClass().getName() + ", got type " + initiatingClassLoader.getType().getName());
                RecordedClassLoader definingClassLoader = loadedClass.getClassLoader();
                Asserts.assertEquals(BOOT_CLASS_LOADER_NAME, definingClassLoader.getName(),
                    "Expected boot loader to be the defining class loader");
                Asserts.assertNull(definingClassLoader.getType(), "boot loader should not have a type");
                isLoaded = true;
            }
        }
        Asserts.assertTrue(isLoaded, "No class load event found for class " + SEARCH_CLASS_NAME);
    }
}
