/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

 /**
  * @test
  * @bug 8293978
  * @summary Crash in PhaseIdealLoop::verify_strip_mined_scheduling
  *
  * @run main/othervm -Xbatch TestDuplicateSimpleLoopBackedge
  *
  */

public class TestDuplicateSimpleLoopBackedge {
    static void zero(Byte[] a) {
        for (int e = 0; e < a.length; e++) {
            a[e] = 0;
        }
    }

    int foo(int g) {
        Byte h[] = new Byte[500];
        zero(h);
        short i = 7;
        while (i != 1) {
            i = (short)(i - 3);
        }
        return 0;
    }

    void bar(String[] k) {
        try {
            int l = 5;
            if (l < foo(l)) {
            }
        } catch (Exception m) {
        }
    }

    public static void main(String[] args) {
        TestDuplicateSimpleLoopBackedge n = new TestDuplicateSimpleLoopBackedge();
        for (int i = 0; i < 10000; ++i) {
            n.bar(args);
        }
    }
}
