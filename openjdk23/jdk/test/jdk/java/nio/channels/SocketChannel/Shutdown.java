/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4618960 4516760
 * @summary Test shutdownXXX and isInputShutdown
 */

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

public class Shutdown {

    /**
     * Accept a connection, and close it immediately causing a hard reset.
     */
    static void acceptAndReset(ServerSocketChannel ssc) throws IOException {
        SocketChannel peer = ssc.accept();
        try {
            peer.setOption(StandardSocketOptions.SO_LINGER, 0);
            peer.configureBlocking(false);
            peer.write(ByteBuffer.wrap(new byte[128*1024]));
        } finally {
            peer.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open()
            .bind(new InetSocketAddress(0));
        try {
            InetAddress lh = InetAddress.getLocalHost();
            int port = ((InetSocketAddress)(ssc.getLocalAddress())).getPort();
            SocketAddress remote = new InetSocketAddress(lh, port);

            // Test SocketChannel shutdownXXX
            SocketChannel sc;
            sc = SocketChannel.open(remote);
            try {
                acceptAndReset(ssc);
                sc.shutdownInput();
                sc.shutdownOutput();
            } finally {
                sc.close();
            }

            // Test Socket adapter shutdownXXX and isShutdownInput
            sc = SocketChannel.open(remote);
            try {
                acceptAndReset(ssc);
                boolean before = sc.socket().isInputShutdown();
                sc.socket().shutdownInput();
                boolean after = sc.socket().isInputShutdown();
                if (before || !after)
                    throw new RuntimeException("Before and after test failed");
                sc.socket().shutdownOutput();
            } finally {
                sc.close();
            }
        } finally {
            ssc.close();
        }
    }
}
