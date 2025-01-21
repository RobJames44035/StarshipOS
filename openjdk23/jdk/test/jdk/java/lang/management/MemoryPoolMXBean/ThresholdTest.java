/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug     6546089
 * @summary Basic unit test of MemoryPoolMXBean.isUsageThresholdExceeded() and
 *          MemoryPoolMXBean.isCollectionThresholdExceeded().
 * @author  Mandy Chung
 *
 * @run main/othervm ThresholdTest
 */

import java.lang.management.*;
import java.util.*;

public class ThresholdTest {
    public static void main(String args[]) throws Exception {
        long[] bigObject = new long[1000000];

        System.gc(); // force an initial full-gc
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        try {
            for (MemoryPoolMXBean p : pools) {
                // verify if isUsageThresholdExceeded() returns correct value
                checkUsageThreshold(p);
                // verify if isCollectionUsageThresholdExceeded() returns correct value
                checkCollectionUsageThreshold(p);
            }
        } finally {
            // restore the default
            for (MemoryPoolMXBean p : pools) {
                if (p.isUsageThresholdSupported()) {
                    p.setUsageThreshold(0);
                }
                if (p.isCollectionUsageThresholdSupported()) {
                    p.setCollectionUsageThreshold(0);
                }
            }
        }

        System.out.println("Test passed.");
    }

    private static void checkUsageThreshold(MemoryPoolMXBean p) throws Exception {

        if (!p.isUsageThresholdSupported()) {
            return;
        }

        long threshold = p.getUsageThreshold();
        if (threshold != 0) {
            // Expect the default threshold is zero (disabled)
            throw new RuntimeException("TEST FAILED: " +
                "Pool " + p.getName() +
                " has non-zero threshold (" + threshold);
        }

        // isUsageThresholdExceeded() should return false if threshold == 0
        if (p.isUsageThresholdExceeded()) {
            throw new RuntimeException("TEST FAILED: " +
                "Pool " + p.getName() +
                " isUsageThresholdExceeded() returned true" +
                " but threshold = 0");
        }

        p.setUsageThreshold(1);
        // force a full gc to minimize the likelihood of running GC
        // between getting the usage and checking the threshold
        System.gc();

        MemoryUsage u = p.getUsage();
        if (u.getUsed() >= 1) {
            if (!p.isUsageThresholdExceeded()) {
                throw new RuntimeException("TEST FAILED: " +
                    "Pool " + p.getName() +
                    " isUsageThresholdExceeded() returned false but " +
                    " threshold(" + p.getUsageThreshold() +
                    ") <= used(" + u.getUsed() + ")");
            }
        } else {
            if (p.isUsageThresholdExceeded()) {
                throw new RuntimeException("TEST FAILED: " +
                    "Pool " + p.getName() +
                    " isUsageThresholdExceeded() returned true but" +
                    " threshold(" + p.getUsageThreshold() +
                    ") > used(" + u.getUsed() + ")");
            }
        }

        // disable low memory detection and isUsageThresholdExceeded()
        // should return false
        p.setUsageThreshold(0);
        if (p.isUsageThresholdExceeded()) {
            throw new RuntimeException("TEST FAILED: " +
                "Pool " + p.getName() +
                " isUsageThresholdExceeded() returned true but threshold = 0");
        }
    }

    private static void checkCollectionUsageThreshold(MemoryPoolMXBean p) throws Exception {

        if (!p.isCollectionUsageThresholdSupported()) {
            return;
        }

        long threshold = p.getCollectionUsageThreshold();
        if (threshold != 0) {
            // Expect the default threshold is zero (disabled)
            throw new RuntimeException("TEST FAILED: " +
                "Pool " + p.getName() +
                " has non-zero threshold (" + threshold);
        }

        // isCollectionUsageThresholdExceeded() should return false if threshold == 0
        if (p.isCollectionUsageThresholdExceeded()) {
            throw new RuntimeException("TEST FAILED: " +
                "Pool " + p.getName() +
                " isCollectionUsageThresholdExceeded() returned true" +
                " but threshold = 0");
        }

        p.setCollectionUsageThreshold(1);
        MemoryUsage u = p.getCollectionUsage();
        if (u == null) {
            if (p.isCollectionUsageThresholdExceeded()) {
                throw new RuntimeException("TEST FAILED: " +
                    "Pool " + p.getName() +
                    " isCollectionUsageThresholdExceeded() returned true but" +
                    " getCollectionUsage() return null");
            }
        } else if (u.getUsed() >= 1) {
            if (!p.isCollectionUsageThresholdExceeded()) {
                throw new RuntimeException("TEST FAILED: " +
                    "Pool " + p.getName() +
                    " isCollectionUsageThresholdExceeded() returned false but " +
                    " threshold(" + p.getCollectionUsageThreshold() +
                    ") < used(" + u.getUsed() + ")");
            }
        } else {
            if (p.isCollectionUsageThresholdExceeded()) {
                throw new RuntimeException("TEST FAILED: " +
                    "Pool " + p.getName() +
                    " isCollectionUsageThresholdExceeded() returned true but" +
                    " threshold(" + p.getCollectionUsageThreshold() +
                    ") > used(" + u.getUsed() + ")");
            }
        }

        // disable low memory detection and isCollectionUsageThresholdExceeded()
        // should return false
        p.setCollectionUsageThreshold(0);
        if (p.isCollectionUsageThresholdExceeded()) {
            throw new RuntimeException("TEST FAILED: " +
                "Pool " + p.getName() +
                " isCollectionUsageThresholdExceeded() returned true but threshold = 0");
        }
    }
}
