/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock;

/**
 * Marker interface for getting Locker.
 */
public interface LockersAware<T> {
        public void setLockers(Lockers<T> locker);
}
