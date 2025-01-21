/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ClassLoaderReference.VisibleClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class visibclasses001a {

    public static void main(String args[]) {
        visibclasses001a _visibclasses001a = new visibclasses001a();
        System.exit(visibclasses001.JCK_STATUS_BASE + _visibclasses001a.runIt(args, System.err));
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
        log.display("Sending signal to debugger: " + visibclasses001.READY);
        pipe.println(visibclasses001.READY);

        // wait for signal QUIT from debugeer
        log.display("Waiting for signal from debugger: " + visibclasses001.QUIT);
        String signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);

        // check received signal
        if (! signal.equals(visibclasses001.QUIT)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + visibclasses001.QUIT + ")");
            log.display("Debugee FAILED");
            return visibclasses001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return visibclasses001.PASSED;
    }

    // tested class with nested classes
    public static class TestedClass {
        int foo = 0;
        public TestedClass() {
            foo = 100;
        }
    }
}
