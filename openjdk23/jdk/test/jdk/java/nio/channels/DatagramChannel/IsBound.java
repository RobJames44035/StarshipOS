/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4468875
 * @summary Simple test of DatagramChannel isBound
 * @library .. /test/lib
 * @build jdk.test.lib.Utils TestServers
 * @run main IsBound
 */

import java.net.*;
import java.nio.*;
import java.nio.channels.*;


public class IsBound {
    public static void main(String argv[]) throws Exception {
        try (TestServers.UdpDayTimeServer daytimeServer
                = TestServers.UdpDayTimeServer.startNewServer(100)) {
            InetSocketAddress isa = new InetSocketAddress(
                daytimeServer.getAddress(),
                daytimeServer.getPort());
            ByteBuffer bb = ByteBuffer.allocateDirect(256);
            bb.put("hello".getBytes());
            bb.flip();

            DatagramChannel dc = DatagramChannel.open();
            dc.send(bb, isa);
            if(!dc.socket().isBound())
                throw new Exception("Test failed");
            dc.close();

            dc = DatagramChannel.open();
            if(dc.socket().isBound())
                throw new Exception("Test failed");
            dc.close();
        }
    }
}
