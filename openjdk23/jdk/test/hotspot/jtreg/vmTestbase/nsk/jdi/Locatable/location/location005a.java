/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.Locatable.location;

import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debuggee application for the location005 JDI test.
 */

public class location005a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;

    public static void log1(String message) {
        if (verbMode)
            System.err.println("**> mainThread: " + message);
    }
    public static void log2(String message) {
        if (verbMode)
            System.err.println("**> " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> mainThread: " + message);
    }

    //====================================================== test program
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
                         Thread thread2 =
                             JDIThreadFactory.newThread(new Threadlocation005a("Thread2"));
                         log1("       thread2 is created");

                         label:
                         synchronized (Threadlocation005a.lockingObject) {
                             synchronized (Threadlocation005a.waitnotifyObj) {
                                 log1("       synchronized (waitnotifyObj) { enter");
                                 log1("       before: thread2.start()");
                                 thread2.start();

                                 try {
                                     log1("       before:   waitnotifyObj.wait();");
                                     Threadlocation005a.waitnotifyObj.wait();
                                     log1("       after:    waitnotifyObj.wait();");
                                     pipe.println("checkready");
                                     instruction = pipe.readln();
                                     if (!instruction.equals("continue")) {
                                         logErr("ERROR: unexpected instruction: " + instruction);
                                         exitCode = FAILED;
                                         break label;
                                     }
                                     pipe.println("docontinue");
                                 } catch ( Exception e2) {
                                     log1("       Exception e2 exception: " + e2 );
                                     pipe.println("waitnotifyerr");
                                 }
                             }
                         }
                         log1("mainThread is out of: synchronized (lockingObject) {");

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

class Threadlocation005a extends NamedTask {

    public Threadlocation005a(String threadName) {
        super(threadName);
    }

    public static Object waitnotifyObj = new Object();
    public static Object lockingObject = new Object();

    private int i1 = 0, i2 = 10;

    public void run() {
        log("method 'run' enter");
        synchronized (waitnotifyObj)                                    {
            log("entered into block:  synchronized (waitnotifyObj)");
            waitnotifyObj.notify();                                     }
        log("exited from block:  synchronized (waitnotifyObj)");
        synchronized (lockingObject)                                    {
            log("entered into block:  synchronized (lockingObject)");   }
        log("exited from block:  synchronized (lockingObject)");
        i1++;
        i2--;
        log("call to 'runt1'");
        runt1();
        log("returned from 'runt1'");
        log("method 'run' exit");
        return;
    }

    public void runt1() {

        log("method 'runt1' enter");
        i1++;
        i2--;
        log("method 'runt1' exit");

        return;
    }

    public static final int breakpointLineNumber1 = 3;


    static void log(String str) {
        location005a.log2("thread2: " + str);
    }

}
