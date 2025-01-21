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

public class decltype008 {
    private static Log log;
    private final static String prefix = "nsk.jdi.TypeComponent.declaringType.";
    private final static String className = "decltype008";
    private final static String debugerName = prefix + className;
    private final static String debugeeName = debugerName + "a";
    private final static String mainClassName = prefix + "decltype008aMainClass";
    private final static String otherClassName = prefix + "decltype008aOtherClass";

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
        ReferenceType otherClass;
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
        otherClass = debugee.classByName(otherClassName);
        if (otherClass == null) {
           log.complain("debuger FAILURE> Class " + otherClassName + " not "
                      + "found.");
           return 2;
        }

        // Get all methods, find constructors and static initializers and
        // check them
        try {
            methods = otherClass.visibleMethods();
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
            String signature = method.signature();

            if (method.isStaticInitializer()) {
                // Static initializers are in OtherClass only
                boolean equal;

                log.display("debuger> " + i + " static initializer " + name
                          + " from " + declTypeName + " with signature "
                          + signature + " read.");
                try {
                    equal = declType.equals(otherClass);
                } catch (ObjectCollectedException e) {
                    log.complain("debuger FAILURE 1> Cannot compare reference "
                               + " types " + declType.name() + " and "
                               + otherClassName);
                    log.complain("debuger FAILURE 1> Exception: " + e);
                    testFailed = true;
                    continue;
                }
                if (!equal) {
                    log.complain("debuger FAILURE 2> Declaring type of "
                               + name + " is " + declTypeName + ", but should "
                               + "be " + otherClassName);
                    testFailed = true;
                }
            } else {
                if (method.isConstructor()) {
                    boolean equalOther;
                    boolean equalMain;
                    // Constructors from OtherClass starts with reference to
                    // String object
                    boolean fromOther =
                        signature.startsWith("(Ljava/lang/String;");
                    // Constructors from MainClass starts with reference to
                    // Long object
                    boolean fromMain =
                        signature.startsWith("(Ljava/lang/Long;");

                    log.display("debuger> " + i + " constructor " + name
                              + " from " + declTypeName + " with signature "
                              + signature + " read.");
                    try {
                        equalOther = declType.equals(otherClass);
                    } catch (ObjectCollectedException e) {
                        log.complain("debuger FAILURE 3> Cannot compare "
                                   + " reference types " + declType.name()
                                   + " and " + otherClassName);
                        log.complain("debuger FAILURE 3> Exception: " + e);
                        testFailed = true;
                        continue;
                    }
                    try {
                        equalMain = declType.equals(mainClass);
                    } catch (ObjectCollectedException e) {
                        log.complain("debuger FAILURE 4> Cannot compare "
                                   + " reference types " + declType.name()
                                   + " and " + mainClassName);
                        log.complain("debuger FAILURE 4> Exception: " + e);
                        testFailed = true;
                        continue;
                    }

                    if (fromOther && !equalOther) {
                        log.complain("debuger FAILURE 5> Declaring type of "
                               + name + " is " + declTypeName + ", but should "
                               + "be " + otherClassName);
                        testFailed = true;
                    }
                    if (fromMain && !equalMain) {
                        log.complain("debuger FAILURE 6> Declaring type of "
                               + name + " is " + declTypeName + ", but should "
                               + "be " + mainClassName);
                        testFailed = true;
                    }
                } else {
                    log.display("debuger> " + i + " method " + name + " from "
                              + declTypeName + " with signature "
                              + signature + " passed.");
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
