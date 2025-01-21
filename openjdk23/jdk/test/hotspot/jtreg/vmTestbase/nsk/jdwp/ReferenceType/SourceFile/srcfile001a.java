/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ReferenceType.SourceFile;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class srcfile001a {

    public static void main(String args[]) {
        srcfile001a _srcfile001a = new srcfile001a();
        System.exit(srcfile001.JCK_STATUS_BASE + _srcfile001a.runIt(args, System.err));
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
        return srcfile001.PASSED;
    }

    static public class TestedClass {
        int foo = 0;
    }
}
