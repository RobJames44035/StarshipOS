/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T7023703neg {

    void testForLoop(boolean cond) {
        final int bug;
        final int bug2;
        for (;cond;) {
            final int item = 0;
            bug2 = 1; //error
        }
        bug = 0; //ok
    }

    void testForEachLoop(java.util.Collection<Integer> c) {
        final int bug;
        final int bug2;
        for (Integer i : c) {
            final int item = 0;
            bug2 = 1; //error
        }
        bug = 0; //ok
    }

    void testWhileLoop(boolean cond) {
        final int bug;
        final int bug2;
        while (cond) {
            final int item = 0;
            bug2 = 1; //error
        }
        bug = 0; //ok
    }

    void testDoWhileLoop(boolean cond) {
        final int bug;
        final int bug2;
        do {
            final int item = 0;
            bug2 = 1; //error
        } while (cond);
        bug = 0; //ok
    }
}
