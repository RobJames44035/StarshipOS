/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6369510
 * @modules jdk.httpserver
 * @run main/othervm B6369510
 * @summary HttpURLConnection sets Content-Type to application/x-www-form-urlencoded
 */

import java.net.*;
import java.util.*;
import java.io.*;
import com.sun.net.httpserver.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class B6369510
{
    com.sun.net.httpserver.HttpServer httpServer;
    ExecutorService executorService;

    public static void main(String[] args) throws Exception
    {
        new B6369510();
    }

    public B6369510()
    {
        try {
            startHttpServer();
            doClient();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    void doClient() {
        try {
            InetSocketAddress address = httpServer.getAddress();
            String urlString = "http://" + InetAddress.getLocalHost().getHostName()
                               + ":" + address.getPort() + "/test/";
            System.out.println("URL == " + urlString);

            // GET Request
            URL url = new URL(urlString);
            HttpURLConnection uc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
            int resp = uc.getResponseCode();
            if (resp != 200)
                throw new RuntimeException("Failed: Response code from GET is not 200 RSP == " + resp);

            System.out.println("Response code from GET = 200 OK");

            //POST Request
            uc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
            uc.setDoOutput(true);
            uc.setRequestMethod("POST");
            OutputStream os = uc.getOutputStream();
            resp = uc.getResponseCode();
            if (resp != 200)
                throw new RuntimeException("Failed: Response code form POST is not 200 RSP == " + resp);

            System.out.println("Response code from POST = 200 OK");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed with IOException");
        } finally {
            httpServer.stop(1);
            executorService.shutdown();
        }
    }

    /**
     * Http Server
     */
    public void startHttpServer() throws IOException {
        InetAddress localhost = InetAddress.getLocalHost();
        httpServer = HttpServer.create(new InetSocketAddress(localhost, 0), 0);

        // create HttpServer context
        HttpContext ctx = httpServer.createContext("/test/", new MyHandler());

        executorService = Executors.newCachedThreadPool();
        httpServer.setExecutor(executorService);
        httpServer.start();
    }

    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            InputStream is = t.getRequestBody();
            Headers reqHeaders = t.getRequestHeaders();
            Headers resHeaders = t.getResponseHeaders();
            while (is.read () != -1) ;
            is.close();

            List<String> ct = reqHeaders.get("content-type");
            String requestMethod = t.getRequestMethod();

            if (requestMethod.equalsIgnoreCase("GET") && ct != null &&
                ct.get(0).equals("application/x-www-form-urlencoded"))
                t.sendResponseHeaders(400, -1);

            else if (requestMethod.equalsIgnoreCase("POST") && ct != null &&
                     !ct.get(0).equals("application/x-www-form-urlencoded"))
                t.sendResponseHeaders(400, -1);

            t.sendResponseHeaders(200, -1);
            t.close();
        }
    }
}
