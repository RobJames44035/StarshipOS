/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.share.locks;

import java.util.concurrent.locks.ReentrantLock;
import nsk.share.Wicket;

/*
 *  Class used for deadlock creation, acquires java.util.concurrent.locks.ReentrantLock
 */
public class ReentrantLockLocker extends DeadlockLocker {
    private ReentrantLock lock = new ReentrantLock();

    public ReentrantLockLocker(Wicket step1, Wicket step2, Wicket readyWicket) {
        super(step1, step2, readyWicket);
    }

    public ReentrantLock getLock() {
        return lock;
    }

    protected void doLock() {
        lock.lock();

        try {
            step1.unlockAll();
            step2.waitFor();
            readyWicket.unlock();
            inner.lock();
        } finally {
            lock.unlock();
        }
    }
}
