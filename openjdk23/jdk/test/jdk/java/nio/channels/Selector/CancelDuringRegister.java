/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/* @test
 * @bug 8287580
 * @summary Check CancelledKeyException not thrown during channel registration
 * @run main CancelDuringRegister
 */

import java.nio.channels.*;

public class CancelDuringRegister {

    private static volatile boolean done;

    public static void main(String[] args) throws Exception {
        try (Selector sel = Selector.open()) {

            // create thread to cancel all keys in the selector's key set
            var thread = new Thread(() -> {
                while (!done) {
                    sel.keys().forEach(SelectionKey::cancel);
                }
            });
            thread.start();

            try (SocketChannel sc = SocketChannel.open()) {
                sc.configureBlocking(false);

                for (int i = 0; i <100_000; i++) {
                    // register
                    var key = sc.register(sel, SelectionKey.OP_READ);
                    sel.selectNow();

                    // cancel and flush
                    key.cancel();
                    do {
                        sel.selectNow();
                    } while (sel.keys().contains(key));
                }
            } finally {
                done = true;
            }

            thread.join();
        }
    }
}
