/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.compiler;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.compiler.TestCodeCacheStats
 */

public class TestCodeCacheStats {
    private final static String EVENT_NAME = EventNames.CodeCacheStatistics;

    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();
        recording.stop();

        List<RecordedEvent> events = Events.fromRecording(recording);
        Events.hasEvents(events);
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            Events.assertField(event, "startAddress");
            Events.assertField(event, "reservedTopAddress");
            Events.assertField(event, "entryCount").atLeast(0);
            Events.assertField(event, "methodCount").atLeast(0);
            Events.assertField(event, "adaptorCount").atLeast(0);
            Events.assertField(event, "unallocatedCapacity").atLeast(1024L);
            Events.assertField(event, "fullCount").equal(0);
        }
    }
}
