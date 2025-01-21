/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.generators;

class RestrictableSingleValueGenerator<T extends Comparable<T>> implements RestrictableGenerator<T> {
    private final T value;

    RestrictableSingleValueGenerator(T value) {
        this.value = value;
    }

    @Override
    public RestrictableGenerator<T> restricted(T newLo, T newHi) {
        if (newLo.compareTo(value) <= 0 && value.compareTo(newHi) <= 0) {
            return this;
        }
        throw new EmptyGeneratorException();
    }

    @Override
    public T next() {
        return value;
    }
}
