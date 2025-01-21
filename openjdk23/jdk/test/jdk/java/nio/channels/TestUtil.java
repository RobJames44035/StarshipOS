/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* Test utilities
 *
 */

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Random;


public class TestUtil {

    // Test hosts used by the channels tests - change these when
    // executing in a different network.
    public static final String UNRESOLVABLE_HOST = "blah-blah.blah-blah.blah";

    private TestUtil() { }

    // Repeatedly try random ports until we bind to one.  You might be tempted
    // to do this:
    //
    //     ServerSocketChannel ssc = ServerSocketChannel.open();
    //     ssc.socket().bind(new InetSocketAddress(0));
    //     SocketAddress sa = ssc.socket().getLocalSocketAddress();
    //
    // but unfortunately it doesn't work on NT 4.0.
    //
    // Returns the bound port.
    //
    static int bind(ServerSocketChannel ssc) throws IOException {
        InetAddress lh = InetAddress.getLocalHost();
        Random r = new Random();
        for (;;) {
            int p = r.nextInt((1 << 16) - 1024) + 1024;
            InetSocketAddress isa = new InetSocketAddress(lh, p);
            try {
                ssc.socket().bind(isa);
            } catch (IOException x) {
                continue;
            }
            return p;
        }
    }

    // A more convenient form of bind(ServerSocketChannel) that returns a full
    // socket address.
    //
    static InetSocketAddress bindToRandomPort(ServerSocketChannel ssc)
        throws IOException
    {
        int p = bind(ssc);
        return new InetSocketAddress(InetAddress.getLocalHost(), p);
    }

    private static String osName = System.getProperty("os.name");

    static boolean onWindows() {
        return osName.startsWith("Windows");
    }
}
