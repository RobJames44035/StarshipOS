/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

//    THIS TEST IS LINE NUMBER SENSITIVE

package nsk.jvmti.GetLineNumberTable;

import java.io.PrintStream;

public class linetab001 {

    final static int JCK_STATUS_BASE = 95;

    native static int check();

    static {
        try {
            System.loadLibrary("linetab001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load linetab001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    static int fld;

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream ref) {
        return check();
    }

    /* This part is line number sensitive,
     * must be changed sinchronously with the native part of the test
     */

    public static void meth00() {} // linetab001.c::m0[0]

    public double meth01() {
        long l = 22;      // linetab001.c::m1[0]
        float f = 6.0f;   // linetab001.c::m1[1]
        double d = 7.0;   // linetab001.c::m1[2]
        return d + f + l; // linetab001.c::m1[3]
    }
}
