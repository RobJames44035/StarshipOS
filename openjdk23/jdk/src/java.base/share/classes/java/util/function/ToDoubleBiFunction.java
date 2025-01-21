/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package java.util.function;

/**
 * Represents a function that accepts two arguments and produces a double-valued
 * result.  This is the {@code double}-producing primitive specialization for
 * {@link BiFunction}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsDouble(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 *
 * @see BiFunction
 * @since 1.8
 */
@FunctionalInterface
public interface ToDoubleBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    double applyAsDouble(T t, U u);
}
