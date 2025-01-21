/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.jdi.ClassUnloadEvent.classSignature;

import java.io.*;
import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import java.lang.reflect.Method;

// This class is the debugged application in the test

// NOTE: Test does not check array class because of difficulty of
//       providing reliable technique for unloading such class.
//       So all these testcases are commented in the test.

class signature001a {
    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int JCK_STATUS_BASE  = 95;

    static final String PREFIX = "nsk.jdi.ClassUnloadEvent.classSignature";
    static final String CHECKED_CLASS   = PREFIX + ".signature001c";
    static final String CHECKED_INTFACE = PREFIX + ".signature001b";
    static final String CHECKED_ARRAY   = PREFIX + ".signature001c[]";

    public static void main(String args[]) {
        signature001a _signature001a = new signature001a();
        System.exit(JCK_STATUS_BASE + _signature001a.run(args));
    }

    int run (String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        // define directory to load class files
        String loadClassDir = (argHandler.getArguments())[0] + File.separator + "loadclass";

        // notify debugger that debugee is ready
        pipe.println(signature001.COMMAND_READY);

        // wait for a command to load checked class
        String command = pipe.readln();
        if (!command.equals(signature001.COMMAND_LOAD)) {
             System.err.println("TEST BUG: unexpected command: " + command);
             return FAILED;
        }

        // load checked class for further unloading
        ClassUnloader checkedClassUnloader = new ClassUnloader();
        try {
            checkedClassUnloader.loadClass(CHECKED_CLASS, loadClassDir);
        } catch (Exception ex) {
            System.err.println("Unexpected exception while loading " + CHECKED_CLASS + ":");
            System.err.println(ex);
            return FAILED;
        }

        // load checked interface for further unloading
        ClassUnloader checkedInterfaceUnloader = new ClassUnloader();
        try {
            checkedInterfaceUnloader.loadClass(CHECKED_INTFACE, loadClassDir);
        } catch (Exception ex) {
            System.err.println("Unexpected exception while loading " + CHECKED_INTFACE + ":");
            System.err.println(ex);
            return FAILED;
        }

/*
        // to load array type
        Object object1;
        try {
            object1 = class1.newInstance();
        } catch (Throwable e) {
            System.err.println("Cannot create instance of " + CHECKED_CLASS);
            System.err.println("Exception/error: " + e.getMessage());
            return FAILED;
        }
        Method method1;
        try {
            method1 = class1.getMethod("initArray", null);
            method1.invoke(object1, null);
            System.out.println("method initArray is invoked");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            if (!(Class.forName(CHECKED_ARRAY).getClassLoader() instanceof KlassLoader)) {
                System.err.println("TEST BUG: Incorrect loader of type" + CHECKED_ARRAY);
                return FAILED;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
*/

        // notify debugger that checked class is loaded
        pipe.println(signature001.COMMAND_LOADED);

        // turn off pipe pinging
        pipe.setPingTimeout(0);

        // wait for a command to unload checked class
        command = pipe.readln();
        if (!command.equals(signature001.COMMAND_UNLOAD)) {
             System.err.println("TEST BUG: unexpected command: " + command);
             return FAILED;
        }

        // try to unload checked class
        boolean classes_unloaded = checkedClassUnloader.unloadClass()
                                && checkedInterfaceUnloader.unloadClass();

        if (!classes_unloaded) {
             pipe.println(signature001.COMMAND_LOADED);
        } else {
             pipe.println(signature001.COMMAND_UNLOADED);
        }

        // wait for a command to exit
        pipe.println(signature001.COMMAND_UNLOAD);

        command = pipe.readln();
        if (!command.equals(signature001.COMMAND_QUIT)) {
             System.err.println("TEST BUG: unknown command: " + command);
             return FAILED;
        }
        return PASSED;
    }
}
