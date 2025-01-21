/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.internal.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Objects;

/**
 * {@link SoftReference} wrapper key for entries in the backing map.
 *
 * @param <T> key type
 *
 * @since 21
 */
final class SoftReferenceKey<T> extends SoftReference<T> implements ReferenceKey<T> {
    /**
     * Saved hashcode of the key. Used when {@link SoftReference} is
     * null.
     */
    private final int hashcode;

    /**
     * Package-Protected constructor.
     *
     * @param key   unwrapped key value
     * @param queue reference queue
     */
    SoftReferenceKey(T key, ReferenceQueue<T> queue) {
        super(key, queue);
        this.hashcode = Objects.hashCode(key);
    }

    /**
     * Cleanup unused key. No need to enqueue since the key did not make it
     * into the map.
     */
    @Override
    public void unused() {
        clear();
    }

    @Override
    public boolean equals(Object obj) {
        // Necessary when removing a null reference
        if (obj == this) {
            return true;
        }
        // Necessary when comparing an unwrapped key
        if (obj instanceof ReferenceKey<?> key) {
            obj = key.get();
        }
        // Note: refersTo is insufficient since keys require equivalence.
        // refersTo would also require a cast to type T.
        return Objects.equals(get(), obj);
    }

    @Override
    public int hashCode() {
        // Use saved hashcode
        return hashcode;
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "#" + System.identityHashCode(this);
    }
}
