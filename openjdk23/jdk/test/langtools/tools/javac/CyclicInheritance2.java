/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4781830
 * @summary incorrect cyclic inheritance error versus name hiding
 *
 * @compile CyclicInheritance2.java
 */

package p1;

class A {
    public class p1 {}
}

class C extends p1.B {
    p1.C p;
}

class B extends p1.A {
    private class p1 {}
}
