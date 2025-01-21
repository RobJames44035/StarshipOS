/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4467968
 * @summary Tests whether wakeup makes the next select() call return immediately
 */

import java.io.IOException;
import java.nio.channels.Selector;


public class WakeupSpeed {

    public static void main(String argv[]) throws Exception {
        int waitTime = 4000;
        Selector selector = Selector.open();
        try {
            selector.wakeup();

            long t1 = System.currentTimeMillis();
            selector.select(waitTime);
            long t2 = System.currentTimeMillis();
            long totalTime = t2 - t1;

            if (totalTime > waitTime)
                throw new RuntimeException("Test failed");
        } finally {
            selector.close();
        }
    }

}
