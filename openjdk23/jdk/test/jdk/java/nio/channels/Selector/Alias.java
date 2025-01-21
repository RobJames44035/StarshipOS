/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4513011
 * @summary Registering and cancelling same fd many times
 * @library .. /test/lib
 * @build jdk.test.lib.Utils TestServers
 * @run main Alias
 */

import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

public class Alias {

    static int success = 0;
    static int LIMIT = 20; // Hangs after just 1 if problem is present

    public static void main(String[] args) throws Exception {
        try (TestServers.DayTimeServer daytimeServer
                = TestServers.DayTimeServer.startNewServer(100)) {
            test1(daytimeServer);
        }
    }

    static void test1(TestServers.DayTimeServer daytimeServer) throws Exception {
        Selector selector = SelectorProvider.provider().openSelector();
        InetAddress myAddress = daytimeServer.getAddress();
        InetSocketAddress isa
            = new InetSocketAddress(myAddress,
                                    daytimeServer.getPort());

        for (int j=0; j<LIMIT; j++) {
            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            boolean result = sc.connect(isa);

            // On some platforms - given that we're using a local server,
            // we may not enter into the if () { } statement below...
            if (!result) {
                SelectionKey key = sc.register(selector,
                                               SelectionKey.OP_CONNECT);
                while (!result) {
                    int keysAdded = selector.select(100);
                    if (keysAdded > 0) {
                        Set readyKeys = selector.selectedKeys();
                        Iterator i = readyKeys.iterator();
                        while (i.hasNext()) {
                            SelectionKey sk = (SelectionKey)i.next();
                            SocketChannel nextReady =
                                (SocketChannel)sk.channel();
                            result = nextReady.finishConnect();
                        }
                    }
                }
                key.cancel();
            }
            read(sc);
        }
        selector.close();
    }

    static void read(SocketChannel sc) throws Exception {
        ByteBuffer bb = ByteBuffer.allocateDirect(100);
        int n = 0;
        while (n == 0) // Note this is not a rigorous check for done reading
            n = sc.read(bb);
        //bb.position(bb.position() - 2);
        //bb.flip();
        //CharBuffer cb = Charset.forName("US-ASCII").newDecoder().decode(bb);
        //System.out.println("Received: \"" + cb + "\"");
        sc.close();
        success++;
    }
}
