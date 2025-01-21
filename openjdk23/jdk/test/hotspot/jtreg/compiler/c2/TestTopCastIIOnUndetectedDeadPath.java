/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8319372
 * @summary CastII because of condition guarding it becomes top
 * @requires vm.compiler2.enabled
 * @run main/othervm -Xcomp -XX:CompileOnly=TestTopCastIIOnUndetectedDeadPath::test -XX:CompileCommand=quiet -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:StressSeed=426264791 -XX:+StressIGVN TestTopCastIIOnUndetectedDeadPath
 * @run main/othervm -Xcomp -XX:CompileOnly=TestTopCastIIOnUndetectedDeadPath::test -XX:CompileCommand=quiet -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN TestTopCastIIOnUndetectedDeadPath
 */

public class TestTopCastIIOnUndetectedDeadPath {
    static class X {
        static void m(int[] a) {

        }
    }

    static int array[] = new int[10];

    static void test(int val) {
        for (int i = 1; i < 10; ++i) {
            for (int j = i; j < 10; ++j) {
                if (i == 0 && j != 0) {
                    X.m(array);
                }
                array[j - 1] = val;
            }
        }
    }

    public static void main(String[] arg) {
        test(42);
    }
}
