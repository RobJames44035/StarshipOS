/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.ReferenceType.methods;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debugee application for the methods004 JDI test.
 */

public class methods004a {

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

        print_log_on_verbose("**> methods004a: debugee started!");
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        methods004aClassForCheck class_for_check = new methods004aClassForCheck();

        print_log_on_verbose("**> methods004a: waiting for \"quit\" signal...");
        pipe.println("ready");
        String instruction = pipe.readln();
        if (instruction.equals("quit")) {
            print_log_on_verbose("**> methods004a: \"quit\" signal recieved!");
            print_log_on_verbose("**> methods004a: completed succesfully!");
            System.exit(0/*STATUS_PASSED*/ + 95/*STATUS_TEMP*/);
        }
        System.err.println("##> methods004a: unexpected signal (no \"quit\") - " + instruction);
        System.err.println("##> methods004a: FAILED!");
        System.exit(2/*STATUS_FAILED*/ + 95/*STATUS_TEMP*/);
    }
}

class methods004aClassForCheck implements methods004aInterfaceForCheck {

    public void    i_interf_void_method(long l) {}
    public long    i_interf_prim_method(long l) {return l;}
    public Object  i_interf_ref_method(Object obj) {return obj;}
}

interface methods004aInterfaceForCheck extends methods004aSuperInterfaceForCheck{
}

interface methods004aSuperInterfaceForCheck {
    void    i_interf_void_method(long l);
    long    i_interf_prim_method(long l);
    Object  i_interf_ref_method(Object obj);
}
