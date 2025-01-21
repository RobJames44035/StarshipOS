/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package java.lang;

import java.lang.ref.Reference;
import java.util.Objects;

/**
 * Functional test for WeakPairMap
 *
 * @author Peter Levart
 */
public class WeakPairMapTest {
    public static void main(String[] args) {
        WeakPairMap<Object, Object, String> pm = new WeakPairMap<>();
        Object key1 = new Object();
        Object key2 = new Object();

        // check for emptiness
        assertEquals(pm.containsKeyPair(key1, key2), false);
        assertEquals(pm.get(key1, key2), null);

        // check for NPE(s)
        for (Object k1 : new Object[]{null, key1}) {
            for (Object k2 : new Object[]{null, key2}) {
                for (String v : new String[]{null, "abc"}) {

                    if (k1 != null && k2 != null && v != null) {
                        // skip non-null args
                        continue;
                    }

                    try {
                        pm.put(k1, k2, v);
                        throw new AssertionError("Unexpected code path, k1=" +
                                                 k1 + ", k2=" + k2 + ", v=" + v);
                    } catch (NullPointerException e) {
                        // expected
                    }

                    try {
                        pm.putIfAbsent(k1, k2, v);
                        throw new AssertionError("Unexpected code path, k1=" +
                                                 k1 + ", k2=" + k2 + ", v=" + v);
                    } catch (NullPointerException e) {
                        // expected
                    }

                    if (k1 != null && k2 != null) {
                        // skip non-null args
                        continue;
                    }

                    try {
                        pm.computeIfAbsent(k1, k2, (_k1, _k2) -> v);
                        throw new AssertionError("Unexpected code path, k1=" +
                                                 k1 + ", k2=" + k2 + ", v=" + v);
                    } catch (NullPointerException e) {
                        // expected
                    }

                    try {
                        pm.containsKeyPair(k1, k2);
                        throw new AssertionError("Unexpected code path, k1=" +
                                                 k1 + ", k2=" + k2);
                    } catch (NullPointerException e) {
                        // expected
                    }

                    try {
                        pm.get(k1, k2);
                        throw new AssertionError("Unexpected code path, k1=" +
                                                 k1 + ", k2=" + k2);
                    } catch (NullPointerException e) {
                        // expected
                    }
                }
            }
        }

        // how much to wait when it is expected for entry to be retained
        final long retentionTimeout = 500L;
        // how much to wait when it is expected for entry to be removed
        final long cleanupTimeout = 30_000L;

        // check insertion
        assertEquals(pm.putIfAbsent(key1, key2, "abc"), null);
        assertEquals(pm.get(key1, key2), "abc");

        // check retention while both keys are still reachable
        assertEquals(gcAndWaitRemoved(pm, "abc", retentionTimeout), false);
        assertEquals(pm.get(key1, key2), "abc");

        // check cleanup when both keys are unreachable
        key1 = null;
        key2 = null;
        assertEquals(gcAndWaitRemoved(pm, "abc", cleanupTimeout), true);

        // new insertion
        key1 = new Object();
        key2 = new Object();
        assertEquals(pm.putIfAbsent(key1, key2, "abc"), null);
        assertEquals(pm.get(key1, key2), "abc");

        // check retention while both keys are still reachable
        assertEquals(gcAndWaitRemoved(pm, "abc", retentionTimeout), false);
        assertEquals(pm.get(key1, key2), "abc");

        // check cleanup when 1st key is unreachable
        key1 = null;
        assertEquals(gcAndWaitRemoved(pm, "abc", cleanupTimeout), true);
        Reference.reachabilityFence(key2);

        // new insertion
        key1 = new Object();
        key2 = new Object();
        assertEquals(pm.putIfAbsent(key1, key2, "abc"), null);
        assertEquals(pm.get(key1, key2), "abc");

        // check retention while both keys are still reachable
        assertEquals(gcAndWaitRemoved(pm, "abc", retentionTimeout), false);
        assertEquals(pm.get(key1, key2), "abc");

        // check cleanup when 2nd key is unreachable
        key2 = null;
        assertEquals(gcAndWaitRemoved(pm, "abc", cleanupTimeout), true);
        Reference.reachabilityFence(key1);
    }

    /**
     * Trigger GC and wait for at most {@code millis} ms for given value to
     * be removed from given WeakPairMap.
     *
     * @return true if element has been removed or false if not
     */
    static <V> boolean gcAndWaitRemoved(WeakPairMap<?, ?, V> pm, V value,
                                        long millis) {
        System.gc();
        for (int i = 0; i < (millis + 99) / 100 && pm.values().contains(value); i++) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new AssertionError("Interrupted");
            }
        }
        return !pm.values().contains(value);
    }

    static void assertEquals(Object actual, Object expected) {
        if (!Objects.equals(actual, expected)) {
            throw new AssertionError("Expected: " + expected + ", actual: " + actual);
        }
    }
}
