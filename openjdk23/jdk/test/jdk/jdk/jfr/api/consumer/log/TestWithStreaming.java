/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.api.consumer.log;

import java.io.Closeable;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import jdk.jfr.Event;
import jdk.jfr.Name;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Checks that it is possible to stream together with log stream
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @build jdk.jfr.api.consumer.log.LogAnalyzer
 * @run main/othervm -Xlog:jfr+event*=debug:file=with-streaming.log
 *      jdk.jfr.api.consumer.log.TestWithStreaming
 */
public class TestWithStreaming {

    @Name("TwoStreams")
    static class TwoStreams extends Event {
        String message;
    }

    public static void main(String... args) throws Exception {
        LogAnalyzer la = new LogAnalyzer("with-streaming.log");
        CountDownLatch latch = new CountDownLatch(2);
        try (RecordingStream rs = new RecordingStream()) {
            rs.enable(TwoStreams.class);
            rs.onEvent("TwoStreams", e -> {
                latch.countDown();
            });
            rs.setStartTime(Instant.MIN);
            rs.startAsync();
            TwoStreams e1 = new TwoStreams();
            e1.commit();
            TwoStreams e2 = new TwoStreams();
            e2.commit();
            latch.await();
            var scheduler = Executors.newScheduledThreadPool(1);
            try (Closeable close = scheduler::shutdown) {
                scheduler.scheduleAtFixedRate(() -> {
                    TwoStreams e = new TwoStreams();
                    e.message = "hello";
                    e.commit();
                }, 0, 10, TimeUnit.MILLISECONDS);
                la.await("hello");
            }
        }
    }
}
