/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.destination;

import static jdk.test.lib.Asserts.assertTrue;

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
 * @summary Set destination to an existing file. File should be overwritten.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.destination.TestDestFileExist
 */
public class TestDestFileExist {

    public static void main(String[] args) throws Throwable {
        Path dest = Paths.get(".", "my.jfr");
        System.out.println("dest=" + dest);
        Files.write(dest, "Dummy data".getBytes());
        assertTrue(Files.exists(dest), "Test error: Failed to create file");
        System.out.println("file size before recording:" + Files.size(dest));
        Recording r = new Recording();
        r.enable(EventNames.OSInformation);
        r.setDestination(dest);
        r.start();
        r.stop();
        List<RecordedEvent> events = RecordingFile.readAllEvents(dest);
        Asserts.assertFalse(events.isEmpty(), "No events found");
        System.out.println(events.getFirst());
        r.close();
    }

}
