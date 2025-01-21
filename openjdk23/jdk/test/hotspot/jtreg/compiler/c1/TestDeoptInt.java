/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/**
 * @test
 * @bug 6769124
 * @summary int value might not be correctly decoded on deopt with c1 on 64 bit
 *
 * @run main/othervm -Xcomp
 *      -XX:CompileCommand=compileonly,compiler.c1.TestDeoptInt::m
 *      compiler.c1.TestDeoptInt
 */

package compiler.c1;

public class TestDeoptInt {

    static class A {
        volatile int vl;
        A(int v) {
            vl = v;
        }
    }

    static void m(int b) {
        A a = new A(10);
        int c;
        c = b + a.vl; //accessing volatile field of class not loaded at compile time forces a deopt
        if(c != 20) {
            System.out.println("a (= " + a.vl + ") + b (= " + b + ") = c (= " + c + ") != 20");
            throw new InternalError();
        }
    }

    public static void main(String[] args) {
        m(10);
    }

}
