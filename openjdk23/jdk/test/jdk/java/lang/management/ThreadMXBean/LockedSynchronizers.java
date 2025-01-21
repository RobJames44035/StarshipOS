/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     5086470 6358247
 * @summary Basic Test for ThreadInfo.getLockedSynchronizers()
 *          SynchronizerLockingThread is the class that creates threads
 *          and do the checking.
 *
 * @author  Mandy Chung
 *
 * @build Barrier
 * @build SynchronizerLockingThread
 * @build ThreadDump
 * @run main/othervm LockedSynchronizers
 */

import java.lang.management.*;
import java.util.*;

public class LockedSynchronizers {
    public static void main(String[] argv) throws Exception {
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        if (!mbean.isSynchronizerUsageSupported()) {
            System.out.println("Monitoring of synchronizer usage not supported");
            return;
        }

        // Start thread and print thread dump
        SynchronizerLockingThread.startLockingThreads();
        ThreadDump.threadDump();

        // Test dumpAllThreads with locked monitors and synchronizers
        ThreadInfo[] tinfos = mbean.dumpAllThreads(true, true);
        SynchronizerLockingThread.checkLocks(tinfos);

        // Test getThreadInfo with locked monitors and synchronizers
        long[] ids = SynchronizerLockingThread.getThreadIds();
        tinfos = mbean.getThreadInfo(ids, true, true);
        if (tinfos.length != ids.length) {
            throw new RuntimeException("Number of ThreadInfo objects = " +
                tinfos.length + " not matched. Expected: " + ids.length);
        }

        SynchronizerLockingThread.checkLocks(tinfos);

        System.out.println("Test passed");
    }
}
