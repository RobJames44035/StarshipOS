/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util.function;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface ThrowingUnaryOperator<T> {

    T apply(T t) throws Throwable;

    public static <T> UnaryOperator<T> toUnaryOperator(
            ThrowingUnaryOperator<T> v) {
        return t -> {
            try {
                return v.apply(t);
            } catch (Throwable ex) {
                throw ExceptionBox.rethrowUnchecked(ex);
            }
        };
    }

}
