/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package sun.nio.ch;

import jdk.internal.event.SocketWriteEvent;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream that writes bytes to a socket channel.
 */
class SocketOutputStream extends OutputStream {
    private final SocketChannelImpl sc;

    /**
     * Initialize a SocketOutputStream that writes to the given socket channel.
     */
    SocketOutputStream(SocketChannelImpl sc) {
        this.sc = sc;
    }

    /**
     * Returns the socket channel.
     */
    SocketChannelImpl channel() {
        return sc;
    }

    @Override
    public void write(int b) throws IOException {
        byte[] a = new byte[]{(byte) b};
        write(a, 0, 1);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (!SocketWriteEvent.enabled()) {
            sc.blockingWriteFully(b, off, len);
            return;
        }
        long start = SocketWriteEvent.timestamp();
        sc.blockingWriteFully(b, off, len);
        SocketWriteEvent.offer(start, len, sc.remoteAddress());
    }

    @Override
    public void close() throws IOException {
        sc.close();
    }
}
