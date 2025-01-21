/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8314106
 * @summary Test that we do not try to copy a Parse Predicate to an unswitched loop if they do not exist anymore.
 * @run main/othervm -Xbatch -XX:-TieredCompilation
 *                   -XX:CompileCommand=compileonly,compiler.predicates.TestLoopUnswitchingWithoutParsePredicates::*
 *                   compiler.predicates.TestLoopUnswitchingWithoutParsePredicates
 */

package compiler.predicates;

public class TestLoopUnswitchingWithoutParsePredicates {
    static byte byFld;
    static byte byArrFld[] = new byte[400];

    public static void main(String[] strArr) {
        for (int i = 0; i < 1000;i++) {
            test(i);
        }
    }

    static void test(int i2) {
        int i10, i11 = 0, i12, i13, i14;
        double dArr[] = new double[400];
        for (i10 = 7; i10 < 307; i10++) {
            byArrFld[i10] = 58;
            for (i12 = 1; i12 < 3; i12++) {
                for (i14 = 1; i14 < 2; i14++) {
                    byFld &= i14;
                    switch (i2) {
                        case 4:
                            dArr[1] = i14;
                        case 2:
                            i13 = i11;
                    }
                }
            }
        }
    }
}

