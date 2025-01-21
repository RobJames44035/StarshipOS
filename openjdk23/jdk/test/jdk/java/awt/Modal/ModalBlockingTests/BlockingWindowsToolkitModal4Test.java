/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8049617
 * @summary Check whether a toolkit modal Dialog created with a hidden Dialog
 *          constructor receives focus; whether its components receive focus
 *          and respond to key events, when there are other windows shown.
 *          Also check the correctness of blocking behavior for other windows shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main BlockingWindowsToolkitModal4Test
 */

public class BlockingWindowsToolkitModal4Test {

    public static void main(String[] args) throws Exception {
        (new BlockingWindowsTest(Dialog.ModalityType.TOOLKIT_MODAL,
                BlockingWindowsTest.DialogOwner.HIDDEN_DIALOG)).doTest();
    }
}
