/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8007464
 * @summary Add graph inference support
 *          smoke test for graph inference
 * @compile TargetType53.java
 */
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

class TargetType53 {

    <P> List<List<P>> perm(List<P> l) { return null; }

    void g(List<List<UnaryOperator<IntStream>>> l) { }

    void test() {
        List<List<UnaryOperator<IntStream>>> l =
            perm(Arrays.asList(s -> s.sorted()));
        g(perm(Arrays.asList(s -> s.sorted())));
    }
}
