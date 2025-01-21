/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package jdk.internal.platform;

/*
 * @author bobv
 * @since 11
 */

public class Container {

    private Container() { }

    /**
     * Returns the platform specific Container Metrics class or
     * null if not supported on this platform.
     *
     * @return Metrics instance or null if not supported
     */
    public static Metrics metrics() {
        return Metrics.systemMetrics();
    }
}
