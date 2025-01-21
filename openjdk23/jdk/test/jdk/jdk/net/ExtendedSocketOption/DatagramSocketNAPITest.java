/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8243099
 * @library /test/lib
 * @modules jdk.net
 * @summary Check ExtendedSocketOption NAPI_ID support for DatagramSocket
 * @run testng DatagramSocketNAPITest
 * @run testng/othervm -Djava.net.preferIPv4Stack=true DatagramSocketNAPITest
 */

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import jdk.test.lib.net.IPSupport;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertThrows;
import static jdk.net.ExtendedSocketOptions.SO_INCOMING_NAPI_ID;

public class DatagramSocketNAPITest {
    private InetAddress hostAddr;
    private static final Class<SocketException> SE = SocketException.class;
    private static final Class<IllegalArgumentException> IAE = IllegalArgumentException.class;
    private static final Class<UnsupportedOperationException> UOE = UnsupportedOperationException.class;

    @BeforeTest
    public void setup() throws IOException {
        IPSupport.throwSkippedExceptionIfNonOperational();
        try (var ds = new DatagramSocket()) {
            if (!ds.supportedOptions().contains(SO_INCOMING_NAPI_ID)) {
                assertThrows(UOE, () -> ds.getOption(SO_INCOMING_NAPI_ID));
                assertThrows(UOE, () -> ds.setOption(SO_INCOMING_NAPI_ID, 42));
                assertThrows(UOE, () -> ds.setOption(SO_INCOMING_NAPI_ID, null));
                throw new SkipException("NAPI ID not supported on this system");
            }
        }
        hostAddr = InetAddress.getLocalHost();
    }

    @Test
    public void testSetGetOptionDatagramSocket() throws IOException {
        try (var ds = new DatagramSocket()) {
            assertEquals((int) ds.getOption(SO_INCOMING_NAPI_ID), 0);
            assertThrows(SE, () -> ds.setOption(SO_INCOMING_NAPI_ID, 42));
            assertThrows(IAE, () -> ds.setOption(SO_INCOMING_NAPI_ID, null));
        }
    }

    @Test
    public void testDatagramSocket() throws Exception {
        int senderID, receiverID, tempID = 0;
        boolean initialRun = true;
        try (var r = new DatagramSocket(new InetSocketAddress(hostAddr, 0))) {
            var port = r.getLocalPort();
            var addr = new InetSocketAddress(hostAddr, port);

            try (var s = new DatagramSocket()) {
                var sendPkt = new DatagramPacket(new byte[128], 128, addr);
                for (int i = 0; i < 10; i++) {
                    s.send(sendPkt);
                    senderID = s.getOption(SO_INCOMING_NAPI_ID);
                    assertEquals(senderID, 0, "DatagramSocket: Sender");

                    var receivePkt = new DatagramPacket(new byte[128], 128);
                    r.receive(receivePkt);
                    receiverID = r.getOption(SO_INCOMING_NAPI_ID);

                    // check ID remains consistent
                    if (initialRun) {
                        assertTrue(receiverID >= 0, "DatagramSocket: Receiver");
                    } else {
                        assertEquals(receiverID, tempID);
                        initialRun = false;
                    }
                    tempID = receiverID;
                }
            }
        }
    }
}
