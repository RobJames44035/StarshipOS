/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.ClassType.isEnum;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * The debugged application of the test.
 */
public class isenum001a {

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
    static Enum4.Enum4_ f4 = Enum4.Enum4_.e1;
    static isenum001Enum5 f5 = isenum001Enum5.e2;
    static isenum001Enum6 f6 = isenum001Enum6.e1;
    static isenum001Enum7 f7 = isenum001Enum7.e2;
    static isenum001Enum8.Enum8_ f8 = isenum001Enum8.Enum8_.e1;

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {
        exitStatus = Consts.TEST_FAILED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);

        pipe.println(isenum001.SIGNAL_READY);


        //pipe.println(isenum001.SIGNAL_GO);
        receiveSignal(isenum001.SIGNAL_QUIT);

        display("completed succesfully.");
        System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
    }

    //--------------------------------------------------------- test specific inner classes

    enum Enum1 {
        e1, e2;
    }

    enum Enum2 {
        e1 {
           int val() {return 1;}
        },

        e2 {
           int val() {return 2;}
        };
        abstract int val();
    }

    enum Enum3 implements isenum001i {
        e1 {
           int val() {return i+1;}
        },

        e2 {
           int val() {return i+2;}
        };
        abstract int val();
    }

    enum Enum4 {
       e1, e2;

       enum Enum4_ {
           e1, e2;
       }
    }

}

//--------------------------------------------------------- test specific classes

enum isenum001Enum5 {
    e1, e2;
}

enum isenum001Enum6 {
    e1 {
       int val() {return 1;}
    },

    e2 {
       int val() {return 2;}
    };
    abstract int val();
}

enum isenum001Enum7 implements isenum001i {
    e1 {
       int val() {return i+1;}
    },

    e2 {
       int val() {return i+2;}
    };
    abstract int val();
}

enum isenum001Enum8 {
   e1, e2;
   enum Enum8_ {
       e1, e2;
   }
}

interface isenum001i {
    int i = 1;
}
