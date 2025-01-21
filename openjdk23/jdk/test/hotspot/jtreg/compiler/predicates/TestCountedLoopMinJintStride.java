/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8328822
 * @summary C2: "negative trip count?" assert failure in profile predicate code
 * @run main/othervm  -XX:-BackgroundCompilation TestCountedLoopMinJintStride
 */

import java.util.Objects;

public class TestCountedLoopMinJintStride {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test1(Integer.MAX_VALUE-1, Integer.MAX_VALUE, 0);
            testHelper1(100, -1, Integer.MAX_VALUE, 0);
            test2(Integer.MAX_VALUE-1, Integer.MAX_VALUE, 0);
            testHelper2(100, -1, Integer.MAX_VALUE, 0);
        }
    }

    private static void test1(int stop, int range, int start) {
        testHelper1(stop, Integer.MIN_VALUE, range, start);
    }

    private static void testHelper1(int stop, int stride, int range, int start) {
        for (int i = stop; i >= start; i += stride) {
            Objects.checkIndex(i, range);
        }
    }

    private static void test2(int stop, int range, int start) {
        testHelper1(stop, Integer.MIN_VALUE, range, start);
    }

    private static void testHelper2(int stop, int stride, int range, int start) {
        for (int i = stop; i >= start; i += stride) {
            if (i < 0 || i >= range) {
                throw new RuntimeException("out of bounds");
            }
        }
    }
}
