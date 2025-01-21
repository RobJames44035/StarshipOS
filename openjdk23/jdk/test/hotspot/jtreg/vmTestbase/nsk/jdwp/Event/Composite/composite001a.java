/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.Event.Composite;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class composite001a {

    public static void main(String args[]) {
        composite001a _composite001a = new composite001a();
        System.exit(composite001.JCK_STATUS_BASE + _composite001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        // exit debugee
        log.display("Debugee PASSED");
        return composite001.PASSED;
    }

}
