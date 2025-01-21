/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ObjectReference.entryCount;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * The debugged application of the test.
 */
public class entrycount002a {

    //----------------------------------------------------- immutable common fields

    static final int PASSED    = 0;
    static final int FAILED    = 2;
    static final int PASS_BASE = 95;
    static final int quit      = -1;

    static int instruction = 1;
    static int lineForComm = 2;
    static int exitCode    = PASSED;

    private static ArgumentHandler argHandler;
    private static Log log;

    //---------------------------------------------------------- immutable common methods

    static void display(String msg) {
        log.display("debuggee > " + msg);
    }

    static void complain(String msg) {
        log.complain("debuggee FAILURE > " + msg);
    }

    private static void methodForCommunication() {
        int i = instruction; // entrycount002.lineForBreak
        int curInstruction = i;
    }

    //------------------------------------------------------ mutable common fields

    //------------------------------------------------------ test specific fields

    static entrycount002aLock lockObj = new entrycount002aLock();
    static int levelMax = 10;

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {

        argHandler = new ArgumentHandler(argv);
        log = argHandler.createDebugeeLog();

        display("debuggee started!");

        label0:
        for (int testCase = 0; instruction != quit; testCase++) {

            switch (testCase) {
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ test case section
                case 0:
                    display("call methodForCommunication() #0");
                    methodForCommunication();

                    lockObj.foo(levelMax);
                    break;
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ end of section

                default:
                    instruction = quit;
                    break;
            }

            display("call methodForCommunication() #1");
            methodForCommunication();
            if (instruction == quit)
                break;
        }

        display("debuggee exits");
        System.exit(PASSED + PASS_BASE);
    }

    //--------------------------------------------------------- test specific methodss

}

//--------------------------------------------------------- test specific classes

class entrycount002aLock {
    synchronized void foo (int level) {
        if (level <= 0) {
           return;
        }
        level--;
        entrycount002a.display("Calling foo with level : " + level);
        foo(level);
    }
}
