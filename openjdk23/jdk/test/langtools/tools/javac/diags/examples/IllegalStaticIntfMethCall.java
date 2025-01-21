/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.illegal.static.intf.meth.call
// options: -XDallowStaticInterfaceMethods

class IllegalStaticIntfMethCall {
    interface A {
        static void m() { }
    }
    void test(A a) {
        a.m();
    }
}
