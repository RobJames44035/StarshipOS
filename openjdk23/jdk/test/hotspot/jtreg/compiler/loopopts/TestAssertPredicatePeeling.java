/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8309902
 * @summary C2: assert(false) failed: Bad graph detected in build_loop_late after JDK-8305189
 * @run main/othervm  -Xcomp -XX:CompileCommand=compileonly,TestAssertPredicatePeeling::* TestAssertPredicatePeeling
 */


public class TestAssertPredicatePeeling {
    static volatile long instanceCount;

    public static void main(String[] strArr) {
        test();
    }

    static int test() {
        int i2 = 2, i17 = 3, i18 = 2, iArr[] = new int[10];

        int i15 = 1;
        while (i15 < 100000) {
            for (int i16 = i15; i16 < 1; ++i16) {
                try {
                    iArr[i16] = 5 / iArr[6];
                    i17 = iArr[5] / i2;
                    i2 = i15;
                } catch (ArithmeticException a_e) {
                }
                instanceCount -= i15;
            }
            i15++;
        }
        return i17;
    }
}

