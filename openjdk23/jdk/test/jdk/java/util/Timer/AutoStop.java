/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8263903
 * @requires vm.gc != "Epsilon"
 * @summary Discarding a Timer causes the Timer thread to stop.
 */

import java.util.Timer;
import java.util.TimerTask;
import java.lang.ref.Reference;

public class AutoStop {
    static final Object wakeup = new Object();
    static Thread tdThread = null;
    static volatile int counter = 0;
    static final int COUNTER_LIMIT = 10;

    public static void main(String[] args) throws Exception {
        Timer t = new Timer();

        // Run an event that records the timer thread.
        t.schedule(new TimerTask() {
                public void run() {
                    synchronized(wakeup) {
                        tdThread = Thread.currentThread();
                        wakeup.notify();
                    }
                }
            }, 0);

        // Wait for the thread to be accessible.
        try {
            synchronized(wakeup) {
                while (tdThread == null) {
                    wakeup.wait();
                }
            }
        } catch (InterruptedException e) {
        }

        // Schedule some events that increment the counter.
        for (int i = 0; i < COUNTER_LIMIT; ++i) {
            t.schedule(new TimerTask() {
                    public void run() {
                        ++counter;
                    }
                }, 100);
        }

        // Ensure the timer is accessible at least until here.
        Reference.reachabilityFence(t);
        t = null;               // Remove the reference to the timer.
        System.gc();            // Run GC to trigger cleanup.
        tdThread.join();        // Wait for thread to stop.
        int finalCounter = counter;
        if (finalCounter != COUNTER_LIMIT) {
            throw new RuntimeException("Unrun events: counter = " + finalCounter);
        }
    }
}

