/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.Method.Bytecodes;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class bytecodes001a {

    public static final int FIRST_LINE_NUMBER = 65;
    public static final int LAST_LINE_NUMBER = 76;

    public static void main(String args[]) {
        bytecodes001a _bytecodes001a = new bytecodes001a();
        System.exit(bytecodes001.JCK_STATUS_BASE + _bytecodes001a.runIt(args, System.err));
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
        log.display("Sending signal to debugger: " + bytecodes001.READY);
        pipe.println(bytecodes001.READY);

        // wait for signal QUIT from debugeer
        log.display("Waiting for signal from debugger: " + bytecodes001.QUIT);
        String signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);

        // check received signal
        if (! signal.equals(bytecodes001.QUIT)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + bytecodes001.QUIT + ")");
            log.display("Debugee FAILED");
            return bytecodes001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return bytecodes001.PASSED;
    }

    // tested class
    public static class TestedClass {
        int foo = 0;

        public void testedMethod() {
            foo = 1;        // foo == 1
            foo++;          // foo == 2
            foo++;          // foo == 3
            foo++;          // foo == 4
            foo++;          // foo == 5
            foo++;          // foo == 6
            foo++;          // foo == 7
            foo++;          // foo == 8
            foo++;          // foo == 9
            foo++;          // foo == 10
        }
    }

}
