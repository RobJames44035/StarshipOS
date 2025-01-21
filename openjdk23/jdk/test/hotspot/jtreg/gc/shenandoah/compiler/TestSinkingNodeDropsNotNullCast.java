/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * bug 8313262
 * @summary Sinking node may cause required cast to be dropped
 * @requires vm.gc.Shenandoah
 * @run main/othervm -XX:-BackgroundCompilation -XX:+UseShenandoahGC TestSinkingNodeDropsNotNullCast
 */

import java.util.Arrays;

public class TestSinkingNodeDropsNotNullCast {
    public static void main(String[] args) {
        Object[] array1 = new Object[100];
        Object[] array2 = new Object[100];
        Arrays.fill(array2, new Object());
        for (int i = 0; i < 20_000; i++) {
            test(array1);
            test(array1);
            test(array2);
        }
    }

    private static Object test(Object[] array) {
        Object o;
        int i = 1;
        do {
            synchronized (new Object()) {
            }
            o = array[i];
            if (o != null) {
                if (o instanceof A) {
                    return ((A) o).field;
                } else {
                    return o;
                }
            }
            i++;
        } while (i < 100);
        return o;
    }

    private static class A {
        Object field;
    }
}
