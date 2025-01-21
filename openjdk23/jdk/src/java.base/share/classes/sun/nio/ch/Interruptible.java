/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.nio.ch;

/**
 * An object that interrupts a thread blocked in an I/O operation.
 */

public interface Interruptible {

    /**
     * Invoked by Thread.interrupt when the given Thread is interrupted. Thread.interrupt
     * invokes this method while holding the given Thread's interrupt lock. This method
     * is also invoked by AbstractInterruptibleChannel when beginning an I/O operation
     * with the current thread's interrupt status set. This method must not block.
     */
    void interrupt(Thread target);

    /**
     * Invoked by Thread.interrupt after releasing the Thread's interrupt lock.
     * It may also be invoked by AbstractInterruptibleChannel or AbstractSelector when
     * beginning an I/O operation with the current thread's interrupt status set, or at
     * the end of an I/O operation when any thread doing I/O on the channel (or selector)
     * has been interrupted. This method closes the channel (or wakes up the Selector) to
     * ensure that AsynchronousCloseException or ClosedByInterruptException is thrown.
     * This method is required to be idempotent.
     */
    void postInterrupt();

}
