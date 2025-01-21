/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jvmti/IterateThroughHeap/abort.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * This test exercises JVMTI function IterateOverHeap().
 * Test checks that if one of available callbacks returned JVMTI_VISIT_ABORT value,
 * then iteration will be stopped and no more objects will be reported.
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm/native -agentlib:Abort=-waittime=5 nsk.jvmti.IterateThroughHeap.abort.Abort
 */

package nsk.jvmti.IterateThroughHeap.abort;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class Abort extends DebugeeClass {

    static {
        loadLibrary("Abort");
    }

    public static void main(String args[]) {
        String[] argv = JVMTITest.commonInit(args);
        System.exit(new Abort().runTest(argv,System.out) + Consts.JCK_STATUS_BASE);
    }

    protected Log log = null;
    protected ArgumentHandler argHandler = null;
    protected int status = Consts.TEST_PASSED;

    public int runTest(String args[], PrintStream out) {
        argHandler = new ArgumentHandler(args);
        log = new Log(out, argHandler);
        log.display("Verifying JVMTI_ABORT.");
        status = checkStatus(status);
        return status;
    }

}
