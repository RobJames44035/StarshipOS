/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/* @test
 * @bug 4729342
 * @library /test/lib
 * @build jdk.test.lib.Utils
 * @run main SelectAndCancel
 * @summary Check for CancelledKeyException when key cancelled during select
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class SelectAndCancel {
    static volatile SelectionKey sk;
    static volatile Throwable ex;

    /*
     * CancelledKeyException is the failure symptom of 4729342
     * NOTE: The failure is timing dependent and is not always
     * seen immediately when the bug is present.
     */
    public static void main(String[] args) throws Throwable {
        final Selector selector = Selector.open();
        final ServerSocketChannel ssc = ServerSocketChannel.open().bind(
                new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
        final InetSocketAddress isa = (InetSocketAddress)ssc.getLocalAddress();
        final CountDownLatch signal = new CountDownLatch(1);

        // Create and start a selector in a separate thread.
        Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        ssc.configureBlocking(false);
                        sk = ssc.register(selector, SelectionKey.OP_ACCEPT);
                        signal.countDown();
                        selector.select();
                    } catch (Throwable e) {
                        ex = e;
                    }
                }
            });
        t.start();

        signal.await();
        // Wait for above thread to get to select() before we call cancel.
        Thread.sleep((long)(300 * jdk.test.lib.Utils.TIMEOUT_FACTOR));

        // CancelledKeyException should not be thrown.
        ssc.close();
        sk.cancel();
        selector.close();
        t.join();
        if (ex != null) {
            throw ex;
        }
    }
}
