/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8224539
 * @summary Test arraycopy optimizations with bad src/dst array offsets.
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -Xbatch -XX:+AlwaysIncrementalInline
 *                   compiler.arraycopy.TestArrayCopyWithBadOffset
 */

package compiler.arraycopy;

public class TestArrayCopyWithBadOffset {

    public static byte[] getSrc() {
        return new byte[5];
    }

    // Test bad src offset
    public static void test1(byte[] dst) {
        byte[] src = getSrc();
        try {
            System.arraycopy(src, Integer.MAX_VALUE-1, dst, 0, src.length);
        } catch (Exception e) {
            // Expected
        }
    }

    public static byte[] getDst() {
        return new byte[5];
    }

    // Test bad dst offset
    public static void test2(byte[] src) {
        byte[] dst = getDst();
        try {
            System.arraycopy(src, 0, dst, Integer.MAX_VALUE-1, dst.length);
        } catch (Exception e) {
            // Expected
        }
    }

    public static void main(String[] args) {
        byte[] array = new byte[5];
        for (int i = 0; i < 10_000; ++i) {
            test1(array);
            test2(array);
        }
    }
}
