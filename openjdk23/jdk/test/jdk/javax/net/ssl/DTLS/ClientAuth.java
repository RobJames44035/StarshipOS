/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.

/*
 * @test
 * @bug 8043758
 * @summary Datagram Transport Layer Security (DTLS)
 * @modules java.base/sun.security.util
 * @library /test/lib
 * @build DTLSOverDatagram
 * @run main/othervm ClientAuth
 */

import javax.net.ssl.SSLEngine;

/**
 * Test DTLS client authentication.
 */
public class ClientAuth extends DTLSOverDatagram {

    public static void main(String[] args) throws Exception {
        ClientAuth testCase = new ClientAuth();
        testCase.runTest(testCase);
    }

    @Override
    SSLEngine createSSLEngine(boolean isClient) throws Exception {
        SSLEngine engine = super.createSSLEngine(isClient);

        if (!isClient) {
            engine.setNeedClientAuth(true);
        }

        return engine;
    }
}
