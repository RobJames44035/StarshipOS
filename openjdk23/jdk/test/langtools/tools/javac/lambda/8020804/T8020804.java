/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8020804
 * @summary javac crashes when speculative attribution infers intersection type with array component
 * @compile T8020804.java
 */

import java.util.*;

class T8020804 {
    interface Supplier<D> {
        D make();
    }

    void m(Object o) { }
    void m(char[] c) { }

    <C extends Collection<?>> C g(Supplier<C> sc) { return null; }

    void test() {
        m(g(LinkedList<Double>::new));
    }
}
