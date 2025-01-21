/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test id=Xcomp
 * @bug 8333252
 * @summary Test that no Template Assertion Predicate is created in Loop Prediction for one iteration loop.
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,*TestTemplateWithoutOpaqueLoopNodes::test
 *                   compiler.predicates.assertion.TestTemplateWithoutOpaqueLoopNodes
 */

/*
 * @test id=Xbatch
 * @bug 8333252
 * @summary Test that no Template Assertion Predicate is created in Loop Prediction for one iteration loop.
 * @run main/othervm -Xbatch -XX:CompileCommand=compileonly,*TestTemplateWithoutOpaqueLoopNodes::test
 *                   compiler.predicates.assertion.TestTemplateWithoutOpaqueLoopNodes
 */

package compiler.predicates.assertion;

public class TestTemplateWithoutOpaqueLoopNodes {
    static long lFld;
    static long lArr[] = new long[10];

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    static void test() {
        int i16 = 1, i17, i19, i20 = 1, i22;
        for (i17 = 6; i17 < 7; i17++) {
            switch ((i16 >> 1) + 38) {
                case 38:
                    for (i19 = 1; i19 < 200000; i19++) {
                    }
                case 1:
                    for (i22 = 1; i22 < 2; i22 += 2) {
                        lArr[i22] = i20;
                    }
                    break;
                case 4:
                    lFld = 42;
            }
        }
    }
}
