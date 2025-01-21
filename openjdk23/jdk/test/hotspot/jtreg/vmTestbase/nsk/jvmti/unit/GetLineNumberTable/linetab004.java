/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


package nsk.jvmti.unit.GetLineNumberTable;

import java.io.PrintStream;

interface Interface004 {
    int instanceMeth0();
    int instanceMeth1();
}

abstract class Abstract004 implements Interface004 {
    protected int fld;

    // Constructor
    Abstract004() {
        fld = 1000;
    }

    public abstract int instanceMeth0();

    public int instanceMeth1() {
        fld = 999;
        fld = instanceMeth0();
        return 0;
    }
}

public class linetab004 extends Abstract004 {

    final static int JCK_STATUS_BASE = 95;

    public int instanceMeth0() {
        int i = 0;
        i++;
        return i;
    }

    public int instanceMeth2() {
        return instanceMeth1();
    }

    static native int staticNativeMeth();
    native int instanceNativeMeth();

    static {
        try {
            System.loadLibrary("linetab004");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load linetab004 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int check();

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        return check();
    }
}
