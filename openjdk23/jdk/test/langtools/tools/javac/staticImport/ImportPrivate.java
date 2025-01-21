/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package importPrivate;

import static importPrivate.A.m;

class A {
    private static int m() {
        return 8;
    }
}

class MyTest {
    public static void meth() {
        m();
    }
}
