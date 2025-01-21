/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8047367
 * @summary Check whether the focus transfer between windows occurs correctly when
 *          the following happens: a frame is shown; a modeless dialog (D)
 *          having a null frame owner is shown; a window having D as owner is shown.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @build TestWindow
 * @run main FocusTransferFDWModelessTest
 */

public class FocusTransferFDWModelessTest {

    public static void main(String[] args) throws Exception {
        FocusTransferFDWTest test = new FocusTransferFDWTest(
                Dialog.ModalityType.MODELESS);
        test.doTest();
    }
}
