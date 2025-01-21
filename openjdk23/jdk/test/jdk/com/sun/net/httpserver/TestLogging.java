/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug 6422914
 * @library /test/lib
 * @summary change httpserver exception printouts
 * @run main TestLogging
 * @run main/othervm -Djava.net.preferIPv6Addresses=true TestLogging
 */

import com.sun.net.httpserver.*;

import java.util.concurrent.*;
import java.util.logging.*;
import java.io.*;
import java.net.*;

import jdk.test.lib.net.URIBuilder;

public class TestLogging extends Test {

    public static void main (String[] args) throws Exception {
        HttpServer s1 = null;
        ExecutorService executor=null;

        try {
            System.out.print ("Test9: ");
            String root = System.getProperty ("test.src")+ "/docs";
            InetAddress loopback = InetAddress.getLoopbackAddress();
            InetSocketAddress addr = new InetSocketAddress(loopback, 0);
            Logger logger = Logger.getLogger ("com.sun.net.httpserver");
            logger.setLevel (Level.ALL);
            Handler h1 = new ConsoleHandler ();
            h1.setLevel (Level.ALL);
            logger.addHandler (h1);
            s1 = HttpServer.create (addr, 0);
            logger.info (root);
            HttpHandler h = new FileServerHandler (root);
            HttpContext c1 = s1.createContext ("/test1", h);
            executor = Executors.newCachedThreadPool();
            s1.setExecutor (executor);
            s1.start();

            int p1 = s1.getAddress().getPort();

            URL url = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(p1)
                .path("/test1/smallfile.txt")
                .toURL();
            System.out.println("URL: " + url);
            HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
            InputStream is = urlc.getInputStream();
            while (is.read() != -1) ;
            is.close();

            url = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(p1)
                .path("/test1/doesntexist.txt")
                .toURLUnchecked();
            System.out.println("URL: " + url);
            urlc = (HttpURLConnection)url.openConnection();
            try {
                is = urlc.getInputStream();
                while (is.read() != -1) ;
                is.close();
            } catch (IOException e) {
                System.out.println ("caught expected exception");
            }

            Socket s = new Socket (InetAddress.getLoopbackAddress(), p1);
            OutputStream os = s.getOutputStream();
            //os.write ("GET xxx HTTP/1.1\r\n".getBytes());
            os.write ("HELLO WORLD\r\n".getBytes());
            is = s.getInputStream();
            while (is.read() != -1) ;
            os.close(); is.close(); s.close();
            System.out.println ("OK");
        } finally {
            if (s1 != null)
                s1.stop(0);
            if (executor != null)
                executor.shutdown();
        }
    }
}
