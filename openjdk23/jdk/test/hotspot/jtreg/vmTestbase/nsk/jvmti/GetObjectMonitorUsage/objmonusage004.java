/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetObjectMonitorUsage;

import java.io.PrintStream;

public class objmonusage004 {

    final static int JCK_STATUS_BASE = 95;
    final static int NUMBER_OF_THREADS = 16;
    final static int WAIT_TIME = 100;

    static {
        try {
            System.loadLibrary("objmonusage004");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load objmonusage004 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    static Object lockStart = new Object();
    static Object lockCheck = new Object();

    native static int getRes();
    native static void check(Object obj, Thread owner,
                             int entryCount, int waiterCount);

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        Thread currThread = Thread.currentThread();
        ContendThread thr[] = new ContendThread[NUMBER_OF_THREADS];
        synchronized (lockCheck) {
            // Virtual threads are not supported by GetObjectMonitorUsage.
            // Correct the expected values if the test is executed with
            // JTREG="TEST_THREAD_FACTORY=Virtual".
            Thread expOwner = currThread.isVirtual() ? null : currThread;
            int expEntryCount = currThread.isVirtual() ? 0 : 2;

            synchronized (lockCheck) {
                check(lockCheck, expOwner, expEntryCount, 0);
            }
            expEntryCount = currThread.isVirtual() ? 0 : 1;
            int expWaiterCount = 0;

            for (int i = 0; i < NUMBER_OF_THREADS; i++) {
                thr[i] = new ContendThread();
                if (!thr[i].isVirtual()) {
                    expWaiterCount++;
                }
                synchronized (lockStart) {
                    thr[i].start();
                    try {
                        lockStart.wait();
                        lockStart.wait(WAIT_TIME);
                    } catch (InterruptedException e) {
                        throw new Error("Unexpected " + e);
                    }
                }
                check(lockCheck, expOwner, expEntryCount, expWaiterCount);
            }
        }

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            try {
                thr[i].join();
            } catch (InterruptedException e) {
                throw new Error("Unexpected " + e);
            }
        }

        check(lockCheck, null, 0, 0);
        return getRes();
    }

    static class ContendThread extends Thread {
        public synchronized void run() {
            synchronized (lockStart) {
                lockStart.notify();
            }
            synchronized (lockCheck) {
                lockCheck.notify();
            }
        }
    }
}
