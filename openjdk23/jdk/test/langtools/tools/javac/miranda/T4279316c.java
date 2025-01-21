/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4279316 4758654 4839284
 * @summary inconsistency in overload resolution at compile time
 * @author gafter
 *
 * @compile T4279316c.java
 */

abstract class I {
    abstract void m(J x);
}

abstract class J extends I {
    abstract void m(I x);
}

abstract class A2 extends J {
    void test(J x) {
        this.m(x); // ambiguous
    }
}
