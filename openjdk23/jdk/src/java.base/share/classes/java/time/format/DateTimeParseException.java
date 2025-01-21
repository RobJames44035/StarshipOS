/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.time.format;

import java.time.DateTimeException;

/**
 * An exception thrown when an error occurs during parsing.
 * <p>
 * This exception includes the text being parsed and the error index.
 *
 * @implSpec
 * This class is intended for use in a single thread.
 *
 * @since 1.8
 */
public class DateTimeParseException extends DateTimeException {

    /**
     * Serialization version.
     */
    @java.io.Serial
    private static final long serialVersionUID = 4304633501674722597L;

    /**
     * @serial The text that was being parsed.
     */
    private final String parsedString;
    /**
     * @serial The error index in the text.
     */
    private final int errorIndex;

    /**
     * Constructs a new exception with the specified message.
     *
     * @param message  the message to use for this exception, may be null
     * @param parsedData  the parsed text, should not be null
     * @param errorIndex  the index in the parsed string that was invalid, should be a valid index
     */
    public DateTimeParseException(String message, CharSequence parsedData, int errorIndex) {
        super(message);
        this.parsedString = parsedData.toString();
        this.errorIndex = errorIndex;
    }

    /**
     * Constructs a new exception with the specified message and cause.
     *
     * @param message  the message to use for this exception, may be null
     * @param parsedData  the parsed text, should not be null
     * @param errorIndex  the index in the parsed string that was invalid, should be a valid index
     * @param cause  the cause exception, may be null
     */
    public DateTimeParseException(String message, CharSequence parsedData, int errorIndex, Throwable cause) {
        super(message, cause);
        this.parsedString = parsedData.toString();
        this.errorIndex = errorIndex;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the string that was being parsed.
     *
     * @return the string that was being parsed, should not be null.
     */
    public String getParsedString() {
        return parsedString;
    }

    /**
     * Returns the index where the error was found.
     *
     * @return the index in the parsed string that was invalid, should be a valid index
     */
    public int getErrorIndex() {
        return errorIndex;
    }

}
