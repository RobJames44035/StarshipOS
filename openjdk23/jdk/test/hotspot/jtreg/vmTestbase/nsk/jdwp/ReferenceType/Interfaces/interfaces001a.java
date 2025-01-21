/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ReferenceType.Interfaces;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class interfaces001a {

    public static void main(String args[]) {
        interfaces001a _interfaces001a = new interfaces001a();
        System.exit(interfaces001.JCK_STATUS_BASE + _interfaces001a.runIt(args, System.err));
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
        return interfaces001.PASSED;
    }

    static public interface TestedClassInterface1 {
        public int methodOfInterface1(int x);
    }

    static public interface TestedClassInterface2 {
        public byte methodOfInterface2(byte b);
    }

    static class TestedClass implements TestedClassInterface1, TestedClassInterface2 {
//    static class TestedClass implements TestedClassInterface1 {
        int foo = 0;
        public int methodOfInterface1(int x) { return x; }
        public byte methodOfInterface2(byte b) { return b; }
    }
}
