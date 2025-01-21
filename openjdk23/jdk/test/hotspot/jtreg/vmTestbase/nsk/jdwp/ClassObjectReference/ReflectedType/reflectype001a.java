/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ClassObjectReference.ReflectedType;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class reflectype001a {

    public static void main(String args[]) {
        reflectype001a _reflectype001a = new reflectype001a();
        System.exit(reflectype001.JCK_STATUS_BASE + _reflectype001a.runIt(args, System.err));
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
        return reflectype001.PASSED;
    }

    static public class TestedClass {
        int foo = 0;
    }
}
