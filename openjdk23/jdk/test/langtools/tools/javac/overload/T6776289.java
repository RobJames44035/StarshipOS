/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6776289
 * @summary Regression: javac7 doesnt resolve method calls properly
 * @compile T6776289.java
 */

class A {
    private void m(int a, int b) { }
}

class T6776289 {
    static void m(int a, String s) { }
    class B extends A {
        public void test() {
            m(1, "");
        }
    }
}
