/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package java.io;

import java.util.Objects;

/**
 * A character stream whose source is a string.
 *
 * @apiNote
 * {@link Reader#of(CharSequence)} provides a method to read from any
 * {@link CharSequence} that may be more efficient than {@code StringReader}.
 *
 * @author      Mark Reinhold
 * @since       1.1
 */

public class StringReader extends Reader {

    private final Reader r;

    /**
     * Creates a new string reader.
     *
     * @param s  String providing the character stream.
     */
    public StringReader(String s) {
        r = Reader.of(s);
    }

    /**
     * Reads a single character.
     *
     * @return     The character read, or -1 if the end of the stream has been
     *             reached
     *
     * @throws     IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        synchronized (lock) {
            return r.read();
        }
    }

    /**
     * Reads characters into a portion of an array.
     *
     * <p> If {@code len} is zero, then no characters are read and {@code 0} is
     * returned; otherwise, there is an attempt to read at least one character.
     * If no character is available because the stream is at its end, the value
     * {@code -1} is returned; otherwise, at least one character is read and
     * stored into {@code cbuf}.
     *
     * @param      cbuf  {@inheritDoc}
     * @param      off   {@inheritDoc}
     * @param      len   {@inheritDoc}
     *
     * @return     {@inheritDoc}
     *
     * @throws     IndexOutOfBoundsException  {@inheritDoc}
     * @throws     IOException  {@inheritDoc}
     */
    public int read(char[] cbuf, int off, int len) throws IOException {
        synchronized (lock) {
            return r.read(cbuf, off, len);
        }
    }

    /**
     * Skips characters. If the stream is already at its end before this method
     * is invoked, then no characters are skipped and zero is returned.
     *
     * <p>The {@code n} parameter may be negative, even though the
     * {@code skip} method of the {@link Reader} superclass throws
     * an exception in this case. Negative values of {@code n} cause the
     * stream to skip backwards. Negative return values indicate a skip
     * backwards. It is not possible to skip backwards past the beginning of
     * the string.
     *
     * <p>If the entire string has been read or skipped, then this method has
     * no effect and always returns {@code 0}.
     *
     * @param n {@inheritDoc}
     *
     * @return {@inheritDoc}
     *
     * @throws IOException {@inheritDoc}
     */
    public long skip(long n) throws IOException {
        synchronized (lock) {
            return r.skip(n);
        }
    }

    /**
     * Tells whether this stream is ready to be read.
     *
     * @return True if the next read() is guaranteed not to block for input
     *
     * @throws     IOException  If the stream is closed
     */
    public boolean ready() throws IOException {
        synchronized (lock) {
            return r.ready();
        }
    }

    /**
     * Tells whether this stream supports the mark() operation, which it does.
     */
    public boolean markSupported() {
        return true;
    }

    /**
     * Marks the present position in the stream.  Subsequent calls to reset()
     * will reposition the stream to this point.
     *
     * @param  readAheadLimit  Limit on the number of characters that may be
     *                         read while still preserving the mark.  Because
     *                         the stream's input comes from a string, there
     *                         is no actual limit, so this argument must not
     *                         be negative, but is otherwise ignored.
     *
     * @throws     IllegalArgumentException  If {@code readAheadLimit < 0}
     * @throws     IOException  If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        synchronized (lock) {
            r.mark(readAheadLimit);
        }
    }

    /**
     * Resets the stream to the most recent mark, or to the beginning of the
     * string if it has never been marked.
     *
     * @throws     IOException  If an I/O error occurs
     */
    public void reset() throws IOException {
        synchronized (lock) {
            r.reset();
        }
    }

    /**
     * Closes the stream and releases any system resources associated with
     * it. Once the stream has been closed, further read(),
     * ready(), mark(), or reset() invocations will throw an IOException.
     * Closing a previously closed stream has no effect. This method will block
     * while there is another thread blocking on the reader.
     */
    public void close() {
        synchronized (lock) {
            try {
                r.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
