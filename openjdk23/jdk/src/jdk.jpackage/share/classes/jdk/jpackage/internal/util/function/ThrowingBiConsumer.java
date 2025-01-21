/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U> {

    void accept(T t, U u) throws Throwable;

    public static <T, U> BiConsumer<T, U> toBiConsumer(
            ThrowingBiConsumer<T, U> v) {
        return (t, u) -> {
            try {
                v.accept(t, u);
            } catch (Throwable ex) {
                throw ExceptionBox.rethrowUnchecked(ex);
            }
        };
    }
}
