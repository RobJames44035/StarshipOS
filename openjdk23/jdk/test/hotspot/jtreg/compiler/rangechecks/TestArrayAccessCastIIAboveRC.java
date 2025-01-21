/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8319793
 * @summary Replacing a test with a dominating test can cause an array access CastII to float above a range check that guards it
 * @run main/othervm -Xbatch -XX:-TieredCompilation TestArrayAccessCastIIAboveRC
 */

public class TestArrayAccessCastIIAboveRC {
    static int N = 400;
    static int iArrFld[] = new int[N];

    static void test() {
        float fArr[] = new float[N];
        int i9, i10, i12;
        long lArr1[] = new long[N];
        for (i9 = 7; i9 < 43; i9++) {
            try {
                i10 = 7 % i9;
                iArrFld[i9 + 1] = i9 / i10;
            } catch (ArithmeticException a_e) {
            }
            for (i12 = 1; 7 > i12; i12++)
                lArr1[i9 - 1] = 42;
            iArrFld[i12] = 4;
            fArr[i9 - 1] = 0;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50_000; ++i) {
            test();
        }
    }
}
