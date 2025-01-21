/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.recording.destination;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.SimpleEventHelper;

/**
 * @test
 * @summary Call setDestination() when recording in different states
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.recording.destination.TestDestState
 */
public class TestDestState {

    public static void main(String[] args) throws Throwable {
        Recording r = new Recording();
        SimpleEventHelper.enable(r, true);

        final Path newDest = Paths.get(".", "new.jfr");
        r.setDestination(newDest);
        System.out.println("new dest: " + r.getDestination());
        Asserts.assertEquals(newDest, r.getDestination(), "Wrong get/set dest when new");

        r.start();
        SimpleEventHelper.createEvent(0);
        Thread.sleep(100);
        final Path runningDest = Paths.get(".", "running.jfr");
        r.setDestination(runningDest);
        System.out.println("running dest: " + r.getDestination());
        Asserts.assertEquals(runningDest, r.getDestination(), "Wrong get/set dest when running");
        SimpleEventHelper.createEvent(1);

        r.stop();
        SimpleEventHelper.createEvent(2);

        // Expect recording to be saved at destination that was set when
        // the recording was stopped, which is runningDest.
        Asserts.assertTrue(Files.exists(runningDest), "No recording file: " + runningDest);
        List<RecordedEvent> events = RecordingFile.readAllEvents(runningDest);
        Asserts.assertFalse(events.isEmpty(), "No event found");
        System.out.printf("Found event %s%n", events.getFirst().getEventType().getName());
        r.close();
    }

}
