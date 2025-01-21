/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.time;

/**
 * Exception used to indicate a problem while calculating a date-time.
 * <p>
 * This exception is used to indicate problems with creating, querying
 * and manipulating date-time objects.
 *
 * @implSpec
 * This class is intended for use in a single thread.
 *
 * @since 1.8
 */
public class DateTimeException extends RuntimeException {

    /**
     * Serialization version.
     */
    @java.io.Serial
    private static final long serialVersionUID = -1632418723876261839L;

    /**
     * Constructs a new date-time exception with the specified message.
     *
     * @param message  the message to use for this exception, may be null
     */
    public DateTimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new date-time exception with the specified message and cause.
     *
     * @param message  the message to use for this exception, may be null
     * @param cause  the cause of the exception, may be null
     */
    public DateTimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
