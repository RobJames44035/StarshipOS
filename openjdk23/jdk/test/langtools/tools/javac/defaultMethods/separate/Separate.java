/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 7192246
 * @summary smoke test for separate compilation of default methods
 * @author  Maurizio Cimadamore
 * @compile  pkg1/A.java
 * @compile  Separate.java
 */

import pkg1.A;

class Separate {
    interface B extends A.I {
        default void m() { A.m(this); }
    }

    interface C extends A.I, B { }
}
