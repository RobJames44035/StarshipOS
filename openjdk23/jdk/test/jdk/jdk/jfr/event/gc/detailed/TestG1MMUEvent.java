/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.jfr.event.gc.detailed;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "G1" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:NewSize=2m -XX:MaxNewSize=2m -Xmx32m -XX:+UseG1GC -XX:MaxGCPauseMillis=75 -XX:GCPauseIntervalMillis=150 -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps jdk.jfr.event.gc.detailed.TestG1MMUEvent
 */
public class TestG1MMUEvent {

    // Corresponds to command-line setting -XX:GCPauseIntervalMillis=150
    private final static double TIME_SLICE = 0.15;

    // Corresponds to command-line setting -XX:MaxGCPauseMillis=75
    private final static double MAX_GC_TIME = 0.075;

    private final static String EVENT_NAME = EventNames.G1MMU;

    public static byte[] bytes;

    public static void main(String[] args) throws Exception {

        Recording recording = new Recording();

        // activate the event we are interested in and start recording
        recording.enable(EVENT_NAME);
        recording.start();

        // Setting NewSize and MaxNewSize will limit eden, so
        // allocating 1024 5k byte arrays should trigger at
        // least one Young GC.
        for (int i = 0; i < 1024; i++) {
            bytes  = new byte[5 * 1024];
        }
        recording.stop();

        // Verify recording
        List<RecordedEvent> all = Events.fromRecording(recording);
        Events.hasEvents(all);

        for (RecordedEvent e : all) {
            Events.assertField(e, "gcId").above(0);
            Events.assertField(e, "timeSlice").isEqual(TIME_SLICE);
            Events.assertField(e, "pauseTarget").isEqual(MAX_GC_TIME);
        }

        recording.close();
    }
}
