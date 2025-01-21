/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class A {
    /**@deprecated*/
    void foo() {
    }
}

class B {
    void bar(A a) {
        a.foo();
    }
}
