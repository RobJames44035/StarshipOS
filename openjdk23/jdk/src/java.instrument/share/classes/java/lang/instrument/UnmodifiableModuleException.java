/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package java.lang.instrument;

/**
 * Thrown to indicate that a module cannot be modified.
 *
 * @see Instrumentation#redefineModule
 * @since 9
 */

public class UnmodifiableModuleException extends RuntimeException {
    private static final long serialVersionUID = 6912511912351080644L;

    /**
     * Constructs an {@code UnmodifiableModuleException} with no
     * detail message.
     */
    public UnmodifiableModuleException() {
        super();
    }

    /**
     * Constructs an {@code UnmodifiableModuleException} with the
     * specified detail message.
     *
     * @param  msg the detail message.
     */
    public UnmodifiableModuleException(String msg) {
        super(msg);
    }
}
