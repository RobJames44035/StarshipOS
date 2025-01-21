/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.RetransformClasses;

import nsk.share.Consts;
import java.io.*;
import java.util.*;

class ClassWithStaticInitializers {
    static {
        b = 0;
        c = 1;
    }

    static public int a = 0;
    static protected float b;
    static private byte c;

    public static void setB(float _b) { b = _b; }
    public static float getB() { return b; }

    public static void setC(byte _c) { c = _c; }
    public static byte getC() { return c; }
}

public class retransform004 {
    public int runIt(String[] args, PrintStream out) {
            ClassWithStaticInitializers.a = 1;
            ClassWithStaticInitializers.setB(1);
            ClassWithStaticInitializers.setC((byte)1);

            forceLoadedClassesRetransformation(ClassWithStaticInitializers.class);

            if (ClassWithStaticInitializers.a == 1
                && ClassWithStaticInitializers.getB() == 1
                && ClassWithStaticInitializers.getC() == 1)
            {
               System.out.println("TEST PASSED: values are the same");
               return Consts.TEST_PASSED;
            } else {
               System.out.println("TEST FAILED: values changed during retransformation");
                return Consts.TEST_FAILED;
            }
    }

    static native boolean forceLoadedClassesRetransformation(Class klass);

    /** run test from command line */
    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        System.exit(run(args, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** run test from JCK-compatible environment */
    public static int run(String args[], PrintStream out) {
        return (new retransform004()).runIt(args, out);
    }
}
