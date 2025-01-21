/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8291637
 * @library /test/lib
 * @run main/othervm -Dhttp.keepAlive.time.server=20 -esa -ea B8291637
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import jdk.test.lib.net.URIBuilder;

public class B8291637 {
    static CompletableFuture<Boolean> passed = new CompletableFuture<>();

    static class Server extends Thread implements AutoCloseable {
        final String param; // the parameter to test "max" or "timeout"
        final ServerSocket serverSocket = new ServerSocket(0);
        final int port;
        volatile Socket s;

        public Server(String param) throws IOException {
            this.param = param;
            port = serverSocket.getLocalPort();
            setDaemon(true);
        }

        public int getPort() {
            return port;
        }

        public void close() throws IOException {
            serverSocket.close();
            if (s != null)
                s.close();
        }

        static final byte[] requestEnd = new byte[] {'\r', '\n', '\r', '\n' };

        // Read until the end of a HTTP request
        static void readOneRequest(InputStream is) throws IOException {
            int requestEndCount = 0, r;
            while ((r = is.read()) != -1) {
                if (r == requestEnd[requestEndCount]) {
                    requestEndCount++;
                    if (requestEndCount == 4) {
                        break;
                    }
                } else {
                    requestEndCount = 0;
                }
            }
        }

        public void run() {
            try {
                while (true) {
                    s = serverSocket.accept();
                    readOneRequest(s.getInputStream());
                    OutputStream os = s.getOutputStream();
                    String resp = "" +
                            "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n" +
                            "Connection: Keep-Alive\r\n" +
                            "Keep-Alive: " + param + "=-10\r\n" + // invalid negative value
                            "\r\n" +
                            "Hello World";
                    os.write(resp.getBytes(StandardCharsets.ISO_8859_1));
                    os.flush();
                    InputStream is = s.getInputStream();
                    long l1 = System.currentTimeMillis();
                    int readResult = is.read();
                    if (readResult != -1) {
                        System.out.println("Unexpected byte received: " + readResult);
                    }
                    long l2 = System.currentTimeMillis();
                    long diff = (l2 - l1) / 1000;
                    /*
                     * timeout is set to 20 seconds. If bug is still present
                     * then the timeout will occur the first time the keep alive
                     * thread wakes up which is after 5 seconds. This allows
                     * very large leeway with slow running hardware.
                     *
                     * Same behavior should occur in case of max=-1 with the bug
                     */
                    if (diff < 19) {
                        passed.complete(false);
                    } else {
                        passed.complete(true);
                    }
                    System.out.println("Time diff = " + diff);
                }
            } catch (Throwable t) {
                System.err.println("Server exception terminating: " + t);
                passed.completeExceptionally(t);
            }
        }
    }

    public static void runTest(String param) throws Exception {
        try (Server server = new Server(param)) {
            server.start();
            URL url = URIBuilder.newBuilder()
                    .scheme("http")
                    .loopback()
                    .port(server.getPort())
                    .path("/")
                    .toURL();
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            try (InputStream i = urlc.getInputStream()) {
                System.out.println("Read " + i.readAllBytes().length);
            }
            if (!passed.get()) {
                throw new RuntimeException("Test failed");
            } else {
                System.out.println("Test passed");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        runTest("timeout");
        runTest("max");
    }
}
