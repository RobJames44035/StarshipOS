/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */
package nsk.jdi.Method.variables;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debuggee application for the variables001 JDI test.
 */

public class variables001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;  // debugger may switch to true

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**> variables001a: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> variables001a: " + message);
    }

    //====================================================== test program

    static variables001aTestClass obj = new variables001aTestClass();

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


class variables001aTestClass {

    public boolean bl () { return false; }
    public byte    bt () { return 0;     }
    public char    ch () { return 0;     }
    public double  db () { return 0.0d;  }
    public float   fl () { return 0.0f;  }
    public int     in () { return 0;     }
    public long    ln () { return 0;     }
    public short   sh () { return 0;     }

    public void vd () { return ; }

    public native void nvd ();

   public int primitiveargsmethod ( boolean bl,
                                   byte    bt,
                                   char    ch,
                                   double  db,
                                   float   fl,
                                   int     in,
                                   long    l,
                                   short   sh ) {
       int i1 = in;
       {
           int i2 = i1;
           i1 = i2;
       }
       {
           int i2 = i1;
           int i3 = i2;
           i1 = i2;
       }
       return i1;
   }

}
