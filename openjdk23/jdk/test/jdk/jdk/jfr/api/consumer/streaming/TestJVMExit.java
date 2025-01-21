/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package jdk.jfr.api.consumer.streaming;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import jdk.jfr.consumer.EventStream;

/**
 * @test
 * @summary Test that a stream ends/closes when an application exists.
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr jdk.attach java.base/jdk.internal.misc
 * @build jdk.jfr.api.consumer.streaming.TestProcess
 *
 * @run main/othervm -Dsun.tools.attach.attachTimeout=100000 jdk.jfr.api.consumer.streaming.TestJVMExit
 */
public class TestJVMExit {

    public static void main(String... args) throws Exception {
        while (true) {
            try {
                testExit();
                return;
            } catch (RuntimeException e) {
                String message = String.valueOf(e.getMessage());
                // If the test application crashes during startup, retry.
                if (!message.contains("is no longer alive")) {
                    throw e;
                }
                System.out.println("Application not alive when trying to get repository. Retrying.");
            }
        }
    }

    private static void testExit() throws Exception {
        try (TestProcess process = new TestProcess("exit-application")) {
            AtomicInteger eventCounter = new AtomicInteger();
            try (EventStream es = EventStream.openRepository(process.getRepository())) {
                // Start from first event in repository
                es.setStartTime(Instant.EPOCH);
                es.onEvent(e -> {
                    if (eventCounter.incrementAndGet() == TestProcess.NUMBER_OF_EVENTS) {
                        process.exit();
                    }
                });
                es.start();
            }
        }
    }
}
