/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.event.security;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.security.TestTLSHandshake;

/*
 * @test
 * @bug 8148188
 * @summary Enhance the security libraries to record events of interest
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.security.TestTLSHandshakeEvent
 */
public class TestTLSHandshakeEvent {
    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            recording.enable(EventNames.TLSHandshake).withStackTrace();
            recording.start();
            TestTLSHandshake handshake = new TestTLSHandshake();
            handshake.run();
            recording.stop();

            List<RecordedEvent> events = Events.fromRecording(recording);
            Events.hasEvents(events);
            assertEvent(events, handshake);
        }
    }

    private static void assertEvent(List<RecordedEvent> events, TestTLSHandshake handshake) throws Exception {
        System.out.println(events);
        for (RecordedEvent e : events) {
            if (handshake.peerHost.equals(e.getString("peerHost"))) {
                Events.assertField(e, "peerPort").equal(handshake.peerPort);
                Events.assertField(e, "protocolVersion").equal(handshake.protocolVersion);
                Events.assertField(e, "certificateId").equal(TestTLSHandshake.CERT_ID);
                Events.assertField(e, "cipherSuite").equal(TestTLSHandshake.CIPHER_SUITE);
                var method = e.getStackTrace().getFrames().get(0).getMethod();
                if (method.getName().equals("recordEvent")) {
                    throw new Exception("Didn't expected recordEvent as top frame");
                }
                return;
            }
        }
        System.out.println(events);
        throw new Exception("Could not find event with hostname: " + handshake.peerHost);
    }
}
