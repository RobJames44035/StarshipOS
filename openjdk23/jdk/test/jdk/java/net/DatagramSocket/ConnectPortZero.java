/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.expectThrows;

/*
 * @test
 * @bug 8240533
 * @summary Check that DatagramSocket, MulticastSocket and DatagramSocketAdaptor
 *          throw expected Exception when connecting to port 0
 * @run testng/othervm ConnectPortZero
 */

public class ConnectPortZero {
    private InetAddress loopbackAddr, wildcardAddr;
    private DatagramSocket datagramSocket, datagramSocketAdaptor;
    private MulticastSocket multicastSocket;

    private static final Class<SocketException> SE = SocketException.class;
    private static final Class<UncheckedIOException> UCIOE = UncheckedIOException.class;

    @BeforeTest
    public void setUp() throws IOException {
        loopbackAddr = InetAddress.getLoopbackAddress();
        wildcardAddr = new InetSocketAddress(0).getAddress();

        datagramSocket = new DatagramSocket();
        multicastSocket = new MulticastSocket();
        datagramSocketAdaptor = DatagramChannel.open().socket();
    }

    @DataProvider(name = "data")
    public Object[][] variants() {
        return new Object[][]{
                { datagramSocket,        loopbackAddr },
                { datagramSocketAdaptor, loopbackAddr },
                { multicastSocket,       loopbackAddr },
                { datagramSocket,        wildcardAddr },
                { datagramSocketAdaptor, wildcardAddr },
                { multicastSocket,       wildcardAddr }
        };
    }

    @Test(dataProvider = "data")
    public void testConnect(DatagramSocket ds, InetAddress addr) {
        Throwable t = expectThrows(UCIOE, () -> ds.connect(addr, 0));
        assertEquals(t.getCause().getClass(), SE);

        assertThrows(SE, () -> ds
                .connect(new InetSocketAddress(addr, 0)));
    }

    @AfterTest
    public void tearDown() {
        datagramSocket.close();
        multicastSocket.close();
        datagramSocketAdaptor.close();
    }
}
