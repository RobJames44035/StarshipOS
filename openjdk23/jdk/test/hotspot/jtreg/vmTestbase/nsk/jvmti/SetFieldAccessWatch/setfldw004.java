/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetFieldAccessWatch;

import java.io.PrintStream;

public class setfldw004 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("setfldw004");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load setfldw004 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void getReady();
    native static void check(int ind);
    native static int getRes();

    static PrintStream out;
    static int result = 0;
    static boolean flag = false;

    static int fld0 = 17;
    static int fld1;
    int fld2 = 18;
    int fld3;

    public static void main(String[] args) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream ref) {
        out = ref;
        setfldw004 t = new setfldw004();

        // access fields first to force cache load
        t.meth();

        getReady();
        flag = true;

        t.meth();

        return (getRes() | result);
    }

    public void meth() {
        int loc;

        // test getstatic bytecode for initialized field
        loc = fld0;
        checkEvent(0);
        if (loc != 17) {
            out.println("fld0 value is corrupted: expected=17, actual=" + loc);
            result = 2;
        }

        // test getstatic bytecode for uninitialized field
        loc = fld1;
        checkEvent(1);
        if (loc != 0) {
            out.println("fld1 value is corrupted: expected=0, actual=" + loc);
            result = 2;
        }

        // test getfield bytecode for initialized field
        loc = fld2;
        checkEvent(2);
        if (loc != 18) {
            out.println("fld2 value is corrupted: expected=18, actual=" + loc);
            result = 2;
        }

        // test getfield bytecode for uninitialized field
        loc = fld3;
        checkEvent(3);
        if (loc != 0) {
            out.println("fld3 value is corrupted: expected=0, actual=" + loc);
            result = 2;
        }
    }

    static void checkEvent(int ind) {
        if (flag) {
            check(ind);
        }
    }
}
