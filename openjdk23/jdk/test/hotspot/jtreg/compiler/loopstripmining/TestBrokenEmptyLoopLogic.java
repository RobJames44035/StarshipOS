/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8315088
 * @requires vm.compiler2.enabled
 * @summary C2: assert(wq.size() - before == EMPTY_LOOP_SIZE) failed: expect the EMPTY_LOOP_SIZE nodes of this body if empty
 * @run main/othervm -Xbatch -XX:CompileCommand=compileonly,TestBrokenEmptyLoopLogic::* -XX:-TieredCompilation TestBrokenEmptyLoopLogic
 *
 */

public class TestBrokenEmptyLoopLogic {

    public static void main(String[] strArr) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }

    static void test() {
        int i8 = 209, i = 1, i12 = 1;
        while (++i < 8) {
            for (int j = 5; j > 1; j -= 2) {
                i12 = 1;
                do {
                } while (++i12 < 3);
            }
            for (int j = i; j < 5; ++j) {
                i8 += i12;
            }
        }
    }
}
