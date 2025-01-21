/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.streaming;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingFile;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Verifies that EventStream::openRepository() read from the latest flush
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.streaming.TestLatestEvent
 */
public class TestLatestEvent {

    @Name("NotLatest")
    static class NotLatestEvent extends Event {

        public int id;
    }

    @Name("Latest")
    static class LatestEvent extends Event {
    }

    @Name("MakeChunks")
    static class MakeChunks extends Event {
    }

    public static void main(String... args) throws Exception {
        CountDownLatch notLatestEvent = new CountDownLatch(6);
        CountDownLatch beginChunks = new CountDownLatch(1);

        try (RecordingStream r = new RecordingStream()) {
            r.setMaxSize(1_000_000_000);
            r.onEvent("MakeChunks", event -> {
                System.out.println(event);
                beginChunks.countDown();
            });
            r.onEvent("NotLatest", event -> {
                System.out.println(event);
                notLatestEvent.countDown();
            });
            r.startAsync();
            MakeChunks e = new MakeChunks();
            e.commit();

            System.out.println("Waiting for first chunk");
            beginChunks.await();
            // Create 5 chunks with events in the repository
            for (int i = 0; i < 5; i++) {
                System.out.println("Creating empty chunk");
                try (Recording r1 = new Recording()) {
                    r1.start();
                    NotLatestEvent notLatest = new NotLatestEvent();
                    notLatest.id = i;
                    notLatest.commit();
                    r1.stop();
                }
            }
            System.out.println("All empty chunks created");

            // Create event in new chunk
            NotLatestEvent notLatest = new NotLatestEvent();
            notLatest.id = 5;
            notLatest.commit();

            // This latch ensures thatNotLatest has been
            // flushed and a new valid position has been written
            // to the chunk header
            boolean timeout = notLatestEvent.await(80, TimeUnit.SECONDS);
            if (notLatestEvent.getCount() != 0) {
                System.out.println("timeout = " + timeout);
                Path repo = Path.of(System.getProperty("jdk.jfr.repository"));
                System.out.println("repo = " + repo);
                List<Path> files = new ArrayList<>(Files.list(repo).toList());
                files.sort(Comparator.comparing(Path::toString));
                for (Path f : files) {
                    System.out.println("------------");
                    System.out.println("File: " + f);
                    for (var event : RecordingFile.readAllEvents(f)) {
                        System.out.println(event);
                    }
                }
               Recording rec =  FlightRecorder.getFlightRecorder().takeSnapshot();
               Path p = Paths.get("error-not-latest.jfr").toAbsolutePath();
               rec.dump(p);
               System.out.println("Dumping repository as a file for inspection at " + p);
               throw new Exception("Timeout 80 s. Expected 6 event, but got "  + (6 - notLatestEvent.getCount()));
            }

            try (EventStream s = EventStream.openRepository()) {
                System.out.println("EventStream opened");
                AtomicBoolean foundLatest = new AtomicBoolean();
                s.onEvent(event -> {
                    String name = event.getEventType().getName();
                    System.out.println("Found event " + name);
                    foundLatest.set(name.equals("Latest"));
                });
                s.startAsync();
                // Must loop here as there is no guarantee
                // that the parser thread starts before event
                // is flushed
                while (!foundLatest.get()) {
                    LatestEvent latest = new LatestEvent();
                    latest.commit();
                    System.out.println("Latest event emitted. Waiting 1 s ...");
                    Thread.sleep(1000);
                }
            }
        }
    }
}
