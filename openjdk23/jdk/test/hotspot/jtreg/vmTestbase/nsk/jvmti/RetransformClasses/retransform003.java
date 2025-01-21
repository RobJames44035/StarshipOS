/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.RetransformClasses;

import nsk.share.Consts;
import java.io.*;
import java.util.*;

public class retransform003 {
    static final String LOADED_CLASS = "nsk.share.jvmti.RetransformClasses.LinearHierarchy.Class1";

    public int runIt(String[] args, PrintStream out) {
        try {
            rightAgentOrder = new int[] { 2, 1, 3 };
            Class klass = Class.forName(LOADED_CLASS);

            rightAgentOrder = new int[] { 1, 3 };
            forceLoadedClassesRetransformation(klass);
            return status;
        }
        catch (ClassNotFoundException e) {
            return Consts.TEST_FAILED;
        }
    }

    static int[] rightAgentOrder = null;
    static int curPosition = 0;
    static int status = Consts.TEST_PASSED;

    public static void callback(String className, int agentID) {
        System.out.printf("Class: %70s; Agent ID: %d\n", className, agentID);

        if (agentID != rightAgentOrder[curPosition]) {
            System.out.printf("ERROR: %d'th agent was invoked instead of %d'th.\n", agentID, rightAgentOrder[curPosition]);
            status = Consts.TEST_FAILED;
        }

        curPosition = ++curPosition % rightAgentOrder.length;
    }

    static native boolean forceLoadedClassesRetransformation(Class klass);

    /** run test from command line */
    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        System.exit(run(args, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** run test from JCK-compatible environment */
    public static int run(String args[], PrintStream out) {
        return (new retransform003()).runIt(args, out);
    }
}
