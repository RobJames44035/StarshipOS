/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/monitoring/ThreadInfo/isSuspended/issuspended001.
 * VM Testbase keywords: [quick, monitoring]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test checks that
 *         ThreadInfo.isSuspended()
 *     returns correct values for a thread in different states.
 *     The test starts an instance of MyThread and checks that isSuspended()
 *     returns false for it. Then it suspends the thread and expects the method
 *     to return true. After that the MyThread is resumed and isSuspended() must
 *     return false.
 *     Testing of the method does not depend on the way to access metrics, so
 *     only one (direct access) is implemented in the test.
 * COMMENT
 *     Fixed the bug
 *     4989235 TEST: The spec is updated accoring to 4982289, 4985742
 *     Updated according to:
 *     5024531 Fix MBeans design flaw that restricts to use JMX CompositeData
 *
 * @library /vmTestbase
 *          /test/lib
 *          /testlibrary
 * @run main/othervm nsk.monitoring.ThreadInfo.isSuspended.issuspended001
 */

package nsk.monitoring.ThreadInfo.isSuspended;

import java.lang.management.*;
import java.io.*;
import nsk.share.*;

import jvmti.JVMTIUtils;

public class issuspended001 {
    private static Wicket mainEntrance = new Wicket();
    private static boolean testFailed = false;

    public static void main(String[] argv) {
        System.exit(Consts.JCK_STATUS_BASE + run(argv, System.out));
    }

    public static int run(String[] argv, PrintStream out) {
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        MyThread thread = new MyThread(out);
        thread.start();

        // Wait for MyThread to start
        mainEntrance.waitFor();

        long id = thread.getId();
        ThreadInfo info = mbean.getThreadInfo(id, Integer.MAX_VALUE);
        boolean isSuspended = info.isSuspended();
        if (isSuspended) {
            out.println("Failure 1.");
            out.println("ThreadInfo.isSuspended() returned true, before "
                      + "Thread.suspend() was invoked.");
            testFailed = true;
        }

        JVMTIUtils.suspendThread(thread);
        info = mbean.getThreadInfo(id, Integer.MAX_VALUE);
        isSuspended = info.isSuspended();
        if (!isSuspended) {
            out.println("Failure 2.");
            out.println("ThreadInfo.isSuspended() returned false, after "
                      + "Thread.suspend() was invoked.");
            testFailed = true;
        }

        JVMTIUtils.resumeThread(thread);
        info = mbean.getThreadInfo(id, Integer.MAX_VALUE);
        isSuspended = info.isSuspended();
        if (isSuspended) {
            out.println("Failure 3.");
            out.println("ThreadInfo.isSuspended() returned true, after "
                      + "Thread.resume() was invoked.");
            testFailed = true;
        }

        thread.die = true;

        if (testFailed)
            out.println("TEST FAILED");
        return (testFailed) ? Consts.TEST_FAILED : Consts.TEST_PASSED;
    }

    private static class MyThread extends Thread {
        final static long WAIT_TIME = 500; // Milliseconds
        Object object = new Object();
        boolean die = false;
        PrintStream out;

        MyThread(PrintStream out) {
            this.out = out;
        }

        public void run() {

            // Notify "main" thread that MyThread has started
            mainEntrance.unlock();

            while (!die) {
                synchronized(object) {
                    try {
                        object.wait(WAIT_TIME);
                    } catch (InterruptedException e) {
                        out.println("Unexpected exception.");
                        e.printStackTrace(out);
                        testFailed = true;
                    }
                } // synchronized
            }
        } // run()
    } // MyThread
}
