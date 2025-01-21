/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.TypeComponent.isStatic;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * The debugged application of the test.
 */
public class isstatic004a {

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

    static isstatic004Enum1 f1 = isstatic004Enum1.e2;
    static isstatic004Enum2 f2 = isstatic004Enum2.e1;
    static isstatic004Enum1.Enum1_ f3 = isstatic004Enum1.Enum1_.e1;

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {
        exitStatus = Consts.TEST_FAILED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println(isstatic004.SIGNAL_READY);


        //pipe.println(isstatic004.SIGNAL_GO);
        receiveSignal(isstatic004.SIGNAL_QUIT);

        display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    //--------------------------------------------------------- test specific inner classes

}

//--------------------------------------------------------- test specific classes

enum isstatic004Enum1 {
    e1, e2;

    enum Enum1_ {
        e1, e2;
    }
}

enum isstatic004Enum2 {
    e1 {
       int val() {return 1;}
    },

    e2 {
       int val() {return 2;}
    };
    abstract int val();
}
