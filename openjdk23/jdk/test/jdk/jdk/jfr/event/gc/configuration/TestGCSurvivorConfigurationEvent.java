/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.configuration;

import static jdk.test.lib.Asserts.assertGreaterThanOrEqual;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.EventVerifier;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @requires vm.gc == "Parallel" | vm.gc == null
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -XX:+UseParallelGC -XX:MaxTenuringThreshold=13 -XX:InitialTenuringThreshold=9 jdk.jfr.event.gc.configuration.TestGCSurvivorConfigurationEvent
 */
public class TestGCSurvivorConfigurationEvent {
    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.enable(EventNames.GCSurvivorConfiguration);
        recording.start();
        recording.stop();
        List<RecordedEvent> events = Events.fromRecording(recording);
        assertGreaterThanOrEqual(events.size(), 1, "Expected at least 1 event");
        GCSurvivorConfigurationEventVerifier verifier = new GCSurvivorConfigurationEventVerifier(events.getFirst());
        verifier.verify();
    }
}

class GCSurvivorConfigurationEventVerifier extends EventVerifier {
    public GCSurvivorConfigurationEventVerifier(RecordedEvent event) {
        super(event);
    }

    public void verify() throws Exception {
        verifyMaxTenuringThresholdIs(13);
        verifyInitialTenuringThresholdIs(9);
    }

    private void verifyMaxTenuringThresholdIs(int expected) throws Exception {
        verifyEquals("maxTenuringThreshold", (byte)expected);
    }

    private void verifyInitialTenuringThresholdIs(int expected) throws Exception {
        verifyEquals("initialTenuringThreshold", (byte)expected);
    }
}
