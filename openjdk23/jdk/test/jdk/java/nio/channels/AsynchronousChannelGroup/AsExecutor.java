/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 4607272
 * @summary tests tasks can be submitted to a channel group's thread pool.
 * @run main AsExecutor
 */

import java.nio.channels.AsynchronousChannelGroup;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AsExecutor {

    public static void main(String[] args) throws Exception {
        // create channel groups
        ThreadFactory factory = Executors.defaultThreadFactory();
        AsynchronousChannelGroup group1 = AsynchronousChannelGroup
            .withFixedThreadPool(5, factory);
        AsynchronousChannelGroup group2 = AsynchronousChannelGroup
            .withCachedThreadPool(Executors.newCachedThreadPool(factory), 0);
        AsynchronousChannelGroup group3 = AsynchronousChannelGroup
            .withThreadPool(Executors.newFixedThreadPool(10, factory));

        try {
            // execute simple tasks
            testSimpleTask(group1);
            testSimpleTask(group2);
            testSimpleTask(group3);
        } finally {
            group1.shutdown();
            group2.shutdown();
            group3.shutdown();
        }
    }

    static void testSimpleTask(AsynchronousChannelGroup group) throws Exception {
        Executor executor = (Executor)group;
        final CountDownLatch latch = new CountDownLatch(1);
        executor.execute(new Runnable() {
            public void run() {
                latch.countDown();
            }
        });
        latch.await();
    }
}
