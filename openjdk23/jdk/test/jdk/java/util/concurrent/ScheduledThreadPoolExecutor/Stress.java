/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * This is not a regression test, but a stress benchmark test for
 * 6602600: Fast removal of cancelled scheduled thread pool tasks
 *
 * This runs in the same wall clock time, but much reduced cpu time,
 * with the changes for 6602600.
 */
public class Stress {

    public static void main(String[] args) throws Throwable {

        final CountDownLatch count = new CountDownLatch(1000);

        final ScheduledThreadPoolExecutor pool =
            new ScheduledThreadPoolExecutor(100);
        pool.prestartAllCoreThreads();

        final Runnable incTask = new Runnable() { public void run() {
            count.countDown();
        }};

        pool.scheduleAtFixedRate(incTask, 0, 10, TimeUnit.MILLISECONDS);

        count.await();

        pool.shutdown();
        pool.awaitTermination(1L, TimeUnit.DAYS);
    }
}
