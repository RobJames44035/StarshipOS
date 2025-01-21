/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.VirtualMachine.HoldEvents;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class holdevents001a {

    public static void main(String args[]) {
        holdevents001a _holdevents001a = new holdevents001a();
        System.exit(holdevents001.JCK_STATUS_BASE + _holdevents001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        // make communication pipe to debugger
        log.display("Creating pipe");
        IOPipe pipe = argumentHandler.createDebugeeIOPipe(log);

        // send debugger signal READY
        log.display("Sending signal to debugger: " + holdevents001.READY);
        pipe.println(holdevents001.READY);

        // wait for signal QUIT from debugeer
        log.display("Waiting for signal from debugger: " + holdevents001.QUIT);
        String signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);

        // check received signal
        if (! signal.equals(holdevents001.QUIT)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + holdevents001.QUIT + ")");
            log.display("Debugee FAILED");
            return holdevents001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return holdevents001.PASSED;
    }

}
