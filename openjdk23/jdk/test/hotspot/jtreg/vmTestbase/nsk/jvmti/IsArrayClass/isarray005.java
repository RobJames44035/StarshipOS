/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.IsArrayClass;

import java.io.PrintStream;

public class isarray005 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("isarray005");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load isarray005 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void check(Class cls, boolean flag);
    native static int getRes();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        check(byte.class, false);
        check(char.class, false);
        check(double.class, false);
        check(float.class, false);
        check(int.class, false);
        check(long.class, false);
        check(short.class, false);
        check(void.class, false);
        check(boolean.class, false);
        check(Object.class, false);
        check(new int[1].getClass(), true);
        check(new byte[1].getClass(), true);
        check(new char[1].getClass(), true);
        check(new double[1].getClass(), true);
        check(new float[1].getClass(), true);
        check(new int[1].getClass(), true);
        check(new long[1].getClass(), true);
        check(new short[1].getClass(), true);
        check(new Object[1].getClass(), true);
        return getRes();
    }
}
