/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.jfr.api.consumer.recordingstream;

import java.time.Duration;

import jdk.jfr.consumer.RecordingStream;
import jdk.test.lib.jfr.EventNames;

/**
 * @test
 * @summary Tests RecordingStream::setMaxAge(...)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestMaxAge
 */
public class TestMaxAge {

    public static void main(String... args) throws Exception {
        Duration testDuration = Duration.ofMillis(1234567);
        try (RecordingStream r = new RecordingStream()) {
            r.setMaxAge(testDuration);
            r.enable(EventNames.ActiveRecording);
            r.onEvent(e -> {
                System.out.println(e);
                Duration d = e.getDuration("maxAge");
                System.out.println(d.toMillis());
                if (testDuration.equals(d)) {
                    r.close();
                    return;
                }
                System.out.println("Max age not set, was " + d.toMillis() + " ms , but expected " + testDuration.toMillis() + " ms");
            });
            r.start();
        }
    }
}
