/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package sun.jvmstat.monitor;

/**
 * Base class for exceptions that occur while interfacing with the
 * Monitoring interfaces.
 *
 * @author Brian Doherty
 * @since 1.5
 */
@SuppressWarnings("serial") // JDK implementation class
public class MonitorException extends Exception {

    /**
     * Create a MonitorException
     */
    public MonitorException() {
        super();
    }

    /**
     * Create a MonitorException with the given message.
     *
     * @param message the message to associate with the exception.
     */
    public MonitorException(String message) {
        super(message);
    }

    /**
     * Create a MonitorException with the given message and cause.
     *
     * @param message the message to associate with the exception.
     * @param cause the exception causing this exception.
     */
    public MonitorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create an InstrumentationException with the given cause.
     *
     * @param cause the exception causing this exception.
     */
    public MonitorException(Throwable cause) {
        super(cause);
    }
}
