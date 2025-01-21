/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.ReferenceType.NestedTypes;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

public class nestedtypes001a {

    public static void main(String args[]) {
        nestedtypes001a _nestedtypes001a = new nestedtypes001a();
        System.exit(nestedtypes001.JCK_STATUS_BASE + _nestedtypes001a.runIt(args, System.err));
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
        log.display("Sending signal to debugger: " + nestedtypes001.READY);
        pipe.println(nestedtypes001.READY);

        // wait for signal QUIT from debugeer
        log.display("Waiting for signal from debugger: " + nestedtypes001.QUIT);
        String signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);

        // check received signal
        if (! signal.equals(nestedtypes001.QUIT)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + nestedtypes001.QUIT + ")");
            log.display("Debugee FAILED");
            return nestedtypes001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return nestedtypes001.PASSED;
    }

    // tested class with nested classes
    public static class TestedClass {

        public interface NestedInterface {
            public int methodFoo();
        }

        public static class StaticNestedClass implements NestedInterface {
            int foo = 0;
            public int methodFoo() { return foo; }
        }

        public class InnerNestedClass extends StaticNestedClass {
            public int methodFoo() { return foo + foo; }
        }

        public TestedClass() {
            // ensure all nested classes are loaded
            InnerNestedClass foo = new InnerNestedClass();
        }
    }
}
