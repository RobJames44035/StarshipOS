/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import static java.lang.System.out;

/**
 * @test
 * @bug 8245462
 * @summary Basic test for interrupted blocking send
 * @run main/othervm InterruptedBlockingSend
 */

public class InterruptedBlockingSend {

    static volatile Throwable throwable;

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        try (ServerSocket ss = new ServerSocket()) {
            ss.setReuseAddress(false);
            ss.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            int port = ss.getLocalPort();
            URI uri = new URI("http://localhost:" + port + "/");

            HttpRequest request = HttpRequest.newBuilder(uri).build();

            Thread t = new Thread(() -> {
                try {
                    client.send(request, BodyHandlers.discarding());
                } catch (InterruptedException e) {
                    throwable = e;
                } catch (Throwable th) {
                    throwable = th;
                }
            });
            t.start();
            Thread.sleep(5000);
            t.interrupt();
            t.join();

            if (!(throwable instanceof InterruptedException)) {
                throw new RuntimeException("Expected InterruptedException, got " + throwable);
            } else {
                out.println("Caught expected InterruptedException: " + throwable);
            }

            out.println("Interrupting before send");
            try {
                Thread.currentThread().interrupt();
                client.send(request, BodyHandlers.discarding());
                throw new AssertionError("Expected InterruptedException not thrown");
            } catch (InterruptedException x) {
                out.println("Got expected exception: " + x);
            }
        }
    }
}
