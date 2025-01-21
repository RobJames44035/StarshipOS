/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8332827
 * @summary [REDO] C2: crash in compiled code because of dependency on removed range check CastIIs
 *
 * @run main/othervm -XX:-TieredCompilation -XX:-UseOnStackReplacement -XX:-BackgroundCompilation
 *                   -XX:CompileCommand=dontinline,TestArrayAccessAboveRCAfterRCCastIIEliminated::notInlined
 *                   TestArrayAccessAboveRCAfterRCCastIIEliminated
 * @run main/othervm -XX:-TieredCompilation -XX:-UseOnStackReplacement -XX:-BackgroundCompilation
 *                   -XX:CompileCommand=dontinline,TestArrayAccessAboveRCAfterRCCastIIEliminated::notInlined
 *                   -XX:+UnlockDiagnosticVMOptions -XX:+StressGCM TestArrayAccessAboveRCAfterRCCastIIEliminated
 * @run main TestArrayAccessAboveRCAfterRCCastIIEliminated
 * @run main/othervm -XX:CompileCommand=dontinline,TestArrayAccessAboveRCAfterRCCastIIEliminated::notInlined
 *                   TestArrayAccessAboveRCAfterRCCastIIEliminated
 *
 */

public class TestArrayAccessAboveRCAfterRCCastIIEliminated {
    private static int intField;
    private static long longField;
    private static volatile int volatileField;

    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < 20_000; i++) {
            test1(9, 10, 1, true);
            test1(9, 10, 1, false);
            test2(9, 10, 1, true);
            test2(9, 10, 1, false);
            test3(9, 10, 1, true);
            test3(9, 10, 1, false);
            test4(9, 10, 1, true);
            test4(9, 10, 1, false);
            test5(9, 10, 1, true);
            test5(9, 10, 1, false);
            test6(9, 10, 1, true);
            test6(9, 10, 1, false);
            test7(9, 10, 1, true);
            test7(9, 10, 1, false);
            test8(9, 10, 1, true);
            test8(9, 10, 1, false);
            test9(9, 10, 1, true);
            test9(9, 10, 1, false);
            test10(9, 10, 1, true);
            test10(9, 10, 1, false);
            test11(9, 10, 1, true);
            test11(9, 10, 1, false);
            test12(9, 10, 1, true);
            test12(9, 10, 1, false);
            test13(9, 10, 1, true);
            test13(9, 10, 1, false);
        }
        try {
            test1(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test2(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test3(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test4(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test5(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test6(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test7(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test8(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test9(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test10(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test11(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test12(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        try {
            test13(-1, 10, 1, true);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
    }

    private static void test1(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = array[otherArray.length];
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = array[otherArray.length];
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test2(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = 1 / (otherArray.length + 1);
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = 1 / (otherArray.length + 1);
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test3(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = 1L / (otherArray.length + 1);
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = 1L / (otherArray.length + 1);
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test4(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = 1 % (otherArray.length + 1);
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = 1 % (otherArray.length + 1);
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test5(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = 1L % (otherArray.length + 1);
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = 1L % (otherArray.length + 1);
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test6(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = 1 % (otherArray.length + 1) + 1 / (otherArray.length + 1);
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = 1 % (otherArray.length + 1) + 1 / (otherArray.length + 1);
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test7(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = 1L % (otherArray.length + 1) + 1L / (otherArray.length + 1);
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = 1L % (otherArray.length + 1) + 1L / (otherArray.length + 1);
        }
        for (int k = 0; k < 10; k++) {

        }
    }
    private static void test8(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = Integer.divideUnsigned(1, (otherArray.length + 1));
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = Integer.divideUnsigned(1, (otherArray.length + 1));
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test9(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = Long.divideUnsigned(1L, (otherArray.length + 1));
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = Long.divideUnsigned(1L, (otherArray.length + 1));
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test10(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = Integer.remainderUnsigned(1, (otherArray.length + 1));
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = Integer.remainderUnsigned(1, (otherArray.length + 1));
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test11(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = Long.remainderUnsigned(1L, (otherArray.length + 1));
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = Long.remainderUnsigned(1L, (otherArray.length + 1));
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test12(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = Integer.divideUnsigned(1, (otherArray.length + 1)) +
                    Integer.remainderUnsigned(1, (otherArray.length + 1));
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            intField = Integer.divideUnsigned(1, (otherArray.length + 1)) +
                    Integer.remainderUnsigned(1, (otherArray.length + 1));
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void test13(int i, int j, int flag, boolean flag2) {
        i = Math.min(i, 9);
        int[] array = new int[10];
        notInlined(array);
        if (flag == 0) {
        }
        if (flag2) {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = Long.remainderUnsigned(1L, (otherArray.length + 1)) +
                    Long.divideUnsigned(1L, (otherArray.length + 1));
        } else {
            float[] newArray = new float[j];
            newArray[i] = 42;
            float[] otherArray = new float[i];
            if (flag == 0) {
            }
            longField = Long.remainderUnsigned(1L, (otherArray.length + 1)) +
                    Long.divideUnsigned(1L, (otherArray.length + 1));
        }
        for (int k = 0; k < 10; k++) {

        }
    }

    private static void notInlined(int[] array) {

    }
}
