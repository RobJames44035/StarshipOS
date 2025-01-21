/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
 * @test
 * @bug 8261812
 * @summary C2 compilation fails with assert(!had_error) failed: bad dominance
 *
 * @run main/othervm -XX:-BackgroundCompilation TestValAtSafepointOverflowsInt
 *
 */

public class TestValAtSafepointOverflowsInt {
    private static volatile int volatileField;

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            testByte(true, false);
            testByte(false, false);
            testShort(true, false);
            testShort(false, false);
            testChar(true, false);
            testChar(false, false);
        }
        testByte(true, true);
        testShort(true, true);
        testChar(true, true);
    }

    private static Object testByte(boolean flag, boolean flag2) {
        int i;
        // loop to delay constant folding
        for (i = 0; i < 9; i++) {
        }
        C obj = new C();
        if (flag) {
            obj.byteField = (byte)(1 << i);
        } else {
            obj.byteField = (byte)(1 << (i+1));
        }
        // Phi for byte here for uncommon trap in never taken path below
        // Phi inputs don't fit in a byte. Phi transfomed to top.
        if (flag2) {
            return obj;
        }
        return null;
    }

    private static Object testShort(boolean flag, boolean flag2) {
        int i;
        for (i = 0; i < 17; i++) {
        }
        C obj = new C();
        if (flag) {
            obj.shortField = (short)(1 << i);
        } else {
            obj.shortField = (short)(1 << (i+1));
        }
        if (flag2) {
            return obj;
        }
        return null;
    }

    private static Object testChar(boolean flag, boolean flag2) {
        int i;
        for (i = 0; i < 17; i++) {
        }
        C obj = new C();
        if (flag) {
            obj.charField = (char)(1 << i);
        } else {
            obj.charField = (char)(1 << (i+1));
        }
        if (flag2) {
            return obj;
        }
        return null;
    }


    static class C {
        byte byteField;
        short shortField;
        char charField;
    }

}
