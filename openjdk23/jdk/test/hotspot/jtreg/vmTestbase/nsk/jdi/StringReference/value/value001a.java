/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.StringReference.value;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.io.*;

/**
 * This class is used as debuggee application for the value001a JDI test.
 */

public class value001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;  // debugger may switch to true

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**> value001a: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> value001a: " + message);
    }

    //====================================================== test program
    //................................................... globals for a debugger

    static ClassForCheck class1 = null;

    //....................................................
    //----------------------------------------------------   main method

    public static void main (String argv[]) {

        int result = new value001a().runThis(argv, System.out);

        System.exit(result + PASS_BASE);
    }


    private static int runThis (String argv[], PrintStream out) {

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

            if (exitCode == FAILED) break ;

            log1("waiting for an instruction from the debugger ...");
            instruction = pipe.readln();
            if (instruction.equals("quit")) {
                log1("'quit' recieved");
                break ;

            } else if (instruction.equals("newcheck")) {
                switch (i) {

    //------------------------------------------------------  section tested

                case 0: class1 = new ClassForCheck();
                        pipe.println("checkready");
                        instruction = pipe.readln();
                        if (!instruction.equals("continue")){
                            logErr("ERRROR: unexpected instruction: " +
                                   instruction);
                            exitCode = FAILED;
                        } else {
                            class1 = null;
                            pipe.println("docontinue");
                        }
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

        return exitCode;
    }
}


class ClassForCheck {

static String str = "abc";

}
