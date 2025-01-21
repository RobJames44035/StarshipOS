/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package java.nio.file;

/**
 * Unchecked exception thrown when an attempt is made to update an object
 * associated with a {@link FileSystem#isReadOnly() read-only} {@code FileSystem}.
 *
 * @since 1.7
 */

public class ReadOnlyFileSystemException
    extends UnsupportedOperationException
{
    @java.io.Serial
    static final long serialVersionUID = -6822409595617487197L;

    /**
     * Constructs an instance of this class.
     */
    public ReadOnlyFileSystemException() {
    }
}
