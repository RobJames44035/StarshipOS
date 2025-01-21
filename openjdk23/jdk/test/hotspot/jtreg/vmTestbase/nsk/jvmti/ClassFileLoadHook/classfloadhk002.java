/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.ClassFileLoadHook;

import java.io.*;

import jdk.test.lib.Utils;
import nsk.share.*;
import nsk.share.jvmti.*;

/**
 * Debuggee class of JVMTI test.
 */
public class classfloadhk002 extends DebugeeClass {

    /** Load native library if required. */
    static {
        System.loadLibrary("classfloadhk002");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new classfloadhk002().runIt(argv, out);
    }

    /* =================================================================== */

    /* constant names */
    public static final String PACKAGE_NAME = "nsk.jvmti.ClassFileLoadHook";
    public static final String TESTED_CLASS_NAME = PACKAGE_NAME + ".classfloadhk002r";

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /* original bytecode of tested class */
    public static byte origClassBytes[] = null;

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        String args[] = argHandler.getArguments();
        if (args.length <= 0) {
            throw new Failure("Path for tested class file to load not specified");
        }

        log.display("Reading original bytecode of tested class: \n\t" + TESTED_CLASS_NAME);
        try {
            origClassBytes = readBytes(TESTED_CLASS_NAME);
        } catch (IOException e) {
            throw new Failure("IOException in reading bytecode of tested class file:\n\t" + e);
        }

        log.display("Sync: debugee ready to load tested class");
        status = checkStatus(status);

        try {
            log.display("Loading tested class: " + TESTED_CLASS_NAME);
            Class testedClass = Class.forName(TESTED_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new Failure("No tested class file found: \n\t" + e);
        }

        log.display("Sync: tested class loaded");
        status = checkStatus(status);

        return status;
    }

    /** Read classfile for class name. */
    public static byte[] readBytes(String classname) throws IOException {
        String filename = ClassFileFinder.findClassFile(classname, Utils.TEST_CLASS_PATH).toString();
        FileInputStream in = new FileInputStream(filename);
        byte[] bytes = new byte[in.available()];
        in.read(bytes);
        in.close();
        return bytes;
    }
}
