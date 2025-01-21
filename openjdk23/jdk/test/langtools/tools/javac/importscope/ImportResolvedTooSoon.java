/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 7101822
 * @summary Verify that imports are declarations are processed in the correct order.
 * @compile ImportResolvedTooSoon.java
 */
package pkg;

import static pkg.B.SubInner.Foo;

class B extends A {
     static class SubInner extends Inner { }
}

class A {
     static class Inner {
         static class Foo { }
     }
}
