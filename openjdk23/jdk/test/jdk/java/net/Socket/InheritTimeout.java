/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4508149
 * @library /test/lib
 * @summary Setting ServerSocket.setSoTimeout shouldn't cause
 *          the timeout to be inherited by accepted connections
 * @run main InheritTimeout
 * @run main/othervm -Djava.net.preferIPv4Stack=true InheritTimeout
 */

import java.net.*;
import java.io.InputStream;
import jdk.test.lib.net.IPSupport;

public class InheritTimeout {

    class Reaper extends Thread {
        Socket s;
        int timeout;

        Reaper(Socket s, int timeout) {
            this.s = s;
            this.timeout = timeout;
        }

        public void run() {
            try {
                Thread.currentThread().sleep(timeout);
                s.close();
            } catch (Exception e) {
            }
        }
    }

   InheritTimeout() throws Exception {
        InetAddress ia = InetAddress.getLocalHost();
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ia, 0));
        ss.setSoTimeout(1000);

        InetSocketAddress isa =
            new InetSocketAddress(ia, ss.getLocalPort());

        // client establishes the connection
        Socket s1 = new Socket();
        s1.connect(isa);

        // receive the connection
        Socket s2 = ss.accept();

        // schedule reaper to close the socket in 5 seconds
        Reaper r = new Reaper(s2, 5000);
        r.start();

        boolean readTimedOut = false;
        try {
            s2.getInputStream().read();
        } catch (SocketTimeoutException te) {
            readTimedOut = true;
        } catch (SocketException e) {
            if (!s2.isClosed()) {
                throw e;
            }
        }

        s1.close();
        ss.close();

        if (readTimedOut) {
            throw new Exception("Unexpected SocketTimeoutException throw!");
        }
   }

   public static void main(String args[]) throws Exception {
        IPSupport.throwSkippedExceptionIfNonOperational();
        new InheritTimeout();
   }
}
