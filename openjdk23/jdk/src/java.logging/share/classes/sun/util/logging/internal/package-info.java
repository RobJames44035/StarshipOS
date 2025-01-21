/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * <p>
 * <b>[JDK INTERNAL]</b>
 * The {@code sun.util.logging.internal} package defines an internal
 * implementation of the {@link jdk.internal.logger.DefaultLoggerFinder} which
 * provides an extension of the {@link java.lang.System.Logger System.Logger}
 * interface making it easy to bridge from {@link java.util.logging};
 * the JDK default implementation of the LoggerFinder will return loggers
 * implementing this extension when {@code java.util.logging} is present.
 * </p>
 * <p>
 * When {@code java.util.logging} is present, Logger instances returned by
 * the JDK default implementation of the LoggerFinder
 * wrap an instance of {@link java.util.logging.Logger java.util.logging.Logger}
 * and implement the {@link
 * sun.util.logging.PlatformLogger.Bridge PlatformLogger.Bridge}
 * extension, overriding all the methods defined in
 * that extension in order to call the corresponding methods on their wrapped
 * {@linkplain java.util.logging.Logger backend Logger} instance.
 * <p>
 * <br>
 * @see java.lang.System.LoggerFinder
 * @see java.lang.System.Logger
 * @see sun.util.logging.PlatformLogger
 * @see sun.util.logging.PlatformLogger.Bridge
 * @see jdk.internal.logger
 *
 * @since 9
 */
package sun.util.logging.internal;
