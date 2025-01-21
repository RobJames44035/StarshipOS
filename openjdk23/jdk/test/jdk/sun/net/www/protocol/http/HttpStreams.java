/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 8011719
 * @library /test/lib
 * @modules jdk.httpserver
 * @summary Basic checks to verify behavior of returned input streams
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import jdk.test.lib.net.URIBuilder;

public class HttpStreams {

    void client(String u) throws Exception {
        byte[] ba = new byte[5];
        HttpURLConnection urlc = (HttpURLConnection)(new URL(u)).openConnection();
        int resp = urlc.getResponseCode();
        InputStream is;
        if (resp == 200)
            is = urlc.getInputStream();
        else
            is = urlc.getErrorStream();

        expectNoThrow(() -> { is.read(); }, "read on open stream should not throw :" + u);
        expectNoThrow(() -> { is.close(); }, "close should never throw: " + u);
        expectNoThrow(() -> { is.close(); }, "close should never throw: " + u);
        expectThrow(() -> { is.read(); }, "read on closed stream should throw: " + u);
        expectThrow(() -> { is.read(ba); }, "read on closed stream should throw: " + u);
        expectThrow(() -> { is.read(ba, 0, 2); }, "read on closed stream should throw: " + u);
    }

    String constructUrlString(int port, String path) throws Exception {
        return URIBuilder.newBuilder()
                .scheme("http")
                .port(port)
                .loopback()
                .path(path)
                .toURL().toString();
    }

    void test() throws Exception {
        HttpServer server = null;
        try {
            server = startHttpServer();
            int serverPort = server.getAddress().getPort();
            client(constructUrlString(serverPort, "/chunked/"));
            client(constructUrlString(serverPort, "/fixed/"));
            client(constructUrlString(serverPort, "/error/"));
            client(constructUrlString(serverPort, "/chunkedError/"));

            // Test with a response cache
            ResponseCache ch = ResponseCache.getDefault();
            ResponseCache.setDefault(new TrivialCacheHandler());
            try {
                client(constructUrlString(serverPort, "/chunked/"));
                client(constructUrlString(serverPort, "/fixed/"));
                client(constructUrlString(serverPort, "/error/"));
                client(constructUrlString(serverPort, "/chunkedError/"));
            } finally {
                ResponseCache.setDefault(ch);
            }
        } finally {
            if (server != null)
                server.stop(0);
        }

        System.out.println("passed: " + pass + ", failed: " + fail);
        if (fail > 0)
            throw new RuntimeException("some tests failed check output");
    }

    public static void main(String[] args) throws Exception {
        (new HttpStreams()).test();
    }

    // HTTP Server
    HttpServer startHttpServer() throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
        httpServer.createContext("/chunked/", new ChunkedHandler());
        httpServer.createContext("/fixed/", new FixedHandler());
        httpServer.createContext("/error/", new ErrorHandler());
        httpServer.createContext("/chunkedError/", new ChunkedErrorHandler());
        httpServer.start();
        return httpServer;
    }

    static abstract class AbstractHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try (InputStream is = t.getRequestBody()) {
                while (is.read() != -1);
            }
            t.sendResponseHeaders(respCode(), length());
            try (OutputStream os = t.getResponseBody()) {
                os.write(message());
            }
            t.close();
        }

        abstract int respCode();
        abstract int length();
        abstract byte[] message();
    }

    static class ChunkedHandler extends AbstractHandler {
        static final byte[] ba =
                "Hello there from chunked handler!".getBytes(StandardCharsets.US_ASCII);
        int respCode() { return 200; }
        int length() { return 0; }
        byte[] message() { return ba; }
    }

    static class FixedHandler extends AbstractHandler {
        static final byte[] ba =
                "Hello there from fixed handler!".getBytes(StandardCharsets.US_ASCII);
        int respCode() { return 200; }
        int length() { return ba.length; }
        byte[] message() { return ba; }
    }

    static class ErrorHandler extends AbstractHandler {
        static final byte[] ba =
                "This is an error mesg from the server!".getBytes(StandardCharsets.US_ASCII);
        int respCode() { return 400; }
        int length() { return ba.length; }
        byte[] message() { return ba; }
    }

    static class ChunkedErrorHandler extends ErrorHandler {
        int length() { return 0; }
    }

    static class TrivialCacheHandler extends ResponseCache
    {
       public CacheResponse get(URI uri, String rqstMethod, Map rqstHeaders) {
          return null;
       }

       public CacheRequest put(URI uri, URLConnection conn) {
          return new TrivialCacheRequest();
       }
    }

    static class TrivialCacheRequest extends CacheRequest
    {
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       public void abort() {}
       public OutputStream getBody() throws IOException { return baos; }
    }

    static interface ThrowableRunnable {
        void run() throws IOException;
    }

    void expectThrow(ThrowableRunnable r, String msg) {
        try { r.run(); fail(msg); } catch (IOException x) { pass(); }
    }

    void expectNoThrow(ThrowableRunnable r, String msg) {
        try { r.run(); pass(); } catch (IOException x) { fail(msg, x); }
    }

    private int pass;
    private int fail;
    void pass() { pass++; }
    void fail(String msg, Exception x) { System.out.println(msg); x.printStackTrace(); fail++; }
    void fail(String msg) { System.out.println(msg); Thread.dumpStack(); fail++; }
}
