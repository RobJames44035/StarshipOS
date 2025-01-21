/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.monitoring.MemoryPoolMBean.getUsageThreshold;

import java.io.*;
import java.util.*;
import nsk.share.*;
import nsk.monitoring.share.*;

public class getthreshold001 {
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
            log.display(i + " pool " + monitor.getName(pool));

            boolean isSupported = monitor.isUsageThresholdSupported(pool);
            if (isSupported) {
                log.display("  supports usage thresholds");

                // Test three values for the threshold
                long max = monitor.getUsage(pool).getMax();
                long used = monitor.getUsage(pool).getUsed();

                // max value can be -1, so take an absolute value
                test(monitor, pool, Math.abs(max), log);
                test(monitor, pool, 0, log);
                test(monitor, pool, used, log);
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
                                   + "expected in pool "
                                   + monitor.getName(pool));
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

    private static void test(MemoryMonitor monitor, Object pool,
                                                      long threshold, Log log) {
        log.display("  setting threshold " + threshold);
        try {
            monitor.setUsageThreshold(pool, threshold);
        } catch (Exception e) {
            log.complain("Unexpected exception " + e);
            e.printStackTrace(log.getOutStream());
            testFailed = true;
            return;
        }
        log.display("  threshold " + threshold + " is set");

        long result = monitor.getUsageThreshold(pool);
        if (threshold != result) {
            log.complain("Threshold value is " + result + " in pool "
                       + monitor.getName(pool) + ", " + threshold
                       + " expected");
            testFailed = true;
        }
        log.display("  threshold " + threshold + " is read");
    } // test()

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
