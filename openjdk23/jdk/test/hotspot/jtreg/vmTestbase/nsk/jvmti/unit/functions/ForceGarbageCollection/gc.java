/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.unit.functions.ForceGarbageCollection;

import java.io.PrintStream;

public class gc {

    int k;
    int y;
    private final static int _SIZE = 3000000;

    static {
        try {
            System.loadLibrary("gc");
        } catch(UnsatisfiedLinkError ule) {
            System.err.println("Could not load gc library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static int GetResult();
    native static void jvmtiForceGC();
    native static void checkGCStart();
    native static void checkGCFinish();

    public static void main(String[] args) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        System.exit(run(args, System.out) + 95 /* STATUS_TEMP */ );
    }

    public static int run(String argv[], PrintStream out) {
         jvmtiForceGC();
         run();
         jvmtiForceGC();
         run1();
         jvmtiForceGC();
         run();
         jvmtiForceGC();
         checkGCStart();
         checkGCFinish();
         return GetResult();
    }

    public static void run() {

        long start, end;
        float difference;

        start = Runtime.getRuntime().totalMemory();

        try {
            gc[] array = new gc[_SIZE];
            for (int i = 0; i < _SIZE; i++) {
                array[i] = new gc();
            }
        } catch (OutOfMemoryError e) {
            System.out.println(e);
        }

        end = Runtime.getRuntime().totalMemory();

        difference = ( end -  start ) / _SIZE;

        System.out.println("start = " + start);
        System.out.println("end   = " + end);
    }

    public static void run1() {

        long start, end;
        float difference;

        start = Runtime.getRuntime().totalMemory();

        try {
            gc[] array = new gc[_SIZE];
            for (int i = 0; i < _SIZE; i++) {
                array[i] = new gc();
            }
        } catch (OutOfMemoryError e) {
            System.out.println(e);
        }

        end = Runtime.getRuntime().totalMemory();

        difference = ( end -  start ) / _SIZE;

        System.out.println("start = " + start);
        System.out.println("end   = " + end);
    }

}
