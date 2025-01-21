/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ReferenceType.Modifiers;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class modifiers001a {

    public static void main(String args[]) {
        modifiers001a _modifiers001a = new modifiers001a();
        System.exit(modifiers001.JCK_STATUS_BASE + _modifiers001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);
        log.display("Creating pipe");
        IOPipe pipe = argumentHandler.createDebugeeIOPipe(log);
        log.display("Creating object of tested class");
        TestedClass foo = new TestedClass();
        log.display("Sending command: " + "ready");
        pipe.println("ready");
        log.display("Waiting for command: " + "quit");
        String command = pipe.readln();
        log.display("Received command: " + command);
        log.display("Debugee PASSED");
        return modifiers001.PASSED;
    }

    static final public class TestedClass {
        int foo = 0;
    }

}
