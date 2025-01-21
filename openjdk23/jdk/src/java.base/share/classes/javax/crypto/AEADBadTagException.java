/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package javax.crypto;

/**
 * This exception is thrown when a {@link Cipher} operating in
 * an AEAD mode (such as GCM/CCM) is unable to verify the supplied
 * authentication tag.
 *
 * @since 1.7
 */
public class AEADBadTagException extends BadPaddingException {

    @java.io.Serial
    private static final long serialVersionUID = -488059093241685509L;

    /**
     * Constructs an {@code AEADBadTagException} with no detail message.
     */
    public AEADBadTagException() {
        super();
    }

    /**
     * Constructs an {@code AEADBadTagException} with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public AEADBadTagException(String msg) {
        super(msg);
    }
}
