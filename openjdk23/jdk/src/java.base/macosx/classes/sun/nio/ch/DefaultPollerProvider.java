/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package sun.nio.ch;

import java.io.IOException;

/**
 * Default PollerProvider for macOS.
 */
class DefaultPollerProvider extends PollerProvider {
    DefaultPollerProvider() { }

    @Override
    Poller readPoller(boolean subPoller) throws IOException {
        return new KQueuePoller(subPoller, true);
    }

    @Override
    Poller writePoller(boolean subPoller) throws IOException {
        return new KQueuePoller(subPoller, false);
    }
}
