/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import static jdk.test.lib.Asserts.assertTrue;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventField;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.runtime.TestThreadDumpEvent
 */
public class TestThreadDumpEvent {

    private final static String EVENT_NAME = EventNames.ThreadDump;
    private static final String RESULT_STRING_MATCH = "Full thread dump";

    public static void main(String[] args) throws Throwable {
        Recording recording = new Recording();
        recording.enable(EVENT_NAME);
        recording.start();
        recording.stop();

        List<RecordedEvent> events = Events.fromRecording(recording);
        boolean isAnyFound = false;
        for (RecordedEvent event : events) {
            System.out.println("Event:" + event);
            isAnyFound = true;
            EventField dumpField = Events.assertField(event, "result");
            dumpField.instring(RESULT_STRING_MATCH).instring("TestThreadDumpEvent.main");
        }
        assertTrue(isAnyFound, "Correct event not found");
    }
}
