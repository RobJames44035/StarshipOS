/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.os;

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
 * @run main/othervm jdk.jfr.event.os.TestPhysicalMemoryEvent
 */
public class TestPhysicalMemoryEvent {
    private final static String EVENT_NAME = EventNames.PhysicalMemory;

    public static void main(String[] args) throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();
        recording.stop();
        List<RecordedEvent> events = Events.fromRecording(recording);
        Events.hasEvents(events);
        for (RecordedEvent event : events) {
            System.out.println("Event: " + event);
            long totalSize = Events.assertField(event, "totalSize").atLeast(0L).getValue();
            Events.assertField(event, "usedSize").atLeast(0L).atMost(totalSize);
        }
    }
}
