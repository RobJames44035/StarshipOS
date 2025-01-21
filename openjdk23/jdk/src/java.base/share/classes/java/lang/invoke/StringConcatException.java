/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package java.lang.invoke;

/**
 * StringConcatException is thrown by {@link StringConcatFactory} when linkage
 * invariants are violated.
 *
 * @since 9
 */
public class StringConcatException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 292L + 9L;

    /**
     * Constructs an exception with a message
     * @param msg exception message
     */
    public StringConcatException(String msg) {
        super(msg);
    }

    /**
     * Constructs an exception with a message and a linked throwable
     * @param msg   exception message
     * @param cause throwable cause
     */
    public StringConcatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
