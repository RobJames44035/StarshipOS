/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * Tests that a thread blocked in Socket.getOutputStream().write()
 * throws a SocketException if the socket is asynchronously closed.
 */
import java.net.*;
import java.io.*;
import java.util.concurrent.CountDownLatch;

public class Socket_getOutputStream_write extends AsyncCloseTest implements Runnable {
    private final Socket s;
    private final CountDownLatch latch;

    public Socket_getOutputStream_write() {
        latch = new CountDownLatch(1);
        s = new Socket();
    }

    public String description() {
        return "Socket.getOutputStream().write()";
    }

    public void run() {
        try {
            OutputStream out = s.getOutputStream();
            byte b[] = new byte[8192];
            latch.countDown();
            for (;;) {
                out.write(b);
            }
        } catch (SocketException se) {
            if (latch.getCount() != 1) {
                closed();
            }
        } catch (Exception e) {
            failed(e.getMessage());
        } finally {
            if (latch.getCount() == 1) {
                latch.countDown();
            }
        }
    }

    public AsyncCloseTest go() {
        try {
            InetAddress lh = InetAddress.getLocalHost();
            try (ServerSocket ss = new ServerSocket(0, 0, lh)) {
                s.connect(new InetSocketAddress(lh, ss.getLocalPort()));
                try (Socket s2 = ss.accept()) {
                    Thread thr = new Thread(this);
                    thr.start();
                    latch.await();
                    Thread.sleep(1000);
                    s.close();
                    thr.join();
                }

                if (isClosed()) {
                    return passed();
                } else {
                    return failed("Socket.getOutputStream().write() wasn't preempted");
                }
            }
        } catch (Exception x) {
            failed(x.getMessage());
            throw new RuntimeException(x);
        }
    }
}
