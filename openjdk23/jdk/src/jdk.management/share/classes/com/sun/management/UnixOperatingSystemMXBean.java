/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package com.sun.management;

/**
 * Platform-specific management interface for the Unix
 * operating system on which the Java virtual machine is running.
 *
 * @author  Mandy Chung
 * @since   1.5
 */
public interface UnixOperatingSystemMXBean extends
    com.sun.management.OperatingSystemMXBean {

    /**
     * Returns the number of open file descriptors.
     *
     * @return the number of open file descriptors.
     */
    public long getOpenFileDescriptorCount();

    /**
     * Returns the maximum number of file descriptors.
     *
     * @return the maximum number of file descriptors.
     */
    public long getMaxFileDescriptorCount();
}
