/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test
 * @bug 6973329
 * @summary C2 with Zero based COOP produces code with broken anti-dependency on x86
 *
 * @run main/othervm -Xbatch -Xcomp
 *    -XX:CompileCommand=compileonly,compiler.c2.Test6973329::*
 *    compiler.c2.Test6973329
 */

package compiler.c2;

public class Test6973329 {
    static class A {
        A next;
        int n;

        public int get_n() {
            return n + 1;
        }
    }

    A a;

    void test(A new_next) {
        A prev_next = a.next;
        a.next = new_next;
        if (prev_next == null) {
            a.n = a.get_n();
        }
    }

    public static void main(String args[]) {
        Test6973329 t = new Test6973329();
        t.a = new A();
        t.a.n = 1;
        t.test(new A());
        if (t.a.n != 2) {
            System.out.println("Wrong value: " + t.a.n + " expected: 2");
            System.exit(97);
        }
    }
}

