/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package javax.management.loading;

/**
 * Marker interface indicating that a ClassLoader should not be added
 * to the {@link ClassLoaderRepository}.  When a ClassLoader is
 * registered as an MBean in the MBean server, it is added to the
 * MBean server's ClassLoaderRepository unless it implements this
 * interface.
 *
 * @since 1.5
 */
public interface PrivateClassLoader {}
