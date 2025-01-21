/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util.function;

import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowingSupplier<T> {

    T get() throws Throwable;

    public static <T> Supplier<T> toSupplier(ThrowingSupplier<T> v) {
        return () -> {
            try {
                return v.get();
            } catch (Throwable ex) {
                throw ExceptionBox.rethrowUnchecked(ex);
            }
        };
    }

}
