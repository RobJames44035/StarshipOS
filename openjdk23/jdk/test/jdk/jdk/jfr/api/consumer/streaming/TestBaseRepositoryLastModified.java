/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.api.consumer.streaming;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

import jdk.jfr.consumer.EventStream;
import jdk.test.lib.Asserts;

/**
 * @test
 * @summary Test that a stream starts against the latest created repository
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @build jdk.jfr.api.consumer.streaming.Application
 * @run main/othervm jdk.jfr.api.consumer.streaming.TestBaseRepositoryLastModified
 */
public class TestBaseRepositoryLastModified {
    public static void main(String... args) throws Exception {
        AtomicBoolean success = new AtomicBoolean();
        Path repository = Path.of("last-modified");

        Application app1 = new Application(repository, "One");
        app1.start();
        app1.awaitRecording();

        Application app2 = new Application(repository, "Two");
        app2.start();
        app2.awaitRecording();

        try (EventStream es = EventStream.openRepository(repository)) {
            es.onEvent("Message", e -> {
                String message = e.getString("content");
                success.set("Two".equals(message));
                es.close();
            });
            es.start();
        }
        app1.stop();
        app2.stop();
        Asserts.assertTrue(success.get(), "Stream not opened against most recent directory");

    }
}
