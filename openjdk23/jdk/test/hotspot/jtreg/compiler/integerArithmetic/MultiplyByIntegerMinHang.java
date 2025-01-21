/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @bug 8213419
 * @summary C2 may hang in MulLNode::Ideal()/MulINode::Ideal() with gcc 8.2.1
 *
 * @run main/othervm -XX:-TieredCompilation -XX:-BackgroundCompilation -XX:-UseOnStackReplacement MultiplyByIntegerMinHang
 *
 */

public class MultiplyByIntegerMinHang {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            if (test1(0) != 0) {
                throw new RuntimeException("incorrect result");
            }
            if (test1(1) != Integer.MIN_VALUE) {
                throw new RuntimeException("incorrect result");
            }
            if (test1(2) != 0) {
                throw new RuntimeException("incorrect result");
            }
            if (test2(0) != 0) {
                throw new RuntimeException("incorrect result");
            }
            if (test2(1) != Long.MIN_VALUE) {
                throw new RuntimeException("incorrect result");
            }
            if (test2(2) != 0) {
                throw new RuntimeException("incorrect result");
            }
        }
    }

    private static int test1(int v) {
        return v * Integer.MIN_VALUE;
    }

    private static long test2(long v) {
        return v * Long.MIN_VALUE;
    }
}
