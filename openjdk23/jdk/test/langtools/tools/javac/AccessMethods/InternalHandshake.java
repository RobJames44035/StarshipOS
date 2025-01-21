/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4430144
 * @summary secret javac internal handshake for super access methods has side-effects.
 *
 * @author gafter
 *
 * @compile InternalHandshake.java
 * @run main InternalHandshake
 */

abstract class B {
    abstract void f();
    void access$1() {
        this.f();
    }
}

public class InternalHandshake extends B {
    void f() {
        System.out.println("correct");
    }
    public static void main(String[] args) {
        new InternalHandshake().access$1();
    }
}
