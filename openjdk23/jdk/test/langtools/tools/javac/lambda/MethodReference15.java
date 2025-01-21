/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that assignments involving method references do not trigger transitional 292 warnings
 * @author  Maurizio Cimadamore
 * @compile -Werror MethodReference15.java
 */

public class MethodReference15 {

    interface SAM {
        void m();
    }

    static void m() { }

    static void test() {
        SAM s = MethodReference15::m;
    }
}
