/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4279316 4758654 4839284
 * @summary inconsistency in overload resolution at compile time
 * @author gafter
 *
 * @compile T4279316b.java
 */

interface I {
    void m(J x);
}

interface J extends I {
    void m(I x);
}

class T {
    void test(J x) {
        x.m(x); // ambiguous
    }
}
