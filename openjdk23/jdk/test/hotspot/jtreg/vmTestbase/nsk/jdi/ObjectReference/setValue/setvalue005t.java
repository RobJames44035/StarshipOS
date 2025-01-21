/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ObjectReference.setValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is a debuggee class.
 */
public class setvalue005t {
    // dummy instance uninitialized fields of reference types
    setvalue005tDummyType dummyType;
    setvalue005tAbsDummyType absDummyType;
    setvalue005tFinDummyType finDummyType;

    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        return new setvalue005t().runIt(args);
    }

    private int runIt(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe setvalue005tPipe = argHandler.createDebugeeIOPipe();

        Thread.currentThread().setName(setvalue005.DEBUGGEE_THRNAME);

        setvalue005tPipe.println(setvalue005.COMMAND_READY);
        String cmd = setvalue005tPipe.readln();
        if (!cmd.equals(setvalue005.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(Consts.JCK_STATUS_BASE +
                Consts.TEST_FAILED);
        }
        return Consts.TEST_PASSED;
    }
}

// Dummy classes used to provoke ClassNotLoadedException
// in the debugger
class setvalue005tDummyType {}
abstract class setvalue005tAbsDummyType {}
final class setvalue005tFinDummyType {}
