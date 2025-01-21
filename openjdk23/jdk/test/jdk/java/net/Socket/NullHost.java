/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4712609
 * @summary Socket(String host, int port) throws NullPointerException if host is null
 */

import java.net.*;
import java.io.IOException;

public class NullHost {
    class Server extends Thread {
        private ServerSocket svr;

        public Server() throws IOException {
            svr = new ServerSocket();
            // The client side calls Socket((String) null, ...) which
            // resolves to InetAddress.getByName((String)null) which in
            // turns will resolve to the loopback address
            svr.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
        }

        public int getPort() {
            return svr.getLocalPort();
        }

        volatile boolean done;
        public void shutdown() {
            try {
                done = true;
                svr.close();
            } catch (IOException e) {
            }
        }

        public void run() {
            Socket s;
            try {
                while (!done) {
                    s = svr.accept();
                    s.close();
                }
            } catch (IOException e) {
                if (!done) e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        NullHost n = new NullHost();
    }

    public NullHost () throws IOException {
        Server s = new Server();
        int port = s.getPort();
        s.start();
        try {
            try (var sock = new Socket((String)null, port)) {}
            try (var sock = new Socket((String)null, port, true)) {}
            try (var sock = new Socket((String)null, port, null, 0)) {}
        } catch (NullPointerException e) {
            throw new RuntimeException("Got a NPE");
        } finally {
            s.shutdown();
        }
    }
}
