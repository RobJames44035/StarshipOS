/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.ClassType.newInstance;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 *  <code>newinstance008a</code> is deugee's part of the newinstance008.
 */
public class newinstance008a {

    public final static String brkpMethodName = "main";
    public final static int brkpLineNumber = 51;
    private static Log log;
    private static IOPipe pipe;

    public static void main (String argv[]) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        log = new Log(System.err, argHandler);
        pipe = argHandler.createDebugeeIOPipe(log);
        pipe.println(newinstance008.SGNL_READY);

        String instr = pipe.readln();
        // the below line is a breakpoint.
        // Debugger will invoke constructor newinstance008a().
        instr = pipe.readln(); // brkpLineNumber
        if (instr.equals(newinstance008.SGNL_QUIT)) {
            log.display("completed succesfully.");
            System.exit(Consts.TEST_PASSED + Consts.JCK_STATUS_BASE);
        }

        log.complain("unexpected signal of debugger.");
        System.exit(Consts.TEST_FAILED + Consts.JCK_STATUS_BASE);
    }

    newinstance008a() {
        try {
            pipe.println(newinstance008.SGNL_READY);
            log.display("constructor:: waiting response from debugger...");
            String instr = pipe.readln();
            if (instr.equals(newinstance008.SGNL_FINISH)) {
                log.display("constructor:: completed succesfully.");
            } else if (instr.equals(newinstance008.SGNL_ABORT)) {
                log.display("constructor:: aborted.");
            }
        } catch(Exception e) {
            log.display("unexpected exception " + e);
        }
    }

}
