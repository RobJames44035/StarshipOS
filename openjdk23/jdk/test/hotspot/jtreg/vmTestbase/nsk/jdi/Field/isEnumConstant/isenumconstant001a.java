/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.Field.isEnumConstant;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * The debugged application of the test.
 */
public class isenumconstant001a {

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

    static isenumconstant001Enum1 f1 = isenumconstant001Enum1.e2;
    static isenumconstant001Enum2 f2 = isenumconstant001Enum2.e1;
    static isenumconstant001Enum3 f3 = isenumconstant001Enum3.e1;
    static isenumconstant001Enum1.Enum1_ f4 = isenumconstant001Enum1.Enum1_.e1;

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {
        exitStatus = Consts.TEST_FAILED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println(isenumconstant001.SIGNAL_READY);


        //pipe.println(isenumconstant001.SIGNAL_GO);
        receiveSignal(isenumconstant001.SIGNAL_QUIT);

        display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    //--------------------------------------------------------- test specific inner classes

}

//--------------------------------------------------------- test specific classes

enum isenumconstant001Enum1 {
    e1, e2;

    enum Enum1_ {
        e1, e2;
    }
}

enum isenumconstant001Enum2 {
    e1 {
       int val() {return 1;}
    },

    e2 {
       int val() {return 2;}
    };
    abstract int val();
}

enum isenumconstant001Enum3 {
    e1(1), e2(2);

    int ii;

    isenumconstant001Enum3 (int i) {
        ii = i;
    }
}
