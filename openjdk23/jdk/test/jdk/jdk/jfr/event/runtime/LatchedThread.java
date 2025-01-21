/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */
package jdk.jfr.event.runtime;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class LatchedThread implements Runnable {
    public final static ThreadGroup THREAD_GROUP = new ThreadGroup("Latched Threads");
    public final Thread thread;
    private final CountDownLatch latch = new CountDownLatch(1);
    private final AtomicBoolean alive = new AtomicBoolean(true);

    public LatchedThread(String name, boolean isVirtual) {
        if (isVirtual) {
            thread = Thread.ofVirtual().name(name).unstarted(this);
        } else {
            thread = Thread.ofPlatform().name(name).group(THREAD_GROUP).unstarted(this);
        }
    }

    public LatchedThread(String name) {
        this(name, false);
    }

    public void start() {
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
    }

    public void awaitStarted() throws InterruptedException {
        latch.await();
    }

    public long getId() {
        return thread.getId();
    }

    public String getName() {
        return thread.getName();
    }

    public void stopAndJoin() throws InterruptedException {
        alive.set(false);
        synchronized (alive) {
            alive.notify();
        }
        join();
    }

    public void run() {
        latch.countDown();
        while (alive.get()) {
            try {
                synchronized (alive) {
                    alive.wait(10);
                }
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }
}
