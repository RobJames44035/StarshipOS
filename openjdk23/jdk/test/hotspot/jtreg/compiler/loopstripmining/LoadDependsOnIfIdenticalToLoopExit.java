/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @bug 8229450
 * @summary shared an identical bool node with a strip-mined loop
 *
 * @run main/othervm -XX:-TieredCompilation -XX:-UseOnStackReplacement -XX:-BackgroundCompilation -XX:LoopMaxUnroll=0 -XX:CompileCommand=dontinline,LoadDependsOnIfIdenticalToLoopExit::not_inlined -XX:CompileCommand=compileonly,LoadDependsOnIfIdenticalToLoopExit::test1 LoadDependsOnIfIdenticalToLoopExit
 *
 */

public class LoadDependsOnIfIdenticalToLoopExit {
    public static void main(String[] args) {
        for (int i = 0; i < 20_000; i++) {
            test1(false, false);
            test1(true, true);
        }
    }

    private static int test1(boolean flag1, boolean flag2) {
        int res = 1;
        int[] array = new int[10];
        not_inlined(array);
        int i;
        for (i = 0; i < 2000; i++) {
            res *= i;
        }

        if (flag1) {
            if (flag2) {
                res++;
            }
        }

        if (i >= 2000) {
            res *= array[0];
        }
        return res;
    }

    private static void not_inlined(int[] array) {
    }
}
