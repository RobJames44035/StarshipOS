/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.awt.color;

import java.io.Serial;

/**
 * This exception is thrown if the native CMM returns an error.
 */
public class CMMException extends RuntimeException {

    /**
     * Use serialVersionUID from JDK 1.2 for interoperability.
     */
    @Serial
    private static final long serialVersionUID = 5775558044142994965L;

    /**
     * Constructs a {@code CMMException} with the specified detail message.
     *
     * @param  message the specified detail message, or {@code null}
     */
    public CMMException(String message) {
        super(message);
    }
}
