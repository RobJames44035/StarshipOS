/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4711325 4862139 5009712
 * @summary compiler allows unimplementable interfaces
 * @author gafter
 *
 * @compile                  T4711325.java
 */

interface A {
    B f();
}
interface B {
    C f();
}
interface C extends A,B {
}
