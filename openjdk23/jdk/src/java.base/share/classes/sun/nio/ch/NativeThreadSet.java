/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.nio.ch;

// Special-purpose data structure for sets of native threads

class NativeThreadSet {
    private static final int OTHER_THREAD_INDEX = -99;

    private final int initialCapacity;
    private long[] threads;             // array of thread handles, created lazily
    private int used;                   // number of thread handles in threads array
    private int otherThreads;           // count of threads without a native thread handle
    private boolean waitingToEmpty;

    NativeThreadSet(int n) {
        initialCapacity = n;
    }

    /**
     * Adds the current thread handle to this set, returning an index so that
     * it can efficiently be removed later.
     */
    int add() {
        long th = NativeThread.current();
        synchronized (this) {
            if (!NativeThread.isNativeThread(th)) {
                otherThreads++;
                return OTHER_THREAD_INDEX;
            }

            // add native thread handle to array, creating or growing array if needed
            int start = 0;
            if (threads == null) {
                threads = new long[initialCapacity];
            } else if (used >= threads.length) {
                int on = threads.length;
                int nn = on * 2;
                long[] nthreads = new long[nn];
                System.arraycopy(threads, 0, nthreads, 0, on);
                threads = nthreads;
                start = on;
            }
            for (int i = start; i < threads.length; i++) {
                if (threads[i] == 0) {
                    threads[i] = th;
                    used++;
                    return i;
                }
            }
            throw new InternalError();
        }
    }

    /**
     * Removes the thread at the given index. A no-op if index is -1.
     */
    void remove(int i) {
        synchronized (this) {
            if (i >= 0) {
                assert threads[i] == NativeThread.current();
                threads[i] = 0;
                used--;
            } else if (i == OTHER_THREAD_INDEX) {
                otherThreads--;
            } else {
                assert i == -1;
                return;
            }
            if (used == 0 && otherThreads == 0 && waitingToEmpty) {
                notifyAll();
            }
        }
    }

    /**
     * Signals all native threads in the thread set and wait for the thread set to empty.
     */
    synchronized void signalAndWait() {
        boolean interrupted = false;
        while (used > 0 || otherThreads > 0) {
            int u = used, i = 0;
            while (u > 0 && i < threads.length) {
                long th = threads[i];
                if (th != 0) {
                    NativeThread.signal(th);
                    u--;
                }
                i++;
            }
            waitingToEmpty = true;
            try {
                wait(50);
            } catch (InterruptedException e) {
                interrupted = true;
            } finally {
                waitingToEmpty = false;
            }
        }
        if (interrupted)
            Thread.currentThread().interrupt();
    }
}
