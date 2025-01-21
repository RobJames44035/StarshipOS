/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetOwnedMonitorInfo;

import nsk.share.Wicket;
import java.io.PrintStream;

public class ownmoninf002 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("ownmoninf002");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load ownmoninf002 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void checkMon(Thread thr);
    native static int getRes();

    public static Wicket startingBarrier;
    public static Wicket endingBarrier;

    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        System.exit(run(argv, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream ref) {
        ownmoninf002a thr = new ownmoninf002a();
        startingBarrier = new Wicket();
        endingBarrier = new Wicket();
        thr.start();
        startingBarrier.waitFor();
        checkMon(thr);
        endingBarrier.unlock();

        try {
            thr.join();
        } catch (InterruptedException e) {
            throw new Error("Unexpected " + e);
        }

        return getRes();
    }
}

class ownmoninf002a extends Thread {
    private synchronized void meth() {
        Object lock = new Object();
        synchronized (lock) {
            ownmoninf002.startingBarrier.unlock();
            ownmoninf002.endingBarrier.waitFor();
        }
    }
    public void run() {
        meth();
    }
}
