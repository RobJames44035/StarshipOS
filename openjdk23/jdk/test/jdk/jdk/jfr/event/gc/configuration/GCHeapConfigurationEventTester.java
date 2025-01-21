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


public abstract class GCHeapConfigurationEventTester {
    public void run() throws Exception {
        Recording recording = new Recording();
        recording.enable(EventNames.GCHeapConfiguration);
        recording.start();
        recording.stop();
        List<RecordedEvent> events = Events.fromRecording(recording);
        assertGreaterThanOrEqual(events.size(), 1, "Expected at least 1 event");
        EventVerifier v = createVerifier(events.getFirst());
        v.verify();
    }

    protected abstract EventVerifier createVerifier(RecordedEvent e);
}
