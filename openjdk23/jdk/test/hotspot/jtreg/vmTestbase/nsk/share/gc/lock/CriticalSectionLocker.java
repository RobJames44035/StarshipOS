/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.gc.lock;

import nsk.share.TestBug;
import nsk.share.gc.lock.Locker;

/**
 * CriticalSectionLocker represents a way to lock a resource
 * by entering some critical section.
 */
public abstract class CriticalSectionLocker<T> implements Locker<T> {
        private transient boolean enabled = false;
        private transient boolean locked = false;
        private Object sync = new Object();
        private Thread thread;
        private Throwable exception;

        private final Runnable runnable = new Runnable() {
                public void run() {
                        //System.out.println("Running");
                        try {
                                do {
                                        synchronized (sync) {
                                                while (enabled && !locked) {
                                                        try {
                                                                sync.wait();
                                                        } catch (InterruptedException e) {
                                                        }
                                                }
                                                if (!enabled)
                                                        break;
                                        }
                                        do {
                                                criticalSection();
                                        } while (locked);
                                } while (enabled);
                        //      System.out.println("Exiting");
                        } catch (RuntimeException t) {
                                t.printStackTrace();
                                exception = t;
                                throw t;
                        }
                }
        };

        public CriticalSectionLocker() {
        }

        /**
         * Enter critical section.
         *
         */
        protected abstract void criticalSection();

        public void enable() {
                synchronized (sync) {
                        if (enabled)
                                throw new TestBug("Locker already enabled.");
//                      System.out.println("Enabling " + this);
                        enabled = true;
                        thread = new Thread(runnable, "Locker: " + runnable);
                        thread.setDaemon(true);
                        thread.start();
                }
        }

        public void lock() {
                synchronized (sync) {
                        if (locked)
                                throw new TestBug("Locker already locked.");
//                      System.out.println("Locking " + this);
                        locked = true;
                        sync.notifyAll();
                }
        }

        public void unlock() {
                synchronized (sync) {
                        if (!locked)
                                throw new TestBug("Locker not locked.");
//                      System.out.println("Unocking " + this);
                        locked = false;
                        sync.notifyAll();
                }
        }

        public void disable() {
                synchronized (sync) {
                        if (!enabled)
                                throw new TestBug("Locker not enabled.");
//                      System.out.println("Disabling " + this);
                        enabled = false;
                        sync.notifyAll();
                }
                try {
                        thread.join();
                } catch (InterruptedException e) {
                        throw new TestBug(e);
                }
        }

        public Throwable getException() {
                return exception;
        }
}
