/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */


package nsk.jdi.TypeComponent.name;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import java.util.*;
import java.io.*;

public class name003 {
    private static Log log;
    private final static String prefix = "nsk.jdi.TypeComponent.name.";
    private final static String className = "name003";
    private final static String debugerName = prefix + className;
    private final static String debugeeName = debugerName + "a";
    private final static String classToCheckName = prefix + "name003aClassToCheck";

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
        Debugee debugee = binder.bindToDebugee(debugeeName
                              + (argHandler.verbose() ? " -verbose" : ""));
        IOPipe pipe = new IOPipe(debugee);
        boolean testFailed = false;
        List methods;

        // Connect with debugee and resume it
        debugee.redirectStderr(out);
        debugee.resume();
        String line = pipe.readln();
        if (line == null) {
            log.complain("debuger FAILURE> UNEXPECTED debugee's signal - null");
            return 2;
        }
        if (!line.equals("ready")) {
            log.complain("debuger FAILURE> UNEXPECTED debugee's signal - "
                      + line);
            return 2;
        }
        else {
            log.display("debuger> debugee's \"ready\" signal recieved.");
        }

        ReferenceType refType = debugee.classByName(classToCheckName);
        if (refType == null) {
           log.complain("debuger FAILURE> Class " + classToCheckName
                      + " not found.");
           return 2;
        }

        // Get all methods, find constructors and static initializers and
        // check them
        try {
            methods = refType.allMethods();
        } catch (Exception e) {
            log.complain("debuger FAILURE> Can't get methods from class");
            log.complain("debuger FAILURE> Exception: " + e);
            return 2;
        }
        int totalMethods = methods.size();
        if (totalMethods < 1) {
            log.complain("debuger FAILURE> Total number of methods read "
                       + totalMethods);
            return 2;
        }
        log.display("debuger> Total methods found: " + totalMethods);
        Iterator methodsIterator = methods.iterator();
        for (int i = 0; methodsIterator.hasNext(); i++) {
            Method method = (Method)methodsIterator.next();
            String decTypeName = method.declaringType().name();
            String name = method.name();

            log.display("debuger> " + i + " method " + name + " from "
                      + decTypeName + " read.");
            if (method.isConstructor()) {
                // For constructors TypeComponent.name() is "<init>"
                if (!name.equals("<init>")) {
                    log.complain("debuger FAILURE 1> Constructor from "
                               + decTypeName + " is read with name " + name
                               + ", but name should be <init>");
                    testFailed = true;
                    continue;
                } else {
                    log.display("debuger> Constructor " + name + " from "
                              + decTypeName + " checked.");
                }
            }
            if (method.isStaticInitializer()) {
                // For static initializers TypeComponent.name() is "<clinit>"
                if (!name.equals("<clinit>")) {
                    log.complain("debuger FAILURE 2> Static initializer from "
                               + decTypeName + " is read with name " + name
                               + ", but name should be <clinit>");
                    testFailed = true;
                    continue;
                } else {
                    log.display("debuger> Static initializer " + name
                              + " from " + decTypeName + " checked.");
                }
            }
        }

        pipe.println("quit");
        debugee.waitFor();
        int status = debugee.getStatus();
        if (testFailed) {
            log.complain("debuger FAILURE> TEST FAILED");
            return 2;
        } else {
            if (status == 95) {
                log.display("debuger> expected Debugee's exit "
                          + "status - " + status);
                return 0;
            } else {
                log.complain("debuger FAILURE> UNEXPECTED Debugee's exit "
                           + "status (not 95) - " + status);
                return 2;
            }
        }
    }
}
