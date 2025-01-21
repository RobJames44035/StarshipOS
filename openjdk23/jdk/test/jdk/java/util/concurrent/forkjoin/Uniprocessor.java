/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8274349
 * @run main/othervm -XX:ActiveProcessorCount=1 Uniprocessor
 * @summary Check the default FJ pool has a reasonable default parallelism
 *          level in a uniprocessor environment.
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;

public class Uniprocessor {

    static volatile boolean done = false;

    public static void main(String[] args) throws InterruptedException {
        // If the default parallelism were zero then this task would not
        // complete and the test will timeout.
        CountDownLatch ran = new CountDownLatch(1);
        ForkJoinPool.commonPool().submit(() -> ran.countDown());
        ran.await();
    }
}
