/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8243488
 * @library /test/lib
 * @build jdk.test.lib.Platform
 * @summary Check that setSendBufferSize and getSendBufferSize work as expected
 * @run testng SetGetSendBufferSize
 * @run testng/othervm -Djava.net.preferIPv4Stack=true SetGetSendBufferSize
 */

import jdk.test.lib.Platform;
import jdk.test.lib.net.IPSupport;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.expectThrows;

public class SetGetSendBufferSize {
    static final Class<SocketException> SE = SocketException.class;
    static final Class<IllegalArgumentException> IAE = IllegalArgumentException.class;

    public interface DatagramSocketSupplier {
        DatagramSocket open() throws IOException;
    }
    static DatagramSocketSupplier supplier(DatagramSocketSupplier supplier) { return supplier; }

    @DataProvider
    public Object[][] invariants() {
        return new Object[][]{
                {"DatagramSocket", supplier(() -> new DatagramSocket())},
                {"MulticastSocket", supplier(() -> new MulticastSocket())},
                {"DatagramSocketAdaptor", supplier(() -> DatagramChannel.open().socket())},
        };
    }

    @Test(dataProvider = "invariants")
    public void testSetInvalidBufferSize(String name, DatagramSocketSupplier supplier) throws IOException {
        var invalidArgs = new int[]{ -1, 0 };

        try (var socket = supplier.open()) {
            for (int i : invalidArgs) {
                Exception ex = expectThrows(IAE, () -> socket.setSendBufferSize(i));
                System.out.println(name + " got expected exception: " + ex);
            }
        }
    }

    @Test(dataProvider = "invariants")
    public void testSetAndGetBufferSize(String name, DatagramSocketSupplier supplier) throws IOException {
        var validArgs = new int[]{ 2345, 3456 };

        try (var socket = supplier.open()) {
            for (int i : validArgs) {
                socket.setSendBufferSize(i);
                assertEquals(socket.getSendBufferSize(), i, name);
            }
        }
    }

    @Test(dataProvider = "invariants")
    public void testInitialSendBufferSize(String name, DatagramSocketSupplier supplier) throws IOException {
        if (Platform.isOSX()) {
            try (var socket = supplier.open()){
                assertTrue(socket.getSendBufferSize() >= 65507, name);
                if (IPSupport.hasIPv6() && !IPSupport.preferIPv4Stack()) {
                    assertEquals(socket.getSendBufferSize(), 65527, name);
                }
            }
        }
    }

    @Test(dataProvider = "invariants")
    public void testSetGetAfterClose(String name, DatagramSocketSupplier supplier) throws IOException {
        var socket = supplier.open();
        socket.close();

        Exception setException = expectThrows(SE, () -> socket.setSendBufferSize(2345));
        System.out.println(name + " got expected exception: " + setException);

        Exception getException = expectThrows(SE, () -> socket.getSendBufferSize());
        System.out.println(name + " got expected exception: " + getException);
    }
}
