/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package java.nio.file;

/**
 * Unchecked exception thrown when an attempt is made to invoke a method on an
 * object created by one file system provider with a parameter created by a
 * different file system provider.
 *
 * @since 1.7
 */
public class ProviderMismatchException
    extends java.lang.IllegalArgumentException
{
    @java.io.Serial
    static final long serialVersionUID = 4990847485741612530L;

    /**
     * Constructs an instance of this class.
     */
    public ProviderMismatchException() {
    }

    /**
     * Constructs an instance of this class.
     *
     * @param   msg
     *          the detail message
     */
    public ProviderMismatchException(String msg) {
        super(msg);
    }
}
