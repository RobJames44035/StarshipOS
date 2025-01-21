/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
 * @bug 5025260
 * @summary ClosedSelectorException is expected when register after close
 */

import java.net.*;
import java.nio.channels.*;

public class CloseThenRegister {

    public static void main (String [] args) throws Exception {
        Selector sel = Selector.open();
        sel.close();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        try {
            ssc.bind(new InetSocketAddress(0));
            ssc.configureBlocking(false);
            ssc.register(sel, SelectionKey.OP_ACCEPT);
            throw new RuntimeException("register after close does not cause CSE!");
        } catch (ClosedSelectorException cse) {
            // expected
        } finally {
            ssc.close();
        }
    }

}
