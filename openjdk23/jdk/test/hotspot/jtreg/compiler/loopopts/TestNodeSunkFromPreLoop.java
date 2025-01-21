/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8314024
 * @requires vm.compiler2.enabled
 * @summary Node used in check in main loop sunk from pre loop before RC elimination
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseLoopPredicate TestNodeSunkFromPreLoop
 *
 */

public class TestNodeSunkFromPreLoop {
    private static int unusedField;

    public static void main(String[] args) {
        A object = new A();
        for (int i = 0; i < 20_000; i++) {
            test(object, 1000, 0);
        }
    }

    private static int test(A object, int stop, int inv) {
        int res = 0;
        // pre/main/post loops created for this loop
        for (int i = 0; i < stop; i++) {
            // Optimized out. Delay transformation of loop above.
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                }
            }
            // null check in pre loop so field load also in pre loop
            int v = object.field;
            int v2 = (v + inv);
            if (i > 1000) {
                // never taken. v + inv has a use out of loop at an unc.
                unusedField = v2;
            }
            // transformed in the main loop to i + (v + inv)
            int v3 = (v + (i + inv));
            if (v3 > 1000) {
                break;
            }
        }
        return res;
    }

    private static class A {
        public int field;
    }
}
