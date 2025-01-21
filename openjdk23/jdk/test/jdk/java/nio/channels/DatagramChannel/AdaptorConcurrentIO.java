/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/* @test
 * @bug 8232673
 * @summary Test DatagramChannel socket adaptor with concurrent send/receive
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AdaptorConcurrentIO {

    public static void main(String[] args) throws Exception {
        testConcurrentSendReceive(0);
        testConcurrentSendReceive(60_000);
    }

    /**
     * Starts a task that blocks in the adaptor's receive method, then invokes
     * the adaptor's send method to send a datagram. If the adaptor were using
     * the channel's blockingLock then send without be blocked waiting for
     * the receive to complete.
     */
    static void testConcurrentSendReceive(int timeout) throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            InetAddress lb = InetAddress.getLoopbackAddress();
            dc.bind(new InetSocketAddress(lb, 0));
            DatagramSocket s = dc.socket();
            s.setSoTimeout(timeout);

            ExecutorService pool = Executors.newSingleThreadExecutor();
            try {
                Future<String> result = pool.submit(() -> {
                    byte[] data = new byte[100];
                    DatagramPacket p = new DatagramPacket(data, 0, data.length);
                    s.receive(p);
                    return new String(p.getData(), p.getOffset(), p.getLength(), "UTF-8");
                });

                Thread.sleep(200); // give chance for thread to block

                byte[] data = "hello".getBytes("UTF-8");
                DatagramPacket p = new DatagramPacket(data, 0, data.length);
                p.setSocketAddress(s.getLocalSocketAddress());
                s.send(p);

                String msg = result.get();
                if (!msg.equals("hello"))
                    throw new RuntimeException("Unexpected message: " + msg);
            } finally {
                pool.shutdown();
            }
        }
    }
}

