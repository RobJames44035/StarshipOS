/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package jdk.jpackage.test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public class Functional {

    public static <T> Supplier<T> identity(Supplier<T> v) {
        return v;
    }

    public static <T> Consumer<T> identity(Consumer<T> v) {
        return v;
    }

    public static <T, U> BiConsumer<T, U> identity(BiConsumer<T, U> v) {
        return v;
    }

    public static Runnable identity(Runnable v) {
        return v;
    }

    public static <T, R> Function<T, R> identity(Function<T, R> v) {
        return v;
    }

    public static <T, R> Function<T, R> identityFunction(Function<T, R> v) {
        return v;
    }

    public static <T> Predicate<T> identity(Predicate<T> v) {
        return v;
    }

    public static <T> Predicate<T> identityPredicate(Predicate<T> v) {
        return v;
    }
}
