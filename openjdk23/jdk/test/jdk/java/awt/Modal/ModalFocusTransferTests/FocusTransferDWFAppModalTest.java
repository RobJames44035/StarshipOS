/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8047367
 * @summary Check whether the focus transfer between windows occurs correctly when the following
 *          happens: an application modal dialog (D) having null frame owner is shown;
 *          a window having D as owner is shown; a frame is shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FocusTransferDWFAppModalTest
 */

public class FocusTransferDWFAppModalTest {

    public static void main(String[] args) throws Exception {
        FocusTransferDWFTest test = new FocusTransferDWFTest(
                Dialog.ModalityType.APPLICATION_MODAL);
        test.doTest();
    }
}
