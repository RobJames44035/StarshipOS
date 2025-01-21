/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.Method.argumentTypeNames;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import java.util.*;
import java.io.*;

/**
 * The test for the implementation of an object of the type     <BR>
 * Method.                                                      <BR>
 *                                                              <BR>
 * The test checks up that results of the method                <BR>
 * <code>com.sun.jdi.Method.argumentTypeNames()</code>          <BR>
 * complies with its spec when a type is one of PrimitiveTypes. <BR>
 * <BR>
 * The cases for testing are as follows.                <BR>
 *                                                      <BR>
 * When a gebuggee creates an object of                 <BR>
 * the following class type:                                                    <BR>
 *    class TestClass {                                                         <BR>
 *              .                                                               <BR>
 *              .                                                               <BR>
 *       public ClassForCheck[] arrayargmethod (ClassForCheck[] cfc) {          <BR>
 *           return cfc;                                                        <BR>
 *       }                                                                      <BR>
 *       public ClassForCheck classargmethod (ClassForCheck classFC) {          <BR>
 *           return classFC;                                                    <BR>
 *       }                                                                      <BR>
 *       public InterfaceForCheck ifaceargmethod (InterfaceForCheck iface) {    <BR>
 *           return iface;                                                      <BR>
 *       }                                                                      <BR>
 *    }                                                                         <BR>                                                    <BR>
 * for all of the above ReferenceType arguments,        <BR>
 * a debugger forms text strings with corresponding     <BR>
 * types, that is "ClassForCheck[]", "ClassForCheck",   <BR>
 * and "InterfaceForCheck".                             <BR>
 * <BR>
 */

public class argumenttypenames002 {

    //----------------------------------------------------- templete section
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //----------------------------------------------------- templete parameters
    static final String
    sHeader1 = "\n==> nsk/jdi/Method/argumentTypeNames/argumenttypenames002",
    sHeader2 = "--> argumenttypenames002: ",
    sHeader3 = "##> argumenttypenames002: ";

    //----------------------------------------------------- main method

    public static void main (String argv[]) {
        int result = run(argv, System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run (String argv[], PrintStream out) {
        return new argumenttypenames002().runThis(argv, out);
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
        "nsk.jdi.Method.argumentTypeNames.argumenttypenames002a";

    String mName = "nsk.jdi.Method.argumentTypeNames";

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
        log2("argumenttypenames002a debuggee launched");
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

            List listOfDebuggeeClasses = vm.classesByName(mName + ".argumenttypenames002aTestClass");
                if (listOfDebuggeeClasses.size() != 1) {
                    testExitCode = FAILED;
                    log3("ERROR: listOfDebuggeeClasses.size() != 1");
                    break ;
                }

            List   methods  = null;
            Method m        = null;

            List   argTypeNames = null;

            int i2;

            for (i2 = 0; ; i2++) {

                int expresult = 0;

                log2("new check: #" + i2);

                switch (i2) {

                case 0:                 // array arg

                        methods = ((ReferenceType) listOfDebuggeeClasses.get(0)).
                                      methodsByName("arrayargmethod");
                        m = (Method) methods.get(0);
                        argTypeNames = m.argumentTypeNames();

                        if (!argTypeNames.contains(mName + ".argumenttypenames002aClassForCheck1[]")) {
                            log3("ERROR: !argTypeNames.contains(mName + '.argumenttypenames002aClassForCheck1[]'");
                            expresult = 1;
                            break;
                        }
                        break;


                case 1:                 // class arg

                        methods = ((ReferenceType) listOfDebuggeeClasses.get(0)).
                                      methodsByName("classargmethod");
                        m = (Method) methods.get(0);
                        argTypeNames = m.argumentTypeNames();

                        if (!argTypeNames.contains(mName + ".argumenttypenames002aClassForCheck1")) {
                            log3("ERROR: !argTypeNames.contains(mName + '.argumenttypenames002aClassForCheck1'");
                            expresult = 1;
                            break;
                        }
                        break;

                case 2:                 // interface arg

                        methods = ((ReferenceType) listOfDebuggeeClasses.get(0)).
                                      methodsByName("ifaceargmethod");
                        m = (Method) methods.get(0);
                        argTypeNames = m.argumentTypeNames();

                        if (!argTypeNames.contains(mName + ".argumenttypenames002aIntfForCheck")) {
                            log3("ERROR: !argTypeNames.contains(mName + '.argumenttypenames002aIntfForCheck'");
                            expresult = 1;
                            break;
                        }
                        break;


                default: expresult = 2;
                         break ;
                }

                if (expresult == 2) {
                    log2("      test cases finished");
                    break ;
                } else if (expresult == 1) {
                    log3("ERROR: expresult != true;  check # = " + i);
                    testExitCode = FAILED;
                }
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
            logHandler.complain("TEST FAILED");
        }
        return testExitCode;
    }
}
