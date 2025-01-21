/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8280113
 * @summary Test async close of a DatagramSocket obtained from a DatagramChannel where
 *     the DatagramChannel's internal socket address caches are already populated
 * @library /test/lib
 * @run junit AdaptorAsyncCloseAfterReceive
 */

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jdk.test.lib.thread.VThreadRunner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.*;

class AdaptorAsyncCloseAfterReceive {

    // used for scheduling socket close
    private static ScheduledExecutorService scheduler;

    @BeforeAll
    static void setup() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @AfterAll
    static void finish() {
        scheduler.shutdown();
    }

    /**
     * Test closing a DatagramSocket, obtained from a DatagramChannel, while the main
     * thread is blocked in receive. The receive method should throw rather than
     * completing with the sender address of a previous datagram.
     */
    @ParameterizedTest
    @CsvSource({"0,0", "100,0", "0,60000", "100,60000"})
    void testReceive(int maxLength, int timeout) throws Exception {
        try (DatagramChannel dc = DatagramChannel.open()) {
            dc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            SocketAddress localAddress = dc.getLocalAddress();

            populateSocketAddressCaches(dc);

            DatagramSocket s = dc.socket();
            s.setSoTimeout(timeout);

            // schedule socket to be closed while main thread blocked in receive
            Future<?> future = scheduler.schedule(() -> s.close(), 1, TimeUnit.SECONDS);
            try {
                while (true) {
                    byte[] ba = new byte[maxLength];
                    DatagramPacket p = new DatagramPacket(ba, maxLength);
                    s.receive(p);
                    SocketAddress sender = p.getSocketAddress();
                    if (sender.equals(localAddress)) {
                        fail("Received datagram from " + sender);
                    } else {
                        System.err.format("Datagram from %s ignored%n", sender);
                    }
                }
            } catch (SocketException e) {
                // expected
            } finally {
                future.cancel(true);
            }
        }
    }

    /**
     * Send and receive a few messages to ensure that the DatagramChannel internal
     * socket address cache is populated. This setup is also done in a virtual
     * thread to ensure that the underlying socket is non-blocking.
     */
    private void populateSocketAddressCaches(DatagramChannel dc) throws Exception {
        VThreadRunner.run(() -> {
            InetSocketAddress remote = (InetSocketAddress) dc.getLocalAddress();
            if (remote.getAddress().isAnyLocalAddress()) {
                InetAddress lb = InetAddress.getLoopbackAddress();
                remote = new InetSocketAddress(lb, dc.socket().getLocalPort());
            }
            for (int i = 0; i < 2; i++) {
                ByteBuffer bb = ByteBuffer.allocate(32);
                dc.send(bb, remote);
                bb.rewind();
                dc.receive(bb);
            }
        });
    }
}
