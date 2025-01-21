/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8305189
 * @summary C2 failed "assert(_outcnt==1) failed: not unique"
 * @library /test/lib
 * @run main/othervm -Xcomp -XX:CompileOnly=TestZeroTripGuardShared::* TestZeroTripGuardShared
 */

import jdk.test.lib.Utils;

public class TestZeroTripGuardShared {
    static double dFld1;
    static int iArrFld[];

    public static void main(String[] strArr) throws Exception {
        Thread thread = new Thread() {
                public void run() {
                    test();
                }
            };
        // Give thread some time to trigger compilation
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(Utils.adjustTimeout(500));
    }

    static void test() {
        int i5 = 2, i8 = 2, i9, i11, i12;
        while (i8 < 5) {
            for (i9 = i8; 6 > i9; i9++) {
                for (i11 = 1; i11 > i9; i11 -= 2) {
                    try {
                        i12 = iArrFld[i11];
                    } catch (ArithmeticException a_e) {
                    }
                    i5 += dFld1;
                }
            }
        }
    }
}
