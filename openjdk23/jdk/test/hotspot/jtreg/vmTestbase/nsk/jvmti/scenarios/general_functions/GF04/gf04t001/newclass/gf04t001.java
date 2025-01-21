/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.general_functions.GF04;

import java.io.PrintStream;

import nsk.share.Consts;

/** Debuggee class for this test. */
public class gf04t001 {

    /** Run test from command line. */
    public static void main(String argv[]) {
        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new gf04t001().runIt(argv, out);
    }

    /* =================================================================== */

    native int check();

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        out.println("Debuggee executed");
        return check();
    }
}
