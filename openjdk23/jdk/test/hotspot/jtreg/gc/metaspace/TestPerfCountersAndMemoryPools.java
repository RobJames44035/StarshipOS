/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.metaspace;

import java.util.List;
import java.lang.management.*;

import jdk.test.lib.Platform;
import static jdk.test.lib.Asserts.*;
import gc.testlibrary.PerfCounters;

/* @test TestPerfCountersAndMemoryPools
 * @bug 8023476
 * @library /test/lib /
 * @requires vm.gc.Serial
 * @requires vm.bits == "64"
 * @summary Tests that a MemoryPoolMXBeans and PerfCounters for metaspace
 *          report the same data.
 * @modules java.base/jdk.internal.misc
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run main/othervm -Xlog:class+load,class+unload=trace -XX:-UseCompressedOops -XX:-UseCompressedClassPointers -XX:+UseSerialGC -XX:+UsePerfData -Xint gc.metaspace.TestPerfCountersAndMemoryPools
 * @run main/othervm -Xlog:class+load,class+unload=trace -XX:+UseCompressedOops -XX:+UseCompressedClassPointers -XX:+UseSerialGC -XX:+UsePerfData -Xint gc.metaspace.TestPerfCountersAndMemoryPools
 */

/* @test TestPerfCountersAndMemoryPools
 * @bug 8023476
 * @library /test/lib /
 * @requires vm.gc.Serial
 * @requires vm.bits == "32"
 * @summary Tests that a MemoryPoolMXBeans and PerfCounters for metaspace
 *          report the same data.
 * @modules java.base/jdk.internal.misc
 *          java.management
 *          jdk.internal.jvmstat/sun.jvmstat.monitor
 * @run main/othervm -Xlog:class+load,class+unload=trace -XX:+UseSerialGC -XX:+UsePerfData -Xint gc.metaspace.TestPerfCountersAndMemoryPools
 */
public class TestPerfCountersAndMemoryPools {
    public static void main(String[] args) throws Exception {
        checkMemoryUsage("Metaspace", "sun.gc.metaspace");

        if (InputArguments.contains("-XX:+UseCompressedClassPointers") && Platform.is64bit()) {
            checkMemoryUsage("Compressed Class Space", "sun.gc.compressedclassspace");
        }
    }

    private static MemoryPoolMXBean getMemoryPool(String memoryPoolName) {
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : pools) {
            if (pool.getName().equals(memoryPoolName)) {
                return pool;
            }
        }

        throw new RuntimeException("Excpted to find a memory pool with name " +
                                   memoryPoolName);
    }

    private static void checkMemoryUsage(String memoryPoolName, String perfNS)
        throws Exception {
        MemoryPoolMXBean pool = getMemoryPool(memoryPoolName);

        // First, call all the methods to let them allocate their own slab of metadata
        getMinCapacity(perfNS);
        getCapacity(perfNS);
        getUsed(perfNS);
        pool.getUsage().getInit();
        pool.getUsage().getUsed();
        pool.getUsage().getCommitted();
        assertEQ(1L, 1L, "Make assert load");

        // Must do a GC to update performance counters
        System.gc();
        assertEQ(getMinCapacity(perfNS), pool.getUsage().getInit(), "MinCapacity out of sync");

        long usedPerfCounters;
        long usedMXBean;

        // the pool.getUsage().getUsed() might load additional classes intermittently
        // so first verify that perfCounters are not changed, call System.gc() to reset them
        do {
            System.gc();
            usedPerfCounters = getUsed(perfNS);
            usedMXBean = pool.getUsage().getUsed();
            System.gc();
        } while (usedPerfCounters != getUsed(perfNS));
        assertEQ(usedPerfCounters, usedMXBean, "Used out of sync");

        assertEQ(getCapacity(perfNS), pool.getUsage().getCommitted(), "Committed out of sync");
    }

    private static long getMinCapacity(String ns) throws Exception {
        return PerfCounters.findByName(ns + ".minCapacity").longValue();
    }

    private static long getCapacity(String ns) throws Exception {
        return PerfCounters.findByName(ns + ".capacity").longValue();
    }

    private static long getUsed(String ns) throws Exception {
        return PerfCounters.findByName(ns + ".used").longValue();
    }
}
