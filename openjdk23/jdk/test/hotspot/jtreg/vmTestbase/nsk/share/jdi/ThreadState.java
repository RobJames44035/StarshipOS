/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package nsk.share.jdi;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;

import nsk.share.Failure;

/**
 * Functions to set and wait for states in threads.
 * Used to sync main thread and debuggee thread.
 */
public class ThreadState {
    private final Lock lock = new ReentrantLock();
    private final Condition cond = lock.newCondition();
    private volatile String currentState;
    private long timeoutMs;

    public ThreadState(String startState, long timeoutMs) {
        currentState = startState;
        this.timeoutMs = timeoutMs;
    }

    /**
     * Set new state.
     */
    public void setState(String newState) {
        lock.lock();
        try {
            log(MSG_SET_STATE, newState);
            currentState = newState;
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Wait for the specified state.
     * Throws Failure if timeout.
     */
    public void waitForState(String waitState) {
        lock.lock();
        try {
            log(MSG_WAIT_STATE, waitState);
            while (!currentState.equals(waitState)) {
                if (!cond.await(timeoutMs, TimeUnit.MILLISECONDS)) {
                    throw new Failure(format(MSG_TIMEOUT, waitState));
                }
            }
            log(MSG_GOT_STATE, waitState);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new Failure(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Simple helper that sets a new state and then wait for another state.
     */
    public void setAndWait(String newState, String waitState) {
        setState(newState);
        waitForState(waitState);
    }

    private static final String MSG_TIMEOUT = "ThreadState(thread='%s', state='%s') timeout waiting for %s";
    private static final String MSG_SET_STATE = "ThreadState(thread='%s', state='%s') set state to %s";
    private static final String MSG_WAIT_STATE = "ThreadState(thread='%s', state='%s') waiting for state %s";
    private static final String MSG_GOT_STATE = "ThreadState(thread='%s', state='%s') got state %s";

    private String format(String pattern, String state) {
        final String threadName = Thread.currentThread().getName();
        return String.format(pattern, threadName, currentState, state);
    }

    private void log(String pattern, String state) {
        System.out.println(format(pattern, state));
    }
}
