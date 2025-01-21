/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4503641 8130394 8249773
 * @summary Check that DatagramChannel.receive returns a new SocketAddress
 *          when it receives a packet from the same source address but
 *          different endpoint.
 * @library /test/lib
 * @build jdk.test.lib.NetworkConfiguration
 *        jdk.test.lib.Platform
 *        ReceiveISA
 * @run main/othervm ReceiveISA
 *
 */
import java.nio.*;
import java.nio.channels.*;
import java.net.*;

import jdk.test.lib.Platform;

import static java.lang.System.out;

public class ReceiveISA {

    public static void main(String args[]) throws Exception {

        String regex = "Dia duit![0-2]";

        try (DatagramChannel dc1 = DatagramChannel.open();        // client
             DatagramChannel dc2 = DatagramChannel.open();        // client
             DatagramChannel dc3 = DatagramChannel.open();
             DatagramChannel dc4 = DatagramChannel.open()) {      // client

            InetAddress lh = InetAddress.getLocalHost();
            InetSocketAddress dest = Platform.isOSX()
                    ? new InetSocketAddress(lh, 0)
                    : null;
            dc3.socket().bind(dest); // bind server to any port

            // get server address
            InetSocketAddress isa = new InetSocketAddress(lh, dc3.socket().getLocalPort());

            ByteBuffer bb = ByteBuffer.allocateDirect(100);
            bb.put("Dia duit!0".getBytes());
            bb.flip();

            ByteBuffer bb1 = ByteBuffer.allocateDirect(100);
            bb1.put("Dia duit!1".getBytes());
            bb1.flip();

            ByteBuffer bb2 = ByteBuffer.allocateDirect(100);
            bb2.put("Dia duit!2".getBytes());
            bb2.flip();

            ByteBuffer bb3 = ByteBuffer.allocateDirect(100);
            bb3.put("garbage".getBytes());
            bb3.flip();

            dc1.send(bb, isa);      // packet 1 from dc1
            dc4.send(bb3, isa);     // interference, packet 4 from dc4
            dc1.send(bb1, isa);     // packet 2 from dc1
            dc2.send(bb2, isa);     // packet 3 from dc2


            // receive 4 packets
            dc3.socket().setSoTimeout(1000);
            ByteBuffer rb = ByteBuffer.allocateDirect(100);
            SocketAddress sa[] = new SocketAddress[3];

            for (int i = 0; i < 3;) {
                SocketAddress receiver = dc3.receive(rb);
                rb.flip();
                byte[] bytes = new byte[rb.limit()];
                rb.get(bytes, 0, rb.limit());
                String msg = new String(bytes);

                if (msg.matches("Dia duit![0-2]")) {
                    if (msg.equals("Dia duit!0")) {
                        sa[0] = receiver;
                        i++;
                    }
                    if (msg.equals("Dia duit!1")) {
                        sa[1] = receiver;
                        i++;
                    }
                    if (msg.equals("Dia duit!2")) {
                        sa[2] = receiver;
                        i++;
                    }
                } else {
                    out.println("Interfered packet sender address is : " + receiver);
                    out.println("random interfered packet is : " + msg);
                }
                rb.clear();
            }

            /*
             * Check that sa[0] equals sa[1] (both from dc1)
             * Check that sa[1] not equal to sa[2] (one from dc1, one from dc2)
             */

            if (!sa[0].equals(sa[1])) {
                throw new Exception("Source address for packets 1 & 2 should be equal");
            }

            if (sa[1].equals(sa[2])) {
                throw new Exception("Source address for packets 2 & 3 should be different");
            }
        }
    }
}
