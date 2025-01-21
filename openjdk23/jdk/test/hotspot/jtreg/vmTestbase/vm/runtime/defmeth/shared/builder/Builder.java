/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package vm.runtime.defmeth.shared.builder;

/**
 * Builder for some arbitrary type {@code <T>}.
 *
 * @param <T>
 */
interface Builder<T> {

    T build();

    TestBuilder done();

}
