/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8342287
 * @summary Test that a fail path projection of a Template Assertion Predicate is not treated as success path projection.
 * @run main/othervm -XX:-TieredCompilation -Xbatch
 *                   -XX:CompileCommand=compileonly,compiler.predicates.assertion.TestTemplateAssertionPredicateWithTwoUCTs::test
 *                   compiler.predicates.assertion.TestTemplateAssertionPredicateWithTwoUCTs
 */

package compiler.predicates.assertion;

public class TestTemplateAssertionPredicateWithTwoUCTs {
    static int iFld;

    public static void main(String[] strArr) {
        for (int i = 0; i < 1000; i++) {
            test();
        }
    }

    static void test() {
        int[][] lArr = new int[100][1];
        for (int i14 = 5; i14 < 273; ++i14) {
            int i16 = 1;
            while (++i16 < 94) {
                lArr[i16][0] += 1;
                switch (i14) {
                    case 11:
                    case 2:
                    case 13:
                        iFld = 34;
                }
            }
        }
    }
}
