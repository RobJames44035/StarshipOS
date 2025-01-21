/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.dump;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;

/**
 * @test
 * @summary Test copyTo and parse file
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.dump.TestDump
 */
public class TestDump {

    public static void main(String[] args) throws Exception {
        Recording r = new Recording();
        r.enable(EventNames.OSInformation);
        r.start();
        r.stop();

        Path path = Paths.get(".", "my.jfr");
        r.dump(path);
        r.close();

        Asserts.assertTrue(Files.exists(path), "Recording file does not exist: " + path);
        Asserts.assertFalse(RecordingFile.readAllEvents(path).isEmpty(), "No events found");
    }

}
