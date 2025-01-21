/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4446035
 * @summary Simple test of DatagramSocket connection consistency
 * @library .. /test/lib
 * @build jdk.test.lib.Utils TestServers
 * @run main IsConnected
 */

import java.net.*;
import java.nio.channels.*;


public class IsConnected {
    public static void main(String argv[]) throws Exception {
        try (TestServers.UdpDayTimeServer daytimeServer
                = TestServers.UdpDayTimeServer.startNewServer(100)) {
            InetSocketAddress isa = new InetSocketAddress(
                daytimeServer.getAddress(), daytimeServer.getPort());
            DatagramChannel dc = DatagramChannel.open();
            dc.configureBlocking(true);
            dc.connect(isa);
            if  (!dc.isConnected())
                throw new RuntimeException("channel.isConnected inconsistent");
            if (!dc.socket().isConnected())
                throw new RuntimeException("socket.isConnected inconsistent");
            dc.close();
        }
    }
}
