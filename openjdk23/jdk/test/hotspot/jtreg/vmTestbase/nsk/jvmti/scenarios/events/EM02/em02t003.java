/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.events.EM02;

import java.lang.reflect.Method;
import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;


import java.util.*;
import java.math.*;

public class em02t003 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new em02t003().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    static Log log = null;
    Log.Logger logger;
    int status = Consts.TEST_PASSED;

    static final int STEP_AMOUNT = 3;
    static final String PACKAGE_NAME = "nsk.jvmti.scenarios.events.EM02";
    static final String TESTED_CLASS_NAME = PACKAGE_NAME + ".em02t003a";

    // run debuggee
    public int runIt(String args[], PrintStream out) {
        argHandler = new ArgumentHandler(args);
        log = new Log(out, argHandler);
        logger = new Log.Logger(log,"debuggee> ");

        String path;
        if (args.length == 0) {
            path = "loadclass";
        } else {
            path = args[0];
        }

        Class<?> loadedClass;
        Thread thrd;

        ClassUnloader unloader = new ClassUnloader();
        for (int i = 0; i < 3; i++) {
            try {
                unloader.loadClass(TESTED_CLASS_NAME, path);
            } catch(ClassNotFoundException e) {
                e.printStackTrace();
                return Consts.TEST_FAILED;
            }
            logger.display("ClassLoading:: Tested class was successfully loaded.");

            logger.display("MethodCompiling:: Provoke compiling.");
            loadedClass = unloader.getLoadedClass();

            try {
                thrd = (Thread )loadedClass.newInstance();
            } catch (Exception e) {
                logger.complain("Unexpected exception " + e);
                e.printStackTrace();
                return Consts.TEST_FAILED;
            }

            if (!invokeMethod(loadedClass, thrd, "start")) {
                return Consts.TEST_FAILED;
            }

            if (!invokeMethod(loadedClass, thrd, "join")) {
                return Consts.TEST_FAILED;
            }

            logger.display("MethodCompiling:: Provoke unloading compiled method - "
                                + "\n\ttrying to unload class...");
            thrd = null;
            loadedClass = null;
            if (!unloader.unloadClass()) {
                logger.complain("WARNING::Class couldn't be unloaded");
            } else {
                logger.display("ClassLoading:: Tested class was successfully unloaded.");
            }

            if (checkStatus(Consts.TEST_PASSED) == Consts.TEST_FAILED) {
                status = Consts.TEST_FAILED;
            }

            logger.display("\n");
        }
        return status;
    }

    boolean invokeMethod(Class<?> cls, Thread thrd, String methodName) {

        Method method;

        try {
            method = cls.getMethod(methodName);
            method.invoke(thrd);
        } catch (Exception e) {
            logger.complain("Unexpected exception " + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
