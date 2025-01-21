/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.api.consumer.recordingstream;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import jdk.jfr.Event;
import jdk.jfr.Name;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests RecordingStream::dump(Path)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestDump
 */
public class TestDump {

    @Name("DumpTest")
    static class DumpEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        testUnstarted();
        testStopped();
        testClosed();
        testOneDump();
        testMultipleDumps();
        testEventAfterDump();
    }

    private static void testUnstarted() throws Exception {
        Path path = Path.of("recording.jfr");
        var rs = new RecordingStream();
        rs.setMaxAge(Duration.ofHours(1));
        try {
            rs.dump(path);
            throw new Exception("Should not be able to dump unstarted recording");
        } catch (IOException ise) {
            // OK, expected
        }
    }

    private static void testStopped() throws Exception {
        Path path = Path.of("recording.jfr");
        try (var rs = new RecordingStream()) {
            rs.setMaxAge(Duration.ofHours(1));
            rs.startAsync();
            DumpEvent event = new DumpEvent();
            event.commit();
            rs.stop();
            rs.dump(path);
            var events = RecordingFile.readAllEvents(path);
            if (events.size() != 1) {
                throw new Exception("Expected one event");
            }
        }
    }

    private static void testClosed() throws Exception {
        Path path = Path.of("recording.jfr");
        var rs = new RecordingStream();
        rs.setMaxAge(Duration.ofHours(1));
        rs.startAsync();
        rs.close();
        try {
            rs.dump(path);
            throw new Exception("Should not be able to dump closed recording");
        } catch (IOException ise) {
            // OK, expected
        }
    }

    private static void testMultipleDumps() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try (var rs = new RecordingStream()) {
            rs.setMaxAge(Duration.ofHours(1));
            rs.onEvent(e -> {
                latch.countDown();
            });
            rs.startAsync();
            while (latch.getCount() > 0) {
                DumpEvent e = new DumpEvent();
                e.commit();
                latch.await(10, TimeUnit.MILLISECONDS);
            }
            latch.await(); // Await first event
            AtomicInteger counter = new AtomicInteger();
            Callable<Boolean> f = () -> {
                try {
                    int id = counter.incrementAndGet();
                    Path p = Path.of("multiple-" + id + ".jfr");
                    rs.dump(p);
                    return !RecordingFile.readAllEvents(p).isEmpty();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                   return false;
                }
            };
            var service = Executors.newFixedThreadPool(3);
            var f1 = service.submit(f);
            var f2 = service.submit(f);
            var f3 = service.submit(f);
            if (!f1.get() && !f1.get() && !f3.get()) {
                throw new Exception("Failed to dump multiple recordings simultanously");
            }
            service.shutdown();
        }
    }

    private static void testOneDump() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try (var rs = new RecordingStream()) {
            rs.setMaxSize(5_000_000);
            rs.onEvent(e -> {
                latch.countDown();
            });
            rs.startAsync();
            while (latch.getCount() > 0) {
                DumpEvent e = new DumpEvent();
                e.commit();
                latch.await(10, TimeUnit.MILLISECONDS);
            }
            Path p = Path.of("one-dump.jfr");
            rs.dump(p);
            if (RecordingFile.readAllEvents(p).isEmpty()) {
                throw new Exception("No events in dump");
            }
        }
    }

    private static void testEventAfterDump() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try (var rs = new RecordingStream()) {
            rs.setMaxAge(Duration.ofHours(1));
            rs.onEvent(e -> {
                latch.countDown();
            });
            rs.startAsync();
            Path p = Path.of("after-dump.jfr");
            rs.dump(p);
            DumpEvent e = new DumpEvent();
            e.commit();
            latch.await();
        }
    }
}
