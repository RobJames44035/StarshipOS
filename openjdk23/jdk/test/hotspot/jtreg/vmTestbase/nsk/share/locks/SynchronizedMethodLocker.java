/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.share.locks;

import nsk.share.Wicket;

/*
 *  Class used for deadlock creation, acquires java lock
 *  using synchronized method
 */
public class SynchronizedMethodLocker extends DeadlockLocker {
    public SynchronizedMethodLocker(Wicket step1, Wicket step2, Wicket readyWicket) {
        super(step1, step2, readyWicket);
    }

    public Object getLock() {
        return this;
    }

    protected synchronized void doLock() {
        step1.unlock();
        step2.waitFor();
        readyWicket.unlock();
        inner.lock();
    }

}
