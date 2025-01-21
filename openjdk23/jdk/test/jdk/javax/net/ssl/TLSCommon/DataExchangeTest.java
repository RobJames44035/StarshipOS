/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;

/**
 * Testing SSLEngine application data exchange using each of the supported cipher
 * suites.
 */
public class DataExchangeTest extends SSLEngineTestCase {

    public static void main(String[] args) {
        DataExchangeTest test = new DataExchangeTest();
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
        doHandshake(clientEngine, serverEngine, maxPacketSize,
                HandshakeMode.INITIAL_HANDSHAKE);
        sendApplicationData(clientEngine, serverEngine);
        sendApplicationData(serverEngine, clientEngine);
    }
}
