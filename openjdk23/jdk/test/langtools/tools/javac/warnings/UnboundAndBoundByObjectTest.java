/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8268148
 * @summary unchecked warnings handle ? and ? extends Object differently
 * @compile -Xlint:all -Werror UnboundAndBoundByObjectTest.java
 */

import java.util.List;

class UnboundAndBoundByObjectTest {
    void f(List<? extends Object> x) {}
    void g(List<?> x) {}

    void h(List<String> x) {
        f((List) x);
        g((List) x);
    }
}
