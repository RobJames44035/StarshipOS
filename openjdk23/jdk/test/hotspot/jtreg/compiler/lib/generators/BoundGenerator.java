/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.generators;

/**
 * This is a common superclass for all generators that maintain a reference to the Generators object that created them.
 * This allows internally creating other generators or using the {@link RandomnessSource} provided in
 * {@link Generators#random}.
 */
abstract class BoundGenerator<T> implements Generator<T> {
    Generators g;

    BoundGenerator(Generators g) {
        this.g = g;
    }
}
