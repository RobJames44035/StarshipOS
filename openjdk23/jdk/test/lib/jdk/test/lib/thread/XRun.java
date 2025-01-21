/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.test.lib.thread;

/**
 * This type serves no other purpose than to simply allow automatically running
 * something in a thread, and have all exceptions propagated to
 * RuntimeExceptions, which are thrown up to thread, which in turn should
 * probably be a {@link TestThread} to they are stored.
 */
public abstract class XRun implements Runnable {

    /**
     * Invokes {@code xrun()} and throws all exceptions caught in it
     * up to the thread.
     */
    public final void run() {
        try {
            xrun();
        } catch (Error e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Override this method to implement what to run in the thread.
     *
     * @throws Throwable
     */
    protected abstract void xrun() throws Throwable;
}
