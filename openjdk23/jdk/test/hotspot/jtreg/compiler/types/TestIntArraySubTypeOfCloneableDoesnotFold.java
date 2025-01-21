/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8239335
 * @summary C2: assert((Value(phase) == t) || (t != TypeInt::CC_GT && t != TypeInt::CC_EQ)) failed: missing Value() optimization
 * @requires vm.compiler2.enabled
 *
 * @run main/othervm -XX:-BackgroundCompilation TestIntArraySubTypeOfCloneableDoesnotFold
 *
 */


public class TestIntArraySubTypeOfCloneableDoesnotFold {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test();
        }
    }

    private static boolean test() {
        return int[].class.isAssignableFrom(Cloneable.class);
    }
}
