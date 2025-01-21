/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Test local address type
 * @library /test/lib
 * @build jdk.test.lib.NetworkConfiguration
 * @run testng/othervm LocalSocketAddressType
 * @run testng/othervm -Djava.net.preferIPv4Stack=true LocalSocketAddressType
 */

import jdk.test.lib.NetworkConfiguration;
import jdk.test.lib.net.IPSupport;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.parseBoolean;
import static java.lang.System.getProperty;
import static java.lang.System.out;
import static jdk.test.lib.Asserts.assertEquals;
import static jdk.test.lib.Asserts.assertTrue;
import static jdk.test.lib.net.IPSupport.*;

public class LocalSocketAddressType {

    @BeforeTest()
    public void setup() {
        IPSupport.printPlatformSupport(out);
        throwSkippedExceptionIfNonOperational();
    }

    @DataProvider(name = "addresses")
    public static Iterator<Object[]> addresses() throws Exception {
        NetworkConfiguration nc = NetworkConfiguration.probe();
        return Stream.concat(nc.ip4Addresses(), nc.ip6Addresses())
                .map(ia -> new Object[] { new InetSocketAddress(ia, 0) })
                .iterator();
    }

    @Test(dataProvider = "addresses")
    public static void testSocketChannel(InetSocketAddress addr) throws Exception {
        try (var c = SocketChannel.open()) {
            Class<? extends InetAddress> cls = addr.getAddress().getClass();
            InetAddress ia = ((InetSocketAddress)c.bind(addr).getLocalAddress()).getAddress();
            assertEquals(ia.getClass(), cls);
            ia = c.socket().getLocalAddress();
            assertEquals(ia.getClass(), cls);
        }
    }

    @Test(dataProvider = "addresses")
    public static void testServerSocketChannel(InetSocketAddress addr) throws Exception {
        try (var c = ServerSocketChannel.open()) {
            Class<? extends InetAddress> cls = addr.getAddress().getClass();
            InetAddress ia = ((InetSocketAddress)c.bind(addr).getLocalAddress()).getAddress();
            assertEquals(ia.getClass(), cls);
            ia = c.socket().getInetAddress();
            assertEquals(ia.getClass(), cls);
        }
    }

    @Test(dataProvider = "addresses")
    public static void testDatagramChannel(InetSocketAddress addr) throws Exception {
        try (var c = DatagramChannel.open()) {
            Class<? extends InetAddress> cls = addr.getAddress().getClass();
            InetAddress ia = ((InetSocketAddress)c.bind(addr).getLocalAddress()).getAddress();
            assertEquals(ia.getClass(), cls);
            ia = c.socket().getLocalAddress();
            assertEquals(ia.getClass(), cls);
        }
    }
}
