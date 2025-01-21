/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.Location.method;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * This class is used as debuggee application for the method001 JDI test.
 */

public class method001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;

    public static void log1(String message) {
        if (verbMode)
            System.err.println("**> method001a: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> method001a: " + message);
    }

    //====================================================== test program

    static TestClass obj = new TestClass();

    //----------------------------------------------------   main method

    public static void main (String argv[]) {

        for (int i=0; i<argv.length; i++) {
            if ( argv[i].equals("-vbs") || argv[i].equals("-verbose") ) {
                verbMode = true;
                break;
            }
        }
        log1("debuggee started!");

        // informing a debugger of readyness
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        pipe.println("ready");


        int exitCode = PASSED;
        for (int i = 0; ; i++) {

            String instruction;

            log1("waiting for an instruction from the debugger ...");
            instruction = pipe.readln();
            if (instruction.equals("quit")) {
                log1("'quit' recieved");
                break ;

            } else if (instruction.equals("newcheck")) {
                switch (i) {

    //------------------------------------------------------  section tested

                case 0:
                                pipe.println("checkready");
                                break ;

    //-------------------------------------------------    standard end section

                default:
                                pipe.println("checkend");
                                break ;
                }

            } else {
                logErr("ERRROR: unexpected instruction: " + instruction);
                exitCode = FAILED;
                break ;
            }
        }

        System.exit(exitCode + PASS_BASE);
    }
}


class TestClass extends Object {

    public TestClass() {
        super();
    }

    public  void testMethod (int param) {

        boolean bl1 = false, bl2 = true;
        byte    bt1 = 0,     bt2 = 1;
        char    ch1 = 0,     ch2 = 1;

        return;
    }

    static String s0 = Integer.toString(123);

//    public native void nativeMethod();

//    public abstract void abstractMethod();

/*
    String s1 = Integer.toString(456);
    String s2 = Integer.toString(789);
    String s3 = Integer.toString(321);
    String s4 = Integer.toString(654);
    String s5 = Integer.toString(987);
*/
}
