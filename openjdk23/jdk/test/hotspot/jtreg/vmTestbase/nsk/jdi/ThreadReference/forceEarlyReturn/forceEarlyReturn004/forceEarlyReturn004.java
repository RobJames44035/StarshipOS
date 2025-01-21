/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdi/ThreadReference/forceEarlyReturn/forceEarlyReturn004.
 * VM Testbase keywords: [quick, jpda, jdi, feature_jdk6_jpda, vm6]
 * VM Testbase readme:
 * DESCRIPTION
 *         The test checks that a result of the method com.sun.jdi.forceEarlyReturn(Value value)
 *         complies with its specification. The test checks:
 *             - attempt to call com.sun.jdi.forceEarlyReturn for native Java method throws NativeMethodException
 *         Test scenario:
 *         Debugger VM force debuggee VM start test thread which execute infinite loop in native method, debugger suspend
 *         debuggee VM and try call forceEarlyReturn() for test thread, NativeMethodException should be thrown.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdi.ThreadReference.forceEarlyReturn.forceEarlyReturn004.forceEarlyReturn004
 *        nsk.jdi.ThreadReference.forceEarlyReturn.forceEarlyReturn004.forceEarlyReturn004a
 * @run main/othervm/native
 *      nsk.jdi.ThreadReference.forceEarlyReturn.forceEarlyReturn004.forceEarlyReturn004
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 */

package nsk.jdi.ThreadReference.forceEarlyReturn.forceEarlyReturn004;

import java.io.PrintStream;
import com.sun.jdi.*;
import nsk.share.Consts;
import nsk.share.jdi.ForceEarlyReturnDebugger;

public class forceEarlyReturn004 extends ForceEarlyReturnDebugger {
    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public String debuggeeClassName() {
        return nsk.jdi.ThreadReference.forceEarlyReturn.forceEarlyReturn004.forceEarlyReturn004a.class.getName();
    }

    public static int run(String argv[], PrintStream out) {
        return new forceEarlyReturn004().runIt(argv, out);
    }

    public void doTest() {
        pipe.println(forceEarlyReturn004a.COMMAND_STOP_THREAD_IN_NATIVE);

        if (!isDebuggeeReady())
            return;

        // this thread execute native method
        ThreadReference threadReference = debuggee.threadByName(forceEarlyReturn004a.testThreadInNativeName);

        vm.suspend();

        try {
            // expect NativeMethodException
            threadReference.forceEarlyReturn(vm.mirrorOf(0));
            setSuccess(false);
            log.complain("Expected 'NativeMethodException' exception was not thrown");
        } catch (NativeMethodException e) {
            // expected exception
        } catch (Exception e) {
            setSuccess(false);
            log.complain("Unexpected exception: " + e);
            e.printStackTrace(log.getOutStream());
        }

        vm.resume();
    }
}
