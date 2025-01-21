/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package java.lang;

import java.io.IOException;

/**
 * A {@code Readable} is a source of characters. Characters from
 * a {@code Readable} are made available to callers of the read
 * method via a {@link java.nio.CharBuffer CharBuffer}.
 *
 * @since 1.5
 */
public interface Readable {

    /**
     * Attempts to read characters into the specified character buffer.
     * The buffer is used as a repository of characters as-is: the only
     * changes made are the results of a put operation. No flipping or
     * rewinding of the buffer is performed. If the {@linkplain
     * java.nio.CharBuffer#length length} of the specified character
     * buffer is zero, then no characters will be read and zero will be
     * returned.
     *
     * @param cb the buffer to read characters into
     * @return The number of {@code char} values added to the buffer,
     *         possibly zero, or -1 if this source of characters is at its end
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if cb is null
     * @throws java.nio.ReadOnlyBufferException if cb is a read only buffer,
     *         even if its length is zero
     */
    public int read(java.nio.CharBuffer cb) throws IOException;
}
