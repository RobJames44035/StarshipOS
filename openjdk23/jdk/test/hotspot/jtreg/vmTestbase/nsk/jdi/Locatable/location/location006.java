/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.Locatable.location;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import java.util.*;
import java.io.*;

/**
 * The test for the implementation of an object of the type     <BR>
 * Locatable.                                                   <BR>
 *                                                              <BR>
 * The test checks up that results of the method                <BR>
 * <code>com.sun.jdi.Locatable.location()</code>                <BR>
 * complies with its spec when a tested method is native.       <BR>
 * <BR>
 * The case for testing is as follows.                  <BR>
 *                                                      <BR>
 * When a gebuggee creates an object of                 <BR>
 * the following class type:                            <BR>
 *    class TestClass {                                 <BR>
 *        public      native void    nativeMethod ();   <BR>
 *   }                                                  <BR>
 * a debugger checks up that the invocation of          <BR>
 * the method Locatable.location() on the nativeMethod  <BR>
 * returns not the null.                                <BR>
 * <BR>
 */

public class location006 {

    //----------------------------------------------------- templete section
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //----------------------------------------------------- templete parameters
    static final String
    sHeader1 = "\n==> nsk/jdi/Locatable/location/location006  ",
    sHeader2 = "--> location006: ",
    sHeader3 = "##> location006: ";

    //----------------------------------------------------- main method

    public static void main (String argv[]) {
        int result = run(argv, System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run (String argv[], PrintStream out) {
        return new location006().runThis(argv, out);
    }

     //--------------------------------------------------   log procedures

    //private static boolean verbMode = false;

    private static Log  logHandler;

    private static void log1(String message) {
        logHandler.display(sHeader1 + message);
    }
    private static void log2(String message) {
        logHandler.display(sHeader2 + message);
    }
    private static void log3(String message) {
        logHandler.complain(sHeader3 + message);
    }

    //  ************************************************    test parameters

    private String debuggeeName =
        "nsk.jdi.Locatable.location.location006a";

    String mName = "nsk.jdi.Locatable.location";

    //====================================================== test program

    static ArgumentHandler      argsHandler;
    static int                  testExitCode = PASSED;

    //------------------------------------------------------ common section

    private int runThis (String argv[], PrintStream out) {

        Debugee debuggee;

        argsHandler     = new ArgumentHandler(argv);
        logHandler      = new Log(out, argsHandler);
        Binder binder   = new Binder(argsHandler, logHandler);


        if (argsHandler.verbose()) {
            debuggee = binder.bindToDebugee(debuggeeName + " -vbs");  // *** tp
        } else {
            debuggee = binder.bindToDebugee(debuggeeName);            // *** tp
        }

        IOPipe pipe     = new IOPipe(debuggee);

        debuggee.redirectStderr(out);
        log2("location006a debuggee launched");
        debuggee.resume();

        String line = pipe.readln();
        if ((line == null) || !line.equals("ready")) {
            log3("signal received is not 'ready' but: " + line);
            return FAILED;
        } else {
            log2("'ready' recieved");
        }

        VirtualMachine vm = debuggee.VM();

    //------------------------------------------------------  testing section
        log1("      TESTING BEGINS");

        for (int i = 0; ; i++) {
        pipe.println("newcheck");
            line = pipe.readln();

            if (line.equals("checkend")) {
                log2("     : returned string is 'checkend'");
                break ;
            } else if (!line.equals("checkready")) {
                log3("ERROR: returned string is not 'checkready'");
                testExitCode = FAILED;
                break ;
            }

            log1("new check: #" + i);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ variable part

            List listOfDebuggeeClasses = vm.classesByName(mName + ".location006aTestClass");
                if (listOfDebuggeeClasses.size() != 1) {
                    testExitCode = FAILED;
                    log3("ERROR: listOfDebuggeeClasses.size() != 1");
                    break ;
                }

            List     methods   = null;
            Method   m         = null;
            Location mLocation = null;

            methods = ((ReferenceType) listOfDebuggeeClasses.get(0)).
                      methodsByName("nativeMethod");
            m = (Method) methods.get(0);
            mLocation = m.location();

            if (mLocation == null) {
                log3("ERROR: mLocation == null for a native method");
                testExitCode = FAILED;
            }
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        }
        log1("      TESTING ENDS");

    //--------------------------------------------------   test summary section
    //-------------------------------------------------    standard end section

        pipe.println("quit");
        log2("waiting for the debuggee to finish ...");
        debuggee.waitFor();

        int status = debuggee.getStatus();
        if (status != PASSED + PASS_BASE) {
            log3("debuggee returned UNEXPECTED exit status: " +
                    status + " != PASS_BASE");
            testExitCode = FAILED;
        } else {
            log2("debuggee returned expected exit status: " +
                    status + " == PASS_BASE");
        }

        if (testExitCode != PASSED) {
            System.out.println("TEST FAILED");
        }
        return testExitCode;
    }
}
