/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4254215
 * @summary Verify rejection of illegal cyclically-scoped class.
 * @author maddox
 *
 * @run compile/fail CyclicScoping_2.java
 */

class B {
    interface K {}
}

class A extends B implements A.I {

    interface I extends K {
        class J {}
    }

    J j;
}
