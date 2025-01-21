/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package sun.nio.ch;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Factory methods for input/output streams based on channels.
 */
public class Streams {
    private Streams() { }

    /**
     * Return an input stream that reads bytes from the given channel.
     */
    public static InputStream of(ReadableByteChannel ch) {
        if (ch instanceof SocketChannelImpl sc) {
            return new SocketInputStream(sc);
        } else {
            return new ChannelInputStream(ch);
        }
    }

    /**
     * Return an output stream that writes bytes to the given channel.
     */
    public static OutputStream of(WritableByteChannel ch) {
        if (ch instanceof SocketChannelImpl sc) {
            return new SocketOutputStream(sc);
        } else {
            return new ChannelOutputStream(ch);
        }
    }
}
