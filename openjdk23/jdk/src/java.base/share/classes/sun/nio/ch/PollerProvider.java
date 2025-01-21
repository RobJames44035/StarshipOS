/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package sun.nio.ch;

import java.io.IOException;

/**
 * Provider class for Poller implementations.
 */
abstract class PollerProvider {
    private static final PollerProvider INSTANCE = new DefaultPollerProvider();

    PollerProvider() { }

    /**
     * Returns the system-wide PollerProvider.
     */
    static PollerProvider provider() {
        return INSTANCE;
    }

    /**
     * Returns the default poller mode.
     * @implSpec The default implementation uses system threads.
     */
    Poller.Mode defaultPollerMode() {
        return Poller.Mode.SYSTEM_THREADS;
    }

    /**
     * Default number of read pollers for the given mode. The count must be a power of 2.
     * @implSpec The default implementation returns 1.
     */
    int defaultReadPollers(Poller.Mode mode) {
        return 1;
    }

    /**
     * Default number of write pollers for the given mode. The count must be a power of 2.
     * @implSpec The default implementation returns 1.
     */
    int defaultWritePollers(Poller.Mode mode) {
        return 1;
    }

    /**
     * Maps a file descriptor to an index from 0 to {@code toIndex}.
     * @implSpec The default implementation is good for Unix file descriptors.
     */
    int fdValToIndex(int fdVal, int toIndex) {
        return fdVal & (toIndex - 1);
    }

    /**
     * Creates a Poller for read ops.
     * @param subPoller true to create a sub-poller
     */
    abstract Poller readPoller(boolean subPoller) throws IOException;

    /**
     * Creates a Poller for write ops.
     * @param subPoller true to create a sub-poller
     */
    abstract Poller writePoller(boolean subPoller) throws IOException;
}
