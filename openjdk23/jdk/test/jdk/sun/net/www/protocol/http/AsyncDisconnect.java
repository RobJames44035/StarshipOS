/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6358532
 * @library /test/lib
 * @modules jdk.httpserver
 * @run main/othervm AsyncDisconnect
 * @run main/othervm -Djava.net.preferIPv6Addresses=true AsyncDisconnect
 * @summary HttpURLConnection.disconnect doesn't really do the job
 */

import java.net.*;
import java.io.*;
import com.sun.net.httpserver.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import jdk.test.lib.net.URIBuilder;

public class AsyncDisconnect implements Runnable
{
    com.sun.net.httpserver.HttpServer httpServer;
    MyHandler httpHandler;
    ExecutorService executorService;
    HttpURLConnection uc;

    public static void main(String[] args) throws Exception {
        new AsyncDisconnect();
    }

    public AsyncDisconnect() throws Exception {
        startHttpServer();
        doClient();
    }

    void doClient() throws Exception {
        Thread t = new Thread(this);

        try {
            InetSocketAddress address = httpServer.getAddress();
            URL url = URIBuilder.newBuilder()
                    .scheme("http")
                    .host(address.getAddress())
                    .port(address.getPort())
                    .path("/test/")
                    .toURL();
            uc = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);

            // create a thread that will disconnect the connection
            t.start();

            uc.getInputStream();

            // if we reach here then we have failed
            throw new RuntimeException("Failed: We Expect a SocketException to be thrown");

        } catch (SocketException se) {
            // this is what we expect to happen and is OK.
            //System.out.println(se);
        } finally {
            httpServer.stop(1);
            t.join();
            executorService.shutdown();

        }
    }

    public void run() {
        // wait for the request to be sent to the server before calling disconnect
        try { Thread.sleep(2000); }
        catch (Exception e) {}

        uc.disconnect();
    }

    /**
     * Http Server
     */
    public void startHttpServer() throws IOException {
        InetAddress loopback = InetAddress.getLoopbackAddress();
        InetSocketAddress address = new InetSocketAddress(loopback, 0);
        httpServer = com.sun.net.httpserver.HttpServer.create(address, 0);
        httpHandler = new MyHandler();

        HttpContext ctx = httpServer.createContext("/test/", httpHandler);

        executorService = Executors.newCachedThreadPool();
        httpServer.setExecutor(executorService);
        httpServer.start();
    }

    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            // give the other thread a chance to close the connection
            try { Thread.sleep(4000); }
            catch (Exception e) {}

            t.sendResponseHeaders(400, -1);
            t.close();
        }
    }

}
