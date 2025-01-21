/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8047367
 * @summary Check whether the focus transfer between windows occurs correctly when
 *          the following happens: a window having a frame (F) owner is shown;
 *          a modeless dialog having F owner is shown; F is shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FocusTransferWDFModeless3Test
 */

public class FocusTransferWDFModeless3Test {

    public static void main(String[] args) throws Exception {
        FocusTransferWDFTest test = new FocusTransferWDFTest(
                Dialog.ModalityType.MODELESS,
                FocusTransferWDFTest.DialogParent.FRAME,
                FocusTransferWDFTest.WindowParent.FRAME);
        test.doTest();
    }
}
