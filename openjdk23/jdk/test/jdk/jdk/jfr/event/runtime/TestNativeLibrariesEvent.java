/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import static jdk.test.lib.Asserts.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Platform;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @bug 8216559
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.runtime.TestNativeLibrariesEvent
 */
public class TestNativeLibrariesEvent {

    private final static String EVENT_NAME = EventNames.NativeLibrary;

    public static void main(String[] args) throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();
        recording.stop();

        List<String> expectedLibs = getExpectedLibs();
        for (RecordedEvent event : Events.fromRecording(recording)) {
            System.out.println("Event:" + event);
            long unsignedTopAddress = event.getValue("topAddress");
            long unsignedBaseAddress = event.getValue("baseAddress");
            assertValidAddresses(unsignedBaseAddress, unsignedTopAddress);
            String lib = Events.assertField(event, "name").notEmpty().getValue();
            for (String expectedLib : new ArrayList<>(expectedLibs)) {
                if (lib.contains(expectedLib)) {
                    expectedLibs.remove(expectedLib);
                }
            }
        }
        assertTrue(expectedLibs.isEmpty(), "Missing libraries:" + expectedLibs.stream().collect(Collectors.joining(", ")));
    }

    private static List<String> getExpectedLibs() throws Throwable {
        List<String> libs = new ArrayList<String>();
        String[] names = { "jvm", "java", "zip" };
        for (String name : names) {
            libs.add(Platform.buildSharedLibraryName(name));
        }
        return libs;
    }

    private static void assertValidAddresses(long unsignedBaseAddress, long unsignedTopAddress) throws Exception {
        if (unsignedTopAddress != 0) { // guard against missing value (0)
            if (Long.compareUnsigned(unsignedTopAddress, unsignedBaseAddress) < 0) {
                throw new Exception("Top address " + Long.toHexString(unsignedTopAddress) + " is below base addess " + Long.toHexString(unsignedBaseAddress));
            }
        }
    }
}
