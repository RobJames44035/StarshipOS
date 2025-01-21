/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.unit.timers;

import java.io.PrintStream;
import java.util.*;

public class JvmtiTest {

    final static int JCK_STATUS_BASE = 95;
    final static int THREADS_LIMIT = 5;
    final static String NAME_PREFIX = "JvmtiTest-";
    static Object counterLock = new Object();
    static int counter = 0;
    static Object finalLock = new Object();

    static {
        try {
            System.loadLibrary("timers");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load timers library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int GetResult();
    native static void RegisterCompletedThread(Thread thread,
        int threadNumber, int iterationCount);
    native static void Analyze();

    static volatile int thrCount = 0;

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        TestThread t[] = new TestThread[THREADS_LIMIT+1];

        synchronized(counterLock) {
            for (int i=1; i <= THREADS_LIMIT; i++) {
                t[i] = new TestThread(NAME_PREFIX + i, i, i * 100);
                t[i].start();
            }

            try {
                while (counter < THREADS_LIMIT) {
                    counterLock.wait();
                }
            } catch (InterruptedException exc) {
                throw new Error("Unexpected: " + exc);
            }
        }

        // all thread waiting to exit now
        Analyze();
        synchronized(finalLock) {
            // let them finish
            finalLock.notifyAll();
        }

        try {
            for (int i=1; i <= THREADS_LIMIT; i++) {
                t[i].join();
            }
        } catch (InterruptedException e) {
            throw new Error("Unexpected: " + e);
        }
        return GetResult();
    }

    static void completeThread() {
        try {
            synchronized(finalLock) {
                synchronized(counterLock) {
                    ++counter;
                    counterLock.notifyAll();
                }
                finalLock.wait();
            }
        } catch (InterruptedException exc) {
            throw new Error("Unexpected: " + exc);
        }
    }

    static class TestThread extends Thread {
        int threadNumber;
        int iterations;

        public TestThread(String name, int threadNumber, int iterations) {
            super(name);
            this.threadNumber = threadNumber;
            this.iterations = iterations;
        }

        public void run() {
            for (int i = iterations; i > 0; --i) {
                List<Integer> list = new ArrayList<Integer>();
                for (int j = 10000; j > 0; --j) {
                    list.add(Integer.valueOf(j));
                }
                Collections.sort(list);
            }
            JvmtiTest.RegisterCompletedThread(this, threadNumber, iterations);
            JvmtiTest.completeThread();
        }
    }
}
