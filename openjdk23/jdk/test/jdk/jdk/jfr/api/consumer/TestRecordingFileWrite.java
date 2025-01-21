/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.jfr.api.consumer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import jdk.jfr.Configuration;
import jdk.jfr.Event;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordingFile;

/**
 * @test
 * @summary Tests RecordingFile::write(Path, Predicate<RecordedEvent>)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.TestRecordingFileWrite
 */
public class TestRecordingFileWrite {

    static class ScrubEvent extends Event {
        long id;
        String message;
    }

    public static void main(String... args) throws Exception {
        Path scrubbed = Paths.get("scrubbed.jfr");
        Path original = Paths.get("original.jfr");

        createRecording(original);
        Queue<String> ids = scrubRecording(original, scrubbed);
        System.out.println("Original size: " + Files.size(original));
        System.out.println("Scrubbed size: " + Files.size(scrubbed));
        System.out.println("Scrubbed event count: " + ids.size());
        if (ids.size() < 50_000) {
            throw new AssertionError("Expected at least 50 000 events to be included");
        }
        verify(scrubbed, ids);
    }

    private static void verify(Path scrubbed, Queue<String> events) throws Exception {
        try (RecordingFile rf = new RecordingFile(scrubbed)) {
            while (rf.hasMoreEvents()) {
                String event = rf.readEvent().toString();
                String expected = events.poll();
                if (!event.equals(expected)) {
                    System.out.println("Found:");
                    System.out.println(event);
                    System.out.println("Expected:");
                    System.out.println(expected);
                    throw new Exception("Found event that should not be there. See log");
                }
            }
        }
        if (!events.isEmpty()) {
            throw new AssertionError("Missing events " + events);
        }
    }

    private static Queue<String> scrubRecording(Path original, Path scrubbed) throws IOException {
        Queue<String> events = new ArrayDeque<>(150_000);
        Random random = new Random();
        try (RecordingFile rf = new RecordingFile(original)) {
            rf.write(scrubbed, event -> {
                boolean keep = random.nextInt(10) == 0;
                if (event.getEventType().getName().equals("jdk.OldObjectSample")) {
                    System.out.println(event);
                    keep = true;
                }
                if (keep) {
                    events.add(event.toString());
                }
                return keep;
            });
        }
        return events;
    }

    private static void createRecording(Path file) throws Exception {
        // Use profile configuration so more complex data structures
        // are serialized
        Configuration c = Configuration.getConfiguration("profile");
        try (Recording r = new Recording(c)) {
            r.start();
            String s = "A";
            // Generate sufficient number of events to provoke
            // chunk rotations
            for (int i = 0; i < 1_000_000; i++) {
                ScrubEvent event = new ScrubEvent();
                event.message = s.repeat(i % 30);
                event.id = i;
                event.commit();
            }
            r.stop();
            r.dump(file);
        }
    }
}
