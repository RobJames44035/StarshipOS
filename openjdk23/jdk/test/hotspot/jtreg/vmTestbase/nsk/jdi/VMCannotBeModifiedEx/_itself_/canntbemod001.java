/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.VMCannotBeModifiedEx._itself_;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;

import java.io.*;

/**
 * The test checks up <t>com.sun.jdi.VMCannotBeModifiedException</t>.
 * It creates, throws, catches and inspects the exception using each
 * of two public constructors.
 */

public class canntbemod001 {

    private static int exitStatus;
    private static Log log;

    private static void display(String msg) {
        log.display(msg);
    }

    private static void complain(String msg) {
        log.complain(msg + "\n");
    }

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {

        canntbemod001 thisTest = new canntbemod001();

        log = new Log(out, new ArgumentHandler(argv));

        thisTest.execTest();

        display("Test finished. exitStatus = " + exitStatus);

        return exitStatus;
    }

    private void execTest() throws Failure {

        exitStatus = Consts.TEST_PASSED;
        display("\nTEST BEGINS");
        display("===========");

        boolean isThrown = false;
        try {
            throwException();
        } catch (VMCannotBeModifiedException e) {
            isThrown = true;
            display("VMCannnotBeModifiedException was caught: " + e);
        }
        if (!isThrown) {
            exitStatus = Consts.TEST_FAILED;
            complain("VMCannnotBeModifiedException was NOT thrown");
        }

        display("");
        isThrown = false;
        try {
            throwException("message");
        } catch (VMCannotBeModifiedException e) {
            isThrown = true;
            display("VMCannnotBeModifiedException  was caught: " + e);
        }
        if (!isThrown) {
            exitStatus = Consts.TEST_FAILED;
            complain("VMCannnotBeModifiedException was NOT thrown");
        }
        display("=============");
        display("TEST FINISHES\n");
    }

    private void throwException() {
        display("throwing VMCannotBeModifiedException()");
        throw new VMCannotBeModifiedException();
    }

    private void throwException(String msg) {
        display("throwing VMCannotBeModifiedException(msg)");
        throw new VMCannotBeModifiedException(msg);
    }
}
