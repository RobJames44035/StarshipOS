/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class ErroneousArg {

    private static class Foo {
        static int j() { return 1; }
    }

    static Foo foo = new Foo();

    static void m(String s) { }
    static void m(Integer i) { }

    static int f(String s) { return 1; }

    static int g(String s) { return 1; }
    static int g(Double s) { return 1; }

    int h() { return 1; }
}

class TestErroneousArg extends ErroneousArg {
    static void test() {
        m(unknown()); //method not found
        m(f(1)); //inapplicable method
        m(g(1)); //inapplicable methods
        m(g(null)); //ambiguous
        m(h()); //static error
        m(foo.j()); //inaccessible method
    }
}
