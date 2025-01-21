/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.ReferenceType.allMethods;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debugee application for the allmethods004 JDI test.
 */

public class allmethods004a {

    static boolean verbose_mode = false;  // debugger may switch to true
                                          // - for more easy failure evaluation


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

        print_log_on_verbose("**> allmethods004a: debugee started!");
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        allmethods004aClassForCheck class_for_check = new allmethods004aClassForCheck();

        print_log_on_verbose("**> allmethods004a: waiting for \"quit\" signal...");
        pipe.println("ready");
        String instruction = pipe.readln();
        if (instruction.equals("quit")) {
            print_log_on_verbose("**> allmethods004a: \"quit\" signal recieved!");
            print_log_on_verbose("**> allmethods004a: completed succesfully!");
            System.exit(0/*STATUS_PASSED*/ + 95/*STATUS_TEMP*/);
        }
        System.err.println("##> allmethods004a: unexpected signal (no \"quit\") - " + instruction);
        System.err.println("##> allmethods004a: FAILED!");
        System.exit(2/*STATUS_FAILED*/ + 95/*STATUS_TEMP*/);
    }
}

class allmethods004aClassForCheck implements allmethods004aInterfaceForCheck {

}

interface allmethods004aInterfaceForCheck {
}
