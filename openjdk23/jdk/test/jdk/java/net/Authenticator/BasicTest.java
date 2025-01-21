/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.io.*;
import java.net.*;
import java.util.*;
import jdk.test.lib.net.URIBuilder;

/**
 * @test
 * @bug 4474947
 * @summary  fix for bug #4244472 is incomplete - HTTP authorization still needs work
 * @library /test/lib
 * @run main/othervm BasicTest
 * @run main/othervm -Djava.net.preferIPv6Addresses=true BasicTest
 */

/*
 * Note, this is not a general purpose test for Basic Authentication because
 * it does not check the correctness of the data, only whether the user
 * authenticator gets called once as expected
 */

public class BasicTest {

    static class BasicServer extends Thread {

        ServerSocket server;

        Socket s;
        InputStream is;
        OutputStream os;

        static final String realm = "wallyworld";

        String reply1 = "HTTP/1.1 401 Unauthorized\r\n"+
            "WWW-Authenticate: Basic realm=\""+realm+"\"\r\n\r\n";

        String reply2 = "HTTP/1.1 200 OK\r\n"+
            "Date: Mon, 15 Jan 2001 12:18:21 GMT\r\n" +
            "Server: Apache/1.3.14 (Unix)\r\n" +
            "Connection: close\r\n" +
            "Content-Type: text/html; charset=iso-8859-1\r\n" +
            "Content-Length: 10\r\n\r\n";

        BasicServer (ServerSocket s) {
            server = s;
        }

        void readAll (Socket s) throws IOException {
            byte[] buf = new byte [128];
            InputStream is = s.getInputStream ();
            while (is.available() > 0) {
                is.read (buf);
            }
        }

        public void run () {
            try {
                System.out.println ("Server 1: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write (reply1.getBytes());
                Thread.sleep (500);

                System.out.println ("Server 2: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write ((reply2+"HelloWorld").getBytes());

                /* Second request now */

                System.out.println ("Server 3: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write (reply1.getBytes());
                Thread.sleep (500);
                s.close ();

                System.out.println ("Server 4: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write ((reply2+"HelloAgain").getBytes());
            }
            catch (Exception e) {
                System.out.println (e);
            }
            finished ();
        }

        public synchronized void finished () {
            notifyAll();
        }

    }

    static class MyAuthenticator extends Authenticator {
        MyAuthenticator () {
            super ();
        }

        int count = 0;

        public PasswordAuthentication getPasswordAuthentication ()
            {
            count ++;
            System.out.println ("Auth called");
            return (new PasswordAuthentication ("user", "passwordNotCheckedAnyway".toCharArray()));
        }

        public int getCount () {
            return (count);
        }
    }


    static void read (InputStream is) throws IOException {
        int c;
        System.out.println ("reading");
        while ((c=is.read()) != -1) {
            System.out.write (c);
        }
        System.out.println ("");
        System.out.println ("finished reading");
    }

    public static void main (String args[]) throws Exception {
        MyAuthenticator auth = new MyAuthenticator ();
        Authenticator.setDefault (auth);
        InetAddress loopback = InetAddress.getLoopbackAddress();
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(loopback, 0));
        int port = ss.getLocalPort ();
        BasicServer server = new BasicServer (ss);
        synchronized (server) {
            server.start();
            System.out.println ("client 1");
            String base = URIBuilder.newBuilder()
                .scheme("http")
                .loopback()
                .port(port)
                .path("/")
                .build()
                .toString();
            URL url = new URL(base + "d1/d2/d3/foo.html");
            URLConnection urlc = url.openConnection(Proxy.NO_PROXY);
            InputStream is = urlc.getInputStream ();
            read (is);
            System.out.println ("client 2");
            url = new URL(base + "d1/foo.html");
            urlc = url.openConnection(Proxy.NO_PROXY);
            is = urlc.getInputStream ();
            read (is);
            server.wait ();
            // check if authenticator was called once (ok) or twice (not)
                int f = auth.getCount();
            if (f != 1) {
                throw new RuntimeException ("Authenticator was called "+f+" times. Should be 1");
            }
        }
    }
}
