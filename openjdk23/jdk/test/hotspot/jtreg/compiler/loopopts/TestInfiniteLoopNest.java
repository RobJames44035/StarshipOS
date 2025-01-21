/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8294217
 * @summary Assertion failure: parsing found no loops but there are some
 * @library /test/lib
 *
 * @run main/othervm -Xmx1G -XX:-BackgroundCompilation TestInfiniteLoopNest
 *
 */

import jdk.test.lib.Utils;

public class TestInfiniteLoopNest {
    long l;

    void q() {
        if (b) {
            Object o = new Object();
            return;
        }

        do {
            l++;
            while (l != 1) --l;
            l = 9;
        } while (l != 5);

    }

    public static void main(String[] p) throws Exception {
        Thread thread = new Thread() {
            public void run() {
                TestInfiniteLoopNest t = new TestInfiniteLoopNest();
                for (int i = 524; i < 19710; i += 1) {
                    b = true;
                    t.q();
                    b = false;
                }
                t.q();
            }
        };
        // Give thread some time to trigger compilation
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(Utils.adjustTimeout(500));
    }

    static Boolean b;
}
