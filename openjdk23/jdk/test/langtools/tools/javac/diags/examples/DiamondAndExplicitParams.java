/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

// key: compiler.misc.diamond.and.explicit.params
// key: compiler.err.cant.apply.diamond.1

class DiamondAndAnonClass {
    static class Foo<X> {
        <Z> Foo() {}
    }
    void m() {
        Foo<String> foo = new <Integer> Foo<>();
    }
}
