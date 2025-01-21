/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/* @test
 * @bug 7023703
 * @summary Valid code doesn't compile
 * @compile T7023703pos.java
 */

class T7023703pos {

    void testForLoop() {
        final int bug;
        for (;"a".equals("b");) {
            final int item = 0;
        }
        bug = 0; //ok
    }

    void testForEachLoop(boolean cond, java.util.Collection<Integer> c) {
        final int bug;
        for (Integer i : c) {
            if (cond) {
                final int item = 0;
            }
        }
        bug = 0; //ok
    }

    void testWhileLoop() {
        final int bug;
        while ("a".equals("b")) {
            final int item = 0;
        }
        bug = 0; //ok
    }

    void testDoWhileLoop() {
        final int bug;
        do {
            final int item = 0;
        } while ("a".equals("b"));
        bug = 0; //ok
    }

    private static class Inner {
        private final int a, b, c, d, e;

        public Inner() {
            a = b = c = d = e = 0;
        }
    }
}
