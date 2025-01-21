/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.Dialog;

/*
 * @test
 * @key headful
 * @bug 8050885
 * @summary Check that calling toFront method for a frame in presence of
 *          blocking application modal dialog having a hidden Frame parent
 *          does not bring the frame to the top of the modal dialog.
 *
 * @library ../helpers /lib/client/
 * @library /test/lib
 * @build ExtendedRobot
 * @build Flag
 * @build TestDialog
 * @build TestFrame
 * @run main FrameToFrontAppModal3Test
 */

public class FrameToFrontAppModal3Test {

    public static void main(String[] args) throws Exception {
        (new FrameToFrontModalBlockedTest(
             Dialog.ModalityType.APPLICATION_MODAL,
             FrameToFrontModalBlockedTest.DialogOwner.HIDDEN_FRAME)).doTest();
    }
}
