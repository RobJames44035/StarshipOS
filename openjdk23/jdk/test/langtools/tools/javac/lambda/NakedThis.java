/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  basic test for capture of non-mutable locals
 * @author  Brian Goetz
 * @author  Maurizio Cimadamore
 * @compile NakedThis.java
 */

class NakedThis {

    interface SAM {
        NakedThis m(int x);
    }

    SAM s1 = (int x) -> this;
    SAM s2 = (int x) -> NakedThis.this;
}
