/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.monitoring.MemoryPoolMBean.getPeakUsage;

import java.io.*;
import java.lang.management.*;
import java.util.*;
import nsk.share.*;
import nsk.monitoring.share.*;

public class getpeak001 {
    private static boolean testFailed = false;

    public static void main(String[] argv) {
        System.exit(Consts.JCK_STATUS_BASE + run(argv, System.out));
    }

    public static int run(String[] argv, PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(out, argHandler);
        MemoryMonitor monitor = Monitor.getMemoryMonitor(log, argHandler);
        List pools = monitor.getMemoryPoolMBeans();
        MemoryUsage usage = null;

        for (int i = 0; i < pools.size(); i++) {
            byte[] b = new byte[10 * 1024]; // Eat 10K
            Object pool = pools.get(i);

            // No exceptions should be thrown
            try {
                usage = monitor.getPeakUsage(pool);
                log.display(i + " " + monitor.getName(pool) + ": " + usage);
            } catch (Throwable t) {
                if (t instanceof ThreadDeath)
                    throw (ThreadDeath) t;
                log.complain("Unexpected exception in pool "
                           + monitor.getName(pool));
                t.printStackTrace(log.getOutStream());
                testFailed = true;
                continue;
            }

            boolean isValid = monitor.isValid(pool);
            if (isValid) {
                if (usage == null) {
                    log.complain("getPeakUsage() returned null for the valid "
                           + "pool " + monitor.getName(pool));
                    testFailed = true;
                }
            } else {
                if (usage != null) {
                    log.complain("getPeakUsage() returned not-null: " + usage
                               + " for invalid pool " + monitor.getName(pool));
                    testFailed = true;
                }
            }
        } // for i

        if (testFailed)
            out.println("TEST FAILED");
        return (testFailed) ? Consts.TEST_FAILED : Consts.TEST_PASSED;
    }
}
