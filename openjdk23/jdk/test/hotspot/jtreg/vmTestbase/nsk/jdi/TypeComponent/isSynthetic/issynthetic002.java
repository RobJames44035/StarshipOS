/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */


package nsk.jdi.TypeComponent.isSynthetic;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import java.util.*;
import java.io.*;

public class issynthetic002 {


    final static String SYNTHETIC_METHOD_NAME = "test";
    final static String SYNTHETIC_METHOD_SIGNATURE = "(Ljava/lang/Object;)Ljava/lang/Object;";

    private static Log log;
    private final static String prefix = "nsk.jdi.TypeComponent.isSynthetic.";
    private final static String className = "issynthetic002";
    private final static String debuggerName = prefix + className;
    private final static String debuggeeName = debuggerName + "a";
    private final static String classToCheckName = prefix + "issynthetic002aClassToCheck";

    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        Binder binder = new Binder(argHandler, log);
        Debugee debuggee = binder.bindToDebugee(debuggeeName
                              + (argHandler.verbose() ? " -verbose" : ""));
        VirtualMachine vm = debuggee.VM();
        boolean canGetSynthetic = vm.canGetSyntheticAttribute();
        IOPipe pipe = new IOPipe(debuggee);
        boolean testFailed = false;
        List methods;
        int totalSyntheticMethods = 0;

        log.display("debugger> Value of canGetSyntheticAttribute in current "
                  + "VM is " + canGetSynthetic);

        // Connect with debuggee and resume it
        debuggee.redirectStderr(out);
        debuggee.resume();
        String line = pipe.readln();
        if (line == null) {
            log.complain("debugger FAILURE> UNEXPECTED debuggee's signal - null");
            return 2;
        }
        if (!line.equals("ready")) {
            log.complain("debugger FAILURE> UNEXPECTED debuggee's signal - "
                      + line);
            return 2;
        }
        else {
            log.display("debugger> debuggee's \"ready\" signal received.");
        }

        ReferenceType refType = debuggee.classByName(classToCheckName);
        if (refType == null) {
            log.complain("debugger FAILURE> Class " + classToCheckName
                       + " not found.");
            return 2;
        }

        // Check methods from debuggee
        try {
            methods = refType.methods();
        } catch (Exception e) {
            log.complain("debugger FAILURE> Can't get methods from "
                       + classToCheckName);
            log.complain("debugger FAILURE> Exception: " + e);
            return 2;
        }
        int totalMethods = methods.size();
        if (totalMethods < 1) {
            log.complain("debugger FAILURE> Total number of methods in debuggee "
                       + "read " + totalMethods);
            return 2;
        }
        log.display("debugger> Total methods in debuggee read: "
                  + totalMethods);
        for (int i = 0; i < totalMethods; i++) {
            Method method = (Method)methods.get(i);
            String name = method.name();
            String signature = method.signature();
            boolean isSynthetic;

            try {
                isSynthetic = method.isSynthetic();

                if (!canGetSynthetic) {
                    log.complain("debugger FAILURE 1> Value of "
                               + "canGetSyntheticAttribute in current VM is "
                               + "false, so UnsupportedOperationException was "
                               + "expected for " + i + " method " + name);
                    testFailed = true;
                    continue;
                } else {
                    log.display("debugger> " + i + " method " + name + " with "
                              + "synthetic value " + isSynthetic + " read "
                              + "without UnsupportedOperationException");
                }
            } catch (UnsupportedOperationException e) {
                if (canGetSynthetic) {
                    log.complain("debugger FAILURE 2> Value of "
                               + "canGetSyntheticAttribute in current VM is "
                               + "true, but cannot get synthetic for method "
                               + "name.");
                    log.complain("debugger FAILURE 2> Exception: " + e);
                    testFailed = true;
                } else {
                    log.display("debugger> UnsupportedOperationException was "
                              + "thrown while getting isSynthetic for " + i
                              + " method " + name + " because value "
                              + "canGetSynthetic is false.");
                }
                continue;
            }


            if (isSynthetic) {
                if (SYNTHETIC_METHOD_NAME.equals(name) && SYNTHETIC_METHOD_SIGNATURE.equals(signature)) {
                    totalSyntheticMethods++;
                } else {
                    testFailed = true;
                    log.complain("debugger FAILURE 3> Found unexpected synthetic method " + name
                            + signature);
                }
            }
        }

        if (totalSyntheticMethods == 0) {
            log.complain("debugger FAILURE 4> Synthetic methods not found.");
            testFailed = true;
        } else if (totalSyntheticMethods > 1 ) {
            log.complain("debugger FAILURE 5> More than one Synthetic method is found.");
            testFailed = true;
        }

        pipe.println("quit");
        debuggee.waitFor();
        int status = debuggee.getStatus();
        if (testFailed) {
            log.complain("debugger FAILURE> TEST FAILED");
            return 2;
        } else {
            if (status == 95) {
                log.display("debugger> expected Debuggee's exit "
                          + "status - " + status);
                return 0;
            } else {
                log.complain("debugger FAILURE> UNEXPECTED Debuggee's exit "
                           + "status (not 95) - " + status);
                return 2;
            }
        }
    }
}
