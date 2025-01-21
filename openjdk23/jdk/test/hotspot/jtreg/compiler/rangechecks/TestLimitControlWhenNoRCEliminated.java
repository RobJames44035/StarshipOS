/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8341407
 * @summary C2: assert(main_limit == cl->limit() || get_ctrl(main_limit) == new_limit_ctrl) failed: wrong control for added limit
 *
 * @run main/othervm -XX:CompileCommand=compileonly,TestLimitControlWhenNoRCEliminated::* -Xcomp TestLimitControlWhenNoRCEliminated
 *
 */

public class TestLimitControlWhenNoRCEliminated {
    static long[] lArr;
    static int iFld;

    public static void main(String[] strArr) {
        try {
            test();
        } catch (NullPointerException npe) {}
    }

    static void test() {
        int x = iFld;
        int i = 1;
        do {
            lArr[i - 1] = 9;
            x += 1;
            iFld += x;
            if (x != 0) {
                A.foo();
            }
        } while (++i < 23);
    }
}

class A {
    static void foo() {
    }
}

