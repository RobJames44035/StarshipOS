/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8047367
 * @summary Check whether the focus transfer between windows occurs correctly when
 *          the following happens: a window having a hidden frame owner is shown;
 *          a document modal dialog having a null dialog owner is shown;
 *          a frame is shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FocusTransferWDFDocModal2Test
 */

public class FocusTransferWDFDocModal2Test {

    public static void main(String[] args) throws Exception {
        FocusTransferWDFTest test = new FocusTransferWDFTest(
                Dialog.ModalityType.DOCUMENT_MODAL,
                FocusTransferWDFTest.DialogParent.NULL_DIALOG,
                FocusTransferWDFTest.WindowParent.NEW_FRAME);
        test.doTest();
    }
}
