/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.internal.platform;

public class SystemMetrics {
    public static Metrics instance() {
        return CgroupMetrics.getInstance();
    }
}
