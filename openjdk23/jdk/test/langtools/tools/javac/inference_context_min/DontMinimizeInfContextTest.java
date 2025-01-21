/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8232933
 * @summary Javac inferred type does not conform to equality constraint
 * @compile DontMinimizeInfContextTest.java
 */

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class DontMinimizeInfContextTest {
    void m() {
        List<? extends A<?, ?>> a = new LinkedList<>();
        Map<String, List<A<?, ?>>> b = a.stream().collect(
                Collectors.groupingBy(A::getval, Collectors.toList())
        );
    }

    class A<K, V> {
        String getval() {
            return "s";
        }
    }
}
