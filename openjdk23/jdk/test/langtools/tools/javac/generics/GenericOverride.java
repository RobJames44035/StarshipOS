/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5041233
 * @summary Cannot override non-trivial generic method
 * @author gafter
 *
 * @compile  GenericOverride.java
 */

package generic.override;

interface R<T extends R<T>> {}

class B extends A {
    <U extends Object & R<U>> void f() {}
}

class A {
    <T extends Object & R<T>> void f() {}
}
