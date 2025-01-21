/*
 * StarshipOS Copyright (c) 1995-2025. R.A. James
 */

package java.net;

import java.io.IOException;

/**
 * Thrown to indicate that there is an error in the underlying
 * protocol, such as a TCP error.
 *
 * @author  Chris Warth
 * @since   1.0
 */
public class ProtocolException extends IOException {
    @java.io.Serial
    private static final long serialVersionUID = -6098449442062388080L;

    /**
     * Constructs a new {@code ProtocolException} with the
     * specified detail message.
     *
     * @param   message   the detail message.
     */
    public ProtocolException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ProtocolException} with no detail message.
     */
    public ProtocolException() {
    }
}
