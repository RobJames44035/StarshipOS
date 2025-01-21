/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package java.lang.reflect;

/**
 * Thrown when Java language access checks cannot be suppressed.
 *
 * @see AccessibleObject#setAccessible(boolean)
 * @since 9
 */

public class InaccessibleObjectException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = 4158786093378140901L;

    /**
     * Constructs an {@code InaccessibleObjectException} with no detail message.
     */
    public InaccessibleObjectException() {
    }

    /**
     * Constructs an {@code InaccessibleObjectException} with the given detail
     * message.
     *
     * @param msg
     *        The detail message
     */
    public InaccessibleObjectException(String msg) {
        super(msg);
    }

}
