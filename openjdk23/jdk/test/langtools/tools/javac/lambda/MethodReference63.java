/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8005183
 * @summary Missing accessor for constructor reference pointing to private inner class ctor
 */
public class MethodReference63 {

    interface SAM {
        void m();
    }

    static class Foo {
        private Foo() { }
    }

    public static void main(String[] args) {
        SAM s = Foo::new;
        s.m();
    }
}
