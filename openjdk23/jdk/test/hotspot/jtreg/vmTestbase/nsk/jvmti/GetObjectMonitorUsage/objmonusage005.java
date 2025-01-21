/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetObjectMonitorUsage;

import nsk.share.Wicket;
import java.io.PrintStream;

public class objmonusage005 {

    final static int JCK_STATUS_BASE = 95;
    final static int WAIT_TIME = 100;

    static {
        try {
            System.loadLibrary("objmonusage005");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load objmonusage005 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    public static Wicket startingBarrier;
    static Object lockCheck = new Object();

    native static void check(Object obj);
    native static int getRes();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        LockingThread thr = new LockingThread();
        startingBarrier = new Wicket();
        thr.start();
        startingBarrier.waitFor();
        check(lockCheck);
        thr.letItGo();
        return getRes();
    }

    static class LockingThread extends Thread {
        private volatile boolean flag = true;

        public synchronized void run() {
            synchronized (lockCheck) {
                startingBarrier.unlock();
                int i = 0;
                int n = 1000;
                while (flag) {
                    if (n <= 0) {
                        n = 1000;
                    }
                    if (i > n) {
                        i = 0;
                        n--;
                    }
                    i++;
                }
            }
        }

        public void letItGo() {
            flag = false;
        }
    }
}
