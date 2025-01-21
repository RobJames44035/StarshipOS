/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.bench.java.lang;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("preview")
public class ScopedValuesData {

    static final ScopedValue<Integer> sl1 = ScopedValue.newInstance();
    static final ThreadLocal<Integer> tl1 = new ThreadLocal<>();

    static final ScopedValue<Integer> sl2 = ScopedValue.newInstance();
    static final ScopedValue<Integer> sl3 = ScopedValue.newInstance();
    static final ScopedValue<Integer> sl4 = ScopedValue.newInstance();
    static final ScopedValue<Integer> sl5 = ScopedValue.newInstance();
    static final ScopedValue<Integer> sl6 = ScopedValue.newInstance();
    static final ScopedValue<AtomicInteger> sl_atomicInt = ScopedValue.newInstance();

    static final ScopedValue<Integer> unbound = ScopedValue.newInstance();

    static final ScopedValue<AtomicReference<Integer>> sl_atomicRef = ScopedValue.newInstance();

    static final ThreadLocal<Integer> tl2 = new ThreadLocal<>();
    static final ThreadLocal<Integer> tl3 = new ThreadLocal<>();
    static final ThreadLocal<Integer> tl4 = new ThreadLocal<>();
    static final ThreadLocal<Integer> tl5 = new ThreadLocal<>();
    static final ThreadLocal<Integer> tl6 = new ThreadLocal<>();
    static final ThreadLocal<AtomicInteger> tl_atomicInt = new ThreadLocal<>();

    static final ScopedValue.Carrier VALUES = ScopedValue
            .where(sl1, 42).where(sl2, 2).where(sl3, 3)
            .where(sl4, 4).where(sl5, 5).where(sl6, 6);

    public static void run(Runnable action) {
        try {
            tl1.set(42); tl2.set(2); tl3.set(3); tl4.set(4); tl5.set(5); tl6.set(6);
            tl1.get();  // Create the ScopedValue cache as a side effect
            tl_atomicInt.set(new AtomicInteger());
            VALUES.where(sl_atomicInt, new AtomicInteger())
                  .where(sl_atomicRef, new AtomicReference<>())
                  .run(action);
        } finally {
            tl1.remove(); tl2.remove(); tl3.remove(); tl4.remove(); tl5.remove(); tl6.remove();
            tl_atomicInt.remove();
        }
    }
}

