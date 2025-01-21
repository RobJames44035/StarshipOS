/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GetArgumentsSize;

import java.io.PrintStream;

public class argsize001 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("argsize001");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load argsize001 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check(Class c1, Class c2);

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream ref) {
        return check(argsize001a.class, Inn.class);
    }

    protected static final float[] meth_stat(int i, String s) {
        float[] f = new float[i];
        return f;
    }

    private char meth_1(char c1, char c2, char c3) {
        char loc1 = c1;
        return loc1;
    }

    strictfp float meth_2(float f, double d, long l) {
        return (float)d / l / f;
    }

    class Inn {
        String fld;
        public synchronized final void meth_inn(String s, long l) {
            fld = s;
        }
    }
}

abstract class argsize001a {
    synchronized argsize001 meth_new(argsize001 p1, argsize001 p2) {
        argsize001[] loc1 = {p1, p2};
        return loc1[1];
    }

    abstract void meth_abs();
}
