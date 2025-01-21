/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.Accessible.modifiers;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * The debugged application of the test.
 */
public class modifiers002a {

    //------------------------------------------------------- immutable common fields

    private static int exitStatus;
    private static ArgumentHandler argHandler;
    private static Log log;
    private static IOPipe pipe;

    //------------------------------------------------------- immutable common methods

    static void display(String msg) {
        log.display("debuggee > " + msg);
    }

    static void complain(String msg) {
        log.complain("debuggee FAILURE > " + msg);
    }

    public static void receiveSignal(String signal) {
        String line = pipe.readln();

        if ( !line.equals(signal) )
            throw new Failure("UNEXPECTED debugger's signal " + line);

        display("debugger's <" + signal + "> signal received.");
    }

    //------------------------------------------------------ mutable common fields

    //------------------------------------------------------ test specific fields

    static Enum1 f1 = Enum1.e1;
    static Enum2 f2 = Enum2.e2;
    static Enum3 f3 = Enum3.e1;

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {
        exitStatus = Consts.TEST_FAILED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println(modifiers002.SIGNAL_READY);
        receiveSignal(modifiers002.SIGNAL_QUIT);

        display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    //--------------------------------------------------------- test specific inner classes

    enum Enum1 {
        e1, e2;
    }

    public enum Enum2 {
       e1, e2;
    }

    enum Enum3 {
        e1 {
           int val() {return 1;}
        },

        e2 {
           int val() {return 2;}
        };
        abstract int val();
    }
}

//--------------------------------------------------------- test specific classes
