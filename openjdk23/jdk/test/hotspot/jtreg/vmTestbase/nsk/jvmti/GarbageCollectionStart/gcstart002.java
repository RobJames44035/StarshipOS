/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.GarbageCollectionStart;

import java.io.*;
import java.math.*;

import nsk.share.*;

/**
 * This test exercises the JVMTI event <code>GarbageCollectionStart</code>.
 * <br>It verifies that the raw monitor functions and memory management
 * functions (<code>Allocate, Deallocate</code>) can be used during processing
 * this event. Usage of these functions is allowed by the JVMTI spec.
 */
public class gcstart002 {
    /* number of interations to provoke garbage collecting */
    final static int ITERATIONS = 2;

    static {
        try {
            System.loadLibrary("gcstart002");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load \"gcstart002\" library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // produce JCK-like exit status
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String argv[], PrintStream out) {
        return new gcstart002().runThis(argv, out);
    }

    private int runThis(String argv[], PrintStream out) {
        try {
            for (int i=0; i<ITERATIONS; i++)
                ClassUnloader.eatMemory(); // provoke garbage collecting
        } catch (OutOfMemoryError e) {
            // ignoring
        }

        return Consts.TEST_PASSED;
    }
}
