/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.generators;

/**
 * Provides a uniform long distribution random generator.
 */
final class UniformLongGenerator extends UniformIntersectionRestrictableGenerator<Long> {
    public UniformLongGenerator(Generators g, Long lo, Long hi) {
        super(g, lo, hi);
    }

    @Override
    public Long next() {
        if (hi() == Long.MAX_VALUE) {
            if (lo() == Long.MIN_VALUE) {
                return g.random.nextLong();
            }
            return g.random.nextLong(lo() - 1, hi()) + 1;
        }
        return g.random.nextLong(lo(), hi() + 1);
    }

    @Override
    protected RestrictableGenerator<Long> doRestrictionFromIntersection(Long lo, Long hi) {
        return new UniformLongGenerator(g, lo, hi);
    }
}
