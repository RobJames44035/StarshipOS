/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key stress randomness
 * @summary Logic that moves a null check in the expanded barrier may cause a memory access that doesn't depend on the barrier to bypass the null check
 * @requires vm.gc.Shenandoah
 * @requires vm.flavor == "server"
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:-TieredCompilation
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC
 *                   -XX:+StressGCM -XX:+StressLCM TestExpandedWBLostNullCheckDep
 */

public class TestExpandedWBLostNullCheckDep {

    static void test(int i, int[] arr) {
        // arr.length depends on a null check for arr
        if (i < 0 || i >= arr.length) {
        }
        // The write barrier here also depends on the null check. The
        // null check is moved in the barrier to enable implicit null
        // checks. The null check must not be moved arr.length
        arr[i] = 0x42;
    }

    static public void main(String[] args) {
        int[] int_arr = new int[10];
        for (int i = 0; i < 20000; i++) {
            test(0, int_arr);
        }
        try {
            test(0, null);
        } catch (NullPointerException npe) {}
    }
}
