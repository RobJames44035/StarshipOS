/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


/*
 * @test
 * @key headful
 * @bug 8049617
 * @summary Check whether a non-modal Dialog created with a null Dialog
 *          constructor receives focus; whether its components receive focus
 *          and respond to key events, when there are other windows shown.
 *          Check also the correctness of blocking behavior for other windows shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main BlockingFDWNonModal2Test
 */

public class BlockingFDWNonModal2Test {

    public static void main(String[] args) throws Exception {
        (new BlockingFDWTest(null,
            BlockingFDWTest.DialogOwner.NULL_DIALOG)).doTest();
    }
}
