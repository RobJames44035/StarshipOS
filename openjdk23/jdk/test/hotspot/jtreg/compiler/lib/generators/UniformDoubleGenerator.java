/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.generators;

/**
 * Provides a uniform double distribution random generator, in the provided range [lo, hi).
 */
final class UniformDoubleGenerator extends UniformIntersectionRestrictableGenerator<Double> {
    /**
     * Creates a new {@link UniformFloatGenerator}.
     *
     * @param lo Lower bound of the range (inclusive).
     * @param hi Higher bound of the range (exclusive).
     */
    public UniformDoubleGenerator(Generators g, double lo, double hi) {
        super(g, lo, hi);
    }

    @Override
    public Double next() {
        return g.random.nextDouble(lo(), hi());
    }

    @Override
    protected RestrictableGenerator<Double> doRestrictionFromIntersection(Double lo, Double hi) {
        return new UniformDoubleGenerator(g, lo, hi);
    }
}
