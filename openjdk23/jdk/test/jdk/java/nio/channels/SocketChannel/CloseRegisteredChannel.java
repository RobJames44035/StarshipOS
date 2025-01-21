/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/* @test
   @bug 4960962 6215050
   @summary Test if the registered SocketChannel can be closed immediately
   @run main/timeout=10 CloseRegisteredChannel
 */

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;

public class CloseRegisteredChannel {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel server = ServerSocketChannel.open();
        ServerSocket s = server.socket ();
        s.bind (new InetSocketAddress (0));
        int port = s.getLocalPort ();
        //System.out.println ("listening on port " + port);

        SocketChannel client = SocketChannel.open ();
        client.connect (new InetSocketAddress (InetAddress.getLoopbackAddress(), port));
        SocketChannel peer = server.accept();
        peer.configureBlocking(true);

        Selector selector = Selector.open ();
        client.configureBlocking (false);
        SelectionKey key = client.register (
            selector, SelectionKey.OP_READ, null
        );
        client.close();
        //System.out.println ("client.isOpen = " + client.isOpen());
        System.out.println ("Will hang here...");
        int nb = peer.read(ByteBuffer.allocate (1024));
        //System.out.println("read nb=" + nb);

        selector.close();
        server.close();
    }
}
