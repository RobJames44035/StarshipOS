/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     5086470 6358247
 * @summary Basic Test for ThreadInfo.getLockedMonitors()
 *          - a stack frame acquires no monitor
 *          - a stack frame acquires one or more monitors
 *          - a stack frame blocks on Object.wait
 *            and the monitor waiting is not locked.
 *          LockingThread is the class that creates threads
 *          and do the checking.
 *
 * @author  Mandy Chung
 *
 * @build Barrier
 * @build LockingThread
 * @build ThreadDump
 * @run main/othervm LockedMonitors
 */

import java.lang.management.*;
import java.util.*;

public class LockedMonitors {
    public static void main(String[] argv) throws Exception {
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        if (!mbean.isObjectMonitorUsageSupported()) {
            System.out.println("Monitoring of object monitor usage is not supported");
            return;
        }

        // Start the thread and print the thread dump
        LockingThread.startLockingThreads();
        ThreadDump.threadDump();

        ThreadInfo[] tinfos;
        long[] ids = LockingThread.getThreadIds();

        // Dump all threads with locked monitors
        tinfos = mbean.dumpAllThreads(true, false);
        LockingThread.checkLockedMonitors(tinfos);

        // Dump all threads with locked monitors and locked synchronizers
        tinfos = mbean.dumpAllThreads(true, true);
        LockingThread.checkLockedMonitors(tinfos);

        // Test getThreadInfo with locked monitors
        tinfos = mbean.getThreadInfo(ids, true, false);
        if (tinfos.length != ids.length) {
            throw new RuntimeException("Number of ThreadInfo objects = " +
                tinfos.length + " not matched. Expected: " + ids.length);
        }
        LockingThread.checkLockedMonitors(tinfos);

        // Test getThreadInfo with locked monitors and locked synchronizers
        tinfos = mbean.getThreadInfo(ids, true, true);
        if (tinfos.length != ids.length) {
            throw new RuntimeException("Number of ThreadInfo objects = " +
                tinfos.length + " not matched. Expected: " + ids.length);
        }
        LockingThread.checkLockedMonitors(tinfos);

        System.out.println("Test passed");
    }
}
