/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.dump;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.FileHelper;

/**
 * @test
 * @summary Test copyTo and parse file
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.dump.TestDumpLongPath
 */
public class TestDumpLongPath {

    public static void main(String[] args) throws Exception {
        Recording r = new Recording();
        final String eventPath = EventNames.OSInformation;
        r.enable(eventPath);
        r.start();
        r.stop();

        Path dir = FileHelper.createLongDir(Paths.get("."));
        Path path = Paths.get(dir.toString(), "my.jfr");
        r.dump(path);
        r.close();

        Asserts.assertTrue(Files.exists(path), "Recording file does not exist: " + path);
        List<RecordedEvent> events = RecordingFile.readAllEvents(path);
        Asserts.assertFalse(events.isEmpty(), "No events found");
        String foundEventPath = events.getFirst().getEventType().getName();
        System.out.printf("Found event: %s%n", foundEventPath);
        Asserts.assertEquals(foundEventPath, eventPath, "Wrong event");
    }

}
