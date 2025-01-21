/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util.function;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R> {

    R apply(T t) throws Throwable;

    public static <T, R> Function<T, R> toFunction(ThrowingFunction<T, R> v) {
        return t -> {
            try {
                return v.apply(t);
            } catch (Throwable ex) {
                throw ExceptionBox.rethrowUnchecked(ex);
            }
        };
    }

}
