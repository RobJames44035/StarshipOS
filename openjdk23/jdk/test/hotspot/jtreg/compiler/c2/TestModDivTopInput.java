/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8283451
 * @summary C2: assert(_base == Long) failed: Not a Long
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressLCM -XX:+StressGCM -XX:+StressCCP -XX:+StressIGVN
 *                   -Xcomp -XX:CompileOnly=TestModDivTopInput::* -XX:-TieredCompilation -XX:StressSeed=87628618 TestModDivTopInput
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:+StressLCM -XX:+StressGCM -XX:+StressCCP -XX:+StressIGVN
 *                   -Xcomp -XX:CompileOnly=TestModDivTopInput::* -XX:-TieredCompilation TestModDivTopInput
 */

public class TestModDivTopInput {

    public static final int N = 400;

    public static float fFld=-2.447F;
    public long lFld=-189L;

    public void mainTest(String[] strArr1) {

        int i18, i20=-14, i21, iArr2[]=new int[N];
        boolean b2=true;
        double d2;
        long l;

        init(iArr2, -13265);

        for (i18 = 13; i18 < 315; ++i18) {
            if (b2) continue;
            for (d2 = 5; d2 < 83; d2++) {
            }
            for (i21 = 4; i21 < 83; i21++) {
                for (l = 1; 2 > l; l++) {
                }
                b2 = b2;
                lFld %= (i20 | 1);
                i20 = (int)fFld;
                i20 += (int)d2;
            }
        }
    }

    public static void main(String[] strArr) {
        TestModDivTopInput _instance = new TestModDivTopInput();
        for (int i = 0; i < 10; i++ ) {
            _instance.mainTest(strArr);
        }
    }

    static void init(int[] arr, int v) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = v;
        }
    }

}
