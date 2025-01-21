/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8050885
 * @summary Check that calling toFront method for a frame in presence of
 *          blocking toolkit modal dialog having a hidden Frame parent
 *          does not bring the frame to the top of the modal dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main FrameToFrontTKModal3Test
 */

public class FrameToFrontTKModal3Test {

    public static void main(String[] args) throws Exception {
        (new FrameToFrontModalBlockedTest(
             Dialog.ModalityType.TOOLKIT_MODAL,
             FrameToFrontModalBlockedTest.DialogOwner.HIDDEN_FRAME)).doTest();
    }
}
