/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug     4530538
 * @summary Basic unit test of ThreadInfo.getBlockedCount()
 * @author  Alexei Guibadoulline and Mandy Chung
 * @author  Jaroslav Bachorik
 *
 * @run main ThreadBlockedCount
 */

import java.lang.management.*;
import java.util.concurrent.Phaser;

public class ThreadBlockedCount {
        static final long EXPECTED_BLOCKED_COUNT = 3;
    static final int  DEPTH = 10;
    private static final ThreadMXBean mbean
        = ManagementFactory.getThreadMXBean();

    private static final Object a = new Object();
    private static final Object b = new Object();
    private static final Object c = new Object();

    private static final Object blockedObj1 = new Object();
    private static final Object blockedObj2 = new Object();
    private static final Object blockedObj3 = new Object();
    private static volatile boolean testOk = true;
    private static BlockingThread blocking;
    private static BlockedThread blocked;

    public static void main(String args[]) throws Exception {
        // real run
        runTest();
        if (!testOk) {
            throw new RuntimeException("TEST FAILED.");
        }
        System.out.println("Test passed.");
    }

    private static void runTest() throws Exception {
        final Phaser p = new Phaser(2);

        blocking = new BlockingThread(p);
        blocking.start();

        blocked = new BlockedThread(p);
        blocked.start();

        try {
            blocking.join();

            testOk = checkBlocked();
            p.arriveAndAwaitAdvance(); // #5

        } catch (InterruptedException e) {
            System.err.println("Unexpected exception.");
            e.printStackTrace(System.err);
            throw e;
        }
    }


    static class BlockedThread extends Thread {
        private final Phaser p;

        BlockedThread(Phaser p) {
            super("BlockedThread");
            this.p = p;
        }

        public void run() {
            int accumulator = 0;
            p.arriveAndAwaitAdvance(); // #1

            // Enter lock a without blocking
            synchronized (a) {
                p.arriveAndAwaitAdvance(); // #2

                // Block to enter blockedObj1
                // blockedObj1 should be owned by BlockingThread
                synchronized (blockedObj1) {
                    accumulator++; // filler
                }
            }

            // Enter lock a without blocking
            synchronized (b) {
                // wait until BlockingThread holds blockedObj2
                p.arriveAndAwaitAdvance(); // #3

                // Block to enter blockedObj2
                // blockedObj2 should be owned by BlockingThread
                synchronized (blockedObj2) {
                    accumulator++; // filler
                }
            }

            // Enter lock a without blocking
            synchronized (c) {
                // wait until BlockingThread holds blockedObj3
                p.arriveAndAwaitAdvance(); // #4

                // Block to enter blockedObj3
                // blockedObj3 should be owned by BlockingThread
                synchronized (blockedObj3) {
                    accumulator++; // filler
                }
            }

            // wait for the main thread to check the blocked count
            System.out.println("Acquired " + accumulator + " monitors");
            p.arriveAndAwaitAdvance(); // #5
            // ... and we can leave now
        } // run()
    } // BlockedThread

    static class BlockingThread extends Thread {
        private final Phaser p;

        BlockingThread(Phaser p) {
            super("BlockingThread");
            this.p = p;
        }

        private void waitForBlocked() {
            // wait for BlockedThread.
            p.arriveAndAwaitAdvance();

            boolean threadBlocked = false;
            while (!threadBlocked) {
                // give a chance for BlockedThread to really block
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.err.println("Unexpected exception.");
                    e.printStackTrace(System.err);
                    testOk = false;
                    break;
                }
                ThreadInfo info = mbean.getThreadInfo(blocked.getId());
                threadBlocked = (info.getThreadState() == Thread.State.BLOCKED);
            }
        }

        public void run() {
            p.arriveAndAwaitAdvance(); // #1

            synchronized (blockedObj1) {
                System.out.println("BlockingThread attempts to notify a");
                waitForBlocked(); // #2
            }

            // block until BlockedThread is ready
            synchronized (blockedObj2) {
                System.out.println("BlockingThread attempts to notify b");
                waitForBlocked(); // #3
            }

            // block until BlockedThread is ready
            synchronized (blockedObj3) {
                System.out.println("BlockingThread attempts to notify c");
                waitForBlocked(); // #4
            }

        } // run()
    } // BlockingThread

    private static long getBlockedCount() {
        long count;
        // Check the mbean now
        ThreadInfo ti = mbean.getThreadInfo(blocked.getId());
        count = ti.getBlockedCount();
        return count;
    }

    private static boolean checkBlocked() {
        // wait for the thread stats to be updated for 10 seconds
        long count = -1;
        for (int i = 0; i < 100; i++) {
            count = getBlockedCount();
            if (count >= EXPECTED_BLOCKED_COUNT) {
                return true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("Unexpected exception.");
                e.printStackTrace(System.err);
                return false;
            }
        }
        System.err.println("TEST FAILED: Blocked thread has " + count +
                            " blocked counts. Expected at least " +
                            EXPECTED_BLOCKED_COUNT);
        return false;
    }
}
