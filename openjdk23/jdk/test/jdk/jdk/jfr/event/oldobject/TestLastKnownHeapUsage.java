/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package jdk.jfr.event.oldobject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import jdk.jfr.internal.test.WhiteBox;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @modules jdk.jfr/jdk.jfr.internal.test
 * @run main/othervm -XX:TLABSize=2k jdk.jfr.event.oldobject.TestLastKnownHeapUsage
 */
public class TestLastKnownHeapUsage {

    public final static List<Object> heap = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        WhiteBox.setWriteAllObjectSamples(true);
        long last = 0;
        for (int i = 1; i <= 5; i++) {
            long heapUsage = recordOldObjects(i);
            System.out.println("Recording: " + i + " heapUsage=" + heapUsage);
            if (heapUsage < last) {
                throw new Exception("Unexpect decrease of heap usage");
            }
        }
    }

    private static long recordOldObjects(int index) throws IOException {
        try (Recording r = new Recording()) {
            r.enable(EventNames.OldObjectSample).withStackTrace().with("cutoff", "infinity");
            r.start();
            System.gc();
            for (int i = 0; i < 20; i++) {
                heap.add(new byte[1_000_000]);
            }
            System.gc();
            r.stop();
            Path p = Paths.get("recording-" + index + ".jfr");
            r.dump(p);
            List<RecordedEvent> events = RecordingFile.readAllEvents(p);
            Events.hasEvents(events);
            long max = 0;
            for (RecordedEvent e : RecordingFile.readAllEvents(p)) {
                long heapUsage = Events.assertField(e, "lastKnownHeapUsage").atLeast(0L).below(1_000_000_000L).getValue();
                max = Math.max(max, heapUsage);
            }
            return max;
        }
    }
}
