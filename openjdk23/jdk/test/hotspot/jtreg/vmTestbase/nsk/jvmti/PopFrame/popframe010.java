/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.PopFrame;

import java.io.PrintStream;

public class popframe010 {

    final static int JCK_STATUS_BASE = 95;
    final static int NESTING_DEPTH = 8;

    static {
        try {
            System.loadLibrary("popframe010");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load popframe010 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void getReady(Class cls, int depth);
    native static int check();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        TestThread thr = new TestThread();
        getReady(TestThread.class, NESTING_DEPTH + 1);

        thr.start();
        try {
            thr.join();
        } catch (InterruptedException e) {
            throw new Error("Unexpected " + e);
        }

        return check();
    }

    static class TestThread extends Thread {
        public void run() {
            countDown(NESTING_DEPTH);
        }

        public void countDown(int nestingCount) {
            if (nestingCount > 0) {
                countDown(nestingCount - 1);
            } else {
                checkPoint();
            }
        }

        // dummy method to be breakpointed in agent
        void checkPoint() {
        }
    }
}
