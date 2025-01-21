/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8007464
 * @summary Add graph inference support
 *          smoke test for graph inference
 * @compile TargetType56.java
 */
class TargetType56 {
    <Z> Z m(Z z) { return null; }

    void test() {
        double d1 = m(1);
        double d2 = m((Integer)null);
        double d3 = m(m(1));
        double d4 = m(m((Integer)null));
    }
}
