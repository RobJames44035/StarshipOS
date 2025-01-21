/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ReferenceType.Methods;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class methods001a {

    public static void main(String args[]) {
        methods001a _methods001a = new methods001a();
        System.exit(methods001.JCK_STATUS_BASE + _methods001a.runIt(args, System.err));
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
        return methods001.PASSED;
    }

    static class TestedClass {
        public TestedClass() {}
        public byte byteMethod(byte b) { return b; }
        public boolean booleanMethod() { return true; }
        public char charMethod(byte b) { return (char) b; }
        public short shortMethod(short x, short y) { return (short) (x - y); }
        public int intMethod(int x, short y) { return x - y; }
        public long longMethod(int x) { return (long) x; }
        public float floatMethod(double x) { return (float) x; }
        public double doubleMethod() { return 2.48e-10; }
        public String stringMethod(String s, char ch) { return s + ch; };
        public TestedClass objectMethod() { return this; }
        public int[] intArrayMethod(int n) { return new int[n]; };
        public TestedClass(boolean b) {}
    }
}
