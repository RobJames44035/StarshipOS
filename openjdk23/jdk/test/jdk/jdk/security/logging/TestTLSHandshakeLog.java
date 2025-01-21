/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.security.logging;

import jdk.test.lib.security.TestTLSHandshake;

/*
 * @test
 * @bug 8148188
 * @summary Enhance the security libraries to record events of interest
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.security.logging.TestTLSHandshakeLog LOGGING_ENABLED
 * @run main/othervm jdk.security.logging.TestTLSHandshakeLog LOGGING_DISABLED
 */
public class TestTLSHandshakeLog {
    public static void main(String[] args) throws Exception {
        LogJvm l = new LogJvm(TLSHandshake.class, args);
        l.addExpected("FINE: X509Certificate: Alg:SHA256withRSA, Serial:" + TestTLSHandshake.CERT_SERIAL);
        l.addExpected("Subject:CN=Regression Test");
        l.addExpected("Key type:EC, Length:256");
        l.addExpected("FINE: ValidationChain: " +
                TestTLSHandshake.ANCHOR_CERT_ID +
                ", " + TestTLSHandshake.CERT_ID);
        l.addExpected("SunJSSE Test Serivce");
        l.addExpected("TLSHandshake:");
        l.addExpected("TLSv1.2");
        l.addExpected(TestTLSHandshake.CIPHER_SUITE +", " + TestTLSHandshake.CERT_ID);
        l.testExpected();
    }

    public static class TLSHandshake {
        public static void main(String[] args) throws Exception {
            TestTLSHandshake handshake = new TestTLSHandshake();
            handshake.run();
        }
    }
}