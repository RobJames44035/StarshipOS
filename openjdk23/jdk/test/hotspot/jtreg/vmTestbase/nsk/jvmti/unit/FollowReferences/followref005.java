/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.unit.FollowReferences;

import java.io.PrintStream;
import nsk.share.jvmti.*;
import nsk.share.*;

public class followref005 extends DebugeeClass {

    static {
        loadLibrary("followref005");
    }

    public static void main(String[] argv) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String[] argv, PrintStream out) {
        return new followref005().runIt(argv, out);
    }

    public int runIt(String[] argv, PrintStream out) {
        return checkStatus(0);
    }
}
