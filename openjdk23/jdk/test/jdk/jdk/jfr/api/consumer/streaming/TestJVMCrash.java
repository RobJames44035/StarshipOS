/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package jdk.jfr.api.consumer.streaming;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import jdk.jfr.consumer.EventStream;

/**
 * @test
 * @summary Test that a stream ends/closes when an application crashes.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr jdk.attach java.base/jdk.internal.misc
 *
 * @run main/othervm -Dsun.tools.attach.attachTimeout=100000 jdk.jfr.api.consumer.streaming.TestJVMCrash
 */
public class TestJVMCrash {

    public static void main(String... args) {
        int id = 1;
        while (true) {
            try (TestProcess process = new TestProcess("crash-application-" + id++, false /* createCore */))  {
                AtomicInteger eventCounter = new AtomicInteger();
                try (EventStream es = EventStream.openRepository(process.getRepository())) {
                    // Start from first event in repository
                    es.setStartTime(Instant.EPOCH);
                    es.onEvent(e -> {
                        if (eventCounter.incrementAndGet() == TestProcess.NUMBER_OF_EVENTS) {
                            process.crash();
                        }
                    });
                    es.startAsync();
                    // If crash corrupts chunk in repository, retry in 30 seconds
                    es.awaitTermination(Duration.ofSeconds(30));
                    if (eventCounter.get() == TestProcess.NUMBER_OF_EVENTS) {
                        return;
                    }
                    System.out.println("Incorrect event count. Retrying...");
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                System.out.println("Retrying...");
            }
        }
    }
}
