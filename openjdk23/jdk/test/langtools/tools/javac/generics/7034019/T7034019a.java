/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 7034019
 * @summary ClassCastException in javac with conjunction types
 *
 * @compile T7034019a.java
 */

class T7034019a {
    interface A {
        abstract <T> void foo();
    }

    interface B {
        abstract void foo();
    }

    static class C<T extends A & B> {
        void test(T x) {
            x.foo();
        }
    }
}
