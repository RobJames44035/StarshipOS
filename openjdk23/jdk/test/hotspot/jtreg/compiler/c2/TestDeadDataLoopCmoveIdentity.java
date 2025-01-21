/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 8271056
 * @summary A dead data loop is created when applying an unsafe case of Cmov'ing identity.
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:CompileCommand=compileonly,compiler.c2.TestDeadDataLoopCmoveIdentity::*
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:StressSeed=359948366 compiler.c2.TestDeadDataLoopCmoveIdentity
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:CompileCommand=compileonly,compiler.c2.TestDeadDataLoopCmoveIdentity::*
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN compiler.c2.TestDeadDataLoopCmoveIdentity
 */

package compiler.c2;

public class TestDeadDataLoopCmoveIdentity {
    static boolean bFld;

    public static void main(String[] strArr) {
        test();
        test2();
    }

    static void test() {
        int i33 = 51925, iArr3[] = new int[10];
        if (bFld) {
            ;
        } else if (bFld) {
            for (int i = 0; i < 100; i++) { }
            do {
                if (i33 != 0) {
                }
                int i34 = 1;
                do {
                    switch (0) {
                        case 122: { }
                    }
                } while (i34 < 1);
                i33 += i33 + 3;
            } while (i33 < 5);
        }
    }

    static void test2() {
        int i33 = 51925, iArr3[] = new int[10];
        if (bFld) {
            ;
        } else if (bFld) {
            do {
                if (i33 != 0) {
                }
                int i34 = 1;
                do {
                    switch (0) {
                        case 122: {}
                    }
                } while (i34 < 1);
            } while (++i33 < 5);
        }
    }
}
