/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8047367 8049339
 * @summary Check whether the focus transfer between windows occurs correctly when the following happens:
 *          a frame (F) is shown; a window having F as owner is shown; a non-modal dialog having
 *          a hidden dialog owner is shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FocusTransferFWDNonModal1Test
 */

public class FocusTransferFWDNonModal1Test {

    public static void main(String[] args) throws Exception {
        FocusTransferFWDTest test = new FocusTransferFWDTest(
            null, FocusTransferFWDTest.DialogParent.HIDDEN_DIALOG);
        test.doTest();
    }
}
