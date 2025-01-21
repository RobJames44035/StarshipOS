/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.monitoring.MemoryPoolMBean.isUsageThresholdSupported;

import java.io.*;
import java.util.*;
import nsk.share.*;
import nsk.monitoring.share.*;

public class issupported001 {
    private static boolean testFailed = false;
    static MemoryMonitor monitor;

    public static void main(String[] argv) {
        System.exit(Consts.JCK_STATUS_BASE + run(argv, System.out));
    }

    public static int run(String[] argv, PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(out, argHandler);
        monitor = Monitor.getMemoryMonitor(log, argHandler);
        List pools = monitor.getMemoryPoolMBeans();

        for (int i = 0; i < pools.size(); i++) {
            Object pool = pools.get(i);
            boolean isSupported = monitor.isUsageThresholdSupported(pool);

            if (isSupported) {
                log.display("  supports usage thresholds");

                // No exceptions are expected
                try {
                    long threshold = monitor.getUsageThreshold(pool);
                    log.display("  threshold " + threshold + " is read");
                } catch (Exception e) {
                    log.complain(e + " is thrown in pool "
                               + monitor.getName(pool));
                    e.printStackTrace(log.getOutStream());
                    testFailed = true;
                }
            } else {
                log.display("  does not support usage thresholds");

                // UnsupportedOperationException is expected
                try {
                    long threshold = monitor.getUsageThreshold(pool);
                    log.complain("Threshold " + threshold + " is returned "
                               + "instead of UnsupportedOperationException "
                               + "in pool " + monitor.getName(pool));
                    testFailed = true;
                } catch (Exception e) {

                    Throwable unwrapped = unwrap(e);

                    if (unwrapped instanceof UnsupportedOperationException) {
                        log.display("  UnsupportedOperationException is "
                                  + "thrown");
                    } else {
                        log.complain("Incorrect execption " + unwrapped
                                   + " is thrown, "
                                   + "UnsupportedOperationException is "
                                   + "expected");
                        unwrapped.printStackTrace(log.getOutStream());
                        testFailed = true;
                    }
                } // try
            }
        } // for i

        if (testFailed)
            out.println("TEST FAILED");
        return (testFailed) ? Consts.TEST_FAILED : Consts.TEST_PASSED;
    }

    static Throwable unwrap(Throwable throwable) {

        Throwable unwrapped, t = throwable;

        do {
            unwrapped = t;

            if (unwrapped instanceof UnsupportedOperationException) {
                break;
            }

            t = unwrapped.getCause();

        } while (t != null);

        return unwrapped;
    }
}
