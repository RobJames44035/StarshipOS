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
 * @run main/othervm -Djdk.tls.client.enableStatusRequestExtension=false
 *      NoMacInitialClientHello
 */

import java.net.DatagramPacket;
import java.net.SocketAddress;

/**
 * Test that a server is able to discard invalid initial ClientHello silently.
 */
public class NoMacInitialClientHello extends DTLSOverDatagram {
    boolean needInvalidRecords = true;

    public static void main(String[] args) throws Exception {
        System.setProperty("jdk.tls.useExtendedMasterSecret", "false");
        NoMacInitialClientHello testCase = new NoMacInitialClientHello();
        testCase.runTest(testCase);
    }

    @Override
    DatagramPacket createHandshakePacket(byte[] ba, SocketAddress socketAddr) {
        if (needInvalidRecords && (ba.length >= 60) &&
            (ba[0] == (byte)0x16) && (ba[13] == (byte)0x01)) {  // ClientHello

            needInvalidRecords = false;
            System.out.println("invalidate ClientHello message");
            if (ba[ba.length - 1] == (byte)0xFF) {
                ba[ba.length - 1] = (byte)0xFE;
            } else {
                ba[ba.length - 1] = (byte)0xFF;
            }
        }

        return super.createHandshakePacket(ba, socketAddr);
    }
}
