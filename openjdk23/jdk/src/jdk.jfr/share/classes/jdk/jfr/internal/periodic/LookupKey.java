/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.jfr.internal.periodic;

/**
 * Lookup key that can be used in a {@code Map} in
 * case hashCode and equals are incorrectly overridden.
 * <p>
 * {@code IdentityHashMap} can't be used as it will not preserve order.
 */
final class LookupKey {
    private final Object object;

    public LookupKey(Object object) {
        this.object = object;
    }

    public int hashCode() {
        return System.identityHashCode(object);
    }

    public boolean equals(Object that) {
        if (that instanceof LookupKey lookupKey) {
            return lookupKey.object == object;
        }
        return false;
    }
}