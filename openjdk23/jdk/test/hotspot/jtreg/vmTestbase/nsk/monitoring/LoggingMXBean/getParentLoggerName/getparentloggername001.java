/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package nsk.monitoring.LoggingMXBean.getParentLoggerName;

import java.lang.management.*;
import java.io.*;
import nsk.share.*;
import nsk.monitoring.share.*;
import java.util.logging.*;

public class getparentloggername001 {

    private static boolean testFailed = false;

    public static void main(String[] args) {

        System.exit(Consts.JCK_STATUS_BASE + run(args, System.out));
    }

    private static Log log;

    static int run(String[] args, PrintStream out) {

        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        LoggingMonitor loggingMonitor = Monitor.getLoggingMonitor(log,
            argumentHandler);

        // Test case 1. check that
        // it returns the name of the parent for the specified logger
        String testLogName = getparentloggername001.class.getName()+".child";
        String testLogParentName = getparentloggername001.class.getName();
        Logger testLog = Logger.getLogger(testLogName);
        Logger testLogParent = Logger.getLogger(testLogParentName);

        // This synchronized block guarantees that testLog will still be alive
        // when loggingMonitor is getting an information about logger
        synchronized (testLog) {
        synchronized (testLogParent) {

            String mxbeanParentName = loggingMonitor.getParentLoggerName(
                testLogName);
            if (! testLogParentName.equals(mxbeanParentName)) {

                testFailed = true;
                log.complain("Failure 1.");
                log.complain("loggingMXBean.getParentLoggerName() returns "
                    + "unexpected name of parent logger");
                log.complain("Parent name is "+testLogParentName+", obtained name "
                    +"is "+mxbeanParentName);
            }


            // Test case 2. check
            // if the specified logger does not exist, null is returned
            if (loggingMonitor.getParentLoggerName("no such logger") != null) {

                testFailed = true;
                log.complain("Failure 2.");
                log.complain("loggingMXBean.getParentLoggerName(\"no such logger\") "
                    + "does not return null");
            }


            // Test case 3. check
            // if the specified logger is the root Logger in the namespace, the
            // result will be an empty string.
            String parentName = loggingMonitor.getParentLoggerName(
                testLogParentName);
            if (parentName == null || !parentName.equals("")) {

                testFailed = true;
                log.complain("Failure 3.");
                log.complain("The specified logger was the root Logger in the "
                    + "namespace, but returned string is not empty: "+parentName);
            }
        }}

        if (testFailed)
            log.complain("TEST FAILED");

        return (testFailed) ? Consts.TEST_FAILED : Consts.TEST_PASSED;
    }

}
