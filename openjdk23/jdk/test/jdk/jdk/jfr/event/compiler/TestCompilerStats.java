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
 * @run main/othervm jdk.jfr.event.compiler.TestCompilerStats
 */
public class TestCompilerStats {
    private final static String EVENT_NAME = EventNames.CompilerStatistics;

    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();
        recording.stop();

        List<RecordedEvent> events = Events.fromRecording(recording);
        Events.hasEvents(events);
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            Events.assertField(event, "compileCount").atLeast(0);
            Events.assertField(event, "bailoutCount").atLeast(0);
            Events.assertField(event, "invalidatedCount").atLeast(0);
            Events.assertField(event, "osrCompileCount").atLeast(0);
            Events.assertField(event, "standardCompileCount").atLeast(0);
            Events.assertField(event, "osrBytesCompiled").atLeast(0L);
            Events.assertField(event, "standardBytesCompiled").atLeast(0L);
            Events.assertField(event, "nmethodsSize").atLeast(0L);
            Events.assertField(event, "nmethodCodeSize").atLeast(0L);
            Events.assertField(event, "peakTimeSpent").atLeast(0L);
            Events.assertField(event, "totalTimeSpent").atLeast(0L);
        }
    }
}
