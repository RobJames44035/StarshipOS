/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4106601 8026245 8071424
 * @library /test/lib
 * @run main/othervm GetLocalAddress
 * @run main/othervm -Djava.net.preferIPv4Stack=true GetLocalAddress
 * @run main/othervm -Djava.net.preferIPv6Addresses=true GetLocalAddress
 * @summary Test the java.net.socket.GetLocalAddress method
 *
 */

import java.net.*;
import jdk.test.lib.net.IPSupport;

public class GetLocalAddress implements Runnable {
    static ServerSocket ss;
    static InetAddress addr;
    static int port;

    public static void main(String args[]) throws Exception {
        IPSupport.throwSkippedExceptionIfNonOperational();

        testBindNull();

        boolean      error = true;
        int          linger = 65546;
        int          value = 0;
        addr = InetAddress.getLocalHost();
        ss = new ServerSocket();
        ss.bind(new InetSocketAddress(addr, 0));
        port = ss.getLocalPort();

        Thread t = new Thread(new GetLocalAddress());
        t.start();
        Socket soc = ss.accept();

        if(addr.equals(soc.getLocalAddress())) {
           error = false;
           }
        if (error)
            throw new RuntimeException("Socket.GetLocalAddress failed.");
        soc.close();
    }

    public void run() {
        try {
            Socket s = new Socket(addr, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void testBindNull() throws Exception {
        try (Socket soc = new Socket()) {
            soc.bind(null);
            if (!soc.isBound())
                throw new RuntimeException(
                    "should be bound after bind(null)");
            if (soc.getLocalPort() <= 0)
                throw new RuntimeException(
                   "bind(null) failed, local port: " + soc.getLocalPort());
            if (soc.getLocalAddress() == null)
                 throw new RuntimeException(
                   "bind(null) failed, local address is null");
        }
    }
}
