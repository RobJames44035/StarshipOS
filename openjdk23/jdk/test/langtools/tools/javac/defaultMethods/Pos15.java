/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary check that overload resolution selects most specific signature
 * @compile Pos15.java
 */

class Pos15 {
    interface A { default String m() { return null; } }
    static abstract class B { abstract public Object m(); }

    static abstract class C extends B implements A {
        void test() {
            m().length();
        }
    }
}
