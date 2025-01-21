/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8346184
 * @summary C2: assert(has_node(i)) failed during split thru phi
 *
 * @run main/othervm -XX:-BackgroundCompilation TestLoadSplitThruPhiNull
 * @run main/othervm -XX:-BackgroundCompilation -XX:-ReduceFieldZeroing TestLoadSplitThruPhiNull
 * @run main TestLoadSplitThruPhiNull
 *
 */

public class TestLoadSplitThruPhiNull {
    private static Object[] fieldArray;
    private static Object fieldObject;

    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test1(true);
            test1(false);
        }
    }

    private static Object test1(boolean flag) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {

                }
            }
        }
        Object[] array = new Object[10];
        fieldArray = array;
        int i;
        for (i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

            }
        }
        Object v = array[i-10];
        if (flag) {
            array[0] = new Object();
        }
        return array[i-10];
    }
}
