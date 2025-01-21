/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetFieldModificationWatch;

import jdk.test.lib.thread.TestThreadFactory;

import java.io.PrintStream;

public class setfmodw001 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("setfmodw001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load setfmodw001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void setWatch(int fld_ind);
    native void touchfld0();
    native static void check(int fld_ind, boolean flag);
    native static int getRes();

    int fld0 = -1;
    static int fld1 = 1;
    private setfmodw001a fld2 = new setfmodw001a();
    static int fld = 10;

    public static Object lock = new Object();

    public static void main(String[] args) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream ref) {
        setfmodw001 t = new setfmodw001();
        setfmodw001a t_a = new setfmodw001a();
        Thread t_b = TestThreadFactory.newThread(new setfmodw001b());
        t_b.start();
        synchronized (lock) {
            fld1 = fld1 + 1;
            check(1, false);
        }
        synchronized (lock) {
            t_a.fld3 = new int[10];
            check(3, false);
        }
        setWatch(1);
        synchronized (lock) {
            fld1 = fld1 + fld;
            check(1, true);
        }
        setWatch(3);
        synchronized (lock) {
            t_a.fld3 = new int[10];
            check(3, true);
        }
        t.meth01();
        try {
            t_b.join();
        } catch (InterruptedException e) {}
        return getRes();
    }

    private void meth01() {
        synchronized (lock) {
            touchfld0();
            check(0, true);
        }
        synchronized (lock) {
            fld2 = new setfmodw001a();
            check(2, false);
        }
        setWatch(2);
        synchronized (lock) {
            fld2 = new setfmodw001a();
            check(2, true);
        }
    }
}

class setfmodw001a {
    int[] fld3;
    int fld = 2;
}

class setfmodw001b implements Runnable  {
    float fld4;
    public void run() {
        synchronized (setfmodw001.lock) {
            fld4 = setfmodw001.fld;
            setfmodw001.check(4, false);
        }
        setfmodw001.setWatch(4);
        synchronized (setfmodw001.lock) {
            fld4 += setfmodw001.fld;
            setfmodw001.check(4, true);
        }
    }
}
