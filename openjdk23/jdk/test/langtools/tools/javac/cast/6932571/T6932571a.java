/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug     6932571
 * @summary Compiling Generics causing Inconvertible types
 * @compile T6932571a.java
 */

class T6932571a {
    static class A<T extends Comparable<? super T>> {
        public void test(T v) {
            Object obj = (Integer)v;
        }
    }

    static class B<T extends Comparable<? extends T>> {
        public void test(T v) {
            Object obj = (Integer)v;
        }
    }
}
