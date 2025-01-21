/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8303737
 * @summary C2: cast nodes from PhiNode::Ideal() cause "Base pointers must match" assert failure
 * @requires vm.gc.Parallel
 * @requires vm.compiler2.enabled
 * @run main/othervm -XX:-BackgroundCompilation -XX:LoopMaxUnroll=2 -XX:+UseParallelGC -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN
 *                   -XX:-UseLoopPredicate -XX:-UseProfiledLoopPredicate -XX:StressSeed=2953783466 TestAddPChainMismatchedBase
 * @run main/othervm -XX:-BackgroundCompilation -XX:LoopMaxUnroll=2 -XX:+UseParallelGC -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN
 *                   -XX:-UseLoopPredicate -XX:-UseProfiledLoopPredicate TestAddPChainMismatchedBase
 */

public class TestAddPChainMismatchedBase {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test();
            testHelper(null, true);
            testHelper2(1000);
        }
    }

    private static void test() {
        int l;
        for (l = 0; l < 5; l++) {
            for (int i = 0; i < 2; i++) {
            }
        }
        testHelper2(l);
    }

    private static void testHelper2(int l) {
        int[] array = new int[1000];
        if (l == 5) {
            l = 4;
        } else {
            l = 1000;
        }
        for (int k = 0; k < 2; k++) {
            int v = 0;
            int i = 0;
            for (; ; ) {
                synchronized (new Object()) {
                }
                array = testHelper(array, false);
                v += array[i];
                int j = i;
                i++;
                if (i >= l) {
                    break;
                }
                array[j] = v;
            }
        }
    }

    private static int[] testHelper(int[] array, boolean flag) {
        if (flag) {
            return new int[1000];
        }
        return array;
    }
}
