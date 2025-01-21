/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 *
 *
 * This unit test checks that closing the channel returned by
 * System.inheritedChannel closes the underlying network socket.
 *
 * The test launches the "echo service" with arguments to instruct the
 * service to close the channel after it receives a small message. The
 * service then delays/lingers for 15 seconds before shutting down. To
 * prove that the close works we check that we see EOF (meaning the
 * peer has closed the connection) in less than 15 seconds.
 */
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import jdk.test.lib.Utils;

public class CloseTest {

    public static void main(String args[]) throws Exception {
        String msg = "HELLO";

        // Launch the service with arguments to tell it to close
        // the connection after reading 5 bytes ("HELLO"). After
        // closing the connection the service should hang around
        // for 15 seconds.

        String service_args[] = new String[2];
        service_args[0] = String.valueOf(msg.length());
        service_args[1] = String.valueOf( Utils.adjustTimeout(15*1000) );

        SocketChannel sc = Launcher.launchWithInetSocketChannel("EchoService", null, service_args);

        // send message - service will echo the message and close the connection.

        sc.write(ByteBuffer.wrap(msg.getBytes("UTF-8")));

        // read the reply (with timeout)
        ByteBuffer bb = ByteBuffer.allocateDirect(50);
        sc.configureBlocking(false);
        Selector sel = sc.provider().openSelector();
        SelectionKey sk = sc.register(sel, SelectionKey.OP_READ);

        long to = Utils.adjustTimeout(12*1000);
        for (;;) {
            long st = System.currentTimeMillis();
            sel.select(to);
            if (sk.isReadable()) {
                int n = sc.read(bb);

                // EOF
                if (n < 0) {
                    break;
                }
            }
            sel.selectedKeys().remove(sk);
            to -= System.currentTimeMillis() - st;
            if (to <= 0) {
                throw new RuntimeException("Timed out waiting for connection to close");
            }
        }
        sel.close();
        sc.close();

        // finally check that the reply length is okay
        bb.flip();
        if (bb.remaining() < msg.length()) {
            throw new RuntimeException("Premature EOF from echo service");
        }

        System.out.println("Test passed - service closed connection.");
    }
}
