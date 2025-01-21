/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.Method.IsObsolete;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class isobsolete001a {

    public static void main(String args[]) {
        isobsolete001a _isobsolete001a = new isobsolete001a();
        System.exit(isobsolete001.JCK_STATUS_BASE + _isobsolete001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        // make communication pipe to debugger
        log.display("Creating pipe");
        IOPipe pipe = argumentHandler.createDebugeeIOPipe(log);

        // ensure tested class loaded
        log.display("Creating object of tested class");
        TestedClass foo = new TestedClass();

        // send debugger signal READY
        log.display("Sending signal to debugger: " + isobsolete001.READY);
        pipe.println(isobsolete001.READY);

        // wait for signal QUIT from debugeer
        log.display("Waiting for signal from debugger: " + isobsolete001.QUIT);
        String signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);

        // check received signal
        if (! signal.equals(isobsolete001.QUIT)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + isobsolete001.QUIT + ")");
            log.display("Debugee FAILED");
            return isobsolete001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return isobsolete001.PASSED;
    }

    // tested class
    public static class TestedClass {
        int foo = 0;

        public void testedMethod() {
            foo = 1;
        }
    }

}
