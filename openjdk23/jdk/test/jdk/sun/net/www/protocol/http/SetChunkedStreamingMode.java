/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/**
 * @test
 * @bug 5049976
 * @library /test/lib
 * @run main/othervm SetChunkedStreamingMode
 * @summary Unspecified NPE is thrown when streaming output mode is enabled
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jdk.test.lib.net.URIBuilder;

public class SetChunkedStreamingMode implements HttpHandler {

    void okReply (HttpExchange req) throws IOException {
        req.sendResponseHeaders(200, 0);
        try(PrintWriter pw = new PrintWriter(req.getResponseBody())) {
            pw.print("Hello .");
        }
        System.out.println ("Server: sent response");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        okReply(exchange);
    }

    static HttpServer server;

    public static void main (String[] args) throws Exception {
        try {
            InetAddress loopback = InetAddress.getLoopbackAddress();
            server = HttpServer.create(new InetSocketAddress(loopback, 0), 10);
            server.createContext("/", new SetChunkedStreamingMode());
            server.setExecutor(Executors.newSingleThreadExecutor());
            server.start();
            System.out.println ("Server: listening on port: " + server.getAddress().getPort());
            URL url = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(server.getAddress().getPort())
                .path("/")
                .toURL();
            System.out.println ("Client: connecting to " + url);
            HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
            urlc.setChunkedStreamingMode (0);
            urlc.setRequestMethod("POST");
            urlc.setDoOutput(true);
            InputStream is = urlc.getInputStream();
        } catch (Exception e) {
            if (server != null) {
                server.stop(1);
            }
            throw e;
        }
        server.stop(1);
    }

    public static void except (String s) {
        server.stop(1);
        throw new RuntimeException (s);
    }
}
