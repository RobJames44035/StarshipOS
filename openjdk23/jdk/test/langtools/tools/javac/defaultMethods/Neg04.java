/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class Neg04 {
    interface IA1 { Integer m(); }
    interface IA2 extends IA1 { default Number m() { return Neg04.m(this); } } //error

    abstract class C implements IA1, IA2 {}

    static int m(IA2 a) { return 0; }
}
