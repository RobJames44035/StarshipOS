/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import jdk.test.lib.net.URIBuilder;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/*
 * @test
 * @bug 8339538
 * @summary Tests that DnsClient correctly calculates left timeout in
 *          presence of empty datagram packets.
 * @library ../lib /test/lib
 * @modules java.base/sun.security.util
 * @run main/othervm TimeoutWithEmptyDatagrams
 */

public class TimeoutWithEmptyDatagrams extends DNSTestBase {
    // initial timeout = 1/4 sec
    private static final int TIMEOUT = 250;
    // try 5 times per server
    private static final int RETRIES = 5;
    // DnsClient retries again with increased timeout if left
    // timeout is less than this value, and max retry attempts
    // is not reached
    private static final int DNS_CLIENT_MIN_TIMEOUT = 0;

    public TimeoutWithEmptyDatagrams() {
        setLocalServer(false);
    }

    public static void main(String[] args) throws Exception {
        new TimeoutWithEmptyDatagrams().run(args);
    }

    /*
     * Tests that we can set the initial UDP timeout interval and the
     * number of retries.
     */
    @Override
    public void runTest() throws Exception {
        // Create a DatagramSocket and bind it to the loopback address to simulate
        // UDP DNS server that doesn't respond
        try (DatagramSocket ds = new DatagramSocket(new InetSocketAddress(
                InetAddress.getLoopbackAddress(), 0))) {
            CountDownLatch gotClientAddress = new CountDownLatch(1);
            AtomicReference<SocketAddress> clientAddress = new AtomicReference<>();
            AtomicBoolean stopTestThreads = new AtomicBoolean();

            String allQuietUrl = URIBuilder.newBuilder()
                    .scheme("dns")
                    .loopback()
                    .port(ds.getLocalPort())
                    .build()
                    .toString();

            // Run a virtual thread that receives client request packets and extracts
            // sender address from them.
            Thread receiverThread = Thread.ofVirtual().start(() -> {
                while (!stopTestThreads.get()) {
                    try {
                        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                        ds.receive(packet);
                        System.err.println("Got packet from " + packet.getSocketAddress());
                        boolean hasClientAddress = clientAddress.get() != null;
                        clientAddress.set(packet.getSocketAddress());
                        if (!hasClientAddress) {
                            gotClientAddress.countDown();
                        }
                    } catch (IOException e) {
                        if (!stopTestThreads.get()) {
                            throw new RuntimeException(e);
                        } else {
                            return;
                        }
                    }
                }
            });

            // Run a virtual thread that will send an empty packets via server socket
            // that should wake up the selector on a client side.
            Thread wakeupThread = Thread.ofVirtual().start(() -> {
                try {
                    long timeout = Math.max(1, TIMEOUT / 4);
                    // wait for a first packet on a server socket
                    gotClientAddress.await();

                    // Now start sending empty packets until we get a notification
                    // from client part to stop sending
                    while (!stopTestThreads.get()) {
                        System.err.println("Server timeout = " + timeout);
                        TimeUnit.MILLISECONDS.sleep(timeout);
                        System.err.println("Sending wakeup packet to " + clientAddress.get());
                        var wakeupPacket = new DatagramPacket(new byte[0], 0);
                        wakeupPacket.setSocketAddress(clientAddress.get());
                        ds.send(wakeupPacket);
                        timeout += Math.max(1, timeout / 2);
                    }
                } catch (IOException ioe) {
                    throw new RuntimeException("Test machinery failure", ioe);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Interrupted during wakeup packets sending");
                } finally {
                    System.err.println("Server thread exiting");
                }
            });

            long startTime = 0;
            try {
                env().put(Context.PROVIDER_URL, allQuietUrl);
                env().put("com.sun.jndi.dns.timeout.initial", String.valueOf(TIMEOUT));
                env().put("com.sun.jndi.dns.timeout.retries", String.valueOf(RETRIES));
                setContext(new InitialDirContext(env()));

                startTime = System.nanoTime();
                context().getAttributes("");

                // Any request should fail after timeouts have expired.
                throw new RuntimeException("Failed: getAttributes succeeded unexpectedly");
            } catch (CommunicationException ce) {
                // We need to catch CommunicationException outside the test framework
                // flow because wakeupThread.join() can take some time that could
                // increase measured timeout
                long endTime = System.nanoTime();
                Duration elapsedTime = Duration.ofNanos(endTime - startTime);
                if (ce.getRootCause() instanceof SocketTimeoutException) {

                    Duration minAllowedTime = Duration.ofMillis(TIMEOUT)
                            .multipliedBy((1 << RETRIES) - 1)
                            .minus(Duration.ofMillis(DNS_CLIENT_MIN_TIMEOUT * RETRIES));
                    Duration maxAllowedTime = Duration.ofMillis(TIMEOUT)
                            .multipliedBy((1 << RETRIES) - 1)
                            // max allowed timeout value is set to 2 * expected timeout
                            .multipliedBy(2);

                    DNSTestUtils.debug("Elapsed (ms):  " + elapsedTime.toMillis());
                    String expectedRangeMsg = "%s - %s"
                            .formatted(minAllowedTime.toMillis(), maxAllowedTime.toMillis());
                    DNSTestUtils.debug("Expected range (ms): " + expectedRangeMsg);

                    // Check that elapsed time is as long as expected, and
                    // not more than 2 times greater.
                    if (elapsedTime.compareTo(minAllowedTime) >= 0 &&
                        elapsedTime.compareTo(maxAllowedTime) <= 0) {
                        System.out.println("elapsed time is as long as expected.");
                    } else {
                        throw new RuntimeException(
                                "Failed: timeout in " + elapsedTime.toMillis() +
                                " ms, expected to be in a range (ms): " + expectedRangeMsg);
                    }
                } else {
                    throw ce;
                }
            } finally {
                stopTestThreads.set(true);
                wakeupThread.join();
                ds.close();
                receiverThread.join();
            }
        }
    }
}
