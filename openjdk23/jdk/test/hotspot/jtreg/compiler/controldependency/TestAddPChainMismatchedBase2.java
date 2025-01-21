/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8303737
 * @summary C2: cast nodes from PhiNode::Ideal() cause "Base pointers must match" assert failure
 * @run main/othervm -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:+StressCCP -Xcomp
 *                   -XX:CompileOnly=TestAddPChainMismatchedBase2::* -XX:StressSeed=1581936900 TestAddPChainMismatchedBase2
 * @run main/othervm -XX:-TieredCompilation -XX:+UnlockDiagnosticVMOptions -XX:+StressIGVN -XX:+StressCCP -Xcomp
 *                   -XX:CompileOnly=TestAddPChainMismatchedBase2::* TestAddPChainMismatchedBase2
 */

public class TestAddPChainMismatchedBase2 {
    static final int N = 400;
    static int iFld;

    public static void main(String[] strArr) {
        test(8);
    }

    static void test(int i2) {
        int i12 = 4, iArr1[] = new int[N];
        double d1, dArr2[] = new double[N];
        do {
            iArr1[i12] = 400907;
            try {
                iArr1[1] = 47 % i2;
            } catch (ArithmeticException a_e) {
            }
            iArr1[i12 + 1] -= d1 = 1;
            while ((d1 += 2) < 5) {
                iArr1 = iArr1;
                iArr1[6] = 3;
            }
        } while (++i12 < 14);
    }
}
