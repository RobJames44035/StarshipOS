/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util.function;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> {

    void accept(T t) throws Throwable;

    public static <T> Consumer<T> toConsumer(ThrowingConsumer<T> v) {
        return o -> {
            try {
                v.accept(o);
            } catch (Throwable ex) {
                throw ExceptionBox.rethrowUnchecked(ex);
            }
        };
    }

}
