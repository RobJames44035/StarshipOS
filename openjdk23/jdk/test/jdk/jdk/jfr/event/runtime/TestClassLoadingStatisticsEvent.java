/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import java.util.List;

import jdk.jfr.Recording;
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
 * @run main/othervm jdk.jfr.event.runtime.TestClassLoadingStatisticsEvent
 */
/**
 * This test will load a number of classes. After each load step we verify that
 * the loadedClassCount and unloadedClassCount attributes are correct.
 *
 * System.gc() will trigger class unloading if -XX:+ExplicitGCInvokesConcurrent
 * is NOT set. If this flag is set G1 will never unload classes on System.gc().
 * As far as the "jfr" key guarantees no VM flags are set from the
 * outside it should be enough with System.gc().
 */
public class TestClassLoadingStatisticsEvent {

    private final static String EVENT_PATH = EventNames.ClassLoadingStatistics;
    private final static String TESTCLASS_PUBLIC_STATIC =
                "jdk.jfr.event.runtime.TestClasses$TestClassPublicStatic";
    private final static String TESTCLASS_PUBLIC_STATIC_INNER =
                "jdk.jfr.event.runtime.TestClasses$TestClassPublicStatic$TestClassPublicStaticInner";

    // Declare unloadableClassLoader as "public static"
    // to prevent the compiler to optimize away all unread writes
    public static TestClassLoader unloadableClassLoader;

    public static void main(String[] args) throws Throwable {
        // Load twice to get more stable result.
        RecordedEvent event = getCurrentEvent();
        event = getCurrentEvent();

        // Declare class ClassLoadingStatisticsHelper.
        TestClasses[] helpers = new TestClasses[10];
        event = verifyCountDelta(event, 1, 0);

        // Should load classes TestClassPrivate and TestClassPrivateStatic.
        for (int c = 0; c < helpers.length; c++) {
            helpers[c] = new TestClasses();
        }
        event = verifyCountDelta(event, 2, 0);

        // Load classes TestClassProtected and B2.
        helpers[0].new TestClassProtected();
        helpers[1].new TestClassProtected(); // This class is already loaded.
        new TestClasses.TestClassProtectedStatic();
        event = verifyCountDelta(event, 2, 0);

        // Load classes TestClassProtected1 and TestClassProtectedStatic1.
        for (int c = 0; c < helpers.length; c++) {
            helpers[c].loadClasses();
        }
        event = verifyCountDelta(event, 2, 0);

        // Load the classes with separate class loader. Will be unloaded later.
        unloadableClassLoader = new TestClassLoader();

        unloadableClassLoader.loadClass(TESTCLASS_PUBLIC_STATIC_INNER);
        event = verifyCountDelta(event, 1, 0);

        unloadableClassLoader.loadClass(TESTCLASS_PUBLIC_STATIC);
        event = verifyCountDelta(event, 1, 0);

        // This System.gc() should not unload classes, since the
        // unloadableClassLoader object is still active.
        System.gc();
        event = verifyCountDelta(event, 0, 0);

        // make classes are unloaded.
        unloadableClassLoader = null;
        System.gc();
        event = verifyCountDelta(event, 0, 2);
    }

    private static RecordedEvent getCurrentEvent() throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_PATH);
        recording.start();
        recording.stop();
        List<RecordedEvent> events = Events.fromRecording(recording);
        Asserts.assertFalse(events.isEmpty(), "No events in recording");
        RecordedEvent event = events.getFirst();
        return event;
    }

    private static RecordedEvent verifyCountDelta(
        RecordedEvent prevEvent, int loadDelta, int unloadDelta) throws Throwable {
        RecordedEvent currEvent = null;
        try {
            long prevLoad = Events.assertField(prevEvent, "loadedClassCount").getValue();
            long prevUnload = Events.assertField(prevEvent, "unloadedClassCount").getValue();

            currEvent = getCurrentEvent();
            Events.assertField(currEvent, "loadedClassCount").atLeast(prevLoad + loadDelta);
            Events.assertField(currEvent, "unloadedClassCount").atLeast(prevUnload + unloadDelta);
            return currEvent;
        } catch (Throwable t) {
            System.out.println("verifyCountDelta failed. prevEvent=" + prevEvent + ", currEvent=" + currEvent);
            throw t;
        }
    }

}
