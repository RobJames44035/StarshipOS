/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ReferenceType.Fields;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class fields001a {

    public static void main(String args[]) {
        fields001a _fields001a = new fields001a();
        System.exit(fields001.JCK_STATUS_BASE + _fields001a.runIt(args, System.err));
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
        return fields001.PASSED;
    }

    static class TestedClass {
        public byte byteField = (byte) 0xEE;
        public boolean booleanField = false;
        public char charField = 'Z';
        public short shortField = 10;
        public int intField = 100;
        public long longField = 1000000;
        public float floatField = (float) 3.14;
        public double doubleField = 2.48e-10;
        public String stringField = "stringFieldValue";
        public TestedClass objectField = this;
        public int[] intArrayField = {intField};
    }

}
