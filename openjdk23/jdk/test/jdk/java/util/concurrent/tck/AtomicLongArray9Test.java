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
 */

import java.util.concurrent.atomic.AtomicLongArray;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AtomicLongArray9Test extends JSR166TestCase {
    public static void main(String[] args) {
        main(suite(), args);
    }
    public static Test suite() {
        return new TestSuite(AtomicLongArray9Test.class);
    }

    /**
     * get and set for out of bound indices throw IndexOutOfBoundsException
     */
    public void testIndexing() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int index : new int[] { -1, SIZE }) {
            final int j = index;
              assertThrows(
                IndexOutOfBoundsException.class,
                () -> aa.getPlain(j),
                () -> aa.getOpaque(j),
                () -> aa.getAcquire(j),
                () -> aa.setPlain(j, 1),
                () -> aa.setOpaque(j, 1),
                () -> aa.setRelease(j, 1),
                () -> aa.compareAndExchange(j, 1, 2),
                () -> aa.compareAndExchangeAcquire(j, 1, 2),
                () -> aa.compareAndExchangeRelease(j, 1, 2),
                () -> aa.weakCompareAndSetPlain(j, 1, 2),
                () -> aa.weakCompareAndSetVolatile(j, 1, 2),
                () -> aa.weakCompareAndSetAcquire(j, 1, 2),
                () -> aa.weakCompareAndSetRelease(j, 1, 2));
        }
    }

    /**
     * getPlain returns the last value set
     */
    public void testGetPlainSet() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            assertEquals(1, aa.getPlain(i));
            aa.set(i, 2);
            assertEquals(2, aa.getPlain(i));
            aa.set(i, -3);
            assertEquals(-3, aa.getPlain(i));
        }
    }

    /**
     * getOpaque returns the last value set
     */
    public void testGetOpaqueSet() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            assertEquals(1, aa.getOpaque(i));
            aa.set(i, 2);
            assertEquals(2, aa.getOpaque(i));
            aa.set(i, -3);
            assertEquals(-3, aa.getOpaque(i));
        }
    }

    /**
     * getAcquire returns the last value set
     */
    public void testGetAcquireSet() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            assertEquals(1, aa.getAcquire(i));
            aa.set(i, 2);
            assertEquals(2, aa.getAcquire(i));
            aa.set(i, -3);
            assertEquals(-3, aa.getAcquire(i));
        }
    }

    /**
     * get returns the last value setPlain
     */
    public void testGetSetPlain() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.setPlain(i, 1);
            assertEquals(1, aa.get(i));
            aa.setPlain(i, 2);
            assertEquals(2, aa.get(i));
            aa.setPlain(i, -3);
            assertEquals(-3, aa.get(i));
        }
    }

    /**
     * get returns the last value setOpaque
     */
    public void testGetSetOpaque() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.setOpaque(i, 1);
            assertEquals(1, aa.get(i));
            aa.setOpaque(i, 2);
            assertEquals(2, aa.get(i));
            aa.setOpaque(i, -3);
            assertEquals(-3, aa.get(i));
        }
    }

    /**
     * get returns the last value setRelease
     */
    public void testGetSetRelease() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.setRelease(i, 1);
            assertEquals(1, aa.get(i));
            aa.setRelease(i, 2);
            assertEquals(2, aa.get(i));
            aa.setRelease(i, -3);
            assertEquals(-3, aa.get(i));
        }
    }

    /**
     * compareAndExchange succeeds in changing value if equal to
     * expected else fails
     */
    public void testCompareAndExchange() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            assertEquals(1, aa.compareAndExchange(i, 1, 2));
            assertEquals(2, aa.compareAndExchange(i, 2, -4));
            assertEquals(-4, aa.get(i));
            assertEquals(-4, aa.compareAndExchange(i,-5, 7));
            assertEquals(-4, aa.get(i));
            assertEquals(-4, aa.compareAndExchange(i, -4, 7));
            assertEquals(7, aa.get(i));
        }
    }

    /**
     * compareAndExchangeAcquire succeeds in changing value if equal to
     * expected else fails
     */
    public void testCompareAndExchangeAcquire() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            assertEquals(1, aa.compareAndExchangeAcquire(i, 1, 2));
            assertEquals(2, aa.compareAndExchangeAcquire(i, 2, -4));
            assertEquals(-4, aa.get(i));
            assertEquals(-4, aa.compareAndExchangeAcquire(i,-5, 7));
            assertEquals(-4, aa.get(i));
            assertEquals(-4, aa.compareAndExchangeAcquire(i, -4, 7));
            assertEquals(7, aa.get(i));
        }
    }

    /**
     * compareAndExchangeRelease succeeds in changing value if equal to
     * expected else fails
     */
    public void testCompareAndExchangeRelease() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            assertEquals(1, aa.compareAndExchangeRelease(i, 1, 2));
            assertEquals(2, aa.compareAndExchangeRelease(i, 2, -4));
            assertEquals(-4, aa.get(i));
            assertEquals(-4, aa.compareAndExchangeRelease(i,-5, 7));
            assertEquals(-4, aa.get(i));
            assertEquals(-4, aa.compareAndExchangeRelease(i, -4, 7));
            assertEquals(7, aa.get(i));
        }
    }

    /**
     * repeated weakCompareAndSetPlain succeeds in changing value when equal
     * to expected
     */
    public void testWeakCompareAndSetPlain() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            do {} while (!aa.weakCompareAndSetPlain(i, 1, 2));
            do {} while (!aa.weakCompareAndSetPlain(i, 2, -4));
            assertEquals(-4, aa.get(i));
            do {} while (!aa.weakCompareAndSetPlain(i, -4, 7));
            assertEquals(7, aa.get(i));
        }
    }

    /**
     * repeated weakCompareAndSetVolatile succeeds in changing value when equal
     * to expected
     */
    public void testWeakCompareAndSetVolatile() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            do {} while (!aa.weakCompareAndSetVolatile(i, 1, 2));
            do {} while (!aa.weakCompareAndSetVolatile(i, 2, -4));
            assertEquals(-4, aa.get(i));
            do {} while (!aa.weakCompareAndSetVolatile(i, -4, 7));
            assertEquals(7, aa.get(i));
        }
    }

    /**
     * repeated weakCompareAndSetAcquire succeeds in changing value when equal
     * to expected
     */
    public void testWeakCompareAndSetAcquire() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            do {} while (!aa.weakCompareAndSetAcquire(i, 1, 2));
            do {} while (!aa.weakCompareAndSetAcquire(i, 2, -4));
            assertEquals(-4, aa.get(i));
            do {} while (!aa.weakCompareAndSetAcquire(i, -4, 7));
            assertEquals(7, aa.get(i));
        }
    }

    /**
     * repeated weakCompareAndSetRelease succeeds in changing value when equal
     * to expected
     */
    public void testWeakCompareAndSetRelease() {
        AtomicLongArray aa = new AtomicLongArray(SIZE);
        for (int i = 0; i < SIZE; i++) {
            aa.set(i, 1);
            do {} while (!aa.weakCompareAndSetRelease(i, 1, 2));
            do {} while (!aa.weakCompareAndSetRelease(i, 2, -4));
            assertEquals(-4, aa.get(i));
            do {} while (!aa.weakCompareAndSetRelease(i, -4, 7));
            assertEquals(7, aa.get(i));
        }
    }

}
