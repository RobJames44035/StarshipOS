/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.vectorapi;

/*
 * @test
 * @bug 8333099
 * @summary We should check for is_LoadVector before checking for equality between vector types
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.vectorapi.TestIsLoadVector::test -Xcomp compiler.vectorapi.TestIsLoadVector
 */

public class TestIsLoadVector {
    static int[] iArrFld = new int[400];

    static void test() {
        int i13, i16 = 3;
        short s = 50;
        for (int i = 0; i < 4; i++) {
            for (int i12 = 0; i12 < 8; ++i12) {
                for (int i14 = 2; 82 > i14; i14++) {
                    s <<= 90;
                    do {
                        try {
                            i13 = 0;
                        } catch (ArithmeticException a_e) {
                        }
                    } while (++i16 < 2);
                }
            }
        }
        int i18 = 1;
        while (++i18 < 41) {
            iArrFld[i18] >>= s;
        }
    }

    public static void main(String[] strArr) {
        for (int i = 0; i < 100; i++) {
            test();
        }
    }
}