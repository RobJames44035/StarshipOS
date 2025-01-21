/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock;

import nsk.share.TestBug;
import nsk.share.gc.lock.Locker;

/**
 * CriticalSectionTimedLocker represents a way to lock a resource
 * by entering some critical section for some time.
 */
public abstract class CriticalSectionTimedLocker<T> extends CriticalSectionLocker<T> {
        private long enterTime;
        private long sleepTime;

        public CriticalSectionTimedLocker() {
                this(5000, 10);
        }

        public CriticalSectionTimedLocker(long enterTime, long sleepTime) {
                setEnterTime(enterTime);
                setSleepTime(sleepTime);
        }

        protected final void criticalSection() {
                criticalSection(enterTime, sleepTime);
        }

        /**
         * Enter critical section for enterTime.
         *
         * Usually, something is done in a loop inside this critical section.
         * In this case, sleepTime is time to sleep after each iteration.
         */
        protected abstract void criticalSection(long enterTime, long sleepTime);

        public final void setEnterTime(long enterTime) {
                this.enterTime = enterTime;
        }

        public final void setSleepTime(long sleepTime) {
                this.sleepTime = sleepTime;
        }
}
