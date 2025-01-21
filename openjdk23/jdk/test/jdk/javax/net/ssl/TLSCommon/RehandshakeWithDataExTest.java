/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;

/**
 * Testing SSLEngines re-handshaking using each of the supported cipher suites
 * with application data exchange before and after re-handshake and closing of
 * the engines.
 */
public class RehandshakeWithDataExTest extends SSLEngineTestCase {

    public static void main(String[] args) {
        RehandshakeWithDataExTest test = new RehandshakeWithDataExTest();
        setUpAndStartKDCIfNeeded();
        test.runTests();
    }

    @Override
    protected void testOneCipher(String cipher) throws SSLException {
        SSLContext context = getContext();
        int maxPacketSize = getMaxPacketSize();
        boolean useSNI = !TEST_MODE.equals("norm");
        SSLEngine clientEngine = getClientSSLEngine(context, useSNI);
        SSLEngine serverEngine = getServerSSLEngine(context, useSNI);
        clientEngine.setEnabledCipherSuites(new String[]{cipher});
        serverEngine.setEnabledCipherSuites(new String[]{cipher});
        serverEngine.setNeedClientAuth(!cipher.contains("anon"));
        long initialEpoch = 0;
        long secondEpoch = 0;
        long thirdEpoch = 0;
        SSLEngineResult r;
        doHandshake(clientEngine, serverEngine, maxPacketSize,
                HandshakeMode.INITIAL_HANDSHAKE);
        sendApplicationData(clientEngine, serverEngine);
        r = sendApplicationData(serverEngine, clientEngine);
        if (TESTED_SECURITY_PROTOCOL.contains("DTLS")) {
            initialEpoch = r.sequenceNumber() >> 48;
        }
        doHandshake(clientEngine, serverEngine, maxPacketSize,
                HandshakeMode.REHANDSHAKE_BEGIN_CLIENT);
        sendApplicationData(clientEngine, serverEngine);
        r = sendApplicationData(serverEngine, clientEngine);
        AssertionError epochError = new AssertionError("Epoch number"
                + " did not grow after re-handshake! "
                + " Was " + initialEpoch + ", now " + secondEpoch + ".");
        if (TESTED_SECURITY_PROTOCOL.contains("DTLS")) {
            secondEpoch = r.sequenceNumber() >> 48;
            if (Long.compareUnsigned(secondEpoch, initialEpoch) <= 0) {
                throw epochError;
            }
        }
        doHandshake(clientEngine, serverEngine, maxPacketSize,
                HandshakeMode.REHANDSHAKE_BEGIN_SERVER);
        sendApplicationData(clientEngine, serverEngine);
        r = sendApplicationData(serverEngine, clientEngine);
        if (TESTED_SECURITY_PROTOCOL.contains("DTLS")) {
        thirdEpoch = r.sequenceNumber() >> 48;
            if (Long.compareUnsigned(thirdEpoch, secondEpoch) <= 0) {
                throw epochError;
            }
        }
        closeEngines(clientEngine, serverEngine);
    }

}
