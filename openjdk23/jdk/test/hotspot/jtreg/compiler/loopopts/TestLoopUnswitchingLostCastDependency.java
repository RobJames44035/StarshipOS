/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @key stress randomness
 * @bug 8241900
 * @summary Loop unswitching may cause dependence on null check to be lost
 *
 * @requires vm.compiler2.enabled
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:+StressGCM -XX:+StressLCM TestLoopUnswitchingLostCastDependency
 */

import java.util.Arrays;

public class TestLoopUnswitchingLostCastDependency {
    private static Object objectField;

    public static void main(String[] args) {
        Object[] array = new Object[100];
        Arrays.fill(array, new Object());
        for (int i = 0; i < 20_000; i++) {
            array[1] = null;
            test(array);
            array[1] = new Object();
            objectField = null;
            test(array);
            array[1] = new Object();
            objectField = new Object();
            test(array);
        }
    }

    private static void test(Object[] array) {
        Object o = objectField;
        Object o3 = array[1];
        int j = 0;
        for (int i = 1; i < 100; i *= 2) {
            Object o2 = array[i];
            // Both branches taken: loop is unswitched.
            if (o3 != null) {
                if (o2 == null) {
                }
                // Both branches taken: loop is unswitched next.
                if (o != null) {
                    // CastPP here becomes control dependent on o2 ==
                    // null check above.
                    if (o.getClass() == Object.class) {
                    }
                    // This causes partial peeling. When that happens,
                    // the o2 == null check becomes redundant with the
                    // o3 != null check in the peeled iteration. The
                    // CastPP with o as input that was control
                    // dependent on the o2 == null check becomes
                    // control dependent on the o3 != null check,
                    // above the o != null check.
                    if (j > 7) {

                    }
                    j++;
                }
            }
        }
    }
}
