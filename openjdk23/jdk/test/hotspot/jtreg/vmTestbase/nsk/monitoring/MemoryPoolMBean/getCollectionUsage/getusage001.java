/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.monitoring.MemoryPoolMBean.getCollectionUsage;

import java.io.*;
import java.util.*;
import nsk.share.*;
import nsk.monitoring.share.*;

public class getusage001 {
    private static boolean testFailed = false;

    public static void main(String[] argv) {
        System.exit(Consts.JCK_STATUS_BASE + run(argv, System.out));
    }

    public static int run(String[] argv, PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(out, argHandler);
        MemoryMonitor monitor = Monitor.getMemoryMonitor(log, argHandler);
        List pools = monitor.getMemoryPoolMBeans();

        for (int i = 0; i < pools.size(); i++) {
            Object pool = pools.get(i);

            try {
                monitor.getCollectionUsage(pool);
            } catch (Exception e) {
                log.complain("Unexpected exception " + e);
                e.printStackTrace(log.getOutStream());
                testFailed = true;
            }
        } // for i

        if (testFailed)
            out.println("TEST FAILED");
        return (testFailed) ? Consts.TEST_FAILED : Consts.TEST_PASSED;
    }
}
