/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import jdk.jfr.Event;
import jdk.jfr.Name;
import jdk.jfr.Recording;
import jdk.jfr.StackTrace;
import jdk.jfr.consumer.EventStream;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests EventStream::setStartTime
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestSetStartTime
 */
public final class TestSetStartTime {

    private final static int SLEEP_TIME_MS = 100;

    @Name("Mark")
    @StackTrace(false)
    public final static class Mark extends Event {
        public boolean before;
    }

    public static void main(String... args) throws Exception {
        testEventStream();
        testRecordingStream();
    }

    private static void testRecordingStream() throws InterruptedException {
        AtomicBoolean exit = new AtomicBoolean();
        int attempt = 1;
        while (!exit.get()) {
            System.out.println("Testing RecordingStream:setStartTime(...). Attempt: " + attempt++);
            AtomicBoolean firstEvent = new AtomicBoolean(true);
            try (RecordingStream r2 = new RecordingStream()) {
                Instant t = Instant.now().plus(Duration.ofMillis(SLEEP_TIME_MS / 2));
                System.out.println("Setting start time: " + t);
                r2.setStartTime(t);
                r2.onEvent(e -> {
                    if (firstEvent.get()) {
                        firstEvent.set(false);
                        if (!e.getBoolean("before")) {
                            // Skipped first event, let's exit
                            exit.set(true);
                        }
                        r2.close();
                    }
                });
                r2.startAsync();
                Mark m1 = new Mark();
                m1.before = true;
                m1.commit();
                System.out.println("First event emitted: " + Instant.now());
                Thread.sleep(SLEEP_TIME_MS);
                Mark m2 = new Mark();
                m2.before = false;
                m2.commit();
                System.out.println("Second event emitted: " + Instant.now());
                r2.awaitTermination();
            }
            System.out.println();
        }
    }

    private static void testEventStream() throws InterruptedException, Exception, IOException {
        AtomicBoolean exit = new AtomicBoolean();
        int attempt = 1;
        while (!exit.get()) {
            System.out.println("Testing EventStream:setStartTime(...). Attempt: " + attempt++);
            AtomicBoolean firstEvent = new AtomicBoolean(true);
            try (Recording r = new Recording()) {
                r.start();
                Mark event1 = new Mark();
                event1.before = true;
                event1.commit();
                Instant t = Instant.now();
                System.out.println("First event emitted: " + t);
                Thread.sleep(SLEEP_TIME_MS);
                Mark event2 = new Mark();
                event2.before = false;
                event2.commit();
                System.out.println("Second event emitted: " + Instant.now());
                try (EventStream es = EventStream.openRepository()) {
                    Instant startTime = t.plus(Duration.ofMillis(SLEEP_TIME_MS / 2));
                    es.setStartTime(startTime);
                    System.out.println("Setting start time: " + startTime);
                    es.onEvent(e -> {
                        if (firstEvent.get()) {
                            firstEvent.set(false);
                            if (!e.getBoolean("before")) {
                                // Skipped first event, let's exit
                                exit.set(true);
                            }
                            es.close();
                        }
                    });
                    es.startAsync();
                    es.awaitTermination();
                }
            }
            System.out.println();
        }
    }
}
