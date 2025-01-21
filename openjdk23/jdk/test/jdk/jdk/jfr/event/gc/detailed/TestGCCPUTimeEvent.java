/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jfr.event.gc.detailed;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.whitebox.WhiteBox;

/**
 * @test id=Serial
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc.Serial
 * @library /test/lib /test/jdk
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmx32m -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseSerialGC jdk.jfr.event.gc.detailed.TestGCCPUTimeEvent
 */

/**
 * @test id=Parallel
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc.Parallel
 * @library /test/lib /test/jdk
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmx32m -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseParallelGC jdk.jfr.event.gc.detailed.TestGCCPUTimeEvent
 */

/**
 * @test id=G1
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc.G1
 * @library /test/lib /test/jdk
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xmx32m -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+UseG1GC jdk.jfr.event.gc.detailed.TestGCCPUTimeEvent
 */

public class TestGCCPUTimeEvent {
    private static final String EVENT_NAME = EventNames.GCCPUTime;

    public static void main(String[] args) throws Exception {

        try (Recording recording = new Recording()) {

            // Activate the event we are interested in and start recording
            recording.enable(EVENT_NAME);
            recording.start();

            // Guarantee one young GC.
            WhiteBox.getWhiteBox().youngGC();
            recording.stop();

            // Verify recording
            List<RecordedEvent> events = Events.fromRecording(recording);
            Events.hasEvent(events, EVENT_NAME);

            recording.close();
        }
    }
}
