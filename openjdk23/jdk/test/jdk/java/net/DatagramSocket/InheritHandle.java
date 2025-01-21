/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4945514 8042581
 * @summary DatagramSocket should make handle not inherited
 */

import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class InheritHandle {
    private static final long SLEEPTIME_MS = 1000L;

    public static void main(String[] args) throws Exception {
        int port;
        try (DatagramSocket sock = new DatagramSocket(0);) {
            sock.setReuseAddress(true);
            port = sock.getLocalPort();

            /**
             * spawn a child to check whether handle passed to it or not; it
             * shouldn't
             */
            Runtime.getRuntime().exec("sleep 10");
        }

        try (DatagramSocket sock = new DatagramSocket(null);) {
            sock.setReuseAddress(true);
            int retries = 0;
            boolean isWindows = System.getProperty("os.name").startsWith("Windows");
            InetSocketAddress addr = new InetSocketAddress(port);
            while (true) {
                try {
                    sock.bind(addr);
                    break;
                } catch (BindException e) {
                    if (isWindows && retries++ < 5) {
                        Thread.sleep(SLEEPTIME_MS);
                        System.out.println("BindException \"" + e.getMessage() + "\", retrying...");
                        continue;
                    } else {
                        throw e;
                    }
                }
            }

        }
    }
}

