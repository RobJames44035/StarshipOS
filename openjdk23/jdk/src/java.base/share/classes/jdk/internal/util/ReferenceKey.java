/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.internal.util;

import java.lang.ref.Reference;

/**
 * View/wrapper of keys used by the backing {@link ReferencedKeyMap}.
 * There are two style of keys; one for entries in the backing map and
 * one for queries to the backing map. This second style avoids the
 * overhead of a {@link Reference} object.
 *
 * @param <T> key type
 *
 * @since 21
 */
public sealed interface ReferenceKey<T> permits StrongReferenceKey, WeakReferenceKey, SoftReferenceKey {
    /**
     * {@return the value of the unwrapped key}
     */
    T get();

    /**
     * Cleanup unused key.
     */
    void unused();

}
