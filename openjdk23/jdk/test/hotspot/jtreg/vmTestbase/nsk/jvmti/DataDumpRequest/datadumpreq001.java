/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.DataDumpRequest;

import java.io.*;

import nsk.share.*;
import nsk.share.jvmti.*;
import vm.share.ProcessUtils;

/**
 * This test exercises the JVMTI event <code>DataDumpRequest</code>.
 * <br>It verifies that the event will be sent only during the live
 * phase of VM execution.<p>
 * The test works as follows. The agent enables the DataDumpRequest
 * event on <code>OnLoad</code> phase. Then the java part
 * imitates Ctrl-\ or Ctrl-Break (on Windows).If the DataDumpRequest
 * was not send the test ignores it and passes. Otherwise, the VM phase
 * is checked during the DataDumpRequest callback.<br>
 * Note that sending CTRL-\ causes HotSpot VM itself to print its full
 * thread dump. The dump may be ignored.
 *
 * @see vm.share.ProcessUtils#sendCtrlBreak
 */
public class datadumpreq001 {
        static {
                System.loadLibrary("ProcessUtils");
                System.loadLibrary("datadumpreq001");
        }

        public static native int waitForResult();

        public static void main(String[] argv) {
                argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

                // produce JCK-like exit status
                System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
        }

        public static int run(String argv[], PrintStream out) {
                ProcessUtils.sendCtrlBreak();
                return waitForResult();
        }
}
