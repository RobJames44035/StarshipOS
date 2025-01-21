/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.share.locks;

import nsk.share.Wicket;

/*
 *  Class used for deadlock creation, acquires java lock
 *  using synchronized block
 */
public class SynchronizedBlockLocker extends DeadlockLocker {
    private Object object = new Object();

    public SynchronizedBlockLocker(Wicket step1, Wicket step2, Wicket readyWicket) {
        super(step1, step2, readyWicket);
    }

    public Object getLock() {
        return object;
    }

    protected void doLock() {
        synchronized (object) {
            step1.unlockAll();
            step2.waitFor();
            readyWicket.unlock();
            inner.lock();
        }
    }
}
