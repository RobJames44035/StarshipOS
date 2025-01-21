/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdwp.Method.IsObsolete;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class isobsolete002a {

    // scaffold objects
    static volatile ArgumentHandler argumentHandler = null;
    static volatile Log log = null;

    // breakpoint line in isobsolete002b
    static final int BREAKPOINT_LINE = 44;

    public static void main(String args[]) {
        System.exit(isobsolete002.JCK_STATUS_BASE + isobsolete002a.runIt(args, System.err));
    }

    public static int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // create tested thread
        log.display("Creating object of tested class");
        isobsolete002b.log = log;
        isobsolete002b object = new isobsolete002b();
        log.display("  ... object created");

        log.display("Invoking tested method before class redefinition");
        object.testedMethod(100);
        log.display("  ... tested method invoked");

        log.display("Invoking tested method after class redefinition");
        object.testedMethod(100);
        log.display("  ... tested method invoked");

        // exit
        log.display("Debugee PASSED");
        return isobsolete002.PASSED;
    }
}
