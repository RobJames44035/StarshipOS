/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ObjectReference.invokeMethod;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * This is a debuggee class.
 */
public class invokemethod006t {
    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        return new invokemethod006t().runIt(args);
    }

    private int runIt(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        invokemethod006tDummyClass invokemethod006tdummyCls = new invokemethod006tDummyClass();
        Thread.currentThread().setName(invokemethod006.DEBUGGEE_THRNAME);

        pipe.println(invokemethod006.COMMAND_READY);
        String cmd = pipe.readln();
        if (cmd.equals(invokemethod006.COMMAND_QUIT)) {
            System.err.println("Debuggee: exiting due to the command "
                + cmd);
            return Consts.TEST_PASSED;
        }

        int stopMeHere = 0; // invokemethod006.DEBUGGEE_STOPATLINE

        cmd = pipe.readln();
        if (!cmd.equals(invokemethod006.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(Consts.JCK_STATUS_BASE +
                Consts.TEST_FAILED);
        }
        return Consts.TEST_PASSED;
    }
}

// Dummy reference types used to provoke ClassNotLoadedException
// in the debugger
class invokemethod006tDummyType {}
final class invokemethod006tFinDummyType {}

// Dummy class used to provoke ClassNotLoadedException
// in the debugger
class invokemethod006tDummyClass {

    void dummyMeth(invokemethod006tDummyType dummyT) {
        System.err.println("the method \"dummyMeth\" was invoked!");
    }

    long finDummyMeth(invokemethod006tFinDummyType finDummyT) {
        System.err.println("the method \"finDummyMeth\" was invoked!");
        return 9223372036854775807L;
    }
}
