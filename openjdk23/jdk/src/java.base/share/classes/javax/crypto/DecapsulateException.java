/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package javax.crypto;

import java.security.GeneralSecurityException;

/**
 * An exception that is thrown by the
 * {@link javax.crypto.KEM.Decapsulator#decapsulate} method to denote an
 * error during decapsulation.
 *
 * @since 21
 */
public class DecapsulateException extends GeneralSecurityException {

    @java.io.Serial
    private static final long serialVersionUID = 21L;

    /**
     * Creates a {@code DecapsulateException} with the specified
     * detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     */
    public DecapsulateException(String message) {
        super(message);
    }

    /**
     * Creates a {@code DecapsulateException} with the specified
     * detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A {@code null} value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     */
    public DecapsulateException(String message, Throwable cause) {
        super(message, cause);
    }
}
