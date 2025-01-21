/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.ReferenceType.isPrepared;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debugee application for the isprepared001 JDI test.
 */

public class isprepared001a {

    static boolean verbose_mode = false;  // debugger may switch to true
                                          // - for more easy failure evaluation

    NotPreparedClass not_prepared_class_0, not_prepared_class_1[] = {not_prepared_class_0};

    NotPreparedInterface not_prepared_interface_0, not_prepared_interface_1[] = {not_prepared_interface_0};

    PreparedClass  prepared_class_0 = new PreparedClass();

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

        print_log_on_verbose("**> isprepared001a: debugee started!");
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        isprepared001a isprepared001a_obj = new isprepared001a();

        print_log_on_verbose("**> isprepared001a: waiting for \"quit\" signal...");
        pipe.println("ready");
        String instruction = pipe.readln();
        if (instruction.equals("quit")) {
            print_log_on_verbose("**> isprepared001a: \"quit\" signal recieved!");
            print_log_on_verbose("**> isprepared001a: completed succesfully!");
            System.exit(0/*STATUS_PASSED*/ + 95/*STATUS_TEMP*/);
        }
        System.err.println("!!**> isprepared001a: unexpected signal (no \"quit\") - " + instruction);
        System.err.println("!!**> isprepared001a: FAILED!");
        System.exit(2/*STATUS_FAILED*/ + 95/*STATUS_TEMP*/);
    }
}

// not prepared class
class NotPreparedClass {}

// not prepared interface
interface NotPreparedInterface {}


// prepared interface
interface PreparedInterface {
    static final int int_var = 1;
}

// prepared class
class PreparedClass implements PreparedInterface {
    static int my_int_var = int_var;

}
