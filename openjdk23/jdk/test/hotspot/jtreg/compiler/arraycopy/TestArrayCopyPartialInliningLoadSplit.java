/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug JDK-8292780
 * @summary misc tests failed "assert(false) failed: graph should be schedulable"
 *
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement TestArrayCopyPartialInliningLoadSplit
 */

public class TestArrayCopyPartialInliningLoadSplit {
    public static void main(String[] args) {
        byte[] array = new byte[16];
        for (int i = 0; i < 20_0000; i++) {
            test(array, 16, 0, 0);
        }
    }

    private static void test(byte[] array, int length, int srcPos, int dstPos) {
        byte[] nonEscaping = new byte[16];
        nonEscaping[0] = 0x42;
        System.arraycopy(array, srcPos, nonEscaping, 1, 8);
        System.arraycopy(nonEscaping, 0, array, 0, length);
    }
}
