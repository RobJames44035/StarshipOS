/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

// key: compiler.misc.feature.pattern.matching.instanceof
// key: compiler.err.feature.not.supported.in.source
// options: -source 15 -Xlint:-options

class PatternMatchingInstanceof {
    boolean m(Object o) {
        return o instanceof String s && s.isEmpty();
    }
}
