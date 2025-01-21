/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.StackFrame.setValue.setvalue005;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * This is a debuggee class.
 */
public class setvalue005t {
    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        return new setvalue005t().runIt(args);
    }

    private int runIt(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        Log log = argHandler.createDebugeeLog();
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        Thread.currentThread().setName(setvalue005.DEBUGGEE_THRDNAME);

        // dummy local var used by debugger for stack frame searching
        int setvalue005tFindMe = 0;

        // dummy local vars used by debugger for testing
        DummyType dummyVar = null;
        FinDummyType finDummyVar = null;

        // Now the debuggee is ready
        pipe.println(setvalue005.COMMAND_READY);
        String cmd = pipe.readln();
        if (cmd.equals(setvalue005.COMMAND_QUIT)) {
            log.complain("Debuggee: exiting due to the command "
                    + cmd);
            return Consts.TEST_PASSED;
        }

        int stopMeHere = 0; // setvalue005.DEBUGGEE_STOPATLINE

        cmd = pipe.readln();
        if (!cmd.equals(setvalue005.COMMAND_QUIT)) {
            log.complain("TEST BUG: unknown debugger command: "
                + cmd);
            return Consts.TEST_FAILED;
        }
        return Consts.TEST_PASSED;
    }
}

// Dummy reference types used to provoke ClassNotLoadedException
// in the debugger
class DummyType {}
final class FinDummyType {}
