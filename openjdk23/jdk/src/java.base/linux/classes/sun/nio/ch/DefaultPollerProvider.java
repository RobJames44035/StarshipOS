/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package sun.nio.ch;

import java.io.IOException;
import jdk.internal.vm.ContinuationSupport;

/**
 * Default PollerProvider for Linux.
 */
class DefaultPollerProvider extends PollerProvider {
    DefaultPollerProvider() { }

    @Override
    Poller.Mode defaultPollerMode() {
        if (ContinuationSupport.isSupported()) {
            return Poller.Mode.VTHREAD_POLLERS;
        } else {
            return Poller.Mode.SYSTEM_THREADS;
        }
    }

    @Override
    int defaultReadPollers(Poller.Mode mode) {
        int ncpus = Runtime.getRuntime().availableProcessors();
        if (mode == Poller.Mode.VTHREAD_POLLERS) {
            return Math.min(Integer.highestOneBit(ncpus), 32);
        } else {
            return Math.max(Integer.highestOneBit(ncpus / 4), 1);
        }
    }

    @Override
    Poller readPoller(boolean subPoller) throws IOException {
        return new EPollPoller(subPoller, true);
    }

    @Override
    Poller writePoller(boolean subPoller) throws IOException {
        return new EPollPoller(subPoller, false);
    }
}
