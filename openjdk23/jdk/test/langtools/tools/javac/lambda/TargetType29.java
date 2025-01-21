/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check type-substitution in SAM type wildcards inference
 * @compile TargetType29.java
 */

import java.util.*;

class TargetType29 {
    interface Reducer<E, V> {
        public V reduce(E element, V value);
    }

    private static <E> int reduce(Iterable<? extends E> iterable, Reducer<? super E, Integer> reducer) { return 0;  }

    void test(List<Integer> li) {
        reduce(li, (e, v) -> e + v);
    }
}
