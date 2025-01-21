/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4254213
 * @summary Verify that member types of classes and interfaces can be inherited.
 *
 * @run compile MemberTypeInheritance.java
 */

class C {
    class D {}
    interface E {}
}

interface I {
    class J {}
    interface K{}
}

class L extends C {}

interface M extends I {}

class X extends C implements I {
    D d;
    E e;
    J j;
    K k;
}

class Y extends L implements M {
    D d;
    E e;
    J j;
    K k;
}

class Outer {

    class C {
        class D {}
        // Inner class cannot have member interface (static member).
    }

    interface I {
        class J {}
        interface K{}
    }

    class L extends C {}

    interface M extends I {}

    class X extends C implements I {
        D d;
        J j;
        K k;
    }

    class Y extends L implements M {
        D d;
        J j;
        K k;
    }

    void test() {

        // Blocks may not contain local interfaces.

        class C {
            class D {}
            // Inner class cannot have member interface (static member).
        }

        class L extends C {}

        class X extends C {
            D d;
        }

        class Y extends L {
            D d;
        }

    }

}
