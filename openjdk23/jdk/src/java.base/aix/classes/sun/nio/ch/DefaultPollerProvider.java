/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package sun.nio.ch;

import java.io.IOException;

/**
 * Default PollerProvider for AIX.
 */
class DefaultPollerProvider extends PollerProvider {
    DefaultPollerProvider() { }

    @Override
    Poller readPoller(boolean subPoller) throws IOException {
        return new PollsetPoller(true);
    }

    @Override
    Poller writePoller(boolean subPoller) throws IOException {
        return new PollsetPoller(false);
    }
}
