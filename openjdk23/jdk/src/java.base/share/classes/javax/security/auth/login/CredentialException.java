/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package javax.security.auth.login;

/**
 * A generic credential exception.
 *
 * @since 1.5
 */
public class CredentialException extends LoginException {

    @java.io.Serial
    private static final long serialVersionUID = -4772893876810601859L;

    /**
     * Constructs a CredentialException with no detail message. A detail
     * message is a String that describes this particular exception.
     */
    public CredentialException() {
        super();
    }

    /**
     * Constructs a CredentialException with the specified detail message.
     * A detail message is a String that describes this particular
     * exception.
     *
     * @param msg the detail message.
     */
    public CredentialException(String msg) {
        super(msg);
    }
}
