/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.destination;

import static jdk.test.lib.Asserts.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;

/**
 * @test
 * @summary Basic test for setDestination with disk=true
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.destination.TestDestToDiskTrue
 */
public class TestDestToDiskTrue {

    public static void main(String[] args) throws Throwable {
        Path dest = Paths.get(".", "my.jfr");
        Recording r = new Recording();
        r.enable(EventNames.OSInformation);
        r.setToDisk(true);
        r.setDestination(dest);
        Asserts.assertEquals(dest, r.getDestination(), "Wrong get/set destination");
        r.start();
        r.stop();

        Asserts.assertEquals(dest, r.getDestination(), "Wrong get/set destination after stop");

        Asserts.assertTrue(Files.exists(dest), "No recording file: " + dest);
        List<RecordedEvent> events = RecordingFile.readAllEvents(dest);
        Asserts.assertFalse(events.isEmpty(), "No event found");
        System.out.printf("Found event %s%n", events.getFirst().getEventType().getName());
        System.out.printf("File size=%d, getSize()=%d%n", Files.size(dest), r.getSize());
        assertEquals(r.getSize(), 0L, "getSize() should return 0, chunks should have be released at stop");
        Asserts.assertNotEquals(Files.size(dest), 0L, "File length 0. Should at least be some bytes");
        r.close();
    }

}
