/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */


package nsk.jdi.TypeComponent.signature;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class sign003a {
    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        sign003aClassToCheck classToCheck = new sign003aClassToCheck();

        log.display("DEBUGEE> debugee started.");
        pipe.println("ready");
        String instruction = pipe.readln();
        if (instruction.equals("quit")) {
            log.display("DEBUGEE> \"quit\" signal recieved.");
            log.display("DEBUGEE> completed succesfully.");
            System.exit(95);
        }
        log.complain("DEBUGEE FAILURE> unexpected signal "
                         + "(no \"quit\") - " + instruction);
        log.complain("DEBUGEE FAILURE> TEST FAILED");
        System.exit(97);
    }
}

class sign003aClassToCheck {
              sign003aClassToCheck()                                       {};
              sign003aClassToCheck(int i, double d)                        {};
              sign003aClassToCheck(int[] i, double[] d)                    {};
              sign003aClassToCheck(int[][] i, double[][] d)                {};
              sign003aClassToCheck(Long L, String S, Object O)             {};
              sign003aClassToCheck(Long[] L, String[] S, Object[] O)       {};
              sign003aClassToCheck(Long[][] L, String[][] S, Object[][] O) {};
    public    sign003aClassToCheck(long l)                                 {};
    private   sign003aClassToCheck(Object obj)                             {};
    protected sign003aClassToCheck(long[] l)                               {};

    static boolean sb;
    static Object sO;
    static float[] sf = new float[10];

    static { sb = true; }
    static { sO = new Object(); }
    static {
        for (int i = 0; i < 10; i++) {
            sf[i] = (float)i;
        }
    }
}
