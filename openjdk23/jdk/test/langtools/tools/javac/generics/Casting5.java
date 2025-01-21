/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 6559182
 * @summary Cast from a raw type with non-generic supertype to a raw type fails unexpectedly
 * @author Maurizio Cimadamore
 *
 * @compile Casting5.java
 */

class Casting5 {
    static interface Super<P> {}
    static class Y implements Super<Integer>{}
    static interface X extends Super<Double>{}
    static class S<L> extends Y {}
    static interface T<L> extends X {}

    public static void main(String... args) {
        S s = null; // same if I use S<Byte>
        T t = null; // same if I use T<Byte>
        t = (T) s;
    }
}
