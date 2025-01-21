/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 7101822
 * @summary Verify that cycles between type parameter bounds and imports/class nesting
 *          are not a problem.
 * @compile TypeParamCycle2.java
 */
package pkg;

import pkg.A.Outer.Inner;

class B extends Inner {
}

class A {
   static class Outer<X extends Inner> { static class Inner {} }
}

