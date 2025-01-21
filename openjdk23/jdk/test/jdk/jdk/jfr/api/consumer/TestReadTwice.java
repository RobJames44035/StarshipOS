/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;

/**
 * @test
 * @summary Reads the recorded file two times and verifies that both reads are the same
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestReadTwice
 */
public class TestReadTwice {

    private static class MyEvent extends Event {
    }

    public static void main(String[] args) throws Throwable {
        try (Recording r = new Recording()) {
            r.enable(MyEvent.class).withoutStackTrace();
            r.start();

            // Commit a single event to the recording
            MyEvent event = new MyEvent();
            event.commit();

            r.stop();

            // Dump the recording to a file
            Path path = Utils.createTempFile("read-twice", ".jfr");
            System.out.println("Dumping to " + path);
            r.dump(path);

            // Read all events from the file in one go
            List<RecordedEvent> events = RecordingFile.readAllEvents(path);

            // Read again the same events one by one
            try (RecordingFile rfile = new RecordingFile(path)) {
                List<RecordedEvent> events2 = new LinkedList<>();
                while (rfile.hasMoreEvents()) {
                    events2.add(rfile.readEvent());
                }

                // Compare sizes
                Asserts.assertEquals(events.size(), events2.size());
            }
        }
    }
}
