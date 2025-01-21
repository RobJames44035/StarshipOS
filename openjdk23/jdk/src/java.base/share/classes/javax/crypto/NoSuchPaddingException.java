/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package javax.crypto;

import java.security.GeneralSecurityException;

/**
 * This exception is thrown when a particular padding mechanism is
 * requested but is not available in the environment.
 *
 * @author Jan Luehe
 *
 * @since 1.4
 */

public class NoSuchPaddingException extends GeneralSecurityException {

    @java.io.Serial
    private static final long serialVersionUID = -4572885201200175466L;

    /**
     * Constructs a {@code NoSuchPaddingException} with no detail
     * message. A detail message is a {@code String} that describes this
     * particular exception.
     */
    public NoSuchPaddingException() {
        super();
    }

    /**
     * Constructs a {@code NoSuchPaddingException} with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NoSuchPaddingException(String msg) {
        super(msg);
    }
}
