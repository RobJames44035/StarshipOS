/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/* @test
 * @bug 8232673
 * @summary Test the DatagramChannel socket adaptor getter methods
 * @run testng AdaptorGetters
 */

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

@Test
public class AdaptorGetters {

    /**
     * Test getters on unbound socket, before and after it is closed.
     */
    public void testUnboundSocket() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        DatagramSocket s = dc.socket();
        try {

            // state
            assertFalse(s.isBound());
            assertFalse(s.isConnected());
            assertFalse(s.isClosed());

            // local address
            assertTrue(s.getLocalAddress().isAnyLocalAddress());
            assertTrue(s.getLocalPort() == 0);
            assertTrue(s.getLocalSocketAddress() == null);

            // remote address
            assertTrue(s.getInetAddress() == null);
            assertTrue(s.getPort() == -1);

        } finally {
            dc.close();
        }

        // state
        assertFalse(s.isBound());
        assertFalse(s.isConnected());
        assertTrue(s.isClosed());

        // local address
        assertTrue(s.getLocalAddress() == null);
        assertTrue(s.getLocalPort() == -1);
        assertTrue(s.getLocalSocketAddress() == null);

        // remote address
        assertTrue(s.getInetAddress() == null);
        assertTrue(s.getPort() == -1);
        assertTrue((s.getRemoteSocketAddress() == null));
    }

    /**
     * Test getters on bound socket, before and after it is closed.
     */
    public void testBoundSocket() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        DatagramSocket s = dc.socket();
        try {
            dc.bind(new InetSocketAddress(0));
            var localAddress = (InetSocketAddress) dc.getLocalAddress();

            // state
            assertTrue(s.isBound());
            assertFalse(s.isConnected());
            assertFalse(s.isClosed());

            // local address
            assertEquals(s.getLocalAddress(), localAddress.getAddress());
            assertTrue(s.getLocalPort() == localAddress.getPort());
            assertEquals(s.getLocalSocketAddress(), localAddress);

            // remote address
            assertTrue(s.getInetAddress() == null);
            assertTrue(s.getPort() == -1);
            assertTrue((s.getRemoteSocketAddress() == null));

        } finally {
            dc.close();
        }

        // state
        assertTrue(s.isBound());
        assertFalse(s.isConnected());
        assertTrue(s.isClosed());

        // local address
        assertTrue(s.getLocalAddress() == null);
        assertTrue(s.getLocalPort() == -1);
        assertTrue(s.getLocalSocketAddress() == null);

        // remote address
        assertTrue(s.getInetAddress() == null);
        assertTrue(s.getPort() == -1);
        assertTrue((s.getRemoteSocketAddress() == null));
    }

    /**
     * Test getters on connected socket, before and after it is closed.
     */
    public void testConnectedSocket() throws Exception {
        var loopback = InetAddress.getLoopbackAddress();
        var remoteAddress = new InetSocketAddress(loopback, 7777);
        DatagramChannel dc = DatagramChannel.open();
        DatagramSocket s = dc.socket();
        try {
            dc.connect(remoteAddress);
            var localAddress = (InetSocketAddress) dc.getLocalAddress();

            // state
            assertTrue(s.isBound());
            assertTrue(s.isConnected());
            assertFalse(s.isClosed());

            // local address
            assertEquals(s.getLocalAddress(), localAddress.getAddress());
            assertTrue(s.getLocalPort() == localAddress.getPort());
            assertEquals(s.getLocalSocketAddress(), localAddress);

            // remote address
            assertEquals(s.getInetAddress(), remoteAddress.getAddress());
            assertTrue(s.getPort() == remoteAddress.getPort());
            assertEquals(s.getRemoteSocketAddress(), remoteAddress);

        } finally {
            dc.close();
        }

        // state
        assertTrue(s.isBound());
        assertTrue(s.isConnected());
        assertTrue(s.isClosed());

        // local address
        assertTrue(s.getLocalAddress() == null);
        assertTrue(s.getLocalPort() == -1);
        assertTrue(s.getLocalSocketAddress() == null);

        // remote address
        assertEquals(s.getInetAddress(), remoteAddress.getAddress());
        assertTrue(s.getPort() == remoteAddress.getPort());
        assertEquals(s.getRemoteSocketAddress(), remoteAddress);
    }

    /**
     * Test getters on disconnected socket, before and after it is closed.
     */
    public void testDisconnectedSocket() throws Exception {
        DatagramChannel dc = DatagramChannel.open();
        DatagramSocket s = dc.socket();
        try {
            var loopback = InetAddress.getLoopbackAddress();
            dc.connect(new InetSocketAddress(loopback, 7777));
            dc.disconnect();

            var localAddress = (InetSocketAddress) dc.getLocalAddress();

            // state
            assertTrue(s.isBound());
            assertFalse(s.isConnected());
            assertFalse(s.isClosed());

            // local address
            assertEquals(s.getLocalAddress(), localAddress.getAddress());
            assertTrue(s.getLocalPort() == localAddress.getPort());
            assertEquals(s.getLocalSocketAddress(), localAddress);

            // remote address
            assertTrue(s.getInetAddress() == null);
            assertTrue(s.getPort() == -1);
            assertTrue((s.getRemoteSocketAddress() == null));


        } finally {
            dc.close();
        }

        // state
        assertTrue(s.isBound());
        assertFalse(s.isConnected());
        assertTrue(s.isClosed());

        // local address
        assertTrue(s.getLocalAddress() == null);
        assertTrue(s.getLocalPort() == -1);
        assertTrue(s.getLocalSocketAddress() == null);

        // remote address
        assertTrue(s.getInetAddress() == null);
        assertTrue(s.getPort() == -1);
        assertTrue((s.getRemoteSocketAddress() == null));
    }
}
