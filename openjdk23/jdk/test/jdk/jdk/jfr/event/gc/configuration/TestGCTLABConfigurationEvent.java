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
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -XX:+UseParallelGC -XX:+UseTLAB -XX:MinTLABSize=3k -XX:TLABRefillWasteFraction=96 jdk.jfr.event.gc.configuration.TestGCTLABConfigurationEvent
 */
public class TestGCTLABConfigurationEvent {
    public static void main(String[] args) throws Exception {
        Recording recording = new Recording();
        recording.enable(EventNames.GCTLABConfiguration);
        recording.start();
        recording.stop();
        List<RecordedEvent> events = Events.fromRecording(recording);
        assertGreaterThanOrEqual(events.size(), 1, "Expected at least 1 event");
        GCTLABConfigurationEventVerifier verifier = new GCTLABConfigurationEventVerifier(events.getFirst());
        verifier.verify();
    }
}

class GCTLABConfigurationEventVerifier extends EventVerifier {
    public GCTLABConfigurationEventVerifier(RecordedEvent event) {
        super(event);
    }

    @Override
    public void verify() throws Exception {
        verifyUsesTLABsIs(true);
        verifyMinTLABSizeIs(kilobyte(3));
        verifyTLABRefillWasteLimitIs(96);
    }

    void verifyUsesTLABsIs(boolean expected) throws Exception {
        verifyEquals("usesTLABs", expected);
    }

    void verifyMinTLABSizeIs(long expected) throws Exception {
        verifyEquals("minTLABSize", expected);
    }

    void verifyTLABRefillWasteLimitIs(long expected) throws Exception {
        verifyEquals("tlabRefillWasteLimit", expected);
    }

    private int kilobyte(int num) {
        return 1024 * num;
    }
}
