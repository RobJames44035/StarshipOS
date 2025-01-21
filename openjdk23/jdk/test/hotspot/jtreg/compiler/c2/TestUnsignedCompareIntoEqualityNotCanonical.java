/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8290529
 * @summary C2: assert(BoolTest(btest).is_canonical()) failure
 * @run main/othervm -XX:-BackgroundCompilation -XX:-UseOnStackReplacement -XX:-TieredCompilation TestUnsignedCompareIntoEqualityNotCanonical
 */


public class TestUnsignedCompareIntoEqualityNotCanonical {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test(0);
            test(1);
        }
    }

    private static int test(int x) {
        if (Integer.compareUnsigned(0, x) >= 0) {
            return 42;
        }
        return -42;
    }
}
