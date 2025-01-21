/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.ReferenceType.sourceName;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debugee application for the sourcename003 JDI test.
 */

public class sourcename003a {

    static boolean verbose_mode = false;  // debugger may switch to true
                                          // - for more easy failure evaluation

    sourcename003 sourcename003_0, sourcename003_1[]={sourcename003_0};

    private static void print_log_on_verbose(String message) {
        if ( verbose_mode ) {
            System.err.println(message);
        }
    }

    public static void main (String argv[]) {

        for (int i=0; i<argv.length; i++) {
            if ( argv[i].equals("-vbs") || argv[i].equals("-verbose") ) {
                verbose_mode = true;
                break;
            }
        }

        print_log_on_verbose("**> sourcename003a: debugee started!");
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        sourcename003a sourcename003a_obj = new sourcename003a();

        print_log_on_verbose("**> sourcename003a: waiting for \"quit\" signal...");
        pipe.println("ready");
        String instruction = pipe.readln();
        if (instruction.equals("quit")) {
            print_log_on_verbose("**> sourcename003a: \"quit\" signal recieved!");
            print_log_on_verbose("**> sourcename003a: completed succesfully!");
            System.exit(0/*STATUS_PASSED*/ + 95/*STATUS_TEMP*/);
        }
        System.err.println("!!**> sourcename003a: unexpected signal (no \"quit\") - " + instruction);
        System.err.println("!!**> sourcename003a: FAILED!");
        System.exit(2/*STATUS_FAILED*/ + 95/*STATUS_TEMP*/);
    }
}
