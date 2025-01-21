/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package java.lang;

/**
 * Signals that the class doesn't have a field of a specified name.
 *
 * @since   1.1
 */
public class NoSuchFieldException extends ReflectiveOperationException {
    @java.io.Serial
    private static final long serialVersionUID = -6143714805279938260L;

    /**
     * Constructor.
     */
    public NoSuchFieldException() {
        super();
    }

    /**
     * Constructor with a detail message.
     *
     * @param s the detail message
     */
    public NoSuchFieldException(String s) {
        super(s);
    }
}
