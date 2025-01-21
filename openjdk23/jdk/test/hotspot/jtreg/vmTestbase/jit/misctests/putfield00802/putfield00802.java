/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase jit/misctests/putfield00802.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm jit.misctests.putfield00802.putfield00802
 */

package jit.misctests.putfield00802;

import java.io.PrintStream;
import nsk.share.TestFailure;

public class putfield00802 {

    public double dou_v;
    public static putfield00802 always_null;

    public static void main(String args[]) {
      System.exit(run(args, System.out) + 95/*STATUS_TEMP*/);
    }

    public static int run(String args[], PrintStream out) {
        try {
            always_null.dou_v = 17.0;
            //int i = 1;                        // (1)
        } catch (NullPointerException e) {      // (2)
            return 0/*STATUS_PASSED*/;
        }
        return 2/*STATUS_FAILED*/;
    }
}
