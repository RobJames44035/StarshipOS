/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8318562
 * @run main/othervm/timeout=200 -XX:CompileCommand=compileonly,TestConvertImplicitNullCheck::test -XX:-TieredCompilation -Xbatch TestConvertImplicitNullCheck
 * @summary Exercise float to double conversion with implicit null check
 *
 */


public class TestConvertImplicitNullCheck {

    float f = 42;

    static double test(TestConvertImplicitNullCheck t) {
        return t.f; // float to double conversion with implicit null check of 't'
    }

    public static void main(String[] args) {
        // Warmup to trigger C2 compilation
        TestConvertImplicitNullCheck t = new TestConvertImplicitNullCheck();
        for (int i = 0; i < 50_000; ++i) {
            test(t);
        }
        // implicit null check
        try {
            test(null);
            throw new RuntimeException("Test failed as no NullPointerException is thrown");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
