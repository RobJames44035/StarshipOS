/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/misctests/clss14702.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.misctests.clss14702.clss14702
 */

package jit.misctests.clss14702;

import nsk.share.TestFailure;

public class  clss14702 {
    static int ML = 1;
    public static void main(String argv[]) {
        clss14702 test = null;
        for (int i = 0; i < ML; i++)
            try {
                if ( test.equals(null) ) {
                    System.out.println("Error! NullPointerException should be thrown.");
                }
                throw new TestFailure("Error! No exception.");
            } catch (Exception e) {
                if ( ! NullPointerException.class.isInstance(e) ) {
                    throw new TestFailure("Error! Exception: " + e);
                }
            }
            System.out.println("Passed");
    }
}
