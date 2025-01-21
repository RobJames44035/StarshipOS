/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/* @test
 * @bug 8243507 8243999
 * @summary Checks to ensure that DatagramSocket constructors behave as expected
 * @run testng Constructor
 */

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

import static org.testng.Assert.assertThrows;
import static org.testng.Assert.assertTrue;

public class Constructor {
    private static final InetAddress LOOPBACK = InetAddress.getLoopbackAddress();
    private static final Class<IllegalArgumentException> IAE = IllegalArgumentException.class;

    private class TestSocketAddress extends SocketAddress {
        TestSocketAddress() {
        }
    }

    @Test
    public void testBindAddress() {
        var addr = new TestSocketAddress();
        assertThrows(IllegalArgumentException.class,
                () -> new DatagramSocket(addr));
    }

    @Test
    public void testInvalidPortRange() {
        var invalidPortValues = new int[]{-1, 65536, Integer.MAX_VALUE};
        for (int i : invalidPortValues) {
            assertThrows(IAE, () -> new DatagramSocket(i));
            assertThrows(IAE, () -> new DatagramSocket(i, LOOPBACK));
        }
    }

    @Test
    public void testDSNullAddress() throws IOException {
        try (var ds = new DatagramSocket()) {
            assertTrue(ds.getLocalAddress().isAnyLocalAddress());
        }

        try (var ds1 = new DatagramSocket(null)) {
            assertTrue(ds1.getLocalAddress().isAnyLocalAddress());
        }

        try (var ds2 = new DatagramSocket(0, null)) {
            assertTrue(ds2.getLocalAddress().isAnyLocalAddress());
        }
    }
}
