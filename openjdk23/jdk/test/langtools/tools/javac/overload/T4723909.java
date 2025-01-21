/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4723909 4758654 4839284
 * @summary class methods do not conform to JLS 15.12.2.2 definition of most specific method
 * @author gafter
 *
 * @compile T4723909.java
 */

class T4723909 {
    static class Test {
        public static void main(String[] args) {
            new Subclass().test(0);
        }
    }
    static class Superclass {
        static void test(int i) {
            System.out.println("test(int i)");
        }
    }
    static class Subclass extends Superclass {
        static void test(long l) {
            System.out.println("test(long l)");
        }
    }
}
