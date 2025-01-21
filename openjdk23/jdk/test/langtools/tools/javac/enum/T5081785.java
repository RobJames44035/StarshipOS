/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class A1 {
    public void check() {
        class Foo {
            enum STRENGTH{};
        };
    }
}

class A2 {
    public A2 check() {
        return new A2() { enum STRENGTH{}; };
    }
}

class A3 {
    Object o = new Object() { enum STRENGTH{}; };
}

class A4 {
    class B {
        enum C { X, Y, Z }
    }
}
