/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ThreadReference.ThreadGroup;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class threadgroup001a {

    public static void main(String args[]) {
        threadgroup001a _threadgroup001a = new threadgroup001a();
        System.exit(threadgroup001.JCK_STATUS_BASE + _threadgroup001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);
        log.display("Creating pipe");
        IOPipe pipe = argumentHandler.createDebugeeIOPipe(log);
        log.display("Sending command: " + "ready");
        pipe.println("ready");
        log.display("Waiting for command: " + "quit");
        String command = pipe.readln();
        log.display("Received command: " + command);
        log.display("Debugee PASSED");
        return threadgroup001.PASSED;
    }
}
