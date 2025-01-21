/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


package nsk.jvmti.unit.GetLocalVariable;

import java.io.PrintStream;

public class getlocal003 {

    static Thread currThread;
    int fld = 17;

    public static void main(String[] args) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        System.exit(run(args, System.out) + 95/*STATUS_TEMP*/);
    }

    public static int run(String argv[], PrintStream ref) {
        currThread = Thread.currentThread();
        getlocal003 t = new getlocal003();
        getMeth();
        t.meth01();
        for (int i = 0; i < 22; i++) {
            staticMeth(i);
        }
        return getRes();
    }

    public static synchronized int staticMeth(int intArg) {
        System.out.println(" JAVA: staticMeth: Started  " + intArg);
        float pi = 3.1415926f;
        if (intArg < 1) {
            checkLoc(currThread);
            System.out.println(" JAVA: staticMeth: Finished " + intArg);
            return intArg;        // <-- MethodExit event here
        }
        {
             { boolean bool_1 = false;
               if (intArg < 2) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             boolean bool_2 = true;
             if (intArg < 3) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { byte byte_1 = 1;
               if (intArg < 4) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             float float_1 = 1;
             if (intArg < 5) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { char char_1 = '1';
               if (intArg < 6) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             char char_2 = '2';
             if (intArg < 7) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { short short_1 = 1;
               if (intArg < 8) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             short short_2 = 2;
             if (intArg < 9) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { int int_1 = 1;
               if (intArg < 10) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             long long_1 = 1;
             if (intArg < 11) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { long long_2 = 2;
               if (intArg < 12) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             int int_2 = 2;
             if (intArg < 12) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { float float_2 = 2;
               if (intArg < 14) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             byte byte_2 = 2;
             if (intArg < 15) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { double double_1 = 1;
               if (intArg < 16) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             Object obj_1 = new Object();
             if (intArg < 17) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { String string_1 = "1";
               if (intArg < 18) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             double double_2 = 2;
             if (intArg < 19) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        {
             { Object obj_2 = new Object();
               if (intArg < 20) {
                   System.out.println(" JAVA: staticMeth: Finished " + intArg);
                   return intArg; // <-- MethodExit event here
               }
             }

             String string_2 = "2";
             if (intArg < 21) {
                 System.out.println(" JAVA: staticMeth: Finished " + intArg);
                 return intArg;   // <-- MethodExit event here
             }
        }
        System.out.println(" JAVA: staticMeth: Finished " + intArg);
        return intArg;              // <-- MethodExit event here
    }

    public double meth01() {
        float f = 6.0f;
        double d = 7.0;
        instMeth();
        return d + f;
    }

    native void instMeth();
    native static void getMeth();
    native static void checkLoc(Thread thr);
    native static int getRes();

    static {
        try {
            System.loadLibrary("getlocal003");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println(" JAVA: Could not load getlocal003 library");
            System.err.println(" JAVA: java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }
}
