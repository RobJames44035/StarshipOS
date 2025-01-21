/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.ExceptionEvent._itself_;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class exevent005t {
    public static void main(String args[]) {
        exevent005t _exevent005t = new exevent005t();
        System.exit(exevent005.JCK_STATUS_BASE + _exevent005t.communication(args));
    }

    private static void raiseArithmeticException() {
        int foo = 10;
        foo = foo / 0;
    }

    int communication(String args[]) throws ArithmeticException {
        String command;
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(exevent005.COMMAND_READY);
        command = pipe.readln();
        if (command.equals(exevent005.COMMAND_RUN)) {
            raiseArithmeticException();
            return exevent005.PASSED;
        } else
            return exevent005.FAILED;
    }
}
