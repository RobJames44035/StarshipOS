/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.monitoring.ThreadMXBean.findMonitorDeadlockedThreads;

import java.io.*;
import nsk.share.*;
import nsk.monitoring.share.*;

public class find001 {
    public static void main(String[] argv) {
        System.exit(Consts.JCK_STATUS_BASE + run(argv, System.out));
    }

    public static int run(String[] argv, PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(out, argHandler);
        ThreadMonitor monitor = Monitor.getThreadMonitor(log, argHandler);
        long id = Thread.currentThread().getId();
        long[] ids = monitor.findMonitorDeadlockedThreads();

        if (ids == null) {
            log.display("findCircularBlockedThread() returned null");
            return Consts.TEST_PASSED;
        }

        if (ids.length == 0) {
            log.display("findCircularBlockedThread() returned array of length "
                      + "0");
            return Consts.TEST_PASSED;
        }

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id) {
                log.complain("TEST FAILED");
                log.complain("findCircularBlockedThread() returned current "
                           + "thread (id = " + id + ")");
                return Consts.TEST_FAILED;
            }
        }
        return Consts.TEST_PASSED;
    }
}
