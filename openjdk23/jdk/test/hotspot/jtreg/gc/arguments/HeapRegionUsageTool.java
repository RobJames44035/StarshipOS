/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.arguments;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;

/**
 * Utility class used by tests to get heap region usage.
 */
public final class HeapRegionUsageTool {

    /**
     * Get MemoryUsage from MemoryPoolMXBean which name matches passed string.
     *
     * @param name
     * @return MemoryUsage
     */
    private static MemoryUsage getUsage(String name){
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            if (pool.getName().matches(name)) {
                return pool.getUsage();
            }
        }
        return null;
    }

    /**
     * Get MemoryUsage of Eden space.
     *
     * @return MemoryUsage
     */
    public static MemoryUsage getEdenUsage() {
        return getUsage(".*Eden.*");
    }

    /**
     * Get MemoryUsage of Survivor space.
     *
     * @return MemoryUsage
     */
    public static MemoryUsage getSurvivorUsage() {
        return getUsage(".*Survivor.*");
    }

    /**
     * Get memory usage of Tenured space
     *
     * @return MemoryUsage
     */
    public static MemoryUsage getOldUsage() {
        return getUsage(".*(Old|Tenured).*");
    }

    /**
     * Get heap usage.
     *
     * @return MemoryUsage
     */
    public static MemoryUsage getHeapUsage() {
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
    }

    /**
     * Helper function to align up.
     *
     * @param value
     * @param alignment
     * @return aligned value
     */
    public static long alignUp(long value, long alignment) {
        return (value + alignment - 1) & ~(alignment - 1);
    }

    /**
     * Helper function to align down.
     *
     * @param value
     * @param alignment
     * @return aligned value
     */
    public static long alignDown(long value, long alignment) {
        return value & ~(alignment - 1);
    }
}
