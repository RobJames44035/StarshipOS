/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.Event.VM_DEATH;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class vmdeath001a {

    public static void main(String args[]) {
        vmdeath001a _vmdeath001a = new vmdeath001a();
        System.exit(vmdeath001.JCK_STATUS_BASE + _vmdeath001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        // exit debugee
        log.display("Debugee PASSED");
        return vmdeath001.PASSED;
    }

}
