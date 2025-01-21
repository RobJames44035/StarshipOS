/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.List;

class T8067883 {
    void testMethod(List<Integer> li) {
        m(null, li);
        m(1, li);
    }

    void testDiamond(List<Integer> li) {
        new Box<>(null, li);
        new Box<>(1, li);
    }

    <Z> void m(List<Z> z, List<String> ls) { }

    static class Box<X> {
        Box(List<X> z, List<String> ls) { }
    }
}
