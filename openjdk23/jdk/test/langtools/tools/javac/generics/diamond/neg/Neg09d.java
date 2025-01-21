/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Neg09d {

    static class Nested<X> {}

    void testQualified() {
        Nested<?> m2 = new Neg09.Nested<>() {};
    }
}
