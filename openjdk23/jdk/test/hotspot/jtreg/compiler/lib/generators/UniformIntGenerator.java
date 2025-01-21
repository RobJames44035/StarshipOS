/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.generators;

/**
 * Provides a uniform int distribution random generator.
 */
final class UniformIntGenerator extends UniformIntersectionRestrictableGenerator<Integer> {
    public UniformIntGenerator(Generators g, int lo, int hi) {
        super(g, lo, hi);
    }

    @Override
    public Integer next() {
        if (hi() == Integer.MAX_VALUE) {
            if (lo() == Integer.MIN_VALUE) {
                return g.random.nextInt();
            }
            return g.random.nextInt(lo() - 1, hi()) + 1;
        }
        return g.random.nextInt(lo(), hi() + 1);
    }

    @Override
    protected RestrictableGenerator<Integer> doRestrictionFromIntersection(Integer lo, Integer hi) {
        return new UniformIntGenerator(g, lo, hi);
    }
}
