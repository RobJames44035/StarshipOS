/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */


package nsk.jdi.TypeComponent.declaringType;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import java.util.*;
import java.io.*;

public class decltype007 {

    private static Log log;
    private final static String prefix = "nsk.jdi.TypeComponent.declaringType.";
    private final static String className = "decltype007";
    private final static String debugerName = prefix + className;
    private final static String debugeeName = debugerName + "a";
    private final static String mainClassName = prefix + "decltype007aMainClass";

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
        ReferenceType mainClass;
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

        mainClass = debugee.classByName(mainClassName);
        if (mainClass == null) {
           log.complain("debuger FAILURE> Class " + mainClassName + " not "
                      + "found.");
           return 2;
        }

        // Get all methods, find constructors and static initializers and
        // check them
        try {
            methods = mainClass.methods();
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
            String name = method.name();
            ReferenceType declType = method.declaringType();
            String declTypeName = declType.name();
            boolean equal;

            log.display("debuger> " + i + " method " + name + " from "
                      + declTypeName + " with sign " + method.signature() +
                      " read.");
            if (method.isConstructor() || method.isStaticInitializer()) {
                try {
                    equal = declType.equals(mainClass);
                } catch (ObjectCollectedException e) {
                    log.complain("debuger FAILURE 1> Cannot compare reference "
                           + " types " + declType.name() + " and "
                           + mainClassName);
                    log.complain("debuger FAILURE 1> Exception: " + e);
                    testFailed = true;
                    continue;
                }
                if (!equal) {
                    log.complain("debuger FAILURE 2> Declaring type of "
                           + name + " is " + declTypeName + ", but should "
                           + "be " + mainClassName);
                    testFailed = true;
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
