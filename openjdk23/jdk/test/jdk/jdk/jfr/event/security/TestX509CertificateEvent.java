/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.event.security;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;
import jdk.test.lib.jfr.VoidFunction;
import jdk.test.lib.security.TestCertificate;

/*
 * @test
 * @bug 8148188 8292033
 * @summary Enhance the security libraries to record events of interest
 * @key jfr
 * @requires vm.hasJFR
 * @modules java.base/sun.security.x509 java.base/sun.security.tools.keytool
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.security.TestX509CertificateEvent
 */
public class TestX509CertificateEvent {
    public static void main(String[] args) throws Throwable {
        testCall(() -> {
            // test regular cert construction
            TestCertificate.ONE.certificate();
            TestCertificate.TWO.certificate();
            // Generate twice to make sure we (now) capture all generate cert events
            TestCertificate.ONE.certificate();
            TestCertificate.TWO.certificate();
        }, 4, true);

        testCall(() -> {
            // test generateCertificates method
            TestCertificate.certificates();
        }, 2, true);

        testCall(() -> {
            // test generateCertPath method
            TestCertificate.certPath();
        }, 4, true);

        testCall(() -> {
            // test keytool cert generation with JFR enabled
            // The keytool test will load the dedicated keystore
            // and call CertificateFactory.generateCertificate
            // cacerts
            TestCertificate.keyToolTest();
        }, -1, false);
    }

    private static void testCall(VoidFunction f, int expected, boolean runAsserts) throws Throwable {
        try (Recording recording = new Recording()) {
            recording.enable(EventNames.X509Certificate);
            recording.start();
            f.run();
            recording.stop();
            List<RecordedEvent> events = Events.fromRecording(recording);
            if (expected >= 0) {
                Asserts.assertEquals(events.size(), expected, "Incorrect number of events");
            }
            if (runAsserts) {
                assertEvent(events, TestCertificate.ONE);
                assertEvent(events, TestCertificate.TWO);
            }
        }
    }

    private static void assertEvent(List<RecordedEvent> events, TestCertificate cert) throws Exception {
        for (RecordedEvent e : events) {
            if (e.getLong("certificateId") == cert.certId) {
                Events.assertField(e, "algorithm").equal(cert.algorithm);
                Events.assertField(e, "subject").equal(cert.subject);
                Events.assertField(e, "issuer").equal(cert.issuer);
                Events.assertField(e, "keyType").equal(cert.keyType);
                Events.assertField(e, "keyLength").equal(cert.keyLength);
                Events.assertField(e, "serialNumber").equal(cert.serialNumber);
                return;
            }
        }
        System.out.println(events);
        throw new Exception("Could not find event with cert Id: " + cert.certId);
    }
}
