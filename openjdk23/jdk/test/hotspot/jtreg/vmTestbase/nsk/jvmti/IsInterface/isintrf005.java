/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.IsInterface;

import java.io.PrintStream;

public class isintrf005 {

    final static int JCK_STATUS_BASE = 95;

    static {
        try {
            System.loadLibrary("isintrf005");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load isintrf005 library");
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
        check(isintrf005.class, false);
        check(InnerInterface.class, true);
        check(InnerClass.class, false);
        check(OuterInterface.class, true);
        check(OuterClass1.class, false);
        check(OuterClass2.class, false);
        return getRes();
    }

    interface InnerInterface {
    }

    class InnerClass implements InnerInterface {
    }
}

interface OuterInterface {
}

abstract class OuterClass1 {
}

class OuterClass2 implements OuterInterface {
}
