/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.awt.image.ColorModel;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @test
 * @bug 8299772
 * @summary "ColorModel.getRGBdefault()" should always return the same object
 */
public final class RGBdefaultSingleton {

    private static volatile boolean failed;
    private static final Map<ColorModel, ?> map =
            Collections.synchronizedMap(new IdentityHashMap<>(1));

    public static void main(String[] args) throws Exception {
        Thread[] ts = new Thread[10];
        CountDownLatch latch = new CountDownLatch(ts.length);
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                latch.countDown();
                try {
                    ColorModel cm;
                    latch.await();
                    cm = ColorModel.getRGBdefault();
                    map.put(cm, null);
                } catch (Throwable t) {
                    t.printStackTrace();
                    failed = true;
                }
            });
        }
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            t.join();
        }
        if (failed) {
            throw new RuntimeException("Unexpected exception");
        } else if (map.size() != 1) {
            throw new RuntimeException("The size of the map != 1");
        }
    }
}
