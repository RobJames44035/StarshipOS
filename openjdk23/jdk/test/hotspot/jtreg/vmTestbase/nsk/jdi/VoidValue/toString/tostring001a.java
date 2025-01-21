/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VoidValue.toString;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * The debugged application of the test.
 */
public class tostring001a {

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

        display("debuger's <" + signal + "> signal received.");
    }

    //------------------------------------------------------ mutable common fields

    //------------------------------------------------------ test specific fields

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 81;

    //------------------------------------------------------ mutable common method

    public static void main (String argv[]) {
        exitStatus = Consts.TEST_FAILED;
        argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);
        try {
            pipe.println(tostring001.SIGNAL_READY);
            receiveSignal(tostring001.SIGNAL_GO);
            log.display("breakpoint line");
            receiveSignal(tostring001.SIGNAL_QUIT); // brkpLineNumber
            display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        } catch (Failure e) {
            log.complain(e.getMessage());
            System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
        }
    }

    //--------------------------------------------------------- test specific methods

    void Mv() {};

    static              void MvS() {};
    strictfp            void MvI() {};
    synchronized        void MvY() {};
    public              void MvU() {};
    protected           void MvR() {};
    private             void MvP() {};

    public static       void MvSM() {};
    private strictfp    void MvIM() {};
    static synchronized void MvYM() {};
    public final        void MvPM() {};

}

//--------------------------------------------------------- test specific classes
