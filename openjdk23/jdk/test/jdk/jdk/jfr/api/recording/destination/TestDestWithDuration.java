/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.destination;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.RecordingState;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.CommonHelper;
import jdk.test.lib.jfr.SimpleEventHelper;

/**
 * @test
 * @summary Test that recording is auto closed after duration
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.destination.TestDestWithDuration
 */
public class TestDestWithDuration {

    public static void main(String[] args) throws Throwable {
        Path dest = Paths.get(".", "my.jfr");
        Recording r = new Recording();
        SimpleEventHelper.enable(r, true);
        r.setDestination(dest);
        r.start();
        SimpleEventHelper.createEvent(1);

        // Waiting for recording to auto close after duration
        r.setDuration(Duration.ofSeconds(1));
        System.out.println("Waiting for recording to auto close after duration");
        CommonHelper.waitForRecordingState(r, RecordingState.CLOSED);
        System.out.println("recording state = " + r.getState());
        Asserts.assertEquals(r.getState(), RecordingState.CLOSED, "Recording not closed");

        Asserts.assertTrue(Files.exists(dest), "No recording file: " + dest);
        System.out.printf("Recording file size=%d%n", Files.size(dest));
        Asserts.assertNotEquals(Files.size(dest), 0L, "File length 0. Should at least be some bytes");

        List<RecordedEvent> events = RecordingFile.readAllEvents(dest);
        Asserts.assertFalse(events.isEmpty(), "No event found");
        System.out.printf("Found event %s%n", events.getFirst().getEventType().getName());
    }

}
