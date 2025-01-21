/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
/*
 * @test
 * @bug 7188755
 * @run main/othervm MultiThreadedSystemProxies
 * @summary Crash due to missing synchronization on gconf_client in
 *          DefaultProxySelector.c
 */
import java.net.ProxySelector;
import java.net.URI;

/* Racey test, not guaranteed to fail, but if it does we have a problem. */

public class MultiThreadedSystemProxies {
    static final int NUM_THREADS = 100;

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.useSystemProxies", "true");
        final ProxySelector ps = ProxySelector.getDefault();
        final URI uri = new URI("http://ubuntu.com");
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ps.select(uri);
                    } catch (Exception x) {
                        throw new RuntimeException(x);
                    }
                }
            });
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }
    }
}
