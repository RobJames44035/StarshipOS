/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8185003
 * @build ThreadDump
 * @run main MaxDepthForThreadInfoTest
 * @summary verifies the functionality of ThreadMXBean.dumpAllThreads
 * and ThreadMXBean.getThreadInfo with maxDepth argument
 */

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;



public class MaxDepthForThreadInfoTest {


    public static void main(String[] Args) {

        ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();

        long[] threadIds = tmxb.getAllThreadIds();

        ThreadInfo[] tinfos = tmxb.getThreadInfo(threadIds, true, true, 0);
        for (ThreadInfo ti : tinfos) {
            if (ti != null && ti.getStackTrace().length > 0) {
                ThreadDump.printThreadInfo(ti);
                throw new RuntimeException("more than requested " +
                        "number of frames dumped");
            }
        }

        tinfos = tmxb.getThreadInfo(threadIds, true, true, 3);
        for (ThreadInfo ti : tinfos) {
            if (ti != null && ti.getStackTrace().length > 3) {
                ThreadDump.printThreadInfo(ti);
                throw new RuntimeException("more than requested " +
                        "number of frames dumped");
            }
        }

        try {
            tmxb.getThreadInfo(threadIds, true, true, -1);
            throw new RuntimeException("Didn't throw IllegalArgumentException " +
                    "for negative maxdepth value");
        } catch (IllegalArgumentException e) {
            System.out.println("Throwed IllegalArgumentException as expected");
        }

        tinfos = tmxb.dumpAllThreads(true, true, 0);
        for (ThreadInfo ti : tinfos) {
            if (ti.getStackTrace().length > 0) {
                ThreadDump.printThreadInfo(ti);
                throw new RuntimeException("more than requested " +
                        "number of frames dumped");
            }
        }
        tinfos = tmxb.dumpAllThreads(true, true, 2);
        for (ThreadInfo ti : tinfos) {
            if (ti.getStackTrace().length > 2) {
                ThreadDump.printThreadInfo(ti);
                throw new RuntimeException("more than requested " +
                        "number of frames dumped");
            }
        }

        try {
            tmxb.dumpAllThreads(true, true, -1);
            throw new RuntimeException("Didn't throw IllegalArgumentException " +
                    "for negative maxdepth value");
        } catch (IllegalArgumentException e) {
            System.out.println("Throwed IllegalArgumentException as expected");
        }

        System.out.println("Test passed");
    }
}
