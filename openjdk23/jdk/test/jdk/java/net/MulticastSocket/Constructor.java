/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/* @test
 * @bug 8243999
 * @summary Checks to ensure that Multicast constructors behave as expected
 * @run testng Constructor
 */

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MulticastSocket;

import static org.testng.Assert.assertTrue;

public class Constructor {
    @Test
    public void testMSNullAddress() throws IOException {
        try (var ms = new MulticastSocket()) {
            assertTrue(ms.getLocalAddress().isAnyLocalAddress());
        }

        try (var ms1 = new MulticastSocket(null)) {
            assertTrue(ms1.getLocalAddress().isAnyLocalAddress());
        }
    }
}
