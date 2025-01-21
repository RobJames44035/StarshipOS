/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetEventNotificationMode;

import java.io.PrintStream;

public class setnotif001 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("setnotif001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load setnotif001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void enableEv(Thread notifyFramePopThread);
    native static int getRes();

    static int fld = 1;

    public static void main(String[] args) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream ref) {
        setnotif001 t = new setnotif001();
        fld++;
        enableEv(Thread.currentThread());
        t.meth();
        Thread thr = new Thread(new setnotif001a());
        thr.start();
        try {
            thr.join();
        } catch (InterruptedException e) {
        }
        fld++;
        meth01(1);
        return getRes();
    }

    void meth() {
    }

    public static void meth01(int i) {
        try {
            if (i > 0) {
                throw new Throwable();
            }
        } catch (Throwable e) {}
    }
}

class setnotif001a implements Runnable {
    public void run() {
        Thread.yield();
    }
}
