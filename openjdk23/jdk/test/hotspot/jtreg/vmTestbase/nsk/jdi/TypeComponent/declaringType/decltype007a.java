/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */


package nsk.jdi.TypeComponent.declaringType;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class decltype007a {
    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);
        decltype007aMainClass mainClass = new decltype007aMainClass();

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

class decltype007aMainClass {
              decltype007aMainClass()           {};
    public    decltype007aMainClass(long l)     {};
    private   decltype007aMainClass(Object obj) {};
    protected decltype007aMainClass(long[] l)   {};

    static int ci;
    static Long cL;
    static long[] cl = new long[10];

    static { ci = 1; }
    static { cL = Long.valueOf(1l); }
    static {
        for (int i = 0; i < 10; i++) {
            cl[i] = (long)i;
        }
    }
}
