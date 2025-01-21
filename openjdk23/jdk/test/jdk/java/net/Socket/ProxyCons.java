/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4097826
 * @library /test/lib
 * @summary SOCKS support inadequate
 * @run main/timeout=40/othervm -DsocksProxyHost=nonexistant ProxyCons
 * @run main/timeout=40/othervm -DsocksProxyHost=nonexistant -Djava.net.preferIPv4Stack=true ProxyCons
 */

import java.net.*;
import jdk.test.lib.net.IPSupport;

public class ProxyCons {
    class Server extends Thread {
        ServerSocket server;
        Server (ServerSocket server) {
            super ();
            this.server = server;
        }
        public void run () {
            try {
                Socket s = server.accept ();
                s.close();
                while (!finished ()) {
                    Thread.sleep (500);
                }
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

    public ProxyCons() {
    }

    void test() throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(localHost, 0));
        try {
            Server s = new Server(ss);
            s.start();
            Socket sock = new Socket(Proxy.NO_PROXY);
            sock.connect(new InetSocketAddress(localHost, ss.getLocalPort()));
            s.done();
            sock.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        } finally {
            ss.close();
        }
    }

    public static void main(String[] args) throws Exception {
        IPSupport.throwSkippedExceptionIfNonOperational();

        ProxyCons c = new ProxyCons();
        c.test();
    }
}
