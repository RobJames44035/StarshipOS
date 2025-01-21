/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * This file is not a regular test, but is processed by ./TreePosTest.java,
 * which verifies the position info in the javac tree.
 * To run the test standalone, compile TreePosTest, then run TreePosTest
 * on this file.
 * @bug 6931927
 * @summary position issues with synthesized anonymous class
 */
class TestAnnotatedAnonClass {
    void m() {
        Object o = new @Deprecated Object() { };
    }
}
