/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8012238
 * @summary Nested method capture and inference
 * @compile NestedCapture01.java
 */
class NestedCapture01 {

    void test(String s) {
       g(m(s.getClass()));
    }

    <F extends String> F m(Class<F> cf) {
       return null;
    }

    <P extends String> P g(P vo) {
       return null;
    }
}
