/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8177097
 * @summary Generic method reference returning wildcard parameterized type does not compile
 * @compile T8177097a.java
 */

import java.util.Map;

class T8177097a {
    interface X<O> {
        Map<?, O> apply();
    }

    <O> void go(X<O> x) { }

    static <I> Map<?, Integer> a() {
        return null;
    }

    void test() {
        go(T8177097a::a);
    }
}
