/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.monitoring.ThreadMXBean.isThreadCpuTimeSupported;

import java.io.*;
import nsk.share.*;
import nsk.monitoring.share.*;

public class thcputime001 {
    public static void main(String[] argv) {
        System.exit(Consts.JCK_STATUS_BASE + run(argv, System.out));
    }

    public static int run(String[] argv, PrintStream out) {
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        Log log = new Log(out, argHandler);
        ThreadMonitor monitor = Monitor.getThreadMonitor(log, argHandler);

        // Check the method is... for the specified way of access to MBeans
        boolean isSupported = monitor.isThreadCpuTimeSupported();
        if (isSupported) {
            log.display("Thread cpu time is supported.");
        } else {
            log.display("Thread cpu time is not supported.");
        }
        return 0;
    }
}
