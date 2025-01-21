/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @bug 6526913
 * @library /test/lib
 * @run main/othervm -Dhttp.keepAlive=false  B6526913
 * @run main/othervm -Djava.net.preferIPv6Addresses=true
 *                   -Dhttp.keepAlive=false B6526913
 * @summary  HttpExchange.getResponseBody().close() throws Exception
 */

import com.sun.net.httpserver.*;

import java.util.concurrent.*;
import java.io.*;
import java.net.*;

import jdk.test.lib.net.URIBuilder;

public class B6526913 {

    public static void main (String[] args) throws Exception {
        Handler handler = new Handler();
        InetAddress loopback = InetAddress.getLoopbackAddress();
        InetSocketAddress addr = new InetSocketAddress (loopback, 0);
        HttpServer server = HttpServer.create (addr, 0);
        HttpContext ctx = server.createContext ("/test", handler);

        ExecutorService executor = Executors.newCachedThreadPool();
        server.setExecutor (executor);
        server.start ();

        URL url = URIBuilder.newBuilder()
            .scheme("http")
            .loopback()
            .port(server.getAddress().getPort())
            .path("/test/foo.html")
            .toURL();
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection (Proxy.NO_PROXY);
        try {
            InputStream is = urlc.getInputStream();
            int c ,count = 0;
            byte [] buf = new byte [32 * 1024];
            while (count < 32 * 1024) {
                count += is.read (buf);
            }
            is.close();
        } finally {
            server.stop(0);
            executor.shutdown();
        }
        if (error) {
            throw new RuntimeException ("Test failed");
        }
    }

    public static boolean error = false;

    static class Handler implements HttpHandler {
        int invocation = 1;
        public void handle (HttpExchange t)
            throws IOException
        {
            InputStream is = t.getRequestBody();
            try {
                while (is.read() != -1) ;
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                error = true;
            }
            /* send a chunked response, but wait a while before
             * sending the final empty chunk
             */
            t.sendResponseHeaders (200, 0);
            OutputStream os = t.getResponseBody();
            byte[] bb = new byte [32 * 1024];
            os.write (bb);
            os.flush();
            try {Thread.sleep (5000); } catch (InterruptedException e){}
            try {
                /* empty chunk sent here */
                os.close();
            } catch (IOException e) {
                error = true;
                e.printStackTrace();
            }
            t.close();
        }
    }
}
