/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.TypeComponent.declaringType;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * The debugged application of the test.
 */
public class decltype009a {

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

    static decltype009Enum1 f1 = decltype009Enum1.e2;
    static decltype009Enum2 f2 = decltype009Enum2.e1;
    static decltype009Enum1.Enum1_ f3 = decltype009Enum1.Enum1_.e1;

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {
        exitStatus = Consts.TEST_FAILED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println(decltype009.SIGNAL_READY);
        receiveSignal(decltype009.SIGNAL_QUIT);

        display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    //--------------------------------------------------------- test specific inner classes

}

//--------------------------------------------------------- test specific classes

enum decltype009Enum1 {
    e1, e2;

    enum Enum1_ {
        e1, e2;
    }
}

enum decltype009Enum2 {
    e1 {
       int val() {return 1;}
    },

    e2 {
       int val() {return 2;}
    };
    abstract int val();
}
