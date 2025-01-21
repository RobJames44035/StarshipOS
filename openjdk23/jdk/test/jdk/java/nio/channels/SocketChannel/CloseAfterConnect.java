/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/* @test
 * @bug 6380091
 */
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.IOException;

public class CloseAfterConnect {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(0));

        InetAddress lh = InetAddress.getLocalHost();
        final SocketChannel sc = SocketChannel.open();
        final InetSocketAddress isa =
            new InetSocketAddress(lh, ssc.socket().getLocalPort());

        // establish connection in another thread
        Runnable connector =
            new Runnable() {
                public void run() {
                    try {
                        sc.connect(isa);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            };
        Thread thr = new Thread(connector);
        thr.start();

        // wait for connect to be established and for thread to
        // terminate
        do {
            try {
                thr.join();
            } catch (InterruptedException x) { }
        } while (thr.isAlive());

        // check connection is established
        if (!sc.isConnected()) {
            throw new RuntimeException("SocketChannel not connected");
        }

        // close channel - this triggered the bug as it attempted to signal
        // a thread that no longer exists
        sc.close();

        // clean-up
        ssc.accept().close();
        ssc.close();
    }
}
