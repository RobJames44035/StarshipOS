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
 * @run main/othervm InvalidCookie
 */

import java.net.DatagramPacket;
import java.net.SocketAddress;

/**
 * Test that if the handshake cookie in client side is incorrect, the handshake
 * process can continue as if the client does not use cookie.
 */
public class InvalidCookie extends DTLSOverDatagram {
    boolean needInvalidCookie = true;

    public static void main(String[] args) throws Exception {
        InvalidCookie testCase = new InvalidCookie();
        testCase.runTest(testCase);
    }

    @Override
    DatagramPacket createHandshakePacket(byte[] ba, SocketAddress socketAddr) {
        if (needInvalidCookie && (ba.length >= 60) &&
                (ba[0] == (byte)0x16) && (ba[13] == (byte)0x03)) {
            // HelloVerifyRequest
            needInvalidCookie = false;
            System.out.println("invalidate handshake verify cookie");
            if (ba[ba.length - 1] == (byte)0xFF) {
                ba[ba.length - 1] = (byte)0xFE;
            } else {
                ba[ba.length - 1] = (byte)0xFF;
            }
        }

        return super.createHandshakePacket(ba, socketAddr);
    }
}
