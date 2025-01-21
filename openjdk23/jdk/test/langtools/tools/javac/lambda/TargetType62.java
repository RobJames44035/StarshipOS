/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8007464
 * @summary Add graph inference support
 *          check that new wildcards inference strategy doesn't run into 7190296
 * @compile TargetType62.java
 */
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class TargetType61 {

    Collector test(Function<Integer, Integer> classifier) {
        return g(classifier, TreeMap::new, m(HashSet::new));
    }

    <R> Collector<Integer, String, R> m(Supplier<R> s) { return null; }

    <T, K, D, M extends Map<K, D>>
            Collector<T, String, M> g(Function<T, K> classifier, Supplier<M> mapFactory, Collector<T, String, D> downstream) { return null; }
}
