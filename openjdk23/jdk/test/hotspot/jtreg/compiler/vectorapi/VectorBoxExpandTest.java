/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.vectorapi;

import jdk.incubator.vector.IntVector;

/*
 * @test
 * @bug 8304948
 * @summary C2 crashes when expanding VectorBox
 * @modules jdk.incubator.vector
 * @library /test/lib
 *
 * @run main/othervm -Xbatch -XX:-TieredCompilation -ea -XX:CompileCommand=dontinline,*VectorBoxExpandTest.test compiler.vectorapi.VectorBoxExpandTest
 */
public class VectorBoxExpandTest {

    private static final int ARR_LEN = 1024;
    private static final int NUM_ITER = 2000;

    private static int[] iarr = new int[ARR_LEN];
    private static IntVector g;

    // C2 would generate IR graph like below:
    //
    //                ------------
    //               /            \
    //       Region |  VectorBox   |
    //            \ | /            |
    //             Phi             |
    //              |              |
    //              |              |
    //       Region |  VectorBox   |
    //            \ | /            |
    //             Phi             |
    //              |              |
    //              |\------------/
    //              |
    //
    //
    // which would be optimized by merge_through_phi through Phi::Ideal and some
    // other transformations. Finally C2 would expand VectorBox on a graph like
    // below:
    //
    //                ------------
    //               /            \
    //       Region |  Proj        |
    //            \ | /            |
    //             Phi             |
    //              |              |
    //              |              |
    //       Region |  Proj        |
    //            \ | /            |
    //             Phi             |
    //              |              |
    //              |\------------/
    //              |
    //              |      Phi
    //              |     /
    //           VectorBox
    //
    // where the cycle case should be taken into consideration as well.
    private static void test() {
        IntVector a = IntVector.fromArray(IntVector.SPECIES_PREFERRED, iarr, 0);

        for (int ic = 0; ic < NUM_ITER; ic++) {
            for (int i = 0; i < iarr.length; i++) {
                a = a.add(a);
            }
        }
        g = a;
    }

    public static void main(String[] args) {
        test();
        System.out.println("PASS");
    }
}
