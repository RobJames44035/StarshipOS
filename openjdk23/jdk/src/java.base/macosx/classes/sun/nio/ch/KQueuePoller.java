/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package sun.nio.ch;

import java.io.IOException;
import static sun.nio.ch.KQueue.*;

/**
 * Poller implementation based on the kqueue facility.
 */
class KQueuePoller extends Poller {
    private final int kqfd;
    private final int filter;
    private final int maxEvents;
    private final long address;

    KQueuePoller(boolean subPoller, boolean read) throws IOException {
        this.kqfd = KQueue.create();
        this.filter = (read) ? EVFILT_READ : EVFILT_WRITE;
        this.maxEvents = (subPoller) ? 64 : 512;
        this.address = KQueue.allocatePollArray(maxEvents);
    }

    @Override
    int fdVal() {
        return kqfd;
    }

    @Override
    void implRegister(int fdVal) throws IOException {
        int err = KQueue.register(kqfd, fdVal, filter, (EV_ADD|EV_ONESHOT));
        if (err != 0)
            throw new IOException("kevent failed: " + err);
    }

    @Override
    void implDeregister(int fdVal, boolean polled) {
        // event was deleted if already polled
        if (!polled) {
            KQueue.register(kqfd, fdVal, filter, EV_DELETE);
        }
    }

    @Override
    int poll(int timeout) throws IOException {
        int n = KQueue.poll(kqfd, address, maxEvents, timeout);
        int i = 0;
        while (i < n) {
            long keventAddress = KQueue.getEvent(address, i);
            int fdVal = KQueue.getDescriptor(keventAddress);
            polled(fdVal);
            i++;
        }
        return n;
    }
}
