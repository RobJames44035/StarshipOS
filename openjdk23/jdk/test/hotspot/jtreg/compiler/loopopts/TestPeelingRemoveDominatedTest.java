/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @requires vm.compiler2.enabled
 * @bug 8269795
 * @summary PhaseIdealLoop::peeled_dom_test_elim wrongly moves a non-dominated test out of a loop together with control dependent data nodes.
 *          This results in a crash due to an out of bounds read of an array.
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -Xcomp -XX:-TieredCompilation -XX:+StressGCM
 *                   -XX:CompileCommand=compileonly,compiler.loopopts.TestPeelingRemoveDominatedTest::* compiler.loopopts.TestPeelingRemoveDominatedTest
 */

package compiler.loopopts;

public class TestPeelingRemoveDominatedTest {
    public static int N = 400;
    static boolean bFld = true;
    static int iArrFld[] = new int[N];

    public static void main(String[] strArr) {
        TestPeelingRemoveDominatedTest _instance = new TestPeelingRemoveDominatedTest();
        for (int i = 0; i < 10; i++) {
            _instance.mainTest();
        }
    }

    public void mainTest() {
        vMeth();
    }


    static void vMeth() {
        iArrFld[1] = 2;
        int i6 = 2;
        while (--i6 > 0) {
            try {
                int i3 = (iArrFld[i6 - 1] / 56);
                iArrFld[1] = (-139 % i3);
            } catch (ArithmeticException a_e) {
            }
            if (bFld) {
            }
        }
    }
}
