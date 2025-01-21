/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ClassLoaderReference.definedClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.net.URLClassLoader;
import java.net.URL;

/**
 * This class is used as debuggee application for the definedclasses001 JDI test.
 */

public class definedclasses001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    static ArgumentHandler argHandler;
    static Log log;

    //--------------------------------------------------   log procedures

    private static void log1(String message) {
        log.display("**> debuggee: " + message);
    }

    private static void logErr(String message) {
        log.complain("**> debuggee: " + message);
    }

    //====================================================== test program

     static ClassLoader classLoader = null;

     static String urlString = "http://download.java.net/java/jdk10/docs/api/overview-summary.html";

    //------------------------------------------------------ common section
    static int instruction = 1;
    static int end         = 0;
                                   //    static int quit        = 0;
                                   //    static int continue    = 2;
    static int maxInstr    = 1;    // 2;

    static int lineForComm = 2;

    private static void methodForCommunication() {
        int i1 = instruction;
        int i2 = i1;
        int i3 = i2;
    }
    //----------------------------------------------------   main method

    public static void main (String argv[]) {

        argHandler = new ArgumentHandler(argv);
        log = argHandler.createDebugeeLog();

        log1("debuggee started!");

        int exitCode = PASSED;


        label0:
            for (int i = 0; ; i++) {

                if (instruction > maxInstr) {
                    logErr("ERROR: unexpected instruction: " + instruction);
                    exitCode = FAILED;
                    break ;
                }

                switch (i) {

    //------------------------------------------------------  section tested

                    case 0:
                                try {
                                    URL[] url = { new URL(urlString) } ;
                                    classLoader = new URLClassLoader(url);
                                } catch ( Exception e ) {
                                    log1("Exception : " + e);
                                }

                                methodForCommunication();
                                break ;

    //-------------------------------------------------    standard end section

                    default:
                                instruction = end;
                                methodForCommunication();
                                break label0;
                }
            }

        System.exit(exitCode + PASS_BASE);
    }
}
