/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8286625
 * @key stress
 * @summary C2 fails with assert(!n->is_Store() && !n->is_LoadStore()) failed: no node with a side effect
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:-BackgroundCompilation -XX:+StressIGVN -XX:StressSeed=4232417824 TestOverUnrolling2
 * @run main/othervm -XX:+UnlockDiagnosticVMOptions -XX:-BackgroundCompilation -XX:+StressIGVN TestOverUnrolling2
 */

public class TestOverUnrolling2 {
   public static void main(String[] args) {
        final byte[] large = new byte[1000];
        final byte[] src = new byte[16];
        for (int i = 0; i < 20_000; i++) {
            test_helper(large, large);
            test(src);
        }
   }

    private static void test(byte[] src) {
        byte[] array = new byte[16];
        test_helper(src, array);
    }

    private static void test_helper(byte[] src, byte[] array) {
        for (int i = 0; i < src.length; i++) {
            array[array.length - 1 - i] = src[i];
        }
    }
}
