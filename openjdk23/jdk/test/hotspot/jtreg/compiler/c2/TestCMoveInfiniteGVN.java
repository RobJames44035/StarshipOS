/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 8280123
 * @run main/othervm -Xcomp -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.c2.TestCMoveInfiniteGVN::test
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:StressSeed=43739875
 *                   compiler.c2.TestCMoveInfiniteGVN
 * @run main/othervm -Xcomp -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.c2.TestCMoveInfiniteGVN::test
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN
 *                   compiler.c2.TestCMoveInfiniteGVN
 */

package compiler.c2;

public class TestCMoveInfiniteGVN {

    static int test(boolean b, int i) {
        int iArr[] = new int[2];

        double d = Math.max(i, i);
        for (int i1 = 1; i1 < 2; i1++) {
            if (i1 != 0) {
                return (b ? 1 : 0); // CMoveI
            }
            for (int i2 = 1; i2 < 2; i2++) {
                switch (i2) {
                    case 1: d -= Math.max(i1, i2); break;
                }
                d -= iArr[i1 - 1];
            }
        }
        return 0;
    }

    static void test() {
        test(true, 234);
    }

    public static void main(String[] strArr) {
        test(); // compilation, then nmethod invalidation during execution
        test(); // trigger crashing recompilation
    }
}
