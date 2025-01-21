/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.internal.platform;

/**
 * Marker interface for cgroup-based metrics
 *
 */
public interface CgroupSubsystem extends Metrics {


    default boolean isContainerized() {
        return false; // This default impl is never used
    }

    /**
     * Returned for metrics of type long if the underlying implementation
     * has determined that no limit is being imposed.
     */
    public static final long LONG_RETVAL_UNLIMITED = -1;
    public static final String MAX_VAL = "max";

    public static long limitFromString(String strVal) {
        if (strVal == null || MAX_VAL.equals(strVal)) {
            return CgroupSubsystem.LONG_RETVAL_UNLIMITED;
        }
        return Long.parseLong(strVal);
    }

}
