/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import jdk.internal.util.ArraysSupport;

/**
 * @test
 * @bug 8218227
 * @summary StringBuilder/StringBuffer constructor throws confusing
 *          NegativeArraySizeException
 * @modules java.base/jdk.internal.util
 * @requires (sun.arch.data.model == "64" & os.maxMemory >= 8G)
 * @run main/othervm -Xms6G -Xmx6G HugeCapacity
 */

public class HugeCapacity {
    private static int failures = 0;

    public static void main(String[] args) {
        testHugeInitialString();
        testHugeInitialCharSequence();
        if (failures > 0) {
            throw new RuntimeException(failures + " tests failed");
        }
    }

    private static void testHugeInitialString() {
        try {
            String str = "Z".repeat(ArraysSupport.SOFT_MAX_ARRAY_LENGTH);
            StringBuffer sb = new StringBuffer(str);
        } catch (OutOfMemoryError ignore) {
        } catch (Throwable unexpected) {
            unexpected.printStackTrace();
            failures++;
        }
    }

    private static void testHugeInitialCharSequence() {
        try {
            CharSequence seq = new MyHugeCharSeq();
            StringBuffer sb = new StringBuffer(seq);
        } catch (OutOfMemoryError ignore) {
        } catch (Throwable unexpected) {
            unexpected.printStackTrace();
            failures++;
        }
    }

    private static class MyHugeCharSeq implements CharSequence {
        public char charAt(int i) {
            throw new UnsupportedOperationException();
        }
        public int length() { return Integer.MAX_VALUE; }
        public CharSequence subSequence(int st, int e) {
            throw new UnsupportedOperationException();
        }
        public String toString() { return ""; }
    }
}
