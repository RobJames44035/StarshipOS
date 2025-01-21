/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4787017
 * @summary finding implicit "this" for constructor invocation should ignore hiding, non-inheritance
 *
 * @compile WhichImplicitThis10.java
 */

public class WhichImplicitThis10 {
    static class A {
        class Inner {}
    }

    static class B extends A {
        class Inner extends A.Inner {
            public Inner() {
                // this following is allowed, even though A.Inner is
                // not a member any enclosing class.
                /*B.this.*/super();
            }
        }
    }
}
