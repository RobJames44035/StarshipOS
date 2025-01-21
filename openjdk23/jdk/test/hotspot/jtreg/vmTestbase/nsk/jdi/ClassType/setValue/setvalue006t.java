/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassType.setValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This is a debuggee class.
 */
public class setvalue006t {
    // dummy static fields used by debugger for testing
    static setvalue006tDummyType dummySFld;
    static setvalue006tFinDummyType finDummySFld;

    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        return new setvalue006t().runIt(args);
    }

    private int runIt(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        Log log = argHandler.createDebugeeLog();
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(setvalue006.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(setvalue006.COMMAND_QUIT)) {
            log.complain("TEST BUG: unknown debugger command: "
                + cmd);
            return Consts.TEST_FAILED;
        }
        return Consts.TEST_PASSED;
    }
}

// Dummy reference types used to provoke ClassNotLoadedException
// in the debugger
class setvalue006tDummyType {}
final class setvalue006tFinDummyType {}
