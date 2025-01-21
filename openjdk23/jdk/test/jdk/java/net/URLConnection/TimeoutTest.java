/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4389976
 * @library /test/lib
 * @summary    can't unblock read() of InputStream from URL connection
 * @run main/timeout=40/othervm -Dsun.net.client.defaultReadTimeout=2000 TimeoutTest
 */

import java.io.*;
import java.net.*;
import jdk.test.lib.net.URIBuilder;

public class TimeoutTest {

    class Server extends Thread {
        ServerSocket server;
        Server (ServerSocket server) {
            super ();
            this.server = server;
        }
        public void run () {
            try {
                Socket s = server.accept ();
                while (!finished ()) {
                    Thread.sleep (1000);
                }
                s.close();
            } catch (Exception e) {
            }
        }
        boolean isFinished = false;

        synchronized boolean finished () {
            return (isFinished);
        }
        synchronized void done () {
            isFinished = true;
        }
    }

    public static void main(String[] args) throws Exception {
        TimeoutTest t = new TimeoutTest ();
        t.test ();
    }

    public void test() throws Exception {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
        Server s = new Server (ss);
        try{
            URL url = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(ss.getLocalPort())
                .toURL();
            System.out.println("URL: " + url);
            URLConnection urlc = url.openConnection ();
            InputStream is = urlc.getInputStream ();
            throw new RuntimeException("Should have received timeout");
        } catch (SocketTimeoutException e) {
            return;
        } finally {
            s.done();
            ss.close();
        }
    }
}
