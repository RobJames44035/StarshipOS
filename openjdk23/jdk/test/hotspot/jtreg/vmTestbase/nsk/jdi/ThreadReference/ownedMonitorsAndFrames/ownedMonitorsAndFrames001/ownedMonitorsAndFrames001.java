/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdi/ThreadReference/ownedMonitorsAndFrames/ownedMonitorsAndFrames001.
 * VM Testbase keywords: [quick, jpda, jdi, feature_jdk6_jpda, vm6]
 * VM Testbase readme:
 * DESCRIPTION
 *         Test check that attempt to call com.sun.jdi.ThreadReference.ownedMonitorsAndFrames on non-suspended VM
 *         throws IncompatibleThreadStateException.
 *         Special thread class is used in debugee VM for testing thread in different states - nsk.share.jpda.StateTestThread.
 *         StateTestThread sequentially changes its state in following order:
 *                 - thread not started
 *                 - thread is running
 *                 - thread is sleeping
 *                 - thread in Object.wait()
 *                 - thread wait on java monitor
 *                 - thread is finished
 *         Debugger VM calls ThreadReference.ownedMonitorsAndFrames() for all this states and expects IncompatibleThreadStateException.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdi.ThreadReference.ownedMonitorsAndFrames.ownedMonitorsAndFrames001.ownedMonitorsAndFrames001
 * @run driver
 *      nsk.jdi.ThreadReference.ownedMonitorsAndFrames.ownedMonitorsAndFrames001.ownedMonitorsAndFrames001
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdi.ThreadReference.ownedMonitorsAndFrames.ownedMonitorsAndFrames001;

import java.io.*;
import com.sun.jdi.*;
import nsk.share.Consts;
import nsk.share.jdi.*;
import nsk.share.jpda.StateTestThread;
import nsk.share.jpda.AbstractDebuggeeTest;

public class ownedMonitorsAndFrames001 extends TestDebuggerType2 {
    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {
        return new ownedMonitorsAndFrames001().runIt(argv, out);
    }

    protected String debuggeeClassName() {
        return AbstractJDIDebuggee.class.getName();
    }

    private void test(ThreadReference threadReference) {
        log.display("Thread state: " + threadReference.status());
        try {
            // call ThreadReference.ownedMonitorsAndFrames() on non-suspended VM
            // IncompatibleThreadStateException should be thrown
            threadReference.ownedMonitorsAndFrames();
            setSuccess(false);
            log.complain("Expected IncompatibleThreadStateException was not thrown");
        } catch (IncompatibleThreadStateException e) {
            // expected exception
        } catch (Throwable e) {
            setSuccess(false);
            log.complain("Unexpected exception: " + e);
            e.printStackTrace(log.getOutStream());
        }
    }

    public void doTest() {
        // create StateTestThread
        pipe.println(AbstractDebuggeeTest.COMMAND_CREATE_STATETESTTHREAD);

        if (!isDebuggeeReady())
            return;

        // StateTestThread in state 0
        ThreadReference threadReference = (ThreadReference) findSingleObjectReference(AbstractDebuggeeTest.stateTestThreadClassName);

        test(threadReference);

        // change StateTestThread's state from 1 to stateTestThreadStates.length
        int state = 1;

        while (state++ < StateTestThread.stateTestThreadStates.length) {
            pipe.println(AbstractDebuggeeTest.COMMAND_NEXTSTATE_STATETESTTHREAD);

            if (!isDebuggeeReady())
                return;

            test(threadReference);
        }
    }
}
