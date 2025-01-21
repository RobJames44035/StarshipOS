/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8219083
 * @summary Exceptions thrown from HttpHandler.handle should not close connection
 *          if response is completed
 * @library /test/lib
 * @run junit ExceptionKeepAlive
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jdk.test.lib.net.URIBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionKeepAlive
{

    public static final Logger LOGGER = Logger.getLogger("com.sun.net.httpserver");

    @Test
    void test() throws IOException, InterruptedException {
        HttpServer httpServer = startHttpServer();
        int port = httpServer.getAddress().getPort();
        try {
            URL url = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(port)
                .path("/firstCall")
                .toURLUnchecked();
            HttpURLConnection uc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
            int responseCode = uc.getResponseCode();
            assertEquals(200, responseCode, "First request should succeed");

            URL url2 = URIBuilder.newBuilder()
                    .scheme("http")
                    .loopback()
                    .port(port)
                    .path("/secondCall")
                    .toURLUnchecked();
            HttpURLConnection uc2 = (HttpURLConnection)url2.openConnection(Proxy.NO_PROXY);
            responseCode = uc2.getResponseCode();
            assertEquals(200, responseCode, "Second request should reuse connection");
        } finally {
            httpServer.stop(0);
        }
    }

    /**
     * Http Server
     */
    HttpServer startHttpServer() throws IOException {
        Handler outHandler = new StreamHandler(System.out,
                                 new SimpleFormatter());
        outHandler.setLevel(Level.FINEST);
        LOGGER.setLevel(Level.FINEST);
        LOGGER.addHandler(outHandler);
        InetAddress loopback = InetAddress.getLoopbackAddress();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(loopback, 0), 0);
        httpServer.createContext("/", new MyHandler());
        httpServer.start();
        return httpServer;
    }

    class MyHandler implements HttpHandler {

        volatile int port1;
        @Override
        public void handle(HttpExchange t) throws IOException {
            String path = t.getRequestURI().getPath();
            if (path.equals("/firstCall")) {
                port1 = t.getRemoteAddress().getPort();
                System.out.println("First connection on client port = " + port1);

                // send response
                t.sendResponseHeaders(200, -1);
                // response is completed now; throw exception
                throw new NumberFormatException();
                // the connection should still be reusable
            } else if (path.equals("/secondCall")) {
                int port2 = t.getRemoteAddress().getPort();
                System.out.println("Second connection on client port = " + port2);

                if (port1 == port2) {
                    t.sendResponseHeaders(200, -1);
                } else {
                    t.sendResponseHeaders(500, -1);
                }
            }
            t.close();
        }
    }
}
