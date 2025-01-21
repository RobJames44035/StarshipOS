/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 6964547 5001942 8129444
 * @library /test/lib
 * @run main/othervm SocksProxyVersion
 * @summary test socksProxyVersion system property
 */

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Proxy;
import jdk.test.lib.net.IPSupport;

public class SocksProxyVersion implements Runnable {
    final ServerSocket ss;
    volatile boolean failed;
    volatile boolean stopped = false;
    volatile int expected;

    public static void main(String[] args) throws Exception {
        if (InetAddress.getLocalHost().isLoopbackAddress()) {
            System.out.println("Test cannot run. getLocalHost returns a loopback address");
            return;
        }
        new SocksProxyVersion();
    }

    public SocksProxyVersion() throws Exception {
        ss = new ServerSocket(0, 0, InetAddress.getLocalHost());
        int port = ss.getLocalPort();
        Thread serverThread = new Thread(this);
        serverThread.start();
        try (ServerSocket socket = ss) {
            runTest(port);
        } finally {
            stopped = true;
        }

        serverThread.join();
        if (failed) {
            throw new RuntimeException("socksProxyVersion not being set correctly");
        }
    }

    final void runTest(int port) throws Exception {
        /*
         * Retrieving the IP Address of the machine
         * since "localhost" is bypassed as a non-proxy host
         */
        String addr = InetAddress.getLocalHost().getHostAddress();

        System.setProperty("socksProxyHost", addr);
        System.setProperty("socksProxyPort", Integer.toString(port));

        Proxy proxy = new Proxy(Proxy.Type.SOCKS,
                                new InetSocketAddress(addr, port));

        if (IPSupport.hasIPv4()) {
            // SOCKS V4 (requires IPv4)
            System.setProperty("socksProxyVersion", "4");
            this.expected = 4;
            check(new Socket(), addr, port);
            check(new Socket(proxy), addr, port);
        }

        // SOCKS V5
        System.setProperty("socksProxyVersion", "5");
        this.expected = 5;
        check(new Socket(), addr, port);
        check(new Socket(proxy), addr, port);
    }

    private void check(Socket socket, String addr, int port)
        throws IOException
    {
        try (Socket s = socket) {
            socket.connect(new InetSocketAddress(addr, port));
        } catch (SocketException e) {
            // java.net.SocketException: Malformed reply from SOCKS server
            // This exception is OK, since the "server" does not implement
            // the socks protocol. It just verifies the version and closes.
        }
    }

    @Override
    public void run() {
        int count = 0;
        try {
            while (!stopped) {
                try (Socket s = ss.accept()) {
                    int version = (s.getInputStream()).read();
                    if (version != expected) {
                        System.out.printf("Iteration: %d, Got: %d, expected: %d%n",
                                          count, version, expected);
                        failed = true;
                    }
                }
                count++;
            }
        } catch (IOException e) {
            if (!ss.isClosed()) {
                e.printStackTrace();
            }
            // ignore, server socket was closed
        }
    }
}
