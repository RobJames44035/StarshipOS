/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.internal.util;

/**
 * Exception that is thrown if there is something wrong with the input, for instance
 * a file that can't be read or a numerical value that is out of range.
 * <p>
 * When this exception is thrown, a user will typically not want to see the
 * command line syntax, but instead information about what was wrong with the
 * input.
 */
public final class UserDataException extends Exception {
    private static final long serialVersionUID = 6656457380115167810L;
    /**
     * The error message.
     *
     * The first letter should not be capitalized, so a context can be printed prior
     * to the error message.
     *
     * @param errorMessage
     */
    public UserDataException(String errorMessage) {
        super(errorMessage);
    }
}
