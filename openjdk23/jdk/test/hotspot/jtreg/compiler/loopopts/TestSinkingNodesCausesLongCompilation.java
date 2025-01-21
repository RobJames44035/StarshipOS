/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8308103
 * @summary Massive (up to ~30x) increase in C2 compilation time since JDK 17
 * @run main/othervm -Xcomp -XX:CompileOnly=TestSinkingNodesCausesLongCompilation::mainTest -XX:+UnlockDiagnosticVMOptions
 *                   -XX:RepeatCompilation=30 TestSinkingNodesCausesLongCompilation
 */

public class TestSinkingNodesCausesLongCompilation {
    public static final int N = 400;
    public static int iFld=41489;

    public void mainTest(String[] strArr1) {
        int i9=-13, i10=-248, i11=-4, i13=33, i15=-171, i18=-58, iArr2[]=new int[N];

        for (i9 = 7; i9 < 256; i9++) {
            i11 = 1;
            do {
            } while (++i11 < 101);
        }
        for (int i14 : iArr2) {
            for (i15 = 63; 0 < i15; i15 -= 2) {
                i10 *= i13;
                i10 >>= i14;
            }
            for (i18 = 2; 63 > i18; i18++) {
                i10 = iFld;
                iArr2[i18] |= i11;
            }
        }
        System.out.println("i9 = " + i9);
    }

    public static void main(String[] strArr) {
        TestSinkingNodesCausesLongCompilation _instance = new TestSinkingNodesCausesLongCompilation();
        for (int i = 0; i < 10; i++) {
            _instance.mainTest(strArr);
        }
    }
}
