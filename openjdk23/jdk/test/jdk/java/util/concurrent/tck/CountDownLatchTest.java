/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file:
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * Other contributors include Andrew Wright, Jeffrey Hayes,
 * Pat Fisher, Mike Judd.
 */

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CountDownLatchTest extends JSR166TestCase {
    public static void main(String[] args) {
        main(suite(), args);
    }
    public static Test suite() {
        return new TestSuite(CountDownLatchTest.class);
    }

    /**
     * negative constructor argument throws IllegalArgumentException
     */
    public void testConstructor() {
        try {
            new CountDownLatch(-1);
            shouldThrow();
        } catch (IllegalArgumentException success) {}
    }

    /**
     * getCount returns initial count and decreases after countDown
     */
    public void testGetCount() {
        final CountDownLatch l = new CountDownLatch(2);
        assertEquals(2, l.getCount());
        l.countDown();
        assertEquals(1, l.getCount());
    }

    /**
     * countDown decrements count when positive and has no effect when zero
     */
    public void testCountDown() {
        final CountDownLatch l = new CountDownLatch(1);
        assertEquals(1, l.getCount());
        l.countDown();
        assertEquals(0, l.getCount());
        l.countDown();
        assertEquals(0, l.getCount());
    }

    /**
     * await returns after countDown to zero, but not before
     */
    public void testAwait() {
        final CountDownLatch l = new CountDownLatch(2);
        final CountDownLatch pleaseCountDown = new CountDownLatch(1);

        Thread t = newStartedThread(new CheckedRunnable() {
            public void realRun() throws InterruptedException {
                assertEquals(2, l.getCount());
                pleaseCountDown.countDown();
                l.await();
                assertEquals(0, l.getCount());
            }});

        await(pleaseCountDown);
        assertEquals(2, l.getCount());
        l.countDown();
        assertEquals(1, l.getCount());
        if (randomBoolean()) assertThreadBlocks(t, Thread.State.WAITING);
        l.countDown();
        assertEquals(0, l.getCount());
        awaitTermination(t);
    }

    /**
     * timed await returns after countDown to zero
     */
    public void testTimedAwait() {
        final CountDownLatch l = new CountDownLatch(2);
        final CountDownLatch pleaseCountDown = new CountDownLatch(1);

        Thread t = newStartedThread(new CheckedRunnable() {
            public void realRun() throws InterruptedException {
                assertEquals(2, l.getCount());
                pleaseCountDown.countDown();
                assertTrue(l.await(LONG_DELAY_MS, MILLISECONDS));
                assertEquals(0, l.getCount());
            }});

        await(pleaseCountDown);
        assertEquals(2, l.getCount());
        l.countDown();
        assertEquals(1, l.getCount());
        if (randomBoolean()) assertThreadBlocks(t, Thread.State.TIMED_WAITING);
        l.countDown();
        assertEquals(0, l.getCount());
        awaitTermination(t);
    }

    /**
     * await throws InterruptedException if interrupted before counted down
     */
    public void testAwait_Interruptible() {
        final CountDownLatch l = new CountDownLatch(1);
        final CountDownLatch pleaseInterrupt = new CountDownLatch(1);
        Thread t = newStartedThread(new CheckedRunnable() {
            public void realRun() throws InterruptedException {
                Thread.currentThread().interrupt();
                try {
                    l.await();
                    shouldThrow();
                } catch (InterruptedException success) {}
                assertFalse(Thread.interrupted());

                pleaseInterrupt.countDown();
                try {
                    l.await();
                    shouldThrow();
                } catch (InterruptedException success) {}
                assertFalse(Thread.interrupted());

                assertEquals(1, l.getCount());
            }});

        await(pleaseInterrupt);
        if (randomBoolean()) assertThreadBlocks(t, Thread.State.WAITING);
        t.interrupt();
        awaitTermination(t);
    }

    /**
     * timed await throws InterruptedException if interrupted before counted down
     */
    public void testTimedAwait_Interruptible() {
        final int initialCount = ThreadLocalRandom.current().nextInt(1, 3);
        final CountDownLatch l = new CountDownLatch(initialCount);
        final CountDownLatch pleaseInterrupt = new CountDownLatch(1);
        Thread t = newStartedThread(new CheckedRunnable() {
            public void realRun() throws InterruptedException {
                Thread.currentThread().interrupt();
                try {
                    l.await(randomTimeout(), randomTimeUnit());
                    shouldThrow();
                } catch (InterruptedException success) {}
                assertFalse(Thread.interrupted());

                pleaseInterrupt.countDown();
                try {
                    l.await(LONGER_DELAY_MS, MILLISECONDS);
                    shouldThrow();
                } catch (InterruptedException success) {}
                assertFalse(Thread.interrupted());

                assertEquals(initialCount, l.getCount());
            }});

        await(pleaseInterrupt);
        if (randomBoolean()) assertThreadBlocks(t, Thread.State.TIMED_WAITING);
        t.interrupt();
        awaitTermination(t);
    }

    /**
     * timed await times out if not counted down before timeout
     */
    public void testAwaitTimeout() throws InterruptedException {
        final CountDownLatch l = new CountDownLatch(1);
        Thread t = newStartedThread(new CheckedRunnable() {
            public void realRun() throws InterruptedException {
                assertEquals(1, l.getCount());

                long startTime = System.nanoTime();
                assertFalse(l.await(timeoutMillis(), MILLISECONDS));
                assertTrue(millisElapsedSince(startTime) >= timeoutMillis());

                assertEquals(1, l.getCount());
            }});

        awaitTermination(t);
        assertEquals(1, l.getCount());
    }

    /**
     * toString indicates current count
     */
    public void testToString() {
        CountDownLatch s = new CountDownLatch(2);
        assertTrue(s.toString().contains("Count = 2"));
        s.countDown();
        assertTrue(s.toString().contains("Count = 1"));
        s.countDown();
        assertTrue(s.toString().contains("Count = 0"));
    }

}
