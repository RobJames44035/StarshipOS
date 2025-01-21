/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import static jdk.test.lib.Asserts.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.runtime.TestJavaThreadStatisticsEvent
 */
public class TestJavaThreadStatisticsEvent {
    private static final String EVENT_NAME = EventNames.JavaThreadStatistics;

    private static final int THREAD_COUNT = 10;
    private static final Random RAND = new Random(4711);

    // verify thread count: 0 <= daemon <= active <= peak <= accumulated.
    public static void main(String[] args) throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME).withThreshold(Duration.ofMillis(10));
        recording.start();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(TestJavaThreadStatisticsEvent::sleepRandom);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }

        recording.stop();
        List<RecordedEvent> events = Events.fromRecording(recording);
        boolean isAnyFound = false;
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            isAnyFound = true;
            // verify thread count: 0 <= daemon <= active <= peak <= accumulated.
            long daemonCount = Events.assertField(event, "daemonCount").atLeast(0L).getValue();
            long activeCount = Events.assertField(event, "activeCount").atLeast(daemonCount).getValue();
            long peakCount = Events.assertField(event, "peakCount").atLeast(activeCount).atLeast(1L).getValue();
            Events.assertField(event, "accumulatedCount").atLeast(peakCount).getValue();
        }
        assertTrue(isAnyFound, "Correct event not found");
    }

    static void sleepRandom() {
        try {
            Thread.sleep(RAND.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
