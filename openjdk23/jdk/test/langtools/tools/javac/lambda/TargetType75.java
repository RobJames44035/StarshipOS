/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8016060 8016059
 * @summary Lambda isn't compiled with return statement
 * @compile TargetType75.java
 */
class TargetType75 {
    interface P<X> {
        void m(X x);
    }

    <Z> void m(P<Z> r, Z z) { }

    void test() {
        m(x->{ return; }, "");
        m(x->System.out.println(""), "");
    }
}
