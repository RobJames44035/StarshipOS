/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.jvmti.scenarios.bcinstr.BI02;

import java.io.*;

import nsk.share.*;
import nsk.share.jvmti.*;

public class bi02t001 extends DebugeeClass {

    static final int MAGIC_NUMBER = 101;
    static final String bi02t001aClassName =
        "nsk.jvmti.scenarios.bcinstr.BI02.bi02t001a";

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new bi02t001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    int status = Consts.TEST_PASSED;
    Log log = null;
    long timeout = 0;

    /* new bytecodes of tested class */
    public static byte newClassBytes[] = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000;

        String fileDir = ".";
        String args[] = argHandler.getArguments();
        if (args.length > 0) {
            fileDir = args[0];
        }

        String fileNameA = fileDir + File.separator + "newclass" + File.separator +
            bi02t001aClassName.replace('.', File.separatorChar) + ".class";
        log.display("Reading bytes of new class: \n\t" + fileNameA);
        try {
            FileInputStream in = new FileInputStream(fileNameA);
            newClassBytes = new byte[in.available()];
            in.read(newClassBytes);
            in.close();
        } catch (Exception e) {
            throw new Failure("Unexpected exception while reading class file:\n\t" + e);
        }

        status = checkStatus(status);

        log.display("redefining bi02t001a class via ClassFileLoadHook event");
        bi02t001a testedClass = new bi02t001a();
        int value = testedClass.check();
        log.display("After redefinition: " + value);
        if (value != MAGIC_NUMBER) {
            log.complain("Wrong value: " + value +
                ", expected: " + MAGIC_NUMBER);
            status = Consts.TEST_FAILED;
        }

        log.display("Debugee finished");
        return checkStatus(status);
    }
}

/* =================================================================== */

class bi02t001a {

    public int check() {
        return 1;
    }
}
