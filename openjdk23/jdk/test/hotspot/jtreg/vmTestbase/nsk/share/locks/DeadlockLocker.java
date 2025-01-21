/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.share.locks;

import nsk.share.*;

/*
 * DeadlockLocker - class used for deadlock creation
 *
 * To create deadlock second 'inner' instance of DeadlockLocker is required.
 *
 * Deadlock creation scenario:
 *      - object acquires its resource
 *      - notify 'inner' through Wicket step1 that resource is locked and 'inner' can try to acquire this object's resource
 *      - wait notification through Wicket step2 from 'inner' that 'inner' acquired its resource
 *      - call readyWicket.unlock to notify thread waiting deadlock creation
 *      - try acquire locked inner's resource, at the same time inner trying to acquire locked this object's resource - deadlock is created
 *
 * Subclasses should implement method doLock() taking in account described scenario.
 *
 *  Objects of DeadlockLocker should not be created directly, to create deadlocked threads use class DeadlockMaker
 */
abstract public class DeadlockLocker {
    protected DeadlockLocker inner;

    protected Wicket step1;

    protected Wicket step2;

    // 'readyWicket' is used to notify DeadlockMacker that deadlock is almost created
    protected Wicket readyWicket;

    public DeadlockLocker(Wicket step1, Wicket step2, Wicket readyWicket) {
        this.step1 = step1;
        this.step2 = step2;
        this.readyWicket = readyWicket;
    }

    public void setInner(DeadlockLocker inner) {
        this.inner = inner;
    }

    protected void checkInnerLocker() {
        if (inner == null) {
            throw new IllegalStateException(getClass().getName() + " deadlockLocker's inner locker is null");
        }
    }

    protected abstract void doLock();

    abstract public Object getLock();

    private int lockCount;

    final public void lock() {
        // call this method once
        if (lockCount++ > 1)
            return;

        // check that inner locker was set
        checkInnerLocker();

        doLock();
    }

}
